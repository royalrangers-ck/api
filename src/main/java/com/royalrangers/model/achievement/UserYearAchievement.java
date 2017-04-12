package com.royalrangers.model.achievement;

import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class UserYearAchievement extends UserAchievement {

    @OneToOne
    private YearAchievement yearAchievement;

}
