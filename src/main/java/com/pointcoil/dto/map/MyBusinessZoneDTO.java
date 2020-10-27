package com.pointcoil.dto.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessZoneDTO {


    private List<MyBusinessZone> myBusinessZone;
    private List<MyBusinessZoneCity> myBusinessZoneCities;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyBusinessZone{
        private String businessId;
        private String businessName;
        private String businessOpen;
        private String businessUnsold;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyBusinessZoneCity{
        private String cityName;
        private Integer businessCount;
    }


}
