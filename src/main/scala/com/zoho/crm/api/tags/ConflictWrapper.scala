package com.zoho.crm.api.tags

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ConflictWrapper extends Model	{
	private var conflictId:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getConflictId() :Option[String]	={
		return  this.conflictId
	}

	def setConflictId( conflictId: Option[String]) 	={
		 this.conflictId = conflictId
		 this.keyModified("conflict_id") = 1
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