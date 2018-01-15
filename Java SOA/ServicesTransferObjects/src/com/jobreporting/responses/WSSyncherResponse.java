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

package com.jobreporting.responses;

import java.util.List;
import java.util.Map;

import com.jobreporting.entities.WSUserEntity;

public class WSSyncherResponse extends WSBaseResponse {
	
	private static final long serialVersionUID = 1L;
	
	
	private Map<String, String> productsDetails;
	
	private int totalPrds;
	
	private Map<String, String> customersDetails;
	
	private int totalCstmrs;
	
	private Map<String, String> tasksDetails;
	
	private int totalTsks;
	
	private List<WSUserEntity> allWSUserEntites;
	

	/**
	 * @return the productsDetails
	 */
	public Map<String, String> getProductsDetails() {
		return productsDetails;
	}

	/**
	 * @param productsDetails the productsDetails to set
	 */
	public void setProductsDetails(Map<String, String> productsDetails) {
		this.productsDetails = productsDetails;
	}

	/**
	 * @return the totalPrds
	 */
	public int getTotalPrds() {
		return totalPrds;
	}

	/**
	 * @param totalPrds the totalPrds to set
	 */
	public void setTotalPrds(int totalPrds) {
		this.totalPrds = totalPrds;
	}

	/**
	 * @return the allWSUserEntites
	 */
	public List<WSUserEntity> getAllWSUserEntites() {
		return allWSUserEntites;
	}

	/**
	 * @param allWSUserEntites the allWSUserEntites to set
	 */
	public void setAllWSUserEntites(List<WSUserEntity> allWSUserEntites) {
		this.allWSUserEntites = allWSUserEntites;
	}

	/**
	 * @return the customersDetails
	 */
	public Map<String, String> getCustomersDetails() {
		return customersDetails;
	}

	/**
	 * @param customersDetails the customersDetails to set
	 */
	public void setCustomersDetails(Map<String, String> customersDetails) {
		this.customersDetails = customersDetails;
	}

	/**
	 * @return the totalCstmrs
	 */
	public int getTotalCstmrs() {
		return totalCstmrs;
	}

	/**
	 * @param totalCstmrs the totalCstmrs to set
	 */
	public void setTotalCstmrs(int totalCstmrs) {
		this.totalCstmrs = totalCstmrs;
	}

	/**
	 * @return the tasksDetails
	 */
	public Map<String, String> getTasksDetails() {
		return tasksDetails;
	}

	/**
	 * @param tasksDetails the tasksDetails to set
	 */
	public void setTasksDetails(Map<String, String> tasksDetails) {
		this.tasksDetails = tasksDetails;
	}

	/**
	 * @return the totalTsks
	 */
	public int getTotalTsks() {
		return totalTsks;
	}

	/**
	 * @param totalTsks the totalTsks to set
	 */
	public void setTotalTsks(int totalTsks) {
		this.totalTsks = totalTsks;
	}
	
	
	
	

}
