package com.pointcoil.mq;

import com.pointcoil.util.SplitExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by WuShuang on 2020/4/10.
 */

@RestController
public class Producer {

    //注入JmsTemplate
    @Autowired
    private JmsTemplate jmsTemplate;


    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("producerMsg")
    public String producerMsg(@RequestParam("file") MultipartFile file,String cityName){
        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
        String path = "/code/point2/pointexcel";

        File file3 = new File(path + File.separator + headNewName);

        try {
            file.transferTo(file3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String absolutePath = file3.getAbsolutePath();
        //System.err.println("上传路径："+absolutePath);
        //每批导入excel总行数
        int FileMaxNum = 6000;
        List<String> splitExcelPath = SplitExcelUtil.splitExcels(path, FileMaxNum);
        //上传完成 删除原文件
        //前期工作：清空分割存储文件夹
        File[] splitFiles=new File(path).listFiles();
        if(null!=splitFiles){
            for(File f2: splitFiles){
                f2.delete();
            }
            System.err.println("源文件删除");
        }
        for (String s: splitExcelPath
        ) {
            jmsTemplate.send("queue_SplitExcelPath", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage message = session.createMapMessage();
                    message.setString("splitExcelPath", s);
                    message.setString("cityName", cityName);
                    return message;
                }
            });
        }
        splitExcelPath.clear();
        return "ok";
    }



}

