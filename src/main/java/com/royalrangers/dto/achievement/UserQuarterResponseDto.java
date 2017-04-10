package com.royalrangers.dto.achievement;

import com.royalrangers.enums.achivement.AchievementState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserQuarterResponseDto {
    private Long id;
    private Date createDate;
    private Date updateDate;
    private AchievementState achievementState;
    private UserAchievementDto user;
    private String quarterAchievementName;
    private String quarterAchievementDescription;
    private String quarterAchievementLogoUrl;
}
