package com.zoho.crm.api.relatedlists

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ResponseWrapper extends Model with ResponseHandler	{
	private var relatedLists:ArrayBuffer[RelatedList] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getRelatedLists() :ArrayBuffer[RelatedList]	={
		return  this.relatedLists
	}

	def setRelatedLists( relatedLists: ArrayBuffer[RelatedList]) 	={
		 this.relatedLists = relatedLists
		 this.keyModified("related_lists") = 1
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