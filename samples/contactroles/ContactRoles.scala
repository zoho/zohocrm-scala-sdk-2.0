package com.zoho.crm.sample.contactroles

import java.lang.reflect.Field
import java.util
import com.zoho.crm.api.contactroles.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, ContactRole, ContactRoleWrapper, ContactRolesOperations, RecordActionWrapper, RecordBodyWrapper, RecordResponseWrapper, ResponseHandler, ResponseWrapper, SuccessResponse}
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.contactroles.ContactRolesOperations.{DeleteContactRolesParam, GetAllContactRolesOfDealParam}
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object ContactRoles {
  /**
   * <h3> Get Contact Roles </h3>
   * This method is used to get all the Contact Roles and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getContactRoles(): Unit = { //Get instance of ContactRolesOperations Class
    val contactRolesOperations = new ContactRolesOperations
    //Call getContactRoles method
    val responseOption = contactRolesOperations.getContactRoles
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
          //Get the list of obtained ContactRole instances
          val contactRoles = responseWrapper.getContactRoles
          
          for (contactRole <- contactRoles) { //Get the ID of each ContactRole
            println("ContactRole ID: " + contactRole.getId)
            //Get the name of each ContactRole
            println("ContactRole Name: " + contactRole.getName)
            //Get the sequence number each ContactRole
            println("ContactRole SequenceNumber: " + contactRole.getSequenceNumber)
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
      else if (response.getStatusCode != 204) { //If response is not as expected
        //Get model object from response
        val responseObject = response.getModel
        //Get the response object's class
        val clas = responseObject.getClass
        //Get all declared fields of the response class
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          field.setAccessible(true)
          //Get each value
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Create Contact Roles </h3>
   * This method is used to create Contact Roles and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def createContactRoles(): Unit = {
    val contactRolesOperations = new ContactRolesOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    //List of ContactRole instances
    val contactRoles = new ArrayBuffer[ContactRole]
    for (i <- 1 to 5) { //Get instance of ContactRole Class
      val contactRole = new ContactRole
      //Set name of the Contact Role
      contactRole.setName(Option("contactRole12".concat(i.toString)))
      //Set sequence number of the Contact Role
      contactRole.setSequenceNumber(Option(i))
      //Add ContactRole instance to the list
      contactRoles.addOne(contactRole)
    }
    //Set the list to contactRoles in BodyWrapper instance
    bodyWrapper.setContactRoles(contactRoles)
    //Call createContactRoles method that takes BodyWrapper instance as parameter
    val responseOption = contactRolesOperations.createContactRoles(bodyWrapper)
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getContactRoles
          
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
   * <h3> Update Contact Roles </h3>
   * This method is used to update Contact Roles and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateContactRoles(): Unit = {
    val contactRolesOperations = new ContactRolesOperations
    val bodyWrapper = new BodyWrapper
    val contactRolesList = new ArrayBuffer[ContactRole]
    val cr1 = new ContactRole
    //Set ID to the ContactRole instance
    cr1.setId(Option(3477061000011644011l))
    //Set name to the ContactRole instance
    cr1.setName(Option("Edisadted1"))
    contactRolesList.addOne(cr1)
    val cr2 = new ContactRole
    cr2.setId(Option(3477061000011644009l))
    cr2.setSequenceNumber(Option(1))
    cr2.setName(Option("Editsded1"))
    contactRolesList.addOne(cr2)
    bodyWrapper.setContactRoles(contactRolesList)
    //Call updateContactRoles method that takes BodyWrapper instance as parameter
    val responseOption = contactRolesOperations.updateContactRoles(bodyWrapper)
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getContactRoles
          
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
   * <h3> Delete Contact Roles </h3>
   * This method is used to delete Contact Roles and print the response.
   *
   * @param contactRoleIds - The List of ContactRole IDs to be deleted.
   * @throws Exception
   */
  @throws[Exception]
  def deleteContactRoles(contactRoleIds: ArrayBuffer[String]): Unit = { //example
    //		ArrayList<Long> contactRoleIds = new ArrayList<Long>(Arrays.asList(3477061000005208001l,3477061000005208002l))
    val contactRolesOperations = new ContactRolesOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    
    for (id <- contactRoleIds) {
      paramInstance.add(new DeleteContactRolesParam().ids, id)
    }
    //Call deleteContactRoles method that takes paramInstance as parameter
    val responseOption = contactRolesOperations.deleteContactRoles(Option(paramInstance))
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getContactRoles
          
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
   * <h3> Get Contact Role </h3>
   * This method is used to get single Contact Role with ID and print the response.
   *
   * @param contactRoleId - The ID of the ContactRole to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getContactRole(contactRoleId: Long): Unit = { //Long contactRoleId = 3477061000005177004l
    val contactRolesOperations = new ContactRolesOperations
    //Call getContactRole method that takes contactRoleId as parameter
    val responseOption = contactRolesOperations.getContactRole(contactRoleId)
    if (responseOption.isDefined) {
      var response = responseOption.get
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
          val contactRoles = responseWrapper.getContactRoles
          
          for (contactRole <- contactRoles) {
            println("ContactRole ID: " + contactRole.getId)
            println("ContactRole Name: " + contactRole.getName)
            println("ContactRole SequenceNumber: " + contactRole.getSequenceNumber)
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

  /**
   * <h3> Update Contact Role </h3>
   * This method is used to update single Contact Role with ID and print the response.
   *
   * @param contactRoleId The ID of the ContactRole to be updated
   * @throws Exception
   */
  @throws[Exception]
  def updateContactRole(contactRoleId: Long): Unit = { //ID of the ContactRole to be updated
    //Long contactRoleId = 525508000005067923l
    val contactRolesOperations = new ContactRolesOperations
    val bodyWrapper = new BodyWrapper
    val contactRolesList = new ArrayBuffer[ContactRole]
    val cr1 = new ContactRole
    cr1.setName(Option("contactRole4w"))
    //Set sequence number to the ContactRole instance
    cr1.setSequenceNumber(Option(2))
    contactRolesList.addOne(cr1)
    bodyWrapper.setContactRoles(contactRolesList)
    //Call updateContactRole method that takes BodyWrapper instance and contactRoleId as parameters
    val responseOption = contactRolesOperations.updateContactRole(contactRoleId,bodyWrapper )
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getContactRoles
          
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
   * <h3> Delete Contact Role </h3>
   * This method is used to delete single Contact Role with ID and print the response.
   *
   * @param contactRoleId ID of the ContactRole to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteContactRole(contactRoleId: Long): Unit = {
    val contactRolesOperations = new ContactRolesOperations
    //Call deleteContactRole which takes contactRoleId as parameter
    val responseOption = contactRolesOperations.deleteContactRole(contactRoleId)
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getContactRoles
          
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
   * <h3> Get All ContactRoles Of Deal </h3>
   * @param dealId ID of the Deals
   * @throws Exception
   */
  @throws[Exception]
  def getAllContactRolesOfDeal(dealId: Long): Unit = {
    //Get instance of ContactRolesOperations Class
    val contactRolesOperations = new ContactRolesOperations

    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap();

//    paramInstance.add(new GetAllContactRolesOfDealParam().ids, "")

    //Call getAllContactRolesOfDeal method that takes Param instance as parameter
    val responseOption = contactRolesOperations.getAllContactRolesOfDeal(dealId, Option(paramInstance))

    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)

      //Check if expected response is received
      if (response.isExpected) { //Get the object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[RecordResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[RecordResponseWrapper]
          //Get the obtained Record instances
          val records = responseWrapper.getData
          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            val createdByOption = record.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            val modifiedByOption = record.getModifiedBy()
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              val value = entry._2
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                println("Record KeyName : " + keyName)
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                for (data <- dataList) {
                  if (data.isInstanceOf[collection.Map[String,Any]]) {
                    println("Record KeyName : " + keyName + " - Value : ")
                    data.asInstanceOf[collection.Map[String,Any]].foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                  else println(data)
                }
              }
              else if (value.isInstanceOf[util.Map[_, _]]) {
                println("Record KeyName : " + keyName + " - Value : ")
                value.asInstanceOf[collection.Map[String,Any]].foreach(entry=>{
                  println(entry._1 + ": " + entry._2)
                })

              }
              else println("Record KeyName : " + keyName + " - Value : " + value)
            })
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
            if (info.getCount != null) { //Get the Count of the Info
              println("Record Info Count: " + info.getCount.toString)
            }
            if (info.getMoreRecords != null) { //Get the MoreRecords of the Info
              println("Record Info MoreRecords: " + info.getMoreRecords.toString)
            }
          }
        }
        else { //Check if the request returned an exception
          if (responseHandler.isInstanceOf[com.zoho.crm.api.contactroles.APIException]) { //Get the received APIException instance
            val exception = responseHandler.asInstanceOf[com.zoho.crm.api.contactroles.APIException]
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

  def getContactRoleOfDeal(contactId: Long, dealId: Long): Unit = {
    //Get instance of ContactRolesOperations Class
    val contactRolesOperations = new ContactRolesOperations()

    //Call getContactRoleOfDeal method that takes Param instance as parameter
    val responseOption = contactRolesOperations.getContactRoleOfDeal(contactId, dealId)

    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)

      //Check if expected response is received
      if (response.isExpected) { //Get the object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[RecordResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[RecordResponseWrapper]
          //Get the obtained Record instances
          val records = responseWrapper.getData
          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            val createdByOption = record.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            val modifiedByOption = record.getModifiedBy()
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              val value = entry._2
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                println("Record KeyName : " + keyName)
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                for (data <- dataList) {
                  if (data.isInstanceOf[collection.Map[String,Any]]) {
                    println("Record KeyName : " + keyName + " - Value : ")
                    data.asInstanceOf[collection.Map[String,Any]].foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                  else println(data)
                }
              }
              else if (value.isInstanceOf[util.Map[_, _]]) {
                println("Record KeyName : " + keyName + " - Value : ")
                value.asInstanceOf[collection.Map[String,Any]].foreach(entry=>{
                  println(entry._1 + ": " + entry._2)
                })

              }
              else println("Record KeyName : " + keyName + " - Value : " + value)
            })
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
            if (info.getCount != null) { //Get the Count of the Info
              println("Record Info Count: " + info.getCount.toString)
            }
            if (info.getMoreRecords != null) { //Get the MoreRecords of the Info
              println("Record Info MoreRecords: " + info.getMoreRecords.toString)
            }
          }
        }
        else { //Check if the request returned an exception
          if (responseHandler.isInstanceOf[com.zoho.crm.api.contactroles.APIException]) { //Get the received APIException instance
            val exception = responseHandler.asInstanceOf[com.zoho.crm.api.contactroles.APIException]
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

  def addContactRoleToDeal(contactId: Long, dealId: Long): Unit = {
    //Get instance of ContactRolesOperations Class
    val contactRolesOperations = new ContactRolesOperations

    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new RecordBodyWrapper()

    val contactRolesList = new ArrayBuffer[ContactRoleWrapper]

    //Get instance of ContactRole Class
    val contactRole = new ContactRoleWrapper()

    //Set name of the Contact Role
    contactRole.setContactRole(Option("contactRole1"))

    contactRolesList.addOne(contactRole)

    //Set the list to contactRoles in BodyWrapper instance
    bodyWrapper.setData(contactRolesList)

    //Call addContactRoleToDeal method that takes BodyWrapper instance as parameter
    val responseOption = contactRolesOperations.addContactRoleToDeal(contactId, dealId, bodyWrapper)

    if (responseOption.isDefined) {
      var response = responseOption.get
      //Get the status code from response
      System.out.println("Status code" + response.getStatusCode)

      if (response.isExpected) {
        //Get object from response
        val actionHandler = response.getObject
        
        if (actionHandler.isInstanceOf[RecordActionWrapper]) {
          //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[RecordActionWrapper]

          //Get the list of obtained action responses
          val actionResponses = actionWrapper.getData
          
          for (actionResponse <- actionResponses) {
            //Check if the request is successful
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
            //Check if the request returned an exception
            else if(actionResponse.isInstanceOf[APIException]) {
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
        //Check if the request returned an exception
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

  def removeContactRoleFromDeal(contactId: Long, dealId: Long): Unit = {
    //Get instance of ContactRolesOperations Class
    val contactRolesOperations = new ContactRolesOperations()

    //Call removeContactRoleFromDeal method that takes contactId and dealId as parameter
    val responseOption = contactRolesOperations.removeContactRoleFromDeal(contactId, dealId)

    if (responseOption.isDefined) {
      var response = responseOption.get
      //Get the status code from response
      println("Status Code: " + response.getStatusCode)

      if (response.isExpected) {
        //Get object from response
        val actionHandler = response.getObject

        if (actionHandler.isInstanceOf[RecordActionWrapper]) {
          //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[RecordActionWrapper]

          //Get the list of obtained action responses
          val actionResponses = actionWrapper.getData

          for (actionResponse <- actionResponses) {
            //Check if the request is successful
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              //Get the received SuccessResponse instance
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]

              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            //Check if the request returned an exception
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
        //Check if the request returned an exception
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
    }
  }
}

class ContactRoles {}
