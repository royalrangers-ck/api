package com.royalrangers.dto.achievement;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RoadMapYearRequestDto {
    private Long sectionId;
    private Long yearId;
    private List<Long> testIds;
}
