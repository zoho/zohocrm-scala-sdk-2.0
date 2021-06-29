package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class AutoNumber extends Model	{
	private var prefix:Option[String] = None
	private var suffix:Option[String] = None
	private var startNumber:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPrefix() :Option[String]	={
		return  this.prefix
	}

	def setPrefix( prefix: Option[String]) 	={
		 this.prefix = prefix
		 this.keyModified("prefix") = 1
	}

	def getSuffix() :Option[String]	={
		return  this.suffix
	}

	def setSuffix( suffix: Option[String]) 	={
		 this.suffix = suffix
		 this.keyModified("suffix") = 1
	}

	def getStartNumber() :Option[Int]	={
		return  this.startNumber
	}

	def setStartNumber( startNumber: Option[Int]) 	={
		 this.startNumber = startNumber
		 this.keyModified("start_number") = 1
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