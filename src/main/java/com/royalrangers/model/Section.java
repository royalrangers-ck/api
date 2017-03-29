package com.royalrangers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class Section extends BaseModel{

    @NotNull
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "platoon_id")
    private Platoon platoon;

    public Section() {}

    public Section(Platoon platoon, Date createDate, Date updateDate, String name) {
        super(createDate,updateDate);
        this.platoon = platoon;
        this.name = name;
    }

}
