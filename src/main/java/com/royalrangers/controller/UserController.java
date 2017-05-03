package com.royalrangers.controller;

import com.dropbox.core.DbxException;
import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.dto.user.*;
import com.royalrangers.enums.ImageType;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.Views;
import com.royalrangers.service.DropboxService;
import com.royalrangers.service.UserService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final long FILE_MAX_SIZE = 1024*1024;

    @Autowired
    private UserService userService;

    @Autowired
    private DropboxService dropboxService;

    @JsonView(Views.Profile.class)
    @GetMapping
    @ApiOperation(value = "Get current user info")
    public ResponseResult getAuthenticatedUserDetail() {

        String username = userService.getAuthenticatedUserEmail();
        log.info("Get details for user " + username);

        return ResponseBuilder.success(userService.getUserByEmail(username));
    }

    @JsonView(Views.Profile.class)
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get user info by user id (for admin)")
    public ResponseResult getUserDetailById(@PathVariable("id") Long id) {
        try {
            log.info("Get details for user id " + id);
            return ResponseBuilder.success(userService.getUserById(id));
        } catch (UserRepositoryException e){
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @JsonView(Views.Profile.class)
    @GetMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get users for approve (for platoon admin)")
    public ResponseResult getUserToApprove(@PathVariable("id") Long platoonId){
        return ResponseBuilder.success(userService.getUsersForApprove(platoonId));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Approve users after registration (for platoon admin)")
    public ResponseResult approveUser(@RequestBody IdsDto param) {
        List<Long> ids = param.getIds();
        userService.approveUsers(ids);
        return ResponseBuilder.success("Users successfully approved.");
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Reject user after registration (for platoon admin)")
    public ResponseResult rejectUser(@RequestBody IdsDto param) {
        List<Long> ids = param.getIds();
        userService.rejectUsers(ids);
        return ResponseBuilder.success("Users successfully rejected.");
    }

    @PutMapping
    @ApiOperation(value = "Update current user")
    public ResponseResult updateAuthorizedUser(@RequestBody UserUpdateDto update) {

        String email = userService.getAuthenticatedUserEmail();

        userService.updateUser(update);
        log.info("Update user " + email);

        return ResponseBuilder.success(String.format("User %s successful updated", email));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update user by user id(for admin)")
    public ResponseResult updateUserById(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdate) {
        try {
            userService.updateUserById(id, userUpdate);
            log.info("Update user with id %d " + id);

            return ResponseBuilder.success(String.format("User with id %d successful updated", id));
        } catch (UserRepositoryException e){
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
            log.info("Set user avatar public URL: " +avatarUrl);

            userService.setUserAvatarUrl(avatarUrl);

            return ResponseBuilder.success("avatarUrl", avatarUrl);
        } catch (IOException | DbxException e) {
            return  ResponseBuilder.fail(e.getMessage());
        }
    }
}
