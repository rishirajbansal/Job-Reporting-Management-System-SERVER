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

package com.jobreporting.servicesBusiness.validations;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public abstract class AbstractBusinessValidator implements IBusinessValidator {
	
	public static LoggerManager logger = GenericUtility.getLogger(AbstractBusinessValidator.class.getName());
	
	public abstract boolean validate(WSBaseRequest request, ERequestType requestType) throws BusinessValidationException;
	
	
	public void validateTokenKey(String tokenKey){
		
		if (GenericUtility.safeTrim(tokenKey).equals(GenericConstants.EMPTY_STRING)){
			logger.debug("Token Key is empty.");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_MANDATORY_FIELD_VALIDATION, ExceptionConstants.USERMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.TOKENKEY_FIELD, ExceptionConstants.ERRORMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.TOKENKEY_FIELD, EExceptionTypes.BUSINESS_VALIDATION_EXCEPTION);
		}
	}
	
	public void validateTokenFormat(String tokenKey){
		
		if (!GenericUtility.safeTrim(tokenKey).equals(GenericConstants.EMPTY_STRING)){
			String[] splitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
			
			if (splitted.length != 4){
				logger.debug("Token Key format is invalid.");
				throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_TOKENKEY_FIELD_VALIDATION, ExceptionConstants.USERMESSAGE_TOKENKEY_FIELD_VALIDATION, ExceptionConstants.ERRORMESSAGE_TOKENKEY_FIELD_VALIDATION, EExceptionTypes.BUSINESS_VALIDATION_EXCEPTION);
			}
			
		}
	}

}
