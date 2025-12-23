package com.zoho.crm.sample.customview

import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.customviews.APIException
import com.zoho.crm.api.customviews.Criteria
import com.zoho.crm.api.customviews.CustomViewsOperations
import com.zoho.crm.api.customviews.CustomViewsOperations.GetCustomViewsParam
import com.zoho.crm.api.customviews.Info
import com.zoho.crm.api.customviews.ResponseHandler
import com.zoho.crm.api.customviews.ResponseWrapper
import com.zoho.crm.api.customviews.SharedDetails
import com.zoho.crm.api.customviews.Translation
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object CustomViews {
  /**
   * <h3> Get CustomViews </h3>
   * This method is used to get the custom views data of a particular module.
   * Specify the module name in your API request whose custom view data you want to retrieve.
   *
   * @param moduleAPIName - Specify the API name of the required module.
   * @throws Exception
   */
  @throws[Exception]
  def getCustomViews(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of CustomViewOperations Class that takes moduleAPIName as parameter
    val customViewsOperations = new CustomViewsOperations(Option(moduleAPIName))
    //Call getCustomViews method
    val paramInstance = new ParameterMap
    paramInstance.add(new GetCustomViewsParam().page,1)
    val responseOption = customViewsOperations.getCustomViews(Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
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
          //Get the list of obtained CustomView instances
          val customViews = responseWrapper.getCustomViews

          for (customView <- customViews) { //Get the DisplayValue of each CustomView
            println("CustomView DisplayValue: " + customView.getDisplayValue)
            //Get the Offline of the each CustomView
            println("CustomView Offline: " + customView.getOffline.toString)
            //Get the Default of each CustomView
            println("CustomView Default: " + customView.getDefault.toString)
            //Get the SystemName of each CustomView
            println("CustomView SystemName: " + customView.getSystemName)
            //Get the SystemDefined of each CustomView
            println("CustomView SystemDefined: " + customView.getSystemDefined.toString)
            //Get the Name of each CustomView
            println("CustomView Name: " + customView.getName)
            //Get the ID of each CustomView
            println("CustomView ID: " + customView.getId)
            //Get the Category of each CustomView
            println("CustomView Category: " + customView.getCategory)
            if (customView.getFavorite != null) { //Get the Favorite of each CustomView
              println("CustomView Favorite: " + customView.getFavorite.toString)
            }
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
            if (info.getPerPage.isDefined) { //Get the PerPage of the Info
              println("CustomView Info PerPage: " + info.getPerPage.toString)
            }
            if (info.getDefault.isDefined) { //Get the Default of the Info
              println("CustomView Info Default: " + info.getDefault)
            }
            if (info.getCount.isDefined) { //Get the Count of the Info
              println("CustomView Info Count: " + info.getCount.toString)
            }
            //Get the Translation instance of CustomView
            val translationOption = info.getTranslation
            if (translationOption.isDefined) { //Get the PublicViews of the Translation
              val translation = translationOption.get
              println("CustomView Info Translation PublicViews: " + translation.getPublicViews)
              //Get the OtherUsersViews of the Translation
              println("CustomView Info Translation OtherUsersViews: " + translation.getOtherUsersViews)
              //Get the SharedWithMe of the Translation
              println("CustomView Info Translation SharedWithMe: " + translation.getSharedWithMe)
              //Get the CreatedByMe of the Translation
              println("CustomView Info Translation CreatedByMe: " + translation.getCreatedByMe)
            }
            if (info.getPage.isDefined) { //Get the Page of the Info
              println("CustomView Info Page: " + info.getPage.toString)
            }
            if (info.getMoreRecords.isDefined) { //Get the MoreRecords of the Info
              println("CustomView Info MoreRecords: " + info.getMoreRecords.toString)
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
   * This method is used to get the data of any specific custom view of the module.
   * Specify the custom view ID of the module in your API request whose custom view data you want to retrieve.
   *
   * @param moduleAPIName - Specify the API name of the required module.
   * @param customViewId  - ID of the CustomView to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def getCustomView(moduleAPIName: String, customViewId: Long): Unit = { //Long customViewId = 3477061000005629003
    val customViewsOperations = new CustomViewsOperations(Option(moduleAPIName))
    //Call getCustomView method that takes customViewId as parameter
    val responseOption = customViewsOperations.getCustomView(customViewId)
    if (responseOption.isDefined) {
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
          val customViews = responseWrapper.getCustomViews

          for (customView <- customViews) {
            println("CustomView DisplayValue: " + customView.getDisplayValue)
            //Get the SharedType of each CustomView
            println("CustomView SharedType: " + customView.getSharedType)
            println("CustomView SystemName: " + customView.getSystemName)
            // Get the Criteria instance of each CustomView
            val criteriaOption = customView.getCriteria
            //Check if criteria is not null
            if (criteriaOption.isDefined) printCriteria(criteriaOption.get)
            val sharedDetails = customView.getSharedDetails
            if (sharedDetails != null) {

              for (sharedDetail <- sharedDetails) { //Get the Name of the each SharedDetails
                println("SharedDetails Name: " + sharedDetail.getName)
                //Get the ID of the each SharedDetails
                println("SharedDetails ID: " + sharedDetail.getId)
                //Get the Type of the each SharedDetails
                println("SharedDetails Type: " + sharedDetail.getType)
                //Get the Subordinates of the each SharedDetails
                println("SharedDetails Subordinates: " + sharedDetail.getSubordinates.toString)
              }
            }
            //Get the SortBy of the each CustomView
            println("CustomView SortBy: " + customView.getSortBy)
            println("CustomView Offline: " + customView.getOffline.toString)
            println("CustomView Default: " + customView.getDefault.toString)
            println("CustomView SystemDefined: " + customView.getSystemDefined.toString)
            println("CustomView Name: " + customView.getName)
            println("CustomView ID: " + customView.getId)
            println("CustomView Category: " + customView.getCategory)
            //Get the list of fields in each CustomView
            val fields = customView.getFields
            if (fields != null) {

              for (field <- fields) {
                println(field)
              }
            }
            if (customView.getFavorite != null) println("CustomView Favorite: " + customView.getFavorite.toString)
            if (customView.getSortOrder != null) { //Get the SortOrder of each CustomView
              println("CustomView SortOrder: " + customView.getSortOrder.toString)
            }
          }
          val infoOption = responseWrapper.getInfo
          if (infoOption.isDefined) {
            val info = infoOption.get
            val translationOption = info.getTranslation
            if (translationOption.isDefined) {
              val translation =translationOption.get
              println("CustomView Info Translation PublicViews: " + translation.getPublicViews)
              println("CustomView Info Translation OtherUsersViews: " + translation.getOtherUsersViews)
              println("CustomView Info Translation SharedWithMe: " + translation.getSharedWithMe)
              println("CustomView Info Translation CreatedByMe: " + translation.getCreatedByMe)
            }
          }
        }
        else if (responseHandler.isInstanceOf[APIException]) {
          val exception = responseHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
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

  private def printCriteria(criteria: Criteria): Unit = {
    if (criteria.getComparator != null) { //Get the Comparator of the Criteria
      println("CustomView Criteria Comparator: " + criteria.getComparator.getValue)
    }
    //Get the Field of the Criteria
    println("CustomView Criteria Field: " + criteria.getField)
    if (criteria.getValue != null) { //Get the Value of the Criteria
      println("CustomView Criteria Value: " + criteria.getValue.toString)
    }
    // Get the List of Criteria instance of each Criteria
    val criteriaGroup = criteria.getGroup
    if (criteriaGroup != null) {

      for (criteria1 <- criteriaGroup) {
        printCriteria(criteria1)
      }
    }
    if (criteria.getGroupOperator != null) { //Get the Group Operator of the Criteria
      println("CustomView Criteria Group Operator: " + criteria.getGroupOperator.getValue)
    }
  }
}

class CustomViews {}
