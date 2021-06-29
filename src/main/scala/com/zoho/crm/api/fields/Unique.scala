package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Unique extends Model	{
	private var casesensitive:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCasesensitive() :Option[String]	={
		return  this.casesensitive
	}

	def setCasesensitive( casesensitive: Option[String]) 	={
		 this.casesensitive = casesensitive
		 this.keyModified("casesensitive") = 1
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