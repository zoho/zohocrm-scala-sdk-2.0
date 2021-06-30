package com.zoho.crm.api.fields

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class ViewType extends Model	{
	private var view:Option[Boolean] = None
	private var edit:Option[Boolean] = None
	private var create:Option[Boolean] = None
	private var quickCreate:Option[Boolean] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getView() :Option[Boolean]	={
		return  this.view
	}

	def setView( view: Option[Boolean]) 	={
		 this.view = view
		 this.keyModified("view") = 1
	}

	def getEdit() :Option[Boolean]	={
		return  this.edit
	}

	def setEdit( edit: Option[Boolean]) 	={
		 this.edit = edit
		 this.keyModified("edit") = 1
	}

	def getCreate() :Option[Boolean]	={
		return  this.create
	}

	def setCreate( create: Option[Boolean]) 	={
		 this.create = create
		 this.keyModified("create") = 1
	}

	def getQuickCreate() :Option[Boolean]	={
		return  this.quickCreate
	}

	def setQuickCreate( quickCreate: Option[Boolean]) 	={
		 this.quickCreate = quickCreate
		 this.keyModified("quick_create") = 1
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