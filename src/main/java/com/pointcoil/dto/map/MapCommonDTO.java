package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by WuShuang on 2019/11/4.
 */
@Data
public class MapCommonDTO {
    @NotNull(message = "用户id不能为空")
    private String userId;
    @NotNull(message = "用户token不能为空")
    private String token;

    private String memberLevel;
    private String sonId;

}
