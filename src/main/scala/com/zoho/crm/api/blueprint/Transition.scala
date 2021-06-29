package com.zoho.crm.api.blueprint

import com.zoho.crm.api.fields.Field
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Transition extends Model	{
	private var nextTransitions:ArrayBuffer[NextTransition] = _
	private var percentPartialSave:Option[Double] = None
	private var data:Option[Record] = None
	private var nextFieldValue:Option[String] = None
	private var name:Option[String] = None
	private var criteriaMatched:Option[Boolean] = None
	private var id:Option[Long] = None
	private var fields:ArrayBuffer[Field] = _
	private var criteriaMessage:Option[String] = None
	private var type1:Option[String] = None
	private var executionTime:Option[OffsetDateTime] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getNextTransitions() :ArrayBuffer[NextTransition]	={
		return  this.nextTransitions
	}

	def setNextTransitions( nextTransitions: ArrayBuffer[NextTransition]) 	={
		 this.nextTransitions = nextTransitions
		 this.keyModified("next_transitions") = 1
	}

	def getPercentPartialSave() :Option[Double]	={
		return  this.percentPartialSave
	}

	def setPercentPartialSave( percentPartialSave: Option[Double]) 	={
		 this.percentPartialSave = percentPartialSave
		 this.keyModified("percent_partial_save") = 1
	}

	def getData() :Option[Record]	={
		return  this.data
	}

	def setData( data: Option[Record]) 	={
		 this.data = data
		 this.keyModified("data") = 1
	}

	def getNextFieldValue() :Option[String]	={
		return  this.nextFieldValue
	}

	def setNextFieldValue( nextFieldValue: Option[String]) 	={
		 this.nextFieldValue = nextFieldValue
		 this.keyModified("next_field_value") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getCriteriaMatched() :Option[Boolean]	={
		return  this.criteriaMatched
	}

	def setCriteriaMatched( criteriaMatched: Option[Boolean]) 	={
		 this.criteriaMatched = criteriaMatched
		 this.keyModified("criteria_matched") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getFields() :ArrayBuffer[Field]	={
		return  this.fields
	}

	def setFields( fields: ArrayBuffer[Field]) 	={
		 this.fields = fields
		 this.keyModified("fields") = 1
	}

	def getCriteriaMessage() :Option[String]	={
		return  this.criteriaMessage
	}

	def setCriteriaMessage( criteriaMessage: Option[String]) 	={
		 this.criteriaMessage = criteriaMessage
		 this.keyModified("criteria_message") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
	}

	def getExecutionTime() :Option[OffsetDateTime]	={
		return  this.executionTime
	}

	def setExecutionTime( executionTime: Option[OffsetDateTime]) 	={
		 this.executionTime = executionTime
		 this.keyModified("execution_time") = 1
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