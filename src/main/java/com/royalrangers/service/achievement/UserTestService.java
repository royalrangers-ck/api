package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementRequestDto;
import com.royalrangers.dto.achievement.UserTestRequestDto;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.*;
import com.royalrangers.repository.achievement.RoadMapQuarterRepository;
import com.royalrangers.repository.achievement.UserQuarterAchievementRepository;
import com.royalrangers.repository.achievement.UserTestRepository;
import com.royalrangers.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    private UserQuarterAchievementRepository userQuarterAchievementRepository;

    @Autowired
    private UserQuarterAchievementService userQuarterAchievementService;

    @Autowired
    private RoadMapQuarterRepository roadMapQuarterRepository;

    public List<UserTest> findAllForUser() {
        return userTestRepository.findByUserId(userService.getAuthenticatedUserId());
    }

    public UserTest addUserTest(UserTestRequestDto params) {
        UserTest userTest = new UserTest();
        UserQuarterAchievement userQuarterAchievement = quarterIsExist(params.getTestId());
        Test test = testService.getTestById(params.getTestId());
        User user = userService.getAuthenticatedUser();

        userTest.setAchievementState(AchievementState.IN_PROGRESS);
        userTest.setUser(user);
        userTest.setUserQuarterAchievement(userQuarterAchievement);
        userTest.setTest(testService.getTestById(params.getTestId()));

        List<Task> taskList = test.getTaskList();
        taskList.forEach(task -> userTaskService.addTaskForUser(task));

        log.debug("Successfully added new UserTest for user with id: " + user.getId());
        return userTestRepository.saveAndFlush(userTest);
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

    public UserTest editUserTest(UserAchievementRequestDto params) {
        UserTest savedUserAchievement = userTestRepository.findOne(params.getId().longValue());
        savedUserAchievement.setUpdateDate(new Date());
        String achievementState = params.getState();
        savedUserAchievement.setAchievementState(AchievementState.valueOf(achievementState));
        checkUserQuarterToApprove(savedUserAchievement.getUserQuarterAchievement());
        return userTestRepository.saveAndFlush(savedUserAchievement);
    }

    private void checkUserQuarterToApprove(UserQuarterAchievement userQuarterAchievement) {
        List<UserTest> testList = userQuarterAchievement.getUserTests();
        List<UserTest> additionalTests = userQuarterAchievement.getAdditionalUserTests();
        RoadMapQuarterAchievement roadMapQuarterAchievement = roadMapQuarterRepository.findBySectionIdAndTestsContains(userService.getAuthenticatedUserId(), testList.get(0).getTest());

        List<UserTest> resultList = testList.stream()
                .filter(userTest -> (AchievementState.APPROVED).equals(userTest.getAchievementState()))
                .collect(Collectors.toList());
        List<UserTest> resultAdditionalTests = additionalTests.stream()
                .filter(additionalTest -> (AchievementState.APPROVED).equals(additionalTest.getAchievementState()))
                .collect(Collectors.toList());

        if (resultList.size() == roadMapQuarterAchievement.getTests().size()
            && resultAdditionalTests.size() == roadMapQuarterAchievement.getAdditionalTests().size()) {
            userQuarterAchievement.setAchievementState(AchievementState.SUBMITTED);
            userQuarterAchievementRepository.saveAndFlush(userQuarterAchievement);
            log.debug("Successfully saved AchievementState.SUBMITTED to UserQuarterAchievement with id: " + userQuarterAchievement.getId());
        }
    }

    private UserQuarterAchievement quarterIsExist(Long testId) {
        UserQuarterAchievement userQuarterAchievement = userQuarterAchievementRepository.findByUserIdAndUserTests_Contains(userService.getAuthenticatedUserId(), testService.getTestById(testId));
        if (userQuarterAchievement == null) {
            QuarterAchievement quarterAchievement = getQuarterFromRoadMap(userService.getAuthenticatedUserId(), testId);
            userQuarterAchievement = userQuarterAchievementService.addUserQuarterAchievement(quarterAchievement);
        }
        return userQuarterAchievement;
    }

    private QuarterAchievement getQuarterFromRoadMap(Long userId, Long testId) {
        Test test = testService.getTestById(testId);
        Long sectionId = userService.getUserById(userId).getSection().getId();

        RoadMapQuarterAchievement roadMapQuarterAchievement = roadMapQuarterRepository.findBySectionIdAndTestsContains(sectionId, test);
        log.debug("Successfully get roadMapQuarterAchievement for special section with id: " + sectionId);
        return roadMapQuarterAchievement.getQuarterAchievement();
    }

}
