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

package com.jobreporting.servicesBusiness.business.base;

import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.responses.WSBaseResponse;


/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public interface IBusinessAction {
	
	public WSBaseResponse execute(WSBaseRequest request, ERequestType requestType) throws Exception;

}
