package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by WuShuang on 2019/11/25.
 */
@Data
public class IndustryDTO {

    private String id;

    @ExcelColumn(value = "一级分类",col = 2)
    private String oneIndustry;
    @ExcelColumn(value = "二级分类",col = 3)
    private String twoIndustry;
    @ExcelColumn(value = "wgs_lng",col = 6)
    private Double wgsLng;
    @ExcelColumn(value = "wgs_lat",col = 7)
    private Double wgsLat;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IndustryList{
        private String name;
        private double value;

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IndustryBrandList{
        @ExcelColumn(value = "一级分类",col = 1)
        private String FirstClassification;
        @ExcelColumn(value = "二级分类",col = 4)
        private String TwoClassification;
        @ExcelColumn(value = "点线圈一级分类",col = 2)
        private String dianXianQuanFirstClass;
        @ExcelColumn(value = "点线圈二级分类",col = 3)
        private String dianXianQuanTwoClass;
    }

}
