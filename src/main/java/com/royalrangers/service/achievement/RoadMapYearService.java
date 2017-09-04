package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.RoadMapYearRequestDto;
import com.royalrangers.model.achievement.RoadMapYearAchievement;
import com.royalrangers.model.achievement.Test;
import com.royalrangers.model.achievement.YearAchievement;
import com.royalrangers.repository.achievement.RoadMapYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RoadMapYearService {

    @Autowired
    private RoadMapYearRepository roadMapYearRepository;

    @Autowired
    private YearAchievementService yearAchievementService;

    @Autowired
    private TestService testService;

    public List<RoadMapYearAchievement> getAllMapForSpecialSection(Long sectionId){
        return roadMapYearRepository.findBySectionId(sectionId);
    }

    public RoadMapYearAchievement saveRoadMapYear(RoadMapYearRequestDto roadMapYearRequestDto) {
        RoadMapYearAchievement roadMapYear = roadMapYearRepository.findBySectionIdAndYearAchievementId(roadMapYearRequestDto.getSectionId(), roadMapYearRequestDto.getYearId());
        YearAchievement yearAchievement = yearAchievementService.getYearAchievementById(roadMapYearRequestDto.getYearId());
        ArrayList<Test> tests = new ArrayList<>();
        roadMapYearRequestDto.getTestIds().forEach(id -> {
            tests.add(testService.getTestById(id));
        });
        if (roadMapYear == null) {
            RoadMapYearAchievement roadMapYearAchievement = new RoadMapYearAchievement();
            roadMapYearAchievement.setSectionId(roadMapYearRequestDto.getSectionId());
            roadMapYearAchievement.setYearAchievement(yearAchievement);
            roadMapYearAchievement.setTests(tests);
            roadMapYearRepository.save(roadMapYearAchievement);
        } else {
            editRoadMapYear(roadMapYearRequestDto);
        }

        return roadMapYearRepository.findBySectionIdAndYearAchievementId(roadMapYearRequestDto.getSectionId(), roadMapYearRequestDto.getYearId());
    }

    private RoadMapYearAchievement editRoadMapYear(RoadMapYearRequestDto roadMapYearRequestDto) {
        RoadMapYearAchievement roadMapYear = roadMapYearRepository.findBySectionIdAndYearAchievementId(roadMapYearRequestDto.getSectionId(), roadMapYearRequestDto.getYearId());
        ArrayList<Test> tests = new ArrayList<>();
        roadMapYearRequestDto.getTestIds().forEach(id -> {
            tests.add(testService.getTestById(id));
        });
        roadMapYear.setTests(tests);
        return roadMapYearRepository.saveAndFlush(roadMapYear);
    }
}
