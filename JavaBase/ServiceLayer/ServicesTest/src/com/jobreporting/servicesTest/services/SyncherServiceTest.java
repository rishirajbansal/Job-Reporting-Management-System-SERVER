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

package com.jobreporting.servicesTest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.jobreporting.requests.WSSyncherRequest;
import com.jobreporting.responses.WSErrorResponse;
import com.jobreporting.responses.WSSyncherResponse;
import com.jobreporting.servicesTest.base.BaseServicesTest;
import com.jobreporting.servicesTest.utilities.TestUtility;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SyncherServiceTest extends BaseServicesTest {
	
	WSSyncherRequest request = null;
	
	public void setUp() throws Exception {
		super.setUp();
		
		request = new WSSyncherRequest();
	}
	

	@Test
	public void testSyncSuccess() {
		System.out.println("\n========================== Sync : Success Case ===============================\n");
		
		request.setTokenKey("T||9wRsWVp3tBkc6cKKtKlj||55||1");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSSyncherResponse response = (WSSyncherResponse)oinStream.readObject();
				
				assertNotNull(response);
				
				assertEquals("200", response.getStatus());
				
				System.out.println("Total Prds : " + response.getTotalPrds());
				System.out.println("Products details size : " + response.getProductsDetails().size());
				System.out.println("Products details : " + response.getProductsDetails());
				
				System.out.println("Total Cstmrs : " + response.getTotalCstmrs());
				System.out.println("Customers details size : " + response.getCustomersDetails().size());
				System.out.println("Customers details : " + response.getCustomersDetails());
				
				System.out.println("Total Tasks : " + response.getTotalTsks());
				System.out.println("Tasks details size : " + response.getTasksDetails().size());
				System.out.println("Tasks details : " + response.getTasksDetails());
				
				System.out.println("Report Structure details size : " + response.getAllWSUserEntites().size());
				
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
	public void testSync_TokenKeyNotPresent() {
		System.out.println("\n========================== Sync : Token Key Not Present Case ===============================\n");
		
		request.setTokenKey("");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
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
	public void testSync_InvalidTokenKeyFormat() {
		System.out.println("\n========================== Sync : Invalid Token Key Format Case ===============================\n");
		
		request.setTokenKey("u7GgZVVMp6dHttxbZWmS");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
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
	public void testSync_InvalidTokenKey() {
		System.out.println("\n========================== Sync : Invalid Token Key Case ===============================\n");
		
		request.setTokenKey("T||u7GgZVVMp6dHttxbZ||36||5");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
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
	public void testSync_InvalidOrg_TokenKey() {
		System.out.println("\n========================== Sync : Invalid Org in Token Key Case ===============================\n");
		
		request.setTokenKey("T||L8x6OmKk3JRkywbNi1oy||12||5");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
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
	public void testSync_InvalidWorker_TokenKey() {
		System.out.println("\n========================== Sync : Invalid Worker in Token Key Case ===============================\n");
		
		request.setTokenKey("T||L8x6OmKk3JRkywbNi1oy||62||5");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("sync").accept(MediaType.WILDCARD);
	            
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
