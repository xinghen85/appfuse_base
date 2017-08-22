
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgFixedPropertyDefineManager;
import com.btxy.basis.model.CfgFixedPropertyField;
import com.btxy.basis.model.CfgFixedPropertyDefine;

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
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
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

import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.util.zx.LongUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Controller
public class CfgFixedPropertyFieldController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgFixedPropertyField";

    private CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager;

    @Autowired
    public void setCfgFixedPropertyDefineManager(CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager) {
        this.cfgFixedPropertyDefineManager = cfgFixedPropertyDefineManager;
    }
    public CfgFixedPropertyFieldController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/list/cfgFixedPropertyDefine/{parentId}/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request)throws Exception {	
  		return list(libraryPath,parentId,"mt",request);
  	}
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/list/cfgFixedPropertyDefine/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable String listFlag,HttpServletRequest request)throws Exception {
        Model model = new ExtendedModelMap();
        model.addAttribute("listFlag",listFlag);
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        if (parentId!=null) {
            CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(parentId);
            model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyFieldList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/view/{fieldId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long fieldId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (fieldId!=null) {
            CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(fieldId);
            model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyFieldForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/add/cfgFixedPropertyDefine/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgFixedPropertyField cfgFixedPropertyField= new  CfgFixedPropertyField();
        cfgFixedPropertyField.setFieldId(SequenceUtil.getNext(CfgFixedPropertyField.class));        
        	cfgFixedPropertyField.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	cfgFixedPropertyField.setOvert(false);
        CfgFixedPropertyDefine cfgFixedPropertyDefine= new CfgFixedPropertyDefine();
        cfgFixedPropertyDefine.setPropertyId(parentId);
        model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
        model.addAttribute("cfgFixedPropertyField", cfgFixedPropertyField);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyFieldForm", "1");

        return new ModelAndView("base/cfg/CfgFixedPropertyFieldForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/edit/{fieldId}/cfgFixedPropertyDefine/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long fieldId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (parentId!=null && fieldId!=null) {
            CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(parentId);
            CfgFixedPropertyField cfgFixedPropertyField=null;
            List<CfgFixedPropertyField> list=cfgFixedPropertyDefine.getFieldList();
            for(CfgFixedPropertyField one:list){
            	if(LongUtil.ifEqual(one.getFieldId(), fieldId)){
            		cfgFixedPropertyField=one;
            		break;
            	}
            }
            model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
            model.addAttribute("cfgFixedPropertyField", cfgFixedPropertyField);
            
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyFieldForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/formSubmit/cfgFixedPropertyDefine/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgFixedPropertyField cfgFixedPropertyField,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyFieldForm", request.getParameter("addFlagOfCfgFixedPropertyFieldForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgFixedPropertyField, svr);      	

            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgFixedPropertyFieldForm", model.asMap());
            }
        }
        log.debug("entering 'onSubmit' method...");

       	CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(parentId);
        
        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgFixedPropertyFieldForm")));
        
        if(isNew){
        	cfgFixedPropertyDefine.getFieldList().add(cfgFixedPropertyField);
        }else{
        	
            List<CfgFixedPropertyField> list=cfgFixedPropertyDefine.getFieldList();
            for(CfgFixedPropertyField one:list){
            	if(LongUtil.ifEqual(one.getFieldId(), cfgFixedPropertyField.getFieldId())){
					one.setDefaultValue(cfgFixedPropertyField.getDefaultValue());						
					
					one.setFieldName(cfgFixedPropertyField.getFieldName());						
					
					one.setLibrary(cfgFixedPropertyField.getLibrary());						
					
					one.setMax(cfgFixedPropertyField.getMax());						
					
					one.setMaxlength(cfgFixedPropertyField.getMaxlength());						
					
					one.setMimlength(cfgFixedPropertyField.getMimlength());						
					
					one.setMin(cfgFixedPropertyField.getMin());						
					
					one.setMultiple(cfgFixedPropertyField.isMultiple());						
					
					one.setOvert(cfgFixedPropertyField.isOvert());						
					
					one.setRequired(cfgFixedPropertyField.isRequired());						
					
					one.setValueList(cfgFixedPropertyField.getValueList());						
					
					one.setValueType(cfgFixedPropertyField.getValueType());						
					
            		break;
            	}
            }
              	
        }
        String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyField/list/cfgFixedPropertyDefine/" + parentId+"/mt/php";
        Locale locale = request.getLocale();
      
        cfgFixedPropertyDefineManager.save(cfgFixedPropertyDefine);

        String key = (isNew) ? "cfgFixedPropertyDefine.added" : "cfgFixedPropertyDefine.updated";
        saveMessage(request, getText(key, locale));

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyField/delete/{fieldId}/cfgFixedPropertyDefine/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response,@PathVariable Long fieldId)throws Exception {
        
        Model model = new ExtendedModelMap(); 
    	Locale locale = request.getLocale();
    	CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(parentId);
    	
    	CfgFixedPropertyField cfgFixedPropertyField1=null;
        List<CfgFixedPropertyField> list=cfgFixedPropertyDefine.getFieldList();
        for(CfgFixedPropertyField one:list){
        	if(LongUtil.ifEqual(one.getFieldId(), fieldId)){
        		cfgFixedPropertyField1=one;
        		break;
        	}
        }
        if(cfgFixedPropertyField1!=null){
        	list.remove(cfgFixedPropertyField1);
        }
        
        cfgFixedPropertyDefineManager.save(cfgFixedPropertyDefine);
        
        saveMessage(request, getText("cfgFixedPropertyDefine.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyField/list/cfgFixedPropertyDefine/" + parentId+"/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
}
