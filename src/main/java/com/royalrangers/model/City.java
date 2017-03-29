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
public class City extends BaseModel {

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Group> groups;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    public City() {}

    public City(Country country, Date createDate, Date updateDate, String name) {
        super (createDate,updateDate);
        this.country = country;
        this.name = name;
    }

}
