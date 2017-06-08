package com.btxy.basis.webapp.controller.st;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.common.CurrentUserUtil;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.StSystemNoticeInfo;
import com.btxy.basis.service.st.StSystemNoticeInfoManager;
import com.btxy.basis.util.list.ListUtil;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
//@RequestMapping("/stSystemNoticeInfoes*")
public class StSystemNoticeInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="stSystemNoticeInfo";

    private StSystemNoticeInfoManager stSystemNoticeInfoManager;

    @Autowired
    public void setStSystemNoticeInfoManager(StSystemNoticeInfoManager stSystemNoticeInfoManager) {
        this.stSystemNoticeInfoManager = stSystemNoticeInfoManager;
    }
    public StSystemNoticeInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"stSystemNoticeInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"stSystemNoticeInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
		try {
			PaginatedListHelper<StSystemNoticeInfo> paginaredList = stSystemNoticeInfoManager.find(currentPage, pageSize, LibraryInfoCache.getInstance()
					.getLibraryIdByPath(libraryPath), new QueryContditionSet<StSystemNoticeInfo>() {
				@Override
				public void setContdition(Query<StSystemNoticeInfo> q) {
					if (searchValue.getTextValue() != null) {
						q.or(q.criteria("noticeLevel").contains(searchValue.getTextValue()),
								q.criteria("noticeName").contains(searchValue.getTextValue()),
								q.criteria("noticeText").contains(searchValue.getTextValue()),
								q.criteria("noticeType").contains(searchValue.getTextValue()), 
								q.criteria("status").contains(searchValue.getTextValue()));
					}

					if (searchValue.getCombinedConditionValue() != null) {
						if (searchValue.getCombinedConditionValue().containsKey("noticeLevel")) {
							Object cvalue = searchValue.getCombinedConditionValue().get("noticeLevel");
							if (cvalue != null && !cvalue.toString().trim().equals("")) {
								q.and(q.criteria("noticeLevel").in(ListUtil.pasreStringList(cvalue.toString(), ",")));
							}
						}
						if (searchValue.getCombinedConditionValue().containsKey("noticeName")) {
							Object cvalue = searchValue.getCombinedConditionValue().get("noticeName");
							if (cvalue != null && !cvalue.toString().trim().equals("")) {
								q.and(q.criteria("noticeName").contains(cvalue.toString().trim()));
							}
						}
						if (searchValue.getCombinedConditionValue().containsKey("noticeType")) {
							Object cvalue = searchValue.getCombinedConditionValue().get("noticeType");
							if (cvalue != null && !cvalue.toString().trim().equals("")) {
								q.and(q.criteria("noticeType").in(ListUtil.pasreStringList(cvalue.toString(), ",")));
							}
						}
						if (searchValue.getCombinedConditionValue().containsKey("status")) {
							Object cvalue = searchValue.getCombinedConditionValue().get("status");
							if (cvalue != null && !cvalue.toString().trim().equals("")) {
								q.and(q.criteria("status").in(ListUtil.pasreStringList(cvalue.toString(), ",")));
							}
						}
					}
					doCustomPropertySearchCondation(searchValue, q);

				}
			});
			// model.addAttribute(stSystemNoticeInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)));

			// model.addAttribute(paginaredList);
			model.addAttribute("stSystemNoticeInfoList", paginaredList.getList());
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalSize", paginaredList.getFullListSize());
		} catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<StSystemNoticeInfo>());
            model.addAttribute("stSystemNoticeInfoList",new ArrayList<StSystemNoticeInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("st/StSystemNoticeInfoList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/view/{noticeId}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long noticeId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (noticeId!=null) {
            StSystemNoticeInfo stSystemNoticeInfo= stSystemNoticeInfoManager.get(noticeId);
            model.addAttribute("stSystemNoticeInfo", stSystemNoticeInfo);
        }
        return new ModelAndView("st/StSystemNoticeInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        StSystemNoticeInfo stSystemNoticeInfo= new  StSystemNoticeInfo();
        stSystemNoticeInfo.setNoticeId(SequenceUtil.getNext(StSystemNoticeInfo.class));     
        stSystemNoticeInfo.setCreateTime(new Date());
        stSystemNoticeInfo.setCreateUserId(CurrentUserUtil.getCurrentUser().getUserId());
        	stSystemNoticeInfo.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        	stSystemNoticeInfo.setOvert(false);
  		
        model.addAttribute("stSystemNoticeInfo", stSystemNoticeInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfStSystemNoticeInfoForm", "1");

        return new ModelAndView("st/StSystemNoticeInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/edit/{noticeId}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long noticeId)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (noticeId!=null) {
            StSystemNoticeInfo stSystemNoticeInfo= stSystemNoticeInfoManager.get(noticeId);
            model.addAttribute("stSystemNoticeInfo", stSystemNoticeInfo);
            
        }
        return new ModelAndView("st/StSystemNoticeInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,StSystemNoticeInfo stSystemNoticeInfo,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfStSystemNoticeInfoForm", request.getParameter("addFlagOfStSystemNoticeInfoForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(stSystemNoticeInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("st/StSystemNoticeInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfStSystemNoticeInfoForm")));
        
  		String success = "redirect:/lb/{libraryPath}/stSystemNoticeInfo/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	stSystemNoticeInfoManager.save(stSystemNoticeInfo,true);
        }else{
        	stSystemNoticeInfoManager.saveMainBody(stSystemNoticeInfo);
        }
        
        
        
        String key = (isNew) ? "stSystemNoticeInfo.added" : "stSystemNoticeInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/stSystemNoticeInfo/view/"+stSystemNoticeInfo.getNoticeId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/delete/{noticeIdList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String noticeIdList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(noticeIdList!=null){
    		String[] a=noticeIdList.split("-");
    		if(a!=null){
    			for(String one:a){
    				stSystemNoticeInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("stSystemNoticeInfo.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/stSystemNoticeInfo/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<StSystemNoticeInfo> list=stSystemNoticeInfoManager.getAll(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getNoticeId());
    			obj.put("text", list.get(i).getNoticeName());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/stSystemNoticeInfo/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	StSystemNoticeInfo stSystemNoticeInfo= stSystemNoticeInfoManager.get(objectId);
	    	if(stSystemNoticeInfo!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(stSystemNoticeInfo, new Object[]{button.getTargetStat()});
		        	stSystemNoticeInfoManager.save(stSystemNoticeInfo);
		        	
		        	obj.put("rtnValue", 1);
		    		obj.put("rtnDescription", button.getButtonName()+"成功!");
		    		super.returnJSON(obj, response);
		    		return;
		        }
	    	}
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	obj.put("rtnValue", 0);
		obj.put("rtnDescription", "数据更新失败，请稍后重试!");
    	super.returnJSON(obj, response);
    }
	
}
