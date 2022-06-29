/*
 * Copyright (c) 2002-2021, Mairie de Paris
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
package fr.paris.lutece.plugins.mydashboard.modules.identity.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import fr.paris.lutece.plugins.identitystore.v2.web.rs.dto.ApplicationRightsDto;
import fr.paris.lutece.plugins.identitystore.v2.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v2.web.service.IdentityService;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardAttribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;



/**
 * class DashboardIdentityService
 */
public class DashboardIdentityService implements IDashBoardIdentityService
{
    
    /** The instance od DashBoard Identity */
    private static DashboardIdentityService _instance;   
    
    /** The Constant BEAN_IDENTITYSTORE_SERVICE. */
    private static final String BEAN_IDENTITYSTORE_SERVICE = "mydashboard-identity.identitystore.service";
    private static final String DEFAULT_ERROR="error";
    
    
    /** The identity service. */
    private IdentityService _identityService;
    
    /** The Constant DASHBOARD_APP_CODE. */
    private static final String DASHBOARD_APP_CODE = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE );
  
    /** The lst contact mode list. */
    private static ReferenceList _lstContactModeList;
    
    /** The lst gender list. */
    private static ReferenceList _lstGenderList;
    
    /** The Constant SPLIT_PATTERN. */
    private static final String SPLIT_PATTERN = ";";

    /**
     * private constructor for singleton.
     */
    private DashboardIdentityService(  )
    {
    }
    
    
    /**
     * Gets the single instance of DashboardIdentityService.
     *
     * @return single instance of DashboardIdentityService
     */
    public static IDashBoardIdentityService getInstance( )
    {
        if ( _instance == null )
        {
            _instance = new DashboardIdentityService( );
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
     * {@inheritDoc}
     */
    @Override

	public DashboardIdentity getDashBoardIdentity(String strApplicationCode)throws AppException
    {
    	return getDashBoardIdentity(strApplicationCode,null);
    	
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
	public DashboardIdentity getDashBoardIdentity(String strApplicationCode,String strGuid)throws AppException
    {
    	IdentityDto identityDto=null; 
    	DashboardIdentity dashboardIdentity=null;
    	 ApplicationRightsDto	applicationRightsDto=   _identityService.getApplicationRights(strApplicationCode);
    	 if( !StringUtils.isEmpty( strGuid ))
    	 {
    		 identityDto = DashboardIdentityUtils.getInstance( ).getIdentityDto( strGuid );
    	 }
    	 else
    	 {
    		  identityDto = new IdentityDto( );
              identityDto.setConnectionId( strGuid );
           
    	 }
    	 dashboardIdentity= DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identityDto, applicationRightsDto);
         return dashboardIdentity;	
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
	public Map<String,String> checkDashboardIdentityFields( DashboardIdentity dashboardIdentity, HttpServletRequest request,boolean bOnlyCheckMandatory )
    {
    	
    	Map<String,String> hashErrors=new HashMap<String,String>();
     

        String strValidateLastName = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_LAST_NAME, Constants.MESSAGE_ERROR_VALIDATION_LASTNAME,dashboardIdentity.getLastName().isMandatory());
        
        if ( !strValidateLastName.isEmpty( ) && (! bOnlyCheckMandatory || dashboardIdentity.getLastName().isMandatory()))
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME,strValidateLastName );
        }

        String strValidatePreferredUsername = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_PREFERREDUSERNAME, Constants.MESSAGE_ERROR_VALIDATION_PREFFEREDUSERNAME,dashboardIdentity.getPreferredUsername().isMandatory());

        if ( !strValidatePreferredUsername.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getPreferredUsername().isMandatory())  )
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME,strValidatePreferredUsername );
        }

        String strValidateFirstname = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_FIRSTNAME, Constants.MESSAGE_ERROR_VALIDATION_FIRSTNAME,dashboardIdentity.getFirstname().isMandatory() );

        if ( !strValidateFirstname.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getFirstname().isMandatory())  )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME,strValidateFirstname );
        }

        String strValidateBirthplace = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE,dashboardIdentity.getBirthplace().isMandatory() );

        if ( !strValidateBirthplace.isEmpty( )  &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthplace().isMandatory())   )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE,strValidateBirthplace );
        }

        String strValidateBirthDate = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHDATE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHDATE,dashboardIdentity.getBirthdate().isMandatory());

        if ( !strValidateBirthDate.isEmpty( )  &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthdate().isMandatory())  )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, strValidateBirthDate );
        }
        
        

        String strValidateBirthCountry = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY , Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY, Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY ,dashboardIdentity.getBirthcountry().isMandatory());

        if ( !strValidateBirthCountry.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthcountry().isMandatory()))
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY, strValidateBirthCountry );
        }

        String strValidateEmail = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_EMAIL , null, Constants.MESSAGE_ERROR_VALIDATION_EMAIL,dashboardIdentity.getEmail().isMandatory() );

        if ( !strValidateEmail.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getEmail( ).isMandatory()) )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_EMAIL,strValidateEmail );
        }

        String strValidatePhone =  getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_PHONE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_PHONE, Constants.MESSAGE_ERROR_VALIDATION_PHONE,dashboardIdentity.getPhone().isMandatory() );

        if ( !strValidatePhone.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getPhone( ).isMandatory()))
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_PHONE, strValidatePhone );
        }

        String strValidateMobilePhone = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_MOBILEPHONE, Constants.MESSAGE_ERROR_VALIDATION_MOBILEPHONE ,dashboardIdentity.getMobilePhone().isMandatory());

        if ( !strValidateMobilePhone.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getMobilePhone( ).isMandatory()))
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE , strValidateMobilePhone );
        }

        String strValidateAdresse = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS,dashboardIdentity.getAddress().isMandatory());

        if ( !strValidateAdresse.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getAddress( ).isMandatory()))
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS,strValidateAdresse );
        }

        //Adress Detail can not be Mandatory
        String strValidateAdresseDetail = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_DETAIL, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_DETAIL,false );

        if ( !strValidateAdresseDetail.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getAddressDetail( ).isMandatory()) )
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL,strValidateAdresseDetail );
        }

        
        
        String strValidateAdressePostalcode = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_POSTALCODE, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_POSTALCODE,dashboardIdentity.getAddressPostalcode().isMandatory() );

        if ( !strValidateAdressePostalcode.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getAddressPostalcode( ).isMandatory()) )
        {
        	hashErrors.put(  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE,strValidateAdressePostalcode );
        }

        String strValidateCity = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_CITY, Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_CITY,dashboardIdentity.getAddressCity().isMandatory() );

        if ( !strValidateCity.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getAddressCity( ).isMandatory()) )
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY,strValidateCity );
        }

        String strPreferredContactMode = dashboardIdentity.getPreferredContactMode( ).getValue( );

        // Case preferred Contact Mode = email. Check if email is empty
        if ( strPreferredContactMode.compareTo( _lstContactModeList.get( 0 ).getName( ) ) == 0 )
        {
            if ( dashboardIdentity.getEmail( ).getValue( ).isEmpty( ) && (! bOnlyCheckMandatory || dashboardIdentity.getEmail( ).isMandatory()) )
            {
            	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL,I18nService.getLocalizedString( Constants.MESSAGE_ERROR_EMAIL_EMPTY, request.getLocale( ) ) );
            
            }
        }

        // Case preferred Contact Mode = telephone. Check if at least telephone or mobile is populated
        if ( strPreferredContactMode.compareTo( _lstContactModeList.get( 1 ).getName( ) ) == 0 )
        {
            if ( ( dashboardIdentity.getPhone( ).getValue( ).isEmpty( ) ) && ( dashboardIdentity.getMobilePhone( ).getValue( ).isEmpty( ) ) &&   (! bOnlyCheckMandatory || dashboardIdentity.getPhone( ).isMandatory()||dashboardIdentity.getMobilePhone().isMandatory())  )
            {
            	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_PHONE,I18nService.getLocalizedString( Constants.MESSAGE_ERROR_TELEPHONE_EMPTY, request.getLocale( ) ) );
              
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

        return hashErrors;
    }
    
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
	public void updateDashboardIdentity(DashboardIdentity dashboardIdentity,boolean bUpdateOnlyManadtory) throws AppException
    {
    	
    	 IdentityDto identityDto = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity,bUpdateOnlyManadtory );
         //do not update certifier fields
    	 DashboardIdentityUtils.getInstance( ).filterByCertifier ( identityDto );
         DashboardIdentityUtils.getInstance().updateIdentity( identityDto );
           	
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityDto getIdentityToUpdate(DashboardIdentity dashboardIdentity,boolean bUpdateOnlyManadtory)
    {
    	
    	 IdentityDto identityDto = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity,bUpdateOnlyManadtory );
         //do not update certifier fields
    	 DashboardIdentityUtils.getInstance( ).filterByCertifier ( identityDto );
         
    	 return identityDto;
           	
    }
    
    
    
    
    /**
     * Populate dashboard identity.
     *
     * @param identity the identity
     * @param request the request
     */
    @Override
    public void populateDashboardIdentity ( DashboardIdentity identity, HttpServletRequest request )
    {
        
    	DashboardIdentityUtils.getInstance().populateDashboardIdentity(identity, request);
        
    }
    
    

    
    
    /**
     * Gets the error validation.
     *
     * @param request the HttpServletRequest
     * @param strAttributeKey the attributeKey to test
     * @param strRegExp the regExp who verify the submit value
     * @param i18nErrorMessage the I18nError message to display
     * @param bCheckMandatory the b check mandatory
     * @return  Empty if no error appear otherwise return the errorMessage
     */
    private String getErrorValidation(HttpServletRequest request,String strAttributeKey,String strRegExp, String i18nErrorMessage,boolean bCheckMandatory)
    {
    	String strError= StringUtils.EMPTY;
    	boolean bError=false;
    	if(strAttributeKey.equals( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) 
    			&& !StringUtils.isBlank(request.getParameter( strAttributeKey ))  
    			&&   !EmailValidator.getInstance( ).isValid( request.getParameter( strAttributeKey )))
    	{
    		
    		strError = I18nService.getLocalizedString( i18nErrorMessage, request.getLocale( ) );
    		 bError=true;
    		
    	}
    	else if(bCheckMandatory && StringUtils.isBlank(request.getParameter( strAttributeKey )))
    	{
    		
    		strError=  I18nService.getLocalizedString( Constants.MESSAGE_ERROR_EMPTY_ERROR_PREFIX+strAttributeKey, request.getLocale( ) );
    		bError=true;
    		
    	}
    		
    	else if ( !strAttributeKey.equals( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) && (request.getParameter( strAttributeKey ) != null && !request.getParameter( strAttributeKey ).matches( strRegExp )) )
         {
    		strError = I18nService.getLocalizedString( i18nErrorMessage, request.getLocale( ) );
    		bError=true;
         }
    	
    	
    	if(bError && StringUtils.isEmpty(strError))
    	{
    		strError=DEFAULT_ERROR;
    	}
    	return strError;
    	
    }
    
    
   
    
   
    
    
    
}