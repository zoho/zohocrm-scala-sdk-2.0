package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class PickListValue extends Model	{
	private var displayValue:Option[String] = None
	private var sequenceNumber:Option[Int] = None
	private var expectedDataType:Option[String] = None
	private var maps:ArrayBuffer[Any] = _
	private var actualValue:Option[String] = None
	private var sysRefName:Option[String] = None
	private var type1:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDisplayValue() :Option[String]	={
		return  this.displayValue
	}

	def setDisplayValue( displayValue: Option[String]) 	={
		 this.displayValue = displayValue
		 this.keyModified("display_value") = 1
	}

	def getSequenceNumber() :Option[Int]	={
		return  this.sequenceNumber
	}

	def setSequenceNumber( sequenceNumber: Option[Int]) 	={
		 this.sequenceNumber = sequenceNumber
		 this.keyModified("sequence_number") = 1
	}

	def getExpectedDataType() :Option[String]	={
		return  this.expectedDataType
	}

	def setExpectedDataType( expectedDataType: Option[String]) 	={
		 this.expectedDataType = expectedDataType
		 this.keyModified("expected_data_type") = 1
	}

	def getMaps() :ArrayBuffer[Any]	={
		return  this.maps
	}

	def setMaps( maps: ArrayBuffer[Any]) 	={
		 this.maps = maps
		 this.keyModified("maps") = 1
	}

	def getActualValue() :Option[String]	={
		return  this.actualValue
	}

	def setActualValue( actualValue: Option[String]) 	={
		 this.actualValue = actualValue
		 this.keyModified("actual_value") = 1
	}

	def getSysRefName() :Option[String]	={
		return  this.sysRefName
	}

	def setSysRefName( sysRefName: Option[String]) 	={
		 this.sysRefName = sysRefName
		 this.keyModified("sys_ref_name") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
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