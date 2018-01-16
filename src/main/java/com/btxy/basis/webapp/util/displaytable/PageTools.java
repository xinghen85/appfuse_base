package com.btxy.basis.webapp.util.displaytable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class PageTools {

	public static int getPageSizeOfUserForm(HttpServletRequest request,String formName) {
		Cookie[] cookies = request.getCookies();
		int size=25;
        for(Cookie c :cookies ){
            if(c.getName()!=null && c.getName().equals(formName+"-pageSize")){
            	size= Integer.parseInt(c.getValue());
            }
        }
		return size;
	}

}