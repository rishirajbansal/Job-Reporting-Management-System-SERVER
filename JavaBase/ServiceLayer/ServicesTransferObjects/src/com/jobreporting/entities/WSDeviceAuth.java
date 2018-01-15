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

package com.jobreporting.entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */

@XmlRootElement
public class WSDeviceAuth extends WSBaseObject {
	
	private static final long serialVersionUID = 1L;

	private String deviceId;
	
	private String androidId;
	

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the androidId
	 */
	public String getAndroidId() {
		return androidId;
	}

	/**
	 * @param androidId the androidId to set
	 */
	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}
	
	
	

}
