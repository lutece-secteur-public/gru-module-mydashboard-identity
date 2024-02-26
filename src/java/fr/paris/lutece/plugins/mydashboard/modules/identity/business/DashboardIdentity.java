/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Rediibution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Rediibutions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Rediibutions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the diibution.
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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import fr.paris.lutece.plugins.mydashboard.modules.identity.util.Constants;

/**
 *
 * DashboardIdentity
 *
 */
public class DashboardIdentity
{
    private Map<String, DashboardAttribute> _mapAttributes;
    private Timestamp _lastUpdateDate;
    private Integer _nCoverage;
    
    public DashboardIdentity ()
    {
        _mapAttributes = new HashMap<String, DashboardAttribute>( );
    }
    
    /**
     * Set a value to a specific attribute of DashboardIdentityAttribute
     * @param key the name of the attribute
     * @param value the value of the attribute
     */
    public void setAttributeValue ( String key, String value )
    {
        DashboardAttribute attribute = _mapAttributes.get( key );
        if ( attribute != null )
        {
            attribute.setValue( value );
            _mapAttributes.put( key, attribute );
        }
    }
    
    /**
     * Get a DashboardAttribute for a key
     * @param key the key
     * @return the DashboardAttribute
     */
    public DashboardAttribute getAttribute ( String key )
    {
        return _mapAttributes.get( key );
    }
    
    /**
     * Set an attribute to DashboardIdentity attribute map
     * @param key the key of the attribute
     * @param attribute the DashboardAttribute to set
     */
    public void setAttribute ( String key, DashboardAttribute attribute )
    {
        if ( attribute != null )
        {
            _mapAttributes.put( key, attribute );
        }
    }

