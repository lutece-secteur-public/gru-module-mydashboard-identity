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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AttributeDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
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
    private static final String VALUE_PHONE = "phone";
    private static final String VALUE_EMAIL = "email";
    private static final String VALUE_LOGIN = "login";
    private static final String VALUE_MOBILE_PHONE = "mobilePhone";

    /**
     * Test of getIdentityAttributes rest service of plugin-identitystore.
     */
    @Test
    public void testConvertDashboardDto( )
    {       
       
        
        List<IdentityDto> listQualifiedIdentity = new ArrayList<>();
        IdentityDto qualifiedIdentity = new IdentityDto( );
        qualifiedIdentity.setConnectionId( "connectionId" );
        qualifiedIdentity.setCustomerId( "1" );

        List<AttributeDto> attributes = new ArrayList< >( );

        AttributeDto attribute = new AttributeDto( );
        
        attribute.setKey( Constants.PROPERTY_KEY_NAME );
        attribute.setValue( VALUE_NAME );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_PREFERREDUSERNAME );
        attribute.setValue( VALUE_PREFERREDUSERNAME );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_FIRSTNAME );
        attribute.setValue( VALUE_FIRSTNAME );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_GENDER );
        attribute.setValue( VALUE_GENDER );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHDATE );
        attribute.setValue( VALUE_BIRTHDATE );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHPLACE );
        attribute.setValue( VALUE_BIRTHPLACE );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_BIRTHCOUNTRY );
        attribute.setValue( VALUE_BIRTHCOUNTRY );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS );
        attribute.setValue( VALUE_ADDRESS );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESSDETAIL );
        attribute.setValue( VALUE_ADDRESSDETAIL );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE );
        attribute.setValue( VALUE_ADDRESS_POSTAL_CODE );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_ADDRESS_CITY );
        attribute.setValue( VALUE_ADDRESS_CITY );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_PHONE );
        attribute.setValue( VALUE_PHONE );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_EMAIL );
        attribute.setValue( VALUE_EMAIL );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_LOGIN );
        attribute.setValue( VALUE_LOGIN );
        attributes.add( attribute );

        attribute = new AttributeDto( );
        attribute.setKey( Constants.PROPERTY_KEY_MOBILE_PHONE );
        attribute.setValue( VALUE_MOBILE_PHONE );
        attributes.add( attribute );

        qualifiedIdentity.setAttributes( attributes );
        
      
        
        DashboardIdentity dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( qualifiedIdentity );
        IdentityDto identityDto2 = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity,false );

        for ( AttributeDto att : identityDto2.getAttributes( ) )
        {
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_NAME  ) )
            {
                assertEquals( VALUE_NAME, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_PREFERREDUSERNAME  ) )
            {
                assertEquals( VALUE_PREFERREDUSERNAME, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_FIRSTNAME  ) )
            {
                assertEquals( VALUE_FIRSTNAME, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_GENDER  ) )
            {
                assertEquals( VALUE_GENDER, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_BIRTHDATE  ) )
            {
                assertEquals( VALUE_BIRTHDATE, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_BIRTHPLACE  ) )
            {
                assertEquals( VALUE_BIRTHPLACE, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_BIRTHCOUNTRY  ) )
            {
                assertEquals( VALUE_BIRTHCOUNTRY, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_ADDRESS ) )
            {
                assertEquals( VALUE_ADDRESS, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_ADDRESSDETAIL ) )
            {
                assertEquals( VALUE_ADDRESSDETAIL, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE  ) )
            {
                assertEquals( VALUE_ADDRESS_POSTAL_CODE, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_ADDRESS_CITY  ) )
            {
                assertEquals( VALUE_ADDRESS_CITY, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_PHONE  ) )
            {
                assertEquals( VALUE_PHONE, att.getValue( ) );
            }
            if( att.getKey( ).equals( Constants.PROPERTY_KEY_MOBILE_PHONE ) )
            {
                assertEquals( VALUE_MOBILE_PHONE, att.getValue( ) );
            }
        }
        
        // PPY says it is normal that these attributes are not modified
        // assertEquals ( VALUE_LOGIN, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_LOGIN ).getValue() );
        // assertEquals ( VALUE_ACCEPTNEWS, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ACCEPT_NEWS ).getValue() );
        // assertEquals ( VALUE_ACCEPTSURVEY, identityDto2.getAttributes( ).get( Constants.PROPERTY_KEY_ACCEPT_SURVEY ).getValue() );
    }
}
