package com.royalrangers.configuration.bootstrap;

import com.royalrangers.enums.AuthorityName;
import com.royalrangers.enums.achivement.AgeCategory;
import com.royalrangers.model.*;
import com.royalrangers.model.achievement.*;
import com.royalrangers.repository.AuthorityRepository;
import com.royalrangers.repository.CountryRepository;
import com.royalrangers.repository.UserRepository;
import com.royalrangers.repository.achievement.*;
import com.royalrangers.service.achievement.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
public class Bootstrap {
    private final String DDL_AUTO_CREATE = "create";
    private final String DDL_AUTO_CREATE_DROP = "create-drop";
    private final String UKRAINE_CITIES = "src/main/resources/init/ukraine.cities";

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private TwelveYearAchievementService twelveYearAchievementService;

    @Autowired
    private ThreeYearAchievementRepository threeYearAchievementRepository;

    @Autowired
    private YearAchievementRepository yearAchievementRepository;

    @Autowired
    private QuarterAchievementRepository quarterAchievementRepository;

    @Autowired
    private QuarterAchievementService quarterAchievementService;

    private AchievementBootstrap achievementBootstrap = new AchievementBootstrap();

    private TestBootstrap testBootstrap = new TestBootstrap();

    @Autowired
    private TestService testService;

    @Autowired
    private TestRepository testRepository;

    private TaskBootstrap taskBootstrap = new TaskBootstrap();

    @Autowired
    private RewardService rewardService;

    private RewardBootstrap rewardBootstrap = new RewardBootstrap();

    @Autowired
    private TaskRepository taskRepository;

    @PostConstruct
    public void init() {
        if (DDL_AUTO_CREATE.equals(ddlAuto) || DDL_AUTO_CREATE_DROP.equals(ddlAuto)) {
            try {
                initCountry("Україна", UKRAINE_CITIES);
            } catch (IOException e) {
                log.error("Error in loading file " + e.getMessage(), e);
            }
            initTwelveYear();
            initReward();
            initAuthorities();
            initUsers();
        }
    }

    private void initUsers() {
        List<User> users = new ArrayList<>();
        IntStream.range(1, 4).forEach(element -> {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User();
            user.setEmail("email" + element + "@mail.test");
            user.setPassword(encoder.encode("password" + element));
            user.setFirstName("first " + element);
            user.setLastName("last " + element);
            user.setGender("gender " + element);
            user.setEnabled(true);
            user.setConfirmed(true);
            user.setApproved(true);
            user.setCountry(new Country(countryRepository.findOne(1L).getName()));
            user.setCity(new City(user.getCountry(), "Cherkasy" + element));
            user.setGroup(new Group(user.getCity(), "group " + element));
            user.setPlatoon(new Platoon(user.getGroup(), "platoon " + element));
            user.setSection(new Section(user.getPlatoon(), "section " + element));
            user.setLastPasswordResetDate(new Date(new GregorianCalendar(2016,
                    Calendar.FEBRUARY, 9).getTimeInMillis()));
            Authority userAuthority = authorityRepository.findOne(1L);
            Authority adminAuthority = authorityRepository.findOne(2L);
            Authority superAdminAuthority = authorityRepository.findOne(3L);
            switch (element) {
                case 1:
                    user.setAuthorities(new HashSet<Authority>() {{
                        add(userAuthority);
                    }});
                    break;
                case 2:
                    user.setAuthorities(new HashSet<Authority>() {{
                        add(userAuthority);
                        add(adminAuthority);
                    }});
                    break;
                case 3:
                    user.setAuthorities(new HashSet<Authority>() {{
                        add(userAuthority);
                        add(adminAuthority);
                        add(superAdminAuthority);
                    }});
                    break;
            }
            users.add(user);
        });
        userRepository.save(users);
    }

