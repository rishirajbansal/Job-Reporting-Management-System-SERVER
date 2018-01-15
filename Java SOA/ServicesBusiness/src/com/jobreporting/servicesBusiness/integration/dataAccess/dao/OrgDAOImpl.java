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
import java.util.HashMap;
import java.util.Map;

import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.DynaFieldNotFoundException;
import com.jobreporting.servicesBusiness.exception.InvalidTokenKeyException;
import com.jobreporting.servicesBusiness.exception.OrgNotFoundException;
import com.jobreporting.servicesBusiness.exception.WorkerNotFoundException;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.DeviceAuthData;


/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class OrgDAOImpl implements IOrgDAO {
	
	public static LoggerManager logger = GenericUtility.getLogger(OrgDAOImpl.class.getName());
	
	
	private static final String SQL_SELECT_FIND_ORG = "SELECT * FROM orgs WHERE name = ?";
	
	private static final String SQL_SELECT_DYNA_WRKS_WORKER_NAME_FIELDID = "SELECT * FROM wrks_fields WHERE html_name = ?";
	
	private static final String SQL_SELECT_USER_ORGS_WRKS_FIND_WORKER = "SELECT * FROM org_wrks_P1 WHERE field_id_values LIKE '%P2%'";
	
	private static final String SQL_SELECT_ORGS_PRODUCTS = "SELECT * FROM orgs_prdts WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_CUSTOMERS = "SELECT * FROM orgs_cstmrs WHERE idorgs = ?";
	
	private static final String SQL_SELECT_ORGS_TASKS = "SELECT * FROM orgs_tsks WHERE idorgs = ?";
	
	private static final String SQL_SELECT_DYNAFIELDS_PRODUCTS_MAPPING = "SELECT * FROM prdts_fields WHERE idprdts_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_CUSTOMERS_MAPPING = "SELECT * FROM cstmrs_fields WHERE idcstmrs_fields IN (P1)";
	
	private static final String SQL_SELECT_DYNAFIELDS_TASKS_MAPPING = "SELECT * FROM tsks_fields WHERE idtsks_fields IN (P1)";
	
	private static final String SQL_SELECT_USER_ORGS_PRDS_ALL = "SELECT * FROM org_prds_P1";
	
	private static final String SQL_SELECT_USER_ORGS_CSTMRS_ALL = "SELECT * FROM org_cstmrs_P1";
	
	private static final String SQL_SELECT_USER_ORGS_TSKS_ALL = "SELECT * FROM org_tsks_P1 WHERE field_id_values LIKE '%P2%' order by created_on desc";
	
	private static final String SQL_SELECT_FETCH_WRK_NAME = "SELECT * FROM org_wrks_P1 WHERE idorg_wrks = ?";
	
	private static final String SQL_SELECT_MOBILE_DEVICE_AUTH = "SELECT * FROM wrks_mobile WHERE idorgs_wrks = ?";
	
	private static final String SQL_UPDATE_MOBILE_DEVICE_AUTH = "UPDATE wrks_mobile SET device_id = ?, android_id = ?, last_updated = NOW() WHERE idorgs_wrks = ?";
	
	private static final String SQL_INSERT_MOBILE_DEVICE_AUTH = "INSERT INTO wrks_mobile (idorgs, idorgs_wrks, device_id, android_id, created_on) VALUES (?, ?, ?, ?, NOW())";
	
	private static final String SQL_SELECT_TOKENKEY_IFEXISTS = "SELECT * FROM tokens WHERE token = ?";
	
	private static final String SQL_SELECT_TOKENKEY_DEATILS_EXISTS = "SELECT * FROM tokens WHERE idorgs = ? AND idorgs_wrks = ?";
	
	private static final String SQL_UPDATE_TOKENKEY = "UPDATE tokens SET token = ?, last_updated = NOW() WHERE idorgs = ? AND idorgs_wrks = ?";
	
	private static final String SQL_INSERT_TOKENKEY = "INSERT INTO tokens (token, idorgs, idorgs_wrks, created_on) VALUES (?, ?, ?, NOW())";
	
	private static final String SQL_SELECT_FIND_ORG_EXISTENCE = "SELECT * FROM orgs WHERE idorgs = ?";
	
	private static final String SQL_SELECT_FIND_WRK_EXISTENCE = "SELECT * FROM org_wrks_P1 WHERE idorg_wrks = ?";
	
	private static final String SQL_FIND_DYNAID_FROM_HTMLNAME = "SELECT * FROM P1 WHERE html_name = ?";
	
	


	public OrgDAOImpl() {
		
	}
	

	@Override
	public String verifyAuthDetails(String orgName, String workerName) throws DataAccessException {
		
		String orgAndWrkId = "";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_FIND_ORG);
			ps.setString(1, orgName);
			
			rs = ps.executeQuery();
			
			logger.debug("[verifyAuthDetails()-QUERY 1] : " + ps.toString());
			
			if (rs.next()){
				logger.debug("Org name found in the database.");
				
				int orgId = rs.getInt("idorgs");
				
				ps = con.prepareStatement(SQL_SELECT_DYNA_WRKS_WORKER_NAME_FIELDID);
				ps.setString(1, BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_WORKER_NAME);
				
				rs = ps.executeQuery();
				
				logger.debug("[verifyAuthDetails()-QUERY 1] : " + ps.toString());
				
				if (rs.next()){
					int dynaId = rs.getInt("idwrks_fields");
					
					String searchWorker = dynaId + BusinessConstants.FIELDID_VALUE_SEPERATOR + workerName;
					
					String sql = SQL_SELECT_USER_ORGS_WRKS_FIND_WORKER.replaceAll("P1", Integer.toString(orgId));
					sql = sql.replaceAll("P2", searchWorker);
					ps = con.prepareStatement(sql);
					
					rs = ps.executeQuery();
					
					logger.debug("[verifyAuthDetails()-QUERY 3] : " + ps.toString());
					
					if (rs.next()){
						logger.debug("Worker name found in the database.");
						
						int workerId = rs.getInt("idorg_wrks");
						orgAndWrkId = Integer.toString(orgId) + "|" + Integer.toString(workerId);

					}
					else{
						logger.debug("Worker name NOT found in the records.");
						throw new WorkerNotFoundException("Worker with this name do not exists, please check again.");
					}
				}
				else{
					logger.debug("Worker name dyna field record NOT found in the records.");
					throw new DynaFieldNotFoundException("Worker name dyna field record NOT found in the records.");
				}
			}
			else{
				logger.debug("Org name NOT found in the records.");
				throw new OrgNotFoundException("Organization with this name do not exists, please check again.");
			}
			
		}
		catch (WorkerNotFoundException wnfEx){
			throw wnfEx;
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch (OrgNotFoundException onfEx){
			throw onfEx;
		}
		catch(SQLException sqlEx){
			logger.error("verifyAuthDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("verifyAuthDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("verifyAuthDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("verifyAuthDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("verifyAuthDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return orgAndWrkId;
		
	}
	
	@Override
	public boolean saveMobileAuthDetails(DeviceAuthData daoData) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_MOBILE_DEVICE_AUTH);
			ps.setInt(1, daoData.getWorkerId());
			
			rs = ps.executeQuery();
			
			logger.debug("(saveMobileAuthDetails) Checking if record already exists for mobile auth - Query 1  : " + ps.toString());
			if (rs.next()){
				logger.debug("Record already exists for this worker, the existing record will be updated.");
				
				ps = con.prepareStatement(SQL_UPDATE_MOBILE_DEVICE_AUTH);
				ps.setString(1, daoData.getDeviceId());
				ps.setString(2, daoData.getAndroidId());
				ps.setInt(3, daoData.getWorkerId());
				
				logger.debug("(saveMobileAuthDetails) Updating existing record of mobile auth - Query 2  : " + ps.toString());
				
				int rowsUpdated = ps.executeUpdate();
				
				if (rowsUpdated <= 0){
					throw new DataAccessException("saveMobileAuthDetails() -> Failed to update record for mobile auth in database.");
				}
				else{
					logger.debug("Mobile Auth details are updated successfully in database.");
					
					flag = true;
					con.commit();
				}
			}
			else{
				logger.debug("Record NOT exists for this worker, new record will be created.");
				
				ps = con.prepareStatement(SQL_INSERT_MOBILE_DEVICE_AUTH);
				ps.setInt(1, daoData.getOrgId());
				ps.setInt(2, daoData.getWorkerId());
				ps.setString(3, daoData.getDeviceId());
				ps.setString(4, daoData.getAndroidId());
				
				logger.debug("(saveMobileAuthDetails) Inserting new record for mobile auth - Query 3  : " + ps.toString());
				
				int rowsInserted = ps.executeUpdate();
				
				if (rowsInserted <= 0){
					throw new DataAccessException("saveMobileAuthDetails() -> Failed to insert record for mobile auth in database.");
				}
				else{
					logger.debug("Mobile Auth details are saved successfully in database.");
					
					flag = true;
					con.commit();
				}
			}
			
		}
		catch(SQLException sqlEx){
			logger.error("saveMobileAuthDetails", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("saveMobileAuthDetails() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("saveMobileAuthDetails", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("saveMobileAuthDetails() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("saveMobileAuthDetails() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return flag;
	}
	
	@Override
	public int loadProducts(int orgId, Map<String, String> productsDetails) throws DataAccessException {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		int totalPrds = 0;
		int prdNameFieldId = 0;
		Map<Integer, String> fieldNamesPrdDetailsEn = new HashMap<Integer, String>(); 
		Map<Integer, String> fieldNamesPrdDetailsEs = new HashMap<Integer, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			//1. Get the configured products field details for this org
			ps = con.prepareStatement(SQL_SELECT_ORGS_PRODUCTS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadProducts) Loading org records for Products Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				consDynaFieldIds = consDynaFieldIds.substring(0, consDynaFieldIds.length()-1);
			}
			
			//2. Get the dyna fields details for the corresponding fields
			String sql = SQL_SELECT_DYNAFIELDS_PRODUCTS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadProducts) Loading dyna records for Products Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					fieldNamesPrdDetailsEn.put(rs.getInt("idprdts_fields"), rs.getString("name_en"));
					fieldNamesPrdDetailsEs.put(rs.getInt("idprdts_fields"), rs.getString("name_es"));
					
					//Get the id for Product name field
					if (GenericUtility.safeTrim(rs.getString("html_name")).equals(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_PRODUCT_NAME)){
						prdNameFieldId = rs.getInt("idprdts_fields");
					}
				}
			}
			
			//3. Get the Product details
			sql = SQL_SELECT_USER_ORGS_PRDS_ALL.replaceAll("P1", Integer.toString(orgId));
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadProducts) Loading records for Products Details - Query 3  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				String prdName = "";
				while (rs.next()){
					String fieldIdValues = rs.getString("field_id_values");
					String[] fieldIdValuesArr = fieldIdValues.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
					
					String fieldsDataEn = new String();
					String fieldsDataEs = new String();
					for (String fieldIdValue : fieldIdValuesArr){
						String[] splitted = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
						String fieldNameEn = fieldNamesPrdDetailsEn.get(Integer.parseInt(splitted[0]));
						String fieldNameEs = fieldNamesPrdDetailsEs.get(Integer.parseInt(splitted[0]));
						String fieldValue = "";
						if (splitted.length > 1){
							fieldValue = splitted[1];
						}
						
						fieldsDataEn = fieldsDataEn + fieldNameEn + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						fieldsDataEs = fieldsDataEs + fieldNameEs + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						
						if (prdNameFieldId == Integer.parseInt(splitted[0])){
							prdName = fieldValue;
						}
					}
					
					fieldsDataEn = fieldsDataEn.substring(0, fieldsDataEn.length()-1);
					fieldsDataEs = fieldsDataEs.substring(0, fieldsDataEs.length()-1);
					
					productsDetails.put(prdName, fieldsDataEn + BusinessConstants.DYNAFIELDS_LIST_VALUES_LOCALE_SEPERATOR + fieldsDataEs);
					++totalPrds;
					
				}
				
				logger.debug("[" + orgId + "] (loadProducts) List of Products are loaded successfully.");
			}
			else{
				logger.debug("[" + orgId + "] (loadProducts) No product exists in the system for this org, product list cannot be generated.");
			}
			
		}
		catch(SQLException sqlEx){
			logger.error("loadProducts", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("loadProducts() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("loadProducts", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("loadProducts() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("loadProducts() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return totalPrds;
		
	}
	
	@Override
	public int loadCustomers(int orgId, Map<String, String> customersDetails) throws DataAccessException {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		int totalCstmrs = 0;
		int cstmrNameFieldId = 0;
		Map<Integer, String> fieldNamesCstmrDetailsEn = new HashMap<Integer, String>(); 
		Map<Integer, String> fieldNamesCstmrDetailsEs = new HashMap<Integer, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			//1. Get the configured customers field details for this org
			ps = con.prepareStatement(SQL_SELECT_ORGS_CUSTOMERS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadCustomers) Loading org records for Customers Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				consDynaFieldIds = consDynaFieldIds.substring(0, consDynaFieldIds.length()-1);
			}
			
			//2. Get the dyna fields details for the corresponding fields
			String sql = SQL_SELECT_DYNAFIELDS_CUSTOMERS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadCustomers) Loading dyna records for Customers Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					fieldNamesCstmrDetailsEn.put(rs.getInt("idcstmrs_fields"), rs.getString("name_en"));
					fieldNamesCstmrDetailsEs.put(rs.getInt("idcstmrs_fields"), rs.getString("name_es"));
					
					//Get the id for Customer name field
					if (GenericUtility.safeTrim(rs.getString("html_name")).equals(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_CUSTOMER_NAME)){
						cstmrNameFieldId = rs.getInt("idcstmrs_fields");
					}
				}
			}
			
			//3. Get the Customer details
			sql = SQL_SELECT_USER_ORGS_CSTMRS_ALL.replaceAll("P1", Integer.toString(orgId));
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadCustomers) Loading records for Customers Details - Query 3  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				String cstmrName = "";
				while (rs.next()){
					String fieldIdValues = rs.getString("field_id_values");
					String[] fieldIdValuesArr = fieldIdValues.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
					
					String fieldsDataEn = new String();
					String fieldsDataEs = new String();
					for (String fieldIdValue : fieldIdValuesArr){
						String[] splitted = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
						String fieldNameEn = fieldNamesCstmrDetailsEn.get(Integer.parseInt(splitted[0]));
						String fieldNameEs = fieldNamesCstmrDetailsEs.get(Integer.parseInt(splitted[0]));
						String fieldValue = "";
						if (splitted.length > 1){
							fieldValue = splitted[1];
						}
						
						fieldsDataEn = fieldsDataEn + fieldNameEn + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						fieldsDataEs = fieldsDataEs + fieldNameEs + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						
						if (cstmrNameFieldId == Integer.parseInt(splitted[0])){
							cstmrName = fieldValue;
						}
					}
					
					fieldsDataEn = fieldsDataEn.substring(0, fieldsDataEn.length()-1);
					fieldsDataEs = fieldsDataEs.substring(0, fieldsDataEs.length()-1);
					
					customersDetails.put(cstmrName, fieldsDataEn + BusinessConstants.DYNAFIELDS_LIST_VALUES_LOCALE_SEPERATOR + fieldsDataEs);
					++totalCstmrs;
					
				}
				
				logger.debug("[" + orgId + "] (loadCustomers) List of Customers are loaded successfully.");
			}
			else{
				logger.debug("[" + orgId + "] (loadCustomers) No customer exists in the system for this org, customer list cannot be generated.");
			}
			
			
		}
		catch(SQLException sqlEx){
			logger.error("loadCustomers", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("loadCustomers() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("loadCustomers", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("loadCustomers() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("loadCustomers() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return totalCstmrs;
		
	}
	
	@Override
	public int loadTasks(int orgId, int wrkId, Map<String, String> tasksDetails) throws DataAccessException {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String consDynaFieldIds = "";
		int totalTsks = 0;
		int tskNameFieldId = 0;
		int tskAssignWrkrFieldId = 0;
		String wrkNameFilter = "";
		Map<Integer, String> fieldNamesTskDetailsEn = new HashMap<Integer, String>(); 
		Map<Integer, String> fieldNamesTskDetailsEs = new HashMap<Integer, String>();
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			//1. Get the configured tasks field details for this org
			ps = con.prepareStatement(SQL_SELECT_ORGS_TASKS);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadTasks) Loading org records for Tasks Details - Query 1  : " + ps.toString());
			
			if (rs.next()){
				String dynaFieldIds = rs.getString("dyna_fields_ids");
				String[] dynaFieldIdsArr = dynaFieldIds.split(BusinessConstants.REG_EX_DYNAFIELD_IDS_SEPERATOR);
				
				for (String dynaFieldId : dynaFieldIdsArr){
					consDynaFieldIds = consDynaFieldIds + dynaFieldId + ",";
				}
				consDynaFieldIds = consDynaFieldIds.substring(0, consDynaFieldIds.length()-1);
			}
			
			//2. Get the dyna fields details for the corresponding fields
			String sql = SQL_SELECT_DYNAFIELDS_TASKS_MAPPING.replaceAll("P1", consDynaFieldIds);
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadTasks) Loading dyna records for Tasks Details - Query 2  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				while (rs.next()){
					fieldNamesTskDetailsEn.put(rs.getInt("idtsks_fields"), rs.getString("name_en"));
					fieldNamesTskDetailsEs.put(rs.getInt("idtsks_fields"), rs.getString("name_es"));
					
					//Get the id for Task name field
					if (GenericUtility.safeTrim(rs.getString("html_name")).equals(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_TASK_NAME)){
						tskNameFieldId = rs.getInt("idtsks_fields");
					}
					
					//Get the id for Assigned Worker field
					if (GenericUtility.safeTrim(rs.getString("html_name")).equals(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_ASSIGNED_WORKER)){
						tskAssignWrkrFieldId = rs.getInt("idtsks_fields");
					}
				}
			}
			
			//3. Get the worker name
			int wrkDynaId = findDynaIdFromFieldHtmlName(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_WORKER_NAME, BusinessConstants.DYNAFIELDS_TABLE_WORKER, BusinessConstants.DYNAFIELDS_TABLE_ID_WORKER);
			
			sql = SQL_SELECT_FETCH_WRK_NAME.replaceAll("P1", Integer.toString(orgId));
			ps = con.prepareStatement(sql);
			ps.setInt(1, wrkId);
			
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadTasks) Loading records for Tasks Details - Query 3  : " + ps.toString());
			
			if (rs.next()){
				
				String fieldIdValues = rs.getString("field_id_values");
				String[] fieldIdValuesArr = fieldIdValues.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
				
				for (String fieldIdValue : fieldIdValuesArr){
					String[] splitted = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
					int fieldId = Integer.parseInt(splitted[0]);
					if (wrkDynaId == fieldId){
						String wrkName = splitted[1];

						wrkNameFilter = tskAssignWrkrFieldId + BusinessConstants.FIELDID_VALUE_SEPERATOR + wrkName;
						break;
					}
				}
				
			}
			
			//4. Get the Task details
			sql = SQL_SELECT_USER_ORGS_TSKS_ALL.replaceAll("P1", Integer.toString(orgId));
			sql = sql.replaceAll("P2", wrkNameFilter);
			ps = con.prepareStatement(sql);
				
			rs = ps.executeQuery();
			
			logger.debug("[" + orgId + "] (loadTasks) Loading records for Tasks Details - Query 4  : " + ps.toString());
			
			if (rs.next()){
				rs.previous();
				
				String tskName = "";
				while (rs.next()){
					String fieldIdValues = rs.getString("field_id_values");
					String[] fieldIdValuesArr = fieldIdValues.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
					
					String fieldsDataEn = new String();
					String fieldsDataEs = new String();
					for (String fieldIdValue : fieldIdValuesArr){
						String[] splitted = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
						String fieldNameEn = fieldNamesTskDetailsEn.get(Integer.parseInt(splitted[0]));
						String fieldNameEs = fieldNamesTskDetailsEs.get(Integer.parseInt(splitted[0]));
						String fieldValue = "";
						if (splitted.length > 1){
							fieldValue = splitted[1];
						}
						
						fieldsDataEn = fieldsDataEn + fieldNameEn + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						fieldsDataEs = fieldsDataEs + fieldNameEs + BusinessConstants.FIELDID_VALUE_SEPERATOR + fieldValue + BusinessConstants.FIELDID_VALUE_DATASET_SEPERATOR;
						
						if (tskNameFieldId == Integer.parseInt(splitted[0])){
							tskName = fieldValue;
						}
					}
					
					fieldsDataEn = fieldsDataEn.substring(0, fieldsDataEn.length()-1);
					fieldsDataEs = fieldsDataEs.substring(0, fieldsDataEs.length()-1);
					
					tasksDetails.put(tskName, fieldsDataEn + BusinessConstants.DYNAFIELDS_LIST_VALUES_LOCALE_SEPERATOR + fieldsDataEs);
					++totalTsks;
					
				}
				
				logger.debug("[" + orgId + "] (loadTasks) List of Tasks are loaded successfully.");
			}
			else{
				logger.debug("[" + orgId + "] (loadTasks) No task exists in the system for this org, task list cannot be generated.");
			}
			
		}
		catch(SQLException sqlEx){
			logger.error("loadTasks", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("loadTasks() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("loadTasks", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("loadTasks() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("loadTasks() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return totalTsks;
		
	}
	
	public boolean saveTokenKey(String tokenKey) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String[] tokenSplitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
			String key = tokenSplitted[1];
			int orgId = Integer.parseInt(tokenSplitted[2]);
			int wrkdId = Integer.parseInt(tokenSplitted[3]);
			
			ps = con.prepareStatement(SQL_SELECT_TOKENKEY_DEATILS_EXISTS);
			ps.setInt(1, orgId);
			ps.setInt(2, wrkdId);
			
			rs = ps.executeQuery();
			
			logger.debug("(saveTokenKey) Checking if record already exists for token key - Query 1  : " + ps.toString());
			if (rs.next()){
				logger.debug("Record already exists for this worker's token key, the existing record will be updated.");
				
				ps = con.prepareStatement(SQL_UPDATE_TOKENKEY);
				ps.setString(1, key);
				ps.setInt(2, orgId);
				ps.setInt(3, wrkdId);
				
				logger.debug("(saveTokenKey) Updating existing record of token key - Query 2  : " + ps.toString());
				
				int rowsUpdated = ps.executeUpdate();
				
				if (rowsUpdated <= 0){
					throw new DataAccessException("saveTokenKey() -> Failed to update record for token key in database.");
				}
				else{
					logger.debug("Token key details are updated successfully in database.");
					
					flag = true;
					con.commit();
				}
			}
			else{
				logger.debug("Token key Record NOT exists for this worker, new record will be created.");
				
				ps = con.prepareStatement(SQL_INSERT_TOKENKEY);
				ps.setString(1, key);
				ps.setInt(2, orgId);
				ps.setInt(3, wrkdId);
				
				logger.debug("(saveTokenKey) Inserting new record for token key - Query 3  : " + ps.toString());
				
				int rowsInserted = ps.executeUpdate();
				
				if (rowsInserted <= 0){
					throw new DataAccessException("saveTokenKey() -> Failed to insert record for token key in database.");
				}
				else{
					logger.debug("Token key details are saved successfully in database.");
					
					flag = true;
					con.commit();
				}
			}
			
		}
		catch(SQLException sqlEx){
			logger.error("saveTokenKey", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("saveTokenKey() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("saveTokenKey", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("saveTokenKey() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("saveTokenKey() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return flag;
		
	}

	public boolean validateTokenAuthenticity(String tokenKey) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String[] tokenSplitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
			String key = tokenSplitted[1];
			int orgId = Integer.parseInt(tokenSplitted[2]);
			int wrkdId = Integer.parseInt(tokenSplitted[3]);
			
			ps = con.prepareStatement(SQL_SELECT_TOKENKEY_IFEXISTS);
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			
			logger.debug("(validateTokenAuthenticity) Checking if token key exists - Query 1  : " + ps.toString());
			if (rs.next()){
				logger.debug("Record found for token key existence and is valid.");
				flag = true;
			}
			else{
				logger.debug("Token key NOT found in the records.");
				throw new InvalidTokenKeyException("Token key is not valid.");
			}
			
		}
		catch (InvalidTokenKeyException itkEx){
			throw itkEx;
		}
		catch(SQLException sqlEx){
			logger.error("validateTokenAuthenticity", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("validateTokenAuthenticity() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("validateTokenAuthenticity", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("validateTokenAuthenticity() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("validateTokenAuthenticity() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return flag;
		
	}
	
	/**
	 * On receiving the subsequent calls from the client after one time auth, it is required to verify if the org and worker has 
	 * not been removed from the system by using the 'Delete Org or Delete Worker' feature from the web interface 
	 */
	public boolean validateTokenData(int orgId, int wrkId) throws DataAccessException {
		
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			ps = con.prepareStatement(SQL_SELECT_FIND_ORG_EXISTENCE);
			ps.setInt(1, orgId);
			
			rs = ps.executeQuery();
			
			logger.debug("(validateTokenData) Checking if Org exists - Query 1  : " + ps.toString());
			if (rs.next()){
				logger.debug("Record found for organization existence.");
				
				String sql = SQL_SELECT_FIND_WRK_EXISTENCE.replaceAll("P1", Integer.toString(orgId));
				ps = con.prepareStatement(sql);
				ps.setInt(1, wrkId);
				
				rs = ps.executeQuery();
				
				logger.debug("(validateTokenData) Checking if Worker exists - Query 2  : " + ps.toString());
				if (rs.next()){
					
					flag = true;
				}
				else{
					logger.debug("Worker NOT found in the records for the id : " + wrkId);
					throw new WorkerNotFoundException("Worker details not found for this request in the system, it is possible that it has been DELETED from the web interface.");
				}
			}
			else{
				logger.debug("Org not found for the id : " + orgId);
				throw new OrgNotFoundException("Organization details not found for this request in the system, it is possible that it has been DELETED from the web interface.");
			}
		}
		catch (OrgNotFoundException onfEx){
			throw onfEx;
		}
		catch (WorkerNotFoundException wnfEx){
			throw wnfEx;
		}
		catch(SQLException sqlEx){
			logger.error("validateTokenData", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("validateTokenData() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("validateTokenData", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("validateTokenData() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("validateTokenData() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return flag;
		
	}
	
	public int findDynaIdFromFieldHtmlName(String dynaHtmlName, String dynaTable, String dynaIdCol) throws DataAccessException {
		
		int dynaId = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = DatabaseConnectionManager.getConnection();
			
			String sql = SQL_FIND_DYNAID_FROM_HTMLNAME.replaceAll("P1", dynaTable);
			ps = con.prepareStatement(sql);
			ps.setString(1, dynaHtmlName);
			
			rs = ps.executeQuery();
			
			logger.debug("[findDynaIdFromFieldHtmlName() Retreiving the dyna field id - QUERY] : " + ps.toString());
			if (rs.next()){
				dynaId = rs.getInt(dynaIdCol);
				
			}
			else{
				logger.debug("Dyna field record NOT found in the records.");
				throw new DynaFieldNotFoundException("Dyna field record NOT found in the records.");
			}
			
		}
		catch (DynaFieldNotFoundException dfnEx){
			throw dfnEx;
		}
		catch(SQLException sqlEx){
			logger.error("validateTokenData", "SQLException occurred in DAO layer : " + sqlEx.getMessage());
			throw new DataAccessException("validateTokenData() -> SQLException occurred in DAO layer : " + sqlEx.getMessage());
		}
		catch(Exception ex){
			logger.error("validateTokenData", "Exception occurred in DAO layer : " + ex.getMessage());
			throw new DataAccessException("validateTokenData() -> Exception occurred in DAO layer : " + ex.getMessage());
		}
		finally{
			try {
				DatabaseConnectionManager.returnConnection(con);
				DatabaseConnectionManager.clearResources(ps, rs);
			} 
			catch (DatabaseConnectionManagerException dcmEx) {
				throw new DataAccessException("validateTokenData() -> DatabaseConnectionManagerException occured during closing resources ", dcmEx);
			}
		}
		
		return dynaId;
		
	}
	

}
