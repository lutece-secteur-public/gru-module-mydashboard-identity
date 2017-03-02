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

/**
 *
 * DashboardIdentity
 *
 */
public class DashboardIdentity
{
    private String _strConnectionId;
    private String _strCustomerId;
    private String _strLastName;
    private String _strPreferredUsername;
    private String _strFirstname;
    private String _strGender;
    private String _strBirthdate;
    private String _strBirthplace;
    private String _strBirthcountry;
    private String _strAddress;
    private String _strAddressDetail;
    private String _strAddressPostalcode;
    private String _strAddressCity;
    private String _strBillingAddress;
    private String _strBillingAddressDetail;
    private String _strBillingAddressPostalcode;
    private String _strBillingAddressCity;
    private String _strEmail;
    private String _strPhone;
    private MobilePhone _mobilePhone;
    private String _strPreferredContactMode;
    private String _strLogin;
    private boolean _bAcceptNews;
    private boolean _bAcceptSurvey;
    private boolean _bFranceConnectCertified;

    /**
     * @return the _strConnectionId
     */
    public String getConnectionId( )
    {
        return _strConnectionId;
    }

    /**
     * @param strConnectionId
     *            the _strConnectionId to set
     */
    public void setConnectionId( String strConnectionId )
    {
        _strConnectionId = strConnectionId;
    }

    /**
     * @return the _strCustomerId
     */
    public String getCustomerId( )
    {
        return _strCustomerId;
    }

    /**
     * @param strCustomerId
     *            the _strCustomerId to set
     */
    public void setCustomerId( String strCustomerId )
    {
        _strCustomerId = strCustomerId;
    }

    /**
     * @return the _strLastName
     */
    public String getLastName( )
    {
        return _strLastName;
    }

    /**
     * @param strLastName
     *            the _strLastName to set
     */
    public void setLastName( String strLastName )
    {
        _strLastName = strLastName;
    }

    /**
     * @return the Preferred Username
     */
    public String getPreferredUsername( )
    {
        return _strPreferredUsername;
    }

    /**
     * @param strPreferredUsername
     *            the Preferred Username to set
     */
    public void setPreferredUsername( String strPreferredUsername )
    {
        _strPreferredUsername = strPreferredUsername;
    }

    /**
     * @return the _strFirstname
     */
    public String getFirstname( )
    {
        return _strFirstname;
    }

    /**
     * @param strFirstname
     *            the _strFirstname to set
     */
    public void setFirstname( String strFirstname )
    {
        _strFirstname = strFirstname;
    }

    /**
     * @return the _strGender
     */
    public String getGender( )
    {
        return _strGender;
    }

    /**
     * @param strGender
     *            the _strGender to set
     */
    public void setGender( String strGender )
    {
        _strGender = strGender;
    }

    /**
     * @return the _strBirthdate
     */
    public String getBirthdate( )
    {
        return _strBirthdate;
    }

    /**
     * @param strBirthdate
     *            the _strBirthdate to set
     */
    public void setBirthdate( String strBirthdate )
    {
        _strBirthdate = strBirthdate;
    }

    /**
     * @return the _strBirthplace
     */
    public String getBirthplace( )
    {
        return _strBirthplace;
    }

    /**
     * @param strBirthplace
     *            the _strBirthplace to set
     */
    public void setBirthplace( String strBirthplace )
    {
        _strBirthplace = strBirthplace;
    }

    /**
     * @return the strBirthcountry
     */
    public String getBirthcountry( )
    {
        return _strBirthcountry;
    }

    /**
     * @param strBirthcountry
     *            the _strBirthcountry to set
     */
    public void setBirthcountry( String strBirthcountry )
    {
        _strBirthcountry = strBirthcountry;
    }

    /**
     * @return the Address
     */
    public String getAddress( )
    {
        return _strAddress;
    }

    /**
     * @param strAddress
     *            the Address to set
     */
    public void setAddress( String strAddress )
    {
        _strAddress = strAddress;
    }

    /**
     * @return the AddressDetail
     */
    public String getAddressDetail( )
    {
        return _strAddressDetail;
    }

    /**
     * @param strAddressDetail
     *            the AddressDetail to set
     */
    public void setAddressDetail( String strAddressDetail )
    {
        _strAddressDetail = strAddressDetail;
    }

    /**
     * @return the _strAddressPostalcode
     */
    public String getAddressPostalcode( )
    {
        return _strAddressPostalcode;
    }

