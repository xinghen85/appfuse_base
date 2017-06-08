package com.btxy.basis.util.zx;

public class DoubleUtil {
	
	public static Double parseDouble(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null){
			return null;
		}else{
			return Double.parseDouble(obj.toString());
		}
	}
}
