package com.zoho.crm.sample.variablegroups

import java.util

import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.variablegroups.{APIException, ResponseHandler, ResponseWrapper, VariableGroup, VariableGroupsOperations}

import scala.collection.mutable.ArrayBuffer


object Variablegroup {
  /**
   * <h3> Get Variable Groups </h3>
   * This method is used to get the details of all the variable groups and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getVariableGroups(): Unit = { //Get instance of VariableGroupsOperations Class
    val variableGroupsOperations = new VariableGroupsOperations
    //Call getVariableGroups method
    val responseOption = variableGroupsOperations.getVariableGroups
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
          //Get the obtained VariableGroup instance
          var variableGroups:ArrayBuffer[VariableGroup] = responseWrapper.getVariableGroups()
          variableGroups.foreach(variableGroup => { //Get the DisplayLabel of each VariableGroup

            println("VariableGroup DisplayLabel: " + variableGroup.getDisplayLabel)
            //Get the APIName of each VariableGroup
            println("VariableGroup APIName: " + variableGroup.getAPIName)
            //Get the Name of each VariableGroup
            println("VariableGroup Name: " + variableGroup.getName)
            //Get the Description of each VariableGroup
            println("VariableGroup Description: " + variableGroup.getDescription)
            //Get the ID of each VariableGroup
            println("VariableGroup ID: " + variableGroup.getId)
          })
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
   * <h3> Get Variable Group By Id </h3>
   * This method is used to get the details of any variable group with group id and print the response.
   *
   * @param variableGroupId - The ID of the VariableGroup to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getVariableGroupById(variableGroupId: Long): Unit = { //example
    //Long variableGroupId = 3477061000003089001l
    val variableGroupsOperations = new VariableGroupsOperations
    //Call getVariableGroupById method that takes variableGroupId as parameter
    val responseOption = variableGroupsOperations.getVariableGroupById(variableGroupId)
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
          val variableGroups = responseWrapper.getVariableGroups
          variableGroups.foreach(variableGroup => { //Get the DisplayLabel of each VariableGroup
            println("VariableGroup DisplayLabel: " + variableGroup.getDisplayLabel)
            println("VariableGroup APIName: " + variableGroup.getAPIName)
            println("VariableGroup Name: " + variableGroup.getName)
            println("VariableGroup Description: " + variableGroup.getDescription)
            println("VariableGroup ID: " + variableGroup.getId)

          })
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

  /**
   * <h3> Get Variable Group By APIName </h3>
   * This method is used to get the details of any variable group with group name and print the response.
   *
   * @param variableGroupName - The API Name of the VariableGroup to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getVariableGroupByAPIName(variableGroupName: String): Unit = { //String variableGroupName = "General"
    val variableGroupsOperations = new VariableGroupsOperations
    //Call getVariableGroupByAPIName method that takes variableGroupName as parameter
    val responseOption = variableGroupsOperations.getVariableGroupByAPIName(variableGroupName)
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
          val variableGroups = responseWrapper.getVariableGroups
          variableGroups.foreach(variableGroup => { //Get the DisplayLabel of each VariableGroup
            println("VariableGroup DisplayLabel: " + variableGroup.getDisplayLabel)
            println("VariableGroup APIName: " + variableGroup.getAPIName)
            println("VariableGroup Name: " + variableGroup.getName)
            println("VariableGroup Description: " + variableGroup.getDescription)
            println("VariableGroup ID: " + variableGroup.getId)

          })
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
}

class Variablegroup {}