package com.freedom.mq.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {

	public static void main(String[] args) throws MQClientException {
		// TODO Auto-generated method stub
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setInstanceName("Consumer");
		
		consumer.subscribe("TopicTest1", "TagA||TagC");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(
					List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.err.println(Thread.currentThread().getName() + " Receive new Messages: " + msgs.size());
				MessageExt msg = msgs.get(0);
				if (msg.getTopic().equals("TopicTest1")) {
					 // 执行TopicTest1的消费逻辑
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        // 执行TagA的消费
                        System.err.println(new String(msg.getBody()));
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagC")) {
                        // 执行TagC的消费
                        System.err.println("TagC:=====");
                        System.err.println(new String(msg.getBody()));
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagD")) {
                        // 执行TagD的消费
                        System.err.println("TagD:=====");
                        System.err.println(new String(msg.getBody()));
                    }
                } else if (msg.getTopic().equals("TopicTest2")) {
                    System.err.println("TopicTest2:=====");
                    System.err.println(new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		
		consumer.start();
		System.err.println("Consumer Started.");

	}

}
