


	
package com.btxy.basis.webapp.controller.st;

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
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.LibraryInfo;
import com.btxy.basis.service.st.LibraryInfoManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
//@RequestMapping("/libraryInfoes*")
public class LibraryInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="libraryInfo";

    private LibraryInfoManager libraryInfoManager;

    @Autowired
    public void setLibraryInfoManager(LibraryInfoManager libraryInfoManager) {
        this.libraryInfoManager = libraryInfoManager;
    }
    public LibraryInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"libraryInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"libraryInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<LibraryInfo> paginaredList=libraryInfoManager.find(currentPage,pageSize,new QueryContditionSet<LibraryInfo>(){
				@Override
				public void setContdition(Query<LibraryInfo> q) {
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("identification").contains(searchValue.getTextValue()),q.criteria("libraryName").contains(searchValue.getTextValue()),q.criteria("path").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("libraryInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<LibraryInfo>());
            model.addAttribute("libraryInfoList",new ArrayList<LibraryInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("st/LibraryInfoList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/view/{libraryId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long libraryId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (libraryId!=null) {
            LibraryInfo libraryInfo= libraryInfoManager.get(libraryId);
            model.addAttribute("libraryInfo", libraryInfo);
        }
        return new ModelAndView("st/LibraryInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        LibraryInfo libraryInfo= new  LibraryInfo();
        libraryInfo.setLibraryId(SequenceUtil.getNext(LibraryInfo.class));     
           
  		
        model.addAttribute("libraryInfo", libraryInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfLibraryInfoForm", "1");

        return new ModelAndView("st/LibraryInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/edit/{libraryId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long libraryId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (libraryId!=null) {
            LibraryInfo libraryInfo= libraryInfoManager.get(libraryId);
            model.addAttribute("libraryInfo", libraryInfo);
            
        }
        return new ModelAndView("st/LibraryInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,LibraryInfo libraryInfo,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfLibraryInfoForm", request.getParameter("addFlagOfLibraryInfoForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(libraryInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("st/LibraryInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfLibraryInfoForm")));
        
  		String success = "redirect:/lb/{libraryPath}/libraryInfo/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	libraryInfoManager.save(libraryInfo,true);
        }else{
        	libraryInfoManager.saveMainBody(libraryInfo);
        }
        
        
        
        String key = (isNew) ? "libraryInfo.added" : "libraryInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/libraryInfo/view/"+libraryInfo.getLibraryId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/delete/{libraryIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String libraryIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(libraryIdList!=null){
    		String[] a=libraryIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				libraryInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("libraryInfo.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/libraryInfo/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/libraryInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<LibraryInfo> list=libraryInfoManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getLibraryId());
    			obj.put("text", list.get(i).getLibraryName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/libraryInfo/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	LibraryInfo libraryInfo= libraryInfoManager.get(objectId);
	    	if(libraryInfo!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(libraryInfo, new Object[]{button.getTargetStat()});
		        	libraryInfoManager.save(libraryInfo);
		        	
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
