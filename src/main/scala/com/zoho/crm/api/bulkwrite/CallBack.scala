package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class CallBack extends Model	{
	private var url:Option[String] = None
	private var method:Choice[String] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getUrl() :Option[String]	={
		return  this.url
	}

	def setUrl( url: Option[String]) 	={
		 this.url = url
		 this.keyModified("url") = 1
	}

	def getMethod() :Choice[String]	={
		return  this.method
	}

	def setMethod( method: Choice[String]) 	={
		 this.method = method
		 this.keyModified("method") = 1
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