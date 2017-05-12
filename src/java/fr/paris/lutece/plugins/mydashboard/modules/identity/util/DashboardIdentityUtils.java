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

import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.CertificateDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardAttribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * class to help managing identity feature
 *
 */
public class DashboardIdentityUtils
{
    private static DashboardIdentityUtils _instance;   
    
    //For matching on DBAttributes and Identity store attributes
    private static Map<String,String> _mapAttributeKeyMatch;
    
    /**
     * private constructor for singleton
     */
    private DashboardIdentityUtils(  )
    {
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
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS, Constants.PROPERTY_KEY_BILLING_ADDRESS );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_DETAIL, Constants.PROPERTY_KEY_BILLING_ADDRESSDETAIL );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_POSTAL_CODE, Constants.PROPERTY_KEY_BILLING_ADDRESS_POSTAL_CODE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_CITY, Constants.PROPERTY_KEY_BILLING_ADDRESS_CITY );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_PHONE, Constants.PROPERTY_KEY_PHONE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_CONTACT_MODE, Constants.PROPERTY_KEY_PREFERRED_CONTACT );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE, Constants.PROPERTY_KEY_MOBILE_PHONE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ACCEPT_NEWS, Constants.PROPERTY_KEY_ACCEPT_NEWS );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_ACCEPT_SURVEY, Constants.PROPERTY_KEY_ACCEPT_SURVEY );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_LOGIN, Constants.PROPERTY_KEY_LOGIN );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_EMAIL, Constants.PROPERTY_KEY_EMAIL );
    }
    
    public static DashboardIdentityUtils getInstance( )
    {
        if ( _instance == null )
        {
            _instance = new DashboardIdentityUtils( );
        }
            return _instance;
    }
    

    /**
     * return an dashboardIdentity from a identityDto
     *
     * @param identity
     *          identityDto to convert
     * @return dashboardIdentity initialized from provided identityDto
     */
    public DashboardIdentity convertToDashboardIdentity( IdentityDto identity )
    {
        DashboardIdentity dashboardIdentity = new DashboardIdentity(  );
        
        dashboardIdentity.setConnectionId( new DashboardAttribute( 
                Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID, 
                identity.getConnectionId( ) ) );
        
        
        dashboardIdentity.setCustomerId( new DashboardAttribute(
                Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID,
                identity.getCustomerId(  ) ) );
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            dashboardIdentity.setAttribute( 
                    attributeMatch.getKey( ), 
                    getDashboardAttributeFromAttributeDtoKey (
                        identity, 
                        attributeMatch.getValue( ), 
                        attributeMatch.getKey( )
                    ) );
        }

        return dashboardIdentity;
    }

    /**
     * return an identityDto from a DashboardIdentity
     *
     * @param dashboardIdentity
     *          dashboardIdentity to convert
     * @return identityDto initialized from provided dashboardIdentity
     */
    public IdentityDto convertToIdentityDto( DashboardIdentity dashboardIdentity )
    {
        IdentityDto identityDto = new IdentityDto(  );
        identityDto.setConnectionId( dashboardIdentity.getConnectionId(  ).getValue( ) );
        identityDto.setCustomerId( dashboardIdentity.getCustomerId(  ).getValue( ) );

        Map<String, AttributeDto> mapAttributes = new HashMap<String, AttributeDto>(  );
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            DashboardAttribute dashboardAttribute = dashboardIdentity.getAttribute( attributeMatch.getKey( ) );
            AttributeDto attribute = new AttributeDto(  );
            attribute.setKey( attributeMatch.getValue( ) );
            attribute.setValue( dashboardAttribute.getValue( ) );
            attribute.setCertified( dashboardAttribute.getCertifierCode( ) != null );
            
            if ( attribute.getCertified( ) )
            {
                CertificateDto certificate = new CertificateDto( );
                certificate.setCertificateExpirationDate( dashboardAttribute.getExpirationDate( ) );
                certificate.setCertifierCode( dashboardAttribute.getCertifierCode( ) );
                certificate.setCertifierLevel( dashboardAttribute.getCertifierLevel( ) );
                certificate.setCertifierName( dashboardAttribute.getCertifierName( ) );
                attribute.setCertificate( certificate );
            }
            mapAttributes.put( attribute.getKey(  ), attribute );
        }
        
        identityDto.setAttributes( mapAttributes );

        return identityDto;
    }
    
    /**
     * Get DashboardAttribute From AttributeDto
     * @param identity the IdentityDto
     * @param identityDtoAttributeKey the identityDto attribute key
     * @param dashboardAttributeKey the Dashboard attribute key
     * @return 
     */
    private DashboardAttribute getDashboardAttributeFromAttributeDtoKey ( IdentityDto identity, String identityDtoAttributeKey, String dashboardAttributeKey )
    {
        AttributeDto attribute = identity.getAttributes( ).get( identityDtoAttributeKey );
        if ( attribute != null )
        {
            if ( attribute.getCertificate( ) != null )
            {
                return new DashboardAttribute(
                    dashboardAttributeKey,
                    attribute.getValue( ),
                    attribute.getCertificate( ).getCertifierCode( ),
                    attribute.getCertificate( ).getCertifierName( ),
                    attribute.getCertificate( ).getCertifierLevel( ),
                    attribute.getCertificate( ).getCertificateExpirationDate( ) );
            }
            else
            {
                return new DashboardAttribute(
                    dashboardAttributeKey,
                    attribute.getValue( ) );
            }
            
        }
        return new DashboardAttribute(
            dashboardAttributeKey,
            StringUtils.EMPTY
            );
    }
    
    /**
     * populaite DashboardIdentity from the request
     * @param identity the DashboardIdentity
     * @param request the HttpServletRequest
     */
    public void populateDashboardIdentity ( DashboardIdentity identity, HttpServletRequest request )
    {
        String connectionId = request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID );
        String customerId = request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID );
        
        if ( connectionId != null )
        {
            identity.setAttributeValue( Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID, connectionId );
        }
        if ( customerId != null )
        {
            identity.setAttributeValue( Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID, customerId );
        }
        
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
    
    public void filterByCertifier ( IdentityDto identity )
    {
        Map<String,AttributeDto> mapAttributes = new HashMap<String,AttributeDto>( );
        for ( Map.Entry<String,AttributeDto> attribute : identity.getAttributes( ).entrySet( ) )
        {
            if ( attribute.getValue( ).getCertificate( ) == null 
                || StringUtils.isEmpty( attribute.getValue( ).getCertificate( ).getCertifierCode( ) ) )
            {
                mapAttributes.put( attribute.getKey( ), attribute.getValue( ) );
            }
        }
        identity.setAttributes( mapAttributes );
    }
}
