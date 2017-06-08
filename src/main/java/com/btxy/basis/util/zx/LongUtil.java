package com.btxy.basis.util.zx;

public class LongUtil {
	public static boolean ifEqual(Long l1,Long l2){
		if(l1!=null && l2!=null){
			return l1.longValue()==l2.longValue();
		}else if(l1==null && l2==null){
			return true;
		}else{
			return false;
		}
	}
	public static boolean ifEqual(Object l1,Object l2){
		if(l1!=null && l2!=null){
			return new Long(l1.toString()).longValue()==new Long(l2.toString()).longValue();
		}else if(l1==null && l2==null){
			return true;
		}else{
			return false;
		}
	}
	public static Long parseLong(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null){
			return null;
		}else{
			return Long.parseLong(obj.toString());
		}
	}
}
