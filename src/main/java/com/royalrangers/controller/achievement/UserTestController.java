package com.royalrangers.controller.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.dto.achievement.UserTestRequestDto;
import com.royalrangers.model.Views;
import com.royalrangers.service.achievement.UserTestService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/achievements/userTest")
public class UserTestController {

    @Autowired
    private UserTestService userTestService;

    @JsonView(Views.Achievement.class)
    @GetMapping
    @ApiOperation(value = "Get a list of tests for current user")
    public ResponseResult getAllUserTest() {
        try {
            return ResponseBuilder.success(userTestService.findAllForUser());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed get all UserTestAchievement");
        }
    }

    @JsonView(Views.AchievementUser.class)
    @GetMapping("/submitted")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get a list of submitted tests performed by platoon members (for platoon admin)")
    public ResponseResult getUserTestsForAdmin() {
        try {
            return ResponseBuilder.success(userTestService.getSubmittedUsersTestsByPlatoon());
        } catch (Exception ex) {
            return ResponseBuilder.fail("Failed get UserTestAchievement for admin");
        }
    }

    @PostMapping
    @ApiOperation(value = "Add test for current user")
    public ResponseResult addUserTest(@RequestBody UserTestRequestDto params) {
        try {
            log.info("Add Test " + params.getTestId());
            return ResponseBuilder.success(userTestService.addUserTest(params));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed add UserTestAchievement");
        }
    }

    @JsonView(Views.Achievement.class)
    @GetMapping("/{userTestId}")
    @ApiOperation(value = "Get user test info")
    public ResponseResult getUserTestById(@PathVariable Long userTestId) {
        try {
            return ResponseBuilder.success(userTestService.getUserTestById(userTestId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed get UserTest by id");
        }
    }

    @DeleteMapping("/{userTestId}")
    @ApiOperation(value = "Delete user test")
    public ResponseResult deleteUserTest(@PathVariable Long userTestId) {
        try {
            userTestService.deleteUserTest(userTestId);
            return ResponseBuilder.success("UserTest was success delete");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed delete UserTest");
        }
    }

    @PutMapping("/{userTestId}")
    @ApiOperation(value = "Update user test")
    public ResponseResult editUserTest(@RequestBody UserAchievementRequestDto params) {
        try {
            return ResponseBuilder.success(userTestService.editUserTest(params));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed edit UserTest");
        }
    }
}
