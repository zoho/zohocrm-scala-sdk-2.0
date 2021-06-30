package com.zoho.crm.api.tags

import com.zoho.crm.api.Param
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import com.zoho.crm.api.util.Constants

class TagsOperations	{
	def getTags( paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def createTags( request: BodyWrapper,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def updateTags( request: BodyWrapper,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def updateTag( id: Long,  request: BodyWrapper,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteTag( id: Long) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def mergeTags( id: Long,  request: MergeWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags/")
		apiPath = apiPath.concat(id.toString())
		apiPath = apiPath.concat("/actions/merge")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def addTagsToRecord( recordId: Long,  moduleAPIName: String,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat(moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/actions/add_tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}

	def removeTagsFromRecord( recordId: Long,  moduleAPIName: String,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat(moduleAPIName.toString())
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(recordId.toString())
		apiPath = apiPath.concat("/actions/remove_tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}

	def addTagsToMultipleRecords( moduleAPIName: String,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat(moduleAPIName.toString())
		apiPath = apiPath.concat("/actions/add_tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}

	def removeTagsFromMultipleRecords( moduleAPIName: String,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/")
		apiPath = apiPath.concat(moduleAPIName.toString())
		apiPath = apiPath.concat("/actions/remove_tags")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}

	def getRecordCountForTag( id: Long,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[CountHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/tags/")
		apiPath = apiPath.concat(id.toString())
		apiPath = apiPath.concat("/actions/records_count")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[CountHandler], "application/json")
	}}
 object TagsOperations{
class GetTagsParam		{
		final val module:Param[String] = new Param[String]("module", "com.zoho.crm.api.Tags.GetTagsParam")
		final val myTags:Param[String] = new Param[String]("my_tags", "com.zoho.crm.api.Tags.GetTagsParam")
	}


class CreateTagsParam		{
		final val module:Param[String] = new Param[String]("module", "com.zoho.crm.api.Tags.CreateTagsParam")
	}


class UpdateTagsParam		{
		final val module:Param[String] = new Param[String]("module", "com.zoho.crm.api.Tags.UpdateTagsParam")
	}


class UpdateTagParam		{
		final val module:Param[String] = new Param[String]("module", "com.zoho.crm.api.Tags.UpdateTagParam")
	}


class AddTagsToRecordParam		{
		final val tagNames:Param[String] = new Param[String]("tag_names", "com.zoho.crm.api.Tags.AddTagsToRecordParam")
		final val overWrite:Param[String] = new Param[String]("over_write", "com.zoho.crm.api.Tags.AddTagsToRecordParam")
	}


class RemoveTagsFromRecordParam		{
		final val tagNames:Param[String] = new Param[String]("tag_names", "com.zoho.crm.api.Tags.RemoveTagsFromRecordParam")
	}


class AddTagsToMultipleRecordsParam		{
		final val tagNames:Param[String] = new Param[String]("tag_names", "com.zoho.crm.api.Tags.AddTagsToMultipleRecordsParam")
		final val ids:Param[Long] = new Param[Long]("ids", "com.zoho.crm.api.Tags.AddTagsToMultipleRecordsParam")
		final val overWrite:Param[String] = new Param[String]("over_write", "com.zoho.crm.api.Tags.AddTagsToMultipleRecordsParam")
	}


class RemoveTagsFromMultipleRecordsParam		{
		final val tagNames:Param[String] = new Param[String]("tag_names", "com.zoho.crm.api.Tags.RemoveTagsFromMultipleRecordsParam")
		final val ids:Param[Long] = new Param[Long]("ids", "com.zoho.crm.api.Tags.RemoveTagsFromMultipleRecordsParam")
	}


class GetRecordCountForTagParam		{
		final val module:Param[String] = new Param[String]("module", "com.zoho.crm.api.Tags.GetRecordCountForTagParam")
	}
}
