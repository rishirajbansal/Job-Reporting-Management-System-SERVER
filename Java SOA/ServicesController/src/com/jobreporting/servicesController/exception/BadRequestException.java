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

package com.jobreporting.servicesController.exception;

import com.jobreporting.generic.exception.ApplicationException;
import com.jobreporting.generic.exception.base.ExceptionDetail;

/**
 * @author Rishi
 *
 */
@SuppressWarnings("serial")
public class BadRequestException extends ApplicationException {
	
	protected Throwable throwable = null;
	
	/**
     * Constructor for BadRequestException
     * @param msg - Message associated with the exception
     */
    public BadRequestException(String msg) {
    	super(msg);
    }

    /**
     * Initializes a newly created <code>BadRequestException</code> object.
     * @param	msg - the message associated with the Exception.
     * @param   cause - Throwable object
     */
    public BadRequestException(String msg, Throwable cause) {
    	super(msg, cause);
    }
    
    /**
     * Constructor for BadRequestException to set exceptionDetail
     */
    public BadRequestException(ExceptionDetail exceptionDetail) {
            super(exceptionDetail);
    }
    


}
