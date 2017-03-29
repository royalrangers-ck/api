package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.achievement.UserTest;
import com.royalrangers.model.achievement.UserTestBean;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserTestService {

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    public List<UserTestBean> getAllUserTestAchievement() {
        List<UserTest> list = userTestRepository.findAll();
        List<UserTestBean> result = new ArrayList<>();
        for (UserTest item : list) {
            UserTestBean userAchievementBean = new UserTestBean();
            userAchievementBean.setId(item.getId());
            userAchievementBean.setCreateDate(item.getCreateDate());
            userAchievementBean.setUpdateDate(item.getUpdateDate());
            userAchievementBean.setAchievementStatus(item.getAchievementStatus());

            UserAchievementBean userBean = UserService.buildUserAchievementBean(item.getUser());
            UserAchievementBean savedUserBean = new UserAchievementBean();
            savedUserBean.setId(userBean.getId());
            savedUserBean.setFirstName(userBean.getFirstName());
            savedUserBean.setLastName(userBean.getLastName());
            savedUserBean.setEmail(userBean.getEmail());
            userAchievementBean.setUser(savedUserBean);

            UserAchievementBean reviewerBean = UserService.buildUserAchievementBean(item.getReviewer());
            UserAchievementBean savedReviewerBean = new UserAchievementBean();
            savedReviewerBean.setId(reviewerBean.getId());
            savedReviewerBean.setFirstName(reviewerBean.getFirstName());
            savedReviewerBean.setLastName(reviewerBean.getLastName());
            savedReviewerBean.setEmail(reviewerBean.getEmail());
            userAchievementBean.setReviewer(savedReviewerBean);

            userAchievementBean.setTest(item.getTest());
            result.add(userAchievementBean);
        }
        return result;
    }

    public void addUserTestAchievement(Map<String, Object> params) {
        UserTest savedUserAchievement = new UserTest();
        savedUserAchievement.setCreateDate(new Date());
        savedUserAchievement.setUpdateDate(new Date());
        Integer achievementStatus = (Integer) params.get("status");
        savedUserAchievement.setAchievementStatus(AchievementStatus.values()[achievementStatus]);
        Integer userId = (Integer) params.get("userId");
        Integer userReviewerId = (Integer) params.get("reviewerId");
        if (!userRepository.exists(userId.longValue()) || !userRepository.exists(userReviewerId.longValue())) {
            throw new UserRepositoryException("Not found user with id");
        }
        savedUserAchievement.setUser(userService.getUserById(userId.longValue()));
        savedUserAchievement.setReviewer(userService.getUserById(userReviewerId.longValue()));
        Integer testId = (Integer) params.get("testId");
        savedUserAchievement.setTest(testService.getTestById(testId.longValue()));
        userTestRepository.saveAndFlush(savedUserAchievement);
    }

    public UserTestBean getUserTestAchievementById(Long id) {
        UserTest user = userTestRepository.findOne(id);
        UserTestBean userYearAchievementBean = new UserTestBean();
        userYearAchievementBean.setId(user.getId());
        userYearAchievementBean.setCreateDate(user.getCreateDate());
        userYearAchievementBean.setUpdateDate(user.getUpdateDate());
        userYearAchievementBean.setAchievementStatus(user.getAchievementStatus());

        UserAchievementBean userBean = UserService.buildUserAchievementBean(user.getUser());
        UserAchievementBean savedUserBean = new UserAchievementBean();
        savedUserBean.setId(userBean.getId());
        savedUserBean.setFirstName(userBean.getFirstName());
        savedUserBean.setLastName(userBean.getLastName());
        savedUserBean.setEmail(userBean.getEmail());
        userYearAchievementBean.setUser(savedUserBean);

        UserAchievementBean reviewerBean = UserService.buildUserAchievementBean(user.getReviewer());
        UserAchievementBean savedReviewerBean = new UserAchievementBean();
        savedReviewerBean.setId(reviewerBean.getId());
        savedReviewerBean.setFirstName(reviewerBean.getFirstName());
        savedReviewerBean.setLastName(reviewerBean.getLastName());
        savedReviewerBean.setEmail(reviewerBean.getEmail());
        userYearAchievementBean.setReviewer(savedReviewerBean);

        userYearAchievementBean.setTest(user.getTest());
        return userYearAchievementBean;
    }

    public void deleteUserTestAchievement(Long id) {
        userTestRepository.delete(id);
    }

    public void editUserTestAchievement(Map<String, Object> params, Long id) {
        UserTest savedUserAchievement = userTestRepository.findOne(id);
        savedUserAchievement.setUpdateDate(new Date());
        Integer achievementStatus = (Integer) params.get("status");
        savedUserAchievement.setAchievementStatus(AchievementStatus.values()[achievementStatus]);
        Integer userId = (Integer) params.get("userId");
        Integer userReviewerId = (Integer) params.get("reviewerId");
        if (!userRepository.exists(userId.longValue()) || !userRepository.exists(userReviewerId.longValue())) {
            throw new UserRepositoryException("Not found user with id");
        }
        savedUserAchievement.setUser(userService.getUserById(userId.longValue()));
        savedUserAchievement.setReviewer(userService.getUserById(userReviewerId.longValue()));
        Integer testId = (Integer) params.get("testId");
        savedUserAchievement.setTest(testService.getTestById(testId.longValue()));
        userTestRepository.saveAndFlush(savedUserAchievement);
    }

}
