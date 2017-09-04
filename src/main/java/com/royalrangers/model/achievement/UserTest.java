package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserTest extends UserAchievement {

    @JsonView(Views.Achievement.class)
    @OneToOne
    private Test test;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userQuarterAchievement_id", nullable = true)
    private UserQuarterAchievement userQuarterAchievement;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userYearAchievement_id", nullable = true)
    private UserYearAchievement userYearAchievement;

}
