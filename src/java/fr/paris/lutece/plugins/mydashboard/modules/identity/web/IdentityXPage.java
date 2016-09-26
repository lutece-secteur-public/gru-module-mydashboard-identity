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

import fr.paris.lutece.plugins.identitystore.web.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.AuthorDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityChangeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.web.service.AuthorType;
import fr.paris.lutece.plugins.identitystore.web.service.IdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 * MyDashboardIdentity application
 */
@Controller( xpageName = "mydashboardIdentity", pageTitleI18nKey = "module.mydashboard.identity.xpage.getIdentity.pageTitle", pagePathI18nKey = "module.mydashboard.identity.xpage.getIdentity.pagePath" )
public class IdentityXPage extends MVCApplication
{
    private static final long serialVersionUID = 1L;
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
    private static final String DASHBOARD_APP_CODE = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE );
    private static final String DASHBOARD_APP_NAME = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_NAME );
    private static final String DASHBOARD_APP_HASH = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_HASH );

    private static final String MARK_GENDER_LIST = "genderlist";
    private static final String MARK_CONTACT_MODE_LIST = "contact_modeList";
        
    // session variable
    private DashboardIdentity _dashboardIdentity;
    private static final String BEAN_IDENTITYSTORE_SERVICE = "mydashboard-identity.identitystore.service";
    
    private IdentityService _identityService;
    
    public IdentityXPage()
	{
		super ();
        _identityService = SpringContextService.getBean( BEAN_IDENTITYSTORE_SERVICE );   	
	}

    /**
     * Get the identity of the current user
     *
     * @param request
     *          The request, with the user logged in
     * @return The XPage to display the identity of the user
     * @throws UserNotSignedException
     *           If the user is not logged in
     */
    @View( value = VIEW_GET_VIEW_IDENTITY, defaultView = true )
    public XPage getViewIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = getConnectedUser( request );

		ReferenceList genderList = new ReferenceList();		
		for (String sItem : Constants.PROPERTY_KEY_GENDER_LIST.split( ";" ) ){
			ReferenceItem refItm = new ReferenceItem();
			refItm.setName(sItem);
			refItm.setCode(sItem);
			genderList.add( refItm );
		}
        
		ReferenceList contactModeList = new ReferenceList();		
		for (String sItem : Constants.PROPERTY_KEY_CONTACT_MODE_LIST.split( ";" ) ){
			ReferenceItem refItm = new ReferenceItem();
			refItm.setName(sItem);
			refItm.setCode(sItem);
			contactModeList.add( refItm );
		}
        
        Map<String, Object> model = getModel(  );
        IdentityDto identityDto = getIdentityDto( luteceUser.getName(  ) );
        _dashboardIdentity = DashboardIdentityUtils.convertToDashboardIdentity( identityDto );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.TRUE );
        model.put( MARK_CONTACT_MODE_LIST, contactModeList);
        model.put( MARK_GENDER_LIST, genderList);

        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Get the XPage to modify the identity of the current user
     *
     * @param request
     *          The request
     * @return The XPage to display
     * @throws UserNotSignedException
     *           If the user has not signed in
     */
    @View( VIEW_GET_MODIFY_IDENTITY )
    public XPage getModifyIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = getConnectedUser( request );

        if ( ( _dashboardIdentity == null ) || ( _dashboardIdentity.getConnectionId(  ) == null ) ||
                !_dashboardIdentity.getConnectionId(  ).equals( luteceUser.getName(  ) ) )
        {
            IdentityDto identityDto = getIdentityDto( luteceUser.getName(  ) );
            _dashboardIdentity = DashboardIdentityUtils.convertToDashboardIdentity( identityDto );
        }

		ReferenceList genderList = new ReferenceList();		
		for (String sItem : Constants.PROPERTY_KEY_GENDER_LIST.split( ";" ) ){
			ReferenceItem refItm = new ReferenceItem();
			refItm.setName(sItem);
			refItm.setCode(sItem);
			genderList.add( refItm );
		}
		
		ReferenceList contactModeList = new ReferenceList();		
		for (String sItem : Constants.PROPERTY_KEY_CONTACT_MODE_LIST.split( ";" ) ){
			ReferenceItem refItm = new ReferenceItem();
			refItm.setName(sItem);
			refItm.setCode(sItem);
			contactModeList.add( refItm );
		}
        
        Map<String, Object> model = getModel(  );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.FALSE );
        model.put( MARK_CONTACT_MODE_LIST, contactModeList);
        model.put( MARK_GENDER_LIST, genderList);
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale(  ), model );
    }

    /**
     * Do the modification of the user identity
     *
     * @param request
     *          The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *           If the user has not signed in
     */
    @Action( ACTION_DO_MODIFY_IDENTITY )
    public XPage doModifyIdentity( HttpServletRequest request )
        throws UserNotSignedException
    {
        checkUserAuthentication( request );

        if ( request.getParameter( PARAMETER_BACK ) != null )
        {
            return redirectView( request, VIEW_GET_VIEW_IDENTITY );
        }

        // fill dashboardIdentity from submitted form
        populate( _dashboardIdentity, request );

        IdentityDto identityDto = DashboardIdentityUtils.convertToIdentityDto( _dashboardIdentity );

        try
        {
        	updateIdentity( identityDto );
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
     * get connected user
     *
     * @param request
     *          request
     * @return lutece user
     * @throws UserNotSignedException
     *           if user is not connected
     */
    private LuteceUser getConnectedUser( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable(  )
            ? SecurityService.getInstance(  ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException(  );
        }

        return luteceUser;
    }

    /**
     * return IdentityDto from strConnectionId
     *
     * @param strConnectionId
     *          user connection id
     * @return IdentityDto
     * @throws UserNotSignedException
     */
    private IdentityDto getIdentityDto( String strConnectionId )
    {
        IdentityDto identityDto = null;
        
        try
        {
            identityDto = _identityService.getIdentity( strConnectionId, 0, DASHBOARD_APP_CODE, DASHBOARD_APP_HASH );
        }
        catch ( IdentityNotFoundException infe )
        {
            // FIXME (as there s no identitystore openam plugin
            // this is to make identityStore work for new users
            // this must be removed when identitystore-openam is done and plugged
            identityDto = new IdentityDto(  );
            identityDto.setConnectionId( strConnectionId );
        }

        return identityDto;
    }
    
    
    /**
     * Update Identity from an IdentityDto
     *
     * @param identityDto
     *          identity Data transfer Object
     * @throws IdentityNotFoundException
     */
    private void updateIdentity( IdentityDto identityDto )
    {

        IdentityChangeDto identityChangeDto = buildIdentityChangeDto( identityDto );
        
        try
        {
            _identityService.updateIdentity( identityChangeDto, DASHBOARD_APP_HASH, null );
        }
        catch ( IdentityNotFoundException infe )
        {
        	_identityService.createIdentity( identityChangeDto, DASHBOARD_APP_HASH );
        	
        	// Only the information from external source are set when an identity is created. Thus, we need to do an update to set all the attributes
        	_identityService.updateIdentity( identityChangeDto, DASHBOARD_APP_HASH, null );
        }

    }

      

    /**
     * check if user is authenticated
     *
     * @param request
     *          request
     * @throws UserNotSignedException
     *           if user is not connected
     */
    private void checkUserAuthentication( HttpServletRequest request )
        throws UserNotSignedException
    {
        getConnectedUser( request );
    }

    /**
     * build a changeDto from Identity
     *
     * @param identity
     *          identity to update
     * @return IdentityChangeDto
     */
    private IdentityChangeDto buildIdentityChangeDto( IdentityDto identity )
    {
        IdentityChangeDto identityChange = new IdentityChangeDto(  );
        AuthorDto author = new AuthorDto(  );
        author.setApplicationCode( DASHBOARD_APP_CODE );
        author.setApplicationName( DASHBOARD_APP_NAME );
        author.setType( AuthorType.TYPE_USER_OWNER.getTypeValue(  ) );

        identityChange.setIdentity( identity );
        identityChange.setAuthor( author );

        return identityChange;
    }
}
