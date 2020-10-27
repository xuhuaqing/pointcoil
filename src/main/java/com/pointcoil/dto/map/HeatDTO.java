package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by WuShuang on 2019/11/29.
 */
@Data
public class HeatDTO extends MapCommonDTO{

    @NotNull(message = "热力图id不能为空")
    private String heatMapId;
    @NotNull(message = "热力图类型不能为空")
    private String heatType;
    @NotNull(message = "城市不能为空")
    private String city;


    @Data
    public static class HeatDataDTO{
        private String count;
        private String shopName;
    }

    @Data
    public static class HeatMapDataDTO{
        private Integer count = 50;
        private Double lat;
        private Double lng;
        private String id;
    }



    @Data
    public static class HeatMapDeleteDTO extends MapCommonDTO{
        @NotNull
        private Double lng;
        @NotNull
        private Double lat;
        @NotNull
        private Double radius;
        @NotNull
        private Integer type;
        @NotNull(message = "热力图id不能为空")
        private String heatMapId;
        @NotNull(message = "城市不能为空")
        private String city;
    }

    @Data
    public static class AddHeatMapDataDTO extends MapCommonDTO{
        @NotNull
        @Min(value = 1,message = "最低不能小于1")
        @Max(value = 100,message = "超过最大数量！")
        private Integer count;
        @NotNull
        private Double lat;
        @NotNull
        private Double lng;
        @NotNull
        private Integer type;
        @NotNull
        private String city;
    }

}
