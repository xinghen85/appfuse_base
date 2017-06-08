package com.btxy.basis.webapp.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.btxy.basis.Constants;
import com.btxy.basis.cache.model.AuthPrivilegeView;
import com.btxy.basis.common.CurrentUserUtil;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.model.CurrentUserPrivilegeMap;
import com.btxy.basis.webapp.filter.model.ParameterRequestWrapper;

/**
 * Filter to wrap request with a request including user preferred locale.
 */
public class LocaleFilter extends OncePerRequestFilter {

    /**
     * This method looks for a "locale" request parameter. If it finds one, it sets it as the preferred locale
     * and also configures it to work with JSTL.
     * 
     * @param request the current request
     * @param response the current response
     * @param chain the chain
     * @throws IOException when something goes wrong
     * @throws ServletException when a communication failure happens
     */
    @SuppressWarnings("unchecked")
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain)
            throws IOException, ServletException {

        String locale = request.getParameter("locale");
        Locale preferredLocale = null;

        if (locale != null) {
            int indexOfUnderscore = locale.indexOf('_');
            if (indexOfUnderscore != -1) {
                String language = locale.substring(0, indexOfUnderscore);
                String country = locale.substring(indexOfUnderscore + 1);
                preferredLocale = new Locale(language, country);
            } else {
                preferredLocale = new Locale(locale);
            }
        }

        HttpSession session = request.getSession(false);

        if (session != null) {
            if (preferredLocale == null) {
                preferredLocale = (Locale) session.getAttribute(Constants.PREFERRED_LOCALE_KEY);
            } else {
                session.setAttribute(Constants.PREFERRED_LOCALE_KEY, preferredLocale);
                Config.set(session, Config.FMT_LOCALE, preferredLocale);
            }

            if (preferredLocale != null && !(request instanceof LocaleRequestWrapper)) {
                request = new LocaleRequestWrapper(request, preferredLocale);
                LocaleContextHolder.setLocale(preferredLocale);
            }
            
            
        }
        
        //ParameterRequestWrapper prw=doPageGroupParameters(request, response);
        // chain.doFilter(prw==null?request:prw, response);
        chain.doFilter(request, response);
        //session = request.getSession(true);
        
        // Reset thread-bound LocaleContext.
        LocaleContextHolder.setLocaleContext(null);
    }
    


	private ParameterRequestWrapper  doPageGroupParameters(HttpServletRequest request, HttpServletResponse response){
		
		//处理历史参数
        try{
        	if(request.getServletPath().startsWith("/images") ||
					request.getServletPath().startsWith("/styles") ||
					request.getServletPath().startsWith("/scripts") ||
					request.getServletPath().startsWith("/frame") ||
					request.getServletPath().startsWith("/plugin")){
				return null;
			}
        	
        	HttpSession session = request.getSession(false);
        	if(session != null){
        		
    			
    			String url = request.getRequestURI();
    			    			
    			if(url!=null){
    				System.out.println("==================ListPageGroupfilter start=========================");
    				System.out.println("url:" + url);
    				String qyeryStr = request.getQueryString();
    				System.out.println("paras:" + qyeryStr);
    				
    				String level=request.getParameter("URLLevel");
    				if(level==null){
    					level="";
    				}
    				String pname = "Filter_Page_Group_[" + url + "]["+level+"]";
    				
    				
    				String type = request.getParameter("pageGroupType");
    				if ("back".equals(type)) {
    					System.out.println("	返回历史列表");
    					
    				
    					String oldQryStr = (String) session.getAttribute(pname);
    					
    					System.out.println("	历史列表URL参数:"+oldQryStr);
    					
    					HttpServletRequest request1 = (HttpServletRequest) request;
    					HashMap m = new HashMap(request1.getParameterMap());
    					if((oldQryStr!=null && !oldQryStr.equals(""))){
    						String[] arrays = oldQryStr.split("&");
    						for (String str : arrays) {
    							if (str != null) {
    								String[] ay1 = str.split("=");
    								if (ay1 != null && ay1.length >= 2) {
    									if(!m.containsKey(ay1[0])){
    										m.put(ay1[0], ay1[1]);
    									}
    									
    								} else if (ay1 != null && ay1.length == 1) {
    									m.put(ay1[0], null);
    								}
    								
    							}
    						}				
    					}
    				
    					m.remove("pageGroupType");
    					
    					System.out.println("	更新后参数:"+m);
    					ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(
    							request, m);
    					
    					//session.removeAttribute(pname);
    					
    					//chain.doFilter(wrapRequest, response);
    					return wrapRequest;
    				} else {
    					System.out.println("	保存参数列表至session");
    					session.setAttribute(pname, qyeryStr);
    					//chain.doFilter(request, response);
    				}    
    				System.out.println("==================ListPageGroupfilter end=========================");
    			}else{
    				//chain.doFilter(request, response);
    			}
        	}else{
        		//chain.doFilter(request, response);
        	}
			
        }catch(Exception e){
        	e.printStackTrace();
        	//chain.doFilter(request, response);
        }
        return null;
	}
}
