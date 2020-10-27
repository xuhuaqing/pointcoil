package com.pointcoil.mq;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by WuShuang on 2020/4/10.
 */
public class TestMQ {

    private static final String QUEUE_NAME ="queue01";

    /**
     *  生产者
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        //1、获取mq连接工程
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://47.104.98.61:61616"
        );
        //2.创建链接
        Connection connection = activeMQConnectionFactory.createConnection();
        //3、启动连接
        connection.start();
        //4、创建会话工厂  设置成默认的
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //5.创建队列
        Destination destination = session.createQueue(QUEUE_NAME);
        //6. 创建消息的生产者 得到生产者
        MessageProducer producer = session.createProducer(destination);
        //7.通过使用MessageProducer 生成 消息
        for (int i = 0; i < 3 ; i++) {
            TextMessage textMessage = session.createTextMessage("msg----------" + i);
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();
        System.err.println("发布消息到mq完成！");
    }


  static class TestMQ02{
       public static void main(String[] args) throws JMSException {
           ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                   ActiveMQConnection.DEFAULT_PASSWORD, "tcp://47.104.98.61:61616");
           Connection connection = activeMQConnectionFactory.createConnection();
           connection.start();
           Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
           Destination destination = session.createQueue(QUEUE_NAME);
           MessageConsumer consumer = session.createConsumer(destination);
           while (true){
               TextMessage textMessage = (TextMessage)consumer.receive();
               if (textMessage != null) {
                   System.out.println("消费者获取到消息:" + textMessage.getText());
                   textMessage.acknowledge();
               }else {
                   break;
               }
           }
           System.err.println("消息接收完成");
       }
   }


}
