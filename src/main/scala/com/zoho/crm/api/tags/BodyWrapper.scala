package com.zoho.crm.api.tags

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var tags:ArrayBuffer[Tag] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getTags() :ArrayBuffer[Tag]	={
		return  this.tags
	}

	def setTags( tags: ArrayBuffer[Tag]) 	={
		 this.tags = tags
		 this.keyModified("tags") = 1
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