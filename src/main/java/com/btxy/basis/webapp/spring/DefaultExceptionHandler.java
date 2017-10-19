package com.btxy.basis.webapp.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.btxy.basis.webapp.controller.BaseFormController.Rtn;

public class DefaultExceptionHandler implements HandlerExceptionResolver {
	private static Logger log = Logger.getLogger(DefaultExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
        
        if(request.getRequestURI().contains("stateMachineSubmit")) {
        		returnJson(response, ex,"数据更新失败，请稍后重试!");
        }else if(request.getRequestURI().contains("/json/")){
    	 		returnJson(response, ex,null);
        }else if(ex instanceof org.springframework.dao.DataAccessException) {  
            return new ModelAndView("dataAccessFailure", model); 
        } else {  
        	
        	 	returnJson(response, ex,null);
        }  
       
		return mv;
	}

	private void returnJson(HttpServletResponse response, Exception ex,String msg) {
		/*	使用response返回	*/
		response.setStatus(HttpStatus.OK.value()); //设置状态码
		response.setContentType(MediaType.APPLICATION_JSON.toString()); //设置ContentType
		response.setCharacterEncoding("UTF-8"); //避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		if(msg==null) {
			msg=ex.getMessage();
		}
		if(ex instanceof org.springframework.dao.DataAccessException) {
		    log.info(ex);
		    msg="数据库访问出错";
		}if(ex instanceof NullPointerException) {
		    log.info(ex);
		    msg="Null";
		}else {
				ex.printStackTrace();
		}
		Rtn ResponseBean=new Rtn(-1,msg);
		try {
		    response.getWriter().write(JSON.toJSONString(ResponseBean));
		} catch (IOException e) {
		   log.error("与客户端通讯异常:"+ e.getMessage(), e);
		}
	}
}