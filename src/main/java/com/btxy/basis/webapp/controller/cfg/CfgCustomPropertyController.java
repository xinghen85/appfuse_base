


package com.btxy.basis.webapp.controller.cfg;

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
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.service.cfg.CfgCustomPropertyManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;
@Controller
//@RequestMapping("/cfgCustomProperties*")
public class CfgCustomPropertyController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgCustomProperty";

    private CfgCustomPropertyManager cfgCustomPropertyManager;

    @Autowired
    public void setCfgCustomPropertyManager(CfgCustomPropertyManager cfgCustomPropertyManager) {
        this.cfgCustomPropertyManager = cfgCustomPropertyManager;
    }
    public CfgCustomPropertyController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"list");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgCustomPropertyList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgCustomProperty");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgCustomProperty> paginaredList=cfgCustomPropertyManager.find(currentPage,pageSize,LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),new QueryContditionSet<CfgCustomProperty>(){
				@Override
				public void setContdition(Query<CfgCustomProperty> q) {
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("defaultValue").contains(searchValue.getTextValue()),q.criteria("propertyCode").contains(searchValue.getTextValue()),q.criteria("propertyName").contains(searchValue.getTextValue()),q.criteria("valueType").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});
        	//model.addAttribute(cfgCustomPropertyManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)));

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgCustomPropertyList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgCustomProperty>());
            model.addAttribute("cfgCustomPropertyList",new ArrayList<CfgCustomProperty>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("cfg/CfgCustomPropertyList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/view/{propertyId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long propertyId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (propertyId!=null) {
            CfgCustomProperty cfgCustomProperty= cfgCustomPropertyManager.get(propertyId);
            model.addAttribute("cfgCustomProperty", cfgCustomProperty);
        }
        return new ModelAndView("cfg/CfgCustomPropertyForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/add*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgCustomProperty cfgCustomProperty= new  CfgCustomProperty();
        cfgCustomProperty.setPropertyId(SequenceUtil.getNext(CfgCustomProperty.class));        
        	cfgCustomProperty.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	cfgCustomProperty.setOvert(false);
        model.addAttribute("cfgCustomProperty", cfgCustomProperty);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgCustomPropertyForm", "1");

        return new ModelAndView("cfg/CfgCustomPropertyForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/edit/{propertyId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long propertyId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (propertyId!=null) {
            CfgCustomProperty cfgCustomProperty= cfgCustomPropertyManager.get(propertyId);
            model.addAttribute("cfgCustomProperty", cfgCustomProperty);
            
        }
        return new ModelAndView("cfg/CfgCustomPropertyForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/formSubmit*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgCustomProperty cfgCustomProperty,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgCustomPropertyForm", request.getParameter("addFlagOfCfgCustomPropertyForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgCustomProperty, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("cfg/CfgCustomPropertyForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgCustomPropertyForm")));
        
        String success = "redirect:/lb/{libraryPath}/cfgCustomProperty/list/php";
        Locale locale = request.getLocale();

        if(isNew){
        	cfgCustomPropertyManager.save(cfgCustomProperty);
        }else{
        	cfgCustomPropertyManager.saveMainBody(cfgCustomProperty);
        }
        
        
        
        String key = (isNew) ? "cfgCustomProperty.added" : "cfgCustomProperty.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {
            success = "redirect:/lb/{libraryPath}/cfgCustomProperty/view/"+cfgCustomProperty.getPropertyId()+"/php" ;
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/delete/{propertyIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String propertyIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	Locale locale = request.getLocale();
    	if(propertyIdList!=null){
    		String[] a=propertyIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgCustomPropertyManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgCustomProperty.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgCustomProperty/list/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgCustomProperty/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgCustomProperty> list=cfgCustomPropertyManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getPropertyId());
    			obj.put("text", list.get(i).getPropertyId());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	
}
