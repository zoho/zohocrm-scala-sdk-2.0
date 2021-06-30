package com.zoho.crm.api.taxes

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var taxes:ArrayBuffer[Tax] = _
	private var preference:Option[Preference] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getTaxes() :ArrayBuffer[Tax]	={
		return  this.taxes
	}

	def setTaxes( taxes: ArrayBuffer[Tax]) 	={
		 this.taxes = taxes
		 this.keyModified("taxes") = 1
	}

	def getPreference() :Option[Preference]	={
		return  this.preference
	}

	def setPreference( preference: Option[Preference]) 	={
		 this.preference = preference
		 this.keyModified("preference") = 1
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