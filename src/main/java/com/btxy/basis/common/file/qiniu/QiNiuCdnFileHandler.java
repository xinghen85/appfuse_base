package com.btxy.basis.common.file.qiniu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.EncoderException;
import org.json.JSONException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.btxy.basis.Constants;
import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.file.base.BaseFileInterfaces;
import com.btxy.basis.common.file.base.UploadReturn;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.FileInfo;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.URLUtils;

public class QiNiuCdnFileHandler implements BaseFileInterfaces{
	private static QiNiuCdnFileHandler instance;
	
	public static QiNiuCdnFileHandler getInstance(){
		if(instance==null){
			return new QiNiuCdnFileHandler(ConfigureContext.getDbCfgParemeters("ACCESS_KEY_FOR_QINIU_CDN").getValue(),
					ConfigureContext.getDbCfgParemeters("SECRET_KEY_FOR_QINIU_CDN").getValue(),
					ConfigureContext.getDbCfgParemeters("publicFileBucketNameForQiNiuCDN").getValue(),
					ConfigureContext.getDbCfgParemeters("privateFileBucketNameForQiNiuCDN").getValue(),
					ConfigureContext.getDbCfgParemeters("publicDownloadAddressForQiNiuCDN").getValue(),
					ConfigureContext.getDbCfgParemeters("privateDownloadAddressForQiNiuCDN").getValue());
		}else{
			return instance;
		}
	}
//////////////////////////////////////////////////////////////////////////////////
	Mac mac;
	String ACCESS_KEY;
	String SECRET_KEY;
	
	String publicFileBucketNameForQiNiuCDN;
	String privateFileBucketNameForQiNiuCDN;
	
	String publicDownloadAddressForQiNiuCDN;
	String privateDownloadAddressForQiNiuCDN;
	
	
	public QiNiuCdnFileHandler(String ACCESS_KEY,String SECRET_KEY,String publicFileBucketNameForQiNiuCDN1,
			String privateFileBucketNameForQiNiuCDN1,String publicDownloadAddressForQiNiuCDN1,String privateDownloadAddressForQiNiuCDN1){
		
		Config.ACCESS_KEY = ACCESS_KEY;
        Config.SECRET_KEY = SECRET_KEY;
        
        mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        
        
        publicFileBucketNameForQiNiuCDN=publicFileBucketNameForQiNiuCDN1;
        privateFileBucketNameForQiNiuCDN=privateFileBucketNameForQiNiuCDN1;
        publicDownloadAddressForQiNiuCDN=publicDownloadAddressForQiNiuCDN1;
        privateDownloadAddressForQiNiuCDN=privateDownloadAddressForQiNiuCDN1;
	}
	@Override
	public UploadReturn uploadPublicFile(String filePath,CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		UploadReturn ret=uploadTemporaryFile(filePath,file);
		if(ret.isSuccess()){
			return uploadFile(ret.getFileInfo(),publicFileBucketNameForQiNiuCDN,true);
		}else{
			return ret;
		}
		
	}

	@Override
	public UploadReturn uploadPrivateFile(String filePath,CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		UploadReturn ret=uploadTemporaryFile(filePath,file);
		if(ret.isSuccess()){
			return uploadFile(ret.getFileInfo(),privateFileBucketNameForQiNiuCDN,false);
		}else{
			return ret;
		}
	}
	private UploadReturn uploadFile(FileInfo fileInfo,String bucketName,boolean ifPublic){
		UploadReturn return1=new UploadReturn();
		return1.setFileInfo(fileInfo);
        
		
        //String bucketName = "btxy-bsreport-public";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.putFile(uptoken, fileInfo.getKey(),fileInfo.getAbsoluteLocation(), extra);
        if(ret.getException()!=null){
        	//request.getSession().setAttribute("error"+check,"error");
        	//log.info("上传图片出错：Exception："+ret.getException());
        	return1.setSuccess(false);
        	return1.setException(ret.getException().getMessage());
        }else{
        	
	        String url = null;//rootUrl+"/"+ret.getKey();
	        if(ifPublic){
	        	url=getPublicDownloadUrl(fileInfo.getKey(),null);
	        }else{
	        	url=getPrivateDownloadUrl(fileInfo.getKey(),null);
	        }
	        return1.setSuccess(true);
        	return1.setKey(ret.getKey());
        	return1.setUrl(url);
        	
        	return1.getFileInfo().setDownloadUrl(url);
        	return1.getFileInfo().setKey(ret.getKey());
        				
        	return1.getFileInfo().setId(SequenceUtil.getNext(FileInfo.class));
			SpringContext.getDatastore().save(return1.getFileInfo());
			
        }
        return return1;
	}
	@Override
	public String getPublicDownloadUrl( String key,String fileName) {
		// TODO Auto-generated method stub
		//return publicDownloadAddressForQiNiuCDN+"/"+key;
		
		String baseUrl = null;
		try {
			baseUrl = URLUtils.makeBaseUrl(this.publicFileBucketNameForQiNiuCDN + ".qiniudn.com", key);
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//privateDownloadAddressForQiNiuCDN+"/"+key;//URLUtils.makeBaseUrl(ConfigureContext.getDbCfgParemeters("privateDownloadAddressForQiNiuCDN").getValue(), key);
		//GetPolicy getPolicy = new GetPolicy();
        //String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        return baseUrl;
	}

	@Override
	public String getPrivateDownloadUrl(String key,String fileName) {
		// TODO Auto-generated method stub
       
		try {
			 String baseUrl = URLUtils.makeBaseUrl(this.privateFileBucketNameForQiNiuCDN + ".qiniudn.com", key);//privateDownloadAddressForQiNiuCDN+"/"+key;//URLUtils.makeBaseUrl(ConfigureContext.getDbCfgParemeters("privateDownloadAddressForQiNiuCDN").getValue(), key);
			GetPolicy getPolicy = new GetPolicy();
	        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
	        /*try {
				downloadUrl=URLEncoder.encode(downloadUrl, "iso-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return downloadUrl;
		}catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncoderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
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
	        OutputStream bos = new FileOutputStream(fileAbsolutePath+file.getOriginalFilename());
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
        
		return uploadFile(fileInfo,publicFileBucketNameForQiNiuCDN,true);
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
        
		return uploadFile(fileInfo,publicFileBucketNameForQiNiuCDN,false);
	}

}
