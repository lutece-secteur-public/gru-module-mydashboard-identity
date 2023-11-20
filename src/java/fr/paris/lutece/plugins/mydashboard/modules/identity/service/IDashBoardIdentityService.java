/*
 * Copyright (c) 2002-2023, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mydashboard.modules.identity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.DuplicateSearchResponse;
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

    /**
     * Search suspicious identities
     * @param dashboardIdentity
     * @param listRules
     * @return suspicious identities
     */
     DuplicateSearchResponse getSuspiciousIdentities( DashboardIdentity dashboardIdentity, List<String> listRules );
     
     /**
      * Return true if exist suspicious identities
      * @param dashboardIdentity
      * @param listRules
      * @return true if exist suspicious identities
      */
     boolean existSuspiciousIdentities ( DashboardIdentity dashboardIdentity, List<String> listRules );

	/**
	 * {@inheritDoc}
	 */
	boolean needCertificationFC(String strApplicationCode, String strGuid, DashboardIdentity dashboardIdentity)
			throws AppException;
}