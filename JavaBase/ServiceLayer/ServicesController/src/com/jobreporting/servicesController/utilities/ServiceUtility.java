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

package com.jobreporting.servicesController.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionDetail;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.responses.WSErrorResponse;
import com.jobreporting.servicesController.constants.ExceptionConstants;



public class ServiceUtility {
	
	public static LoggerManager logger = GenericUtility.getLogger(ServiceUtility.class.getName());
	
	
	public static WSErrorResponse generateErrorResponse(ExceptionDetail exDetail){
		
		WSErrorResponse errorResponse = new WSErrorResponse();
		
		errorResponse.setStatus(exDetail.getStatus());
		errorResponse.setCode(exDetail.getCode());
		errorResponse.setUserMessage(exDetail.getUserMessage());
		errorResponse.setErrorMessage(exDetail.getErrorMessage());
		
		return errorResponse;
		//return new JAXBElement<WSErrorResponse>(null, WSErrorResponse.class, errorResponse);
	}
	
	public static Object deSerializeServiceRequest(byte[] byteObject){
		
		Object obj = null;
		
		try{
			ByteArrayInputStream bInStream = new ByteArrayInputStream(byteObject);
			ObjectInputStream oInStream = new ObjectInputStream(bInStream);
			obj = (Object)oInStream.readObject();
			oInStream.close();
			bInStream.close();
		}
		catch(ClassNotFoundException cnfEx){
			logger.error("ClassNotFoundException exception occured in deSerializeByteStream");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_STREAM_CONVERSION_EXCEPTION, ExceptionConstants.USERMESSAGE_STREAM_CONVERSION, cnfEx.getMessage(), EExceptionTypes.STREAM_CONVERSION_EXCEPTION);
		}
		catch(IOException ioEx){
			logger.error("IOException exception occured in deSerializeByteStream");
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_STREAM_CONVERSION_EXCEPTION, ExceptionConstants.USERMESSAGE_STREAM_CONVERSION, ioEx.getMessage(), EExceptionTypes.STREAM_CONVERSION_EXCEPTION);
		}
		
		return obj;
		
	}
	
	public static StreamingOutput serializeServiceResponse(final Object response){
		
		StreamingOutput responseStream = null;
		
		responseStream = new StreamingOutput() {
			
	        public void write(OutputStream outStream) throws IOException, WebApplicationException {
	        	ObjectOutputStream oOutStream = new ObjectOutputStream(outStream);
	        	oOutStream.writeObject(response);
	        }
	        
		};
		
		return responseStream;
		
	}
	
	

}
