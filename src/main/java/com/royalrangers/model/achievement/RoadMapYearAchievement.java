package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.model.BaseModel;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class RoadMapYearAchievement extends BaseModel {

    @JsonView(Views.Achievement.class)
    private Long sectionId;

    @JsonView(Views.Achievement.class)
    @OneToOne
    private YearAchievement yearAchievement;

    @JsonView(Views.Achievement.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "RoadMapYear_Tests",
            joinColumns = {@JoinColumn(name = "RoadMapYearAchievement_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "test_id", referencedColumnName = "id")})
    private List<Test> tests;

    @JsonView(Views.Achievement.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "RoadMapYear_AdditionalTests",
            joinColumns = {@JoinColumn(name = "RoadMapYearAchievement_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "test_id", referencedColumnName = "id")})
    private List<Test> additionalTests;

}
