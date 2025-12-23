package com.zoho.crm.sample.modules

import java.lang.reflect.Field
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util

import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.customviews.Criteria
import com.zoho.crm.api.customviews.CustomView
import com.zoho.crm.api.customviews.SharedDetails
import com.zoho.crm.api.modules.APIException
import com.zoho.crm.api.modules.ActionHandler
import com.zoho.crm.api.modules.ActionResponse
import com.zoho.crm.api.modules.ActionWrapper
import com.zoho.crm.api.modules.Argument
import com.zoho.crm.api.modules.BodyWrapper
import com.zoho.crm.api.modules.Module
import com.zoho.crm.api.modules.ModulesOperations
import com.zoho.crm.api.modules.ModulesOperations.GetModulesHeader
import com.zoho.crm.api.modules.RelatedListProperties
import com.zoho.crm.api.modules.ResponseHandler
import com.zoho.crm.api.modules.ResponseWrapper
import com.zoho.crm.api.modules.SuccessResponse
import com.zoho.crm.api.profiles.Profile
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Modules {
  /**
   * <h3> Get Modules </h3>
   * This method is used to get metadata about all the modules and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getModules(): Unit = { //Get instance of ModulesOperations Class
    val moduleOperations = new ModulesOperations
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2020, 5, 20, 10, 0, 0, 1, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetModulesHeader().IfModifiedSince, ifmodifiedsince)
    //Call getModules method that takes headerInstance as parameters
    val responseOption = moduleOperations.getModules(Option(headerInstance))
    if (responseOption.isDefined) { //check response
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the list of obtained Module instances
          val modules = responseWrapper.getModules

          for (module <- modules) { //Get the Name of each Module
            println("Module Name: " + module.getName)
            //Get the GlobalSearchSupported of each Module
            println("Module GlobalSearchSupported: " + module.getGlobalSearchSupported.toString)
            //Get the Deletable of each Module
            println("Module Deletable: " + module.getDeletable.toString)
            //Get the Description of each Module
            println("Module Description: " + module.getDescription)
            //Get the Creatable of each Module
            println("Module Creatable: " + module.getCreatable.toString)
            //Get the InventoryTemplateSupported of each Module
            println("Module InventoryTemplateSupported: " + module.getInventoryTemplateSupported.toString)
            if (module.getModifiedTime != null) { //Get the ModifiedTime of each Module
              println("Module ModifiedTime: " + module.getModifiedTime)
            }
            //Get the PluralLabel of each Module
            println("Module PluralLabel: " + module.getPluralLabel)
            //Get the PresenceSubMenu of each Module
            println("Module PresenceSubMenu: " + module.getPresenceSubMenu.toString)
            //Get the TriggersSupported of each Module
            println("Module TriggersSupported: " + module.getTriggersSupported.toString)
            //Get the Id of each Module
            println("Module Id: " + module.getId.toString)
            //Get the Visibility of each Module
            println("Module Visibility: " + module.getVisibility.toString)
            //Get the Convertable of each Module
            println("Module Convertable: " + module.getConvertable.toString)
            //Get the Editable of each Module
            println("Module Editable: " + module.getEditable.toString)
            //Get the EmailtemplateSupport of each Module
            println("Module EmailtemplateSupport: " + module.getEmailtemplateSupport.toString)
            //Get the list of Profile instance each Module
            val profiles = module.getProfiles
            //Check if profiles is not null
            if (profiles != null) {

              for (profile <- profiles) { //Get the Name of each Profile
                println("Module Profile Name: " + profile.getName)
                //Get the Id of each Profile
                println("Module Profile Id: " + profile.getId.toString)
              }
            }
            //Get the FilterSupported of each Module
            println("Module FilterSupported: " + module.getFilterSupported.toString)
            //Get the ShowAsTab of each Module
            println("Module ShowAsTab: " + module.getShowAsTab.toString)
            //Get the WebLink of each Module
            println("Module WebLink: " + module.getWebLink)
            //Get the SequenceNumber of each Module
            println("Module SequenceNumber: " + module.getSequenceNumber.toString)
            //Get the SingularLabel of each Module
            println("Module SingularLabel: " + module.getSingularLabel)
            //Get the Viewable of each Module
            println("Module Viewable: " + module.getViewable.toString)
            //Get the APISupported of each Module
            println("Module APISupported: " + module.getAPISupported.toString)
            //Get the APIName of each Module
            println("Module APIName: " + module.getAPIName)
            //Get the QuickCreate of each Module
            println("Module QuickCreate: " + module.getQuickCreate.toString)
            //Get the modifiedBy User instance of each Module
            val modifiedByOption = module.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Module Modified By User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Module Modified By User-ID: " + modifiedBy.getId)
            }
            //Get the GeneratedType of each Module
            println("Module GeneratedType: " + module.getGeneratedType.getValue)
            //Get the FeedsRequired of each Module
            println("Module FeedsRequired: " + module.getFeedsRequired.toString)
            //Get the ScoringSupported of each Module
            println("Module ScoringSupported: " + module.getScoringSupported.toString)
            //Get the WebformSupported of each Module
            println("Module WebformSupported: " + module.getWebformSupported.toString)
            //Get the list of Argument instance each Module
            val arguments = module.getArguments
            //Check if arguments is not null
            if (arguments != null) {

              for (argument <- arguments) { //Get the Name of each Argument
                println("Module Argument Name: " + argument.getName)
                //Get the Value of each Argument
                println("Module Argument Value: " + argument.getValue)
              }
            }
            //Get the ModuleName of each Module
            println("Module ModuleName: " + module.getModuleName)
            //Get the BusinessCardFieldLimit of each Module
            println("Module BusinessCardFieldLimit: " + module.getBusinessCardFieldLimit.toString)
            //Get the parentModule Module instance of each Module
            val parentModuleOption = module.getParentModule
            if (parentModuleOption.isDefined ) { //Get the Name of Parent Module
              val parentModule = parentModuleOption.get
              println("Module Parent Module Name: " + parentModule.getAPIName)
              //Get the Value of Parent Module
              println("Module Parent Module Id: " + parentModule.getId.toString)
            }
          }
        }
        else { //Check if the request returned an exception
          if (responseHandler.isInstanceOf[APIException]) { //Get the received APIException instance
            val exception = responseHandler.asInstanceOf[APIException]
            //Get the Status
            println("Status: " + exception.getStatus.getValue)
            //Get the Code
            println("Code: " + exception.getCode.getValue)
            println("Details: ")
            //Get the details map

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            //Get the Message
            println("Message: " + exception.getMessage.getValue)
          }
        }
      }
      else { //If response is not as expected
        //Get model object from response
        val responseObject = response.getModel
        //Get the response object's class
        val clas = responseObject.getClass
        //Get all declared fields of the response class
        val fields = clas.getDeclaredFields
        for (field <- fields) { //Get each value
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Get Module </h3>
   * This method is used to get metadata about single module with it's API Name and print the response.
   *
   * @param moduleAPIName The API Name of the module to obtain metadata
   * @throws Exception
   */
  @throws[Exception]
  def getModule(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    val moduleOperations = new ModulesOperations
    //Call getModule method that takes moduleAPIName as parameter
    val responseOption = moduleOperations.getModule(moduleAPIName)
    if (responseOption.isDefined) { //check response
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) {
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          val modules = responseWrapper.getModules

          for (module <- modules) {
            println("Module Name: " + module.getName)
            println("Module GlobalSearchSupported: " + module.getGlobalSearchSupported.toString)
            if (module.getKanbanView != null) { //Get the KanbanView of each Module
              println("Module KanbanView: " + module.getKanbanView.toString)
            }
            println("Module Deletable: " + module.getDeletable.toString)
            println("Module Description: " + module.getDescription)
            println("Module Creatable: " + module.getCreatable.toString)
            if (module.getFilterStatus != null) { //Get the FilterStatus of each Module
              println("Module FilterStatus: " + module.getFilterStatus.toString)
            }
            println("Module InventoryTemplateSupported: " + module.getInventoryTemplateSupported.toString)
            if (module.getModifiedTime != null) println("Module ModifiedTime: " + module.getModifiedTime)
            println("Module PluralLabel: " + module.getPluralLabel)
            println("Module PresenceSubMenu: " + module.getPresenceSubMenu.toString)
            println("Module TriggersSupported: " + module.getTriggersSupported.toString)
            println("Module Id: " + module.getId.toString)
            //Get the RelatedListProperties instance of each Module
            val relatedListPropertiesOption = module.getRelatedListProperties
            //Check if relatedListProperties is not null
            if (relatedListPropertiesOption.isDefined) { //Get the SortBy of each RelatedListProperties
              val relatedListProperties=relatedListPropertiesOption.get
              println("Module RelatedListProperties SortBy: " + relatedListProperties.getSortBy)
              //Get List of fields APIName
              val fields = relatedListProperties.getFields
              //Check if fields is not null
              if (fields != null) {

                for (fieldName <- fields) { //Get the Field Name
                  println("Module RelatedListProperties Fields: " + fieldName)
                }
              }
              //Get the Name of RelatedListProperties
              println("Module RelatedListProperties SortOrder: " + relatedListProperties.getSortOrder)
            }
            //Get List of properties field APIName
            val properties = module.getproperties()
            //Check if properties is not null
            if (properties != null) {

              for (fieldName <- properties) {
                println("Module Properties Fields: " + fieldName)
              }
            }
            //Get the PerPage of each Module
            println("Module PerPage: " + module.getPerPage.toString)
            println("Module Visibility: " + module.getVisibility.toString)
            println("Module Convertable: " + module.getConvertable.toString)
            println("Module Editable: " + module.getEditable.toString)
            println("Module EmailtemplateSupport: " + module.getEmailtemplateSupport.toString)
            val profiles = module.getProfiles
            if (profiles != null) {

              for (profile <- profiles) {
                println("Module Profile Name: " + profile.getName)
                println("Module Profile Id: " + profile.getId.toString)
              }
            }
            println("Module FilterSupported: " + module.getFilterSupported.toString)
            //Get the DisplayField of each Module
            println("Module DisplayField: " + module.getDisplayField)
            //Get List of SearchLayoutFields APIName
            val searchLayoutFields = module.getSearchLayoutFields
            //Check if searchLayoutFields is not null
            if (searchLayoutFields != null) {

              for (fieldName <- searchLayoutFields) {
                println("Module SearchLayoutFields Fields: " + fieldName)
              }
            }
            if (module.getKanbanViewSupported != null) { //Get the KanbanViewSupported of each Module
              println("Module KanbanViewSupported: " + module.getKanbanViewSupported.toString)
            }
            println("Module ShowAsTab: " + module.getShowAsTab.toString)
            println("Module WebLink: " + module.getWebLink)
            println("Module SequenceNumber: " + module.getSequenceNumber.toString)
            println("Module SingularLabel: " + module.getSingularLabel)
            println("Module Viewable: " + module.getViewable.toString)
            println("Module APISupported: " + module.getAPISupported.toString)
            println("Module APIName: " + module.getAPIName)
            println("Module QuickCreate: " + module.getQuickCreate.toString)
            val modifiedByOption = module.getModifiedBy
            if (modifiedByOption.isDefined) {
              val modifiedBy=modifiedByOption.get
              println("Module Modified By User-Name: " + modifiedBy.getName)
              println("Module Modified By User-ID: " + modifiedBy.getId)
            }
            println("Module GeneratedType: " + module.getGeneratedType.getValue)
            println("Module FeedsRequired: " + module.getFeedsRequired.toString)
            println("Module ScoringSupported: " + module.getScoringSupported.toString)
            println("Module WebformSupported: " + module.getWebformSupported.toString)
            val arguments = module.getArguments
            if (arguments != null) {

              for (argument <- arguments) {
                println("Module Argument Name: " + argument.getName)
                println("Module Argument Value: " + argument.getValue)
              }
            }
            println("Module ModuleName: " + module.getModuleName)
            println("Module BusinessCardFieldLimit: " + module.getBusinessCardFieldLimit.toString)
            //Get the CustomView instance of each Module
            val customViewOption = module.getCustomView
            //Check if customView is not null
            if (customViewOption.isDefined) printCustomView(customViewOption.get)
            val parentModuleOption = module.getParentModule
            if (parentModuleOption.isDefined ) {
              val parentModule = parentModuleOption.get
              println("Module Parent Module Name: " + parentModule.getAPIName)
              println("Module Parent Module Id: " + parentModule.getId.toString)
            }
          }
        }
        else if (responseHandler.isInstanceOf[APIException]) {
          val exception = responseHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")
          if (exception.getDetails != null) {

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  private def printCustomView(customView: CustomView): Unit = { //Get the DisplayValue of the CustomView
    println("Module CustomView DisplayValue: " + customView.getDisplayValue)
    //Get the SharedType of the CustomView
    println("Module CustomView SharedType: " + customView.getSharedType)
    //Get the SystemName of the CustomView
    println("Module CustomView SystemName: " + customView.getSystemName)
    // Get the Criteria instance of the CustomView
    val criteriaOption = customView.getCriteria
    //Check if criteria is not null
    if (criteriaOption.isDefined) printCriteria(criteriaOption.get)
    //Get the list of SharedDetails instance of the CustomView
    val sharedDetails = customView.getSharedDetails
    if (sharedDetails != null) {

      for (sharedDetail <- sharedDetails) { //Get the Name of the each SharedDetails
        println("Module SharedDetails Name: " + sharedDetail.getName)
        //Get the ID of the each SharedDetails
        println("Module SharedDetails ID: " + sharedDetail.getId)
        //Get the Type of the each SharedDetails
        println("Module SharedDetails Type: " + sharedDetail.getType)
        //Get the Subordinates of the each SharedDetails
        println("Module SharedDetails Subordinates: " + sharedDetail.getSubordinates.toString)
      }
    }
    //Get the SortBy of the CustomView
    println("Module CustomView SortBy: " + customView.getSortBy)
    //Get the Offline of the CustomView
    println("Module CustomView Offline: " + customView.getOffline.toString)
    //Get the Default of the CustomView
    println("Module CustomView Default: " + customView.getDefault.toString)
    //Get the SystemDefined of the CustomView
    println("Module CustomView SystemDefined: " + customView.getSystemDefined.toString)
    //Get the Name of the CustomView
    println("Module CustomView Name: " + customView.getName)
    //Get the ID of the CustomView
    println("Module CustomView ID: " + customView.getId)
    //Get the Category of the CustomView
    println("Module CustomView Category: " + customView.getCategory)
    //Get the list of string
    val fields = customView.getFields
    if (fields != null) {

      for (field <- fields) {
        println(field)
      }
    }
    if (customView.getFavorite != null) { //Get the Favorite of the CustomView
      println("Module CustomView Favorite: " + customView.getFavorite.toString)
    }
    if (customView.getSortOrder != null) { //Get the SortOrder of the CustomView
      println("Module CustomView SortOrder: " + customView.getSortOrder.toString)
    }
  }

  private def printCriteria(criteria: Criteria): Unit = {
    if (criteria.getComparator() != null) { //Get the Comparator of the Criteria
      println("Module CustomView Criteria Comparator: " + criteria.getComparator.getValue)
    }
    //Get the Field of the Criteria
    println("Module CustomView Criteria Field: " + criteria.getField)
    if (criteria.getValue != null) { //Get the Value of the Criteria
      println("Module CustomView Criteria Value: " + criteria.getValue.toString)
    }
    // Get the List of Criteria instance of each Criteria
    val criteriaGroup = criteria.getGroup
    if (criteriaGroup != null) {

      for (criteria1 <- criteriaGroup) {
        printCriteria(criteria1)
      }
    }
    //Get the Group Operator of the Criteria
    println("Module CustomView Criteria Group Operator: " + criteria.getGroupOperator)
  }

  /**
   * <h3> Update Module By APIName </h3>
   * This method is used to update module details using module APIName and print the response.
   *
   * @param moduleAPIName The API Name of the module to update
   * @throws Exception
   */
  @throws[Exception]
  def updateModuleByAPIName(moduleAPIName: String): Unit = {
    val moduleOperations = new ModulesOperations
    val modules = new ArrayBuffer[Module]
    val profiles = new ArrayBuffer[Profile]
    //Get instance of Profile Class
    val profile = new Profile
    //To set the Profile Id
    profile.setId(Option(3477061000000026014l))
    //		profile.setDelete(true)
    profiles.addOne(profile)
    val module = new Module
    module.setProfiles(profiles)
    modules.addOne(module)
    val request = new BodyWrapper
    request.setModules(modules)
    //Call updateModuleByAPIName method that takes BodyWrapper instance and moduleAPIName as parameter
    val responseOption = moduleOperations.updateModuleByAPIName(moduleAPIName,request )
    if (responseOption.isDefined) { //check response
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getModules

          for (actionResponse <- actionResponses) { //Check if the request is successful
            if (actionResponse.isInstanceOf[SuccessResponse]) { //Get the received SuccessResponse instance
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")


              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            else if (actionResponse.isInstanceOf[APIException]) {
              val exception = actionResponse.asInstanceOf[APIException]
              println("Status: " + exception.getStatus.getValue)
              println("Code: " + exception.getCode.getValue)
              println("Details: ")

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")
          if (exception.getDetails != null) {

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Update Module By Id </h3>
   * This method is used to update module details using module Id and print the response.
   *
   * @param moduleId - The Id of the module to obtain metadata
   * @throws Exception
   */
  @throws[Exception]
  def updateModuleById(moduleId: Long): Unit = { //Long moduleId = 3477061000003905003L
    val moduleOperations = new ModulesOperations
    val modules = new ArrayBuffer[Module]
    val profiles = new ArrayBuffer[Profile]
    val profile = new Profile
    profile.setId(Option(3477061000000026014l))
    profiles.addOne(profile)
    val module = new Module
    module.setProfiles(profiles)
    module.setAPIName(Option("apiName1"))
    modules.addOne(module)
    val request = new BodyWrapper
    request.setModules(modules)
    //Call updateModuleById method that takes BodyWrapper instance and moduleId as parameter
    val responseOption = moduleOperations.updateModuleById(moduleId,request )
    if (responseOption.isDefined) { //check response
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getModules

          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            else if (actionResponse.isInstanceOf[APIException]) {
              val exception = actionResponse.asInstanceOf[APIException]
              println("Status: " + exception.getStatus.getValue)
              println("Code: " + exception.getCode.getValue)
              println("Details: ")

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")
          if (exception.getDetails != null) {

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }
}

class Modules {}
