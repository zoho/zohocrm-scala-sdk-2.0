package com.zoho.crm.api.currencies

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ActionWrapper extends Model with ActionHandler	{
	private var currencies:ArrayBuffer[ActionResponse] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCurrencies() :ArrayBuffer[ActionResponse]	={
		return  this.currencies
	}

	def setCurrencies( currencies: ArrayBuffer[ActionResponse]) 	={
		 this.currencies = currencies
		 this.keyModified("currencies") = 1
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