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


/**
 * This class provides constants for REST services
 *
 */
public final class RestConstants
{
    public static final String IDENTITY_PATH = "identity/";
    public static final String PARAM_ID_CONNECTION = "connection_id";
    public static final String PARAM_CLIENT_CODE = "client_code";
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_CERTIFIER_ID = "certifier_id";
    public static final String URL_IDENTITYSTORE_ENDPOINT = "mydashboard.identity.urlIdentityStoreEndpoint";
    public static final String URL_TOKEN = "mydashboard.identity.urlApiManager";
    public static final String TOKEN = "mydashboard.identity.urlApiManagerToken";

    //CONSTANT FOR SENDING JSON FLUX
    public static final String TYPE_AUTHENTIFICATION = "Bearer";
    public static final String PARAMS_ACCES_TOKEN = "access_token";
    public static final String PARAMS_GRANT_TYPE = "grant_type";
    public static final String PARAMS_GRANT_TYPE_VALUE = "client_credentials";

    //CONTENT FORMAT
    public static final String CONTENT_FORMAT = "application/json; charset=utf-8";
    public static final String CONTENT_FORMAT_TOKEN = "application/x-www-form-urlencoded";

    //HTTP ERROR MESSAGE
    public static final String ERROR_MESSAGE = "Failed : HTTP error code : ";

    /**
     * Default constructor
     */
    private RestConstants(  )
    {
    }
}
