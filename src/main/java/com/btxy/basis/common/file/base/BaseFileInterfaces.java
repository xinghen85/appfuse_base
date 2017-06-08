package com.btxy.basis.common.file.base;

import java.io.File;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface BaseFileInterfaces {
	public UploadReturn uploadPublicFile(String filePath,CommonsMultipartFile file);
	public UploadReturn uploadPrivateFile(String filePath,CommonsMultipartFile file);
	
	public UploadReturn uploadPublicFile(String filePath,File file);
	public UploadReturn uploadPrivateFile(String filePath,File file);
	
	
	public String getPublicDownloadUrl(String key,String fileName);
	public String getPrivateDownloadUrl(String key,String fileName);
	
	public UploadReturn uploadTemporaryFile(String filePath,
			CommonsMultipartFile file);
}
