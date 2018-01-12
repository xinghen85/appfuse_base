package com.btxy.basis.webapp.taglib;

import java.io.IOException;

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
public class TablePageSizeSetTag extends TagSupport {
	
    private String formName;
    private String pageSize;
    
	
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	private String getSelected(String pageSize,int size){
		if(pageSize!=null && !"".equals(pageSize.trim()) && new Integer(pageSize).intValue()==size){
			return "selected";
		}else{
			return "";
		}
	}
	public int doStartTag() throws JspException {
		 StringBuffer sb=new StringBuffer();
		 try{
			 
			 sb.append("<div class=\"dataTables_length\" id=\"DataTables_Table_0_length\">\r\n");
			 sb.append("	<span class=\"user-info\">&#x663E;&#x793A;\r\n");
					
			 sb.append("		<select   size=\"1\" onchange=\"setPageSizeToCookieOfForm('"+formName+"',this.value)\" value=\""+pageSize+"\">\r\n");
//			 sb.append("			<option value=\"10\" "+getSelected(pageSize,10)+">10</option>\r\n");
			 sb.append("			<option value=\"25\" "+getSelected(pageSize,25)+">25</option>\r\n");
			 sb.append("			<option value=\"50\" "+getSelected(pageSize,50)+">50</option>\r\n");
			 sb.append("			<option value=\"100\" "+getSelected(pageSize,100)+">100</option>\r\n");
			 sb.append("			<option value=\"200\" "+getSelected(pageSize,200)+">200</option>\r\n");
			 sb.append("			<option value=\"300\" "+getSelected(pageSize,300)+">300</option>\r\n");
			 sb.append("			<option value=\"500\" "+getSelected(pageSize,500)+">500</option>\r\n");
			 sb.append("			<option value=\"1000\" "+getSelected(pageSize,1000)+">1000</option></select>&nbsp;&#x6761;&#x8BB0;&#x5F55; \r\n");
			 sb.append("	</span>\r\n");
			 sb.append("</div>\r\n");
			
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
	
	 public static void main(String[] args) { 
	 }
}
