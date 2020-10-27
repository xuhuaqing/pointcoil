package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/20.
 */
@Data
public class CityExcelDTO {

    @ExcelColumn(value = "省份",col = 1)
    private String province;
    @ExcelColumn(value = "城市",col = 2)
    private String city;
    @ExcelColumn(value = "城市等级",col = 3)
    private String cityLevel;
    @ExcelColumn(value = "常住人口（万）",col = 4)
    private Double residentPopulationPro;
    @ExcelColumn(value = "GDP（亿）",col = 5)
    private Double GDP;
    @ExcelColumn(value = "三产比例",col = 6)
    private String threeProduction;
    @ExcelColumn(value = "社会消费品零售总额（亿）",col = 7)
    private Double retailTotal;
    @ExcelColumn(value = "城镇居民人均可支配收入",col = 8)
    private String income;
    @ExcelColumn(value = "人均消费支出",col = 9)
    private String expenditure;


}
