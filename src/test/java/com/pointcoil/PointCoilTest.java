package com.pointcoil;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.hp.hpl.sparta.xpath.Step;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.util.Base64Util;
import com.pointcoil.util.MailUtils;
import com.pointcoil.util.SendMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WuShuang on 2019/11/1.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PointCoilTest {

    @Test
    public  void sz() {

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        // 初始化client对象
        IAcsClient client = null;
        try {
            client = SendMsg.initClient();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        // 短信模板请求对象
        SendSmsRequest request1 =SendMsg.getSMS_195722492("13989838220",dateFormat.format(date));
        // 根据短信模板code来获取不同的短信模板请求对象
        // 发送短信
        SendSmsResponse response = null;
        try {
            response = client.getAcsResponse(request1);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("请求的ID:" + response.getRequestId());
        System.out.println("请求的状态码:" + response.getCode());
        System.out.println("请求的状态码描述:" + response.getMessage());
        System.out.println("请求的回执ID:" + response.getBizId());
    }

  /*  public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    @Test
    public  void getCard(){
      *//*  boolean integer = isInteger(1111 + "");
        System.err.println(integer);*//*

        MailUtils instance = MailUtils.getInstance();
        try {
            instance.send("961338825@qq.com","dianxianquan");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void stes(){
        String [] s = new String[10];
        for (int i = 0; i <s.length ; i++) {
            s[i] = i+"";
        }

        String string = JSONArray.toJSONString(s);
        System.err.println(string);

    }

    @Test
    public void set(){
*//*
        new Thread(
                () -> {
                    try {
                        Thread.sleep(10000);
                        System.err.println("iniininini");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        System.err.println("wq");


        new Thread(new Runnable() { @Override public void run() {

        } }).start();*//*
    }

    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }

*/


}
