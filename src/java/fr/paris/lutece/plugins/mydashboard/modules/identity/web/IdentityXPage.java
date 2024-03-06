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

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import fr.paris.lutece.plugins.avatar.service.AvatarService;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.service.IdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.DashboardIdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.plugins.verifybackurl.service.AuthorizedUrlService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.ISecurityTokenService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.site.properties.SitePropertiesGroup;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

/**
 * MyDashboardIdentity application
 */
@Controller( xpageName = "mydashboardIdentity", pageTitleI18nKey = "module.mydashboard.identity.xpage.getIdentity.pageTitle", pagePathI18nKey = "module.mydashboard.identity.xpage.getIdentity.pagePath" )
public class IdentityXPage extends MVCApplication
{
    private static final long   serialVersionUID                          = 1L;
    private static final String VIEW_GET_VIEW_IDENTITY                    = "getViewIdentity";
    private static final String VIEW_GET_MODIFY_IDENTITY                  = "getModifyIdentity";
    private static final String VIEW_GET_CHECK_IDENTITY                   = "getCheckIdentity";

    private static final String ACTION_DO_MODIFY_IDENTITY                 = "doModifyIdentity";
    private static final String ACTION_DO_CHECK_IDENTITY                  = "doCheckIdentity";
    private static final String ACTION_DO_COMPLETE_SUSPICIOUS_IDENTITY    = "doCompleteIdentity";

    private static final String PARAMETER_BACK                            = "back";
    private static final String PARAMETER_APP_CODE                        = "app_code";
    private static final String PARAMETER_ORIGIN_ACTION                   = "origin";

    private static final String MARK_IDENTITY                             = "identity";
    private static final String MARK_VIEW_MODE                            = "viewMode";
    private static final String MARK_AVATAR_URL                           = "avatar_url";
    private static final String MARK_AVATARSERVER_POST_URL                = "avatarserver_post_url";
    private static final String MARK_MYDASHBOARD_SITE_PROPERTIES          = "mydashboard_site_properties";
    private static final String BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES = "mydashboard-identity.sitePropertiesGroup";
    private static final String MARK_SERVICE_URL                          = "service_url";
    private static final String MARK_SERVICE_NAME                         = "service_name";
    private static final String MARK_MANDATORY_INFORMATIONS_SAVED         = "mandatory_informations_saved";
    private static final String MARK_NEED_CERTIFICATION_FC                = "needCertificationFC";
    private static final String MARK_NEED_LOGIN_CERTIFICATION             = "needLoginCertification";
    private static final String MARK_NEED_EMAIL_CERTIFICATION             = "needEmailCertification";

    private static final String TEMPLATE_GET_VIEW_MODIFY_IDENTITY         = "skin/plugins/mydashboard/modules/identity/edit_identity.html";
    private static final String TEMPLATE_GET_VIEW_CHECK_IDENTITY          = "skin/plugins/mydashboard/modules/identity/check_identity.html";
    private static final String TEMPLATE_SUSPICIOUS_COMPLETE_IDENTITY     = "skin/plugins/mydashboard/modules/identity/suspiciousIdentity/view_complete_identity.html";
    private static final String TEMPLATE_GET_VIEW_ERROR                   = "skin/plugins/mydashboard/modules/identity/view_error.html";

    private static final String MARK_GENDER_LIST                          = "genderlist";
    private static final String MARK_CONTACT_MODE_LIST                    = "contact_modeList";
    private static final String MARK_ORIGIN_ACTION                        = "origin";
    private static final String MARK_NS_NAME                              = "numericServiceName";
    private static final String MARK_TOKEN                                = "token";
    private static final String MARK_ERRORS                               = "errors";
    
    private static final String BEAN_IDENTITYSTORE_SERVICE                = "mydashboard-identity.identitystore.service";
    private static final String SPLIT_PATTERN                             = ";";
    private static final String PROPERTY_AVATERSERVER_POST_URL            = "mydashboard.identity.avatarserver.post.url";
    private static final String AVATARSERVER_POST_URL                     = AppPropertiesService.getProperty( PROPERTY_AVATERSERVER_POST_URL );

