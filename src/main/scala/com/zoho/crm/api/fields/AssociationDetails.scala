package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class AssociationDetails extends Model	{
	private var lookupField:Option[LookupField] = None
	private var relatedField:Option[LookupField] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getLookupField() :Option[LookupField]	={
		return  this.lookupField
	}

	def setLookupField( lookupField: Option[LookupField]) 	={
		 this.lookupField = lookupField
		 this.keyModified("lookup_field") = 1
	}

	def getRelatedField() :Option[LookupField]	={
		return  this.relatedField
	}

	def setRelatedField( relatedField: Option[LookupField]) 	={
		 this.relatedField = relatedField
		 this.keyModified("related_field") = 1
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