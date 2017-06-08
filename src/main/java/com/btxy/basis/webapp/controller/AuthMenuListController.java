package com.btxy.basis.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;
import com.btxy.basis.cache.cfg.AuthShortCutPrivilegeCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.cache.model.AuthPrivilegeView;
import com.btxy.basis.common.CurrentUserUtil;
import com.btxy.basis.common.model.CurrentUserPrivilegeMap;
import com.btxy.basis.model.AuthShortCutPrivilege;
import com.btxy.basis.model.AuthUser;
import com.btxy.basis.model.AuthUserLibraryRole;
import com.btxy.basis.model.LibraryInfo;
import com.btxy.basis.model.StSystemNoticeInfo;
import com.btxy.basis.service.st.StSystemNoticeInfoManager;

@Controller
public class AuthMenuListController extends BaseFormController {
	

	@Autowired
	private StSystemNoticeInfoManager stSystemNoticeInfoManager;


	@RequestMapping(value = "/mainMenu*", method = RequestMethod.GET)
	public ModelAndView mainMenu(HttpServletRequest request){
		Model model = new ExtendedModelMap(); 
		AuthUser user = CurrentUserUtil.getCurrentUser();
		
		LibraryInfo libraryInfo=LibraryInfoCache.getInstance().getLibraryInfoById(user.getDefaultLibrary());
		if(libraryInfo!=null){
			return new ModelAndView("redirect:/lb/"+libraryInfo.getPath()+"/mainMenu", model.asMap());
		}else{
			return new ModelAndView("redirect:/lb/-1/mainMenu", model.asMap());
		}
	}

