package com.zoho.crm.sample.query

import java.lang.reflect.Field
import java.util

import com.zoho.crm.api.query.ResponseHandler
import com.zoho.crm.api.query.ResponseWrapper
import com.zoho.crm.api.record.Info
import com.zoho.crm.api.query.APIException
import com.zoho.crm.api.query.BodyWrapper
import com.zoho.crm.api.query.QueryOperations
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Queries {
  /**
   * <h3> Get Records </h3>
   * This method is used to get records from the module through a COQL query.
   *
   * @throws Exception
   */
  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def getRecords(): Unit = { //Get instance of QueryOperations Class
    val queryOperations = new QueryOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    val selectQuery = "select Last_Name from Leads where Last_Name is not null limit 10"
    bodyWrapper.setSelectQuery(Option(selectQuery))
    //Call getRecords method that takes BodyWrapper instance as parameter
    val responseOption = queryOperations.getRecords(bodyWrapper)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get the object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
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
}

class Queries {}
