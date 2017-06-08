package com.btxy.basis.cache.model;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.Query;

import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.model.OrderedMap;

public class BaseCacheByLibrary<T,PK> {
	protected static synchronized void  initIncrease(Class<?> c1,Long library){
		String key=c1.getName()+"-"+library;
		if(!initSizeMap.containsKey(key)){
			initSizeMap.put(key, 0);
		}
		initSizeMap.put(key, initSizeMap.get(key)+1);
	}
	protected static synchronized void  destroyIncrease(Class<?> c1,Long library){
		String key=c1.getName()+"-"+library;
		if(!destroySizeMap.containsKey(key)){
			destroySizeMap.put(key, 0);
		}
		destroySizeMap.put(key, destroySizeMap.get(key)+1);
	}
	public static volatile  Map<String,Integer> initSizeMap=new Hashtable<String,Integer>();
	public static volatile  Map<String,Integer> destroySizeMap=new Hashtable<String,Integer>();
	
	
	public static Object getInstance(Class<?> c,Long library){
		synchronized(c){
			String key=c.getName()+"-"+library;
			
			if(!instanceMap.containsKey(key)){
				
				try {
					Constructor c1 = c.getDeclaredConstructor(new Class[]{Long.class});
					c1.setAccessible(true);   
					instanceMap.put(key, c1.newInstance(new Object[]{library}));
				} catch (Exception e) {
					e.printStackTrace();
				}   
	            
			}
			return instanceMap.get(key);
		}
	}
	public static void refresh(Class<?> c,Long library){
		String key=c.getName()+"-"+library;
		synchronized(c){
			if(instanceMap.containsKey(key)){
				destroyIncrease(c,library);
				instanceMap.remove(key);
			}
		}
	}
	public static void refreshAll() {
		instanceMap.clear();
	}
	
	
	
	protected static volatile Map<String,Object> instanceMap=new Hashtable<String,Object>();
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected DAO<T, PK> dao;
	
	public Long getLibrary() {
		return library;
	}

	public void setLibrary(Long library) {
		this.library = library;
	}

	protected Long library;
	
	Class<T> entityClass;
	
	ExtendFormInfo form;
	
	public BaseCacheByLibrary(final Class<T> entityClass1,Long libraryId){
		initIncrease(entityClass1,libraryId);
		dao=new BasicDAO<T,PK>(entityClass1,SpringContext.getDatastore());
		this.library=libraryId;
		entityClass=entityClass1;
		form=CfgFormInfoCache.getInstance().getCfgFormInfoByClass(entityClass);
		init();
	}
	public BaseCacheByLibrary(final Class<T> entityClass1,Long libraryId,boolean init){
		initIncrease(entityClass1,libraryId);
		dao=new BasicDAO<T,PK>(entityClass1,SpringContext.getDatastore());
		this.library=libraryId;
		entityClass=entityClass1;
		form=CfgFormInfoCache.getInstance().getCfgFormInfoByClass(entityClass);
		if(init){
			init();	
		}
		
	}
	
	//protected List<T> list=new ArrayList<T>();
	
	protected OrderedMap<PK,T> map=new OrderedMap<PK,T>();
	private void init(){
		Query<T> q = dao.createQuery(); 
    	q.or(q.criteria("library").equal(library),q.criteria("overt").equal(true));
    	
		if(form!=null && form.getIdGetMethod()!=null){
			List<T> list=dao.find(q).asList();
			if(list!=null){
				for(int i=0;i<list.size();i++){
					PK id;
					try {
						id = (PK) form.getIdGetMethod().invoke(list.get(i));
						map.put(id,list.get(i));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}else{
			List<T> list=dao.find(q).asList();
			List<PK> list1=dao.findIds();
			if(list!=null){
				for(int i=0;i<list1.size();i++){
					map.put(list1.get(i),list.get(i));
				}
			}
		}
    	
	}
	
	public List<T> getList(){
		return map.getList();
	}
	
	public T getEntityById(PK key){
		if(map!=null){
			return map.get(key);	
		}else{
			return null;
		}
		
	}
	
}
