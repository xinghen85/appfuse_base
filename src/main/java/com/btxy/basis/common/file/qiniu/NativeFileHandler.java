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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.btxy.basis.Constants;
import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.file.base.BaseFileInterfaces;
import com.btxy.basis.common.file.base.UploadReturn;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.FileInfo;
import com.btxy.basis.util.file.FileOperate;
import com.qiniu.api.rs.URLUtils;

public class NativeFileHandler implements BaseFileInterfaces{
	private static NativeFileHandler instance;
	
	public static NativeFileHandler getInstance(){
		if(instance==null){
			return new NativeFileHandler(ConfigureContext.getDbCfgParemeters("publicDownloadAddressForLocal").getValue(),
					ConfigureContext.getDbCfgParemeters("privateDownloadAddressForLocal").getValue());
		}else{
			return instance;
		}
	}
//////////////////////////////////////////////////////////////////////////////////
	
	String publicDownloadAddressForLocal;
	String privateDownloadAddressForLocal;
	
	FileOperate fileOperate=new FileOperate();
	
	public NativeFileHandler(String publicDownloadAddressForLocal1,String privateDownloadAddressForLocal1){
		
		
        
		publicDownloadAddressForLocal=publicDownloadAddressForLocal1;
		privateDownloadAddressForLocal=privateDownloadAddressForLocal1;
        
	}

	@Override
	public UploadReturn uploadPublicFile(String filePath,
			CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		UploadReturn ret=uploadTemporaryFile(filePath,file);
		if(ret.isSuccess()){
			String fileAbsolutePath=Constants.BMAP_UPLOAD_FILE_HOME+File.separator+"public"+File.separator+filePath+File.separator+ret.getFileInfo().getDateId()+File.separator+ret.getFileInfo().getFileId()+File.separator+file.getOriginalFilename();
			
			fileOperate.copyFile(ret.getFileInfo().getAbsoluteLocation(), fileAbsolutePath);
			
			ret.getFileInfo().setAbsoluteLocation(fileAbsolutePath);
			
			ret.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
			
			ret.getFileInfo().setDownloadUrl(getPublicDownloadUrl(ret.getFileInfo().getKey(),file.getOriginalFilename()));
			
			SpringContext.getDatastore().save(ret.getFileInfo());
			
			return ret;
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
			String fileAbsolutePath=Constants.BMAP_UPLOAD_FILE_HOME+File.separator+"private"+File.separator+filePath+File.separator+ret.getFileInfo().getDateId()+File.separator+ret.getFileInfo().getFileId()+File.separator+file.getOriginalFilename();
			
			fileOperate.copyFile(ret.getFileInfo().getAbsoluteLocation(), fileAbsolutePath);
			
			ret.getFileInfo().setAbsoluteLocation(fileAbsolutePath);
			
			ret.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
			
			ret.getFileInfo().setDownloadUrl(getPrivateDownloadUrl(ret.getFileInfo().getKey(),file.getOriginalFilename()));
			
			SpringContext.getDatastore().save(ret.getFileInfo());
			
			
			return ret;
		}else{
			return ret;
		}
	}

	@Override
	public UploadReturn uploadPublicFile(String filePath, File file) {
		// TODO Auto-generated method stub
		UploadReturn ret=new UploadReturn();
		FileInfo fileInfo=new FileInfo();		
		fileInfo.setFileName(file.getName());
		String dateId=sdf.format(new Date());
        String fileId=UUID.randomUUID().toString();
        
        fileInfo.setFileId(fileId);
        
        fileInfo.setKey(filePath+"/"+dateId+"/"+fileId+"/"+file.getName());        
                
        ret.setFileInfo(fileInfo);
        
        String fileAbsolutePath=Constants.BMAP_UPLOAD_FILE_HOME+File.separator+"public"+File.separator+filePath+File.separator+ret.getFileInfo().getDateId()+File.separator+ret.getFileInfo().getFileId()+File.separator+file.getName();
		
		fileOperate.copyFile(file.getAbsolutePath(), fileAbsolutePath);
		
		ret.getFileInfo().setAbsoluteLocation(fileAbsolutePath);
		
		ret.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
		
		ret.getFileInfo().setDownloadUrl(getPublicDownloadUrl(ret.getFileInfo().getKey(),file.getName()));
		
		SpringContext.getDatastore().save(ret.getFileInfo());
		
		
		return ret;
	}

	@Override
	public UploadReturn uploadPrivateFile(String filePath, File file) {
		// TODO Auto-generated method stub
		UploadReturn ret=new UploadReturn();
		FileInfo fileInfo=new FileInfo();		
		fileInfo.setFileName(file.getName());
		String dateId=sdf.format(new Date());
        String fileId=UUID.randomUUID().toString();
        
        fileInfo.setFileId(fileId);
        
        fileInfo.setKey(filePath+"/"+dateId+"/"+fileId+"/"+file.getName());        
                
        
        
        String fileAbsolutePath=Constants.BMAP_UPLOAD_FILE_HOME+File.separator+"private"+File.separator+filePath+File.separator+ret.getFileInfo().getDateId()+File.separator+ret.getFileInfo().getFileId()+File.separator+file.getName();
		
		fileOperate.copyFile(file.getAbsolutePath(), fileAbsolutePath);
		
		ret.getFileInfo().setAbsoluteLocation(fileAbsolutePath);
		
		ret.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
		
		ret.getFileInfo().setDownloadUrl(getPrivateDownloadUrl(ret.getFileInfo().getKey(),file.getName()));
		
		SpringContext.getDatastore().save(ret.getFileInfo());
		
		
		return ret;
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
