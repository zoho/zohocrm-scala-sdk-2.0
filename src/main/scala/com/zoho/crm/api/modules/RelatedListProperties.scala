package com.zoho.crm.api.modules

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class RelatedListProperties extends Model	{
	private var sortBy:Option[String] = None
	private var fields:ArrayBuffer[String] = _
	private var sortOrder:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getSortBy() :Option[String]	={
		return  this.sortBy
	}

	def setSortBy( sortBy: Option[String]) 	={
		 this.sortBy = sortBy
		 this.keyModified("sort_by") = 1
	}

	def getFields() :ArrayBuffer[String]	={
		return  this.fields
	}

	def setFields( fields: ArrayBuffer[String]) 	={
		 this.fields = fields
		 this.keyModified("fields") = 1
	}

	def getSortOrder() :Option[String]	={
		return  this.sortOrder
	}

	def setSortOrder( sortOrder: Option[String]) 	={
		 this.sortOrder = sortOrder
		 this.keyModified("sort_order") = 1
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