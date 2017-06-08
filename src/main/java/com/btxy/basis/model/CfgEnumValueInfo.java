package com.btxy.basis.model;

import java.io.Serializable;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.SequenceUtil;

@TableAnnoExtend(description="枚举变量值",textSearch=true,combinedSearch=false,parent="CfgEnumInfo")
@javax.persistence.Entity
@javax.persistence.Table(name="CfgEnumValueInfo",catalog="bsquiz")
public class CfgEnumValueInfo implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	

	public Long getEnumValueId() {
		return enumValueId;
	}

	public void setEnumValueId(Long enumValueId) {
		this.enumValueId = enumValueId;
	}

	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="枚举变量值ID")
	private Long enumValueId;
	@FieldAnnoExtend(description="枚举变量值编码",required=true)
	private String code;
	@FieldAnnoExtend(description="枚举变量值名称",required=true)
	private String value;
	@FieldAnnoExtend(description="Java常量名称",required=true)
	private String constantName;

	@Transient
	@javax.persistence.Transient
	private String fullCode;
	
	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getConstantName() {
		return constantName;
	}

	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}

	public static void main(String[] args) {
		Datastore ds=SpringContext.getDatastore();
		
		for(CfgEnumInfo one:ds.find(CfgEnumInfo.class).asList()){
			for(CfgEnumValueInfo  t:one.getValues()){
				System.out.println(one.getEnumId()+","+t.getEnumValueId());
				//t.setEnumValueId(SequenceUtil.getNext(CfgEnumValueInfo.class));
			}
			ds.save(one);
		}
		//ds.createQuery(CfgEnumInfo.class).
		/*CfgEnumInfo cfi=new CfgEnumInfo();
		//System.out.println(cfi.getFormName());
		cfi.setEnumId(SequenceUtil.getNext(CfgEnumInfo.class));
		CfgEnumValueInfo cevi=new CfgEnumValueInfo();
		cevi.setId(SequenceUtil.getNext(CfgEnumValueInfo.class));
		cevi.setValue("1111");
		
		cfi.getValues().add(cevi);*/
		
	}	
	
}
