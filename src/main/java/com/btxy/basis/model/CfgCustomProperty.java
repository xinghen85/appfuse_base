package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.util.list.ListUtil;

@Entity(value="cfg_custom_property", noClassnameStored=true) 
@TableAnnoExtend(description="系统可变属性",textSearch=true)
@javax.persistence.Entity
@javax.persistence.Table(name="CfgCustomProperty",catalog="bsquiz")
public class CfgCustomProperty implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="属性ID")
	private Long propertyId;
	
	@FieldAnnoExtend(description="属性名称",required=true)
	private String propertyName;
	
	@FieldAnnoExtend(description="属性编码",required=true)
	private String propertyCode;
	
	@FieldAnnoExtend(type=1,enumCode="AV",description="属性值类型",required=true)
	private String valueType;
	
	@FieldAnnoExtend(description="默认值")
	private String defaultValue;
	
	@FieldAnnoExtend(description="是否必填",required=true)
	private boolean required;
	
	
	private List<String> valueList=new ArrayList<String>();
	
	@FieldAnnoExtend(type=6,description="对象",foreignModel="CfgFormInfo")
	@Reference(lazy = true,ignoreMissing=true)  
	private CfgFormInfo formInfo;
	
	@FieldAnnoExtend(description="是否用于搜索",required=true)
	private boolean useQuery=false;
	
	
	
	
	
	@FieldAnnoExtend(description="是否多选")
	private boolean multiple;
	@FieldAnnoExtend(description="最大长度")
	private long maxlength;
	@FieldAnnoExtend(description="最小长度")
	private long mimlength;
	@FieldAnnoExtend(description="最大值")
	private long max;
	@FieldAnnoExtend(description="最小值")
	private long min;
	
	private long library;
	private boolean overt=false;
	
	
	
	public long getMimlength() {
		return mimlength;
	}
	public void setMimlength(long mimlength) {
		this.mimlength = mimlength;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public CfgFormInfo getFormInfo() {
		return formInfo;
	}
	public void setFormInfo(CfgFormInfo formInfo) {
		this.formInfo = formInfo;
	}
	public long getLibrary() {
		return library;
	}
	public void setLibrary(long library) {
		this.library = library;
	}
	public boolean isOvert() {
		return overt;
	}
	public void setOvert(boolean overt) {
		this.overt = overt;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public boolean isUseQuery() {
		return useQuery;
	}
	public void setUseQuery(boolean useQuery) {
		this.useQuery = useQuery;
	}
	public long getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(long maxlength) {
		this.maxlength = maxlength;
	}
	

	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	public static void main(String[] args) {
		Datastore ds=SpringContext.getDatastore();
		List<CfgCustomProperty> b= ds.find(CfgCustomProperty.class).asList();
		for(CfgCustomProperty one:b){
			//one.setApplyToLibrarys(ListUtil.pasreList(new Long[]{1l}));
			//one.setFormId(3l);
			ds.save(one);
		}
		
		
	}
	
}
