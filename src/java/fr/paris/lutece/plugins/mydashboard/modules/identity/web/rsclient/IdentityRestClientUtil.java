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

import java.util.ArrayList;
import java.util.List;


import com.sun.jersey.multipart.FormDataMultiPart;

//import fr.paris.lutece.plugins.identitystore.business.AttributeKeyHome;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.Attribute;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.Identity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.AttributeDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.service.dto.IdentityDto;




/**
 *
 * class to help managing rest feature
 *
 */
public final class IdentityRestClientUtil
{
    /**
     * private constructor
     */
    private IdentityRestClientUtil(  )
    {
    }

    /**
     * returns a identityDto initialized from provided identity
     * @param identity business identity to convert
     * @return identityDto initialized from provided identity
     */
    public static IdentityDto convertToDto( Identity identity )
    {
        IdentityDto identityDto = new IdentityDto(  );

        if ( identity.getAttributes(  ) != null )
        {
            List<AttributeDto> lstAttributeDto = new ArrayList<AttributeDto>(  );

            for ( Attribute attribute : identity.getAttributes(  ) )
            {
                AttributeDto attrDto = new AttributeDto(  );
                attrDto.setKey( attribute.getKey(  ) );
                attrDto.setValue( attribute.getValue(  ) );
//                attrDto.setType( AttributeKeyHome.findByKey( attribute.getKey(  ) ).getKeyType(  ).getCode(  ) );
                lstAttributeDto.add( attrDto );
            }

            identityDto.setAttributes( lstAttributeDto );
        }

        return identityDto;
    }
    
    /**
     * returns a FormDataMultiPart object initialized from provided identity
     * @param identity business identity to convert
     * @return formParams initialized from provided identity
     */
    public static FormDataMultiPart convertToFormDataMultiPart( Identity identity )
    {
        FormDataMultiPart formParams = new FormDataMultiPart(  );

        if ( identity.getAttributes(  ) != null )
        {
            for ( Attribute attribute : identity.getAttributes(  ) )
            {
                formParams.field(attribute.getKey(), attribute.getValue());
            }
        }

        return formParams;
    }
    
    /**
     * returns a identity initialized from provided identityDto
     * @param identityDto identity to convert
     * @return identity initialized from provided identity
     */
    public static Identity convertFromDto( IdentityDto identityDto )
    {
        Identity identity = new Identity(  );

        if ( identityDto.getAttributes(  ) != null )
        {
            List<Attribute> listAttribute = new ArrayList<Attribute>(  );

            for ( AttributeDto attributeDto : identityDto.getAttributes(  ) )
            {
                Attribute attribute = new Attribute(  );
                attribute.setKey( attributeDto.getKey(  ) );
                attribute.setValue( attributeDto.getValue(  ) );
//                attribute.setType( AttributeKeyHome.findByKey( attribute.getKey(  ) ).getKeyType(  ).getCode(  ) );
                listAttribute.add( attribute );
            }

            identity.setAttributes( listAttribute );
        }

        return identity;
    }
}
