package com.zoho.crm.api.record

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model

class PricingDetails extends Record with Model	{

	def getToRange() :Option[Double]	={
		return  this.getKeyValue("to_range").asInstanceOf[Option[Double]]
	}

	def setToRange( toRange: Option[Double]) 	={
		 this.addKeyValue("to_range", toRange)
	}

	def getDiscount() :Option[Double]	={
		return  this.getKeyValue("discount").asInstanceOf[Option[Double]]
	}

	def setDiscount( discount: Option[Double]) 	={
		 this.addKeyValue("discount", discount)
	}

	def getFromRange() :Option[Double]	={
		return  this.getKeyValue("from_range").asInstanceOf[Option[Double]]
	}

	def setFromRange( fromRange: Option[Double]) 	={
		 this.addKeyValue("from_range", fromRange)
	}}