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

package com.jobreporting.servicesBusiness.validations;

import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSSyncherRequest;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;

public class SyncherValidator extends AbstractBusinessValidator {

	@Override
	public boolean validate(WSBaseRequest request, ERequestType requestType) throws BusinessValidationException {

		boolean isValid = true;
		
		WSSyncherRequest wsSyncherRequest = (WSSyncherRequest)request;
		
		switch(requestType){
		
			case SYNCHER_REQUEST: 
				isValid = validateSyncherRequest(wsSyncherRequest);
				break;
		
			default: 
				throw new BusinessException("Unsupported request type.");
		
		}
		
		return isValid;
		
	}
	
	public boolean validateSyncherRequest(WSSyncherRequest wsSyncherRequest){
		
		boolean isValid = true;
		
		/* Mandatory validation check - token key */
		validateTokenKey(wsSyncherRequest.getTokenKey());
		
		/* Format validation check - token key */
		validateTokenFormat(wsSyncherRequest.getTokenKey());
		
		return isValid;
		
	}


}
