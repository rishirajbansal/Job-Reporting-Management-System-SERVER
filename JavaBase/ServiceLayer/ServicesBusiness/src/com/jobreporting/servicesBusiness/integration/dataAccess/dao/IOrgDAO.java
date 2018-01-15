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

package com.jobreporting.servicesBusiness.integration.dataAccess.dao;

import java.util.Map;

import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.DeviceAuthData;


/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public interface IOrgDAO {
	
	public String verifyAuthDetails(String orgName, String workerName) throws DataAccessException;
	
	public boolean saveMobileAuthDetails(DeviceAuthData daoData) throws DataAccessException;
	
	public int loadProducts(int orgId, Map<String, String> productsDetails) throws DataAccessException;
	
	public int loadCustomers(int orgId, Map<String, String> customersDetails) throws DataAccessException;
	
	public int loadTasks(int orgId, int wrkId, Map<String, String> tasksDetails) throws DataAccessException;
	
	public boolean saveTokenKey(String tokenKey) throws DataAccessException;
	
	public boolean validateTokenAuthenticity(String tokenKey) throws DataAccessException;
	
	public boolean validateTokenData(int orgId, int wrkId) throws DataAccessException;
	
	public int findDynaIdFromFieldHtmlName(String dynaHtmlName, String dynaTable, String dynaIdCol) throws DataAccessException;


}
