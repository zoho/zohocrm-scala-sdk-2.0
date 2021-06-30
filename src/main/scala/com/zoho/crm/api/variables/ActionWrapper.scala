package com.zoho.crm.api.variables

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ActionWrapper extends Model with ActionHandler	{
	private var variables:ArrayBuffer[ActionResponse] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getVariables() :ArrayBuffer[ActionResponse]	={
		return  this.variables
	}

	def setVariables( variables: ArrayBuffer[ActionResponse]) 	={
		 this.variables = variables
		 this.keyModified("variables") = 1
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