package com.zoho.crm.sample.role

import java.util

import com.zoho.crm.api.roles.{APIException, ResponseWrapper, RolesOperations}


object Roles {
  /**
   * <h3> Get Roles </h3>
   * This method is used to retrieve the data of roles through an API request and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getRoles(): Unit = { //Get instance of RolesOperations Class
    val rolesOperations = new RolesOperations
    //Call getRoles method
    val responseOption = rolesOperations.getRoles
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
          //Get the list of obtained Role instances
          val roles = responseWrapper.getRoles
          for (role <- roles) { //Get the DisplayLabel of each Role
            println("Role DisplayLabel: " + role.getDisplayLabel)
            //Get the forecastManager User instance of each Role
            val forecastManagerOption = role.getForecastManager
            //Check if forecastManager is not null
            if (forecastManagerOption.isDefined) { //Get the ID of the forecast Manager
              val forecastManager= forecastManagerOption.get
              println("Role Forecast Manager User-ID: " + forecastManager.getId)
              //Get the name of the forecast Manager
              println("Role Forecast Manager User-Name: " + forecastManager.getName)
            }
            //Get the ShareWithPeers of each Role
            println("Role ShareWithPeers: " + role.getShareWithPeers.toString)
            //Get the Name of each Role
            println("Role Name: " + role.getName)
            //Get the Description of each Role
            println("Role Description: " + role.getDescription)
            //Get the Id of each Role
            println("Role ID: " + role.getId)
            //Get the reportingTo User instance of each Role
            val reportingToOption = role.getReportingTo
            //Check if reportingTo is not null
            if (reportingToOption.isDefined) { //Get the ID of the reportingTo User
              val reportingTo = reportingToOption.get
              println("Role ReportingTo User-ID: " + reportingTo.getId)
              //Get the name of the reportingTo User
              println("Role ReportingTo User-Name: " + reportingTo.getName)
            }
            //Get the AdminUser of each Role
            println("Role AdminUser: " + role.getAdminUser.toString)
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
   * <h3> Get Role </h3>
   * This method is used to retrieve the data of single role through an API request and print the response.
   *
   * @param roleId The ID of the Role to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getRole(roleId: Long): Unit = { //example
    //Long roleId = 3477061000000003881
    val rolesOperations = new RolesOperations
    //Call getRole method that takes roleId as parameter
    val responseOption = rolesOperations.getRole(roleId)
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
          val roles = responseWrapper.getRoles
          for (role <- roles) {
            println("Role DisplayLabel: " + role.getDisplayLabel)
            val forecastManagerOption = role.getForecastManager
            //Check if forecastManager is not null
            if (forecastManagerOption.isDefined) { //Get the ID of the forecast Manager
              val forecastManager= forecastManagerOption.get
              println("Role Forecast Manager User-ID: " + forecastManager.getId)
              //Get the name of the forecast Manager
              println("Role Forecast Manager User-Name: " + forecastManager.getName)
            }
            println("Role ShareWithPeers: " + role.getShareWithPeers.toString)
            println("Role Name: " + role.getName)
            println("Role Description: " + role.getDescription)
            println("Role ID: " + role.getId)
            val reportingToOption = role.getReportingTo
            //Check if reportingTo is not null
            if (reportingToOption.isDefined) { //Get the ID of the reportingTo User
              val reportingTo = reportingToOption.get
              println("Role ReportingTo User-ID: " + reportingTo.getId)
              //Get the name of the reportingTo User
              println("Role ReportingTo User-Name: " + reportingTo.getName)
            }
            println("Role AdminUser: " + role.getAdminUser.toString)
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
}

class Roles {}