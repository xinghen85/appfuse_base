package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Dee {

	static Configuration cfg = new Configuration();

	public void main(String ftlName,Map<String,Object> map, PageContext pageContext) throws JspException {
		String vstr = null;
		try {
			Template temp = cfg.getTemplate(ftlName, "utf-8");
			Writer out = new StringWriter();
			temp.process(map, out);
			vstr = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		try {
			pageContext.getOut().write(vstr);
		} catch (IOException io) {
			throw new JspException(io);
		}
	}

}
