package com.fss.m24;
/********************************************************************
 * Project			:	MPAYSIMULATOR 
 * File				:	ResourceBundleUtil.java	 
 * Author			:   Karthiksankaran
 * Creation Date	:	Jun 1, 2010 5:25:10 PM  
 * Version			:	  
 * Purpose			:	 
 **********************************************************************/

import java.util.ResourceBundle;


public class ResourceBundleUtil {
	
	public static String  getValue(String key) {

		ResourceBundle rb = ResourceBundle.getBundle("ussdnuup");
        String value=rb.getString(key);
		return value;

	}
	
}
