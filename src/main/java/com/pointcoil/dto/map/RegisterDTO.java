package com.pointcoil.dto.map;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.pointcoil.constant.Constants.REGEX_MOBILE;

/**
 * Created by WuShuang on 2019/11/1.
 */
@Data
public class RegisterDTO {

    @NotNull
    @Size(min = 2,max = 20,message = "只能起2~20个字符的名字!")
    private String userName;

    @Pattern(regexp=REGEX_MOBILE,message = "手机号格式不正确")
    @NotNull
    private String userPhone;

    @Email(message = "邮箱格式不正确")
    @NotNull
    private String userEmail;

    @NotNull
    @Size(min = 1,max = 30,message = "只能起1~30个字符的名字!")
    private String enterpriseName;

    @NotNull
    @Size(min = 1,max = 30,message = "只能起1~30个字符的名字!")
    private String makeName;

    @NotNull
    private String businessLicense;


}
