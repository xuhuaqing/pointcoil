package com.pointcoil.dto.map;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:WuShuang
 * @date:2020/4/27
 * @ver:1.0
 **/
@Data
public class WxpayVo implements Serializable {
    private String userId;
    private String tradeNo;
    private String [] cityName;
    private String time;
    private double totalMoney;
}
