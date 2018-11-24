package com.freedom.mq.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ReceiveMessage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//初始化配置信息
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "127.0.0.1:9092");
		//指定消费者组(必须)
		properties.put("group.id", "hello-kafka");
		//数据key的反序列化处理类
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//数据value的反序列化处理类
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//创建消费者
		@SuppressWarnings("resource")
		Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		//订阅hello-topic的消息
		consumer.subscribe(Arrays.asList("hello-topic"));
		//读取消息
		while (true) {
			@SuppressWarnings("deprecation")
			ConsumerRecords<String, String> records = consumer.poll(50);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("消息的Key为:" + record.key() + ",消息的值为:" + record.value());
			}
		}
	}

}
