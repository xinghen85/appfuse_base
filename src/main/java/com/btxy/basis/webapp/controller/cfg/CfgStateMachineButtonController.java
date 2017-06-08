
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgStateMachineValueManager;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.CfgStateMachineValue;

import java.util.List;

import com.btxy.basis.util.zx.LongUtil;

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
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.webapp.util.displaytable.PageTools;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;

import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.util.zx.LongUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Controller
public class CfgStateMachineButtonController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgStateMachineButton";

    private CfgStateMachineValueManager cfgStateMachineValueManager;

    @Autowired
    public void setCfgStateMachineValueManager(CfgStateMachineValueManager cfgStateMachineValueManager) {
        this.cfgStateMachineValueManager = cfgStateMachineValueManager;
    }
    public CfgStateMachineButtonController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/list/cfgStateMachineValue/{parentId}/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request)throws Exception {	
  		return list(libraryPath,parentId,"mt",request);
  	}
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/list/cfgStateMachineValue/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable String listFlag,HttpServletRequest request)throws Exception {
        Model model = new ExtendedModelMap();
        model.addAttribute("listFlag",listFlag);
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        if (parentId!=null) {
            CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(parentId);
            model.addAttribute("cfgStateMachineValue", cfgStateMachineValue);
        }
        return new ModelAndView("cfg/CfgStateMachineButtonList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/view/{buttonId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long buttonId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (buttonId!=null) {
            CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(buttonId);
            CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(cfgStateMachineValue.getMachineId());
            if(define!=null){
            	CfgEnumInfo cfgEnumInfo=CfgEnumInfoCache.getInstance().getCfgEnumInfoById(define.getEnumId());
            	model.addAttribute("cfgEnumInfo", cfgEnumInfo);
            	
            }
            model.addAttribute("cfgStateMachineValue", cfgStateMachineValue);
        }
        return new ModelAndView("cfg/CfgStateMachineButtonForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/add/cfgStateMachineValue/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgStateMachineButton cfgStateMachineButton= new  CfgStateMachineButton();
        cfgStateMachineButton.setButtonId(SequenceUtil.getNext(CfgStateMachineButton.class));    
        CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(parentId);
        
        CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(cfgStateMachineValue.getMachineId());
        if(define!=null){
        	CfgEnumInfo cfgEnumInfo=CfgEnumInfoCache.getInstance().getCfgEnumInfoById(define.getEnumId());
        	model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        	
        }
        cfgStateMachineButton.setURL("/${formName}/edit/stateMachineSubmit/${objectId}/"+define.getMachineId()+"/"+cfgStateMachineButton.getButtonId()+"/php");

        
        model.addAttribute("cfgStateMachineValue", cfgStateMachineValue);
        model.addAttribute("cfgStateMachineButton", cfgStateMachineButton);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgStateMachineButtonForm", "1");

        
        
        
        return new ModelAndView("cfg/CfgStateMachineButtonForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/edit/{buttonId}/cfgStateMachineValue/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long buttonId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (parentId!=null && buttonId!=null) {
            CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(parentId);
            CfgStateMachineButton cfgStateMachineButton=null;
            List<CfgStateMachineButton> list=cfgStateMachineValue.getButtons();
            for(CfgStateMachineButton one:list){
            	if(LongUtil.ifEqual(one.getButtonId(), buttonId)){
            		cfgStateMachineButton=one;
            		break;
            	}
            }
            model.addAttribute("cfgStateMachineValue", cfgStateMachineValue);
            model.addAttribute("cfgStateMachineButton", cfgStateMachineButton);

            if(cfgStateMachineButton.getURL()==null || cfgStateMachineButton.getURL().equals("")){
            	cfgStateMachineButton.setURL("/${formName}/edit/stateMachineSubmit/${objectId}/"+cfgStateMachineValue.getMachineId()+"/"+cfgStateMachineButton.getButtonId()+"/php");
            }
            CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(cfgStateMachineValue.getMachineId());
            if(define!=null){
            	CfgEnumInfo cfgEnumInfo=CfgEnumInfoCache.getInstance().getCfgEnumInfoById(define.getEnumId());
            	model.addAttribute("cfgEnumInfo", cfgEnumInfo);
            	
            }
            model.addAttribute("cfgStateMachineValue", cfgStateMachineValue);
        }
        return new ModelAndView("cfg/CfgStateMachineButtonForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/formSubmit/cfgStateMachineValue/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgStateMachineButton cfgStateMachineButton,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgStateMachineButtonForm", request.getParameter("addFlagOfCfgStateMachineButtonForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgStateMachineButton, svr);      	

            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("cfg/CfgStateMachineButtonForm", model.asMap());
            }
        }
        log.debug("entering 'onSubmit' method...");

       	CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(parentId);
        
        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgStateMachineButtonForm")));
        
        if(isNew){
        	cfgStateMachineValue.getButtons().add(cfgStateMachineButton);
        }else{
        	
            List<CfgStateMachineButton> list=cfgStateMachineValue.getButtons();
            for(CfgStateMachineButton one:list){
            	if(LongUtil.ifEqual(one.getButtonId(), cfgStateMachineButton.getButtonId())){
					one.setURL(cfgStateMachineButton.getURL());						
					
					one.setButtonName(cfgStateMachineButton.getButtonName());						
					
					one.setRoleList(cfgStateMachineButton.getRoleList());						
					
					one.setTargetStat(cfgStateMachineButton.getTargetStat());						
					
					one.setViewType(cfgStateMachineButton.getViewType());						
					
            		break;
            	}
            }
              	
        }
        String success = "redirect:/lb/{libraryPath}/cfgStateMachineButton/list/cfgStateMachineValue/" + parentId+"/mt/php";
        Locale locale = request.getLocale();
      
        cfgStateMachineValueManager.save(cfgStateMachineValue);

        String key = (isNew) ? "cfgStateMachineValue.added" : "cfgStateMachineValue.updated";
        saveMessage(request, getText(key, locale));

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineButton/delete/{buttonId}/cfgStateMachineValue/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response,@PathVariable Long buttonId)throws Exception {
        
        Model model = new ExtendedModelMap(); 
    	Locale locale = request.getLocale();
    	CfgStateMachineValue cfgStateMachineValue= cfgStateMachineValueManager.get(parentId);
    	
    	CfgStateMachineButton cfgStateMachineButton1=null;
        List<CfgStateMachineButton> list=cfgStateMachineValue.getButtons();
        for(CfgStateMachineButton one:list){
        	if(LongUtil.ifEqual(one.getButtonId(), buttonId)){
        		cfgStateMachineButton1=one;
        		break;
        	}
        }
        if(cfgStateMachineButton1!=null){
        	list.remove(cfgStateMachineButton1);
        }
        
        cfgStateMachineValueManager.save(cfgStateMachineValue);
        
        saveMessage(request, getText("cfgStateMachineValue.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgStateMachineButton/list/cfgStateMachineValue/" + parentId+"/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
}
