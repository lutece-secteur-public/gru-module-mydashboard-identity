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
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.MobilePhone;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * class to help managing identity feature
 *
 */
public final class DashboardIdentityUtils
{
    /**
     * private constructor
     */
    private DashboardIdentityUtils(  )
    {
    }

    /**
     * return an dashboardIdentity from a identityDto
     *
     * @param identity
     *          identityDto to convert
     * @return dashboardIdentity initialized from provided identityDto
     */
    public static DashboardIdentity convertToDashboardIdentity( IdentityDto identity )
    {
        DashboardIdentity dashboardIdentity = new DashboardIdentity(  );
        dashboardIdentity.setConnectionId( identity.getConnectionId(  ) );
        dashboardIdentity.setCustomerId( identity.getCustomerId(  ) );

        if ( identity.getAttributes(  ) != null )
        {
            AttributeDto attribute = identity.getAttributes(  )
                                             .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_NAME ) );

            if ( attribute != null )
            {
                dashboardIdentity.setLastName( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setLastName( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHNAME ) );

            if ( attribute != null )
            {
                dashboardIdentity.setBirthname( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setBirthname( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_FIRSTNAME ) );

            if ( attribute != null )
            {
                dashboardIdentity.setFirstname( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setFirstname( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  ).get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_GENDER ) );

            if ( attribute != null )
            {
                dashboardIdentity.setGender( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setGender( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHDATE ) );

            if ( attribute != null )
            {
                dashboardIdentity.setBirthdate( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setBirthdate( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHPLACE ) );

            if ( attribute != null )
            {
                dashboardIdentity.setBirthplace( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setBirthplace( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_NUMBER ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressNumber( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressNumber( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_SUFFIX ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressSuffix( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressSuffix( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_STREET ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressStreet( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressStreet( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_BUILDING ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressBuilding( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressBuilding( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_STAIR ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressStair( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressStair( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE ) );

            if ( attribute != null )
            {
                dashboardIdentity.setAddressPostalcode( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setAddressPostalcode( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  ).get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_EMAIL ) );

            if ( attribute != null )
            {
                dashboardIdentity.setEmail( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setEmail( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  ).get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_PHONE ) );

            if ( attribute != null )
            {
                dashboardIdentity.setPhone( attribute.getValue(  ) );
            }
            else
            {
                dashboardIdentity.setPhone( StringUtils.EMPTY );
            }

            attribute = identity.getAttributes(  )
                                .get( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_MOBILE_PHONE ) );

            if ( attribute != null )
            {
                MobilePhone mobilePhone = new MobilePhone(  );
                mobilePhone.setMobilePhoneNumber( attribute.getValue(  ) );

                if ( attribute.isCertified(  ) )
                {
                    mobilePhone.setMobilePhoneCertified( true );
                }

                dashboardIdentity.setMobilePhone( mobilePhone );
            }
            else
            {
                MobilePhone mobilePhone = new MobilePhone(  );
                mobilePhone.setMobilePhoneNumber( StringUtils.EMPTY );
                dashboardIdentity.setMobilePhone( mobilePhone );
            }
        }
        else
        {
            // initialize mobile phone object to let bean populate method fill the
            // field
            MobilePhone mobilePhone = new MobilePhone(  );
            mobilePhone.setMobilePhoneNumber( StringUtils.EMPTY );
            dashboardIdentity.setMobilePhone( mobilePhone );
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
    public static IdentityDto convertToIdentityDto( DashboardIdentity dashboardIdentity )
    {
        IdentityDto identityDto = new IdentityDto(  );
        identityDto.setConnectionId( dashboardIdentity.getConnectionId(  ) );
        identityDto.setCustomerId( dashboardIdentity.getCustomerId(  ) );

        Map<String, AttributeDto> mapAttributes = new HashMap<String, AttributeDto>(  );

        AttributeDto attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_NAME ) );
        attribute.setValue( dashboardIdentity.getLastName(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHNAME ) );
        attribute.setValue( dashboardIdentity.getBirthname(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_FIRSTNAME ) );
        attribute.setValue( dashboardIdentity.getFirstname(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_GENDER ) );
        attribute.setValue( dashboardIdentity.getGender(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHDATE ) );
        attribute.setValue( dashboardIdentity.getBirthdate(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_BIRTHPLACE ) );
        attribute.setValue( dashboardIdentity.getBirthplace(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_NUMBER ) );
        attribute.setValue( dashboardIdentity.getAddressNumber(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_SUFFIX ) );
        attribute.setValue( dashboardIdentity.getAddressSuffix(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_STREET ) );
        attribute.setValue( dashboardIdentity.getAddressStreet(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_BUILDING ) );
        attribute.setValue( dashboardIdentity.getAddressBuilding(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_STAIR ) );
        attribute.setValue( dashboardIdentity.getAddressStair(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_ADDRESS_POSTAL_CODE ) );
        attribute.setValue( dashboardIdentity.getAddressPostalcode(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_PHONE ) );
        attribute.setValue( dashboardIdentity.getPhone(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_EMAIL ) );
        attribute.setValue( dashboardIdentity.getEmail(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        attribute = new AttributeDto(  );
        attribute.setKey( AppPropertiesService.getProperty( Constants.PROPERTY_KEY_MOBILE_PHONE ) );
        attribute.setValue( dashboardIdentity.getMobilePhone(  ).getMobilePhoneNumber(  ) );
        mapAttributes.put( attribute.getKey(  ), attribute );

        identityDto.setAttributes( mapAttributes );

        return identityDto;
    }
}
