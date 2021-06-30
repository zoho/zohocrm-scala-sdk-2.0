package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class DeletedRecordsWrapper extends Model with DeletedRecordsHandler	{
	private var data:ArrayBuffer[DeletedRecord] = _
	private var info:Option[Info] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[DeletedRecord]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[DeletedRecord]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getInfo() :Option[Info]	={
		return  this.info
	}

	def setInfo( info: Option[Info]) 	={
		 this.info = info
		 this.keyModified("info") = 1
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