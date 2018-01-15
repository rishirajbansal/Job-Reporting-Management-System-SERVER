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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.exception.ApplicationException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesController.exception.InitException;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * @author Rishi
 *
 */
public class ApplicationController extends ServletContainer{


	private static final long serialVersionUID = 1L;

	public static LoggerManager logger = GenericUtility.getLogger(ApplicationController.class.getName());
	
	public String propertiesFilePath = null;
	
	InitConfigurator initConfigurator = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		logger.info("Loading the Application...");
		
		super.init(config);
		
		try{
			propertiesFilePath = config.getInitParameter(GenericConstants.PROPERTIES_FILE_PATH);
			
			/*Call InitConfigurator to initialize prerequisites properties*/
			initConfigurator = new InitConfigurator(propertiesFilePath);
			
			boolean flag = initConfigurator.initialize();
			if (!flag){
				logger.error("Application Controller Failed to initialize prerequisites properties from InitConfigurator. System will be terminated.");
				throw new ApplicationException("Application Controller Failed to initialize prerequisites properties from InitConfigurator. System will be terminated.");
			}
			else{
				logger.info("Application Controller initialized prerequisites properties successfully from InitConfigurator.");
			}
		}
		catch(InitException iEx){
			logger.fatal("Problem occurred during initializing the application. Problem occurred in InitConfigurator." + iEx.getMessage());
			logger.fatal("System is terminated.");
			System.exit(1);
		}
		catch (ApplicationException aEx){
			logger.fatal("Problem occurred during initializing the application." + aEx.getMessage());
			logger.fatal("System is terminated.");
			System.exit(1);
		}
		catch(ContainerException cEx){
			logger.fatal("Problem occurred during initializing the application." + cEx.getMessage());
			logger.fatal("System is terminated.");
			System.exit(1);
		}
		catch(Exception ex){
			logger.fatal("Problem occurred during initializing the application." + ex.getMessage());
			logger.fatal("System is terminated.");
			System.exit(1);
		}
		catch(Throwable th){
			logger.fatal("Problem occurred during initializing the application." + th.getMessage());
			logger.fatal("System is terminated.");
			System.exit(1);
		}
		
		logger.info("Loading of Application is done.");
	}
	
	@Override
	public void destroy(){
		logger.info("In Destroy: Clearing the resources and terminating Business threads...");
		initConfigurator.terminate();
		logger.info("In Destroy: Clearing the resources and terminating Business threads is done.");
	}

}
