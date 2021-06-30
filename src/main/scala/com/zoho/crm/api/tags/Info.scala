package com.zoho.crm.api.tags

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Info extends Model	{
	private var count:Option[Int] = None
	private var allowedCount:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCount() :Option[Int]	={
		return  this.count
	}

	def setCount( count: Option[Int]) 	={
		 this.count = count
		 this.keyModified("count") = 1
	}

	def getAllowedCount() :Option[Int]	={
		return  this.allowedCount
	}

	def setAllowedCount( allowedCount: Option[Int]) 	={
		 this.allowedCount = allowedCount
		 this.keyModified("allowed_count") = 1
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