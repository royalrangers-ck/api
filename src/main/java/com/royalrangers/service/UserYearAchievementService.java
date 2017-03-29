package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.bean.UserBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.UserYearAchievement;
import com.royalrangers.model.achievement.UserYearAchievementBean;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.UserYearAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserYearAchievementService {

    @Autowired
    private UserYearAchievementRepository userYearAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private YearAchievementService yearAchievementService;

    public List<UserYearAchievementBean> getAllUserYearAchievements() {
        List<UserYearAchievement> list = userYearAchievementRepository.findAll();
        List<UserYearAchievementBean> result = new ArrayList<>();
        for (UserYearAchievement item : list) {
            UserYearAchievementBean userAchievementBean = new UserYearAchievementBean();
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

            userAchievementBean.setYearAchievement(item.getYearAchievement());
            result.add(userAchievementBean);
        }
        return result;
    }

    public void addUserYearAchievement(Map<String, Object> params) {
        UserYearAchievement savedUserAchievement = new UserYearAchievement();
        savedUserAchievement.setCreateDate(new Date());
        savedUserAchievement.setUpdateDate(new Date());
        Integer achievementStatus = (Integer) params.get("status");
        savedUserAchievement.setAchievementStatus(AchievementStatus.values()[achievementStatus]);
        Integer userId = (Integer) params.get("userId");
        Integer userReviewerId = (Integer) params.get("reviewerId");
        if (!userRepository.exists(userId.longValue()) || !userRepository.exists(userReviewerId.longValue())) {
            throw new UserRepositoryException("Not found user with id");
        }
        Integer yearId = (Integer) params.get("yearAchievementId");
        savedUserAchievement.setUser(userService.getUserById(userId.longValue()));
        savedUserAchievement.setReviewer(userService.getUserById(userReviewerId.longValue()));
        savedUserAchievement.setYearAchievement(yearAchievementService.getYearAchievementById(yearId.longValue()));
        userYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

    public UserYearAchievementBean getUserYearAchievementById(Long id) {
        UserYearAchievement user = userYearAchievementRepository.findOne(id);
        UserYearAchievementBean userYearAchievementBean = new UserYearAchievementBean();
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

        userYearAchievementBean.setYearAchievement(user.getYearAchievement());
        return userYearAchievementBean;
    }

    public void deleteUserYearAchievement(Long id) {
        userYearAchievementRepository.delete(id);
    }

    public void editUserYearAchievement(Map<String, Object> params, Long id) {
        UserYearAchievement savedUserAchievement = userYearAchievementRepository.findOne(id);
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
        Integer yearId = (Integer) params.get("yearAchievementId");
        savedUserAchievement.setYearAchievement(yearAchievementService.getYearAchievementById(yearId.longValue()));
        userYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

}
