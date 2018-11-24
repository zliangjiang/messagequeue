package com.freedom.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendMessage {
	//声明一个队列的名称
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		//1.设置主机,其实默认用的就是本机,端口5672,不用设置
		factory.setHost("localhost");
		//2.创建连接
		Connection connection = factory.newConnection();
		//3.创建通道
		Channel channel = connection.createChannel();
		//4.将消息放到队列里
		//2,3,4,5参数的意思，是否是持久的;是否是独立的;是否自动删除;队列参数
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello World!";
		//5.发布消息到RabbitMQ服务器
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [X] Sent '" + message + "'");
		//6.发送完毕之后，关闭连接
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
		
	}
}
