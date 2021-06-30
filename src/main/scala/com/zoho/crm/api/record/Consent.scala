package com.zoho.crm.api.record

import com.zoho.crm.api.users.User
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model
import java.time.LocalDate
import java.time.OffsetDateTime

class Consent extends Record with Model	{

	def getOwner() :Option[User]	={
		return  this.getKeyValue("Owner").asInstanceOf[Option[User]]
	}

	def setOwner( owner: Option[User]) 	={
		 this.addKeyValue("Owner", owner)
	}

	def getContactThroughEmail() :Option[Boolean]	={
		return  this.getKeyValue("Contact_Through_Email").asInstanceOf[Option[Boolean]]
	}

	def setContactThroughEmail( contactThroughEmail: Option[Boolean]) 	={
		 this.addKeyValue("Contact_Through_Email", contactThroughEmail)
	}

	def getContactThroughSocial() :Option[Boolean]	={
		return  this.getKeyValue("Contact_Through_Social").asInstanceOf[Option[Boolean]]
	}

	def setContactThroughSocial( contactThroughSocial: Option[Boolean]) 	={
		 this.addKeyValue("Contact_Through_Social", contactThroughSocial)
	}

	def getContactThroughSurvey() :Option[Boolean]	={
		return  this.getKeyValue("Contact_Through_Survey").asInstanceOf[Option[Boolean]]
	}

	def setContactThroughSurvey( contactThroughSurvey: Option[Boolean]) 	={
		 this.addKeyValue("Contact_Through_Survey", contactThroughSurvey)
	}

	def getContactThroughPhone() :Option[Boolean]	={
		return  this.getKeyValue("Contact_Through_Phone").asInstanceOf[Option[Boolean]]
	}

	def setContactThroughPhone( contactThroughPhone: Option[Boolean]) 	={
		 this.addKeyValue("Contact_Through_Phone", contactThroughPhone)
	}

	def getMailSentTime() :Option[OffsetDateTime]	={
		return  this.getKeyValue("Mail_Sent_Time").asInstanceOf[Option[OffsetDateTime]]
	}

	def setMailSentTime( mailSentTime: Option[OffsetDateTime]) 	={
		 this.addKeyValue("Mail_Sent_Time", mailSentTime)
	}

	def getConsentDate() :Option[LocalDate]	={
		return  this.getKeyValue("Consent_Date").asInstanceOf[Option[LocalDate]]
	}

	def setConsentDate( consentDate: Option[LocalDate]) 	={
		 this.addKeyValue("Consent_Date", consentDate)
	}

	def getConsentRemarks() :Option[String]	={
		return  this.getKeyValue("Consent_Remarks").asInstanceOf[Option[String]]
	}

	def setConsentRemarks( consentRemarks: Option[String]) 	={
		 this.addKeyValue("Consent_Remarks", consentRemarks)
	}

	def getConsentThrough() :Option[String]	={
		return  this.getKeyValue("Consent_Through").asInstanceOf[Option[String]]
	}

	def setConsentThrough( consentThrough: Option[String]) 	={
		 this.addKeyValue("Consent_Through", consentThrough)
	}

	def getDataProcessingBasis() :Option[String]	={
		return  this.getKeyValue("Data_Processing_Basis").asInstanceOf[Option[String]]
	}

	def setDataProcessingBasis( dataProcessingBasis: Option[String]) 	={
		 this.addKeyValue("Data_Processing_Basis", dataProcessingBasis)
	}}