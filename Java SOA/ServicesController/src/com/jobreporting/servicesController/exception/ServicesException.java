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
public class ServicesException extends ApplicationException{
	
	protected Throwable throwable = null;
	
	/**
     * Constructor for ServicesException
     * @param msg - Message associated with the exception
     */
    public ServicesException(String msg) {
    	super(msg);
    }

    /**
     * Initializes a newly created <code>ServicesException</code> object.
     * @param	msg - the message associated with the Exception.
     * @param   cause - Throwable object
     */
    public ServicesException(String msg, Throwable cause) {
    	super(msg, cause);
    }
    
    /**
     * Constructor for ServicesException to set exceptionDetail
     */
    public ServicesException(ExceptionDetail exceptionDetail) {
            super(exceptionDetail);
    }
    


}
