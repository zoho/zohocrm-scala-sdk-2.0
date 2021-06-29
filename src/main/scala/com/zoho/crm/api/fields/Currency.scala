package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Currency extends Model	{
	private var roundingOption:Option[String] = None
	private var precision:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getRoundingOption() :Option[String]	={
		return  this.roundingOption
	}

	def setRoundingOption( roundingOption: Option[String]) 	={
		 this.roundingOption = roundingOption
		 this.keyModified("rounding_option") = 1
	}

	def getPrecision() :Option[Int]	={
		return  this.precision
	}

	def setPrecision( precision: Option[Int]) 	={
		 this.precision = precision
		 this.keyModified("precision") = 1
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