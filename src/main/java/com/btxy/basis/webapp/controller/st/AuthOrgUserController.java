


	
package com.btxy.basis.webapp.controller.st;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppRole;
import com.btxy.basis.model.AuthOrgUser;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.service.st.AuthOrgUserManager;
import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.util.pass.AES;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;

@Controller
//@RequestMapping("/authOrgUsers*")
public class AuthOrgUserController extends BaseFormController{
	private static final String DM_FORM_NAME="authOrgUser";

    private AuthOrgUserManager authOrgUserManager;

    @Autowired
    public void setAuthOrgUserManager(AuthOrgUserManager authOrgUserManager) {
        this.authOrgUserManager = authOrgUserManager;
    }

//    @Autowired
//    private DtObjectInstanceInfoManager dtObjectInstanceInfoManager;

    
    public AuthOrgUserController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"authOrgUserList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"authOrgUser");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<AuthOrgUser> paginaredList=authOrgUserManager.find(currentPage,pageSize,LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),new QueryContditionSet<AuthOrgUser>(){
				@Override
				public void setContdition(Query<AuthOrgUser> q) {
			    			if(searchValue.getCombinedConditionValue()!=null){
			if(searchValue.getCombinedConditionValue().containsKey("email")){
				Object cvalue=searchValue.getCombinedConditionValue().get("email");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("email").contains(cvalue.toString().trim()));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("fullName")){
				Object cvalue=searchValue.getCombinedConditionValue().get("fullName");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("fullName").contains(cvalue.toString().trim()));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("objectId")){
				Object cvalue=searchValue.getCombinedConditionValue().get("objectId");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("objectId").in(ListUtil.pasreLongList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("objectModelId")){
				Object cvalue=searchValue.getCombinedConditionValue().get("objectModelId");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("objectModelId").in(ListUtil.pasreLongList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("phoneNumber")){
				Object cvalue=searchValue.getCombinedConditionValue().get("phoneNumber");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("phoneNumber").contains(cvalue.toString().trim()));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("roleList")){
				Object cvalue=searchValue.getCombinedConditionValue().get("roleList");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("roleList").in(ListUtil.pasreLongList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("userName")){
				Object cvalue=searchValue.getCombinedConditionValue().get("userName");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("userName").contains(cvalue.toString().trim()));
				}
			}
		}
		doCustomPropertySearchCondation(searchValue,q);
			    	
				}        		
        	});
        	model.addAttribute("authOrgUserList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<AuthOrgUser>());
            model.addAttribute("authOrgUserList",new ArrayList<AuthOrgUser>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("st/AuthOrgUserList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/view/{userId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long userId,HttpServletRequest request)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
    	model.addAttribute("fromObjectId", request.getParameter("fromObjectId"));
    	
        if (userId!=null) {
            AuthOrgUser authOrgUser= authOrgUserManager.get(userId);
            
            try{
            	authOrgUser.setPassword(new AES().decrypt(authOrgUser.getPassword()));
            }catch(Exception e){
            	e.printStackTrace();
            }
            
            model.addAttribute("authOrgUser", authOrgUser);
            
/*lyz            DmObjectInfo objectInfo=ObjectInfoCache.getInstance(new Long(request.getParameter("fromObjectId"))).getDmObjectInfo();
            
            if(objectInfo!=null){
	            String name=dtObjectInstanceInfoManager.getObjectInstanceName(objectInfo, authOrgUser.getObjectInstanceId());
	            
	            model.addAttribute("authOrgUserName", name);
	            model.addAttribute("objectName", objectInfo.getObjectName());
	            
            }
 */
            
            List<Long> roles=authOrgUser.getRoleList();
            
            List<String> roleNames=new ArrayList<String>();
            for(Long one:roles){
            	AuthAppRole role=AuthAppRoleCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getEntityById(one);
            	if(role!=null){
            		roleNames.add(role.getRoleName());
            	}
            }
            model.addAttribute("roleNames", ListUtil.toString(roleNames, ","));
        }
        return new ModelAndView("st/AuthOrgUserForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        AuthOrgUser authOrgUser= new  AuthOrgUser();
        authOrgUser.setUserId(SequenceUtil.getNext(AuthOrgUser.class));     
           
        	authOrgUser.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	authOrgUser.setOvert(false);
  		
        model.addAttribute("authOrgUser", authOrgUser);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthOrgUserForm", "1");

        return new ModelAndView("st/AuthOrgUserForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/edit/{userId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long userId,HttpServletRequest request)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    	
    	model.addAttribute("fromObjectId", request.getParameter("fromObjectId"));   
    		
        if (userId!=null) {
            AuthOrgUser authOrgUser= authOrgUserManager.get(userId);
            
            try{
            	authOrgUser.setPassword(new AES().decrypt(authOrgUser.getPassword()));
            }catch(Exception e){
            	e.printStackTrace();
            }
            
            model.addAttribute("authOrgUser", authOrgUser);
            
            /*lyz
            DmObjectInfo objectInfo=ObjectInfoCache.getInstance(new Long(request.getParameter("fromObjectId"))).getDmObjectInfo();
            
            if(objectInfo!=null){
	            String name=dtObjectInstanceInfoManager.getObjectInstanceName(objectInfo, authOrgUser.getObjectInstanceId());
	            
	            model.addAttribute("authOrgUserName", name);
	            model.addAttribute("objectName", objectInfo.getObjectName());
	            
            }*/
            
            List<Long> roles=authOrgUser.getRoleList();
            
            List<String> roleNames=new ArrayList<String>();
            for(Long one:roles){
            	AuthAppRole role=AuthAppRoleCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getEntityById(one);
            	if(role!=null){
            		roleNames.add(role.getRoleName());
            	}
            }
            model.addAttribute("roleNames", ListUtil.toString(roleNames, ","));
        }
        return new ModelAndView("st/AuthOrgUserForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthOrgUser authOrgUser,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthOrgUserForm", request.getParameter("addFlagOfAuthOrgUserForm"));
        
        model.addAttribute("fromObjectId", request.getParameter("fromObjectId"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authOrgUser, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("st/AuthOrgUserForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthOrgUserForm")));
        
  		String success = "redirect:/lb/{libraryPath}/authOrgUser/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        authOrgUser.setPassword(new AES().encrypt(authOrgUser.getPassword()));
        
        if(isNew){
        	authOrgUserManager.save(authOrgUser,true);
        }else{
        	authOrgUserManager.saveMainBody(authOrgUser);
        }
        
        
        
        String key = (isNew) ? "authOrgUser.added" : "authOrgUser.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/authOrgUser/view/"+authOrgUser.getUserId()+"/php?fromObjectId="+request.getParameter("fromObjectId") ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/delete/{userIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String userIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(userIdList!=null){
    		String[] a=userIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				authOrgUserManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("authOrgUser.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/authOrgUser/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<AuthOrgUser> list=authOrgUserManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getUserId());
    			obj.put("text", list.get(i).getFullName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/authOrgUser/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	AuthOrgUser authOrgUser= authOrgUserManager.get(objectId);
	    	if(authOrgUser!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(authOrgUser, new Object[]{button.getTargetStat()});
		        	authOrgUserManager.save(authOrgUser);
		        	
		        	obj.put("rtnValue", 1);
		    		obj.put("rtnDescription", button.getButtonName()+"成功!");
		    		super.returnJSON(obj, response);
		    		return;
		        }
	    	}
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	obj.put("rtnValue", 0);
		obj.put("rtnDescription", "数据更新失败，请稍后重试!");
    	super.returnJSON(obj, response);
    }
	/////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/lb/{libraryPath}/authOrgUser/view/idx_user_username/validate/php*",method = RequestMethod.POST)
    public void validateIndex1(@PathVariable String libraryPath,@RequestParam("userName") String userName,@RequestParam("userId") Long userId,HttpServletResponse response)throws Exception {
    	Boolean resultb=authOrgUserManager.checkUniqueIndexForUserName(userId,userName);   
    	response.setContentType("text/json;charset=utf-8");  
 	   	try {
 			response.getWriter().write(resultb.toString());
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
    }
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/view/idx_user_email/validate/php*",method = RequestMethod.POST)
    public void validateIndex2(@PathVariable String libraryPath,@RequestParam("email") String email,@RequestParam("userId") Long userId,HttpServletResponse response)throws Exception {
    	Boolean resultb=authOrgUserManager.checkUniqueIndexForEmail(userId,email);   
    	response.setContentType("text/json;charset=utf-8");  
 	   	try {
 			response.getWriter().write(resultb.toString());
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
    }
    @RequestMapping(value = "/lb/{libraryPath}/authOrgUser/view/idx_user_phonenumber/validate/php*",method = RequestMethod.POST)
    public void validateIndex3(@PathVariable String libraryPath,@RequestParam("phoneNumber") String phoneNumber,@RequestParam("userId") Long userId,HttpServletResponse response)throws Exception {
    	Boolean resultb=authOrgUserManager.checkUniqueIndexForPhoneNumber(userId,phoneNumber);   
    	response.setContentType("text/json;charset=utf-8");  
 	   	try {
 			response.getWriter().write(resultb.toString());
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
    }
    
}
