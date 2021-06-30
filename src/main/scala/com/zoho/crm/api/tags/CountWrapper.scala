package com.zoho.crm.api.tags

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class CountWrapper extends Model with CountHandler	{
	private var count:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCount() :Option[String]	={
		return  this.count
	}

	def setCount( count: Option[String]) 	={
		 this.count = count
		 this.keyModified("count") = 1
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