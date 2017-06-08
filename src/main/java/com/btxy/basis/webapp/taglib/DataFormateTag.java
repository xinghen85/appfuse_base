package com.btxy.basis.webapp.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.displaytag.tags.el.ExpressionEvaluator;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContext;
import org.springmodules.validation.commons.ValidatorFactory;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.LookUpInfoCache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFormInfo;

import com.btxy.basis.util.list.ListUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * <p>This class is designed to render a <label> tag for labeling your forms and
 * adds an asterik (*) for required fields.  It was originally written by Erik
 * Hatcher (http://www.ehatchersolutions.com/JavaDevWithAnt/).
 * <p/>
 * <p>It is designed to be used as follows:
 * <pre>&lt;tag:label key="userForm.username"/&gt;</pre>
 *
 * @jsp.tag name="label" bodycontent="empty"
 */
public class DataFormateTag extends TagSupport {
	private String dataType;
	private String value;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public int doStartTag() throws JspException {
		String rtn = value;
		try {

			if(value!=null && !"".equals(value.trim())){
				if ("AVA".equals(dataType)) {
					rtn = value;
				} else if ("AVB".equals(dataType)) {
					DecimalFormat df4 = new DecimalFormat("0");
					rtn=df4.format(new Double(value.trim()));
				} else if ("AVC".equals(dataType)) {
					DecimalFormat df4 = new DecimalFormat("0.00");
					rtn=df4.format(new Double(value.trim()));
				} else if ("AVD".equals(dataType) || "AVE".equals(dataType)) {
					rtn=value;
				} else if ("AVF".equals(dataType)) {
					rtn=value;
				} else if ("AVG".equals(dataType)) {
					rtn=value;
				} else if ("AVH".equals(dataType)) {
					rtn=value;
				} else if ("AVI".equals(dataType)) {
					DecimalFormat df4 = new DecimalFormat("0.00");
					rtn=df4.format(100*new Double(value.trim()));
				}
			}
			
			try {

				pageContext.getOut().write(rtn);

			} catch (IOException io) {
				throw new JspException(io);
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				pageContext.getOut().write(value);
			} catch (IOException io) {
				throw new JspException(io);
			}
		}

		return super.doStartTag();
	}
	 
}
