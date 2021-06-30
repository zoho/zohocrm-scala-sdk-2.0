package com.zoho.crm.api.users

import com.zoho.crm.api.profiles.Profile
import com.zoho.crm.api.roles.Role
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model
import scala.collection.mutable.ArrayBuffer

class User extends Record with Model	{

	def getCountry() :Option[String]	={
		return  this.getKeyValue("country").asInstanceOf[Option[String]]
	}

	def setCountry( country: Option[String]) 	={
		 this.addKeyValue("country", country)
	}

	def getCustomizeInfo() :Option[CustomizeInfo]	={
		return  this.getKeyValue("customize_info").asInstanceOf[Option[CustomizeInfo]]
	}

	def setCustomizeInfo( customizeInfo: Option[CustomizeInfo]) 	={
		 this.addKeyValue("customize_info", customizeInfo)
	}

	def getRole() :Option[Role]	={
		return  this.getKeyValue("role").asInstanceOf[Option[Role]]
	}

	def setRole( role: Option[Role]) 	={
		 this.addKeyValue("role", role)
	}

	def getSignature() :Option[String]	={
		return  this.getKeyValue("signature").asInstanceOf[Option[String]]
	}

	def setSignature( signature: Option[String]) 	={
		 this.addKeyValue("signature", signature)
	}

	def getCity() :Option[String]	={
		return  this.getKeyValue("city").asInstanceOf[Option[String]]
	}

	def setCity( city: Option[String]) 	={
		 this.addKeyValue("city", city)
	}

	def getNameFormat() :Option[String]	={
		return  this.getKeyValue("name_format").asInstanceOf[Option[String]]
	}

	def setNameFormat( nameFormat: Option[String]) 	={
		 this.addKeyValue("name_format", nameFormat)
	}

	def getPersonalAccount() :Option[Boolean]	={
		return  this.getKeyValue("personal_account").asInstanceOf[Option[Boolean]]
	}

	def setPersonalAccount( personalAccount: Option[Boolean]) 	={
		 this.addKeyValue("personal_account", personalAccount)
	}

	def getDefaultTabGroup() :Option[String]	={
		return  this.getKeyValue("default_tab_group").asInstanceOf[Option[String]]
	}

	def setDefaultTabGroup( defaultTabGroup: Option[String]) 	={
		 this.addKeyValue("default_tab_group", defaultTabGroup)
	}

	def getLanguage() :Option[String]	={
		return  this.getKeyValue("language").asInstanceOf[Option[String]]
	}

	def setLanguage( language: Option[String]) 	={
		 this.addKeyValue("language", language)
	}

	def getLocale() :Option[String]	={
		return  this.getKeyValue("locale").asInstanceOf[Option[String]]
	}

	def setLocale( locale: Option[String]) 	={
		 this.addKeyValue("locale", locale)
	}

	def getMicrosoft() :Option[Boolean]	={
		return  this.getKeyValue("microsoft").asInstanceOf[Option[Boolean]]
	}

	def setMicrosoft( microsoft: Option[Boolean]) 	={
		 this.addKeyValue("microsoft", microsoft)
	}

	def getIsonline() :Option[Boolean]	={
		return  this.getKeyValue("Isonline").asInstanceOf[Option[Boolean]]
	}

	def setIsonline( isonline: Option[Boolean]) 	={
		 this.addKeyValue("Isonline", isonline)
	}

	def getStreet() :Option[String]	={
		return  this.getKeyValue("street").asInstanceOf[Option[String]]
	}

	def setStreet( street: Option[String]) 	={
		 this.addKeyValue("street", street)
	}

	def getCurrency() :Option[String]	={
		return  this.getKeyValue("Currency").asInstanceOf[Option[String]]
	}

	def setCurrency( currency: Option[String]) 	={
		 this.addKeyValue("Currency", currency)
	}

	def getAlias() :Option[String]	={
		return  this.getKeyValue("alias").asInstanceOf[Option[String]]
	}

	def setAlias( alias: Option[String]) 	={
		 this.addKeyValue("alias", alias)
	}

	def getTheme() :Option[Theme]	={
		return  this.getKeyValue("theme").asInstanceOf[Option[Theme]]
	}

	def setTheme( theme: Option[Theme]) 	={
		 this.addKeyValue("theme", theme)
	}

	def getState() :Option[String]	={
		return  this.getKeyValue("state").asInstanceOf[Option[String]]
	}

	def setState( state: Option[String]) 	={
		 this.addKeyValue("state", state)
	}

	def getFax() :Option[String]	={
		return  this.getKeyValue("fax").asInstanceOf[Option[String]]
	}

	def setFax( fax: Option[String]) 	={
		 this.addKeyValue("fax", fax)
	}

	def getCountryLocale() :Option[String]	={
		return  this.getKeyValue("country_locale").asInstanceOf[Option[String]]
	}

	def setCountryLocale( countryLocale: Option[String]) 	={
		 this.addKeyValue("country_locale", countryLocale)
	}

