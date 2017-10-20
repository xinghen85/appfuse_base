package com.btxy.basis.webapp.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.btxy.basis.Constants;
import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.cache.ehcache.ObjectUpdateMsgCache;
import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgParameter;

public class StartupListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
        log.debug("Initializing context...");

        ServletContext context = event.getServletContext();

        // Orion starts Servlets before Listeners, so check if the config
        // object already exists
        Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(Constants.CONFIG);
        if (config == null) {
            config = new HashMap<String, Object>();
        }

        ApplicationContext ctx =WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	    	Datastore ds=(Datastore) ctx.getBean("datastore");
	    	LibraryInfoCache.startUpInit(ds);
	    	Cache.startupInit(ctx);
        PasswordEncoder passwordEncoder = null;
        SpringContext.setApplicationContext(ctx);
        
    		CfgParameter cfgParameter=ConfigureContext.getDbCfgParemeters("login_show_code");
    		if(cfgParameter!=null){
    			if(cfgParameter.getValue().equals("y")){
                config.put("showCode", Boolean.TRUE);
    			}
    		}
        try {
            ProviderManager provider = (ProviderManager) ctx.getBean("org.springframework.security.authentication.ProviderManager#0");
            for (Object o : provider.getProviders()) {
                AuthenticationProvider p = (AuthenticationProvider) o;
                if (p instanceof RememberMeAuthenticationProvider) {
                    config.put("rememberMeEnabled", Boolean.TRUE);
                } else if (ctx.getBean("passwordEncoder") != null) {
                    passwordEncoder = (PasswordEncoder) ctx.getBean("passwordEncoder");
                }
            }
        } catch (NoSuchBeanDefinitionException n) {
            log.debug("authenticationManager bean not found, assuming test and ignoring..."); 
            // ignore, should only happen when testing
        }

        context.setAttribute(Constants.CONFIG, config);

        // output the retrieved values for the Init and Context Parameters
        if (log.isDebugEnabled()) {
            log.debug("Remember Me Enabled? " + config.get("rememberMeEnabled"));
            if (passwordEncoder != null) {
                log.debug("Password Encoder: " + passwordEncoder.getClass().getSimpleName());
            }
            log.debug("Populating drop-downs...");
        }
        
    }

    public static void setupContext(ServletContext context) {
    	
    }
    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    		if(SpringContext.getApplicationContext()!=null) {
        		ObjectUpdateMsgCache.getInstance().shuttdown();
    		}
    }
}
