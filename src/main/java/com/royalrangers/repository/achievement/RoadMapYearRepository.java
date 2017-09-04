package com.royalrangers.repository.achievement;

import com.royalrangers.model.achievement.RoadMapYearAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadMapYearRepository extends JpaRepository<RoadMapYearAchievement, Long> {
    List<RoadMapYearAchievement> findBySectionId(Long sectionId);
    RoadMapYearAchievement findBySectionIdAndYearAchievementId(Long sectionId, Long yearId);
}
