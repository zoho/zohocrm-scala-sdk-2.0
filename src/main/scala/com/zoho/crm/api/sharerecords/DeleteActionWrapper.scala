package com.zoho.crm.api.sharerecords

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class DeleteActionWrapper extends Model with DeleteActionHandler	{
	private var share:Option[DeleteActionResponse] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getShare() :Option[DeleteActionResponse]	={
		return  this.share
	}

	def setShare( share: Option[DeleteActionResponse]) 	={
		 this.share = share
		 this.keyModified("share") = 1
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