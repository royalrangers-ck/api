package com.royalrangers.repository.achievement;

import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.achivement.AchievementState;
import com.royalrangers.model.achievement.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long>{
    List<UserTest> findByUserId(Long id);
    List<UserTest> findByUserPlatoonIdAndAchievementState(Long id, AchievementState state);
    List<UserTest> findAllByTest(Long testId);

    UserTest findByUserIdAndTestId(Long userId, Long testId);

    List<UserTest> findByAchievementStateAndTest_UserAgeGroupsContains(AchievementState achievementStates, List<UserAgeGroup> list);
}
