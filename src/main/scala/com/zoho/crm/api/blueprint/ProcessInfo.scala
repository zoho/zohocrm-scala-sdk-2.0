package com.zoho.crm.api.blueprint

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ProcessInfo extends Model	{
	private var fieldId:Option[String] = None
	private var isContinuous:Option[Boolean] = None
	private var apiName:Option[String] = None
	private var continuous:Option[Boolean] = None
	private var fieldLabel:Option[String] = None
	private var name:Option[String] = None
	private var columnName:Option[String] = None
	private var fieldValue:Option[String] = None
	private var id:Option[Long] = None
	private var fieldName:Option[String] = None
	private var escalation:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getFieldId() :Option[String]	={
		return  this.fieldId
	}

	def setFieldId( fieldId: Option[String]) 	={
		 this.fieldId = fieldId
		 this.keyModified("field_id") = 1
	}

	def getIsContinuous() :Option[Boolean]	={
		return  this.isContinuous
	}

	def setIsContinuous( isContinuous: Option[Boolean]) 	={
		 this.isContinuous = isContinuous
		 this.keyModified("is_continuous") = 1
	}

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getContinuous() :Option[Boolean]	={
		return  this.continuous
	}

	def setContinuous( continuous: Option[Boolean]) 	={
		 this.continuous = continuous
		 this.keyModified("continuous") = 1
	}

	def getFieldLabel() :Option[String]	={
		return  this.fieldLabel
	}

	def setFieldLabel( fieldLabel: Option[String]) 	={
		 this.fieldLabel = fieldLabel
		 this.keyModified("field_label") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getColumnName() :Option[String]	={
		return  this.columnName
	}

	def setColumnName( columnName: Option[String]) 	={
		 this.columnName = columnName
		 this.keyModified("column_name") = 1
	}

	def getFieldValue() :Option[String]	={
		return  this.fieldValue
	}

	def setFieldValue( fieldValue: Option[String]) 	={
		 this.fieldValue = fieldValue
		 this.keyModified("field_value") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getFieldName() :Option[String]	={
		return  this.fieldName
	}

	def setFieldName( fieldName: Option[String]) 	={
		 this.fieldName = fieldName
		 this.keyModified("field_name") = 1
	}

	def getEscalation() :Option[String]	={
		return  this.escalation
	}

	def setEscalation( escalation: Option[String]) 	={
		 this.escalation = escalation
		 this.keyModified("escalation") = 1
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