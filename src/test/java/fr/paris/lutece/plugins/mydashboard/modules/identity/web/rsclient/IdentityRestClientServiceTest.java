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
package fr.paris.lutece.plugins.mydashboard.modules.identity.web.rsclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.test.LuteceTestCase;

/**
 *
 * @author levy
 */
public class IdentityRestClientServiceTest extends LuteceTestCase
{
    private static final String VALUE_NAME = "lastname";
    private static final String VALUE_PREFERREDUSERNAME = "preferredusername";
    private static final String VALUE_FIRSTNAME = "firstname";
    private static final String VALUE_GENDER = "gender";
    private static final String VALUE_BIRTHDATE = "birthdate";
    private static final String VALUE_BIRTHPLACE = "birthplace";
    private static final String VALUE_BIRTHCOUNTRY = "birthcountry";
    private static final String VALUE_ADDRESS = "address";
    private static final String VALUE_ADDRESSDETAIL = "detail";
    private static final String VALUE_ADDRESS_POSTAL_CODE = "addressPostalcode";
    private static final String VALUE_ADDRESS_CITY = "addressCity";
    private static final String VALUE_BILLING_ADDRESS = "billingAddress";
    private static final String VALUE_BILLING_ADDRESSDETAIL = "billingAddressDetail";
    private static final String VALUE_BILLING_ADDRESS_POSTAL_CODE = "billingAddressPostalcode";
    private static final String VALUE_BILLING_ADDRESS_CITY = "billingAddressCity";
    private static final String VALUE_PHONE = "phone";
    private static final String VALUE_EMAIL = "email";
    private static final String VALUE_LOGIN = "login";
    private static final String VALUE_MOBILE_PHONE = "mobilePhone";
    private static final String VALUE_PREFERRED_CONTACT = "preferredContactMode";
    private static final String VALUE_ACCEPTNEWS = "true";
    private static final String VALUE_ACCEPTSURVEY = "false";

    /**
     * Test of getIdentityAttributes rest service of plugin-identitystore.
     */
    @Test
    public void testConvertDashboardDto( )
    {
        IdentityDto identityDto = new IdentityDto( );
        identityDto.setConnectionId( "connectionId" );
        identityDto.setCustomerId( "1" );

        Map<String, AttributeDto> mapAttributes = new HashMap<String, AttributeDto>( );

        AttributeDto attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_NAME );
        attribute.setValue( VALUE_NAME );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_PREFERREDUSERNAME );
        attribute.setValue( VALUE_PREFERREDUSERNAME );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_FIRSTNAME );
        attribute.setValue( VALUE_FIRSTNAME );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_GENDER );
        attribute.setValue( VALUE_GENDER );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHDATE );
        attribute.setValue( VALUE_BIRTHDATE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHPLACE );
        attribute.setValue( VALUE_BIRTHPLACE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHCOUNTRY );
        attribute.setValue( VALUE_BIRTHCOUNTRY );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS );
        attribute.setValue( VALUE_ADDRESS );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESSDETAIL );
        attribute.setValue( VALUE_ADDRESSDETAIL );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE );
        attribute.setValue( VALUE_ADDRESS_POSTAL_CODE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS_CITY );
        attribute.setValue( VALUE_ADDRESS_CITY );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BILLING_ADDRESS );
        attribute.setValue( VALUE_BILLING_ADDRESS );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BILLING_ADDRESSDETAIL );
        attribute.setValue( VALUE_BILLING_ADDRESSDETAIL );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BILLING_ADDRESS_POSTAL_CODE );
        attribute.setValue( VALUE_BILLING_ADDRESS_POSTAL_CODE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BILLING_ADDRESS_CITY );
        attribute.setValue( VALUE_BILLING_ADDRESS_CITY );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_PHONE );
        attribute.setValue( VALUE_PHONE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_EMAIL );
        attribute.setValue( VALUE_EMAIL );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_LOGIN );
        attribute.setValue( VALUE_LOGIN );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_MOBILE_PHONE );
        attribute.setValue( VALUE_MOBILE_PHONE );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_PREFERRED_CONTACT );
        attribute.setValue( VALUE_PREFERRED_CONTACT );
        mapAttributes.put( attribute.getKey( ), attribute );
        identityDto.setAttributes( mapAttributes );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ACCEPT_NEWS );
        attribute.setValue( VALUE_ACCEPTNEWS );
        mapAttributes.put( attribute.getKey( ), attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ACCEPT_SURVEY );
        attribute.setValue( VALUE_ACCEPTSURVEY );
        mapAttributes.put( attribute.getKey( ), attribute );

        DashboardIdentity dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identityDto );
        IdentityDto identityDto2 = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity );

        assertEquals( VALUE_NAME, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_NAME ).getValue( ) );
        assertEquals( VALUE_PREFERREDUSERNAME, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_PREFERREDUSERNAME ).getValue( ) );
        assertEquals( VALUE_FIRSTNAME, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_FIRSTNAME ).getValue( ) );
        assertEquals( VALUE_GENDER, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_GENDER ).getValue( ) );
        assertEquals( VALUE_BIRTHDATE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BIRTHDATE ).getValue( ) );
        assertEquals( VALUE_BIRTHPLACE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BIRTHPLACE ).getValue( ) );
        assertEquals( VALUE_BIRTHCOUNTRY, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BIRTHCOUNTRY ).getValue( ) );
        assertEquals( VALUE_ADDRESS, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ADDRESS ).getValue( ) );
        assertEquals( VALUE_ADDRESSDETAIL, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ADDRESSDETAIL ).getValue( ) );
        assertEquals( VALUE_ADDRESS_POSTAL_CODE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE ).getValue( ) );
        assertEquals( VALUE_ADDRESS_CITY, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ADDRESS_CITY ).getValue( ) );
        assertEquals( VALUE_BILLING_ADDRESS, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BILLING_ADDRESS ).getValue( ) );
        assertEquals( VALUE_BILLING_ADDRESSDETAIL, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BILLING_ADDRESSDETAIL ).getValue( ) );
        assertEquals( VALUE_BILLING_ADDRESS_POSTAL_CODE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BILLING_ADDRESS_POSTAL_CODE ).getValue( ) );
        assertEquals( VALUE_BILLING_ADDRESS_CITY, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_BILLING_ADDRESS_CITY ).getValue( ) );
        assertEquals( VALUE_PHONE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_PHONE ).getValue( ) );
        assertEquals( VALUE_PREFERRED_CONTACT, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_PREFERRED_CONTACT ).getValue( ) );
        assertEquals( VALUE_MOBILE_PHONE, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_MOBILE_PHONE ).getValue( ) );

        // PPY says it is normal that these attributes are not modified
        // assertEquals ( VALUE_LOGIN, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_LOGIN ).getValue() );
        // assertEquals ( VALUE_ACCEPTNEWS, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ACCEPT_NEWS ).getValue() );
        // assertEquals ( VALUE_ACCEPTSURVEY, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ACCEPT_SURVEY ).getValue() );
    }
}
