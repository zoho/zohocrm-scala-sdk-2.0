package com.zoho.crm.api.relatedrecords

import com.zoho.crm.api.Header
import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.Param
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import com.zoho.crm.api.util.Utility
import java.time.OffsetDateTime
import com.zoho.crm.api.util.Constants

class RelatedRecordsOperations(var relatedListAPIName: String, var recordId: Long, var moduleAPIName: String)	{

	def getRelatedRecords( paramInstance: Option[ParameterMap]=None,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecords( request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def delinkRecords( paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getRelatedRecord( relatedRecordId: Long,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecord( relatedRecordId: Long,  request: BodyWrapper,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def delinkRecord( relatedRecordId: Long,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setHeader(headerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}}
 object RelatedRecordsOperations{
class GetRelatedRecordsParam		{
		final val page:Param[Int] = new Param[Int]("page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsParam")
		final val perPage:Param[Int] = new Param[Int]("per_page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsParam")
	}


class GetRelatedRecordsHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsHeader")
		final val XEXTERNAL:Header[String] = new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsHeader")
	}


class DelinkRecordsParam		{
		final val ids:Param[Long] = new Param[Long]("ids", "com.zoho.crm.api.RelatedRecords.DelinkRecordsParam")
	}


class GetRelatedRecordHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordHeader")
	}


class UpdateRelatedRecordHeader		{
		final val XEXTERNAL:Header[String] = new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.UpdateRelatedRecordHeader")
	}


class DelinkRecordHeader		{
		final val XEXTERNAL:Header[String] = new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.DelinkRecordHeader")
	}
}
