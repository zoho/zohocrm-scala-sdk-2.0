package com.zoho.crm.api.blueprint

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ValidationError extends Model	{
	private var apiName:Option[String] = None
	private var message:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getMessage() :Option[String]	={
		return  this.message
	}

	def setMessage( message: Option[String]) 	={
		 this.message = message
		 this.keyModified("message") = 1
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