package com.pointcoil.util;

import com.pointcoil.constant.Constants;
import lombok.Data;

/**
 * @Description 返回前端的统一类
 * @Author 元稹
 * @Date 18/12/2018 17:47
 * @Version V1.0
 */
@Data
public class Response<T> {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回总数
     */
    private Integer count;

    public Response() {
    }

    /**
     * @Description 成功构造器
     * @Author 元稹
     * @Date 18/12/2018 18:23
     * @Param [success, msg, data]
     * @Return
     */
    public Response(String msg, T data) {
        this.code = Constants.SUCCESS_CODE;
        this.msg = msg;
        this.data = data;
    }

    public Response(int code, T data) {

        this.code = code;
        this.data = data;
    }

    public Response(Integer code, T data) {
        this.msg = Constants.SUCCESS_MSG;
        this.code = code;
        this.data = data;
    }

    public Response(Integer code, T data, String msg) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Response(Integer code, T data, Integer count) {
        this.msg = "";
        this.code = Constants.SUCCESS_ADMIN_CODE;
        this.data = data;
        this.count = count;
    }

    /**
     * @Description 失败构造器
     * @Author 元稹
     * @Date 18/12/2018 18:23
     * @Param [success, code, msg]
     * @Return
     */
    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
