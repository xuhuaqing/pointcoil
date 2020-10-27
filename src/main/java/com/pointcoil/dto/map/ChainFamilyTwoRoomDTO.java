package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/15.
 */
@Data
public class ChainFamilyTwoRoomDTO {

    @ExcelColumn(value = "房型",col = 1)
    private String houseType;
    @ExcelColumn(value = "售价",col = 2)
    private String price;
    @ExcelColumn(value = "建筑面积",col = 3)
    private String builtUpArea;
    @ExcelColumn(value = "面积",col = 4)
    private String area;
    @ExcelColumn(value = "是否下架",col = 5)
    private Boolean lowerShelf;
    @ExcelColumn(value = "客厅数量",col = 6)
    private Integer livingRoomsNum;
    @ExcelColumn(value = "朝向",col = 7)
    private String orientation;
    @ExcelColumn(value = "小区名称",col = 8)
    private String communityName;
    @ExcelColumn(value = "楼层",col = 9)
    private String floor;
    @ExcelColumn(value = "标题",col = 10)
    private String title;
    @ExcelColumn(value = "单价",col = 11)
    private Integer unitPrice;
    @ExcelColumn(value = "是否必看好房",col = 12)
    private Boolean goodsHouse;
    @ExcelColumn(value = "房源链接",col = 13)
    private String housingLink;
    @ExcelColumn(value = "小区编号",col = 14)
    private String cellId;
    @ExcelColumn(value = "价格",col = 15)
    private Integer priceNum;
    @ExcelColumn(value = "房源编号",col = 16)
    private String housiongNum;
    @ExcelColumn(value = "卧室数量",col = 17)
    private Integer bedroomsNum;
    @ExcelColumn(value = "城市编号",col = 18)
    private Integer city_code;
    @ExcelColumn(value = "权属",col = 19)
    private String ownership;
    @ExcelColumn(value = "挂牌",col = 20)
    private String listing;
    @ExcelColumn(value = "用途",col = 21)
    private String purpose;
    @ExcelColumn(value = "电梯",col = 22)
    private String elevator;
    @ExcelColumn(value = "年代",col = 23)
    private String years;
    @ExcelColumn(value = "楼型",col = 24)
    private String buildingType;
    @ExcelColumn(value = "装修",col = 25)
    private String renovation;
    @ExcelColumn(value = "经度",col = 26)
    private String lon;
    @ExcelColumn(value = "地区",col = 27)
    private String region;
    @ExcelColumn(value = "纬度",col = 28)
    private String lat;
    @ExcelColumn(value = "位置描述",col = 29)
    private String locationDescription;
    @ExcelColumn(value = "近7日带看",col = 30)
    private Integer daysLook7;
    @ExcelColumn(value = "近30日带看",col = 31)
    private Integer daysLook30;
    @ExcelColumn(value = "关注房源",col = 32)
    private String followHours;
    @ExcelColumn(value = "主图",col = 33)
    private String masterGraph;
    @ExcelColumn(value = "缩略图",col = 34)
    private String thumbnail;

}
