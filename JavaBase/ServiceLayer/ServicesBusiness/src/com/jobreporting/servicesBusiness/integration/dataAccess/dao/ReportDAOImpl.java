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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.DynaFieldNotFoundException;
import com.jobreporting.servicesBusiness.exception.EntityDataNotFoundException;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.UserEntity;


/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class ReportDAOImpl implements IReportDAO {
	
	public static LoggerManager logger = GenericUtility.getLogger(ReportDAOImpl.class.getName());
	
	
	private static final String SQL_SELECT_ORGS_PRODUCTS = "SELECT * FROM orgs_prdts WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_TASKS = "SELECT * FROM orgs_tsks WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_WORKERS = "SELECT * FROM orgs_wrks WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_CUSTOMERS = "SELECT * FROM orgs_cstmrs WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_RPT_STRUCT = "SELECT * FROM orgs_rpt_structs WHERE idorgs = ?";
	
	private static final String SQL_SELECT_DYNAFIELDS_PRODUCTS_MAPPING = "SELECT * FROM prdts_fields WHERE idprdts_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_TASKS_MAPPING = "SELECT * FROM tsks_fields WHERE idtsks_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_WORKERS_MAPPING = "SELECT * FROM wrks_fields WHERE idwrks_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_CUSTOMERS_MAPPING = "SELECT * FROM cstmrs_fields WHERE idcstmrs_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_RPT_MAPPING = "SELECT * FROM rpts_fields WHERE idrpts_fields IN (P1)";
	
	private static final String SQL_INSERT_USER_ORGS_RPTS = "INSERT INTO org_rpts_P1 (sub_datetime, sub_by, data, clientname, coordinates, location, update_his, created_on) VALUES (NOW(), ?, ?, ?, ?, ?, ?, NOW())";
	
	private static final String SQL_SELECT_USER_ORGS_FIND_ENTITY_NAME = "SELECT * FROM P1_P2 WHERE P3 = ?";
	
	private static final String SQL_UPDATE_USER_ORGS_RPTS_DATA = "UPDATE org_rpts_P1 SET data = ? where idorg_rpts = ?";
	
	
	
	
	@Override
	public boolean fetchProductDynaDetails(int orgId, List<UserEntity> listPrdUserEntities) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		Map<String, String> dynaFieldsProcessedDetailsEn = new HashMap<String, String>();
		Map<String, String> dynaFieldsProcessedDetailsEs = new HashMap<String, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_ORGS_PRODUCTS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchProductDynaDetails) Loading org records for Products Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				String dynaFieldsListValuesEn = rs.getString("dyna_fields_list_values_en");
				String[] dynaFieldsListValuesEnArr = dynaFieldsListValuesEn.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				String dynaFieldsListValuesEs = rs.getString("dyna_fields_list_values_es");
				String[] dynaFieldsListValuesEsArr = dynaFieldsListValuesEs.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					String dynaFieldsValue = BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY;
					
					if (dynaFieldsListValuesEnArr.length > 0){
						for (String value : dynaFieldsListValuesEnArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEn.put(dynaFieldId, dynaFieldsValue);
					
					if (dynaFieldsListValuesEsArr.length > 0){
						for (String value : dynaFieldsListValuesEsArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEs.put(dynaFieldId, dynaFieldsValue);
					
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				
				consDynaFieldIds = consDynaFieldIds.substring(0,  consDynaFieldIds.length()-1);
				
			}
			else{
				logger.error("(fetchProductDynaDetails) Failed to retreive org records for Product details.");
				throw new DynaFieldNotFoundException("Failed to retreive org records for Product details.");
			}
			
			//Get the details of Dyna fields
			String sql = SQL_SELECT_DYNAFIELDS_PRODUCTS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchProductDynaDetails) Loading dyna records for Products Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					UserEntity userEntity = new UserEntity();
					
					populateDynaFieldsObj(rs, BusinessConstants.DYNAFIELDS_TABLE_ID_PRODUCT, userEntity);
					
					if (dynaFieldsProcessedDetailsEn.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEn.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEn(listValuesList);
						}
					}
					
					if (dynaFieldsProcessedDetailsEs.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEs.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEs(listValuesList);
						}
					}
					
					userEntity.setHtmlValidationsList(Arrays.asList(userEntity.getHtmlValidations().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEnList(Arrays.asList(userEntity.getHtmlValidationsMessagesEn().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEsList(Arrays.asList(userEntity.getHtmlValidationsMessagesEs().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					
					listPrdUserEntities.add(userEntity);
				}
				
				flag = true;
				
			}
			else{
				logger.error("(fetchProductDynaDetails) Failed to retreive dyna records for Product details.");
				throw new DynaFieldNotFoundException("Failed to retreive dyna records for Product details.");
			}
			
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchProductDynaDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchProductDynaDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchProductDynaDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchProductDynaDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchProductDynaDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		
		return flag;
	}
	
	@Override
	public boolean fetchTaskDynaDetails(int orgId, List<UserEntity> listTskUserEntities) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		Map<String, String> dynaFieldsProcessedDetailsEn = new HashMap<String, String>();
		Map<String, String> dynaFieldsProcessedDetailsEs = new HashMap<String, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_ORGS_TASKS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchTaskDynaDetails) Loading org records for Tasks Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				String dynaFieldsListValuesEn = rs.getString("dyna_fields_list_values_en");
				String[] dynaFieldsListValuesEnArr = dynaFieldsListValuesEn.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				String dynaFieldsListValuesEs = rs.getString("dyna_fields_list_values_es");
				String[] dynaFieldsListValuesEsArr = dynaFieldsListValuesEs.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					String dynaFieldsValue = BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY;
					
					if (dynaFieldsListValuesEnArr.length > 0){
						for (String value : dynaFieldsListValuesEnArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEn.put(dynaFieldId, dynaFieldsValue);
					
					if (dynaFieldsListValuesEsArr.length > 0){
						for (String value : dynaFieldsListValuesEsArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEs.put(dynaFieldId, dynaFieldsValue);
					
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				
				consDynaFieldIds = consDynaFieldIds.substring(0,  consDynaFieldIds.length()-1);
			}
			else{
				logger.error("(fetchTaskDynaDetails) Failed to retreive org records for Task details.");
				throw new DynaFieldNotFoundException("Failed to retreive org records for Task details.");
			}
			
			//Get the details of Dyna fields
			String sql = SQL_SELECT_DYNAFIELDS_TASKS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchTaskDynaDetails) Loading dyna records for Task Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					UserEntity userEntity = new UserEntity();
					
					populateDynaFieldsObj(rs, BusinessConstants.DYNAFIELDS_TABLE_ID_TASK, userEntity);
					
					if (dynaFieldsProcessedDetailsEn.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEn.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEn(listValuesList);
						}
					}
					
					if (dynaFieldsProcessedDetailsEs.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEs.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEs(listValuesList);
						}
					}
					
					userEntity.setHtmlValidationsList(Arrays.asList(userEntity.getHtmlValidations().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEnList(Arrays.asList(userEntity.getHtmlValidationsMessagesEn().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEsList(Arrays.asList(userEntity.getHtmlValidationsMessagesEs().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					
					listTskUserEntities.add(userEntity);
				}
				
				flag = true;
				
			}
			else{
				logger.error("(fetchTaskDynaDetails) Failed to retreive dyna records for Task details.");
				throw new DynaFieldNotFoundException("Failed to retreive dyna records for Task details.");
			}
			
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchTaskDynaDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchTaskDynaDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchTaskDynaDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchTaskDynaDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchTaskDynaDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		
		return flag;
	}

	@Override
	public boolean fetchWorkerDynaDetails(int orgId, List<UserEntity> listWrkUserEntities) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		Map<String, String> dynaFieldsProcessedDetailsEn = new HashMap<String, String>();
		Map<String, String> dynaFieldsProcessedDetailsEs = new HashMap<String, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_ORGS_WORKERS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchWorkerDynaDetails) Loading org records for Worker Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				String dynaFieldsListValuesEn = rs.getString("dyna_fields_list_values_en");
				String[] dynaFieldsListValuesEnArr = dynaFieldsListValuesEn.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				String dynaFieldsListValuesEs = rs.getString("dyna_fields_list_values_es");
				String[] dynaFieldsListValuesEsArr = dynaFieldsListValuesEs.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					String dynaFieldsValue = BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY;
					
					if (dynaFieldsListValuesEnArr.length > 0){
						for (String value : dynaFieldsListValuesEnArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEn.put(dynaFieldId, dynaFieldsValue);
					
					if (dynaFieldsListValuesEsArr.length > 0){
						for (String value : dynaFieldsListValuesEsArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEs.put(dynaFieldId, dynaFieldsValue);
					
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				
				consDynaFieldIds = consDynaFieldIds.substring(0,  consDynaFieldIds.length()-1);
			}
			else{
				logger.error("(fetchWorkerDynaDetails) Failed to retreive org records for Worker details.");
				throw new DynaFieldNotFoundException("Failed to retreive org records for Worker details.");
			}
			
			//Get the details of Dyna fields
			String sql = SQL_SELECT_DYNAFIELDS_WORKERS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchWorkerDynaDetails) Loading dyna records for Workers Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					UserEntity userEntity = new UserEntity();
					
					populateDynaFieldsObj(rs, BusinessConstants.DYNAFIELDS_TABLE_ID_WORKER, userEntity);
					
					if (dynaFieldsProcessedDetailsEn.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEn.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEn(listValuesList);
						}
					}
					
					if (dynaFieldsProcessedDetailsEs.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEs.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEs(listValuesList);
						}
					}
					
					userEntity.setHtmlValidationsList(Arrays.asList(userEntity.getHtmlValidations().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEnList(Arrays.asList(userEntity.getHtmlValidationsMessagesEn().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEsList(Arrays.asList(userEntity.getHtmlValidationsMessagesEs().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					
					listWrkUserEntities.add(userEntity);
				}
				
				flag = true;
				
			}
			else{
				logger.error("(fetchWorkerDynaDetails) Failed to retreive dyna records for Worker details.");
				throw new DynaFieldNotFoundException("Failed to retreive dyna records for Worker details.");
			}
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchWorkerDynaDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchWorkerDynaDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchWorkerDynaDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchWorkerDynaDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchWorkerDynaDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		
		return flag;
	}

	@Override
	public boolean fetchCustomerDynaDetails(int orgId, List<UserEntity> listCstUserEntities) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		Map<String, String> dynaFieldsProcessedDetailsEn = new HashMap<String, String>();
		Map<String, String> dynaFieldsProcessedDetailsEs = new HashMap<String, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_ORGS_CUSTOMERS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchCustomerDynaDetails) Loading org records for Customer Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				String dynaFieldsListValuesEn = rs.getString("dyna_fields_list_values_en");
				String[] dynaFieldsListValuesEnArr = dynaFieldsListValuesEn.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				String dynaFieldsListValuesEs = rs.getString("dyna_fields_list_values_es");
				String[] dynaFieldsListValuesEsArr = dynaFieldsListValuesEs.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					String dynaFieldsValue = BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY;
					
					if (dynaFieldsListValuesEnArr.length > 0){
						for (String value : dynaFieldsListValuesEnArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEn.put(dynaFieldId, dynaFieldsValue);
					
					if (dynaFieldsListValuesEsArr.length > 0){
						for (String value : dynaFieldsListValuesEsArr){
							if (value.indexOf(dynaFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR) != -1){
								dynaFieldsValue = (value.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR))[1];
								break;
							}
						}
					}
					dynaFieldsProcessedDetailsEs.put(dynaFieldId, dynaFieldsValue);
					
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				
				consDynaFieldIds = consDynaFieldIds.substring(0,  consDynaFieldIds.length()-1);
			}
			else{
				logger.error("(fetchCustomerDynaDetails) Failed to retreive org records for Customer details.");
				throw new DynaFieldNotFoundException("Failed to retreive org records for Customer details.");
			}
			
			//Get the details of Dyna fields
			String sql = SQL_SELECT_DYNAFIELDS_CUSTOMERS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchCustomerDynaDetails) Loading dyna records for Customers Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					UserEntity userEntity = new UserEntity();
					
					populateDynaFieldsObj(rs, BusinessConstants.DYNAFIELDS_TABLE_ID_CUSTOMER, userEntity);
					
					if (dynaFieldsProcessedDetailsEn.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEn.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEn(listValuesList);
						}
					}
					
					if (dynaFieldsProcessedDetailsEs.containsKey(userEntity.getIdDynaFields())){
						String dynaFieldsValue = dynaFieldsProcessedDetailsEs.get(userEntity.getIdDynaFields());
						if (!GenericUtility.safeTrim(dynaFieldsValue).equals(BusinessConstants.DYNAFIELDS_LIST_FIELD_EMPTY)){
							String[] listValuesArr = dynaFieldsValue.split(BusinessConstants.LIST_VALUES_SEPERATOR);
							List<String> listValuesList = Arrays.asList(listValuesArr);
							userEntity.setSelectedListValuesEs(listValuesList);
						}
					}
					
					userEntity.setHtmlValidationsList(Arrays.asList(userEntity.getHtmlValidations().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEnList(Arrays.asList(userEntity.getHtmlValidationsMessagesEn().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEsList(Arrays.asList(userEntity.getHtmlValidationsMessagesEs().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					
					listCstUserEntities.add(userEntity);
				}
				
				flag = true;
				
			}
			else{
				logger.error("(fetchCustomerDynaDetails) Failed to retreive dyna records for Worker details.");
				throw new DynaFieldNotFoundException("Failed to retreive dyna records for Worker details.");
			}
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchCustomerDynaDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchCustomerDynaDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchCustomerDynaDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchCustomerDynaDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchCustomerDynaDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		
		return flag;
	}
	
	@Override
	public boolean fetchReportDynaDetails(int orgId, List<UserEntity> allUserEntities) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";		
		
		List<UserEntity> listPrdUserEntities = new ArrayList<UserEntity>();
		List<UserEntity> listTskUserEntities = new ArrayList<UserEntity>();
		List<UserEntity> listWrkUserEntities = new ArrayList<UserEntity>();
		List<UserEntity> listCstUserEntities = new ArrayList<UserEntity>();
		List<UserEntity> listRptUserEntities = new ArrayList<UserEntity>();
		
		try{
			boolean loadFlag = fetchProductDynaDetails(orgId, listPrdUserEntities);
            if (!loadFlag){
            	logger.error("Failed to load Dyna Details for Prodcucts while fetching the details for report details.");
                return loadFlag;
            }
            
            loadFlag = fetchTaskDynaDetails(orgId, listTskUserEntities);
            if (!loadFlag){
            	logger.error("Failed to load Dyna Details for Tasks while fetching the details for report details.");
                return loadFlag;
            }
            
            loadFlag = fetchWorkerDynaDetails(orgId, listWrkUserEntities);
            if (!loadFlag){
            	logger.error("Failed to load Dyna Details for Workers while fetching the details for report details.");
                return loadFlag;
            }
            
            loadFlag = fetchCustomerDynaDetails(orgId, listCstUserEntities);
            if (!loadFlag){
            	logger.error("Failed to load Dyna Details for Customers while fetching the details for report details.");
                return loadFlag;
            }

            
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_ORGS_RPT_STRUCT);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchReportDynaDetails) Loading org records for Report Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				
				String cstmrDynaFieldIds = rs.getString("cstmrs_dyna_fields");
				String[] cstmrDynaFieldIdsArr = cstmrDynaFieldIds.split(BusinessConstants.DYNAFIELD_IDS_SEPERATOR);
				for (UserEntity userEntity : listCstUserEntities){
					String dynaId = userEntity.getIdDynaFields();
					if (ArrayUtils.contains(cstmrDynaFieldIdsArr, dynaId)){
						userEntity.setIdDynaFields(BusinessConstants.DYNAFIELDS_CUSTOMER_CBID_PREFIX + dynaId);
						allUserEntities.add(userEntity);
					}
				}
				
				String tskDynaFieldIds = rs.getString("tsks_dyna_fields");
				String[] tskDynaFieldIdsArr = tskDynaFieldIds.split(BusinessConstants.DYNAFIELD_IDS_SEPERATOR);
				for (UserEntity userEntity : listTskUserEntities){
					String dynaId = userEntity.getIdDynaFields();
					if (ArrayUtils.contains(tskDynaFieldIdsArr, dynaId)){
						userEntity.setIdDynaFields(BusinessConstants.DYNAFIELDS_TASK_CBID_PREFIX + dynaId);
						allUserEntities.add(userEntity);
					}
				}
				
				String prdDynaFieldIds = rs.getString("prds_dyna_fields");
				String[] prdDynaFieldIdsArr = prdDynaFieldIds.split(BusinessConstants.DYNAFIELD_IDS_SEPERATOR);
				for (UserEntity userEntity : listPrdUserEntities){
					String dynaId = userEntity.getIdDynaFields();
					if (ArrayUtils.contains(prdDynaFieldIdsArr, dynaId)){
						userEntity.setIdDynaFields(BusinessConstants.DYNAFIELDS_PRODUCT_CBID_PREFIX + dynaId);
						allUserEntities.add(userEntity);
					}
				}
				
				String wrkDynaFieldIds = rs.getString("wrks_dyna_fields");
				String[] wrkDynaFieldIdsArr = wrkDynaFieldIds.split(BusinessConstants.DYNAFIELD_IDS_SEPERATOR);
				for (UserEntity userEntity : listWrkUserEntities){
					String dynaId = userEntity.getIdDynaFields();
					if (ArrayUtils.contains(wrkDynaFieldIdsArr, dynaId)){
						userEntity.setIdDynaFields(BusinessConstants.DYNAFIELDS_WORKER_CBID_PREFIX + dynaId);
						allUserEntities.add(userEntity);
					}
				}
				
				
				String dynaFieldIds = rs.getString("rpts_dyna_fields");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				
				consDynaFieldIds = consDynaFieldIds.substring(0,  consDynaFieldIds.length()-1);
			}
			else{
				logger.error("(fetchReportDynaDetails) Failed to retreive org records for Report details.");
				throw new DynaFieldNotFoundException("Failed to retreive org records for Report details.");
			}
			
			//Get the details of Dyna fields
			String sql = SQL_SELECT_DYNAFIELDS_RPT_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (fetchReportDynaDetails) Loading dyna records for Report Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					UserEntity userEntity = new UserEntity();
					
					populateDynaFieldsObj(rs, BusinessConstants.DYNAFIELDS_TABLE_ID_REPORT, userEntity);
					
					if (userEntity.getHtmlListValuesEn() != null && userEntity.getHtmlListValuesEn().length() > 0){
						String[] listValuesArr = userEntity.getHtmlListValuesEn().split(BusinessConstants.REG_EX_PIPE_SEPERATOR);
						List<String> listValuesList = Arrays.asList(listValuesArr);
						userEntity.setSelectedListValuesEn(listValuesList);
                    }

					if (userEntity.getHtmlListValuesEs() != null && userEntity.getHtmlListValuesEs().length() > 0){
						String[] listValuesArr = userEntity.getHtmlListValuesEs().split(BusinessConstants.REG_EX_PIPE_SEPERATOR);
						List<String> listValuesList = Arrays.asList(listValuesArr);
						userEntity.setSelectedListValuesEs(listValuesList);
                    }
					
					userEntity.setHtmlValidationsList(Arrays.asList(userEntity.getHtmlValidations().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEnList(Arrays.asList(userEntity.getHtmlValidationsMessagesEn().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					userEntity.setHtmlValidationsMessagesEsList(Arrays.asList(userEntity.getHtmlValidationsMessagesEs().split(BusinessConstants.REG_EX_MULTIPLE_VALUES_SEPERATOR)));
					
					listRptUserEntities.add(userEntity);
				}
				
				flag = true;
				
			}
			else{
				logger.error("(fetchReportDynaDetails) Failed to retreive dyna records for Report details.");
				throw new DynaFieldNotFoundException("Failed to retreive dyna records for Report details.");
			}
			
			for (UserEntity userEntity : listRptUserEntities){
				String dynaId = userEntity.getIdDynaFields();
				userEntity.setIdDynaFields(BusinessConstants.DYNAFIELDS_REPORTING_CBID_PREFIX + dynaId);
				allUserEntities.add(userEntity);
			}
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchReportDynaDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchReportDynaDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchReportDynaDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchReportDynaDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchReportDynaDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		
		return flag;
		
	}
	
	@Override
	public int saveReportDetails(String tokenKey, String reportData, String wrkName, String clientName, String coordinates, String location, String updateHis) throws DataAccessException {

		int reportId = -1;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet generatedKeys = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String[] tokenSplitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
			String key = tokenSplitted[1];
			int orgId = Integer.parseInt(tokenSplitted[2]);
			int wrkdId = Integer.parseInt(tokenSplitted[3]);
			
			String sql = SQL_INSERT_USER_ORGS_RPTS.replaceAll("P1", Integer.toString(orgId));
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, wrkName);
			ps.setString(2, reportData);
			ps.setString(3, clientName);
			ps.setString(4, coordinates);
			ps.setString(5, location);
			ps.setString(6, updateHis);
			
			logger.debug("(saveReportDetails) Saving the report data in system - Query : " + ps.toString());
			
			int rowsInserted = ps.executeUpdate();
			
			if (rowsInserted <= 0){
				throw new DataAccessException("saveReportDetails() -> Failed to insert record for report data in database.");
			}
			else{
				logger.debug("Report data details are saved successfully in database.");
				
				generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()){
					reportId = generatedKeys.getInt(1);
					logger.debug("Generated key : " + reportId);
				}
				
				con.commit();
			}
			
		}
		catch(SQLException sqlEx){
			logger.error("saveReportDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("saveReportDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("saveReportDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("saveReportDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("saveReportDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return reportId;
		
	}
	
	public boolean updateReportData(int orgId, int reportId, String reportData) throws DataAccessException {
		
		boolean isUpdated = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String sql = SQL_UPDATE_USER_ORGS_RPTS_DATA.replaceAll("P1", Integer.toString(orgId));
			ps = con.prepareStatement(sql);
			ps.setString(1, reportData);
			ps.setInt(2, reportId);
			
			logger.debug("(updateReportData) Updating the report data in system - Query : " + ps.toString());
			
			int rowUpdated = ps.executeUpdate();
			
			if (rowUpdated <= 0){
				throw new DataAccessException("updateReportData() -> Failed to update record for report data in database.");
			}
			else{
				logger.debug("Report data details are updated successfully in database.");
				
				isUpdated = true;
				
				con.commit();
			}			
			
		}
		catch(SQLException sqlEx){
			logger.error("updateReportData", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("updateReportData() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("updateReportData", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("updateReportData() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("updateReportData() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return isUpdated;
		
	}
	
	public String fetchEntitySingleDataFromId(int orgId, int entityId, int dynaId, String entityTable, String entityIdCol) throws DataAccessException {
		
		String entityData = "";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String sql = SQL_SELECT_USER_ORGS_FIND_ENTITY_NAME.replaceAll("P1", entityTable);
			sql = sql.replaceAll("P2", Integer.toString(orgId));
			sql = sql.replaceAll("P3", entityIdCol);
			ps = con.prepareStatement(sql);
			ps.setInt(1, entityId);
			
			rs = ps.executeQuery();
			
			logger.debug("[fetchEntitySingleDataFromId() Fetching the worker name - QUERY 1] : " + ps.toString());
			
			if (rs.next()){
				String fieldIdValues = rs.getString("field_id_values");
				String[] fieldIdValuesArr = fieldIdValues.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String fieldIdValue : fieldIdValuesArr){
					String[] splitted = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
					
					if (dynaId == Integer.parseInt(splitted[0])){
						entityData = splitted[1];
						break;
					}
				}
			}
			else{
				logger.debug("Entity Data NOT found in the records");
				throw new EntityDataNotFoundException("Entity Data NOT found in the records");
			}
			
		}
		catch (EntityDataNotFoundException ednfEx){
			throw ednfEx;
		}
		catch(SQLException sqlEx){
			logger.error("fetchEntitySingleDataFromId", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("fetchEntitySingleDataFromId() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("fetchWorkerNameFromId", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("fetchEntitySingleDataFromId() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("fetchWorkerNameFromId() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return entityData;
		
	}

	public void populateDynaFieldsObj(ResultSet rs, String id, UserEntity userEntity) throws SQLException{
        
		userEntity.setIdDynaFields(rs.getString(id));
		userEntity.setNameEn(rs.getString("name_en"));
		userEntity.setNameEs(rs.getString("name_es"));
		userEntity.setDescriptionEn(rs.getString("description_en"));
        userEntity.setDescriptionEs(rs.getString("description_es"));
        userEntity.setHtmlName(rs.getString("html_name"));
        userEntity.setHtmlType(rs.getString("html_type"));
        userEntity.setHtmlListValuesEn(rs.getString("html_list_values_en"));
        userEntity.setHtmlListValuesEs(rs.getString("html_list_values_es"));
        userEntity.setHtmlValidations(rs.getString("html_validations"));
        userEntity.setHtmlValidationsMessagesEn(rs.getString("html_validations_messages_en"));
        userEntity.setHtmlValidationsMessagesEs(rs.getString("html_validations_messages_es"));
        userEntity.setIcon(rs.getString("icon"));
        userEntity.setRecom(rs.getString("recom"));
        userEntity.setCreatedOn(rs.getString("created_on"));
        userEntity.setLastUpdated(rs.getString("last_updated"));
        
    }

}