    /**
     * @return the _ConnectionId
     */
    public DashboardAttribute getConnectionId( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID );
    }

    /**
     * @param connectionId
     *            the _ConnectionId to set
     */
    public void setConnectionId( DashboardAttribute connectionId )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_CONNECTION_ID, connectionId );
    }

    /**
     * @return the _CustomerId
     */
    public DashboardAttribute getCustomerId( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID );
    }

    /**
     * @param customerId
     *            the _customerId to set
     */
    public void setCustomerId( DashboardAttribute customerId )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_CUSTOMER_ID, customerId );
    }

    /**
     * @return the _LastName
     */
    public DashboardAttribute getLastName( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME );
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName( DashboardAttribute lastName )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_LAST_NAME, lastName );
    }

    /**
     * @return the Preferred Username
     */
    public DashboardAttribute getPreferredUsername( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME );
    }

    /**
     * @param preferredUsername
     *            the preferredUsername to set
     */
    public void setPreferredUsername( DashboardAttribute preferredUsername )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME, preferredUsername );
    }

    /**
     * @return the _Firstname
     */
    public DashboardAttribute getFirstname( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME );
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname( DashboardAttribute firstname )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME, firstname );
    }

    /**
     * @return the _Gender
     */
    public DashboardAttribute getGender( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_GENDER );
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender( DashboardAttribute gender )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_GENDER, gender );
    }

    /**
     * @return the _Birthdate
     */
    public DashboardAttribute getBirthdate( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE );
    }

    /**
     * @param birthdate
     *            the birthdate to set
     */
    public void setBirthdate( DashboardAttribute birthdate )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE, birthdate );
    }

    /**
     * @return the _Birthplace
     */
    public DashboardAttribute getBirthplace( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE );
    }

    /**
     * @param birthplace
     *            the _birthplace to set
     */
    public void setBirthplace( DashboardAttribute birthplace )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE, birthplace );
    }

    /**
     * @return the Birthcountry
     */
    public DashboardAttribute getBirthcountry( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY );
    }

    /**
     * @param birthcountry
     *            the birthcountry to set
     */
    public void setBirthcountry( DashboardAttribute birthcountry )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY, birthcountry );
    }

    /**
     * @return the Address
     */
    public DashboardAttribute getAddress( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS );
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress( DashboardAttribute address )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS, address );
    }

    /**
     * @return the AddressDetail
     */
    public DashboardAttribute getAddressDetail( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL );
    }

    /**
     * @param addressDetail
     *            the addressDetail to set
     */
    public void setAddressDetail( DashboardAttribute addressDetail )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_DETAIL, addressDetail );
    }

    /**
     * @return the _AddressPostalcode
     */
    public DashboardAttribute getAddressPostalcode( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE );
    }

    /**
     * @param addressPostalcode
     *            the addressPostalcode to set
     */
    public void setAddressPostalcode( DashboardAttribute addressPostalcode )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_POSTAL_CODE, addressPostalcode );
    }

    /**
     * @return the AddressCity
     */
    public DashboardAttribute getAddressCity( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY );
    }

    /**
     * @param addressCity
     *            the addressCity to set
     */
    public void setAddressCity( DashboardAttribute addressCity )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_ADDRESS_CITY, addressCity );
    }

    /**
     * @return the BillingAddress
     */
    public DashboardAttribute getBillingAddress( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS );
    }

    /**
     * @param billingAddress
     *            the billingAddress to set
     */
    public void setBillingAddress( DashboardAttribute billingAddress )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS, billingAddress );
    }

    /**
     * @return the BillingAddressDetail
     */
    public DashboardAttribute getBillingAddressDetail( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_DETAIL );
    }

    /**
     * @param billingAddressDetail
     *            the billingAddressDetail to set
     */
    public void setBillingAddressDetail( DashboardAttribute billingAddressDetail )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_DETAIL, billingAddressDetail );
    }

    /**
     * @return the BillingAddressPostalcode
     */
    public DashboardAttribute getBillingAddressPostalcode( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_POSTAL_CODE );
    }

    /**
     * @param billingAddressPostalcode
     *            the billingAddressPostalcode to set
     */
    public void setBillingAddressPostalcode( DashboardAttribute billingAddressPostalcode )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_POSTAL_CODE, billingAddressPostalcode );
    }

    /**
     * @return the BillingAddressCity
     */
    public DashboardAttribute getBillingAddressCity( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_CITY );
    }

    /**
     * @param billingAddressCity
     *            the billingAddressCity to set
     */
    public void setBillingAddressCity( DashboardAttribute billingAddressCity )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BILLING_ADDRESS_CITY, billingAddressCity );
    }

    /**
     * @return the _Email
     */
    public DashboardAttribute getEmail( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL );
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( DashboardAttribute email )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_EMAIL, email );
    }

    /**
     * @return the _Phone
     */
    public DashboardAttribute getPhone( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_PHONE );
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone( DashboardAttribute phone )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_PHONE, phone );
    }

    /**
     * @return the _MobilePhone
     */
    public DashboardAttribute getMobilePhone( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE );
    }

    /**
     * @param mobilePhone
     *            the mobilePhone to set
     */
    public void setMobilePhone( DashboardAttribute mobilePhone )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_MOBILE_PHONE, mobilePhone );
    }

    /**
     * @return the Login
     */
    public DashboardAttribute getLogin( )
    {
        //In this implementation of MyDashboardIdentity, login == email of user.
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_LOGIN );
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin( DashboardAttribute login )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_LOGIN, login );
    }
  
    /**
     * @return the FranceConnectCertified flag
     */
    public DashboardAttribute getFranceConnectCertified( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_FRANCE_CONNECT_CERTIFIED );
    }

    /**
     * Last Update Date
     * @return last Update Date
     */
	public Timestamp getLastUpdateDate() {
		return _lastUpdateDate;
	}

	/**
	 * 
	 * @param _lastUpdateDate last update date
	 */
	public void setLastUpdateDate(Timestamp _lastUpdateDate) {
		this._lastUpdateDate = _lastUpdateDate;
	}
	
    /**
     * @return the _BirthplaceCode
     */
    public DashboardAttribute getBirthplaceCode( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE );
    }

    /**
     * @param birthplaceCode
     *            the _birthplaceCode to set
     */
    public void setBirthplaceCode( DashboardAttribute birthplaceCode )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE, birthplaceCode );
    }

    /**
     * @return the BirthcountryCode
     */
    public DashboardAttribute getBirthcountryCode( )
    {
        return _mapAttributes.get( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE );
    }

    /**
     * @param birthcountryCode
     *            the birthcountryCode to set
     */
    public void setBirthcountryCode( DashboardAttribute birthcountryCode )
    {
        _mapAttributes.put( Constants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE, birthcountryCode );
    }
	/**
	 * 
	 * @return the coverage of the identity
	 */
	public Integer getCoverage() {
		return _nCoverage;
	}
  
	/**
	 * set the coverage of the identity
	 * @param coverage the coverage
	 */
	public void setCoverage(Integer coverage) {
		this._nCoverage = coverage;
	}


}
