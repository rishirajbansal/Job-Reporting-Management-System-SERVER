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

package com.jobreporting.servicesBusiness.integration.dataAccess.base;

import java.security.MessageDigest;

import com.jobreporting.servicesBusiness.exception.DataAccessException;

import sun.misc.BASE64Encoder;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class DAOUtility {
	
	private DAOUtility() {
        
    }
	
	/**
	 * This encrypts a Password using MD5 Hash algorithm this is one way only,
	 * after using this one can compare two values by Hashing the other one
	 * and seeing if they are equal
	 *
	 * @param strPassword strPassword
	 *
	 * @return String
	 *
	*/
	public static String encryptPassword(String strPassword) {
		
		String strEncoded = new String("");

		try {			
			String input = strPassword;
			byte[] inputbytes = input.getBytes("UTF8");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(inputbytes);

			byte[] digest = md5.digest();
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(digest);
			strEncoded = base64.substring(0, base64.length() - 4);
		}
		catch(Exception e) {
			throw new DataAccessException("Error occured during the encryption of password");
		}
		return strEncoded;
	}
	

}
