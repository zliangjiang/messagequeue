package com.freedom.mq.rocketmq;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class Producer {

	public static void main(String[] args) throws MQClientException, Exception{
		// TODO Auto-generated method stub
		//声明并初始化一个producer
		//需要一个producer group名字作为构造方法的参数,这里为producer1
		DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		//设置NameServer地址,此处应改为实际NameServer地址,多个地址之间用;分隔
		//NameServer的地址须有，但是也可以通过环境变量的方式设置,不一定非得写死在代码里
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("Producer");
		//调用start()方法启动一个producer实例
		producer.start();
		//发送10条消息到Topic为TopicTest,tag为TagA,消息内容为"Hello RocketMQ"拼接上i的值
		for (int i=0; i<10; i++) {
			try {
					{
						Message msg = new Message("TopicTest1",//topic
								"TagA",//tag
								("Hello RocketMQ " + i).getBytes());
						SendResult sendResult = producer.send(msg);
						System.out.println(sendResult);
					}
					{
						Message msg = new Message("TopicTest2",
								"TagB",
								("Hello MetaQ").getBytes());
						SendResult sendResult = producer.send(msg);
						System.out.println(sendResult);
					}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		//应用退出时，要调用shutdown来清理资源,关闭网络连接，从MetaQ服务器上注销自己
		producer.shutdown();
		
	}

}
