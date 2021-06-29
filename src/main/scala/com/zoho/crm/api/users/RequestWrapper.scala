package com.zoho.crm.api.users

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class RequestWrapper extends Model	{
	private var users:ArrayBuffer[User] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getUsers() :ArrayBuffer[User]	={
		return  this.users
	}

	def setUsers( users: ArrayBuffer[User]) 	={
		 this.users = users
		 this.keyModified("users") = 1
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