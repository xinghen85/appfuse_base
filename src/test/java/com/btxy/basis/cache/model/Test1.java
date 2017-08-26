package com.btxy.basis.cache.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;

public class Test1  {
	//	1:63
	//	2:45
	//lock 3:57 15
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		BlockingQueue queue = new LinkedBlockingQueue();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 1,TimeUnit.DAYS, queue);
		for(int i=0;i<10000;i++){
			executor.execute(new Runnable() {
				public void run() {
					int i=(int) (Math.random()*100);
					if(i>5){
						//读
						long time=System.currentTimeMillis();
						//System.out.println("======读取操作======["+time+"]");
						AuthPrivilegeInfoCache.getInstance().getList().size();
					}else{
						//写
						//System.out.println("=====刷新操作======");
						AuthPrivilegeInfoCache.refresh(AuthPrivilegeInfoCache.class);
					}
				}
			});
		}
		while(executor.getCompletedTaskCount()<10000){
			System.out.println("====ActiveCount:"+executor.getActiveCount()+",CompletedTaskCount:"+executor.getCompletedTaskCount());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("===运行结束,总计运行时间："+(end-start)/1000 +"s,"+AuthPrivilegeInfoCache.getInstance().print());
		System.exit(0);
	}

	

}
