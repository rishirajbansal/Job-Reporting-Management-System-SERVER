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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;

/**
 * @author Rishi
 *
 */

@Path("/hello")
public class HelloService {
	
	public static LoggerManager logger = GenericUtility.getLogger(HelloService.class.getName());
	
	@GET
	@Produces( {MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public String sayXMLHello() {
		logger.debug("Hello Web Service is Called.");
		return "<?xml version=\"1.0\"?>" + "<hello> Hello. Welcome to Job reporting Web Services." + "</hello>";
	}
	
	@Path("/hello1")
	@GET
	@Produces( {MediaType.APPLICATION_OCTET_STREAM})
	public StreamingOutput sayXMLHello1() {
		
		//final ObjectOutputStream oo = null;
		
		/*final WSSerial response = new WSSerial();
		
		logger.debug("Hello Web Service 2 is Called.");
		
		response.setResponse("Test");
		response.setStatus("200");*/
		

		
		/*try {
			oo = new ObjectOutputStream(new FileOutputStream("test.out"));
			oo.writeObject(response);
			
			
		    
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return new StreamingOutput() {
	        public void write(OutputStream output) throws IOException, WebApplicationException {
	            try {
	            	ObjectOutputStream oo = new ObjectOutputStream(output);
	    			//oo.writeObject(response);
	            } catch (Exception e) {
	                throw new WebApplicationException(e);
	            }
	        }
		};
		
	}
	
	@Path("/hello2")
	@POST
	@Consumes({ MediaType.WILDCARD })
	@Produces( {MediaType.APPLICATION_OCTET_STREAM})
	//public StreamingOutput sayXMLHello2(InputStream request) {
	public StreamingOutput sayXMLHello2(byte[] request) {
		
		
		logger.debug("Hello Web Service 3 is Called.");
		
		ObjectInputStream oInStream = null;

		
		try {

			ByteArrayInputStream bInStream = new ByteArrayInputStream(request);
    		oInStream = new ObjectInputStream(bInStream);
            /*WSSerial data = (WSSerial)oInStream.readObject();

			logger.debug(data.getStatus());
			logger.debug(data.getResponse());*/
			
			oInStream.close();
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		
		/*final WSSerial response = new WSSerial();
		
		response.setResponse("Test");
		response.setStatus("200");*/
		
		
		return new StreamingOutput() {
	        public void write(OutputStream output) throws IOException, WebApplicationException {
	            try {
	            	ObjectOutputStream oo = new ObjectOutputStream(output);
	    			//oo.writeObject(response);
	            } catch (Exception e) {
	                throw new WebApplicationException(e);
	            }
	        }
		};
		
	}


}
