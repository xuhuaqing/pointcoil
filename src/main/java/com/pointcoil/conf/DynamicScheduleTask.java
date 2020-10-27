package com.pointcoil.conf;

import com.pointcoil.dto.map.TaskDTO;
import com.pointcoil.mapper.CronMapper;
import com.pointcoil.mapper.MapUserMapper;
import com.pointcoil.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author:WuShuang
 * @date:2019/6/12
 * @ver:1.0
 **/
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {


    @Autowired
    private RedisUtil redisUtil;


    private long time =   30 * 60 * 1000;
    private long sendTime =   5 * 60 * 1000;

    @Autowired      //注入mapper
    @SuppressWarnings("all") //抑制所有类型的警告
     CronMapper cronMapper;

   /* @Autowired
    @SuppressWarnings("all")
    TblOrderMapper tblOrderMapper;

    OrderDeleteDTO orderDeleteDTO =  new OrderDeleteDTO();*/

    @Autowired
    MapUserMapper mapUserMapper;

    /**
     * 执行定时任务.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {


        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () ->{

                    /**
                     * 每天晚上0点 扫描数据库 去判断数据库时间是否 小于等于当前时间 就 把会员停掉 并且删除redis 当中的token 子账号也要删掉时间
                     *
                     * 会员等级 归零
                     *
                     */
                    long l = System.currentTimeMillis();
                    List<TaskDTO> list = mapUserMapper.selectAllUser();
                   list.forEach(
                           s -> {
                               long time = Timestamp.valueOf(s.getEndTime()).getTime();
                                if(l>=time){
                                    mapUserMapper.deleteMembers(s.getId());
                                    redisUtil.remove(s.getId());
                                }
                           }
                   );


                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

}