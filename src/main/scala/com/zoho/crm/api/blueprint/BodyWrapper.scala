package com.zoho.crm.api.blueprint

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var blueprint:ArrayBuffer[BluePrint] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getBlueprint() :ArrayBuffer[BluePrint]	={
		return  this.blueprint
	}

	def setBlueprint( blueprint: ArrayBuffer[BluePrint]) 	={
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