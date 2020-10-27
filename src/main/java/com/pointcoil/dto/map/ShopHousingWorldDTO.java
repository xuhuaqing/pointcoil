package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/15.
 */
@Data
public class ShopHousingWorldDTO {
    @ExcelColumn(value = "名字",col = 1)
    private String name;
    @ExcelColumn(value = "链接",col = 2)
    private String url;
    @ExcelColumn(value = "类型",col = 3)
    private String type;
    @ExcelColumn(value = "地址",col = 4)
    private String address;
    @ExcelColumn(value = "区域",col = 5)
    private String region;
    @ExcelColumn(value = "商圈",col = 6)
    private String businessCircle;
    @ExcelColumn(value = "简称年代",col = 7)
    private String years;
    @ExcelColumn(value = "单价",col = 8)
    private String unitPrice;
    @ExcelColumn(value = "物业类别",col = 9)
    private String propertyType;
    @ExcelColumn(value = "总 层 数",col = 10)
    private String layerNumber;
    @ExcelColumn(value = "物 业 费",col = 11)
    private String propertyFee;
    @ExcelColumn(value = "建筑面积",col = 12)
    private String builtUpArea;
    @ExcelColumn(value = "总户数",col = 13)
    private String households;
    @ExcelColumn(value = "竣工时间",col = 14)
    private String completionTime;
    @ExcelColumn(value = "电梯数量",col = 15)
    private String elevatorNum;
    @ExcelColumn(value = "停车位",col = 16)
    private String parkingSpace;
    @ExcelColumn(value = "物业公司",col = 17)
    private String propertyCompany;
    @ExcelColumn(value = "环线位置",col = 18)
    private String loopLine;
    @ExcelColumn(value = "是否可分割",col = 19)
    private String division;
    @ExcelColumn(value = "空　　调",col = 20)
    private String airConditioner;
    @ExcelColumn(value = "装修状况",col = 21)
    private String decorationStatus;
    @ExcelColumn(value = "占地面积",col = 22)
    private String areaCovered;
    @ExcelColumn(value = "开间面积",col = 23)
    private String bayArea;
    @ExcelColumn(value = "开 发 商",col = 24)
    private String developers;
    @ExcelColumn(value = "公交",col = 25)
    private String bus;
    @ExcelColumn(value = "地铁",col = 26)
    private String metro;
    @ExcelColumn(value = "位置",col = 27)
    private String position;
}
