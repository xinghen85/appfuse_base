package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;
import com.btxy.basis.cache.model.AuthPrivilegeView;
import com.btxy.basis.cache.model.PrivilegeLongVO;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.util.list.ListUtil;


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
public class FileUploadTag extends TagSupport {
	
    private String filePath;
    private String ifNeedDownload;
    private String ifPublic;
    private String fileInfoFieldCode;
    
    private String fileInfoFieldName;
    
    private String ctx;
    
    private String value;

    
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getIfNeedDownload() {
		return ifNeedDownload;
	}

	public void setIfNeedDownload(String ifNeedDownload) {
		this.ifNeedDownload = ifNeedDownload;
	}

	public String getIfPublic() {
		return ifPublic;
	}

	public void setIfPublic(String ifPublic) {
		this.ifPublic = ifPublic;
	}

	public String getFileInfoFieldCode() {
		return fileInfoFieldCode;
	}

	public void setFileInfoFieldCode(String fileInfoFieldCode) {
		this.fileInfoFieldCode = fileInfoFieldCode;
	}

	public String getFileInfoFieldName() {
		return fileInfoFieldName;
	}

	public void setFileInfoFieldName(String fileInfoFieldName) {
		this.fileInfoFieldName = fileInfoFieldName;
	}

	public int doStartTag() throws JspException {
		 StringBuffer sb=new StringBuffer();
		 try{
			 JSONObject obj= JSONObject.parseObject(value);
			 if(obj==null){
				 obj=new JSONObject();
				 obj.put("fileId", "");
				 obj.put("contentType", "");
				 obj.put("size", "");
				 obj.put("fileName", "");
				 obj.put("key", "");
				 obj.put("downloadUrl", "");
				 obj.put("absoluteLocation", "");
			 }
			 sb.append(" 	<input type=\"hidden\" value=\""+obj.getString("fileId")+"\" name=\""+fileInfoFieldCode+".fileId\" id=\""+fileInfoFieldCode+"_fileId\"/>\r\n");
			 sb.append("    <input type=\"hidden\" value=\""+obj.getString("contentType")+"\" name=\""+fileInfoFieldCode+".contentType\" id=\""+fileInfoFieldCode+"_contentType\"/>\r\n");
			 sb.append("    <input type=\"hidden\" value=\""+obj.getString("size")+"\" name=\""+fileInfoFieldCode+".size\" id=\""+fileInfoFieldCode+"_size\"/>\r\n");
			 sb.append("    <input type=\"hidden\" value=\""+obj.getString("fileName")+"\" name=\""+fileInfoFieldCode+".fileName\" id=\""+fileInfoFieldCode+"_fileName\"/>\r\n");
			 sb.append("    <input type=\"hidden\" value=\""+obj.getString("key")+"\" name=\""+fileInfoFieldCode+".key\" id=\""+fileInfoFieldCode+"_key\"/>\r\n");
			 sb.append("    <input type=\"hidden\" value=\""+obj.getString("downloadUrl")+"\" name=\""+fileInfoFieldCode+".downloadUrl\" id=\""+fileInfoFieldCode+"_downloadUrl\"/>\r\n");
	            
			 sb.append("    <div class=\"input-group input-group-sm\">\r\n");
			 sb.append("		<input type=\"text\" value=\""+obj.getString("absoluteLocation")+"\" name=\""+fileInfoFieldCode+".absoluteLocation\" id=\""+fileInfoFieldCode+"_absoluteLocation\" class=\"form-control input-sm\" />\r\n");
			 sb.append("		<span class=\"input-group-btn\">\r\n");
			 sb.append("			<a href=\"#\" class=\"btn btn-inverse btn_a_click\" id=\"btn_"+fileInfoFieldCode+"_file_upload\"><i class=\"fa fa-file\"></i></a>\r\n");
			 sb.append("		</span>\r\n");
			 sb.append("	</div>\r\n");
				
			 sb.append("	<script type=\"text/javascript\">\r\n");
				
			 sb.append("	    $(document).ready(function() {\r\n");
			    	
				
				
			 sb.append("			$('#btn_"+fileInfoFieldCode+"_file_upload').click(function(){\r\n");
			 sb.append("			bootboxOpenDialog(\""+ctx+"/lb/files/web/taglib/"+filePath+"/"+ifNeedDownload+"/"+ifPublic+"/fileupload/showform\",\""+fileInfoFieldName+"\",\r\n");

			 sb.append("					function(){\r\n");
								
			 sb.append("						if($(\"#uploadReturn_fileId\")!=null && $(\"#uploadReturn_fileId\").text()!=null){\r\n");
									
			 sb.append("							$(\"#"+fileInfoFieldCode+"_fileId\").val($(\"#uploadReturn_fileId\").text()) ;\r\n");
			 sb.append("							$(\"#"+fileInfoFieldCode+"_fileName\").val($(\"#uploadReturn_fileName\").text()) ;\r\n");
			 sb.append("							$(\"#"+fileInfoFieldCode+"_contentType\").val($(\"#uploadReturn_contentType\").text()) ;\r\n");
			 sb.append("							$(\"#"+fileInfoFieldCode+"_size\").val($(\"#uploadReturn_size\").text()) ;\r\n");
			 sb.append("							$(\"#"+fileInfoFieldCode+"_absoluteLocation\").val($(\"#uploadReturn_absolutePath\").text()) ;\r\n");
			 
			 sb.append("							$(\"#"+fileInfoFieldCode+"_key\").val($(\"#uploadReturn_filekey\").text()) ;\r\n");
			 sb.append("							$(\"#"+fileInfoFieldCode+"_downloadUrl\").val($(\"#uploadReturn_fileURL\").text()) ;\r\n");
			 sb.append("					}		\r\n");
								
								    
			 sb.append("					});		\r\n");		
					
			 sb.append("		});			\r\n");
			    
			 sb.append("	});\r\n");
			    
			 sb.append("	</script>\r\n");
			
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
