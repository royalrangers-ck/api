package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.achievement.QuarterAchievement;
import com.royalrangers.model.achievement.UserQuarterAchievement;
import com.royalrangers.repository.achievement.UserQuarterAchievementRepository;
import com.royalrangers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserQuarterAchievementService {

    @Autowired
    private UserQuarterAchievementRepository userQuarterAchievementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuarterAchievementService quarterAchievementService;

    public List<UserQuarterAchievement> findAllForUser() {
        return userQuarterAchievementRepository.findByUserId(userService.getAuthenticatedUserId());
    }

    public UserQuarterAchievement addUserQuarterAchievement(QuarterAchievement quarterAchievement) {
        UserQuarterAchievement savedUserAchievement = new UserQuarterAchievement();
        savedUserAchievement.setUserAgeGroup(quarterAchievement.getUserAgeGroup());
        savedUserAchievement.setAchievementState(AchievementState.IN_PROGRESS);
        savedUserAchievement.setUser(userService.getAuthenticatedUser());
        savedUserAchievement.setQuarterAchievement(quarterAchievement);
        savedUserAchievement.setUserAgeGroup(quarterAchievement.getUserAgeGroup());
        return userQuarterAchievementRepository.saveAndFlush(savedUserAchievement);
    }

    public UserQuarterAchievement getUserQuarterAchievementById(Long id) {
        return userQuarterAchievementRepository.findOne(id);
    }

    public List<UserQuarterAchievement> getUserQuarterAchievementByAchievementId(Long achievementId) {
        return userQuarterAchievementRepository.findAllByQuarterAchievement(achievementId);
    }

    public void deleteUserQuarterAchievement(Long id) {
        userQuarterAchievementRepository.delete(id);
    }

    public UserQuarterAchievement editUserQuarterAchievement(UserAchievementRequestDto params, Long id) {
        UserQuarterAchievement savedUserAchievement = userQuarterAchievementRepository.findOne(id);
        savedUserAchievement.setUpdateDate(new Date());
        savedUserAchievement.setAchievementState(AchievementState.valueOf(params.getState()));
        savedUserAchievement.setQuarterAchievement(quarterAchievementService.getQuarterAchievementById(params.getId().longValue()));
        return userQuarterAchievementRepository.saveAndFlush(savedUserAchievement);
    }

}
