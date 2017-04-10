package com.royalrangers.service.achievement;

import com.royalrangers.dto.achievement.AchievementRequestDto;
import com.royalrangers.repository.achievement.TwelveYearAchievementRepository;
import com.royalrangers.model.achievement.TwelveYearAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TwelveYearAchievementService {

    @Autowired
    private TwelveYearAchievementRepository twelveYearAchievementRepository;

    public List<TwelveYearAchievement> getAllTwelveYearAchievement() {
        return twelveYearAchievementRepository.findAll();
    }

    public void addTwelveYearAchievement(AchievementRequestDto params) {
        TwelveYearAchievement twelveYearAchievement = new TwelveYearAchievement();
        twelveYearAchievement.setName(params.getName());
        twelveYearAchievement.setDescription(params.getDescription());
        twelveYearAchievement.setLogoUrl(params.getLogoUrl());
        twelveYearAchievement.setRequirements(params.getRequirements());
        twelveYearAchievementRepository.saveAndFlush(twelveYearAchievement);
    }

    public TwelveYearAchievement getTwelveYearAchievementById(Long id) {
        return twelveYearAchievementRepository.findOne(id);
    }

    public void deleteTwelveYearAchievement(Long id) {
        twelveYearAchievementRepository.delete(id);
    }

    public TwelveYearAchievement editTwelveYearAchievement(AchievementRequestDto params, Long twelveYearId) {
        TwelveYearAchievement twelveYearAchievement = getTwelveYearAchievementById(twelveYearId);
        twelveYearAchievement.setName(params.getName());
        twelveYearAchievement.setDescription(params.getDescription());
        twelveYearAchievement.setLogoUrl(params.getLogoUrl());
        twelveYearAchievement.setRequirements(params.getRequirements());
        return twelveYearAchievementRepository.saveAndFlush(twelveYearAchievement);
    }

}