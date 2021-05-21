package com.surittec.spring.boot.crud.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static boolean isEmailValid(String email) {
	    if (email != null && email.length() > 0) {
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(email);
	        if (matcher.matches()) return true;
	    }
	    return false;
	}
	
	public static boolean isNameValid(String name) {
		if(name != null && name.length() > 0) {
			String expression = "^[A-ZÁ-Úa-zá-ú0-9 ]*$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(name);
	        if (matcher.matches()) return true;
		}
		return false;
	}
}
