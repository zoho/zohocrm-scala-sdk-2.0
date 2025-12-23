package com.zoho.crm.sample.taxes

import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.taxes.TaxesOperations.DeleteTaxesParam
import com.zoho.crm.api.taxes.{APIException, ActionWrapper, BodyWrapper, ResponseWrapper, SuccessResponse, Tax, TaxesOperations}

import scala.collection.mutable.ArrayBuffer


object Taxes {
  /**
   * <h3> Get Taxes </h3>
   * This method is used to get all the Organization Taxes and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getTaxes(): Unit = { //Get instance of TaxesOperations Class
    val taxesOperations = new TaxesOperations
    //Call getTaxes method
    val responseOption = taxesOperations.getTaxes
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
          //Get the list of obtained Tax instances
          val taxes = responseWrapper.getTaxes
          for (tax <- taxes) { //Get the DisplayLabel of each Tax
            println("Tax DisplayLabel: " + tax.getDisplayLabel)
            //Get the Name of each Tax
            println("Tax Name: " + tax.getName)
            //Get the ID of each Tax
            println("Tax ID: " + tax.getId)
            //Get the Value of each Tax
            println("Tax Value: " + tax.getValue.toString)
          }
          //Get the Preference instance of Tag
          val preferenceOption = responseWrapper.getPreference
          //Check if preference is not null
          if (preferenceOption.isDefined) { //Get the AutoPopulateTax of each Preference
            var preference = preferenceOption.get
            println("Preference AutoPopulateTax: " + preference.getAutoPopulateTax.toString)
            //Get the ModifyTaxRates of each Preference
            println("Preference ModifyTaxRates: " + preference.getModifyTaxRates.toString)
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
   * <h3> Create Taxes </h3>
   * This method is used to create Organization Taxes and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def createTaxes(): Unit = {
    val taxesOperations = new TaxesOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Tax instances
    val taxList = new ArrayBuffer[Tax]
    //Get instance of Tax Class
    var tax1 = new Tax
    tax1.setName(Option("MyTsdax1122"))
    tax1.setSequenceNumber(Option(2))
    tax1.setValue(Option(10.0))
    taxList.addOne(tax1)
    tax1 = new Tax
    tax1.setName(Option("MyTsax2122"))
    tax1.setValue(Option(12.0))
    taxList.addOne(tax1)
    request.setTaxes(taxList)
    //Call createTaxes method that takes BodyWrapper class instance as parameter
    val responseOption = taxesOperations.createTaxes(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getTaxes
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
   * <h3> Update Taxes </h3>
   * This method is used to update Organization Taxes and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateTaxes(): Unit = {
    val taxesOperations = new TaxesOperations
    val request = new BodyWrapper
    val taxList = new ArrayBuffer[Tax]
    var tax1 = new Tax
    tax1.setId(Option(3477061000011673002l))
    tax1.setName(Option("MyT2ax1134"))
    tax1.setSequenceNumber(Option(1))
    tax1.setValue(Option(15.0))
    taxList.addOne(tax1)
    tax1 = new Tax
    tax1.setId(Option(3524033000005022001l))
    tax1.setValue(Option(25.0))
    taxList.addOne(tax1)
    tax1 = new Tax
    tax1.setId(Option(3524033000005711001l))
    tax1.setSequenceNumber(Option(2))
    taxList.addOne(tax1)
    request.setTaxes(taxList)
    //Call updateTaxes method that takes BodyWrapper instance as parameter
    val responseOption = taxesOperations.updateTaxes(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTaxes
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
   * <h3> Delete Taxes </h3>
   * This method is used to delete Organization Taxes and print the response.
   *
   * @param taxIds - The List of the tax IDs to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteTaxes(taxIds: ArrayBuffer[Long]): Unit = { //example
    //ArrayList<Long> taxIds = new ArrayList<Long>(Arrays.asList(3477061000006198032l,3477061000005682038l))
    val taxesOperations = new TaxesOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    for (taxId <- taxIds) {
      paramInstance.add(new
          DeleteTaxesParam().ids, taxId)
    }
    //Call deleteTaxes method that takes paramInstance as parameter
    val responseOption = taxesOperations.deleteTaxes(Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTaxes
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
   * <h3> Get Tax </h3>
   * This method is used to get the Organization Tax and print the response.
   *
   * @param taxId - The ID of the tax to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getTax(taxId: Long): Unit = { //Long taxId = 3477061000006054012l
    val taxesOperations = new TaxesOperations
    //Call getTax method that takes taxId as parameter
    val responseOption = taxesOperations.getTax(taxId)
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
          val taxes = responseWrapper.getTaxes
          for (tax <- taxes) {
            println("Tax DisplayLabel: " + tax.getDisplayLabel)
            println("Tax Name: " + tax.getName)
            println("Tax ID: " + tax.getId)
            println("Tax Value: " + tax.getValue.toString)
          }
          val preferenceOption = responseWrapper.getPreference
          if (preferenceOption.isDefined) {
            val preference=preferenceOption.get
            println("Preference AutoPopulateTax: " + preference.getAutoPopulateTax.toString)
            println("Preference ModifyTaxRates: " + preference.getModifyTaxRates.toString)
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
   * <h3> Delete Tax </h3>
   * This method is used to delete Organization Tax and print the response.
   *
   * @param taxId - The ID of the tax to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteTax(taxId: Long): Unit = {
    val taxesOperations = new TaxesOperations
    //Call deleteTax method that takes taxId as parameter
    val responseOption = taxesOperations.deleteTax(taxId)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTaxes
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

class Taxes {}