package com.pointcoil.dto.map;

import lombok.Data;

/**
 * Created by WuShuang on 2020/3/23.
 */
@Data
public class OrderDTO {
    private String tradeNo;
    private String userName;
    private String userPhone;
    private String enterpriseName;
    private String makeName;
    private String memberLevel;
    private String year;
    private String createTime;
    private Double totalMoney;
}
