package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Reminder extends Model	{
	private var period:Option[String] = None
	private var unit:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPeriod() :Option[String]	={
		return  this.period
	}

	def setPeriod( period: Option[String]) 	={
		 this.period = period
		 this.keyModified("period") = 1
	}

	def getUnit() :Option[String]	={
		return  this.unit
	}

	def setUnit( unit: Option[String]) 	={
		 this.unit = unit
		 this.keyModified("unit") = 1
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