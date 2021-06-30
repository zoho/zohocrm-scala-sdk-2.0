package com.zoho.crm.api.record

import com.zoho.crm.api.tags.Tag
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Record extends Model	{
	protected var keyValues:HashMap[String, Any] = HashMap()
	private var keyModified:HashMap[String, Int] = HashMap()

	def getId() :Option[Long]	={
		return  this.getKeyValue("id").asInstanceOf[Option[Long]]
	}

	def setId( id: Option[Long]) 	={
		 this.addKeyValue("id", id)
	}

	def getCreatedBy() :Option[User]	={
		return  this.getKeyValue("Created_By").asInstanceOf[Option[User]]
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.addKeyValue("Created_By", createdBy)
	}

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.getKeyValue("Created_Time").asInstanceOf[Option[OffsetDateTime]]
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.addKeyValue("Created_Time", createdTime)
	}

	def getModifiedBy() :Option[User]	={
		return  this.getKeyValue("Modified_By").asInstanceOf[Option[User]]
	}

	def setModifiedBy( modifiedBy: Option[User]) 	={
		 this.addKeyValue("Modified_By", modifiedBy)
	}

	def getModifiedTime() :Option[OffsetDateTime]	={
		return  this.getKeyValue("Modified_Time").asInstanceOf[Option[OffsetDateTime]]
	}

	def setModifiedTime( modifiedTime: Option[OffsetDateTime]) 	={
		 this.addKeyValue("Modified_Time", modifiedTime)
	}

	def getTag() :ArrayBuffer[Tag]	={
		return  this.getKeyValue("Tag").asInstanceOf[ArrayBuffer[Tag]]
	}

	def setTag( tag: ArrayBuffer[Tag]) 	={
		 this.addKeyValue("Tag", tag)
	}

	def addFieldValue[T]( field: Field[T],  value: T) 	={
		 this.addKeyValue(field.getAPIName(), value.asInstanceOf[Any])
	}

	def addKeyValue( apiName: String,  value: Any) 	={
		 this.keyValues(apiName) = value.asInstanceOf[Any]
		 this.keyModified(apiName) = 1
	}

	def getKeyValue( apiName: String) :Any	={
		if((( this.keyValues.contains(apiName))))
		{
			return  this.keyValues(apiName)
		}
		return None
	}

	def getKeyValues() :HashMap[String, Any]	={
		return  this.keyValues
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