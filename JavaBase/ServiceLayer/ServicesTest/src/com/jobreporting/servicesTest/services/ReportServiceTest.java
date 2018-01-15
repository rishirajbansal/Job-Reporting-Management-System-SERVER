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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import com.jobreporting.requests.WSReportsRequest;
import com.jobreporting.requests.WSSyncherRequest;
import com.jobreporting.responses.WSErrorResponse;
import com.jobreporting.responses.WSReportsResponse;
import com.jobreporting.servicesTest.base.BaseServicesTest;
import com.jobreporting.servicesTest.utilities.TestUtility;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ReportServiceTest extends BaseServicesTest {

	WSReportsRequest request = null;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		request = new WSReportsRequest();
	}
	
	@Test
	public void testReportSubmit_SuccessCase() {
		System.out.println("\n========================== Sync : Success Case ===============================\n");
		
		request.setTokenKey("T||L8x6OmKk3JRkywbNi1oy||55||1");
		request.setReportData("p_1:Samsung Galaxy|p_2:C001|p_3:New Launch|t_1:Camera Issue|t_2:Scheduled|t_3:Fix camera|w_1:Kate|w_2:Driver|c_1:John Kepler");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("reports").accept(MediaType.WILDCARD);
	            
				ClientResponse clientResponse = builder.post(ClientResponse.class, requestBStream);
				InputStream inStream = clientResponse.getEntityInputStream();
				ObjectInputStream oinStream = new ObjectInputStream(inStream);
				WSReportsResponse response = (WSReportsResponse)oinStream.readObject();
				
				assertNotNull(response);
				
				System.out.println("response : " + response.getResponse());
				System.out.println("status : " + response.getStatus());
				
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
	public void testReportSubmit_DataNotPresent() {
		System.out.println("\n========================== Sync : Data Not Present Case ===============================\n");
		
		request.setTokenKey("T||L8x6OmKk3JRkywbNi1oy||55||1");
		request.setReportData("");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("reports").accept(MediaType.WILDCARD);
	            
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
	public void testReportSubmit_PublishingExceptionFromWebServer() {
		System.out.println("\n========================== Sync : Success Case ===============================\n");
		
		request.setTokenKey("T||9wRsWVp3tBkc6cKKtKlj||55||1");
		request.setReportData("p_1:Samsung Galaxy|p_2:C001|p_3:New Launch|t_1:Camera Issue|t_2:Scheduled|t_3:Fix camera|w_1:Kate|w_2:Driver|c_1:John Kepler");
		
		try{
			byte[] requestBStream = TestUtility.convertRequestObjToByteStream(request);
			
			if (null != requestBStream){
				
				WebResource.Builder builder = service.path("reports").accept(MediaType.WILDCARD);
	            
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
