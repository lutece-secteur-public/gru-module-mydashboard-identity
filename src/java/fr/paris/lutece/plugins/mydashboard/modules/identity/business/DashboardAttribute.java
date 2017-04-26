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
package fr.paris.lutece.plugins.mydashboard.modules.identity.business;

import java.util.Date;

/**
 * This is the business class for the object DashboardAttribute
 */ 
public class DashboardAttribute
{
    // Variables declarations 
    private String _strKey;
    private String _strValue;
    private String _strCertifierCode;
    private String _strCertifierName;
    private int _nCertifierLevel;
    private Date _tCertificateExpirationDate;
    
    public DashboardAttribute( )
    {
        
    }

    public DashboardAttribute( String key, String value, String certifierCode, String certifierName, int nCertifierLevel, Date tCertificateExpirtationDate )
    {
        _strKey = key;
        _strValue = value;
        _strCertifierCode = certifierCode;
        _strCertifierName = certifierName;
        _nCertifierLevel = nCertifierLevel;
        _tCertificateExpirationDate = tCertificateExpirtationDate;
    }
    
    public DashboardAttribute( String key, String value )
    {
        _strKey = key;
        _strValue = value;
        _strCertifierName = null;
        _strCertifierCode = null;
        _tCertificateExpirationDate = null;
    }
    
       /**
        * Returns the Key
        * @return The Key
        */ 
    public String getKey()
    {
        return _strKey;
    }
    
       /**
        * Sets the Key
        * @param strKey The Key
        */ 
    public void setKey( String strKey )
    {
        _strKey = strKey;
    }
    
       /**
        * Returns the Value
        * @return The Value
        */ 
    public String getValue()
    {
        return _strValue;
    }
    
       /**
        * Sets the Value
        * @param strValue The Value
        */ 
    public void setValue( String strValue )
    {
        _strValue = strValue;
    }
    
       /**
        * Returns the CertifierCode
        * @return The CertifierCode
        */ 
    public String getCertifierCode()
    {
        return _strCertifierCode;
    }
    
       /**
        * Sets the CertifierCode
        * @param strCertifierCode The CertifierCode
        */ 
    public void setCertifierCode( String strCertifierCode )
    {
        _strCertifierCode = strCertifierCode;
    }
    
       /**
        * Returns the CertifierName
        * @return The CertifierName
        */ 
    public String getCertifierName()
    {
        return _strCertifierName;
    }
    
       /**
        * Sets the CertifierName
        * @param strCertifierName The CertifierName
        */ 
    public void setCertifierName( String strCertifierName )
    {
        _strCertifierName = strCertifierName;
    }
    
    /**
     * Returns the Certifier level
     * @return The CertifierLevel
     */ 
    public int getCertifierLevel()
    {
        return _nCertifierLevel;
    }
    
    /**
     * Sets the CertifierLevel
     * @param nCertifierLevel The CertifierLevel
     */ 
    public void setCertifierName( int nCertifierLevel )
    {
        _nCertifierLevel = nCertifierLevel;
    }
    
       /**
        * Returns the ExpirationDate of the certificate
        * @return The ExpirationDate of the certificate
        */ 
    public Date getExpirationDate()
    {
        return _tCertificateExpirationDate;
    }
    
       /**
        * Sets the ExpirationDate of the certificate
        * @param tCertificateExpirationDate The ExpirationDate of the certificate
        */ 
    public void setExpirationDate( Date tCertificateExpirationDate )
    {
        _tCertificateExpirationDate = tCertificateExpirationDate;
    }
    
    
 }