


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
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.service.cfg.CfgEnumInfoManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;
@Controller
//@RequestMapping("/cfgEnumInfoes*")
public class CfgEnumInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgEnumInfo";

    private CfgEnumInfoManager cfgEnumInfoManager;

    @Autowired
    public void setCfgEnumInfoManager(CfgEnumInfoManager cfgEnumInfoManager) {
        this.cfgEnumInfoManager = cfgEnumInfoManager;
    }
    public CfgEnumInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"list");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgEnumInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgEnumInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgEnumInfo> paginaredList=cfgEnumInfoManager.find(currentPage,pageSize,new QueryContditionSet<CfgEnumInfo>(){
				@Override
				public void setContdition(Query<CfgEnumInfo> q) {
					if(searchValue.getTextValue()!=null){
			    		q.or(q.criteria("constantName").contains(searchValue.getTextValue()),
			    				q.criteria("enumCode").contains(searchValue.getTextValue()),
			    				q.criteria("enumName").contains(searchValue.getTextValue()));
			    	}
			    	
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgEnumInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgEnumInfo>());
            model.addAttribute("cfgEnumInfoList",new ArrayList<CfgEnumInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgEnumInfoList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/view/{enumId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long enumId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
        if (enumId!=null) {
            CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(enumId);
            model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        }
        return new ModelAndView("base/cfg/CfgEnumInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/add*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgEnumInfo cfgEnumInfo= new  CfgEnumInfo();
        cfgEnumInfo.setEnumId(SequenceUtil.getNext(CfgEnumInfo.class));        
        model.addAttribute("cfgEnumInfo", cfgEnumInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgEnumInfoForm", "1");

        return new ModelAndView("base/cfg/CfgEnumInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/edit/{enumId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long enumId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   	
        if (enumId!=null) {
            CfgEnumInfo cfgEnumInfo= cfgEnumInfoManager.get(enumId);
            model.addAttribute("cfgEnumInfo", cfgEnumInfo);
            
        }
        return new ModelAndView("base/cfg/CfgEnumInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgEnumInfo cfgEnumInfo,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgEnumInfoForm", request.getParameter("addFlagOfCfgEnumInfoForm"));
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgEnumInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgEnumInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgEnumInfoForm")));
        
        String success = "redirect:/lb/{libraryPath}/cfgEnumInfo/list/php";
        Locale locale = request.getLocale();

        if(isNew){
        	cfgEnumInfoManager.save(cfgEnumInfo);
        }else{
        	cfgEnumInfoManager.saveMainBody(cfgEnumInfo);
        }
        
        
        
        String key = (isNew) ? "cfgEnumInfo.added" : "cfgEnumInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {
            success = "redirect:/lb/{libraryPath}/cfgEnumInfo/view/"+cfgEnumInfo.getEnumId()+"/php" ;
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/delete/{enumIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String enumIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	Locale locale = request.getLocale();
    	if(enumIdList!=null){
    		String[] a=enumIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgEnumInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgEnumInfo.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgEnumInfo/list/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgEnumInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgEnumInfo> list=cfgEnumInfoManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getEnumId());
    			obj.put("text", list.get(i).getEnumId());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	
}
