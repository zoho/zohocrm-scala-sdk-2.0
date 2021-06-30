package com.zoho.crm.api.notes

import com.zoho.crm.api.Header
import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.Param
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import java.time.OffsetDateTime
import com.zoho.crm.api.util.Constants

class NotesOperations	{
	def getNotes( paramInstance: Option[ParameterMap]=None,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		handlerInstance.setHeader(headerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def createNotes( request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def updateNotes( request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteNotes( paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getNote( id: Long,  paramInstance: Option[ParameterMap]=None,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		handlerInstance.setHeader(headerInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateNote( id: Long,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteNote( id: Long) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Notes/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}}
 object NotesOperations{
class GetNotesParam		{
		final val page:Param[Int] = new Param[Int]("page", "com.zoho.crm.api.Notes.GetNotesParam")
		final val perPage:Param[Int] = new Param[Int]("per_page", "com.zoho.crm.api.Notes.GetNotesParam")
		final val fields:Param[String] = new Param[String]("fields", "com.zoho.crm.api.Notes.GetNotesParam")
	}


class GetNotesHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.Notes.GetNotesHeader")
	}


class DeleteNotesParam		{
		final val ids:Param[Long] = new Param[Long]("ids", "com.zoho.crm.api.Notes.DeleteNotesParam")
	}


class GetNoteParam		{
		final val fields:Param[String] = new Param[String]("fields", "com.zoho.crm.api.Notes.GetNoteParam")
	}


class GetNoteHeader		{
		final val IfModifiedSince:Header[OffsetDateTime] = new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.Notes.GetNoteHeader")
	}
}
