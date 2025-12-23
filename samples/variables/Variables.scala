package com.zoho.crm.sample.variables

import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.variablegroups.VariableGroup
import com.zoho.crm.api.variables.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, ResponseHandler, ResponseWrapper, SuccessResponse, Variable, VariablesOperations}
import com.zoho.crm.api.variables.VariablesOperations.DeleteVariablesParam
import com.zoho.crm.api.variables.VariablesOperations.GetVariableByIDParam
import com.zoho.crm.api.variables.VariablesOperations.GetVariableForAPINameParam
import com.zoho.crm.api.variables.VariablesOperations.GetVariablesParam

import scala.collection.mutable.ArrayBuffer


object Variables {
  /**
   * <h3> Get Variables </h3>
   * This method is used to retrieve all the available variables through an API request and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getVariables(): Unit = { //Get instance of VariablesOperations Class
    val variablesOperations = new VariablesOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new VariablesOperations.GetVariablesParam().group, "created")
    //Call getVariables method that takes paramInstance as parameter
    val responseOption = variablesOperations.getVariables(Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)//Get the status code from response
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
          //Get the obtained Variable instance
          val variables = responseWrapper.getVariables
          for (variable <- variables) { //Get the APIName of each Variable
            println("Variable APIName: " + variable.getAPIName)
            //Get the Name of each Variable
            println("Variable Name: " + variable.getName)
            //Get the Description of each Variable
            println("Variable Description: " + variable.getDescription)
            //Get the ID of each Variable
            println("Variable ID: " + variable.getId)
            //Get the Type of each Variable
            println("Variable Type: " + variable.getType)
            // Get the VariableGroup instance of each Variable
            val variableGroup = variable.getVariableGroup().get

            //Check if variableGroup is not null
            if (variableGroup != null) { //Get the Name of each VariableGroup
              println("Variable VariableGroup APIName: " + variableGroup.getAPIName)
              //Get the ID of each VariableGroup
              println("Variable VariableGroup ID: " + variableGroup.getId)
            }
            //Get the Value of each Variable
            println("Variable Value: " + variable.getValue)
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
   * <h3> Create Variables </h3>
   * This method is used to create a new variable in CRM and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def createVariables(): Unit = {
    val variablesOperations = new VariablesOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Variable instances
    val variableList = new ArrayBuffer[Variable]
    //Get instance of Variable Class
    var variable1 = new Variable
    variable1.setName(Option("Variabl3asde6665512"))
    variable1.setAPIName(Option("Variaasdb3le5512"))
    var variableGroup = new VariableGroup
    variableGroup.setId(Option(3477061000010321010l))
    variable1.setVariableGroup(Option(variableGroup))
    variable1.setType(Option("integer"))
    variable1.setValue("55")
    variable1.setDescription(Option("This denotes variable 5 description"))
    variableList.addOne(variable1)
    variable1.setName(Option("Variabl3asde6665512"))
    variable1.setAPIName(Option("Variaasdb3le55121"))
    variableGroup = new VariableGroup
    variableGroup.setId(Option(3477061000010321010l))
    variable1.setVariableGroup(Option(variableGroup))
    variable1.setType(Option("integer"))
    variable1.setValue("55")
    variable1.setDescription(Option("This denotes variable 5 description"))
    variableList.addOne(variable1)
    request.setVariables(variableList)
    //Call createVariables method that takes BodyWrapper instance as parameter
    val responseOption = variablesOperations.createVariables(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getVariables
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
   * <h3> Update Variables </h3>
   * This method is used to update details of variables in CRM and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateVariables(): Unit = {
    val variablesOperations = new VariablesOperations
    val request = new BodyWrapper
    val variableList = new ArrayBuffer[Variable]
    var variable1 = new Variable
    variable1.setId(Option(3477061000011665007l))
    variable1.setValue("4763")
    variable1.setDescription(Option("This is a new description"))

    variableList.addOne(variable1)
    variable1 = new Variable
    variable1.setId(Option(3524033000006489003L))
    variable1.setDescription(Option("Thissd is a new description"))
    variableList.addOne(variable1)

    request.setVariables(variableList)
    //Call updateVariables method that takes BodyWrapper class instance as parameter
    val responseOption = variablesOperations.updateVariables(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getVariables
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
   * <h3> Delete Variables </h3>
   * This method is used to delete details of multiple variables in CRM simultaneously and print the response.
   *
   * @param variableIds - The List of the Variable IDs to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteVariables(variableIds: ArrayBuffer[Long]): Unit = { //example
    val variablesOperations = new VariablesOperations
    val paramInstance = new ParameterMap
    for (variableId <- variableIds) {
      paramInstance.add(new DeleteVariablesParam().ids, variableId)
    }
    //Call deleteVariables method that takes BodyWrapper class instance as parameter
    val responseOption = variablesOperations.deleteVariables(Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get

      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getVariables
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
   * <h3> Get Variable By Id </h3>
   * This method is used to get the details of any specific variable.
   * Specify the unique ID of the variable in your API request to get the data for that particular variable group.
   *
   * @param variableId - The ID of the Variable to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getVariableById(variableId: Long): Unit = { //Long variableId = 3477061000003320163l
    val variablesOperations = new VariablesOperations
    val paramInstance = new ParameterMap
    //    paramInstance.add(new GetVariableByIDParam().group, "3477061000003089001") //"General"

    //Call getVariableById method that takes paramInstance and variableId as parameter
    val responseOption = variablesOperations.getVariableById(variableId,Option(paramInstance))
    if (responseOption.isDefined) { //Get the status code from response
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
          val variables = responseWrapper.getVariables
          for (variable <- variables) {
            println("Variable APIName: " + variable.getAPIName)
            println("Variable Name: " + variable.getName)
            println("Variable Description: " + variable.getDescription)
            println("Variable ID: " + variable.getId)
            println("Variable Type: " + variable.getType)
            val variableGroup = variable.getVariableGroup.get
            if (variableGroup != null) {
              println("Variable VariableGroup APIName: " + variableGroup.getAPIName)
              println("Variable VariableGroup ID: " + variableGroup.getId)
            }
            println("Variable Value: " + variable.getValue)
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
   * <h3> Update Variable By Id </h3>
   * This method is used to update details of a specific variable in CRM and print the response.
   *
   * @param variableId - The ID of the Variable to be updated
   * @throws Exception
   */
  @throws[Exception]
  def updateVariableById(variableId: Long): Unit = {
    val variablesOperations = new VariablesOperations
    val request = new BodyWrapper
    val variableList = new ArrayBuffer[Variable]
    val variable1 = new Variable
    variable1.setAPIName(Option("TestAPIName"))
    variableList.addOne(variable1)
    request.setVariables(variableList)
    //Call updateVariableById method that takes BodyWrapper instance and variableId as parameter
    val responseOption = variablesOperations.updateVariableById(variableId,request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getVariables
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
   * <h3> Delete Variable </h3>
   * This method is used to delete details of a specific variable in CRM and print the response.
   *
   * @param variableId - The ID of the Variable to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteVariable(variableId: Long): Unit = {
    val variablesOperations = new VariablesOperations
    //Call deleteVariable method that takes variableId as parameter
    val responseOption = variablesOperations.deleteVariable(variableId)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getVariables
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
   * <h3> Get Variable for API Name </h3>
   * This method is used to get the details of any specific variable.
   * Specify the unique name of the variable in your API request to get the data for that particular variable group.
   *
   * @param variableName - The name of the Variable to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getVariableForAPIName(variableName: String): Unit = { //String variableName = "Variable55"
    val variablesOperations = new VariablesOperations
    val paramInstance = new ParameterMap
    //    paramInstance.add(new GetVariableForAPINameParam().group, "General") //"3477061000003089001"

    //Call getVariableForAPIName method that takes paramInstance and variableName as parameter
    val responseOption = variablesOperations.getVariableForAPIName(variableName,Option(paramInstance))
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
          val variables = responseWrapper.getVariables
          for (variable <- variables) {
            println("Variable APIName: " + variable.getAPIName)
            println("Variable Name: " + variable.getName)
            println("Variable Description: " + variable.getDescription)
            println("Variable ID: " + variable.getId)
            println("Variable Type: " + variable.getType)
            val variableGroup = variable.getVariableGroup.get
            if (variableGroup != null) {
              println("Variable VariableGroup APIName: " + variableGroup.getAPIName)
              println("Variable VariableGroup ID: " + variableGroup.getId)
            }
            println("Variable Value: " + variable.getValue)
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
   * This method is used to update details of a specific variable in CRM and print the response.
   *
   * @param variableName - The name of the Variable to be updated
   * @throws Exception
   */
  @throws[Exception]
  def updateVariableByAPIName(variableName: String): Unit = {
    val variablesOperations = new VariablesOperations
    val request = new BodyWrapper
    val variableList = new ArrayBuffer[Variable]
    val variable1 = new Variable
    variable1.setDescription(Option("Test update Variable By APIName"))
    variableList.addOne(variable1)
    request.setVariables(variableList)
    //Call updateVariableByAPIName method that takes BodyWrapper instance and variableName as parameter
    val responseOption = variablesOperations.updateVariableByAPIName(variableName,request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getVariables
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

class Variables {}
