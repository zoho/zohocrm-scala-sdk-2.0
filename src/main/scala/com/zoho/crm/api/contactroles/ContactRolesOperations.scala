package com.zoho.crm.api.contactroles

import com.zoho.crm.api.Param
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.CommonAPIHandler
import com.zoho.crm.api.util.Utility
import com.zoho.crm.api.util.Constants

class ContactRolesOperations	{
	def getContactRoles() :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def createContactRoles( request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_POST)
		handlerInstance.setCategoryMethod("CREATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def updateContactRoles( request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		handlerInstance.setMandatoryChecker(true)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteContactRoles( paramInstance: Option[ParameterMap]=None) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setParam(paramInstance)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getContactRole( id: Long) :Option[APIResponse[ResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		return handlerInstance.apiCall(classOf[ResponseHandler], "application/json")
	}

	def updateContactRole( id: Long,  request: BodyWrapper) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def deleteContactRole( id: Long) :Option[APIResponse[ActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Contacts/roles/")
		apiPath = apiPath.concat(id.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		return handlerInstance.apiCall(classOf[ActionHandler], "application/json")
	}

	def getAllContactRolesOfDeal( dealId: Long,  paramInstance: Option[ParameterMap]=None) :Option[APIResponse[RecordResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Deals/")
		apiPath = apiPath.concat(dealId.toString())
		apiPath = apiPath.concat("/Contact_Roles")
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setParam(paramInstance)
		handlerInstance.setModuleAPIName("Contacts")
		Utility.getFields("Contacts", handlerInstance)
		return handlerInstance.apiCall(classOf[RecordResponseHandler], "application/json")
	}

	def getContactRoleOfDeal( contactId: Long,  dealId: Long) :Option[APIResponse[RecordResponseHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Deals/")
		apiPath = apiPath.concat(dealId.toString())
		apiPath = apiPath.concat("/Contact_Roles/")
		apiPath = apiPath.concat(contactId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_GET)
		handlerInstance.setCategoryMethod("READ")
		handlerInstance.setModuleAPIName("Contacts")
		Utility.getFields("Contacts", handlerInstance)
		return handlerInstance.apiCall(classOf[RecordResponseHandler], "application/json")
	}

	def addContactRoleToDeal( contactId: Long,  dealId: Long,  request: RecordBodyWrapper) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Deals/")
		apiPath = apiPath.concat(dealId.toString())
		apiPath = apiPath.concat("/Contact_Roles/")
		apiPath = apiPath.concat(contactId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_PUT)
		handlerInstance.setCategoryMethod("UPDATE")
		handlerInstance.setContentType("application/json")
		handlerInstance.setRequest(request)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}

	def removeContactRoleFromDeal( contactId: Long,  dealId: Long) :Option[APIResponse[RecordActionHandler]]	={
		var handlerInstance :CommonAPIHandler = new CommonAPIHandler()
		var apiPath :String = new String()
		apiPath = apiPath.concat("/crm/v2/Deals/")
		apiPath = apiPath.concat(dealId.toString())
		apiPath = apiPath.concat("/Contact_Roles/")
		apiPath = apiPath.concat(contactId.toString())
		handlerInstance.setAPIPath(apiPath)
		handlerInstance.setHttpMethod(Constants.REQUEST_METHOD_DELETE)
		handlerInstance.setCategoryMethod(Constants.REQUEST_METHOD_DELETE)
		return handlerInstance.apiCall(classOf[RecordActionHandler], "application/json")
	}}
 object ContactRolesOperations{
class DeleteContactRolesParam		{
		final val ids:Param[String] = new Param[String]("ids", "com.zoho.crm.api.ContactRoles.DeleteContactRolesParam")
	}


class GetAllContactRolesOfDealParam		{
		final val ids:Param[String] = new Param[String]("ids", "com.zoho.crm.api.ContactRoles.GetAllContactRolesOfDealParam")
	}
}
