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

package com.jobreporting.requests;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class WSBaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String tokenKey;


	/**
	 * @return the tokenKey
	 */
	public String getTokenKey() {
		return tokenKey;
	}

	/**
	 * @param tokenKey the tokenKey to set
	 */
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	

}
