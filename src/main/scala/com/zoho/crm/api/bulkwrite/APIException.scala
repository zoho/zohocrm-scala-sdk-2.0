package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class APIException extends Model with ActionResponse with ResponseWrapper with ResponseHandler	{
	private var code:Choice[String] = _
	private var message:Choice[String] = _
	private var status:Choice[String] = _
	private var details:HashMap[String, Any] = _
	private var errorMessage:Choice[String] = _
	private var errorCode:Option[Int] = None
	private var xError:Choice[String] = _
	private var info:Choice[String] = _
	private var xInfo:Choice[String] = _
	private var httpStatus:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCode() :Choice[String]	={
		return  this.code
	}

	def setCode( code: Choice[String]) 	={
		 this.code = code
		 this.keyModified("code") = 1
	}

	def getMessage() :Choice[String]	={
		return  this.message
	}

	def setMessage( message: Choice[String]) 	={
		 this.message = message
		 this.keyModified("message") = 1
	}

	def getStatus() :Choice[String]	={
		return  this.status
	}

	def setStatus( status: Choice[String]) 	={
		 this.status = status
		 this.keyModified("status") = 1
	}

	def getDetails() :HashMap[String, Any]	={
		return  this.details
	}

	def setDetails( details: HashMap[String, Any]) 	={
		 this.details = details
		 this.keyModified("details") = 1
	}

	def getErrorMessage() :Choice[String]	={
		return  this.errorMessage
	}

	def setErrorMessage( errorMessage: Choice[String]) 	={
		 this.errorMessage = errorMessage
		 this.keyModified("ERROR_MESSAGE") = 1
	}

	def getErrorCode() :Option[Int]	={
		return  this.errorCode
	}

	def setErrorCode( errorCode: Option[Int]) 	={
		 this.errorCode = errorCode
		 this.keyModified("ERROR_CODE") = 1
	}

	def getXError() :Choice[String]	={
		return  this.xError
	}

	def setXError( xError: Choice[String]) 	={
		 this.xError = xError
		 this.keyModified("x-error") = 1
	}

	def getInfo() :Choice[String]	={
		return  this.info
	}

	def setInfo( info: Choice[String]) 	={
		 this.info = info
		 this.keyModified("info") = 1
	}

	def getXInfo() :Choice[String]	={
		return  this.xInfo
	}

	def setXInfo( xInfo: Choice[String]) 	={
		 this.xInfo = xInfo
		 this.keyModified("x-info") = 1
	}

	def getHttpStatus() :Option[String]	={
		return  this.httpStatus
	}

	def setHttpStatus( httpStatus: Option[String]) 	={
		 this.httpStatus = httpStatus
		 this.keyModified("http_status") = 1
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