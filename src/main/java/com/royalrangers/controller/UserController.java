package com.royalrangers.controller;

import com.dropbox.core.DbxException;
import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.dto.user.UserUpdateDto;
import com.royalrangers.enums.ImageType;
import com.royalrangers.exception.EntryAlreadyExistsException;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.Views;
import com.royalrangers.service.DropboxService;
import com.royalrangers.service.UserService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final long FILE_MAX_SIZE = 1024 * 1024;

    @Autowired
    private UserService userService;

    @Autowired
    private DropboxService dropboxService;

    @JsonView(Views.Profile.class)
    @GetMapping
    @ApiOperation(value = "Get current user info")
    public ResponseResult getAuthenticatedUserDetail() {
        try {
            String username = userService.getAuthenticatedUserEmail();
            log.info("Get details for user " + username);

            return ResponseBuilder.success(userService.getUserByEmail(username));
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error get current user info");
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get user info (for admin)")
    public ResponseResult getUserDetailById(@PathVariable("userId") Long id) {
        try {
            log.info("Get details for user id " + id);
            return ResponseBuilder.success(userService.getUserById(id));
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error get user info");
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/temp")
    @ApiOperation(value = "Get current temp_user info")
    public ResponseResult getAuthenticatedTempUserDetail() {
        if (!userService.isTempUserExist()) {
            return ResponseBuilder.fail("Temp_user with email " + userService.getAuthenticatedUser().getEmail() + " is not found");
        }
        try {
            return ResponseBuilder.success(userService.getTempUser());
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error get current temp_user info");
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/temp/{temp_userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get temp_user info by id")
    public ResponseResult getTempUserDetailById(@PathVariable("temp_userId") Long id) {
        if (!userService.isTempUserExist()) {
            return ResponseBuilder.fail("Temp_user with id " + id + " is not found");
        }
        try {
            return ResponseBuilder.success(userService.getTempUserById(id));
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error get temp user");
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/approve/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ApiOperation(value = "Get tempUsers for approve update(for platoon admins and super admin)")
    public ResponseResult getTempUsersToApproveUpdate() {
        try {
            return ResponseBuilder.success(userService.getTempUsersForUpdate());
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error get temp users");
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/approve/registration")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ApiOperation(value = "Get users for approve (for platoon admins and super admin)")
    public ResponseResult getUserToApprove() {
        try {
            return ResponseBuilder.success(userService.getUsersForApprove());
        } catch (UserRepositoryException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @PostMapping("/approve/registration/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ApiOperation(value = "Approve users after registration")
    public ResponseResult approveUsers(@PathVariable("userId") Long id) {
        try {
            userService.approveUser(id);
            return ResponseBuilder.success("User is successfully approved.");
        } catch (UserRepositoryException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @PostMapping("/reject/registration/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ApiOperation(value = "Reject user after registration (for platoon admin)")
    public ResponseResult rejectUser(@PathVariable("userId") Long id) {
        try {
            userService.rejectUser(id);
            return ResponseBuilder.success("User is successfully rejected.");
        } catch (UserRepositoryException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @PostMapping("/reject/temp/{tempUserId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ApiOperation(value = "Reject tempUser (for admin)")
    public ResponseResult rejectTempUser(@PathVariable("tempUserId") Long id) {
        try {
            if (userService.getTempUserById(id) == null) {
                return ResponseBuilder.fail("Temp_user with id " + id + " is not found");
            } else {
                userService.rejectTempUser(id);
                return ResponseBuilder.success("TempUser is successfully rejected.");
            }
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error reject temp user");
        }
    }

    @PostMapping("/update/temp")
    @ApiOperation(value = "Update user data (for current user)")
    public ResponseResult updateTempUser(@RequestBody UserUpdateDto update) {
        try {
            userService.updateTempUser(update);
            if (userService.isTempUserEqualsUser(userService.getTempUser())) {
                userService.removeTempUserByAuthenticatedUserId();
                return ResponseBuilder.success("Temp user was deleted as it is the same as user");
            }
            log.info("Update temp_user " + userService.getAuthenticatedUser().getEmail());

            return ResponseBuilder.success("User " + userService.getAuthenticatedUser().getEmail() + " is successfully updated, waiting for approve this update by admin");
        } catch (EntryAlreadyExistsException e) {
            return ResponseBuilder.fail(e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.fail("Error update user data");
        }
    }

    @PostMapping("/update/{temp_userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Confirm to update user data from temp_user data(for admin)")
    public ResponseResult updateUser(@PathVariable("temp_userId") Long id, @RequestBody UserUpdateDto update) {
        try {
            if (userService.getTempUserById(id) == null) {
                return ResponseBuilder.fail("Temp_user with id " + id + " is not found");
            } else {
                log.info("Update temp_user " + userService.getTempUserById(id).getEmail());
                userService.updateUser(id, update);

                return ResponseBuilder.success("User is successfully updated");
            }
        } catch (UserRepositoryException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update user (for admin)")
    public ResponseResult updateUserById(@PathVariable("userId") Long id, @RequestBody UserUpdateDto userUpdate) {
        try {
            userService.updateUserById(id, userUpdate);
            log.info("Update user with id " + id);

            return ResponseBuilder.success("User with id " + id + " is successfully updated");
        } catch (UserRepositoryException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @PostMapping("/avatar")
    @ApiOperation(value = "Upload and set avatar for current user")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {
        if (file.getSize() >= FILE_MAX_SIZE)
            return ResponseBuilder.fail("File too large.");

        try {
            String avatarUrl = dropboxService.imageUpload(file, ImageType.USER_AVATAR);
            log.info("Set user avatar public URL: " + avatarUrl);
            userService.setUserAvatarUrl(avatarUrl);

            return ResponseBuilder.success("avatarUrl", avatarUrl);
        } catch (IOException | DbxException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}