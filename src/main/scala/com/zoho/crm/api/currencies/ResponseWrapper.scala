package com.zoho.crm.api.currencies

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var currencies:ArrayBuffer[Currency] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCurrencies() :ArrayBuffer[Currency]	={
		return  this.currencies
	}

	def setCurrencies( currencies: ArrayBuffer[Currency]) 	={
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