    /**
     * @param strAddressPostalcode
     *            the _strAddressPostalcode to set
     */
    public void setAddressPostalcode( String strAddressPostalcode )
    {
        _strAddressPostalcode = strAddressPostalcode;
    }

    /**
     * @return the AddressCity
     */
    public String getAddressCity( )
    {
        return _strAddressCity;
    }

    /**
     * @param strAddressCity
     *            the AddressCity to set
     */
    public void setAddressCity( String strAddressCity )
    {
        _strAddressCity = strAddressCity;
    }

    /**
     * @return the BillingAddress
     */
    public String getBillingAddress( )
    {
        return _strBillingAddress;
    }

    /**
     * @param strBillingAddress
     *            the BillingAddress to set
     */
    public void setBillingAddress( String strBillingAddress )
    {
        _strBillingAddress = strBillingAddress;
    }

    /**
     * @return the BillingAddressDetail
     */
    public String getBillingAddressDetail( )
    {
        return _strBillingAddressDetail;
    }

    /**
     * @param strBillingAddressDetail
     *            the BillingAddressDetail to set
     */
    public void setBillingAddressDetail( String strBillingAddressDetail )
    {
        _strBillingAddressDetail = strBillingAddressDetail;
    }

    /**
     * @return the BillingAddressPostalcode
     */
    public String getBillingAddressPostalcode( )
    {
        return _strBillingAddressPostalcode;
    }

    /**
     * @param strBillingAddressPostalcode
     *            the BillingAddressPostalcode to set
     */
    public void setBillingAddressPostalcode( String strBillingAddressPostalcode )
    {
        _strBillingAddressPostalcode = strBillingAddressPostalcode;
    }

    /**
     * @return the BillingAddressCity
     */
    public String getBillingAddressCity( )
    {
        return _strBillingAddressCity;
    }

    /**
     * @param strBillingAddressCity
     *            the BillingAddressCity to set
     */
    public void setBillingAddressCity( String strBillingAddressCity )
    {
        _strBillingAddressCity = strBillingAddressCity;
    }

    /**
     * @return the _strEmail
     */
    public String getEmail( )
    {
        return _strEmail;
    }

    /**
     * @param strEmail
     *            the _strEmail to set
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * @return the _strPhone
     */
    public String getPhone( )
    {
        return _strPhone;
    }

    /**
     * @param strPhone
     *            the strPhone to set
     */
    public void setPhone( String strPhone )
    {
        _strPhone = strPhone;
    }

    /**
     * @return the _strMobilePhone
     */
    public MobilePhone getMobilePhone( )
    {
        return _mobilePhone;
    }

    /**
     * @param mobilePhone
     *            the mobilePhone to set
     */
    public void setMobilePhone( MobilePhone mobilePhone )
    {
        _mobilePhone = mobilePhone;
    }

    /**
     * @return the PreferredContactMode
     */
    public String getPreferredContactMode( )
    {
        return _strPreferredContactMode;
    }

    /**
     * @param strPreferredContactMode
     *            thePreferred Contact Mode to set
     */
    public void setPreferredContactMode( String strPreferredContactMode )
    {
        _strPreferredContactMode = strPreferredContactMode;
    }

    /**
     * @return the Login
     */
    public String getLogin( )
    {
        return _strLogin;
    }

    /**
     * @param strLogin
     *            the Login to set
     */
    public void setLogin( String strLogin )
    {
        _strLogin = strLogin;
    }

    /**
     * @return the AcceptNews flag
     */
    public boolean getAcceptNews( )
    {
        return _bAcceptNews;
    }

    /**
     * @param bAcceptNews
     *            the AcceptNews flag to set
     */
    public void setAcceptNews( boolean bAcceptNews )
    {
        _bAcceptNews = bAcceptNews;
    }

    /**
     * @return the AcceptSurvey flag
     */
    public boolean getAcceptSurvey( )
    {
        return _bAcceptSurvey;
    }

    /**
     * @param bAcceptSurvey
     *            the AcceptSurvey flag to set
     */
    public void setAcceptSurvey( boolean bAcceptSurvey )
    {
        _bAcceptSurvey = bAcceptSurvey;
    }
    
    /**
     * @return the FranceConnectCertified flag
     */
    public boolean getFranceConnectCertified( )
    {
        return _bFranceConnectCertified;
    }

    /**
     * @param bFranceConnectCertified
     *            the FranceConnectCertified flag to set
     */
    public void setFranceConnectCertified( boolean bFranceConnectCertified )
    {
        _bFranceConnectCertified = bFranceConnectCertified;
    }
    
}
