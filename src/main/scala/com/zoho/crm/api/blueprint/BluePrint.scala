package com.zoho.crm.api.blueprint

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class BluePrint extends Model	{
	private var transitionId:Option[Long] = None
	private var data:Option[Record] = None
	private var processInfo:Option[ProcessInfo] = None
	private var transitions:ArrayBuffer[Transition] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getTransitionId() :Option[Long]	={
		return  this.transitionId
	}

	def setTransitionId( transitionId: Option[Long]) 	={
		 this.transitionId = transitionId
		 this.keyModified("transition_id") = 1
	}

	def getData() :Option[Record]	={
		return  this.data
	}

	def setData( data: Option[Record]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getProcessInfo() :Option[ProcessInfo]	={
		return  this.processInfo
	}

	def setProcessInfo( processInfo: Option[ProcessInfo]) 	={
		 this.processInfo = processInfo
		 this.keyModified("process_info") = 1
	}

	def getTransitions() :ArrayBuffer[Transition]	={
		return  this.transitions
	}

	def setTransitions( transitions: ArrayBuffer[Transition]) 	={
		 this.transitions = transitions
		 this.keyModified("transitions") = 1
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