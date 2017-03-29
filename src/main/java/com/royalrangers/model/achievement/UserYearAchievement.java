package com.royalrangers.model.achievement;

import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.model.BaseModel;
import com.royalrangers.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserYearAchievement extends BaseModel {

    @Enumerated
    private AchievementStatus achievementStatus;

    @OneToOne
    private User user;

    @OneToOne
    private User reviewer;

    @OneToOne
    private YearAchievement yearAchievement;
}
