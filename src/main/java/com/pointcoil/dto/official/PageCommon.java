package com.pointcoil.dto.official;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by WuShuang on 2019/11/1.
 */
@Data
public class PageCommon {
    @Min(value = 1,message = "最小值为1")
    @NotNull(message = "page不能为空")
    private Integer page;

}
