package com.pointcoil.entity;

import lombok.Data;

/**
 * Created by WuShuang on 2019/11/30.
 */
@Data
public class UserEntity {
    private Integer userId;
    /**
     * 微信名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;

    /**
     * 二次密码
     */
    private String confirmPassword;
    /**
     * 微信openId
     */
    private String openId;

    /**
     * code
     */
    private String smsCode;
    /**
     * 微信头像
     */
    private String avatar;
    /**
     * 微信code
     */
    private String code;

    private Integer six;

    private String createTime;
    private String updateTime;
    private String city;

}
