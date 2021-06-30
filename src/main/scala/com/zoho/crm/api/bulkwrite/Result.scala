package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Result extends Model	{
	private var downloadUrl:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDownloadUrl() :Option[String]	={
		return  this.downloadUrl
	}

	def setDownloadUrl( downloadUrl: Option[String]) 	={
		 this.downloadUrl = downloadUrl
		 this.keyModified("download_url") = 1
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