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
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSOneTimeAuthRequest;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class OneTimeAuthValidator extends AbstractBusinessValidator {

	@Override
	public boolean validate(WSBaseRequest request, ERequestType requestType) throws BusinessValidationException {
		
		boolean isValid = true;
		
		WSOneTimeAuthRequest wsOneTimeAuthRequest = (WSOneTimeAuthRequest)request;
		
		switch(requestType){
		
			case ONETIMEAUTH: 
				isValid = validateOneTimeAuth(wsOneTimeAuthRequest);
				break;
		
			default: 
				throw new BusinessException("Unsupported request type.");
		
		}
		
		return isValid;
	}
	
	public boolean validateOneTimeAuth(WSOneTimeAuthRequest wsOneTimeAuthRequest){
		
		boolean isValid = true;
		
		if (GenericUtility.safeTrim(wsOneTimeAuthRequest.getOrgName()).equals(GenericConstants.EMPTY_STRING)){
			logger.debug("Org Name is empty.");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_MANDATORY_FIELD_VALIDATION, ExceptionConstants.USERMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.ONETIMEAUTH_FIELD_ORGNAME, ExceptionConstants.ERRORMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.ONETIMEAUTH_FIELD_ORGNAME, EExceptionTypes.BUSINESS_VALIDATION_EXCEPTION);
		}
		
		if (GenericUtility.safeTrim(wsOneTimeAuthRequest.getWorkerName()).equals(GenericConstants.EMPTY_STRING)){
			logger.debug("Worker Name is empty.");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_MANDATORY_FIELD_VALIDATION, ExceptionConstants.USERMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.ONETIMEAUTH_FIELD_WORKERNAME, ExceptionConstants.ERRORMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.ONETIMEAUTH_FIELD_WORKERNAME, EExceptionTypes.BUSINESS_VALIDATION_EXCEPTION);
		}
		
		return isValid;
		
	}

}
