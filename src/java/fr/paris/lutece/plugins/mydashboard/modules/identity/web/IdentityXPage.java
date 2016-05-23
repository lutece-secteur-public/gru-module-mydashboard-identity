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

import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.IdentityService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * MyDashboardIdentity application
 */
@Controller( xpageName = "mydashboardIdentity", pageTitleI18nKey = "module.mydashboard.identity.xpage.getIdentity.pageTitle", pagePathI18nKey = "module.mydashboard.identity.xpage.getIdentity.pagePath" )
public class IdentityXPage extends MVCApplication
{
    private static final long serialVersionUID = 1L;
    private static final String VIEW_GET_GENERIC_VIEW_IDENTITY = "getGenericViewIdentity";
    private static final String VIEW_GET_GENERIC_MODIFY_IDENTITY = "getGenericModifyIdentity";
    private static final String ACTION_DO_GENERIC_MODIFY_IDENTITY = "doGenericModifyIdentity";
    private static final String VIEW_GET_VIEW_IDENTITY = "getViewIdentity";
    private static final String VIEW_GET_MODIFY_IDENTITY = "getModifyIdentity";
    private static final String ACTION_DO_MODIFY_IDENTITY = "doModifyIdentity";
    private static final String MESSAGE_ERROR_UPDATE_IDENTITY = "module.mydashboard.identity.message.error.identity.update";
    private static final String MESSAGE_INFO_IDENTITY_UPDATED = "module.mydashboard.identity.message.info.identity.updated";
    private static final String PARAMETER_BACK = "back";
    private static final String MARK_IDENTITY = "identity";
    private static final String MARK_VIEW_MODE = "viewMode";
    private static final String TEMPLATE_GET_VIEW_MODIFY_IDENTITY = "skin/plugins/mydashboard/modules/identity/edit_identity.html";
    private static final String TEMPLATE_GET_GENERIC_VIEW_IDENTITY = "skin/plugins/mydashboard/modules/identity/view_generic_identity.html";
    private static final String TEMPLATE_GET_GENERIC_MODIFY_IDENTITY = "skin/plugins/mydashboard/modules/identity/modify_generic_identity.html";
    private static final String IDENTITY_GENERIC_ATTRIBUTE_PREFIX = "attribute#";

    //session variable
    private DashboardIdentity _dashboardIdentity;

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

        Map<String, Object> model = getModel(  );
        IdentityDto identityDto = identityService.getIdentity( luteceUser );
        _dashboardIdentity = DashboardIdentityUtils.convertToDashboardIdentity( identityDto );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.TRUE );

        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale(  ), model );
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

        if ( ( _dashboardIdentity == null ) || ( _dashboardIdentity.getConnectionId(  ) == null ) ||
                !_dashboardIdentity.getConnectionId(  ).equals( luteceUser.getName(  ) ) )
        {
            IdentityDto identityDto = IdentityService.getService(  ).getIdentity( luteceUser );
            _dashboardIdentity = DashboardIdentityUtils.convertToDashboardIdentity( identityDto );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.FALSE );

        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale(  ), model );
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

        //fill dashboardIdentity from submitted form
        populate( _dashboardIdentity, request );

        IdentityDto identityDto = DashboardIdentityUtils.convertToIdentityDto( _dashboardIdentity );

        try
        {
            IdentityService.getService(  ).updateIdentity( identityDto );
        }
        catch ( AppException appEx )
        {
            addError( MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale(  ) );

            return redirectView( request, VIEW_GET_MODIFY_IDENTITY );
        }

        addInfo( MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale(  ) );

        return redirectView( request, VIEW_GET_VIEW_IDENTITY );
    }

    /**
     * Get the identity of the current user
     * @param request The request, with the user logged in
     * @return The XPage to display the identity of the user
     * @throws UserNotSignedException If the user is not logged in
     */
    @View( value = VIEW_GET_GENERIC_VIEW_IDENTITY )
    public XPage getGenericViewIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
            ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        Map<String, Object> model = getModel(  );
        IdentityDto identityDto = null;
        identityDto = IdentityService.getService(  ).getIdentity( luteceUser );
        model.put( MARK_IDENTITY, identityDto );
        request.getSession(  ).setAttribute( MARK_IDENTITY, identityDto );

        return getXPage( TEMPLATE_GET_GENERIC_VIEW_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Get the identity of the current user
     * @param request The request, with the user logged in
     * @return The XPage to display the identity of the user
     * @throws UserNotSignedException If the user is not logged in
     */
    @View( value = VIEW_GET_GENERIC_MODIFY_IDENTITY )
    public XPage getGenericModifyIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
            ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        Map<String, Object> model = getModel(  );
        IdentityDto identityDto = IdentityService.getService(  ).getIdentity( luteceUser );
        model.put( MARK_IDENTITY, identityDto );
        request.getSession(  ).setAttribute( MARK_IDENTITY, identityDto );

        return getXPage( TEMPLATE_GET_GENERIC_MODIFY_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Do the modification of the user identity
     * @param request The request
     * @return The next view to redirect to
     * @throws UserNotSignedException If the user has not signed in
     */
    @Action( ACTION_DO_GENERIC_MODIFY_IDENTITY )
    public XPage doGenericModifyIdentity( HttpServletRequest request )
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
            return redirectView( request, VIEW_GET_GENERIC_VIEW_IDENTITY );
        }

        IdentityService identityService = IdentityService.getService(  );

        Map<String, AttributeDto> mapAttributes = new HashMap<String, AttributeDto>(  );
        Set<String> setParameterKeys = request.getParameterMap(  ).entrySet(  );
        Iterator it = request.getParameterMap(  ).entrySet(  ).iterator(  );

        while ( it.hasNext(  ) )
        {
            Map.Entry entry = (Map.Entry) it.next(  );

            if ( ( (String) entry.getKey(  ) ).startsWith( IDENTITY_GENERIC_ATTRIBUTE_PREFIX ) )
            {
                AttributeDto attribute = new AttributeDto(  );
                attribute.setKey( ( (String) entry.getKey(  ) ).substring( IDENTITY_GENERIC_ATTRIBUTE_PREFIX.length(  ) ) );
                attribute.setValue( request.getParameter( (String) entry.getKey(  ) ) );
                mapAttributes.put( attribute.getKey(  ), attribute );
            }
        }

        IdentityDto identityDto = (IdentityDto) request.getSession(  ).getAttribute( MARK_IDENTITY );
        identityDto.setAttributes( mapAttributes );

        try
        {
            identityService.updateIdentity( identityDto );
        }
        catch ( AppException appEx )
        {
            addError( MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale(  ) );

            return redirectView( request, VIEW_GET_GENERIC_MODIFY_IDENTITY );
        }

        addInfo( MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale(  ) );

        return redirectView( request, VIEW_GET_GENERIC_VIEW_IDENTITY );
    }
}
