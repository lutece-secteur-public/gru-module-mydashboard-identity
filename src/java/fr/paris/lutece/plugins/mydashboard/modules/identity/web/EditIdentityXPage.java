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

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.AttributeCategory;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.DashboardIdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.html.EncodingService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.ISecurityTokenService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.site.properties.SitePropertiesGroup;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

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
    
    private static final String VIEW_GET_EDIT_IDENTITY_INFORMATIONS           = "getEditIdentityInformations";
    private static final String VIEW_GET_EDIT_IDENTITY_COORDINATES            = "getEditIdentityCoordinates";
    
    private static final String ACTION_DO_MODIFY_IDENTITY_INFORMATIONS        = "doModifyIdentityInformations";
    private static final String ACTION_DO_MODIFY_IDENTITY_COORDINATES         = "doModifyIdentityCoordinates";
    private static final String ACTION_TYPE_MODIFY_ACCOUNT                    = "modifyAccount";
    
    private static final String TEMPLATE_GET_VIEW_MODIFY_IDENTITY             = "skin/plugins/mydashboard/modules/identity/modify_identity.html";
        
    private static final String MARK_PAGE_TITLE                               = "pageTitle";
    private static final String MARK_IDENTITY                                 = "identity";
    private static final String MARK_GENDER_LIST                              = "genderlist";
    private static final String MARK_EDIT_INFORMATIONS                        = "editInformations";
    private static final String MARK_EDIT_COORDINATES                         = "editCoordinates";
    private static final String MARK_ACTION_NAME                              = "actionName";
    private static final String MARK_TOKEN                                    = "token";
    private static final String MARK_BUTTON_VALIDATE                          = "btnValidate";
    private static final String MARK_MYDASHBOARD_SITE_PROPERTIES              = "mydashboard_site_properties";
    
    private static final String SPLIT_PATTERN                                 = ";";
 
    private static final String MESSAGE_COORDINATES_PAGE_TITLE = "module.mydashboard.identity.xpage.edit_identity.coordinates.page.title";
    private static final String MESSAGE_COORDINATES_BTN_VALIDATE = "module.mydashboard.identity.xpage.edit_identity.coordinates.validate.button";
    private static final String MESSAGE_IDENTITY_INFOS_PAGE_TITLE = "module.mydashboard.identity.xpage.edit_identity.identity.infos.page.title";
    private static final String MESSAGE_IDENTITY_INFOS_BTN_VALIDATE = "module.mydashboard.identity.xpage.edit_identity.identity.infos.validate.button";
    
    private static final String PROPERTY_REDIRECT_URL_CERTIFY_EMAIL_ACTION    = AppPropertiesService.getProperty( "mydashboard.identity.certify_email.action");
    
    private static final String PROPERTY_REDIRECT_MODIFY_ACCOUNT_PAGE         = AppPropertiesService.getProperty( "mydashboard.identity.suspicious.modify_account.redirect.page" );
    private static final String PROPERTY_REDIRECT_MODIFY_ACCOUNT_VIEW         = AppPropertiesService.getProperty( "mydashboard.identity.suspicious.modify_account.redirect.view" );

    private static final String URL_SUSPICION_IDENTITY_COMPLETION  = "Portal.jsp?page=mydashboardIdentity&view=completeIdentity&origin=" + Constants.ORIGIN_ACTION_MODIFY_ACCOUNT;

    private DashboardIdentity   _dashboardIdentity;
    private ISecurityTokenService _securityTokenService = SecurityTokenService.getInstance( );
    private SitePropertiesGroup _dashboardPropertiesGroup = ( SitePropertiesGroup ) SpringContextService.getBean( "mydashboard-identity.sitePropertiesGroup" );
    private ReferenceList       _lstGenderList;
    
    /**
     * Constructor
     */
    public EditIdentityXPage( )
    {
        super( );
        
        _lstGenderList = new ReferenceList( );

        int i = 0;

        for ( String sItem : Constants.PROPERTY_KEY_GENDER_LIST.split( SPLIT_PATTERN ) )
        {
            ReferenceItem refItm = new ReferenceItem( );
            refItm.setName( sItem );
            refItm.setCode( String.valueOf( i ) );
            _lstGenderList.add( refItm );
            i++;
        }
    }
    
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
        
        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( _dashboardPropertiesGroup.getDatastoreKeysPrefix( ) ).toMap( ) );
        model.put( MARK_PAGE_TITLE, I18nService.getLocalizedString( MESSAGE_IDENTITY_INFOS_PAGE_TITLE, request.getLocale( ) ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_EDIT_INFORMATIONS, true);
        model.put( MARK_ACTION_NAME, "jsp/site/Portal.jsp?page=editIdentity&action=doModifyIdentityInformations" );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY_INFORMATIONS ) );
        model.put( MARK_BUTTON_VALIDATE, I18nService.getLocalizedString( MESSAGE_IDENTITY_INFOS_BTN_VALIDATE, request.getLocale( ) ) );
        
        addMessage( request, model );
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }
    
    private void addMessage( HttpServletRequest request, Map<String, Object> model )
    {
        String strErrorMessageInSession = DashboardIdentityUtils.getInstance( ).getMessageInSession( true, true, request );
        String stInfoMessageInSession = DashboardIdentityUtils.getInstance( ).getMessageInSession( false, true, request );
        
        if( StringUtils.isNotEmpty( strErrorMessageInSession ) )
        {
            addError( strErrorMessageInSession, request.getLocale( ) );
            fillCommons( model );
        }
        if( StringUtils.isNotEmpty( stInfoMessageInSession ) )
        {
            addInfo( stInfoMessageInSession, request.getLocale( ) );
            fillCommons( model );
        }
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
        
        if ( ( _dashboardIdentity == null ) || ( _dashboardIdentity.getConnectionId( ) == null ) || !_dashboardIdentity.getConnectionId( ).getValue( ).equals( luteceUser.getName( ) ) )
        {
            IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
            _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );
        }
        
        Map<String, Object> model = getModel( );
        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( _dashboardPropertiesGroup.getDatastoreKeysPrefix( ) ).toMap( ) );
        model.put( MARK_PAGE_TITLE, I18nService.getLocalizedString( MESSAGE_COORDINATES_PAGE_TITLE, request.getLocale( ) ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_EDIT_COORDINATES, true);
        model.put( MARK_ACTION_NAME, "jsp/site/Portal.jsp?page=editIdentity&action=doModifyIdentityCoordinates" );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY_COORDINATES ) );
        model.put( MARK_BUTTON_VALIDATE, I18nService.getLocalizedString( MESSAGE_COORDINATES_BTN_VALIDATE, request.getLocale( ) ) );
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }
    
    /**
     * Do the modification of the user identity informations
     *
     * @param request
     *            The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *             If the user has not signed in
     * @throws AccessDeniedException 
     */
    @Action( ACTION_DO_MODIFY_IDENTITY_INFORMATIONS )
    public XPage doModifyIdentityInformations( HttpServletRequest request ) throws UserNotSignedException, AccessDeniedException
    {
        // CSRF Token control
        if ( !_securityTokenService.validate( request, ACTION_DO_MODIFY_IDENTITY_INFORMATIONS ) )
        {
            throw new AccessDeniedException( Constants.MESSAGE_ERROR_TOKEN  );
        }
        
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }
        
        // fill dashboardIdentity from submitted form
        DashboardIdentityService.getInstance( ).populateDashboardIdentity( _dashboardIdentity, request );
        _dashboardIdentity.getGender().setMandatory( true );
        _dashboardIdentity.getLastName().setMandatory( true );
        _dashboardIdentity.getFirstname().setMandatory( true );
        _dashboardIdentity.getBirthdate().setMandatory( true );
        
        Map<String, String> hashErros = DashboardIdentityService.getInstance( ).checkDashboardIdentityFields( _dashboardIdentity, request, false, AttributeCategory.IDENTITY_INDORMATIONS );
        if ( !hashErros.isEmpty( ) )
        {
            hashErros.forEach( ( x, y ) -> addError( y ) );
            return redirectView( request, VIEW_GET_EDIT_IDENTITY_INFORMATIONS );
        }
        
        //Suspicious identity
        if( DashboardIdentityService.getInstance( ).existSuspiciousIdentities( _dashboardIdentity,DashboardIdentityUtils.getInstance( ).getAllSuspiciousIdentityRules( ) ) )
        {
            DashboardIdentityUtils.getInstance( ).setCurrentDashboardIdentityInSession( request, _dashboardIdentity );
            DashboardIdentityUtils.getInstance( ).setRedirectUrlAfterCompletionInSession( PROPERTY_REDIRECT_MODIFY_ACCOUNT_PAGE, PROPERTY_REDIRECT_MODIFY_ACCOUNT_VIEW, request );
            
            return redirect( request, new UrlItem( URL_SUSPICION_IDENTITY_COMPLETION ).getUrl( ) );            
        }
        
        try
        {
            DashboardIdentityService.getInstance( ).updateDashboardIdentity( _dashboardIdentity, false, AttributeCategory.IDENTITY_INDORMATIONS );
        }
        catch ( Exception appEx )
        {

            AppLogService.error( "An error appear during updating Identity informations for  user guid {} ", _dashboardIdentity.getConnectionId( ), appEx );

            addError( Constants.MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale( ) );

            return redirectView( request, VIEW_GET_EDIT_IDENTITY_INFORMATIONS );
        }
        
        // reint dashboard and check identity informations
        _dashboardIdentity = null;

        addInfo( Constants.MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale( ) );

        return redirectView( request, VIEW_GET_EDIT_IDENTITY_INFORMATIONS );
    }
    
    /**
     * Do the modification of the user identity coordinates
     *
     * @param request
     *            The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *             If the user has not signed in
     * @throws AccessDeniedException 
     */
    @Action( ACTION_DO_MODIFY_IDENTITY_COORDINATES )
    public XPage doModifyIdentityCoordinates( HttpServletRequest request ) throws UserNotSignedException, AccessDeniedException
    {
        // CSRF Token control
        if ( !_securityTokenService.validate( request, ACTION_DO_MODIFY_IDENTITY_COORDINATES ) )
        {
            throw new AccessDeniedException( Constants.MESSAGE_ERROR_TOKEN  );
        }
        
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }
        
        // fill dashboardIdentity from submitted form
        DashboardIdentityService.getInstance( ).populateDashboardIdentity( _dashboardIdentity, request );
        
        Map<String, String> hashErros = DashboardIdentityService.getInstance( ).checkDashboardIdentityFields( _dashboardIdentity, request, false );
        if ( !hashErros.isEmpty( ) )
        {
            hashErros.forEach( ( x, y ) -> addError( y ) );
            return redirectView( request, VIEW_GET_EDIT_IDENTITY_COORDINATES );
        }
        
        try
        {
            DashboardIdentityService.getInstance( ).updateDashboardIdentity( _dashboardIdentity, false, AttributeCategory.COORDINATES_INFORMATIONS );
        }
        catch ( Exception appEx )
        {

            AppLogService.error( "An error appear during updating Identity coordinates for  user guid {} ", _dashboardIdentity.getConnectionId( ), appEx );

            addError( Constants.MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale( ) );

            return redirectView( request, VIEW_GET_EDIT_IDENTITY_COORDINATES );
        }
        
        IdentityDto identityFromRic = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
        DashboardIdentity dashboardIdentityFromRic = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identityFromRic );
        
        if ( !_dashboardIdentity.getEmail( ).getValue( ).equals( dashboardIdentityFromRic.getEmail( ).getValue( ) ) )
        {
            String strEncodeEmail =  EncodingService.encodeUrl( _dashboardIdentity.getEmail( ).getValue( ) );
                    
            return redirect( request, PROPERTY_REDIRECT_URL_CERTIFY_EMAIL_ACTION + "&email=" + strEncodeEmail + "&token=" + _securityTokenService.getToken( request, ACTION_TYPE_MODIFY_ACCOUNT ) );
        }
        
        // reint dashboard and check identity informations
        _dashboardIdentity = null;

        addInfo( Constants.MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale( ) );

        return redirectView( request, VIEW_GET_EDIT_IDENTITY_COORDINATES );
    }
}