package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/16.
 */
@Data
public class MeiTuanWmDTO {

    @ExcelColumn(value = "country",col = 1)
    private String country;
    @ExcelColumn(value = "monthSaleCount",col = 2)
    private Integer monthSaleCount;
    @ExcelColumn(value = "city",col = 3)
    private String city;
    @ExcelColumn(value = "avgPrice",col = 4)
    private String avg_price;
    @ExcelColumn(value = "rating",col = 5)
    private Double rating;
    @ExcelColumn(value = "telephones",col = 6)
    private String telephones;
    @ExcelColumn(value = "description",col = 7)
    private String description;
    @ExcelColumn(value = "likeCount",col = 8)
    private String like_count;
    @ExcelColumn(value = "appCode",col = 9)
    private String app_code;
    @ExcelColumn(value = "logiRating",col = 10)
    private String log_rating;
    @ExcelColumn(value = "title",col = 11)
    private String title;
    @ExcelColumn(value = "geoPoint",col = 12)
    private String geo_point;
    @ExcelColumn(value = "minDeliver",col = 13)
    private String min_deliver;
    @ExcelColumn(value = "qualRating",col = 14)
    private Double qual_rating;
    @ExcelColumn(value = "state",col = 15)
    private String state;
    @ExcelColumn(value = "id",col = 16)
    private Integer id;
    @ExcelColumn(value = "deliverFee",col = 17)
    private Double deliver_fee;
    @ExcelColumn(value = "deliverTime",col = 18)
    private String deliver_time;
    @ExcelColumn(value = "address",col = 19)
    private String address;
    @ExcelColumn(value = "tipInfo",col = 20)
    private String tipInfo;
    @ExcelColumn(value = "catName2",col = 21)
    private String cat_name2;
    @ExcelColumn(value = "catName1",col = 22)
    private String cat_name1;
    @ExcelColumn(value = "catPathKey",col = 23)
    private String cat_path_key;
    @ExcelColumn(value = "keyValues",col = 24)
    private String key_values;
    @ExcelColumn(value = "url",col = 25)
    private String url;
    @ExcelColumn(value = "commentCount",col = 26)
    private Integer comment_count;
    @ExcelColumn(value = "tags",col = 27)
    private String tags;
    @ExcelColumn(value = "promotions",col = 28)
    private String promotions;
    @ExcelColumn(value = "isChainStore",col = 29)
    private Boolean is_chain_store;
    @ExcelColumn(value = "district",col = 30)
    private String district;
    @ExcelColumn(value = "imageUrls",col = 31)
    private String images_urls;
    @ExcelColumn(value = "ratingDist",col = 32)
    private String ratingdist;
    @ExcelColumn(value = "location",col = 33)
    private String location;
    @ExcelColumn(value = "openingHours",col = 34)
    private String opening_hours;
    @ExcelColumn(value = "catId1",col = 35)
    private String catId1;
    @ExcelColumn(value = "catId2",col = 36)
    private String catId2;
    @ExcelColumn(value = "bulletin",col = 37)
    private String bulletin;
}