    // Views
    private static final String VIEW_VALIDATE_LAST_NAME                   = "validate_lastName";
    private static final String VIEW_VALIDATE_PREFERRED_USERNAME          = "validate_preferredUsername";
    private static final String VIEW_VALIDATE_FISRTNAME                   = "validate_firstname";
    private static final String VIEW_VALIDATE_BIRTHDATE                   = "validate_birthdate";
    private static final String VIEW_VALIDATE_BIRTHPLACE                  = "validate_birthplace";
    private static final String VIEW_VALIDATE_BIRTHCOUNTRY                = "validate_birthcountry";
    private static final String VIEW_VALIDATE_ADDRESS                     = "validate_address";
    private static final String VIEW_VALIDATE_ADDRESS_DETAIL              = "validate_addressdetail";
    private static final String VIEW_VALIDATE_ADDRESS_POSTALCODE          = "validate_addressPostalcode";
    private static final String VIEW_VALIDATE_ADDRESS_CITY                = "validate_addressCity";
    private static final String VIEW_VALIDATE_EMAIL                       = "validate_email";
    private static final String VIEW_VALIDATE_PHONE                       = "validate_phone";
    private static final String VIEW_VALIDATE_MOBILEPHONE                 = "validate_mobilephone";
    private static final String VIEW_SUSPICION_IDENTITY_COMPLETION        = "completeIdentity";
    
    //PROPERTIES
    private static final String PROPERTY_REDIRECT_MODIFY_ACCOUNT_PAGE     = AppPropertiesService.getProperty( "mydashboard.identity.suspicious.modify_account.redirect.page" );
    private static final String PROPERTY_REDIRECT_MODIFY_ACCOUNT_VIEW     = AppPropertiesService.getProperty( "mydashboard.identity.suspicious.modify_account.redirect.view" );
    private static final String PROPERTY_REDIRECT_COMPLETION_ACCOUNT_VIEW = AppPropertiesService.getProperty( "mydashboard.identity.suspicious.completion_account.redirect.view" );
    private static final String PROPERTY_COMPLETION_ATTRIBUTES_NEED_FC = AppPropertiesService.getProperty( "mydashboard.identity.completion_attributes_need_certification" );
    private static final String PROPERTY_IDENTITYSTORE_EMAIL_CERTIFIER_CODE =  AppPropertiesService.getProperty( "myluteceusergu.identitystore.emailcertifier.code", "emailcertifier");
    private static final String PROPERTY_FC_CERTIFIER_CODE =  AppPropertiesService.getProperty( "myluteceusergu.identitystore.fccertifier.code", "fccertifier" );
    private static final String PROPERTY_REDIRECT_URL_CERTIFY_EMAIL = AppPropertiesService.getProperty( "mydashboard.identity.completion.certify_email");
    private static final int PROPERTY_IDENTITYSTORE_EMAIL_LEVEL_MIN =  AppPropertiesService.getPropertyInt( "mydashboard.identity.emailcertifier.level_min", 200);
    
    private ReferenceList       _lstContactModeList;
    private ReferenceList       _lstGenderList;

    // session variable
    private DashboardIdentity   _dashboardIdentity;
    private DashboardIdentity   _checkdIdentity;
    private DashboardIdentity   _completionIdentity;
    private String              _strAppCode;
    private boolean             _bMandatoryInformationsSaved;

    private IdentityService     _identityService;
    private boolean             _bReInitAppCode                           = false;
    private String              _originActionCompletion;
    private ISecurityTokenService _securityTokenService = SecurityTokenService.getInstance( );

    /**
     * Constructor
     */
    public IdentityXPage( )
    {
        super( );
        _identityService = SpringContextService.getBean( BEAN_IDENTITYSTORE_SERVICE );

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

        _lstContactModeList = new ReferenceList( );

        for ( String sItem : Constants.PROPERTY_KEY_CONTACT_MODE_LIST.split( SPLIT_PATTERN ) )
        {
            ReferenceItem refItm = new ReferenceItem( );
            refItm.setName( sItem );
            refItm.setCode( sItem );
            _lstContactModeList.add( refItm );
        }
    }

