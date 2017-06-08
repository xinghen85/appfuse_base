package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;

import com.btxy.basis.cache.model.AuthPrivilegeView;
import com.btxy.basis.common.model.OrderedMap;
import com.btxy.basis.model.AuthAppRole;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.model.AuthUser;
import com.btxy.basis.service.base.MgGenericManager;
import com.btxy.basis.util.map.MapUtil;

public class AuthUserCache<T extends AuthUser>{
	
	////////////////////////////////////////////////////////////////////////////////////
	private T user;
	private OrderedMap<Long,AuthPrivilegeInfo> privilegeMap=new OrderedMap<Long,AuthPrivilegeInfo>();
	private List<AuthPrivilegeView> treeList=new ArrayList<AuthPrivilegeView>();
	private Map<String,List<AuthPrivilegeInfo>> buttonGroupMap=new HashMap<String,List<AuthPrivilegeInfo>>();
	

	
	public AuthUserCache(MgGenericManager<T, Long> authOrgUserManager,Long userId,Long library){
		init(authOrgUserManager,userId, library);
	}	
	private void init(MgGenericManager<T, Long> authOrgUserManager,Long userId,Long library){
		user=authOrgUserManager.get(userId);
		List<Long> lroles=user.getRoleList(library);
		for(Long roleId:lroles){
			AuthAppRole role=AuthAppRoleCache.getInstance(library).getEntityById(roleId);
			for(Long id1:role.getPrivilegeList()){
				AuthPrivilegeInfoCache authPrivilegeInfoCache=AuthPrivilegeInfoCache.getInstance();
				AuthPrivilegeInfo p=authPrivilegeInfoCache.getEntityById(id1);
				if(p!=null){
					privilegeMap.put(p.getPrivilegeId(), p);
				}
			}
		}
		initPtree();
	}
	private void initPtree(){
		List<AuthPrivilegeInfo> list=privilegeMap.getList();
    	Map<Long,List<AuthPrivilegeInfo>> map=new HashMap<Long,List<AuthPrivilegeInfo>>();

    	for (AuthPrivilegeInfo one : list) {
    		if(one.getFormId()!=0){
    			one.setForm(CfgFormInfoCache.getInstance().getEntityById(one.getFormId()));
    		}
    		if(one.getMachineId()!=0){
    			one.setStateMachineDefine(CfgStateMachineDefineCache.getInstance().getEntityById(one.getMachineId()));
    		}
    	}
    	for (AuthPrivilegeInfo one : list) {
   		 	MapUtil.appendListEntityToMap(map, one.getParent(), one);
    	}
    	initTree(treeList,0l,map);

    	for (AuthPrivilegeInfo one : list) {
    		if(one.isIfButtonGroup()){
    			if(one.getParent()!=null){
    				AuthPrivilegeInfo a1=privilegeMap.get(one.getParent());
    				if(a1!=null && a1.getForm()!=null){
    					MapUtil.appendListEntityToMap(buttonGroupMap, a1.getForm().getFormCode(), one);
    				}
    			}
    		}
    	}
    	sortTree(treeList);
	}
	private void sortTree(List<AuthPrivilegeView> list){
		Collections.sort(list, new ComparatorAuthPrivilege());
		for(AuthPrivilegeView one:list){
			sortTree(one.getChilden());
    	}
	}

	private void initTree(List<AuthPrivilegeView> array,Long parentId,Map<Long,List<AuthPrivilegeInfo>> map){
		if(map.containsKey(parentId)){
	    	for(AuthPrivilegeInfo one:map.get(parentId)){
	    		AuthPrivilegeView authPrivilegeView=new AuthPrivilegeView();
	    		authPrivilegeView.setAuthPrivilegeInfo(one);
				array.add(authPrivilegeView);
				initTree(authPrivilegeView.getChilden(),one.getPrivilegeId(),map);
			}
		}else{
			Log.info(parentId);
		}
    }
	

	public List<AuthPrivilegeInfo> getButtonGroupByFormCode(String formName){
		return buttonGroupMap.get(formName);
	}

	public T getUser() {
		return user;
	}

	public List<AuthPrivilegeView> getTreeList() {
		return treeList;
	}

	public static class ComparatorAuthPrivilege implements Comparator<AuthPrivilegeView> {

		@Override
		public int compare(AuthPrivilegeView user0, AuthPrivilegeView user1) {
			// 首先比较年龄，如果年龄相同，则比较名字

			int flag = new Double(user0.getAuthPrivilegeInfo().getSortNo()).compareTo(user1.getAuthPrivilegeInfo().getSortNo());
			if (flag == 0) {
				return user0.getAuthPrivilegeInfo().getPrivilegeId().compareTo(user1.getAuthPrivilegeInfo().getPrivilegeId());
			} else {
				return flag;
			}
		}

	}
}
