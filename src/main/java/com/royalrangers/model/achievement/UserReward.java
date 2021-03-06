package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.model.User;
import com.royalrangers.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class UserReward extends UserAchievement {

    @JsonIgnore
    @OneToOne
    private User user;

    @JsonView(Views.Achievement.class)
    @OneToOne
    private Reward reward;

}
