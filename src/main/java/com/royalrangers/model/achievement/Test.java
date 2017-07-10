package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.TestType;
import com.royalrangers.model.BaseModel;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class Test extends BaseModel {

    @JsonView(Views.Achievement.class)
    private String name;

    @JsonView(Views.Achievement.class)
    private String shortDescription;

    @JsonView(Views.Achievement.class)
    @Column(length = 1200)
    private String description;

    @JsonView(Views.Achievement.class)
    private String logoUrl;

    @JsonView(Views.Achievement.class)
    @Enumerated
    private TestType testType;

    @JsonView(Views.Achievement.class)
    @ElementCollection(targetClass = UserAgeGroup.class)
    @Column(name = "age_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<UserAgeGroup> userAgeGroups;

    @JsonView(Views.AchievementUser.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    private List<Task> taskList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarterAchievement_id", nullable = true)
    private QuarterAchievement quarterAchievement;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yearAchievement_id")
    private YearAchievement year;

}