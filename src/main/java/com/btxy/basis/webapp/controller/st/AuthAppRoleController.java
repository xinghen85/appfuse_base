


	
package com.btxy.basis.webapp.controller.st;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppRole;
import com.btxy.basis.service.st.AuthAppRoleManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.mongodb.MongoException;
@Controller
public class AuthAppRoleController extends BaseFormController{
	private static final String DM_FORM_NAME="authAppRole";
	private static final String LIST_NAME="base/st/AuthAppRoleList";
	private static final String FORM_NAME="base/st/AuthAppRoleForm";

    @Autowired
    private AuthAppRoleManager dbManager;
    
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/list/php*")
    public ModelAndView listFirst(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue){	
  		return list(libraryPath,request,searchValue,"mt");
  	} 
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/list/{listFlag}/php*")
    public ModelAndView list(@PathVariable String libraryPath,HttpServletRequest request,final SearchConditionValue searchValue,@PathVariable String listFlag){

		QueryContditionSet<AuthAppRole> qcs = new QueryContditionSet<AuthAppRole>() {
			@Override
			public void setContdition(Query<AuthAppRole> q) {
				if (searchValue.getTextValue() != null ) {
					q.or(q.criteria("description").contains(searchValue.getTextValue()), q.criteria("roleName").contains(searchValue.getTextValue()));
				}
			}
		};
		Model model=super.list(dbManager, DM_FORM_NAME, libraryPath, request, searchValue, listFlag, qcs);
        return new ModelAndView(LIST_NAME, model.asMap());
    }
 
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/view/{id}/php*",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String libraryPath,@PathVariable Long id){
        return super.view(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, id);
    }
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/edit/{id}/php*",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String libraryPath,@PathVariable Long id){
        return super.edit(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, id);
    }
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/delete/{idList}/php*",method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String libraryPath,HttpServletRequest request, HttpServletResponse response,@PathVariable String idList){
        return super.delete(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, request, idList);
    }
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/add/php*",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String libraryPath){
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        AuthAppRole authAppRole= new  AuthAppRole();
        authAppRole.setRoleId(SequenceUtil.getNext(AuthAppRole.class));     
        authAppRole.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
        authAppRole.setOvert(false);
  		
        model.addAttribute(DM_FORM_NAME, authAppRole);
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthAppRoleForm", "1");

        return new ModelAndView(FORM_NAME, model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/formSubmit/php*",method = RequestMethod.POST)

    public ModelAndView onSubmit(@PathVariable String libraryPath,AuthAppRole bean,  HttpServletRequest request, HttpServletResponse response){  
        Model model = new ExtendedModelMap(); 
  		String success = "redirect:/lb/{libraryPath}/authAppRole/list/mt/php";
        model.addAttribute("formEditFlag", true);
        model.addAttribute("addFlagOfAuthAppRoleForm", request.getParameter("addFlagOfAuthAppRoleForm"));
        
		try {
        super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
        if (validator != null) { // validator is null during testing     
        	ServerValidataResult svr=new ServerValidataResult();      	
        	validate(bean, svr);      	
            
            if (svr.hasErrors()) { // don't validate when deleting
            	saveError(request,svr.getAllErrorMessage());
        		return new ModelAndView(FORM_NAME, model.asMap());
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthAppRoleForm")));
        Locale locale = request.getLocale();
        if(isNew){
        	dbManager.save(bean,true);
        }else{
        	dbManager.saveMainBody(bean);
        }
        
        String key = (isNew) ? "authAppRole.added" : "authAppRole.updated";
        saveMessage(request, getText(key, locale));

        if (!isNew) { 
	  		success = "redirect:/lb/{libraryPath}/authAppRole/view/"+bean.getRoleId()+"/php" ;
            
        }
		} catch (MongoException e) {
			e.printStackTrace();

			saveMessage(request, "添加角色失败，" + e.getMessage());

		}

        return new ModelAndView(success, model.asMap());
    }
    public static class KeyValue{
    	public Long getId() {
			return id;
		}
		public String getText() {
			return text;
		}
		Long id;
		String text;
		public KeyValue(Long id, String text) {
			super();
			this.id = id;
			this.text = text;
		}
    }

    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/list/select/php*")
    @ResponseBody
    public Object getSelect2Json2(){
    	List<AuthAppRole> list=dbManager.getAll();
    	List<KeyValue> out=new ArrayList<KeyValue>();
    	for (AuthAppRole object : list) {
			out.add(new KeyValue( object.getRoleId(),object.getRoleName()));
		}
    	return out;
  	} 
    
    
    
    
    
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/edit/privilege/{roleId}/php*",method = RequestMethod.GET)
    public ModelAndView editPrivilege(@PathVariable String libraryPath,@PathVariable Long roleId){
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (roleId!=null) {
            AuthAppRole authAppRole= dbManager.get(roleId);
            model.addAttribute(DM_FORM_NAME, authAppRole);
        }
        return new ModelAndView("base/st/AuthAppRoleAndPrivilegeForm", model.asMap());
    }
    @RequestMapping(value = "/lb/{libraryPath}/authAppRole/edit/privilege/{roleId}/submit/php*",method = RequestMethod.POST)
    public ModelAndView editPrivilegeSubmit(@PathVariable String libraryPath,@PathVariable Long roleId, HttpServletRequest request,
            HttpServletResponse response){
    	Model model = new ExtendedModelMap();
    	super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (roleId!=null) {
            AuthAppRole authAppRole= dbManager.get(roleId);
            
            
            String[] selectPrivilegeList=request.getParameterValues("selectPrivilegeList");
            List<Long> idlist=new ArrayList<Long>();
            for(int i=0;selectPrivilegeList!=null && i<selectPrivilegeList.length;i++){
            	idlist.add(new Long(selectPrivilegeList[i]));
            }
            authAppRole.setPrivilegeList(idlist);
            dbManager.save(authAppRole);
            
            model.addAttribute(DM_FORM_NAME, authAppRole);
            
            saveMessage(request, "为角色分配权限成功！");
            System.out.println(selectPrivilegeList);
        }
        return new ModelAndView("redirect:/lb/{libraryPath}/authAppRole/list/mt/php", model.asMap());
    }
    
    

	
}
