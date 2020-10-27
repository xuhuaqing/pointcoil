package com.pointcoil.dto.map;

import lombok.Data;

/**
 * Created by WuShuang on 2019/12/3.
 */
@Data
public class DistributionMapDTO {

    private String name;

    private Integer value;

    @Data
    public static  class DistributionMapCity{
        private String shortName;
        private Double longitude;
        private Double latitude;
    }

}
