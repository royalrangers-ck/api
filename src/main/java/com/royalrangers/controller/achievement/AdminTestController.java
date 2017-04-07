package com.royalrangers.controller.achievement;

import com.royalrangers.bean.ResponseResult;
import com.royalrangers.service.achievement.AdminTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @Autowired
    private AdminTestService adminTestService;

    @GetMapping("/achievements/userTest/submitted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseResult getUserTestsForAdmin(@RequestParam String email) {
        return adminTestService.getUsersData(email);
    }
}