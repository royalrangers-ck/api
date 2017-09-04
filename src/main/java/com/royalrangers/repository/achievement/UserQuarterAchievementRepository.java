package com.royalrangers.repository.achievement;

import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.achievement.Test;
import com.royalrangers.model.achievement.UserQuarterAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserQuarterAchievementRepository extends JpaRepository<UserQuarterAchievement, Long>{
    List<UserQuarterAchievement> findByUserId(Long id);
    List<UserQuarterAchievement> findAllByQuarterAchievement(Long QuarterAchievementId);
    List<UserQuarterAchievement> findByUserPlatoonIdAndAchievementState(Long id, AchievementState state);

    public List<UserQuarterAchievement> findByUserAgeGroup(UserAgeGroup userAgeGroup);
    UserQuarterAchievement findByUserIdAndQuarterAchievementId(Long userId, Long quarterId);

    UserQuarterAchievement findByUserIdAndUserTests_Contains(Long userId, Test test);
}
