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

package com.jobreporting.generic.configManager;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.exception.LocalizationException;
import com.jobreporting.generic.exception.PropertyManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;

public class Localization {
	
	public static LoggerManager logger = GenericUtility.getLogger(Localization.class.getName());
	
	private static String resourcesFilePath = null;
	
	private static Localization localization = null;
	private static Locale locale = null;
	private static ResourceBundle rBundle = null;
	
	private static PropertyManager propertyManager 	= PropertyManager.getPropertyManager();
	
	
	/** Object used for locking. */
	private static Object lockObject = new Object();
	
	
	private Localization() throws LocalizationException{
		
		try {
			load();
		}
		catch(Exception ex) {
			throw new PropertyManagerException(ex.getMessage());
		}
	}
	
	public static Localization createInstance(String propFilePath)  {
		
		resourcesFilePath = propFilePath + GenericConstants.LOCALIZATION_BUNDLE_FOLDER_NAME;
		
		if (localization == null) {
			synchronized (lockObject) {
				localization = new Localization();
			}
		}

		return localization;
	}
	
	public static Localization getLocalization(){
		return localization;
	}
	
	public synchronized void load() throws Exception {

		
		try{
			String configuredLocale = propertyManager.getProperty(GenericConstants.COMMON_PROPERTIES_FILE_NAME, GenericConstants.LOCALE);
			
			if (GenericUtility.safeTrim(configuredLocale).equals(GenericConstants.LOCALE_ESPANISH_SPAIN)){
				locale = new Locale("es", "ES");
			}
			else{
				locale = new Locale("en", "US");
			}
			
			logger.debug("Locale Configured : " + locale);
			
			File file = new File(resourcesFilePath);
			URL[] urls = {file.toURI().toURL()};
			ClassLoader resourcesLoader = new URLClassLoader(urls);
			
			rBundle = ResourceBundle.getBundle(GenericConstants.LOCALIZATION_BUNDLE_FILE_NAME, locale, resourcesLoader);
			
		}
		catch (MissingResourceException meEx){
			logger.error("MissingResourceException occured, it seems the bundle is not found in resources " + " : " + meEx.getMessage());
			throw new LocalizationException("MissingResourceException occured, it seems the bundle is not found in resources " + " : " + meEx.getMessage());
		}
		catch(Exception ex) {
			logger.error("Exception occured while loading Localization : "+ ex.getMessage());
			throw new LocalizationException("Exception occured while loading Localization : "+ ex.getMessage());
		}
		
	}
	
	
	public static String getLocaleText(String key){
		
		String value = "";
		
		try{
			value = rBundle.getString(key);
		}
		catch (MissingResourceException meEx){
			logger.error("MissingResourceException occured, it seems the key " +  key + " not found in resources " + " : " + meEx.getMessage());
			throw new LocalizationException("MissingResourceException occured, it seems the key " +  key + " not found in resources " + " : " + meEx.getMessage());
		}
		catch(Exception ex) {
			logger.error("Exception occured while getting the value of locale key : "+ ex.getMessage());
			throw new LocalizationException("Exception occured while getting the value of locale key : "+ ex.getMessage());
		}
		
		return value;
		
	}

	/**
	 * @return the locale
	 */
	public static Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public static void setLocale(Locale locale) {
		Localization.locale = locale;
	}
	
	

}
