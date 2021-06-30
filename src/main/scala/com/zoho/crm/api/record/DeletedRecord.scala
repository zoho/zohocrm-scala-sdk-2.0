package com.zoho.crm.api.record

import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class DeletedRecord extends Model	{
	private var deletedBy:Option[User] = None
	private var id:Option[Long] = None
	private var displayName:Option[String] = None
	private var type1:Option[String] = None
	private var createdBy:Option[User] = None
	private var deletedTime:Option[OffsetDateTime] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDeletedBy() :Option[User]	={
		return  this.deletedBy
	}

	def setDeletedBy( deletedBy: Option[User]) 	={
		 this.deletedBy = deletedBy
		 this.keyModified("deleted_by") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getDisplayName() :Option[String]	={
		return  this.displayName
	}

	def setDisplayName( displayName: Option[String]) 	={
		 this.displayName = displayName
		 this.keyModified("display_name") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
	}

	def getCreatedBy() :Option[User]	={
		return  this.createdBy
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.createdBy = createdBy
		 this.keyModified("created_by") = 1
	}

	def getDeletedTime() :Option[OffsetDateTime]	={
		return  this.deletedTime
	}

	def setDeletedTime( deletedTime: Option[OffsetDateTime]) 	={
		 this.deletedTime = deletedTime
		 this.keyModified("deleted_time") = 1
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