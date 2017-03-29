package com.royalrangers.model;

import com.royalrangers.enums.AuthorityName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Authority extends BaseModel {

    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<User> users;

}