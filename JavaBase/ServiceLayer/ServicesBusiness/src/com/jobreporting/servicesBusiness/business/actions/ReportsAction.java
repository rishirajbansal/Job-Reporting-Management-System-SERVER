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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.configManager.PropertyManager;
import com.jobreporting.generic.exception.base.EExceptionTypes;
import com.jobreporting.generic.exception.base.ExceptionUtility;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSReportsRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSReportsResponse;
import com.jobreporting.servicesBusiness.business.base.AbstractBusinessAction;
import com.jobreporting.servicesBusiness.business.base.ERequestType;
import com.jobreporting.servicesBusiness.constants.BusinessConstants;
import com.jobreporting.servicesBusiness.constants.ExceptionConstants;
import com.jobreporting.servicesBusiness.exception.BusinessException;
import com.jobreporting.servicesBusiness.exception.BusinessValidationException;
import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.exception.DataPostingException;
import com.jobreporting.servicesBusiness.exception.DynaFieldNotFoundException;
import com.jobreporting.servicesBusiness.exception.EntityDataNotFoundException;
import com.jobreporting.servicesBusiness.exception.InvalidTokenKeyException;
import com.jobreporting.servicesBusiness.exception.OrgNotFoundException;
import com.jobreporting.servicesBusiness.exception.ReportPublishingException;
import com.jobreporting.servicesBusiness.exception.WorkerNotFoundException;
import com.jobreporting.servicesBusiness.integration.dataAccess.base.DAOFactory;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IReportDAO;
import com.jobreporting.servicesBusiness.utilities.Utilities;
import com.jobreporting.servicesBusiness.utilities.ValidateTokenAuthenticity;
import com.jobreporting.servicesBusiness.validations.IBusinessValidator;
import com.jobreporting.servicesBusiness.validations.ReportValidator;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class ReportsAction extends AbstractBusinessAction {
	
	public static LoggerManager logger = GenericUtility.getLogger(ReportsAction.class.getName());
	
	DAOFactory daoFactory = null;
	
	private static PropertyManager propertyManager 	= PropertyManager.getPropertyManager();
	
	public ReportsAction(){
		daoFactory= DAOFactory.getDAOFactory(DAOFactory.SQL);
	}
	

	@Override
	public WSBaseResponse execute(WSBaseRequest request, ERequestType requestType) throws Exception {
		
		WSReportsRequest wsReportsRequest = (WSReportsRequest)request;
		
		WSReportsResponse wsReportsResponse = new WSReportsResponse();
		
		
		try{
			switch(requestType){
			
				case REPORT_SUB_REQUEST:
					submitAndPublishReportData(wsReportsRequest, wsReportsResponse, requestType);
					break;
			
				default: 
					throw new BusinessException("Unsupported request type.");
			
			}
		}
		catch(BusinessValidationException bvEx){
			logger.debug("BusinessValidationException occurred due to validation failure in Report service : " + bvEx.getMessage());
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
		catch(DynaFieldNotFoundException dfnEx){
			logger.debug("DynaFieldNotFoundException occurred in DAO layer as certain dyna field not found in the records : " + dfnEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_DYNAFIELD_NOT_FOUND, ExceptionConstants.USERMESSAGE_DYNAFIELD_NOT_FOUND, dfnEx.getMessage(), EExceptionTypes.DYNAFIELDNOT_FOUND_EXCEPTION);
		}
		catch(WorkerNotFoundException wnfEx){
			logger.debug("WorkerNotFoundException occurred in DAO layer as certain dyna field not found in the records : " + wnfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_WORKERNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_2, ExceptionConstants.ERRORMESSAGE_WORKERNAME_NOT_FOUND_2, EExceptionTypes.WORKER_NOT_FOUND_EXCEPTION);
		}
		catch(EntityDataNotFoundException ednfEx){
			logger.debug("EntityDataNotFoundException occurred in DAO layer as entity data not found in the records : " + ednfEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_WORKERNAME_NOT_FOUND, ExceptionConstants.USERMESSAGE_WORKERNAME_NOT_FOUND_2, ExceptionConstants.ERRORMESSAGE_WORKERNAME_NOT_FOUND_2, EExceptionTypes.WORKER_NOT_FOUND_EXCEPTION);
		}
		catch(DataAccessException daEx){
			logger.error("DataAccessException occurred in DAO layer : " + daEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_DATA_ACCESS_EXCEPTION, ExceptionConstants.USERMESSAGE_DATA_ACCESS_EXCEPTION, daEx.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		catch(DataPostingException dpEx){
			logger.error("DataPostingException occurred while posting data to web server : " + dpEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_DATA_POSTING_EXCEPTION, ExceptionConstants.USERMESSAGE_DATA_POSTING_EXCEPTION, ExceptionConstants.ERRORMESSAGE_DATA_POSTING_EXCEPTION + " : " + dpEx.getMessage(), EExceptionTypes.DATA_POSTING_EXCEPTION);
		}
		catch(ReportPublishingException rpEx){
			logger.error("ReportPublishingException occurred while publishing report : " + rpEx.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_REPORT_PUBLISH_EXCEPTION, ExceptionConstants.USERMESSAGE_REPORT_PUBLISH_EXCEPTION, ExceptionConstants.ERRORMESSAGE_REPORT_PUBLISH_EXCEPTION + " : " + rpEx.getMessage(), EExceptionTypes.REPORT_PUBLISH_EXCEPTION);
		}
		catch(Exception ex){
			logger.error("Exception occurred : " + ex.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BUSINESS_EXCEPTION, ExceptionConstants.USERMESSAGE_BUSINESS_EXCEPTION, ex.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		catch(Throwable th){
			logger.error("Throwable occurred : " + th.getMessage());
			throw ExceptionUtility.createExceptionDetail(ExceptionConstants.CODE_BUSINESS_EXCEPTION, ExceptionConstants.USERMESSAGE_BUSINESS_EXCEPTION, th.getMessage(), EExceptionTypes.APPLICATION_EXCEPTION);
		}
		
		
		return wsReportsResponse;
		
	}
	
	public void submitAndPublishReportData(WSReportsRequest wsReportsRequest, WSReportsResponse wsReportsResponse, ERequestType requestType){
		
		IBusinessValidator validator = new ReportValidator();
		
		boolean isValid = validator.validate((WSBaseRequest)wsReportsRequest, requestType);
		
		if (isValid){
			IOrgDAO orgDao = daoFactory.getOrgDAO();
			
			//Validate Token key Authenticity
			String tokenKey = wsReportsRequest.getTokenKey();
			logger.debug("Validating token key authenticity for : " + tokenKey);
			boolean isTokenkeyValid = ValidateTokenAuthenticity.validateTokenKeyAuthenticity(tokenKey, orgDao);
			
			if (isTokenkeyValid){
				logger.debug("Token key is Authenticated successfully.");
				
				String[] tokenSplitted = tokenKey.split(BusinessConstants.REG_EX_TOKENKEY_VALUES_SEPERATOR);
				String key = tokenSplitted[1];
				int orgId = Integer.parseInt(tokenSplitted[2]);
				int wrkdId = Integer.parseInt(tokenSplitted[3]);
				
				logger.debug("Validating token data... ");
				boolean isValidTokenData = ValidateTokenAuthenticity.validateTokenKeyData(orgId, wrkdId, orgDao);
				
				if (isValidTokenData){
					
					logger.debug("Token key is Validated successfully.");
					
					/*Save Report Data Details*/
					IReportDAO reportDao = daoFactory.getReportDAO();
					
					String data = wsReportsRequest.getReportData();
					
					/* Set Worker details */
					int wrkDynaId = orgDao.findDynaIdFromFieldHtmlName(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_WORKER_NAME, BusinessConstants.DYNAFIELDS_TABLE_WORKER, BusinessConstants.DYNAFIELDS_TABLE_ID_WORKER);
					String wrkName = reportDao.fetchEntitySingleDataFromId(orgId, wrkdId, wrkDynaId, BusinessConstants.ENTITY_TABLE_WORKER, BusinessConstants.ENTITY_TABLE_WORKER_ID);
					
					/* Set Client details */
					int cstmrDynaId = orgDao.findDynaIdFromFieldHtmlName(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_CUSTOMER_NAME, BusinessConstants.DYNAFIELDS_TABLE_CUSTOMER, BusinessConstants.DYNAFIELDS_TABLE_ID_CUSTOMER);
					String clientName = getFieldDataFromReportData(data, BusinessConstants.DYNAFIELDS_CUSTOMER_CBID_PREFIX + cstmrDynaId);
					
					/* Set Location details */
					String coordinates = wsReportsRequest.getLatitude() + BusinessConstants.COORDNIATES_SEPERATOR + wsReportsRequest.getLongitude();
					String location = wsReportsRequest.getLocation();
					if (GenericUtility.safeTrim(coordinates).equals(BusinessConstants.COORDNIATES_SEPERATOR)){
						coordinates = "";
					}
					if (GenericUtility.safeTrim(location).equals(GenericConstants.EMPTY_STRING)){
						location = BusinessConstants.LOCATION_NO_LOCATION_FOUND;
					}
					/*else{
						if (GenericUtility.safeTrim(location).equals(BusinessConstants.LOCATION_GPS_NOT_ENABLED)){
							location = BusinessConstants.LOCATION_MSG_GPS_NOT_ENABLED;
						}
						else if (GenericUtility.safeTrim(location).equals(BusinessConstants.LOCATION_GPS_ENABLED)){
							location = BusinessConstants.LOCATION_MSG_GPS_ENABLED;
						}
						else if (GenericUtility.safeTrim(location).equals(BusinessConstants.LOCATION_GEOCODE_TIMEDOUT)){
							location = BusinessConstants.LOCATION_MSG_GEOCODE_TIMEDOUT;
						}
					}*/
					
					/* Set Update history details */
					String updateHis = Utilities.createDateStringFromPattern(BusinessConstants.DATEFORMAT_UPDATE_HIS) + BusinessConstants.UPDATE_HIS_SEPERATOR + wrkName;
					
					int reportId = reportDao.saveReportDetails(tokenKey, data, wrkName, clientName, coordinates, location, updateHis);
					
					if (reportId != -1){
						logger.debug("Report data details are saved successfully.");
						
						boolean isReportDataModified = false;
						
						/* Set Photo details */
						String photoName = wsReportsRequest.getPhotoName();
						byte[] photoBytesData = wsReportsRequest.getPhoto();
						if (null != photoBytesData){
							logger.debug("Photo data is present, will be send to web/file server");
							
							photoName = BusinessConstants.REPORT_DATA_IMAGE_TYPE_PHOTO_PREFIX + photoName;
							
							String photoPath = uploadImageData(orgId, reportId, BusinessConstants.REPORT_DATA_IMAGE_TYPE_PHOTO, photoBytesData, photoName);
							
							logger.debug("Photo uploaded at : " + photoPath);		
							
							//Update the data with the photo path
							int photoDynaId = orgDao.findDynaIdFromFieldHtmlName(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_REPORT_PHOTO, BusinessConstants.DYNAFIELDS_TABLE_REPORT, BusinessConstants.DYNAFIELDS_TABLE_ID_REPORT);
							data = data.replaceAll(BusinessConstants.DYNAFIELDS_REPORTING_CBID_PREFIX + photoDynaId + BusinessConstants.DYNA_CONTROL_VALUE_PLACEHOLDER, photoPath);
							
							isReportDataModified = true;
							
						}
						
						/* Set Signature details */
						byte[] signBytesData = wsReportsRequest.getSign();
						if (null != signBytesData){
							logger.debug("Sign data is present, will be send to web/file server");
							
							String signFileName = BusinessConstants.REPORT_DATA_IMAGE_TYPE_SIGN_PREFIX + BusinessConstants.SIGN_FILE_NAME;
							
							String signPath = uploadImageData(orgId, reportId, BusinessConstants.REPORT_DATA_IMAGE_TYPE_SIGN, signBytesData, signFileName);
							
							logger.debug("Signature uploaded at : " + signPath);		
							
							//Update the data with the sign path
							int signDynaId = orgDao.findDynaIdFromFieldHtmlName(BusinessConstants.DYNAFIELDS_FIELDID_HTMLNAME_REPORT_SIGN, BusinessConstants.DYNAFIELDS_TABLE_REPORT, BusinessConstants.DYNAFIELDS_TABLE_ID_REPORT);
							data = data.replaceAll(BusinessConstants.DYNAFIELDS_REPORTING_CBID_PREFIX + signDynaId + BusinessConstants.DYNA_CONTROL_VALUE_PLACEHOLDER, signPath);
							
							isReportDataModified = true;
							
						}
						
						/* Update the report record after the placeholders replacements */
						if (isReportDataModified){
							boolean dataUpdated = reportDao.updateReportData(orgId, reportId, data);
							if (dataUpdated){
								logger.debug("Report record is updated after data modification");
							}
							else{
								logger.debug("Failed to update the report record after data modification");
							}
						}
						
						wsReportsResponse.setReportId(BusinessConstants.REPORT_NAMING_PREFIX + Integer.toString(reportId));
						
						generateSuccessResponse(wsReportsResponse);
						
						//Publish Report Details
						//TODO:Move it to thread manager in next phase
						logger.debug("Publishing the report...");
						publishReportData(orgId, reportId);
						logger.debug("Report Published Successfully for id :" + reportId);
						
					}
					else{
						logger.error("Failed to save report data successfully.");
						throw new BusinessException("Failed to save report data successfully.");
					}
				}
				
			}
			
		}
		else{
			logger.debug("Report Sub Request validations failed.");
			throw new BusinessException("Report Sub Request validations failed.");
		}
		
	}
	
	public String getFieldDataFromReportData(String rptData, String dynaId){
		
		String fieldData = "";
		
		String[] dataSplit = rptData.split(BusinessConstants.REG_EX_FIELDID_VALUE_DATASET_SEPERATOR);
		
		for (String fieldIdValue : dataSplit){
			
			String[] fieldIdValueSplit = fieldIdValue.split(BusinessConstants.REG_EX_FIELDID_VALUE_SEPERATOR);
			if (GenericUtility.safeTrim(dynaId).equals(fieldIdValueSplit[0])){
				fieldData = fieldIdValueSplit[1];
				break;
			}
			
		}
		
		return fieldData;
		
	}
	
	public String uploadImageData(int orgId, int rptId, int type, byte[] imageData, String imageName){
		
		int responseCode = 0;
		StringBuffer response = new StringBuffer();
		
		String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        
        HttpURLConnection urlConnection = null;
		DataOutputStream dataOutputStream;
		
		try{	
			String webServerHost = propertyManager.getProperty(GenericConstants.COMMON_PROPERTIES_FILE_NAME, GenericConstants.WEB_SERVER_HOST);
			String webServerImageUploaderPath = propertyManager.getProperty(GenericConstants.COMMON_PROPERTIES_FILE_NAME, GenericConstants.WEB_SERVER_IMAGE_UPLOADER_PATH);
			
			String urlPath = webServerHost + webServerImageUploaderPath + "?orgId=" + orgId + "&rptId=" + rptId + "&type=" + type;
			
			logger.debug("Web server Image Uploader Path : " + urlPath);
			
			
            URL url = new URL(urlPath);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            urlConnection.setRequestProperty(BusinessConstants.REPORT_DATA_IMAGE_URI_NAME, imageName);            
            urlConnection.setDoOutput(true);
            
            dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + 
            							BusinessConstants.REPORT_DATA_IMAGE_URI_NAME + 
            							"\";filename=\"" + imageName + "\"" + lineEnd);
            dataOutputStream.writeBytes(lineEnd);
            
            dataOutputStream.write(imageData);
            
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            
            logger.info("Communicating with web server with POST... ");
            responseCode = urlConnection.getResponseCode();
    		logger.info("Response Code returned : " + responseCode);
    		
    		if (responseCode == HttpURLConnection.HTTP_OK){
    			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    			String inputLine;
    		 
    			while ((inputLine = in.readLine()) != null) {
    				response.append(inputLine);
    			}
    			in.close();
    			
    			logger.debug(response.toString());
    		}
    		else{
    			throw new DataPostingException("Response code is not 200/OK : " + responseCode);
    		}
		}
		catch (Exception ex){
			logger.error("Exception occured while sending image data : " + ex.getMessage());
			throw new DataPostingException("Exception occured while sending image data : " + ex.getMessage());
		}
		
		return response.toString();
		
	}
	
	public void publishReportData(int orgId, int reportId){
		
		int responseCode = 0;
		StringBuffer response = new StringBuffer();
        
        HttpURLConnection urlConnection = null;		
		
		try{
			String webServerHost = propertyManager.getProperty(GenericConstants.COMMON_PROPERTIES_FILE_NAME, GenericConstants.WEB_SERVER_HOST);
			String webServerReportPublisherPath = propertyManager.getProperty(GenericConstants.COMMON_PROPERTIES_FILE_NAME, GenericConstants.WEB_SERVER_REPORT_PUBLISHER_PATH);
			
			String urlPath = webServerHost + webServerReportPublisherPath + "?orgId=" + orgId + "&rptId=" + reportId;
			
			logger.debug("Web server Report Publisher Path : " + urlPath);
			
            URL url = new URL(urlPath);
            
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");

            logger.info("Communicating with web server with GET... ");
            responseCode = urlConnection.getResponseCode();
    		logger.info("Response Code returned : " + responseCode);
    		
    		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
		 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			logger.debug(response.toString());
    		
    		if (responseCode == HttpURLConnection.HTTP_OK){
    			logger.debug("Success response returned from web server.");
    		}
    		else{
    			String cause = "Response code is not 200/OK, response code : " + responseCode;
    			cause = cause + " " +response.toString();
    			
    			throw new ReportPublishingException(cause);
    		}
    		
		}
		catch (Exception ex){
			logger.error("Exception occured while sending report data for publishing: " + ex.getMessage());
			throw new ReportPublishingException("Exception occured while sending report data for publishing: " + ex.getMessage());
		}
		
	}
	
	public void generateSuccessResponse(WSReportsResponse wsReportsResponse){
		
		wsReportsResponse.setResponse(BusinessConstants.RESPONSE_SUCCESS);
		
	}
	

}
