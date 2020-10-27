package com.pointcoil.util;

import com.alibaba.fastjson.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.pointcoil.constant.Constants.EFFECTIVE_TIME;
import static com.pointcoil.constant.Constants.REDIS_EXPIRE_TIME;

/**
 * @Date: 2019-04-18 14:25
 * @Version: 1.0.0
 **/
@Component
@Slf4j
public class TokenCheck {

    @Autowired
    private PointCoilProperties pointCoilProperties;

    @Autowired
    private RedisUtil redisUtil;

    public Object checkTokenByShopId(String key,String token){
        if(StringUtils.isEmpty(key)||StringUtils.isEmpty(token)){
            return null;
        }
        Object redisDate = redisUtil.get(key);
        if(null==redisDate){
            return null;
        }
        String s = redisDate.toString();
        if(!s.equals(token)){
            return null;
        }

        if(key.equals("26a2daa3fd2211e9af0d0242ac110003")){
            redisUtil.set(key,token,EFFECTIVE_TIME);
        }else if(key.equals("21f5f2762b7411eaa8e20242ac110002")){
            redisUtil.set(key,token,EFFECTIVE_TIME);
        }else {
            redisUtil.set(key,token,REDIS_EXPIRE_TIME);
        }
        return redisDate;
    }

}
