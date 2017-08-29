package com.btxy.basis.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgEnumValueInfo;
/**
 * 
 * @author Administrator
 *
 */
public class Constant {
	public static final String SERVER_TYPE_BMS="BMS";
	public static final String SERVER_TYPE_EPG="EPG";
	public static final String SERVER_TYPE_UNKNOWE="unknowe";
	
	public static final String ABNORMAL_TYPE_OFFLINE="OFFLINE";
	public static final String ABNORMAL_TYPE_JMXBLOCKED="JMXBLOCKED";
	public static final String ABNORMAL_TYPE_LOADOVERTOP="LOADOVERTOP";
	
	/******是否状态******/
    public static final String TRUE = "10A";//是
    public static final String FALSE = "10B";//否
	/******用户自定义属性数据类型******/
    public static final String PROPERTY_DATA_TYPE_BOOLEAN = "AVA";//布尔型
    public static final String PROPERTY_DATA_TYPE_LONG = "AVB";//整型
    public static final String PROPERTY_DATA_TYPE_DOUBLE = "AVC";//浮点型
    public static final String PROPERTY_DATA_TYPE_STRING = "AVD";//字符串
    public static final String PROPERTY_DATA_TYPE_TEXT = "AVE";//文本型
    public static final String PROPERTY_DATA_TYPE_ENUM = "AVF";//列表
    public static final String PROPERTY_DATA_TYPE_DATE = "AVG";//日期
    public static final String PROPERTY_DATA_TYPE_EMAIL = "AVH";//Email
    /******权限类型******/
    public static final String PVIVILEGE_TYPE_CATALOG = "AWA";//目录
    public static final String PVIVILEGE_TYPE_MENU = "AWB";//菜单项
    public static final String PVIVILEGE_TYPE_OPERATOR = "AWC";//操作项
    /******固有属性值类型******/
    public static final String FIXED_PROPERTY_VALUE_TYPE_LIST = "AXA";//列表
    public static final String FIXED_PROPERTY_VALUE_TYPE_MULITI_LIST = "AXB";//二级列表
    public static final String FIXED_PROPERTY_VALUE_TYPE_TREE_LIST = "AXC";//树列表
   
    /******权限操作类型******/
    public static final String PVIVILEGE_OPERATE_TYPE_LIST = "AZA";//列表
    public static final String PVIVILEGE_OPERATE_TYPE_ADD = "AZB";//添加
    public static final String PVIVILEGE_OPERATE_TYPE_EDIT = "AZC";//修改
    public static final String PVIVILEGE_OPERATE_TYPE_DELETE = "AZD";//删除
    public static final String PVIVILEGE_OPERATE_TYPE_VIEW = "AZE";//查看
    
    /******Form对象数据变化拦截类型******/
    public static final String FORM_DATA_CHANGE_DOTYPE_NONE = "BBA";//None
    public static final String FORM_DATA_CHANGE_DOTYPE_COMMON = "BBB";//通用拦截
    public static final String FORM_DATA_CHANGE_DOTYPE_SPECIAL = "BBC";//自定义拦截
    
    /******任务状态******/
    public static final String TASK_STATUS_NEW = "BGA";//新建
    public static final String TASK_STATUS_WAITING = "BGB";//队列等待
    public static final String TASK_STATUS_RUNNING = "BGC";//执行中
    public static final String TASK_STATUS_DONE = "BGD";//执行成功
    public static final String TASK_STATUS_FAILURE = "BGE";//执行失败
    
    
    
    /******函数定义状态*****/
    public static final String FUNDEFINE_STATUS_NEW = "9RA";//新建
    public static final String FUNDEFINE_STATUS_PENDING_AUDIT = "9RB";//待审核
    public static final String FUNDEFINE_STATUS_AUDITING = "9RC";//审核中
    public static final String FUNDEFINE_STATUS_OK = "9RD";//审核通过
    public static final String FUNDEFINE_STATUS_NOT_OK = "9RE";//审核不通过
    public static final String FUNDEFINE_STATUS_ONLINE = "9RF";//已发布
    public static final String FUNDEFINE_STATUS_OFFLINE = "9RG";//已下线
	
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//SpringContext.getDatastore();
		Datastore ds=SpringContext.getDatastore();
		
		Map<String,String> map=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		List<CfgEnumInfo> list=ds.find(CfgEnumInfo.class).asList();
	    for(CfgEnumInfo one:list){
	    	if(!map.containsKey(one.getEnumId())){
	    		if(one.getConstantName()!=null && !"".equals(one.getConstantName().trim()) && one.getEnumCode()!=null && !"".equals(one.getEnumCode().trim())){
	    			
	    			if(one.getValues()!=null && one.getValues().size()>0){
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
