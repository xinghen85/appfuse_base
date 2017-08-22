


	
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgIconInfoManager;
import com.btxy.basis.model.CfgIconInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Locale;

import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.webapp.util.displaytable.PageTools;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.common.model.OrderedMap;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;

import org.mongodb.morphia.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.util.zx.LongUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;

import java.lang.reflect.Method;


@Controller
//@RequestMapping("/cfgIconInfoes*")
public class CfgIconInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgIconInfo";

    private CfgIconInfoManager cfgIconInfoManager;

    @Autowired
    public void setCfgIconInfoManager(CfgIconInfoManager cfgIconInfoManager) {
        this.cfgIconInfoManager = cfgIconInfoManager;
    }
    public CfgIconInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgIconInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgIconInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgIconInfo> paginaredList=cfgIconInfoManager.find(currentPage,pageSize,LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),new QueryContditionSet<CfgIconInfo>(){
				@Override
				public void setContdition(Query<CfgIconInfo> q) {
					// TODO Auto-generated method stub
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("iconCode").contains(searchValue.getTextValue()),q.criteria("iconFolder").contains(searchValue.getTextValue()),q.criteria("iconName").contains(searchValue.getTextValue()),q.criteria("iconSize").contains(searchValue.getTextValue()),q.criteria("iconType").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});
        	//model.addAttribute(cfgIconInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)));

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgIconInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgIconInfo>());
            model.addAttribute("cfgIconInfoList",new ArrayList<CfgIconInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgIconInfoList", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/list/forselect/select1/php*")
    public ModelAndView listForSelect(@PathVariable String libraryPath,HttpServletRequest request)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	
        try {       
        	List<CfgIconInfo> paginaredList=cfgIconInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	//model.addAttribute(cfgIconInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)));
        	OrderedMap<String,List<CfgIconInfo>> odmap=new OrderedMap<String,List<CfgIconInfo>>();
        	for(CfgIconInfo one:paginaredList){
        		if(!odmap.containsKey(one.getIconFolder())){
        			odmap.put(one.getIconFolder(), new ArrayList<CfgIconInfo>());
        		}
        		odmap.get(one.getIconFolder()).add(one);
        	}
        	
        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgIconInfoList",paginaredList);
        	
        	model.addAttribute("cfgIconFolderList",odmap.getKeyList());
        	model.addAttribute("cfgIconInfoListSplitByFolder",odmap);
        	
        	model.addAttribute("totalSize", paginaredList.size());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgIconInfo>());
            model.addAttribute("cfgIconInfoList",new ArrayList<CfgIconInfo>());
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgIconInfoListForSelect", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/view/{iconId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long iconId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (iconId!=null) {
            CfgIconInfo cfgIconInfo= cfgIconInfoManager.get(iconId);
            model.addAttribute("cfgIconInfo", cfgIconInfo);
        }
        return new ModelAndView("base/cfg/CfgIconInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgIconInfo cfgIconInfo= new  CfgIconInfo();
        cfgIconInfo.setIconId(SequenceUtil.getNext(CfgIconInfo.class));     
           
        	cfgIconInfo.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	cfgIconInfo.setOvert(false);
  		
        model.addAttribute("cfgIconInfo", cfgIconInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgIconInfoForm", "1");

        return new ModelAndView("base/cfg/CfgIconInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/edit/{iconId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long iconId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (iconId!=null) {
            CfgIconInfo cfgIconInfo= cfgIconInfoManager.get(iconId);
            model.addAttribute("cfgIconInfo", cfgIconInfo);
            
        }
        return new ModelAndView("base/cfg/CfgIconInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgIconInfo cfgIconInfo,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgIconInfoForm", request.getParameter("addFlagOfCfgIconInfoForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgIconInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgIconInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgIconInfoForm")));
        
  		String success = "redirect:/lb/{libraryPath}/cfgIconInfo/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	cfgIconInfoManager.save(cfgIconInfo,true);
        }else{
        	cfgIconInfoManager.saveMainBody(cfgIconInfo);
        }
        
        
        
        String key = (isNew) ? "cfgIconInfo.added" : "cfgIconInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/cfgIconInfo/view/"+cfgIconInfo.getIconId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/delete/{iconIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String iconIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(iconIdList!=null){
    		String[] a=iconIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgIconInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgIconInfo.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/cfgIconInfo/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgIconInfo> list=cfgIconInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getIconId());
    			obj.put("text", list.get(i).getIconName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/cfgIconInfo/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	CfgIconInfo cfgIconInfo= cfgIconInfoManager.get(objectId);
	    	if(cfgIconInfo!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(cfgIconInfo, new Object[]{button.getTargetStat()});
		        	cfgIconInfoManager.save(cfgIconInfo);
		        	
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
