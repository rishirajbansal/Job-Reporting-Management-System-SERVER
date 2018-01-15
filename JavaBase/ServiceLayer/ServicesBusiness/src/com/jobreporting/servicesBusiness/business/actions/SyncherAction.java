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
package com.jobreporting.servicesBusiness.business.actions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jobreporting.entities.WSUserEntity;
import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSSyncherRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSSyncherResponse;
import com.jobreporting.servicesBusiness.business.base.AbstractBusinessAction;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.InvalidTokenKeyException;
import com.jobreporting.servicesBusiness.exception.OrgNotFoundException;
import com.jobreporting.servicesBusiness.exception.WorkerNotFoundException;
import com.jobreporting.servicesBusiness.integration.dataAccess.base.DAOFactory;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IReportDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.UserEntity;
import com.jobreporting.servicesBusiness.utilities.ValidateTokenAuthenticity;
import com.jobreporting.servicesBusiness.validations.IBusinessValidator;
import com.jobreporting.servicesBusiness.validations.SyncherValidator;

/**
 * @author Rishi Raj
 *
 */
public class SyncherAction extends AbstractBusinessAction {
	
	public static LoggerManager logger = GenericUtility.getLogger(SyncherAction.class.getName());
	
	DAOFactory daoFactory = null;
	
	public SyncherAction(){
		daoFactory= DAOFactory.getDAOFactory(DAOFactory.SQL);
	}


