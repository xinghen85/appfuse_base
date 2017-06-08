package com.btxy.basis.webapp.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.CurrentUserUtil;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthUser;
import com.btxy.basis.model.AuthUserLibraryRole;
import com.btxy.basis.service.st.AuthAppUserManager;
import com.btxy.basis.util.zx.LongUtil;
import com.mongodb.MongoException;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
public class UserController extends BaseFormController{
	private static final String DM_FORM_NAME="authAppUser";

    @Autowired
	private AuthAppUserManager authAppUserManager;
    
    @RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/view/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,HttpServletRequest request) {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	
    	AuthUser user1 = CurrentUserUtil.getCurrentUser();
    	AuthAppUser user=authAppUserManager.get(user1.getUserId());
        model.addAttribute("authAppUser", user);
    	model.addAttribute("formEditFlag", false);
        return new ModelAndView("st/AuthAppUserFormForMe", model.asMap());
    }
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/edit/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath) {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	
    	AuthUser user1 = CurrentUserUtil.getCurrentUser();
    	AuthAppUser user=authAppUserManager.get(user1.getUserId());
        model.addAttribute("authAppUser", user);
    	model.addAttribute("formEditFlag", true);
        return new ModelAndView("st/AuthAppUserFormForMe", model.asMap());
    }
	
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/changePassword/php*",method = RequestMethod.GET)
    public ModelAndView changePassword(@PathVariable String libraryPath) {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	
    	AuthUser user1 = CurrentUserUtil.getCurrentUser();
    	
    	AuthAppUser user=authAppUserManager.get(user1.getUserId());
    	user.setPassword("");
    	user.setPasswordHint("");
        model.addAttribute("authAppUser", user);
    	model.addAttribute("formEditFlag", true);   
        return new ModelAndView("st/AuthAppUserFormChangePasswordForMe", model.asMap());
    }
    
	
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/edit/formSubmit/php*",method = RequestMethod.POST)
    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthAppUser authAppUser,  HttpServletRequest request,
                           HttpServletResponse response) {  
    	Model model = new ExtendedModelMap(); 
    	String success = "redirect:/lb/{libraryPath}/authAppUser/currentUser/edit/php";
    	try{
	        
	        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
	        model.addAttribute("formEditFlag", true);
	        model.addAttribute("addFlagOfAuthAppUserForm", request.getParameter("addFlagOfAuthAppUserForm"));
	        
	        
	        if (validator != null) { // validator is null during testing     
	        	ServerValidataResult svr=new ServerValidataResult();      	
	        	validate(authAppUser, svr);      	
	            
	            if (svr.hasErrors()) { // don't validate when deleting
	            	saveError(request,svr.getAllErrorMessage());
	        		return new ModelAndView("st/AuthAppUserForm", model.asMap());
	            }
	        }
	
	        log.debug("entering 'onSubmit' method...");
	        Locale locale = request.getLocale();
	
	        AuthAppUser olduser=authAppUserManager.get(authAppUser.getUserId());
            authAppUser.setPassword(olduser.getPassword());
            authAppUser.setPasswordHint(olduser.getPasswordHint());
            authAppUser.setLibraryRoleList(olduser.getLibraryRoleList());
            
	        
	        authAppUserManager.save(authAppUser);
	        saveMessage(request, getText("authAppUser.updated", locale));
	
    	}catch(MongoException e){
    		e.printStackTrace();
    		saveMessage(request, "修改用户信息失败，"+e.getMessage());
    		
    	}
    	return new ModelAndView(success, model.asMap());
        
    }
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/changePassword/formSubmit/php*",method = RequestMethod.POST)
    public ModelAndView onSubmitChangePassword(@PathVariable String libraryPath,AuthAppUser authAppUser,  HttpServletRequest request,
                           HttpServletResponse response) {  
    	Model model = new ExtendedModelMap(); 
    	String success = "redirect:/lb/{libraryPath}/authAppUser/currentUser/changePassword/php";
    	try{
	        
	        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
	        model.addAttribute("formEditFlag", true);
	        model.addAttribute("addFlagOfAuthAppUserForm", request.getParameter("addFlagOfAuthAppUserForm"));
	        
	        
	        if (validator != null) { // validator is null during testing     
	        	ServerValidataResult svr=new ServerValidataResult();      	
	        	validate(authAppUser, svr);      	
	            
	            if (svr.hasErrors()) { // don't validate when deleting
	            	saveError(request,svr.getAllErrorMessage());
	        		return new ModelAndView("st/AuthAppUserForm", model.asMap());
	            }
	        }
	
	        log.debug("entering 'onSubmit' method...");
		                
	
	        AuthAppUser olduser=authAppUserManager.get(authAppUser.getUserId());
	        String oldPasswrod=authAppUserManager.getEncoderedPasword(authAppUser.getOldPassword());
	        if(oldPasswrod!=null && oldPasswrod.equals(olduser.getPassword())){

	        	olduser.setPassword(authAppUser.getPassword());
	        	olduser.setPasswordHint(authAppUser.getPassword());
		        authAppUserManager.saveForChangePassword(olduser);
		        saveMessage(request,"修改密码成功！");
	        }else{

		        saveMessage(request, "修改密码失败，原密码输入错误！");
	        }
    	}catch(MongoException e){
    		e.printStackTrace();
    		
    		saveMessage(request, "修改用户密码失败，"+e.getMessage());
    		
    	}
    	return new ModelAndView(success, model.asMap());
        
    }
	
	///////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/changeSetting/php*",method = RequestMethod.GET)
    public ModelAndView changeSetting(@PathVariable String libraryPath) {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    	
    	AuthUser user1 = CurrentUserUtil.getCurrentUser();
    	
    	AuthAppUser user=authAppUserManager.get(user1.getUserId());
    	
    	user.setPassword("");
    	user.setPasswordHint("");
       
        model.addAttribute("authAppUser", user);
        
        Long library=LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath);
        AuthUserLibraryRole aulr=null;
        if(user.getLibraryRoleList()!=null){
        	for(AuthUserLibraryRole one:user.getLibraryRoleList()){
        		if(LongUtil.ifEqual(library, one.getLibraryId())){
        			aulr=one;
        			model.addAttribute("addFlagOfAuthUserLibraryRoleForm", "0");
        		}
        	}
        }
        if(aulr==null){
        	aulr=new AuthUserLibraryRole();
        	aulr.setLibraryRoleId(SequenceUtil.getNext(AuthUserLibraryRole.class));   
        	aulr.setLibraryId(library);
        	aulr.setIfDefault(true);
        	aulr.setOvert(false);
        	model.addAttribute("addFlagOfAuthUserLibraryRoleForm", "1");
        }
        model.addAttribute("authUserLibraryRole", aulr);
        
        model.addAttribute("authAppUser", user);
        model.addAttribute("formEditFlag", true);
        
        return new ModelAndView("st/AuthUserLibraryRoleFormForMe", model.asMap());
    }
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/currentUser/changeSetting/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView changeSettingOnSubmit(@PathVariable String libraryPath,AuthUserLibraryRole authUserLibraryRole,HttpServletRequest request,
                           HttpServletResponse response) {                
		AuthUser user1 = CurrentUserUtil.getCurrentUser();
    	
    	AuthAppUser user=authAppUserManager.get(user1.getUserId());
		
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthUserLibraryRoleForm", request.getParameter("addFlagOfAuthUserLibraryRoleForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authUserLibraryRole, svr);      	

            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("st/AuthUserLibraryRoleForm", model.asMap());
            }
        }
        log.debug("entering 'onSubmit' method...");

       	AuthAppUser authAppUser= authAppUserManager.get(user.getUserId());
        
        boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthUserLibraryRoleForm")));
        
        if(isNew){
        	authAppUser.getLibraryRoleList().add(authUserLibraryRole);
        }else{
        	
            List<AuthUserLibraryRole> list=authAppUser.getLibraryRoleList();
            for(AuthUserLibraryRole one:list){
            	if(LongUtil.ifEqual(one.getLibraryRoleId(), authUserLibraryRole.getLibraryRoleId())){
					one.setAdminServerList(authUserLibraryRole.getAdminServerList());	
					one.setDescription(authUserLibraryRole.getDescription());	
					one.setIfDefault(authUserLibraryRole.isIfDefault());	
					one.setLibraryId(authUserLibraryRole.getLibraryId());	
					one.setOvert(authUserLibraryRole.isOvert());	
					one.setReportServerList(authUserLibraryRole.getReportServerList());		
					one.setRoleList(authUserLibraryRole.getRoleList());						
					
            		break;
            	}
            }
              	
        }
        String success = "redirect:/lb/{libraryPath}/authAppUser/currentUser/changeSetting/php";
      
        authAppUserManager.save(authAppUser);

        saveMessage(request, "修改个人配置成功");

        return new ModelAndView(success, model.asMap());
    }
}
