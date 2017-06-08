package com.btxy.basis.util;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipUtil {
	public final static String encoding = "GBK";  
	  
    // 压缩  
    public static File zip(String srcPathname, String zipFilepath)throws BuildException, RuntimeException {  
        File file = new File(srcPathname);  
        if (!file.exists())  
            throw new RuntimeException("source file or directory "  
                    + srcPathname + " does not exist.");  
        
        File zipFile = new File(zipFilepath);
        if (!zipFile.getParentFile().exists()) {
        	zipFile.getParentFile().mkdirs();
		}
        
        Project proj = new Project();  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(proj);  
        // 判断是目录还是文件  
        if (file.isDirectory()) {  
            fileSet.setDir(file);  
        } else {  
            fileSet.setFile(file);  
        }  
  
        Zip zip = new Zip();  
        zip.setProject(proj);  
        zip.setDestFile(zipFile);  
        zip.addFileset(fileSet);  
        zip.setEncoding(encoding);  
        zip.execute();  
        return zipFile;
    }  
    
    public static File unZipFiles(File zipFile,String descDir)throws IOException{
    	File f = new File(descDir);
    	if (!f.exists()) {
			f.mkdirs();
		}
    	unZipFiles(zipFile.getAbsolutePath(), descDir);
    	return f;
    }
    // 解压缩  
    public static void unZipFiles(String zipFilepath, String destDir)throws BuildException, RuntimeException {  
        if (!new File(zipFilepath).exists())  
            throw new RuntimeException("zip file " + zipFilepath  
                    + " does not exist.");  
  
        Project proj = new Project();  
        Expand expand = new Expand();  
        expand.setProject(proj);  
        expand.setTaskType("unzip");  
        expand.setTaskName("unzip");  
        expand.setEncoding(encoding);  
  
        expand.setSrc(new File(zipFilepath));  
        expand.setDest(new File(destDir));  
        expand.execute();  
    }  
	
	
	/*public static File zip(String inputFileName,String targetZipFile) {
        try {
			return zip(targetZipFile, new File(inputFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }

    private static File zip(String zipFileName, File inputFile) throws Exception {
    	if (inputFile.exists()) {
			inputFile.delete();
		}
    	File tarZip = new File(zipFileName);
    	if (!tarZip.getParentFile().exists()) {
    		tarZip.getParentFile().mkdirs();
		}
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.close();
        return inputFile;
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }
    
    *//**
	 * 解压到指定目录
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 *//*
	public static void unZipFiles(String zipFile,String descDir)throws IOException{
		unZipFiles(new File(zipFile), descDir);
	}
	*//**
	 * 解压文件到指定目录
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 *//*
	@SuppressWarnings("rawtypes")
	public static File unZipFiles(File zipFile,String descDir)throws IOException{
		File pathFile = new File(descDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		File[] listFiles = pathFile.listFiles();
		for (File file : listFiles) {
			file.delete();
		}
		ZipFile zip = new ZipFile(zipFile);
		for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir+File.separator+zipEntryName).replaceAll("\\*", "/");;
			if (outPath.lastIndexOf("/") > -1) {
				outPath = outPath.substring(0, outPath.lastIndexOf('/'));
			}
			//判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath);
			//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if(file.isDirectory()){
				if(!file.exists()){
					file.mkdirs();
				}
				continue;
			}
			//输出文件路径信息
//			System.out.println(outPath);
			
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while((len=in.read(buf1))>0){
				out.write(buf1,0,len);
			}
			in.close();
			out.close();
		}
		return pathFile;
//		System.out.println("******************解压完毕********************");
	}*/
}
