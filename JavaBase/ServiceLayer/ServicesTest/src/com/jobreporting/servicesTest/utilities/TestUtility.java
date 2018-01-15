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

package com.jobreporting.servicesTest.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUtility {
	
	public static byte[] convertRequestObjToByteStream(Object request) {
		
		byte[] requestBytes = null;
		
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(request);
	        oos.flush();
	        baos.close();
	        oos.close();
	        
	        requestBytes = baos.toByteArray();
		}
		catch (IOException ioEx){
			System.err.println("Excpetion occurred in test utility in (convertRequestObjToByteStream) " + ioEx.getMessage());
			ioEx.printStackTrace();
		}
		
		return requestBytes;
		
	}

}
