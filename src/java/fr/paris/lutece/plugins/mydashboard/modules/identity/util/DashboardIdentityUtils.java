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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AttributeDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AuthorType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.RequestAuthor;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.ResponseStatus;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.ResponseStatusType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.AttributeDefinitionDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.IdentityChangeRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.IdentityChangeResponse;
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
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;


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
    public static final String DASHBOARD_APP_CODE = AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE );
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
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE, Constants.PROPERTY_KEY_BIRTHPLACE_CODE );
        _mapAttributeKeyMatch.put(Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE, Constants.PROPERTY_KEY_BIRTHCOUNTRY_CODE );
    }
    private static ReferenceList _lstContactModeList;
    private static ReferenceList _lstGenderList;
    private static final String SPLIT_PATTERN = ";";
    private static final String SESSION_DASHBOARD_IDENTITY = "dashboardIdentity";
    private static final String SESSION_REDIRECT_URL = "redirectUrl";
    public static final String SESSION_ERROR_MESSAGE = "errorMessage";
    public static final String SESSION_INFO_MESSAGE = "infoMessage";
    public static final String SESSION_SN_NAME = "serviceNumericName";
    
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
    public DashboardIdentity convertToDashboardIdentity( IdentityDto identity )
    {     
    	return convertToDashboardIdentity( identity, null);
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
    public DashboardIdentity convertToDashboardIdentity( IdentityDto identity, ServiceContractSearchResponse contractSearchResponse )
    {
        DashboardIdentity dashboardIdentity = new DashboardIdentity(  );
       
        
        if(identity!=null)
        {
        	dashboardIdentity.setLastUpdateDate(identity.getLastUpdateDate());
        	
        	dashboardIdentity.setConnectionId( new DashboardAttribute( 
                Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID, 
                identity.getConnectionId( ) ) );
        
        
        	dashboardIdentity.setCustomerId( new DashboardAttribute(
                Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID,
                identity.getCustomerId( ) ) );
        }
        
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            dashboardIdentity.setAttribute( 
                    attributeMatch.getKey( ), 
                    getDashboardAttributeFromAttributeDtoKey (
                    		identity, 
                        attributeMatch.getValue( ), 
                        attributeMatch.getKey( ),contractSearchResponse ) 
                    ) ;
        }

        return dashboardIdentity;
        
    }
    
    public boolean needCertificationFC( DashboardIdentity dashboardIdentity, ServiceContractSearchResponse contractSearchResponse )
    {
    	boolean needCertificationFC = false;
    	
    	for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
    		DashboardAttribute attribute = dashboardIdentity.getAttribute( attributeMatch.getKey( ) );
    		Optional<AttributeDefinitionDto> optionalContract = contractSearchResponse.getServiceContract( ).getAttributeDefinitions( )
    				.stream( ).filter( e -> e.getKeyName( ).equals( attributeMatch.getValue( ) ) ).findFirst( );
    		
    		if ( optionalContract.isPresent( ) && optionalContract.get( ).getAttributeRequirement( ) != null )
    		{
    			needCertificationFC = ( attribute.getCertifierLevel( ) != Integer.valueOf( optionalContract.get( ).getAttributeRequirement( ).getLevel( ) ) 
    					&& optionalContract.get( ).getAttributeRequirement( ).getLevel( ).equals( "600" ) );
    		}
        }
    	
    	return needCertificationFC;
    }

    /**
     * return an identityDto from a DashboardIdentity
     *
     * @param dashboardIdentity
     *          dashboardIdentity to convert
     *  @param bOnlyMandatory true the IdentitityDTO must contains only Mandatory informations
     * @return identityDto initialized from provided dashboardIdentity
     */
    public IdentityDto convertToIdentityDto( DashboardIdentity dashboardIdentity,boolean bOnlyMandatory )
    {      
        IdentityDto identity = new IdentityDto( ); 
        
        identity.setLastUpdateDate(dashboardIdentity.getLastUpdateDate());
        identity.setConnectionId(dashboardIdentity.getConnectionId(  )!=null?  dashboardIdentity.getConnectionId(  ).getValue( ):null );
        identity.setCustomerId( dashboardIdentity.getCustomerId(  )!=null? dashboardIdentity.getCustomerId(  ).getValue( ):null );
        
        List<AttributeDto> listCertifiedAttribute = new ArrayList< >( );
        
        for ( Map.Entry<String,String> attributeMatch : _mapAttributeKeyMatch.entrySet( ) )
        {
            DashboardAttribute dashboardAttribute = dashboardIdentity.getAttribute( attributeMatch.getKey( ) );
            if(!bOnlyMandatory || dashboardAttribute.isMandatory())
            { 	
            	AttributeDto certifiedAttribute = new AttributeDto(  );
                certifiedAttribute.setKey( attributeMatch.getValue() );
                certifiedAttribute.setValue( dashboardAttribute.getValue( ) );
                certifiedAttribute.setCertifier( dashboardAttribute.getCertifierCode( ) );
                certifiedAttribute.setCertificationDate( new Date() );
                                
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
    private DashboardAttribute getDashboardAttributeFromAttributeDtoKey ( IdentityDto identity, String identityDtoAttributeKey, String dashboardAttributeKey, ServiceContractSearchResponse contractSearchResponse )
    {
        
    	
    	Optional<AttributeDto> certifiedAttribute=null;
    	
    	if(identity!=null)
    	{
    		certifiedAttribute =
    		   identity.getAttributes( )
               .stream( )
               .filter( attribute -> attribute.getKey( ).equals( identityDtoAttributeKey ) )
               .findAny( );
    	}
        
        DashboardAttribute dashboardAttribute = null;
        if ( certifiedAttribute != null && certifiedAttribute.isPresent( ) )
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
	            dashboardAttributeKey, StringUtils.EMPTY);
	        
	        List<String> certificationProcessNotCertifiable = Arrays.asList( Constants.PROPERTY_CERTIFICATION_PROCESS_NOT_CERTIFIABLE.split( ";" ) );
	        if(certificationProcessNotCertifiable !=null &&  certificationProcessNotCertifiable.size()>0)
	        {
	        	dashboardAttribute.setCertifierCode(   certificationProcessNotCertifiable.get(0));  
	         }
        }
        
        dashboardAttribute.setMandatory( isMandatoryAttribute( contractSearchResponse, identityDtoAttributeKey ) );
        
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
    
    public void filterByCertifier ( IdentityDto identity )
    {
        List<String> certificationProcessNotCertifiable = Arrays.asList( Constants.PROPERTY_CERTIFICATION_PROCESS_NOT_CERTIFIABLE.split( ";" ) );
        filterByCertifier( identity, certificationProcessNotCertifiable );
    }
    
    /**
     * Drop attributes from IdentityDto if there is a certificate. This is used to not update identity when a certificate is found.
     * @param identity
     * @param listCertificationProcessNotCertifiable
     */
    public void filterByCertifier ( IdentityDto identity, List<String> listCertificationProcessNotCertifiable )
    {
        List<AttributeDto> listCertifiedAttribute = new ArrayList< >();
        
        if( identity != null && identity.getAttributes( ) != null )
        {        
            for ( AttributeDto certifiedAttribute : identity.getAttributes( ) )
            {
                if ( certifiedAttribute.getCertifier() == null 
                    || listCertificationProcessNotCertifiable.contains( certifiedAttribute.getCertifier( ) ) )
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
    public IdentityDto getIdentity( String strConnectionId )
    {
        IdentitySearchResponse identitySearchResponse = null;
        IdentityDto identity=null;

        try
        {
            RequestAuthor requestAuthor = new RequestAuthor( );
            requestAuthor.setName( DASHBOARD_APP_CODE );
            requestAuthor.setType( AuthorType.owner );
            identitySearchResponse = _identityService.getIdentityByConnectionId( strConnectionId, DASHBOARD_APP_CODE ,requestAuthor);
        	if( identitySearchResponse!=null && 
					!ResponseStatusType.NOT_FOUND.equals(identitySearchResponse.getStatus().getType()) 
					&&  identitySearchResponse.getIdentities() != null 
					&& identitySearchResponse.getIdentities().size() > 0 )
        	{
                identity=identitySearchResponse.getIdentities().get(0);   
        	}
        	else
        	{
        	    identity=new IdentityDto();
        	    identity.setConnectionId(strConnectionId);
        	}
            
        }
        catch( IdentityStoreException | AppException infe )
        {
            AppLogService.error( "Identity App Exception for :" + strConnectionId, infe );
            identity=new IdentityDto();
    	    identity.setConnectionId(strConnectionId);
            
        }

        return identity;
    }
    
    /**
     * Update Identity from an IdentityDto.
     *
     * @param identityDto            identity Data transfer Object
     * @throws IdentityNotFoundException the identity not found exception
     * @throws AppException the app exception
     */
    public void updateIdentity(   IdentityDto identity )throws AppException
    {
        IdentityChangeRequest identityChangeRequest = buildIdentityChangeDto( identity );
        
        try
        {
            if( !StringUtils.isEmpty( identity.getCustomerId( ) ) )
            {
            	final IdentityChangeResponse response= _identityService.updateIdentity( identityChangeRequest.getIdentity( ).getCustomerId( ), identityChangeRequest, DASHBOARD_APP_CODE,getOwnerRequestAuthor() );
            	if (response==null ||  !ResponseStatusType.OK.equals(  response.getStatus().getType())  )
          	  {
          		  AppLogService.error( "Error when  updating the identity for connectionId {} the idantity change status is {} ", identity.getConnectionId( ), response!=null? response.getStatus():"");
          		  
          		  throw new IdentityStoreException(response==null ? "":response.getStatus().getType().name());
          	  }
            }
            else
            {
            	final IdentityChangeResponse response=_identityService.createIdentity( identityChangeRequest, DASHBOARD_APP_CODE ,getOwnerRequestAuthor());
            	  if (response==null || !ResponseStatusType.SUCCESS.equals( response.getStatus().getType()  ))
            	  {
            		  AppLogService.error( "Error when creating  the identity for connectionId {} the idantity change status is {} ", identity.getConnectionId( ), response!=null? response.getStatus():"");
            		  
            		  throw new IdentityStoreException(response==null ? "":response.getStatus().getType().name());
            	  }
            	
            }
        } catch ( AppException | IdentityStoreException e )
        {
            AppLogService.error( "Error when creating or updating the identity for connectionId {}", identity.getConnectionId( ),e );
        }
        	
    }
    
    /**
     * build a changeDto from Identity.
     *
     * @param identity            identity to update
     * @return IdentityChangeDto
     */
    private IdentityChangeRequest buildIdentityChangeDto(  IdentityDto identity)
    {
        IdentityChangeRequest identityChangeRequest = new IdentityChangeRequest( );
                
        identityChangeRequest.setIdentity( identity );

        return identityChangeRequest;
    }
    
    
    /**
     * Gets the owner request author.
     *
     * @return the owner request author
     */
    public RequestAuthor getOwnerRequestAuthor()
    {
    
	    RequestAuthor requestAuthor = new RequestAuthor( );
	    requestAuthor.setName( DASHBOARD_APP_CODE );
	    requestAuthor.setType( AuthorType.owner );
	    
	    return requestAuthor;
    }
    
    /**
     * 
     * @return
     */
    public Map<String,String> getMapAttributeKeyMatch ( )
    {
        return _mapAttributeKeyMatch;
    }
    
    /**
     * 
     * @param request
     * @return
     */
    public DashboardIdentity getCurrentDashboardIdentityInSession( HttpServletRequest request )
    {
        return ( DashboardIdentity ) request.getSession( ).getAttribute( SESSION_DASHBOARD_IDENTITY );
    }
	
    /**
     * 
     * @param request
     * @param dashboardIdentity
     */
    public void setCurrentDashboardIdentityInSession ( HttpServletRequest request, DashboardIdentity dashboardIdentity )
    {
        if( dashboardIdentity != null )
        {
            request.getSession( ).setAttribute( SESSION_DASHBOARD_IDENTITY, dashboardIdentity );
        }
        else
        {
            request.getSession( ).removeAttribute( SESSION_DASHBOARD_IDENTITY );
        }
    }
    
    /**
     * 
     * @param request
     * @return
     */
    public String getRedirectUrlAfterCompletionInSession ( HttpServletRequest request )
    {
        return ( String ) request.getSession( ).getAttribute( SESSION_REDIRECT_URL );
    }
    
    /**
     * 
     * @param strRedirectXPage
     * @param request
     */
    public void setRedirectUrlAfterCompletionInSession ( String strPage, String strView , HttpServletRequest request)
    {
        if( StringUtils.isNotEmpty( strPage ) && StringUtils.isNotEmpty( strView ) )
        {
            UrlItem url = new UrlItem( "Portal.jsp?page=" + strPage + "&view=" + strView );
            
            request.getSession( ).setAttribute( SESSION_REDIRECT_URL, url.getUrl( ) );
        }
    }
    
    /**
     * Get name of numeric service in the session
     * @param request
     * @return the name of numeric service in the session
     */
    public String getNumericServiceNameInSession ( HttpServletRequest request  )
    {
        return ( String ) request.getSession( ).getAttribute( SESSION_SN_NAME );
    }
    
    /**
     * Set Numeric service name in session
     * @param strServiceNumericName
     * @param request
     */
    public void setNumericServiceNameInSession ( String strServiceNumericName, HttpServletRequest request )
    {
        if( StringUtils.isNotEmpty( strServiceNumericName ) )
        {
            request.getSession( ).setAttribute( SESSION_SN_NAME, strServiceNumericName );
        }
    }
    
    
    /**
     * Get message in the session
     * @param bErrorMessage ( set true if is a error message and false if is a info message )
     * @param bRemoveAttribute
     * @param request
     * @param request
     * @return the message in session
     */
    public String getMessageInSession ( boolean bErrorMessage, boolean bRemoveAttribute, HttpServletRequest request )
    {
        String strAttributeName = bErrorMessage ? SESSION_ERROR_MESSAGE : SESSION_INFO_MESSAGE;
             
        String strMessage = ( String ) request.getSession( ).getAttribute( strAttributeName );
        
        if( bRemoveAttribute )
        {
            request.getSession( ).removeAttribute( strAttributeName );
        }
        
        return strMessage;
    }
    
    /**
     * Set message in the session
     * @param strMessage
     * @param bErrorMessage ( set true if is a error message and false if is a info message )
     * @param request
     */
    public void setMessageInSession ( String strMessage, boolean bErrorMessage, HttpServletRequest request )
    {
        if( StringUtils.isNotEmpty( strMessage ) )
        {
            String strAttributeName = bErrorMessage ? SESSION_ERROR_MESSAGE : SESSION_INFO_MESSAGE;
            
            request.getSession( ).setAttribute( strAttributeName, strMessage );
        }
    }
    
    
    /**
     * Gets all rules
     * @return
     */
    public List<String> getAllSuspiciousIdentityRules ( )
    {
        List<String> listAllRules = new ArrayList< >( );
        listAllRules.addAll( getSuspiciousIdentityStrictRules( ) );
        listAllRules.addAll( getSuspiciousIdentityNotStrictRules( ) );
        
        return listAllRules;
    }
    
    /**
     * Gets strict rules
     * @return list of strict rules
     */
    public List<String> getSuspiciousIdentityStrictRules ( )
    {
        return Arrays.asList( Constants.PROPERTY_SUSPICIOUS_LIST_RULE_STRIC.split( ";" ));
    }
    
    /**
     * Gets not strict rules
     * @return list of not strict rules
     */
    public List<String> getSuspiciousIdentityNotStrictRules ( )
    {
        return Arrays.asList( Constants.PROPERTY_SUSPICIOUS_LIST_RULE_NOT_STRIC.split( ";" ));        
    }
    

    /**
     * Set mandatory attribute for completion identity
     */
    public DashboardIdentity initMandatoryAttributeForCompletionIdentity( String strOriginActionCompletion )
    {
        DashboardIdentity completionIdentity = new DashboardIdentity( );

        for ( Map.Entry<String, String> attribute : DashboardIdentityUtils.getInstance( ).getMapAttributeKeyMatch( ).entrySet( ) )
        {
            DashboardAttribute dashboardAttribute = new DashboardAttribute( );
            dashboardAttribute.setKey( attribute.getValue( ) );
            
            switch ( Integer.parseInt( strOriginActionCompletion ) )
            {
                case Constants.ORIGIN_ACTION_CREATE_ACCOUNT  :
                    dashboardAttribute.setMandatory( isMandatoryCompletionCreateAndCompletionAccount( attribute ) );
                    break;
                case Constants.ORIGIN_ACTION_MODIFY_ACCOUNT:
                    dashboardAttribute.setMandatory( isMandatoryCompletionModifyAccount( attribute ) );
                    break;
                case Constants.ORIGIN_ACTION_COMPLETION_ACCOUNT  :
                    dashboardAttribute.setMandatory( isMandatoryCompletionCreateAndCompletionAccount( attribute ) );
                    break;
                default:
                    break;
            }
            
            completionIdentity.setAttribute( attribute.getKey( ), dashboardAttribute );
        }
        
        return completionIdentity;
    }
    
    /**
     * Return true if the attribute is mandatory for create account completion
     * @param attribute
     * @return true if the attributie is mandatory
     */
    public boolean isMandatoryCompletionCreateAndCompletionAccount ( Entry<String, String> attribute   )
    {
        return attribute.getValue( ).equals( Constants.PROPERTY_KEY_BIRTHPLACE_CODE ) 
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_BIRTHCOUNTRY_CODE ) ;

    }
    
    /**
     * Return true if the attribute is mandatory for modify account completion
     * @param attribute
     * @return true if the attribute is mandatory
     */
    public boolean isMandatoryCompletionModifyAccount ( Entry<String, String> attribute   )
    {
        return attribute.getValue( ).equals( Constants.PROPERTY_KEY_BIRTHPLACE_CODE ) 
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_BIRTHCOUNTRY_CODE )
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_GENDER ) 
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_FIRSTNAME )
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_BIRTHDATE )
                || attribute.getValue( ).equals( Constants.PROPERTY_KEY_NAME ) ;
    }
    
    /**
     * Update dashboardIdentity in the session
     * @param currentDasboardIdentity
     * @param currentDasboardIdentity in session
     */
    public void updateDashboardIdentityInSession( DashboardIdentity currentDasboardIdentity, DashboardIdentity dasboardIdentitySession )
    {
        
        if( currentDasboardIdentity.getGender( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getGender( ).getValue( ) ) )
        {
            dasboardIdentitySession.setGender( currentDasboardIdentity.getGender( ) );
        }
        if( currentDasboardIdentity.getFirstname( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getFirstname( ).getValue( ) ) )
        {
            dasboardIdentitySession.setFirstname( currentDasboardIdentity.getFirstname( ) );
        }
        if( currentDasboardIdentity.getLastName( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getLastName( ).getValue( ) ) )
        {
            dasboardIdentitySession.setLastName( currentDasboardIdentity.getLastName( ) );
        }        
        if( currentDasboardIdentity.getBirthdate( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getBirthdate( ).getValue( ) ) )
        {
            dasboardIdentitySession.setBirthdate( currentDasboardIdentity.getBirthdate( ) );
        }  
        if( currentDasboardIdentity.getPreferredUsername( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getPreferredUsername( ).getValue( ) ) )
        {
            dasboardIdentitySession.setPreferredUsername( currentDasboardIdentity.getPreferredUsername( ) );
        }        
        if( currentDasboardIdentity.getBirthplaceCode( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getBirthplaceCode( ).getValue( ) ) )
        {
            dasboardIdentitySession.setBirthplaceCode( currentDasboardIdentity.getBirthplaceCode( ) );
        }        
        if( currentDasboardIdentity.getBirthcountryCode( ) != null && StringUtils.isNotEmpty( currentDasboardIdentity.getBirthcountryCode( ).getValue( ) ) )
        {
            dasboardIdentitySession.setBirthcountryCode( currentDasboardIdentity.getBirthcountryCode( ) );
        }
    }
    
}
