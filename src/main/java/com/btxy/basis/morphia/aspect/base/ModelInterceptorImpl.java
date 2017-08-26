package com.btxy.basis.morphia.aspect.base;

import java.io.Serializable;
import java.util.Date;

import com.btxy.basis.cache.ehcache.ObjectUpdateMsgCache;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.LogModelChangeInfo;


public class ModelInterceptorImpl<T extends Serializable> implements ModelInterceptorInterface<T>{
	public final static int CHANGE_TYPE_SAVE=0;
	public final static int CHANGE_TYPE_REMOVE=1;
	static final int CHANGE_TYPE_INSERT=1;
	static final int CHANGE_TYPE_UPDATE=2;
	
	@Override
	public void onChange(T t, int type) {
		System.out.println("ModelInterceptorImpl"+t+"|"+type);
		LogModelChangeInfo log1=new LogModelChangeInfo();
		log1.setChangeType(type);
		log1.setId(SequenceUtil.getNext(LogModelChangeInfo.class));
		log1.setObject(t);
		log1.setTime(new Date());
		log1.setUserId(0l);
		
		
		ObjectUpdateMsgCache.getInstance().set("md_"+type+"_"+log1.getId(), log1);
	}
	
	
}
