package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.btxy.basis.cache.model.AuthPrivilegeView;
import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.cache.model.PrivilegeLongVO;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.util.map.MapUtil;

public class AuthPrivilegeInfoCache extends BaseCache<AuthPrivilegeInfo,Long>{
	
	public static  AuthPrivilegeInfoCache getInstance(){
		return (AuthPrivilegeInfoCache) getInstance(AuthPrivilegeInfoCache.class);	
	}
	
	///////////////////////////////////////////////////////////////////////////////
	public AuthPrivilegeInfoCache() {
		
		super(AuthPrivilegeInfo.class,false);
		init_super();
		init();
		initTableTree();
	}

	private void init_super(){
		List<AuthPrivilegeInfo> list=dao.createQuery().order("parent, sortNo").asList();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				map.put(list.get(i).getPrivilegeId(), list.get(i));
			}
		}
	}
	List<AuthPrivilegeView> treeList=new ArrayList<AuthPrivilegeView>();
	public List<AuthPrivilegeView> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<AuthPrivilegeView> treeList) {
		this.treeList = treeList;
	}
	
	Map<String,List<AuthPrivilegeInfo>> buttonGroupMap=new HashMap<String,List<AuthPrivilegeInfo>>();
	private void init(){
		List<AuthPrivilegeInfo> list=super.getList();
    	Map<Long,List<AuthPrivilegeInfo>> map=new HashMap<Long,List<AuthPrivilegeInfo>>();
    	
    	for(int i=0;list!=null && i<list.size();i++){
    		AuthPrivilegeInfo one=list.get(i);
    		if(one.getFormId()!=null && one.getFormId().longValue()!=0){
    			one.setForm(CfgFormInfoCache.getInstance().getEntityById(one.getFormId()));
    		}
    		if(one.getMachineId()!=null && one.getMachineId().longValue()!=0){
    			one.setStateMachineDefine(CfgStateMachineDefineCache.getInstance().getEntityById(one.getMachineId()));
    		}
    	}
    	for(int i=0;list!=null && i<list.size();i++){
    		 MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
    	}
    	initTree(0l,treeList,map);
		
    	for(int i=0;list!=null && i<list.size();i++){
    		AuthPrivilegeInfo one=list.get(i);
    		if(one.isIfButtonGroup()){
    			if(one.getParent()!=null){
    				AuthPrivilegeInfo a1=super.getEntityById(one.getParent());
    				if(a1!=null && a1.getForm()!=null){
    					MapUtil.appendListEntityToMap(buttonGroupMap, a1.getForm().getFormCode(), one);
    				}
    			}
    		}
    	}
	}
	
	
	public List<AuthPrivilegeInfo> getButtonGroupByFormCode(String formName){
		return buttonGroupMap.get(formName);
	}
	
	
	private void initTree(Long parentId,List<AuthPrivilegeView> array,Map<Long,List<AuthPrivilegeInfo>> map){
    	List<AuthPrivilegeInfo> list1=map.get(parentId);
    	if(list1!=null){
    		for(AuthPrivilegeInfo one:list1){
    			AuthPrivilegeView av=new AuthPrivilegeView();
    			av.setAuthPrivilegeInfo(one);
    			array.add(av);
    			initTree(one.getPrivilegeId(),av.getChilden(),map);
    		}
    	}
    }
	PrivilegeLongVO maxCol=new PrivilegeLongVO(0l);
	PrivilegeLongVO maxRow=new PrivilegeLongVO(0l);
	Map<String,List<AuthPrivilegeView>> privilegeMap=new HashMap<String,List<AuthPrivilegeView>>();
	private void initTableTree(){
		List<AuthPrivilegeView> list= treeList;
		 
		 
		 maxCol=new PrivilegeLongVO(0l);
		 Integer col=1;
		 
		 for(AuthPrivilegeView one:list){
			 one.getAuthPrivilegeInfo().setCol(col);
			 if(maxCol.getValue().intValue()<col){
				 maxCol.setValue(col.longValue());
			 }
			 if(one.getChilden()==null || one.getChilden().size()==0){
				 one.getAuthPrivilegeInfo().setIfLeaf(true);
			 }else{
				 one.getAuthPrivilegeInfo().setIfLeaf(false);
				 int rowsize=initTree1(one,col+1,maxCol);
				 one.getAuthPrivilegeInfo().setAllChildrenRowSize(rowsize);
			 }
			 
		 }
		 privilegeMap=new HashMap<String,List<AuthPrivilegeView>>();
		 
		 maxRow=new PrivilegeLongVO(0l);
		 Integer row=1;
		 for(AuthPrivilegeView one:list){
			 one.getAuthPrivilegeInfo().setRow(row);
			 if(maxRow.getValue().intValue()<row){
				 maxRow.setValue(row.longValue());
			 }
			 String key=row+"-"+one.getAuthPrivilegeInfo().getCol();
			 MapUtil.appendListEntityToMap(privilegeMap, key, one);
			 initTree2(one,privilegeMap,maxRow);
			 row=row+(one.getAuthPrivilegeInfo().getAllChildrenRowSize()>0?one.getAuthPrivilegeInfo().getAllChildrenRowSize():1);
			 
		 }
	}
	private int initTree1(AuthPrivilegeView parent,Integer col,PrivilegeLongVO maxCol){
		 int totalRowsize=0;
		 for(AuthPrivilegeView one:parent.getChilden()){
			 one.getAuthPrivilegeInfo().setCol(col);
			 if(maxCol.getValue().intValue()<col){
				 maxCol.setValue(col.longValue());
			 }
			 if(one.getChilden()==null || one.getChilden().size()==0){
				 one.getAuthPrivilegeInfo().setIfLeaf(true);
				 parent.getLeafChilden().add(one);
			 }else{
				 one.getAuthPrivilegeInfo().setIfLeaf(false);
				 parent.getNotLeafChilden().add(one);
				 int rowsize=initTree1(one,col+1,maxCol);
				 one.getAuthPrivilegeInfo().setAllChildrenRowSize(rowsize);
				 totalRowsize=totalRowsize+rowsize;
			 }
			 
			 
		 }
		 if(parent.getLeafChilden()!=null && parent.getLeafChilden().size()>0){
			 totalRowsize=totalRowsize+1;
		 } 
		 
		 return totalRowsize;
	 }
	 private void initTree2(AuthPrivilegeView parent,Map<String,List<AuthPrivilegeView>> map,PrivilegeLongVO maxRow){
		 Integer row=parent.getAuthPrivilegeInfo().getRow();
		 if(parent.getNotLeafChilden()!=null && parent.getNotLeafChilden().size()>0){
			 for(AuthPrivilegeView one:parent.getNotLeafChilden()){
				 one.getAuthPrivilegeInfo().setRow(row);
				 if(maxRow.getValue().intValue()<row){
					 maxRow.setValue(row.longValue());
				 }
				 String key=row+"-"+one.getAuthPrivilegeInfo().getCol();
				 MapUtil.appendListEntityToMap(map, key, one);
				 
				 initTree2(one,map,maxRow);
				 row=row+(one.getAuthPrivilegeInfo().getAllChildrenRowSize()>0?one.getAuthPrivilegeInfo().getAllChildrenRowSize():1);
			 }
		 }
		 if(parent.getLeafChilden()!=null && parent.getLeafChilden().size()>0){
			 for(AuthPrivilegeView one:parent.getLeafChilden()){
				 one.getAuthPrivilegeInfo().setRow(row);
				 if(maxRow.getValue().intValue()<row){
					 maxRow.setValue(row.longValue());
				 }
				 
			 }
			 String key=row+"-"+parent.getLeafChilden().get(0).getAuthPrivilegeInfo().getCol();
			 map.put(key, parent.getLeafChilden());
			 row=row+1;
		 }
	 }

	public PrivilegeLongVO getMaxCol() {
		return maxCol;
	}

	public PrivilegeLongVO getMaxRow() {
		return maxRow;
	}

	public Map<String, List<AuthPrivilegeView>> getTableTreePrivilegeMap() {
		return privilegeMap;
	}

	 
}
