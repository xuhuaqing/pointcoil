package com.pointcoil.dto.map;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:WuShuang
 * @date:2020/4/25
 * @ver:1.0
 **/
@Data
public class CityDTO {
    private String province;
    private BigDecimal price;
    private List<String> cityName;
}
