package com.freedom.zookeeper;

import org.apache.zookeeper.CreateMode;

import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;

public class ZkClientTest {
	static String servers = "localhost:2181/root/apps";
	static String rootPath = "root/apps";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZkClient zkClient = new ZkClient(servers, 5000);
		//zkClient.createPersistent("root/apps/test/rest", true);
		//System.out.println(zkClient.getChildren("/root"));
		zkClient.subscribeDataChanges("/name1", new IZkDataListener() {
			/**
			 * 数据被删除时触发
			 */
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("节点数据被删除: " + dataPath);
			}
			
			/**
			 * 数据被修改时触发
			 */
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("节点数据被修改: " + dataPath + ",获取数据: " + data);
			}
		});
		try {
			User user = new User();
			user.setCode("200");
			user.setName("zlj");
			zkClient.create("/name1", user, CreateMode.EPHEMERAL);
			//User data = zkClient.readData("/name1");
			Thread.sleep(1000);
			
			User user2 = new User();
			user2.setCode("200");
			user2.setName("demo");
			zkClient.writeData("/name1", user2);
			Thread.sleep(1000);
			zkClient.delete("/name1");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
