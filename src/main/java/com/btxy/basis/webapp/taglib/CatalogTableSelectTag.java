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
public class CatalogTableSelectTag extends TagSupport {
	
    private String quizDataId;
    private String value;
    
    private String sheetId;
    private String name;
    
	
	 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	
	 public String getQuizDataId() {
		return quizDataId;
	}

	public void setQuizDataId(String quizDataId) {
		this.quizDataId = quizDataId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	private String getChecked(Map<String,Boolean> map,String id){
		if(map.containsKey(id)){
			return "checked";
		}
		return "";
	}
	private String getChecked(Map<String,Boolean> map,Long id){
		if(map.containsKey(id.toString())){
			return "checked";
		}
		return "";
	}
	
	public int doStartTag() throws JspException {
		 StringBuffer sb=new StringBuffer();
		 try{
			 Map<String,Boolean> map=new HashMap<String,Boolean>();
			 int start=value.trim().indexOf('[');
			 int end=value.trim().indexOf(']');
			 if(start+1==end){
				 value="";
			 }else if(start<end){
				 value=value.trim().substring(start+1,end);
			 }
			 if(value!=null){
				 String[] a=value.split(",");
				 if(a!=null && a.length>0){
					 for(String one:a){
						 if(one!=null && one.trim()!=null &&  !"".equals(one)){
							 map.put(one.trim(), true);
						 }
					 }
				 }
			 }
	        	
			 //QuizDataCache quizDataCache=QuizDataCacheFactory.getInstance(new Long(quizDataId));
			 /*List<QbCatalogInfo>  clist=quizDataCache.getCatalogInfoListOfThisData();
			 
			 int rowIndex=1;
			 LongVO maxCol=new LongVO(1l);
			 for(QbCatalogInfo one:clist){
				 
				 if(one.getParent()==null || one.getParent().intValue()==0){
					 					 
					 one.setRow(rowIndex);					 
					 one.setCol(1);				 
					 int rowSpan=getRowSpan(2,one,clist,maxCol);
					 
					 one.setIfLevelLeaf(false);
					 
					 one.setIfLeaf(false);
					 one.setAllChildrenRowSize(rowSpan);
					 
					 rowIndex=rowIndex+rowSpan;
				 }
				 
			 }
			 rowIndex=1;
			 for(QbCatalogInfo one:clist){
				 
				 if(one.getParent()==null || one.getParent().intValue()==0){ 
					 
					 sb.append("<tr colspan='0'>\r\n");
					 
					 sb.append("<td rowspan="+one.getAllChildrenRowSize()+">\r\n");
					 sb.append("<label class='catalog_select_floating'><input type='checkbox' id='ct_"+sheetId+"_"+one.getCatalogId()+"' value='"+one.getCatalogId()+"' name=\""+name+"\" "+getChecked(map,one.getCatalogId())+"/>"+one.getCatalogName()+"</label>\r\n");	
					 sb.append("</td>\r\n");
					 
					 getChildTableCode(map,rowIndex,sb,one,clist,maxCol);
					 
					 sb.append("</tr>\r\n");
					 
					 if(one.getAllChildrenRowSize()>1){
						 for(int i=1;i<one.getAllChildrenRowSize();i++){
							 sb.append("<tr>\r\n");
							 getChildTableCode(map,rowIndex+i,sb,one,clist,maxCol);
							 sb.append("</tr>\r\n");
						 }
					 }
					 rowIndex=rowIndex+one.getAllChildrenRowSize();
				 }
				 
			 }*/
			 try {
				 
				pageContext.getOut().write(sb.toString());
				 
	             
	         } catch (IOException io) {
	             throw new JspException(io);
	         }

		 }catch(Exception e){
			 e.printStackTrace();
			 
			 try {
	             pageContext.getOut().write("");
	         } catch (IOException io) {
	             throw new JspException(io);
	         }
		 }
		 
	     return super.doStartTag();
	 }

	
	 public void release() {
	        super.release();
	 }
	 
	 public static void main(String[] args) { 
		 
	 }
}
class LongVO{
	private Long value;

	public LongVO(){
		
	}
	public LongVO(Long v1){
		this.value=v1;
	}
	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
}