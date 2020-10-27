package com.pointcoil.dto.map;

import lombok.Data;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/4.
 */
@Data
public class MapIndustryDTO {
    private String industryId;
    private String industryName;
    private List<IndustryLevelTwo> industryLevelTwo;

    @Data
    public static class IndustryLevelTwo{
        private String industryLevelTwoId;
        private String industryLevelTwoName;
    }


}
