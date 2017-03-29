package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.achievement.UserThreeYearAchievement;
import com.royalrangers.model.achievement.UserThreeYearAchievementBean;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.UserThreeYearAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserThreeYearAchievementService {

    @Autowired
    private UserThreeYearAchievementRepository userThreeYearAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreeYearAchievementService threeYearAchievementService;

    public List<UserThreeYearAchievementBean> getAllUserThreeYearAchievements(){
        List<UserThreeYearAchievement> list = userThreeYearAchievementRepository.findAll();
        List<UserThreeYearAchievementBean> result = new ArrayList<>();
        for (UserThreeYearAchievement item : list) {
            UserThreeYearAchievementBean userAchievementBean = new UserThreeYearAchievementBean();
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

            userAchievementBean.setThreeYearAchievement(item.getThreeYearAchievement());
            result.add(userAchievementBean);
        }
        return result;
    }

    public void addUserThreeYearAchievement(Map<String, Object> params){
        UserThreeYearAchievement savedUserAchievement = new UserThreeYearAchievement();
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
        Integer threeYearId = (Integer) params.get("threeYearAchievementId");
        savedUserAchievement.setThreeYearAchievement(threeYearAchievementService.getThreeYearAchievementById(threeYearId.longValue()));
        userThreeYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

    public UserThreeYearAchievementBean getUserThreeYearAchievementById(Long id){
        UserThreeYearAchievement user = userThreeYearAchievementRepository.findOne(id);
        UserThreeYearAchievementBean userYearAchievementBean = new UserThreeYearAchievementBean();
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

        userYearAchievementBean.setThreeYearAchievement(user.getThreeYearAchievement());
        return userYearAchievementBean;
    }

    public void deleteUserThreeYearAchievement(Long id){
        userThreeYearAchievementRepository.delete(id);
    }

    public void editUserThreeYearAchievement(Map<String, Object> params, Long id){
        UserThreeYearAchievement savedUserAchievement = userThreeYearAchievementRepository.findOne(id);
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
        Integer threeYearId = (Integer) params.get("threeYearAchievementId");
        savedUserAchievement.setThreeYearAchievement(threeYearAchievementService.getThreeYearAchievementById(threeYearId.longValue()));
        userThreeYearAchievementRepository.saveAndFlush(savedUserAchievement);
    }

}
