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

import java.util.List;

import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.UserEntity;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public interface IReportDAO {
	
	public boolean fetchProductDynaDetails(int orgId, List<UserEntity> listPrdUserEntities) throws DataAccessException;
	
	public boolean fetchTaskDynaDetails(int orgId, List<UserEntity> listTskUserEntities) throws DataAccessException;
	
	public boolean fetchWorkerDynaDetails(int orgId, List<UserEntity> listWrkUserEntities) throws DataAccessException;
	
	public boolean fetchCustomerDynaDetails(int orgId, List<UserEntity> listCstUserEntities) throws DataAccessException;
	
	public boolean fetchReportDynaDetails(int orgId, List<UserEntity> allUserEntities) throws DataAccessException;
	
	public int saveReportDetails(String tokenKey, String reportData, String wrkName, String clientName, String coordinates, String location, String updateHis) throws DataAccessException;
	
	public String fetchEntitySingleDataFromId(int orgId, int entityId, int dynaId, String entityTable, String entityIdCol) throws DataAccessException;
	
	public boolean updateReportData(int orgId, int reportId, String reportData) throws DataAccessException;
	

}
