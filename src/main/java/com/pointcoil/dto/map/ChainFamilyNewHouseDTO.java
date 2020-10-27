package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/16.
 */
@Data
public class ChainFamilyNewHouseDTO {

    @ExcelColumn(value = "id",col = 1)
    private String id;
    @ExcelColumn(value = "城市名称",col = 2)
    private String city_name;
    @ExcelColumn(value = "缩略图",col = 3)
    private String thumbnail;
    @ExcelColumn(value = "最小面积",col = 4)
    private String minimum_area;
    @ExcelColumn(value = "最大面积",col = 5)
    private String maximum_area;
    @ExcelColumn(value = "区域",col = 6)
    private String region;
    @ExcelColumn(value = "街道",col = 7)
    private String street;
    @ExcelColumn(value = "装修",col = 8)
    private String renovation;
    @ExcelColumn(value = "户型情况",col = 9)
    private String apartment_situation;
    @ExcelColumn(value = "名称",col = 10)
    private String name;
    @ExcelColumn(value = "地址",col = 11)
    private String address;
    @ExcelColumn(value = "平均价格",col = 12)
    private String average_price;
    @ExcelColumn(value = "项目简称",col = 13)
    private String project_abbreviation;
    @ExcelColumn(value = "标签",col = 14)
    private String label;
    @ExcelColumn(value = "类型",col = 15)
    private String type;
    @ExcelColumn(value = "销售状态",col = 16)
    private String sales_status;
    @ExcelColumn(value = "开盘时间",col = 17)
    private String opening_time;
    @ExcelColumn(value = "最低总价",col = 18)
    private String lowest_total_price;
    @ExcelColumn(value = "项目描述",col = 19)
    private String project_description;
    @ExcelColumn(value = "纬度",col = 20)
    private String lat;
    @ExcelColumn(value = "经度",col = 21)
    private String lon;
}
