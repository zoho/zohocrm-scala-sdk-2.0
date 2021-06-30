package com.zoho.crm.api.customviews

import com.zoho.crm.api.Param
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import com.zoho.crm.api.util.Constants

class CustomViewsOperations(var module: Option[String]=None)	{

	def getCustomViews( paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/custom_views")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addParam(new Param[String]("module", "com.zoho.crm.api.CustomViews.GetCustomViewsParam"),  this.module)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def getCustomView( id: Long) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/settings/custom_views/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.addParam(new Param[String]("module", "com.zoho.crm.api.CustomViews.GetCustomViewParam"),  this.module)
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}}
 object CustomViewsOperations{
class GetCustomViewsParam		{
		final val page:Param[Int] = new Param[Int]("page", "com.zoho.crm.api.CustomViews.GetCustomViewsParam")
		final val perPage:Param[Int] = new Param[Int]("per_page", "com.zoho.crm.api.CustomViews.GetCustomViewsParam")
	}


class GetCustomViewParam		{
	}
}
