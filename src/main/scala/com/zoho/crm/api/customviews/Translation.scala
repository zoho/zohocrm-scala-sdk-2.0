package com.zoho.crm.api.customviews

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Translation extends Model	{
	private var publicViews:Option[String] = None
	private var otherUsersViews:Option[String] = None
	private var sharedWithMe:Option[String] = None
	private var createdByMe:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPublicViews() :Option[String]	={
		return  this.publicViews
	}

	def setPublicViews( publicViews: Option[String]) 	={
		 this.publicViews = publicViews
		 this.keyModified("public_views") = 1
	}

	def getOtherUsersViews() :Option[String]	={
		return  this.otherUsersViews
	}

	def setOtherUsersViews( otherUsersViews: Option[String]) 	={
		 this.otherUsersViews = otherUsersViews
		 this.keyModified("other_users_views") = 1
	}

	def getSharedWithMe() :Option[String]	={
		return  this.sharedWithMe
	}

	def setSharedWithMe( sharedWithMe: Option[String]) 	={
		 this.sharedWithMe = sharedWithMe
		 this.keyModified("shared_with_me") = 1
	}

	def getCreatedByMe() :Option[String]	={
		return  this.createdByMe
	}

	def setCreatedByMe( createdByMe: Option[String]) 	={
		 this.createdByMe = createdByMe
		 this.keyModified("created_by_me") = 1
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