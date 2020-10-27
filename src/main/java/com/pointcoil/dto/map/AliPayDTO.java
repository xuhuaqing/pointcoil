package com.pointcoil.dto.map;

import lombok.Data;

/**
 * @author:WuShuang
 * @date:2019/8/13
 * @ver:1.0
 **/
@Data
public class AliPayDTO extends MapCommonDTO{
    private String body;
    private String subject;
    private String [] cityName;
    private String totalAmount;
    private String outTradeNo;
    /**
     * 0是月  1是年
     */
    private Integer yearOrMonth;
    private Integer time;


}
