package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ConvertActionWrapper extends Model with ConvertActionHandler	{
	private var data:ArrayBuffer[ConvertActionResponse] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[ConvertActionResponse]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[ConvertActionResponse]) 	={
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