package com.btxy.basis.util.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StringTemplate {
	public static String executeSmall(String temmplate,Map root){
		String rtnValue=temmplate;
		Configuration cfg = new Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        
        stringLoader.putTemplate("myTemplate", temmplate);
        cfg.setTemplateLoader(stringLoader);
        Template temp;
		try {
			temp = cfg.getTemplate("myTemplate","utf-8");
	        
	       
	        Writer out = new StringWriter(1024);
	        temp.process(root, out);
	        rtnValue=out.toString();//.replaceAll("[\\n\\r]", "");
	        out.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (TemplateException e) {
			
			e.printStackTrace();
		} 
		return rtnValue;
	}
}
