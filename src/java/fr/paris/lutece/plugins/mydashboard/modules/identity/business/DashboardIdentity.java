/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

public class DashboardIdentity
{
    private String _strConnectionId;
    private String _strCustomerId;
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
     * @param _strConnectionId the _strConnectionId to set
     */
    public void setConnectionId( String _strConnectionId )
    {
        this._strConnectionId = _strConnectionId;
    }

    /**
     * @return the _strCustomerId
     */
    public String getCustomerId(  )
    {
        return _strCustomerId;
    }

    /**
     * @param _strCustomerId the _strCustomerId to set
     */
    public void setCustomerId( String _strCustomerId )
    {
        this._strCustomerId = _strCustomerId;
    }

    /**
     * @return the _strLastName
     */
    public String getLastName(  )
    {
        return _strLastName;
    }

    /**
     * @param _strLastName the _strLastName to set
     */
    public void setLastName( String _strLastName )
    {
        this._strLastName = _strLastName;
    }

    /**
     * @return the _strBirthname
     */
    public String getBirthname(  )
    {
        return _strBirthname;
    }

    /**
     * @param _strBirthname the _strBirthname to set
     */
    public void setBirthname( String _strBirthname )
    {
        this._strBirthname = _strBirthname;
    }

    /**
     * @return the _strFirstname
     */
    public String getFirstname(  )
    {
        return _strFirstname;
    }

    /**
     * @param _strFirstname the _strFirstname to set
     */
    public void setFirstname( String _strFirstname )
    {
        this._strFirstname = _strFirstname;
    }

    /**
     * @return the _strGender
     */
    public String getGender(  )
    {
        return _strGender;
    }

    /**
     * @param _strGender the _strGender to set
     */
    public void setGender( String _strGender )
    {
        this._strGender = _strGender;
    }

    /**
     * @return the _strBirthdate
     */
    public String getBirthdate(  )
    {
        return _strBirthdate;
    }

    /**
     * @param _strBirthdate the _strBirthdate to set
     */
    public void setBirthdate( String _strBirthdate )
    {
        this._strBirthdate = _strBirthdate;
    }

    /**
     * @return the _strBirthplace
     */
    public String getBirthplace(  )
    {
        return _strBirthplace;
    }

    /**
     * @param _strBirthplace the _strBirthplace to set
     */
    public void setBirthplace( String _strBirthplace )
    {
        this._strBirthplace = _strBirthplace;
    }

    /**
     * @return the _strAddressNumber
     */
    public String getAddressNumber(  )
    {
        return _strAddressNumber;
    }

    /**
     * @param _strAddressNumber the _strAddressNumber to set
     */
    public void setAddressNumber( String _strAddressNumber )
    {
        this._strAddressNumber = _strAddressNumber;
    }

    /**
     * @return the _strAddressSuffix
     */
    public String getAddressSuffix(  )
    {
        return _strAddressSuffix;
    }

    /**
     * @param _strAddressSuffix the _strAddressSuffix to set
     */
    public void setAddressSuffix( String _strAddressSuffix )
    {
        this._strAddressSuffix = _strAddressSuffix;
    }

    /**
     * @return the _strAddressStreet
     */
    public String getAddressStreet(  )
    {
        return _strAddressStreet;
    }

    /**
     * @param _strAddressStreet the _strAddressStreet to set
     */
    public void setAddressStreet( String _strAddressStreet )
    {
        this._strAddressStreet = _strAddressStreet;
    }

    /**
     * @return the _strAddressBuilding
     */
    public String getAddressBuilding(  )
    {
        return _strAddressBuilding;
    }

    /**
     * @param _strAddressBuilding the _strAddressBuilding to set
     */
    public void setAddressBuilding( String _strAddressBuilding )
    {
        this._strAddressBuilding = _strAddressBuilding;
    }

    /**
     * @return the _strAddressStair
     */
    public String getAddressStair(  )
    {
        return _strAddressStair;
    }

    /**
     * @param _strAddressStair the _strAddressStair to set
     */
    public void setAddressStair( String _strAddressStair )
    {
        this._strAddressStair = _strAddressStair;
    }

    /**
     * @return the _strAddressPostalcode
     */
    public String getAddressPostalcode(  )
    {
        return _strAddressPostalcode;
    }

    /**
     * @param _strAddressPostalcode the _strAddressPostalcode to set
     */
    public void setAddressPostalcode( String _strAddressPostalcode )
    {
        this._strAddressPostalcode = _strAddressPostalcode;
    }

    /**
     * @return the _strEmail
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * @param _strEmail the _strEmail to set
     */
    public void setEmail( String _strEmail )
    {
        this._strEmail = _strEmail;
    }

    /**
     * @return the _strPhone
     */
    public String getPhone(  )
    {
        return _strPhone;
    }

    /**
     * @param _strPhone the _strPhone to set
     */
    public void setPhone( String _strPhone )
    {
        this._strPhone = _strPhone;
    }

    /**
     * @return the _strMobilePhone
     */
    public MobilePhone getMobilePhone(  )
    {
        return _mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone( MobilePhone mobilePhone )
    {
        this._mobilePhone = mobilePhone;
    }
}
