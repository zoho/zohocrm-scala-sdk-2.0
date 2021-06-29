package com.zoho.crm.api.sharerecords

import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class ShareRecord extends Model	{
	private var shareRelatedRecords:Option[Boolean] = None
	private var sharedThrough:Option[SharedThrough] = None
	private var sharedTime:Option[OffsetDateTime] = None
	private var permission:Option[String] = None
	private var sharedBy:Option[User] = None
	private var user:Option[User] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getShareRelatedRecords() :Option[Boolean]	={
		return  this.shareRelatedRecords
	}

	def setShareRelatedRecords( shareRelatedRecords: Option[Boolean]) 	={
		 this.shareRelatedRecords = shareRelatedRecords
		 this.keyModified("share_related_records") = 1
	}

	def getSharedThrough() :Option[SharedThrough]	={
		return  this.sharedThrough
	}

	def setSharedThrough( sharedThrough: Option[SharedThrough]) 	={
		 this.sharedThrough = sharedThrough
		 this.keyModified("shared_through") = 1
	}

	def getSharedTime() :Option[OffsetDateTime]	={
		return  this.sharedTime
	}

	def setSharedTime( sharedTime: Option[OffsetDateTime]) 	={
		 this.sharedTime = sharedTime
		 this.keyModified("shared_time") = 1
	}

	def getPermission() :Option[String]	={
		return  this.permission
	}

	def setPermission( permission: Option[String]) 	={
		 this.permission = permission
		 this.keyModified("permission") = 1
	}

	def getSharedBy() :Option[User]	={
		return  this.sharedBy
	}

	def setSharedBy( sharedBy: Option[User]) 	={
		 this.sharedBy = sharedBy
		 this.keyModified("shared_by") = 1
	}

	def getUser() :Option[User]	={
		return  this.user
	}

	def setUser( user: Option[User]) 	={
		 this.user = user
		 this.keyModified("user") = 1
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