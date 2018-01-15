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

import com.jobreporting.generic.exception.ApplicationException;
import com.jobreporting.generic.exception.base.ExceptionDetail;


@SuppressWarnings("serial")
public class BusinessException extends ApplicationException{
	
	protected Throwable throwable = null;

	
	/**
     * Constructor for BusinessException
     * @param msg - Message associated with the exception
     */
    public BusinessException(String msg) {
    	super(msg);
    }

    /**
     * Initializes a newly created <code>BusinessException</code> object.
     * @param	msg - the message associated with the Exception.
     * @param   cause - Throwable object
     */
    public BusinessException(String msg, Throwable cause) {
    	super(msg, cause);
    }
    
    /**
     * Constructor for BusinessException to set exceptionDetail
     */
    public BusinessException(ExceptionDetail exceptionDetail) {
            super(exceptionDetail);
    }


}
