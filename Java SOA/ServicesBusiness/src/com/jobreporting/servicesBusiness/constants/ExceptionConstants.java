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

import com.jobreporting.generic.common.BundleConstants;
import com.jobreporting.generic.configManager.Localization;

/**
 * @author Rishi
 *
 */
public class ExceptionConstants {
	
	/* Generic Data Access Exception details */
	public static final String CODE_DATA_ACCESS_EXCEPTION = "WS101";
	public static final String USERMESSAGE_DATA_ACCESS_EXCEPTION = Localization.getLocaleText(BundleConstants.USERMESSAGE_DATA_ACCESS_EXCEPTION);
	
	/* Generic Business Exception details */
	public static final String CODE_BUSINESS_EXCEPTION = "WS102";
	public static final String USERMESSAGE_BUSINESS_EXCEPTION = Localization.getLocaleText(BundleConstants.USERMESSAGE_BUSINESS_EXCEPTION);
	
	
	
	/* Org Name not found Exception details */
	public static final String CODE_ORGNAME_NOT_FOUND = "WS111";
	public static final String USERMESSAGE_ORGNAME_NOT_FOUND_1 = Localization.getLocaleText(BundleConstants.USERMESSAGE_ORGNAME_NOT_FOUND_1);
	public static final String ERRORMESSAGE_ORGNAME_NOT_FOUND_1 = "No record found for the provided Org name in the database.";
	public static final String USERMESSAGE_ORGNAME_NOT_FOUND_2 = Localization.getLocaleText(BundleConstants.USERMESSAGE_ORGNAME_NOT_FOUND_2);
	public static final String ERRORMESSAGE_ORGNAME_NOT_FOUND_2 = "Organization details not found for this request in the system, it is possible that it has been DELETED from the web interface.";
	
	/* Dyna Field Name not found Exception details */
	public static final String CODE_DYNAFIELD_NOT_FOUND = "WS112";
	public static final String USERMESSAGE_DYNAFIELD_NOT_FOUND = Localization.getLocaleText(BundleConstants.USERMESSAGE_DYNAFIELD_NOT_FOUND);
	public static final String ERRORMESSAGE_DYNAFIELD_NOT_FOUND = "Certain dyna field record not found in the database.";
	
	/* Worker Name not found Exception details */
	public static final String CODE_WORKERNAME_NOT_FOUND = "WS113";
	public static final String USERMESSAGE_WORKERNAME_NOT_FOUND_1 = Localization.getLocaleText(BundleConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_1);
	public static final String ERRORMESSAGE_WORKERNAME_NOT_FOUND_1 = "No record found for the provided Worker name in the database.";
	public static final String USERMESSAGE_WORKERNAME_NOT_FOUND_2 = Localization.getLocaleText(BundleConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_2);
	public static final String ERRORMESSAGE_WORKERNAME_NOT_FOUND_2 = "Worker details not found for this request in the system, it is possible that it has been DELETED from the web interface.";
	
	
	/* Mandatory Fields Validation Exception details */
	public static final String CODE_MANDATORY_FIELD_VALIDATION = "WS114";
	public static final String USERMESSAGE_MANDATORY_FIELD_VALIDATION = Localization.getLocaleText(BundleConstants.USERMESSAGE_MANDATORY_FIELD_VALIDATION);
	public static final String ERRORMESSAGE_MANDATORY_FIELD_VALIDATION = "Mandatory field validation check failed. Missing fields are : ";
	
	/* Token key Format Validation Exception details */
	public static final String CODE_TOKENKEY_FIELD_VALIDATION = "WS115";
	public static final String USERMESSAGE_TOKENKEY_FIELD_VALIDATION = Localization.getLocaleText(BundleConstants.USERMESSAGE_TOKENKEY_FIELD_VALIDATION);
	public static final String ERRORMESSAGE_TOKENKEY_FIELD_VALIDATION = "The format of token key is not correct and does not contain 3 token key seperators";
	
	/* Token key invalid Exception details */
	public static final String CODE_TOKENKEY_INVALID = "WS116";
	public static final String USERMESSAGE_TOKENKEY_INVALID = Localization.getLocaleText(BundleConstants.USERMESSAGE_TOKENKEY_INVALID);
	public static final String ERRORMESSAGE_TOKENKEY_INVALID = "Failed to find the record of token key in system.";
	
	/* Entity Single Data not found Exception details */
	public static final String CODE_ENTITY_SINGLE_DATA_NOT_FOUND = "WS117";
	public static final String USERMESSAGE_ENTITY_SINGLE_DATA_NOT_FOUND = Localization.getLocaleText(BundleConstants.USERMESSAGE_ENTITY_SINGLE_DATA_NOT_FOUND);
	public static final String ERRORMESSAGE_ENTITY_SINGLE_DATA_NOT_FOUND = "Data for the reocrd not found in the database.";
	
	/* Data Posting Exception details */
	public static final String CODE_DATA_POSTING_EXCEPTION = "WS118";
	public static final String USERMESSAGE_DATA_POSTING_EXCEPTION = Localization.getLocaleText(BundleConstants.USERMESSAGE_DATA_POSTING_EXCEPTION);
	public static final String ERRORMESSAGE_DATA_POSTING_EXCEPTION = "Error occured while posting the report image data on web server.";
	
	/* Report Publishing Exception details */
	public static final String CODE_REPORT_PUBLISH_EXCEPTION = "WS119";
	public static final String USERMESSAGE_REPORT_PUBLISH_EXCEPTION = Localization.getLocaleText(BundleConstants.USERMESSAGE_REPORT_PUBLISH_EXCEPTION);
	public static final String ERRORMESSAGE_REPORT_PUBLISH_EXCEPTION = "Error occured while report data publishing.";
	
	
	
	public static final String USERMESSAGE_LOCATION_MANDATORY_FIELD_VALIDATION = Localization.getLocaleText(BundleConstants.USERMESSAGE_LOCATION_MANDATORY_FIELD_VALIDATION);

}

