package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class SuccessfulConvert extends Model with ConvertActionResponse	{
	private var contacts:Option[String] = None
	private var deals:Option[String] = None
	private var accounts:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getContacts() :Option[String]	={
		return  this.contacts
	}

	def setContacts( contacts: Option[String]) 	={
		 this.contacts = contacts
		 this.keyModified("Contacts") = 1
	}

	def getDeals() :Option[String]	={
		return  this.deals
	}

	def setDeals( deals: Option[String]) 	={
		 this.deals = deals
		 this.keyModified("Deals") = 1
	}

	def getAccounts() :Option[String]	={
		return  this.accounts
	}

	def setAccounts( accounts: Option[String]) 	={
		 this.accounts = accounts
		 this.keyModified("Accounts") = 1
	}

	def isKeyModified( key: String) :Any	={
		if((( this.keyModified.contains(key))))
		{
			return  this.keyModified(key)
		}
		return None
	}

	def setKeyModified( key: String,  modification: Int) 	={
		 this.keyModified(key) = modification
	}}