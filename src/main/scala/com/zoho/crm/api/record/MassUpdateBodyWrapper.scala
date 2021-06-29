package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class MassUpdateBodyWrapper extends Model	{
	private var data:ArrayBuffer[Record] = _
	private var cvid:Option[String] = None
	private var ids:ArrayBuffer[Long] = _
	private var territory:Option[Territory] = None
	private var overWrite:Option[Boolean] = None
	private var criteria:ArrayBuffer[Criteria] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[Record]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[Record]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getCvid() :Option[String]	={
		return  this.cvid
	}

	def setCvid( cvid: Option[String]) 	={
		 this.cvid = cvid
		 this.keyModified("cvid") = 1
	}

	def getIds() :ArrayBuffer[Long]	={
		return  this.ids
	}

	def setIds( ids: ArrayBuffer[Long]) 	={
		 this.ids = ids
		 this.keyModified("ids") = 1
	}

	def getTerritory() :Option[Territory]	={
		return  this.territory
	}

	def setTerritory( territory: Option[Territory]) 	={
		 this.territory = territory
		 this.keyModified("territory") = 1
	}

	def getOverWrite() :Option[Boolean]	={
		return  this.overWrite
	}

	def setOverWrite( overWrite: Option[Boolean]) 	={
		 this.overWrite = overWrite
		 this.keyModified("over_write") = 1
	}

	def getCriteria() :ArrayBuffer[Criteria]	={
		return  this.criteria
	}

	def setCriteria( criteria: ArrayBuffer[Criteria]) 	={
		 this.criteria = criteria
		 this.keyModified("criteria") = 1
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