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

import javax.xml.bind.annotation.XmlRootElement;

import com.jobreporting.entities.WSUserEntity;


@XmlRootElement
public class WSOneTimeAuthResponse extends WSBaseResponse {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> productsDetails;
	
	private int totalPrds;
	
	private String tokenKey;
	
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
	
	
	

}
