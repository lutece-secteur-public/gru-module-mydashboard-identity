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

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;

import fr.paris.lutece.plugins.mydashboard.modules.identity.business.Identity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.ResponseDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.web.Constants;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * IdentityRestClientService
 */
public class IdentityRestClientService
{
    
    private static final String BEAN_IDENTITYRESTCLIENTSERVICE = "mydashboard-identity.identityRestClientService";

    private IdentityRestClientService(  )
    {
    }
   
    /**
     * Get the instance of {@link IdentityRestClientService}
     * @return the instance of {@link IdentityRestClientService}
     */
    public static IdentityRestClientService getService(  )
    {
        return SpringContextService.getBean( BEAN_IDENTITYRESTCLIENTSERVICE );
    }

    /**
     * Get identity attributes.
     *
     * @param strIdConnection the connection id
     * @param strClientCode the client code
     */
    public IdentityDto getIdentity( String strIdConnection, String strClientCode )
    {
        AppLogService.debug( "Get identity attributes of " + strIdConnection );
        
        Client client = Client.create(  );

        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    RestConstants.URL_IDENTITYSTORE_ENDPOINT ) 
                    + RestConstants.IDENTITY_PATH + strIdConnection + "?"  
                    + RestConstants.FORMAT_QUERY + "=" + RestConstants.MEDIA_TYPE_JSON + "&" 
                    + RestConstants.PARAM_CLIENT_CODE + "=" + strClientCode );

        ClientResponse response = webResource.accept( MediaType.APPLICATION_JSON ).get( ClientResponse.class );
        
        if ( response.getStatus( ) != 200 )
        {
            throw new AppException( RestConstants.ERROR_MESSAGE + response.getStatus(  ) );
        }
        
        IdentityDto identityDto = null;
        if ( response.hasEntity(  ) && response.getType(  ).toString(  ).equals(MediaType.APPLICATION_JSON)) 
        {
            identityDto = response.getEntity(IdentityDto.class);
        }
        
        return identityDto;
    }

    
    
    /**
     * Update attributes.
     *
     * @param strIdConnection the connection id
     * @param strClientCode the client code
     */
    public ResponseDto updateAttributes( Identity identity )
    {
        AppLogService.debug( "Update identity attributes" );
        
        Client client = Client.create(  );
        
        //TODO valoriser certifier id
        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    RestConstants.URL_IDENTITYSTORE_ENDPOINT ) 
                    + RestConstants.IDENTITY_PATH + identity.getConnectionId(  ) + "?"  
                    + RestConstants.PARAM_CLIENT_CODE + "=" + Constants.CLIENT_CODE + "&" 
                    + RestConstants.PARAM_USER_ID + "=" + identity.getCustomerId(  ) + "&"
                    + RestConstants.PARAM_CERTIFIER_ID + "=" + "" );

        FormDataMultiPart formParams = IdentityRestClientUtil.convertToFormDataMultiPart( identity );
        
        ClientResponse response = webResource.type( MediaType.MULTIPART_FORM_DATA ).accept( MediaType.MULTIPART_FORM_DATA ).post( ClientResponse.class, formParams );
        
        if ( response.getStatus( ) != 200 )
        {
            throw new AppException( RestConstants.ERROR_MESSAGE + response.getStatus(  ) );
        }
        
        ResponseDto responseDto = null;
        if ( response.hasEntity(  ) && response.getType(  ).toString(  ).equals(MediaType.APPLICATION_JSON) ) 
        {
            responseDto = response.getEntity(ResponseDto.class);
        }
        
        return responseDto;
    }
    
}
