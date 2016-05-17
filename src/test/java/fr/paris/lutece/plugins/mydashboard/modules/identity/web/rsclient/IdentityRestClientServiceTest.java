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

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.AttributeDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.IdentityDto;
import fr.paris.lutece.test.LuteceTestCase;


/**
 *
 * @author levy
 */
public class IdentityRestClientServiceTest extends LuteceTestCase
{
    private static final String PLUGIN_IDENTITYSTORE_REST_URL = "http://localhost:8080/identity/rest/identitystore/identity/";
    
    /**
     * Test of getIdentityAttributes rest service of plugin-identitystore.
     */
    @Test
    public void testRestServiceGetIdentityAttributes(  )
    {
        System.out.println( "getIdentityAttributes" );
        
        String strIdConnection = "azerty";
        String strClientCode = "MyDashboard";

        Client client = Client.create(  );

        WebResource webResource = client.resource( PLUGIN_IDENTITYSTORE_REST_URL + strIdConnection    
                    + "?format=json&client_code=" + strClientCode );

        ClientResponse response = webResource.accept( MediaType.APPLICATION_JSON ).get( ClientResponse.class );
        
        if ( response.getStatus(  ) != 200 )
        {
            System.out.println( "Status : " + response.getStatus(  ) );
        }
        
        IdentityDto identityDto = null;
        if ( response.hasEntity(  ) && response.getType(  ).toString(  ).equals(MediaType.APPLICATION_JSON)) 
        {
            identityDto = response.getEntity(IdentityDto.class);
        }
        
        if ( identityDto != null )
        {
            System.out.println( "Identity : " );
            System.out.println( "ClientAppCode : " + identityDto.getClientAppCode(  ) );
            System.out.println( "Attributes : " );
            for ( AttributeDto attributeDto : identityDto.getAttributes(  ) )
            {
                System.out.println( attributeDto.getKey(  ) + " - " + attributeDto.getType(  ) + " - " + attributeDto.getValue(  ) );
            }
        }
    }

}
