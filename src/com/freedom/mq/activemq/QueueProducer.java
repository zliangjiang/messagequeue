package com.freedom.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 点对点式消息队列（Queue）
 * 消息生产者
 * Title: QueueProducer.java
 * Copyright: Copyright (c)2018
 * @author: BlackDragon
 * @date: 2018年11月24日
 */
public class QueueProducer {

    public static void main(String[] args) {
        //连接信息设置
        String username = "zlj";
        String password = "12345";
        String brokerURL = "failover://tcp://localhost:61616";
        //连接工厂
        ConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination = null;
        //消息生产者
        MessageProducer messageProducer = null;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为QueueTest的消息队列
            destination = session.createQueue("QueueTest");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            TextMessage message = null;
            for (int i=0; i<10; i++) {
                //创建要发送的文本信息
                message = session.createTextMessage("Queue消息测试" +(i+1));
                //通过消息生产者发出消息 
                messageProducer.send(message);
                System.out.println("发送成功：" + message.getText());
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}