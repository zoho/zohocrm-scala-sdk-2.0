package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class LineTax extends Model	{
	private var percentage:Option[Double] = None
	private var name:Option[String] = None
	private var id:Option[Long] = None
	private var value:Option[Double] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPercentage() :Option[Double]	={
		return  this.percentage
	}

	def setPercentage( percentage: Option[Double]) 	={
		 this.percentage = percentage
		 this.keyModified("percentage") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getValue() :Option[Double]	={
		return  this.value
	}

	def setValue( value: Option[Double]) 	={
		 this.value = value
		 this.keyModified("value") = 1
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