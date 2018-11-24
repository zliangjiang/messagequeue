package com.freedom.mq.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SendMessage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//初始化配置信息
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "127.0.0.1:9092");
		//数据Key的序列化处理类
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		//数据value的序列化处理类
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		//创建生产者
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		//生成一条新的记录，前一个参数是Topic的名称，后面两个对应数据的键值
		ProducerRecord<String, String> record = new ProducerRecord<String, String>("hello-topic","username","spirit");
		//发送记录
		producer.send(record);
		//关闭消息生产者
		producer.close();
	}

}
