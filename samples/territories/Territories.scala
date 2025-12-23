package com.zoho.crm.sample.territories

import java.util
import com.zoho.crm.api.customviews.Criteria
import com.zoho.crm.api.territories.APIException
import com.zoho.crm.api.territories.ResponseHandler
import com.zoho.crm.api.territories.ResponseWrapper
import com.zoho.crm.api.territories.TerritoriesOperations
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object Territories {
  /**
   * <h3> Get Territories </h3>
   * This method is used to get the list of territories enabled for your organization and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getTerritories(): Unit = { //Get instance of TerritoriesOperations Class
    val territoriesOperations = new TerritoriesOperations
    //Call getTerritories method
    val responseOption = territoriesOperations.getTerritories
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
          //Get the list of obtained Territory instances
          val territoryList = responseWrapper.getTerritories
          for (territory <- territoryList) { //Get the CreatedTime of each Territory
            println("Territory CreatedTime: " + territory.getCreatedTime)
            //Get the ModifiedTime of each Territory
            println("Territory ModifiedTime: " + territory.getModifiedTime)
            //Get the manager User instance of each Territory
            val managerOption = territory.getManager
            //Check if manager is not null
            if (managerOption.isDefined) { //Get the Name of the Manager
              val manager = managerOption.get
              println("Territory Manager User-Name: " + manager.getName)
              //Get the ID of the Manager
              println("Territory Manager User-ID: " + manager.getId)
            }
            //Get the ParentId of each Territory
            println("Territory ParentId: " + territory.getParentId)
            // Get the Criteria instance of each Territory
            val criteriaOption = territory.getCriteria
            //Check if criteria is not null
            if (criteriaOption.isDefined){
              printCriteria(criteriaOption.get)
            }
            //Get the Name of each Territory
            println("Territory Name: " + territory.getName)
            //Get the modifiedBy User instance of each Territory
            val modifiedByOption = territory.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Territory Modified By User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Territory Modified By User-ID: " + modifiedBy.getId)
            }
            //Get the Description of each Territory
            println("Territory Description: " + territory.getDescription)
            //Get the ID of each Territory
            println("Territory ID: " + territory.getId)
            //Get the createdBy User instance of each Territory
            val createdByOption = territory.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Territory Created By User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Territory Created By User-ID: " + createdBy.getId)
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
   * <h3> Get Territory </h3>
   * This method is used to get the single territory and print the response.
   *
   * @param territoryId - The ID of the Territory to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getTerritory(territoryId: Long): Unit = { //example
    //Long territoryId = 3477061000003051397l
    val territoriesOperations = new TerritoriesOperations
    //Call getTerritory method that takes territoryId as parameter
    val responseOption = territoriesOperations.getTerritory(territoryId)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
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
          val territoryList = responseWrapper.getTerritories
          for (territory <- territoryList) {
            println("Territory CreatedTime: " + territory.getCreatedTime)
            println("Territory ModifiedTime: " + territory.getModifiedTime)
            val managerOption = territory.getManager
            //Check if manager is not null
            if (managerOption.isDefined) { //Get the Name of the Manager
              val manager = managerOption.get
              println("Territory Manager User-Name: " + manager.getName)
              //Get the ID of the Manager
              println("Territory Manager User-ID: " + manager.getId)
            }
            println("Territory ParentId: " + territory.getParentId)
            val criteria = territory.getCriteria
            val criteriaOption = territory.getCriteria
            //Check if criteria is not null
            if (criteriaOption.isDefined){
              printCriteria(criteriaOption.get)
            }
            println("Territory Name: " + territory.getName)
            val modifiedByOption = territory.getModifiedBy
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Territory Modified By User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Territory Modified By User-ID: " + modifiedBy.getId)
            }
            println("Territory Description: " + territory.getDescription)
            println("Territory ID: " + territory.getId)
            val createdByOption = territory.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Territory Created By User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Territory Created By User-ID: " + createdBy.getId)
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
    if (criteria.getField != null) { //Get the Field of the Criteria
      println("Territory Criteria Field: " + criteria.getField)
    }
    if (criteria.getComparator != null) { //Get the Comparator of the Criteria
      println("Territory Criteria Comparator: " + criteria.getComparator.getValue.toString)
    }
    if (criteria.getValue != null) { //Get the Value of the Criteria
      println("Territory Criteria Value: " + criteria.getValue.toString)
    }
    // Get the List of Criteria instance of each Criteria
    val criteriaGroup = criteria.getGroup
    if (criteriaGroup != null) {
      for (criteria1 <- criteriaGroup) {
        printCriteria(criteria1)
      }
    }
    if (criteria.getGroupOperator != null) { //Get the Group Operator of the Criteria
      println("Territory Criteria Group Operator: " + criteria.getGroupOperator.getValue.toString)
    }
  }
}
class Territories{

}
