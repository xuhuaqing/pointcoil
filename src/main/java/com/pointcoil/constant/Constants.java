package com.pointcoil.constant;

/**
 * @Description 常量类
 * @Author 元稹
 * @Date 18/12/2018 14:55
 * @Version V1.0
 */
public class Constants {

    /**
     *
     */

    public static final int REMINDTIME =  60 * 5;
    public static final String INVITE = "invite";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String REGISTER = "register";
    public static final String JOIN = "join";
    public static final String QUIT = "quit";
    public static final int ERROR_CODE = 10001;
    public static final int ZERO = 0;
    public static final String ERROR_MSG = "未知错误";

    public static final String REMINDMSG = "当天最多提醒发货一次哦~";

    public static final String ERROR_UPDATE_MSG = "更新失败";

    public static final String SMS_CODE_ERROR_MSG = "验证码错误";

    public static final String NO_DATA = "没有数据";

    public static final String COMMON_ERROR_SELECT = "请至少选择一个身份";

    public static final int SUCCESS_CODE = 200;

    public static final int PASSWORD_ERROR_CODE = 10003;

    public static final String PASSWORD_ERROR_MSG = "帐号或者密码错误";

    public static final String PASSWORD_ERR_MSG = "当前密码输入错误";

    public static final String TOKEN_ISSUER = "watch";

    public static final Integer EFFECTIVE_TIME = 7 * 24 * 60 * 60 ;

    public static final String SUCCESS_MSG = "success";

    public static final String MATCHMAKER = "4";

    public static final String MATCHMAKER_NUM = "1";

    public static final String SINGLE = "0";

    public static final String SINGLE_NUM = "2";

    public static final String THREE_NUM = "3";

    public static final int HAVE_DATA_CODE = 10005;

    public static final String NO_DATA_MSG = "数据不能为空";

    public static final String NULL_DATA_MSG = "数据为空";

    public static final String HAVE_DATA_MSG = "手机号已经被注册";

    public static final String DIFFERENT_PASSWORD_MSG = "两次密码不一致";

    public static final int DIFFERENT_PASSWORD_CODE = 10102;

    public static final String SUCCESS_LOGIN_MSG = "登录成功";
    public static final String defaultAddress = "defaultAddress";

    public static final int SUCCESS_ADMIN_CODE = 0;

    public static final String HTTPS_PREFIX = "https://";

    public static final int SUCCESS_OPERATION_CODE = 0;

    public static final int ERROR_OPERATION_CODE = 10006;

    public static final String ERROR_OPERATION_MSG = "操作失败";

    public static final String SUCCESS_OPERATION_MSG = "操作成功";

    public static final String HAVA_MATCHMAKER_MSG = "已经是好友了";

    public static final int HAVA_MATCHMAKER_CODE = 10103;

    public static final int HAVE_MESSAGE_CODE = 10002;

    public static final String HAVE_MESSAGE_MSG = "公告内容已经存在";

    public static final String MAN = "0";

    public static final String WOMAN = "1";

    public static final String ADMIN_IDENTITY = "1";

    public static final String IN_GROUP_ALREADY = "该媒婆已经在群里了";

    public static final String PHONE_FORMAT_MSG = "手机号格式不正确";

    public static final int PHONE_FORMAT_CODE = 10006;

    public static final String ACCESSKEY_ID = "LTAIPxtmDjhQVgFY";

    public static final String ACCESSKEY_SECRET = "Fg47FhX4GEWdu60BpgSrNr6Vb26KxY";

    public static final String SIGN_NAME = "shouwang";

    public static final String TEMPLATE_CODE = "SMS_157660098";

    public static final String OK_DATA = "OK";

    public static final String DATA_IS_EXIST = "数据已存在";

    public static final String NO_OPERATION_PERMISSIONS = "没有操作权限";

    public static final String PASSWORD = "password";

    /**
     * 此处替换成您的appKey
     */
    public static final String APP_KEY = "kj7swf8ok3gw2";
    /**
     * 此处替换成您的appSecret
     */
    public static final String APP_SECRET = "ZcW4OwHMrC6";
    /**
     * 自定义api地址
     */
    public static final String API = "http://api.cn.ronghub.com";

    public static final String NONE = "";

