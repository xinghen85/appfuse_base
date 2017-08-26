package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.LookUpInfoCache;
import com.btxy.basis.cache.cfg.CfgCustomPropertyCache;
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.model.FixedPropertyEnum;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.LabelValue;


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
public class LookupSelectTag2 extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1496976754724601830L;
	private String name;
    private String prompt;
    private String scope;
    private String value="";
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
    
    private String cssClass="";
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
		if(cssClass!=null&&cssClass.trim().length()>0){
			this.cssClass = cssClass.trim();
		}
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
		if(value!=null){
			this.value = value;
		}
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
		 		Map<String,Object> maps=new HashMap<String,Object>();
	     		if("1".equals(type)){//form
	     		
		        	Map<String, CfgEnumInfo> map=CfgEnumInfoCache.getInstance().getCfgEnumInfoMap();
		        	CfgEnumInfo enumInfo=map.get(enumCode);
	        		if(enumInfo!=null && enumInfo.getValues()!=null){
	        			maps.put("values", enumInfo.getValues());
			        }
		        	
		        }else if("2".equals(type)){
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);

        			maps.put("fixedPropertyEnums",list1);
		        }else if("3".equals(type)){
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);

        			maps.put("fixedPropertyEnums",list1);
		        }else if("4".equals(type)){
		        	//List<FixedPropertyEnum> list1=FormPropertyCache.getInstance().getFixedPropertyEnumByPropertyCodeAndLibrary(propertyCode, library);
		        	List<FixedPropertyEnum> list1=null;
		        	if(propertyCode!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
		        	}else if(propertyId!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyId(new Long(propertyId));
		        	}
        			maps.put("fixedPropertyEnums",list1);
		        }else if("6".equals(type) || "9".equals(type)){
		        	if(formName!=null){
		        		formName=formName.substring(0,1).toLowerCase()+formName.substring(1);
		        		List<LabelValue> list=LookUpInfoCache.getInstance(formName).getIdNameList(filter,dataSource);
	        			maps.put("labelValue",list);
		        	}
		        }else if("100".equals(type)){
		        	CfgCustomProperty cp=CfgCustomPropertyCache.getInstance(library).getCfgCustomProperty(propertyCode);
		        	if(cp!=null){
	        			maps.put("labelValue",cp.getValueList());
		        	}
		        }
	     	maps.put("useCommon", true);
			maps.put("bean", this);
			new Dee().main("groupButton",maps,pageContext);
	 }
	 private void getCodeOfAjax(StringBuffer sbSelect2,StringBuffer sbCommon,boolean mt,Long library,boolean useSelect2) throws JspException{
	 		Map<String,Object> map=new HashMap<String,Object>();
	     	map.put("useCommon", !useSelect2);
			map.put("bean", this);
         new Dee().main("ajax",map,pageContext);
	 }
	 private void getCodeOfCommon(StringBuffer sbSelect2,StringBuffer sbCommon,boolean mt,Long library,boolean useSelect2) throws JspException{
	 		Map<String,Object> maps=new HashMap<String,Object>();
	     	maps.put("useCommon", !useSelect2);
			maps.put("bean", this);
		 
		       
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

        			maps.put("valueList", valueList);
		        	
		        }else if("2".equals(type)){
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);

        			maps.put("fixedPropertyEnums",list1);
		        	
		        }else if("3".equals(type)){
		        	List<FixedPropertyEnum> list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);

        			maps.put("fixedPropertyEnums",list1);
		        }else if("4".equals(type)){
		        	List<FixedPropertyEnum> list1=null;
		        	if(propertyCode!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyCode(propertyCode);
		        	}else if(propertyId!=null){
		        		list1=CfgFixedPropertyDefineCache.getInstance(library).getEnumListByPropertyId(new Long(propertyId));
		        	}
        			maps.put("fixedPropertyEnums",list1);
		        }else if("6".equals(type) || "9".equals(type)){
		        	
		        	if(formName!=null){
		        		formName=formName.substring(0,1).toLowerCase()+formName.substring(1);
		        		List<LabelValue> list=LookUpInfoCache.getInstance(formName).getIdNameList(filter,dataSource);
	        			maps.put("labelValue",list);
		        	}
		        	
		        }else if("100".equals(type)){
		        	
		        	CfgCustomProperty cp=CfgCustomPropertyCache.getInstance(library).getCfgCustomProperty(propertyCode);
		        	if(cp!=null ){
	        			maps.put("labelValue",cp.getValueList());
		        	}
		        }
	        }
         new Dee().main("common",maps,pageContext);
	 }
		public String getPleaseSelect1() {
			return "true".equals(this.pleaseSelect)?"false":"true";
		}
		private String getCssClass1() {
			return cssClass.replaceFirst("form-control","");
		}
	 public void release() {
	        super.release();
	 }
}
