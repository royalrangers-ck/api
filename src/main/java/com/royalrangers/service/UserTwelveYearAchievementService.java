package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.achievement.TwelveYearAchievement;
import com.royalrangers.model.achievement.UserTwelveYearAchievement;
import com.royalrangers.model.achievement.UserTwelveYearAchievementBean;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.UserTwelveYearAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserTwelveYearAchievementService {

    @Autowired
    private UserTwelveYearAchievementRepository userTwelveYearAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TwelveYearAchievementService twelveYearAchievementService;

    public List<UserTwelveYearAchievementBean> getAllUserWithAchievement() {
        List<UserTwelveYearAchievement> list = userTwelveYearAchievementRepository.findAll();
        List<UserTwelveYearAchievementBean> result = new ArrayList<>();
        for (UserTwelveYearAchievement item : list) {
            UserTwelveYearAchievementBean userAchievementBean = new UserTwelveYearAchievementBean();
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

            userAchievementBean.setTwelveYearAchievement(item.getTwelveYearAchievement());
            result.add(userAchievementBean);
        }
        return result;
    }

    public void addUserTwelveYearAchievement(Map<String, Object> params) {
        UserTwelveYearAchievement savedUserAchievement = new UserTwelveYearAchievement();
        savedUserAchievement.setCreateDate(new Date());
        savedUserAchievement.setUpdateDate(new Date());
        Integer achievementStatus = (Integer) params.get("status");
        savedUserAchievement.setAchievementStatus(AchievementStatus.values()[achievementStatus]);
        Integer userId = (Integer) params.get("userId");
        Integer userReviewerId = (Integer) params.get("reviewerId");
        if (!userRepository.exists(userId.longValue()) || !userRepository.exists(userReviewerId.longValue())) {
            throw new UserRepositoryException("Not found user with id " + userId);
        }
        savedUserAchievement.setUser(userService.getUserById(userId.longValue()));
        savedUserAchievement.setReviewer(userService.getUserById(userReviewerId.longValue()));
        Integer twelveYearId = (Integer) params.get("twelveYearAchievementId");
        savedUserAchievement.setTwelveYearAchievement(twelveYearAchievementService.getTwelveYearAchievementById(twelveYearId.longValue()));
        userTwelveYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

    public UserTwelveYearAchievementBean getUserTwelveYearAchievementById(Long id) {
        UserTwelveYearAchievement user = userTwelveYearAchievementRepository.findOne(id);
        UserTwelveYearAchievementBean userYearAchievementBean = new UserTwelveYearAchievementBean();
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

        userYearAchievementBean.setTwelveYearAchievement(user.getTwelveYearAchievement());
        return userYearAchievementBean;
    }

    public void delete(Long id) {
        userTwelveYearAchievementRepository.delete(id);
    }

    public void editTwelveYearAchievement(Map<String, Object> params, Long id) {
        UserTwelveYearAchievement editUserAchievement = userTwelveYearAchievementRepository.findOne(id);
        editUserAchievement.setUpdateDate(new Date());
        Integer userId = (Integer) params.get("userId");
        Integer userReviewerId = (Integer) params.get("reviewerId");
        if (!userRepository.exists(userId.longValue()) || !userRepository.exists(userReviewerId.longValue())) {
            throw new UserRepositoryException("Not found user with id " + userId);
        }
        editUserAchievement.setUser(userService.getUserById(userId.longValue()));
        editUserAchievement.setReviewer(userService.getUserById(userReviewerId.longValue()));
        Integer twelveYearId = (Integer) params.get("twelveYearAchievementId");
        editUserAchievement.setTwelveYearAchievement(twelveYearAchievementService.getTwelveYearAchievementById(twelveYearId.longValue()));
        userTwelveYearAchievementRepository.saveAndFlush(editUserAchievement);
    }

}
