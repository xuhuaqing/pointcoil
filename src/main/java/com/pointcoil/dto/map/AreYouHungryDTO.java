package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by WuShuang on 2019/11/14.
 */
@Data
public class AreYouHungryDTO {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public AreYouHungryDTO(String id, String title, String address, String monthSaleCount, String bdLng, String bdLat,String city) {
        this.id = id;
        this.title = title;
        this.address = address;
        if(StringUtils.isEmpty(monthSaleCount) || monthSaleCount.trim().isEmpty() || !isInteger(monthSaleCount)){
            this.monthSaleCount = "0";
        }else {
            this.monthSaleCount = monthSaleCount;
        }
        this.bdLng = bdLng;
        this.bdLat = bdLat;
        this.city = city;
    }

    @ExcelColumn(value = "id",col = 1)
    private String id;
    @ExcelColumn(value = "otherIds",col = 2)
    private String otherIds;
    @ExcelColumn(value = "state",col = 3)
    private String state;
    @ExcelColumn(value = "city",col = 4)
    private String city;
    @ExcelColumn(value = "title",col = 5)
    private String title;
    @ExcelColumn(value = "address",col = 6)
    private String address;
    @ExcelColumn(value = "catName1",col = 7)
    private String catName1;
    @ExcelColumn(value = "catName2",col = 8)
    private String catName2;
    @ExcelColumn(value = "telephones",col = 9)
    private String telephones;
    @ExcelColumn(value = "mobilephones",col = 10)
    private String mobilePhones;
    @ExcelColumn(value = "telephones400",col = 11)
    private String telephones400;
    @ExcelColumn(value = "openingHours",col = 12)
    private String openingHours;
    @ExcelColumn(value = "lon",col = 13)
    private Double lon;
    @ExcelColumn(value = "lat",col = 14)
    private Double lat;
    @ExcelColumn(value = "promotions",col = 15)
    private String promotions;
    @ExcelColumn(value = "minDeliverFee",col = 16)
    private Integer minDeliverFee;
    @ExcelColumn(value = "deliverFee",col = 17)
    private Integer deliverFee;
    @ExcelColumn(value = "rating",col = 18)
    private Double rating;
    @ExcelColumn(value = "commentCount",col = 19)
    private Integer commentCount;
    @ExcelColumn(value = "monthSaleCount",col = 20)
    private String monthSaleCount;
    @ExcelColumn(value = "url",col = 21)
    private String url;
    @ExcelColumn(value = "chain",col = 22)
    private Integer chain;
    @ExcelColumn(value = "bd_lon",col = 23)
    private String bdLng;
    @ExcelColumn(value = "bd_lat",col = 24)
    private String bdLat;

}
