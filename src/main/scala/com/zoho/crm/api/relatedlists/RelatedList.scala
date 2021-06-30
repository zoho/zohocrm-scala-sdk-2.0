package com.zoho.crm.api.relatedlists

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class RelatedList extends Model	{
	private var id:Option[Long] = None
	private var sequenceNumber:Option[String] = None
	private var displayLabel:Option[String] = None
	private var apiName:Option[String] = None
	private var module:Option[String] = None
	private var name:Option[String] = None
	private var action:Option[String] = None
	private var href:Option[String] = None
	private var type1:Option[String] = None
	private var connectedmodule:Option[String] = None
	private var linkingmodule:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getSequenceNumber() :Option[String]	={
		return  this.sequenceNumber
	}

	def setSequenceNumber( sequenceNumber: Option[String]) 	={
		 this.sequenceNumber = sequenceNumber
		 this.keyModified("sequence_number") = 1
	}

	def getDisplayLabel() :Option[String]	={
		return  this.displayLabel
	}

	def setDisplayLabel( displayLabel: Option[String]) 	={
		 this.displayLabel = displayLabel
		 this.keyModified("display_label") = 1
	}

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getModule() :Option[String]	={
		return  this.module
	}

	def setModule( module: Option[String]) 	={
		 this.module = module
		 this.keyModified("module") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getAction() :Option[String]	={
		return  this.action
	}

	def setAction( action: Option[String]) 	={
		 this.action = action
		 this.keyModified("action") = 1
	}

	def getHref() :Option[String]	={
		return  this.href
	}

	def setHref( href: Option[String]) 	={
		 this.href = href
		 this.keyModified("href") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
	}

	def getConnectedmodule() :Option[String]	={
		return  this.connectedmodule
	}

	def setConnectedmodule( connectedmodule: Option[String]) 	={
		 this.connectedmodule = connectedmodule
		 this.keyModified("connectedmodule") = 1
	}

	def getLinkingmodule() :Option[String]	={
		return  this.linkingmodule
	}

	def setLinkingmodule( linkingmodule: Option[String]) 	={
		 this.linkingmodule = linkingmodule
		 this.keyModified("linkingmodule") = 1
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