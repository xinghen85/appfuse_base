package com.btxy.basis.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.common.file.base.BaseFileInterfaces;
import com.btxy.basis.common.file.qiniu.NativeFileHandler;
import com.btxy.basis.common.file.qiniu.QiNiuCdnFileHandler;

public class FileHelp {
	public static BaseFileInterfaces getFileCdn() {
        if("2".equals(ConfigureContext.getDbCfgParemeters("FileCDNType").getValue())){
        	return QiNiuCdnFileHandler.getInstance();
        }else{
        	return NativeFileHandler.getInstance();
        }
	}
	
	public static File downLoadFile(String urlStr,String localFile) {
        try {
        	File local = new File(localFile);
        	if (!local.getParentFile().exists()) {
        		local.getParentFile().mkdirs();
			}
        	
        	URL url = new URL(urlStr);
            int byteread = 0;
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(localFile);

            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            fs.flush();
            fs.close();
            inStream.close();
            return local;
        } catch (Exception e) {
            throw new RuntimeException("下载文件异常"+e.getMessage());
        }
	}
	public static void copyFile(String sourceFile, String targetFile){
		copyFile(new File(sourceFile), new File(targetFile));
	}
	// 复制文件
    public static void copyFile(File sourceFile, File targetFile){
    	
    	
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
        	if (!targetFile.getParentFile().exists()) {
        		targetFile.getParentFile().mkdirs();
			}
        	
        	if (targetFile.exists()) {
				targetFile.delete();
			}
        	
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        }catch (Exception e) {
        	e.printStackTrace();
		}finally {
            try {
				// 关闭流
				if (inBuff != null)
				    inBuff.close();
				if (outBuff != null)
				    outBuff.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
}
