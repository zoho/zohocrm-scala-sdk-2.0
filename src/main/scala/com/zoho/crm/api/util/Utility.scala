package com.zoho.crm.api.util

import java.io.{File, FileWriter, IOException, UnsupportedEncodingException}
import java.time.{Instant, OffsetDateTime, ZoneId}
import java.util
import java.util.logging.{Level, Logger}

import _root_.org.json.{JSONArray, JSONException, JSONObject}
import com.zoho.api.logger.SDKLogger
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.fields.{APIException, Field, FieldsOperations, ResponseWrapper}
import com.zoho.crm.api.modules.ModulesOperations
import com.zoho.crm.api.modules.ModulesOperations.GetModulesHeader
import com.zoho.crm.api.relatedlists.RelatedListsOperations
import com.zoho.crm.api.{HeaderMap, Initializer, modules, relatedlists}
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

/**
 * This class handles module field details.
 */
object  Utility {

  private val apiTypeVsdataType:mutable.HashMap[String,String] = mutable.HashMap()
  private val apiTypeVsStructureName:mutable.HashMap[String,String] = mutable.HashMap()
  private val LOGGER = Logger.getLogger(classOf[SDKLogger].getName)
  private var newFile:Boolean = false
  private var getModifiedModules:Boolean = false
  private var moduleAPIName:String = ""
  private var forceRefresh:Boolean = false
  private val JSONDETAILS = Initializer.jsonDetails
  var apiSupportedModule = new mutable.HashMap[String,String]()

  def verifyPhotoSupport(moduleAPIName: String): Unit =synchronized {
    return
  }

