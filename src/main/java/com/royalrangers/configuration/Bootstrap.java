package com.royalrangers.configuration;

import com.royalrangers.enums.AuthorityName;
import com.royalrangers.model.*;
import com.royalrangers.repository.AuthorityRepository;
import com.royalrangers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.IntStream;

@Component
public class Bootstrap {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        initAuthorities();
        initUsers();
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
            user.setCountry(new Country(new Date(), new Date(),"Ukraine" + element));
            user.setCity(new City(user.getCountry(), new Date(), new Date(),"Cherkasy" + element));
            user.setGroup(new Group(user.getCity(), new Date(), new Date(), "group " + element));
            user.setPlatoon(new Platoon(user.getGroup(),new Date(), new Date(), "platoon " + element));
            user.setSection(new Section(user.getPlatoon(),new Date(), new Date(), "section " + element));
            user.setLastPasswordResetDate(new Date(new GregorianCalendar(2016,
                    Calendar.FEBRUARY, 9).getTimeInMillis()));
            Authority userAuthority = authorityRepository.findOne(1L);
            Authority adminAuthority = authorityRepository.findOne(2L);
            Authority superAdminAuthority = authorityRepository.findOne(3L);
            switch (element) {
                case 1:
                    user.setAuthorities(new HashSet<Authority>() {{ add(userAuthority); }});
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

}
