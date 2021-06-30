package com.zoho.crm.api.variables

import com.zoho.crm.api.variablegroups.VariableGroup
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Variable extends Model	{
	private var apiName:Option[String] = None
	private var name:Option[String] = None
	private var description:Option[String] = None
	private var id:Option[Long] = None
	private var type1:Option[String] = None
	private var variableGroup:Option[VariableGroup] = None
	private var value:Any = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getDescription() :Option[String]	={
		return  this.description
	}

	def setDescription( description: Option[String]) 	={
		 this.description = description
		 this.keyModified("description") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
	}

	def getVariableGroup() :Option[VariableGroup]	={
		return  this.variableGroup
	}

	def setVariableGroup( variableGroup: Option[VariableGroup]) 	={
		 this.variableGroup = variableGroup
		 this.keyModified("variable_group") = 1
	}

	def getValue() :Any	={
		return  this.value
	}

	def setValue( value: Any) 	={
		 this.value = value
		 this.keyModified("value") = 1
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