package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Resource extends Model	{
	private var status:Choice[String] = _
	private var type1:Choice[String] = _
	private var module:Option[String] = None
	private var fileId:Option[String] = None
	private var ignoreEmpty:Option[Boolean] = None
	private var findBy:Option[String] = None
	private var fieldMappings:ArrayBuffer[FieldMapping] = _
	private var file:Option[File] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getStatus() :Choice[String]	={
		return  this.status
	}

	def setStatus( status: Choice[String]) 	={
		 this.status = status
		 this.keyModified("status") = 1
	}

	def getType() :Choice[String]	={
		return  this.type1
	}

	def setType( type1: Choice[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
	}

	def getModule() :Option[String]	={
		return  this.module
	}

	def setModule( module: Option[String]) 	={
		 this.module = module
		 this.keyModified("module") = 1
	}

	def getFileId() :Option[String]	={
		return  this.fileId
	}

	def setFileId( fileId: Option[String]) 	={
		 this.fileId = fileId
		 this.keyModified("file_id") = 1
	}

	def getIgnoreEmpty() :Option[Boolean]	={
		return  this.ignoreEmpty
	}

	def setIgnoreEmpty( ignoreEmpty: Option[Boolean]) 	={
		 this.ignoreEmpty = ignoreEmpty
		 this.keyModified("ignore_empty") = 1
	}

	def getFindBy() :Option[String]	={
		return  this.findBy
	}

	def setFindBy( findBy: Option[String]) 	={
		 this.findBy = findBy
		 this.keyModified("find_by") = 1
	}

	def getFieldMappings() :ArrayBuffer[FieldMapping]	={
		return  this.fieldMappings
	}

	def setFieldMappings( fieldMappings: ArrayBuffer[FieldMapping]) 	={
		 this.fieldMappings = fieldMappings
		 this.keyModified("field_mappings") = 1
	}

	def getFile() :Option[File]	={
		return  this.file
	}

	def setFile( file: Option[File]) 	={
		 this.file = file
		 this.keyModified("file") = 1
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