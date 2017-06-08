/**
 * @Administrator
 */
package com.btxy.basis.util.math;

import java.math.BigDecimal;

public class ScaleUtil {
	public static String getPrecision(Float f,int scale){
		BigDecimal bd = new BigDecimal(f);   
//		System.out.println(bd.toPlainString());
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	public static String getPrecision(Double f,int scale){
		String s=String.valueOf(f);
		return getPrecision(s, scale);
	}
	public static String getPrecision(Integer f,int scale){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	public static String getPrecision(String f,int scale){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	
	public static Double getDouble(Float f,int scale){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);  
		return bd.doubleValue();
	}
	//////////////////////////////////////////////////////////////////
	public static String getPrecision2(Float f,int scale,int scale1){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale1, BigDecimal.ROUND_HALF_UP);  
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd.toPlainString();
	}
	public static String getPrecision2(Double f,int scale,int scale1){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale1, BigDecimal.ROUND_HALF_UP);  
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	public static String getPrecision2(Integer f,int scale,int scale1){
		BigDecimal bd = new BigDecimal(f);   
		bd = bd.setScale(scale1, BigDecimal.ROUND_HALF_UP);  
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	public static String getPrecision2(String f,int scale,int scale1){
		BigDecimal bd = new BigDecimal(f);
		bd = bd.setScale(scale1, BigDecimal.ROUND_HALF_UP);  
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
		return bd.toPlainString();
	}
	
	public static Double getDouble2(Float f,int scale,int scale1){
		BigDecimal bd = new BigDecimal(f);  
		bd = bd.setScale(scale1, BigDecimal.ROUND_HALF_UP);  
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);  
		return bd.doubleValue();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Float f=0.775f;
		System.out.println(ScaleUtil.getPrecision(f+"", 2));
	}

}