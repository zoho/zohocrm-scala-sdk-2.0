package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BulkWriteResponse extends Model with ResponseWrapper	{
	private var status:Option[String] = None
	private var characterEncoding:Option[String] = None
	private var resource:ArrayBuffer[Resource] = _
	private var id:Option[Long] = None
	private var callback:Option[CallBack] = None
	private var result:Option[Result] = None
	private var createdBy:Option[User] = None
	private var operation:Option[String] = None
	private var createdTime:Option[OffsetDateTime] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getStatus() :Option[String]	={
		return  this.status
	}

	def setStatus( status: Option[String]) 	={
		 this.status = status
		 this.keyModified("status") = 1
	}

	def getCharacterEncoding() :Option[String]	={
		return  this.characterEncoding
	}

	def setCharacterEncoding( characterEncoding: Option[String]) 	={
		 this.characterEncoding = characterEncoding
		 this.keyModified("character_encoding") = 1
	}

	def getResource() :ArrayBuffer[Resource]	={
		return  this.resource
	}

	def setResource( resource: ArrayBuffer[Resource]) 	={
		 this.resource = resource
		 this.keyModified("resource") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getCallback() :Option[CallBack]	={
		return  this.callback
	}

	def setCallback( callback: Option[CallBack]) 	={
		 this.callback = callback
		 this.keyModified("callback") = 1
	}

	def getResult() :Option[Result]	={
		return  this.result
	}

	def setResult( result: Option[Result]) 	={
		 this.result = result
		 this.keyModified("result") = 1
	}

	def getCreatedBy() :Option[User]	={
		return  this.createdBy
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.createdBy = createdBy
		 this.keyModified("created_by") = 1
	}

	def getOperation() :Option[String]	={
		return  this.operation
	}

	def setOperation( operation: Option[String]) 	={
		 this.operation = operation
		 this.keyModified("operation") = 1
	}

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.createdTime
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.createdTime = createdTime
		 this.keyModified("created_time") = 1
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