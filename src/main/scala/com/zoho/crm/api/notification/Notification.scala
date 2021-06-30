package com.zoho.crm.api.notification

import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Notification extends Model	{
	private var channelExpiry:Option[OffsetDateTime] = None
	private var resourceUri:Option[String] = None
	private var resourceId:Option[String] = None
	private var notifyUrl:Option[String] = None
	private var resourceName:Option[String] = None
	private var channelId:Option[Long] = None
	private var events:ArrayBuffer[String] = _
	private var token:Option[String] = None
	private var notifyOnRelatedAction:Option[Boolean] = None
	private var fields:HashMap[String, Any] = _
	private var deleteevents:Option[Boolean] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getChannelExpiry() :Option[OffsetDateTime]	={
		return  this.channelExpiry
	}

	def setChannelExpiry( channelExpiry: Option[OffsetDateTime]) 	={
		 this.channelExpiry = channelExpiry
		 this.keyModified("channel_expiry") = 1
	}

	def getResourceUri() :Option[String]	={
		return  this.resourceUri
	}

	def setResourceUri( resourceUri: Option[String]) 	={
		 this.resourceUri = resourceUri
		 this.keyModified("resource_uri") = 1
	}

	def getResourceId() :Option[String]	={
		return  this.resourceId
	}

	def setResourceId( resourceId: Option[String]) 	={
		 this.resourceId = resourceId
		 this.keyModified("resource_id") = 1
	}

	def getNotifyUrl() :Option[String]	={
		return  this.notifyUrl
	}

	def setNotifyUrl( notifyUrl: Option[String]) 	={
		 this.notifyUrl = notifyUrl
		 this.keyModified("notify_url") = 1
	}

	def getResourceName() :Option[String]	={
		return  this.resourceName
	}

	def setResourceName( resourceName: Option[String]) 	={
		 this.resourceName = resourceName
		 this.keyModified("resource_name") = 1
	}

	def getChannelId() :Option[Long]	={
		return  this.channelId
	}

	def setChannelId( channelId: Option[Long]) 	={
		 this.channelId = channelId
		 this.keyModified("channel_id") = 1
	}

	def getEvents() :ArrayBuffer[String]	={
		return  this.events
	}

	def setEvents( events: ArrayBuffer[String]) 	={
		 this.events = events
		 this.keyModified("events") = 1
	}

	def getToken() :Option[String]	={
		return  this.token
	}

	def setToken( token: Option[String]) 	={
		 this.token = token
		 this.keyModified("token") = 1
	}

	def getNotifyOnRelatedAction() :Option[Boolean]	={
		return  this.notifyOnRelatedAction
	}

	def setNotifyOnRelatedAction( notifyOnRelatedAction: Option[Boolean]) 	={
		 this.notifyOnRelatedAction = notifyOnRelatedAction
		 this.keyModified("notify_on_related_action") = 1
	}

	def getFields() :HashMap[String, Any]	={
		return  this.fields
	}

	def setFields( fields: HashMap[String, Any]) 	={
		 this.fields = fields
		 this.keyModified("fields") = 1
	}

	def getDeleteevents() :Option[Boolean]	={
		return  this.deleteevents
	}

	def setDeleteevents( deleteevents: Option[Boolean]) 	={
		 this.deleteevents = deleteevents
		 this.keyModified("_delete_events") = 1
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