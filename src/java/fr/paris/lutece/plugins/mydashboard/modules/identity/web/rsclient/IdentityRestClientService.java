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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;

import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.ResponseDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.web.Constants;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import javax.ws.rs.core.MediaType;


/**
 * IdentityRestClientService
 */
public final class IdentityRestClientService
{
    private static final String BEAN_IDENTITYRESTCLIENTSERVICE = "mydashboard-identity.identityRestClientService";
    private ObjectMapper _mapper = new ObjectMapper(  );

    private IdentityRestClientService(  )
    {
        _mapper = new ObjectMapper(  );
        _mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _mapper.enable( SerializationFeature.INDENT_OUTPUT );
        _mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );
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
                    RestConstants.URL_IDENTITYSTORE_ENDPOINT ) + RestConstants.IDENTITY_PATH )
                                        .queryParam( RestConstants.PARAM_ID_CONNECTION, strIdConnection )
                                        .queryParam( RestConstants.PARAM_CLIENT_CODE, strClientCode );

        ClientResponse response = webResource.accept( MediaType.APPLICATION_JSON ).get( ClientResponse.class );

        if ( response.getStatus(  ) != Status.OK.getStatusCode(  ) )
        {
            if ( response.getStatus(  ) == Status.NOT_FOUND.getStatusCode(  ) )
            {
                //client not found
                IdentityDto identity = new IdentityDto(  );
                identity.setConnectionId( strIdConnection );
                //TODO  how to set customerID???
                identity.setCustomerId( strIdConnection );

                return identity;
            }
            else
            {
                throw new AppException( RestConstants.ERROR_MESSAGE + response.getStatus(  ) );
            }
        }

        IdentityDto identityDto = null;
        String strJsonResponse = response.getEntity( String.class );

        if ( JSONUtils.mayBeJSON( strJsonResponse ) )
        {
            try
            {
                identityDto = _mapper.readValue( strJsonResponse, IdentityDto.class );
            }
            catch ( IOException e )
            {
                throw new AppException( RestConstants.ERROR_MESSAGE + e.getMessage(  ) );
            }
        }
        else
        {
            throw new AppException( RestConstants.ERROR_MESSAGE + " not json response " + strJsonResponse );
        }

        return identityDto;
    }

    /**
     * Update attributes.
     *
     * @param strIdConnection the connection id
     * @param strClientCode the client code
     */
    public ResponseDto updateIdentity( IdentityDto identity )
    {
        AppLogService.debug( "Update identity attributes" );

        Client client = Client.create(  );

        //TODO valoriser certifier id
        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    RestConstants.URL_IDENTITYSTORE_ENDPOINT ) + RestConstants.IDENTITY_PATH );

        FormDataMultiPart formParams = new FormDataMultiPart(  );

        try
        {
            formParams.field( RestConstants.PARAM_CLIENT_CODE,
                AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE ) );
            formParams.field( RestConstants.PARAM_ID_CONNECTION, identity.getConnectionId(  ) );
            formParams.field( RestConstants.PARAM_USER_ID, identity.getCustomerId(  ) );
            formParams.field( RestConstants.PARAM_CERTIFIER_ID, StringUtils.EMPTY );
            formParams.bodyPart( _mapper.writeValueAsString( identity ), MediaType.APPLICATION_JSON_TYPE );
        }
        catch ( JsonProcessingException e )
        {
            try
            {
                formParams.close(  );
            }
            catch ( IOException e1 )
            {
                throw new AppException( RestConstants.ERROR_MESSAGE + e.getMessage(  ) );
            }

            throw new AppException( RestConstants.ERROR_MESSAGE + e.getMessage(  ) );
        }

        ClientResponse response = webResource.type( MediaType.MULTIPART_FORM_DATA )
                                             .post( ClientResponse.class, formParams );

        if ( response.getStatus(  ) != Status.OK.getStatusCode(  ) )
        {
            throw new AppException( RestConstants.ERROR_MESSAGE + response.getStatus(  ) );
        }

        ResponseDto responseDto = null;

        if ( response.hasEntity(  ) && response.getType(  ).toString(  ).equals( MediaType.APPLICATION_JSON ) )
        {
            try
            {
                responseDto = _mapper.readValue( response.getEntity( String.class ), ResponseDto.class );
            }
            catch ( IOException e )
            {
                throw new AppException( RestConstants.ERROR_MESSAGE + e.getMessage(  ) );
            }
        }

        return responseDto;
    }
}
