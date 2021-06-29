package com.zoho.crm.api.currencies

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class BaseCurrencyWrapper extends Model	{
	private var baseCurrency:Option[Currency] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getBaseCurrency() :Option[Currency]	={
		return  this.baseCurrency
	}

	def setBaseCurrency( baseCurrency: Option[Currency]) 	={
		 this.baseCurrency = baseCurrency
		 this.keyModified("base_currency") = 1
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