package com.royalrangers.bean;

import com.royalrangers.enums.Status;
import com.royalrangers.enums.UserAgeGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private Long birthDate;
    private Status status;
    private UserAgeGroup userAgeGroup;
    private Boolean enabled;
    private Boolean confirmed;
    private Boolean approved;
    private Long countryId;
    private Long cityId;
    private Long groupId;
    private Long platoonId;
    private Long sectionId;
    private String countryName;
    private String cityName;
    private String groupName;
    private String platoonName;
    private String sectionName;

}

