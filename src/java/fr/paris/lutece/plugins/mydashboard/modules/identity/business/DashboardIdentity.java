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
    private int _nCustomerId;
    private String _strLastName;
    private String _strBirthname;
    private String _strFirstname;
    private String _strGender;
    private String _strBirthdate;
    private String _strBirthplace;
    private String _strAddressNumber;
    private String _strAddressSuffix;
    private String _strAddressStreet;
    private String _strAddressBuilding;
    private String _strAddressStair;
    private String _strAddressPostalcode;
    private String _strEmail;
    private String _strPhone;
    private MobilePhone _mobilePhone;

    /**
     * @return the _strConnectionId
     */
    public String getConnectionId(  )
    {
        return _strConnectionId;
    }

    /**
     * @param strConnectionId
     *          the _strConnectionId to set
     */
    public void setConnectionId( String strConnectionId )
    {
        this._strConnectionId = strConnectionId;
    }

    /**
     * @return the _nCustomerId
     */
    public int getCustomerId(  )
    {
        return _nCustomerId;
    }

    /**
     * @param nCustomerId
     *          the _nCustomerId to set
     */
    public void setCustomerId( int nCustomerId )
    {
        this._nCustomerId = nCustomerId;
    }

    /**
     * @return the _strLastName
     */
    public String getLastName(  )
    {
        return _strLastName;
    }

    /**
     * @param strLastName
     *          the _strLastName to set
     */
    public void setLastName( String strLastName )
    {
        this._strLastName = strLastName;
    }

    /**
     * @return the _strBirthname
     */
    public String getBirthname(  )
    {
        return _strBirthname;
    }

    /**
     * @param strBirthname
     *          the strBirthname to set
     */
    public void setBirthname( String strBirthname )
    {
        this._strBirthname = strBirthname;
    }

    /**
     * @return the _strFirstname
     */
    public String getFirstname(  )
    {
        return _strFirstname;
    }

    /**
     * @param strFirstname
     *          the _strFirstname to set
     */
    public void setFirstname( String strFirstname )
    {
        this._strFirstname = strFirstname;
    }

    /**
     * @return the _strGender
     */
    public String getGender(  )
    {
        return _strGender;
    }

    /**
     * @param strGender
     *          the _strGender to set
     */
    public void setGender( String strGender )
    {
        this._strGender = strGender;
    }

    /**
     * @return the _strBirthdate
     */
    public String getBirthdate(  )
    {
        return _strBirthdate;
    }

    /**
     * @param strBirthdate
     *          the _strBirthdate to set
     */
    public void setBirthdate( String strBirthdate )
    {
        this._strBirthdate = strBirthdate;
    }

    /**
     * @return the _strBirthplace
     */
    public String getBirthplace(  )
    {
        return _strBirthplace;
    }

    /**
     * @param strBirthplace
     *          the _strBirthplace to set
     */
    public void setBirthplace( String strBirthplace )
    {
        this._strBirthplace = strBirthplace;
    }

    /**
     * @return the _strAddressNumber
     */
    public String getAddressNumber(  )
    {
        return _strAddressNumber;
    }

    /**
     * @param strAddressNumber
     *          the _strAddressNumber to set
     */
    public void setAddressNumber( String strAddressNumber )
    {
        this._strAddressNumber = strAddressNumber;
    }

    /**
     * @return the _strAddressSuffix
     */
    public String getAddressSuffix(  )
    {
        return _strAddressSuffix;
    }

    /**
     * @param strAddressSuffix
     *          the _strAddressSuffix to set
     */
    public void setAddressSuffix( String strAddressSuffix )
    {
        this._strAddressSuffix = strAddressSuffix;
    }

    /**
     * @return the _strAddressStreet
     */
    public String getAddressStreet(  )
    {
        return _strAddressStreet;
    }

    /**
     * @param strAddressStreet
     *          the _strAddressStreet to set
     */
    public void setAddressStreet( String strAddressStreet )
    {
        this._strAddressStreet = strAddressStreet;
    }

    /**
     * @return the _strAddressBuilding
     */
    public String getAddressBuilding(  )
    {
        return _strAddressBuilding;
    }

    /**
     * @param strAddressBuilding
     *          the strAddressBuilding to set
     */
    public void setAddressBuilding( String strAddressBuilding )
    {
        this._strAddressBuilding = strAddressBuilding;
    }

    /**
     * @return the _strAddressStair
     */
    public String getAddressStair(  )
    {
        return _strAddressStair;
    }

    /**
     * @param strAddressStair
     *          the _strAddressStair to set
     */
    public void setAddressStair( String strAddressStair )
    {
        this._strAddressStair = strAddressStair;
    }

    /**
     * @return the _strAddressPostalcode
     */
    public String getAddressPostalcode(  )
    {
        return _strAddressPostalcode;
    }

    /**
     * @param strAddressPostalcode
     *          the _strAddressPostalcode to set
     */
    public void setAddressPostalcode( String strAddressPostalcode )
    {
        this._strAddressPostalcode = strAddressPostalcode;
    }

    /**
     * @return the _strEmail
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * @param strEmail
     *          the _strEmail to set
     */
    public void setEmail( String strEmail )
    {
        this._strEmail = strEmail;
    }

    /**
     * @return the _strPhone
     */
    public String getPhone(  )
    {
        return _strPhone;
    }

    /**
     * @param strPhone
     *          the strPhone to set
     */
    public void setPhone( String strPhone )
    {
        this._strPhone = strPhone;
    }

    /**
     * @return the _strMobilePhone
     */
    public MobilePhone getMobilePhone(  )
    {
        return _mobilePhone;
    }

    /**
     * @param mobilePhone
     *          the mobilePhone to set
     */
    public void setMobilePhone( MobilePhone mobilePhone )
    {
        this._mobilePhone = mobilePhone;
    }
}
