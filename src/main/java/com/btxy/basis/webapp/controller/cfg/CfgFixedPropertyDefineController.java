


	
package com.btxy.basis.webapp.controller.cfg;


import com.btxy.basis.service.cfg.CfgFixedPropertyDefineManager;
import com.btxy.basis.service.cfg.CfgFormInfoManager;
import com.btxy.basis.model.AuthUser;
import com.btxy.basis.model.CfgFixedPropertyDefine;
import com.btxy.basis.model.CfgFormInfo;

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

import com.btxy.basis.common.CurrentUserUtil;
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
//@RequestMapping("/cfgFixedPropertyDefines*")
public class CfgFixedPropertyDefineController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgFixedPropertyDefine";

    private CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager;

    @Autowired
    public void setCfgFixedPropertyDefineManager(CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager) {
        this.cfgFixedPropertyDefineManager = cfgFixedPropertyDefineManager;
    }
    
    private CfgFormInfoManager cfgFormInfoManager;

    @Autowired
    public void setCfgFormInfoManager(CfgFormInfoManager cfgFormInfoManager) {
        this.cfgFormInfoManager = cfgFormInfoManager;
    }
    
    public CfgFixedPropertyDefineController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/list/frameEdit/{listFlag}/php*")
    public ModelAndView frameEdit(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgFixedPropertyDefineList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgFixedPropertyDefine");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	AuthUser user=CurrentUserUtil.getCurrentUser();
        	final List<Long> roleList=user.getRoleList(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	List<CfgFixedPropertyDefine> paginaredList=cfgFixedPropertyDefineManager.find(new QueryContditionSet<CfgFixedPropertyDefine>(){
				@Override
				public void setContdition(Query<CfgFixedPropertyDefine> q) {
					// TODO Auto-generated method stub
					q.criteria("roleList").hasAnyOf(roleList);
			    	
				}        		
        	} );
        	
        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgFixedPropertyDefineList",paginaredList);
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.size());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgFixedPropertyDefine>());
            model.addAttribute("cfgFixedPropertyDefineList",new ArrayList<CfgFixedPropertyDefine>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("cfg/frame/EditFrame", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgFixedPropertyDefineList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgFixedPropertyDefine");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgFixedPropertyDefine> paginaredList=cfgFixedPropertyDefineManager.find(currentPage,pageSize,new QueryContditionSet<CfgFixedPropertyDefine>(){
				@Override
				public void setContdition(Query<CfgFixedPropertyDefine> q) {
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("propertyCode").contains(searchValue.getTextValue()),q.criteria("propertyName").contains(searchValue.getTextValue()),q.criteria("valueType").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgFixedPropertyDefineList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgFixedPropertyDefine>());
            model.addAttribute("cfgFixedPropertyDefineList",new ArrayList<CfgFixedPropertyDefine>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("cfg/CfgFixedPropertyDefineList", model.asMap());
    }
    private void initFormIdListWhenLoad(CfgFixedPropertyDefine one){
    	if(one!=null && one.getFormList()!=null){
    		one.getFormIdList().clear();
    		for(CfgFormInfo id1:one.getFormList()){
    			one.getFormIdList().add(id1.getFormId());
    		}
    	}
    }
    private void initFormListWhenSave(CfgFixedPropertyDefine one){
    	if(one!=null && one.getFormIdList()!=null){
    		one.getFormList().clear();
    		for(Long id1:one.getFormIdList()){
    			CfgFormInfo cfi=new CfgFormInfo();
    			cfi.setFormId(id1);
    			one.getFormList().add(cfi);
    		}
    	}
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/view/{propertyId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long propertyId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
    	model.addAttribute("cfgFormInfoList",getAllCfgFormInfo());
    	
        if (propertyId!=null) {
            CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyId);
            initFormIdListWhenLoad(cfgFixedPropertyDefine);
            model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
        }
        return new ModelAndView("cfg/CfgFixedPropertyDefineForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgFixedPropertyDefine cfgFixedPropertyDefine= new  CfgFixedPropertyDefine();
        cfgFixedPropertyDefine.setPropertyId(SequenceUtil.getNext(CfgFixedPropertyDefine.class));     
           
  		
        model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyDefineForm", "1");

        model.addAttribute("cfgFormInfoList",getAllCfgFormInfo());
        
        return new ModelAndView("cfg/CfgFixedPropertyDefineForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/edit/{propertyId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long propertyId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true); 
    	
    	model.addAttribute("cfgFormInfoList",getAllCfgFormInfo());
    	
        if (propertyId!=null) {
            CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyId);
            initFormIdListWhenLoad(cfgFixedPropertyDefine);
            model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
            
        }
        return new ModelAndView("cfg/CfgFixedPropertyDefineForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgFixedPropertyDefine cfgFixedPropertyDefine,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyDefineForm", request.getParameter("addFlagOfCfgFixedPropertyDefineForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgFixedPropertyDefine, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("cfg/CfgFixedPropertyDefineForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgFixedPropertyDefineForm")));
        
  		String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyDefine/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        initFormListWhenSave(cfgFixedPropertyDefine);
        if(isNew){
        	cfgFixedPropertyDefineManager.save(cfgFixedPropertyDefine);
        }else{
        	cfgFixedPropertyDefineManager.saveMainBody(cfgFixedPropertyDefine);
        }
        
        
        
        String key = (isNew) ? "cfgFixedPropertyDefine.added" : "cfgFixedPropertyDefine.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/cfgFixedPropertyDefine/view/"+cfgFixedPropertyDefine.getPropertyId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/delete/{propertyIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String propertyIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(propertyIdList!=null){
    		String[] a=propertyIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgFixedPropertyDefineManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgFixedPropertyDefine.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyDefine/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyDefine/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgFixedPropertyDefine> list=cfgFixedPropertyDefineManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getPropertyId());
    			obj.put("text", list.get(i).getPropertyName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	
    private List<CfgFormInfo> getAllCfgFormInfo() {	
    	List<CfgFormInfo> list=cfgFormInfoManager.getAll();
    	Map<Long,List<CfgFormInfo>> map=new HashMap<Long,List<CfgFormInfo>>();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		 MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
    	}
    	initTree("",0l,map);
    	return list;
  	} 

	private void initTree(String parentLab,Long parentId,Map<Long,List<CfgFormInfo>> map){
    	List<CfgFormInfo> list1=map.get(parentId);
    	if(list1!=null){
    		for(CfgFormInfo one:list1){
    			one.setFormName(parentLab+one.getFormName());
    			initTree(parentLab+"&nbsp;&nbsp;&nbsp;&nbsp;",one.getFormId(),map);
    		}
    	}
    }
}
