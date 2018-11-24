package com.freedom.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 点对点式消息队列（Queue）
 * 消息消费者
 * Title: Consumer.java
 * Copyright: Copyright (c)2018
 * @author: BlackDragon
 * @date: 2018年11月24日
 */
public class Consumer {

	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		//1.创建ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"zlj",//ActiveMQConnectionFactory.DEFAULT_USER,
				"12345",//ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		//2.创建一个Connection并开启
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.创建Session会话，用来接收消息，通过参数可以设置，是否启用事务、消息签收模式
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		//4.创建Destination对象，在点对点模式中，该对象被称为Queue；在发布订阅模式中，该对象被称为Topic
		Destination destination = session.createQueue("queue1");
		//5.创建消息的消费者
		MessageConsumer messageConsumer = session.createConsumer(destination);
		//6.消费者从消息中间件的Queue获取消息
		while (true) {
			TextMessage textMessage = (TextMessage) messageConsumer.receive();
			if (null == textMessage) {
				break;
			}
			System.out.println("消费者接收到的内容:" + textMessage.getText());
		}
		//7.释放Connection
		if (null != connection) {
			connection.close();
		}
		
	}

}
