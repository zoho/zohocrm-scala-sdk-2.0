package com.zoho.crm.api.notification

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var watch:ArrayBuffer[Notification] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getWatch() :ArrayBuffer[Notification]	={
		return  this.watch
	}

	def setWatch( watch: ArrayBuffer[Notification]) 	={
		 this.watch = watch
		 this.keyModified("watch") = 1
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