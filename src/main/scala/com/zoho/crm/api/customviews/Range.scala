package com.zoho.crm.api.customviews

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Range extends Model	{
	private var from:Option[Int] = None
	private var to:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getFrom() :Option[Int]	={
		return  this.from
	}

	def setFrom( from: Option[Int]) 	={
		 this.from = from
		 this.keyModified("from") = 1
	}

	def getTo() :Option[Int]	={
		return  this.to
	}

	def setTo( to: Option[Int]) 	={
		 this.to = to
		 this.keyModified("to") = 1
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