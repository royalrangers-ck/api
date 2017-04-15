package com.royalrangers.repository.achievement;

import com.royalrangers.model.achievement.UserYearAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserYearAchievementRepository extends JpaRepository<UserYearAchievement, Long>{
    List<UserYearAchievement> findByUserId(Long id);
    List<UserYearAchievement> findAllByYearAchievement(Long yearAchievementId);
    List<UserYearAchievement> findByUser_PlatoonId(Long id);
}
