package com.zoho.crm.api.roles

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var roles:ArrayBuffer[Role] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getRoles() :ArrayBuffer[Role]	={
		return  this.roles
	}

	def setRoles( roles: ArrayBuffer[Role]) 	={
		 this.roles = roles
		 this.keyModified("roles") = 1
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