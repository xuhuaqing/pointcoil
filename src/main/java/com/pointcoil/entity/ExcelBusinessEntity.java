package com.pointcoil.entity;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/12/12.
 */
@Data
public class ExcelBusinessEntity {

    @ExcelColumn(value = "商圈出售",col = 1)
    private String businessUnsold;
    @ExcelColumn(value = "商圈公开",col = 2)
    private String businessOpen;
    @ExcelColumn(value = "商圈等级",col = 3)
    private String businessLevel;
    @ExcelColumn(value = "商圈名称",col = 4)
    private String businessName;
    @ExcelColumn(value = "商圈描述",col = 5)private String businessDescribe;
    @ExcelColumn(value = "商圈类型",col = 6)private String businessType;
    @ExcelColumn(value = "半径",col = 7)private String radius;
    @ExcelColumn(value = "经纬度",col = 8)private String coordinates;
    @ExcelColumn(value = "商圈截图",col = 9)private String screenshotImg;
    @ExcelColumn(value = "热力图截图",col = 10)private String thermodynamicChart;
    @ExcelColumn(value = "所属城市",col = 11)private String city;
    @ExcelColumn(value = "商圈面积",col = 12)private String businessArea;
    @ExcelColumn(value = "商圈所属区",col = 13)private String district;
    @ExcelColumn(value = "商圈所属省",col = 14)private String province;
    @ExcelColumn(value = "ID",col = 15)private String customizedId = "";
}
