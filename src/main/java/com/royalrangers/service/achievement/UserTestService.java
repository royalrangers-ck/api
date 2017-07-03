package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.dto.achievement.UserTestRequestDto;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.Task;
import com.royalrangers.model.achievement.Test;
import com.royalrangers.model.achievement.UserTest;
import com.royalrangers.repository.achievement.UserTestRepository;
import com.royalrangers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class UserTestService {

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private UserQuarterAchievementService userQuarterAchievementService;

    public List<UserTest> findAllForUser() {
        return userTestRepository.findByUserId(userService.getAuthenticatedUserId());
    }

    public UserTest addUserTest(UserTestRequestDto params, User user) {
        UserTest savedUserAchievement = new UserTest();
        savedUserAchievement.setAchievementState(AchievementState.NOT_STARTED);
        savedUserAchievement.setUser(user);
        savedUserAchievement.setTest(testService.getTestById(params.getTestId()));
        List<Task> tasks = testService.getTestById(params.getTestId()).getTaskList();
        tasks.forEach(task -> userTaskService.addTaskForUser(task));
        userTestRepository.saveAndFlush(savedUserAchievement);
        return savedUserAchievement;
    }

    public UserTest getUserTestById(Long id) {
        return userTestRepository.findOne(id);
    }

    public List<UserTest> getSubmittedUsersTestsByPlatoon() {
        return userTestRepository.findByUserPlatoonIdAndAchievementState(userService.getAuthenticatedUser().getPlatoon().getId(), AchievementState.SUBMITTED);
    }

    public List<UserTest> getUserTestsByTestId(Long testId) {
        return userTestRepository.findAllByTest(testId);
    }

    public void deleteUserTest(Long id) {
        userTestRepository.delete(id);
    }

    public void editUserTest(UserAchievementRequestDto params, Long id) {
        UserTest savedUserAchievement = userTestRepository.findOne(id);
        savedUserAchievement.setUpdateDate(new Date());
        String achievementState = params.getState();
        savedUserAchievement.setAchievementState(AchievementState.valueOf(achievementState));
        userTestRepository.saveAndFlush(savedUserAchievement);
        Stream.of(UserAgeGroup.values()).forEach(ageGroups -> {
            updateQuarterAchievements(UserAgeGroup.valueOf(ageGroups.toString()));
        });
    }

    public void updateQuarterAchievements(UserAgeGroup userAgeGroup) {
        List<UserTest> userTestListForBeginner = userTestRepository.findByAchievementStateAndTest_UserAgeGroupsContains(AchievementState.APPROVED, new ArrayList<>(Arrays.asList(userAgeGroup)));
        if (checkCountUserApprovedTestForCreate(userTestListForBeginner)) {
            userQuarterAchievementService.addUserQuarterAchievement(userAgeGroup);
        }
        if (checkCountUserApprovedTestForUpdate(userTestListForBeginner)) {
            userQuarterAchievementService.autoEditQuarterAchievement(AchievementState.APPROVED, userAgeGroup);
        }
    }

    private boolean checkCountUserApprovedTestForCreate(List<UserTest> userTestListForBeginner){
        if (userTestListForBeginner.size() == 1 || userTestListForBeginner.size() == 5 || userTestListForBeginner.size() == 9 || userTestListForBeginner.size() == 13 ||
                userTestListForBeginner.size() == 17 || userTestListForBeginner.size() == 21 || userTestListForBeginner.size() == 25 || userTestListForBeginner.size() == 29 ||
                userTestListForBeginner.size() == 33 || userTestListForBeginner.size() == 37 || userTestListForBeginner.size() == 41 || userTestListForBeginner.size() == 45) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkCountUserApprovedTestForUpdate(List<UserTest> userTestListForBeginner){
        if (userTestListForBeginner.size() == 4 || userTestListForBeginner.size() == 8 ||
                userTestListForBeginner.size() == 12 || userTestListForBeginner.size() == 16 ||
                userTestListForBeginner.size() == 20 || userTestListForBeginner.size() == 24 ||
                userTestListForBeginner.size() == 28 || userTestListForBeginner.size() == 32 ||
                userTestListForBeginner.size() == 36 || userTestListForBeginner.size() == 40 ||
                userTestListForBeginner.size() == 44 || userTestListForBeginner.size() == 48){
            return true;
        } else {
            return false;
        }
    }

}
