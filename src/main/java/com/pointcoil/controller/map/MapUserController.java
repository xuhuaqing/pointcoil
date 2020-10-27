package com.pointcoil.controller.map;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.pointcoil.conf.DataValidation;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.*;
import com.pointcoil.service.map.MapUserService;
import com.pointcoil.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.pointcoil.constant.Constants.*;
import static com.pointcoil.constant.MsgConstant.*;

/**
 * Created by WuShuang on 2019/11/1.
 */
@RestController
@RequestMapping("/api/mapUser/")
@SuppressWarnings("ALL")
@Slf4j
public class MapUserController {

    @Autowired
    private MapUserService mapService;

   @Autowired
   private RedisUtil redisUtil;

   @Autowired
   private PointCoilProperties pointCoilPropertiesl;

   @Autowired
   private TokenCheck tokenCheck;

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
      String phoneSearch = pointCoilPropertiesl.getRedis().getPhoneSearch();
      String s = redisUtil.hGet(phoneSearch, phoneNumber);
      if(StringUtils.isEmpty(s)){
         return ResponseUtil.toClient(MSG_001002);
      }
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
      redisUtil.set("sendMsg"+phoneNumber,checkCode, REMINDTIME);
      return  ResponseUtil.successToClient();
   }


   /**
    *  判断免登录
    *
    * @param
    * @return java.lang.String
    * @methodName isLogin
    * @author WuShunag
    * @date 0:27
    */
   @PostMapping("isLogin")
   public String isLogin(@RequestBody @Valid ISPhoneDTO isPhoneDTO){
       cn.hutool.json.JSONObject jsonObject1 = new cn.hutool.json.JSONObject();
       Object o = redisUtil.get("11isLogin=" + isPhoneDTO.getPhoneNumber());
       if(o == null){
           jsonObject1.put("flag","0");
           return ResponseUtil.successToClient(jsonObject1);
       }else {
            return o.toString();
       }
   }

   /**
    *注册用户
    *
    * @Author: WuShuang on 2019/11/1  16:25
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("register")
   public String register(@RequestBody @Valid RegisterDTO registerDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      return mapService.register(registerDTO);
   }




   /**登陆
    *
    * @Author: WuShuang on 2019/11/2  13:10
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("login")
   public String login(@RequestBody @Valid ISPhoneDTO.LoginDTO loginDTO ,BindingResult result){

      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      String o1 = (String) redisUtil.get("sendMsg" + loginDTO.getPhoneNumber());
      if(StringUtils.isEmpty(o1)){
         return ResponseUtil.errorToClient(MSG_000004);
      }
      if(!o1.equals(loginDTO.getCode())){
         return ResponseUtil.errorToClient(MSG_000004);
      }
      MapCommonDTO mapCommonDTO = mapService.login(loginDTO);
      String token = pointCoilPropertiesl.getRedis().getLoginHomeKey()+Md5Util.getMD5(UUID.randomUUID().toString().replace("-", ""));
      Object o = redisUtil.get(mapCommonDTO.getUserId());
      Map<String, Object> map = new HashMap<>(2);
      if(null != o){

         if(loginDTO.getPhoneNumber().equals("13989838220")){
            redisUtil.set(mapCommonDTO.getUserId(),o.toString(),EFFECTIVE_TIME);
            map.put("token",o.toString());
         }else if(loginDTO.getPhoneNumber().equals("17612714215")){
            redisUtil.set(mapCommonDTO.getUserId(),o.toString(),EFFECTIVE_TIME);
            map.put("token",o.toString());
         }else {
            //redisUtil.remove(mapCommonDTO.getUserId());
            redisUtil.set(mapCommonDTO.getUserId(),o.toString(),REDIS_EXPIRE_TIME);
            map.put("token",o.toString());
         }


      }else {

         if(loginDTO.getPhoneNumber().equals("13989838220")){
            redisUtil.set(mapCommonDTO.getUserId(),token,EFFECTIVE_TIME);
            map.put("token",token);
         }else if(loginDTO.getPhoneNumber().equals("17612714215")){
            redisUtil.set(mapCommonDTO.getUserId(),token,EFFECTIVE_TIME);
            map.put("token",token);
         }else {
            map.put("token",token);
            redisUtil.set(mapCommonDTO.getUserId(),token,REDIS_EXPIRE_TIME);
         }

      }
      map.put("userId",mapCommonDTO.getUserId());
      map.put("userName",loginDTO.getPhoneNumber());
      map.put("memberLevel",mapCommonDTO.getToken());
      Integer token2 =Integer.valueOf(mapCommonDTO.getToken());
      if(token2>0){
         TimeDTO localDateTimeMap = mapService.selectTime(mapCommonDTO.getUserId());
         map.put("starTime",localDateTimeMap.getStarTime().split(" ")[0]);
         map.put("endTime",localDateTimeMap.getEndTime().split(" ")[0]);
      }else {
         map.put("starTime","");
         map.put("endTime","");
      }

      String isAccountSub = pointCoilPropertiesl.getRedis().getIsAccountSub();

      //判断子账号
      String s = redisUtil.hGet(isAccountSub, mapCommonDTO.getUserId());
      if(StringUtils.isEmpty(s)){
         map.put("isSubAccount","0");
         map.put("sonId","");
      }else {
         map.put("isSubAccount","1");
         String parentId =  mapService.selectParentId(mapCommonDTO.getUserId());
         Object o2 = redisUtil.get(parentId);
         if(null == o2){
            String token1 = Md5Util.getMD5(pointCoilPropertiesl.getRedis().getLoginHomeKey()+UUID.randomUUID().toString().replace("-", ""));
            map.put("token",token1);
            redisUtil.set(parentId,token1,REDIS_EXPIRE_TIME);
         }else {
            map.put("token",o2.toString());
            redisUtil.set(parentId,o2.toString(),REDIS_EXPIRE_TIME);
         }
         map.put("userId",parentId);
         //子账号ID
         map.put("sonId",mapCommonDTO.getUserId());
      }

      if(loginDTO.getPhoneNumber().equals("13989838220")){
         map.put("admin","1");
         map.put("flag","1");
          redisUtil.set("11isLogin=" + "13989838220",ResponseUtil.successToClient(map),EFFECTIVE_TIME);
      }else if(loginDTO.getPhoneNumber().equals("15715868696"))
      {
         map.put("admin","1");
      }else if(loginDTO.getPhoneNumber().equals("17612714215"))
      {
          map.put("flag","1");
          map.put("admin","1");
          redisUtil.set("11isLogin=" + "17612714215",ResponseUtil.successToClient(map),EFFECTIVE_TIME);
      }else if (loginDTO.getPhoneNumber().equals("18844996694")){
         map.put("admin","1");
      }
      return ResponseUtil.successToClient(map);
   }

   /**
    *会员中心
    *
    * @Author: WuShuang on 2019/11/4  20:35
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("memberInit")
   public String memberInit(@RequestBody @Valid MapCommonDTO mapCommonDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.memberInit();
   }



   /**
    * 我的商圈
    *
    * @Author: WuShuang on 2019/11/18  10:35
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("myBusinessZone")
   public String myBusinessZone(@RequestBody @Valid MapPageDTO mapCommonDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.myBusinessZone(mapCommonDTO);
   }


   /**
    *创建子账号
    *
    * @Author: WuShuang on 2019/11/18  14:37
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("createSubAccount")
   public String createSubAccount(@RequestBody @Valid MapSubAccountDTO mapSubAccountDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapSubAccountDTO.getUserId(),mapSubAccountDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.createSubAccount(mapSubAccountDTO);
   }


   /**
    *我的子账号
    *
    * @Author: WuShuang on 2019/11/18  15:49
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("mySubAccount")
   public String mySubAccount(@RequestBody @Valid MapPageDTO mapSubAccountDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapSubAccountDTO.getUserId(),mapSubAccountDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.mySubAccount(mapSubAccountDTO);
   }


   /**
    * 删除子账号
    *
    * @Author: WuShuang on 2019/11/18  16:21
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("deleteSubAccount")
   public String deleteSubAccount(@RequestBody @Valid MapPageDTO.SubAccountDTO subAccountDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(subAccountDTO.getUserId(),subAccountDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.deleteSubAccount(subAccountDTO);
   }


   /**
    *定制服务
    *
    * @Author: WuShuang on 2019/11/18  17:02
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("customizedService")
   public String customizedService(@RequestBody @Valid CustomizedServiceDTO customizedServiceDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(customizedServiceDTO.getUserId(),customizedServiceDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.customizedService(customizedServiceDTO);
   }



   /**
    * 我的关注
    *
    * @Author: WuShuang on 2019/12/2  16:24
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("myConcern")
   public String myConcern(@RequestBody @Valid MapPageDTO mapCommonDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.myConcern(mapCommonDTO);
   }


   /**
    * 饼形图  男女
    *
    * @Author: WuShuang on 2019/12/3  9:35
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("pieChartBySix")
   public String pieChartBySix(@RequestBody @Valid BrandDTO.BrandId mapCommonDTO, BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.pieChartBySix(mapCommonDTO);
   }



   /**
    * 用户关注品牌的地理分布图
    *
    * @Author: WuShuang on 2019/12/3  15:57
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("distributionMap")
   public String distributionMap(@RequestBody @Valid BrandDTO.BrandId mapCommonDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.distributionMap(mapCommonDTO);
   }

   /**
    * 支付二维码
    *
    * @Author: WuShuang on 2019/12/23  13:17
    * @param: []
    * @return: java.lang.String
    * @Description:
    */
   @PostMapping("qrCode")
   public String qrCode(@RequestBody @Valid BrandDTO.QR mapCommonDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return ResponseUtil.successToClient(QRCode.qrCode("http://www.hzdxq.cn/pointcoil/aliPay?totalAmount="+mapCommonDTO.getPrice()));
   }


   /**
    * 城市初始化
    *
    * @param
    * @return java.lang.String
    * @methodName memberCityInit
    * @author WuShunag
    * @date 22:06
    */
   @PostMapping("memberCityInit")
   public String memberCityInit(@RequestBody @Valid MapCommonDTO mapCommonDTO,BindingResult result){
      if (result.hasErrors()) {
         return DataValidation.validation(result);
      }
      Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
      if (null == redisData) {
         return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
      }
      return mapService.memberCityInit(mapCommonDTO);
   }
}
