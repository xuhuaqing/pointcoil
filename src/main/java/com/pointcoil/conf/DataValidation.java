package com.pointcoil.conf;

import com.pointcoil.constant.MsgConstant;
import com.pointcoil.util.ResponseUtil;
import org.springframework.validation.BindingResult;

/**
 * @ClassNameDataValidation
 * @Date 2019/4/15 0015 13:24
 * @Version 1.0
 **/
public class DataValidation {

    public static String validation(BindingResult result){
        StringBuffer stringBuffer = new StringBuffer();
        result.getFieldErrors().forEach(fieldError -> {
            stringBuffer.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
        });
        return ResponseUtil.errorMsgToClient(MsgConstant.MSG_000003,stringBuffer.toString());
    }

}
