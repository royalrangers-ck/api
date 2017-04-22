package com.royalrangers.controller.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.model.Views;
import com.royalrangers.service.achievement.AdminTestService;
import com.royalrangers.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @Autowired
    private AdminTestService adminTestService;

    @JsonView(Views.AchievementUser.class)
    @GetMapping("/achievements/userTest/submitted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseResult getUserTestsForAdmin() {
        return ResponseBuilder.success(adminTestService.getUsersData());
    }
}
