package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.dto.achievement.UserQuarterAchievementDto;
import com.royalrangers.dto.achievement.UserTestRequestDto;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.QuarterAchievement;
import com.royalrangers.model.achievement.Test;
import com.royalrangers.model.achievement.UserQuarterAchievement;
import com.royalrangers.model.achievement.UserTest;
import com.royalrangers.repository.achievement.TestRepository;
import com.royalrangers.repository.achievement.UserQuarterAchievementRepository;
import com.royalrangers.repository.achievement.UserTestRepository;
import com.royalrangers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserQuarterAchievementService {

    @Autowired
    private UserQuarterAchievementRepository userQuarterAchievementRepository;

    @Autowired
    private UserQuarterAchievementService userQuarterAchievementService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private QuarterAchievementService quarterAchievementService;

    public List<UserQuarterAchievement> findAllForUser() {
        return userQuarterAchievementRepository.findByUserId(userService.getAuthenticatedUserId());
    }

    public UserQuarterAchievement addUserQuarterAchievement(QuarterAchievement quarterAchievement, User user) {
        UserQuarterAchievement savedUserAchievement = new UserQuarterAchievement();
        savedUserAchievement.setUserAgeGroup(quarterAchievement.getUserAgeGroup());
        savedUserAchievement.setAchievementState(AchievementState.NOT_STARTED);
        savedUserAchievement.setUser(user);
        savedUserAchievement.setQuarterAchievement(quarterAchievement);
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
        String achievementState = params.getState();
        savedUserAchievement.setAchievementState(AchievementState.valueOf(achievementState));
        Integer quarterId = params.getId();
        savedUserAchievement.setQuarterAchievement(quarterAchievementService.getQuarterAchievementById(quarterId.longValue()));
        return userQuarterAchievementRepository.saveAndFlush(savedUserAchievement);
    }

    public void autoEditQuarterAchievement(AchievementState achievementState, UserAgeGroup userAgeGroup) {
        List<UserQuarterAchievement> userQuarterAchievementList = userQuarterAchievementRepository.findByUserAgeGroup(userAgeGroup);
        UserQuarterAchievement savedUserAchievement = userQuarterAchievementList.get(userQuarterAchievementList.size() - 1);
        savedUserAchievement.setUpdateDate(new Date());
        savedUserAchievement.setAchievementState(achievementState);
        userQuarterAchievementRepository.saveAndFlush(savedUserAchievement);
    }


    public UserQuarterAchievement adminAddQuarterToUser(UserQuarterAchievementDto userQuarterAchievementDto) {
        List<User> usersFromSection = userService.getUsersBySectionId(userQuarterAchievementDto.getSectionId());
        if (usersFromSection.size() != 0) {
            Stream.of(usersFromSection.toArray()).forEach(userElement -> {
                User user = (User) userElement;
                Long userId = user.getId();
                UserQuarterAchievement userQuarterAchievement = userQuarterAchievementRepository.findByUserIdAndQuarterAchievementId(userId, userQuarterAchievementDto.getQuarterId());
                if (userQuarterAchievement == null){
                    userQuarterAchievement = userQuarterAchievementService.addUserQuarterAchievement(quarterAchievementService.getQuarterAchievementById(userQuarterAchievementDto.getQuarterId()), user);
                }
                Stream.of(userQuarterAchievementDto.getTestIds().toArray()).forEach(testId -> {
                    UserTest userTest = userTestRepository.findByUserIdAndTestId(userId, (Long) testId);
                    if (userTest == null) {
                        UserTestRequestDto userTestRequestDto = new UserTestRequestDto();
                        userTestRequestDto.setTestId((Long) testId);
                        userTestRequestDto.setUserId(userId);
                        userTestService.addUserTest(userTestRequestDto, user);
                    } else {
                        userTest.setUser(user);
                        userTest.setUserQuarterAchievement(userQuarterAchievement);
                    }
                });

            });
        }

    }



}
