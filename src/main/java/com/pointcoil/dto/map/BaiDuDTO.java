package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/11/13.
 */
@Data
public class BaiDuDTO {


    @ExcelColumn(value = "uid" ,col = 1)
    private String uid;
    @ExcelColumn(value = "street_id" ,col = 2)
    private String streetId;

    @ExcelColumn(value = "name" ,col = 3)
    private String name;

    @ExcelColumn(value = "address" ,col = 4)
    private String address;

    @ExcelColumn(value = "type" ,col = 5)
    private String type;


    @ExcelColumn(value = "tag" ,col = 6)
    private String tag;

    @ExcelColumn(value = "navi_location" ,col = 7)
    private String naviLocation;
    @ExcelColumn(value = "alias" ,col = 8)
    private String alias;

    @ExcelColumn(value = "detail_url" ,col = 9)
    private String detailUrl;

    @ExcelColumn(value = "price" ,col = 10)
    private String price;

    @ExcelColumn(value = "shop_hours" ,col = 11)
    private String shopHours;

    @ExcelColumn(value = "overall_rating" ,col = 12)
    private String overallRating;

    @ExcelColumn(value = "taste_rating" ,col = 13)
    private String tasteRating;


    @ExcelColumn(value = "service_rating" ,col = 14)
    private String serviceRating;

    @ExcelColumn(value = "environment_rating" ,col = 15)
    private String environmentRating;

    @ExcelColumn(value = "facility_rating" ,col = 16)
    private String facilityRating;

    @ExcelColumn(value = "hygiene_rating" ,col = 17)
    private String hygieneRating;

    @ExcelColumn(value = "technology_rating" ,col = 18)
    private String technologyRating;

    @ExcelColumn(value = "image_num" ,col = 19)
    private String imageNum;

    @ExcelColumn(value = "groupon_num" ,col = 20)
    private String grouponNum;

    @ExcelColumn(value = "discount_num" ,col = 21)
    private String discountNum;

    @ExcelColumn(value = "comment_num" ,col = 22)
    private String commentNum;

    @ExcelColumn(value = "favorite_num" ,col = 23)
    private String favoriteNum;

    @ExcelColumn(value = "checkin_num" ,col = 24)
    private String checkinNum;

    @ExcelColumn(value = "brand" ,col = 25)
    private String brand;

    @ExcelColumn(value = "content_tag" ,col = 26)
    private String contentTag;

    @ExcelColumn(value = "featured_service" ,col = 27)
    private String featuredService;

    @ExcelColumn(value = "atmosphere" ,col = 28)
    private String atmosphere;

    @ExcelColumn(value = "baidulat" ,col = 29)
    private String baidulat;

    @ExcelColumn(value = "baidulon" ,col = 30)
    private String baidulon;

    @ExcelColumn(value = "wgs84lat" ,col = 31)
    private String wgs84lat;

    @ExcelColumn(value = "wgs84lon" ,col = 32)
    private String wgs84lon;

    @ExcelColumn(value = "gcj02lat" ,col = 33)
    private String gcj02lat;

    @ExcelColumn(value = "gcj02lon" ,col = 34)
    private String gcj02lon;


}
