package com.btxy.basis.util.zx;

public class BooleanUtil {
	
	public static Boolean parseBoolean(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null){
			return null;
		}else{
			return Boolean.parseBoolean(obj.toString());
		}
	}
}
