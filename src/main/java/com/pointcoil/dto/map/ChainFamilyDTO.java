package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/15.
 */
@Data
public class ChainFamilyDTO {

    @ExcelColumn(value = "链接",col = 1)
    private String link;
    @ExcelColumn(value = "小区名称",col = 2)
    private String name;
    @ExcelColumn(value = "id",col = 3)
    private String id;
    @ExcelColumn(value = "分区",col = 4)
    private String partition;
    @ExcelColumn(value = "区域",col = 5)
    private String region;
    @ExcelColumn(value = "区划",col = 6)
    private String geology;
    @ExcelColumn(value = "建成年代",col = 7)
    private String ageCompletion;
    @ExcelColumn(value = "产权",col = 8)
    private String propertyRight;
    @ExcelColumn(value = "建筑类型",col = 9)
    private String architecturalType;
    @ExcelColumn(value = "开发商",col = 10)
    private String developers;
    @ExcelColumn(value = "产权类型",col = 11)
    private String propertyRights;
    @ExcelColumn(value = "用水",col = 12)
    private String water;
    @ExcelColumn(value = "用电",col = 13)
    private String electric;
    @ExcelColumn(value = "固定车位数",col = 14)
    private String fixedCarsNum;
    @ExcelColumn(value = "停车费用",col = 15)
    private String parkingCost;
    @ExcelColumn(value = "燃气费用",col = 16)
    private String gasCost;
    @ExcelColumn(value = "绿化率",col = 17)
    private String greed;
    @ExcelColumn(value = "容积率",col = 18)
    private String plotRatio;
    @ExcelColumn(value = "物业公司",col = 19)
    private String propertyCompany;
    @ExcelColumn(value = "物业价格",col = 20)
    private String propertyPrice;
    @ExcelColumn(value = "物业电话",col = 21)
    private String propertyTelephone;
    @ExcelColumn(value = "纬度",col = 22)
    private String lat;
    @ExcelColumn(value = "经度",col = 23)
    private String lon;
    @ExcelColumn(value = "户数",col = 25)
    private String households;
}
