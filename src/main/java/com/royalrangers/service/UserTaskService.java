package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.UserTask;
import com.royalrangers.model.achievement.UserTaskBean;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    public List<UserTaskBean> getAllUserTaskAchievement() {
        List<UserTask> list = userTaskRepository.findAll();
        List<UserTaskBean> result = new ArrayList<>();
        for (UserTask item : list) {
            UserTaskBean userAchievementBean = new UserTaskBean();
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

            userAchievementBean.setTask(item.getTask());
            result.add(userAchievementBean);
        }
        return result;
    }

    public void addUserTaskAchievement(Map<String, Object> params) {
        UserTask savedUserAchievement = new UserTask();
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
        Integer taskId = (Integer) params.get("taskId");
        savedUserAchievement.setTask(taskService.getTaskById(taskId.longValue()));
        userTaskRepository.saveAndFlush(savedUserAchievement);
    }

    public UserTaskBean getUserTaskAchievementById(Long id) {
        UserTask user = userTaskRepository.findOne(id);
        UserTaskBean userYearAchievementBean = new UserTaskBean();
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

        userYearAchievementBean.setTask(user.getTask());
        return userYearAchievementBean;
    }

    public void deleteUserTaskAchievement(Long id) {
        userTaskRepository.delete(id);
    }

    public void editUserTaskAchievement(Map<String, Object> params, Long id) {
        UserTask savedUserAchievement = userTaskRepository.findOne(id);
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
        Integer taskId = (Integer) params.get("taskId");
        savedUserAchievement.setTask(taskService.getTaskById(taskId.longValue()));
        userTaskRepository.saveAndFlush(savedUserAchievement);
    }

}
