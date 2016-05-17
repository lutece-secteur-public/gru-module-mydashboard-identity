/*
 * Copyright (c) 2002-2016, Mairie de Paris
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.mydashboard.modules.identity.business.Identity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.IdentityService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;


/**
 * MyDashboardIdentity application
 */
@Controller( xpageName = "mydashboard", pageTitleI18nKey = "mydashboard.getIdentity.pageTitle", pagePathI18nKey = "mydashboard.getIdentity.pagePath" )
public class IdentityXPage extends MVCApplication
{
    private static final long serialVersionUID = 1L;
    private static final String VIEW_GET_VIEW_IDENTITY = "getViewIdentity";
    private static final String VIEW_GET_MODIFY_IDENTITY = "getModifyIdentity";
//    private static final String VIEW_GET_DASHBOARDS = "getDashboards";
    private static final String ACTION_DO_MODIFY_IDENTITY = "doModifyIdentity";

    private static final String MARK_LOCALE = "locale";
    private static final String PARAMETER_BACK = "back";
//    private static final String PARAMETER_PANEL= "panel";
//    private static final String PARAMETER_SUFFIX_DISPLAY = "_display";
//    private static final String PARAMETER_MOVE_UP = "moveUp";
//    private static final String PARAMETER_DASHBOARD_COMPONENT_ID = "myDashboardComponentId";
//    private static final String JSP_URL_PORTAL = "jsp/site/Portal.jsp";
//    private static final String TEMPLATE_GET_DASHBOARDS = "skin/plugins/mydashboard/get_dashboards.html";
    private static final String TEMPLATE_GET_VIEW_IDENTITY = "skin/plugins/mydashboard/modules/identity/view_identity.html";
    private static final String TEMPLATE_GET_MODIFY_IDENTITY = "skin/plugins/mydashboard/modules/identity/modify_identity.html";

    /**
     * Get the identity of the current user
     * @param request The request, with the user logged in
     * @return The XPage to display the identity of the user
     * @throws UserNotSignedException If the user is not logged in
     */
    @View( value = VIEW_GET_VIEW_IDENTITY, defaultView = true )
    public XPage getViewIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
            ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        IdentityService identityService = IdentityService.getService(  );

        Map<String, Object> model = new HashMap<String, Object>(  );
        Identity identity;
        identity = identityService.getIdentity( luteceUser );
        model.put( Constants.MARK_IDENTITY, identity );

        return getXPage( TEMPLATE_GET_VIEW_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Get the XPage to modify the identity of the current user
     * @param request The request
     * @return The XPage to display
     * @throws UserNotSignedException If the user has not signed in
     */
    @View( VIEW_GET_MODIFY_IDENTITY )
    public XPage getModifyIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
                ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        IdentityService identityService = IdentityService.getService(  );

        Map<String, Object> model = new HashMap<String, Object>(  );
        Identity identity;
        identity = identityService.getIdentity( luteceUser );
        model.put( Constants.MARK_IDENTITY, identity );

        return getXPage( TEMPLATE_GET_MODIFY_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Do the modification of the user identity
     * @param request The request
     * @return The next view to redirect to
     * @throws UserNotSignedException If the user has not signed in
     */
    @Action( ACTION_DO_MODIFY_IDENTITY )
    public XPage doModifyIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
            ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        if ( request.getParameter( PARAMETER_BACK ) != null )
        {
            return redirectView( request, VIEW_GET_VIEW_IDENTITY );
        }

        IdentityService identityService = IdentityService.getService(  );

        Identity identity = null;
        //TODO populate identity
//        populate( identity, request );
        identityService.updateAttributes( identity );

        return redirectView( request, VIEW_GET_VIEW_IDENTITY );
    }

}
