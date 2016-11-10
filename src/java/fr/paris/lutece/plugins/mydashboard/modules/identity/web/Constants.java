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

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * This class provides constants for identity services
 *
 */
public final class Constants
{
    // properties
    
    
    public static final String PROPERTY_APPLICATION_CODE = "mydashboard.identity.application.code";
    public static final String PROPERTY_APPLICATION_NAME = "mydashboard.identity.application.name";
    public static final String PROPERTY_KEY_LOGIN = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.login" );
    public static final String PROPERTY_KEY_GENDER = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.gender" );
    public static final String PROPERTY_KEY_BIRTHDATE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.bdate" );
    public static final String PROPERTY_KEY_BIRTHPLACE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.bplace" );
    public static final String PROPERTY_KEY_NAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.name.family" );
    public static final String PROPERTY_KEY_PREFERREDUSERNAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.name.preferred-username" );
    public static final String PROPERTY_KEY_FIRSTNAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.name.given" );
    public static final String PROPERTY_KEY_BIRTHCOUNTRY = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.bcountry" );
    public static final String PROPERTY_KEY_ADDRESS = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.home-info.postal.address" );
    public static final String PROPERTY_KEY_ADDRESSDETAIL = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.home-info.postal.addressdetail" );
    public static final String PROPERTY_KEY_ADDRESS_POSTAL_CODE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.home-info.postal.postalcode" );
    public static final String PROPERTY_KEY_ADDRESS_CITY = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.home-info.postal.city" );
    public static final String PROPERTY_KEY_BILLING_ADDRESS = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.billing-info.postal.address" );
    public static final String PROPERTY_KEY_BILLING_ADDRESSDETAIL = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.billing-info.postal.addressdetail" );
    public static final String PROPERTY_KEY_BILLING_ADDRESS_POSTAL_CODE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.billing-info.postal.postalcode" );
    public static final String PROPERTY_KEY_BILLING_ADDRESS_CITY = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.billing-info.postal.city" );
    public static final String PROPERTY_KEY_EMAIL = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.home-info.online.email" );
    public static final String PROPERTY_KEY_MOBILE_PHONE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.home-info.telecom.mobile.number" );
    public static final String PROPERTY_KEY_PHONE = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.home-info.telecom.telephone.number" );
    public static final String PROPERTY_KEY_PREFERRED_CONTACT = AppPropertiesService.getProperty( 
            "mydashboard.identity.attribute.user.home-info.preferred-contact-mode" );
    public static final String PROPERTY_KEY_GENDER_LIST = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.listref.gender" );
    public static final String PROPERTY_KEY_CONTACT_MODE_LIST = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.listref.contact-mode" );
    public static final String PROPERTY_KEY_ACCEPT_NEWS = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.user.acceptnews" );
    public static final String PROPERTY_KEY_ACCEPT_SURVEY = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.user.acceptsurvey" );

    //Fields validation messages
    public static final String MESSAGE_ERROR_UPDATE_IDENTITY = "module.mydashboard.identity.message.error.identity.update";
    public static final String MESSAGE_INFO_IDENTITY_UPDATED = "module.mydashboard.identity.message.info.identity.updated";
    public static final String MESSAGE_ERROR_VALIDATION_LASTNAME = "module.mydashboard.identity.message.error.identity.check.format.lastname";
    public static final String MESSAGE_ERROR_VALIDATION_PREFFEREDUSERNAME = "module.mydashboard.identity.message.error.identity.check.format.preferredusername";
    public static final String MESSAGE_ERROR_VALIDATION_FIRSTNAME = "module.mydashboard.identity.message.error.identity.check.format.firstname";
    public static final String MESSAGE_ERROR_VALIDATION_BIRTHDATE = "module.mydashboard.identity.message.error.identity.check.format.birthdate";
    public static final String MESSAGE_ERROR_VALIDATION_BIRTHPLACE = "module.mydashboard.identity.message.error.identity.check.format.birthplace";
    public static final String MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY = "module.mydashboard.identity.message.error.identity.check.format.birthcountry";
    public static final String MESSAGE_ERROR_VALIDATION_BIRTHCITY = "module.mydashboard.identity.message.error.identity.check.format.birthcity";
    public static final String MESSAGE_ERROR_VALIDATION_ADDRESS = "module.mydashboard.identity.message.error.identity.check.format.address";
    public static final String MESSAGE_ERROR_VALIDATION_ADDRESS_DETAIL = "module.mydashboard.identity.message.error.identity.check.format.address-detail";
    public static final String MESSAGE_ERROR_VALIDATION_ADDRESS_POSTALCODE = "module.mydashboard.identity.message.error.identity.check.format.address-postalcode";
    public static final String MESSAGE_ERROR_VALIDATION_ADDRESS_CITY = "module.mydashboard.identity.message.error.identity.check.format.address-city";
    public static final String MESSAGE_ERROR_VALIDATION_PHONE = "module.mydashboard.identity.message.error.identity.check.format.phone";
    public static final String MESSAGE_ERROR_VALIDATION_MOBILEPHONE = "module.mydashboard.identity.message.error.identity.check.format.mobilephone";
    public static final String MESSAGE_ERROR_VALIDATION_EMAIL = "module.mydashboard.identity.message.error.identity.check.format.email";
    public static final String MESSAGE_ERROR_EMAIL_EMPTY = "module.mydashboard.identity.message.error.identity.check.empty.email";
    public static final String MESSAGE_ERROR_TELEPHONE_EMPTY = "module.mydashboard.identity.message.error.identity.check.empty.phone";

    // FIELDS VALIDATION REGEXP
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_LAST_NAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.lastname" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_PREFERREDUSERNAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.preferredusername" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_FIRSTNAME = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.firstname" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.birthplace" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.birthcountry" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_BIRTHDATE = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.birthdate" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_PHONE = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.phone" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_MOBILEPHONE = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.mobilephone" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.address" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_DETAIL = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.address-detail" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_POSTALCODE = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.address-postalcode" );
    public static final String PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_CITY = AppPropertiesService.getProperty( 
            "mydashboard.identity.application.validation.regex.address-city" );

    public static final String NO_CUSTOMER_ID = StringUtils.EMPTY;
    
    /**
     * Default constructor
     */
    private Constants(  )
    {
    }
}
