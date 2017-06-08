package com.btxy.basis.webapp.filter.model;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper{

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	private Map parameters;   
	public ParameterRequestWrapper(HttpServletRequest request,Map newParams) {   
	  super(request);   
	  this.parameters=newParams;   
	}   
	/*public ParameterRequestWrapper(HttpServletRequest request,String paraURL) {   
	  super(request);   
	  String[] arrays=paraURL.split("&");
	  for(String str:arrays){
		  if(str!=null){
			  String[] ay1=str.split("=");
			  if(ay1!=null && ay1.length>=2){
				  this.params.put(ay1[0], ay1[1]);	
			  }else if(ay1!=null && ay1.length==1){
				  this.params.put(ay1[0], null);	
			  }
			  		  
		  }

	  }
	  //this.params=newParams;   
	}   */
	
	public Map getParameterMap() {   
	  return parameters;   
	}   
	public Enumeration getParameterNames() {   
	  Vector l=new Vector(parameters.keySet());   
	  return l.elements();   
	}   
	public String[] getParameterValues(String name) {   
	  Object v = parameters.get(name);   
	  if(v==null){   
	    return null;   
	  }else if(v instanceof String[]){   
	    return (String[]) v;   
	  }else if(v instanceof String){   
	    return new String[]{(String) v};   
	  }else{   
	    return new String[]{v.toString()};   
	  }   
	}   
	public String getParameter(String name) {   
	  Object v = parameters.get(name);   
	  if(v==null){   
	    return null;   
	  }else if(v instanceof String[]){             
	    String []strArr=(String[]) v;   
	    if(strArr.length>0){   
	      return strArr[0];   
	    }else{   
	      return null;   
	    }   
	  }else if(v instanceof String){   
	    return (String) v;   
	  }else{   
	    return v.toString();   
	  }   
	}      


}
