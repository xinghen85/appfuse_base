
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgEnumInfoManager;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.model.CfgEnumInfo;

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
public class CfgEnumValueInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgEnumValueInfo";

    private CfgEnumInfoManager cfgEnumInfoManager;

    @Autowired
    public void setCfgEnumInfoManager(CfgEnumInfoManager cfgEnumInfoManager) {
        this.cfgEnumInfoManager = cfgEnumInfoManager;
    }
    public CfgEnumValueInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/list/cfgEnumInfo/{parentId}/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request)throws Exception {	
  		return list(libraryPath,parentId,"list",request);
  	}
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/list/cfgEnumInfo/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable String listFlag,HttpServletRequest request)throws Exception {
        Model model = new ExtendedModelMap();
        model.addAttribute("listFlag",listFlag);
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        if (parentId!=null) {
            CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(parentId);
            model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        }
        return new ModelAndView("base/cfg/CfgEnumValueInfoList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/view/{enumValueId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long enumValueId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (enumValueId!=null) {
            CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(enumValueId);
            model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        }
        return new ModelAndView("base/cfg/CfgEnumValueInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/add/cfgEnumInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgEnumValueInfo cfgEnumValueInfo= new  CfgEnumValueInfo();
        cfgEnumValueInfo.setEnumValueId(SequenceUtil.getNext(CfgEnumValueInfo.class));        
        CfgEnumInfo cfgEnumInfo= new CfgEnumInfo();
        cfgEnumInfo.setEnumId(parentId);
        model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        model.addAttribute("cfgEnumValueInfo", cfgEnumValueInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgEnumValueInfoForm", "1");

        return new ModelAndView("base/cfg/CfgEnumValueInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/edit/{enumValueId}/cfgEnumInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long enumValueId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (parentId!=null && enumValueId!=null) {
            CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(parentId);
            CfgEnumValueInfo cfgEnumValueInfo=null;
            List<CfgEnumValueInfo> list=cfgEnumInfo.getValues();
            for(CfgEnumValueInfo one:list){
            	if(LongUtil.ifEqual(one.getEnumValueId(), enumValueId)){
            		cfgEnumValueInfo=one;
            		break;
            	}
            }
            model.addAttribute("cfgEnumInfo", cfgEnumInfo);
            model.addAttribute("cfgEnumValueInfo", cfgEnumValueInfo);
            
        }
        return new ModelAndView("base/cfg/CfgEnumValueInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/formSubmit/cfgEnumInfo/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgEnumValueInfo cfgEnumValueInfo,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgEnumValueInfoForm", request.getParameter("addFlagOfCfgEnumValueInfoForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgEnumValueInfo, svr);      	

            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgEnumValueInfoForm", model.asMap());
            }
        }
        log.debug("entering 'onSubmit' method...");

       	CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(parentId);
        
        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgEnumValueInfoForm")));
        
        if(isNew){
        	cfgEnumInfo.getValues().add(cfgEnumValueInfo);
        }else{
        	
            List<CfgEnumValueInfo> list=cfgEnumInfo.getValues();
            for(CfgEnumValueInfo one:list){
            	if(LongUtil.ifEqual(one.getEnumValueId(), cfgEnumValueInfo.getEnumValueId())){
					one.setCode(cfgEnumValueInfo.getCode());						
					
					one.setConstantName(cfgEnumValueInfo.getConstantName());						
					
					one.setValue(cfgEnumValueInfo.getValue());						
					
            		break;
            	}
            }
              	
        }
        String success = "redirect:/lb/{libraryPath}/cfgEnumValueInfo/list/cfgEnumInfo/" + parentId+"/php";
        Locale locale = request.getLocale();
      
        cfgEnumInfoManager.save(cfgEnumInfo);

        String key = (isNew) ? "cfgEnumInfo.added" : "cfgEnumInfo.updated";
        saveMessage(request, getText(key, locale));

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/delete/{enumValueId}/cfgEnumInfo/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response,@PathVariable Long enumValueId)throws Exception {
        
        Model model = new ExtendedModelMap(); 
    	Locale locale = request.getLocale();
    	CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(parentId);
    	
    	CfgEnumValueInfo cfgEnumValueInfo1=null;
        List<CfgEnumValueInfo> list=cfgEnumInfo.getValues();
        for(CfgEnumValueInfo one:list){
        	if(LongUtil.ifEqual(one.getEnumValueId(), enumValueId)){
        		cfgEnumValueInfo1=one;
        		break;
        	}
        }
        if(cfgEnumValueInfo1!=null){
        	list.remove(cfgEnumValueInfo1);
        }
        
        cfgEnumInfoManager.save(cfgEnumInfo);
        
        saveMessage(request, getText("cfgEnumInfo.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgEnumValueInfo/list/cfgEnumInfo/" + parentId+"/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    } 
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumValueInfo/edit/move/{enumValueId}/{moveType}/cfgEnumInfo/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView move(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable Integer moveType,HttpServletRequest request,
            HttpServletResponse response,@PathVariable Long enumValueId)throws Exception {
        
        Model model = new ExtendedModelMap(); 
    	CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(parentId);
    	
    	int index=-1;
        List<CfgEnumValueInfo> list=cfgEnumInfo.getValues();
        for(int i=0;i<list.size();i++){
        	CfgEnumValueInfo one=list.get(i);
        	if(LongUtil.ifEqual(one.getEnumValueId(), enumValueId)){
        		index=i;
        		break;
        	}
        }
        if(moveType==0 && index>0){
        	swap(list,index,index-1);
        }else if(moveType==1 && index<list.size()-1){
        	swap(list,index,index+1);
        }
        
        cfgEnumInfoManager.save(cfgEnumInfo);
        
        saveMessage(request, "移动枚举值成功！");
        
        String success = "redirect:/lb/{libraryPath}/cfgEnumValueInfo/list/cfgEnumInfo/" + parentId+"/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    } 
    private void swap(List<CfgEnumValueInfo> list,int i,int j){
    	if(list!=null){
    		if(i>=0 && i<list.size() && j>=0 && j<list.size()){
    			Long enumValueId=list.get(i).getEnumValueId();
    			String code=list.get(i).getCode();
    			String value=list.get(i).getValue();
    			String constantName=list.get(i).getConstantName();
    			
    			list.get(i).setEnumValueId(list.get(j).getEnumValueId());
    			list.get(i).setCode(list.get(j).getCode());
    			list.get(i).setValue(list.get(j).getValue());
    			list.get(i).setConstantName(list.get(j).getConstantName());
    			
    			list.get(j).setEnumValueId(enumValueId);
    			list.get(j).setCode(code);
    			list.get(j).setValue(value);
    			list.get(j).setConstantName(constantName);
    		}
    	}
    }
}
