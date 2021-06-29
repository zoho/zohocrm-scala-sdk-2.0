package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class MultiSelectLookup extends Model	{
	private var displayLabel:Option[String] = None
	private var linkingModule:Option[String] = None
	private var lookupApiname:Option[String] = None
	private var apiName:Option[String] = None
	private var connectedlookupApiname:Option[String] = None
	private var id:Option[Long] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDisplayLabel() :Option[String]	={
		return  this.displayLabel
	}

	def setDisplayLabel( displayLabel: Option[String]) 	={
		 this.displayLabel = displayLabel
		 this.keyModified("display_label") = 1
	}

	def getLinkingModule() :Option[String]	={
		return  this.linkingModule
	}

	def setLinkingModule( linkingModule: Option[String]) 	={
		 this.linkingModule = linkingModule
		 this.keyModified("linking_module") = 1
	}

	def getLookupApiname() :Option[String]	={
		return  this.lookupApiname
	}

	def setLookupApiname( lookupApiname: Option[String]) 	={
		 this.lookupApiname = lookupApiname
		 this.keyModified("lookup_apiname") = 1
	}

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getConnectedlookupApiname() :Option[String]	={
		return  this.connectedlookupApiname
	}

	def setConnectedlookupApiname( connectedlookupApiname: Option[String]) 	={
		 this.connectedlookupApiname = connectedlookupApiname
		 this.keyModified("connectedlookup_apiname") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
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