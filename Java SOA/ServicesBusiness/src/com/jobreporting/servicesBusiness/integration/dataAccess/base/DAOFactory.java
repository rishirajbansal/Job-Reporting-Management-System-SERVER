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

package com.jobreporting.servicesBusiness.integration.dataAccess.base;

import com.jobreporting.servicesBusiness.exception.DataAccessException;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IReportDAO;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public abstract class DAOFactory {
	
	public static final int SQL = 1;
	
	public static DAOFactory getDAOFactory(int factory) {
		
		switch(factory) {
			case SQL:
				return new SQLDAOFactory();
			
			default:
				throw new DataAccessException("Only SQL Factories are supported"); 
		}
		
	}
	
	public abstract IOrgDAO getOrgDAO();
	
	public abstract IReportDAO getReportDAO();


}
