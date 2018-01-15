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

import javax.xml.bind.annotation.XmlRootElement;

import com.jobreporting.entities.WSDeviceAuth;


@XmlRootElement
public class WSOneTimeAuthRequest extends WSBaseRequest {
	
	private static final long serialVersionUID = 1L;
	
	
	private String orgName;
	
	private String workerName;
	
	private WSDeviceAuth deviceAuth;
	
	

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}

	/**
	 * @param workerName the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	/**
	 * @return the deviceAuth
	 */
	public WSDeviceAuth getDeviceAuth() {
		return deviceAuth;
	}

	/**
	 * @param deviceAuth the deviceAuth to set
	 */
	public void setDeviceAuth(WSDeviceAuth deviceAuth) {
		this.deviceAuth = deviceAuth;
	}

	
	

}
