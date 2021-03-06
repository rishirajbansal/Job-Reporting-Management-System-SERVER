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

package com.jobreporting.servicesBusiness.exception;

import com.jobreporting.generic.exception.base.ExceptionDetail;

@SuppressWarnings("serial")
public class InvalidTokenKeyException extends BusinessException {
	
	protected Throwable throwable = null;
	
	/**
     * Constructor for InvalidTokenKeyException
     * @param msg - Message associated with the exception
     */
    public InvalidTokenKeyException(String msg) {
    	super(msg);
    }

    /**
     * Initializes a newly created <code>InvalidTokenKeyException</code> object.
     * @param	msg - the message associated with the Exception.
     * @param   cause - Throwable object
     */
    public InvalidTokenKeyException(String msg, Throwable cause) {
    	super(msg, cause);
    }
    
    /**
     * Constructor for InvalidTokenKeyException to set exceptionDetail
     */
    public InvalidTokenKeyException(ExceptionDetail exceptionDetail) {
            super(exceptionDetail);
    }
    




	
	

}
