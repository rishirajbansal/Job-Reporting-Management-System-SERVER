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

package com.jobreporting.generic.exception;

import com.jobreporting.generic.exception.base.ExceptionDetail;

/**
 * @author Rishi
 *
 */
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException{
	
	protected Throwable throwable = null;
	
	private ExceptionDetail exceptionDetail;
	
	String error = "";
	
	/**
     * Constructor for ApplicationException
     * @param msg - Message associated with the exception
     */
    public ApplicationException(String msg) {
    	super(msg);
    	error = msg;
    }

    /**
     * Initializes a newly created <code>ApplicationException</code> object.
     * @param	msg - the message associated with the Exception.
     * @param   cause - Throwable object
     */
    public ApplicationException(String msg, Throwable cause) {
    	super(msg, cause);
    }
    
    /**
     * Constructor for ApplicationException to set exceptionDetail
     */
    public ApplicationException(ExceptionDetail exceptionDetail) {
    		super(exceptionDetail.getErrorMessage());
            this.exceptionDetail = exceptionDetail ;
            this.error = exceptionDetail.getErrorMessage();
    }
    
    /**
     * Returns the error message string of the exception object.
     *
     * @return  the error message string of this <code>Exception</code>
     *          object if it was <code>Exception</code> with an
     *          error message string; or <code>null</code> if it was
     *          <code>Exception</code> created} with no error message.
     *
     */
    public String getMessage() {
    	/*if (throwable == null) {
    		return GenericConstants.EMPTY_STRING;
        }*/
    	
    	return error;
    }
    
    public ExceptionDetail getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(ExceptionDetail exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

}
