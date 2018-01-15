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

package com.jobreporting.servicesController.services.resourceHandlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import com.jobreporting.generic.exception.ApplicationException;
import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.servicesBusiness.business.base.ActionDirectory;
import com.jobreporting.servicesBusiness.business.base.ActionFactory;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.business.base.IBusinessAction;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesController.constants.ExceptionConstants;
import com.jobreporting.servicesController.exception.BadRequestException;
import com.jobreporting.servicesController.exception.StreamConversionException;
import com.jobreporting.servicesController.utilities.ServiceUtility;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
@Path("/oneTimeAuth")
public class OneTimeAuthService {
	
	public static LoggerManager logger = GenericUtility.getLogger(OneTimeAuthService.class.getName());
	
	
	@POST
	@Consumes({ MediaType.WILDCARD })
	@Produces( {MediaType.APPLICATION_OCTET_STREAM})
	public StreamingOutput oneTimeAuth(byte[] requestBStream){
		
		StreamingOutput responseStream = null;
		
		WSBaseResponse response = null;
		
		try{
			if (null != requestBStream){
				
				Object obj = ServiceUtility.deSerializeServiceRequest(requestBStream);
				
				if (obj != null && obj instanceof WSBaseRequest){
					WSBaseRequest request = (WSBaseRequest)obj;
					IBusinessAction action = ActionFactory.getActionInstance(ActionDirectory.ACTION_ONETIMEAUTH);
					response = action.execute(request, ERequestType.ONETIMEAUTH);
					
					if (null != response) {
						response.setStatus(ExceptionConstants.SERVICE_STATUS_SUCCESS);
					}
					else{
						throw new ApplicationException("Response returned by Business layer found null.");
					}
				}
				else{
					throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BAD_REQUEST_EXCEPTION, ExceptionConstants.USERMESSAGE_BAD_REQUEST_1, ExceptionConstants.ERRORMESSAGE_BAD_REQUEST_1, EExceptionTypes.BAD_REQUEST_EXCEPTION);
				}
				
			}
			else{
				throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BAD_REQUEST_EXCEPTION, ExceptionConstants.USERMESSAGE_BAD_REQUEST_1, ExceptionConstants.ERRORMESSAGE_BAD_REQUEST_1, EExceptionTypes.BAD_REQUEST_EXCEPTION);
			}
		}
		catch(StreamConversionException scEx){
			scEx.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_BAD_REQUEST_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Stream Convresion Exception occurred during the serivce execution : " + scEx.getMessage());
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + scEx.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(scEx.getExceptionDetail());
		}
		catch(BusinessException bEx){
			bEx.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_BUSINESS_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Business Exception occurred during the serivce execution : " + bEx.getMessage());
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + bEx.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(bEx.getExceptionDetail());
		}
		catch (BadRequestException brEx){
			brEx.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_BAD_REQUEST_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Bad Request Exception occurred during the serivce execution : " + brEx.getMessage());
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + brEx.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(brEx.getExceptionDetail());
		}
		catch(ApplicationException aEx){
			aEx.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_APPLICATION_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Application Exception occurred during the serivce execution : " + aEx.getMessage());
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + aEx.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(aEx.getExceptionDetail());
		}
		catch(Exception ex){
			ApplicationException exception = ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_EXCEPTION, ExceptionConstants.USERMESSAGE_EXCEPTION, ex.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Exception occurred during the serivce execution : " + ex.getMessage());
			exception.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_EXCEPTION);
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + exception.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(exception.getExceptionDetail());
		}
		catch(Throwable th){
			ApplicationException exception = ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_EXCEPTION, ExceptionConstants.USERMESSAGE_EXCEPTION, th.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
			logger.debug("|~| One Time Auth Service |~| Throwable occurred during the serivce execution : " + th.getMessage());
			exception.getExceptionDetail().setStatus(ExceptionConstants.SERVICE_STATUS_THROWABLE);
			logger.debug("An error response object will be sent to the client with error details. ExceptionDetail :" + exception.getExceptionDetail());
			response = ServiceUtility.generateErrorResponse(exception.getExceptionDetail());
		}
		
		try{
			responseStream = ServiceUtility.serializeServiceResponse(response);
		}
		catch(Exception ex){
			logger.error("Excpetion occurred while serializing the repsonse into stream : " + ex.getMessage());
			logger.error("To resume the service request, null response will be send to prevent the unexpected waiting behaviour in client.");
		}
		
		
		return responseStream;
		
	}
	

}
