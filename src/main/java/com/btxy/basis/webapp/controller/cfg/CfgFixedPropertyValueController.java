




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
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;

import com.btxy.basis.model.CfgFixedPropertyDefine;
import com.btxy.basis.model.CfgFixedPropertyValue;
import com.btxy.basis.service.cfg.CfgFixedPropertyDefineManager;
import com.btxy.basis.service.cfg.CfgFixedPropertyValueManager;
import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
public class CfgFixedPropertyValueController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgFixedPropertyValue";

    private CfgFixedPropertyValueManager cfgFixedPropertyValueManager;

    @Autowired
    public void setCfgFixedPropertyValueManager(CfgFixedPropertyValueManager cfgFixedPropertyValueManager) {
        this.cfgFixedPropertyValueManager = cfgFixedPropertyValueManager;
    }
    
    private CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager;

    @Autowired
	public void setCfgFixedPropertyDefineManager(
			CfgFixedPropertyDefineManager cfgFixedPropertyDefineManager) {
		this.cfgFixedPropertyDefineManager = cfgFixedPropertyDefineManager;
	}
	public CfgFixedPropertyValueController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/list/{propertyDefineId}/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,@PathVariable Long propertyDefineId,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,0l,propertyDefineId,request,searchValue,"list");
  	} 
  	@RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/list/{propertyDefineId}/cfgFixedPropertyValue/{parentId}/php*")
    public ModelAndView listSecond(@PathVariable String libraryPath,@PathVariable final Long parentId,@PathVariable Long propertyDefineId,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {
  		return list(libraryPath,parentId,propertyDefineId,request,searchValue,"list");
  	}   
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/list/{propertyDefineId}/cfgFixedPropertyValue/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable final Long parentId,@PathVariable final Long propertyDefineId,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        
        Model model = new ExtendedModelMap();
      	model.addAttribute("listFlag",listFlag);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgFixedPropertyValueList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgFixedPropertyValue");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("parentId",parentId);
    	model.addAttribute("propertyDefineId",propertyDefineId);
    	
    	int valueTreeLevel=1;
    	if(parentId==null || parentId.longValue()==0){
    		valueTreeLevel=1; 		
    	}else{
    		CfgFixedPropertyValue pobj=this.cfgFixedPropertyValueManager.get(parentId);
    		if(pobj==null || pobj.getParent()==null || pobj.getParent().longValue()==0){
    			valueTreeLevel=2; 	
    		}else{
    			valueTreeLevel=3;
    		}  			
    	}
    	CfgFixedPropertyDefine cfd=cfgFixedPropertyDefineManager.get(propertyDefineId);
    	if(cfd!=null && "AXA".equals(cfd.getValueType())){
    		model.addAttribute("showTreeHyperlink","0");
    	}else if(cfd!=null &&  "AXB".equals(cfd.getValueType())){
    		if(valueTreeLevel==1){
    			model.addAttribute("showTreeHyperlink","1");
    		}else{
    			model.addAttribute("showTreeHyperlink","0");
    		}
    		
    	}else if(cfd!=null &&  "AXC".equals(cfd.getValueType())){
    		model.addAttribute("showTreeHyperlink","1");
    	}else{
    		model.addAttribute("showTreeHyperlink","0");
    	}
    	
    	if(propertyDefineId!=null){
   		 	CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyDefineId);
   		 	model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
    	}
    	
        try {       
        	PaginatedListHelper<CfgFixedPropertyValue> paginaredList=cfgFixedPropertyValueManager.find(currentPage,pageSize,LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),new QueryContditionSet<CfgFixedPropertyValue>(){
				@Override
				public void setContdition(Query<CfgFixedPropertyValue> q) {
					// TODO Auto-generated method stub
					
					q.field("parent").equal(parentId);
			    	q.and(q.criteria("cfgFixedPropertyDefineId").equal(propertyDefineId));
			    		if(searchValue.getTextValue()!=null){
			    			q.or(q.criteria("propertyName").contains(searchValue.getTextValue()));
			    		}			    	
				}        		
        	});
        	//model.addAttribute(cfgFixedPropertyValueManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)));
        	List<CfgFixedPropertyValue> parentsList=new ArrayList<CfgFixedPropertyValue>();
        	Long p1=parentId;
        	while(p1!=0){
        		CfgFixedPropertyValue cfgFixedPropertyValue1=cfgFixedPropertyValueManager.get(p1);
        		if(cfgFixedPropertyValue1!=null){
        			parentsList.add(0,cfgFixedPropertyValue1);
        			p1=cfgFixedPropertyValue1.getParent()==null?0l:cfgFixedPropertyValue1.getParent();
        		}
        	}
        	model.addAttribute("parentsList",parentsList);
        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgFixedPropertyValueList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgFixedPropertyValue>());
            model.addAttribute("cfgFixedPropertyValueList",new ArrayList<CfgFixedPropertyValue>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyValueList", model.asMap());
     
    }

    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/view/{propertyDefineId}/{propertyValueId}/cfgFixedPropertyValue/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long propertyValueId,@PathVariable Long parentId,@PathVariable Long propertyDefineId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	model.addAttribute("parentId",parentId);
    	model.addAttribute("propertyDefineId",propertyDefineId);
    	if(propertyDefineId!=null){
    		 CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyDefineId);
    		 model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
    	}
        if (propertyValueId!=null) {
            CfgFixedPropertyValue cfgFixedPropertyValue= cfgFixedPropertyValueManager.get(propertyValueId);
            model.addAttribute("cfgFixedPropertyValue", cfgFixedPropertyValue);
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyValueForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/add/{propertyDefineId}/cfgFixedPropertyValue/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId,@PathVariable Long propertyDefineId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("parentId",parentId);
    	model.addAttribute("propertyDefineId",propertyDefineId);
    	
    	if(propertyDefineId!=null){
   		 	CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyDefineId);
   		 	model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
    	}
    	
        CfgFixedPropertyValue cfgFixedPropertyValue= new  CfgFixedPropertyValue();
        cfgFixedPropertyValue.setPropertyValueId(SequenceUtil.getNext(CfgFixedPropertyValue.class));        
        	cfgFixedPropertyValue.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	cfgFixedPropertyValue.setOvert(false);
        cfgFixedPropertyValue.setParent(parentId);
        cfgFixedPropertyValue.setCfgFixedPropertyDefineId(propertyDefineId);
        model.addAttribute("cfgFixedPropertyValue", cfgFixedPropertyValue);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyValueForm", "1");

        return new ModelAndView("base/cfg/CfgFixedPropertyValueForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/edit/{propertyDefineId}/{propertyValueId}/cfgFixedPropertyValue/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long propertyValueId,@PathVariable Long parentId,@PathVariable Long propertyDefineId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);  
    	model.addAttribute("parentId",parentId); 	
    	model.addAttribute("propertyDefineId",propertyDefineId);
    	
    	if(propertyDefineId!=null){
   		 	CfgFixedPropertyDefine cfgFixedPropertyDefine= cfgFixedPropertyDefineManager.get(propertyDefineId);
   		 	model.addAttribute("cfgFixedPropertyDefine", cfgFixedPropertyDefine);
    	}
    	
    	
        if (propertyValueId!=null) {
            CfgFixedPropertyValue cfgFixedPropertyValue= cfgFixedPropertyValueManager.get(propertyValueId);
            model.addAttribute("cfgFixedPropertyValue", cfgFixedPropertyValue);
            
        }
        return new ModelAndView("base/cfg/CfgFixedPropertyValueForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/formSubmit/{propertyDefineId}/cfgFixedPropertyValue/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgFixedPropertyValue cfgFixedPropertyValue,@PathVariable Long parentId,@PathVariable Long propertyDefineId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgFixedPropertyValueForm", request.getParameter("addFlagOfCfgFixedPropertyValueForm"));
        model.addAttribute("parentId",parentId);
        model.addAttribute("propertyDefineId",propertyDefineId);
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgFixedPropertyValue, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgFixedPropertyValueForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgFixedPropertyValueForm")));
        
        String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyValue/list/"+propertyDefineId+"/cfgFixedPropertyValue/"+parentId+"/php" ;
        Locale locale = request.getLocale();

        if(isNew){
        	cfgFixedPropertyValueManager.save(cfgFixedPropertyValue);
        }else{
        	cfgFixedPropertyValueManager.saveMainBody(cfgFixedPropertyValue);
        }
        
        
        
        String key = (isNew) ? "cfgFixedPropertyValue.added" : "cfgFixedPropertyValue.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {
            success = "redirect:/lb/{libraryPath}/cfgFixedPropertyValue/list/"+propertyDefineId+"/cfgFixedPropertyValue/"+parentId+"/php" ;
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/delete/{propertyDefineId}/{propertyValueIdList}/cfgFixedPropertyValue/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable String propertyValueIdList,@PathVariable Long parentId,@PathVariable Long propertyDefineId,HttpServletRequest request,
            HttpServletResponse response)throws Exception {
        Model model = new ExtendedModelMap(); 
        model.addAttribute("parentId",parentId);
        model.addAttribute("propertyDefineId",propertyDefineId);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	Locale locale = request.getLocale();
    	if(propertyValueIdList!=null){
    		String[] a=propertyValueIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgFixedPropertyValueManager.remove(new Long(one));
    				deleteChildren(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgFixedPropertyValue.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/cfgFixedPropertyValue/list/"+propertyDefineId+"/cfgFixedPropertyValue/"+parentId+"/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
        
    }    
    private void deleteChildren(final Long parentId){
    	List<CfgFixedPropertyValue> list=cfgFixedPropertyValueManager.find(new QueryContditionSet<CfgFixedPropertyValue>(){
			@Override
			public void setContdition(Query<CfgFixedPropertyValue> q) {
				// TODO Auto-generated method stub
				q.field("parent").equal(parentId);
			}        		
    	});
    	for(int i=0;list!=null && i<list.size();i++){
    		cfgFixedPropertyValueManager.remove(list.get(i).getPropertyValueId());
    		deleteChildren(list.get(i).getPropertyValueId());
    	}
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgFixedPropertyValue/list/{propertyDefineId}/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,@PathVariable Long propertyDefineId,HttpServletResponse response)throws Exception {	
    	List<CfgFixedPropertyValue> list=cfgFixedPropertyValueManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	Map<Long,List<CfgFixedPropertyValue>> map=new HashMap<Long,List<CfgFixedPropertyValue>>();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		 MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
    	}
    	initTree("",0l,array,map);
    	returnJSON(array,response);
  	} 

	private void initTree(String parentLab,Long parentId,JSONArray array,Map<Long,List<CfgFixedPropertyValue>> map){
    	List<CfgFixedPropertyValue> list1=map.get(parentId);
    	if(list1!=null){
    		for(CfgFixedPropertyValue one:list1){
    			JSONObject obj=new JSONObject();
    			obj.put("id", one.getPropertyValueId());
    			obj.put("text", parentLab+one.getPropertyValueId());
    			array.add(obj);
    			initTree(parentLab+"&nbsp;&nbsp;&nbsp;&nbsp;",one.getPropertyValueId(),array,map);
    		}
    	}
    }
}
