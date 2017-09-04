package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.*;
import com.royalrangers.repository.achievement.UserYearAchievementRepository;
import com.royalrangers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserYearAchievementService {

    @Autowired
    private UserYearAchievementRepository userYearAchievementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private YearAchievementService yearAchievementService;

    public List<UserYearAchievement> findAllForUser() {
        return userYearAchievementRepository.findByUserId(userService.getAuthenticatedUserId());
    }

    public UserYearAchievement addUserYearAchievement(YearAchievement yearAchievement, User user) {
        UserYearAchievement savedUserAchievement = new UserYearAchievement();
        savedUserAchievement.setAchievementState(AchievementState.NOT_STARTED);
        savedUserAchievement.setUser(user);
        savedUserAchievement.setYearAchievement(yearAchievement);
        userYearAchievementRepository.saveAndFlush(savedUserAchievement);
        return savedUserAchievement;
    }

    public UserYearAchievement getUserYearAchievementById(Long id) {
        return userYearAchievementRepository.findOne(id);
    }

    public List<UserYearAchievement> getUserYearAchievementByAchievementId(Long achievementId) {
        return userYearAchievementRepository.findAllByYearAchievement(achievementId);
    }

    public void deleteUserYearAchievement(Long id) {
        userYearAchievementRepository.delete(id);
    }

    public UserYearAchievement editUserYearAchievement(UserAchievementRequestDto params, Long id) {
        UserYearAchievement savedUserAchievement = userYearAchievementRepository.findOne(id);
        savedUserAchievement.setUpdateDate(new Date());
        savedUserAchievement.setAchievementState(AchievementState.valueOf(params.getState()));
        Integer yearId = params.getId();
        savedUserAchievement.setYearAchievement(yearAchievementService.getYearAchievementById(yearId.longValue()));
        return userYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

}
