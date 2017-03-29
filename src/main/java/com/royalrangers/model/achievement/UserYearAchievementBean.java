package com.royalrangers.model.achievement;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.bean.UserBean;
import com.royalrangers.enums.achivement.AchievementStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserYearAchievementBean{
        private Long id;
        private Long createDate;
        private Long updateDate;
        private AchievementStatus achievementStatus;
        private UserAchievementBean user;
        private UserAchievementBean reviewer;
        private YearAchievement yearAchievement;
}