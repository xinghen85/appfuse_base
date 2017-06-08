package com.btxy.basis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	public static boolean checkEmail(String email){
        
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


	public static boolean checkPhone(String phone){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
