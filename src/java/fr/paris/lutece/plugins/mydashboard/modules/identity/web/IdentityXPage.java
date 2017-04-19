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

import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.plugins.avatar.service.AvatarService;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.AuthorDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityChangeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.web.service.AuthorType;
import fr.paris.lutece.plugins.identitystore.web.service.IdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardAttribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.site.properties.SitePropertiesGroup;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
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
    private static final String PARAMETER_BACK = "back";
    private static final String MARK_IDENTITY = "identity";
    private static final String MARK_VIEW_MODE = "viewMode";
    private static final String MARK_AVATAR_URL = "avatar_url";
    private static final String MARK_AVATARSERVER_POST_URL = "avatarserver_post_url";
    private static final String MARK_MYDASHBOARD_SITE_PROPERTIES ="mydashboard_site_properties";
    private static final String BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES = "mydashboard-identity.sitePropertiesGroup";
    
    private static final String TEMPLATE_GET_VIEW_MODIFY_IDENTITY = "skin/plugins/mydashboard/modules/identity/edit_identity.html";
    private static final String DASHBOARD_APP_CODE = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE );
    private static final String DASHBOARD_APP_NAME = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_NAME );
    private static final String MARK_GENDER_LIST = "genderlist";
    private static final String MARK_CONTACT_MODE_LIST = "contact_modeList";
    private static final String BEAN_IDENTITYSTORE_SERVICE = "mydashboard-identity.identitystore.service";
    private static final String SPLIT_PATTERN = ";";
    private static final String PROPERTY_AVATERSERVER_POST_URL = "mydashboard.identity.avatarserver.post.url";
    private static final String AVATARSERVER_POST_URL = AppPropertiesService.getProperty( PROPERTY_AVATERSERVER_POST_URL );

    // Views
    private static final String VIEW_VALIDATE_LAST_NAME = "validate_lastName";
    private static final String VIEW_VALIDATE_PREFERRED_USERNAME = "validate_preferredUsername";
    private static final String VIEW_VALIDATE_FISRTNAME = "validate_firstname";
    private static final String VIEW_VALIDATE_BIRTHDATE = "validate_birthdate";
    private static final String VIEW_VALIDATE_BIRTHPLACE = "validate_birthplace";
    private static final String VIEW_VALIDATE_BIRTHCOUNTRY = "validate_birthcountry";
    private static final String VIEW_VALIDATE_ADDRESS = "validate_address";
    private static final String VIEW_VALIDATE_ADDRESS_DETAIL = "validate_addressdetail";
    private static final String VIEW_VALIDATE_ADDRESS_POSTALCODE = "validate_addressPostalcode";
    private static final String VIEW_VALIDATE_ADDRESS_CITY = "validate_addressCity";
    private static final String VIEW_VALIDATE_EMAIL = "validate_email";
    private static final String VIEW_VALIDATE_PHONE = "validate_phone";
    private static final String VIEW_VALIDATE_MOBILEPHONE = "validate_mobilephone";
    private static final String VIEW_UPDATE_ACCEPT_NEWS = "update_acceptNews";
    private static final String VIEW_UPDATE_ACCEPT_SURVEY = "update_acceptSurvey";
    private ReferenceList _lstContactModeList;
    private ReferenceList _lstGenderList;

    // session variable
    private DashboardIdentity _dashboardIdentity;
    private IdentityService _identityService;

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

        SitePropertiesGroup dashboardPropertiesGroup = (SitePropertiesGroup)SpringContextService.getBean( BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES );
        String strMyDashboardPropertiesPrefix = dashboardPropertiesGroup.getDatastoreKeysPrefix( );
        
        Map<String, Object> model = getModel( );
        IdentityDto identityDto = getIdentityDto( luteceUser.getName( ) );
        _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identityDto );

        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( strMyDashboardPropertiesPrefix ).toMap( ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.TRUE );
        model.put( MARK_CONTACT_MODE_LIST, _lstContactModeList );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_AVATAR_URL, getAvatarUrl( request ) );

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

        if ( ( _dashboardIdentity == null ) || ( _dashboardIdentity.getConnectionId( ) == null )
                || !_dashboardIdentity.getConnectionId( ).equals( luteceUser.getName( ) ) )
        {
            IdentityDto identityDto = getIdentityDto( luteceUser.getName( ) );
            _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identityDto );
        }
        
        SitePropertiesGroup dashboardPropertiesGroup = (SitePropertiesGroup)SpringContextService.getBean( BEAN_MYDASHBOARD_IDENTITY_SITE_PROPERTIES );
        String strMyDashboardPropertiesPrefix = dashboardPropertiesGroup.getDatastoreKeysPrefix( );
        
        Map<String, Object> model = getModel( );
        
        model.put( MARK_MYDASHBOARD_SITE_PROPERTIES, DatastoreService.getDataByPrefix( strMyDashboardPropertiesPrefix ).toMap( ) );
        model.put( MARK_IDENTITY, _dashboardIdentity );
        model.put( MARK_VIEW_MODE, Boolean.FALSE );
        model.put( MARK_CONTACT_MODE_LIST, _lstContactModeList );
        model.put( MARK_GENDER_LIST, _lstGenderList );
        model.put( MARK_AVATAR_URL, getAvatarUrl( request ) );
        model.put( MARK_AVATARSERVER_POST_URL, AVATARSERVER_POST_URL );

        return getXPage( TEMPLATE_GET_VIEW_MODIFY_IDENTITY, request.getLocale( ), model );
    }

    /**
     * Do the modification of the user identity
     *
     * @param request
     *            The request
     * @return The next view to redirect to
     * @throws UserNotSignedException
     *             If the user has not signed in
     */
    @Action( ACTION_DO_MODIFY_IDENTITY )
    public XPage doModifyIdentity( HttpServletRequest request ) throws UserNotSignedException
    {
        checkUserAuthentication( request );

        if ( request.getParameter( PARAMETER_BACK ) != null )
        {
            return redirectView( request, VIEW_GET_VIEW_IDENTITY );
        }

        // fill dashboardIdentity from submitted form
        DashboardIdentityUtils.getInstance( ).populateDashboardIdentity( _dashboardIdentity, request );

        if ( !checkDashboardIdentityFields( _dashboardIdentity, request ) )
        {
            return redirectView( request, VIEW_GET_MODIFY_IDENTITY );
        }

        IdentityDto identityDto = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( _dashboardIdentity );

        try
        {
            updateIdentity( identityDto );
        }
        catch( Exception appEx )
        {
            addError( Constants.MESSAGE_ERROR_UPDATE_IDENTITY, request.getLocale( ) );

            return redirectView( request, VIEW_GET_MODIFY_IDENTITY );
        }

        addInfo( Constants.MESSAGE_INFO_IDENTITY_UPDATED, request.getLocale( ) );

        return redirectView( request, VIEW_GET_VIEW_IDENTITY );
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
     * return IdentityDto from strConnectionId
     *
     * @param strConnectionId
     *            user connection id
     * @return IdentityDto
     * @throws UserNotSignedException
     */
    private IdentityDto getIdentityDto( String strConnectionId )
    {
        IdentityDto identityDto = null;

        try
        {
            identityDto = _identityService.getIdentityByConnectionId( strConnectionId, DASHBOARD_APP_CODE );
        }
        catch( IdentityNotFoundException infe )
        {
            identityDto = new IdentityDto( );
            identityDto.setConnectionId( strConnectionId );
        }

        return identityDto;
    }

    /**
     * Update Identity from an IdentityDto
     *
     * @param identityDto
     *            identity Data transfer Object
     * @throws IdentityNotFoundException
     */
    private void updateIdentity( IdentityDto identityDto )
    {
        IdentityChangeDto identityChangeDto = buildIdentityChangeDto( identityDto );

        _identityService.updateIdentity( identityChangeDto, null );

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
     * build a changeDto from Identity
     *
     * @param identity
     *            identity to update
     * @return IdentityChangeDto
     */
    private IdentityChangeDto buildIdentityChangeDto( IdentityDto identity )
    {
        IdentityChangeDto identityChange = new IdentityChangeDto( );
        AuthorDto author = new AuthorDto( );
        author.setApplicationCode( DASHBOARD_APP_CODE );
        author.setApplicationName( DASHBOARD_APP_NAME );
        author.setType( AuthorType.TYPE_USER_OWNER.getTypeValue( ) );

        identityChange.setIdentity( identity );
        identityChange.setAuthor( author );

        return identityChange;
    }

    /**
     * Check fields format of dashboardIdentity
     *
     * @param dashboardIdentity
     *            dashboardIdentity to check
     * @param request
     *            the httpServletrequest to add errors
     * @return IdentityChangeDto
     */
    private boolean checkDashboardIdentityFields( DashboardIdentity dashboardIdentity, HttpServletRequest request )
    {
        boolean bStatus = true;

        String strValidateLastName = validateLastName( request ).getContent( );

        if ( !strValidateLastName.isEmpty( ) )
        {
            addError( strValidateLastName );
            bStatus = false;
        }

        String strValidatePreferredUsername = validatePreferredUserName( request ).getContent( );

        if ( !strValidatePreferredUsername.isEmpty( ) )
        {
            addError( strValidatePreferredUsername );
            bStatus = false;
        }

        String strValidateFirstname = validateFirstName( request ).getContent( );

        if ( !strValidateFirstname.isEmpty( ) )
        {
            addError( strValidateFirstname );
            bStatus = false;
        }

        String strValidateBirthplace = validateBirthPlace( request ).getContent( );

        if ( !strValidateBirthplace.isEmpty( ) )
        {
            addError( strValidateBirthplace );
            bStatus = false;
        }

        String strValidateBirthDate = validateBirthDate( request ).getContent( );

        if ( !strValidateBirthDate.isEmpty( ) )
        {
            addError( strValidateBirthDate );
            bStatus = false;
        }

        String strValidateBirthCountry = validateBirthCountry( request ).getContent( );

        if ( !strValidateBirthCountry.isEmpty( ) )
        {
            addError( strValidateBirthCountry );
            bStatus = false;
        }

        String strValidateEmail = validateEmail( request ).getContent( );

        if ( !strValidateEmail.isEmpty( ) )
        {
            addError( strValidateEmail );
            bStatus = false;
        }

        String strValidatePhone = validatePhone( request ).getContent( );

        if ( !strValidatePhone.isEmpty( ) )
        {
            addError( strValidatePhone );
            bStatus = false;
        }

        String strValidateMobilePhone = validateMobilePhone( request ).getContent( );

        if ( !strValidateMobilePhone.isEmpty( ) )
        {
            addError( strValidateMobilePhone );
            bStatus = false;
        }

        String strValidateAdresse = validateAddress( request ).getContent( );

        if ( !strValidateAdresse.isEmpty( ) )
        {
            addError( strValidateAdresse );
            bStatus = false;
        }

        String strValidateAdresseDetail = validateAddressDetail( request ).getContent( );

        if ( !strValidateAdresseDetail.isEmpty( ) )
        {
            addError( strValidateAdresseDetail );
            bStatus = false;
        }

        String strValidateAdressePostalcode = validateAddressPostalCode( request ).getContent( );

        if ( !strValidateAdressePostalcode.isEmpty( ) )
        {
            addError( strValidateAdressePostalcode );
            bStatus = false;
        }

        String strValidateCity = validateAddressCity( request ).getContent( );

        if ( !strValidateCity.isEmpty( ) )
        {
            addError( strValidateCity );
            bStatus = false;
        }

        String strPreferredContactMode = dashboardIdentity.getPreferredContactMode( ).getValue( );

        // Case preferred Contact Mode = email. Check if email is empty
        if ( strPreferredContactMode.compareTo( _lstContactModeList.get( 0 ).getName( ) ) == 0 )
        {
            if ( dashboardIdentity.getEmail( ).getValue( ).isEmpty( ) )
            {
                addError( I18nService.getLocalizedString( Constants.MESSAGE_ERROR_EMAIL_EMPTY, request.getLocale( ) ) );
                bStatus = false;
            }
        }

        // Case preferred Contact Mode = telephone. Check if at least telephone or mobile is populated
        if ( strPreferredContactMode.compareTo( _lstContactModeList.get( 1 ).getName( ) ) == 0 )
        {
            if ( ( dashboardIdentity.getPhone( ).getValue( ).isEmpty( ) ) && ( dashboardIdentity.getMobilePhone( ).getValue( ).isEmpty( ) ) )
            {
                addError( I18nService.getLocalizedString( Constants.MESSAGE_ERROR_TELEPHONE_EMPTY, request.getLocale( ) ) );
                bStatus = false;
            }
        }

        // Populate gender with list codes {0,1,2} instead of values
        String strGender = dashboardIdentity.getGender( ).getValue( );

        for ( ReferenceItem rItem : _lstGenderList )
        {
            if ( strGender.compareTo( rItem.getName( ) ) == 0 )
            {
                dashboardIdentity.setGender( new DashboardAttribute( 
                        Constants.ATTRIBUTE_DB_IDENTITY_GENDER,
                        rItem.getCode( ) ) );
            }
        }

        return bStatus;
    }

    /**
     * @param request
     *            the request
     * @return an XPage object with validation error message
     */
    @View( VIEW_VALIDATE_LAST_NAME )
    public XPage validateLastName( HttpServletRequest request )
    {
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_LAST_NAME ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_LASTNAME, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( "preferredUsername" ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_PREFERREDUSERNAME ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_PREFFEREDUSERNAME, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( "firstname" ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_FIRSTNAME ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_FIRSTNAME, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( "birthdate" ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHDATE ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHDATE, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( "birthplace" ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_DETAIL ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_DETAIL, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_POSTALCODE ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_POSTALCODE, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_CITY ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_CITY, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !EmailValidator.getInstance( ).isValid( request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_EMAIL, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_PHONE ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_PHONE ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_PHONE, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
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
        String errorValidationMessage = StringUtils.EMPTY;

        if ( !request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE ).matches( Constants.PROPERTY_KEY_VALIDATION_REGEXP_MOBILEPHONE ) )
        {
            errorValidationMessage = I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_MOBILEPHONE, request.getLocale( ) );
        }

        XPage xpContent = getXPage( );
        xpContent.setContent( errorValidationMessage );
        xpContent.setStandalone( true );

        return xpContent;
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

    /**
     * Update the accept_news attribute of the current Identity
     * 
     * @param request
     *            the request
     */
    @View( VIEW_UPDATE_ACCEPT_NEWS )
    public void updateAcceptNews( HttpServletRequest request )
    {
        if ( !request.getParameter( "bAccept" ).isEmpty( ) )
        {
            updateIdentityAttribute( Constants.PROPERTY_KEY_ACCEPT_NEWS, request.getParameter( "bAccept" ) );
            String strAcceptNews = (Boolean.parseBoolean( request.getParameter( "bAccept" ) ) == true ) ?
                    Constants.TRUE : Constants.FALSE;
                    
            _dashboardIdentity.setAcceptNews( new DashboardAttribute (
                Constants.ATTRIBUTE_DB_IDENTITY_ACCEPT_NEWS,
                strAcceptNews
            ) );
        }
    }

    /**
     * Update the accept_survey attribute of the current Identity
     * 
     * @param request
     *            the request
     */
    @View( VIEW_UPDATE_ACCEPT_SURVEY )
    public void updateAcceptSurvey( HttpServletRequest request )
    {
        if ( !request.getParameter( "bAccept" ).isEmpty( ) )
        {
            updateIdentityAttribute( Constants.PROPERTY_KEY_ACCEPT_SURVEY, request.getParameter( "bAccept" ) );
            String strAcceptSurveys = (Boolean.parseBoolean( request.getParameter( "bAccept" ) ) == true ) ?
                    Constants.TRUE : Constants.FALSE;
            _dashboardIdentity.setAcceptSurvey( new DashboardAttribute (
                Constants.ATTRIBUTE_DB_IDENTITY_ACCEPT_SURVEY,
                strAcceptSurveys
            ) );

        }
    }

    /**
     * Update an attribute of current Identity with provided key/value
     * 
     * @param propertyKeyToUpdate
     *            the attribute key in IdentityStore
     * @param value
     *            the attribute value to set
     */
    private void updateIdentityAttribute( String propertyKeyToUpdate, String value )
    {
        IdentityDto identityDto = new IdentityDto( );
        identityDto.setConnectionId( _dashboardIdentity.getConnectionId( ).getValue( ) );
        identityDto.setCustomerId( _dashboardIdentity.getCustomerId( ).getValue( ) );

        Map<String, AttributeDto> mapAttributes = new HashMap<String, AttributeDto>( );
        AttributeDto attribute = new AttributeDto( );
        attribute.setKey( propertyKeyToUpdate );
        attribute.setValue( value );
        mapAttributes.put( attribute.getKey( ), attribute );
        identityDto.setAttributes( mapAttributes );

        updateIdentity( identityDto );
    }
}
