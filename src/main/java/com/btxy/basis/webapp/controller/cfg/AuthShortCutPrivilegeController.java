


	
package com.btxy.basis.webapp.controller.cfg;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthShortCutPrivilege;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.service.cfg.AuthShortCutPrivilegeManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
//@RequestMapping("/authShortCutPrivileges*")
public class AuthShortCutPrivilegeController extends BaseFormController{
	private static final String DM_FORM_NAME="authShortCutPrivilege";

    private AuthShortCutPrivilegeManager authShortCutPrivilegeManager;

    @Autowired
    public void setAuthShortCutPrivilegeManager(AuthShortCutPrivilegeManager authShortCutPrivilegeManager) {
        this.authShortCutPrivilegeManager = authShortCutPrivilegeManager;
    }
    public AuthShortCutPrivilegeController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"authShortCutPrivilegeList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"authShortCutPrivilege");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<AuthShortCutPrivilege> paginaredList=authShortCutPrivilegeManager.find(currentPage,pageSize,"order",new QueryContditionSet<AuthShortCutPrivilege>(){
				@Override
				public void setContdition(Query<AuthShortCutPrivilege> q) {
					// TODO Auto-generated method stub
					if(searchValue.getTextValue()!=null ){
			    		q.or(q.criteria("icon").contains(searchValue.getTextValue()),q.criteria("shortCutName").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("authShortCutPrivilegeList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<AuthShortCutPrivilege>());
            model.addAttribute("authShortCutPrivilegeList",new ArrayList<AuthShortCutPrivilege>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/AuthShortCutPrivilegeList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/view/{shortCutId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long shortCutId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (shortCutId!=null) {
            AuthShortCutPrivilege authShortCutPrivilege= authShortCutPrivilegeManager.get(shortCutId);
            model.addAttribute("authShortCutPrivilege", authShortCutPrivilege);
        }
        return new ModelAndView("base/cfg/AuthShortCutPrivilegeForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        AuthShortCutPrivilege authShortCutPrivilege= new  AuthShortCutPrivilege();
        authShortCutPrivilege.setShortCutId(SequenceUtil.getNext(AuthShortCutPrivilege.class));     
        authShortCutPrivilege.setOrder(authShortCutPrivilege.getShortCutId());   
  		
        model.addAttribute("authShortCutPrivilege", authShortCutPrivilege);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthShortCutPrivilegeForm", "1");

        return new ModelAndView("base/cfg/AuthShortCutPrivilegeForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/edit/{shortCutId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long shortCutId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (shortCutId!=null) {
            AuthShortCutPrivilege authShortCutPrivilege= authShortCutPrivilegeManager.get(shortCutId);
            model.addAttribute("authShortCutPrivilege", authShortCutPrivilege);
            
        }
        return new ModelAndView("base/cfg/AuthShortCutPrivilegeForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthShortCutPrivilege authShortCutPrivilege,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthShortCutPrivilegeForm", request.getParameter("addFlagOfAuthShortCutPrivilegeForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authShortCutPrivilege, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/AuthShortCutPrivilegeForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthShortCutPrivilegeForm")));
        
  		String success = "redirect:/lb/{libraryPath}/authShortCutPrivilege/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	authShortCutPrivilegeManager.save(authShortCutPrivilege,true);
        }else{
        	authShortCutPrivilegeManager.saveMainBody(authShortCutPrivilege);
        }
        
        
        
        String key = (isNew) ? "authShortCutPrivilege.added" : "authShortCutPrivilege.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/authShortCutPrivilege/view/"+authShortCutPrivilege.getShortCutId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/delete/{shortCutIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String shortCutIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(shortCutIdList!=null){
    		String[] a=shortCutIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				authShortCutPrivilegeManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("authShortCutPrivilege.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/authShortCutPrivilege/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<AuthShortCutPrivilege> list=authShortCutPrivilegeManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getShortCutId());
    			obj.put("text", list.get(i).getShortCutName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/authShortCutPrivilege/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	AuthShortCutPrivilege authShortCutPrivilege= authShortCutPrivilegeManager.get(objectId);
	    	if(authShortCutPrivilege!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(authShortCutPrivilege, new Object[]{button.getTargetStat()});
		        	authShortCutPrivilegeManager.save(authShortCutPrivilege);
		        	
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
	
}
