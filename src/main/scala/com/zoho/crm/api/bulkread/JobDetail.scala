package com.zoho.crm.api.bulkread

import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class JobDetail extends Model	{
	private var id:Option[Long] = None
	private var operation:Option[String] = None
	private var state:Choice[String] = _
	private var query:Option[Query] = None
	private var createdBy:Option[User] = None
	private var createdTime:Option[OffsetDateTime] = None
	private var result:Option[Result] = None
	private var fileType:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getOperation() :Option[String]	={
		return  this.operation
	}

	def setOperation( operation: Option[String]) 	={
		 this.operation = operation
		 this.keyModified("operation") = 1
	}

	def getState() :Choice[String]	={
		return  this.state
	}

	def setState( state: Choice[String]) 	={
		 this.state = state
		 this.keyModified("state") = 1
	}

	def getQuery() :Option[Query]	={
		return  this.query
	}

	def setQuery( query: Option[Query]) 	={
		 this.query = query
		 this.keyModified("query") = 1
	}

	def getCreatedBy() :Option[User]	={
		return  this.createdBy
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.createdBy = createdBy
		 this.keyModified("created_by") = 1
	}

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.createdTime
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.createdTime = createdTime
		 this.keyModified("created_time") = 1
	}

	def getResult() :Option[Result]	={
		return  this.result
	}

	def setResult( result: Option[Result]) 	={
		 this.result = result
		 this.keyModified("result") = 1
	}

	def getFileType() :Option[String]	={
		return  this.fileType
	}

	def setFileType( fileType: Option[String]) 	={
		 this.fileType = fileType
		 this.keyModified("file_type") = 1
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