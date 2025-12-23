package com.zoho.crm.sample.relatedlist

import java.util
import com.zoho.crm.api.relatedlists.APIException
import com.zoho.crm.api.relatedlists.RelatedListsOperations
import com.zoho.crm.api.relatedlists.ResponseHandler
import com.zoho.crm.api.relatedlists.ResponseWrapper
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object RelatedLists {
  /**
   * <h3> Get RelatedLists </h3>
   * This method is used to get the related list data of a particular module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to get related lists
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedLists(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of RelatedListsOperations Class that takes moduleAPIName as parameter
    val relatedListsOperations = new RelatedListsOperations(Option(moduleAPIName))
    //Call getRelatedLists method
    val responseOption = relatedListsOperations.getRelatedLists
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
          //Get the list of obtained RelatedList instances
          val relatedLists = responseWrapper.getRelatedLists
          for (relatedList <- relatedLists) { //Get the SequenceNumber of each RelatedList
            println("RelatedList SequenceNumber: " + relatedList.getSequenceNumber)
            //Get the DisplayLabel of each RelatedList
            println("RelatedList DisplayLabel: " + relatedList.getDisplayLabel)
            //Get the APIName of each RelatedList
            println("RelatedList APIName: " + relatedList.getAPIName)
            //Get the Module of each RelatedList
            println("RelatedList Module: " + relatedList.getModule)
            //Get the Name of each RelatedList
            println("RelatedList Name: " + relatedList.getName)
            //Get the Action of each RelatedList
            println("RelatedList Action: " + relatedList.getAction)
            //Get the ID of each RelatedList
            println("RelatedList ID: " + relatedList.getId)
            //Get the Href of each RelatedList
            println("RelatedList Href: " + relatedList.getHref)
            //Get the Type of each RelatedList
            println("RelatedList Type: " + relatedList.getType)
            //Get the Connected Module of each RelatedList
            println("RelatedList Connectedmodule: " + relatedList.getConnectedmodule)
            //Get the Linking Module of each RelatedList
            println("RelatedList Linkingmodule: " + relatedList.getLinkingmodule)
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
   * <h3> Get RelatedList </h3>
   * This method is used to get the single related list data of a particular module with relatedListId and print the response.
   *
   * @param moduleAPIName - The API Name of the module to get related list
   * @param relatedListId - The ID of the relatedList to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedList(moduleAPIName: String, relatedListId: Long): Unit = { //example,
    //Long relatedListId = 525508000005067912l
    val relatedListsOperations = new RelatedListsOperations(Option(moduleAPIName))
    //Call getRelatedList method which takes relatedListId as parameter
    val responseOption = relatedListsOperations.getRelatedList(relatedListId)
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
          //Get the list of obtained CustomView instances
          val relatedLists = responseWrapper.getRelatedLists
          for (relatedList <- relatedLists) {
            println("RelatedList SequenceNumber: " + relatedList.getSequenceNumber)
            println("RelatedList DisplayLabel: " + relatedList.getDisplayLabel)
            println("RelatedList APIName: " + relatedList.getAPIName)
            println("RelatedList Module: " + relatedList.getModule)
            println("RelatedList Name: " + relatedList.getName)
            println("RelatedList Action: " + relatedList.getAction)
            println("RelatedList ID: " + relatedList.getId)
            println("RelatedList Href: " + relatedList.getHref)
            println("RelatedList Type: " + relatedList.getType)
            println("RelatedList Connectedmodule: " + relatedList.getConnectedmodule)
            println("RelatedList Linkingmodule: " + relatedList.getLinkingmodule)
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

class RelatedLists {}