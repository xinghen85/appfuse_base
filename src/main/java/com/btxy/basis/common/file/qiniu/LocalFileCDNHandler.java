package com.btxy.basis.common.file.qiniu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.EncoderException;
import org.json.JSONException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.Constants;
import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.file.base.BaseFileInterfaces;
import com.btxy.basis.common.file.base.UploadReturn;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.FileInfo;
import com.btxy.basis.util.file.FileOperate;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.URLUtils;

public class LocalFileCDNHandler implements BaseFileInterfaces{
	private static LocalFileCDNHandler instance;
	
	public static LocalFileCDNHandler getInstance(){
		if(instance==null){
			return new LocalFileCDNHandler(ConfigureContext.getDbCfgParemeters("publicDownloadAddressForLocal").getValue(),
					ConfigureContext.getDbCfgParemeters("privateDownloadAddressForLocal").getValue());
		}else{
			return instance;
		}
	}
//////////////////////////////////////////////////////////////////////////////////
	
	String publicDownloadAddressForLocal;
	String privateDownloadAddressForLocal;
	
	FileOperate fileOperate=new FileOperate();
	
	public LocalFileCDNHandler(String publicDownloadAddressForLocal1,String privateDownloadAddressForLocal1){
		
		
        
		publicDownloadAddressForLocal=publicDownloadAddressForLocal1;
		privateDownloadAddressForLocal=privateDownloadAddressForLocal1;
        
	}

	@Override
	public UploadReturn uploadPublicFile(String filePath,
			CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		UploadReturn ret=uploadTemporaryFile(filePath,file);
		if(ret.isSuccess()){
			return uploadFile(ret.getFileInfo(),filePath,true);
		}else{
			return ret;
		}
	}

	@Override
	public UploadReturn uploadPrivateFile(String filePath,
			CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		UploadReturn ret=uploadTemporaryFile(filePath,file);
		if(ret.isSuccess()){
			
			return uploadFile(ret.getFileInfo(),filePath,false);
		}else{
			return ret;
		}
	}
	private UploadReturn uploadFile(FileInfo fileInfo,String filePath,boolean ifPublic){
		UploadReturn return1=new UploadReturn();
		return1.setFileInfo(fileInfo);
        
		RestTemplate rest = new RestTemplate();
	    FileSystemResource resource = new FileSystemResource(new File(fileInfo.getAbsoluteLocation()));
	    MultiValueMap param = new LinkedMultiValueMap();
	    param.add("file", resource);
	    param.add("name", fileInfo.getFileName());
	 
	    String url = "http://"+publicDownloadAddressForLocal+"/api/files/web/"+filePath+"/1/"+(ifPublic?1:0)+"/upload/php";
	    
	    String string = rest.postForObject(url, param, String.class);
	    
	    if(string!=null){
	    	JSONObject obj=JSONObject.parseObject(string);
	    	if("0".equals(obj.getString("result"))){
	    		return1.setSuccess(true);
	    		return1.getFileInfo().setDownloadUrl(obj.getString("fileURL"));
	    		return1.getFileInfo().setKey(obj.getString("filekey"));
	    		return1.getFileInfo().setContentType(obj.getString("contentType"));
	    		return1.getFileInfo().setSize(obj.getLong("size"));
	    		return1.getFileInfo().setFileName(obj.getString("fileName"));
	    		
	    		return1.getFileInfo().setAbsoluteLocation(obj.getString("absolutePath"));
	    		return1.getFileInfo().setDateId(obj.getString("dateId"));
	    		return1.getFileInfo().setFileId(obj.getString("fileId"));
	    		
	    		
	    		return1.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
				SpringContext.getDatastore().save(return1.getFileInfo());
	    	}else{
	    		return1.setSuccess(false);
	    	}
	    }
	    
        return return1;
	}
	@Override
	public UploadReturn uploadPublicFile(String filePath, File file) {
		// TODO Auto-generated method stub
		FileInfo fileInfo=new FileInfo();		
		fileInfo.setFileName(file.getName());
		String dateId=sdf.format(new Date());
        String fileId=UUID.randomUUID().toString();
        
        fileInfo.setFileId(fileId);
        
        fileInfo.setKey(filePath+"/"+dateId+"/"+fileId+"/"+file.getName());        
                
        fileInfo.setAbsoluteLocation(file.getAbsolutePath());
        
		return uploadFile(fileInfo,filePath,true);
	}

	@Override
	public UploadReturn uploadPrivateFile(String filePath, File file) {
		// TODO Auto-generated method stub
		FileInfo fileInfo=new FileInfo();		
		fileInfo.setFileName(file.getName());
		String dateId=sdf.format(new Date());
        String fileId=UUID.randomUUID().toString();
        
        fileInfo.setFileId(fileId);
        
        fileInfo.setKey(filePath+"/"+dateId+"/"+fileId+"/"+file.getName());        
                
        fileInfo.setAbsoluteLocation(file.getAbsolutePath());
        
		return uploadFile(fileInfo,filePath,false);
	}
	// 将 s 进行 BASE64 编码 
	public  String getBASE64(String s) { 
		if (s == null) return null; 
		return (new sun.misc.BASE64Encoder()).encode( s.getBytes() ); 
	} 
	@Override
	public String getPublicDownloadUrl(String key,String fileName) {
		// TODO Auto-generated method stub
		String baseUrl = null;
		try {
			baseUrl = "http://"+publicDownloadAddressForLocal+"/api/files/web/public/download/"+URLEncoder.encode(fileName,"UTF-8")+"?key="+URLEncoder.encode(key,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return baseUrl;
	}

	@Override
	public String getPrivateDownloadUrl(String key,String fileName) {
		String baseUrl = null;
		try {
			baseUrl = "http://"+privateDownloadAddressForLocal+"/api/files/web/public/download/"+URLEncoder.encode(fileName,"UTF-8")+"?key="+URLEncoder.encode(key,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return baseUrl;
	}

	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	@Override
	public UploadReturn uploadTemporaryFile(String filePath,CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		
		UploadReturn return1=new UploadReturn();
		FileInfo fileInfo=new FileInfo();
		
		fileInfo.setContentType(file.getContentType());
		fileInfo.setSize(file.getSize());
		fileInfo.setFileName(file.getOriginalFilename());
		
		
		String dateId=sdf.format(new Date());
        String fileId=UUID.randomUUID().toString();
        //String fileName=file.getOriginalFilename();
        fileInfo.setDateId(dateId);
        fileInfo.setFileId(fileId);
        
        fileInfo.setKey(filePath+"/"+dateId+"/"+fileId+"/"+file.getOriginalFilename());
        
        //fileInfo.setRelativeLocation(File.separator+"temporary"+File.separator+dateId+File.separator+fileId+File.separator);
        
        
        String fileAbsolutePath=Constants.BMAP_UPLOAD_FILE_HOME+File.separator+"temporary"+File.separator+dateId+File.separator+fileId+File.separator;
        
        fileInfo.setAbsoluteLocation(fileAbsolutePath+file.getOriginalFilename());
        
        
        File dirPath = new File(fileAbsolutePath);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        
		try {
			InputStream stream = file.getInputStream();
			//write the file to the file specified
	        OutputStream bos = new FileOutputStream(fileAbsolutePath + file.getOriginalFilename());
	        int bytesRead;
	        byte[] buffer = new byte[8192];

	        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	            bos.write(buffer, 0, bytesRead);
	        }

	        bos.close();
	        stream.close();
		} catch (IOException e) {
			return1.setSuccess(false);
			return1.setException(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return1.setSuccess(true);
		return1.setFileInfo(fileInfo);
		return return1;
	}
	

}
