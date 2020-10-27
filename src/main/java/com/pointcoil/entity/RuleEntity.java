package com.pointcoil.entity;

import lombok.Data;

/**
 * Created by WuShuang on 2019/12/20.
 */
@Data
public class RuleEntity {
    private String id;
    private String firstClassClassification;
    private String twoLevelClassification;
    private String threeLevelClassification;
    private String minCount;
    private String cityName;
    private String type;
}
