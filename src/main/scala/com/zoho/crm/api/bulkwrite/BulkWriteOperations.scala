package com.zoho.crm.api.bulkwrite

import com.zoho.crm.api.Header
import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import com.zoho.crm.api.util.Constants

class BulkWriteOperations	{
	def uploadFile( request: FileBodyWrapper,  headerInstance: Option[HeaderMap]=None) :Option[APIResponse[ActionResponse]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("https://content.zohoapis.com/crm/v2/upload")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("multipart/form-data")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		handlerInstance.setHeader(headerInstance)
		return handlerInstance.apiCall(classOf[ActionResponse], "application/json")
	}

	def createBulkWriteJob( request: RequestWrapper) :Option[APIResponse[ActionResponse]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/bulk/v2/write")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionResponse], "application/json")
	}

	def getBulkWriteJobDetails( jobId: Long) :Option[APIResponse[ResponseWrapper]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/bulk/v2/write/")
		apiPath = apiPath.concat(jobId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		return handlerInstance.apiCall(classOf[ResponseWrapper], "application/json")
	}

	def downloadBulkWriteResult( downloadUrl: String) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/")
		apiPath = apiPath.concat(downloadUrl.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/octet-stream")
	}}
 object BulkWriteOperations{
class UploadFileHeader		{
		final val feature:Header[String] = new Header[String]("feature", "com.zoho.crm.api.BulkWrite.UploadFileHeader")
		final val XCRMORG:Header[String] = new Header[String]("X-CRM-ORG", "com.zoho.crm.api.BulkWrite.UploadFileHeader")
	}
}
