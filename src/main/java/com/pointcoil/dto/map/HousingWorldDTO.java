package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/15.
 */
@Data
public class HousingWorldDTO {
        @ExcelColumn(value = "名称",col = 1)
        private String name;
    @ExcelColumn(value = "小区地址",col = 2)
        private String address;
    @ExcelColumn(value = "所属区域",col = 3)
        private String region;
    @ExcelColumn(value = "产权描述",col = 4)
        private String propertyRight;
    @ExcelColumn(value = "物业类别",col = 5)
        private String property;
    @ExcelColumn(value = "建筑年代",col = 6)
        private String architecturalAge;
    @ExcelColumn(value = "建筑类型",col = 7)
        private String architecturalType;
    @ExcelColumn(value = "建筑结构",col = 8)
        private String architecturalStructure;
    @ExcelColumn(value = "建筑面积",col = 9)
        private String architecturalArea;
    @ExcelColumn(value = "占地面积",col = 10)
        private String areaCovered;
    @ExcelColumn(value = "开发商",col = 11)
        private String developers;
    @ExcelColumn(value = "房屋总数",col = 12)
        private String houseNum;
    @ExcelColumn(value = "楼栋总数",col = 13)
        private String tungNum;
    @ExcelColumn(value = "绿化率",col = 14)
        private String greenRate;
    @ExcelColumn(value = "容积率",col = 15)
        private String volumeRate;
    @ExcelColumn(value = "物业费",col = 16)
        private String propertyFee;
    @ExcelColumn(value = "商场",col = 17)
        private String market;
    @ExcelColumn(value = "医院",col = 18)
        private String hospital;
    @ExcelColumn(value = "邮局",col = 19)
        private String postOffice;
    @ExcelColumn(value = "银行",col = 20)
        private String bank;
    @ExcelColumn(value = "小区内部配套",col = 21)
        private String InternalMatching;
    @ExcelColumn(value = "baidu_lon",col = 22)
        private String baiduLon;
    @ExcelColumn(value = "baidu_lat",col = 23)
        private String baiduLat;
    @ExcelColumn(value = "wgs_lon",col = 24)
        private String wgsLon;
    @ExcelColumn(value = "wgs_lat",col = 25)
        private String wgsLat;
    @ExcelColumn(value = "链接",col = 26)
        private String url;

}
