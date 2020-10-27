package com.pointcoil.conf;

import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

/**
 * Created by WuShuang on 2020/4/11.
 */
@Component
@EnableJms
public class ActiveMQConnectionConfig {



    @Value("${myqueue}")
    private String myQueue;


    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }





/*


    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        redeliveryPolicy.setDestination(activeMQQueue());
        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQQueue activeMQQueue(){
        return new ActiveMQQueue("queue_SplitExcelPath");
    }

    @Bean(name = "listenerContainer")
    public DefaultMessageListenerContainer defaultMessageListenerContainer(){
        DefaultMessageListenerContainer defaultJmsListenerContainerFactory = new DefaultMessageListenerContainer();
        defaultJmsListenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        defaultJmsListenerContainerFactory.setDestination(activeMQQueue());
        defaultJmsListenerContainerFactory.setSessionTransacted(true);
        defaultJmsListenerContainerFactory.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.err.println("1111");
            }
        });
        return defaultJmsListenerContainerFactory;
    }





    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        "admin",
                        "admin",
                        "tcp://47.104.98.61:61616");
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
        cachingConnectionFactory.setSessionCacheSize(100);
        return cachingConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory,Queue queue){
        JmsTemplate jmsTemplate=new JmsTemplate();
        jmsTemplate.setDeliveryMode(2);//进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式
        return jmsTemplate;
    }


    //定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
    @Bean(name = "jmsListenerContainerFactory")
    public JmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        //设置连接数
        factory.setConcurrency("6-15");
        //重连间隔时间
        factory.setRecoveryInterval(100L);
        factory.setSessionAcknowledgeMode(4);
        return factory;
    }
*/


}

