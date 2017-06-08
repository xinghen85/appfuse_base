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
import com.btxy.basis.cache.cfg.CfgCustomPropertyCache;
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.model.FixedPropertyEnum;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.LabelValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
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
public class LookupSelectTag extends TagSupport {
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
    private String propertyId;
    
    private String selectModel;//0:普通下拉框 1：select2下拉框 
    
    private String cssClass;
    private String cssStyle;
    
	private String useFullId;
	
	private String onSelectChange;
	
	private String placeholder;
	private String allowClear;
	private String stateMachineCode;
	
	private String filter;//必须是json格式
	
	private String dataSource;
	
	
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getStateMachineCode() {
		return stateMachineCode;
	}
	public void setStateMachineCode(String stateMachineCode) {
		this.stateMachineCode = stateMachineCode;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getAllowClear() {
		return allowClear;
	}
	public void setAllowClear(String allowClear) {
		this.allowClear = allowClear;
	}
	public String getOnSelectChange() {
		return onSelectChange;
	}
	public void setOnSelectChange(String onSelectChange) {
		this.onSelectChange = onSelectChange;
	}
	public String getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getPleaseSelect() {
		return pleaseSelect;
	}
	public String getSelectModel() {
		return selectModel;
	}
	public void setSelectModel(String selectModel) {
		this.selectModel = selectModel;
	}
	public String isPleaseSelect() {
		return pleaseSelect;
	}
	public void setPleaseSelect(String pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
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
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
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
	
	public String getUseFullId() {
		return useFullId;
	}
	public void setUseFullId(String useFullId) {
		this.useFullId = useFullId;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	private String getPlaceholderCode(){
		if(this.placeholder==null || "".equals(this.placeholder.trim())){
			return "";
		}else{
			return "placeholder: \""+this.placeholder+"\" ,";
		}
	}
	private String getAllowClearCode(){
		boolean mt=false;
	    if("true".equals(this.multiple)){
	    	mt=true;
	    }
	    
		if("true".equals(this.allowClear) && !mt){
			return " allowClear: true ,";
		}else{
			return " ";
		}
	}
	private static final int COMMON_SELECT_MAX_LENGTH=10;
	 public int doStartTag() throws JspException {

		 	
		    boolean mt=false;
		    if("true".equals(this.multiple)){
		    	mt=true;
		    }
		    StringBuffer sbCommon=new StringBuffer();
	        StringBuffer sbSelect2=new StringBuffer();

	        /*
	    	 * 0:基本类型;
	    	 * 1:基本枚举类型;
	    	 * 2:固定变量-enum;
	    	 * 3:固定变量-muliti;
	    	 * 4:固定变量-tree;
	    	 * 5: child List;
	    	 * 6:foreignKey  此类型慎用
	    	 * 7:TreeParentKey 
	    	 * 8:foreignParentKey 
	    	 * 9:foreighKey 普通外键，非lazy加载的对象外键
	    	 * 10.文件
	    	 * 
	    	 * select model 0:普通下拉框 1:select2下拉框  3:弹出对话框选择数据 4:按钮组
	    	 * */
	        boolean useSelect2=true;
	        if((!mt && "1".equals(type)) || "0".equals(this.selectModel)){
        		useSelect2=false;
        	}
	        
	        if(mt && value!=null){
	        	int start=value.trim().indexOf('[');
	        	int end=value.trim().indexOf(']');
	        	if(start+1==end){
	        		value="";
	        	}else if(start<end){
	        		value=value.trim().substring(start+1,end);
	        	}
	        	
	        }
	        
	        Long library=LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath);
	        
	        
	        if("4".equals(this.selectModel)){
	        	this.getCodeOfGroupButton(sbSelect2, sbCommon, mt, library, useSelect2);
	        }else if(ajax!=null && "true".equals(ajax.trim())){
	        	this.getCodeOfAjax(sbSelect2, sbCommon, mt, library, useSelect2);
	        }else{
	        	getCodeOfCommon(sbSelect2,sbCommon,mt,library,useSelect2);
	        
	        }
	        
        	
        	
           /* StringBuffer sb = new StringBuffer();
            sb.append(lookupvalue.getLabel());*/
           
            

	        return super.doStartTag();
	    }
	 private void getCodeOfGroupButton(StringBuffer sbSelect2,StringBuffer sbCommon,boolean mt,Long library,boolean useSelect2) throws JspException{
		 sbCommon.append("<div >\r\n");
     	if("1".equals(type)){//form
     		
	        	Map<String, CfgEnumInfo> map=CfgEnumInfoCache.getInstance().getCfgEnumInfoMap();
	        	if(map!=null && map.containsKey(enumCode)){
	        		CfgEnumInfo enumInfo=map.get(enumCode);
	        		if(enumInfo!=null && enumInfo.getValues()!=null){
	        			
			        	for(int ii=0;ii<enumInfo.getValues().size();ii++){
			        		
			        		sbCommon.append("<label class=' "+getSelectCode(value,enumInfo.getValues().get(ii).getFullCode(),"active")+"'>\r\n");
			        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+enumInfo.getValues().get(ii).getFullCode()+"' "+getSelectCode(value,enumInfo.getValues().get(ii).getFullCode(),"checked")+" name=\""+name+"\"> "+enumInfo.getValues().get(ii).getValue()+"\r\n");
			        		sbCommon.append("</label>\r\n");

			        	}
			        }
	        	}
	        	
	        }else if("2".equals(type)){
	        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
	        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
	        	if(list1!=null ){
	        		
		        	for(int ii=0;ii<list1.size();ii++){
		        		sbCommon.append("<label class=' "+getSelectCode(value,list1.get(ii).getId(),"active")+"'>\r\n");
		        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+list1.get(ii).getId()+"' "+getSelectCode(value,list1.get(ii).getId(),"checked")+" name=\""+name+"\"> "+list1.get(ii).getName()+"\r\n");
		        		sbCommon.append("</label>\r\n");
		        		
		        	}
		        }else{
		        	
		        }
	        }else if("3".equals(type)){
	        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
	        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
	        	
	        	if(list1!=null ){
	        		
		        	for(int ii=0;ii<list1.size();ii++){
		        		
		        		if("true".equals(useFullId)){
		        			sbCommon.append("<label class=' "+getSelectCode(value,list1.get(ii).getFullId(),"active")+"'>\r\n");
			        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+list1.get(ii).getId()+"' "+getSelectCode(value,list1.get(ii).getFullId(),"checked")+" name=\""+name+"\"> "+list1.get(ii).getName()+"\r\n");
			        		sbCommon.append("</label>\r\n");
			        	}else{
			        		sbCommon.append("<label class=' "+getSelectCode(value,list1.get(ii).getId(),"active")+"'>\r\n");
			        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+list1.get(ii).getId()+"' "+getSelectCode(value,list1.get(ii).getId(),"checked")+" name=\""+name+"\"> "+list1.get(ii).getName()+"\r\n");
			        		sbCommon.append("</label>\r\n");
			        	}
		        		
		        		
		        	}
		        }
	        }else if("4".equals(type)){
	        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
	        	List<FixedPropertyEnum> list1=null;
	        	if(propertyCode!=null){
	        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
	        	}else if(propertyId!=null){
	        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyId(new Long(propertyId));
	        	}
	        	
	        	
	        	if(list1!=null ){
	        		
		        	for(int ii=0;ii<list1.size();ii++){
		        		sbCommon.append("<label class=' "+getSelectCode(value,list1.get(ii).getId(),"active")+"'>\r\n");
		        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+list1.get(ii).getId()+"' "+getSelectCode(value,list1.get(ii).getId(),"checked")+" name=\""+name+"\"> "+list1.get(ii).getName()+"\r\n");
		        		sbCommon.append("</label>\r\n");
		        		
		        	}
		        }
	        }else if("6".equals(type) || "9".equals(type)){
	        	
	        	if(formName!=null){
	        		formName=formName.substring(0,1).toLowerCase()+formName.substring(1);
	        		List<LabelValue> list=LookUpInfoCache.getInstance(formName).getIdNameList(filter,dataSource);
	        		
			        if(list!=null){
			        	
			        	for(int ii=0;ii<list.size();ii++){
			        		sbCommon.append("<label class=' "+getSelectCode(value,list.get(ii).getValue(),"active")+"'>\r\n");
			        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+list.get(ii).getValue()+"' "+getSelectCode(value,list.get(ii).getValue(),"checked")+" name=\""+name+"\"> "+list.get(ii).getLabel()+"\r\n");
			        		sbCommon.append("</label>\r\n");
			        		
			        	}
			        }
	        	}
	        	
	        }else if("100".equals(type)){
	        	
	        	CfgCustomProperty cp=CfgCustomPropertyCache.getInstance(library).getCfgCustomProperty(propertyCode);
	        	if(cp!=null && cp.getValueList()!=null && cp.getValueList().size()>0){
	        		if(!useSelect2 && "true".equals(this.pleaseSelect)){
     				sbCommon.append("       <option value=\"\">请选择</option>\r\n");
     			}
		        	for(int ii=0;ii<cp.getValueList().size();ii++){
		        		
		        		sbCommon.append("<label class=' "+getSelectCode(value,cp.getValueList().get(ii),"active")+"'>\r\n");
		        		sbCommon.append("<input type='"+(mt?"checkbox":"radio")+"' value='"+cp.getValueList().get(ii)+"' "+getSelectCode(value,cp.getValueList().get(ii),"checked")+" name=\""+name+"\"> "+cp.getValueList().get(ii)+"\r\n");
		        		sbCommon.append("</label>\r\n");
		        		
		        	}
	        	}
	        }
     	
			sbCommon.append("</div>\r\n");
			    
			    
			    try {
	            	
	            	pageContext.getOut().write(sbCommon.toString());
	            	
	                
	            } catch (IOException io) {
	                throw new JspException(io);
	            }
	        
	 }
	 private void getCodeOfAjax(StringBuffer sbSelect2,StringBuffer sbCommon,boolean mt,Long library,boolean useSelect2) throws JspException{
		 if(cssClass!=null && !"".equals(cssClass.trim())){
	        	
	        	sbSelect2.append("      <input id=\""+id+"\" name=\""+name+"\" type=\"text\" value=\""+value+"\" onChange=\""+onSelectChange+"\" class=\""+(cssClass.replaceFirst("form-control",""))+"\" style=\""+(cssStyle==null?"":cssStyle)+"\" />\r\n");
	        	sbCommon.append("       <select id=\""+id+"\" name=\""+name+"\" class=\""+cssClass+"\" onChange=\""+onSelectChange+"\" style=\""+(cssStyle==null?"":cssStyle)+"\"></select>\r\n");
	        }else{
	        	sbSelect2.append("      <input id=\""+id+"\" name=\""+name+"\" type=\"text\" value=\""+value+"\" onChange=\""+onSelectChange+"\" style=\""+(cssStyle==null?"":cssStyle)+"\" />\r\n");
	        	sbCommon.append("       <select id=\""+id+"\" name=\""+name+"\" onChange=\""+onSelectChange+"\"  style=\""+(cssStyle==null?"":cssStyle)+"\"></select>\r\n");
	        }
	        
		       
			sbSelect2.append("<script type=\"text/javascript\">\r\n");
			sbSelect2.append("$(document).ready(function() {\r\n");
			sbSelect2.append("$.ajax({ \r\n");
			sbSelect2.append("	type: \"get\", \r\n");
			sbSelect2.append("	url: \""+url+"\", \r\n");
			sbSelect2.append("	dataType: \"json\",\r\n");
			sbSelect2.append("	success: function(msg){	\r\n");
			sbSelect2.append("		$(\"#"+id+"\").select2({"+(mt?"multiple: true,":"")+"\r\n");
			sbSelect2.append("	 		"+this.getPlaceholderCode()+this.getAllowClearCode()+"data:msg\r\n");
			sbSelect2.append("		});		\r\n");
			sbSelect2.append(" 	}\r\n");	 
			sbSelect2.append(" });\r\n");	  
			sbSelect2.append("});\r\n");	  
			sbSelect2.append("</script>\r\n");
			
			
			sbCommon.append("<script type=\"text/javascript\">\r\n");
			sbCommon.append("$(document).ready(function() {\r\n");
			if(value!=null && !"".equals(value)){
				sbCommon.append("	select2ForFormElement($(\"#"+id+"\"),\""+url+"\","+("true".equals(this.pleaseSelect)?"false":"true")+",\""+value+"\");\r\n");
			}else{
				sbCommon.append("	select2ForFormElement($(\"#"+id+"\"),\""+url+"\","+("true".equals(this.pleaseSelect)?"false":"true")+");\r\n");

			}
			sbCommon.append(" });\r\n");	        
			sbCommon.append("</script>\r\n");
			
			try {
         	if(useSelect2){
         		pageContext.getOut().write(sbSelect2.toString());
         	}else{
         		pageContext.getOut().write(sbCommon.toString());
         	}
             
         } catch (IOException io) {
             throw new JspException(io);
         }
	 }
	 private void getCodeOfCommon(StringBuffer sbSelect2,StringBuffer sbCommon,boolean mt,Long library,boolean useSelect2) throws JspException{
		 if(cssClass!=null && !"".equals(cssClass.trim())){
	        	if(mt || "1".equals(type)){
	        		sbSelect2.append("      <input id=\""+id+"\" name=\""+name+"\" type=\"text\" onChange=\""+onSelectChange+"\" value=\""+value+"\" class=\""+(cssClass.replaceFirst("form-control",""))+"\" style=\""+(cssStyle==null?"":cssStyle)+"\" />\r\n");
		        	sbCommon.append("       <select id=\""+id+"\" name=\""+name+"\" class=\""+cssClass+"\" onChange=\""+onSelectChange+"\" style=\""+(cssStyle==null?"":cssStyle)+"\">\r\n");

	        	}else{
	        		sbSelect2.append("      <input id=\""+id+"\" name=\""+name+"\" type=\"text\" onChange=\""+onSelectChange+"\"  value=\""+value+"\" class=\""+(cssClass.replaceFirst("form-control",""))+"\" style=\""+(cssStyle==null?"":cssStyle)+"\" />\r\n");
		        	sbCommon.append("       <select id=\""+id+"\" name=\""+name+"\" onChange=\""+onSelectChange+"\" class=\""+(cssClass.replaceFirst("form-control",""))+"\" style=\""+(cssStyle==null?"":cssStyle)+"\">\r\n");

	        	}
	        }else{
	        	sbSelect2.append("      <input id=\""+id+"\" name=\""+name+"\" type=\"text\" onChange=\""+onSelectChange+"\"  value=\""+value+"\" style=\""+(cssStyle==null?"":cssStyle)+"\" />\r\n");
	        	sbCommon.append("       <select id=\""+id+"\" name=\""+name+"\" onChange=\""+onSelectChange+"\" style=\""+(cssStyle==null?"":cssStyle)+"\">\r\n");
	        }
	        
		 	if(!mt && "true".equals(this.pleaseSelect)){
				sbCommon.append("       <option></option>\r\n");
			}else if(!mt && this.placeholder!=null && !"".equals(this.placeholder.trim())){
				sbCommon.append("       <option></option>\r\n");
			}
		 
		       
	        sbSelect2.append("<script type=\"text/javascript\">\r\n");
	        sbSelect2.append("$(document).ready(function() {\r\n");
	        sbSelect2.append("$(\"#"+id+"\").select2({"+(mt?"multiple: true,":"")+"\r\n");
	        sbSelect2.append("	"+this.getPlaceholderCode()+this.getAllowClearCode()+" data:[\r\n");
	        
	        if(library!=null){
	        	if("1".equals(type)){//form
	        		
	        		Long stateMachineId=null;
	    			CfgStateMachineDefine stateMachineDefine=CfgStateMachineDefineCache.getInstance().getCfgStateMachineDefineByCode(stateMachineCode);
	    			
	    			if(stateMachineDefine!=null){
	    				stateMachineId=stateMachineDefine.getMachineId();
	    			}
	    			
	        		List<CfgEnumValueInfo> valueList=null;
	        		if(!mt && stateMachineId!=null && stateMachineId!=0){
	        			valueList=CfgEnumInfoCache.getInstance().getEnumValueList(enumCode,stateMachineId,value);
	        		}else{
	        			valueList=CfgEnumInfoCache.getInstance().getEnumValueList(enumCode);
	        		}
		        	if(valueList!=null){
		        		
			        	for(int ii=0;ii<valueList.size();ii++){
			        		
			        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
				        		sbSelect2.append("{id:'"+valueList.get(ii).getFullCode()+"',text:'"+valueList.get(ii).getValue()+"'}			\r\n");
			        		//}else{
			        			sbCommon.append("       <option value=\""+valueList.get(ii).getFullCode()+"\" "+getSelectCode(value,valueList.get(ii).getFullCode())+">"+valueList.get(ii).getValue()+"</option>\r\n");
			        		//}
			        		
			        		
			        	}
				        
		        	}
		        	
		        }else if("2".equals(type)){
		        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
		        	
		        	if(list1!=null ){
		        		
			        	for(int ii=0;ii<list1.size();ii++){
			        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
				        		sbSelect2.append("{id:'"+list1.get(ii).getId()+"',text:'"+list1.get(ii).getName()+"'}			\r\n");
			        		//}else{
			        			sbCommon.append("       <option value=\""+list1.get(ii).getId()+"\" "+getSelectCode(value,list1.get(ii).getId())+">"+list1.get(ii).getName()+"</option>\r\n");
			        		//}
			        		
			        	}
			        }else{
			        	
			        }
		        }else if("3".equals(type)){
		        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
		        	
		        	if(list1!=null ){
		        		
			        	for(int ii=0;ii<list1.size();ii++){
			        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
				        		if("true".equals(useFullId)){
				        			sbSelect2.append("{id:'"+list1.get(ii).getFullId()+"',text:'"+list1.get(ii).getName()+"'}			\r\n");
				        		}else{
				        			sbSelect2.append("{id:'"+list1.get(ii).getId()+"',text:'"+list1.get(ii).getName()+"'}			\r\n");
				        		}
				        		
			        		//}else{
			        			if("true".equals(useFullId)){
			        				sbCommon.append("       <option value=\""+list1.get(ii).getFullId()+"\" "+getSelectCode(value,list1.get(ii).getFullId())+">"+list1.get(ii).getName()+"</option>\r\n");
			        			}else{
			        				sbCommon.append("       <option value=\""+list1.get(ii).getId()+"\" "+getSelectCode(value,list1.get(ii).getId())+">"+list1.get(ii).getName()+"</option>\r\n");
				        		}
			        		//}
			        	}
			        }
		        }else if("4".equals(type)){
		        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
		        	List<FixedPropertyEnum> list1=null;
		        	if(propertyCode!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
		        	}else if(propertyId!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyId(new Long(propertyId));
		        	}
		        	if(list1!=null ){
		        		
			        	for(int ii=0;ii<list1.size();ii++){
			        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
				        		sbSelect2.append("{id:'"+list1.get(ii).getId()+"',text:'"+list1.get(ii).getName()+"'}			\r\n");
			        		//}else{
				        		sbCommon.append("       <option value=\""+list1.get(ii).getId()+"\" "+getSelectCode(value,list1.get(ii).getId())+">"+list1.get(ii).getName()+"</option>\r\n");
			        		//}
			        	}
			        }
		        }else if("6".equals(type) || "9".equals(type)){
		        	
		        	if(formName!=null){
		        		formName=formName.substring(0,1).toLowerCase()+formName.substring(1);
		        		List<LabelValue> list=LookUpInfoCache.getInstance(formName).getIdNameList(filter,dataSource);
		        		
				        if(list!=null){
//				        	sbSelect2.append("{id:'"+"  "+"',text:'"+"  "+"'}			\r\n");
//				        	sbCommon.append("       <option value=\""+"  "+"\" "+"  "+">"+"  "+"</option>\r\n");
				        	for(int ii=0;ii<list.size();ii++){
				        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
					        		
//				        		sbSelect2.append("		,");
				        		sbSelect2.append("{id:'"+list.get(ii).getValue()+"',text:'"+list.get(ii).getLabel()+"'}			\r\n");
				        		//}else{
				        			sbCommon.append("       <option value=\""+list.get(ii).getValue()+"\" "+getSelectCode(value,list.get(ii).getValue())+">"+list.get(ii).getLabel()+"</option>\r\n");
				        		//}
				        	}
				        }
		        	}
		        	
		        }else if("100".equals(type)){
		        	
		        	CfgCustomProperty cp=CfgCustomPropertyCache.getInstance(library).getCfgCustomProperty(propertyCode);
		        	if(cp!=null && cp.getValueList()!=null && cp.getValueList().size()>0){
		        		
			        	for(int ii=0;ii<cp.getValueList().size();ii++){
			        		//if(useSelect2){
				        		if(ii>0){
				        			sbSelect2.append("		,");
				        		}
				        		sbSelect2.append("{id:'"+cp.getValueList().get(ii)+"',text:'"+cp.getValueList().get(ii)+"'}			\r\n");
			        		//}else{
			        			sbCommon.append("       <option value=\""+cp.getValueList().get(ii)+"\" "+getSelectCode(value,cp.getValueList().get(ii))+">"+cp.getValueList().get(ii)+"</option>\r\n");
			        		//}
			        	}
		        	}
		        }
	        }
	        
	        sbSelect2.append("		\r\n");
	        sbSelect2.append("	 ]		\r\n");
	        sbSelect2.append("});		\r\n");
	        sbSelect2.append(" });\r\n");
			    
	        sbSelect2.append("</script>\r\n");
	        sbCommon.append("       </select>\r\n");
	        
	        if(!mt && !"1".equals(type)){
	        	sbCommon.append("<script type=\"text/javascript\">\r\n");
	        	sbCommon.append("$(document).ready(function() {\r\n");
	        	sbCommon.append("$(\"#"+id+"\").select2({\r\n");
	        	sbCommon.append("	"+this.getPlaceholderCode()+this.getAllowClearCode()+" \r\n");
	        	sbCommon.append("});		\r\n");
	        	sbCommon.append(" });\r\n");
				    
	        	sbCommon.append("</script>\r\n");
	        }
	        
	      
	        
	        try {
         	if(mt){
         		pageContext.getOut().write(sbSelect2.toString());
         	}else{
         		pageContext.getOut().write(sbCommon.toString());
         	}
             
         } catch (IOException io) {
             throw new JspException(io);
         }
	 }
	 private String getSelectCode(String value,String selVal){
		 return getSelectCode(value,selVal,"selected =\"true\"");
		 
	 }
	 private String getSelectCode(String value,Long selVal){
		 return getSelectCode(value,selVal,"selected =\"true\"");
	 }
	 private String getSelectCode(String value,String selVal,String code){
		 if(value!=null && value.trim().equals(selVal)){
			 return code;
		 }else{
			 return "";
		 }
	 }
	 private String getSelectCode(String value,Long selVal,String code){
		 if(value!=null && value.trim().equals(selVal.toString())){
			 return code;
		 }else{
			 return "";
		 }
	 }
	 public void release() {
	        super.release();
	 }
}
