package com.royalrangers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"group\"")
public class Group extends BaseModel {

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Platoon> platoons;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public Group(){}

    public Group(City city, Date createDate, Date updateDate, String name) {
        super(createDate,updateDate);
        this.city = city;
        this.name = name;
    }

}
