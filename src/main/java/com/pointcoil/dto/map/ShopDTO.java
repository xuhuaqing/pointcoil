package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/14.
 */
@Data
public class ShopDTO {
    @ExcelColumn(value = "店铺ID",col = 1)
    private String   shopId;
    @ExcelColumn(value = "名称",col = 2)
    private String   shopName;
    @ExcelColumn(value = "地址",col = 3)
    private String   address;
    @ExcelColumn(value = "店铺链接",col = 4)
    private String   shopLink;
    @ExcelColumn(value = "评分",col = 5)
    private Double   shopScore;
    @ExcelColumn(value = "环境评分",col = 6)
    private Double   environmentScore;
    @ExcelColumn(value = "服务评分",col = 7)
    private Double   serviceScore;
    @ExcelColumn(value = "质量评分",col = 8)
    private Double   qualityScore;
    @ExcelColumn(value = "平均消费",col = 9)
    private Double   consume;
    @ExcelColumn(value = "点评数量",col = 10)
    private Integer   commentNum;
    @ExcelColumn(value = "分店标识",col = 11)
    private String   branch;
    @ExcelColumn(value = "国家",col = 12)
    private String   country;
    @ExcelColumn(value = "省",col = 13)
    private String   province;
    @ExcelColumn(value = "城市",col = 14)
    private String   city;
    @ExcelColumn(value = "分区",col = 15)
    private String   area;
    @ExcelColumn(value = "商圈",col = 16)
    private String   business;
    @ExcelColumn(value = "是否支持外卖",col = 17)
    private Boolean   takeOut;
    @ExcelColumn(value = "电话",col = 18)
    private String   telephone;
    @ExcelColumn(value = "一级分类名称",col = 19)
    private String   oneSortName;
    @ExcelColumn(value = "二级分类名称",col = 20)
    private String   twoSortName;
    @ExcelColumn(value = "分类路径",col = 21)
    private String   totalSortName;
    @ExcelColumn(value = "是否验证",col = 22)
    private Boolean   verification;
    @ExcelColumn(value = "支持预定",col = 23)
    private Boolean   payReserve;
    @ExcelColumn(value = "是否连锁店",col = 24)
    private Boolean   chinaStore;
    @ExcelColumn(value = "团购，外卖",col = 25)
    private String   groupBuying;
    @ExcelColumn(value = "最后点评时间",col = 26)
    private String   lastCommentTime;
    @ExcelColumn(value = "坐标",col = 27)
    private String   coordinate;
    @ExcelColumn(value = "正面标签",col = 28)
    private String   frontLabel;
    @ExcelColumn(value = "负面标签",col = 29)
    private String   negativeLabel;
    @ExcelColumn(value = "评价分布",col = 30)
    private String   evaluationDistribution;
    @ExcelColumn(value = "店铺主图",col = 31)
    private String shopOwnerPicture;
    @ExcelColumn(value = "图片",col = 32)
    private String   picture;


}
