package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.List;

import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.model.AuthShortCutPrivilege;
import com.btxy.basis.util.zx.LongUtil;

public class AuthShortCutPrivilegeCache extends BaseCache<AuthShortCutPrivilege, Long> {

	public static AuthShortCutPrivilegeCache getInstance() {
		return (AuthShortCutPrivilegeCache) getInstance(AuthShortCutPrivilegeCache.class);
	}

	public AuthShortCutPrivilegeCache() {
		super(AuthShortCutPrivilege.class, false);
		init();
	}

	private void init() {
		List<AuthShortCutPrivilege> list = dao.createQuery().order("order").asList();

		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getShortCutId(), list.get(i));
		}
	}

	public List<AuthShortCutPrivilege> getShortCutPrivilegeListForRole(List<Long> roleList) {
		List<AuthShortCutPrivilege> list = new ArrayList<AuthShortCutPrivilege>();
		if (super.getList() != null) {
			for (AuthShortCutPrivilege one : super.getList()) {
				if (ifEqual(roleList, one.getRoleList())) {
					list.add(one);
				}
			}
		}

		return list;
	}

	static private boolean ifEqual(List<Long> roleList1, List<Long> roleList2) {
		if (roleList1 != null && roleList2 != null) {
			for (Long role1 : roleList1) {
				for (Long role2 : roleList2) {
					if (LongUtil.ifEqual(role1, role2)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
