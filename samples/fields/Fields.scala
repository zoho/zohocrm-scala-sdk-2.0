package com.zoho.crm.sample.fields

import java.util
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.fields.APIException
import com.zoho.crm.api.fields.AssociationDetails
import com.zoho.crm.api.fields.AutoNumber
import com.zoho.crm.api.fields.Crypt
import com.zoho.crm.api.fields.Currency
import com.zoho.crm.api.fields.Field
import com.zoho.crm.api.fields.FieldsOperations
import com.zoho.crm.api.fields.FieldsOperations.GetFieldsParam
import com.zoho.crm.api.fields.Formula
import com.zoho.crm.api.fields.LookupField
import com.zoho.crm.api.fields.Module
import com.zoho.crm.api.fields.MultiSelectLookup
import com.zoho.crm.api.fields.PickListValue
import com.zoho.crm.api.fields.ResponseHandler
import com.zoho.crm.api.fields.ResponseWrapper
import com.zoho.crm.api.fields.ToolTip
import com.zoho.crm.api.fields.Unique
import com.zoho.crm.api.fields.ViewType
import com.zoho.crm.api.layouts.Layout
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object Fields {
  /**
   * <h3> Get Fields </h3>
   * This method is used to get metadata about all the fields of a module and print the response.
   *
   * @throws Exception
   * @param moduleAPIName The API Name of the module to get fields
   */
  @throws[Exception]
  def getFields(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of FieldsOperations Class that takes moduleAPIName as parameter
    val fieldOperations = new FieldsOperations(Option(moduleAPIName))
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
//    paramInstance.add(new GetFieldsParam().type1, "Unused")
    //Call getFields method that takes paramInstance as parameter 
    val responseOption = fieldOperations.getFields(Option(paramInstance))
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
          //Get the list of obtained Field instances
          val fields = responseWrapper.getFields
          
          for (field <- fields) { //Get the SystemMandatory of each Field
            println("Field SystemMandatory: " + field.getSystemMandatory.toString)
            //Get the Webhook of each Field
            println("Field Webhook: " + field.getWebhook.toString)
            //Get the JsonType of each Field
            println("Field JsonType: " + field.getJsonType)
            //Get the Object obtained Crypt instance
            val cryptOption = field.getCrypt
            //Check if crypt is not null
            if (cryptOption.isDefined) { //Get the Mode of the Crypt
              val crypt = cryptOption.get
              println("Field Crypt Mode: " + crypt.getMode)
              //Get the Column of the Crypt
              println("Field Crypt Column: " + crypt.getColumn)
              //Get the Table of the Crypt
              println("Field Crypt Table: " + crypt.getTable)
              //Get the Status of the Crypt
              println("Field Crypt Status: " + crypt.getStatus.toString)
            }
            //Get the FieldLabel of each Field
            println("Field FieldLabel: " + field.getFieldLabel)
            //Get the Object obtained ToolTip instance
            val tooltipOption = field.getTooltip
            //Check if tooltip is not null
            if (tooltipOption.isDefined) { //Get the Name of the ToolTip
              val tooltip = tooltipOption.get
              println("Field ToolTip Name: " + tooltip.getName)
              //Get the Value of the ToolTip
              println("Field ToolTip Value: " + tooltip.getValue)
            }
            //Get the CreatedSource of each Field
            println("Field CreatedSource: " + field.getCreatedSource)
            //Get the FieldReadOnly of each Field
            println("Field FieldReadOnly: " + field.getFieldReadOnly.toString)
            //Get the DisplayLabel of each Field
            println("Field DisplayLabel: " + field.getDisplayLabel)
            //Get the ReadOnly of each Field
            println("Field ReadOnly: " + field.getReadOnly.toString)
            //Get the Object obtained AssociationDetails instance
            val associationDetailsOption = field.getAssociationDetails
            //Check if associationDetails is not null
            if (associationDetailsOption.isDefined) { //Get the Object obtained LookupField instance
              val associationDetails = associationDetailsOption.get
              val lookupFieldOption = associationDetails.getLookupField
              //Check if lookupField is not null
              if (lookupFieldOption.isDefined) { //Get the ID of the LookupField\
                val lookupField =  lookupFieldOption.get
                println("Field AssociationDetails LookupField ID: " + lookupField.getId)
                //Get the Name of the LookupField
                println("Field AssociationDetails LookupField Name: " + lookupField.getName)
              }
              val relatedFieldOption = associationDetails.getRelatedField
              //Check if relatedField is not null
              if (relatedFieldOption.isDefined) { //Get the ID of the relatedField
                val relatedField = relatedFieldOption.get
                println("Field AssociationDetails RelatedField ID: " + relatedField.getId)
                //Get the Name of the relatedField
                println("Field AssociationDetails RelatedField Name: " + relatedField.getName)
              }
            }
            if (field.getQuickSequenceNumber != null) { //Get the QuickSequenceNumber of each Field
              println("Field QuickSequenceNumber: " + field.getQuickSequenceNumber.toString)
            }
            //Get the BusinesscardSupported of each Field
            println("Field BusinesscardSupported: " + field.getBusinesscardSupported.toString)
            //Check if MultiModuleLookup is not null
            if (field.getMultiModuleLookup != null) { //Get the MultiModuleLookup map

              field.getMultiModuleLookup.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            //Get the Object obtained Currency instance
            val currencyOption = field.getCurrency
            //Check if currency is not null
            if (currencyOption.isDefined) { //Get the RoundingOption of the Currency
              val currency = currencyOption.get
              println("Field Currency RoundingOption: " + currency.getRoundingOption)
              if (currency.getPrecision != null) { //Get the Precision of the Currency
                println("Field Currency Precision: " + currency.getPrecision.toString)
              }
            }
            //Get the ID of each Field
            println("Field ID: " + field.getId.toString)
            if (field.getCustomField != null) { //Get the CustomField of each Field
              println("Field CustomField: " + field.getCustomField.toString)
            }
            //Get the Object obtained Module instance
            val lookupOption = field.getLookup
            //Check if lookup is not null
            if (lookupOption.isDefined) { //Get the Object obtained Layout instance
              val lookup = lookupOption.get
              val layoutOption = lookup.getLayout
              //Check if layout is not null
              if (layoutOption.isDefined) { //Get the ID of the Layout
                val layout= layoutOption.get
                println("Field ModuleLookup Layout ID: " + layout.getId.toString)
                //Get the Name of the Layout
                println("Field ModuleLookup Layout Name: " + layout.getName)
              }
              //Get the DisplayLabel of the Module
              println("Field ModuleLookup DisplayLabel: " + lookup.getDisplayLabel)
              //Get the APIName of the Module
              println("Field ModuleLookup APIName: " + lookup.getAPIName)
              //Get the Module of the Module
              println("Field ModuleLookup Module: " + lookup.getModule)
              if (lookup.getId.isDefined) { //Get the ID of the Module
                println("Field ModuleLookup ID: " + lookup.getId.toString)
              }
            }
            if (field.getVisible.isDefined) { //Get the Visible of each Field
              println("Field Visible: " + field.getVisible.toString)
            }
            if (field.getLength.isDefined) { //Get the Length of each Field
              println("Field Length: " + field.getLength.toString)
            }
            //Get the Object obtained ViewType instance
            val viewTypeOption = field.getViewType
            if (viewTypeOption.isDefined) {
              val viewType = viewTypeOption.get
              println("Field ViewType View: " + viewType.getView.toString)
              println("Field ViewType Edit: " + viewType.getEdit.toString)
              println("Field ViewType Create: " + viewType.getCreate.toString)
              println("Field ViewType QuickCreate: " + viewType.getQuickCreate.toString)
            }
            val subformOption = field.getSubform
            if (subformOption.isDefined) {
              val subform = subformOption.get
              val layoutOption = subform.getLayout
              if (layoutOption.isDefined) {
                val layout = layoutOption.get
                println("Field Subform Layout ID: " + layout.getId.toString)
                println("Field Subform Layout Name: " + layout.getName)
              }
              println("Field Subform DisplayLabel: " + subform.getDisplayLabel)
              println("Field Subform APIName: " + subform.getAPIName)
              println("Field Subform Module: " + subform.getModule)
              if (subform.getId.isDefined) println("Field Subform ID: " + subform.getId.toString)
            }
            //Get the APIName of each Field
            println("Field APIName: " + field.getAPIName.toString)
            //Get the Object obtained Unique instance
            val uniqueOption = field.getUnique
            if (uniqueOption.isDefined){
              val unique = uniqueOption.get
              println("Field Unique Casesensitive : " + unique.getCasesensitive)
            }
            if (field.getHistoryTracking.isDefined) { //Get the HistoryTracking of each Field
              println("Field HistoryTracking: " + field.getHistoryTracking.toString)
            }
            //Get the DataType of each Field
            println("Field DataType: " + field.getDataType.toString)
            //Get the Object obtained Formula instance
            val formulaOption = field.getFormula
            //Check if formula is not null
            if (formulaOption.isDefined) { //Get the ReturnType of the Formula
              val formula = formulaOption.get
              println("Field Formula ReturnType : " + formula.getReturnType)
              if (formula.getExpression.isDefined) { //Get the Expression of the Formula
                println("Field Formula Expression : " + formula.getExpression.toString)
              }
            }
            if (field.getDecimalPlace.isDefined) { //Get the DecimalPlace of each Field
              println("Field DecimalPlace: " + field.getDecimalPlace.toString)
            }
            //Get the MassUpdate of each Field
            println("Field MassUpdate: " + field.getMassUpdate.toString)
            if (field.getBlueprintSupported.isDefined) { //Get the BlueprintSupported of each Field
              println("Field BlueprintSupported: " + field.getBlueprintSupported.toString)
            }
            //Get all entries from the MultiSelectLookup instance
            val multiSelectLookupOption = field.getMultiselectlookup
            if (multiSelectLookupOption.isDefined) { //Get the DisplayValue of the MultiSelectLookup
              var multiSelectLookup = multiSelectLookupOption.get
              println("Field MultiSelectLookup DisplayLabel: " + multiSelectLookup.getDisplayLabel)
              //Get the LinkingModule of the MultiSelectLookup
              println("Field MultiSelectLookup LinkingModule: " + multiSelectLookup.getLinkingModule)
              //Get the LookupApiname of the MultiSelectLookup
              println("Field MultiSelectLookup LookupApiname: " + multiSelectLookup.getLookupApiname)
              //Get the APIName of the MultiSelectLookup
              println("Field MultiSelectLookup APIName: " + multiSelectLookup.getAPIName)
              //Get the ConnectedlookupApiname of the MultiSelectLookup
              println("Field MultiSelectLookup ConnectedlookupApiname: " + multiSelectLookup.getConnectedlookupApiname)
              //Get the ID of the MultiSelectLookup
              println("Field MultiSelectLookup ID: " + multiSelectLookup.getId)
            }
            //Get the PickListValue of each Field
            val pickListValues = field.getPickListValues
            if (pickListValues != null) {
              
              for (pickListValue <- pickListValues) { //Get the DisplayValue of each PickListValues
                println("Field PickListValue DisplayValue: " + pickListValue.getDisplayValue)
                if (pickListValue.getSequenceNumber.isDefined) { //Get the SequenceNumber of each PickListValues
                  println(" Field PickListValue SequenceNumber: " + pickListValue.getSequenceNumber.toString)
                }
                //Get the ExpectedDataType of each PickListValues
                println("Field PickListValue ExpectedDataType: " + pickListValue.getExpectedDataType)
                //Get the ActualValue of each PickListValues
                println("Field PickListValue ActualValue: " + pickListValue.getActualValue)
                if (pickListValue.getMaps != null) {
                  
                  for (map <- pickListValue.getMaps) { //Get each value from the map
                    println(map)
                  }
                }
                //Get the SysRefName of each PickListValues
                println("Field PickListValue SysRefName: " + pickListValue.getSysRefName)
                //Get the Type of each PickListValues
                println("Field PickListValue Type: " + pickListValue.getType)
              }
            }
            //Get the AutoNumber of each Field
            val autoNumberOption = field.getAutoNumber
            //Check if autoNumber is not null
            if (autoNumberOption.isDefined) { //Get the Prefix of the AutoNumber
              var autoNumber = autoNumberOption.get
              println("Field AutoNumber Prefix: " + autoNumber.getPrefix)
              //Get the Suffix of the AutoNumber
              println("Field AutoNumber Suffix: " + autoNumber.getSuffix)
              if (autoNumber.getStartNumber.isDefined) { //Get the StartNumber of the AutoNumber
                println("Field AutoNumber StartNumber: " + autoNumber.getStartNumber.toString)
              }
            }
            if (field.getDefaultValue.isDefined) { //Get the DefaultValue of each Field
              println("Field DefaultValue: " + field.getDefaultValue.toString)
            }
            if (field.getSectionId.isDefined) { //Get the SectionId of each Field
              println("Field SectionId: " + field.getSectionId.toString)
            }
            //Check if ValidationRule is not null
            if (field.getValidationRule != null) { //Get the details map

              field.getValidationRule.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            //Check if ConvertMapping is not null
            if (field.getConvertMapping != null) {

              field.getConvertMapping.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
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
   * <h3> Get Field </h3>
   * This method is used to get metadata about a single field of a module with fieldID and print the response.
   *
   * @param moduleAPIName The API Name of the field's module
   * @param fieldId       The ID of the field to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getField(moduleAPIName: String, fieldId: Long): Unit = { //example,
    //Long fieldId = 525508000005067912l
    val fieldOperations = new FieldsOperations(Option(moduleAPIName))
    //Call getField method which takes fieldId as parameter
    val responseOption = fieldOperations.getField(fieldId)
    if (responseOption.isDefined) {
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
          val fields = responseWrapper.getFields
          
          for (field <- fields) {
            println("Field SystemMandatory: " + field.getSystemMandatory.toString)
            println("Field Webhook: " + field.getWebhook.toString)
            println("Field JsonType: " + field.getJsonType)
            val cryptOption = field.getCrypt
            if (cryptOption.isDefined) {
              val crypt=cryptOption.get
              println("Field Crypt Mode: " + crypt.getMode)
              println("Field Crypt Column: " + crypt.getColumn)
              println("Field Crypt Table: " + crypt.getTable)
              println("Field Crypt Status: " + crypt.getStatus.toString)
            }
            println("Field FieldLabel: " + field.getFieldLabel)
            val tooltipOption  = field.getTooltip
            if (tooltipOption.isDefined) {
              val tooltip= tooltipOption.get
              println("Field ToolTip Name: " + tooltip.getName)
              println("Field ToolTip Value: " + tooltip.getValue)
            }
            println("Field CreatedSource: " + field.getCreatedSource)
            println("Field FieldReadOnly: " + field.getFieldReadOnly.toString)
            println("Field DisplayLabel: " + field.getDisplayLabel)
            println("Field ReadOnly: " + field.getReadOnly.toString)
            val associationDetailsOption = field.getAssociationDetails
            if (associationDetailsOption.isDefined) {
              val associationDetails = associationDetailsOption.get
              val lookupFieldOption = associationDetails.getLookupField
              if (lookupFieldOption.isDefined) {
                val lookupField = lookupFieldOption.get
                println("Field AssociationDetails LookupField ID: " + lookupField.getId)
                println("Field AssociationDetails LookupField Name: " + lookupField.getName)
              }
              val relatedFieldOption= associationDetails.getRelatedField
              if (relatedFieldOption.isDefined) {
                val relatedField= relatedFieldOption.get
                println("Field AssociationDetails RelatedField ID: " + relatedField.getId)
                println("Field AssociationDetails RelatedField Name: " + relatedField.getName)
              }
            }
            if (field.getQuickSequenceNumber != null) println("Field QuickSequenceNumber: " + field.getQuickSequenceNumber.toString)
            println("Field BusinesscardSupported: " + field.getBusinesscardSupported.toString)
            if (field.getMultiModuleLookup != null) {

              field.getMultiModuleLookup.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            val currencyOption = field.getCurrency
            if (currencyOption.isDefined) {
              val currency = currencyOption.get
              println("Field Currency RoundingOption: " + currency.getRoundingOption)
              if (currency.getPrecision.isDefined) println("Field Currency Precision: " + currency.getPrecision.toString)
            }
            println("Field ID: " + field.getId.toString)
            if (field.getCustomField.isDefined) println("Field CustomField: " + field.getCustomField.toString)
            val lookupOption = field.getLookup
            if (lookupOption.isDefined) {
              val lookup= lookupOption.get
              val layoutOption = lookup.getLayout
              if (layoutOption.isDefined) {
                val layout = layoutOption.get
                println("Field ModuleLookup Layout ID: " + layout.getId.toString)
                println("Field ModuleLookup Layout Name: " + layout.getName)
              }
              println("Field ModuleLookup DisplayLabel: " + lookup.getDisplayLabel)
              println("Field ModuleLookup APIName: " + lookup.getAPIName)
              println("Field ModuleLookup Module: " + lookup.getModule)
              if (lookup.getId.isDefined) println("Field ModuleLookup ID: " + lookup.getId.toString)
            }
            if (field.getVisible.isDefined) println("Field Visible: " + field.getVisible.toString)
            if (field.getLength.isDefined) println("Field Length: " + field.getLength.toString)
            val viewTypeOption = field.getViewType
            if (viewTypeOption.isDefined) {
              val viewType = viewTypeOption.get
              println("Field ViewType View: " + viewType.getView.toString)
              println("Field ViewType Edit: " + viewType.getEdit.toString)
              println("Field ViewType Create: " + viewType.getCreate.toString)
              println("Field ViewType QuickCreate: " + viewType.getQuickCreate.toString)
            }
            val subformOption = field.getSubform
            if (subformOption.isDefined) {
              val subform = subformOption.get
              val layoutOption = subform.getLayout
              if (layoutOption.isDefined) {
                val layout = layoutOption.get
                println("Field Subform Layout ID: " + layout.getId.toString)
                println("Field Subform Layout Name: " + layout.getName)
              }
              println("Field Subform DisplayLabel: " + subform.getDisplayLabel)
              println("Field Subform APIName: " + subform.getAPIName)
              println("Field Subform Module: " + subform.getModule)
              if (subform.getId.isDefined) println("Field Subform ID: " + subform.getId.toString)
            }
            println("Field APIName: " + field.getAPIName.toString)
            val uniqueOption = field.getUnique
            if (uniqueOption.isDefined){
              val unique = uniqueOption.get
              println("Field Unique Casesensitive : " + unique.getCasesensitive)
            }
            if (field.getHistoryTracking.isDefined) println("Field HistoryTracking: " + field.getHistoryTracking.toString)
            println("Field DataType: " + field.getDataType.toString)
            val formulaOption = field.getFormula
            if (formulaOption.isDefined) {
              val formula = formulaOption.get
              println("Field Formula ReturnType : " + formula.getReturnType)
              if (formula.getExpression.isDefined) println("Field Formula Expression : " + formula.getExpression.toString)
            }
            if (field.getDecimalPlace.isDefined) println("Field DecimalPlace: " + field.getDecimalPlace.toString)
            println("Field MassUpdate: " + field.getMassUpdate.toString)
            if (field.getBlueprintSupported.isDefined) println("Field BlueprintSupported: " + field.getBlueprintSupported.toString)
            val multiSelectLookupOption = field.getMultiselectlookup
            if (multiSelectLookupOption.isDefined) {
              var multiSelectLookup = multiSelectLookupOption.get
              println("Field MultiSelectLookup DisplayLabel: " + multiSelectLookup.getDisplayLabel)
              println("Field MultiSelectLookup LinkingModule: " + multiSelectLookup.getLinkingModule)
              println("Field MultiSelectLookup LookupApiname: " + multiSelectLookup.getLookupApiname)
              println("Field MultiSelectLookup APIName: " + multiSelectLookup.getAPIName)
              println("Field MultiSelectLookup ConnectedlookupApiname: " + multiSelectLookup.getConnectedlookupApiname)
              println("Field MultiSelectLookup ID: " + multiSelectLookup.getId)
            }
            val pickListValues = field.getPickListValues
            if (pickListValues != null) {
              
              for (pickListValue <- pickListValues) {
                println("Field PickListValue DisplayValue: " + pickListValue.getDisplayValue)
                if (pickListValue.getSequenceNumber.isDefined) println(" Field PickListValue SequenceNumber: " + pickListValue.getSequenceNumber.toString)
                if (pickListValue.getExpectedDataType.isDefined) println("Field PickListValue ExpectedDataType: " + pickListValue.getExpectedDataType)
                println("Field PickListValue ActualValue: " + pickListValue.getActualValue)
                if (pickListValue.getMaps != null) {
                  
                  for (map <- pickListValue.getMaps) {
                    println(map)
                  }
                }
                println("Field PickListValue SysRefName: " + pickListValue.getSysRefName)
                println("Field PickListValue Type: " + pickListValue.getType)
              }
            }
            val autoNumberOption = field.getAutoNumber
            if (autoNumberOption.isDefined) {
              val autoNumber = autoNumberOption.get
              println("Field AutoNumber Prefix: " + autoNumber.getPrefix)
              println("Field AutoNumber Suffix: " + autoNumber.getSuffix)
              if (autoNumber.getStartNumber.isDefined) println("Field AutoNumber StartNumber: " + autoNumber.getStartNumber.toString)
            }
            if (field.getDefaultValue.isDefined) println("Field DefaultValue: " + field.getDefaultValue.toString)
            if (field.getSectionId.isDefined) println("Field SectionId: " + field.getSectionId.toString)
            if (field.getValidationRule != null) {

              field.getValidationRule.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            if (field.getConvertMapping != null) {

              field.getConvertMapping.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
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

class Fields {}
