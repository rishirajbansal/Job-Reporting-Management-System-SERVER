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

package com.jobreporting.generic.common;

/**
 * @author Rishi
 *
 */
public class GenericConstants {
	
	public static final String EMPTY_STRING										= "";
	public static final String STRING_TRUE										= "true";
	public static final String STRING_FALSE										= "false";
	
	public static final String PROPERTIES_FILE_PATH								= "PropertiesFilePath";
	
	public static final String FILE_SEPARATOR 									= System.getProperty("file.separator");
	public static final String PIPE_SEPARATOR 									= "|";
	
	public static final String COMMON_PROPERTIES_FILE_NAME 						= "common.properties";
	public static final String DATABASE_PROPERTIES_FILE_NAME 					= "database.properties";
	public static final String LOCALIZATION_BUNDLE_FOLDER_NAME 					= "bundle";
	public static final String LOCALIZATION_BUNDLE_FILE_NAME 					= "strings";

	
	/*
	 * Database configuration constants
	 */
	public static final String DATABASE_DRIVER									= "driver";
	public static final String DATABASE_URL										= "url";
	public static final String DATABASE_USERNAME								= "username";
	public static final String DATABASE_PASSWORD								= "password";
	public static final String DATABASE_AUTO_COMMIT								= "autoCommit";
	public static final String DATABASE_ACTIVE_MAX_POOL_SIZE					= "activeMaxPoolSize";
	
	/*
	 * Common Properties constants
	 */
	public static final String WEB_SERVER_HOST									= "web_server_host";
	public static final String WEB_SERVER_IMAGE_UPLOADER_PATH					= "web_server_image_uploader_path";
	public static final String WEB_SERVER_REPORT_PUBLISHER_PATH					= "web_server_report_publisher_path";
	public static final String LOCALE											= "locale";
	
	/*
	 * Locale
	 */
	public static final String LOCALE_ENGLISH_US								= "en";
	public static final String LOCALE_ESPANISH_SPAIN							= "es";

	


}
