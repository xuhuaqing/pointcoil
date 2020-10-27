package com.pointcoil.dto.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.pointcoil.constant.Constants.REGEX_MOBILE;

/**
 * Created by WuShuang on 2019/11/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapSubAccountDTO extends MapCommonDTO{

    @NotNull(message = "账号名称不能为空！")
    @Size(min = 2,max = 10,message = "账号名称在2~10之间！")
    private String accountName;

    /*
    0是女 1是男
     */
    @NotNull(message = "性别不能为空！")
    private Integer accountSex;

    @Pattern(regexp=REGEX_MOBILE,message = "手机号格式不正确")
    @NotNull
    private String accountPhone;

    @Email(message = "邮箱格式不正确！")
    private String accountEmail;

    private String id;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubAccountDTO{
        private String accountName;

        private Integer accountSex;

        private String accountPhone;

        private String accountEmail;

        private String id;
    }

}