	def getFirstName() :Option[String]	={
		return  this.getKeyValue("first_name").asInstanceOf[Option[String]]
	}

	def setFirstName( firstName: Option[String]) 	={
		 this.addKeyValue("first_name", firstName)
	}

	def getEmail() :Option[String]	={
		return  this.getKeyValue("email").asInstanceOf[Option[String]]
	}

	def setEmail( email: Option[String]) 	={
		 this.addKeyValue("email", email)
	}

	def getReportingTo() :Option[User]	={
		return  this.getKeyValue("Reporting_To").asInstanceOf[Option[User]]
	}

	def setReportingTo( reportingTo: Option[User]) 	={
		 this.addKeyValue("Reporting_To", reportingTo)
	}

	def getDecimalSeparator() :Option[String]	={
		return  this.getKeyValue("decimal_separator").asInstanceOf[Option[String]]
	}

	def setDecimalSeparator( decimalSeparator: Option[String]) 	={
		 this.addKeyValue("decimal_separator", decimalSeparator)
	}

	def getZip() :Option[String]	={
		return  this.getKeyValue("zip").asInstanceOf[Option[String]]
	}

	def setZip( zip: Option[String]) 	={
		 this.addKeyValue("zip", zip)
	}

	def getWebsite() :Option[String]	={
		return  this.getKeyValue("website").asInstanceOf[Option[String]]
	}

	def setWebsite( website: Option[String]) 	={
		 this.addKeyValue("website", website)
	}

	def getTimeFormat() :Option[String]	={
		return  this.getKeyValue("time_format").asInstanceOf[Option[String]]
	}

	def setTimeFormat( timeFormat: Option[String]) 	={
		 this.addKeyValue("time_format", timeFormat)
	}

	def getOffset() :Option[Long]	={
		return  this.getKeyValue("offset").asInstanceOf[Option[Long]]
	}

	def setOffset( offset: Option[Long]) 	={
		 this.addKeyValue("offset", offset)
	}

	def getProfile() :Option[Profile]	={
		return  this.getKeyValue("profile").asInstanceOf[Option[Profile]]
	}

	def setProfile( profile: Option[Profile]) 	={
		 this.addKeyValue("profile", profile)
	}

	def getMobile() :Option[String]	={
		return  this.getKeyValue("mobile").asInstanceOf[Option[String]]
	}

	def setMobile( mobile: Option[String]) 	={
		 this.addKeyValue("mobile", mobile)
	}

	def getLastName() :Option[String]	={
		return  this.getKeyValue("last_name").asInstanceOf[Option[String]]
	}

	def setLastName( lastName: Option[String]) 	={
		 this.addKeyValue("last_name", lastName)
	}

	def getTimeZone() :Option[String]	={
		return  this.getKeyValue("time_zone").asInstanceOf[Option[String]]
	}

	def setTimeZone( timeZone: Option[String]) 	={
		 this.addKeyValue("time_zone", timeZone)
	}

	def getZuid() :Option[String]	={
		return  this.getKeyValue("zuid").asInstanceOf[Option[String]]
	}

	def setZuid( zuid: Option[String]) 	={
		 this.addKeyValue("zuid", zuid)
	}

	def getConfirm() :Option[Boolean]	={
		return  this.getKeyValue("confirm").asInstanceOf[Option[Boolean]]
	}

	def setConfirm( confirm: Option[Boolean]) 	={
		 this.addKeyValue("confirm", confirm)
	}

	def getFullName() :Option[String]	={
		return  this.getKeyValue("full_name").asInstanceOf[Option[String]]
	}

	def setFullName( fullName: Option[String]) 	={
		 this.addKeyValue("full_name", fullName)
	}

	def getTerritories() :ArrayBuffer[Territory]	={
		return  this.getKeyValue("territories").asInstanceOf[ArrayBuffer[Territory]]
	}

	def setTerritories( territories: ArrayBuffer[Territory]) 	={
		 this.addKeyValue("territories", territories)
	}

	def getPhone() :Option[String]	={
		return  this.getKeyValue("phone").asInstanceOf[Option[String]]
	}

	def setPhone( phone: Option[String]) 	={
		 this.addKeyValue("phone", phone)
	}

	def getDob() :Option[String]	={
		return  this.getKeyValue("dob").asInstanceOf[Option[String]]
	}

	def setDob( dob: Option[String]) 	={
		 this.addKeyValue("dob", dob)
	}

	def getDateFormat() :Option[String]	={
		return  this.getKeyValue("date_format").asInstanceOf[Option[String]]
	}

	def setDateFormat( dateFormat: Option[String]) 	={
		 this.addKeyValue("date_format", dateFormat)
	}

	def getStatus() :Option[String]	={
		return  this.getKeyValue("status").asInstanceOf[Option[String]]
	}

	def setStatus( status: Option[String]) 	={
		 this.addKeyValue("status", status)
	}

	def getName() :Option[String]	={
		return  this.getKeyValue("name").asInstanceOf[Option[String]]
	}

	def setName( name: Option[String]) 	={
		 this.addKeyValue("name", name)
	}}