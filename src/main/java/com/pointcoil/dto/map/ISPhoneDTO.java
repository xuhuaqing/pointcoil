package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.pointcoil.constant.Constants.REGEX_MOBILE;

/**
 * Created by WuShuang on 2019/11/1.
 */
@Data
public class ISPhoneDTO {
    @Pattern(regexp=REGEX_MOBILE,message = "格式不正确")
    @NotNull
    private String phoneNumber;


    @Data
    public static class LoginDTO{
        @Pattern(regexp=REGEX_MOBILE,message = "格式不正确")
        @NotNull
        private String phoneNumber;
        @NotNull
        private String code;
    }
}
