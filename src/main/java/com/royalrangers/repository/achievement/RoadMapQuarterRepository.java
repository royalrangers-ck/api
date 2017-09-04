package com.royalrangers.repository.achievement;

import com.royalrangers.model.achievement.RoadMapQuarterAchievement;
import com.royalrangers.model.achievement.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadMapQuarterRepository extends JpaRepository<RoadMapQuarterAchievement, Long> {
    List<RoadMapQuarterAchievement> findBySectionId(Long sectionId);
    RoadMapQuarterAchievement findBySectionIdAndQuarterAchievementId(Long sectionId, Long quarterId);
    RoadMapQuarterAchievement findBySectionIdAndTestsContains(Long sectionId, Test test);
}
