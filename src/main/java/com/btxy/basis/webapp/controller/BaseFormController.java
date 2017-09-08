package com.btxy.basis.webapp.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.Constants;
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.cfg.CfgCustomPropertyCache;
import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.model.User;
import com.btxy.basis.service.MailEngine;
import com.btxy.basis.service.base.MgGenericManager;
import com.btxy.basis.webapp.util.displaytable.PageTools;

/**
 * Implementation of <strong>SimpleFormController</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Form controllers.
 *
 * <p><a href="BaseFormController.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseFormController implements ServletContextAware {

    public static class IdText{
		Object id;
		Object text;
		public IdText(Object id, Object text) {
			super();
			this.id = id;
			this.text = text;
		}
		public Object getId() {
			return id;
		}
		public Object getText() {
			return text;
		}
    }
    public static class Rtn{
		int rtnValue;
		String rtnDescription;
    		public int getRtnValue() {
			return rtnValue;
		}
		public String getRtnDescription() {
			return rtnDescription;
		}
		public Rtn(int rtnValue, String rtnDescription) {
			super();
			this.rtnValue = rtnValue;
			this.rtnDescription = rtnDescription;
		}
    }
    
    
    protected final transient Log log = LogFactory.getLog(getClass());
    public static final String MESSAGES_KEY = "successMessages";
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;
    protected String templateName = "accountCreated.vm";
    protected String cancelView;
    protected String successView;

    private MessageSourceAccessor messages;
    private ServletContext servletContext;

    @Autowired(required = false)
	protected
    Validator validator;

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }


    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }
    
    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

        if (messages == null) {
            messages = new ArrayList();
        }

        messages.add(msg);
        request.getSession().setAttribute(MESSAGES_KEY, messages);
    }

    /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey
     * @param arg
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, String arg, Locale locale) {
        return getText(msgKey, new Object[] { arg }, locale);
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey
     * @param args
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Object[] args, Locale locale) {
        return messages.getMessage(msgKey, args, locale);
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    public Map getConfiguration() {
        Map config = (HashMap) servletContext.getAttribute(Constants.CONFIG);

        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }

        return config;
    }

    /**
     * Set up a custom property editor for converting form inputs to real objects
     * @param request the current request
     * @param binder the data binder
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Integer.class, null,
                                    new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null,
                                    new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class,
                                    new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat(getText("date.format", request.getLocale()));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, 
                                    new CustomDateEditor(dateFormat, true));
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * @param user the user to send a message to.
     * @param msg the message to send.
     * @param url the URL of the application.
     */
    protected void sendUserMessage(User user, String msg, String url) {
        if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user [" + user.getEmail() + "]...");
        }

        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);

        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(message, templateName, model);
    }

    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
   
    public final BaseFormController setCancelView(String cancelView) {
        this.cancelView = cancelView;
        return this;
    }

    public final String getCancelView() {
        // Default to successView if cancelView is invalid
        if (this.cancelView == null || this.cancelView.length()==0) {
            return getSuccessView();
        }
        return this.cancelView;   
    }

    public final String getSuccessView() {
        return this.successView;
    }
    
    public final BaseFormController setSuccessView(String successView) {
        this.successView = successView;
        return this;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected ServletContext getServletContext() {
        return servletContext;
    }
    
    public int getDisplayTagCurrentPage(HttpServletRequest request,String displayTableId){
    	String strP=request.getParameter((new ParamEncoder(displayTableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
    	
    	int currentPage=0;
    	if(strP==null){
    		currentPage=1;
    	}else{
    		currentPage=Integer.parseInt(strP);
    	}
    	//当前页
    	//System.out.println("currentPage:"+currentPage);
    	return currentPage;
    }
    
    public void libraryAndPropertyPass(HttpServletRequest request,String formName,String libraryPath){
    	request.setAttribute("libraryPath", libraryPath);
    	request.setAttribute("customPropertyList",CfgCustomPropertyCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgCustomPropertyList(formName));
    	request.setAttribute("fixedPropertyList",CfgFixedPropertyDefineCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgFixedPropertyListByFormCode(formName));
    }
    
    public void libraryAndPropertyPass(Model model,String formName){
    	
    	//model.addAttribute("fixedPropertyList",CfgFixedPropertyDefineCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgFixedPropertyListByFormCode(formName));
    	
    	
    	
    	model.addAttribute("allEnumInfoMap",CfgEnumInfoCache.getInstance().getCfgEnumInfoMap());
    	model.addAttribute("allEnumValueInfoMap",CfgEnumInfoCache.getInstance().getCfgAllEnumValueMap());

    }
    public void libraryAndPropertyPass(Model model,String formName,String libraryPath){
    	model.addAttribute("libraryPath", libraryPath);
    	model.addAttribute("libraryId", LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
    	model.addAttribute("customPropertyList",CfgCustomPropertyCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgCustomPropertyList(formName));
    	
    	model.addAttribute("fixedPropertyList",CfgFixedPropertyDefineCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgFixedPropertyListByFormCode(formName));
    	
    	model.addAttribute("fixedPropertyEnumMap",CfgFixedPropertyDefineCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getCfgFixedPropertyEnumMap(formName));
    	model.addAttribute("allFixedPropertyEnumValueInfoMap",CfgFixedPropertyDefineCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath)).getAllFixedPropertyEnumValueInfoMap(formName));
    	
    	model.addAttribute("allEnumInfoMap",CfgEnumInfoCache.getInstance().getCfgEnumInfoMap());
    	model.addAttribute("allEnumValueInfoMap",CfgEnumInfoCache.getInstance().getCfgAllEnumValueMap());

    }
    
   public void validate(Object form,ServerValidataResult svr){
	   
   }
   
   public void returnJSON(JSONObject obj,HttpServletResponse response){
	   response.setContentType("text/html;charset=utf-8");  
	   try {
			response.getWriter().write(obj==null?"{}":obj.toJSONString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
   }
   public void returnJSON(JSONArray array, HttpServletResponse response) {
	   response.setContentType("text/html;charset=utf-8");  
	   try {
			response.getWriter().write(array==null?"[]":array.toJSONString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
   public void doCustomPropertySearchCondation(SearchConditionValue searchValue,Query<?> q){
	   doCustomPropertySearchCondation(null,searchValue,q);
   }
   public void doCustomPropertySearchCondation(Long library,SearchConditionValue searchValue,Query<?> q){
	   if(library!=null && searchValue.getCustomPropertyValue()!=null){
			for(String one:searchValue.getCustomPropertyValue().keySet()){
				Object cvalue=searchValue.getCustomPropertyValue().get(one);
				if(cvalue!=null && !cvalue.toString().trim().equals("")){
					CfgCustomProperty  ccpy=CfgCustomPropertyCache.getInstance(library).getCfgCustomProperty(one);
					if(ccpy!=null){
						if("AVF".equals(ccpy.getValueType())){
							if(ccpy.isMultiple()){
								q.and(q.criteria("customPropertyMap."+one+"").hasThisOne(cvalue));
							}else{
								q.and(q.criteria("customPropertyMap."+one+"").equal(cvalue));
							}
						}else if("AVD".equals(ccpy.getValueType()) || "AVE".equals(ccpy.getValueType())){
							q.and(q.criteria("customPropertyMap."+one+"").contains(cvalue.toString()));
						}else{
							q.and(q.criteria("customPropertyMap."+one+"").equal(cvalue));
						}
					}
					
				}
			}
	};
   }
   


	protected <T>  Model list(MgGenericManager<T, Long> dbManager,String fromName,String libraryPath,HttpServletRequest request,
			final SearchConditionValue searchValue, @PathVariable String listFlag, QueryContditionSet<T> qcs) {
		Model model = new ExtendedModelMap();

		libraryAndPropertyPass(model, fromName, libraryPath);
		int currentPage = getDisplayTagCurrentPage(request, fromName + "List");
		int pageSize = PageTools.getPageSizeOfUserForm(request, fromName);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("listFlag", listFlag);
		try {
			PaginatedListHelper<T> paginaredList = dbManager.find(currentPage, pageSize, LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath), qcs);

			model.addAttribute(fromName + "List", paginaredList.getList());
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalSize", paginaredList.getFullListSize());
		} catch (Exception se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(new PaginatedListHelper<AuthAppUser>());
			model.addAttribute(fromName + "List", new ArrayList<AuthAppUser>());
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalSize", 0);
		}
        return model;
	}
	public <T> ModelAndView view(MgGenericManager<T, Long> dbManager,String DM_FORM_NAME,String FORM_NAME,String libraryPath, Long id) {
		Model model = new ExtendedModelMap();
		libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
		model.addAttribute("formEditFlag", false);
		if (id != null) {
			T bean = dbManager.get(id);
			model.addAttribute(DM_FORM_NAME, bean);
		}
		return new ModelAndView(FORM_NAME+"", model.asMap());
	}
    public <T> ModelAndView edit(MgGenericManager<T, Long> dbManager,String DM_FORM_NAME,String FORM_NAME,String libraryPath,Long id){
    	Model model = new ExtendedModelMap();
    	libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
    	model.addAttribute("formEditFlag", true);   
    		
        if (id!=null) {
            T bean= dbManager.get(id);
            model.addAttribute(DM_FORM_NAME, bean);
        }
        return new ModelAndView(FORM_NAME, model.asMap());
    }
    public <T> ModelAndView delete(MgGenericManager<T, Long> dbManager,String DM_FORM_NAME,String FORM_NAME,String libraryPath,HttpServletRequest request,String idList){
        Model model = new ExtendedModelMap(); 
        libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
        
    	Locale locale = request.getLocale();
    	if(idList!=null){
    		String[] a=idList.split("-");
    		if(a!=null){
    			for(String one:a){
    				dbManager.remove(new Long(one));
    			}
    		}
    	}
        saveMessage(request, getText(DM_FORM_NAME+".deleted", locale));
        
        String success = "redirect:/lb/{libraryPath}/"+DM_FORM_NAME+"/list/mt/php?pageGroupType=back";
        return new ModelAndView(success, model.asMap());
    }    
}
