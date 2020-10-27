package com.pointcoil.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author:WuShuang
 * @date:2019/5/8
 * @ver:1.0
 **/
public class SendMsg {

    /**
     * 获取IAcsClient对象
     *
     * @return
     */
    public static IAcsClient initClient() throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient需要的几个参数
        // 短信API产品名称
        final String product = "Dysmsapi";
        // 短信API产品域名
        final String domain = "dysmsapi.aliyuncs.com";
        // 秘钥key和secret
        final String appkey = "LTAI4FoHDWLXNE1JcqVKmE5z";
        final String appSecret = "DRzKvQQEAdVUmDUaQUfFvXrjPSsaIq";
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", appkey, appSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }


    /**
     * 获取SMS_72780019短信模板对应的请求
     *
     * @return
     */
    public static SendSmsRequest getSMS164815153Message(String phone,Integer code) {
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("杭州点线圈");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_177520253");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");
        return request;
    }

    /**
     * 获取SMS_72780019短信模板对应的请求
     *
     * @return
     */
    public static SendSmsRequest getSMS_195722492(String phone,String time) {
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("点线圈");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_196640022");
        request.setTemplateParam("{\"mtname\":\""+phone+"\",\"submittime\":\""+time+"\"}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");
        return request;
    }

}
