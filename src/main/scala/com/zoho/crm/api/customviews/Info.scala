package com.zoho.crm.api.customviews

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Info extends Model	{
	private var perPage:Option[Int] = None
	private var default1:Option[String] = None
	private var count:Option[Int] = None
	private var page:Option[Int] = None
	private var moreRecords:Option[Boolean] = None
	private var translation:Option[Translation] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPerPage() :Option[Int]	={
		return  this.perPage
	}

	def setPerPage( perPage: Option[Int]) 	={
		 this.perPage = perPage
		 this.keyModified("per_page") = 1
	}

	def getDefault() :Option[String]	={
		return  this.default1
	}

	def setDefault( default1: Option[String]) 	={
		 this.default1 = default1
		 this.keyModified("default") = 1
	}

	def getCount() :Option[Int]	={
		return  this.count
	}

	def setCount( count: Option[Int]) 	={
		 this.count = count
		 this.keyModified("count") = 1
	}

	def getPage() :Option[Int]	={
		return  this.page
	}

	def setPage( page: Option[Int]) 	={
		 this.page = page
		 this.keyModified("page") = 1
	}

	def getMoreRecords() :Option[Boolean]	={
		return  this.moreRecords
	}

	def setMoreRecords( moreRecords: Option[Boolean]) 	={
		 this.moreRecords = moreRecords
		 this.keyModified("more_records") = 1
	}

	def getTranslation() :Option[Translation]	={
		return  this.translation
	}

	def setTranslation( translation: Option[Translation]) 	={
		 this.translation = translation
		 this.keyModified("translation") = 1
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