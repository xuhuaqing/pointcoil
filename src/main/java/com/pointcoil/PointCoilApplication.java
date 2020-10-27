package com.pointcoil;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * @Author: WuShuang
 * @Version: 1.0.0
 **/
@SpringBootApplication
@MapperScan("com.pointcoil.mapper")
@EnableMongoRepositories("com.pointcoil.mongo")
public class PointCoilApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(PointCoilApplication.class, args);
       // MybatisRedisCache.getInstance();
    }




}
