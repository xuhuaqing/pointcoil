package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by WuShuang on 2019/11/18.
 */
@Data
public class MapPageDTO extends MapCommonDTO{

    @NotNull(message = "页数不能为空！")
    private String  page;

    private String cityName;
    private String brandId;

    @Data
    public static class SubAccountDTO extends MapCommonDTO{
        @NotNull(message = "子账号id 不能为空")
        private String subAccountId;
    }
}
