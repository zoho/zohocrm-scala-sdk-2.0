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

class RelatedRecordsOperations(var relatedListAPIName: String, var moduleAPIName: String, var xExternal: Option[String]=None)	{

	def getRelatedRecords( recordId: Long,  paramInstance: Option[ParameterMap]=None,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsHeader"),  this.xExternal)
		handlerInstance.setParam(paramInstance)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecords( recordId: Long,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.UpdateRelatedRecordsHeader"),  this.xExternal)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def delinkRecords( recordId: Long,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.DelinkRecordsHeader"),  this.xExternal)
		handlerInstance.setParam(paramInstance)
		Utility.getFields( this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getRelatedRecordsUsingExternalId( externalValue: String,  paramInstance: Option[ParameterMap]=None,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsUsingExternalIDHeader"),  this.xExternal)
		handlerInstance.setParam(paramInstance)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecordsUsingExternalId( externalValue: String,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.UpdateRelatedRecordsUsingExternalIDHeader"),  this.xExternal)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteRelatedRecordsUsingExternalId( externalValue: String,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.DeleteRelatedRecordsUsingExternalIDHeader"),  this.xExternal)
		handlerInstance.setParam(paramInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getRelatedRecord( relatedRecordId: Long,  recordId: Long,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordHeader"),  this.xExternal)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecord( relatedRecordId: Long,  recordId: Long,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.UpdateRelatedRecordHeader"),  this.xExternal)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def delinkRecord( relatedRecordId: Long,  recordId: Long) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(relatedRecordId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.DelinkRecordHeader"),  this.xExternal)
		Utility.getFields( this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getRelatedRecordUsingExternalId( externalFieldValue: String,  externalValue: String,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalFieldValue.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordUsingExternalIDHeader"),  this.xExternal)
		handlerInstance.setHeader(headerInstance)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateRelatedRecordUsingExternalId( externalFieldValue: String,  externalValue: String,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalFieldValue.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.UpdateRelatedRecordUsingExternalIDHeader"),  this.xExternal)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteRelatedRecordUsingExternalId( externalFieldValue: String,  externalValue: String) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat( this.moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalValue.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat( this.relatedListAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(externalFieldValue.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.addHeader(new Header[String]("X-EXTERNAL", "com.zoho.crm.api.RelatedRecords.DeleteRelatedRecordUsingExternalIDHeader"),  this.xExternal)
		Utility.getRelatedLists( this.relatedListAPIName,  this.moduleAPIName, handlerInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}}
 object RelatedRecordsOperations{
class GetRelatedRecordsHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsHeader")
	}


class GetRelatedRecordsParam		{
		final val page:Param[Int] = new Param[Int]("page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsParam")
		final val perPage:Param[Int] = new Param[Int]("per_page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsParam")
	}


class UpdateRelatedRecordsHeader		{
	}


class DelinkRecordsHeader		{
	}


class DelinkRecordsParam		{
		final val ids:Param[String] = new Param[String]("ids", "com.zoho.crm.api.RelatedRecords.DelinkRecordsParam")
	}


class GetRelatedRecordsUsingExternalIDHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsUsingExternalIDHeader")
	}


class GetRelatedRecordsUsingExternalIDParam		{
		final val page:Param[Int] = new Param[Int]("page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsUsingExternalIDParam")
		final val perPage:Param[Int] = new Param[Int]("per_page", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordsUsingExternalIDParam")
	}


class UpdateRelatedRecordsUsingExternalIDHeader		{
	}


class DeleteRelatedRecordsUsingExternalIDHeader		{
	}


class DeleteRelatedRecordsUsingExternalIDParam		{
		final val ids:Param[String] = new Param[String]("ids", "com.zoho.crm.api.RelatedRecords.DeleteRelatedRecordsUsingExternalIDParam")
	}


class GetRelatedRecordHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordHeader")
	}


class UpdateRelatedRecordHeader		{
	}


class DelinkRecordHeader		{
	}


class GetRelatedRecordUsingExternalIDHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.RelatedRecords.GetRelatedRecordUsingExternalIDHeader")
	}


class UpdateRelatedRecordUsingExternalIDHeader		{
	}


class DeleteRelatedRecordUsingExternalIDHeader		{
	}
}
