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
package fr.paris.lutece.plugins.mydashboard.modules.identity.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AuthorType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.RequestAuthor;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.CertifiedAttribute;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.Identity;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.IdentityChangeRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.IdentitySearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.service.IdentityService;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityStoreException;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardAttribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;


/**
 *
 * class to help managing identity feature
 *
 */
public class DashboardIdentityUtils
{
    private static DashboardIdentityUtils _instance;   
    private static final String BEAN_IDENTITYSTORE_SERVICE = "mydashboard-identity.identitystore.service";
    //For matching on DBAttributes and Identity store attributes
    private static Map<String,String> _mapAttributeKeyMatch;
    private IdentityService _identityService;
    private static final String DASHBOARD_APP_CODE = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE );
    static {
        _mapAttributeKeyMatch = new HashMap<String,String>( );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME, Constants.PROPERTY_KEY_NAME );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME, Constants.PROPERTY_KEY_PREFERREDUSERNAME );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME, Constants.PROPERTY_KEY_FIRSTNAME );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_GENDER, Constants.PROPERTY_KEY_GENDER );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, Constants.PROPERTY_KEY_BIRTHDATE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE, Constants.PROPERTY_KEY_BIRTHPLACE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY, Constants.PROPERTY_KEY_BIRTHCOUNTRY );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS, Constants.PROPERTY_KEY_ADDRESS );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL, Constants.PROPERTY_KEY_ADDRESSDETAIL );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE, Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY, Constants.PROPERTY_KEY_ADDRESS_CITY );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_PHONE, Constants.PROPERTY_KEY_PHONE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE, Constants.PROPERTY_KEY_MOBILE_PHONE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_LOGIN, Constants.PROPERTY_KEY_LOGIN );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_EMAIL, Constants.PROPERTY_KEY_EMAIL );
    }
    private static ReferenceList _lstContactModeList;
    private static ReferenceList _lstGenderList;
    private static final String SPLIT_PATTERN = ";";

    /**
     * private constructor for singleton
     */
    private DashboardIdentityUtils(  )
    {
    }
    
    public static DashboardIdentityUtils getInstance( )
    {
        if ( _instance == null )
        {
            _instance = new DashboardIdentityUtils( );
            _instance._identityService = SpringContextService.getBean( BEAN_IDENTITYSTORE_SERVICE );
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
            return _instance;
    }
    
    
    
    
    /**
     * return an dashboardIdentity from a identityDto
     *
     * @param identitySearchResponse
     *          identitySearchResponse to convert
     * @return dashboardIdentity initialized from provided identityDto
     */
    public DashboardIdentity convertToDashboardIdentity( IdentitySearchResponse identitySearchResponse )
    {     
    	return convertToDashboardIdentity( identitySearchResponse, null);
    }
    
   
    
    

    /**
     * return an dashboardIdentity from a identityDto
     *
     * @param identity
     *          identityDto to convert
     *          
     * @param  contractSearchResponse The App contract        
     * @return dashboardIdentity initialized from provided identityDto
     */
    public DashboardIdentity convertToDashboardIdentity( IdentitySearchResponse identitySearchResponse, ServiceContractSearchResponse contractSearchResponse )
    {
        DashboardIdentity dashboardIdentity = new DashboardIdentity(  );
        
        dashboardIdentity.setConnectionId( new DashboardAttribute( 
                Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID, 
                identitySearchResponse.getIdentities( ).get( 0 ).getConnectionId( ) ) );
        
        
        dashboardIdentity.setCustomerId( new DashboardAttribute(
                Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID,
                identitySearchResponse.getIdentities( ).get( 0 ).getCustomerId( ) ) );
        
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            dashboardIdentity.setAttribute( 
                    attributeMatch.getKey( ), 
                    getDashboardAttributeFromAttributeDtoKey (
                        identitySearchResponse, 
                        attributeMatch.getValue( ), 
                        attributeMatch.getKey( ),contractSearchResponse ) 
                    ) ;
        }

        return dashboardIdentity;
        
    }

    /**
     * return an identityDto from a DashboardIdentity
     *
     * @param dashboardIdentity
     *          dashboardIdentity to convert
     *  @param bOnlyMandatory true the IdentitityDTO must contains only Mandatory informations
     * @return identityDto initialized from provided dashboardIdentity
     */
    public Identity convertToIdentityDto( DashboardIdentity dashboardIdentity,boolean bOnlyMandatory )
    {      
        Identity identity = new Identity( );        
        identity.setConnectionId( dashboardIdentity.getConnectionId(  ).getValue( ) );
        identity.setCustomerId( dashboardIdentity.getCustomerId(  ).getValue( ) );
        
        List<CertifiedAttribute> listCertifiedAttribute = new ArrayList< >( );
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            DashboardAttribute dashboardAttribute = dashboardIdentity.getAttribute( attributeMatch.getKey( ) );
            if(!bOnlyMandatory || dashboardAttribute.isMandatory())
            { 	
                CertifiedAttribute certifiedAttribute = new CertifiedAttribute(  );
                certifiedAttribute.setKey( attributeMatch.getKey( ) );
                certifiedAttribute.setValue( dashboardAttribute.getValue( ) );
                certifiedAttribute.setCertificationProcess( dashboardAttribute.getCertifierCode( ) );
                certifiedAttribute.setCertificationDate( dashboardAttribute.getCertificateDate( ) );
                                
	            listCertifiedAttribute.add( certifiedAttribute );
            }
        }
        identity.setAttributes( listCertifiedAttribute );
        


        return identity;
    }
    
    /**
     * Get DashboardAttribute From AttributeDto
     * @param identitySearchResponse the identitySearchResponse
     * @param identityDtoAttributeKey the identityDto attribute key
     * @param dashboardAttributeKey the Dashboard attribute key
     * @param contractSearchResponse the contractSearchResponse
     * @return 
     */
    private DashboardAttribute getDashboardAttributeFromAttributeDtoKey ( IdentitySearchResponse identitySearchResponse, String identityDtoAttributeKey, String dashboardAttributeKey, ServiceContractSearchResponse contractSearchResponse )
    {
        
       Optional<fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.CertifiedAttribute> certifiedAttribute = identitySearchResponse.getIdentities( ).get( 0 ).getAttributes( )
               .stream( )
               .filter( attribute -> attribute.getKey( ).equals( identityDtoAttributeKey ) )
               .findAny( );
        
        DashboardAttribute dashboardAttribute = null;
        if ( certifiedAttribute.isPresent( ) )
        {
            if ( certifiedAttribute.get( ).getCertifier( ) != null )
            {
            	dashboardAttribute= new DashboardAttribute(
                    dashboardAttributeKey,
                    certifiedAttribute.get( ).getValue( ),
                    certifiedAttribute.get( ).getCertifier( ),
                    certifiedAttribute.get( ).getCertifier( ),
                    certifiedAttribute.get( ).getCertificationLevel( ),
                    null,
                    certifiedAttribute.get( ).getCertificationDate( ) );
            	
            }
            else
            {
            	dashboardAttribute= new DashboardAttribute(
                    dashboardAttributeKey,
                    certifiedAttribute.get( ).getValue( ) );
            }
            
        }
        else
        {
	        dashboardAttribute= new DashboardAttribute(
	            dashboardAttributeKey,
	            StringUtils.EMPTY
	            );
        }
        
        dashboardAttribute.setMandatory( isMandatoryAttribute( contractSearchResponse, dashboardAttributeKey ) );
        
        return dashboardAttribute;
    }
    
    /**
     * Return true if the attribute is mandatory
     * @param contractSearchResponse
     * @param identityAttributeKey
     * @return true if the attribute key is mandatory
     */
    public boolean isMandatoryAttribute ( ServiceContractSearchResponse contractSearchResponse, String identityAttributeKey )
    {
        if ( contractSearchResponse != null && contractSearchResponse.getServiceContract( ) != null 
                && contractSearchResponse.getServiceContract( ).getAttributeDefinitions( ) != null  )
        {
            return contractSearchResponse.getServiceContract( ).getAttributeDefinitions( )
                    .stream( )
                    .anyMatch( x -> x.getKeyName( ).equals( identityAttributeKey ) && x.getAttributeRight( ).isMandatory( ) );
        }
        return false;
    }
    
    /**
     * populaite DashboardIdentity from the request
     * @param identity the DashboardIdentity
     * @param request the HttpServletRequest
     */
    public void populateDashboardIdentity ( DashboardIdentity identity, HttpServletRequest request )
    {
        
        for ( String strAttributeKey : _mapAttributeKeyMatch.keySet( ) )
        {
            String attributeValue = request.getParameter( strAttributeKey );
            if ( attributeValue != null )
            {
                identity.setAttributeValue( strAttributeKey, attributeValue );
            }
        }
    }
    
    /**
     * Drop attributes from IdentityDto if there is a certificate. This is used to not update identity when a certificate is found.
     * @param identity 
     */
    
    public void filterByCertifier ( Identity identity )
    {
        List<String> certificationProcessNotCertifiable = Arrays.asList( Constants.PROPERTY_CERTIFICATION_PROCESS_NOT_CERTIFIABLE.split( ";" ) );
        filterByCertifier( identity, certificationProcessNotCertifiable );
    }
    
    /**
     * Drop attributes from IdentityDto if there is a certificate. This is used to not update identity when a certificate is found.
     * @param identity
     * @param listCertificationProcessNotCertifiable
     */
    public void filterByCertifier ( Identity identity, List<String> listCertificationProcessNotCertifiable )
    {
        List<CertifiedAttribute> listCertifiedAttribute = new ArrayList< >();
        
        if( identity != null && identity.getAttributes( ) != null )
        {        
            for ( CertifiedAttribute certifiedAttribute : identity.getAttributes( ) )
            {
                if ( certifiedAttribute.getCertificationProcess( ) == null 
                    || listCertificationProcessNotCertifiable.contains( certifiedAttribute.getCertificationProcess( ) ) )
                {
                    listCertifiedAttribute.add( certifiedAttribute );
                }
            }
            identity.setAttributes( listCertifiedAttribute );
        }
    }
    
    
    /**
     * return identitySearchResponse from strConnectionId
     *
     * @param strConnectionId
     *            user connection id
     * @return IdentitySearchResponse
     * @throws UserNotSignedException
     */
    public IdentitySearchResponse getIdentity( String strConnectionId )
    {
        IdentitySearchResponse identitySearchResponse = null;
        
        try
        {
            identitySearchResponse = _identityService.getIdentityByConnectionId( strConnectionId, DASHBOARD_APP_CODE );
        }
        catch( IdentityStoreException | AppException infe )
        {
            AppLogService.error( "Identity Not Found for guig:" + strConnectionId, infe );
        }

        return identitySearchResponse;
    }
    
    /**
     * Update Identity from an IdentityDto.
     *
     * @param identityDto            identity Data transfer Object
     * @throws IdentityNotFoundException the identity not found exception
     * @throws AppException the app exception
     */
    public void updateIdentity( Identity identity )
    {
        IdentityChangeRequest identityChangeRequest = buildIdentityChangeDto( identity );
        
        try
        {
            if( !StringUtils.isEmpty( identity.getCustomerId( ) ) )
            {
                _identityService.updateIdentity( identityChangeRequest.getIdentity( ).getCustomerId( ), identityChangeRequest, DASHBOARD_APP_CODE );
            }
            else
            {
                _identityService.createIdentity( identityChangeRequest, DASHBOARD_APP_CODE );               
            }
        } catch ( AppException | IdentityStoreException e )
        {
            AppLogService.error( "Error when creating or updating the identity for connectionId {}", e, identity.getConnectionId( ) );
        }
        	
    }
    
    /**
     * build a changeDto from Identity.
     *
     * @param identity            identity to update
     * @return IdentityChangeDto
     */
    private IdentityChangeRequest buildIdentityChangeDto( Identity identity )
    {
        IdentityChangeRequest identityChangeRequest = new IdentityChangeRequest( );
                
        RequestAuthor requestAuthor = new RequestAuthor( );
        requestAuthor.setName( DASHBOARD_APP_CODE );
        requestAuthor.setType( AuthorType.owner );
        
        identityChangeRequest.setOrigin( requestAuthor );
        identityChangeRequest.setIdentity( identity );

        return identityChangeRequest;
    }
}
