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
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.AttributeDefinitionDto;
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
	public boolean needCertification( String strApplicationCode, String strGuid, DashboardIdentity dashboardIdentity, List<String> listAttributesNeedFC, int nLevelMin )
    {
        ServiceContractSearchResponse serviceContractSearchResponse = getActiveServiceContract( strApplicationCode );
        return DashboardIdentityUtils.getInstance( ).needCertification( dashboardIdentity, serviceContractSearchResponse, listAttributesNeedFC, nLevelMin );
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
     

        String strValidateLastName = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_LAST_NAME, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_LASTNAME, request.getLocale( ) ), dashboardIdentity.getLastName().isMandatory());
        
        if ( !strValidateLastName.isEmpty( ) &&  !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getLastName()) && ( ! bOnlyCheckMandatory ||  dashboardIdentity.getLastName().isMandatory()))
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME,strValidateLastName );
        }

        String strValidatePreferredUsername = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_PREFERREDUSERNAME, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_PREFFEREDUSERNAME, request.getLocale( ) ), dashboardIdentity.getPreferredUsername().isMandatory());

        if ( !strValidatePreferredUsername.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getPreferredUsername()) && (! bOnlyCheckMandatory ||  dashboardIdentity.getPreferredUsername().isMandatory())  )
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME,strValidatePreferredUsername );
        }

        String strValidateFirstname = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME, Constants.PROPERTY_KEY_VALIDATION_REGEXP_FIRSTNAME, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_FIRSTNAME, request.getLocale( ) ), dashboardIdentity.getFirstname().isMandatory() );

        if ( !strValidateFirstname.isEmpty( ) &&  !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getFirstname())  &&  (! bOnlyCheckMandatory || dashboardIdentity.getFirstname().isMandatory())  )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME,strValidateFirstname );
        }

        if ( StringUtils.isBlank( request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE ) ) )
        {
            String strValidateBirthplace = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE, request.getLocale( ) ), dashboardIdentity.getBirthplace().isMandatory() );
    
            if ( !strValidateBirthplace.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getBirthplace()) &&  (! bOnlyCheckMandatory ||   dashboardIdentity.getBirthplace().isMandatory())  )
            {
            	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE,strValidateBirthplace );
            }
        }

        String strValidateBirthDate = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHDATE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHDATE, request.getLocale( ) ), dashboardIdentity.getBirthdate().isMandatory());

        if ( !strValidateBirthDate.isEmpty( )  && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getBirthdate()) &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthdate().isMandatory()) )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, strValidateBirthDate );
        }
        
        if ( StringUtils.isBlank( request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE )) )
        {
            String strValidateBirthCountry = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY , Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY, request.getLocale( ) ), dashboardIdentity.getBirthcountry().isMandatory());
    
            if ( !strValidateBirthCountry.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getBirthcountry()) &&  (! bOnlyCheckMandatory || dashboardIdentity.getBirthcountry().isMandatory()))
            {
            	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY, strValidateBirthCountry );
            }
        }
        
        String strValidateEmail = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_EMAIL , null, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_EMAIL, request.getLocale( ) ), dashboardIdentity.getEmail().isMandatory() );

        if ( !strValidateEmail.isEmpty( ) &&  !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getEmail( )) && (! bOnlyCheckMandatory || dashboardIdentity.getEmail( ).isMandatory()) )
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_EMAIL,strValidateEmail );
        }

        String strValidatePhone =  getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_PHONE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_PHONE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_PHONE, request.getLocale( ) ), dashboardIdentity.getPhone().isMandatory() );

        if ( !strValidatePhone.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getPhone( )) &&  (! bOnlyCheckMandatory || dashboardIdentity.getPhone( ).isMandatory()))
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_PHONE, strValidatePhone );
        }

        String strValidateMobilePhone = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_MOBILEPHONE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_MOBILEPHONE, request.getLocale( ) ), dashboardIdentity.getMobilePhone().isMandatory());

        if ( !strValidateMobilePhone.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified( dashboardIdentity.getMobilePhone( )) &&  (! bOnlyCheckMandatory ||dashboardIdentity.getMobilePhone( ).isMandatory()))
        {
        	hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE , strValidateMobilePhone );
        }

        String strValidateAdresse = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS, request.getLocale( ) ), dashboardIdentity.getAddress().isMandatory());

        if ( !strValidateAdresse.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getAddress( ))&& (! bOnlyCheckMandatory || (dashboardIdentity.getAddress( ).isMandatory())))
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS,strValidateAdresse );
        }

        //Adress Detail can not be Mandatory
        String strValidateAdresseDetail = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_DETAIL, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_DETAIL, request.getLocale( ) ), false );

        if ( !strValidateAdresseDetail.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getAddressDetail( ))&&  (! bOnlyCheckMandatory ||( dashboardIdentity.getAddressDetail( ).isMandatory())) )
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL,strValidateAdresseDetail );
        }

        
        
        String strValidateAdressePostalcode = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_POSTALCODE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_POSTALCODE, request.getLocale( ) ), dashboardIdentity.getAddressPostalcode().isMandatory() );

        if ( !strValidateAdressePostalcode.isEmpty( ) &&!DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getAddressPostalcode( ))&&  (! bOnlyCheckMandatory || (dashboardIdentity.getAddressPostalcode( ).isMandatory()) ))
        {
        	hashErrors.put(  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE,strValidateAdressePostalcode );
        }

        String strValidateCity = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY , Constants.PROPERTY_KEY_VALIDATION_REGEXP_ADDRESS_CITY, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_ADDRESS_CITY, request.getLocale( ) ), dashboardIdentity.getAddressCity().isMandatory() );

        if ( !strValidateCity.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified( dashboardIdentity.getAddressCity( ))&& (! bOnlyCheckMandatory ||(dashboardIdentity.getAddressCity( ).isMandatory()) ))
        {
        	hashErrors.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY,strValidateCity );
        }
        
        String strValidateBirthplaceCode = getErrorValidation(request, Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE, Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHPLACE_CODE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHPLACE_CODE, request.getLocale( ) ), dashboardIdentity.getBirthplaceCode().isMandatory() );

        if ( !strValidateBirthplaceCode.isEmpty( )  && !DashboardIdentityUtils.isDashboardAttributeCertified(dashboardIdentity.getBirthplaceCode())&& (! bOnlyCheckMandatory || (dashboardIdentity.getBirthplaceCode().isMandatory())  ) )
        {
            hashErrors.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE,strValidateBirthplaceCode );
        }
        
        String strValidateBirthCountryCode = getErrorValidation(request,  Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE , Constants.PROPERTY_KEY_VALIDATION_REGEXP_BIRTHCOUNTRY_CODE, I18nService.getLocalizedString( Constants.MESSAGE_ERROR_VALIDATION_BIRTHCOUNTRY_CODE, request.getLocale( ) ), dashboardIdentity.getBirthcountryCode().isMandatory());

        if ( !strValidateBirthCountryCode.isEmpty( ) && !DashboardIdentityUtils.isDashboardAttributeCertified( dashboardIdentity.getBirthcountryCode()) && (! bOnlyCheckMandatory || dashboardIdentity.getBirthcountryCode().isMandatory()))
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
    public Map<String,String> checkDashboardIdentityFieldsFromServiceContract( DashboardIdentity dashboardIdentity, HttpServletRequest request, boolean bOnlyCheckMandatory, String strAppCode )
    {
    	Map<String,String> hashErrors = new HashMap<String,String>();
    	
    	ServiceContractSearchResponse contractSearchResponse = getActiveServiceContract( strAppCode );
    	if ( contractSearchResponse != null )
    	{
    		List<AttributeDefinitionDto> attributeDefinitionDtoList = contractSearchResponse.getServiceContract( ).getAttributeDefinitions( );
        	
        	for ( AttributeDefinitionDto attributeDefinitionDto : attributeDefinitionDtoList )
        	{
        		for ( Map.Entry<String,String> attributeMatch : DashboardIdentityUtils.getInstance( ).getMapAttributeKeyMatch( ).entrySet( ) )
        		{
        			if ( attributeMatch.getKey( ).equals( attributeDefinitionDto.getKeyName( ) ) )
        			{
        				boolean isMandatory = false;
        				
        				if ( !attributeMatch.getKey( ).equals( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL )
        				        && !(  attributeMatch.getKey( ).equals( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY ) && StringUtils.isNotBlank( request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE ) ) ) 
        				        && !(  attributeMatch.getKey( ).equals( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE ) && StringUtils.isNotBlank( request.getParameter( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE ) ) ))
        				{
        					isMandatory = dashboardIdentity.getAttribute( attributeMatch.getValue( ) ).isMandatory( );
        				}
        				
        				
        				String strValidate = getErrorValidation( request, attributeMatch.getKey( ), attributeDefinitionDto.getValidationRegex( ), attributeDefinitionDto.getValidationErrorMessage( ), dashboardIdentity.getAttribute( attributeMatch.getValue( ) ).isMandatory( ) );
        				
        				if ( !strValidate.isEmpty( ) && ( !bOnlyCheckMandatory || isMandatory ) )
                        {
                        	hashErrors.put( attributeMatch.getKey( ), strValidate );
                        }
        			}
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
    private String getErrorValidation(HttpServletRequest request,String strAttributeKey,String strRegExp, String errorMessage,boolean bCheckMandatory)
    {
    	String strError= StringUtils.EMPTY;
    	
    	
    	
    	boolean bError=false;
    	if(strAttributeKey.equals( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) 
    			&& !StringUtils.isBlank(request.getParameter( strAttributeKey ))  
    			&&   !EmailValidator.getInstance( ).isValid( request.getParameter( strAttributeKey )))
    	{
    		
    		strError = errorMessage;
    		 bError=true;
    		
    	}
    	else if(bCheckMandatory && StringUtils.isBlank(request.getParameter( strAttributeKey )) && StringUtils.isBlank( strError ))
    	{
    		
    		strError=  I18nService.getLocalizedString( Constants.MESSAGE_ERROR_EMPTY_ERROR_PREFIX+strAttributeKey, request.getLocale( ) );
    		bError=true;
    		
    	}
    		
    	else if ( strRegExp != null && !strAttributeKey.equals( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL ) && (request.getParameter( strAttributeKey ) != null && !request.getParameter( strAttributeKey ).matches( strRegExp )) )
         {
    		strError = errorMessage;
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
        if( Constants.PROPERTY_SUSPICIOUS_IDENTITY_ACTIVATION_INDICATEUR )
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
            for( IdentityDto identity : suspiciousSearchResponse.getIdentities( ) )
            {
                if( StringUtils.isEmpty( identity.getConnectionId( ) ) || dashboardIdentity.getConnectionId( ) == null 
                        || !identity.getConnectionId( ).equals( dashboardIdentity.getConnectionId( ).getValue( ) ) )
                {
                    return true;
                }
            }              
            return false;
        }
        return false;
    }
}