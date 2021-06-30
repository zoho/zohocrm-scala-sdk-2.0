package com.zoho.crm.api.bulkread

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Query extends Model	{
	private var module:Option[String] = None
	private var cvid:Option[String] = None
	private var fields:ArrayBuffer[String] = _
	private var page:Option[Int] = None
	private var criteria:Option[Criteria] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getModule() :Option[String]	={
		return  this.module
	}

	def setModule( module: Option[String]) 	={
		 this.module = module
		 this.keyModified("module") = 1
	}

	def getCvid() :Option[String]	={
		return  this.cvid
	}

	def setCvid( cvid: Option[String]) 	={
		 this.cvid = cvid
		 this.keyModified("cvid") = 1
	}

	def getFields() :ArrayBuffer[String]	={
		return  this.fields
	}

	def setFields( fields: ArrayBuffer[String]) 	={
		 this.fields = fields
		 this.keyModified("fields") = 1
	}

	def getPage() :Option[Int]	={
		return  this.page
	}

	def setPage( page: Option[Int]) 	={
		 this.page = page
		 this.keyModified("page") = 1
	}

	def getCriteria() :Option[Criteria]	={
		return  this.criteria
	}

	def setCriteria( criteria: Option[Criteria]) 	={
		 this.criteria = criteria
		 this.keyModified("criteria") = 1
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