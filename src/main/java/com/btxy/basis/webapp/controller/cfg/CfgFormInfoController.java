package com.btxy.basis.webapp.controller.cfg;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.service.cfg.CfgFormInfoManager;
import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
public class CfgFormInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgFormInfo";

    @Autowired
    private CfgFormInfoManager cfgFormInfoManager;

    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,0l,request,searchValue,"list");
  	} 
  	@RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/list/cfgFormInfo/{parentId}/php*")
    public ModelAndView listSecond(@PathVariable String libraryPath,@PathVariable final Long parentId,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {
  		return list(libraryPath,parentId,request,searchValue,"list");
  	}   
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/list/cfgFormInfo/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable final Long parentId,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        
        Model model = new ExtendedModelMap();
      	model.addAttribute("listFlag",listFlag);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgFormInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgFormInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("parentId",parentId);
        try {       
        	PaginatedListHelper<CfgFormInfo> paginaredList=cfgFormInfoManager.find(currentPage,pageSize,new QueryContditionSet<CfgFormInfo>(){
				@Override
				public void setContdition(Query<CfgFormInfo> q) {
					q.field("parent").equal(parentId);
			    		if(searchValue.getTextValue()!=null){
			    			q.or(q.criteria("formCode").contains(searchValue.getTextValue()),q.criteria("formName").contains(searchValue.getTextValue()),q.criteria("modelClassName").contains(searchValue.getTextValue()));
			    		}			    	
				}        		
        	});
        	List<CfgFormInfo> parentsList=new ArrayList<CfgFormInfo>();
        	Long p1=parentId;
        	while(p1!=0){
        		CfgFormInfo cfgFormInfo1=cfgFormInfoManager.get(p1);
        		if(cfgFormInfo1!=null){
        			parentsList.add(0,cfgFormInfo1);
        			p1=cfgFormInfo1.getParent()==null?0l:cfgFormInfo1.getParent();
        		}
        	}
        	model.addAttribute("parentsList",parentsList);
        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgFormInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgFormInfo>());
            model.addAttribute("cfgFormInfoList",new ArrayList<CfgFormInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("cfg/CfgFormInfoList", model.asMap());
     
    }

    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/view/{formId}/cfgFormInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long formId,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	model.addAttribute("parentId",parentId);
        if (formId!=null) {
            CfgFormInfo cfgFormInfo= cfgFormInfoManager.get(formId);
            model.addAttribute("cfgFormInfo", cfgFormInfo);
        }
        return new ModelAndView("cfg/CfgFormInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/add/cfgFormInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("parentId",parentId);
        CfgFormInfo cfgFormInfo= new  CfgFormInfo();
        cfgFormInfo.setFormId(SequenceUtil.getNext(CfgFormInfo.class));        
        cfgFormInfo.setParent(parentId);
        model.addAttribute("cfgFormInfo", cfgFormInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFormInfoForm", "1");

        return new ModelAndView("cfg/CfgFormInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/edit/{formId}/cfgFormInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long formId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);  
    	model.addAttribute("parentId",parentId); 	
        if (formId!=null) {
            CfgFormInfo cfgFormInfo= cfgFormInfoManager.get(formId);
            model.addAttribute("cfgFormInfo", cfgFormInfo);
            
        }
        return new ModelAndView("cfg/CfgFormInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/formSubmit/cfgFormInfo/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgFormInfo cfgFormInfo,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFormInfoForm", request.getParameter("addFlagOfCfgFormInfoForm"));
        model.addAttribute("parentId",parentId);
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgFormInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("cfg/CfgFormInfoForm", model.asMap());
            }
            try{
            	Class c=Class.forName("com.letv.flow.manage.model."+cfgFormInfo.getFormCode().substring(0,1).toUpperCase()+cfgFormInfo.getFormCode().substring(1));
            	cfgFormInfo.setModelClassName("com.letv.flow.manage.model."+cfgFormInfo.getFormCode().substring(0,1).toUpperCase()+cfgFormInfo.getFormCode().substring(1));
            }catch(Exception e){
            	saveError(request,"form不存在");
        		return new ModelAndView("cfg/CfgFormInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgFormInfoForm")));
        
        String success = "redirect:/lb/{libraryPath}/cfgFormInfo/list/cfgFormInfo/"+parentId+"/php" ;
        Locale locale = request.getLocale();

        if(isNew){
        	cfgFormInfoManager.save(cfgFormInfo);
        }else{
        	cfgFormInfoManager.saveMainBody(cfgFormInfo);
        }
        
        
        
        String key = (isNew) ? "cfgFormInfo.added" : "cfgFormInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {
            success = "redirect:/lb/{libraryPath}/cfgFormInfo/list/cfgFormInfo/"+parentId+"/php" ;
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/delete/{formIdList}/cfgFormInfo/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable String formIdList,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response)throws Exception {
        Model model = new ExtendedModelMap(); 
        model.addAttribute("parentId",parentId);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	Locale locale = request.getLocale();
    	if(formIdList!=null){
    		String[] a=formIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgFormInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgFormInfo.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgFormInfo/list/cfgFormInfo/"+parentId+"/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
        
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFormInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgFormInfo> list=cfgFormInfoManager.getAll();
    	Map<Long,List<CfgFormInfo>> map=new HashMap<Long,List<CfgFormInfo>>();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		 MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
    	}
    	initTree("",0l,array,map);
    	returnJSON(array,response);
  	} 

	private void initTree(String parentLab,Long parentId,JSONArray array,Map<Long,List<CfgFormInfo>> map){
    	List<CfgFormInfo> list1=map.get(parentId);
    	if(list1!=null){
    		for(CfgFormInfo one:list1){
    			JSONObject obj=new JSONObject();
    			obj.put("id", one.getFormId());
    			obj.put("text", parentLab+one.getFormName());
    			array.add(obj);
    			initTree(parentLab+"&nbsp;&nbsp;&nbsp;&nbsp;",one.getFormId(),array,map);
    		}
    	}
    }
}
