package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class File extends Model	{
	private var status:Choice[String] = _
	private var name:Option[String] = None
	private var addedCount:Option[Int] = None
	private var skippedCount:Option[Int] = None
	private var updatedCount:Option[Int] = None
	private var totalCount:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getStatus() :Choice[String]	={
		return  this.status
	}

	def setStatus( status: Choice[String]) 	={
		 this.status = status
		 this.keyModified("status") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getAddedCount() :Option[Int]	={
		return  this.addedCount
	}

	def setAddedCount( addedCount: Option[Int]) 	={
		 this.addedCount = addedCount
		 this.keyModified("added_count") = 1
	}

	def getSkippedCount() :Option[Int]	={
		return  this.skippedCount
	}

	def setSkippedCount( skippedCount: Option[Int]) 	={
		 this.skippedCount = skippedCount
		 this.keyModified("skipped_count") = 1
	}

	def getUpdatedCount() :Option[Int]	={
		return  this.updatedCount
	}

	def setUpdatedCount( updatedCount: Option[Int]) 	={
		 this.updatedCount = updatedCount
		 this.keyModified("updated_count") = 1
	}

	def getTotalCount() :Option[Int]	={
		return  this.totalCount
	}

	def setTotalCount( totalCount: Option[Int]) 	={
		 this.totalCount = totalCount
		 this.keyModified("total_count") = 1
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