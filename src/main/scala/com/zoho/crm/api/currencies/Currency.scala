package com.zoho.crm.api.currencies

import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class Currency extends Model	{
	private var symbol:Option[String] = None
	private var createdTime:Option[OffsetDateTime] = None
	private var isActive:Option[Boolean] = None
	private var exchangeRate:Option[String] = None
	private var format:Option[Format] = None
	private var createdBy:Option[User] = None
	private var prefixSymbol:Option[Boolean] = None
	private var isBase:Option[Boolean] = None
	private var modifiedTime:Option[OffsetDateTime] = None
	private var name:Option[String] = None
	private var modifiedBy:Option[User] = None
	private var id:Option[Long] = None
	private var isoCode:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getSymbol() :Option[String]	={
		return  this.symbol
	}

	def setSymbol( symbol: Option[String]) 	={
		 this.symbol = symbol
		 this.keyModified("symbol") = 1
	}

	def getCreatedTime() :Option[OffsetDateTime]	={
		return  this.createdTime
	}

	def setCreatedTime( createdTime: Option[OffsetDateTime]) 	={
		 this.createdTime = createdTime
		 this.keyModified("created_time") = 1
	}

	def getIsActive() :Option[Boolean]	={
		return  this.isActive
	}

	def setIsActive( isActive: Option[Boolean]) 	={
		 this.isActive = isActive
		 this.keyModified("is_active") = 1
	}

	def getExchangeRate() :Option[String]	={
		return  this.exchangeRate
	}

	def setExchangeRate( exchangeRate: Option[String]) 	={
		 this.exchangeRate = exchangeRate
		 this.keyModified("exchange_rate") = 1
	}

	def getFormat() :Option[Format]	={
		return  this.format
	}

	def setFormat( format: Option[Format]) 	={
		 this.format = format
		 this.keyModified("format") = 1
	}

	def getCreatedBy() :Option[User]	={
		return  this.createdBy
	}

	def setCreatedBy( createdBy: Option[User]) 	={
		 this.createdBy = createdBy
		 this.keyModified("created_by") = 1
	}

	def getPrefixSymbol() :Option[Boolean]	={
		return  this.prefixSymbol
	}

	def setPrefixSymbol( prefixSymbol: Option[Boolean]) 	={
		 this.prefixSymbol = prefixSymbol
		 this.keyModified("prefix_symbol") = 1
	}

	def getIsBase() :Option[Boolean]	={
		return  this.isBase
	}

	def setIsBase( isBase: Option[Boolean]) 	={
		 this.isBase = isBase
		 this.keyModified("is_base") = 1
	}

	def getModifiedTime() :Option[OffsetDateTime]	={
		return  this.modifiedTime
	}

	def setModifiedTime( modifiedTime: Option[OffsetDateTime]) 	={
		 this.modifiedTime = modifiedTime
		 this.keyModified("modified_time") = 1
	}

	def getName() :Option[String]	={
		return  this.name
	}

	def setName( name: Option[String]) 	={
		 this.name = name
		 this.keyModified("name") = 1
	}

	def getModifiedBy() :Option[User]	={
		return  this.modifiedBy
	}

	def setModifiedBy( modifiedBy: Option[User]) 	={
		 this.modifiedBy = modifiedBy
		 this.keyModified("modified_by") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getIsoCode() :Option[String]	={
		return  this.isoCode
	}

	def setIsoCode( isoCode: Option[String]) 	={
		 this.isoCode = isoCode
		 this.keyModified("iso_code") = 1
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