package com.royalrangers.model.achievement;

import com.royalrangers.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class Achievement extends BaseModel {

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String logoUrl;

    @NotNull
    private String requirements;

}
