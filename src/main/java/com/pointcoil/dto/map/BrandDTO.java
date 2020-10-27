package com.pointcoil.dto.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by WuShuang on 2019/11/4.
 */
@Data
public class BrandDTO extends MapCommonDTO{

    private String brandId;
    @NotNull
    @Size(min = 1,max = 40,message = "标题请输入1~40个字符串之间")
    private String brandTitle;
    @NotNull
    private String brandIndustryOne;
    @NotNull
    private String brandIndustryTwo;
    @NotNull
    private String brandImg;
    private String createTime;
    private String updateTime;

    @Data
    public static class SelectBrandDTO{
        private String brandId;
        private String brandTitle;
        private String brandIndustryOne;
        private String brandIndustryTwo;
        private String brandImg;
        private String industryLevelTwoName;
        private String industryLevelOneName;
        List<MapIndustryDTO> mapIndustryDTO;
    }
    @Data
    public static class UpdateBrandDTO extends MapCommonDTO{
        private String brandId;
        private String brandTitle;
        private String brandIndustryOne;
        private String brandIndustryTwo;
        private String brandImg;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BrandId extends MapCommonDTO{
        @NotNull(message = "不能为空！")
        private String brandId;
        private String cityName;
    }
    @Data
    public static class BrandInit{
        private String brandId;
        private String brandImg;
        private String title;
    }

    @Data
    public static class QR extends MapCommonDTO{
        private String price;
    }

    @Data
    public static class DistributionMap{
        private String name;
        private Integer value;
    }
}
