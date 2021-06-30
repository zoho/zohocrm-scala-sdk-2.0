package com.zoho.crm.api.blueprint

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ResponseWrapper extends Model with ResponseHandler	{
	private var blueprint:Option[BluePrint] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getBlueprint() :Option[BluePrint]	={
		return  this.blueprint
	}

	def setBlueprint( blueprint: Option[BluePrint]) 	={
		 this.blueprint = blueprint
		 this.keyModified("blueprint") = 1
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