package com.zoho.crm.api.record

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model

class Participants extends Record with Model	{

	def getName() :Option[String]	={
		return  this.getKeyValue("name").asInstanceOf[Option[String]]
	}

	def setName( name: Option[String]) 	={
		 this.addKeyValue("name", name)
	}

	def getEmail() :Option[String]	={
		return  this.getKeyValue("Email").asInstanceOf[Option[String]]
	}

	def setEmail( email: Option[String]) 	={
		 this.addKeyValue("Email", email)
	}

	def getInvited() :Option[Boolean]	={
		return  this.getKeyValue("invited").asInstanceOf[Option[Boolean]]
	}

	def setInvited( invited: Option[Boolean]) 	={
		 this.addKeyValue("invited", invited)
	}

	def getType() :Option[String]	={
		return  this.getKeyValue("type").asInstanceOf[Option[String]]
	}

	def setType( type1: Option[String]) 	={
		 this.addKeyValue("type", type1)
	}

	def getParticipant() :Option[String]	={
		return  this.getKeyValue("participant").asInstanceOf[Option[String]]
	}

	def setParticipant( participant: Option[String]) 	={
		 this.addKeyValue("participant", participant)
	}

	def getStatus() :Option[String]	={
		return  this.getKeyValue("status").asInstanceOf[Option[String]]
	}

	def setStatus( status: Option[String]) 	={
		 this.addKeyValue("status", status)
	}}