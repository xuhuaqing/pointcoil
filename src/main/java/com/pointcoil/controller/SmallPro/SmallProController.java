package com.pointcoil.controller.SmallPro;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.pointcoil.conf.DataValidation;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.dto.map.ISPhoneDTO;
import com.pointcoil.entity.UserEntity;
import com.pointcoil.mapper.SmallProMapper;
import com.pointcoil.service.SmallPro.SmallProService;
import com.pointcoil.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static com.pointcoil.constant.Constants.REMINDTIME;
import static com.pointcoil.constant.MsgConstant.MSG_000004;
import static com.pointcoil.constant.MsgConstant.MSG_001002;

/**
 * Created by WuShuang on 2019/11/30.
 */
@RestController
@RequestMapping("/api/smallpro/")
@SuppressWarnings("ALL")
@Slf4j
public class SmallProController {

    @Autowired
    private SmallProService smallProService;
    @Autowired
    private SmallProMapper smallProMapper;
    @Autowired
    private PointCoilProperties pointCoilPropertiesl;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 获取微信openID
     *
     * @param user
    * @param bindingResult
     * @return java.lang.String
     * @methodName signWx
     * @author WuShunag
     * @date 11:28
     */
    @PostMapping("signWx")
    public String signWx(@Valid @RequestBody UserEntity user) {
        log.info("come in signWx:{}", user);
        /*
         * 发送request请求
         * */
        RestTemplate restTemplate = new RestTemplate();
        String params = "appid=" + "wx6d93cbc58407ef48" + "&secret=" + "96333b1335e9f4e829663bd90b1694d0" + "&js_code=" + user.getCode();
        /*
         * 微信接口 用于查询oponid
         * */
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + params;
        String response = restTemplate.getForObject(url, String.class);
        log.info("response:" + response);
        JSON json = JSONUtil.parse(response);
        String openId = StrUtil.toString(json.getByPath("openid"));
        if (StrUtil.isBlank(openId)) {
           List<Map<String , String>> map = smallProMapper.findAppId();
            for (int i = 0; i < map.size(); i++) {
                Map<String, String> map1 = map.get(i);
                params = "appid=" + map1.get("appid").trim().toString() + "&secret=" + map1.get("secret").trim().toString() + "&js_code=" + user.getCode();
                url = "https://api.weixin.qq.com/sns/jscode2session?" + params;
                response = restTemplate.getForObject(url, String.class);
                json = JSONUtil.parse(response);
                openId = StrUtil.toString(json.getByPath("openid"));
                if (!StrUtil.isBlank(openId)){
                    break;
                }
            }
        }
        return ResponseUtil.successToClient(json);
    }



    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @return java.lang.String
     * @methodName sendMessage
     * @author WuShunag
     * @date 18:16
     */
    @PostMapping("sendMessage")
    public String sendMessage(@RequestBody @Valid ISPhoneDTO isPhoneDTO, BindingResult result) throws ClientException {
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        String phoneNumber = isPhoneDTO.getPhoneNumber();
       /*
        String phoneSearch = pointCoilPropertiesl.getRedis().getPhoneSearch();
        String s = redisUtil.hGet(phoneSearch, phoneNumber);
        if(StringUtils.isEmpty(s)){
            return ResponseUtil.toClient(MSG_001002);
        }*/
        String checkCode = GenerateRandomCode.generNumCode(6);
        // 初始化client对象
        IAcsClient client = SendMsg.initClient();
        // 短信模板请求对象
        SendSmsRequest request1 =SendMsg.getSMS164815153Message(phoneNumber,Integer.valueOf(checkCode));
        // 根据短信模板code来获取不同的短信模板请求对象
        // 发送短信
        SendSmsResponse response = client.getAcsResponse(request1);
        System.out.println("_____________发送SMS_72780019短信收到的响应信息_______________");
        System.out.println("请求的ID:" + response.getRequestId());
        System.out.println("请求的状态码:" + response.getCode());
        System.out.println("请求的状态码描述:" + response.getMessage());
        System.out.println("请求的回执ID:" + response.getBizId());
        redisUtil.set("sendMsgSmallPro"+phoneNumber,checkCode, REMINDTIME);
        return  ResponseUtil.successToClient();
    }


    /**
     *  保存个人信息
     *
     * @Author: WuShuang on 2019/11/30  13:47
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("preservationUser")
    public String preservationUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult){
        String o1 = (String) redisUtil.get("sendMsgSmallPro" + user.getPhoneNumber());
        if(StringUtils.isEmpty(o1)){
            return ResponseUtil.errorToClient(MSG_000004);
        }
        if(!o1.equals(user.getCode())){
            return ResponseUtil.errorMsgToClient(MSG_000004);
        }
        return smallProService.preservationUser(user);
    }

    /**
     * 查看商圈报告
     *
     * @Author: WuShuang on 2019/11/26  10:21
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showBusinessReport")
    public String showBusinessReport(@Valid @RequestBody BusinessZoneDTO.BusinessReportApi businessReport, BindingResult result){
        log.info("入参：{}",businessReport);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }

        return smallProService.showBusinessReport(businessReport);
    }

    /**
     * 我关注的商圈
     *
     * @Author: WuShuang on 2019/12/2  9:41
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showMyFollowBusiness")
    public String showMyFollowBusiness(@Valid @RequestBody UserEntity userEntity, BindingResult result){
        log.info("入参：{}",userEntity);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        return smallProService.showMyFollowBusiness(userEntity);
    }


    /**
     *  查看商圈关注的人
     *
     * @Author: WuShuang on 2019/12/2  13:20
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("businessFollowPeo")
    public String businessFollowPeo(@Valid @RequestBody BusinessZoneDTO.BusinessReportApi businessReport, BindingResult result){
        log.info("入参：{}",businessReport);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        return smallProService.businessFollowPeo(businessReport);
    }
}
