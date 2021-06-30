package com.zoho.crm.api.attachments

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class Attachment extends Model	{
	private var owner:Option[User] = None
	private var modifiedTime:Option[OffsetDateTime] = None
	private var fileName:Option[String] = None
	private var createdTime:Option[OffsetDateTime] = None
	private var size:Option[Long] = None
	private var parentId:Option[Record] = None
	private var editable:Option[Boolean] = None
	private var fileId:Option[String] = None
	private var type1:Option[String] = None
	private var seModule:Option[String] = None
	private var modifiedBy:Option[User] = None
	private var state:Option[String] = None
	private var id:Option[Long] = None
	private var createdBy:Option[User] = None
	private var linkUrl:Option[String] = None
	private var description:Option[String] = None
	private var category:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getOwner() :Option[User]	={
		return  this.owner
	}

	def setOwner( owner: Option[User]) 	={
		 this.owner = owner
		 this.keyModified("Owner") = 1
	}

	def getModifiedTime() :Option[OffsetDateTime]	={
		return  this.modifiedTime
	}

	def setModifiedTime( modifiedTime: Option[OffsetDateTime]) 	={
		 this.modifiedTime = modifiedTime
		 this.keyModified("Modified_Time") = 1
	}

	def getFileName() :Option[String]	={
		return  this.fileName
	}

	def setFileName( fileName: Option[String]) 	={
		 this.fileName = fileName
		 this.keyModified("File_Name") = 1
	}

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.createdTime
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.createdTime = createdTime
		 this.keyModified("Created_Time") = 1
	}

	def getSize() :Option[Long]	={
		return  this.size
	}

	def setSize( size: Option[Long]) 	={
		 this.size = size
		 this.keyModified("Size") = 1
	}

	def getParentId() :Option[Record]	={
		return  this.parentId
	}

	def setParentId( parentId: Option[Record]) 	={
		 this.parentId = parentId
		 this.keyModified("Parent_Id") = 1
	}

	def geteditable() :Option[Boolean]	={
		return  this.editable
	}

	def seteditable( editable: Option[Boolean]) 	={
		 this.editable = editable
		 this.keyModified("$editable") = 1
	}

	def getfileId() :Option[String]	={
		return  this.fileId
	}

	def setfileId( fileId: Option[String]) 	={
		 this.fileId = fileId
		 this.keyModified("$file_id") = 1
	}

	def gettype() :Option[String]	={
		return  this.type1
	}

	def settype( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("$type") = 1
	}

	def getseModule() :Option[String]	={
		return  this.seModule
	}

	def setseModule( seModule: Option[String]) 	={
		 this.seModule = seModule
		 this.keyModified("$se_module") = 1
	}

	def getModifiedBy() :Option[User]	={
		return  this.modifiedBy
	}

	def setModifiedBy( modifiedBy: Option[User]) 	={
		 this.modifiedBy = modifiedBy
		 this.keyModified("Modified_By") = 1
	}

	def getstate() :Option[String]	={
		return  this.state
	}

	def setstate( state: Option[String]) 	={
		 this.state = state
		 this.keyModified("$state") = 1
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
		 this.keyModified("Created_By") = 1
	}

	def getlinkUrl() :Option[String]	={
		return  this.linkUrl
	}

	def setlinkUrl( linkUrl: Option[String]) 	={
		 this.linkUrl = linkUrl
		 this.keyModified("$link_url") = 1
	}

	def getDescription() :Option[String]	={
		return  this.description
	}

	def setDescription( description: Option[String]) 	={
		 this.description = description
		 this.keyModified("description") = 1
	}

	def getCategory() :Option[String]	={
		return  this.category
	}

	def setCategory( category: Option[String]) 	={
		 this.category = category
		 this.keyModified("category") = 1
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