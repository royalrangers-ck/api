package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class UserYearAchievement extends UserAchievement {

    @JsonView(Views.Achievement.class)
    @OneToOne
    private YearAchievement yearAchievement;

    @JsonView(Views.Achievement.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userYearAchievement")
    private List<UserTest> additionalTests;

}
