package com.zoho.crm.sample.notification

import java.lang.reflect.Field
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.notification.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, Info, Notification, NotificationOperations, ResponseHandler, ResponseWrapper, SuccessResponse}
import com.zoho.crm.api.notification.NotificationOperations.DisableNotificationsParam
import com.zoho.crm.api.notification.NotificationOperations.GetNotificationDetailsParam
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Notifications {
  /**
   * <h3> Enable Notifications </h3>
   * This method is used to Enable Notifications and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def enableNotifications(): Unit = { //Get instance of NotificationOperations Class
    val notificationOperations = new NotificationOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    //List of Notification instances
    val notifications = new ArrayBuffer[Notification]
    //Get instance of Notification Class
    val notification = new Notification
    //Set channel Id of the Notification
    notification.setChannelId(Option(100000006800211l))
    val events = new ArrayBuffer[String]
    events.addOne("Deals.all")
    //To subscribe based on particular operations on given modules.
    notification.setEvents(events)
    //To set the expiry time for instant notifications.
    notification.setChannelExpiry(Option(OffsetDateTime.of(2020, 5, 2, 1, 0, 0, 0, ZoneOffset.of("+05:30"))))
    //To ensure that the notification is sent from Zoho CRM, by sending back the given value in notification URL body.
    //By using this value, user can validate the notifications.
    notification.setToken(Option("TOKEN_FOR_VERIFICATION_OF_1000000068002"))
    //URL to be notified (POST request)
    notification.setNotifyUrl(Option("https://www.zohoapis.com"))
    //Add Notification instance to the list
    notifications.addOne(notification)
    val notification2 = new Notification
    notification2.setChannelId(Option(100000006800211l))
    val events2 = new ArrayBuffer[String]
    events2.addOne("Accounts.all")
    notification2.setEvents(events2)
    notification2.setChannelExpiry(Option(OffsetDateTime.of(2020, 5, 2, 10, 0, 0, 1, ZoneOffset.of("+05:30"))))
    notification2.setToken(Option("TOKEN_FOR_VERIFICATION_OF_1000000068002"))
    notification2.setNotifyUrl(Option("https://www.zohoapis.com"))
    notifications.addOne(notification2)
    //Set the list to notifications in BodyWrapper instance
    bodyWrapper.setWatch(notifications)
    //Call enableNotifications method that takes BodyWrapper instance as parameter
    val responseOption= notificationOperations.enableNotifications(bodyWrapper)
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getWatch

          for (actionResponse <- actionResponses) { //Check if the request is successful
            if (actionResponse.isInstanceOf[SuccessResponse]) { //Get the received SuccessResponse instance
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              //Get the Status
              println("Status: " + successResponse.getStatus.getValue)
              //Get the Code
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")
              //Get the details map

              successResponse.getDetails.foreach(entry=>{

                if (entry._2.isInstanceOf[ArrayBuffer[Any]]) {
                  val dataList = entry._2.asInstanceOf[ArrayBuffer[Any]]
                  if (dataList.size > 0 && dataList(0).isInstanceOf[Notification]) {
                    @SuppressWarnings(Array("unchecked")) val eventList = dataList.asInstanceOf[ArrayBuffer[Notification]]

                    for (event <- eventList) {
                      println("Notification ChannelExpiry: " + event.getChannelExpiry)
                      println("Notification ResourceUri: " + event.getResourceUri)
                      println("Notification ResourceId: " + event.getResourceId)
                      println("Notification ResourceName: " + event.getResourceName)
                      println("Notification ChannelId: " + event.getChannelId)
                    }
                  }
                }
                else println(entry._1 + ": " + entry._2)
              })
              //Get the Message
              println("Message: " + successResponse.getMessage.getValue)
            }
            else { //Check if the request returned an exception
              if (actionResponse.isInstanceOf[APIException]) { //Get the received APIException instance
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
   * <h3> Get Notification Details </h3>
   * This method is used to get all the Notification and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getNotificationDetails(): Unit = {
    val notificationOperations = new NotificationOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new GetNotificationDetailsParam().channelId, 100000006800211l)
    //    paramInstance.add(new GetNotificationDetailsParam().module, "Accounts")
    //    paramInstance.add(new GetNotificationDetailsParam().page, 1)
    //    paramInstance.add(new GetNotificationDetailsParam().perPage, 2)
    //Call getNotificationDetails method
    val responseOption= notificationOperations.getNotificationDetails(Option(paramInstance))
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the list of obtained Notification instances
          val notifications = responseWrapper.getWatch

          for (notification <- notifications) { //Get the NotifyOnRelatedAction of each Notification
            println("Notification NotifyOnRelatedAction: " + notification.getNotifyOnRelatedAction)
            println("Notification ChannelExpiry: " + notification.getChannelExpiry)
            println("Notification ResourceUri: " + notification.getResourceUri)
            println("Notification ResourceId: " + notification.getResourceId)
            //Get the NotifyUrl each Notification
            println("Notification NotifyUrl: " + notification.getNotifyUrl)
            println("Notification ResourceName: " + notification.getResourceName)
            println("Notification ChannelId: " + notification.getChannelId)
            //Get the events List of each Notification
            val fields = notification.getEvents
            //Check if fields is not null
            if (fields != null) {

              for (fieldName <- fields) { //Get the Events
                println("Notification Events: " + fieldName)
              }
            }
            //Get the Token each Notification
            println("Notification Token: " + notification.getToken)
          }
          //Get the Object obtained Info instance
          val infoOption  = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info=infoOption.get
            if (info.getPerPage.isDefined) { //Get the PerPage of the Info
              println("Record Info PerPage: " + info.getPerPage.toString)
            }
            if (info.getCount.isDefined) { //Get the Count of the Info
              println("Record Info Count: " + info.getCount.toString)
            }
            if (info.getPage.isDefined) { //Get the Page of the Info
              println("Record Info Page: " + info.getPage.toString)
            }
            if (info.getMoreRecords.isDefined) { //Get the MoreRecords of the Info
              println("Record Info MoreRecords: " + info.getMoreRecords.toString)
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
      else if (response.getStatusCode != 204) {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          field.setAccessible(true)
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Update Notifications </h3>
   * This method is used to update Notifications and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateNotifications(): Unit = {
    val notificationOperations = new NotificationOperations
    val bodyWrapper = new BodyWrapper
    val notificationList = new ArrayBuffer[Notification]
    val notification = new Notification
    //Set ChannelId to the Notification instance
    notification.setChannelId(Option(100000006800211l))
    val events = new ArrayBuffer[String]
    events.addOne("Accounts.all")
    notification.setEvents(events)
    //Set name to the Notification instance
    //    notification.setChannelExpiry(OffsetDateTime.now)
    notification.setToken(Option("TOKEN_FOR_VERIFICATION_OF_1000000068002"))
    notification.setNotifyUrl(Option("https://www.zohoapis.com"))
    notificationList.addOne(notification)
    //Set the list to notification in BodyWrapper instance
    bodyWrapper.setWatch(notificationList)
    //Call updateNotifications method that takes BodyWrapper instance as parameter
    val responseOption= notificationOperations.updateNotifications(bodyWrapper)
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getWatch

          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")
              successResponse.getDetails.foreach(entry=>{

                if (entry._2.isInstanceOf[ArrayBuffer[Any]]) {
                  val dataList = entry._2.asInstanceOf[ArrayBuffer[Any]]
                  if (dataList.size > 0 && dataList(0).isInstanceOf[Notification]) {
                    @SuppressWarnings(Array("unchecked")) val eventList = dataList.asInstanceOf[ArrayBuffer[Notification]]

                    for (event <- eventList) {
                      println("Notification ChannelExpiry: " + event.getChannelExpiry)
                      println("Notification ResourceUri: " + event.getResourceUri)
                      println("Notification ResourceId: " + event.getResourceId)
                      println("Notification ResourceName: " + event.getResourceName)
                      println("Notification ChannelId: " + event.getChannelId)
                    }
                  }
                }
                else println(entry._1 + ": " + entry._2)
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
   * <h3> Update Specific Information of a Notification </h3>
   * This method is used to update single Notification and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateNotification(): Unit = {
    val notificationOperations = new NotificationOperations
    val bodyWrapper = new BodyWrapper
    val notificationList = new ArrayBuffer[Notification]
    val notification = new Notification
    notification.setChannelId(Option(100000006800211l))
    val events = new ArrayBuffer[String]
    events.addOne("Deals.all")
    notification.setEvents(events)
    //    notification.setChannelExpiry(OffsetDateTime.now)
    notification.setToken(Option("TOKEN_FOR_VERIFICATION_OF_1000000068002"))
    notification.setNotifyUrl(Option("https://www.zohoapis.com"))
    notificationList.addOne(notification)
    bodyWrapper.setWatch(notificationList)
    //Call updateNotification method that takes BodyWrapper instance as parameters
    val responseOption= notificationOperations.updateNotification(bodyWrapper)
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getWatch

          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                if (entry._2.isInstanceOf[ArrayBuffer[Any]]) {
                  val dataList = entry._2.asInstanceOf[ArrayBuffer[Any]]
                  if (dataList.size > 0 && dataList(0).isInstanceOf[Notification]) {
                    @SuppressWarnings(Array("unchecked")) val eventList = dataList.asInstanceOf[ArrayBuffer[Notification]]

                    for (event <- eventList) {
                      println("Notification ChannelExpiry: " + event.getChannelExpiry)
                      println("Notification ResourceUri: " + event.getResourceUri)
                      println("Notification ResourceId: " + event.getResourceId)
                      println("Notification ResourceName: " + event.getResourceName)
                      println("Notification ChannelId: " + event.getChannelId)
                    }
                  }
                }
                else println(entry._1 + ": " + entry._2)
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
   * <h3> Disable Notifications </h3>
   * To stop all the instant notifications enabled by the user for a channel.
   *
   * @param channelIds - Specify the unique IDs of the notification channels to be disabled.
   * @throws Exception
   */
  @throws[Exception]
  def disableNotifications(channelIds: ArrayBuffer[Long]): Unit = { //example
    //ArrayList<Long> channelIds = new ArrayList<Long>(Arrays.asList(3477061000005208001l))
    val notificationOperations = new NotificationOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap

    for (id <- channelIds) {
      paramInstance.add(new DisableNotificationsParam().channelIds, id)
    }
    //Call disableNotifications method that takes paramInstance as parameter
    val responseOption= notificationOperations.disableNotifications(Option(paramInstance))
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getWatch

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
   * <h3> Disable Specific Notifications </h3>
   * This method is used to disable notifications for the specified events in a channel.
   *
   * @throws Exception
   */
  @throws[Exception]
  def disableNotification(): Unit = {
    val notificationOperations = new NotificationOperations
    val bodyWrapper = new BodyWrapper
    val notificationList = new ArrayBuffer[Notification]
    val notification = new Notification
    notification.setChannelId(Option(100000006800211l))
    val events = new ArrayBuffer[String]
    events.addOne("Deals.edit")
    notification.setEvents(events)
    notification.setDeleteevents(Option(true))
    notificationList.addOne(notification)
    bodyWrapper.setWatch(notificationList)
    //Call disableNotification which takes BodyWrapper instance as parameter
    val responseOption= notificationOperations.disableNotification(bodyWrapper)
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getWatch

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
}

class Notifications {}
