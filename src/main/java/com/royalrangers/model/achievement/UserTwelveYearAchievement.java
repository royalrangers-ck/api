package com.royalrangers.model.achievement;

import com.royalrangers.enums.achivement.AchievementStatus;
import com.royalrangers.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserTwelveYearAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long createDate;

    @Column
    private Long updateDate;

    @Enumerated
    private AchievementStatus achievementStatus;

    @OneToOne
    private User user;

    @OneToOne
    private User reviewer;

    @OneToOne
    private TwelveYearAchievement twelveYearAchievement;
}
