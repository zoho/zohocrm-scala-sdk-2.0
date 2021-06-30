package com.zoho.crm.api.sharerecords

import com.zoho.crm.api.modules.Module
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class SharedThrough extends Model	{
	private var module:Option[Module] = None
	private var id:Option[Long] = None
	private var entityName:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getModule() :Option[Module]	={
		return  this.module
	}

	def setModule( module: Option[Module]) 	={
		 this.module = module
		 this.keyModified("module") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getEntityName() :Option[String]	={
		return  this.entityName
	}

	def setEntityName( entityName: Option[String]) 	={
		 this.entityName = entityName
		 this.keyModified("entity_name") = 1
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