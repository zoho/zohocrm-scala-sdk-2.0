package com.zoho.crm.api.taxes

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Preference extends Model	{
	private var autoPopulateTax:Option[Boolean] = None
	private var modifyTaxRates:Option[Boolean] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getAutoPopulateTax() :Option[Boolean]	={
		return  this.autoPopulateTax
	}

	def setAutoPopulateTax( autoPopulateTax: Option[Boolean]) 	={
		 this.autoPopulateTax = autoPopulateTax
		 this.keyModified("auto_populate_tax") = 1
	}

	def getModifyTaxRates() :Option[Boolean]	={
		return  this.modifyTaxRates
	}

	def setModifyTaxRates( modifyTaxRates: Option[Boolean]) 	={
		 this.modifyTaxRates = modifyTaxRates
		 this.keyModified("modify_tax_rates") = 1
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