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

import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IOrgDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.IReportDAO;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.OrgDAOImpl;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ReportDAOImpl;

/**
 * @author Rishi Raj Bansal
 * @since September 2016
 */
public class SQLDAOFactory extends DAOFactory {
	
	
	/**
     * Returns the Org DAO associated with the current DAOFactory.
     * @return the Org DAO associated with the current DAOFactory.
     */
	@Override
	public IOrgDAO getOrgDAO(){
		return new OrgDAOImpl();
	}
	
	/**
     * Returns the Report DAO associated with the current DAOFactory.
     * @return the Report DAO associated with the current DAOFactory.
     */
	@Override
	public IReportDAO getReportDAO() {
		return new ReportDAOImpl();
	}

		
	

}
