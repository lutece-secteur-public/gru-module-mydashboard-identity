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
     * @return the Preferred Username
     */
    public String getPreferredUsername(  )
    {
        return _strPreferredUsername;
    }

    /**
     * @param strPreferredUsername
     *          the Preferred Username to set
     */
    public void setPreferredUsername( String strPreferredUsername )
    {
        this._strPreferredUsername = strPreferredUsername;
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
     * @return the strBirthcountry
     */
    public String getBirthcountry(  )
    {
        return _strBirthcountry;
    }

    /**
     * @param strBirthcountry
     *          the _strBirthcountry to set
     */
    public void setBirthcountry( String strBirthcountry )
    {
        this._strBirthcountry = strBirthcountry;
    }
    /**
	 * @return the Address
	 */
	public String getAddress()
	{
		return _strAddress;
	}

	/**
	 * @param _strAddress the Address to set
	 */
	public void setAddress( String strAddress )
	{
		this._strAddress = strAddress;
	}

	/**
	 * @return the AddressDetail
	 */
	public String getAddressDetail()
	{
		return _strAddressDetail;
	}

	/**
	 * @param _strAddressDetail the AddressDetail to set
	 */
	public void setAddressDetail( String strAddressDetail )
	{
		this._strAddressDetail = strAddressDetail;
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
	 * @return the AddressCity
	 */
	public String getAddressCity()
	{
		return _strAddressCity;
	}

	/**
	 * @param _strAddressCity the AddressCity to set
	 */
	public void setAddressCity( String strAddressCity )
	{
		this._strAddressCity = strAddressCity;
	}

	/**
	 * @return the BillingAddress
	 */
	public String getBillingAddress()
	{
		return _strBillingAddress;
	}

	/**
	 * @param _strBillingAddress the BillingAddress to set
	 */
	public void setBillingAddress( String strBillingAddress )
	{
		this._strBillingAddress = strBillingAddress;
	}

	/**
	 * @return the BillingAddressDetail
	 */
	public String getBillingAddressDetail()
	{
		return _strBillingAddressDetail;
	}

	/**
	 * @param _strBillingAddressDetail the BillingAddressDetail to set
	 */
	public void setBillingAddressDetail( String strBillingAddressDetail )
	{
		this._strBillingAddressDetail = strBillingAddressDetail;
	}

	/**
	 * @return the BillingAddressPostalcode
	 */
	public String getBillingAddressPostalcode()
	{
		return _strBillingAddressPostalcode;
	}

	/**
	 * @param _strBillingAddressPostalcode the BillingAddressPostalcode to set
	 */
	public void setBillingAddressPostalcode( String strBillingAddressPostalcode )
	{
		this._strBillingAddressPostalcode = strBillingAddressPostalcode;
	}

	/**
	 * @return the BillingAddressCity
	 */
	public String getBillingAddressCity()
	{
		return _strBillingAddressCity;
	}

	/**
	 * @param _strBillingAddressCity the BillingAddressCity to set
	 */
	public void setBillingAddressCity( String strBillingAddressCity )
	{
		this._strBillingAddressCity = strBillingAddressCity;
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

	/**
	 * @return the PreferredContactMode
	 */
	public String getPreferredContactMode()
	{
		return _strPreferredContactMode;
	}

	/**
	 * @param _strPreferredContactMode thePreferred Contact Mode to set
	 */
	public void setPreferredContactMode( String strPreferredContactMode )
	{
		this._strPreferredContactMode = strPreferredContactMode;
	}
}
