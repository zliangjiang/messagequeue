package com.freedom.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//1.创建ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"zlj",//ActiveMQConnectionFactory.DEFAULT_USER,
				"12345",//ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		//2.创建一个Connection并开启
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.创建Session会话,用来接收消息，通过参数可以设置，是否启动事务。消息签收模式
		Session  session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		//4。创建Destination对象。在点对点模式中，该对象被为Queue;在发布订阅模式中，该对象被称为Topic
		Destination destination = session.createQueue("queue1");
		//5.创建消息的生产者
		MessageProducer messageProducer = session.createProducer(destination);
		//6.设置生产者的消息持久化与非持久化特性
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//7.选择需要的JMS消息格式，创建并发送消息，此处选择的是TextMessage字符串对象
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("生产者"+"activemq消息测试2");
		messageProducer.send(textMessage);
		//8.释放Connection
		if (null != connection) {
			connection.close();
		}
	}

}
