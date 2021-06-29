package com.zoho.crm.api.sharerecords

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var share:ArrayBuffer[ShareRecord] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getShare() :ArrayBuffer[ShareRecord]	={
		return  this.share
	}

	def setShare( share: ArrayBuffer[ShareRecord]) 	={
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