    public static final String VERIFICATION_CODE_ERROR = "验证码错误";


    public static final String CODE_ERROR_MSG = "当前密码输入错误";

    public static final int EIGHT_NUM = 8;
    public static final String EIGHT_String = "8";

    public static final String HAVE_CARD_MSG = "您已经有名片了,不能创建多张";

    public static final int HAVE_CARD_CODE = 10444;
    public static final String NO_CARD_MSG = "你还没有名片";
    public static final String NO_CARD_ME_MSG = "该用户没有名片";
    public static final int NO_CARD_ME_CODE = 10888;
    public static final String HAVE_BUSINESS_MSG = "0";
    public static final String NO_BUSINESS_MSG = "1";
    public static final String ADDRESS_NO_SAFE_MSG = "地址不合法";
    public static final int ADDRESS_NO_SAFE_CODE = 10222;
    public static final String OK_MSG = "OK";
    public static final String STATUS_MSG = "status";
    public static final double EARTH_RADIUS = 6371393;

    public static final String TEN_NUM = "10";
    public static final String NICE_NUM = "9";
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9])|(19[0-9])|(16[0-9]))\\d{8}$";

    public static final String BOY_CLOTHING = "男装";

    public static final String GIRL_CLOTHING = "女装";

    public static final String KID_CLOTHING = "童装";

    public static final String ONE_NUM = "1";

    public static final String TWO_NUM = "2";

    public static Integer REDIS_EXPIRE_TIME =  60 *60;

    public static final int ZERO_INT = 0;

    public static final int ONE_INT = 1;

    public static final int TWO_INT = 2;

    public static final int THREE_INT = 3;

    public static final String SEVEN_INT = "7";

    public static final String FIVE_INT = "5";

    public static final String SIX_STRING = "6";

    public static final String IP_URL = "http://118.190.246.14:8080";

    public static final String PATH = "/code/img/swTemplate";

    public static final String REALPATH = "/img/swTemplate/";

    public static final String STATUS = "0";

    public static final String TYPENAME = "本地服务";

    public static final int HAVE_LIKE_CODE = 10666;

    public static final String HAVE_LIKE_MSG = "已经点赞过了";

    public static final int HAVE_FOLLOW_CODE = 10777;

    public static final String HAVE_FOLLOW_MSG = "已经关注过了";

    public static final int HAVE_COLLECTED_CODE = 10555;

    public static final String HAVE_COLLECTED_MSG = "已经收藏过了";

    public static final String IS_SHOW_TEXT = "展示";
    public static final String NO_SHOW_TEXT = "不展示";

    public static final String HAVE = "有";
    public static final String NO_HAVE = "没有";

    public static final String BOY = "男";

    public static final String GIRL = "女";

    public static final String IS_ENTER = "入驻";

    public static final String NO_ENTER = "没入驻";

    public static final String NO_POWER_MSG = "您还没有权限";

    public static final int NO_POWER_CODE = 10001;

    public static final int HAVE_NAME_CODE = 10002;

    public static final String HAVE_NAME_MSG = "用户名已经存在";

    public static final String HOT = "热门";

    public static final String NO_HOT = "不热门";

    public static final String FEATURED = "精选";

    public static final String NO_FEATURED = "不精选";

    public static final String REGISTER_USER = "注册用户";

    public static final String WHOLESALER = "批发商";

    public static final String MANUFACTOR = "厂家";

    public static final String ADMIN_USER = "管理员";

    public static final String HAVE_ADD = "已经有记录了,请修改";

    public static final int BETWEEN_ERROR_CODE = 10875;

    public static final String BETWEEN_ERROR_MSG = "已经有记录了,请修改";

    public static final String FOUR_ERROR_MSG = "已经有四个分类了,请删除或者修改";

    public static final int FOUR_ERROR_CODE = 10666;

    public static final int NO_MORE_THAN_TWO_CODE = 10444;

    public static final String NO_MORE_THAN_TWO_MSG = "已经有两条数据了,无法继续添加";

    public static final int HAVE_COLOR_CODE = 10333;

    public static final String HAVE_COLOR_MSG = "已经有该类型了,请勿重复录入";

//    public static final String AppUrl = "http://129.28.180.167:8080";

    public static final String AppUrl = "https://wwww.weshareverything.com";
}
