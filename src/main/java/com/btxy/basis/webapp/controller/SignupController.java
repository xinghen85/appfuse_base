package com.btxy.basis.webapp.controller;

import org.apache.commons.lang.StringUtils;

import com.btxy.basis.Constants;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.User;
import com.btxy.basis.service.RoleManager;
import com.btxy.basis.service.UserExistsException;
import com.btxy.basis.service.st.AuthAppUserManager;
import com.btxy.basis.util.pass.AES;
import com.btxy.basis.webapp.util.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

/**
 * Controller to signup new users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
/*@RequestMapping("/signup*")*/
public class SignupController extends BaseFormController {

    private AuthAppUserManager authAppUserManager;

    @Autowired
    public void setAuthAppUserManager(AuthAppUserManager authAppUserManager) {
        this.authAppUserManager = authAppUserManager;
    }
    
    private static final String DM_FORM_NAME="authAppUser";
    
    
    

    @RequestMapping(value = "/signup/add*",method = RequestMethod.GET)
    public ModelAndView add()throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME);
        AuthAppUser authAppUser= new  AuthAppUser();
        authAppUser.setUserId(SequenceUtil.getNext(AuthAppUser.class));     
        authAppUser.setOvert(true);
  		
        model.addAttribute("authAppUser", authAppUser);
        

        return new ModelAndView("signup/signup", model.asMap());
    }
    @RequestMapping(value = "/signup/{userId}/perfect*",method = RequestMethod.GET)
    public ModelAndView perfect(@PathVariable Long userId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME);
            
        if (userId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get(userId);
            model.addAttribute("authAppUser", authAppUser);
        }  

        return new ModelAndView("signup/perfect", model.asMap());
    }
    @RequestMapping(value = "/signup/{userId}/activation*",method = RequestMethod.GET)
    public ModelAndView activation(@PathVariable Long userId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME);
    	if ( userId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get( userId);
            model.addAttribute("authAppUser", authAppUser);
        }  
        

        return new ModelAndView("signup/activation", model.asMap());
    }

    @RequestMapping(value = "/signup/{userId}/sensitize*",method = RequestMethod.GET)

    public ModelAndView onActivationSubmit(@PathVariable Long userId,@RequestParam("key") String key,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME);
    	if ( userId!=null) {
            AuthAppUser authAppUser= authAppUserManager.get( userId);
            model.addAttribute("authAppUser", authAppUser);
            String str1=userId+"-"+authAppUser.getCreateTime();
            String key1=new AES().encrypt(str1);
            if(key1!=null && key1.equals(key)){
            	log.info("userId="+userId+",createTime="+authAppUser.getCreateTime()+",key1="+key1+",key="+key+",校验成功");
            }
            authAppUser.setEnabled(true);
            //authAppUser.set
            
            authAppUserManager.save(authAppUser,false);
        }  
        

        return new ModelAndView("signup/sensitize", model.asMap());
    }
    
    @RequestMapping(value = "/signup/submit*",method = RequestMethod.POST)

    public ModelAndView onSubmit(AuthAppUser authAppUser,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME);
        
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authAppUser, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("signup/signup", model.asMap());
            }
        }
        authAppUser.setEnabled(false);
        //authAppUser.set
        
        authAppUserManager.save(authAppUser,true);
        

        log.debug("entering 'onSubmit' method...");

        
  		String success = "redirect:/signup/"+authAppUser.getUserId()+"/perfect";
  		
        
        //Locale locale = request.getLocale();

        
        
        model.addAttribute("authAppUser", authAppUser);
        
        //saveMessage(request, getText(key, locale));

        

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/signup/perfect/submit*",method = RequestMethod.POST)

    public ModelAndView onPerfectSubmit(AuthAppUser authAppUser,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME);
        
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authAppUser, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("signup/perfect", model.asMap());
            }
        }
        authAppUser.setEnabled(false);
        //authAppUser.set
        
        authAppUserManager.save(authAppUser,false);
        

        log.debug("entering 'onSubmit' method...");

        
  		String success = "redirect:/signup/"+authAppUser.getUserId()+"/activation";
  		
        
        //Locale locale = request.getLocale();

        
        
        model.addAttribute("authAppUser", authAppUser);
        
        //saveMessage(request, getText(key, locale));

        

        return new ModelAndView(success, model.asMap());
    }
    
    @RequestMapping(value = "/signup/authAppUser/view/idx_user_email/validate/php*",method = RequestMethod.POST)
    public void validateIndex2(@RequestParam("email") String email,HttpServletResponse response)throws Exception {
    	Boolean resultb=authAppUserManager.checkUniqueIndexForEmail(0l,email);   
    	response.setContentType("text/json;charset=utf-8");  
 	   	try {
 			response.getWriter().write(resultb.toString());
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
    }
    @RequestMapping(value = "/signup/authAppUser/view/idx_user_phonenumber/validate/php*",method = RequestMethod.POST)
    public void validateIndex3(@RequestParam("phoneNumber") String phoneNumber,HttpServletResponse response)throws Exception {
    	Boolean resultb=authAppUserManager.checkUniqueIndexForPhoneNumber(0l,phoneNumber);   
    	response.setContentType("text/json;charset=utf-8");  
 	   	try {
 			response.getWriter().write(resultb.toString());
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
    }
}
