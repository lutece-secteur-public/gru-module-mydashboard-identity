package fr.paris.lutece.plugins.mydashboard.modules.identity.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.identitystore.v2.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.portal.service.util.AppException;

/**
 * The Interface IDashBoardIdentityService.
 */
public interface IDashBoardIdentityService {


	/**
	 * Gets the dash board identity
	 *
	 * @param strApplicationCode the str application code
	 * @return the dash board identity
	 * @throws AppException the app exception
	 */
	DashboardIdentity getDashBoardIdentity(String strApplicationCode) throws AppException;

	/**
	 * Gets the dash board identity.
	 *
	 * @param strApplicationCode the str application code
	 * @param strGuid the user guid
	 * @return the dash board identity
	 * @throws AppException the app exception
	 */
	DashboardIdentity getDashBoardIdentity(String strApplicationCode, String strGuid) throws AppException;

	/**
	 * Check fields format of dashboardIdentity.
	 *
	 * @param dashboardIdentity            dashboardIdentity to check
	 * @param request            the httpServletrequest 
	 * @param bOnlyCheckMandatory true if only mandatory file must be checked
	 * @return a Map of errors
	 */
	Map<String,String> checkDashboardIdentityFields(DashboardIdentity dashboardIdentity, HttpServletRequest request,
			boolean bOnlyCheckMandatory);

	/**
	 * Update identitity using dashboardinformations.
	 *
	 * @param dashboardIdentity the dashboard identity
	 * @param bUpdateOnlyManadtory Only Update mandatory Informations
	 * @throws AppException the app exception
	 */
	void updateDashboardIdentity(DashboardIdentity dashboardIdentity, boolean bUpdateOnlyManadtory) throws AppException;
	
	/**
	 * Return an IdentityDTO object wich contains only identity fields to update
	 * @param dashboardIdentity the dashboard Identity
	 * @param bUpdateOnlyManadtory only madantarory field must be updated
	 * @return  an IdentityDTO object wich contains only identity fields to update
	 */
      IdentityDto getIdentityToUpdate(DashboardIdentity dashboardIdentity,boolean bUpdateOnlyManadtory);
	
	/**
	 * Populate dashboard identity.
	 *
	 * @param dashboardIdentity the identity
	 * @param request the request
	 */
	void populateDashboardIdentity ( DashboardIdentity dashboardIdentity, HttpServletRequest request );

}