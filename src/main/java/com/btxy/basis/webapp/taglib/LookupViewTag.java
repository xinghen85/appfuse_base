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
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.util.list.ListUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.ArrayList;
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
public class LookupViewTag extends TagSupport {
	private String name;
    private String prompt;
    private String scope;
    private String value;
    private String type;
    private String formName;
    private String refresh;
    
    
    private String libraryPath;
    
    private String multiple;
    private String pleaseSelect;
    private String id;
    private String ajax;
    private String url;
    
    private String enumCode;
    private String propertyCode;
    
    private String selectModel;
    
    private String cssClass;
    private String dataSource;
    
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getPleaseSelect() {
		return pleaseSelect;
	}
	public void setPleaseSelect(String pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAjax() {
		return ajax;
	}
	public void setAjax(String ajax) {
		this.ajax = ajax;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnumCode() {
		return enumCode;
	}
	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getSelectModel() {
		return selectModel;
	}
	public void setSelectModel(String selectModel) {
		this.selectModel = selectModel;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public String getLibraryPath() {
		return libraryPath;
	}
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public int doStartTag() throws JspException {
		 try{
			 String formatValue=value;
			 boolean mt=false;
			 if("true".equals(this.multiple)){
				 mt=true;
			 }   
			 String rtnName="";
			 List<String> valueList=new ArrayList<String>();
			 List<String> nameList=new ArrayList<String>();
			 if(mt && formatValue!=null){
				 int start=formatValue.trim().indexOf('[');
				 int end=formatValue.trim().indexOf(']');
				 if(start+1==end){
					 formatValue="";
				 }else if(start<end){
					 formatValue=formatValue.trim().substring(start+1,end);
					 String[] va=formatValue.split(",");
					 if(va!=null){
						 for(String va0:va){
							 if(va0!=null){
								 valueList.add((va0.trim()));
							 }
						 }
						 
					 }
				 }
	        	
			 }
			 if(formatValue!=null && !"".equals(formatValue)){
				 Long library=LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath);
				 if(library!=null){
					 if("1".equals(type)){
						 Map<String, String> map1=CfgEnumInfoCache.getInstance().getCfgAllEnumValueMap();
						 if(map1!=null){
							 if(mt && valueList!=null){
								 for(String vl0:valueList){
									 nameList.add(map1.get(vl0));
								 }
							 }else{
								 rtnName=map1.get(formatValue);
							 }
							 
						 }
					 }else if("2".equals(type) || "3".equals(type) || "4".equals(type)){
						 if(propertyCode!=null && !"".equals(propertyCode)){
							 //Map<Long,String> map1=FormPropertyCache.getInstance().getAllFixedPropertyEnumValueInfoMapByPropertyCode(propertyCode, library);
							 //if(map1!=null){
								 if(mt && valueList!=null){
									 for(String vl0:valueList){
										 
										 nameList.add(CfgFixedPropertyDefineCache.getInstance(library).getEnumTextByEnumId(new Long(vl0)));
									 }
								 }else{
									 rtnName=CfgFixedPropertyDefineCache.getInstance(library).getEnumTextByEnumId(new Long(formatValue));
								 }
							 //}
						 }
					 }else if("5".equals(type) || "9".equals(type)){
						 if(formName!=null && !"".equals(formName)){
					     		formName=formName.substring(0,1).toLowerCase()+formName.substring(1);
					     		
					     		if(mt && valueList!=null){
									 for(String vl0:valueList){
										 if(vl0!=null && !"".equals(vl0.trim())){
											 nameList.add(LookUpInfoCache.getInstance(formName).getNameById(new Long(vl0),dataSource));
										 }
										 
									 }
								 }else{
									 rtnName=LookUpInfoCache.getInstance(formName).getNameById(new Long(formatValue),dataSource);
								 }
					     		
					        	
						 }
					 }
				 } 
			 }
			 
			 
			 try {
				 if(mt){
					 pageContext.getOut().write(ListUtil.toString(nameList, ","));
				 }else{
					 pageContext.getOut().write(rtnName==null?"":rtnName);
				 }
	             
	         } catch (IOException io) {
	             throw new JspException(io);
	         }

		 }catch(Exception e){
			 e.printStackTrace();
			 
			 try {
	             pageContext.getOut().write(this.value);
	         } catch (IOException io) {
	             throw new JspException(io);
	         }
		 }
		 
	     return super.doStartTag();
	 }

	 public void release() {
	        super.release();
	 }
}
