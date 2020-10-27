package com.pointcoil.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassNameMsgConstant
 * @Author
 * @Date 2019/4/15 0015 10:24
 * @Version 1.0
 **/
public class MsgConstant {

    private final static Map<String, String> MSG = new HashMap<>();
    /**
     * 成功返回默认码
     */
    public static final int SUCCESS_CODE = 200;

    public static final int PAGE_SIZE =10;
    /**
     * 成功返回默认信息
     */
    public static final String SUCCESS_MSG = "SUCCESS";
    public final static String MSG_000000 = "000000";
    public final static String MSG_000001 = "000001";
    public final static String MSG_000002 = "000002";
    public final static String MSG_000003 = "000003";
    public final static String MSG_000004 = "000004";

    public final static int MSG_0 = 0;

    /**
     * app用户相关
     */
    public final static String MSG_001000 = "001000";
    public final static String MSG_001001 = "001001";
    public final static String MSG_001002 = "001002";
    public final static String MSG_001003 = "001003";
    public final static String MSG_001004 = "001004";
    public final static String MSG_001005 = "001005";
    public final static String MSG_001006 = "001006";
    public final static String MSG_001007 = "001007";
    public final static String MSG_001008 = "001008";
    public final static String MSG_001009 = "001009";
    public final static String MSG_001010 = "001010";
    public final static String MSG_001011 = "001011";
    public final static String MSG_001012 = "001012";
    public final static String MSG_001013 = "001013";
    public final static String MSG_001014 = "001014";
    public final static String MSG_001015 = "001015";
    public final static String MSG_001016 = "001016";
    public final static String MSG_001017 = "001017";
    /**
     * app首页
     */
    public final static String MSG_002000 = "002000";
    /**
     * 商品相关
     */
    public final static String MSG_003000 = "003000";
    public final static String MSG_003001 = "003001";
    public final static String MSG_003002 = "003002";

    static {
        /**
         * 正常返回
         */
        MSG.put(MSG_000000, "ok");
        /**
         * 系统异常
         */
        MSG.put(MSG_000001, "系统异常");
        MSG.put(MSG_000002, "redis取值失败");
        MSG.put(MSG_000003, "数据校验未通过");
        MSG.put(MSG_000004, "短信验证失败");
        MSG.put(MSG_001005, "登录状态已失效，请重新登录");
        MSG.put(MSG_001000, "两次输入密码不相同");
        MSG.put(MSG_001001, "注册信息已存在");
        MSG.put(MSG_001002, "手机号还未审核通过");
        MSG.put(MSG_001003, "密码错误");
        MSG.put(MSG_002000, "查询类别不正确");
        MSG.put(MSG_001004, "用户类型不正确");
        MSG.put(MSG_003000, "选择数量超库存");
        MSG.put(MSG_003001, "选择数量未达起批数");
        MSG.put(MSG_003002, "选择商品已下架");
        MSG.put(MSG_001006, "用户未绑定手机号");
        MSG.put(MSG_001007, "输入信息不完整");
        MSG.put(MSG_001008, "无数据");
        MSG.put(MSG_001009, "品牌创建超过数量！");
        MSG.put(MSG_001010, "商圈创建超过数量！");
        MSG.put(MSG_001011, "无商圈！");
        MSG.put(MSG_001012, "标签创建超过数量！");
        MSG.put(MSG_001013, "您无权查看热力图，请充值会员！");
        MSG.put(MSG_001014, "您创建的子账号超出！！");
        MSG.put(MSG_001015, "温馨提示您的账号权限无法创建更多子账号，如需帮助，请联系网站管理员！！");
        MSG.put(MSG_001016, "不可以用主账号添加！！");
        MSG.put(MSG_001017, "标签名称重复！");

    }

    public static String getMsg(String key) {
        return MSG.get(key);
    }

}
