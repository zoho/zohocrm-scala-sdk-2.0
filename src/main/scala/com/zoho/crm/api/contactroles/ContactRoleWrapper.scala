package com.zoho.crm.api.contactroles

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ContactRoleWrapper extends Model	{
	private var contactRole:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getContactRole() :Option[String]	={
		return  this.contactRole
	}

	def setContactRole( contactRole: Option[String]) 	={
		 this.contactRole = contactRole
		 this.keyModified("Contact_Role") = 1
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