package com.btxy.basis.cache.cfg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test2  {
	//	1:63
	//	2:45
	//lock 3:57 15
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		BlockingQueue queue = new LinkedBlockingQueue();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 1,TimeUnit.DAYS, queue);
		for(Integer j=0;j<2;j++){
			final Long libraryid=j.longValue();
			for(int i=0;i<5000;i++){
				executor.execute(new Runnable() {
					public void run() {
						int i=(int) (Math.random()*100);
						if(i>5){
							//读
							long time=System.currentTimeMillis();
							//System.out.println("======读取操作======["+time+"]");
							CfgFixedPropertyDefineCache.getInstance(libraryid).getList().size();
						}else{
							//写
							//System.out.println("=====刷新操作======");
							CfgFixedPropertyDefineCache.refresh(CfgFixedPropertyDefineCache.class,libraryid);
						}
					}
				});
			}
		}
		
		while(executor.getCompletedTaskCount()<10000){
			System.out.println("====ActiveCount:"+executor.getActiveCount()+",CompletedTaskCount:"+executor.getCompletedTaskCount());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("===运行结束,总计运行时间："+(end-start)/1000 +"s,总计初始化"+CfgFixedPropertyDefineCache.initSizeMap+"次，总计销毁"+CfgFixedPropertyDefineCache.destroySizeMap+"次");
		System.exit(0);
	}

	

}
