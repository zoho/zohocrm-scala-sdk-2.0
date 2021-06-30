package com.zoho.crm.api.bulkread

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Result extends Model	{
	private var page:Option[Int] = None
	private var count:Option[Int] = None
	private var downloadUrl:Option[String] = None
	private var perPage:Option[Int] = None
	private var moreRecords:Option[Boolean] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPage() :Option[Int]	={
		return  this.page
	}

	def setPage( page: Option[Int]) 	={
		 this.page = page
		 this.keyModified("page") = 1
	}

	def getCount() :Option[Int]	={
		return  this.count
	}

	def setCount( count: Option[Int]) 	={
		 this.count = count
		 this.keyModified("count") = 1
	}

	def getDownloadUrl() :Option[String]	={
		return  this.downloadUrl
	}

	def setDownloadUrl( downloadUrl: Option[String]) 	={
		 this.downloadUrl = downloadUrl
		 this.keyModified("download_url") = 1
	}

	def getPerPage() :Option[Int]	={
		return  this.perPage
	}

	def setPerPage( perPage: Option[Int]) 	={
		 this.perPage = perPage
		 this.keyModified("per_page") = 1
	}

	def getMoreRecords() :Option[Boolean]	={
		return  this.moreRecords
	}

	def setMoreRecords( moreRecords: Option[Boolean]) 	={
		 this.moreRecords = moreRecords
		 this.keyModified("more_records") = 1
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