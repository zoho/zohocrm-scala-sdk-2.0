package com.zoho.crm.api.profiles

import com.zoho.crm.api.Header
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import java.time.OffsetDateTime
import com.zoho.crm.api.util.Constants

class ProfilesOperations(var ifModifiedSince: Option[OffsetDateTime]=None)	{

	def getProfiles() :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/profiles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.Profiles.GetProfilesHeader"),  this.ifModifiedSince)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def getProfile( id: Long) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/profiles/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addHeader(new Header[OffsetDateTime]("If-Modified-Since", "com.zoho.crm.api.Profiles.GetProfileHeader"),  this.ifModifiedSince)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}}
 object ProfilesOperations{
class GetProfilesHeader		{
	}


class GetProfileHeader		{
	}
}
