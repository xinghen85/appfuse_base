package com.btxy.basis.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class PoiTools {
	/**
	 * 查找word中指定文本（正则表达式）
	 * @param path
	 * @param regex
	 * @return
	 */
	public static List<String> findText(String text,String regex) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isBlank(text)) {
			return result;
		}
		Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(text);
	    while (m.find()){
	    	String find = m.group();
	    	result.add(find);
	    }
		return result;
	}
	/**
	 * 查找word中指定文本（正则表达式）
	 * @param path
	 * @param regex
	 * @return
	 */
	public static List<String> findVariableTextList(String text) {
		List<String> result = new ArrayList<String>();
		if(text!=null){
			String str=text.trim();
			if(str.length()>3){
				str=str.substring(2,str.length()-1);
				String[] a=str.split("\\.");
				if(a!=null){
					for(String aone:a){
						result.add(aone);
					}
				}
			}	
			
		}
		
		return result;
	}
	public static Long findFirstNumber(String text) {
		if(text!=null){
			Pattern p=Pattern.compile("(\\d+)");   
			Matcher m=p.matcher(text);       
			if(m.find()){
				return new Long(m.group(1));    
			}
		}
		return null;
	}
	/**
	 * 获取word2007以上的文本内容
	 * @param path
	 * @return
	 */
	public static String getWordText(String path) {
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(path);  
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);  
			String text = extractor.getText();
			extractor.close();
			return text;
		}catch (Exception e) {
//			e.printStackTrace();
			try {
				InputStream is = new FileInputStream(new File(path));  
				WordExtractor ex = new WordExtractor(is);  
				String text2003 = ex.getText();  
				ex.close();
				return text2003;
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获取ppt文本
	 * @param path
	 * @return
	 */
	public static String getPptText(String path){
        try {
        	XMLSlideShow xmlslideshow = new XMLSlideShow(new FileInputStream(path));
			XSLFPowerPointExtractor ppt = new XSLFPowerPointExtractor(xmlslideshow);
			String text = ppt.getText();
			ppt.close();
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
	
	public static void main(String[] args) { 
		List<String> list=PoiTools.findVariableTextList("${v109.jkfd.fd}");
		for(String one:list){
			System.out.println(one);
		}
	}
}
