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

package com.jobreporting.generic.exception.base;

public class ExceptionDetail {
	
	private String status;
	
    private String code;

    private String errorMessage;
    
    private String userMessage;

    
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}


	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}


	@Override
    public String toString(){
        StringBuffer sBuffer = new StringBuffer();

        sBuffer.append("[");
        sBuffer.append("Status : " + status).append(" || ");
        sBuffer.append("Code : " + code).append(" || ");
        sBuffer.append("ErrorMessage : " + errorMessage).append(" || ");
        sBuffer.append("UserMessage : " + userMessage);
        sBuffer.append("]");

        return sBuffer.toString();
    }

}
