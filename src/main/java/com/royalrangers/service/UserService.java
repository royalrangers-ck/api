package com.royalrangers.service;

import com.royalrangers.bean.UserAchievementBean;
import com.royalrangers.bean.UserBean;
import com.royalrangers.enums.AuthorityName;
import com.royalrangers.enums.Status;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.exception.UserRepositoryException;
import com.royalrangers.model.Authority;
import com.royalrangers.model.User;
import com.royalrangers.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@PropertySource("classpath:emailproperties.yml")
public class UserService {

    @Value("${confirmRegistrationUrl}")
    private String confirmRegistrationUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PlatoonRepository platoonRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    public void grantAuthority(User user, AuthorityName... roles) {
        Set<Authority> authoritySet = new HashSet<>();
        for (AuthorityName role : roles) {
            Authority authority = authorityRepository.findByName(role);
            authoritySet.add(authority);
        }
        user.setAuthorities(authoritySet);
    }

    public User createUserFromUserForm(UserBean userBean) {
        User user = new User();
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setEmail(userBean.getEmail());
        user.setFirstName(userBean.getFirstName());
        user.setLastName(userBean.getLastName());
        user.setPassword(passwordEncoder.encode(userBean.getPassword()));
        user.setEmail(userBean.getEmail());
        user.setEnabled(false);
        user.setConfirmed(false);
        user.setApproved(false);
        user.setUserAgeGroup(determineUserAgeGroup(calculateUserAge(userBean.getBirthDate())));
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis()));
        user.setGender(userBean.getGender());
        user.setTelephoneNumber(userBean.getTelephoneNumber());
        user.setBirthDate(userBean.getBirthDate());
        user.setCountry(countryRepository.findOne(userBean.getCountryId()));
        user.setCity(cityRepository.findOne(userBean.getCityId()));
        user.setGroup(groupRepository.findOne(userBean.getGroupId()));
        user.setPlatoon(platoonRepository.findOne(userBean.getPlatoonId()));
        user.setSection(sectionRepository.findOne(userBean.getSectionId()));
        if (Objects.equals(userBean.getStatus(), Status.TEACHER)) {
            grantAuthority(user, AuthorityName.ROLE_USER, AuthorityName.ROLE_ADMIN);
        } else {
            grantAuthority(user, AuthorityName.ROLE_USER);
        }
        return user;
    }

    public static UserBean buildUserBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setCreateDate(user.getCreateDate());
        userBean.setUpdateDate(user.getUpdateDate());
        userBean.setId(user.getId());
        userBean.setEmail(user.getEmail());
        userBean.setPassword(user.getPassword());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setGender(user.getGender());
        userBean.setEnabled(user.getEnabled());
        userBean.setConfirmed(user.getConfirmed());
        userBean.setApproved(user.getApproved());
        userBean.setBirthDate(user.getBirthDate());
        userBean.setTelephoneNumber(user.getTelephoneNumber());
        userBean.setConfirmed(user.getConfirmed());
        userBean.setEnabled(user.getEnabled());
        userBean.setApproved(user.getApproved());
        userBean.setUserAgeGroup(user.getUserAgeGroup());
        userBean.setCountryId(user.getCountry().getId());
        userBean.setCityId(user.getCity().getId());
        userBean.setGroupId(user.getGroup().getId());
        userBean.setPlatoonId(user.getPlatoon().getId());
        userBean.setSectionId(user.getSection().getId());
        return userBean;
    }

    public static UserAchievementBean buildUserAchievementBean(User user){
        UserAchievementBean userBean = new UserAchievementBean();
        userBean.setId(user.getId());
        userBean.setEmail(user.getEmail());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        return  userBean;
    }

    public Boolean isEmailExist(String email) {
        return (userRepository.findByEmail(email) != null);
    }

    public String getConfirmRegistrationLink(User user) {
        String token = verificationTokenService.generateToken(user);
        return confirmRegistrationUrl + "/api/registration/confirm?token=" + token;
    }

    public int calculateUserAge(Long birthdate) {
        Calendar cal = Calendar.getInstance();
        double userAge = (cal.getTime().getTime() - birthdate) * 3.170979E-11;
        return (int) userAge;
    }

    public UserAgeGroup determineUserAgeGroup(int userAge) {
        UserAgeGroup rank = UserAgeGroup.BEGINNER;
        if (userAge >= 5 && userAge <= 7)
            return UserAgeGroup.BEGINNER;
        if (userAge >= 8 && userAge <= 10)
            return UserAgeGroup.PIONEER;
        if (userAge >= 11 && userAge <= 13)
            return UserAgeGroup.PATHFINDER;
        if (userAge >= 14 && userAge <= 16)
            return UserAgeGroup.RANGER;
        if (userAge >= 17)
            return UserAgeGroup.ADULT;
        return rank;
    }

    public List<User> getUsersToApproveByPlatoonID(Long platoonId) {
        return userRepository.findAllByConfirmedTrueAndApprovedFalseAndPlatoonId(platoonId);
    }

    public List<UserBean> getUsersForApprove(Long platoonId) {
        List<User> listUsersToApprove = getUsersToApproveByPlatoonID(platoonId);
        List<UserBean> listUsersBeanToApprove = new ArrayList<>();
        for (User user : listUsersToApprove) {
            UserBean userBean = buildUserBean(user);
            listUsersBeanToApprove.add(userBean);
        }
        return listUsersBeanToApprove;
    }

    public User getUserById(Long id){
        return userRepository.findOne(id);
    }

    public void approveUsers(List<Long> ids) {
        ids.forEach(id -> {
            User user = userRepository.findOne(id);
            user.setApproved(true);
            user.setEnabled(true);
            userRepository.save(user);
        });
    }

    public void rejectUsers(List<Long> ids) {
        ids.forEach(id -> {
            User user = userRepository.findOne(id);
            user.setEnabled(false);
            userRepository.save(user);
        });
    }
    public boolean isAuthenticatedUserHasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }

    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void updateUserByEmail(String email, UserBean update) {

        User user = userRepository.findByEmail(email);

        if(!isAuthenticatedUserHasRole("ROLE_ADMIN")) {
            updateUserItself(user, update);
        } else {
            updateUserByAdmin(user, update);
        }
    }

    public void updateUserById(Long id, UserBean update) {
        if(!userRepository.exists(id)) {
            throw new UserRepositoryException("Not found user with id " + id);
        }
        User user = userRepository.findOne(id);
        updateUserByAdmin(user, update);
    }

    public void updateUserByAdmin(User user, UserBean update) {

        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setGender(update.getGender());
        user.setTelephoneNumber(update.getTelephoneNumber());
        user.setBirthDate(update.getBirthDate());
        user.setUserAgeGroup(update.getUserAgeGroup());
        user.setUserRank(update.getUserRank());
        user.setCountry(countryRepository.findOne(update.getCountryId()));
        user.setCity(cityRepository.findOne(update.getCityId()));
        user.setGroup(groupRepository.findOne(update.getGroupId()));
        user.setPlatoon(platoonRepository.findOne(update.getPlatoonId()));
        user.setSection(sectionRepository.findOne(update.getSectionId()));

        userRepository.save(user);
    }

    private void updateUserItself(User user, UserBean update) {
        user.setTelephoneNumber(update.getTelephoneNumber());
        user.setUserAgeGroup(update.getUserAgeGroup());
        user.setUserRank(update.getUserRank());
        user.setCountry(countryRepository.findOne(update.getCountryId()));
        user.setCity(cityRepository.findOne(update.getCityId()));
        user.setGroup(groupRepository.findOne(update.getGroupId()));
        user.setPlatoon(platoonRepository.findOne(update.getPlatoonId()));
        user.setSection(sectionRepository.findOne(update.getSectionId()));
    }
}

