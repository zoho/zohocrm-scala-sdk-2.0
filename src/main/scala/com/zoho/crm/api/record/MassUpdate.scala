package com.zoho.crm.api.record

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class MassUpdate extends Model with MassUpdateResponse	{
	private var status:Choice[String] = _
	private var failedCount:Option[Int] = None
	private var updatedCount:Option[Int] = None
	private var notUpdatedCount:Option[Int] = None
	private var totalCount:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getStatus() :Choice[String]	={
		return  this.status
	}

	def setStatus( status: Choice[String]) 	={
		 this.status = status
		 this.keyModified("Status") = 1
	}

	def getFailedCount() :Option[Int]	={
		return  this.failedCount
	}

	def setFailedCount( failedCount: Option[Int]) 	={
		 this.failedCount = failedCount
		 this.keyModified("Failed_Count") = 1
	}

	def getUpdatedCount() :Option[Int]	={
		return  this.updatedCount
	}

	def setUpdatedCount( updatedCount: Option[Int]) 	={
		 this.updatedCount = updatedCount
		 this.keyModified("Updated_Count") = 1
	}

	def getNotUpdatedCount() :Option[Int]	={
		return  this.notUpdatedCount
	}

	def setNotUpdatedCount( notUpdatedCount: Option[Int]) 	={
		 this.notUpdatedCount = notUpdatedCount
		 this.keyModified("Not_Updated_Count") = 1
	}

	def getTotalCount() :Option[Int]	={
		return  this.totalCount
	}

	def setTotalCount( totalCount: Option[Int]) 	={
		 this.totalCount = totalCount
		 this.keyModified("Total_Count") = 1
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