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

package com.jobreporting.servicesBusiness.business.base;

import java.util.HashMap;
import java.util.Map;

import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.exception.ActionException;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class ActionFactory {
	
	public static LoggerManager logger = GenericUtility.getLogger(ActionFactory.class.getName());
	
	private static ActionFactory actionFactory = null;
	
	private static Map<String, IBusinessAction> actionObjects = new HashMap<String, IBusinessAction>();
	
	/** Object used for locking. */
	private static Object lockObject = new Object();
	
	private ActionFactory(){
		loadActionDirectory();
	}
	
	public static void instantiate() throws ActionException{
		
		try{
			if (null == actionFactory){
				synchronized(lockObject){
					actionFactory = new ActionFactory();
				}
			}
		}
		catch(Exception ex){
			logger.error(" Exception occured in instantiating ActionFactory : " + ex.getMessage());
			throw new ActionException("Exception occured in in instantiating ActionFactory ", ex);
		}
		catch(Throwable th){
			logger.error(" Throwable occured in instantiating ActionDirectory : " + th.getMessage());
			throw new ActionException("Throwable occured in instantiating ActionFactory ", th);
		}
	}
	
	private void loadActionDirectory(){
		
		actionObjects.put(ActionDirectory.ACTION_ONETIMEAUTH, new com.jobreporting.servicesBusiness.business.actions.OneTimeAuthAction());
		actionObjects.put(ActionDirectory.ACTION_SYNCHER, new com.jobreporting.servicesBusiness.business.actions.SyncherAction());
		actionObjects.put(ActionDirectory.ACTION_REPORTS, new com.jobreporting.servicesBusiness.business.actions.ReportsAction());
		
	}
	
	public static synchronized IBusinessAction getActionInstance(String action){
		
		return actionObjects.get(action);
	}


}
