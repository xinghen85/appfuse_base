package com.btxy.basis.util.string;

import java.math.BigDecimal;

public class string {
	public static String delnull(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
	public static int delintnull(Object obj){
		if(obj==null){
			return 0;
		}else if("".equals(obj)){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}
	public static String delbigdnull(Object obj){
		if(obj==null){
			return "0";
		}else{
			return ((BigDecimal)obj).toString();
		}
	}
	
	public static boolean delbooleannull(Object obj){
		
		if(obj==null){
			return false;
		}else{
			return Boolean.parseBoolean(obj.toString());
		}
	}
	
	public  static String[] delstringarraynull(Object[] objarray){
		
		
		if(objarray==null){
			String[] s = {""};
			return s;
		}else{
			return (String[])objarray;
		}
	}
	
	public  static String[] delObj2StringArray(Object obj){
		if(obj==null){
			String[] s = {""};
			return s;
		}else{
			return obj.toString().split("\\,");
		}
	}
	
	
}