  def getFields(moduleAPIName: String): Unit =synchronized {
    Utility.moduleAPIName = moduleAPIName
    getFieldsInfo(Utility.moduleAPIName)
  }
  /**
   * This method to fetch field details of the current module for the current user and store the result in a JSON file.
   *
   * @param moduleAPIName A String containing the CRM module API name.
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def getFieldsInfo(moduleAPIName: String): Unit =synchronized {

    var recordFieldDetailsPath:String = null
    var lastModifiedTime:String = null

    try {
      if (moduleAPIName != null && searchJSONDetails(moduleAPIName).isDefined) return
      val resourcesPath = new File(Initializer.getInitializer.getResourcePath + File.separator + Constants.FIELD_DETAILS_DIRECTORY)
      if (!resourcesPath.exists) resourcesPath.mkdirs
      recordFieldDetailsPath = getFileName
      val recordFieldDetails = new File(recordFieldDetailsPath)
      if (recordFieldDetails.exists) {
        var recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
        if (Initializer.getInitializer.getSDKConfig.getAutoRefreshFields && !newFile && !getModifiedModules && (recordFieldDetailsJson.optString(Constants.FIELDS_LAST_MODIFIED_TIME).isEmpty || forceRefresh || (System.currentTimeMillis - recordFieldDetailsJson.getString(Constants.FIELDS_LAST_MODIFIED_TIME).toLong) > 3600000)) {
          getModifiedModules = true
          if (recordFieldDetailsJson.has(Constants.FIELDS_LAST_MODIFIED_TIME)) lastModifiedTime =  recordFieldDetailsJson.getString(Constants.FIELDS_LAST_MODIFIED_TIME)
          modifyFields(recordFieldDetailsPath, lastModifiedTime)
          getModifiedModules = false
        }
        else if (!Initializer.getInitializer.getSDKConfig.getAutoRefreshFields && forceRefresh && !getModifiedModules) {
          getModifiedModules = true
          modifyFields(recordFieldDetailsPath, lastModifiedTime)
          getModifiedModules = false
        }
        recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
        if (moduleAPIName==null || recordFieldDetailsJson.has(moduleAPIName.toLowerCase)) return
        else {
          fillDatatype()
          recordFieldDetailsJson.put(moduleAPIName.toLowerCase, new JSONObject)
          var file = new FileWriter(recordFieldDetailsPath)
          file.flush()
          file.write(recordFieldDetailsJson.toString) // write existing data + dummy

          file.flush()
          file.close()
          val fieldDetails = getFieldsDetails(moduleAPIName)
          recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
          recordFieldDetailsJson.put(moduleAPIName.toLowerCase, fieldDetails)
          file = new FileWriter(recordFieldDetailsPath)
          file.flush()
          file.write(recordFieldDetailsJson.toString) // overwrting the dummy +existing data

          file.flush()
          file.close()
        }
      }
      else if (Initializer.getInitializer.getSDKConfig.getAutoRefreshFields) {
        newFile = true
        fillDatatype()

        apiSupportedModule = if (apiSupportedModule.nonEmpty) apiSupportedModule
        else getModules(null)

        var recordFieldDetailsJson = new JSONObject
        recordFieldDetailsJson.put(Constants.FIELDS_LAST_MODIFIED_TIME, String.valueOf(System.currentTimeMillis))

        for ( module <- apiSupportedModule.keySet ) {
          if (!recordFieldDetailsJson.has(module.toLowerCase)) {
            recordFieldDetailsJson.put(module.toLowerCase, new JSONObject)
            var file = new FileWriter(recordFieldDetailsPath)
            file.write(recordFieldDetailsJson.toString)
            file.flush()
            file.close() // file created with only dummy

            val fieldDetails = getFieldsDetails(module)
            recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
            recordFieldDetailsJson.put(module.toLowerCase, fieldDetails)
            file = new FileWriter(recordFieldDetailsPath)
            file.flush()
            file.write(recordFieldDetailsJson.toString)
            file.flush()
            file.close()
          }
        }

        newFile = false
      }
      else if (forceRefresh && !getModifiedModules) { //New file - and force refresh by User
        getModifiedModules = true
        val recordFieldDetailsJson = new JSONObject
        val file = new FileWriter(recordFieldDetailsPath)
        file.write(recordFieldDetailsJson.toString)
        file.flush()
        file.close()
        modifyFields(recordFieldDetailsPath, lastModifiedTime)
        getModifiedModules = false
      }
      else {
        fillDatatype()
        var recordFieldDetailsJson = new JSONObject
        recordFieldDetailsJson.put(moduleAPIName.toLowerCase, new JSONObject)
        var file = new FileWriter(recordFieldDetailsPath)
        file.write(recordFieldDetailsJson.toString)
        file.flush()
        file.close()
        val fieldDetails = getFieldsDetails(moduleAPIName)
        recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
        recordFieldDetailsJson.put(moduleAPIName.toLowerCase, fieldDetails)
        file = new FileWriter(recordFieldDetailsPath)
        file.flush()
        file.write(recordFieldDetailsJson.toString)
        file.flush()
        file.close()
      }
    } catch {
      case e@(_: IOException | _:  JSONException | _: SDKException) =>
        if (recordFieldDetailsPath==null && new File(recordFieldDetailsPath).exists) {
          try {
            val recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
            if (recordFieldDetailsJson.has(moduleAPIName.toLowerCase)) recordFieldDetailsJson.remove(moduleAPIName.toLowerCase)
            if (newFile) {
              if (recordFieldDetailsJson.get(Constants.FIELDS_LAST_MODIFIED_TIME) != null) recordFieldDetailsJson.remove(Constants.FIELDS_LAST_MODIFIED_TIME)
              newFile = false
            }
            if (getModifiedModules || forceRefresh) {
              getModifiedModules = false
              forceRefresh = false
              if (lastModifiedTime==null) recordFieldDetailsJson.put(Constants.FIELDS_LAST_MODIFIED_TIME, lastModifiedTime)
            }
            val file = new FileWriter(recordFieldDetailsPath)
            file.flush()
            file.write(recordFieldDetailsJson.toString)
            file.flush()
            file.close()
          } catch {
            case ex: IOException =>
              val exception = new SDKException(Constants.EXCEPTION, ex)
              LOGGER.log(Level.SEVERE, Constants.EXCEPTION, exception)
              throw exception
          }
        }
        var exception:SDKException = new SDKException(Constants.EXCEPTION, e.asInstanceOf[Exception])
        e match {
          case kException: SDKException =>
            exception = kException
          case _ =>
        }

        LOGGER.log(Level.SEVERE, Constants.EXCEPTION, exception)
        throw exception
    }
  }

  @throws[IOException]
  @throws[SDKException]
  private def modifyFields(recordFieldDetailsPath: String, modifiedTime: String): Unit = {
    val modifiedModules:mutable.HashMap[String,String] = getModules(modifiedTime)
    val recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
    recordFieldDetailsJson.put(Constants.FIELDS_LAST_MODIFIED_TIME, String.valueOf(System.currentTimeMillis))
    var file = new FileWriter(recordFieldDetailsPath)
    file.flush()
    file.write(recordFieldDetailsJson.toString)
    file.flush()
    file.close()
    if (modifiedModules.nonEmpty) {
      for ( module <- modifiedModules.keySet ) {
        if (recordFieldDetailsJson.has(module)) deleteFields(recordFieldDetailsJson, module)
      }
      file = new FileWriter(recordFieldDetailsPath)
      file.flush()
      file.write(recordFieldDetailsJson.toString)
      file.flush()
      file.close()
      for ( module <- modifiedModules.keySet ) {
        getFieldsInfo(module)
      }
    }
  }

  def deleteFields(recordFieldDetailsJson: JSONObject, module: String): Unit = {
    val subformModules = new ArrayBuffer[String]
    val fieldsJSON = recordFieldDetailsJson.getJSONObject(module.toLowerCase)
    fieldsJSON.keySet.forEach((key: String) => {
      def foo(key: String) = if (fieldsJSON.getJSONObject(key).has(Constants.SUBFORM) && fieldsJSON.getJSONObject(key).getBoolean(Constants.SUBFORM)) subformModules.addOne(fieldsJSON.getJSONObject(key).getString(Constants.MODULE))

      foo(key)
    })
    recordFieldDetailsJson.remove(module.toLowerCase)
    if (subformModules.nonEmpty) {
      for ( subformModule <- subformModules ) {
        deleteFields(recordFieldDetailsJson, subformModule)
      }
    }
  }

  @throws[UnsupportedEncodingException]
  private def getFileName = {
    val converterInstance =new Converter() {

      override def getResponse(response: Any, pack: String): Any = None

      override def formRequest(requestObject: Any, pack: String, instanceNumber: Integer, memberDetails: JSONObject): Any = None

      override def appendToRequest(requestBase: HttpEntityEnclosingRequestBase, requestObject: Any): Unit = None

      override def getWrappedResponse(response: Any, pack: String): Option[_] = None
    }
    Initializer.getInitializer.getResourcePath + File.separator + Constants.FIELD_DETAILS_DIRECTORY + File.separator + converterInstance.getEncodedFileName
  }

  @throws[SDKException]
  def getRelatedLists(relatedModuleName: String, moduleAPIName: String, commonAPIHandler: CommonAPIHandler): Unit =synchronized {
    try {
      var isNewData = false
      val key = (moduleAPIName + Constants.UNDERSCORE + Constants.RELATED_LISTS).toLowerCase
      val resourcesPath = new File(Initializer.getInitializer.getResourcePath + File.separator + Constants.FIELD_DETAILS_DIRECTORY)
      if (!resourcesPath.exists) resourcesPath.mkdirs
      val recordFieldDetailsPath = getFileName
      val recordFieldDetails = new File(recordFieldDetailsPath)
      if (!recordFieldDetails.exists || (recordFieldDetails.exists && Initializer.getJSON(recordFieldDetailsPath).optJSONArray(key) == null)) {
        isNewData = true
        val relatedListValues = getRelatedListDetails(moduleAPIName)
        val recordFieldDetailsJSON = if (recordFieldDetails.exists) Initializer.getJSON(recordFieldDetailsPath)
        else new JSONObject
        recordFieldDetailsJSON.put(key, relatedListValues)
        val file = new FileWriter(recordFieldDetailsPath)
        file.write(recordFieldDetailsJSON.toString)
        file.flush()
        file.close()
      }
      val recordFieldDetailsJSON = Initializer.getJSON(recordFieldDetailsPath)
      val modulerelatedList = recordFieldDetailsJSON.getJSONArray(key)
      if (!checkRelatedListExists(relatedModuleName, modulerelatedList, commonAPIHandler) && !isNewData) {
        recordFieldDetailsJSON.remove(key)
        val file = new FileWriter(recordFieldDetailsPath)
        file.write(recordFieldDetailsJSON.toString)
        file.flush()
        file.close()
        getRelatedLists(relatedModuleName, moduleAPIName, commonAPIHandler)
      }
    } catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.EXCEPTION, e)
        throw e
      case e: Exception =>
        val exception = new SDKException(Constants.EXCEPTION, e)
        LOGGER.log(Level.SEVERE, Constants.EXCEPTION, exception)
        throw exception
    }
  }


  @throws[JSONException]
  @throws[SDKException]
    private def checkRelatedListExists(relatedModuleName: String, modulerelatedListJA: JSONArray, commonAPIHandler: CommonAPIHandler): Boolean = {
    for ( index <- 0 until modulerelatedListJA.length ) {
      val relatedListJO = modulerelatedListJA.getJSONObject(index)
      if (relatedListJO.getString(Constants.API_NAME) != null && relatedListJO.getString(Constants.API_NAME).equalsIgnoreCase(relatedModuleName)) {
        if (relatedListJO.getString(Constants.HREF) == Constants.NULL_VALUE) throw new SDKException(Constants.UNSUPPORTED_IN_API, commonAPIHandler.getHttpMethod + " " + commonAPIHandler.getAPIPath + Constants.UNSUPPORTED_IN_API_MESSAGE)
        if (!relatedListJO.getString(Constants.MODULE).equalsIgnoreCase(Constants.NULL_VALUE)) {
          commonAPIHandler.setModuleAPIName(relatedListJO.getString(Constants.MODULE))
          getFieldsInfo(relatedListJO.getString(Constants.MODULE))
        }
        return true
      }
    }
    false
  }

  @throws[SDKException]
  private def getRelatedListDetails(moduleAPIName: String): JSONArray = {
    val relatedListsOperations = new RelatedListsOperations(Option(moduleAPIName))
    val responseOption  = relatedListsOperations.getRelatedLists()
    val relatedListJA = new JSONArray

    if (responseOption.isDefined) {
      val response = responseOption.get
      if (response.getStatusCode == Constants.NO_CONTENT_STATUS_CODE) return relatedListJA
      if (response.isExpected) {
        val responseHandler = response.getObject
        responseHandler match {
          case responseWrapper: relatedlists.ResponseWrapper =>
            val relatedLists = responseWrapper.getRelatedLists()
            for ( relatedList <- relatedLists ) {
              val relatedListDetail = new JSONObject
              relatedListDetail.put(Constants.API_NAME, if (relatedList.getAPIName().isDefined)relatedList.getAPIName().get else Constants.NULL_VALUE)
              relatedListDetail.put(Constants.MODULE, if (relatedList.getModule().isDefined) relatedList.getModule().get else Constants.NULL_VALUE)
              relatedListDetail.put(Constants.NAME, if (relatedList.getName().isDefined)relatedList.getName().get  else Constants.NULL_VALUE)
              relatedListDetail.put(Constants.HREF, if (relatedList.getHref().isDefined) relatedList.getHref().get  else Constants.NULL_VALUE)
              relatedListJA.put(relatedListDetail)
            }
          case _: relatedlists.APIException =>
            val exception = responseHandler.asInstanceOf[APIException]
            val errorResponse = new JSONObject
            errorResponse.put(Constants.CODE, exception.getCode().getValue)
            errorResponse.put(Constants.STATUS, exception.getStatus().getValue)
            errorResponse.put(Constants.MESSAGE, exception.getMessage().getValue)
            throw new SDKException(Constants.API_EXCEPTION, errorResponse)
          case _ =>
        }
      }
      else {
        val errorResponse = new JSONObject
        errorResponse.put(Constants.CODE, response.getStatusCode)
        throw new SDKException(Constants.API_EXCEPTION, errorResponse)
      }
    }
    relatedListJA
  }

  def getFieldsDetails(moduleAPIName: String): JSONObject = {
    val fieldsDetails = new JSONObject
    val fieldOperation = new FieldsOperations(Option(moduleAPIName))
    val responseOption = fieldOperation.getFields()

    if (responseOption.isDefined) {
      val response = responseOption.get
      if (response.getStatusCode == Constants.NO_CONTENT_STATUS_CODE) {
        return fieldsDetails
      }
      // Check if expected response is received
      if (response.isExpected) {
        val responseHandler = response.getObject
        responseHandler match {
          case responseWrapper: ResponseWrapper =>
            val fields = responseWrapper.getFields()
            fields.foreach(field => {
              breakable {
                val keyName = field.getAPIName().orNull
                if (Constants.KEYS_TO_SKIP.contains(keyName)) {
                  break
                }
                val fieldDetail = new JSONObject
                setDataType(fieldDetail, field, moduleAPIName)
                fieldsDetails.put(field.getAPIName().get, fieldDetail)
              }
            })

            if (Constants.INVENTORY_MODULES.contains(moduleAPIName.toLowerCase)) {
              val fieldDetail = new JSONObject
              fieldDetail.put(Constants.NAME, Constants.LINE_TAX)
              fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
              fieldDetail.put(Constants.STRUCTURE_NAME, Constants.LINE_TAX_NAMESPACE)
              fieldDetail.put(Constants.LOOKUP, true)
              fieldsDetails.put(Constants.LINE_TAX, fieldDetail)
            }
            if (Constants.NOTES.equalsIgnoreCase(moduleAPIName)) {
              val fieldDetail = new JSONObject
              fieldDetail.put(Constants.NAME, Constants.ATTACHMENTS)
              fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
              fieldDetail.put(Constants.STRUCTURE_NAME, Constants.ATTACHMENTS_NAMESPACE)
              fieldsDetails.put(Constants.ATTACHMENTS, fieldDetail)
            }
          case _ =>
            responseHandler match {
              case exception: APIException =>
                val errorResponse = new JSONObject
                errorResponse.put(Constants.CODE, exception.getCode().getValue)
                errorResponse.put(Constants.STATUS, exception.getStatus().getValue)
                errorResponse.put(Constants.MESSAGE, exception.getMessage().getValue)
                val exceptionInstance = new SDKException(Constants.API_EXCEPTION, errorResponse)
                if (Utility.moduleAPIName.equalsIgnoreCase(moduleAPIName)){
                  throw exceptionInstance
                }
                LOGGER.log(Level.SEVERE, Constants.API_EXCEPTION, exceptionInstance)
              case _ =>
            }
        }
      }
      else {
        val errorResponse = new JSONObject
        errorResponse.put(Constants.CODE, response.getStatusCode)
        throw new SDKException(Constants.API_EXCEPTION, errorResponse)
      }
    }
    fieldsDetails

  }

  def searchJSONDetails(key: String): Option[JSONObject] = {
    val key1 = Constants.PACKAGE_NAMESPACE + ".record." + key
    val iter = JSONDETAILS.keySet.iterator
    while ( {
      iter.hasNext
    }) {
      val keyInJSON = iter.next
      if (keyInJSON.equalsIgnoreCase(key1)) {
        val returnJSON = new JSONObject
        returnJSON.put(Constants.MODULEPACKAGENAME, keyInJSON)
        returnJSON.put(Constants.MODULEDETAILS, JSONDETAILS.getJSONObject(keyInJSON))
        return Option(returnJSON)
      }
    }
    None
  }


  @throws[SDKException]
  def getModules(): Unit = synchronized{
    apiSupportedModule = getModules(null)
  }

  @throws[SDKException]
  private def getModules(header: String):mutable.HashMap[String,String] = {
    val apiNames = new mutable.HashMap[String,String]()
    val headerHashMap = new HeaderMap
    if (header!=null) {
      val headerValue = OffsetDateTime.ofInstant(Instant.ofEpochMilli(header.toLong), ZoneId.systemDefault).withNano(0)
      headerHashMap.add(new GetModulesHeader().IfModifiedSince, headerValue)
    }
    val responseOption = new ModulesOperations().getModules(Option(headerHashMap))
    if (responseOption.isDefined) {
      val response = responseOption.get
      if (util.Arrays.asList(Constants.NO_CONTENT_STATUS_CODE, Constants.NOT_MODIFIED_STATUS_CODE).contains(response.getStatusCode)) return apiNames
      // Check if expected response is received
      if (response.isExpected) {
        val responseObject = response.getObject
        responseObject match {
          case wrapper: modules.ResponseWrapper =>
            val modules = wrapper.getModules()
            for ( module <- modules ) {
              if (module.getAPISupported().get) apiNames.put(module.getAPIName().get.toLowerCase(), module.getGeneratedType().getValue)
            }
          case _ => responseObject match {
            case exception: modules.APIException =>
              val errorResponse = new JSONObject
              errorResponse.put(Constants.CODE, exception.getCode().getValue)
              errorResponse.put(Constants.STATUS, exception.getStatus().getValue)
              errorResponse.put(Constants.MESSAGE, exception.getMessage().getValue)
              throw new SDKException(Constants.API_EXCEPTION, errorResponse)
            case _ =>
          }
        }
      }
    }
    apiNames
  }

  @throws[SDKException]
  def refreshModules(): Unit = {
    forceRefresh = true
    getFieldsInfo("")
    forceRefresh = false
  }

  def getJSONObject(json: JSONObject, key: String): Option[JSONObject] = {
    val iter = json.keySet.iterator
    while ( {
      iter.hasNext
    }) {
      val keyInJSON = iter.next
      if (keyInJSON.equalsIgnoreCase(key)) return Option(json.getJSONObject(keyInJSON))
    }
     None
  }

  private def setDataType(fieldDetail: JSONObject, field: Field, moduleAPIName:String): Unit = {
    val apiType = field.getDataType().orNull
    val keyName = field.getAPIName().orNull
    var module = ""


    if (field.getSystemMandatory().isDefined &&  field.getSystemMandatory().get.isInstanceOf[Boolean] && field.getSystemMandatory().get && !(moduleAPIName.equalsIgnoreCase(Constants.CALLS) && keyName.equalsIgnoreCase(Constants.CALL_DURATION))) fieldDetail.put(Constants.REQUIRED, true)

    if (keyName.equalsIgnoreCase(Constants.PRODUCT_DETAILS) && Constants.INVENTORY_MODULES.contains(moduleAPIName.toLowerCase)) {
      fieldDetail.put(Constants.NAME, keyName)
      fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
      fieldDetail.put(Constants.STRUCTURE_NAME, Constants.INVENTORY_LINE_ITEMS)
      fieldDetail.put(Constants.SKIP_MANDATORY, true)
      return
    }
    else if (keyName.equalsIgnoreCase(Constants.PRICING_DETAILS) && moduleAPIName.equalsIgnoreCase(Constants.PRICE_BOOKS)) {
      fieldDetail.put(Constants.NAME, keyName)
      fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
      fieldDetail.put(Constants.STRUCTURE_NAME, Constants.PRICINGDETAILS)
      fieldDetail.put(Constants.SKIP_MANDATORY, true)
      return
    }
    else if (keyName.equalsIgnoreCase(Constants.PARTICIPANT_API_NAME) && (moduleAPIName.equalsIgnoreCase(Constants.EVENTS) || moduleAPIName.equalsIgnoreCase(Constants.ACTIVITIES))) {
      fieldDetail.put(Constants.NAME, keyName)
      fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
      fieldDetail.put(Constants.STRUCTURE_NAME, Constants.PARTICIPANTS)
      fieldDetail.put(Constants.SKIP_MANDATORY, true)
      return
    }
    else if (keyName.equalsIgnoreCase(Constants.COMMENTS) && (moduleAPIName.equalsIgnoreCase(Constants.SOLUTIONS) || moduleAPIName.equalsIgnoreCase(Constants.CASES))) {
      fieldDetail.put(Constants.NAME, keyName)
      fieldDetail.put(Constants.TYPE, Constants.LIST_NAMESPACE)
      fieldDetail.put(Constants.STRUCTURE_NAME, Constants.COMMENT_NAMESPACE)
      fieldDetail.put(Constants.LOOKUP, true)
      return
    }
    else if (keyName.equalsIgnoreCase(Constants.LAYOUT)) {
      fieldDetail.put(Constants.NAME, keyName)
      fieldDetail.put(Constants.TYPE, Constants.LAYOUT_NAMESPACE)
      fieldDetail.put(Constants.STRUCTURE_NAME, Constants.LAYOUT_NAMESPACE)
      fieldDetail.put(Constants.LOOKUP, true)
      return
    }
    else if (apiTypeVsdataType.keySet.contains(apiType)){

      fieldDetail.put(Constants.TYPE, apiTypeVsdataType(apiType))
    }
    else if (apiType.equalsIgnoreCase(Constants.FORMULA)) {
      if (field.getFormula().isDefined) {
        val returnType = field.getFormula().get.getReturnType().orNull
        val apiDataType = apiTypeVsdataType.get(returnType) match {
          case Some(value) =>value
          case _ =>null
        }
        if (apiDataType != null) fieldDetail.put(Constants.TYPE, apiDataType)
      }
      fieldDetail.put(Constants.READ_ONLY, true)
    }
    else return
    if (apiType.toLowerCase.contains(Constants.LOOKUP)) fieldDetail.put(Constants.LOOKUP, true)

    if (apiType.toLowerCase.equalsIgnoreCase(Constants.CONSENT_LOOKUP)) fieldDetail.put(Constants.SKIP_MANDATORY, true)

    if (apiTypeVsStructureName.contains(apiType)) fieldDetail.put(Constants.STRUCTURE_NAME, apiTypeVsStructureName(apiType))

    if (field.getDataType().isDefined && field.getDataType().get.equalsIgnoreCase(Constants.PICKLIST) && (field.getPickListValues != null && field.getPickListValues().nonEmpty)) {
      val values = new JSONArray()
      for(plv <-field.getPickListValues() ){
        values.put(plv.getActualValue().get)
      }
      fieldDetail.put(Constants.VALUES, values)
    }
    if (apiType.equalsIgnoreCase(Constants.SUBFORM)) {
      module = field.getSubform().get.getModule().orNull

      fieldDetail.put(Constants.MODULE, module)

      fieldDetail.put(Constants.SKIP_MANDATORY, true)

      fieldDetail.put(Constants.SUBFORM, true)
    }
    if (apiType.equalsIgnoreCase(Constants.LOOKUP)) {
      module = field.getLookup().get.getModule().orNull
      if (module != null && !module.equalsIgnoreCase(Constants.SE_MODULE)) {
        fieldDetail.put(Constants.MODULE, module)
        if (module.equalsIgnoreCase(Constants.ACCOUNTS) && !field.getCustomField().get) fieldDetail.put(Constants.SKIP_MANDATORY, true)
      }
      else module = ""
      fieldDetail.put(Constants.LOOKUP, true)
    }
    if (module.length > 0) getFieldsInfo(module)
    fieldDetail.put(Constants.NAME, keyName)
  }

  private def fillDatatype(): Unit = {
    if (apiTypeVsdataType.nonEmpty) return
    val fieldAPINamesString = Array[String]("textarea", "text", "website", "email", "phone", "mediumtext", "multiselectlookup", "profileimage","autonumber") // No I18N
    val fieldAPINamesInteger = Array[String]("integer")
    val fieldAPINamesBoolean = Array[String]("boolean")
    val fieldAPINamesLong = Array[String]("long", "bigint")
    val fieldAPINamesDouble = Array[String]("double", "percent", "lookup", "currency")
    val fieldAPINamesFile = Array[String]( "imageupload")
    val fieldAPINamesFieldFile = Array[String](  "fileupload")

    val fieldAPINamesDateTime = Array[String]("datetime", "event_reminder" )
    val fieldAPINamesDate = Array[String]("date")
    val fieldAPINamesLookup = Array[String]("lookup")
    val fieldAPINamesPickList = Array[String]("picklist")
    val fieldAPINamesMultiSelectPickList = Array[String]("multiselectpicklist")
    val fieldAPINamesSubForm = Array[String]("subform")
    val fieldAPINamesOwnerLookUp = Array[String]("ownerlookup", "userlookup" )
    val fieldAPINamesMultiUserLookUp = Array[String]("multiuserlookup")
    val fieldAPINamesMultiModuleLookUp = Array[String]("multimodulelookup")
    val fieldAPINameTaskRemindAt = Array[String]("ALARM")

    val fieldAPINameRecurringActivity = Array[String]("RRULE")

    val fieldAPINameReminder = Array[String]("multireminder")

    val fieldAPINameConsentLookUp = Array[String]("consent_lookup")
    for ( fieldAPIName <- fieldAPINamesString ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.STRING_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesInteger ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.INTEGER_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesBoolean ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.BOOLEAN_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesLong ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LONG_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesDouble ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.DOUBLE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesFile ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.FILE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesDateTime ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.DATETIME_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesDate ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.DATE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesLookup ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.RECORD_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.RECORD_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesPickList ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.CHOICE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesMultiSelectPickList ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.CHOICE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesSubForm ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.RECORD_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesOwnerLookUp ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.USER_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.USER_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesMultiUserLookUp ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.USER_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesMultiModuleLookUp ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.MODULE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINamesFieldFile ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.FIELD_FILE_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINameTaskRemindAt ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.REMINDAT_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.REMINDAT_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINameRecurringActivity ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.RECURRING_ACTIVITY_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.RECURRING_ACTIVITY_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINameReminder ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.LIST_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.REMINDER_NAMESPACE)
    }

    for ( fieldAPIName <- fieldAPINameConsentLookUp ) {
      apiTypeVsdataType.put(fieldAPIName, Constants.CONSENT_NAMESPACE)
      apiTypeVsStructureName.put(fieldAPIName, Constants.CONSENT_NAMESPACE)
    }
  }
}
