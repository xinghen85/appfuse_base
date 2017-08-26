package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.btxy.basis.common.SpringContext;

@Entity(value="cfg_enum_info", noClassnameStored=true) 
@TableAnnoExtend(description="枚举变量",textSearch=true,combinedSearch=false,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="CfgEnumInfo",catalog="bsquiz")
public class CfgEnumInfo implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="枚举ID")
	private Long enumId;
	@FieldAnnoExtend(description="枚举名称",required=true,name=true)
	private String enumName;
	@FieldAnnoExtend(description="枚举编码",required=true)
	private String enumCode;
	
	//private boolean editable;
	
	@FieldAnnoExtend(description="Java常量名称",required=true)
	private String constantName;
	
	@FieldAnnoExtend(type=5,childModel="CfgEnumValueInfo")
	List<CfgEnumValueInfo> values=new ArrayList<CfgEnumValueInfo>();

	public Long getEnumId() {
		return enumId;
	}

	public void setEnumId(Long enumId) {
		this.enumId = enumId;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getEnumCode() {
		return enumCode;
	}

	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}



	public String getConstantName() {
		return constantName;
	}

	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}

	public List<CfgEnumValueInfo> getValues() {
		return values;
	}

	public void setValues(List<CfgEnumValueInfo> values) {
		if(values!=null){
			this.values = values;
		}
	}


	public static void main(String[] args) {
		
		//SpringContext.getDatastore();
		Datastore ds=SpringContext.getDatastore();
		
		Map<String,String> map=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		List<CfgEnumInfo> list=ds.find(CfgEnumInfo.class).asList();
	    for(CfgEnumInfo one:list){
	    	if(!map.containsKey(one.getEnumId())){
	    		if(one.getConstantName()!=null && !"".equals(one.getConstantName().trim()) && one.getEnumCode()!=null && !"".equals(one.getEnumCode().trim())){
	    			
	    			if(one.getValues().size()>0){
	    				sb.append("    /******"+one.getEnumName()+"******/\r\n");
	    				for(CfgEnumValueInfo v1:one.getValues()){
	    					if(v1.getConstantName()!=null && v1.getCode()!=null ){
		    					sb.append("    public static final String "+one.getConstantName()+"_"+v1.getConstantName()+" = \""+one.getEnumCode()+""+v1.getCode()+"\";//"+v1.getValue()+"\r\n");
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	    System.out.println(sb.toString());
	}
	
}