    private void initAuthorities() {
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthorityName.ROLE_USER);
        authorityRepository.save(userAuthority);

        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthorityName.ROLE_ADMIN);
        authorityRepository.save(adminAuthority);

        Authority superAdminAuthority = new Authority();
        superAdminAuthority.setName(AuthorityName.ROLE_SUPER_ADMIN);
        authorityRepository.save(superAdminAuthority);
        authorityRepository.save(superAdminAuthority);
    }

    private void initCountry(String countryName, String path) throws IOException {
        Country country = new Country(countryName);
        Set<City> citySet = new HashSet<>();
        Files.lines(Paths.get(path), StandardCharsets.UTF_8)
                .forEach(element -> citySet.add(new City(country, element)));
        country.setCity(citySet);
        countryRepository.save(country);
    }

    private void initReward() {
        Stream.of(rewardBootstrap.createReward().toArray()).forEach(element -> {
            rewardService.addReward((Reward) element);
        });
    }

    private void initTwelveYear() {
        twelveYearAchievementService.addTwelveYearAchievement(achievementBootstrap.createTwelveYear());
        initThreeYear();
    }

    private void initThreeYear() {
        Stream.of(twelveYearAchievementService.getAllTwelveYearAchievement().toArray()).forEach(twelveYearId -> {
            TwelveYearAchievement twelveYearAchievement = (TwelveYearAchievement) twelveYearId;
            Stream.of(achievementBootstrap.createThreeYear().toArray()).forEach(element -> {
                ThreeYearAchievement threeYearAchievement = (ThreeYearAchievement) element;
                threeYearAchievement.setTwelveYearAchievement(twelveYearAchievement);
                threeYearAchievementRepository.saveAndFlush(threeYearAchievement);
            });
        });
        initYear();
    }

    private void initYear() {
        Stream.of(threeYearAchievementRepository.findAll().toArray()).forEach(threeYearAchievements -> {
            ThreeYearAchievement threeYearAchievement = (ThreeYearAchievement) threeYearAchievements;
            AgeCategory ageCategory = threeYearAchievement.getAgeCategory();
            Map<String, Object> map = achievementBootstrap.createYear();
            List<YearAchievement> yearList = null;
            if (map.containsKey("for_" + ageCategory.toString().toLowerCase())) {
                yearList = (List<YearAchievement>) map.get("for_" + ageCategory.toString().toLowerCase());
                Stream.of(yearList.toArray()).forEach(yearAchievement -> {
                    YearAchievement editYearAchievement = (YearAchievement) yearAchievement;
                    editYearAchievement.setThreeYearAchievement(threeYearAchievementRepository.findOne(threeYearAchievement.getId()));
                    yearAchievementRepository.saveAndFlush(editYearAchievement);
                });
            } else {
                yearList = (List<YearAchievement>) map.get("for_beginners");
                Stream.of(yearList.toArray()).forEach(yearAchievement -> {
                    YearAchievement editYearAchievement = (YearAchievement) yearAchievement;
                    editYearAchievement.setThreeYearAchievement(threeYearAchievementRepository.findOne(threeYearAchievement.getId()));
                    yearAchievementRepository.saveAndFlush(editYearAchievement);
                });
            }
        });
        initQuarter();
    }

    private void initQuarter() {
        Stream.of(yearAchievementRepository.findAll().toArray()).forEach(year -> {
            YearAchievement yearAchievement = (YearAchievement) year;
            Map<String, Object> map = achievementBootstrap.createQuarter();
            List<QuarterAchievement> quarterAchievementList;
            if (map.containsKey("forYear" + yearAchievement.getId())) {
                quarterAchievementList = (List<QuarterAchievement>) map.get("forYear" + yearAchievement.getId());
                Stream.of(quarterAchievementList.toArray()).forEach(quarter -> {
                    QuarterAchievement quarterAchievement = (QuarterAchievement) quarter;
                    quarterAchievement.setYearAchievement(yearAchievement);
                    quarterAchievementRepository.saveAndFlush(quarterAchievement);
                });
            } else {
                quarterAchievementList = (List<QuarterAchievement>) map.get("forYear1");
                Stream.of(quarterAchievementList.toArray()).forEach(quarter -> {
                    QuarterAchievement quarterAchievement = (QuarterAchievement) quarter;
                    quarterAchievement.setYearAchievement(yearAchievement);
                    quarterAchievementRepository.saveAndFlush(quarterAchievement);
                });
            }
        });
        initTest();
    }

    public void initTest() {
        IntStream.range(1, quarterAchievementService.getAllQuarterAchievement().size()).forEach(quarterId -> {
            QuarterAchievement quarterAchievement = quarterAchievementService.getQuarterAchievementById((long) quarterId);
            Map<String, Object> mapTest = testBootstrap.createTest();
            List<Test> tests = null;
            if (mapTest.containsKey("testForQuarter" + quarterId)) {
                tests = (List<Test>) mapTest.get("testForQuarter" + quarterId);
                Stream.of(tests.toArray()).forEach(testElement -> {
                    Test test = (Test) testElement;
                    test.setQuarterAchievement(quarterAchievement);
                    testRepository.saveAndFlush(test);
                });
            } else {
                tests = (List<Test>) mapTest.get("testForQuarter1");
                Stream.of(tests.toArray()).forEach(testElement -> {
                    Test test = (Test) testElement;
                    test.setQuarterAchievement(quarterAchievement);
                    testRepository.saveAndFlush(test);
                });
            }
        });
        initTask();
    }

    public void initTask() {
        IntStream.range(1, testService.getAllTest().size()).forEach(testId -> {
            Test test = testService.getTestById((long) testId);
            Map<String, Object> map = taskBootstrap.createTask();
            List<Task> list = null;
            if (map.containsKey("listForTest" + testId)) {
                list = (List<Task>) map.get("listForTest" + testId);
                Stream.of(list.toArray()).forEach(item -> {
                    Task task = (Task) item;
                    task.setTest(test);
                    taskRepository.saveAndFlush(task);
                });
            } else {
                list = (List<Task>) map.get("listForTest1");
                Stream.of(list.toArray()).forEach(item -> {
                    Task task = (Task) item;
                    task.setTest(test);
                    taskRepository.saveAndFlush(task);
                });
            }
        });
    }

}
