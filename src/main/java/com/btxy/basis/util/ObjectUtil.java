package com.btxy.basis.util;

public class ObjectUtil {

	public static Long getLong(Object obj){
		if(obj instanceof Long){
			return (Long)obj;
		}else{
			if(obj==null){
				return null;
			}else{
				return Long.parseLong(obj.toString());
			}
		}
	}
	
	public static Integer getInt(Object obj){
		if(obj instanceof Long){
			return (Integer)obj;
		}else{
			if(obj==null){
				return null;
			}else{
				return Integer.parseInt(obj.toString());
			}
		}
	}
	public static String getString(Object obj){
		if(obj instanceof String){
			return (String)obj;
		}else{
			if(obj==null){
				return null;
			}else{
				return (obj.toString());
			}
		}
	}
	public static Double getDouble(Object obj){
		if(obj instanceof Double){
			return (Double)obj;
		}else{
			if(obj==null){
				return null;
			}else{
				return Double.parseDouble(obj.toString());
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
