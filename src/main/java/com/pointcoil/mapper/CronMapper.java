package com.pointcoil.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @Author: Wushuang
 * @Date: 2019-04-23 14:21
 * @Version: 1.0.0
 **/
public interface CronMapper {
    @Select("select cron from cron limit 1")
    String getCron();

    @Select("select cron from cron where cron_id= 2 ")
    String getCron2();
}