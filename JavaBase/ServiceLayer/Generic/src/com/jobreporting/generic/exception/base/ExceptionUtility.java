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

package com.jobreporting.generic.exception.base;

import com.jobreporting.generic.exception.ApplicationException;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.exception.LocalizationException;
import com.jobreporting.generic.exception.PropertyManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.exception.ActionException;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.DataPostingException;
import com.jobreporting.servicesBusiness.exception.DynaFieldNotFoundException;
import com.jobreporting.servicesBusiness.exception.EntityDataNotFoundException;
import com.jobreporting.servicesBusiness.exception.InvalidTokenKeyException;
import com.jobreporting.servicesBusiness.exception.OrgNotFoundException;
import com.jobreporting.servicesBusiness.exception.ReportPublishingException;
import com.jobreporting.servicesBusiness.exception.WorkerNotFoundException;
import com.jobreporting.servicesController.exception.BadRequestException;
import com.jobreporting.servicesController.exception.InitException;
import com.jobreporting.servicesController.exception.ServicesException;
import com.jobreporting.servicesController.exception.StreamConversionException;

/**
 * @author Rishi
 *
 */
public class ExceptionUtility {
	
	public static LoggerManager logger = GenericUtility.getLogger(ExceptionUtility.class.getName());
	
	
	
	public static ApplicationException createExceptionDetail(String errorCode, String userMessage, String errorMessage, EExceptionTypes exceptionType){
		
		ApplicationException exception = null;
		
		ExceptionDetail exDetail = new ExceptionDetail();
		exDetail.setCode(errorCode);
		exDetail.setUserMessage(userMessage);
		exDetail.setErrorMessage(errorMessage);
		
		switch(exceptionType){
		
			case ACTION_EXCEPTION:
				exception = new ActionException(exDetail);
				break;
				
			case APPLICATION_EXCEPTION:
				exception = new ApplicationException(exDetail);	
				break;
				
			case BAD_REQUEST_EXCEPTION:
				exception = new BadRequestException(exDetail);
				break;
				
			case BUSINESS_EXCEPTION:
				exception = new BusinessException(exDetail);
				break;
				
			case BUSINESS_VALIDATION_EXCEPTION:
				exception = new BusinessValidationException(exDetail);
				break;
				
			case DATABASE_CONNECTION_MANAGER_EXCEPTION:
				exception = new DatabaseConnectionManagerException(exDetail);
				break;
				
			case DATA_ACCESS_EXCEPTION:
				exception = new DataAccessException(exDetail);
				break;

			case INIT_EXCEPTION:
				exception = new InitException(exDetail);
				break;
				
			case PROPERTY_MANAGER_EXCEPTION:
				exception = new PropertyManagerException(exDetail);
				break;
				
			case LOCALIZATION_EXCEPTION:
				exception = new LocalizationException(exDetail);
				break;
				
			case SERVICES_EXCEPTION:
				exception = new ServicesException(exDetail);
				break;
				
			case ORG_NOT_FOUND_EXCEPTION:
				exception = new OrgNotFoundException(exDetail);
				break;
				
			case DYNAFIELDNOT_FOUND_EXCEPTION:
				exception = new DynaFieldNotFoundException(exDetail);
				break;
				
			case WORKER_NOT_FOUND_EXCEPTION:
				exception = new WorkerNotFoundException(exDetail);
				break;
				
			case STREAM_CONVERSION_EXCEPTION:
				exception = new StreamConversionException(exDetail);
				break;
				
			case TOKENKEY_INVALID_EXCEPTION:
				exception = new InvalidTokenKeyException(exDetail);
				break;
				
			case ENTITY_DATA_NOT_FOUND_EXCEPTION:
				exception = new EntityDataNotFoundException(exDetail);
				break;
				
			case REPORT_PUBLISH_EXCEPTION:
				exception = new ReportPublishingException(exDetail);
				break;
				
			case DATA_POSTING_EXCEPTION:
				exception = new DataPostingException(exDetail);
				break;
				
			default:
				logger.error("Invalid exception type");
				break;
		
		}
		
		return exception;
	}
	
	public static ApplicationException createExceptionDetail(ExceptionDetail exDetail, EExceptionTypes exceptionType){
		
		return createExceptionDetail(exDetail.getCode(), exDetail.getUserMessage(), exDetail.getErrorMessage(), exceptionType);

	}

}
