package com.zoho.crm.sample.blueprint

import java.util

import com.zoho.crm.api.blueprint.{APIException, ActionResponse, BluePrint, BluePrintOperations, BodyWrapper, NextTransition, ProcessInfo, ResponseHandler, ResponseWrapper, SuccessResponse, Transition, ValidationError}
import com.zoho.crm.api.fields.AutoNumber
import com.zoho.crm.api.fields.Field
import com.zoho.crm.api.fields.MultiSelectLookup
import com.zoho.crm.api.fields.PickListValue
import com.zoho.crm.api.fields.ToolTip
import com.zoho.crm.api.fields.ViewType
import com.zoho.crm.api.layouts.Layout
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer


object BluePrints {
  /**
   * <h3> Get Blueprint </h3>
   * This method is used to get a single record's Blueprint details with ID and print the response.
   *
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to get Blueprint
   * @throws Exception
   */
  @throws[Exception]
  def getBlueprint(moduleAPIName: String, recordId: Long): Unit = { //example
    //String moduleAPIName = "Leads"
    //Long recordId = 3477061000004381002l
    //Get instance of BluePrintOperations Class that takes recordId and moduleAPIName as parameter
    val bluePrintOperations = new BluePrintOperations(recordId,moduleAPIName)
    //Call getBlueprint method
    val responseOption = bluePrintOperations.getBlueprint
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
          //Get the obtained BluePrint instance
          val bluePrintOption  = responseWrapper.getBlueprint
          val bluePrint = bluePrintOption.get
          //Get the ProcessInfo instance of the obtained BluePrint
          val processInfoOption= bluePrint.getProcessInfo()
          //Check if ProcessInfo is not null
          if (processInfoOption.isDefined) { //Get the Field ID of the ProcessInfo
            var processInfo = processInfoOption.get
            println("ProcessInfo Field-ID: " + processInfo.getFieldId)
            //Get the isContinuous of the ProcessInfo
            println("ProcessInfo isContinuous: " + processInfo.getIsContinuous)
            //Get the API Name of the ProcessInfo
            println("ProcessInfo API Name: " + processInfo.getAPIName)
            //Get the Continuous of the ProcessInfo
            println("ProcessInfo Continuous: " + processInfo.getContinuous)
            //Get the FieldLabel of the ProcessInfo
            println("ProcessInfo FieldLabel: " + processInfo.getFieldLabel)
            //Get the Name of the ProcessInfo
            println("ProcessInfo Name: " + processInfo.getName)
            //Get the ColumnName of the ProcessInfo
            println("ProcessInfo ColumnName: " + processInfo.getColumnName)
            //Get the FieldValue of the ProcessInfo
            println("ProcessInfo FieldValue: " + processInfo.getFieldValue)
            //Get the ID of the ProcessInfo
            println("ProcessInfo ID: " + processInfo.getId)
            //Get the FieldName of the ProcessInfo
            println("ProcessInfo FieldName: " + processInfo.getFieldName)
          }
          //Get the list of transitions from BluePrint instance
          val transitions = bluePrint.getTransitions
          for (transition <- transitions) {
            val nextTransitions = transition.getNextTransitions
            for (nextTransition <- nextTransitions) { //Get the ID of the NextTransition
              println("NextTransition ID: " + nextTransition.getId)
              //Get the Name of the NextTransition
              println("NextTransition Name: " + nextTransition.getName)
            }
            //Get the PercentPartialSave of each Transition
            println("Transition PercentPartialSave: " + transition.getPercentPartialSave)
            val dataOption = transition.getData
            val data = dataOption.get
            //Get the ID of each record
            println("Record ID: " + data.getId)
            //Get the createdBy User instance of each record
            val createdByOption = dataOption.get.getCreatedBy
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              val createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
            }
            //Check if the created time is not null
            if (data.getCreatedTime.isDefined) { //Get the created time of each record
              println("Record Created Time: " + data.getCreatedTime.toString)
            }
            //Check if the modified time is not null
            if (data.getModifiedTime.isDefined) { //Get the modified time of each record
              println("Record Modified Time: " + data.getModifiedTime.toString)
            }
            //Get the modifiedBy User instance of each record
            val modifiedByOption = data.getModifiedBy
            //Check if modifiedByUser is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              val modifiedBy = modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By user-Name: " + modifiedBy.getName)
            }
            //Get all entries from the keyValues map
            data.getKeyValues().foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            //Get the NextFieldValue of the Transition
            println("Transition NextFieldValue: " + transition.getNextFieldValue)
            //Get the Name of each Transition
            println("Transition Name: " + transition.getName)
            //Get the CriteriaMatched of the Transition
            println("Transition CriteriaMatched: " + transition.getCriteriaMatched.toString)
            //Get the ID of the Transition
            println("Transition ID: " + transition.getId)
            println("Transition Fields: ")
            val fields = transition.getFields
            for (field <- fields) { //Get the webhook of each Field
              println("Transition Fields Webhook: " + field.getWebhook)
              //Get the JsonType of each Field
              println("Transition Fields JsonType: " + field.getJsonType)
              //Get the DisplayLabel of each Field
              println("Transition Fields DisplayLabel: " + field.getDisplayLabel)
              //Get the ValidationRule of each Field
              println("Transition Fields ValidationRule: " + field.getValidationRule)
              //Get the DataType of each Field
              println("Transition Fields DataType: " + field.getDataType)
              //Get the Type of each Field
              println("Transition Fields Type: " + field.getType)
              //Get the ColumnName of each Field
              println("Transition Fields ColumnName: " + field.getColumnName)
              //Get the PersonalityName of each Field
              println("Transition Fields PersonalityName: " + field.getPersonalityName)
              //Get the ID of each Field
              println("Transition Fields ID: " + field.getId)
              //Get the TransitionSequence of each Field
              println("Transition Fields TransitionSequence: " + field.getTransitionSequence.toString)
              if (field.getMandatory.isDefined) { //Get the Mandatory of each Field
                println("Transition Fields Mandatory: " + field.getMandatory.toString)
              }
              val layoutOption = field.getLayouts
              if (layoutOption.isDefined) { //Get the ID of the Layout
                var layout = layoutOption.get
                println("Transition Fields Layout ID: " + layout.getId)
                //Get the name of the Layout
                println("Transition Fields Layout Name: " + layout.getName)
              }
              //Get the APIName of each Field
              println("Transition Fields APIName: " + field.getAPIName)
              //Get the Content of each Field
              println("Transition Fields Content: " + field.getContent)
              if (field.getSystemMandatory.isDefined) { //Get the SystemMandatory of each Field
                println("Transition Fields SystemMandatory: " + field.getSystemMandatory.toString)
              }
              //Get the Crypt of each Field
              println("Transition Fields Crypt: " + field.getCrypt)
              //Get the FieldLabel of each Field
              println("Transition Fields FieldLabel: " + field.getFieldLabel)
              //Get the Tooltip of each Field
              val toolTipOption = field.getTooltip
              if (toolTipOption.isDefined ) { //Get the Tooltip Name
                val toolTip = toolTipOption.get
                println("Transition Fields Tooltip Name: " + toolTip.getName)
                //Get the Tooltip Value
                println("Transition Fields Tooltip Value: " + toolTip.getValue)
              }
              //Get the CreatedSource of each Field
              println("Transition Fields CreatedSource: " + field.getCreatedSource)
              if (field.getFieldReadOnly.isDefined) { //Get the FieldReadOnly of each Field
                println("Transition Fields FieldReadOnly: " + field.getFieldReadOnly.toString)
              }
              if (field.getReadOnly.isDefined) { //Get the ReadOnly of each Field
                println("Transition Fields ReadOnly: " + field.getReadOnly.toString)
              }
              //Get the AssociationDetails of each Field
              println("Transition Fields AssociationDetails: " + field.getAssociationDetails)
              if (field.getQuickSequenceNumber.isDefined) { //Get the QuickSequenceNumber of each Field
                println("Transition Fields QuickSequenceNumber: " + field.getQuickSequenceNumber.toString)
              }
              if (field.getCustomField.isDefined) { //Get the CustomField of each Field
                println("Transition Fields CustomField: " + field.getCustomField.toString)
              }
              if (field.getVisible.isDefined) { //Get the Visible of each Field
                println("Transition Fields Visible: " + field.getVisible.toString)
              }
              if (field.getLength.isDefined) { //Get the Length of each Field
                println("Transition Fields Length: " + field.getLength.toString)
              }
              //Get the DecimalPlace of each Field
              println("Transition Fields DecimalPlace: " + field.getDecimalPlace)
              val viewTypeOption = field.getViewType
              if (viewTypeOption.isDefined) { //Get the View of the ViewType
                val viewType = viewTypeOption.get
                println("Transition Fields ViewType View: " + viewType.getView.toString)
                //Get the Edit of the ViewType
                println("Transition Fields ViewType Edit: " + viewType.getEdit.toString)
                //Get the Create of the ViewType
                println("Transition Fields ViewType Create: " + viewType.getCreate.toString)
                println("Transition Fields ViewType QuickCreate: " + viewType.getQuickCreate.toString)
              }
              val pickListValues = field.getPickListValues
              if (pickListValues != null) {
                for (pickListValue <- pickListValues) { //Get the DisplayValue of each PickListValues
                  println("Transition Fields PickListValue DisplayValue: " + pickListValue.getDisplayValue)
                  //Get the SequenceNumber of each PickListValues
                  println("Transition Fields PickListValue SequenceNumber: " + pickListValue.getSequenceNumber.toString)
                  //Get the ExpectedDataType of each PickListValues
                  println("Transition Fields PickListValue ExpectedDataType: " + pickListValue.getExpectedDataType)
                  //Get the ActualValue of each PickListValues
                  println("Transition Fields PickListValue ActualValue: " + pickListValue.getActualValue)
                  for (map <- pickListValue.getMaps) {
                    println(map)
                  }
                }
              }
              //Get all entries from the MultiSelectLookup instance
              val multiSelectLookupOption = field.getMultiselectlookup
              if (multiSelectLookupOption.isDefined) { //Get the DisplayValue of the MultiSelectLookup
                val multiSelectLookup= multiSelectLookupOption.get
                println("Transition Fields MultiSelectLookup DisplayLabel: " + multiSelectLookup.getDisplayLabel)
                //Get the LinkingModule of the MultiSelectLookup
                println("Transition Fields MultiSelectLookup LinkingModule: " + multiSelectLookup.getLinkingModule)
                //Get the LookupApiname of the MultiSelectLookup
                println("Transition Fields MultiSelectLookup LookupApiname: " + multiSelectLookup.getLookupApiname)
                //Get the APIName of the MultiSelectLookup
                println("Transition Fields MultiSelectLookup APIName: " + multiSelectLookup.getAPIName)
                //Get the ConnectedlookupApiname of the MultiSelectLookup
                println("Transition Fields MultiSelectLookup ConnectedlookupApiname: " + multiSelectLookup.getConnectedlookupApiname)
                //Get the ID of the MultiSelectLookup
                println("Transition Fields MultiSelectLookup ID: " + multiSelectLookup.getId)
              }
              //Get the AutoNumber of each Field
              val autoNumberOption = field.getAutoNumber
              if (autoNumberOption.isDefined) { //Get the Prefix of the AutoNumber
                val autoNumber = autoNumberOption.get
                println("Transition Fields AutoNumber Prefix: " + autoNumber.getPrefix)
                //Get the Suffix of the AutoNumber
                println("Transition Fields AutoNumber Suffix: " + autoNumber.getSuffix)
                if (autoNumber.getStartNumber.isDefined) { //Get the StartNumber of the AutoNumber
                  println("Transition Fields Auto StartNumber: " + autoNumber.getStartNumber.toString)
                }
              }
              //Get the ConvertMapping of each Field
              println("Transition Fields ConvertMapping: ")
              if (field.getConvertMapping != null) { //Get all entries from the ConvertMapping
                field.getConvertMapping.foreach(entry=>{
                  println(entry._1 + ": " + entry._2)
                })
              }
            }
            //Get the CriteriaMessage of each Transition
            println("Transition CriteriaMessage: " + transition.getCriteriaMessage)
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
   * <h3> Update Blueprint </h3>
   * This method is used to update a single record's Blueprint details with ID and print the response.
   *
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to get Blueprint
   * @param transitionId  The ID of the Blueprint transition Id
   * @throws Exception
   */
  @throws[Exception]
  def updateBlueprint(moduleAPIName: String, recordId: Long, transitionId: Long): Unit = { //ID of the BluePrint to be updated
    //Long transitionId = 3477061000000173096l
    //Get instance of BluePrintOperations Class that takes moduleAPIName and recordId as parameter
    val bluePrintOperations = new BluePrintOperations(recordId,moduleAPIName)
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    //List of BluePrint instances
    val bluePrintList = new ArrayBuffer[BluePrint]
    //Get instance of BluePrint Class
    val bluePrint = new BluePrint
    //Set transition_id to the BluePrint instance
    bluePrint.setTransitionId(Option(transitionId))
    //Get instance of Record Class
    val data = new Record
    val lookup:Map[String,Any] = Map()
    lookup.put("Phone", "8940372937")

    lookup.put("id", "8940372937")
    //		data.addKeyValue("Lookup_2", lookup)
    data.addKeyValue("Phone", "8940372937")
    data.addKeyValue("Notes", "Updated via blueprint")
    val attachments:Map[String,Any] =Map()
    val fileIds = new ArrayBuffer[String]
    fileIds.addOne("blojtd2d13b5f044e4041a3315e0793fb21ef")
    attachments.put("$file_id", fileIds)
    data.addKeyValue("Attachments", attachments)
    val checkLists = new ArrayBuffer[Map[String,Any]]
    var checkListItem1:Map[String,Any] = Map()
    checkListItem1.put("list 1", true)
    checkLists.addOne(checkListItem1)

    var checkListItem2:Map[String,Any] = Map()
    checkListItem2.put("list 2", true)
    checkLists.addOne(checkListItem2)
    var checkListItem3:Map[String,Any] = Map()

    checkListItem3.put("list 3", true)
    checkLists.addOne(checkListItem3)
    data.addKeyValue("CheckLists", checkLists)
    //Set data to the BluePrint instance
    bluePrint.setData(Option(data))
    //Add BluePrint instance to the list
    bluePrintList.addOne(bluePrint)
    //Set the list to bluePrint in BodyWrapper instance
    bodyWrapper.setBlueprint(bluePrintList)
    //Call updateBluePrint method that takes BodyWrapper instance
    val responseOption = bluePrintOperations.updateBlueprint(bodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionResponse = response.getObject
        //Check if the request is successful
        if (actionResponse.isInstanceOf[SuccessResponse]) { //Get the received SuccessResponse instance
          val successResponse = actionResponse.asInstanceOf[SuccessResponse]
          println("Status: " + successResponse.getStatus.getValue)
          println("Code: " + successResponse.getCode.getValue)
          println("Details: ")
          if (successResponse.getDetails != null) {
            successResponse.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
          println("Message: " + successResponse.getMessage.getValue)
        }
        else if (actionResponse.isInstanceOf[APIException]) {
          val exception = actionResponse.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")
          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
            if (entry._2.isInstanceOf[ArrayBuffer[Any]]) {
              @SuppressWarnings(Array("unchecked")) val validationError = entry._2.asInstanceOf[ArrayBuffer[ValidationError]]
              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            else println(entry._2.toString)
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

class BluePrints {}
