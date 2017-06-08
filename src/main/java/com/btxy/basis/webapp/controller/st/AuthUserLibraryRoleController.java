
package com.btxy.basis.webapp.controller.st;

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

import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthUserLibraryRole;
import com.btxy.basis.service.st.AuthAppUserManager;
import com.btxy.basis.util.zx.LongUtil;
import com.btxy.basis.webapp.controller.BaseFormController;

@Controller
public class AuthUserLibraryRoleController extends BaseFormController{
	private static final String DM_FORM_NAME="authUserLibraryRole";

    private AuthAppUserManager authAppUserManager;

    @Autowired
    public void setAuthAppUserManager(AuthAppUserManager authAppUserManager) {
        this.authAppUserManager = authAppUserManager;
    }
    public AuthUserLibraryRoleController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/list/authAppUser/{parentId}/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request)throws Exception {	
  		return list(libraryPath,parentId,"mt",request);
  	}
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/list/authAppUser/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable String listFlag,HttpServletRequest request)throws Exception {
        Model model = new ExtendedModelMap();
        model.addAttribute("listFlag",listFlag);
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        if (parentId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get(parentId);
            model.addAttribute("authAppUser", authAppUser);
        }
        return new ModelAndView("st/AuthUserLibraryRoleList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/view/{libraryRoleId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long libraryRoleId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (libraryRoleId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get(libraryRoleId);
            model.addAttribute("authAppUser", authAppUser);
        }
        return new ModelAndView("st/AuthUserLibraryRoleForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/add/authAppUser/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        AuthUserLibraryRole authUserLibraryRole= new  AuthUserLibraryRole();
        authUserLibraryRole.setLibraryRoleId(SequenceUtil.getNext(AuthUserLibraryRole.class));        
        AuthAppUser authAppUser= new AuthAppUser();
        authAppUser.setUserId(parentId);
        model.addAttribute("authAppUser", authAppUser);
        model.addAttribute("authUserLibraryRole", authUserLibraryRole);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthUserLibraryRoleForm", "1");

        return new ModelAndView("st/AuthUserLibraryRoleForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/edit/{libraryRoleId}/authAppUser/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long libraryRoleId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (parentId!=null && libraryRoleId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get(parentId);
            AuthUserLibraryRole authUserLibraryRole=null;
            List<AuthUserLibraryRole> list=authAppUser.getLibraryRoleList();
            for(AuthUserLibraryRole one:list){
            	if(LongUtil.ifEqual(one.getLibraryRoleId(), libraryRoleId)){
            		authUserLibraryRole=one;
            		break;
            	}
            }
            model.addAttribute("authAppUser", authAppUser);
            model.addAttribute("authUserLibraryRole", authUserLibraryRole);
            
        }
        return new ModelAndView("st/AuthUserLibraryRoleForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/formSubmit/authAppUser/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthUserLibraryRole authUserLibraryRole,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
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

       	AuthAppUser authAppUser= authAppUserManager.get(parentId);
        
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
        String success = "redirect:/lb/{libraryPath}/authUserLibraryRole/list/authAppUser/" + parentId+"/mt/php";
        Locale locale = request.getLocale();
      
        authAppUserManager.libraryRoleSave(authAppUser);

        String key = (isNew) ? "authAppUser.added" : "authAppUser.updated";
        saveMessage(request, getText(key, locale));

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authUserLibraryRole/delete/{libraryRoleId}/authAppUser/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response,@PathVariable Long libraryRoleId)throws Exception {
        
        Model model = new ExtendedModelMap(); 
    	Locale locale = request.getLocale();
    	AuthAppUser authAppUser= authAppUserManager.get(parentId);
    	
    	AuthUserLibraryRole authUserLibraryRole1=null;
        List<AuthUserLibraryRole> list=authAppUser.getLibraryRoleList();
        for(AuthUserLibraryRole one:list){
        	if(LongUtil.ifEqual(one.getLibraryRoleId(), libraryRoleId)){
        		authUserLibraryRole1=one;
        		break;
        	}
        }
        if(authUserLibraryRole1!=null){
        	list.remove(authUserLibraryRole1);
        }
        
        authAppUserManager.saveAuthUserLibraryRole(authAppUser,list);
        
        saveMessage(request, getText("authAppUser.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/authUserLibraryRole/list/authAppUser/" + parentId+"/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }   
     
}
