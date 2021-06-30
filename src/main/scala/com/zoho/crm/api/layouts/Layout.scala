package com.zoho.crm.api.layouts

import com.zoho.crm.api.profiles.Profile
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Layout extends Model	{
	private var createdTime:Option[OffsetDateTime] = None
	private var convertMapping:HashMap[String, Any] = _
	private var modifiedTime:Option[OffsetDateTime] = None
	private var visible:Option[Boolean] = None
	private var createdFor:Option[User] = None
	private var name:Option[String] = None
	private var modifiedBy:Option[User] = None
	private var profiles:ArrayBuffer[Profile] = _
	private var id:Option[Long] = None
	private var createdBy:Option[User] = None
	private var sections:ArrayBuffer[Section] = _
	private var status:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.createdTime
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.createdTime = createdTime
		 this.keyModified("created_time") = 1
	}

	def getConvertMapping() :HashMap[String, Any]	={
		return  this.convertMapping
	}

	def setConvertMapping( convertMapping: HashMap[String, Any]) 	={
		 this.convertMapping = convertMapping
		 this.keyModified("convert_mapping") = 1
	}

	def getModifiedTime() :Option[OffsetDateTime]	={
		return  this.modifiedTime
	}

	def setModifiedTime( modifiedTime: Option[OffsetDateTime]) 	={
		 this.modifiedTime = modifiedTime
		 this.keyModified("modified_time") = 1
	}

	def getVisible() :Option[Boolean]	={
		return  this.visible
	}

	def setVisible( visible: Option[Boolean]) 	={
		 this.visible = visible
		 this.keyModified("visible") = 1
	}

	def getCreatedFor() :Option[User]	={
		return  this.createdFor
	}

	def setCreatedFor( createdFor: Option[User]) 	={
		 this.createdFor = createdFor
		 this.keyModified("created_for") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getModifiedBy() :Option[User]	={
		return  this.modifiedBy
	}

	def setModifiedBy( modifiedBy: Option[User]) 	={
		 this.modifiedBy = modifiedBy
		 this.keyModified("modified_by") = 1
	}

	def getProfiles() :ArrayBuffer[Profile]	={
		return  this.profiles
	}

	def setProfiles( profiles: ArrayBuffer[Profile]) 	={
		 this.profiles = profiles
		 this.keyModified("profiles") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getCreatedBy() :Option[User]	={
		return  this.createdBy
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.createdBy = createdBy
		 this.keyModified("created_by") = 1
	}

	def getSections() :ArrayBuffer[Section]	={
		return  this.sections
	}

	def setSections( sections: ArrayBuffer[Section]) 	={
		 this.sections = sections
		 this.keyModified("sections") = 1
	}

	def getStatus() :Option[Int]	={
		return  this.status
	}

	def setStatus( status: Option[Int]) 	={
		 this.status = status
		 this.keyModified("status") = 1
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