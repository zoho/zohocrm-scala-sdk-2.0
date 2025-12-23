package com.zoho.crm.sample.sharerecords

import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.modules.Module
import com.zoho.crm.api.sharerecords.APIException
import com.zoho.crm.api.sharerecords.ActionHandler
import com.zoho.crm.api.sharerecords.ActionResponse
import com.zoho.crm.api.sharerecords.ActionWrapper
import com.zoho.crm.api.sharerecords.BodyWrapper
import com.zoho.crm.api.sharerecords.DeleteActionHandler
import com.zoho.crm.api.sharerecords.DeleteActionResponse
import com.zoho.crm.api.sharerecords.DeleteActionWrapper
import com.zoho.crm.api.sharerecords.ResponseHandler
import com.zoho.crm.api.sharerecords.ResponseWrapper
import com.zoho.crm.api.sharerecords.ShareRecord
import com.zoho.crm.api.sharerecords.ShareRecordsOperations
import com.zoho.crm.api.sharerecords.ShareRecordsOperations.GetSharedRecordDetailsParam
import com.zoho.crm.api.sharerecords.SharedThrough
import com.zoho.crm.api.sharerecords.SuccessResponse
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object ShareRecords {
  /**
   * <h3> Get Shared Record Details </h3>
   * This method is used to get the details of a shared record and print the response.
   *
   * @param moduleAPIName - The API Name of the module to get shared record details.
   * @param recordId      - The ID of the record to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def getSharedRecordDetails(moduleAPIName: String, recordId: Long): Unit = { //example
    //String moduleAPIName = "Leads"
    //Long recordId = 3477061000005177002L
    //Get instance of ShareRecordsOperations Class that takes moduleAPIName and recordId as parameter
    val shareRecordsOperations = new ShareRecordsOperations(recordId,moduleAPIName )
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new GetSharedRecordDetailsParam().view, "summary")
    //		paramInstance.add(GetSharedRecordDetailsParam.SHAREDTO, 3477061000000173021l)
    //Call getSharedRecordDetails method that takes paramInstance as parameter
    val responseOption = shareRecordsOperations.getSharedRecordDetails(Option(paramInstance))
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
          //Get the obtained ShareRecord instance
          val shareRecords = responseWrapper.getShare

          for (shareRecord <- shareRecords) { //Get the ShareRelatedRecords of each ShareRecord
            println("ShareRecord ShareRelatedRecords: " + shareRecord.getShareRelatedRecords.toString)
            //Get the SharedThrough instance of each ShareRecord
            val sharedThroughOption = shareRecord.getSharedThrough
            //Check if sharedThrough is not null
            if (sharedThroughOption.isDefined) { //Get the EntityName of the SharedThrough
              val sharedThrough=sharedThroughOption.get
              println("ShareRecord SharedThrough EntityName: " + sharedThrough.getEntityName)
              //Get the Module instance of each ShareRecord
              val moduleOption = sharedThrough.getModule
              if (moduleOption.isDefined) { //Get the ID of the Module
                val module=moduleOption.get
                println("ShareRecord SharedThrough Module ID: " + module.getId.toString)
                //Get the name of the Module
                println("ShareRecord SharedThrough Module Name: " + module.getName)
              }
              //Get the ID of the SharedThrough
              println("ShareRecord SharedThrough ID: " + sharedThrough.getId)
            }
            //Get the SharedTime of each ShareRecord
            println("ShareRecord SharedTime: " + shareRecord.getSharedTime)
            //Get the Permission of each ShareRecord
            println("ShareRecord Permission: " + shareRecord.getPermission.toString)
            //Get the sharedBy of each ShareRecord
            val sharedByOption = shareRecord.getSharedBy
            //Check if sharedBy is not null
            if (sharedByOption.isDefined) { //Get the ID of the  User
              val sharedBy = sharedByOption.get
              println("ShareRecord SharedBy-ID: " + sharedBy.getId)
              //Get the FullName of the  User
              println("ShareRecord SharedBy-FullName: " + sharedBy.getFullName)
              //Get the Zuid of the  User
              println("ShareRecord SharedBy-Zuid: " + sharedBy.getZuid)
            }
            //Get the shared User instance of each ShareRecord
            val userOption  = shareRecord.getUser
            //Check if user is not null
            if (userOption.isDefined) { //Get the ID of the shared User
              val user =userOption.get
              println("ShareRecord User-ID: " + user.getId)
              //Get the FullName of the shared User
              println("ShareRecord User-FullName: " + user.getFullName)
              //Get the Zuid of the shared User
              println("ShareRecord User-Zuid: " + user.getZuid)
            }
          }
          if (responseWrapper.getShareableUser != null) {
            val shareableUsers = responseWrapper.getShareableUser

            for (shareableUser <- shareableUsers) { //Get the ID of the shareable User
              println("ShareRecord User-ID: " + shareableUser.getId)
              //Get the FullName of the shareable User
              println("ShareRecord User-FullName: " + shareableUser.getFullName)
              //Get the Zuid of the shareable User
              println("ShareRecord User-Zuid: " + shareableUser.getZuid)
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
   * <h3> Share Record </h3>
   * This method is used to share the record and print the response.
   *
   * @param moduleAPIName - The API Name of the module to shared record.
   * @param recordId      - The ID of the record to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def shareRecord(moduleAPIName: String, recordId: Long): Unit = {
    val shareRecordsOperations = new ShareRecordsOperations(recordId,moduleAPIName)
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of ShareRecord instances
    val shareList = new ArrayBuffer[ShareRecord]
    //Get instance of ShareRecord Class
    var share1 = new ShareRecord
    //    for (i <- 0 until 9) {
    //      share1 = new ShareRecord
    //      //Set the record is shared with or without related records.
    //      share1.setShareRelatedRecords(true)
    //      //Set the access permission given to the user for that record.
    //      share1.setPermission("read_write")
    //      val user = new User
    //      user.setId(3524033000006089006L)
    //      //Set the users details with whom the record is shared.
    //      share1.setUser(user)
    //      shareList.addOne(share1)
    //    }
    share1.setShareRelatedRecords(Option(true))
    share1.setPermission(Option("read_write"))
    val user = new User
    user.setId(Option(3477061000005791024l))
    share1.setUser(Option(user))
    shareList.addOne(share1)
    request.setShare(shareList)
    //Call shareRecord method that takes BodyWrapper instance as parameter
    val responseOption = shareRecordsOperations.shareRecord(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getShare

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
   * <h3> Update Share Permissions </h3>
   * This method is used to update the sharing permissions of a record granted to users as Read-Write, Read-only, or grant full access.
   *
   * @param moduleAPIName - The API Name of the module to update share permissions.
   * @param recordId      - The ID of the record to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def updateSharePermissions(moduleAPIName: String, recordId: Long): Unit = {
    val shareRecordsOperations = new ShareRecordsOperations(recordId,moduleAPIName)
    val request = new BodyWrapper
    val shareList = new ArrayBuffer[ShareRecord]
    val share1 = new ShareRecord
    share1.setShareRelatedRecords(Option(true))
    share1.setPermission(Option("full_access"))
    val user = new User
    user.setId(Option(3477061000005791024l))
    share1.setUser(Option(user))
    shareList.addOne(share1)
    request.setShare(shareList)
    //Call updateSharePermissions method that takes BodyWrapper instance as parameter
    val responseOption = shareRecordsOperations.updateSharePermissions(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getShare

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
   * <h3> Revoke Shared Record </h3>
   * This method is used to revoke access to a shared record that was shared to users and print the response.
   *
   * @param moduleAPIName - The API Name of the module to revoke shared record.
   * @param recordId      - The ID of the record to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def revokeSharedRecord(moduleAPIName: String, recordId: Long): Unit = {
    val shareRecordsOperations = new ShareRecordsOperations(recordId,moduleAPIName)
    //Call revokeSharedRecord method
    val responseOption = shareRecordsOperations.revokeSharedRecord
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val deleteActionHandler = response.getObject
        if (deleteActionHandler.isInstanceOf[DeleteActionWrapper]) { //Get the received DeleteActionWrapper instance
          val deleteActionWrapper = deleteActionHandler.asInstanceOf[DeleteActionWrapper]
          //Get the received DeleteActionResponse instance
          val deleteActionResponse = deleteActionWrapper.getShare
          if (deleteActionResponse.isInstanceOf[SuccessResponse]) {
            val successResponse = deleteActionResponse.asInstanceOf[SuccessResponse]
            println("Status: " + successResponse.getStatus.getValue)
            println("Code: " + successResponse.getCode.getValue)
            println("Details: ")
            successResponse.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + successResponse.getMessage.getValue)
          }
          else if (deleteActionResponse.isInstanceOf[APIException]) {
            val exception = deleteActionResponse.asInstanceOf[APIException]
            println("Status: " + exception.getStatus.getValue)
            println("Code: " + exception.getCode.getValue)
            println("Details: ")
            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + exception.getMessage.getValue)
          }
        }
        else if (deleteActionHandler.isInstanceOf[APIException]) {
          val exception = deleteActionHandler.asInstanceOf[APIException]
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

class ShareRecords {}