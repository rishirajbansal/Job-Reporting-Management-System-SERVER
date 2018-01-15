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

package com.jobreporting.servicesBusiness.utilities;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;

public class ValidateTokenAuthenticity {
	
	
	public static boolean validateTokenKeyAuthenticity(String tokenKey, IOrgDAO orgDao){
		
		boolean isValid = false;
		
		if (!GenericUtility.safeTrim(tokenKey).equals(GenericConstants.EMPTY_STRING)){			
			isValid = orgDao.validateTokenAuthenticity(tokenKey);
		}
		
		return isValid;
		
	}
	
	public static boolean validateTokenKeyData(int orgId, int wrkId, IOrgDAO orgDao){
		
		boolean isValid = false;
		
		isValid = orgDao.validateTokenData(orgId, wrkId);
		
		return isValid;
		
	}

}
