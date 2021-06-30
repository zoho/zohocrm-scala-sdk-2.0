package com.zoho.crm.api.currencies

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class BaseCurrencyActionWrapper extends Model with BaseCurrencyActionHandler	{
	private var baseCurrency:Option[ActionResponse] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getBaseCurrency() :Option[ActionResponse]	={
		return  this.baseCurrency
	}

	def setBaseCurrency( baseCurrency: Option[ActionResponse]) 	={
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