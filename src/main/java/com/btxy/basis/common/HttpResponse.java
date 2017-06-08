package com.btxy.basis.common;

import java.io.Serializable;

public class HttpResponse implements Serializable{
	private static final long serialVersionUID = 102020202l;
	private String resultCode;
	private String message;
	public HttpResponse() {
	}
	public HttpResponse(String resultCode,String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
