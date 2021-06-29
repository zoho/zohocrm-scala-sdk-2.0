package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class RecurringActivity extends Model	{
	private var rrule:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getRrule() :Option[String]	={
		return  this.rrule
	}

	def setRrule( rrule: Option[String]) 	={
		 this.rrule = rrule
		 this.keyModified("RRULE") = 1
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