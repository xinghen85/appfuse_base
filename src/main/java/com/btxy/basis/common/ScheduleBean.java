package com.btxy.basis.common;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ScheduleBean extends TimerTask {
	private static boolean isRunning = false;
	private static final Log log = LogFactory.getLog(ScheduleBean.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!isRunning) {

			isRunning = true;
			log.info("开始执行指定任务["+this.getClass()+"]");
			try{
				dojob();
			}catch(Exception e){
				e.printStackTrace();
			}
			isRunning = false;
			log.info("指定任务执行结束["+this.getClass()+"]");

		} else {
			log.info("上一次任务执行还未结束["+this.getClass()+"]");
		}
	}
	public ScheduleBean(){
		
	}
	public abstract void dojob();

}
