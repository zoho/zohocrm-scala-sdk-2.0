package com.zoho.crm.api.tags

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class RecordActionWrapper extends Model with RecordActionHandler	{
	private var data:ArrayBuffer[RecordActionResponse] = _
	private var wfScheduler:Option[Boolean] = None
	private var successCount:Option[String] = None
	private var lockedCount:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[RecordActionResponse]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[RecordActionResponse]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getWfScheduler() :Option[Boolean]	={
		return  this.wfScheduler
	}

	def setWfScheduler( wfScheduler: Option[Boolean]) 	={
		 this.wfScheduler = wfScheduler
		 this.keyModified("wf_scheduler") = 1
	}

	def getSuccessCount() :Option[String]	={
		return  this.successCount
	}

	def setSuccessCount( successCount: Option[String]) 	={
		 this.successCount = successCount
		 this.keyModified("success_count") = 1
	}

	def getLockedCount() :Option[Int]	={
		return  this.lockedCount
	}

	def setLockedCount( lockedCount: Option[Int]) 	={
		 this.lockedCount = lockedCount
		 this.keyModified("locked_count") = 1
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