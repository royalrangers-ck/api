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
public class Platoon extends BaseModel{

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "platoon", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Section> sections;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Platoon() {}

    public Platoon(Group group, Date createDate, Date updateDate, String name) {
        super(createDate,updateDate);
        this.group = group;
        this.name = name;
    }

}
