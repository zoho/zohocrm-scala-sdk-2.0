package com.zoho.crm.api.layouts

import com.zoho.crm.api.fields.Field
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Section extends Model	{
	private var displayLabel:Option[String] = None
	private var sequenceNumber:Option[Int] = None
	private var issubformsection:Option[Boolean] = None
	private var tabTraversal:Option[Int] = None
	private var apiName:Option[String] = None
	private var columnCount:Option[Int] = None
	private var name:Option[String] = None
	private var generatedType:Option[String] = None
	private var fields:ArrayBuffer[Field] = _
	private var properties:Option[Properties] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getDisplayLabel() :Option[String]	={
		return  this.displayLabel
	}

	def setDisplayLabel( displayLabel: Option[String]) 	={
		 this.displayLabel = displayLabel
		 this.keyModified("display_label") = 1
	}

	def getSequenceNumber() :Option[Int]	={
		return  this.sequenceNumber
	}

	def setSequenceNumber( sequenceNumber: Option[Int]) 	={
		 this.sequenceNumber = sequenceNumber
		 this.keyModified("sequence_number") = 1
	}

	def getIssubformsection() :Option[Boolean]	={
		return  this.issubformsection
	}

	def setIssubformsection( issubformsection: Option[Boolean]) 	={
		 this.issubformsection = issubformsection
		 this.keyModified("isSubformSection") = 1
	}

	def getTabTraversal() :Option[Int]	={
		return  this.tabTraversal
	}

	def setTabTraversal( tabTraversal: Option[Int]) 	={
		 this.tabTraversal = tabTraversal
		 this.keyModified("tab_traversal") = 1
	}

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getColumnCount() :Option[Int]	={
		return  this.columnCount
	}

	def setColumnCount( columnCount: Option[Int]) 	={
		 this.columnCount = columnCount
		 this.keyModified("column_count") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getGeneratedType() :Option[String]	={
		return  this.generatedType
	}

	def setGeneratedType( generatedType: Option[String]) 	={
		 this.generatedType = generatedType
		 this.keyModified("generated_type") = 1
	}

	def getFields() :ArrayBuffer[Field]	={
		return  this.fields
	}

	def setFields( fields: ArrayBuffer[Field]) 	={
		 this.fields = fields
		 this.keyModified("fields") = 1
	}

	def getProperties() :Option[Properties]	={
		return  this.properties
	}

	def setProperties( properties: Option[Properties]) 	={
		 this.properties = properties
		 this.keyModified("properties") = 1
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