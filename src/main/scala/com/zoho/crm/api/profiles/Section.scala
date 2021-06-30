package com.zoho.crm.api.profiles

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Section extends Model	{
	private var name:Option[String] = None
	private var categories:ArrayBuffer[Category] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getCategories() :ArrayBuffer[Category]	={
		return  this.categories
	}

	def setCategories( categories: ArrayBuffer[Category]) 	={
		 this.categories = categories
		 this.keyModified("categories") = 1
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