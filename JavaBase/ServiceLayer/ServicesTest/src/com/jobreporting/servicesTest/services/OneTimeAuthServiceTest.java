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

/**
 * 
 */
package com.jobreporting.servicesTest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.jobreporting.entities.WSDeviceAuth;
import com.jobreporting.requests.WSOneTimeAuthRequest;
import com.jobreporting.responses.WSErrorResponse;
import com.jobreporting.responses.WSOneTimeAuthResponse;
import com.jobreporting.servicesTest.base.BaseServicesTest;
import com.jobreporting.servicesTest.utilities.TestUtility;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Rishi Raj
 *
 */
public class OneTimeAuthServiceTest extends BaseServicesTest {
	
	WSOneTimeAuthRequest request = null;
	
	public void setUp() throws Exception {
		super.setUp();
		
		request = new WSOneTimeAuthRequest();
	}


	@Test
	public void testOneTimeAuthSuccess() {
		
		System.out.println("\n========================== One Time Auth : Success Case ===============================\n");
		
		WSDeviceAuth deviceAuth = new WSDeviceAuth();
        deviceAuth.setDeviceId("88888dfjsdlkjsfsd");
        deviceAuth.setAndroidId("sdflkjdsf7987987");
        
		request.setOrgName("ANZ Square");
		request.setWorkerName("Kate");
		request.setDeviceAuth(deviceAuth);

		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("oneTimeAuth").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSOneTimeAuthResponse response = (WSOneTimeAuthResponse)oinStream.readObject();
				
				assertNotNull(response);

				System.out.println("status : " + response.getStatus());
				
				assertEquals("200", response.getStatus());
				
				String tokenKey = response.getTokenKey();
				System.out.println("Token Key: " + tokenKey);
				System.out.println("Total Prds : " + response.getTotalPrds());
				System.out.println("Products details size : " + response.getProductsDetails().size());
				System.out.println("Report Structure details size : " + response.getAllWSUserEntites().size());
				
				assertNotNull(tokenKey);
				
			}
			else{
				System.err.println("Byte stream request object found null. Cannot proceed testing.");
			}
				
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}
		
	}
	
	@Test
	public void testOneTimeAuth_AuthNotPresent() {
		
		System.out.println("\n========================== One Time Auth : Auth details not present Case ===============================\n");
		
		request.setOrgName("");
		request.setWorkerName("");

		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("oneTimeAuth").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSErrorResponse response = (WSErrorResponse)oinStream.readObject();
				
				assertNotNull(response);
				
				System.out.println("Code : " + response.getCode());
				System.out.println("status : " + response.getStatus());
				System.out.println("Error Message : " + response.getErrorMessage());
				System.out.println("User Message : " + response.getUserMessage());

				
			}
			else{
				System.err.println("Byte stream request object found null. Cannot proceed testing.");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}
		
	}
	
	@Test
	public void testOneTimeAuth_InvalidOrg() {
		
		System.out.println("\n========================== One Time Auth : Invalid Org Name Case ===============================\n");
		
		request.setOrgName("Org 1");
		request.setWorkerName("Wrk 1");

		try{
			
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("oneTimeAuth").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSErrorResponse response = (WSErrorResponse)oinStream.readObject();
				
				assertNotNull(response);
				
				System.out.println("Code : " + response.getCode());
				System.out.println("status : " + response.getStatus());
				System.out.println("Error Message : " + response.getErrorMessage());
				System.out.println("User Message : " + response.getUserMessage());
				
			}
			else{
				System.err.println("Byte stream request object found null. Cannot proceed testing.");
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}
		
	}
	
	@Test
	public void testOneTimeAuth_InvalidWorker() {
		
		System.out.println("\n========================== One Time Auth : Invalid Worker Name Case ===============================\n");
		
		request.setOrgName("ANZ Square");
		request.setWorkerName("Wrk 1");

		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("oneTimeAuth").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSErrorResponse response = (WSErrorResponse)oinStream.readObject();
				
				assertNotNull(response);
				
				System.out.println("Code : " + response.getCode());
				System.out.println("status : " + response.getStatus());
				System.out.println("Error Message : " + response.getErrorMessage());
				System.out.println("User Message : " + response.getUserMessage());
				
			}
			else{
				System.err.println("Byte stream request object found null. Cannot proceed testing.");
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}
		
	}

}
