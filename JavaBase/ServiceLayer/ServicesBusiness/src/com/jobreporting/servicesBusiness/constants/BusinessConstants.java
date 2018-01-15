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

package com.jobreporting.servicesBusiness.constants;

public class BusinessConstants {
	
	public static final String RESPONSE_SUCCESS											= "true";
	public static final String RESPONSE_FALSE											= "false";
	
	
	public static final String DYNAFIELD_IDS_SEPERATOR									= "|";
	public static final String FIELDID_VALUE_SEPERATOR									= ":";
	public static final String FIELDID_VALUE_DATASET_SEPERATOR							= "|";
	public static final String LIST_VALUES_SEPERATOR									= ",";
	public static final String MULTIPLE_VALUES_SEPERATOR								= "|";
	public static final String DYNAFIELDS_LIST_VALUES_LOCALE_SEPERATOR					= "<|>";
	
	public static final String UPDATE_HIS_SEPERATOR										= ":";
	public static final String COORDNIATES_SEPERATOR									= "|";
	
	public static final String TOKENKEY_VALUES_SEPERATOR								= "||";
	public static final String TOKENKEY_VALUES_PREFIX									= "T";
	
	public static final String REG_EX_PIPE_SEPERATOR									= "\\|";
	public static final String REG_EX_DYNAFIELD_IDS_SEPERATOR							= "\\|";
	public static final String REG_EX_FIELDID_VALUE_DATASET_SEPERATOR					= "\\|";
	public static final String REG_EX_FIELDID_VALUE_SEPERATOR							= "\\:";
	public static final String REG_EX_TOKENKEY_VALUES_SEPERATOR							= "[|][|]";
	public static final String REG_EX_MULTIPLE_VALUES_SEPERATOR							= "\\|";
	public static final String REG_EX_DYNAFIELDS_LIST_VALUES_LOCALE_SEPERATOR			= "\\<\\|>";
	
	
	/* Dyna Field constants */
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_WORKER_NAME					= "wrk_name";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_PRODUCT_NAME					= "prd_name";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_CUSTOMER_NAME				= "cstmr_name";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_TASK_NAME					= "tsk_name";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_ASSIGNED_WORKER				= "tsk_worker";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_REPORT_PHOTO					= "rpt_photo";
	public static final String DYNAFIELDS_FIELDID_HTMLNAME_REPORT_SIGN					= "rpt_signpad";
	
	public static final String ENTITY_TABLE_WORKER										= "org_wrks";
	public static final String ENTITY_TABLE_CUSTOMER									= "org_cstmrs";
	
	public static final String ENTITY_TABLE_WORKER_ID									= "idorg_wrks";
	public static final String ENTITY_TABLE_CUSTOMER_ID									= "idorg_cstmrs";

	
	public static final String DYNAFIELDS_TABLE_WORKER									= "wrks_fields";
	public static final String DYNAFIELDS_TABLE_CUSTOMER								= "cstmrs_fields";
	public static final String DYNAFIELDS_TABLE_REPORT									= "rpts_fields";
	
	public static final String DYNAFIELDS_TABLE_ID_PRODUCT								= "idprdts_fields";
	public static final String DYNAFIELDS_TABLE_ID_TASK									= "idtsks_fields";
	public static final String DYNAFIELDS_TABLE_ID_WORKER								= "idwrks_fields";
	public static final String DYNAFIELDS_TABLE_ID_CUSTOMER								= "idcstmrs_fields";
	public static final String DYNAFIELDS_TABLE_ID_REPORT								= "idrpts_fields";
	
	public static final String DYNAFIELDS_PRODUCT_CBID_PREFIX							= "p_";
	public static final String DYNAFIELDS_TASK_CBID_PREFIX								= "t_";
	public static final String DYNAFIELDS_WORKER_CBID_PREFIX							= "w_";
	public static final String DYNAFIELDS_CUSTOMER_CBID_PREFIX							= "c_";
	public static final String DYNAFIELDS_REPORTING_CBID_PREFIX							= "r_";
	
	public static final String DYNA_CONTROL_VALUE_PLACEHOLDER                           = "-Placeholder";
	
	
	public static final String DYNAFIELDS_LIST_FIELD_EMPTY								= "none";
	
	public static final String TOKENKEY_FIELD											= "Token Key";
	
	/* One Time Auth Service constants */
	public static final String ONETIMEAUTH_FIELD_ORGNAME								= "Org Name";
	public static final String ONETIMEAUTH_FIELD_WORKERNAME								= "Worker Name";
	
	/* Report Service constants */
	public static final String REPORTSUB_FIELD_REPORTDATA								= "Report Data";
	public static final int REPORT_DATA_IMAGE_TYPE_PHOTO		                        = 1;
    public static final int REPORT_DATA_IMAGE_TYPE_SIGN			                    	= 2;
    public static final String REPORT_DATA_IMAGE_TYPE_PHOTO_PREFIX		                = "P_";
    public static final String REPORT_DATA_IMAGE_TYPE_SIGN_PREFIX			            = "S_";
    public static final String REPORT_DATA_IMAGE_URI_NAME                    			= "image";
    public static final String SIGN_FILE_NAME                                           = "sign.jpg";
    public static final String REPORT_NAMING_PREFIX                                     = "JobReport-R";
	
	
	/* Date Format constants */
	public static final String DATEFORMAT_UPDATE_HIS									= "MMM dd, yyyy hh:mm aaa";
	
	/* Location Constants */
    public static final String LOCATION_GPS_NOT_ENABLED                                 = "0";
    public static final String LOCATION_GPS_ENABLED                                     = "1";
    public static final String LOCATION_GEOCODE_TIMEDOUT                                = "2";
    public static final String LOCATION_NO_LOCATION_FOUND                               = "3";
    public static final String LOCATION_MSG_NO_LOCATION_FOUND                           = "No location details received from mobile";
    public static final String LOCATION_MSG_GPS_NOT_ENABLED                             = "GPS not enabled, no location detection from mobile";
    public static final String LOCATION_MSG_GPS_ENABLED                             	= "GPS enabled, no location details received from mobile";
    public static final String LOCATION_MSG_GEOCODE_TIMEDOUT                            = "GPS enabled, identify location timed out in mobile";

    
	
}	
