package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

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
public class RolePrivilegeSelectTag extends TagSupport {
	
    private String roleId;
    private String value;
    
    
    private String name;
    
	
    
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
			 
			 //List<AuthPrivilegeView> list= AuthPrivilegeInfoCache.getInstance().getTreeList();
			 
			 Map<String, List<AuthPrivilegeView>> privilegeMap=AuthPrivilegeInfoCache.getInstance().getTableTreePrivilegeMap();
			 PrivilegeLongVO maxRow=AuthPrivilegeInfoCache.getInstance().getMaxRow();
			 PrivilegeLongVO maxCol=AuthPrivilegeInfoCache.getInstance().getMaxRow();
			 //sb.append(maxRow.getValue()+"-"+maxCol.getValue());
			 for(int i=1;i<=maxRow.getValue().intValue();i++){
				 sb.append("<tr>\r\n");
				 for(int j=1;j<=maxCol.getValue().intValue();j++){
					 String key=i+"-"+j;
					 List<AuthPrivilegeView> list1=privilegeMap.get(key);
					 if(list1!=null && list1.size()>0){
						 boolean ifleaf=list1.get(0).getAuthPrivilegeInfo().isIfLeaf();
						 if(!ifleaf){
							 AuthPrivilegeView one=list1.get(0);
							 sb.append("<td nowrap rowspan=\""+one.getAuthPrivilegeInfo().getAllChildrenRowSize()+"\">"+getHtmlCode(one,map)+"</td>\r\n");
						 }else{
							 int colspan=maxCol.getValue().intValue()-list1.get(0).getAuthPrivilegeInfo().getCol()+1;
							 List<String> list2=new ArrayList<String>();
							 for(AuthPrivilegeView oo:list1){
								 list2.add(getHtmlCode(oo,map));
							 }
							 sb.append("<td colspan=\""+(colspan)+"\">"+(ListUtil.toString(list2," "))+"</td>\r\n");

						 }
						 
						 
					 }
				 } 
				 sb.append("</tr>\r\n");
			 }
			 sb.append("<script type=\"text/javascript\">\r\n");
			 sb.append("function privilegeChecked(id){\r\n");
				 
			 sb.append("	 if($('#ct_'+id).prop('checked') ==true){\r\n");
			 sb.append("		 $(\".parentPrivilege\"+id).prop(\"checked\",true);\r\n");
			 sb.append("	 }else{\r\n");
			 sb.append("		 $(\".parentPrivilege\"+id).prop(\"checked\",false);\r\n");
			 sb.append("	 }\r\n");
			 sb.append("}\r\n");
			 sb.append("</script>\r\n");
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
	private String getLine(int row,int col){
		if(col==1 || col==2){
			return "nowrap";
		}
		return "";
	}
	private String getHtmlCode(AuthPrivilegeView one,Map<String,Boolean> map){
		List<String> classes=new ArrayList<String>();
		Long parent=one.getAuthPrivilegeInfo().getParent();
		while(parent!=null && parent.longValue()!=0){
			classes.add("parentPrivilege"+parent);
			AuthPrivilegeInfo ai=AuthPrivilegeInfoCache.getInstance().getEntityById(parent);
			if(ai!=null){
				parent=ai.getParent();
			}else{
				break;
			}
		}
		String str="<label class='catalog_select_floating'><input onclick=\"privilegeChecked("+one.getAuthPrivilegeInfo().getPrivilegeId()+")\" class=\""+ListUtil.toString(classes," ")+"\" type='checkbox' id='ct_"+one.getAuthPrivilegeInfo().getPrivilegeId()+"' value='"+one.getAuthPrivilegeInfo().getPrivilegeId()+"' name=\""+name+"\" "+getChecked(map,one.getAuthPrivilegeInfo().getPrivilegeId())+"/>"+one.getAuthPrivilegeInfo().getPrivilegeName()+"</label>";
		return str;
	}
	
	 public static void main(String[] args) { 
	 }
}