	@RequestMapping(value = "/lb/{libraryPath}/mainMenu*", method = RequestMethod.GET)
	public ModelAndView mainMenuOfLibrary(@PathVariable String libraryPath,HttpServletRequest request){
		Model model = new ExtendedModelMap();

		AuthUser user = CurrentUserUtil.getCurrentUser();

		model.addAttribute("libraryPath", libraryPath);
		model.addAttribute("currentUser", user);

		LibraryInfo currentLibrary=LibraryInfoCache.getInstance().getLibraryInfoByPath(libraryPath);
		{
			List<AuthPrivilegeView> list1 =Cache.getAppInstance(user.getUserId(), currentLibrary.getLibraryId()).getTreeList();
			model.addAttribute("userAuthPrivilegeViewTree", list1);
			for(AuthPrivilegeView one:list1){
				if(one.getAuthPrivilegeInfo().getPrivilegeId().longValue()==1199l){
					model.addAttribute("currentPrivilegeTreeCache", one.getChilden());
				}
			}
		}
		{
			List<AuthShortCutPrivilege> list = AuthShortCutPrivilegeCache.getInstance().getShortCutPrivilegeListForRole(user.getRoleList(currentLibrary.getLibraryId()));
			for (AuthShortCutPrivilege auth : list) {
				auth.setAuthPrivilegeInfo(AuthPrivilegeInfoCache.getInstance().getEntityById(auth.getPrivilegeId()));
			}
			model.addAttribute("AuthShortCutPrivilegeList", list);
		}


		List<StSystemNoticeInfo> noticces = stSystemNoticeInfoManager.getNoticeByUser(user,currentLibrary.getLibraryId());

		model.addAttribute("SystemNoticeList", noticces);

		//获取所参与的库
		
		List<AuthUserLibraryRole> ulrList=user.getLibraryRoleList();
		
		List<LibraryInfo> otherLibraryList=new ArrayList<LibraryInfo>();
		if(ulrList.size()>0){
			for(AuthUserLibraryRole one:ulrList){
				if(one.getLibraryId()!=null){
					LibraryInfo l=LibraryInfoCache.getInstance().getLibraryInfoById(one.getLibraryId());
					if(l!=null && l.getLibraryId().longValue()!=currentLibrary.getLibraryId().longValue()){
						otherLibraryList.add(l);
					}
				}
			}
		}else{
			LibraryInfo l=LibraryInfoCache.getInstance().getLibraryInfoById(user.getLibrary());
			if(l!=null && l.getLibraryId().longValue()!=currentLibrary.getLibraryId().longValue()){
				otherLibraryList.add(l);
			}
		}
		model.addAttribute("currentLibrary", currentLibrary);
		model.addAttribute("otherLibraryList", otherLibraryList);
		
		initSessionPrivilegeMap(request,user,currentLibrary.getLibraryId());
		
		return new ModelAndView("main/mainCanvas3.0", model.asMap());
	}
	@RequestMapping(value = "/lb/{libraryPath}/menuList/root/{parentId}/php*", method = RequestMethod.GET)
	public ModelAndView viewByRootId(@PathVariable String libraryPath,@PathVariable Long parentId, HttpServletRequest request)
			throws Exception {
		Model model = new ExtendedModelMap();

		model.addAttribute("libraryPath", libraryPath);

		LibraryInfo currentLibrary=LibraryInfoCache.getInstance().getLibraryInfoByPath(libraryPath);
		AuthUser user = CurrentUserUtil.getCurrentUser();
		
		List<AuthPrivilegeView> listAll = Cache.getAppInstance(user.getUserId(), currentLibrary.getLibraryId()).getTreeList();

		List<AuthPrivilegeView> currentPrivilegeTreeCache = new ArrayList<AuthPrivilegeView>();

		for (AuthPrivilegeView one : listAll) {
			if ("AWA".equals(one.getAuthPrivilegeInfo().getPrivilegeType())) {
				if (parentId!=null && parentId.longValue()==one.getAuthPrivilegeInfo().getPrivilegeId().longValue()) {
					currentPrivilegeTreeCache.addAll(one.getChilden());

					model.addAttribute("rootMenuName", one.getAuthPrivilegeInfo().getPrivilegeName());
				}
				
			}
		}
		model.addAttribute("currentPrivilegeTreeCache",
				currentPrivilegeTreeCache);

		initSessionPrivilegeMap(request,user,currentLibrary.getLibraryId());
		
		return new ModelAndView("main/menu3.0", model.asMap());
	}
	private void initSessionPrivilegeMap(HttpServletRequest request,AuthUser user,Long library){
		HttpSession session = request.getSession();
		
		if (session.getAttribute("currentUserPrivilegeMap") == null) {
			Map<String, Boolean> privilegeMap = new HashMap<String, Boolean>();
			CurrentUserPrivilegeMap cpm = new CurrentUserPrivilegeMap();
			List<AuthPrivilegeView> list =Cache.getAppInstance(user.getUserId(), library).getTreeList();
			if (list != null) {
				initPrivilegeMap(list, privilegeMap);
			}
			cpm.setUser(user);
			cpm.setPrivilegeMap(privilegeMap);
			session.setAttribute("currentUserPrivilegeMap", cpm);
			
			session.setAttribute("ifSuperAdmin", "0");
			List<AuthUserLibraryRole> lrlist=user.getLibraryRoleList();
			if(lrlist!=null){
				for(AuthUserLibraryRole one:lrlist){
					if(one.getRoleList()!=null){
						for(Long r:one.getRoleList()){
							if(r.longValue()==1){
								session.setAttribute("ifSuperAdmin", "1");
							}
						}
					}
				}
			}
		}
	}
	private void initPrivilegeMap(List<AuthPrivilegeView> list,Map<String, Boolean> privilegeMap) {
		if (list != null) {
			for (AuthPrivilegeView one : list) {
				if (one.getAuthPrivilegeInfo() != null
						&& "AWC".equals(one.getAuthPrivilegeInfo()
								.getPrivilegeType())) {
					if ("AZA".equals(one.getAuthPrivilegeInfo()
							.getOperateName())
							&& one.getAuthPrivilegeInfo().getForm() != null) {
						privilegeMap.put(one.getAuthPrivilegeInfo().getForm()
								.getFormCode()
								+ "-list", true);
					} else if ("AZB".equals(one.getAuthPrivilegeInfo()
							.getOperateName())
							&& one.getAuthPrivilegeInfo().getForm() != null) {
						privilegeMap.put(one.getAuthPrivilegeInfo().getForm()
								.getFormCode()
								+ "-add", true);
					} else if ("AZC".equals(one.getAuthPrivilegeInfo()
							.getOperateName())
							&& one.getAuthPrivilegeInfo().getForm() != null) {
						privilegeMap.put(one.getAuthPrivilegeInfo().getForm()
								.getFormCode()
								+ "-edit", true);
					} else if ("AZD".equals(one.getAuthPrivilegeInfo()
							.getOperateName())
							&& one.getAuthPrivilegeInfo().getForm() != null) {
						privilegeMap.put(one.getAuthPrivilegeInfo().getForm()
								.getFormCode()
								+ "-delete", true);
					} else if ("AZE".equals(one.getAuthPrivilegeInfo()
							.getOperateName())
							&& one.getAuthPrivilegeInfo().getForm() != null) {
						privilegeMap.put(one.getAuthPrivilegeInfo().getForm()
								.getFormCode()
								+ "-view", true);
					}
				}
				initPrivilegeMap(one.getChilden(), privilegeMap);
			}

		}
	}
	/*
	 * @RequestMapping(value =
	 * "/lb/{libraryPath}/authPrivilegeInfo/list/select/php*") public void
	 * getSelect2Json(@PathVariable String libraryPath,HttpServletResponse
	 * response)throws Exception { List<AuthPrivilegeInfo>
	 * list=authPrivilegeInfoManager.getAll(); Map<Long,List<AuthPrivilegeInfo>>
	 * map=new HashMap<Long,List<AuthPrivilegeInfo>>(); JSONArray array=new
	 * JSONArray(); for(int i=0;list!=null && i<list.size();i++){
	 * MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
	 * } initTree("",0l,array,map); returnJSON(array,response); }
	 */
/*
 * 
@RequestMapping(value = "/lb/{libraryPath}/menuList/{menuType}/php*", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable String libraryPath,
			@PathVariable String menuType, HttpServletRequest request)
			throws Exception {

		Model model = new ExtendedModelMap();

		model.addAttribute("libraryPath", libraryPath);

		LibraryInfo currentLibrary=LibraryInfoCache.getInstance().getLibraryInfoByPath(libraryPath);
		AuthAppUser user = CurrentUserUtil.getCurrentUser();
		
		List<AuthPrivilegeView> listAll = AuthAppUserCache.getInstance(user.getUserId(), currentLibrary.getLibraryId()).getTreeList();//AuthPrivilegeInfoCache.getInstance().getTreeList();

		List<AuthPrivilegeView> currentPrivilegeTreeCache = new ArrayList<AuthPrivilegeView>();

		if (listAll != null) {
			for (AuthPrivilegeView one : listAll) {
				if ("AWA".equals(one.getAuthPrivilegeInfo().getPrivilegeType())) {
					if ("work_console".equals(menuType)
							&& "工作台".equals(one.getAuthPrivilegeInfo()
									.getPrivilegeName())) {
						currentPrivilegeTreeCache.addAll(one.getChilden());

						model.addAttribute("rootMenuName", "工作台");
					}
					if ("profile".equals(menuType)
							&& "用户中心".equals(one.getAuthPrivilegeInfo()
									.getPrivilegeName())) {
						currentPrivilegeTreeCache.addAll(one.getChilden());

						model.addAttribute("rootMenuName", "用户中心");
					}
					if ("settings".equals(menuType)
							&& "系统配置".equals(one.getAuthPrivilegeInfo()
									.getPrivilegeName())) {
						currentPrivilegeTreeCache.addAll(one.getChilden());

						model.addAttribute("rootMenuName", "系统配置");
					}
					if ("super_settings".equals(menuType)
							&& "超级管理权限".equals(one.getAuthPrivilegeInfo()
									.getPrivilegeName())) {
						currentPrivilegeTreeCache.addAll(one.getChilden());

						model.addAttribute("rootMenuName", "超级管理权限");
					}
				}
			}
		}
		model.addAttribute("currentPrivilegeTreeCache",
				currentPrivilegeTreeCache);

		HttpSession session = request.getSession();

		if (session.getAttribute("currentUserPrivilegeMap") == null) {
			Map<String, Boolean> privilegeMap = new HashMap<String, Boolean>();
			CurrentUserPrivilegeMap cpm = new CurrentUserPrivilegeMap();
			List<AuthPrivilegeView> list = AuthPrivilegeInfoCache.getInstance()
					.getTreeList();
			if (list != null) {
				initPrivilegeMap(list, privilegeMap);
			}
			cpm.setUser(CurrentUserUtil.getCurrentUser());
			cpm.setPrivilegeMap(privilegeMap);
			session.setAttribute("currentUserPrivilegeMap", cpm);
		}
		return new ModelAndView("main/menu3.0", model.asMap());
	}
 * 
 */
}
