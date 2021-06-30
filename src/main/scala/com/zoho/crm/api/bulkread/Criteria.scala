package com.zoho.crm.api.bulkread

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Criteria extends Model	{
	private var apiName:Option[String] = None
	private var value:Any = None
	private var groupOperator:Choice[String] = _
	private var group:ArrayBuffer[Criteria] = _
	private var comparator:Choice[String] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getValue() :Any	={
		return  this.value
	}

	def setValue( value: Any) 	={
		 this.value = value
		 this.keyModified("value") = 1
	}

	def getGroupOperator() :Choice[String]	={
		return  this.groupOperator
	}

	def setGroupOperator( groupOperator: Choice[String]) 	={
		 this.groupOperator = groupOperator
		 this.keyModified("group_operator") = 1
	}

	def getGroup() :ArrayBuffer[Criteria]	={
		return  this.group
	}

	def setGroup( group: ArrayBuffer[Criteria]) 	={
		 this.group = group
		 this.keyModified("group") = 1
	}

	def getComparator() :Choice[String]	={
		return  this.comparator
	}

	def setComparator( comparator: Choice[String]) 	={
		 this.comparator = comparator
		 this.keyModified("comparator") = 1
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