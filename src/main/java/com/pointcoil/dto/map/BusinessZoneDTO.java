package com.pointcoil.dto.map;

import cn.hutool.json.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by WuShuang on 2019/11/5.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessZoneDTO extends MapCommonDTO {

    private String xxx;

    private String customizedId;

    @NotNull(message = "brandId不能为空")
    private String brandId;

    @NotEmpty(message = "经纬度不能为空")
    private List<Coordinate> coordinates;

    private String radius;
      @NotNull(message = "不能为空")
       @Min(value = 0,message = "最小为0")
      @Max(value = 3,message = "最大为3")
    private Integer businessType;

      @NotNull(message = "商圈名称不能为空")
     @Size(min = 2,max = 30,message = "只允许2~30的汉字")
    private String businessName;

      @NotNull(message = "商圈描述不能为空")
      @Size(min = 2,max = 1000,message = "只允许2~1000的汉字")
    private String businessDescribe;

      @NotNull(message = "商圈等级不能为空")
      @Max(value = 2,message = "最大为2")
      @Min(value = 0,message = "最小为0")
    private Integer businessLevel;

      @NotNull(message = "不能为空")
    private Integer businessOpen;

      @NotNull(message = "不能为空")
    private Integer businessUnsold;

      @NotNull(message = "商圈截图不能为空")
    private String screenshotImg;

      @NotNull(message = "商圈截图热力图")
    private String thermodynamicChart;

     @NotNull(message = "商圈面积不能为空！")
    private String businessArea;

    private String sonId;

    private String city;

    private String province;

    private String district;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Document(value = "tbl_coordinate")
    public static class Coordinate{
        private double lng;
        private double lat;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class  ShowBusinessZoneDTO extends MapCommonDTO {
        @NotNull(message = "不能为空")
        private String brandId;
        @Min(value = 0,message = "最小的值为0")
        @Max(value = 1,message = "最大的值为1")
        @NotNull(message = "不能为空")
        private Integer businessOpen;
        @Min(value = 0,message = "最小的值为0")
        @Max(value = 1,message = "最大的值为1")
        @NotNull(message = "不能为空")
        private Integer businessUnsold;
        @Min(value = 0,message = "最小的值为0")
        @Max(value = 1,message = "最大的值为1")
        @NotNull(message = "不能为空")
        private Integer all;
        @NotNull(message = "不能为空")
        private String cityName;
    }


    public static class SBusinessZoneDTO{
        private List<BusinessZoneDTO.Coordinate> coordinates;
        private String businessId;
        private String lngAndLat;

        public String getCustomizedId() {
            return customizedId;
        }

        public void setCustomizedId(String customizedId) {
            this.customizedId = customizedId;
        }

        private String customizedId;

        public String getBusinessOpen() {
            return businessOpen;
        }

        public void setBusinessOpen(String businessOpen) {
            this.businessOpen = businessOpen;
        }

        public String getBusinessUnsold() {
            return businessUnsold;
        }

        public void setBusinessUnsold(String businessUnsold) {
            this.businessUnsold = businessUnsold;
        }

        private String businessOpen;
        private String businessUnsold;

        public List<BusinessZoneDTO.Coordinate> getCoordinates() {
            return coordinates;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getLngAndLat() {
            return lngAndLat;
        }

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessLevel() {
            return businessLevel;
        }

        public void setBusinessLevel(String businessLevel) {
            this.businessLevel = businessLevel;
        }

        private String reportId;
        private String radius;
        private String businessType;
        private String businessName;
        private String businessLevel;

        public void setCoordinates(List<BusinessZoneDTO.Coordinate> coordinates) {
            this.coordinates = coordinates;
        }
        public void setLngAndLat(String lngAndLat) {
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                List<BusinessZoneDTO.Coordinate> list = objmapper.readValue(lngAndLat,List.class);//将json字符串转化成list
                setCoordinates(list);//调用setStar方法
                this.lngAndLat = lngAndLat;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    @Data
     public static class BusinessAddress extends MapCommonDTO{
        @NotNull(message = "经度不能为空")
        private double lng;
        @NotNull(message = "纬度不能为空")
        private double lat;
        @NotNull(message = "品牌id不能为空")
        private String brandId;
        private String city;
    }

    @Data
    public static class BusinessReport extends MapCommonDTO{
        @NotNull(message = "商圈报告id不为空！")
        private String reportId;
    }


    @Data
    public static class BusinessReportApi{
        @NotNull(message = "商圈报告id不为空！")
        private String reportId;
        @NotNull(message = "openId不能为空！")
        private String openId;
    }



    @Data
    public static class OtherDTO{
        private String otherName;
        private String otherType;
    }


    @Data
    public static class ShowBusinessById extends MapCommonDTO{
        @NotNull(message = "商圈id 不可以为空")
        private String businessId;
    }

    @Data
    public static class DeleteBusiness extends MapCommonDTO{
        @NotNull(message = "商圈id 不可以为空")
        private String businessId;
        @NotNull(message = "品牌id不能为空")
        private String brandId;
    }

    @Data
    public static class UpdateBusiness extends MapCommonDTO{
        private String businessType;
        private String businessName;
        private String businessLevel;
        private String businessId;
        private String businessDescribe;
        private String businessUnsold;
        private String businessOpen;
    }

    @Data
    public static class I1{
        private String lonAndLat;
        private String radius;
        private String type;
        private String id;
    }


    @Data
    public static class ZoomBusiness extends MapCommonDTO{
        @NotNull(message = "品牌id不能为空")
        private String brandId;
        @NotNull(message = "城市不能为空")
        private String city;
    }

    @Data
    public static class ZoomBusinessDTO{
        private String address;
        private String area;
        private String lng;
        private String lat;
    }

    @Data
    public static class PictureChar extends MapCommonDTO{
        @NotNull(message = "报告id不能为空！")
        private String reportId;
        @NotNull(message = "热力图截图不能为空！")
        private String thermodynamicChart;
    }
}
