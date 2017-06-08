




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
import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.service.cfg.AuthPrivilegeInfoManager;
import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.util.map.MapUtil;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
public class AuthPrivilegeInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="authPrivilegeInfo";

    private AuthPrivilegeInfoManager authPrivilegeInfoManager;

    @Autowired
    public void setAuthPrivilegeInfoManager(AuthPrivilegeInfoManager authPrivilegeInfoManager) {
        this.authPrivilegeInfoManager = authPrivilegeInfoManager;
    }
    public AuthPrivilegeInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,0l,request,searchValue,"mt");
  	} 
  	@RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/{parentId}/php*")
    public ModelAndView listSecond(@PathVariable String libraryPath,@PathVariable final Long parentId,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {
  		return list(libraryPath,parentId,request,searchValue,"mt");
  	}   
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/{parentId}/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,@PathVariable final Long parentId,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        
        Model model = new ExtendedModelMap();
      	model.addAttribute("listFlag",listFlag);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"authPrivilegeInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"authPrivilegeInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("parentId",parentId);
        try {       
        	PaginatedListHelper<AuthPrivilegeInfo> paginaredList=authPrivilegeInfoManager.find(currentPage,pageSize,"sortNo",new QueryContditionSet<AuthPrivilegeInfo>(){
				@Override
				public void setContdition(Query<AuthPrivilegeInfo> q) {
					// TODO Auto-generated method stub
					q.field("parent").equal(parentId);
			    				if(searchValue.getCombinedConditionValue()!=null){
			if(searchValue.getCombinedConditionValue().containsKey("operateName")){
				Object cvalue=searchValue.getCombinedConditionValue().get("operateName");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("operateName").in(ListUtil.pasreStringList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("operatorRequestType")){
				Object cvalue=searchValue.getCombinedConditionValue().get("operatorRequestType");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("operatorRequestType").in(ListUtil.pasreStringList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("privilegeName")){
				Object cvalue=searchValue.getCombinedConditionValue().get("privilegeName");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("privilegeName").contains(cvalue.toString().trim()));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("privilegeType")){
				Object cvalue=searchValue.getCombinedConditionValue().get("privilegeType");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("privilegeType").in(ListUtil.pasreStringList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("taskType")){
				Object cvalue=searchValue.getCombinedConditionValue().get("taskType");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("taskType").in(ListUtil.pasreStringList(cvalue.toString(),",")));
				}
			}
		}
		doCustomPropertySearchCondation(searchValue,q);;
				}        		
        	});
        	List<AuthPrivilegeInfo> parentsList=new ArrayList<AuthPrivilegeInfo>();
        	Long p1=parentId;
        	while(p1!=0){
        		AuthPrivilegeInfo authPrivilegeInfo1=authPrivilegeInfoManager.get(p1);
        		if(authPrivilegeInfo1!=null){
        			parentsList.add(0,authPrivilegeInfo1);
        			p1=authPrivilegeInfo1.getParent()==null?0l:authPrivilegeInfo1.getParent();
        		}
        	}
        	model.addAttribute("parentsList",parentsList);
        	//model.addAttribute(paginaredList);
        	model.addAttribute("authPrivilegeInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<AuthPrivilegeInfo>());
            model.addAttribute("authPrivilegeInfoList",new ArrayList<AuthPrivilegeInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("cfg/AuthPrivilegeInfoList", model.asMap());
     
    }

    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/view/{privilegeId}/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long privilegeId,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	model.addAttribute("parentId",parentId);
        if (privilegeId!=null) {
            AuthPrivilegeInfo authPrivilegeInfo= authPrivilegeInfoManager.get(privilegeId);
            model.addAttribute("authPrivilegeInfo", authPrivilegeInfo);
        }
        return new ModelAndView("cfg/AuthPrivilegeInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/add/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath,@PathVariable Long parentId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("parentId",parentId);
        AuthPrivilegeInfo authPrivilegeInfo= new  AuthPrivilegeInfo();
        authPrivilegeInfo.setPrivilegeId(SequenceUtil.getNext(AuthPrivilegeInfo.class));        
        authPrivilegeInfo.setParent(parentId);
        
       
        
        
        model.addAttribute("authPrivilegeInfo", authPrivilegeInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthPrivilegeInfoForm", "1");

        return new ModelAndView("cfg/AuthPrivilegeInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/edit/{privilegeId}/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long privilegeId,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);  
    	model.addAttribute("parentId",parentId); 	
        if (privilegeId!=null) {
            AuthPrivilegeInfo authPrivilegeInfo= authPrivilegeInfoManager.get(privilegeId);
            model.addAttribute("authPrivilegeInfo", authPrivilegeInfo);
            
        }
        return new ModelAndView("cfg/AuthPrivilegeInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/edit/move/{privilegeId}/{moveType}/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView editMove(@PathVariable String libraryPath,@PathVariable Long privilegeId,@PathVariable Integer moveType,@PathVariable Long parentId)throws Exception {
    	
        Model model = new ExtendedModelMap();
    	//super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	//model.addAttribute("formEditFlag", true);  
    	//model.addAttribute("parentId",parentId); 	
        if (privilegeId!=null) {
            //AuthPrivilegeInfo authPrivilegeInfo= authPrivilegeInfoManager.get(privilegeId);
            //model.addAttribute("authPrivilegeInfo", authPrivilegeInfo);
            
            authPrivilegeInfoManager.saveMove(parentId,privilegeId,moveType);
            
            
            
        }
        String success = "redirect:/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/{parentId}/mt/php" ;
        
        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/formSubmit/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthPrivilegeInfo authPrivilegeInfo,@PathVariable Long parentId,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {                
                           
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthPrivilegeInfoForm", request.getParameter("addFlagOfAuthPrivilegeInfoForm"));
        model.addAttribute("parentId",parentId);
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(authPrivilegeInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("cfg/AuthPrivilegeInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthPrivilegeInfoForm")));
        
        String success = "redirect:/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/"+parentId+"/mt/php" ;
        Locale locale = request.getLocale();

        CfgFormInfo formInfo=CfgFormInfoCache.getInstance().getEntityById(authPrivilegeInfo.getFormId());
        authPrivilegeInfo.setForm(formInfo);
        
        if(authPrivilegeInfo.getForm()!=null && "AWB".equals(authPrivilegeInfo.getPrivilegeType())){
        	//authPrivilegeInfo.setForm(cfgFormInfoManager.get(authPrivilegeInfo.getForm().getFormId()));
        	if(authPrivilegeInfo.getUrl()!=null && !authPrivilegeInfo.getUrl().equals("")){
        		
        	}else{
        		authPrivilegeInfo.setUrl("/"+authPrivilegeInfo.getForm().getFormCode()+"/list/mt/php");
        	}
        }
        
        if(isNew){
        	if(authPrivilegeInfo.getIconCode()==null || "".equals(authPrivilegeInfo.getIconCode())){
        		if("AWA".equals(authPrivilegeInfo.getPrivilegeType())){
                	authPrivilegeInfo.setIconCode("fa fa-folder");
        		}else if("AWB".equals(authPrivilegeInfo.getPrivilegeType())){
        			authPrivilegeInfo.setIconCode("fa fa-th-list");
        		}else if("AWC".equals(authPrivilegeInfo.getPrivilegeType())){
        			authPrivilegeInfo.setIconCode("fa fa-leaf");
        		}
        	}
        	
        	
        	authPrivilegeInfoManager.save(authPrivilegeInfo,true);
        }else{
        	authPrivilegeInfoManager.saveMainBody(authPrivilegeInfo);
        }
        if(isNew && authPrivilegeInfo.getForm()!=null && "AWB".equals(authPrivilegeInfo.getPrivilegeType())){
        	authPrivilegeInfoManager.rebuildChildPivilege(authPrivilegeInfo);
        }
        
        
        
        String key = (isNew) ? "authPrivilegeInfo.added" : "authPrivilegeInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {
            success = "redirect:/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/"+parentId+"/mt/php" ;
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/delete/{privilegeIdList}/authPrivilegeInfo/{parentId}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,@PathVariable String privilegeIdList,@PathVariable Long parentId,HttpServletRequest request,
            HttpServletResponse response)throws Exception {
        Model model = new ExtendedModelMap(); 
        model.addAttribute("parentId",parentId);
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	Locale locale = request.getLocale();
    	if(privilegeIdList!=null){
    		String[] a=privilegeIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				authPrivilegeInfoManager.remove(new Long(one));
    				deleteChildren(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("authPrivilegeInfo.deleted", locale));
        String success = "redirect:/lb/{libraryPath}/authPrivilegeInfo/list/authPrivilegeInfo/"+parentId+"/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
        
    }    
    private void deleteChildren(final Long parentId){
    	List<AuthPrivilegeInfo> list=authPrivilegeInfoManager.find(new QueryContditionSet<AuthPrivilegeInfo>(){
			@Override
			public void setContdition(Query<AuthPrivilegeInfo> q) {
				// TODO Auto-generated method stub
				q.field("parent").equal(parentId);
			}        		
    	});
    	for(int i=0;list!=null && i<list.size();i++){
    		authPrivilegeInfoManager.remove(list.get(i).getPrivilegeId());
    		deleteChildren(list.get(i).getPrivilegeId());
    	}
    }
    @RequestMapping(value = "/lb/{libraryPath}/authPrivilegeInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<AuthPrivilegeInfo> list=authPrivilegeInfoManager.getAll();
    	Map<Long,List<AuthPrivilegeInfo>> map=new HashMap<Long,List<AuthPrivilegeInfo>>();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		 MapUtil.appendListEntityToMap(map, list.get(i).getParent(), list.get(i));
    	}
    	initTree("",0l,array,map);
    	returnJSON(array,response);
  	} 

	private void initTree(String parentLab,Long parentId,JSONArray array,Map<Long,List<AuthPrivilegeInfo>> map){
    	List<AuthPrivilegeInfo> list1=map.get(parentId);
    	if(list1!=null){
    		for(AuthPrivilegeInfo one:list1){
    			JSONObject obj=new JSONObject();
    			obj.put("id", one.getPrivilegeId());
    			obj.put("text", parentLab+one.getPrivilegeName());
    			array.add(obj);
    			initTree(parentLab+"&nbsp;&nbsp;&nbsp;&nbsp;",one.getPrivilegeId(),array,map);
    		}
    	}
    }
}
