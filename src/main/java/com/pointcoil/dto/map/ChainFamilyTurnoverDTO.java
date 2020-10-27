package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/16.
 */
@Data
public class ChainFamilyTurnoverDTO {

    @ExcelColumn(value = "单价",col = 1)
    private String unit_price;
    @ExcelColumn(value = "成交价格",col = 1)
    private String transaction_price;
    @ExcelColumn(value = "面积",col = 1)
    private String area;
    @ExcelColumn(value = "客厅数量",col = 1)
    private String living_rooms_num;
    @ExcelColumn(value = "朝向",col = 1)
    private String orientation;
    @ExcelColumn(value = "小区名称",col = 1)
    private String community_name;
    @ExcelColumn(value = "楼层",col = 1)
    private String floor;
    @ExcelColumn(value = "标题",col = 1)
    private String title;
    @ExcelColumn(value = "房源链接",col = 1)
    private String housing_link;
    @ExcelColumn(value = "小区编号",col = 1)
    private String cell_id;
    @ExcelColumn(value = "价格",col = 1)
    private String price;
    @ExcelColumn(value = "房源编号",col = 1)
    private String housing_num;
    @ExcelColumn(value = "卧室数量",col = 1)
    private String bedrooms_num;
    @ExcelColumn(value = "城市编号",col = 1)
    private String city_num;
    @ExcelColumn(value = "权属",col = 1)
    private String ownership;
    @ExcelColumn(value = "用途",col = 1)
    private String purpose;
    @ExcelColumn(value = "电梯",col = 1)
    private String elevator;
    @ExcelColumn(value = "年代",col = 1)
    private String years;
    @ExcelColumn(value = "成交日期",col = 1)
    private String closing_date;
    @ExcelColumn(value = "楼型",col = 1)
    private String building_type;
    @ExcelColumn(value = "装修",col = 1)
    private String renovation;
    @ExcelColumn(value = "经度",col = 1)
    private String lon;
    @ExcelColumn(value = "地区",col = 1)
    private String region;
    @ExcelColumn(value = "纬度",col = 1)
    private String lat;
    @ExcelColumn(value = "位置描述",col = 1)
    private String location_description;
    @ExcelColumn(value = "浏览次数",col = 1)
    private String browse_num;
    @ExcelColumn(value = "调价次数",col = 1)
    private String price_adjustment_times;
    @ExcelColumn(value = "挂牌价格",col = 1)
    private String listed_price;
    @ExcelColumn(value = "成交周期(天)",col = 1)
    private String transaction_period_days;
    @ExcelColumn(value = "带看次数",col = 1)
    private String visits_num;
    @ExcelColumn(value = "关注次数",col = 1)
    private String frequency_num;
    @ExcelColumn(value = "户型格局",col = 1)
    private String apartment_pattern;

}
