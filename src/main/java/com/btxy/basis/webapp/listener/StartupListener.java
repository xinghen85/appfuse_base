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
//import com.letv.flow.manage.service.IpSync;
//import com.letv.flow.manage.service.tongji.TongJiPrivince;
//import com.letv.flow.manage.webapp.controller.gby.LogPlayController;

/**
 * <p>StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 * <p/>
 * <p>Keep in mind that this listener is executed outside of OpenSessionInViewFilter,
 * so if you're using Hibernate you'll have to explicitly initialize all loaded data at the
 * GenericDao or service level to avoid LazyInitializationException. Hibernate.initialize() works
 * well for doing this.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
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

        ApplicationContext ctx =
                WebApplicationContextUtils.getRequiredWebApplicationContext(context);
       // LogPlayController logc = ctx.getBean(LogPlayController.class);
//lyz		IpSync.base=context.getRealPath("/");
//		TongJiPrivince.init(IpSync.base+"/");
		//logc.jobOfLogInitAtTomcatStart();
//        for(String name:ctx.getBeanDefinitionNames()){
//        	//System.out.print(name+":");
//        	//Object obj=ctx.getBean(name);
//        	//System.out.println(obj);
//        }
    	Datastore ds=(Datastore) ctx.getBean("datastore");
    	LibraryInfoCache.startUpInit(ds);
    	Cache.startupInit(ctx);
        PasswordEncoder passwordEncoder = null;
        
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
        
//        timer = new Timer(true);
//        
//        timer.schedule(new UpdateLazyOpeScheduleBean(event.getServletContext()),0,20*1000);//调用exportHistoryBean，0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
//        timer.schedule(new TaskScheduleBean(event.getServletContext()),0,10*1000);//调用exportHistoryBean，0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。

    }
   // private Timer timer = null;

    public static void setupContext(ServletContext context) {
    	
    }
//    private static void doReindexing(GenericManager manager) {
//        manager.reindexAll(false);
//    }

    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    	//timer.cancel();
    	ObjectUpdateMsgCache.getInstance().shuttdown();
    }
}
