package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Territory extends Model	{
	private var id:Option[Long] = None
	private var includeChild:Option[Boolean] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getIncludeChild() :Option[Boolean]	={
		return  this.includeChild
	}

	def setIncludeChild( includeChild: Option[Boolean]) 	={
		 this.includeChild = includeChild
		 this.keyModified("include_child") = 1
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