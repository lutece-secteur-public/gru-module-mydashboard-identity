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
package fr.paris.lutece.plugins.mydashboard.modules.identity.service;

import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.ResponseDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.web.Constants;
import fr.paris.lutece.plugins.mydashboard.modules.identity.web.rsclient.IdentityRestClientService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * IdentityService
 */
public class IdentityService implements IIdentityService
{
    private static final String BEAN_IDENTITYSERVICE = "mydashboard-identity.identityService";

    /**
     * Constructor
     */
    protected IdentityService(  )
    {
    }

    /**
     * Get the instance of {@link IdentityService}
     * @return the instance of {@link IdentityService}
     */
    public static IdentityService getService(  )
    {
        return SpringContextService.getBean( BEAN_IDENTITYSERVICE );
    }

    @Override
    public IdentityDto getIdentity( LuteceUser user )
    {
        IdentityRestClientService identityRestClientService = IdentityRestClientService.getService(  );

        return identityRestClientService.getIdentity( user.getName(  ),
            AppPropertiesService.getProperty( Constants.PROPERTY_APPLICATION_CODE ) );
    }

    @Override
    public ResponseDto updateIdentity( IdentityDto identity )
    {
        IdentityRestClientService identityRestClientService = IdentityRestClientService.getService(  );

        ResponseDto responseDto = identityRestClientService.updateIdentity( identity );

        return responseDto;
    }
}
