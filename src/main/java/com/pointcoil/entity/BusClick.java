package com.pointcoil.entity;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/12/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusClick {

    @ExcelColumn(value = "count", col = 1)
    private String count;

    @ExcelColumn(value = "lat", col = 2)
    private String lat;

    @ExcelColumn(value = "lng", col = 3)
    private String lng;

    @ExcelColumn(value = "name", col = 4)
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExportExcel{
        private String businessUnsold;
        private String businessOpen;
        private String businessLevel;
        private String businessName;
        private String businessDescribe;
        private String businessType;
        private String radius;
        private String coordinates;
        private String screenshotImg;
        private String thermodynamicChart;
        private String city;
        private String businessArea;
        private String district;
        private String province;
        private String customizedId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExportExcelByUser{
        private String userName;
        private String userPhone;
        private String userEmail;
        private String enterpriseName;
        private String makeName;
        private String businessLicense;
        private String createTime;
    }

}
