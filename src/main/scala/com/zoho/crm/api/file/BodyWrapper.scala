package com.zoho.crm.api.file

import com.zoho.crm.api.util.StreamWrapper
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var file:ArrayBuffer[StreamWrapper] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getFile() :ArrayBuffer[StreamWrapper]	={
		return  this.file
	}

	def setFile( file: ArrayBuffer[StreamWrapper]) 	={
		 this.file = file
		 this.keyModified("file") = 1
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