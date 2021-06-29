package com.zoho.crm.api.currencies

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Format extends Model	{
	private var decimalSeparator:Choice[String] = _
	private var thousandSeparator:Choice[String] = _
	private var decimalPlaces:Choice[String] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDecimalSeparator() :Choice[String]	={
		return  this.decimalSeparator
	}

	def setDecimalSeparator( decimalSeparator: Choice[String]) 	={
		 this.decimalSeparator = decimalSeparator
		 this.keyModified("decimal_separator") = 1
	}

	def getThousandSeparator() :Choice[String]	={
		return  this.thousandSeparator
	}

	def setThousandSeparator( thousandSeparator: Choice[String]) 	={
		 this.thousandSeparator = thousandSeparator
		 this.keyModified("thousand_separator") = 1
	}

	def getDecimalPlaces() :Choice[String]	={
		return  this.decimalPlaces
	}

	def setDecimalPlaces( decimalPlaces: Choice[String]) 	={
		 this.decimalPlaces = decimalPlaces
		 this.keyModified("decimal_places") = 1
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