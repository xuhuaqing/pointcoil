package com.pointcoil.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wushuang
 * @Date: 2019-04-25 16:07
 * @Version: 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException  {

    private String errorCode;
    private String errorMessage;

    public ApiException(String errorCode){
        this.errorCode=errorCode;
    }

}
