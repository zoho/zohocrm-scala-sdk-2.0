package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BodyWrapper extends Model	{
	private var data:ArrayBuffer[Record] = _
	private var trigger:ArrayBuffer[String] = _
	private var process:ArrayBuffer[String] = _
	private var duplicateCheckFields:ArrayBuffer[String] = _
	private var wfTrigger:Option[String] = None
	private var larId:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getData() :ArrayBuffer[Record]	={
		return  this.data
	}

	def setData( data: ArrayBuffer[Record]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getTrigger() :ArrayBuffer[String]	={
		return  this.trigger
	}

	def setTrigger( trigger: ArrayBuffer[String]) 	={
		 this.trigger = trigger
		 this.keyModified("trigger") = 1
	}

	def getProcess() :ArrayBuffer[String]	={
		return  this.process
	}

	def setProcess( process: ArrayBuffer[String]) 	={
		 this.process = process
		 this.keyModified("process") = 1
	}

	def getDuplicateCheckFields() :ArrayBuffer[String]	={
		return  this.duplicateCheckFields
	}

	def setDuplicateCheckFields( duplicateCheckFields: ArrayBuffer[String]) 	={
		 this.duplicateCheckFields = duplicateCheckFields
		 this.keyModified("duplicate_check_fields") = 1
	}

	def getWfTrigger() :Option[String]	={
		return  this.wfTrigger
	}

	def setWfTrigger( wfTrigger: Option[String]) 	={
		 this.wfTrigger = wfTrigger
		 this.keyModified("wf_trigger") = 1
	}

	def getLarId() :Option[String]	={
		return  this.larId
	}

	def setLarId( larId: Option[String]) 	={
		 this.larId = larId
		 this.keyModified("lar_id") = 1
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