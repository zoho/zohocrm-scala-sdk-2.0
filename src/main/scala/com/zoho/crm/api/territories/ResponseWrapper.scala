package com.zoho.crm.api.territories

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var territories:ArrayBuffer[Territory] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getTerritories() :ArrayBuffer[Territory]	={
		return  this.territories
	}

	def setTerritories( territories: ArrayBuffer[Territory]) 	={
		 this.territories = territories
		 this.keyModified("territories") = 1
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