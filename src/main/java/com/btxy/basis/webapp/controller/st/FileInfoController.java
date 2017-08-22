


	
package com.btxy.basis.webapp.controller.st;

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
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.FileInfo;
import com.btxy.basis.service.st.FileInfoManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.util.displaytable.PageTools;


@Controller
//@RequestMapping("/fileInfoes*")
public class FileInfoController extends BaseFormController{
	private static final String DM_FORM_NAME="fileInfo";

    private FileInfoManager fileInfoManager;

    @Autowired
    public void setFileInfoManager(FileInfoManager fileInfoManager) {
        this.fileInfoManager = fileInfoManager;
    }
    public FileInfoController() {      
    }  
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue)throws Exception {	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag)throws Exception {
        Model model = new ExtendedModelMap();
       
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        int currentPage=super.getDisplayTagCurrentPage(request,"fileInfoList");
        
    	int pageSize=PageTools.getPageSizeOfUserForm(request,"fileInfo");
    	
    	model.addAttribute("searchValue",searchValue);
    	model.addAttribute("listFlag",listFlag);
    	
        try {       
			PaginatedListHelper<FileInfo> paginaredList = fileInfoManager.find(currentPage, pageSize, new QueryContditionSet<FileInfo>() {
				@Override
				public void setContdition(Query<FileInfo> q) {
					if (searchValue.getCombinedConditionValue() != null) {
						if (searchValue.getCombinedConditionValue().containsKey("fileName")) {
							Object cvalue = searchValue.getCombinedConditionValue().get("fileName");
							if (cvalue != null && !cvalue.toString().trim().equals("")) {
								q.and(q.criteria("fileName").contains(cvalue.toString().trim()));
							}
						}
					}
					doCustomPropertySearchCondation(searchValue, q);
				}
			});

        	//model.addAttribute(paginaredList);
        	model.addAttribute("fileInfoList",paginaredList.getList());
        	model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", paginaredList.getFullListSize());
        } catch (Exception se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(new PaginatedListHelper<FileInfo>());
            model.addAttribute("fileInfoList",new ArrayList<FileInfo>());
            model.addAttribute("pageSize", pageSize);
        	model.addAttribute("totalSize", 0);
        }
        return new ModelAndView("base/st/FileInfoList", model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/view/{id}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long id)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", false);
    	
        if (id!=null) {
            FileInfo fileInfo= fileInfoManager.get(id);
            model.addAttribute("fileInfo", fileInfo);
        }
        return new ModelAndView("base/st/FileInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        FileInfo fileInfo= new  FileInfo();
        fileInfo.setId(SequenceUtil.getNext(FileInfo.class));     
           
  		
        model.addAttribute("fileInfo", fileInfo);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfFileInfoForm", "1");

        return new ModelAndView("base/st/FileInfoForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/edit/{id}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long id)throws Exception {
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (id!=null) {
            FileInfo fileInfo= fileInfoManager.get(id);
            model.addAttribute("fileInfo", fileInfo);
            
        }
        return new ModelAndView("base/st/FileInfoForm", model.asMap());
    }
    
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,FileInfo fileInfo,  HttpServletRequest request,
                           HttpServletResponse response)throws Exception {  
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfFileInfoForm", request.getParameter("addFlagOfFileInfoForm"));
        
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(fileInfo, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView("base/st/FileInfoForm", model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfFileInfoForm")));
        
  		String success = "redirect:/lb/{libraryPath}/fileInfo/list/mt/php";
  		
        
        Locale locale = request.getLocale();

        if(isNew){
        	fileInfoManager.save(fileInfo,true);
        }else{
        	fileInfoManager.saveMainBody(fileInfo);
        }
        
        
        
        String key = (isNew) ? "fileInfo.added" : "fileInfo.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) {   	
        	        
	  		success = "redirect:/lb/{libraryPath}/fileInfo/view/"+fileInfo.getId()+"/php" ;
            
        }
    

        return new ModelAndView(success, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/delete/{idList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request,
            HttpServletResponse response,@PathVariable String idList)throws Exception {
        Model model = new ExtendedModelMap(); 
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(idList!=null){
    		String[] a=idList.split("-");
    		if(a!=null){
    			for(String one:a){
    				fileInfoManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText("fileInfo.deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/fileInfo/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
    
    @RequestMapping(value = "/lb/{libraryPath}/fileInfo/list/select/php*")
    public void getSelect2Json(@PathVariable String libraryPath,HttpServletResponse response)throws Exception {	
    	List<FileInfo> list=fileInfoManager.getAll();
    	JSONArray array=new JSONArray();
    	for(int i=0;list!=null && i<list.size();i++){
    		    JSONObject obj=new JSONObject();
    			obj.put("id", list.get(i).getId());
    			obj.put("text", list.get(i).getId());
    			array.add(obj);
    	}
    	returnJSON(array,response);
  	} 

	@RequestMapping(value = "/lb/{libraryPath}/fileInfo/edit/stateMachineSubmit/{objectId}/{machineId}/{buttonId}/php*",method = RequestMethod.POST)
    public void stateMachineSubmit(@PathVariable String libraryPath,@PathVariable Long objectId,@PathVariable Long machineId,@PathVariable Long buttonId,HttpServletResponse response)throws Exception {
    	JSONObject obj=new JSONObject();
    	try{
	    	//StTaskInfo task=StTaskInfoCache.getInstance().addTask(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath),taskType, formName, objectId, new HashMap<String,Object>());
	    	FileInfo fileInfo= fileInfoManager.get(objectId);
	    	if(fileInfo!=null){
	    		CfgStateMachineDefine define=CfgStateMachineDefineCache.getInstance().getEntityById(machineId);
	    		CfgStateMachineButton button=CfgStateMachineValueCache.getInstance().getCfgStateMachineButton(buttonId);
		        if(define!=null && button!=null){
		        	Method setMethod=CfgStateMachineDefineCache.getInstance().getStateFieldSetMethod(machineId);
		        	setMethod.invoke(fileInfo, new Object[]{button.getTargetStat()});
		        	fileInfoManager.save(fileInfo);
		        	
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
