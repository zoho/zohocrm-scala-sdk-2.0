package com.zoho.crm.api.variablegroups

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var variableGroups:ArrayBuffer[VariableGroup] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getVariableGroups() :ArrayBuffer[VariableGroup]	={
		return  this.variableGroups
	}

	def setVariableGroups( variableGroups: ArrayBuffer[VariableGroup]) 	={
		 this.variableGroups = variableGroups
		 this.keyModified("variable_groups") = 1
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