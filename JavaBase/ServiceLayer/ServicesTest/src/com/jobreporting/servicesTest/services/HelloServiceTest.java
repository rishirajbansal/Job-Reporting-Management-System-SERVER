package com.jobreporting.servicesTest.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.servicesTest.base.BaseServicesTest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class HelloServiceTest extends BaseServicesTest {
	
	WSBaseRequest request = null;
	
	public void setUp() throws Exception {
		super.setUp();
		
		request = new WSBaseRequest();
	}

	@Test
	public void test() {
		
		try{
			/*String regex = "[^:|,;<>]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher("ramón");
            boolean valid = matcher.matches();
            if (!valid){
                System.out.println("invalid");
            }*/
            
	        
			WebResource.Builder builder = service.path("hello").accept(MediaType.APPLICATION_XML);
			String response = service.path("hello").accept(MediaType.APPLICATION_XML).get(String.class);
			assertNotNull(response);
			
			System.out.println("response : " + response);

		}
		catch(Exception ex){
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}

	}
	
	@Test
	public void test2() {
		
		try{
			 
			WebResource.Builder builder = service.path("hello").path("hello1").accept(MediaType.APPLICATION_OCTET_STREAM);
			ClientResponse response = builder.get(ClientResponse.class);
			InputStream is = response.getEntityInputStream();
			ObjectInputStream oi = new ObjectInputStream(is);
			oi.readObject();
			
			assertNotNull(response);
			
			System.out.println("response : " + response);

		}
		catch(Exception ex){
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}

	}
	
	@Test
	public void test3() {
		
		try{
			
			final WSBaseResponse request = new WSBaseResponse();
            request.setResponse("Report USer");
            request.setStatus("Report PWD");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();
            baos.close();
            oos.close();

            byte[] requestBytes = baos.toByteArray();
			 
            WebResource.Builder builder = service.path("hello").path("hello2").accept(MediaType.WILDCARD);
            
			ClientResponse response = builder.post(ClientResponse.class, requestBytes);
			InputStream is = response.getEntityInputStream();
			ObjectInputStream oi = new ObjectInputStream(is);
			oi.readObject();
			
			assertNotNull(response);
			
			System.out.println("response : " + response);

		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Exception occurred : " + ex.getMessage());
			fail("Exception occurred : " + ex.getMessage());
		}

	}

}
