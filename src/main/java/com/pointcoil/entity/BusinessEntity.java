package com.pointcoil.entity;

import lombok.Data;

/**
 * Created by WuShuang on 2019/12/5.
 */
@Data
public class BusinessEntity {
    private String businessId;
    private String businessType;
    private String businessName;
    private String businessDescribe;
    private String businessLevel;
    private String businessOpen;
    private String businessUnsold;
    private String screenshotImg;
    private String thermodynamicChart;
    private String city;
    private String businessArea;
    private String createTime;
}
