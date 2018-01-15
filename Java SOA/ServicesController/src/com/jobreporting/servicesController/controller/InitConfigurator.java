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

package com.jobreporting.servicesController.controller;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.configManager.Localization;
import com.jobreporting.generic.configManager.PropertyManager;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.exception.PropertyManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.business.base.ActionFactory;
import com.jobreporting.servicesBusiness.exception.ActionException;
import com.jobreporting.servicesController.exception.InitException;

/**
 * @author Rishi
 *
 */
public class InitConfigurator {
	
	public static LoggerManager logger = GenericUtility.getLogger(InitConfigurator.class.getName());
	
	public String propertiesFilePath = null;
	
	PropertyManager propertyManager = null;
	
	public InitConfigurator(String propertiesFilePath){
		this.propertiesFilePath = propertiesFilePath;
	}
	
	public boolean initialize() throws InitException{
		
		boolean flag = true;
		
		try{
			if (!GenericUtility.safeTrim(propertiesFilePath).equals(GenericConstants.EMPTY_STRING)){
				
				if (propertiesFilePath.lastIndexOf(GenericConstants.FILE_SEPARATOR) != propertiesFilePath.length() - 1){
					propertiesFilePath = propertiesFilePath + GenericConstants.FILE_SEPARATOR;
				}
				
				/*Load common properties file in memory */
				propertyManager = PropertyManager.createInstance(propertiesFilePath);
				if (propertyManager == null) {
					logger.error("InitConfigurator Failed to load the common properties file.");
					throw new InitException("InitConfigurator Failed to load the common properties file.");
				}
				else {
					logger.info("InitConfigurator loaded the common properties file successfully in memory.");
				}
				
				/*Load Localization */
				Localization localization = Localization.createInstance(propertiesFilePath);
				if (localization == null) {
					logger.error("InitConfigurator Failed to load the localization.");
					throw new InitException("InitConfigurator Failed to load the localization.");
				}
				else {
					logger.info("InitConfigurator loaded the localization successfully.");
				}
				
				/* Initialize Database's DataSource & Pool Manager */
				DatabaseConnectionManager.instantiate();
				boolean status = DatabaseConnectionManager.testDBConnection();
				if (status){
					logger.info("Database connections & pooling are configured successfully. Test Passed.");
				}
				else{
					logger.info("Database connections & pooling are FAILED to be configured successfully. Test FAILED.");
					throw new InitException("Database connections & pooling are FAILED to be configured successfully. Test FAILED.");
				}
				
				/* Load Action factory */
				ActionFactory.instantiate();
				logger.info("Action Factory loaded the Action Directory successfully.");
				
				/* Call Thread Manager to spawn business threads */
				/*try{
					logger.info("Calling Thread Manager to spawn business threads...");
					ThreadManager threadManager = ThreadManager.createInstance();
					threadManager.igniteThreads();
				}
				catch(ThreadManagerException tmEx){
					logger.error("ThreadManagerException occurred while spawning Business Threads : "  + tmEx.getMessage());
					throw new InitException("ThreadManagerException occurred while spawning Business Threads : "  + tmEx.getMessage());
				}
				logger.info("Thread Manager spawned the business threads successfully.");*/
				
			}
			else{
				throw new InitException("Properties file path is empty. Init Configurator failed.");
			}
			
		}
		catch(PropertyManagerException pmEx){
			logger.error("PropertyManagerException occurred in initializing the application : " + pmEx.getMessage());
			flag = false;
		}
		catch(DatabaseConnectionManagerException dcmEx){
			logger.error("DatabaseConnectionManagerException occurred in initializing the application : " + dcmEx.getMessage());
			flag = false;
		}
		catch(ActionException aEx){
			logger.error("ActionException occurred in initializing the application : " + aEx.getMessage());
			flag = false;
		}
		
		return flag;
		
	}
	
	public void terminate(){
		/*try{
			logger.info("Terminating Business Threads...");
			ThreadManager threadManager = ThreadManager.createInstance();
			threadManager.terminateThreadManager();
			logger.info("Business Threads terminated.");

		}
		catch(Exception ex){
			logger.error("Exception occurred while terminating Business threads : " + ex.getMessage());
			throw new ThreadManagerException("Exception occurred while terminating Business threads : " + ex.getMessage());
		}*/
	}

}
