/*
 * Copyright (c) 2002-2024, City of Paris
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
package fr.paris.lutece.plugins.mydashboard.modules.identity.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.portal.service.security.ISecurityTokenService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * EditIdentity application
 */
@Controller( xpageName = "editIdentity", pageTitleI18nKey = "module.mydashboard.identity.xpage.getIdentity.pageTitle", pagePathI18nKey = "module.mydashboard.identity.xpage.getIdentity.pagePath" )
public class EditIdentityXPage extends MVCApplication
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final String VIEW_GET_EDIT_IDENTITY_INFORMATIONS       = "getEditIdentityInformations";
    private static final String VIEW_GET_EDIT_IDENTITY_COORDINATES        = "getEditIdentityCoordinates";
    
    private static final String ACTION_DO_MODIFY_IDENTITY_INFORMATIONS    = "doModifyIdentityInformations";
    private static final String ACTION_DO_MODIFY_IDENTITY_COORDINATES     = "doModifyIdentityCoordinates";
    
    private static final String TEMPLATE_GET_VIEW_MODIFY_IDENTITY         = "skin/plugins/mydashboard/modules/identity/modify_identity.html";
    
    private static final String MARK_PAGE_TITLE                           = "pageTitle";
    private static final String MARK_IDENTITY                             = "identity";
    private static final String MARK_EDIT_INFORMATIONS                    = "editInformations";
    private static final String MARK_EDIT_COORDINATES                     = "editCoordinates";
    private static final String MARK_ACTION_NAME                          = "actionName";
    private static final String MARK_TOKEN                                = "token";
    private static final String MARK_BUTTON_VALIDATE                      = "btnValidate";
    
    private DashboardIdentity   _dashboardIdentity;
    private ISecurityTokenService _securityTokenService = SecurityTokenService.getInstance( );
    
    /**
     * Get the edit identity of the informations user view
     *
     * @param request
     *            The request, with the user logged in
     * @return The XPage to edit the identity of the user
     * @throws UserNotSignedException
     *             If the user is not logged in
     */
    @View( value = VIEW_GET_EDIT_IDENTITY_INFORMATIONS, defaultView = true )
    public XPage getEditIdentityInformations( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }
        
        IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
        _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );
        
        Map<String, Object> model = getModel( );
        model.put( MARK_PAGE_TITLE, "Modifier vos informations d'identité" );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_EDIT_INFORMATIONS, true);
        model.put( MARK_ACTION_NAME, "jsp/site/Portal.jsp?page=editIdentity&action=doModifyIdentityInformations" );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY_INFORMATIONS ) );
        model.put( MARK_BUTTON_VALIDATE, "Modifier les informations" );
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }
    
    /**
     * Get the edit identity of the coordinates user view
     *
     * @param request
     *            The request, with the user logged in
     * @return The XPage to edit the identity of the user
     * @throws UserNotSignedException
     *             If the user is not logged in
     */
    @View( value = VIEW_GET_EDIT_IDENTITY_COORDINATES )
    public XPage getEditIdentityCoordinates( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }
        
        IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
        _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );
        
        Map<String, Object> model = getModel( );
        model.put( MARK_PAGE_TITLE, "Modifier vos coordonnées" );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_EDIT_COORDINATES, true);
        model.put( MARK_ACTION_NAME, "jsp/site/Portal.jsp?page=editIdentity&action=doModifyIdentityCoordinates" );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY_COORDINATES ) );
        model.put( MARK_BUTTON_VALIDATE, "Modifier les coordonnées" );
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }
}