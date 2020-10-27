package com.pointcoil.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author:WuShuang
 * @date:2019/5/5
 * @ver:1.0
 **/
public class GenerateRandomCode {

    private static final String SYMBOLS = "0123456789";


    private static final Random RANDOM = new SecureRandom();


 /*   public static String  createRandomNumber(){
        char[] nonceChars = new char[6];

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }


        return new String(nonceChars);

    }*/


    /**
     *获取随机验证码
     * @param len
     * @return
     */
    public static String generNumCode(int len){
        //实例化 StringBuffer ,用作拼接验证码
        //博主会在这篇博文发后不久，会更新一篇String与StringBuilder开发时的抉择的博文。
        StringBuffer code = new StringBuffer();
        code.append(getRandom());
        //长度减1，随机拼接数字
        for (int i = 0; i < len-1; i++) {
            code.append(new Random().nextInt(10));
        }
        //利用递归算法，如果随机数长度不够则重新随机
        if(code.length() != 6){
            return generNumCode(6);
        }else {
            return code.toString();
        }
    }

    /**
     * 使用递归算法，获取第一个随机数不为0
     * @return int
     */
    public static int getRandom(){
        int number = new Random().nextInt(10);
        if(0 == number){
            return getRandom();
        }
        return number;
    }


}
