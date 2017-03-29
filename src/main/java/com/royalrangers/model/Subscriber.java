package com.royalrangers.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class Subscriber extends BaseModel {

    @NotNull
    private String email;

    public Subscriber(){}

    public Subscriber(Date createDate, Date updateDate, String email) {
        super(createDate,updateDate);
        this.email = email;
    }
}