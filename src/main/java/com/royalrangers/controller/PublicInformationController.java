package com.royalrangers.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.royalrangers.dto.ResponseResult;
import com.royalrangers.enums.UserRank;
import com.royalrangers.exception.TokenException;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.Region;
import com.royalrangers.model.Platoon;
import com.royalrangers.model.Section;
import com.royalrangers.model.Views;
import com.royalrangers.repository.*;
import com.royalrangers.service.UserService;
import com.royalrangers.utils.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicInformationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PlatoonRepository platoonRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @GetMapping("/ranks")
    @ApiOperation(value = "Get a list of ranks")
    public ResponseResult getUserRankList() {
        List<Enum> rankList = Arrays.asList(UserRank.values());
        return ResponseBuilder.success(rankList);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/countries")
    @ApiOperation(value = "Get a list of countries")
    public ResponseResult getAllCountries() {
        return ResponseBuilder.success(countryRepository.findAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/regions")
    @ApiOperation(value = "Get a list of regions")
    public ResponseResult getAllRegions() {
        return ResponseBuilder.success(regionRepository.findAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/region")
    @ApiOperation(value = "Get a list of regions for given country")
    public ResponseResult getCitiesByCountry(@RequestParam Long countryId) {
        List<Region> cities = regionRepository.findByCountryId(countryId);
        return ResponseBuilder.success(cities);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/cities")
    @ApiOperation(value = "Get a list of cities")
    public ResponseResult getAllCities() {
        return ResponseBuilder.success(cityRepository.findAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/city")
    @ApiOperation(value = "Get a list of cities for given region")
    public ResponseResult getCitiesByRegion(@RequestParam Long regionId) {
        return ResponseBuilder.success(cityRepository.findByRegionId(regionId));
    }

    @JsonView(Views.Public.class)
    @GetMapping("/platoons")
    @ApiOperation(value = "Get a list of platoons")
    public ResponseResult getAllPlatoons() {
        return ResponseBuilder.success(platoonRepository.findAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/platoon")
    @ApiOperation(value = "Get a list of platoons for given city")
    public ResponseResult getPlatoonsByCity(@RequestParam Long cityId) {
        List<Platoon> platoons = platoonRepository.findByCityId(cityId);
        return ResponseBuilder.success(platoons);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/sections")
    @ApiOperation(value = "Get a list of sections")
    public ResponseResult getAllSections() {
        return ResponseBuilder.success(sectionRepository.findAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/section")
    @ApiOperation(value = "Get a list of sections for given platoon")
    public ResponseResult getSectionsByPlatoon(@RequestParam Long platoonId) {
        List<Section> sections = sectionRepository.findByPlatoonId(platoonId);
        return ResponseBuilder.success(sections);
    }

    @PostMapping("/forgotPassword")
    @ApiOperation(value = "Send email with resetPasswordLink for user")
    public ResponseResult sendResetPasswordLink(@RequestParam("email") String email) {
        try {
            userService.sendResetPasswordEmail(email);
        } catch (UserRepositoryException | UnknownHostException e) {
            return ResponseBuilder.fail(e.getMessage());
        }
        return ResponseBuilder.success("Send reset password email to user: " + email);
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "Change user password by given token")
    public ResponseResult registrationConfirm(@RequestParam("token") String token, @RequestBody String newPassword) {
        try {
            userService.changeUserPassword(token, newPassword);
        } catch (TokenException e) {
            log.info(e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
        return ResponseBuilder.success("User password was changed successfully");
    }

}
