package com.pointcoil.dto.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by WuShuang on 2019/11/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO extends MapCommonDTO{
    @NotNull(message = "品牌id不可空")
    private String brandId;
    @NotNull(message = "标签名称")
    @Size(min = 2,max = 10,message = "名称在2~10个字之间")
    private String labelName;
    private String labelRemarks;
    @NotNull(message = "图片必选")
    private String labelStyleId;
    @NotNull(message = "经纬度不能为空")
    private BusinessZoneDTO.Coordinate coordinates;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowLabel extends MapCommonDTO{
        @NotNull(message = "品牌id不可空")
        private String brandId;
        @NotEmpty(message = "标签名不能为空")
        private List<String> labelName;
        @Max(value = 1,message = "不能大于1")
        @Min(value = 0,message = "不能小于0")
        @NotNull(message = "不能为空")
        //0是全部  1是 有条件筛选
        private Integer isAll;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowLabelDTO{
        private String labelId;
        private String userId;
        private String brandId;
        private String labelName;
        private String labelRemarks;

        public String getLabelId() {
            return labelId;
        }

        public void setLabelId(String labelId) {
            this.labelId = labelId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getLabelRemarks() {
            return labelRemarks;
        }

        public void setLabelRemarks(String labelRemarks) {
            this.labelRemarks = labelRemarks;
        }

        public String getLabelStyleId() {
            return labelStyleId;
        }

        public void setLabelStyleId(String labelStyleId) {
            this.labelStyleId = labelStyleId;
        }

        public String getLabelStyleImg() {
            return labelStyleImg;
        }

        public void setLabelStyleImg(String labelStyleImg) {
            this.labelStyleImg = labelStyleImg;
        }

        public String getLatAndLng() {
            return latAndLng;
        }
        public void setLatAndLng(String latAndLng) {
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                BusinessZoneDTO.Coordinate list = objmapper.readValue(latAndLng,BusinessZoneDTO.Coordinate.class);//将json字符串转化成list
                setCoordinate(list);//调用setStar方法
                this.latAndLng = latAndLng;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public BusinessZoneDTO.Coordinate getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(BusinessZoneDTO.Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        private String labelStyleId;
        private String labelStyleImg;
        private String latAndLng;
        private BusinessZoneDTO.Coordinate coordinate;






    }

    @Data
    public static class LabelId extends MapCommonDTO{
        @NotNull(message = "标签id不能为空")
        private String labelId;
    }

    @Data
    public static class SelectLabelById{
        private String labelId;
        private String userId;
        private String labelName;
        private String labelRemarks;
        private String labelStyleId;
        private String labelStyleImg;
        private String latAndLng;
    }

    @Data
    public static class UpdateLabel extends MapCommonDTO{
        @NotNull(message = "标签id 不可为空")
        private String labelId;
        private String labelName;
        private String labelRemarks;
        private String labelStyleId;
    }

}
