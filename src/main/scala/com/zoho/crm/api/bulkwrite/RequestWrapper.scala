package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class RequestWrapper extends Model	{
	private var characterEncoding:Option[String] = None
	private var operation:Choice[String] = _
	private var callback:Option[CallBack] = None
	private var resource:ArrayBuffer[Resource] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCharacterEncoding() :Option[String]	={
		return  this.characterEncoding
	}

	def setCharacterEncoding( characterEncoding: Option[String]) 	={
		 this.characterEncoding = characterEncoding
		 this.keyModified("character_encoding") = 1
	}

	def getOperation() :Choice[String]	={
		return  this.operation
	}

	def setOperation( operation: Choice[String]) 	={
		 this.operation = operation
		 this.keyModified("operation") = 1
	}

	def getCallback() :Option[CallBack]	={
		return  this.callback
	}

	def setCallback( callback: Option[CallBack]) 	={
		 this.callback = callback
		 this.keyModified("callback") = 1
	}

	def getResource() :ArrayBuffer[Resource]	={
		return  this.resource
	}

	def setResource( resource: ArrayBuffer[Resource]) 	={
		 this.resource = resource
		 this.keyModified("resource") = 1
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