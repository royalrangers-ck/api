package com.royalrangers.controller.achievement;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.dto.achievement.RoadMapQuarterRequestDto;
import com.royalrangers.dto.achievement.RoadMapYearRequestDto;
import com.royalrangers.model.Views;
import com.royalrangers.service.achievement.RoadMapQuarterService;
import com.royalrangers.service.achievement.RoadMapYearService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/achievements/roadMap")
public class RoadMapController {

    @Autowired
    private RoadMapQuarterService roadMapQuarterService;

    @Autowired
    private RoadMapYearService roadMapYearService;

    @PostMapping("/quarter")
    @ApiOperation(value = "Add new QuarterAchievementRoadMap for special section")
    public ResponseResult addRoadMapQuarter(@RequestBody RoadMapQuarterRequestDto roadMapQuarterRequestDto){
        try {
            return ResponseBuilder.success(roadMapQuarterService.saveRoadMapQuarter(roadMapQuarterRequestDto));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail(ex.getMessage());
        }
    }

    @PostMapping("/year")
    @ApiOperation(value = "Add new YearAchievementRoadMap for special section")
    public ResponseResult addRoadMapYear(@RequestBody RoadMapYearRequestDto roadMapYearRequestDto){
        try {
            return ResponseBuilder.success(roadMapYearService.saveRoadMapYear(roadMapYearRequestDto));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail(ex.getMessage());
        }
    }

    @JsonView(Views.Achievement.class)
    @GetMapping("/year/{sectionId}")
    @ApiOperation(value = "Get all YearAchievementRoadMap for special section")
    public ResponseResult getRoadMapYear(@PathVariable Long sectionId){
        try {
            return ResponseBuilder.success(roadMapYearService.getAllMapForSpecialSection(sectionId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail(ex.getMessage());
        }
    }

    @JsonView(Views.Achievement.class)
    @GetMapping("/quarter/{sectionId}")
    @ApiOperation(value = "Get all QuarterAchievementRoadMap for special section")
    public ResponseResult getRoadMapQuarter(@PathVariable Long sectionId){
        try {
            return ResponseBuilder.success(roadMapQuarterService.getAllMapForSpecialSection(sectionId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseBuilder.fail(ex.getMessage());
        }
    }

}
