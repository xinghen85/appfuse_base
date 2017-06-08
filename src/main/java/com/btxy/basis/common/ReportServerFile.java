package com.btxy.basis.common;

import java.io.Serializable;

public class ReportServerFile implements Serializable{
	private static final long serialVersionUID = 526652l;
	private String fileName;
	private String downURL;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDownURL() {
		return downURL;
	}
	public void setDownURL(String downURL) {
		this.downURL = downURL;
	}
	
}
