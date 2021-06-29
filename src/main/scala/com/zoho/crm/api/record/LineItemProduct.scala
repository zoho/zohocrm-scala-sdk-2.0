package com.zoho.crm.api.record

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model

class LineItemProduct extends Record with Model	{

	def getProductCode() :Option[String]	={
		return  this.getKeyValue("Product_Code").asInstanceOf[Option[String]]
	}

	def setProductCode( productCode: Option[String]) 	={
		 this.addKeyValue("Product_Code", productCode)
	}

	def getCurrency() :Option[String]	={
		return  this.getKeyValue("Currency").asInstanceOf[Option[String]]
	}

	def setCurrency( currency: Option[String]) 	={
		 this.addKeyValue("Currency", currency)
	}

	def getName() :Option[String]	={
		return  this.getKeyValue("name").asInstanceOf[Option[String]]
	}

	def setName( name: Option[String]) 	={
		 this.addKeyValue("name", name)
	}}