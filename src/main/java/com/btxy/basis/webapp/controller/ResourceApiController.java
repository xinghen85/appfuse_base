package com.btxy.basis.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.cache.ehcache.ObjectCache;
import com.btxy.basis.cache.ehcache.ObjectUpdateMsgCache;



@Controller
public class ResourceApiController  {
	
	
	private static final Log log = LogFactory.getLog(ResourceApiController.class);

	@RequestMapping(value = "/api/sendMsg/php*",method = RequestMethod.GET)
    public ModelAndView sendMsg(@PathVariable String libraryPath,@PathVariable Long dataModelId)throws Exception {
    	Model model = new ExtendedModelMap();
        return new ModelAndView("test/sendMsg", model.asMap());
    }
	@RequestMapping(value = "/api/showMsg/php*",method = RequestMethod.GET)
    public ModelAndView showMsg(@PathVariable String libraryPath,@PathVariable Long dataModelId)throws Exception {
    	Model model = new ExtendedModelMap();
        return new ModelAndView("test/showMsg", model.asMap());
    }
	
	@RequestMapping(value = "/lb/default/ehcache/detail*")
	public void getEhcacheInfo(
			@RequestParam(value = "mac", required = false) String mac,
			
			
			HttpServletRequest request,HttpServletResponse response) {
		
			
			response.setContentType("text/html;charset=utf-8");   
			JSONObject jo=new JSONObject();
			
			if("objectUpdateMsgCache".equals(request.getParameter("cache"))){
				ObjectUpdateMsgCache cache=ObjectUpdateMsgCache.getInstance();
				jo.put("diskStoreSize", cache.getCache().getDiskStoreSize());
				jo.put("mMemoryStoreSize", cache.getCache().getMemoryStoreSize());
				jo.put("objectCount", cache.getCache().getStatistics().getObjectCount());
				jo.put("status", cache.getCache().getStatus());
				
				try {
					JSONObject jo1=new JSONObject();
					if(cache.getCache().getKeys()!=null){
						for(Object one:cache.getCache().getKeys()){
							jo1.put(one.toString(), cache.get(one.toString()));
						}
					}
					jo.put("details", jo1);
					
					response.getWriter().write(jo.toJSONString());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}else if("objectCache".equals(request.getParameter("cache"))){
				ObjectCache cache=ObjectCache.getInstance();
				jo.put("diskStoreSize", cache.getCache().getDiskStoreSize());
				jo.put("mMemoryStoreSize", cache.getCache().getMemoryStoreSize());
				jo.put("objectCount", cache.getCache().getStatistics().getObjectCount());
				jo.put("status", cache.getCache().getStatus());
				
				try {
					JSONObject jo1=new JSONObject();
					if(cache.getCache().getKeys()!=null){
						for(Object one:cache.getCache().getKeys()){
							jo1.put(one.toString(), cache.get(one.toString()));
						}
					}
					jo.put("details", jo1);
					
					response.getWriter().write(jo.toJSONString());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
			
			
			
			

	}
	
	
	@RequestMapping(value = "/api/stb/cibn/qryflag/**")
	public void getdetailforcibn(
			@RequestParam(value = "mac", required = false) String mac,
			
			
			HttpServletRequest request,HttpServletResponse response) {
		

		response.setContentType("text/html;charset=utf-8");   

		log.info("getdetail");
		try {
			
			
			
			
			response.getWriter().write(request.getRequestURL().toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
	
	
	

	
}
