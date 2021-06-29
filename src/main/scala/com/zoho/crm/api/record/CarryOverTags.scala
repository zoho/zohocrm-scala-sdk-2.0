package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class CarryOverTags extends Model	{
	private var contacts:ArrayBuffer[String] = _
	private var accounts:ArrayBuffer[String] = _
	private var deals:ArrayBuffer[String] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getContacts() :ArrayBuffer[String]	={
		return  this.contacts
	}

	def setContacts( contacts: ArrayBuffer[String]) 	={
		 this.contacts = contacts
		 this.keyModified("Contacts") = 1
	}

	def getAccounts() :ArrayBuffer[String]	={
		return  this.accounts
	}

	def setAccounts( accounts: ArrayBuffer[String]) 	={
		 this.accounts = accounts
		 this.keyModified("Accounts") = 1
	}

	def getDeals() :ArrayBuffer[String]	={
		return  this.deals
	}

	def setDeals( deals: ArrayBuffer[String]) 	={
		 this.deals = deals
		 this.keyModified("Deals") = 1
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