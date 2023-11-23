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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import fr.paris.lutece.plugins.identityquality.v3.web.service.IdentityQualityService;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.ResponseStatusType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.DuplicateSearchRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.DuplicateSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.service.ServiceContractService;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityStoreException;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardAttribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;



/**
 * class DashboardIdentityService
 */
public class DashboardIdentityService implements IDashBoardIdentityService
{
    
    /** The instance od DashBoard Identity */
    private static DashboardIdentityService _instance;   
    
    /** The Constant BEAN_SERVICE_CONTRACT_SERVICE. */
    private static final String BEAN_SERVICE_CONTRACT_SERVICE = "mydashboard-identity.serviceContract.service";
    private static final String BEAN_SERVICE_IDENTITY_QUALITYSERVICE = "mydashboard-identity.identityQualityService";
    private static final String DEFAULT_ERROR="error";
    
    /** The ServiceContractService */
    private ServiceContractService _serviceContractService;
    private IdentityQualityService _identityQualityService ;

  
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
            _instance._serviceContractService = SpringContextService.getBean( BEAN_SERVICE_CONTRACT_SERVICE );
            _instance._identityQualityService = SpringContextService.getBean( BEAN_SERVICE_IDENTITY_QUALITYSERVICE );
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
        IdentityDto identity = null; 
        DashboardIdentity dashboardIdentity = null;

         ServiceContractSearchResponse serviceContractSearchResponse = getActiveServiceContract( strApplicationCode );
         
         if( !StringUtils.isEmpty( strGuid ))
         {
              identity = DashboardIdentityUtils.getInstance( ).getIdentity( strGuid );
         }

         dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity, serviceContractSearchResponse );
         return dashboardIdentity;  
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean needCertificationFC( String strApplicationCode, String strGuid, DashboardIdentity dashboardIdentity ) throws AppException
    {
        ServiceContractSearchResponse serviceContractSearchResponse = getActiveServiceContract( strApplicationCode );
        return DashboardIdentityUtils.getInstance( ).needCertificationFC( dashboardIdentity, serviceContractSearchResponse );
    }
    
    @Override
    public ServiceContractSearchResponse getActiveServiceContract( String strApplicationCode )
    {
        ServiceContractSearchResponse serviceContractSearchResponse = null;
        try
        {
            serviceContractSearchResponse = _serviceContractService.getActiveServiceContract( strApplicationCode, DashboardIdentityUtils.DASHBOARD_APP_CODE, DashboardIdentityUtils.getInstance( ).getOwnerRequestAuthor( ) );
        } 
        catch ( IdentityStoreException e )
        {
            AppLogService.error( "Error ServiceContract for application {}", e.getMessage( ), strApplicationCode );
        }
        
        return serviceContractSearchResponse;
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
        
        String strValidateBirthplaceCode = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE_CODE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE_CODE,dashboardIdentity.getBirthplaceCode().isMandatory() );

        if ( !strValidateBirthplaceCode.isEmpty( )  &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthplaceCode().isMandatory())   )
        {
            hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE,strValidateBirthplaceCode );
        }
        
        String strValidateBirthCountryCode = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY_CODE, Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY_CODE, dashboardIdentity.getBirthcountryCode().isMandatory());

        if ( !strValidateBirthCountryCode.isEmpty( ) &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthcountryCode().isMandatory()))
        {
            hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE, strValidateBirthCountryCode );
        }

        // Populate gender with list codes {0,1,2} instead of values
        String strGender = dashboardIdentity.getGender( ).getValue( );

        if( StringUtils.isNotEmpty( strGender ) )
        {
            for ( ReferenceItem rItem : _lstGenderList )
            {
                if ( strGender.compareTo( rItem.getName( ) ) == 0 )
                {
                    dashboardIdentity.setGender( new DashboardAttribute( 
                            Constants.ATTRIBUTE_DB_IDENTITY_GENDER,
                            rItem.getCode( ) ) );
                }
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
    	
    	IdentityDto identity = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity,bUpdateOnlyManadtory );
         //do not update certifier fields
    	 DashboardIdentityUtils.getInstance( ).filterByCertifier ( identity );
         DashboardIdentityUtils.getInstance().updateIdentity( identity );
           	
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityDto getIdentityToUpdate(DashboardIdentity dashboardIdentity,boolean bUpdateOnlyManadtory)
    {
    	
    	IdentityDto identity = DashboardIdentityUtils.getInstance( ).convertToIdentityDto( dashboardIdentity,bUpdateOnlyManadtory );
         //do not update certifier fields
    	 DashboardIdentityUtils.getInstance( ).filterByCertifier ( identity );
         
    	 return identity;
           	
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
    
    @Override
    public DuplicateSearchResponse getSuspiciousIdentities( DashboardIdentity dashboardIdentity, List<String> listRules )
    {       
        DuplicateSearchRequest duplicateSearchRequest = new DuplicateSearchRequest( );
        duplicateSearchRequest.setRuleCodes( listRules );
                
        initAttributeSuspiciousSearchRequest( duplicateSearchRequest, dashboardIdentity );
               
        try
        {
            return _identityQualityService.searchDuplicates( duplicateSearchRequest, DashboardIdentityUtils.DASHBOARD_APP_CODE, DashboardIdentityUtils.getInstance( ).getOwnerRequestAuthor( ) );           
        }
        catch ( IdentityStoreException | AppException ex )
        {
            AppLogService.info( "Error getting Search duplicate identities ", ex );
        }

        return null;
    }
    
    /**
     * Init attribute for suspicious search request
     * @param duplicateSearchRequest
     * @param dashboardIdentity
     * @param bOnlyCheckNotEmpty
     */
    private void initAttributeSuspiciousSearchRequest ( DuplicateSearchRequest duplicateSearchRequest, DashboardIdentity dashboardIdentity )
    {
        Map<String, String> mapAttributes = new HashMap<>( );
        
        for ( Map.Entry<String,String> attribute : DashboardIdentityUtils.getInstance( ).getMapAttributeKeyMatch( ).entrySet( ) )
        {
            DashboardAttribute dashboardAttribute = dashboardIdentity.getAttribute( attribute.getKey( ) );
            if( dashboardAttribute != null )
            {
                mapAttributes.put( attribute.getValue( ), dashboardAttribute.getValue( ) );
            }
        }
       
        duplicateSearchRequest.setAttributes( mapAttributes );
    }

    @Override
    public boolean existSuspiciousIdentities( DashboardIdentity dashboardIdentity, List<String> listRules )
    {
        DuplicateSearchResponse suspiciousSearchResponse =  DashboardIdentityService.getInstance( ).getSuspiciousIdentities( dashboardIdentity, listRules ) ;

        if( suspiciousSearchResponse != null && suspiciousSearchResponse.getStatus( ).getType( ).equals( ResponseStatusType.OK ) &&
                CollectionUtils.isNotEmpty( suspiciousSearchResponse.getIdentities( ) ) )
        {
            if( dashboardIdentity.getConnectionId( ) != null && 
                    StringUtils.isNotEmpty( dashboardIdentity.getConnectionId( ).getValue( ) ) )
            {
                return suspiciousSearchResponse.getIdentities( ).stream( ).noneMatch( i -> i.getConnectionId( ).equals( dashboardIdentity.getConnectionId( ).getValue( ) ) );
            }
            return true;
        }
        return false;
    }
}
