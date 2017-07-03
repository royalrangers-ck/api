package com.royalrangers.repository.achievement;

import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.User;
import com.royalrangers.model.achievement.UserQuarterAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuarterAchievementRepository extends JpaRepository<UserQuarterAchievement, Long>{
    List<UserQuarterAchievement> findByUserId(Long id);
    List<UserQuarterAchievement> findAllByQuarterAchievement(Long QuarterAchievementId);
    List<UserQuarterAchievement> findByUserPlatoonIdAndAchievementState(Long id, AchievementState state);
    List<UserQuarterAchievement> findByUserAgeGroup(UserAgeGroup userAgeGroup);
    UserQuarterAchievement findByQuarterAchievementId(Long quarterId);
    UserQuarterAchievement findByUserIdAndQuarterAchievementId(Long userId, Long quarterId);
}
