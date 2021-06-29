package com.zoho.crm.api.attachments

import com.zoho.crm.api.util.StreamWrapper
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class FileBodyWrapper extends Model with ResponseHandler	{
	private var file:Option[StreamWrapper] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getFile() :Option[StreamWrapper]	={
		return  this.file
	}

	def setFile( file: Option[StreamWrapper]) 	={
		 this.file = file
		 this.keyModified("file") = 1
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