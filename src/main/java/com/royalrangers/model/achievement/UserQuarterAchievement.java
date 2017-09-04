package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class UserQuarterAchievement extends UserAchievement {

    @JsonView(Views.Achievement.class)
    private UserAgeGroup userAgeGroup;

    @JsonView(Views.Achievement.class)
    @OneToOne
    private QuarterAchievement quarterAchievement;

    @JsonView(Views.Achievement.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userQuarterAchievement")
    private List<UserTest> userTests;

    @JsonView(Views.Achievement.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userQuarterAchievement")
    private List<UserTest> additionalUserTests;

}
