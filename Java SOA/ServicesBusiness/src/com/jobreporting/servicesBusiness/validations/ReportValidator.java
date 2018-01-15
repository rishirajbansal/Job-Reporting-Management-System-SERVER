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
import com.jobreporting.requests.WSReportsRequest;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;

public class ReportValidator extends AbstractBusinessValidator {

	@Override
	public boolean validate(WSBaseRequest request, ERequestType requestType) throws BusinessValidationException {

		boolean isValid = true;
		
		WSReportsRequest wsReportsRequest = (WSReportsRequest)request;
		
		switch(requestType){
		
			case REPORT_SUB_REQUEST: 
				isValid = validateReportSubRequest(wsReportsRequest);
				break;
		
			default: 
				throw new BusinessException("Unsupported request type.");
		
		}
		
		return isValid;
		
	}
	
	public boolean validateReportSubRequest(WSReportsRequest wsReportsRequest){
		
		boolean isValid = true;
		
		/* Mandatory validation check - token key */
		validateTokenKey(wsReportsRequest.getTokenKey());
		
		/* Format validation check - token key */
		validateTokenFormat(wsReportsRequest.getTokenKey());
		
		if (GenericUtility.safeTrim(wsReportsRequest.getReportData()).equals(GenericConstants.EMPTY_STRING)){
			logger.debug("Report Data found empty.");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_MANDATORY_FIELD_VALIDATION, ExceptionConstants.USERMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.REPORTSUB_FIELD_REPORTDATA, ExceptionConstants.ERRORMESSAGE_MANDATORY_FIELD_VALIDATION + BusinessConstants.REPORTSUB_FIELD_REPORTDATA, EExceptionTypes.BUSINESS_VALIDATION_EXCEPTION);
		}
		
		return isValid;
		
	}

}
