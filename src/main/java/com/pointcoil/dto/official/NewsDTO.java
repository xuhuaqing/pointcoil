package com.pointcoil.dto.official;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by WuShuang on 2019/11/1.
 */
@Data
public class NewsDTO {

    @NotNull(message = "新闻id 不可为空")
    @Size(min = 10, max = 60, message = "字符串长度太短。或太长")
    private String newsId;
}
