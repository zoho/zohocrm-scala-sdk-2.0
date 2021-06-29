package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class RemindAt extends Model	{
	private var alarm:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getAlarm() :Option[String]	={
		return  this.alarm
	}

	def setAlarm( alarm: Option[String]) 	={
		 this.alarm = alarm
		 this.keyModified("ALARM") = 1
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