package com.royalrangers.dto.achievement;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RoadMapQuarterRequestDto {
    private Long sectionId;
    private Long quarterId;
    private List<Long> testIds;
    private List<Long> additionalTestIds;
}
