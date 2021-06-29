package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class MassUpdateActionWrapper extends Model with MassUpdateActionHandler	{
	private var data:ArrayBuffer[MassUpdateActionResponse] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[MassUpdateActionResponse]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[MassUpdateActionResponse]) 	={
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