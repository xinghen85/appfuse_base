package com.btxy.basis.data.common;

public class DataException extends Exception{
	public static int EXCEPTION_PAPER_NOT_FOUNDED=1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataException(String msg){
		super(msg);
	}
}
