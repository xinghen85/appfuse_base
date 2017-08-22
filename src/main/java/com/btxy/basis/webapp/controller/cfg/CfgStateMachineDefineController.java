package com.btxy.basis.webapp.controller.cfg;

import java.lang.reflect.Method;
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
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;

import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.service.cfg.CfgStateMachineDefineManager;
import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;
@Controller
//@RequestMapping("/cfgStateMachineDefines*")
public class CfgStateMachineDefineController extends BaseFormController{
	private static final String DM_FORM_NAME="cfgStateMachineDefine";

    private CfgStateMachineDefineManager cfgStateMachineDefineManager;

    @Autowired
    public void setCfgStateMachineDefineManager(CfgStateMachineDefineManager cfgStateMachineDefineManager) {
        this.cfgStateMachineDefineManager = cfgStateMachineDefineManager;
    }
    public CfgStateMachineDefineController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"cfgStateMachineDefineList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"cfgStateMachineDefine");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
        	PaginatedListHelper<CfgStateMachineDefine> paginaredList=cfgStateMachineDefineManager.find(currentPage,pageSize,new QueryContditionSet<CfgStateMachineDefine>(){
				@Override
				public void setContdition(Query<CfgStateMachineDefine> q) {
					// TODO Auto-generated method stub
			    	
					//if(searchValue.getTextValue()!=null && !"".equals(searchValue.getTextValue())){}
			    			if(searchValue.getCombinedConditionValue()!=null){
			if(searchValue.getCombinedConditionValue().containsKey("enumId")){
				Object cvalue=searchValue.getCombinedConditionValue().get("enumId");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("enumId").in(ListUtil.pasreLongList(cvalue.toString(),",")));
				}
			}
			if(searchValue.getCombinedConditionValue().containsKey("machineName")){
				Object cvalue=searchValue.getCombinedConditionValue().get("machineName");
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					q.and(q.criteria("machineName").contains(cvalue.toString().trim()));
				}
			}
		}
		doCustomPropertySearchCondation(searchValue,q);
				}        		
        	});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("cfgStateMachineDefineList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<CfgStateMachineDefine>());
            model.addAttribute("cfgStateMachineDefineList",new ArrayList<CfgStateMachineDefine>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/cfg/CfgStateMachineDefineList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/view/{machineId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long machineId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (machineId!=null) {
            CfgStateMachineDefine cfgStateMachineDefine= cfgStateMachineDefineManager.get(machineId);
            model.addAttribute("cfgStateMachineDefine", cfgStateMachineDefine);
        }
        return new ModelAndView("base/cfg/CfgStateMachineDefineForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        CfgStateMachineDefine cfgStateMachineDefine= new  CfgStateMachineDefine();
        cfgStateMachineDefine.setMachineId(SequenceUtil.getNext(CfgStateMachineDefine.class));     
           
  		
        model.addAttribute("cfgStateMachineDefine", cfgStateMachineDefine);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgStateMachineDefineForm", "1");

        return new ModelAndView("base/cfg/CfgStateMachineDefineForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/edit/{machineId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long machineId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (machineId!=null) {
            CfgStateMachineDefine cfgStateMachineDefine= cfgStateMachineDefineManager.get(machineId);
            model.addAttribute("cfgStateMachineDefine", cfgStateMachineDefine);
            
        }
        return new ModelAndView("base/cfg/CfgStateMachineDefineForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,CfgStateMachineDefine cfgStateMachineDefine,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfCfgStateMachineDefineForm", request.getParameter("addFlagOfCfgStateMachineDefineForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(cfgStateMachineDefine, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/cfg/CfgStateMachineDefineForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfCfgStateMachineDefineForm")));
        
  		String success = "redirect:/lb/{libraryPath}/cfgStateMachineDefine/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	cfgStateMachineDefineManager.save(cfgStateMachineDefine,true);
        }else{
        	cfgStateMachineDefineManager.saveMainBody(cfgStateMachineDefine);
        }
        
        
        
        String key = (isNew) ? "cfgStateMachineDefine.added" : "cfgStateMachineDefine.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/cfgStateMachineDefine/view/"+cfgStateMachineDefine.getMachineId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/view/refreshButtonGroup/{formName}/{stateMachineId}/{stateFieldElement}/{buttonGroupIFrameElement}/{stateValue}/{objectId}/{otherObjectId1}/{otherObjectId2}/{otherObjectId3}/{taskId}/php*",method = RequestMethod.GET)
    public void view_RefreshButtonGroup2(@PathVariable String libraryPath,@PathVariable String formName,@PathVariable Long stateMachineId,@PathVariable String stateFieldElement,@PathVariable String buttonGroupIFrameElement,
    		@PathVariable String stateValue,@PathVariable String objectId,@PathVariable String otherObjectId1,@PathVariable String otherObjectId2,@PathVariable String otherObjectId3,@PathVariable String taskId, HttpServletRequest request,HttpServletResponse response)throws Exception {
    	String ctx=request.getParameter("ctxValue");
    	if(ctx==null){
    		ctx="";
    	}
    	JSONObject rtnObj=new JSONObject();
    	
    	JSONArray array=new JSONArray();
    	
    	
    	Class<?> formClass=CfgStateMachineDefineCache.getInstance().getFormClass(stateMachineId);
    	Method method=CfgStateMachineDefineCache.getInstance().getStateFieldGetMethod(stateMachineId);
    	if(formClass!=null){
    		try {  	
    			String newState = "";
    			if ("rptFunctionInstance".equals(formName)) {
					//RptFunctionInstanceManager rptFunctionInstanceManager = SpringContext.getApplicationContext().getBean("rptFunctionInstanceManager", RptFunctionInstanceManager.class);
					//RptFunctionInstance rptFunctionInstance = rptFunctionInstanceManager.getRptFunctionInstance(new Long(objectId), new Long(otherObjectId1));
					//newState = rptFunctionInstance.getStatus();
				}else {
					Object obj=SpringContext.getDatastore().get(formClass,new Long(objectId));
	            	newState=(String) method.invoke(obj);//执行get方法返回一个Object
				}
    			
    			
            	rtnObj.put("newState", newState);
            	
            	/*lyz
            	 StTaskInfo unfinishedTask=StTaskInfoCache.getInstance().getUnFinishedTaskList(formName, new Long(objectId));
            	
            	if(stateValue!=null && stateValue.equals(newState)){
                	if(unfinishedTask!=null){
        				rtnObj.put("ifRefresh", 1);
        			}else{
        				rtnObj.put("ifRefresh", 0);
        			}
            	}else{
            		
            		//获取最新状态的参数
            		rtnObj.put("newStateText", CfgEnumInfoCache.getInstance().getEnumValueByEnumCode(newState));
            		       		            		
            		
            		
        			Map<String,String> map=new HashMap<String,String>();
        			
        			map.put("objectId", objectId);
        			//map.put("taskId", taskId);
        			map.put("formName", formName);
        			
        			map.put("otherObjectId1", otherObjectId1);
        			map.put("otherObjectId2", otherObjectId2);
        			map.put("otherObjectId3", otherObjectId3);
        			
        			
        			if(unfinishedTask!=null){
                		map.put("taskId",unfinishedTask.getTaskId().toString() );
                	}else{
                		map.put("taskId", "0");
                	}
        			
        			if(unfinishedTask!=null){
        				rtnObj.put("ifRefresh", 1);
        			}else{
        				rtnObj.put("ifRefresh", 0);
        			}
        			
        			List<AuthPrivilegeInfo> authlist=AuthPrivilegeInfoCache.getInstance().getButtonGroupByFormCode(formName);
        			StringBuffer sb = new StringBuffer();
        			
        			if(authlist!=null ){
        				for(AuthPrivilegeInfo cmb:authlist){
        					if(stateMachineId!=null){
        						if(cmb.getMachineId()!=null && cmb.getMachineId().longValue()==stateMachineId.longValue()){
        							
        							if(ifShowButton(newState,cmb.getEnumCodeList())){
        								if(!(unfinishedTask!=null && unfinishedTask.getTaskType().equals(cmb.getTaskType()))){
        									String realUrl=cmb.getUrl();

                        					if(realUrl!=null){
                        						realUrl=StringTemplate.executeSmall(realUrl, map);
                        					}
                        					
                        					sb.append("	<button class='btn btn-info  btn-sm' type='button' onclick='btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+objectId+"()' id='btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+objectId+"Element'  >\r\n");
                							sb.append("		"+cmb.getPrivilegeName()+"\r\n");
                							sb.append(" </button>\r\n");
                        					
                        					JSONObject a0=new JSONObject();
                        					a0.put("name", cmb.getPrivilegeName());
                        					a0.put("url", realUrl);
                        	    			array.add(a0);
        								}
        								
        							}
        							
        						}
        						
        					}					
        					
        					
        				}
        			}
        			rtnObj.put("btnGroupHtml", sb.toString());
            			
            	}
            	*/
            	
            } catch (Exception e) {
				e.printStackTrace();
			}  
    	}
    	rtnObj.put("buttons", array);

       super.returnJSON(rtnObj, response);
    }
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/view/refreshButtonGroup/{formName}/{stateMachineId}/{stateFieldElement}/{buttonGroupIFrameElement}/{stateValue}/{objectId}/{taskId}/php*",method = RequestMethod.GET)
    public void view_RefreshButtonGroup(@PathVariable String libraryPath,@PathVariable String formName,@PathVariable Long stateMachineId,@PathVariable String stateFieldElement,@PathVariable String buttonGroupIFrameElement,
    		@PathVariable String stateValue,@PathVariable String objectId,@PathVariable String taskId, HttpServletRequest request,HttpServletResponse response)throws Exception {
    	view_RefreshButtonGroup2(libraryPath,formName,stateMachineId,stateFieldElement,buttonGroupIFrameElement,stateValue,objectId,"0","0","0",taskId,request,response);
    	
    }
    private boolean ifShowButton(String state,List<String> allowStateList){
		if(allowStateList!=null){
			for(String str0:allowStateList){
				if(state!=null && state.endsWith(str0)){
					return true;
				}
			}
		}
		return false;
	}
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/delete/{machineIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String machineIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(machineIdList!=null){
    		String[] a=machineIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				cfgStateMachineDefineManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("cfgStateMachineDefine.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/cfgStateMachineDefine/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<CfgStateMachineDefine> list=cfgStateMachineDefineManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getMachineId());
    			obj.put("text", list.get(i).getMachineName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/cfgStateMachineDefine/list/view/{machineId}/getEnumCodeList/php*")
    public void getSelect2JsonOfPrivilege(@PathVariable String libraryPath,@PathVariable Long machineId,HttpServletResponse response)throws Exception {	
    	JSONArray array=new JSONArray();
    	
    	if (machineId!=null) {
            CfgStateMachineDefine define= cfgStateMachineDefineManager.get(machineId);
            if(define!=null){
            	Long enumId=define.getEnumId();
				
				if(enumId!=null && enumId.longValue()>0){
					CfgEnumInfo enumInfo=CfgEnumInfoCache.getInstance().getCfgEnumInfoById(enumId);
					if(enumInfo!=null && enumInfo.getValues()!=null){
						for(CfgEnumValueInfo val:enumInfo.getValues()){
							JSONObject obj=new JSONObject();
			    			obj.put("id", enumInfo.getEnumCode()+val.getCode());
			    			obj.put("text", val.getValue());
			    			array.add(obj);
						}
					}
				}
            }
        }

    	returnJSON(array,response);
  	}
	
}
