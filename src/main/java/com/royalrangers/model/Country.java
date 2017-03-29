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
public class Country extends BaseModel {

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<City> city;

    public Country() {}

    public Country(Date createDate, Date updateDate, String name) {
        super (createDate,updateDate);
        this.name = name;
    }
}