    /**
     * Get the identity of the current user
     *
     * @param request
     *            The request, with the user logged in
     * @return The XPage to display the identity of the user
     * @throws UserNotSignedException
     *             If the user is not logged in
     */
    @View( value = VIEW_GET_VIEW_IDENTITY, defaultView = true )
    public XPage getViewIdentity( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = getConnectedUser( request );

        String strAppCode = request.getParameter( PARAMETER_APP_CODE );

        if ( _strAppCode == null || strAppCode != null && !strAppCode.equals( _strAppCode ) )
        {
            _bReInitAppCode = true;
            _strAppCode = strAppCode;
        }

        SitePropertiesGroup dashboardPropertiesGroup = ( SitePropertiesGroup ) SpringContextService.getBean( BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES );
        String strMyDashboardPropertiesPrefix = dashboardPropertiesGroup.getDatastoreKeysPrefix( );

        Map<String, Object> model = getModel( );
        IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
        _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );

        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( strMyDashboardPropertiesPrefix ).toMap( ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.TRUE );
        model.put( MARK_CONTACT_MODE_LIST, _lstContactModeList );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_AVATAR_URL, getAvatarUrl( request ) );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY ) );

        // check back url
        String strBackUrl = AuthorizedUrlService.getInstance( ).getServiceBackUrl( request );

        if ( !StringUtils.isEmpty( strBackUrl ) )
        {
            model.put( MARK_SERVICE_URL, strBackUrl );
            String strServiceName = !StringUtils.isEmpty( _strAppCode ) ? AuthorizedUrlService.getInstance( ).getNameByApplicationCode( _strAppCode, strBackUrl ) : AuthorizedUrlService.getInstance( ).getName( strBackUrl );
            if ( !StringUtils.isEmpty( strServiceName ) )
            {
                model.put( MARK_SERVICE_NAME, strServiceName );
            }
        }
        
        addMessage( request, model );
        
        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }

    /**
     * Get the XPage to modify the identity of the current user
     *
     * @param request
     *            The request
     * @return The XPage to display
     * @throws UserNotSignedException
     *             If the user has not signed in
     */
    @View( VIEW_GET_MODIFY_IDENTITY )
    public XPage getModifyIdentity( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = getConnectedUser( request );
        
        String strAppCode = request.getParameter( PARAMETER_APP_CODE );

        if ( _strAppCode == null || strAppCode != null && !strAppCode.equals( _strAppCode ) )
        {
            _bReInitAppCode = true;
            _strAppCode = strAppCode;
        }

        if ( ( _dashboardIdentity == null ) || ( _dashboardIdentity.getConnectionId( ) == null ) || !_dashboardIdentity.getConnectionId( ).getValue( ).equals( luteceUser.getName( ) ) )
        {
            IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
            _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );
        }

        SitePropertiesGroup dashboardPropertiesGroup = ( SitePropertiesGroup ) SpringContextService.getBean( BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES );
        String strMyDashboardPropertiesPrefix = dashboardPropertiesGroup.getDatastoreKeysPrefix( );

        Map<String, Object> model = getModel( );

        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( strMyDashboardPropertiesPrefix ).toMap( ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.FALSE );
        model.put( MARK_CONTACT_MODE_LIST, _lstContactModeList );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_AVATAR_URL, getAvatarUrl( request ) );
        model.put( MARK_AVATARSERVER_POST_URL, AVATARSERVER_POST_URL );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_MODIFY_IDENTITY ) );

        // get BackUrl
        String strBackUrl = AuthorizedUrlService.getInstance( ).getServiceBackUrl( request );
        if ( !StringUtils.isEmpty( strBackUrl ) )
        {
            model.put( MARK_SERVICE_URL, strBackUrl );
            String strServiceName = !StringUtils.isEmpty( _strAppCode ) ? AuthorizedUrlService.getInstance( ).getNameByApplicationCode( _strAppCode, strBackUrl ) : AuthorizedUrlService.getInstance( ).getName( strBackUrl );
            if ( !StringUtils.isEmpty( strServiceName ) )
            {
                model.put( MARK_SERVICE_NAME, strServiceName );
            }
        }
        
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
     * Get the XPage to modify the identity of the current user
     *
     * @param request
     *            The request
     * @return The XPage to display
     * @throws UserNotSignedException
     *             If the user has not signed in
     */
    @View( VIEW_GET_CHECK_IDENTITY )
    public XPage getCheckIdentity( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = getConnectedUser( request );
        String strAppCode = request.getParameter( PARAMETER_APP_CODE );

        if ( _strAppCode == null || strAppCode != null && !strAppCode.equals( _strAppCode ) )
        {
            _bReInitAppCode = true;
            _strAppCode = strAppCode;
        }
        

        try
        {
            _checkdIdentity = DashboardIdentityService.getInstance( ).getDashBoardIdentity( _strAppCode, luteceUser.getName( ) );
        } 
        catch ( AppException e )
        {
            AppLogService.error( "An error appear during retreaving Identity information for app_code {} and user guid {} ", strAppCode, luteceUser.getName( ), e );
            return getXPage( TEMPLATE_GET_VIEW_ERROR, request.getLocale( ) );
        }

        _bReInitAppCode = false;

        SitePropertiesGroup dashboardPropertiesGroup = ( SitePropertiesGroup ) SpringContextService.getBean( BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES );
        String strMyDashboardPropertiesPrefix = dashboardPropertiesGroup.getDatastoreKeysPrefix( );

        Map<String, Object> model = getModel( );
        
        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( strMyDashboardPropertiesPrefix ).toMap( ) );
        model.put( MARK_IDENTITY, _checkdIdentity );
        model.put( MARK_CONTACT_MODE_LIST, _lstContactModeList );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_AVATAR_URL, getAvatarUrl( request ) );
        model.put( MARK_AVATARSERVER_POST_URL, AVATARSERVER_POST_URL );
        model.put( MARK_MANDATORY_INFORMATIONS_SAVED, _bMandatoryInformationsSaved );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_CHECK_IDENTITY ) );
        
        // check back url in session

        String strBackUrl = AuthorizedUrlService.getInstance( ).getServiceBackUrl( request );
        if ( !StringUtils.isEmpty( strBackUrl ) )
        {
            model.put( MARK_SERVICE_URL, strBackUrl );
            String strServiceName = !StringUtils.isEmpty( _strAppCode ) ? AuthorizedUrlService.getInstance( ).getNameByApplicationCode( _strAppCode, strBackUrl ) : AuthorizedUrlService.getInstance( ).getName( strBackUrl );
            if ( !StringUtils.isEmpty( strServiceName ) )
            {
                model.put( MARK_SERVICE_NAME, strServiceName );
            }
        }
        
        if ( _strAppCode != null && _checkdIdentity != null )
        {
            model.put( MARK_NEED_CERTIFICATION_FC, DashboardIdentityService.getInstance().needCertification( _strAppCode, luteceUser.getName( ), _checkdIdentity, Arrays.asList( PROPERTY_COMPLETION_ATTRIBUTES_NEED_FC.split( "," ) ), 400 ) );
  
            boolean bLoginNeedCertification = DashboardIdentityService.getInstance().needCertification( _strAppCode, luteceUser.getName( ), _checkdIdentity, Arrays.asList( Constants.ATTRIBUTE_DB_IDENTITY_LOGIN ), PROPERTY_IDENTITYSTORE_EMAIL_LEVEL_MIN )
                    && ( StringUtils.isEmpty( _checkdIdentity.getLogin( ).getCertifierCode( ) ) || !_checkdIdentity.getLogin( ).getCertifierCode( ).equals( PROPERTY_IDENTITYSTORE_EMAIL_CERTIFIER_CODE ) );

            if( bLoginNeedCertification && StringUtils.isNotEmpty( PROPERTY_REDIRECT_URL_CERTIFY_EMAIL ) )
            {
               return redirect( request, PROPERTY_REDIRECT_URL_CERTIFY_EMAIL + "&type=login" ) ;
            }
 
            boolean bContactEmailNeedCertification = DashboardIdentityService.getInstance().needCertification( _strAppCode, luteceUser.getName( ), _checkdIdentity, Arrays.asList( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ), PROPERTY_IDENTITYSTORE_EMAIL_LEVEL_MIN )
                    && ( StringUtils.isEmpty( _checkdIdentity.getEmail( ).getCertifierCode( ) ) || !_checkdIdentity.getEmail( ).getCertifierCode( ).equals( PROPERTY_IDENTITYSTORE_EMAIL_CERTIFIER_CODE ) );
            
            if( ( bContactEmailNeedCertification || StringUtils.isEmpty( _checkdIdentity.getEmail( ).getValue( ) ) ) && StringUtils.isNotEmpty( PROPERTY_REDIRECT_URL_CERTIFY_EMAIL ) )
            {
               return redirect( request, PROPERTY_REDIRECT_URL_CERTIFY_EMAIL + "&type=email" ) ;
            }
            
            model.put( MARK_NEED_EMAIL_CERTIFICATION, bContactEmailNeedCertification );
            model.put( MARK_NEED_LOGIN_CERTIFICATION, bLoginNeedCertification );
        }

        // reinit Information
        _bMandatoryInformationsSaved = false;

        getLoadingErrors( request, model );
        
        return getXPage( TEMPLATE_GET_VIEW_CHECK_IDENTITY, request.getLocale( ), model );
    }

    private void getLoadingErrors( HttpServletRequest request, Map<String, Object> model )
    {
        if( ObjectUtils.isEmpty( model.get( MARK_ERRORS ) ) )
        {
            Map<String, String> hashErros =  DashboardIdentityService.getInstance( ).checkDashboardIdentityFieldsFromServiceContract( _checkdIdentity, request, true, _strAppCode, true ) ;
            if ( !hashErros.isEmpty( ) )
            {
                hashErros.forEach( ( x, y ) -> addError( y, x ) );
            }
            fillCommons( model );
        }
    }

    /**
     * Do the modification of the user identity
     *
     * @param request
     *            The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *             If the user has not signed in
     * @throws AccessDeniedException 
     */
    @Action( ACTION_DO_MODIFY_IDENTITY )
    public XPage doModifyIdentity( HttpServletRequest request ) throws UserNotSignedException, AccessDeniedException
    {
        // CSRF Token control
        if ( !_securityTokenService.validate( request, ACTION_DO_MODIFY_IDENTITY ) )
        {
            throw new AccessDeniedException( Constants.MESSAGE_ERROR_TOKEN  );
        }

        AuthorizedUrlService.getInstance( ).getServiceBackUrl( request );
        checkUserAuthentication( request );

        if ( _dashboardIdentity == null || request.getParameter( PARAMETER_BACK ) != null )
        {
            return redirectView( request, VIEW_GET_VIEW_IDENTITY );
        }

        // fill dashboardIdentity from submitted form
        DashboardIdentityService.getInstance( ).populateDashboardIdentity( _dashboardIdentity, request );
        _dashboardIdentity.getLastName().setMandatory( true );
        _dashboardIdentity.getFirstname().setMandatory( true );
         _dashboardIdentity.getBirthdate().setMandatory( true );
         _dashboardIdentity.getGender( ).setMandatory( true );
       
        
        Map<String, String> hashErros = DashboardIdentityService.getInstance( ).checkDashboardIdentityFields( _dashboardIdentity, request, false );
        if ( !hashErros.isEmpty( ) )
        {
            hashErros.forEach( ( x, y ) -> addError( y ) );
            return redirectView( request, VIEW_GET_MODIFY_IDENTITY );
        }
        
        //Suspicious identity
        if( DashboardIdentityService.getInstance( ).existSuspiciousIdentities( _dashboardIdentity,DashboardIdentityUtils.getInstance( ).getAllSuspiciousIdentityRules( ) ) )
        {
            DashboardIdentityUtils.getInstance( ).setCurrentDashboardIdentityInSession( request, _dashboardIdentity );
            DashboardIdentityUtils.getInstance( ).setRedirectUrlAfterCompletionInSession( PROPERTY_REDIRECT_MODIFY_ACCOUNT_PAGE, PROPERTY_REDIRECT_MODIFY_ACCOUNT_VIEW, request );
            
            return redirect( request, VIEW_SUSPICION_IDENTITY_COMPLETION, PARAMETER_ORIGIN_ACTION, Constants.ORIGIN_ACTION_MODIFY_ACCOUNT );
        }
        
        try
        {
            DashboardIdentityService.getInstance( ).updateDashboardIdentity( _dashboardIdentity, false );
        } catch ( Exception appEx )
        {

            AppLogService.error( "An error appear during updating Identity information for  user guid {} ", _dashboardIdentity.getConnectionId( ), appEx );

            addError( Constants.MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale( ) );

            return redirectView( request, VIEW_GET_MODIFY_IDENTITY );
        }

        // reint dashboard and check identity informations
        _dashboardIdentity = null;
        _checkdIdentity = null;

        addInfo( Constants.MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale( ) );

        return redirectView( request, VIEW_GET_VIEW_IDENTITY );
    }

    
    /**
     * Do the modification of the user identity
     *
     * @param request
     *            The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *             If the user has not signed in
     * @throws AccessDeniedException 
     */
    @Action( ACTION_DO_CHECK_IDENTITY )
    public XPage doCheckIdentity( HttpServletRequest request ) throws UserNotSignedException, AccessDeniedException
    {
        // CSRF Token control
        if ( !_securityTokenService.validate( request, ACTION_DO_CHECK_IDENTITY ) )
        {
            throw new AccessDeniedException( Constants.MESSAGE_ERROR_TOKEN  );
        }
        
        checkUserAuthentication( request );
        AuthorizedUrlService.getInstance( ).getServiceBackUrl( request );

        if ( _checkdIdentity == null || request.getParameter( PARAMETER_BACK ) != null )
        {
            return redirectView( request, VIEW_GET_CHECK_IDENTITY );
        }

        // fill dashboardIdentity from submitted form
        DashboardIdentityService.getInstance( ).populateDashboardIdentity( _checkdIdentity, request );
        Map<String, String> hashErros = DashboardIdentityService.getInstance( ).checkDashboardIdentityFields( _checkdIdentity, request, true );
        hashErros.putAll( DashboardIdentityService.getInstance( ).checkDashboardIdentityFieldsFromServiceContract( _checkdIdentity, request, true, _strAppCode, false ) );
        if ( !hashErros.isEmpty( ) )
        {
            hashErros.forEach( ( x, y ) -> addError( y, x ) );
            return redirectView( request, VIEW_GET_CHECK_IDENTITY );
        }
        
        //Suspicious identity
        if( DashboardIdentityService.getInstance( ).existSuspiciousIdentities( _checkdIdentity,DashboardIdentityUtils.getInstance( ).getAllSuspiciousIdentityRules( ) ) )
        {
            DashboardIdentityUtils.getInstance( ).setCurrentDashboardIdentityInSession( request, _checkdIdentity );
            DashboardIdentityUtils.getInstance( ).setRedirectUrlAfterCompletionInSession( PROPERTY_REDIRECT_MODIFY_ACCOUNT_PAGE, PROPERTY_REDIRECT_COMPLETION_ACCOUNT_VIEW, request );
            
            //Numeric service name
            ServiceContractSearchResponse serviceContract = DashboardIdentityService.getInstance( ).getActiveServiceContract( _strAppCode );
            if( serviceContract != null && serviceContract.getServiceContract( ) != null )
            {
                DashboardIdentityUtils.getInstance( ).setNumericServiceNameInSession( serviceContract.getServiceContract( ).getName( ), request );
            }
            
            return redirect( request, VIEW_SUSPICION_IDENTITY_COMPLETION, PARAMETER_ORIGIN_ACTION, Constants.ORIGIN_ACTION_COMPLETION_ACCOUNT );
        }
        
        try
        {
            DashboardIdentityService.getInstance( ).updateDashboardIdentity( _checkdIdentity, false );
        } catch ( Exception appEx )
        {
            addError( Constants.MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale( ) );

            return redirectView( request, VIEW_GET_CHECK_IDENTITY );
        }
        // reint dashboard and check identity informations
        _dashboardIdentity = null;
        _checkdIdentity = null;
        addInfo( Constants.MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale( ) );
        _bMandatoryInformationsSaved = true;

        return redirectView( request, VIEW_GET_CHECK_IDENTITY );
    }

    /**
     * get connected user
     *
     * @param request
     *            request
     * @return lutece user
     * @throws UserNotSignedException
     *             if user is not connected
     */
    private LuteceUser getConnectedUser( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }

        return luteceUser;
    }

    /**
     * check if user is authenticated
     *
     * @param request
     *            request
     * @throws UserNotSignedException
     *             if user is not connected
     */
    private void checkUserAuthentication( HttpServletRequest request ) throws UserNotSignedException
    {
        getConnectedUser( request );
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_LAST_NAME )
    public XPage validateLastName( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent(
                getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_LAST_NAME, Constants.MESSAGE_ERROR_VALIDATION_LASTNAME ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_PREFERRED_USERNAME )
    public XPage validatePreferredUserName( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, "preferredUsername", Constants.PROPERTY_KEY_VALIDATION_REGEXP_PREFERREDUSERNAME, Constants.MESSAGE_ERROR_VALIDATION_PREFFEREDUSERNAME ) );

        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_FISRTNAME )
    public XPage validateFirstName( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, "firstname", Constants.PROPERTY_KEY_VALIDATION_REGEXP_FIRSTNAME, Constants.MESSAGE_ERROR_VALIDATION_FIRSTNAME ) );

        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_BIRTHDATE )
    public XPage validateBirthDate( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, "birthdate", Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHDATE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHDATE ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_BIRTHPLACE )
    public XPage validateBirthPlace( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, "birthplace", Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE ) );

        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_BIRTHCOUNTRY )
    public XPage validateBirthCountry( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent(
                getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY, Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_ADDRESS )
    public XPage validateAddress( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS, Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_ADDRESS_DETAIL )
    public XPage validateAddressDetail( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL, Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_DETAIL,
                Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_DETAIL ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_ADDRESS_POSTALCODE )
    public XPage validateAddressPostalCode( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_POSTALCODE,
                Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_POSTALCODE ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_ADDRESS_CITY )
    public XPage validateAddressCity( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent(
                getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY, Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_CITY, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_CITY ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_EMAIL )
    public XPage validateEmail( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_EMAIL, null, Constants.MESSAGE_ERROR_VALIDATION_EMAIL ) );

        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_PHONE )
    public XPage validatePhone( HttpServletRequest request )
    {
        XPage xpContent = getXPage( );
        xpContent.setContent( getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_PHONE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_PHONE, Constants.MESSAGE_ERROR_VALIDATION_PHONE ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_MOBILEPHONE )
    public XPage validateMobilePhone( HttpServletRequest request )
    {

        XPage xpContent = getXPage( );
        xpContent.setContent(
                getErrorValidation( request, Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_MOBILEPHONE, Constants.MESSAGE_ERROR_VALIDATION_MOBILEPHONE ) );
        xpContent.setStandalone( true );

        return xpContent;
    }

    private String getErrorValidation( HttpServletRequest request, String strAttributeKey, String strRegExp, String i18nErrorMessage )
    {

        return getErrorValidation( request, strAttributeKey, strRegExp, i18nErrorMessage, false );

    }

    /**
     * 
     * @param request
     *            the HttpServletRequest
     * @param strAttributeKey
     *            the attributeKey to test
     * @param strRegExp
     *            the regExp who verify the submit value
     * @param i18nErrorMessage
     *            the I18nError message to display
     * @return Empty if no error appear otherwise return the errorMessage
     */
    private String getErrorValidation( HttpServletRequest request, String strAttributeKey, String strRegExp, String i18nErrorMessage, boolean bCheckMandatory )
    {
        String strError = StringUtils.EMPTY;
        if ( strAttributeKey.equals( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) && ( request.getParameter( strAttributeKey ) == null
                || ( StringUtils.isEmpty( strAttributeKey ) && !EmailValidator.getInstance( ).isValid( request.getParameter( strAttributeKey ) ) ) ) )
        {
            strError = I18nService.getLocalizedString( i18nErrorMessage, request.getLocale( ) );

        }

        else if ( request.getParameter( strAttributeKey ) == null || !request.getParameter( strAttributeKey ).matches( strRegExp ) )
        {
            strError = I18nService.getLocalizedString( i18nErrorMessage, request.getLocale( ) );
        } else if ( bCheckMandatory && StringUtils.isBlank( request.getParameter( strAttributeKey ) ) )
        {

            strError = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_EMPTY_ERROR_PREFIX + strAttributeKey, request.getLocale( ) );

        }

        return strError;

    }

    /**
     * Return the avatar URL
     * 
     * @param request
     *            The HTTP request
     * @return The URL
     */
    private String getAvatarUrl( HttpServletRequest request )
    {
        LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );

        return AvatarService.getAvatarUrl( user.getEmail( ) );
    }

    @View( VIEW_SUSPICION_IDENTITY_COMPLETION )
    public XPage getViewSuspiciousIdentity( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );
        _originActionCompletion = request.getParameter( PARAMETER_ORIGIN_ACTION );

        DashboardIdentity dasboardIdentitySession = DashboardIdentityUtils.getInstance( ).getCurrentDashboardIdentityInSession( request );

        if ( dasboardIdentitySession == null )
        {
            return redirect( request, AppPathService.getRootForwardUrl( ) );
        }

        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_IDENTITY, _completionIdentity == null ? dasboardIdentitySession : _completionIdentity );
        model.put( MARK_ORIGIN_ACTION, _originActionCompletion );
        model.put( MARK_NS_NAME, DashboardIdentityUtils.getInstance( ).getNumericServiceNameInSession( request ) );
        model.put( MARK_TOKEN, _securityTokenService.getToken( request, ACTION_DO_COMPLETE_SUSPICIOUS_IDENTITY ) );
        
        return getXPage( TEMPLATE_SUSPICIOUS_COMPLETE_IDENTITY, I18nService.getDefaultLocale( ), model );
    }

    @Action( ACTION_DO_COMPLETE_SUSPICIOUS_IDENTITY )
    public XPage doCompleteSuspiciousIdentity( HttpServletRequest request ) throws AccessDeniedException
    {
        // CSRF Token control
        if ( !_securityTokenService.validate( request, ACTION_DO_COMPLETE_SUSPICIOUS_IDENTITY ) )
        {
            throw new AccessDeniedException( Constants.MESSAGE_ERROR_TOKEN  );
        }
        
        DashboardIdentity dasboardIdentitySession = DashboardIdentityUtils.getInstance( ).getCurrentDashboardIdentityInSession( request );

        if ( dasboardIdentitySession == null )
        {
            return redirect( request, AppPathService.getRootForwardUrl( ) );
        }

        _completionIdentity = DashboardIdentityUtils.getInstance( ).initMandatoryAttributeForCompletionIdentity( dasboardIdentitySession, _originActionCompletion );

        DashboardIdentityService.getInstance( ).populateDashboardIdentity( _completionIdentity, request );

        Map<String, String> hashErros = DashboardIdentityService.getInstance( ).checkDashboardIdentityFields( _completionIdentity, request, false );
        if ( !hashErros.isEmpty( ) )
        {
            hashErros.forEach( ( x, y ) -> addError( y ) );
            return redirect( request, VIEW_SUSPICION_IDENTITY_COMPLETION, PARAMETER_ORIGIN_ACTION, Integer.parseInt( _originActionCompletion ));
        }

        DashboardIdentityUtils.getInstance( ).updateDashboardIdentityInSession( _completionIdentity, dasboardIdentitySession );

        DashboardIdentityUtils.getInstance( ).setCurrentDashboardIdentityInSession( request, dasboardIdentitySession );

        // redirection vers la page ayant demander la completion
        String strRedirectUrl = DashboardIdentityUtils.getInstance( ).getRedirectUrlAfterCompletionInSession( request );

        return redirect( request, strRedirectUrl );

    }

}