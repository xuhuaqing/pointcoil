package com.pointcoil.util;

import java.math.BigDecimal;

/**
 * @Description 金额计算工具类
 * @Author 元稹
 * @Date 16/12/2018 22:50
 * @Version V1.0
 */
public class ArithmeticUtil {

    private static final int DEF_DIV_SCALE = 5;

    /**
     * @Description 金额相加
     * @Author 元稹
     * @Date 16/12/2018 22:51
     * @Param [v1, v2]
     * @Return double
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @Description 金额相减
     * @Author 元稹
     * @Date 16/12/2018 22:51
     * @Param [v1, v2]
     * @Return double
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * @Description 金额相乘
     * @Author 元稹
     * @Date 16/12/2018 22:51
     * @Param [v1, v2]
     * @Return double
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @Description 金额相除：精确10位小数
     * @Author 元稹
     * @Date 16/12/2018 22:57
     * @Param [v1, v2]
     * @Return double
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * @Description 金额相除：精确10位小数
     * @Author 元稹
     * @Date 16/12/2018 22:59
     * @Param [v1, v2, scale]
     * @Return double
     */
    private static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @Description 金额精确到多少位
     * @Author 元稹
     * @Date 16/12/2018 22:58
     * @Param [v, scale]
     * @Return double
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
