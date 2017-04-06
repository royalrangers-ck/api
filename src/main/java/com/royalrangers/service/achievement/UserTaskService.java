package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.UserAchievementBean;
import com.royalrangers.dto.achievement.UserAchievementRequestDTO;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.achievement.UserTask;
import com.royalrangers.dto.achievement.UserTaskBean;
import com.royalrangers.repository.achievement.UserTaskRepository;
import com.royalrangers.service.UserService;
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
    private UserService userService;

    @Autowired
    private TaskService taskService;

    public List<UserTaskBean> getAllForUser() {
        List<UserTask> userTasks = userTaskRepository.findByUserId(userService.getAuthenticatedUserId());
        List<UserTaskBean> result = new ArrayList<>();
        for (UserTask item : userTasks) {
            result.add(buildUserAchievementBean(item));
        }
        return result;
    }

    public void addUserTask(UserAchievementRequestDTO params) {
        UserTask userTask = new UserTask();
        userTask.setCreateDate(new Date());
        userTask.setUpdateDate(new Date());
        String achievementState = params.getState();
        userTask.setAchievementState(AchievementState.valueOf(achievementState));
        userTask.setUser(userService.getUserById(userService.getAuthenticatedUserId()));
        Integer taskId = params.getId();
        userTask.setTask(taskService.getTaskById(taskId.longValue()));
        userTaskRepository.saveAndFlush(userTask);
    }

    public UserTaskBean getUserTaskById(Long id) {
        UserTask user = userTaskRepository.findOne(id);
        return buildUserAchievementBean(user);
    }

    public void deleteUserTask(Long id) {
        userTaskRepository.delete(id);
    }

    public void editUserTask(UserAchievementRequestDTO params, Long id) {
        UserTask savedUserTask = userTaskRepository.findOne(id);
        savedUserTask.setUpdateDate(new Date());
        String achievementState = params.getState();
        savedUserTask.setAchievementState(AchievementState.valueOf(achievementState));
        Integer taskId = params.getId();
        savedUserTask.setTask(taskService.getTaskById(taskId.longValue()));
        userTaskRepository.saveAndFlush(savedUserTask);
    }

    private UserTaskBean buildUserAchievementBean(UserTask item) {
        UserTaskBean userAchievementBean = new UserTaskBean();
        userAchievementBean.setId(item.getId());
        userAchievementBean.setCreateDate(item.getCreateDate());
        userAchievementBean.setUpdateDate(item.getUpdateDate());
        userAchievementBean.setAchievementState(item.getAchievementState());
        UserAchievementBean userBean = UserService.buildUserAchievementBean(item.getUser());
        userAchievementBean.setUser(userBean);
        userAchievementBean.setTask(item.getTask());
        return userAchievementBean;
    }

}
