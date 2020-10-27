package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/11/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GouldDTO {


    @ExcelColumn(value = "id",col = 1)
    private String id;
    @ExcelColumn(value = "name",col = 2)
    private String name;
    @ExcelColumn(value = "address",col = 3)
    private String address;
    @ExcelColumn(value = "pname",col = 5)
    private String province;
    @ExcelColumn(value = "cityname",col = 7)
    private String city;
    @ExcelColumn(value = "adname",col = 9)
    private String area;
    @ExcelColumn(value = "type",col = 10)
    private String classification;
    @ExcelColumn(value = "tel",col = 12)
    private String telephone;
/*    @ExcelColumn(value = "平均消费",col = 13)
    private Double averageConsumption;
    @ExcelColumn(value = "评分",col = 14)
    private Double score;*/
    @ExcelColumn(value = "wgslat",col = 15)
    private String wgs84lat;
    @ExcelColumn(value = "wgslon",col = 16)
    private String wgs84lon;
    @ExcelColumn(value = "gaodelat",col = 17)
    private String gouldLat;
    @ExcelColumn(value = "gaodelon",col = 18)
    private String gouldLon;
    @ExcelColumn(value = "bdlat",col = 19)
    private String baiduLat;
    @ExcelColumn(value = "bdlon",col = 20)
    private String baiduLon;
    private String count;
}
