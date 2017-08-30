package com.btxy.basis.webapp.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.btxy.basis.cache.cfg.IdNameCache;

public class IdNameTag extends TagSupport {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5554976925892116536L;
	private String formName;
	private String id;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	
	 public int doStartTag() throws JspException {

	    String name=	IdNameCache.getInstance().getMap().get(formName+id);
     	if(name==null) {
     		name=id;
     	}
     	try {
			pageContext.getOut().write(name);
		} catch (IOException e) {
			throw new JspException(e);
		}

        return super.doStartTag();
    }
	
}
