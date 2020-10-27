package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/11/27.
 */
@Data
public class ThermodynamicDTO {

    @ExcelColumn(value = "id")
    private String id;              //
    @ExcelColumn(value = "店名")
    private String shopName;        //
    @ExcelColumn(value = "价格")
    private String price;
    @ExcelColumn(value = "星级")
    private String starClass;       //
    @ExcelColumn(value = "评分")
    private String score;
    @ExcelColumn(value = "电话")
    private String telephone;
    @ExcelColumn(value = "评论数")
    private String commentNumber;
    @ExcelColumn(value = "一级分类")
    private String firstClassClassification;       //
    @ExcelColumn(value = "二级分类")
    private String twoLevelClassification;         //
    @ExcelColumn(value = "三级分类")
    private String threeLevelClassification;       //
    @ExcelColumn(value = "团购")
    private String groupBuying;
    @ExcelColumn(value = "区县")
    private String districtAndCounty;
    @ExcelColumn(value = "推荐菜")
    private String recommendedDishes;
    @ExcelColumn(value = "时间")
    private String time;             //
    @ExcelColumn(value = "区域")
    private String region;


    public ThermodynamicDTO(String id, String shopName, String starClass,String firstClassClassification, String twoLevelClassification, String threeLevelClassification, String time, String wgs_lng, String wgs_lat) {
        this.id = id;
        this.shopName = shopName;
        this.starClass = starClass;
        this.firstClassClassification = firstClassClassification;
        this.twoLevelClassification = twoLevelClassification;
        this.threeLevelClassification = threeLevelClassification;
        this.time = time;
        this.wgs_lng = wgs_lng;
        this.wgs_lat = wgs_lat;
    }

    public ThermodynamicDTO(){}

    @ExcelColumn(value = "地址")
    private String address;
    @ExcelColumn(value = "路径")
    private String route;
    @ExcelColumn(value = "bd_lon")
    private String wgs_lng;             //
    @ExcelColumn(value = "bd_lat")
    private String wgs_lat;             //

    private Double wgsLng;
    private Double wgsLat;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ThermodynamicDTO1{
        private String id;
        private String shopName;
        private String firstClassClassification;
        private String twoLevelClassification;
        private String districtAndCounty;
        private Double wgsLng;
        private Double wgsLat;
    }
}
