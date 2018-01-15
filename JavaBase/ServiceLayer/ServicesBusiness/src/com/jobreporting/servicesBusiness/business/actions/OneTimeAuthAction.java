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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.jobreporting.entities.WSDeviceAuth;
import com.jobreporting.entities.WSUserEntity;
import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSOneTimeAuthRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSOneTimeAuthResponse;
import com.jobreporting.servicesBusiness.business.base.AbstractBusinessAction;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.DynaFieldNotFoundException;
import com.jobreporting.servicesBusiness.exception.OrgNotFoundException;
import com.jobreporting.servicesBusiness.exception.WorkerNotFoundException;
import com.jobreporting.servicesBusiness.integration.dataAccess.base.DAOFactory;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IReportDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.DeviceAuthData;
import com.jobreporting.servicesBusiness.integration.dataAccess.dataObjects.UserEntity;
import com.jobreporting.servicesBusiness.validations.IBusinessValidator;
import com.jobreporting.servicesBusiness.validations.OneTimeAuthValidator;



/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class OneTimeAuthAction extends AbstractBusinessAction {
	
	public static LoggerManager logger = GenericUtility.getLogger(OneTimeAuthAction.class.getName());
	
	DAOFactory daoFactory = null;
	
	public OneTimeAuthAction(){
		daoFactory= DAOFactory.getDAOFactory(DAOFactory.SQL);
	}
	

	@Override
	public WSBaseResponse execute(WSBaseRequest request, ERequestType requestType) throws Exception {
		
		WSOneTimeAuthRequest wsOneTimeAuthRequest = (WSOneTimeAuthRequest)request;
		
		WSOneTimeAuthResponse wsOneTimeAuthResponse = new WSOneTimeAuthResponse();
		
		try{
			switch(requestType){
			
				case ONETIMEAUTH:
					oneTimeAuth(wsOneTimeAuthRequest, wsOneTimeAuthResponse, requestType);
					break;
			
				default: 
					throw new BusinessException("Unsupported request type.");
			
			}
		}
		catch(BusinessValidationException bvEx){
			logger.debug("BusinessValidationException occurred due to validation failure of One Time Auth service : " + bvEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(bvEx.getExceptionDetail(), EExceptionTypes.BUSINESS_EXCEPTION);
		}
		catch(OrgNotFoundException onfEx){
			logger.debug("OrgNotFoundException occurred in DAO layer as org name not found in the records : " + onfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_ORGNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_ORGNAME_NOT_FOUND_1, ExceptionConstants.ERRORMESSAGE_ORGNAME_NOT_FOUND_1, EExceptionTypes.ORG_NOT_FOUND_EXCEPTION);
		}
		catch(DynaFieldNotFoundException dfnEx){
			logger.debug("DynaFieldNotFoundException occurred in DAO layer as certain dyna field not found in the records : " + dfnEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_DYNAFIELD_NOT_FOUND, ExceptionConstants.USERMESSAGE_DYNAFIELD_NOT_FOUND, dfnEx.getMessage(), EExceptionTypes.DYNAFIELDNOT_FOUND_EXCEPTION);
		}
		catch(WorkerNotFoundException wnfEx){
			logger.debug("WorkerNotFoundException occurred in DAO layer as certain dyna field not found in the records : " + wnfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_WORKERNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_1, ExceptionConstants.ERRORMESSAGE_WORKERNAME_NOT_FOUND_1, EExceptionTypes.WORKER_NOT_FOUND_EXCEPTION);
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
		
		return wsOneTimeAuthResponse;
		
	}
	
	public void oneTimeAuth(WSOneTimeAuthRequest wsOneTimeAuthRequest, WSOneTimeAuthResponse wsOneTimeAuthResponse, ERequestType requestType){
		
		IBusinessValidator validator = new OneTimeAuthValidator();
		
		boolean isValid = validator.validate((WSBaseRequest)wsOneTimeAuthRequest, requestType);
		
		if (isValid){
			IOrgDAO orgDao = daoFactory.getOrgDAO();
			
			String orgAndWrkId = orgDao.verifyAuthDetails(wsOneTimeAuthRequest.getOrgName(), wsOneTimeAuthRequest.getWorkerName());
			
			if (!GenericUtility.safeTrim(orgAndWrkId).equals(GenericConstants.EMPTY_STRING)){
				logger.debug("One time authentication details are valid.");
				
				int orgId = Integer.parseInt((orgAndWrkId.split(BusinessConstants.REG_EX_PIPE_SEPERATOR))[0]);				
				WSDeviceAuth wsDeviceAuth = wsOneTimeAuthRequest.getDeviceAuth();
				if (null != wsDeviceAuth){
					logger.debug("Saving mobile device auth details");
					boolean flag = saveMobileDeviceAuthDetails(wsOneTimeAuthRequest, orgDao, orgAndWrkId);
					
					if (flag){
						logger.debug("Mobile Device Auth details are saved successfully.");
					}
					else{
						logger.error("Mobile Device Auth details are failed to update successfully.");
					}
				}
				
				String tokenKey = generateTokenKey(orgAndWrkId);
				//Save token key
				boolean saved = orgDao.saveTokenKey(tokenKey);
				if (saved){
					logger.debug("Token details are saved successfully.");
				}
				else{
					logger.error("Token details are failed to save successfully.");
				}
				
				//Load Products Details
				Map<String, String> productsDetails = new HashMap<String, String>();
				int totalPrds = orgDao.loadProducts(orgId, productsDetails);
				
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
				
				populateReportStructureDetailsResponse(wsOneTimeAuthResponse, allUserEntities);
				
				wsOneTimeAuthResponse.setTokenKey(tokenKey);
				wsOneTimeAuthResponse.setTotalPrds(totalPrds);
				wsOneTimeAuthResponse.setProductsDetails(productsDetails);
				
			}
			else{
				logger.debug("One time auth info is invalid.");
				throw new BusinessException("One time auth info is invalid.");
			}
			
		}
		else{
			logger.debug("One Time Auth validations failed.");
			throw new BusinessException("One Time Auth validations failed.");
		}
		
	}
	
	private boolean saveMobileDeviceAuthDetails(WSOneTimeAuthRequest wsOneTimeAuthRequest, IOrgDAO orgDao, String orgAndWrkId){
		
		WSDeviceAuth wsDeviceAuth = wsOneTimeAuthRequest.getDeviceAuth();
		
		int orgId = Integer.parseInt((orgAndWrkId.split(BusinessConstants.REG_EX_PIPE_SEPERATOR))[0]);
		int wrkId = Integer.parseInt((orgAndWrkId.split(BusinessConstants.REG_EX_PIPE_SEPERATOR))[1]);
		
		DeviceAuthData daoData = new DeviceAuthData();
		daoData.setDeviceId(wsDeviceAuth.getDeviceId());
		daoData.setAndroidId(wsDeviceAuth.getAndroidId());
		daoData.setOrgId(orgId);
		daoData.setWorkerId(wrkId);
		
		boolean flag = orgDao.saveMobileAuthDetails(daoData);
		
		return flag;
		
	}
	
	private void populateReportStructureDetailsResponse(WSOneTimeAuthResponse wsOneTimeAuthResponse, List<UserEntity> allUserEntities) {
		
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
		
		wsOneTimeAuthResponse.setAllWSUserEntites(allWSUserEntites);
		
	}
	
	private String generateTokenKey(String orgAndWrkId){
		
		String tokenKey = BusinessConstants.TOKENKEY_VALUES_PREFIX + BusinessConstants.TOKENKEY_VALUES_SEPERATOR + 
							RandomStringUtils.randomAlphanumeric(20) + BusinessConstants.TOKENKEY_VALUES_SEPERATOR + 
							(orgAndWrkId.split(BusinessConstants.REG_EX_PIPE_SEPERATOR))[0] + BusinessConstants.TOKENKEY_VALUES_SEPERATOR + (orgAndWrkId.split(BusinessConstants.REG_EX_PIPE_SEPERATOR))[1];
		
		return tokenKey;
	}
	
	public void generateSuccessResponse(WSOneTimeAuthResponse wsOneTimeAuthResponse){
		
		wsOneTimeAuthResponse.setResponse(BusinessConstants.RESPONSE_SUCCESS);
		
	}
	
	public void generateFalseResponse(WSOneTimeAuthResponse wsOneTimeAuthResponse){
		
		wsOneTimeAuthResponse.setResponse(BusinessConstants.RESPONSE_FALSE);
		
	}

}
