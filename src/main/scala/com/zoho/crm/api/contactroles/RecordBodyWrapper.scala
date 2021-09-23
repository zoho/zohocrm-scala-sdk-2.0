package com.zoho.crm.api.contactroles

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class RecordBodyWrapper extends Model	{
	private var data:ArrayBuffer[ContactRoleWrapper] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[ContactRoleWrapper]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[ContactRoleWrapper]) 	={
		 this.data = data
		 this.keyModified("data") = 1
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