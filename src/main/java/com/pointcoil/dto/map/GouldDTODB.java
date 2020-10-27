package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/22.
 */
@Data
public class GouldDTODB {
    @ExcelColumn(value = "name",col = 2)
    private String name;
    @ExcelColumn(value = "cityname",col = 7)
    private String city;
    @ExcelColumn(value = "type",col = 10)
    private String classification;
    @ExcelColumn(value = "bdlat",col = 19)
    private Double baiduLat;
    @ExcelColumn(value = "bdlon",col = 20)
    private Double baiduLon;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Gould{
        private List<String> residentialQuartersName;
        private List<String> officeBuilding;
        private List<BusinessZoneDTO.OtherDTO> otherName;

        private Integer residentialQuartersNum;
        private Integer officeBuildingNum;
        private Integer otherNum;
    }
}
