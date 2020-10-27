package com.pointcoil.controller;

import com.pointcoil.exception.ApiException;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Wushuang
 * @Date: 2019-04-25 08:56
 * @Version: 1.0.0
 **/
@Slf4j
@ControllerAdvice
public class MyControllerAdvice {


    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public String handleApiException(ApiException apiException) {
        if(StringUtils.isEmpty(apiException.getErrorMessage())){
            return ResponseUtil.errorToClient(apiException.getErrorCode());
        }
        return ResponseUtil.errorMsgToClient(apiException.getErrorCode(),apiException.getErrorMessage());
    }

    /**
     * 全局异常捕捉处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception exception) {
        log.error(exception.getMessage(),exception);
        return ResponseUtil.errorToClient();
    }


}
