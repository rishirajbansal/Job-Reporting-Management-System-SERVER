/*
 * Licensed To: ThoughtExecution & 9sistemes
 * Authored By: Rishi Raj Bansal
 * Developed in: Sep-Oct 2016
 *
 * ===========================================================================
 * This is FULLY owned and COPYRIGHTED by ThoughtExecution
 * This code may NOT be RESOLD or REDISTRIBUTED under any circumstances, and is only to be used with this application
 * Using the code from this application in another application is strictly PROHIBITED and not PERMISSIBLE
 * ===========================================================================
 */

package com.jobreporting.generic.utilities;

import com.jobreporting.generic.loggerManager.LoggerManager;

/**
 * @author Rishi
 *
 */
public class GenericUtility {
	
	public static LoggerManager getLogger(String className) {
		return new LoggerManager(className);
	}
	
	/**
	 * safeTrim takes a String and trims the leading and trailing spaces and returs a it
	 * this method will return an empty string if the String passed is is ==null or is the string "null"
	 *
	 * @param s Sting string to trim leading and trailing spaces
	 * @return String
	 */
	public static String safeTrim(String s) {
		if ((s == null) || s.equals("null")) {
			return "";
		}
		else {
			return s.trim();
		}
	}

}