	@Override
	public WSBaseResponse execute(WSBaseRequest request, ERequestType requestType) throws Exception {
		
		WSSyncherRequest wsSyncherRequest = (WSSyncherRequest)request;
		
		WSSyncherResponse wsSyncherResponse = new WSSyncherResponse();
		
		
		try{
			switch(requestType){
			
				case SYNCHER_REQUEST:
					sync(wsSyncherRequest, wsSyncherResponse, requestType);
					break;
			
				default: 
					throw new BusinessException("Unsupported request type.");
			
			}
		}
		catch(BusinessValidationException bvEx){
			logger.debug("BusinessValidationException occurred due to validation failure of Syncher service : " + bvEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(bvEx.getExceptionDetail(), EExceptionTypes.BUSINESS_EXCEPTION);
		}
		catch(InvalidTokenKeyException itkEx){
			logger.debug("InvalidTokenKeyException occurred in DAO layer as token key found invalid : " + itkEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_TOKENKEY_INVALID, ExceptionConstants.USERMESSAGE_TOKENKEY_INVALID, ExceptionConstants.ERRORMESSAGE_TOKENKEY_INVALID, EExceptionTypes.TOKENKEY_INVALID_EXCEPTION);
		}
		catch(OrgNotFoundException onfEx){
			logger.debug("OrgNotFoundException occurred in DAO layer as org name not found in the records : " + onfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_ORGNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_ORGNAME_NOT_FOUND_2, ExceptionConstants.ERRORMESSAGE_ORGNAME_NOT_FOUND_2, EExceptionTypes.ORG_NOT_FOUND_EXCEPTION);
		}
		catch(WorkerNotFoundException wnfEx){
			logger.debug("WorkerNotFoundException occurred in DAO layer as certain dyna field not found in the records : " + wnfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_WORKERNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_2, ExceptionConstants.ERRORMESSAGE_WORKERNAME_NOT_FOUND_2, EExceptionTypes.WORKER_NOT_FOUND_EXCEPTION);
		}
		catch(DataAccessException daEx){
			logger.error("DataAccessException occurred in DAO layer : " + daEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_DATA_ACCESS_EXCEPTION, ExceptionConstants.USERMESSAGE_DATA_ACCESS_EXCEPTION, daEx.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		catch(Exception ex){
			logger.error("Exception occurred : " + ex.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BUSINESS_EXCEPTION, ExceptionConstants.USERMESSAGE_BUSINESS_EXCEPTION, ex.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		catch(Throwable th){
			logger.error("Throwable occurred : " + th.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BUSINESS_EXCEPTION, ExceptionConstants.USERMESSAGE_BUSINESS_EXCEPTION, th.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		
		
		return wsSyncherResponse;
		
	}
	
	public void sync(WSSyncherRequest wsSyncherRequest, WSSyncherResponse wsSyncherResponse, ERequestType requestType){
		
		IBusinessValidator validator = new SyncherValidator();
		
		boolean isValid = validator.validate((WSBaseRequest)wsSyncherRequest, requestType);
		
		if (isValid){
			IOrgDAO orgDao = daoFactory.getOrgDAO();
			
			//Validate Token key Authenticity
			String tokenKey = wsSyncherRequest.getTokenKey();
			logger.debug("Validating token key authenticity for : " + tokenKey);
			boolean isTokenkeyValid = ValidateTokenAuthenticity.validateTokenKeyAuthenticity(tokenKey, orgDao);
			
			if (isTokenkeyValid){
				logger.debug("Token key is authenticated successfully.");
				
				String[] tokenSplitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
				String key = tokenSplitted[1];
				int orgId = Integer.parseInt(tokenSplitted[2]);
				int wrkdId = Integer.parseInt(tokenSplitted[3]);
				
				logger.debug("Validating token data... ");
				boolean isValidTokenData = ValidateTokenAuthenticity.validateTokenKeyData(orgId, wrkdId, orgDao);
				
				if (isValidTokenData){
					
					//Load Products Details
					Map<String, String> productsDetails = new HashMap<String, String>();
					int totalPrds = orgDao.loadProducts(orgId, productsDetails);

					//Load Customers Details
					Map<String, String> customersDetails = new HashMap<String, String>();
					int totalCstmrs = orgDao.loadCustomers(orgId, customersDetails);
					
					//Load Tasks Details
					Map<String, String> tasksDetails = new LinkedHashMap<String, String>();
					int totalTsks = orgDao.loadTasks(orgId, wrkdId, tasksDetails);
					
					//Load Report Structure Details
					IReportDAO reportDao = daoFactory.getReportDAO();
					List<UserEntity> allUserEntities = new ArrayList<UserEntity>();
					boolean flag = reportDao.fetchReportDynaDetails(orgId, allUserEntities);
					if (flag){
						logger.debug("Report structure details are loaded successfully.");
					}
					else{
						logger.error("Report structure details are FAILED to load.");
					}
					
					populateReportStructureDetailsResponse(wsSyncherResponse, allUserEntities);
					
					wsSyncherResponse.setTotalPrds(totalPrds);
					wsSyncherResponse.setProductsDetails(productsDetails);
					
					wsSyncherResponse.setTotalCstmrs(totalCstmrs);
					wsSyncherResponse.setCustomersDetails(customersDetails);
					
					wsSyncherResponse.setTotalTsks(totalTsks);
					wsSyncherResponse.setTasksDetails(tasksDetails);
					
				}
				
			}
			
		}
		else{
			logger.debug("Sync Request validations failed.");
			throw new BusinessException("Sync Request validations failed.");
		}
		
	}
	
	private void populateReportStructureDetailsResponse(WSSyncherResponse wsSyncherResponse, List<UserEntity> allUserEntities) {
		
		List<WSUserEntity> allWSUserEntites = new ArrayList<WSUserEntity>();
		
		for (UserEntity userEntity : allUserEntities){
			WSUserEntity wsUserEntity = new WSUserEntity();
			
			wsUserEntity.setIdDynaFields(userEntity.getIdDynaFields());
			wsUserEntity.setNameEn(userEntity.getNameEn());
			wsUserEntity.setNameEs(userEntity.getNameEs());
			wsUserEntity.setDescriptionEn(userEntity.getDescriptionEn());
			wsUserEntity.setDescriptionEs(userEntity.getDescriptionEs());
			wsUserEntity.setHtmlName(userEntity.getHtmlName());
			wsUserEntity.setHtmlType(userEntity.getHtmlType());
			wsUserEntity.setHtmlListValuesEn(userEntity.getHtmlListValuesEn());
			wsUserEntity.setHtmlListValuesEs(userEntity.getHtmlListValuesEs());
			wsUserEntity.setHtmlValidations(userEntity.getHtmlValidations());
			wsUserEntity.setHtmlValidationsMessagesEn(userEntity.getHtmlValidationsMessagesEn());
			wsUserEntity.setHtmlValidationsMessagesEs(userEntity.getHtmlValidationsMessagesEs());
			wsUserEntity.setIcon(userEntity.getIcon());
			wsUserEntity.setRecom(userEntity.getRecom());
			wsUserEntity.setCreatedOn(userEntity.getCreatedOn());
			wsUserEntity.setLastUpdated(userEntity.getLastUpdated());
			
			wsUserEntity.setSelectedListValuesEn(userEntity.getSelectedListValuesEn());
			wsUserEntity.setSelectedListValuesEs(userEntity.getSelectedListValuesEs());
			wsUserEntity.setHtmlValidationsList(userEntity.getHtmlValidationsList());
			wsUserEntity.setHtmlValidationsMessagesEnList(userEntity.getHtmlValidationsMessagesEnList());
			wsUserEntity.setHtmlValidationsMessagesEsList(userEntity.getHtmlValidationsMessagesEsList());
			
			allWSUserEntites.add(wsUserEntity);
			
		}
		
		wsSyncherResponse.setAllWSUserEntites(allWSUserEntites);
		
	}


}
