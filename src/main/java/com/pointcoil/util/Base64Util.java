package com.pointcoil.util;

import cn.hutool.core.codec.Base64;

/**
 * Created by WuShuang on 2020/3/23.
 */
public class Base64Util {
    /**
     * 使用Base64进行加密
     * @param res 密文
     * @return
     */
    public static String Base64Encode(String res) {
        return Base64.encode(res.getBytes());
    }

    /**
     * 使用Base64进行解密
     * @param res
     * @return
     */
    public static String Base64Decode(String res) {
        return new String(Base64.decode(res));
    }

}
