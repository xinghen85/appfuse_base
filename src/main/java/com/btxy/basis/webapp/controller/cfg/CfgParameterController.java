


	
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgParameterManager;
import com.btxy.basis.model.CfgParameter;

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
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.webapp.util.displaytable.PageTools;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;

import org.mongodb.morphia.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.util.zx.LongUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Controller
//@RequestMapping("/cfgParameters*")
public class CfgParameterController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgParameter";

    private CfgParameterManager cfgParameterManager;

    @Autowired
    public void setCfgParameterManager(CfgParameterManager cfgParameterManager) {
        this.cfgParameterManager = cfgParameterManager;
    }
    public CfgParameterController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgParameterList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgParameter");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgParameter> paginaredList=cfgParameterManager.find(currentPage,pageSize,new QueryContditionSet<CfgParameter>(){
				@Override
				public void setContdition(Query<CfgParameter> q) {
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("description").contains(searchValue.getTextValue()),q.criteria("name").contains(searchValue.getTextValue()),q.criteria("note").contains(searchValue.getTextValue()),q.criteria("value").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgParameterList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgParameter>());
            model.addAttribute("cfgParameterList",new ArrayList<CfgParameter>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgParameterList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/view/{parameterId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long parameterId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (parameterId!=null) {
            CfgParameter cfgParameter= cfgParameterManager.get(parameterId);
            model.addAttribute("cfgParameter", cfgParameter);
        }
        return new ModelAndView("base/cfg/CfgParameterForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgParameter cfgParameter= new  CfgParameter();
        cfgParameter.setParameterId(SequenceUtil.getNext(CfgParameter.class));     
           
  		
        model.addAttribute("cfgParameter", cfgParameter);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgParameterForm", "1");

        return new ModelAndView("base/cfg/CfgParameterForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/edit/{parameterId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long parameterId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (parameterId!=null) {
            CfgParameter cfgParameter= cfgParameterManager.get(parameterId);
            model.addAttribute("cfgParameter", cfgParameter);
            
        }
        return new ModelAndView("base/cfg/CfgParameterForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgParameter cfgParameter,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgParameterForm", request.getParameter("addFlagOfCfgParameterForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgParameter, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgParameterForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgParameterForm")));
        
  		String success = "redirect:/lb/{libraryPath}/cfgParameter/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	cfgParameterManager.save(cfgParameter,true);
        }else{
        	cfgParameterManager.saveMainBody(cfgParameter);
        }
        
        
        
        String key = (isNew) ? "cfgParameter.added" : "cfgParameter.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/cfgParameter/view/"+cfgParameter.getParameterId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/delete/{parameterIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String parameterIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(parameterIdList!=null){
    		String[] a=parameterIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgParameterManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgParameter.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/cfgParameter/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgParameter/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgParameter> list=cfgParameterManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getParameterId());
    			obj.put("text", list.get(i).getParameterId());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	
}
