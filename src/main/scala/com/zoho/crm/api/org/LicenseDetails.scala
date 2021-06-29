package com.zoho.crm.api.org

import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class LicenseDetails extends Model	{
	private var paidExpiry:Option[OffsetDateTime] = None
	private var usersLicensePurchased:Option[Long] = None
	private var trialType:Option[String] = None
	private var trialExpiry:Option[String] = None
	private var paid:Option[Boolean] = None
	private var paidType:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getPaidExpiry() :Option[OffsetDateTime]	={
		return  this.paidExpiry
	}

	def setPaidExpiry( paidExpiry: Option[OffsetDateTime]) 	={
		 this.paidExpiry = paidExpiry
		 this.keyModified("paid_expiry") = 1
	}

	def getUsersLicensePurchased() :Option[Long]	={
		return  this.usersLicensePurchased
	}

	def setUsersLicensePurchased( usersLicensePurchased: Option[Long]) 	={
		 this.usersLicensePurchased = usersLicensePurchased
		 this.keyModified("users_license_purchased") = 1
	}

	def getTrialType() :Option[String]	={
		return  this.trialType
	}

	def setTrialType( trialType: Option[String]) 	={
		 this.trialType = trialType
		 this.keyModified("trial_type") = 1
	}

	def getTrialExpiry() :Option[String]	={
		return  this.trialExpiry
	}

	def setTrialExpiry( trialExpiry: Option[String]) 	={
		 this.trialExpiry = trialExpiry
		 this.keyModified("trial_expiry") = 1
	}

	def getPaid() :Option[Boolean]	={
		return  this.paid
	}

	def setPaid( paid: Option[Boolean]) 	={
		 this.paid = paid
		 this.keyModified("paid") = 1
	}

	def getPaidType() :Option[String]	={
		return  this.paidType
	}

	def setPaidType( paidType: Option[String]) 	={
		 this.paidType = paidType
		 this.keyModified("paid_type") = 1
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