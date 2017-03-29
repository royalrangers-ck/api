package com.royalrangers.model.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.royalrangers.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Task extends BaseModel {

    private String name;

    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = true)
    private Test test;

}