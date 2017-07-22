package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


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
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5190705083093043948L;
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