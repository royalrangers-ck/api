package com.royalrangers.controller.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.model.Views;
import com.royalrangers.service.achievement.UserYearAchievementService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/achievements/userYear")
public class UserYearAchievementController {

    @Autowired
    private UserYearAchievementService userYearAchievementService;

    @JsonView(Views.Achievement.class)
    @GetMapping
    @ApiOperation(value = "Get a list of year achievements for current user")
    public ResponseResult getAllUserYearAchievement() {
        try {
            return ResponseBuilder.success(userYearAchievementService.findAllForUser());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed get all user achievement");
        }
    }

    @JsonView(Views.Achievement.class)
    @GetMapping("/{userAchievementId}")
    @ApiOperation(value = "Get year achievement info")
    public ResponseResult getUserYearAchievementById(@PathVariable Long userAchievementId) {
        try {
            return ResponseBuilder.success(userYearAchievementService.getUserYearAchievementById(userAchievementId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed get userAchievement by id");
        }
    }

    @DeleteMapping("/{userAchievementId}")
    @ApiOperation(value = "Delete current user year achievement")
    public ResponseResult deleteUserYearAchievement(@PathVariable Long userAchievementId) {
        try {
            userYearAchievementService.deleteUserYearAchievement(userAchievementId);
            return ResponseBuilder.success("UserAchievement was success delete");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed delete userAchievement");
        }
    }

    @PutMapping("/{userAchievementId}")
    @ApiOperation(value = "Update current user year achievement")
    public ResponseResult editUserYearAchievement(@RequestBody UserAchievementRequestDto params, @PathVariable Long userAchievementId) {
        try {
            return ResponseBuilder.success(userYearAchievementService.editUserYearAchievement(params, userAchievementId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail("Failed edit userAchievement");
        }
    }
}
