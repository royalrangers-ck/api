package com.royalrangers.bean;

import com.royalrangers.enums.Status;
import com.royalrangers.enums.UserAgeGroup;
import com.royalrangers.enums.UserRank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserBean {

    private Long id;
    private Date createDate;
    private Date updateDate;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String telephoneNumber;
    private Long birthDate;
    private Status status;
    private UserAgeGroup userAgeGroup;
    private UserRank userRank;
    private Boolean enabled;
    private Boolean confirmed;
    private Boolean approved;
    private Long countryId;
    private Long cityId;
    private Long groupId;
    private Long platoonId;
    private Long sectionId;
}

