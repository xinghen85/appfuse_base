package com.btxy.basis.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.annotations.Transient;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectUtils {
	private static final String CLASS_NAME = "className";
	/**
	 * 把实体bean对象转换成DBObject
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static DBObject bean2DBObject(Object bean){
		return bean2DBObject(bean, null);
	}
	/**
	 * 把实体bean对象转换成DBObject
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static DBObject bean2DBObject(Object bean,String className){
		if (bean == null) {
			return null;
		}
		DBObject dbObject = new BasicDBObject();
		try {
			// 获取对象对应类中的所有属性域
			Field[] fields = bean.getClass().getDeclaredFields();
			for (Field field : fields) {
				// 获取属性名
				String varName = field.getName();
				if ("serialVersionUID".equals(varName)) {
					if (StringUtils.isNotBlank(className) && !dbObject.containsField(CLASS_NAME)) {
						dbObject.put(CLASS_NAME, className);
					}
					continue;
				}
				
//				if ("sb".equals(varName)) {
//					System.out.println(varName);
//				}
				
				//注解
				Transient annotation = field.getAnnotation(Transient.class);
				if (annotation != null) {
					if (StringUtils.isNotBlank(className) && !dbObject.containsField(CLASS_NAME)) {
						dbObject.put(CLASS_NAME, className);
					}
					continue;
				}
				
				// 修改访问控制权限
				boolean accessFlag = field.isAccessible();
				if (!accessFlag) {
					field.setAccessible(true);
				}
				
				Object value = field.get(bean);
				if (value == null) {
					if (StringUtils.isNotBlank(className) && !dbObject.containsField(CLASS_NAME)) {
						dbObject.put(CLASS_NAME, className);
					}
					dbObject.put(varName, null);
					continue;
				}
				
				Class c = field.getType();
				String vClassName = value.getClass().getName();
				
				if (vClassName != null && vClassName.startsWith("com.btxy.basis")) {
					value = bean2DBObject(value,vClassName);
					c = DBObject.class;
				}else if ("java.lang.StringBuffer".equals(vClassName)) {
					
					DBObject tmp = new BasicDBObject();
					tmp.put(CLASS_NAME, "java.lang.StringBuffer");
					tmp.put(varName, value.toString());
					className = "java.lang.StringBuffer";
					value = tmp;
					c = DBObject.class;
				}else if ("java.util.ArrayList".equals(vClassName)) {
					List<?> tmp = (List<?>) value;
					List<Object> list = new ArrayList<Object>();
					for (Object object : tmp) {
						String oClassName = object.getClass().getName();
						if (oClassName.startsWith("com.btxy.basis")) {
							DBObject bean2dbObject = bean2DBObject(object,oClassName);
//							if (bean2dbObject != null) {
								list.add(bean2dbObject);
//							}
						}else {
							list.add(object);
						}
					}
					value = list;
					c = list.getClass();
				}else if ("java.util.HashMap".equals(vClassName)) {
					Map<?,?> tmp = (Map<?,?>) value;
					Map<Object, Object> map = new HashMap<Object, Object>();
					for (Object key : tmp.keySet()) {
						Object k = null;
						Object v = null;
						
						Object object = tmp.get(key);
						
						String mapKClassName = key.getClass().getName();
						if (mapKClassName.startsWith("com.btxy.basis")) {
							k = bean2DBObject(key,mapKClassName);
						}else {
							k = key;
						}
						
						String mapVClassName = object.getClass().getName();
						if (mapVClassName.startsWith("com.btxy.basis")) {
							v = bean2DBObject(object,mapVClassName);
						}else {
							v = object;
						}
						
						map.put(k, v);
					}
					value = map;
					c = map.getClass();
				}
				
				
				Object convert = ConvertUtils.convert(value, c);
				dbObject.put(varName, convert);
				if (StringUtils.isNotBlank(className) && !dbObject.containsField(CLASS_NAME)) {
					dbObject.put(CLASS_NAME, className);
				}
				
				// 恢复访问控制权限
				field.setAccessible(accessFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbObject;
	}

	/**
	 * 把DBObject转换成bean对象
	 * 
	 * @param dbObject
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object dbObject2Bean(DBObject dbObject, Object bean) {
		if (bean == null) {
			return null;
		}
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			for (Field field : fields) {
				String varName = field.getName();
				Object value = dbObject.get(varName);
				
				if (value != null) {
					BeanUtils.setProperty(bean, varName, convert(value));
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	
	
	private static Object convert(Object value) throws Exception{
		if (value instanceof BasicDBObject) {
			BasicDBObject dbObject = (BasicDBObject) value;
			if (dbObject.isEmpty()) {
				return null;
			}
			String className = dbObject.getString(CLASS_NAME);
			if (StringUtils.isBlank(className)) {
				return dbObject.toMap();
			}
			if ("java.lang.StringBuffer".equals(className)) {
				StringBuffer buf = new StringBuffer();
				Set<String> keys = dbObject.keySet();
				for (String key : keys) {
					if (!CLASS_NAME.equals(key)) {
						String val = dbObject.get(key).toString();
						buf.append(val);
						return buf;
					}
				}
			}
			Object obj = Class.forName(className).newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String varName = field.getName();
				Object v = dbObject.get(varName);
				
				if (value != null) {
					BeanUtils.setProperty(obj, varName, convert(v));
				}
			}
			return obj;
			
		}else if (value instanceof BasicDBList) {
			List<Object> list = new ArrayList<Object>();
			BasicDBList dbList = (BasicDBList) value;
			for (Object object : dbList) {
				if (object instanceof BasicDBObject) {
					BasicDBObject dbObject = (BasicDBObject) object;
					if (dbObject.isEmpty()) {
						return null;
					}
					String className = dbObject.getString(CLASS_NAME);
					Object obj = Class.forName(className).newInstance();
					Field[] fields = obj.getClass().getDeclaredFields();
					for (Field field : fields) {
						String varName = field.getName();
						Object v = dbObject.get(varName);
						if (value != null) {
							BeanUtils.setProperty(obj, varName, convert(v));
						}
					}
					list.add(obj);
				}else {
					list.add(object);
				}
			}
			return list;
		}else {
			return value;
		}
	}
}
