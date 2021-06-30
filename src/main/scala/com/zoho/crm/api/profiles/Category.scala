package com.zoho.crm.api.profiles

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Category extends Model	{
	private var displayLabel:Option[String] = None
	private var permissionsDetails:ArrayBuffer[String] = _
	private var name:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDisplayLabel() :Option[String]	={
		return  this.displayLabel
	}

	def setDisplayLabel( displayLabel: Option[String]) 	={
		 this.displayLabel = displayLabel
		 this.keyModified("display_label") = 1
	}

	def getPermissionsDetails() :ArrayBuffer[String]	={
		return  this.permissionsDetails
	}

	def setPermissionsDetails( permissionsDetails: ArrayBuffer[String]) 	={
		 this.permissionsDetails = permissionsDetails
		 this.keyModified("permissions_details") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
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