package com.pointcoil.controller.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.JsonArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.pointcoil.conf.WebSocketServer;
import com.pointcoil.dto.map.AliPayDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.dto.map.WxpayVo;
import com.pointcoil.mapper.PayMapper;
import com.pointcoil.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.alipay.api.AlipayConstants.FORMAT;


/**
 * @author:WuShuang
 * @date:2019/7/16
 * @ver:1.0
 **/
@RestController
@Slf4j
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(com.pointcoil.util.HttpUtil.class);


    //沙箱环境
    //appid
    public static String APP_ID = "2021001100611177";
    //pkcs8的私钥
    public static String APP_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCkWQNFV21znfOLBszn+Ha3rNNOYfDBcxFrdGtjMORcBbgy1PV1yMtPnzR4P1qKrp4zCKl5J16wzk8CojA4bQ+zj5EsHC28lvVkHR99u82FSxIkkQitjykDMN4vcz33GGP6ZRQsVrm5rv3ScMWYAOjIs+UX5xqfp5mzrC0fdxyCx6+SrMhg2sue1WYQnyRXpTr0wW1IBKQEXL5wqfswTxac8RTZmSOXUfjOCkTC9UtEZOnuO5IWbNmgBF7gNlPNsExBk1M48dctA8MDhcALX/n6LCJEKz+EmH73kSLsOqeZaTPcLIMpVUDC9iyICpDqP96NkTH22VAQPlZcuI6frMn3AgMBAAECggEBAKByvny+A/8yH4iFVRrLOavMMGd/urpCCqmreQPXwboNY21M927MX6EWPvV+dHicTZppRHshcpKOeBiMGyObZg7ZhNNZl1Vc06mvvQTViqotRkMozR4Ue+e/l8sfaUmJKGJWuEEpUhzzuJ3wtxfVG26GIo0ZdKaVy7rbxxnHAbCyji/YRzUIoMZl7uYnOrRe132ttyCtViVdRhIuQin86b26u/VBVawvQxaT6SIzLIXiOd7ld44iFWGBk9+rCRUv+m0WNVNiU7xNaZVfT4elvVEcszPlSaNIRvl33zEiwKLFFCbzG5UxvoaiPYtffl+lNovR75Y8mX6TjE3dEL2zmEECgYEAz35HbVmEQWBVVyu7z7mtBMnxoBkOdP84qCkwblFwdCQIQpGnAZyrxt7HURvD5NBiZv4BsqhYiE03Ue5b0twZG7uyahcAGMeg4PvgRNHLtubC9xgL9rhbmY9KwLZgPPeD4SxjaUNFeHgc5I8chBpDKFVHoPDGI2QbmXzj0kWfrkcCgYEAysSgrAvT9cY3LzsYgpNaNziw2HcNVLU3gLQMS06h/qPAaUqjLca+LDaxkWrCPPD2QtHiWbCishaarcsByKKOXVbc4DpL6qdqjrs2nVOhIDLX9WMIHJleLziaJgeHRByKF/laa38VWFbhjgSDUzYxjMWnOB43ia2IK7lPiS6rbtECgYEAqyWk8uxYa7hzDBD1vFSSAxYzaHPiedyetg4rdYNVN2cVLRuI3oqFIedWDm/ReCXuVLeFXQEA7YSLcdgjA10qlLaQZnOMT+QxrgH0La/C0Z2sCTZbf+j3HUWVSjnkTcV3kT8UzJmBbuncIyZsRgaHiHg3Oef/9uc24LakECf/mzcCgYEAi8zPvm8VP7HfIGhNONHhdItYmpZSkaGz17/LbiyT0h4SrkWwhgIt4gv/l3QU4bF9CIru1cnMEp81HDgITc2N/FrERLDM4Md211vKhuQ/BhUoAl66HAW+Ay6yUSAFqfdHWsooBk+DZvnX33PLG5cUyKsJ5M4omnsTlJnadYnA1tECgYEAhjdz5M7Ez2kkOg7WRmfcKmN4VM0I8QL9DTiHuQ7MWDbPfGtFYlGw2c4ONQ6IsGI848J+8+8oO3fOfpNwXgEzt/JspS9cBQJpDaO6dv/WJr6Dzv3eJvONrKeUwVjEBoAzHmEb1A9a2J0nOxaKlOzyjXDdBkW4SPAQL0LC+QG+BBE=";
    //支付宝公钥                             "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGr4pe+eTm+y4cp2MyidrI9l3+fclMTC7+iiSPPiz5zTXsk5RdS/qUOZd1Ip/kf26kWWBONqQFmoSeQrwkXkiyiZaIAHS5/276EA49SyDjV0oyvg2HpuMdMKG5ZtRPyIRw8kSIbw8UtJk/K0Ghw2GuFwB8874PuQ7upDnN0MfFdcQfaa1IZRhHKIqbcu90F7CWtagTzgMu6BhkSe/1B+57wy/sg0mwuIZcE6X2YAN/2Bfre+eSpo7cj8Yp62ZwIeqpA7enlZaH0fON2PXVDNcA/FinFMKyPZcesFu/p+FHutxVHbRidSnPqulGyyI9lBpKR7NRdTE34rf/T0H823tbAgMBAAECggEAHjl4cNXdY2RMtctufGHpQh3ffAPYT5HtHdv8VlTanPhLOJrpuYIaanb+ISAzB4D9YE5aSVMDveouBqHtPLAX2lD8AeswRqkmy2EdAO5CJCJrmTWDLcTqxJssP8HutTTHW91SOdfKZ5gbp8xaPyXsXrf0uRWtujOKQU2DV0eZ/RqcCvJx+GDp3UaEf5EzArkHugZeM7FpiuLc7xlGoB5D5zMo7NtUB90sc1zON44zBYRz/J4aG5EGpSusU7zrz5hS2tDobsdyfzevnVmPgmRLmuNonPUn86V6NQ9/GKxDPUpSEMgMYGC/Gu1qdKPwF8T70Rhfhcj65fVWHeOYPjGaGQKBgQDCMrau+nQddYWFm1l51p2yzUdwbI+22Yn/DcrcUMS8GTSHUVSm6we7yDqXTfnUsdqUNk/sSYhXShkPjuhytWgsh5czxyYhAhvY/pQ5raKPsBrzOIW2o51M+i3D6D8w/1Twju9+UM8fXwPMSzxdPsAWLcGh3POSWWH8gHGdjmMhrQKBgQCxjFy1vTTy1+WEWJ4fgXN5MwAZS4RSNjPbKXG7COjoc2Nvh4aPFPl1bqDBBIr4ctLBQajYLp9sVA4kifdQmjmWP9raC5I7mUX0mFWWS5bxtOqYerH731Mg3K2M0e/e0ZIF+CumUTK/eaSZmJgI1Zcm8NjX9UWtcIpxq4BkdzYCJwKBgEgnS5UL3WSsq6WOqNoKEaHDf82XqHYJsaogV8UwBUjWHJxCba9vF/p/VLWu9So/wrTa0Ss7zvqPv2v2bwtft4sD/fvw/Iu7GtmvDSe5CfNVPU4tLBFGDUXyllp5yjDMTaTrlrRaFfrT9LdNJOqITBpxecZIL/+qUivdFVmy88YpAoGAIxPsLSvYjjIBcohflTHqMoPU/SpiVivHSwGBKsaz3VkOyDYqa5JlRsI4mGnhWvy0juH+DdgIP0rMEIQBjLcrsXekMXgfyA6cN5rxLeR5Sf/IaZ+0EmMo9VEkKLJBhfA5vVwuo4lfUV0jHAV95CDu4A6tiN04iu+1KogLsc2rqfkCgYEApxIAo1wjzzroJxAsF7HNYxoJ//mR4WSN72Why0KDXVj5+H/jkZRm21x+zxylZ8kQhdWQid9Kjghy/VcMohezOt0uDf/PKsr+pyzIIVh2UrVJRoyHkrtirZAHyi/2VGjit+UJvc66HnhL7bhIgXuu2dNZDPXDkDPw9wFydx1ZRlk="
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Nfk+7XE/Kxp4lR050aHSWt2bEw8yfS1U5hIPdHIC80EyHhd3BQr1sVdb+QMwl5u66vfnpU8G/TCKycmxA7wS0K+pj2UD+hnfGw8vRWS2zKIVq3BRatkdDel0chVldDO7uizEFSdlggksjKhkUQbb7v5+scrZb2ebEH+mNg7aCH3Cir7SszxbQbDnjNccueJDhVgwbP+Nrh7RKBKPsJu3e0kz5wvMPq5h7T3CU4tVaf4KLCTJ5iM1tUFgTCnYMvRzVGZ9pNgyhitGbLO2jKop9rCc0Fq3GPlMcO/y0HnL9cMUfb2NMiKegkBg8jwh1vpLQDqguh9WgoS5NFCYF6ScQIDAQAB";
    //签名方式                                  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvUszJHDXghLcVcIzPaXx2RL2qfsF7mjdehE1I333JWY+D6xE0VfD1sa4pd1hwICSSUDSQXtvkFvAm+Op93jUCSSoT46urnO+adpqj86fUQugq2XMn/gjR06zvesZjwUEyFbo7wCE4yOXU/Prwwf/v01Y8wfgl+4LHZEBkJwLGEbuQ621GPKJ6s45XL1xU90NeeLJajid0m0AvIs7UtiK4zRx1qjo0D/69E08tlpP9rI0Be7LeJKVjVyIsQgLvLpzm/QZNvqngaMZQCTZkMKA379v8G8TbnOhA5UYTvLrwTMZ36HDOTlaGMjXAtGGUWp0jm8ohzIliSunNIHMuYeKvQIDAQAB"
    public static String sign_type = "RSA2";
    //编码格式
    public static String CHARSET = "utf-8";
    //正式环境支付宝网关
    //public static String url = "https://openapi.alipaydev.com/gateway.do";
    public static String url = "https://openapi.alipay.com/gateway.do";
    // 返回格式
    public static final String FORMAT = "JSON";

    /*@Autowired
    private AlipayFeedbackMapper alipayFeedbackMapper;*/

    //实例化客户端
    AlipayClient alipayClient = new DefaultAlipayClient(url, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, sign_type);
    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();


    private Random random;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 调起支付宝
     *
     * @param //                                          对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     * @param //商品的标题/交易标题/订单标题/订单关键字等
     * @param //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * @param
     * @return java.lang.String
     * @methodName aliPay
     * @author WuShunag
     * @date 16:21
     */
    @RequestMapping("/aliPay")
    public String aliPay(@RequestBody AliPayDTO aliPayDTO) throws AlipayApiException {
                if(aliPayDTO.getYearOrMonth() == 1){
                    aliPayDTO.setTime(12*aliPayDTO.getTime());
                }
        /*//第一次办理会员
        Double d = payMapper.selectMember(aliPayDTO.getMemberId(),aliPayDTO.getYear());
        aliPayDTO.setTotalAmount(String.valueOf(d));*/
        AlipayClient alipayClient = new DefaultAlipayClient(url, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, sign_type);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        String card = getCard();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"body\":").append("\"点线圈\"");
        sb.append(",\"subject\":").append("\"点线圈会员\"");
        sb.append(",\"total_amount\":").append("\""+aliPayDTO.getTotalAmount()+"\"");
        sb.append(",\"out_trade_no\":").append("\""+card+"\"");
        sb.append("}");

        System.err.println(sb.toString());
        request.setBizContent(sb.toString());
        request.setNotifyUrl("https://www.hzdxq.cn/pointcoil/notify1");
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()) {
            String qrCode = response.getQrCode();
            payMapper.aliPay(aliPayDTO,card,JSONArray.toJSONString(aliPayDTO.getCityName()),0);
        /*    ModelAndView mav=new ModelAndView("/socket");
            mav.addObject("cid", aliPayDTO.getUserId());*/
            return ResponseUtil.successToClient(qrCode);
        }else {
            return ResponseUtil.errorToClient();
        }
    }

    public  String getCard(){
        Random rand=new Random();//生成随机数
        String cardNnumer="";
        for(int a=0;a<18;a++){
            cardNnumer+=rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }
    /**
     * 接受支付宝异步通知
     */
    @RequestMapping(value = "/notify1")
    @Transactional(rollbackFor = Exception.class)
    public String alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入支付宝支付异步通知");
        Map<String, String> map = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            map.put(name, valueStr);
        }
        System.out.println("支付结果---：" + map.toString());
        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, ALIPAY_PUBLIC_KEY, CHARSET, sign_type);
            log.info("这个布尔是啥=" + signVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("支付结果---：" + map.toString());
        //根据交易流水号查询交易信息
        if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {
            String trad_no = map.get("out_trade_no");
            payMapper.pay(trad_no);
            String userId = payMapper.selectUserId(trad_no);

            Map<String,String> map1 = payMapper.findCityAndTime(trad_no);
            String cityName = map1.get("cityName");
            List<String> list = JSONArray.parseArray(cityName, String.class);
            String time = map1.get("time");
            list.stream().forEach(
                    s -> {
                        Integer i = payMapper.purchased(s,userId);
                        if(i == 0){
                            payMapper.updateMember(userId,s,time);
                        }else {
                            payMapper.updaMemberTime(userId,time,s);
                        }
                    }
            );

            new Thread(
                    () ->{
                        MailUtils instance = MailUtils.getInstance();
                        try {
                            instance.send();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );

           /* payMapper.pay(trad_no);
            String userId = payMapper.selectUserId(trad_no);



            Map<String,String> map1 = payMapper.findCityAndTime(trad_no);
            String cityName = map1.get("cityName");
            List<String> list = JSONArray.parseArray(cityName, String.class);
            String time = map1.get("time");
            list.stream().forEach(
                    s -> {
                       Integer i = payMapper.purchased(s,userId);
                       if(i == 0){
                           payMapper.updateMember(userId,s,time);
                       }else {
                           payMapper.updaMemberTime(userId,time,s);
                       }
                    }
            );*/



            System.err.println("==========================================success====================================");
            try {
                WebSocketServer.sendInfo("支付成功！",userId);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseUtil.errorToClient();
            }
            return "success";
        } else {
            return "failure";
        }
        // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
    }


    /**
     * 是否支付
     *
     * @Author: WuShuang on 2019/12/26  14:41
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("isPay")
    public String isPay(@RequestBody MapCommonDTO mapCommonDTO){


        try {
            Thread.sleep(10000);
            Integer i = payMapper.select(mapCommonDTO);
            Integer z = 0;
            while (true){
                if(z<100000){
                    z++;
                    if(i == 0){
                        System.err.println("000");
                        continue;
                    }else {
                        return ResponseUtil.successToClient();
                    }
                }else {
                    return ResponseUtil.errorToClient();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseUtil.errorToClient();
    }



    //初始化
    public final static String WX_APP_ID = "wx5b9e6e049e1f1d7f"; //公众账号appid（改为自己实际的）
    public final static String APP_SECRET = "ddd91acd24dfc1e782d85aafeaead3a6";
    public final static String MCH_ID = "1587681571"; //商户号（改为自己实际的）
    public final static String API_KEY = "f4dc118f208e7a493e51415b589ff74c"; //（改为自己实际的）key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    //统一下单
    public final static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public final static String NOTIFY_URL = "https://www.hzdxq.cn/pointcoil/notify"; //微信支付回调接口，就是微信那边收到（改为自己实际的）
    public final static String CREATE_IP = "139.129.242.146";//发起支付ip（改为自己实际的）
    /**
     * 微信支付->扫码支付(模式二)->统一下单->微信二维码
     * @return
     */
    @PostMapping("/qrcode")
    public String wxpayPay(HttpServletResponse response,@RequestBody WxpayVo wxpayVo) {
        String urlCode = null;
        // 获取订单信息
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = String.valueOf(PayToolUtil.buildRandom(4));
        String nonce_str = strTime + strRandom;
        String card = getCard();
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", WX_APP_ID);//公众账号ID
        packageParams.put("mch_id", MCH_ID);//商户号
        packageParams.put("nonce_str", nonce_str);//随机字符串
        packageParams.put("body", "资源");  //商品描述
        packageParams.put("out_trade_no", card);//商户订单号
        packageParams.put("total_fee",changeY2F(wxpayVo.getTotalMoney())); //标价金额 订单总金额，单位为分
        packageParams.put("spbill_create_ip", CREATE_IP);//终端IP APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
        packageParams.put("notify_url", NOTIFY_URL);//通知地址 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
        packageParams.put("trade_type", "NATIVE");//交易类型 NATIVE 扫码支付
        // 签名
        String sign = PayToolUtil.createSign("UTF-8", packageParams, API_KEY);
        packageParams.put("sign", sign);

        // 将请求参数转换为xml格式的string
        String requestXML = PayToolUtil.getRequestXml(packageParams);
        logger.info("requestXML:{}", requestXML);

        // 调用微信支付统一下单接口
        String resXml = HttpUtil.postData(UFDODER_URL, requestXML);
        logger.info("resXml: {}", resXml);

        // 解析微信支付结果
        Map map = null;
        try {
            map = XMLUtil4jdom.doXMLParse(resXml);
            logger.info("map: {}", map);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回微信支付的二维码连接
        urlCode = (String) map.get("code_url");
        logger.info("urlCode:{}", urlCode);
        if(StringUtils.isNotEmpty(urlCode)){
            AliPayDTO aliPayDTO = new AliPayDTO();
            aliPayDTO.setUserId(wxpayVo.getUserId());
            aliPayDTO.setTime(Integer.parseInt(wxpayVo.getTime()));
            aliPayDTO.setTotalAmount(String.valueOf(wxpayVo.getTotalMoney()));
            payMapper.aliPay(aliPayDTO,card,JSONArray.toJSONString(wxpayVo.getCityName()),1);
        }

        return ResponseUtil.successToClient(urlCode);

        /*try {
            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "gif";
            Hashtable hints = new Hashtable();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix;
            bitMatrix = new MultiFormatWriter().encode(urlCode, BarcodeFormat.QR_CODE, width, height, hints);
            QRUtil.writeToStream(bitMatrix, format, response.getOutputStream());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static String changeY2F(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        price = Double.valueOf(df.format(price));
        int money = (int)(price * 100);
        return String.valueOf(money);
    }


    /**
     * 微信支付-回调
     * @param request
     * @param response
     */
    @PostMapping("/notify")
    public String wxpayNotify(HttpServletRequest request, HttpServletResponse response) {
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = null;
        try {
            sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s ;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null){
                sb.append(s);
            }
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //解析xml成map
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = XMLUtil4jdom.doXMLParse(sb.toString());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = map.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        //判断签名是否正确
        if(PayToolUtil.isTenpaySign("UTF-8", packageParams, API_KEY)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////

                String out_trade_no = (String)packageParams.get("out_trade_no");



                    String trad_no = map.get("out_trade_no");
                    payMapper.pay(trad_no);
                    String userId = payMapper.selectUserId(trad_no);

                    Map<String,String> map1 = payMapper.findCityAndTime(trad_no);
                    String cityName = map1.get("cityName");
                    List<String> list = JSONArray.parseArray(cityName, String.class);
                    String time = map1.get("time");
                    list.stream().forEach(
                            s -> {
                                Integer i = payMapper.purchased(s,userId);
                                if(i == 0){
                                    payMapper.updateMember(userId,s,time);
                                }else {
                                    payMapper.updaMemberTime(userId,time,s);
                                }
                            }
                    );



                    System.err.println("==========================================success====================================");
                    try {
                        WebSocketServer.sendInfo("支付成功！",userId);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return ResponseUtil.errorToClient();
                    }





                logger.info("支付成功");

                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return ("fail");
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            try {
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else{
            logger.info("通知签名验证失败");
        }
        return ("success");
    }






    private String body;
    private String subject;
    private String totalAmount;
    private String outTradeNo;
    public static void main(String[] args) throws AlipayApiException {

    }
}