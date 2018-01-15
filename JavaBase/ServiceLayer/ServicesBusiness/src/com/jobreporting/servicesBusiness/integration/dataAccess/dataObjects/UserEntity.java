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

package com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects;

import java.util.List;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class UserEntity extends DynaFields {
	
	private List<String> selectedListValuesEn;
    private List<String> selectedListValuesEs;
    private List<String> htmlValidationsList;
    private List<String> htmlValidationsMessagesEnList;
    private List<String> htmlValidationsMessagesEsList;
    
    private String savedValue;
    

	/**
	 * @return the selectedListValuesEn
	 */
	public List<String> getSelectedListValuesEn() {
		return selectedListValuesEn;
	}

	/**
	 * @param selectedListValuesEn the selectedListValuesEn to set
	 */
	public void setSelectedListValuesEn(List<String> selectedListValuesEn) {
		this.selectedListValuesEn = selectedListValuesEn;
	}

	/**
	 * @return the selectedListValuesEs
	 */
	public List<String> getSelectedListValuesEs() {
		return selectedListValuesEs;
	}

	/**
	 * @param selectedListValuesEs the selectedListValuesEs to set
	 */
	public void setSelectedListValuesEs(List<String> selectedListValuesEs) {
		this.selectedListValuesEs = selectedListValuesEs;
	}

	/**
	 * @return the htmlValidationsList
	 */
	public List<String> getHtmlValidationsList() {
		return htmlValidationsList;
	}

	/**
	 * @param htmlValidationsList the htmlValidationsList to set
	 */
	public void setHtmlValidationsList(List<String> htmlValidationsList) {
		this.htmlValidationsList = htmlValidationsList;
	}

	/**
	 * @return the htmlValidationsMessagesEnList
	 */
	public List<String> getHtmlValidationsMessagesEnList() {
		return htmlValidationsMessagesEnList;
	}

	/**
	 * @param htmlValidationsMessagesEnList the htmlValidationsMessagesEnList to set
	 */
	public void setHtmlValidationsMessagesEnList(List<String> htmlValidationsMessagesEnList) {
		this.htmlValidationsMessagesEnList = htmlValidationsMessagesEnList;
	}

	/**
	 * @return the htmlValidationsMessagesEsList
	 */
	public List<String> getHtmlValidationsMessagesEsList() {
		return htmlValidationsMessagesEsList;
	}

	/**
	 * @param htmlValidationsMessagesEsList the htmlValidationsMessagesEsList to set
	 */
	public void setHtmlValidationsMessagesEsList(List<String> htmlValidationsMessagesEsList) {
		this.htmlValidationsMessagesEsList = htmlValidationsMessagesEsList;
	}

	/**
	 * @return the savedValue
	 */
	public String getSavedValue() {
		return savedValue;
	}

	/**
	 * @param savedValue the savedValue to set
	 */
	public void setSavedValue(String savedValue) {
		this.savedValue = savedValue;
	}
    

	

}
