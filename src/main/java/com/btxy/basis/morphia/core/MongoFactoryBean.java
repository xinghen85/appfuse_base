package com.btxy.basis.morphia.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoFactoryBean extends AbstractFactoryBean<MongoClient> {

	public void setServerStrings(String[] serverStrings) {
		this.serverStrings = serverStrings;
	}

	// 表示服务器列表(主从复制或者分片)的字符串数组
	private String[] serverStrings;

	@Override
	public Class<?> getObjectType() {
		return Mongo.class;
	}

	@Override
	protected MongoClient createInstance() throws Exception {
		MongoClient mongo = initMongo();
		return mongo;
	}

	/**
	 * 初始化mongo实例
	 * 
	 * @return
	 * @throws Exception
	 */
	private MongoClient initMongo() {
		List<ServerAddress> serverList = getServerList();
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoClient mongo = new MongoClient(serverList, credentialsList);
		return mongo;
	}

	/**
	 * 根据服务器字符串列表，解析出服务器对象列表
	 * <p>
	 * 
	 * @Title: getServerList
	 *         </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<ServerAddress> getServerList(){
		List<ServerAddress> serverList = new ArrayList<ServerAddress>();
		try {
			for (String serverString : serverStrings) {
				String[] temp = serverString.split(":");
				String host = temp[0];
				if (temp.length > 2) {
					throw new IllegalArgumentException("Invalid server address string: " + serverString);
				}
				if (temp.length == 2) {
					serverList.add(new ServerAddress(host, Integer.parseInt(temp[1])));
				} else {
					serverList.add(new ServerAddress(host));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while converting serverString to ServerAddressList", e);
		}
		if (serverList.size() == 0) {
			throw new RuntimeException("必须配置mongodb的server");
		}
		return serverList;
	}

	/* ------------------- setters --------------------- */

}
