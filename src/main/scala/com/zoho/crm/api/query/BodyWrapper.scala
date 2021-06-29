package com.zoho.crm.api.query

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class BodyWrapper extends Model	{
	private var selectQuery:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getSelectQuery() :Option[String]	={
		return  this.selectQuery
	}

	def setSelectQuery( selectQuery: Option[String]) 	={
		 this.selectQuery = selectQuery
		 this.keyModified("select_query") = 1
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