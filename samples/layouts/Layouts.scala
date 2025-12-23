package com.zoho.crm.sample.layouts

import java.util
import com.zoho.crm.api.fields.AssociationDetails
import com.zoho.crm.api.fields.AutoNumber
import com.zoho.crm.api.fields.Crypt
import com.zoho.crm.api.fields.Currency
import com.zoho.crm.api.fields.Field
import com.zoho.crm.api.fields.Formula
import com.zoho.crm.api.fields.LookupField
import com.zoho.crm.api.fields.Module
import com.zoho.crm.api.fields.MultiSelectLookup
import com.zoho.crm.api.fields.PickListValue
import com.zoho.crm.api.fields.ToolTip
import com.zoho.crm.api.fields.Unique
import com.zoho.crm.api.fields.ViewType
import com.zoho.crm.api.layouts.APIException
import com.zoho.crm.api.layouts.LayoutsOperations
import com.zoho.crm.api.layouts.Properties
import com.zoho.crm.api.layouts.ResponseHandler
import com.zoho.crm.api.layouts.ResponseWrapper
import com.zoho.crm.api.layouts.Section
import com.zoho.crm.api.profiles.Profile
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object Layouts {
  /**
   * <h3> Get Layouts </h3>
   * This method is used to get metadata about all the layouts of a module and print the response.
   *
   * @param moduleAPIName The API Name of the module to get layouts.
   * @throws Exception
   */
  @throws[Exception]
  def getLayouts(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of LayoutsOperations Class that takes moduleAPIName as parameter
    val layoutsOperations = new LayoutsOperations(Option(moduleAPIName))
    //Call getLayouts method
    val responseOption = layoutsOperations.getLayouts
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
          //Get the list of obtained Layout instances
          val layouts = responseWrapper.getLayouts
          
          for (layout <- layouts) {
            if (layout.getCreatedTime != null) { //Get the CreatedTime of each Layout
              println("Layout CreatedTime: " + layout.getCreatedTime.toString)
            }
            //Check if ConvertMapping is not null
            if (layout.getConvertMapping != null) { //Get the MultiModuleLookup map

              layout.getConvertMapping.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            if (layout.getModifiedTime != null) { //Get the ModifiedTime of each Layout
              println("Layout ModifiedTime: " + layout.getModifiedTime.toString)
            }
            //Get the Visible of each Layout
            println("Layout Visible: " + layout.getVisible.toString)
            //Get the createdFor User instance of each Layout
            val createdForOption = layout.getCreatedFor
            //Check if createdFor is not null
            if (createdForOption.isDefined) { //Get the Name of the createdFor User
              val createdFor=createdForOption.get
              println("Layout CreatedFor User-Name: " + createdFor.getName)
              //Get the ID of the createdFor User
              println("Layout CreatedFor User-ID: " + createdFor.getId)
              //Get the Email of the createdFor User
              println("Layout CreatedFor User-Email: " + createdFor.getEmail)
            }
            //Get the Name of each Layout
            println("Layout Name: " + layout.getName)
            //Get the modifiedBy User instance of each Layout
            val modifiedByOption = layout.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Layout ModifiedBy User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Layout ModifiedBy User-ID: " + modifiedBy.getId)
              //Get the Email of the modifiedBy User
              println("Layout ModifiedBy User-Email: " + modifiedBy.getEmail)
            }
            //Get the profiles of each Layout
            val profiles = layout.getProfiles
            //Check if profiles is not null
            if (profiles != null) {
              
              for (profile <- profiles) { //Get the Default of each Profile
                println("Layout Profile Default: " + profile.getDefault.toString)
                //Get the Name of each Profile
                println("Layout Profile Name: " + profile.getName.toString)
                //Get the ID of each Profile
                println("Layout Profile ID: " + profile.getId.toString)
              }
            }
            //Get the ID of each Layout
            println("Layout ID: " + layout.getId)
            //Get the createdBy User instance of each Layout
            val createdByOption = layout.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Layout CreatedBy User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Layout CreatedBy User-ID: " + createdBy.getId)
              //Get the Email of the createdBy User
              println("Layout CreatedBy User-Email: " + createdBy.getEmail)
            }
            //Get the sections of each Layout
            val sections = layout.getSections
            //Check if sections is not null
            if (sections != null) {
              
              for (section <- sections) { //Get the DisplayLabel of each Section
                println("Layout Section DisplayLabel: " + section.getDisplayLabel)
                //Get the SequenceNumber of each Section
                println("Layout Section SequenceNumber: " + section.getSequenceNumber.toString)
                //Get the Issubformsection of each Section
                println("Layout Section Issubformsection: " + section.getIssubformsection.toString)
                //Get the TabTraversal of each Section
                println("Layout Section TabTraversal: " + section.getTabTraversal.toString)
                //Get the APIName of each Section
                println("Layout Section APIName: " + section.getAPIName)
                //Get the ColumnCount of each Section
                println("Layout Section ColumnCount: " + section.getColumnCount.toString)
                //Get the Name of each Section
                println("Layout Section Name: " + section.getName)
                //Get the GeneratedType of each Section
                println("Layout Section GeneratedType: " + section.getGeneratedType)
                //Get the fields of each Section
                val fields = section.getFields
                if (fields != null) {
                  
                  for (field <- fields) {
                    printField(field)
                  }
                }
                //Get the properties User instance of each Section
                val propertiesOption = section.getProperties
                //Check if properties is not null
                if (propertiesOption.isDefined) { //Get the ReorderRows of each Properties
                  val properties = propertiesOption.get
                  println("Layout Section Properties ReorderRows: " + properties.getReorderRows.toString)
                  //Get the tooltip User instance of the Properties
                  val tooltipOption = properties.getTooltip
                  //Check if tooltip is not null
                  if (tooltipOption.isDefined) { //Get the Name of the ToolTip
                    val tooltip = tooltipOption.get
                    println("Layout Section Properties ToolTip Name: " + tooltip.getName.toString)
                    //Get the Value of the ToolTip
                    println("Layout Section Properties ToolTip Value: " + tooltip.getValue.toString)
                  }
                  //Get the MaximumRows of each Properties
                  println("Layout Section Properties MaximumRows: " + properties.getMaximumRows.toString)
                }
              }
            }
            //Get the Status of each Layout
            println("Layout Status: " + layout.getStatus)
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
   * <h3> Get Layout </h3>
   * This method is used to get metadata about a single layout of a module with layoutID and print the response.
   *
   * @param moduleAPIName The API Name of the layout's module
   * @param layoutId      The ID of the field to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getLayout(moduleAPIName: String, layoutId: Long): Unit = { //Long layoutId = 3477061000000091055l
    val layoutsOperations = new LayoutsOperations(Option(moduleAPIName))
    //Call getLayout method that takes layoutId as parameter
    val responseOption = layoutsOperations.getLayout(layoutId)
    if (responseOption.isDefined) {
      val response = responseOption.get
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
          val layouts = responseWrapper.getLayouts
          
          for (layout <- layouts) {
            if (layout.getCreatedTime != null) println("Layout CreatedTime: " + layout.getCreatedTime.toString)
            if (layout.getConvertMapping != null) {

              layout.getConvertMapping.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            if (layout.getModifiedTime != null) println("Layout ModifiedTime: " + layout.getModifiedTime.toString)
            println("Layout Visible: " + layout.getVisible.toString)
            val createdByOption = layout.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Layout CreatedBy User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Layout CreatedBy User-ID: " + createdBy.getId)
              //Get the Email of the createdBy User
              println("Layout CreatedBy User-Email: " + createdBy.getEmail)
            }
            println("Layout Name: " + layout.getName)
            val modifiedByOption = layout.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Layout ModifiedBy User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Layout ModifiedBy User-ID: " + modifiedBy.getId)
              //Get the Email of the modifiedBy User
              println("Layout ModifiedBy User-Email: " + modifiedBy.getEmail)
            }
            val profiles = layout.getProfiles
            if (profiles != null) {
              
              for (profile <- profiles) {
                println("Layout Profile Default: " + profile.getDefault.toString)
                println("Layout Profile Name: " + profile.getName.toString)
                println("Layout Profile ID: " + profile.getId.toString)
              }
            }
            println("Layout ID: " + layout.getId)

            val sections = layout.getSections
            if (sections != null) {
              
              for (section <- sections) {
                println("Layout Section DisplayLabel: " + section.getDisplayLabel)
                println("Layout Section SequenceNumber: " + section.getSequenceNumber.toString)
                println("Layout Section Issubformsection: " + section.getIssubformsection.toString)
                println("Layout Section TabTraversal: " + section.getTabTraversal.toString)
                println("Layout Section APIName: " + section.getAPIName)
                println("Layout Section ColumnCount: " + section.getColumnCount.toString)
                println("Layout Section Name: " + section.getName)
                println("Layout Section GeneratedType: " + section.getGeneratedType)
                val fields = section.getFields
                if (fields != null) {
                  
                  for (field <- fields) {
                    printField(field)
                  }
                }
                val propertiesOption = section.getProperties
                if (propertiesOption.isDefined) {
                  val properties=propertiesOption.get
                  println("Layout Section Properties ReorderRows: " + properties.getReorderRows.toString)
                  val tooltipOption = properties.getTooltip
                  if (tooltipOption.isDefined) {
                    val tooltip = tooltipOption.get
                    println("Layout Section Properties ToolTip Name: " + tooltip.getName.toString)
                    println("Layout Section Properties ToolTip Value: " + tooltip.getValue.toString)
                  }
                  println("Layout Section Properties MaximumRows: " + properties.getMaximumRows.toString)
                }
              }
            }
            println("Layout Status: " + layout.getStatus)
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

  private def printField(field: Field): Unit = { //Get the SystemMandatory of each Field
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
    if (tooltipOption.isDefined) {
      val tooltip= tooltipOption.get
      println("Field ToolTip Name: " + tooltip.getName)
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
      if (lookupFieldOption.isDefined) { //Get the ID of the LookupField
        val lookupField= lookupFieldOption.get
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
    if (field.getBusinesscardSupported != null) { //Get the BusinesscardSupported of each Field
      println("Field BusinesscardSupported: " + field.getBusinesscardSupported.toString)
    }
    //Check if MultiModuleLookup is not null
    if (field.getMultiModuleLookup != null) {

      field.getMultiModuleLookup.foreach(entry=>{
        println(entry._1 + ": " + entry._2)
      })
    }
    //Get the Object obtained Currency instance
    val currencyOption = field.getCurrency
    //Check if currency is not null
    if (currencyOption.isDefined) { //Get the RoundingOption of the Currency
      val currency= currencyOption.get
      println("Field Currency RoundingOption: " + currency.getRoundingOption)
      if (currency.getPrecision.isDefined) { //Get the Precision of the Currency
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
        val layout = layoutOption.get
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
      if (lookup.getId != null) { //Get the ID of the Module
        println("Field ModuleLookup ID: " + lookup.getId.toString)
      }
    }
    if (field.getVisible != null) { //Get the Visible of each Field
      println("Field Visible: " + field.getVisible.toString)
    }
    if (field.getLength != null) { //Get the Length of each Field
      println("Field Length: " + field.getLength.toString)
    }
    //Get the Object obtained ViewType instance
    val viewTypeOption = field.getViewType
    //Check if viewType is not null
    if (viewTypeOption.isDefined) { //Get the View of the ViewType
      val viewType = viewTypeOption.get
      println("Field ViewType View: " + viewType.getView.toString)
      //Get the Edit of the ViewType
      println("Field ViewType Edit: " + viewType.getEdit.toString)
      //Get the Create of the ViewType
      println("Field ViewType Create: " + viewType.getCreate.toString)
      println("Field ViewType QuickCreate: " + viewType.getQuickCreate.toString)
    }
    val subformOption = field.getSubform
    //Check if subform is not null
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
      if (subform.getId != null) println("Field Subform ID: " + subform.getId.toString)
    }
    //Get the APIName of each Field
    println("Field APIName: " + field.getAPIName.toString)
    //Get the Object obtained Unique instance
    val uniqueOption = field.getUnique
    //Check if unique is not null
    if (uniqueOption.isDefined) { //Get the Casesensitive of the Unique
      val unique = uniqueOption.get
      println("Field Unique Casesensitive : " + unique.getCasesensitive)
    }
    if (field.getHistoryTracking != null) { //Get the HistoryTracking of each Field
      println("Field HistoryTracking: " + field.getHistoryTracking.toString)
    }
    //Get the DataType of each Field
    println("Field DataType: " + field.getDataType.toString)
    //Get the Object obtained Formula instance
    val formulaOption= field.getFormula
    //Check if formula is not null
    if (formulaOption.isDefined) { //Get the ReturnType of the Formula
      val formula = formulaOption.get
      println("Field Formula ReturnType : " + formula.getReturnType)
      //Get the Expression of the Formula
      println("Field Formula Expression : " + formula.getExpression)
    }
    if (field.getDecimalPlace != null) { //Get the DecimalPlace of each Field
      println("Field DecimalPlace: " + field.getDecimalPlace.toString)
    }
    if (field.getMassUpdate != null) { //Get the MassUpdate of each Field
      println("Field MassUpdate: " + field.getMassUpdate.toString)
    }
    if (field.getBlueprintSupported != null) { //Get the BlueprintSupported of each Field
      println("Field BlueprintSupported: " + field.getBlueprintSupported.toString)
    }
    //Get all entries from the MultiSelectLookup instance
    val multiSelectLookupOption = field.getMultiselectlookup
    if (multiSelectLookupOption.isDefined) { //Get the DisplayValue of the MultiSelectLookup
      val multiSelectLookup = multiSelectLookupOption.get
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
        if (pickListValue.getSequenceNumber != null) { //Get the SequenceNumber of each PickListValues
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
      val autoNumber= autoNumberOption.get
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
    if (field.getValidationRule != null) {
      field.getConvertMapping.foreach(entry=>{
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

class Layouts {}
