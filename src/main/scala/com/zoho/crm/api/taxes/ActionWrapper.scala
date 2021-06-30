package com.zoho.crm.api.taxes

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ActionWrapper extends Model with ActionHandler	{
	private var taxes:ArrayBuffer[ActionResponse] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getTaxes() :ArrayBuffer[ActionResponse]	={
		return  this.taxes
	}

	def setTaxes( taxes: ArrayBuffer[ActionResponse]) 	={
		 this.taxes = taxes
		 this.keyModified("taxes") = 1
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