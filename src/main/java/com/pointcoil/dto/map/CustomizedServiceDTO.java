package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

import static com.pointcoil.constant.Constants.REGEX_MOBILE;

/**
 * Created by WuShuang on 2019/11/18.
 */
@Data
public class CustomizedServiceDTO extends MapCommonDTO {

    @NotNull(message = "真实姓名不能为空！")
    private String name;
    @NotNull(message = "手机号不能为空！")
    @Pattern(regexp=REGEX_MOBILE,message = "手机号格式不正确")
    private String telephone;
    @NotNull(message = "内容不能为空！")
    private String content;


}
