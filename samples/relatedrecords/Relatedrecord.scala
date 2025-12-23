package com.zoho.crm.sample.relatedrecords

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util

import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.attachments.Attachment
import com.zoho.crm.api.layouts.Layout
import com.zoho.crm.api.record.{Comment, Consent, FileDetails, InventoryLineItems, LineTax, Participants, PricingDetails, Record, RecurringActivity, RemindAt}
import com.zoho.crm.api.relatedrecords.FileBodyWrapper
import com.zoho.crm.api.relatedrecords.APIException
import com.zoho.crm.api.relatedrecords.ActionHandler
import com.zoho.crm.api.relatedrecords.ActionResponse
import com.zoho.crm.api.relatedrecords.ActionWrapper
import com.zoho.crm.api.relatedrecords.BodyWrapper
import com.zoho.crm.api.relatedrecords.RelatedRecordsOperations
import com.zoho.crm.api.relatedrecords.RelatedRecordsOperations.DelinkRecordsParam
import com.zoho.crm.api.relatedrecords.RelatedRecordsOperations.GetRelatedRecordHeader
import com.zoho.crm.api.relatedrecords.RelatedRecordsOperations.GetRelatedRecordsHeader
import com.zoho.crm.api.relatedrecords.RelatedRecordsOperations.GetRelatedRecordsParam
import com.zoho.crm.api.relatedrecords.ResponseHandler
import com.zoho.crm.api.relatedrecords.ResponseWrapper
import com.zoho.crm.api.relatedrecords.SuccessResponse
import com.zoho.crm.api.tags.Tag
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer


object Relatedrecord {
  /**
   * <h3> Get Related Records </h3>
   * This method is used to get the related list records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to get related records.
   * @param recordId           - The ID of the record to be obtained.
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedRecords(moduleAPIName: String, recordId: Long, relatedListAPIName: String): Unit = { //example
    //Get instance of RelatedRecordsOperations Class that takes moduleAPIName, recordId and relatedListAPIName as parameter
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
//    paramInstance.add(new GetRelatedRecordsParam().page, 1)
//    paramInstance.add(new GetRelatedRecordsParam().perPage, 2)
    //Get instance of HeaderMap Class
    val headerInstance = new HeaderMap
//    val startdatetime = OffsetDateTime.of(2019, 6, 1, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
//    headerInstance.add(new GetRelatedRecordsHeader().IfModifiedSince, startdatetime)
    //Call getRelatedRecords method that takes paramInstance, headerInstance as parameter
    val responseOption = relatedRecordsOperations.getRelatedRecords(recordId, Option(paramInstance),Option( headerInstance))
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
          //Get the list of obtained Record instances
          val records = responseWrapper.getData

          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            var createdByOption = record.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              var createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            var modifiedByOption = record.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              var modifiedBy =modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //Get the list of Tag instance each Record
            val tags = record.getTag
            //Check if tags is not null
            if (tags.length > 0) {

              for (tag <- tags) { //Get the Name of each Tag
                println("Record Tag Name: " + tag.getName)
                //Get the Id of each Tag
                println("Record Tag ID: " + tag.getId)
              }
            }
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              var value = entry._2
              if (value.isInstanceOf[Option[_]]) value = value.asInstanceOf[Option[_]].getOrElse(null)
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                if (dataList.size > 0) if (dataList(0).isInstanceOf[FileDetails]) {
                  @SuppressWarnings(Array("unchecked")) val fileDetails = value.asInstanceOf[ArrayBuffer[FileDetails]]

                  for (fileDetail <- fileDetails) { //Get the Extn of each FileDetails
                    println("Record FileDetails Extn: " + fileDetail.getExtn)
                    //Get the IsPreviewAvailable of each FileDetails
                    println("Record FileDetails IsPreviewAvailable: " + fileDetail.getIsPreviewAvailable)
                    //Get the DownloadUrl of each FileDetails
                    println("Record FileDetails DownloadUrl: " + fileDetail.getDownloadUrl)
                    //Get the DeleteUrl of each FileDetails
                    println("Record FileDetails DeleteUrl: " + fileDetail.getDeleteUrl)
                    //Get the EntityId of each FileDetails
                    println("Record FileDetails EntityId: " + fileDetail.getEntityId)
                    //Get the Mode of each FileDetails
                    println("Record FileDetails Mode: " + fileDetail.getMode)
                    //Get the OriginalSizeByte of each FileDetails
                    println("Record FileDetails OriginalSizeByte: " + fileDetail.getOriginalSizeByte)
                    //Get the PreviewUrl of each FileDetails
                    println("Record FileDetails PreviewUrl: " + fileDetail.getPreviewUrl)
                    //Get the FileName of each FileDetails
                    println("Record FileDetails FileName: " + fileDetail.getFileName)
                    //Get the FileId of each FileDetails
                    println("Record FileDetails FileId: " + fileDetail.getFileId)
                    //Get the AttachmentId of each FileDetails
                    println("Record FileDetails AttachmentId: " + fileDetail.getAttachmentId)
                    //Get the FileSize of each FileDetails
                    println("Record FileDetails FileSize: " + fileDetail.getFileSize)
                    //Get the CreatorId of each FileDetails
                    println("Record FileDetails CreatorId: " + fileDetail.getCreatorId)
                    //Get the LinkDocs of each FileDetails
                    println("Record FileDetails LinkDocs: " + fileDetail.getLinkDocs)
                  }
                }
                else if (dataList(0).isInstanceOf[Choice[_]]) {
                  @SuppressWarnings(Array("unchecked")) val choiceList = dataList.asInstanceOf[(ArrayBuffer[Choice[_$1]]) forSome {type _$1}]
                  println(keyName)
                  println("values")

                  for (choice <- choiceList) {
                    println(choice.getValue)
                  }
                }
                else if (dataList(0).isInstanceOf[InventoryLineItems]) {
                  @SuppressWarnings(Array("unchecked")) val productDetails = value.asInstanceOf[ArrayBuffer[InventoryLineItems]]

                  for (productDetail <- productDetails) {
                    val lineItemProductOption = productDetail.getProduct
                    if (lineItemProductOption.isDefined) {
                      val lineItemProduct= lineItemProductOption.get
                      println("Record ProductDetails LineItemProduct ProductCode: " + lineItemProduct.getProductCode)
                      println("Record ProductDetails LineItemProduct Currency: " + lineItemProduct.getCurrency)
                      println("Record ProductDetails LineItemProduct Name: " + lineItemProduct.getName)
                      println("Record ProductDetails LineItemProduct Id: " + lineItemProduct.getId)
                    }
                    println("Record ProductDetails Quantity: " + productDetail.getQuantity.toString)
                    println("Record ProductDetails Discount: " + productDetail.getDiscount)
                    println("Record ProductDetails TotalAfterDiscount: " + productDetail.getTotalAfterDiscount.toString)
                    println("Record ProductDetails NetTotal: " + productDetail.getNetTotal.toString)
                    if (productDetail.getBook.isDefined) println("Record ProductDetails Book: " + productDetail.getBook.toString)
                    println("Record ProductDetails Tax: " + productDetail.getTax.toString)
                    println("Record ProductDetails ListPrice: " + productDetail.getListPrice.toString)
                    println("Record ProductDetails UnitPrice: " + productDetail.getUnitPrice)
                    println("Record ProductDetails QuantityInStock: " + productDetail.getQuantityInStock.toString)
                    println("Record ProductDetails Total: " + productDetail.getTotal.toString)
                    println("Record ProductDetails ID: " + productDetail.getId)
                    println("Record ProductDetails ProductDescription: " + productDetail.getProductDescription)
                    val lineTaxes = productDetail.getLineTax

                    for (lineTax <- lineTaxes) {
                      println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                      println("Record ProductDetails LineTax Name: " + lineTax.getName)
                      println("Record ProductDetails LineTax Id: " + lineTax.getId)
                      println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                    }
                  }
                }
                else if (dataList(0).isInstanceOf[Tag]) {
                  @SuppressWarnings(Array("unchecked")) val tagList = value.asInstanceOf[ArrayBuffer[Tag]]

                  for (tag <- tagList) {
                    println("Record Tag Name: " + tag.getName)
                    println("Record Tag ID: " + tag.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[PricingDetails]) {
                  @SuppressWarnings(Array("unchecked")) val pricingDetails = value.asInstanceOf[ ArrayBuffer[PricingDetails]]

                  for (pricingDetail <- pricingDetails) {
                    println("Record PricingDetails ToRange: " + pricingDetail.getToRange.toString)
                    println("Record PricingDetails Discount: " + pricingDetail.getDiscount.toString)
                    println("Record PricingDetails ID: " + pricingDetail.getId)
                    println("Record PricingDetails FromRange: " + pricingDetail.getFromRange.toString)
                  }
                }
                else if(dataList(0).isInstanceOf[Participants])
                {
                  val participants = value.asInstanceOf[ ArrayBuffer[Participants]]
                  for (participant <- participants) {
                    println("Record Participants Name: " + participant.getName().toString)
                    println("Record Participants Invited: " + participant.getInvited().toString())
                    println("Record Participants ID: " + participant.getId().toString)
                    println("Record Participants Type: " + participant.getType().toString)
                    println("Record Participants Participant: " + participant.getParticipant().toString)
                    println("Record Participants Status: " + participant.getStatus().toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Record]) {
                  @SuppressWarnings(Array("unchecked")) val recordList = dataList.asInstanceOf[ ArrayBuffer[Record]]

                  for (record1 <- recordList) { //Get the details map


                    record1.getKeyValues.foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                }
                else if (dataList(0).isInstanceOf[LineTax]) {
                  @SuppressWarnings(Array("unchecked")) val lineTaxes = dataList.asInstanceOf[ ArrayBuffer[LineTax]]

                  for (lineTax <- lineTaxes) {
                    println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                    println("Record ProductDetails LineTax Name: " + lineTax.getName)
                    println("Record ProductDetails LineTax Id: " + lineTax.getId)
                    println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Comment]) {
                  @SuppressWarnings(Array("unchecked")) val comments = dataList.asInstanceOf[ ArrayBuffer[Comment]]

                  for (comment <- comments) {
                    println("Record Comment CommentedBy: " + comment.getCommentedBy)
                    println("Record Comment CommentedTime: " + comment.getCommentedTime.toString)
                    println("Record Comment CommentContent: " + comment.getCommentContent)
                    println("Record Comment Id: " + comment.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[Attachment]) { //Get the list of obtained Attachment instances
                  @SuppressWarnings(Array("unchecked")) val attachments = dataList.asInstanceOf[ ArrayBuffer[Attachment]]


                  for (attachment <- attachments) { //Get the owner User instance of each attachment
                    val ownerOption = attachment.getOwner
                    //Check if owner is not null
                    if (ownerOption.isDefined) { //Get the Name of the Owner
                      var owner = ownerOption.get
                      println("Record Attachment Owner User-Name: " + owner.getName)
                      //Get the ID of the Owner
                      println("Record Attachment Owner User-ID: " + owner.getId)
                      //Get the Email of the Owner
                      println("Record Attachment Owner User-Email: " + owner.getEmail)
                    }
                    //Get the modified time of each attachment
                    println("Record Attachment Modified Time: " + attachment.getModifiedTime.toString)
                    //Get the name of the File
                    println("Record Attachment File Name: " + attachment.getFileName)
                    //Get the created time of each attachment
                    println("Record Attachment Created Time: " + attachment.getCreatedTime.toString)
                    //Get the Attachment file size
                    println("Record Attachment File Size: " + attachment.getSize.toString)
                    //Get the parentId Record instance of each attachment
                    val parentIdOption = attachment.getParentId
                    //Check if parentId is not null
                    if (parentIdOption.isDefined) { //Get the parent record Name of each attachment
                      var parentId =parentIdOption.get
                      println("Record Attachment parent record Name: " + parentId.getKeyValue("name"))
                      //Get the parent record ID of each attachment
                      println("Record Attachment parent record ID: " + parentId.getId)
                    }
                    //Get the attachment is Editable
                    println("Record Attachment is Editable: " + attachment.geteditable().toString)
                    //Get the file ID of each attachment
                    println("Record Attachment File ID: " + attachment.getfileId())
                    //Get the type of each attachment
                    println("Record Attachment File Type: " + attachment.gettype)
                    //Get the seModule of each attachment
                    println("Record Attachment seModule: " + attachment.getseModule())
                    //Get the modifiedBy User instance of each attachment
                    var modifiedByOption = attachment.getModifiedBy
                    if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
                      var modifiedBy = modifiedByOption.get
                      println("Record Attachment Modified By User-Name: " + modifiedBy.getName)
                      println("Record Attachment Modified By User-ID: " + modifiedBy.getId)
                      println("Record Attachment Modified By User-Email: " + modifiedBy.getEmail)
                    }
                    //Get the state of each attachment
                    println("Record Attachment State: " + attachment.getstate)
                    //Get the ID of each attachment
                    println("Record Attachment ID: " + attachment.getId)
                    //Get the createdBy User instance of each attachment
                    var createdByOption = attachment.getCreatedBy
                    if (createdByOption.isDefined) {
                      var createdBy = modifiedByOption.get

                      println("Record Attachment Created By User-Name: " + createdBy.getName)
                      println("Record Attachment Created By User-ID: " + createdBy.getId)
                      println("Record Attachment Created By User-Email: " + createdBy.getEmail)
                    }
                    //Get the linkUrl of each attachment
                    println("Record Attachment LinkUrl: " + attachment.getlinkUrl())
                  }
                }
                else println(keyName + ": " + value)
              }
              else if (value.isInstanceOf[Record]) {
                val recordValue = value.asInstanceOf[Record]
                println("Record " + keyName + " ID: " + recordValue.getId)
                println("Record " + keyName + " Name: " + recordValue.getKeyValue("name"))
              }
              else if (value.isInstanceOf[Layout]) {
                val layout = value.asInstanceOf[Layout]
                if (layout != null) {
                  println("Record " + keyName + " ID: " + layout.getId)
                  println("Record " + keyName + " Name: " + layout.getName)
                }
              }
              else if (value.isInstanceOf[User]) {
                val user = value.asInstanceOf[User]
                if (user != null) {
                  println("Record " + keyName + " User-ID: " + user.getId)
                  println("Record " + keyName + " User-Name: " + user.getName)
                  println("Record " + keyName + " User-Email: " + user.getEmail)
                }
              }
              else if (value.isInstanceOf[Choice[_]]) println(keyName + ": " + value.asInstanceOf[Choice[_]].getValue)
              else if (value.isInstanceOf[RemindAt]) println(keyName + ": " + value.asInstanceOf[RemindAt].getAlarm)
              else if (value.isInstanceOf[RecurringActivity]) {
                println(keyName)
                println("RRULE" + ": " + value.asInstanceOf[RecurringActivity].getRrule)
              }
              else if (value.isInstanceOf[Consent]) {
                val consent = value.asInstanceOf[Consent]
                println("Record Consent ID: " + consent.getId)
                //Get the Owner User instance of each attachment
                val ownerOption = consent.getOwner
                if (ownerOption.isDefined) { //Get the name of the owner User
                  var owner = ownerOption.get
                  println("Record Consent Owner Name: " + owner.getName)
                  //Get the ID of the owner User
                  println("Record Consent Owner ID: " + owner.getId)
                  //Get the Email of the owner User
                  println("Record Consent Owner Email: " + owner.getEmail)
                }
                val consentCreatedByOption = consent.getCreatedBy
                if (consentCreatedByOption.isDefined) { //Get the name of the CreatedBy User
                  var consentCreatedBy = consentCreatedByOption.get
                  println("Record Consent CreatedBy Name: " + consentCreatedBy.getName)
                  //Get the ID of the CreatedBy User
                  println("Record Consent CreatedBy ID: " + consentCreatedBy.getId)
                  //Get the Email of the CreatedBy User
                  println("Record Consent CreatedBy Email: " + consentCreatedBy.getEmail)
                }
                val consentModifiedByOption = consent.getModifiedBy
                if (consentModifiedByOption.isDefined) { //Get the name of the ModifiedBy User
                  var consentModifiedBy = consentModifiedByOption.get
                  println("Record Consent ModifiedBy Name: " + consentModifiedBy.getName)
                  //Get the ID of the ModifiedBy User
                  println("Record Consent ModifiedBy ID: " + consentModifiedBy.getId)
                  //Get the Email of the ModifiedBy User
                  println("Record Consent ModifiedBy Email: " + consentModifiedBy.getEmail)
                }
                println("Record Consent CreatedTime: " + consent.getCreatedTime)
                println("Record Consent ModifiedTime: " + consent.getModifiedTime)
                println("Record Consent ContactThroughEmail: " + consent.getContactThroughEmail)
                println("Record Consent ContactThroughSocial: " + consent.getContactThroughSocial)
                println("Record Consent ContactThroughSurvey: " + consent.getContactThroughSurvey)
                println("Record Consent ContactThroughPhone: " + consent.getContactThroughPhone)
                println("Record Consent MailSentTime: " + consent.getMailSentTime.toString)
                println("Record Consent ConsentDate: " + consent.getConsentDate.toString)
                println("Record Consent ConsentRemarks: " + consent.getConsentRemarks)
                println("Record Consent ConsentThrough: " + consent.getConsentThrough)
                println("Record Consent DataProcessingBasis: " + consent.getDataProcessingBasis)
                //To get custom values
                println("Record Consent Lawful Reason: " + consent.getKeyValue("Lawful_Reason"))
              }
              else println(keyName + ": " + value)
            })
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
   * <h3> Update Related Records </h3>
   * This method is used to update the relation between the records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to update related records.
   * @param recordId           - The ID of the record to be obtained.
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @throws Exception
   */
  @throws[Exception]
  def updateRelatedRecords(moduleAPIName: String, recordId: Long, relatedListAPIName: String): Unit = { //API Name of the module
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Record instances
    val records = new ArrayBuffer[Record]
    //Get instance of Record Class
    val record1 = new Record
    /*
     * Call addKeyValue method that takes two arguments
     * 1 -> A string that is the Field's API Name
     * 2 -> Value
     */
    record1.addKeyValue("id", 3477061000007230023l)
    record1.addKeyValue("Note_Content", "asd")
    //Add Record instance to the list
    records.addOne(record1)
    val record2 = new Record
    record2.addKeyValue("id", 3477061000005919001l)
    record2.addKeyValue("Note_Content", "asd")
    records.addOne(record2)
    //Set the list to Records in BodyWrapper instance
    request.setData(records)
    //Call updateRelatedRecords method that takes BodyWrapper instance as parameter.
    val responseOption = relatedRecordsOperations.updateRelatedRecords(recordId, request)
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getData
           
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
   * <h3> Delink Records </h3>
   * This method is used to delete the association between modules and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to delink related records.
   * @param recordId           - The ID of the record
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param relatedListIds     - The List of related record IDs.
   * @throws Exception
   */
  @throws[Exception]
  def delinkRecords(moduleAPIName: String, recordId: Long, relatedListAPIName: String, relatedListIds: ArrayBuffer[String]): Unit = { //List<Long> relatedListIds = new ArrayList<Long>(Arrays.asList(3477061000005919001l, 3477061000005917011l))
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    val paramInstance = new ParameterMap
     
    for (relatedListId <- relatedListIds) {
      paramInstance.add(new DelinkRecordsParam().ids, relatedListId)
    }
    //Call delinkRecords method that takes paramInstance instance as parameter.
    val responseOption = relatedRecordsOperations.delinkRecords(recordId, Option(paramInstance))
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData
           
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
   * <h3> Get Related Records Using External Id </h3>
   * This method is used to get the related list records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to get related records.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedRecordsUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String): Unit = { //example
    val xExternal = "Leads.External"
    //Get instance of RelatedRecordsOperations Class that takes relatedListAPIName, moduleAPIName and xExternal as parameter
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
//    paramInstance.add(new GetRelatedRecordsParam().page, 1)
//    paramInstance.add(new GetRelatedRecordsParam().perPage, 2)
    //Get instance of HeaderMap Class
    val headerInstance = new HeaderMap
//    val startdatetime = OffsetDateTime.of(2019, 6, 1, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
//    headerInstance.add(new GetRelatedRecordsHeader().IfModifiedSince, startdatetime)
    //Call getRelatedRecordsUsingExternalId method that takes externalValue, paramInstance and headerInstance as parameter
    val responseOption = relatedRecordsOperations.getRelatedRecordsUsingExternalId(externalValue, Option(paramInstance), Option( headerInstance))
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
          //Get the list of obtained Record instances
          val records = responseWrapper.getData

          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            var createdByOption = record.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              var createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            var modifiedByOption = record.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              var modifiedBy =modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //Get the list of Tag instance each Record
            val tags = record.getTag
            //Check if tags is not null
            if (tags.length > 0) {

              for (tag <- tags) { //Get the Name of each Tag
                println("Record Tag Name: " + tag.getName)
                //Get the Id of each Tag
                println("Record Tag ID: " + tag.getId)
              }
            }
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              var value = entry._2
              if (value.isInstanceOf[Option[_]]) value = value.asInstanceOf[Option[_]].getOrElse(null)
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                if (dataList.size > 0) if (dataList(0).isInstanceOf[FileDetails]) {
                  @SuppressWarnings(Array("unchecked")) val fileDetails = value.asInstanceOf[ArrayBuffer[FileDetails]]

                  for (fileDetail <- fileDetails) { //Get the Extn of each FileDetails
                    println("Record FileDetails Extn: " + fileDetail.getExtn)
                    //Get the IsPreviewAvailable of each FileDetails
                    println("Record FileDetails IsPreviewAvailable: " + fileDetail.getIsPreviewAvailable)
                    //Get the DownloadUrl of each FileDetails
                    println("Record FileDetails DownloadUrl: " + fileDetail.getDownloadUrl)
                    //Get the DeleteUrl of each FileDetails
                    println("Record FileDetails DeleteUrl: " + fileDetail.getDeleteUrl)
                    //Get the EntityId of each FileDetails
                    println("Record FileDetails EntityId: " + fileDetail.getEntityId)
                    //Get the Mode of each FileDetails
                    println("Record FileDetails Mode: " + fileDetail.getMode)
                    //Get the OriginalSizeByte of each FileDetails
                    println("Record FileDetails OriginalSizeByte: " + fileDetail.getOriginalSizeByte)
                    //Get the PreviewUrl of each FileDetails
                    println("Record FileDetails PreviewUrl: " + fileDetail.getPreviewUrl)
                    //Get the FileName of each FileDetails
                    println("Record FileDetails FileName: " + fileDetail.getFileName)
                    //Get the FileId of each FileDetails
                    println("Record FileDetails FileId: " + fileDetail.getFileId)
                    //Get the AttachmentId of each FileDetails
                    println("Record FileDetails AttachmentId: " + fileDetail.getAttachmentId)
                    //Get the FileSize of each FileDetails
                    println("Record FileDetails FileSize: " + fileDetail.getFileSize)
                    //Get the CreatorId of each FileDetails
                    println("Record FileDetails CreatorId: " + fileDetail.getCreatorId)
                    //Get the LinkDocs of each FileDetails
                    println("Record FileDetails LinkDocs: " + fileDetail.getLinkDocs)
                  }
                }
                else if (dataList(0).isInstanceOf[Choice[_]]) {
                  @SuppressWarnings(Array("unchecked")) val choiceList = dataList.asInstanceOf[(ArrayBuffer[Choice[_$1]]) forSome {type _$1}]
                  println(keyName)
                  println("values")

                  for (choice <- choiceList) {
                    println(choice.getValue)
                  }
                }
                else if (dataList(0).isInstanceOf[InventoryLineItems]) {
                  @SuppressWarnings(Array("unchecked")) val productDetails = value.asInstanceOf[ArrayBuffer[InventoryLineItems]]

                  for (productDetail <- productDetails) {
                    val lineItemProductOption = productDetail.getProduct
                    if (lineItemProductOption.isDefined) {
                      val lineItemProduct= lineItemProductOption.get
                      println("Record ProductDetails LineItemProduct ProductCode: " + lineItemProduct.getProductCode)
                      println("Record ProductDetails LineItemProduct Currency: " + lineItemProduct.getCurrency)
                      println("Record ProductDetails LineItemProduct Name: " + lineItemProduct.getName)
                      println("Record ProductDetails LineItemProduct Id: " + lineItemProduct.getId)
                    }
                    println("Record ProductDetails Quantity: " + productDetail.getQuantity.toString)
                    println("Record ProductDetails Discount: " + productDetail.getDiscount)
                    println("Record ProductDetails TotalAfterDiscount: " + productDetail.getTotalAfterDiscount.toString)
                    println("Record ProductDetails NetTotal: " + productDetail.getNetTotal.toString)
                    if (productDetail.getBook.isDefined) println("Record ProductDetails Book: " + productDetail.getBook.toString)
                    println("Record ProductDetails Tax: " + productDetail.getTax.toString)
                    println("Record ProductDetails ListPrice: " + productDetail.getListPrice.toString)
                    println("Record ProductDetails UnitPrice: " + productDetail.getUnitPrice)
                    println("Record ProductDetails QuantityInStock: " + productDetail.getQuantityInStock.toString)
                    println("Record ProductDetails Total: " + productDetail.getTotal.toString)
                    println("Record ProductDetails ID: " + productDetail.getId)
                    println("Record ProductDetails ProductDescription: " + productDetail.getProductDescription)
                    val lineTaxes = productDetail.getLineTax

                    for (lineTax <- lineTaxes) {
                      println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                      println("Record ProductDetails LineTax Name: " + lineTax.getName)
                      println("Record ProductDetails LineTax Id: " + lineTax.getId)
                      println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                    }
                  }
                }
                else if (dataList(0).isInstanceOf[Tag]) {
                  @SuppressWarnings(Array("unchecked")) val tagList = value.asInstanceOf[ArrayBuffer[Tag]]

                  for (tag <- tagList) {
                    println("Record Tag Name: " + tag.getName)
                    println("Record Tag ID: " + tag.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[PricingDetails]) {
                  @SuppressWarnings(Array("unchecked")) val pricingDetails = value.asInstanceOf[ ArrayBuffer[PricingDetails]]

                  for (pricingDetail <- pricingDetails) {
                    println("Record PricingDetails ToRange: " + pricingDetail.getToRange.toString)
                    println("Record PricingDetails Discount: " + pricingDetail.getDiscount.toString)
                    println("Record PricingDetails ID: " + pricingDetail.getId)
                    println("Record PricingDetails FromRange: " + pricingDetail.getFromRange.toString)
                  }
                }
                else if(dataList(0).isInstanceOf[Participants])
                {
                  val participants = value.asInstanceOf[ ArrayBuffer[Participants]]
                  for (participant <- participants) {
                    println("Record Participants Name: " + participant.getName().toString)
                    println("Record Participants Invited: " + participant.getInvited().toString())
                    println("Record Participants ID: " + participant.getId().toString)
                    println("Record Participants Type: " + participant.getType().toString)
                    println("Record Participants Participant: " + participant.getParticipant().toString)
                    println("Record Participants Status: " + participant.getStatus().toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Record]) {
                  @SuppressWarnings(Array("unchecked")) val recordList = dataList.asInstanceOf[ ArrayBuffer[Record]]

                  for (record1 <- recordList) { //Get the details map


                    record1.getKeyValues.foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                }
                else if (dataList(0).isInstanceOf[LineTax]) {
                  @SuppressWarnings(Array("unchecked")) val lineTaxes = dataList.asInstanceOf[ ArrayBuffer[LineTax]]

                  for (lineTax <- lineTaxes) {
                    println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                    println("Record ProductDetails LineTax Name: " + lineTax.getName)
                    println("Record ProductDetails LineTax Id: " + lineTax.getId)
                    println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Comment]) {
                  @SuppressWarnings(Array("unchecked")) val comments = dataList.asInstanceOf[ ArrayBuffer[Comment]]

                  for (comment <- comments) {
                    println("Record Comment CommentedBy: " + comment.getCommentedBy)
                    println("Record Comment CommentedTime: " + comment.getCommentedTime.toString)
                    println("Record Comment CommentContent: " + comment.getCommentContent)
                    println("Record Comment Id: " + comment.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[Attachment]) { //Get the list of obtained Attachment instances
                  @SuppressWarnings(Array("unchecked")) val attachments = dataList.asInstanceOf[ ArrayBuffer[Attachment]]


                  for (attachment <- attachments) { //Get the owner User instance of each attachment
                    val ownerOption = attachment.getOwner
                    //Check if owner is not null
                    if (ownerOption.isDefined) { //Get the Name of the Owner
                      var owner = ownerOption.get
                      println("Record Attachment Owner User-Name: " + owner.getName)
                      //Get the ID of the Owner
                      println("Record Attachment Owner User-ID: " + owner.getId)
                      //Get the Email of the Owner
                      println("Record Attachment Owner User-Email: " + owner.getEmail)
                    }
                    //Get the modified time of each attachment
                    println("Record Attachment Modified Time: " + attachment.getModifiedTime.toString)
                    //Get the name of the File
                    println("Record Attachment File Name: " + attachment.getFileName)
                    //Get the created time of each attachment
                    println("Record Attachment Created Time: " + attachment.getCreatedTime.toString)
                    //Get the Attachment file size
                    println("Record Attachment File Size: " + attachment.getSize.toString)
                    //Get the parentId Record instance of each attachment
                    val parentIdOption = attachment.getParentId
                    //Check if parentId is not null
                    if (parentIdOption.isDefined) { //Get the parent record Name of each attachment
                      var parentId =parentIdOption.get
                      println("Record Attachment parent record Name: " + parentId.getKeyValue("name"))
                      //Get the parent record ID of each attachment
                      println("Record Attachment parent record ID: " + parentId.getId)
                    }
                    //Get the attachment is Editable
                    println("Record Attachment is Editable: " + attachment.geteditable().toString)
                    //Get the file ID of each attachment
                    println("Record Attachment File ID: " + attachment.getfileId())
                    //Get the type of each attachment
                    println("Record Attachment File Type: " + attachment.gettype)
                    //Get the seModule of each attachment
                    println("Record Attachment seModule: " + attachment.getseModule())
                    //Get the modifiedBy User instance of each attachment
                    var modifiedByOption = attachment.getModifiedBy
                    if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
                      var modifiedBy = modifiedByOption.get
                      println("Record Attachment Modified By User-Name: " + modifiedBy.getName)
                      println("Record Attachment Modified By User-ID: " + modifiedBy.getId)
                      println("Record Attachment Modified By User-Email: " + modifiedBy.getEmail)
                    }
                    //Get the state of each attachment
                    println("Record Attachment State: " + attachment.getstate)
                    //Get the ID of each attachment
                    println("Record Attachment ID: " + attachment.getId)
                    //Get the createdBy User instance of each attachment
                    var createdByOption = attachment.getCreatedBy
                    if (createdByOption.isDefined) {
                      var createdBy = modifiedByOption.get

                      println("Record Attachment Created By User-Name: " + createdBy.getName)
                      println("Record Attachment Created By User-ID: " + createdBy.getId)
                      println("Record Attachment Created By User-Email: " + createdBy.getEmail)
                    }
                    //Get the linkUrl of each attachment
                    println("Record Attachment LinkUrl: " + attachment.getlinkUrl())
                  }
                }
                else println(keyName + ": " + value)
              }
              else if (value.isInstanceOf[Record]) {
                val recordValue = value.asInstanceOf[Record]
                println("Record " + keyName + " ID: " + recordValue.getId)
                println("Record " + keyName + " Name: " + recordValue.getKeyValue("name"))
              }
              else if (value.isInstanceOf[Layout]) {
                val layout = value.asInstanceOf[Layout]
                if (layout != null) {
                  println("Record " + keyName + " ID: " + layout.getId)
                  println("Record " + keyName + " Name: " + layout.getName)
                }
              }
              else if (value.isInstanceOf[User]) {
                val user = value.asInstanceOf[User]
                if (user != null) {
                  println("Record " + keyName + " User-ID: " + user.getId)
                  println("Record " + keyName + " User-Name: " + user.getName)
                  println("Record " + keyName + " User-Email: " + user.getEmail)
                }
              }
              else if (value.isInstanceOf[Choice[_]]) println(keyName + ": " + value.asInstanceOf[Choice[_]].getValue)
              else if (value.isInstanceOf[RemindAt]) println(keyName + ": " + value.asInstanceOf[RemindAt].getAlarm)
              else if (value.isInstanceOf[RecurringActivity]) {
                println(keyName)
                println("RRULE" + ": " + value.asInstanceOf[RecurringActivity].getRrule)
              }
              else if (value.isInstanceOf[Consent]) {
                val consent = value.asInstanceOf[Consent]
                println("Record Consent ID: " + consent.getId)
                //Get the Owner User instance of each attachment
                val ownerOption = consent.getOwner
                if (ownerOption.isDefined) { //Get the name of the owner User
                  var owner = ownerOption.get
                  println("Record Consent Owner Name: " + owner.getName)
                  //Get the ID of the owner User
                  println("Record Consent Owner ID: " + owner.getId)
                  //Get the Email of the owner User
                  println("Record Consent Owner Email: " + owner.getEmail)
                }
                val consentCreatedByOption = consent.getCreatedBy
                if (consentCreatedByOption.isDefined) { //Get the name of the CreatedBy User
                  var consentCreatedBy = consentCreatedByOption.get
                  println("Record Consent CreatedBy Name: " + consentCreatedBy.getName)
                  //Get the ID of the CreatedBy User
                  println("Record Consent CreatedBy ID: " + consentCreatedBy.getId)
                  //Get the Email of the CreatedBy User
                  println("Record Consent CreatedBy Email: " + consentCreatedBy.getEmail)
                }
                val consentModifiedByOption = consent.getModifiedBy
                if (consentModifiedByOption.isDefined) { //Get the name of the ModifiedBy User
                  var consentModifiedBy = consentModifiedByOption.get
                  println("Record Consent ModifiedBy Name: " + consentModifiedBy.getName)
                  //Get the ID of the ModifiedBy User
                  println("Record Consent ModifiedBy ID: " + consentModifiedBy.getId)
                  //Get the Email of the ModifiedBy User
                  println("Record Consent ModifiedBy Email: " + consentModifiedBy.getEmail)
                }
                println("Record Consent CreatedTime: " + consent.getCreatedTime)
                println("Record Consent ModifiedTime: " + consent.getModifiedTime)
                println("Record Consent ContactThroughEmail: " + consent.getContactThroughEmail)
                println("Record Consent ContactThroughSocial: " + consent.getContactThroughSocial)
                println("Record Consent ContactThroughSurvey: " + consent.getContactThroughSurvey)
                println("Record Consent ContactThroughPhone: " + consent.getContactThroughPhone)
                println("Record Consent MailSentTime: " + consent.getMailSentTime.toString)
                println("Record Consent ConsentDate: " + consent.getConsentDate.toString)
                println("Record Consent ConsentRemarks: " + consent.getConsentRemarks)
                println("Record Consent ConsentThrough: " + consent.getConsentThrough)
                println("Record Consent DataProcessingBasis: " + consent.getDataProcessingBasis)
                //To get custom values
                println("Record Consent Lawful Reason: " + consent.getKeyValue("Lawful_Reason"))
              }
              else println(keyName + ": " + value)
            })
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
   * <h3> Update Related Records Using External Id</h3>
   * This method is used to update the relation between the records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to update related records.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @throws Exception
   */
  @throws[Exception]
  def updateRelatedRecordsUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String): Unit = { //API Name of the module
    val xExternal = "Leads.External,Products.Products_External"
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Record instances
    val records = new ArrayBuffer[Record]
    //Get instance of Record Class
    val record1 = new Record
    /*
      * Call addKeyValue method that takes two arguments
      * 1 -> A string that is the Field's API Name
      * 2 -> Value
      */
    record1.addKeyValue("Products_External", "TestExternalLead121")
    record1.addKeyValue("Note_Content", "asd")
    //Add Record instance to the list
    records.addOne(record1)
    val record2 = new Record
    record2.addKeyValue("Products_External", "TestExternal122")
    record2.addKeyValue("Note_Content", "asd")
    records.addOne(record2)
    //Set the list to Records in BodyWrapper instance
    request.setData(records)
    //Call updateRelatedRecordsUsingExternalId method that takes externalValue and BodyWrapper instance as parameter.
    val responseOption = relatedRecordsOperations.updateRelatedRecordsUsingExternalId(externalValue, request)
    if (responseOption.isDefined) {
      var response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getData

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
   * <h3> Delete RelatedRecords Using External Id </h3>
   * This method is used to delete the association between modules and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to delink related records.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param relatedListIds     - The List of related record IDs.
   * @throws Exception
   */
  @throws[Exception]
  def deleteRelatedRecordsUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String, relatedListIds: ArrayBuffer[String]): Unit = { //List<Long> relatedListIds = new ArrayList<Long>(Arrays.asList(3477061000005919001l, 3477061000005917011l))
    val xExternal = "Leads.External,Products.Products_External"
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    val paramInstance = new ParameterMap

    for (relatedListId <- relatedListIds) {
      paramInstance.add(new DelinkRecordsParam().ids, relatedListId)
    }
    //Call deleteRelatedRecordsUsingExternalId method that takes externalValue and paramInstance instance as parameter.
    val responseOption = relatedRecordsOperations.deleteRelatedRecordsUsingExternalId(externalValue, Option(paramInstance))
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData

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
   * <h3> Get Related Record </h3>
   * This method is used to get the single related list record and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to get related record.
   * @param recordId           - The ID of the record to be get Related List.
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param relatedListId      - The ID of the related record.
   * @param destinationFolder  - The absolute path of the destination folder to store the file.
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedRecord(moduleAPIName: String, recordId: Long, relatedListAPIName: String, relatedListId: Long, destinationFolder: String): Unit = { //Long relatedListId = 3477061000004994115l
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    val headerInstance = new HeaderMap
    val startdatetime = OffsetDateTime.of(2019, 6, 1, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetRelatedRecordHeader().IfModifiedSince, startdatetime)
    //Call getRelatedRecord method that takes headerInstance and relatedRecordId as parameter
    val responseOption = relatedRecordsOperations.getRelatedRecord(relatedListId, recordId, Option(headerInstance ))
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
          //Get the list of obtained Record instances
          val records = responseWrapper.getData

          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            var createdByOption = record.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              var createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            var modifiedByOption = record.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              var modifiedBy =modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //Get the list of Tag instance each Record
            val tags = record.getTag
            //Check if tags is not null
            if (tags.length > 0) {

              for (tag <- tags) { //Get the Name of each Tag
                println("Record Tag Name: " + tag.getName)
                //Get the Id of each Tag
                println("Record Tag ID: " + tag.getId)
              }
            }
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              var value = entry._2
              if (value.isInstanceOf[Option[_]]) value = value.asInstanceOf[Option[_]].getOrElse(null)
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                if (dataList.size > 0) if (dataList(0).isInstanceOf[FileDetails]) {
                  @SuppressWarnings(Array("unchecked")) val fileDetails = value.asInstanceOf[ArrayBuffer[FileDetails]]

                  for (fileDetail <- fileDetails) { //Get the Extn of each FileDetails
                    println("Record FileDetails Extn: " + fileDetail.getExtn)
                    //Get the IsPreviewAvailable of each FileDetails
                    println("Record FileDetails IsPreviewAvailable: " + fileDetail.getIsPreviewAvailable)
                    //Get the DownloadUrl of each FileDetails
                    println("Record FileDetails DownloadUrl: " + fileDetail.getDownloadUrl)
                    //Get the DeleteUrl of each FileDetails
                    println("Record FileDetails DeleteUrl: " + fileDetail.getDeleteUrl)
                    //Get the EntityId of each FileDetails
                    println("Record FileDetails EntityId: " + fileDetail.getEntityId)
                    //Get the Mode of each FileDetails
                    println("Record FileDetails Mode: " + fileDetail.getMode)
                    //Get the OriginalSizeByte of each FileDetails
                    println("Record FileDetails OriginalSizeByte: " + fileDetail.getOriginalSizeByte)
                    //Get the PreviewUrl of each FileDetails
                    println("Record FileDetails PreviewUrl: " + fileDetail.getPreviewUrl)
                    //Get the FileName of each FileDetails
                    println("Record FileDetails FileName: " + fileDetail.getFileName)
                    //Get the FileId of each FileDetails
                    println("Record FileDetails FileId: " + fileDetail.getFileId)
                    //Get the AttachmentId of each FileDetails
                    println("Record FileDetails AttachmentId: " + fileDetail.getAttachmentId)
                    //Get the FileSize of each FileDetails
                    println("Record FileDetails FileSize: " + fileDetail.getFileSize)
                    //Get the CreatorId of each FileDetails
                    println("Record FileDetails CreatorId: " + fileDetail.getCreatorId)
                    //Get the LinkDocs of each FileDetails
                    println("Record FileDetails LinkDocs: " + fileDetail.getLinkDocs)
                  }
                }
                else if (dataList(0).isInstanceOf[Choice[_]]) {
                  @SuppressWarnings(Array("unchecked")) val choiceList = dataList.asInstanceOf[(ArrayBuffer[Choice[_$1]]) forSome {type _$1}]
                  println(keyName)
                  println("values")

                  for (choice <- choiceList) {
                    println(choice.getValue)
                  }
                }
                else if (dataList(0).isInstanceOf[InventoryLineItems]) {
                  @SuppressWarnings(Array("unchecked")) val productDetails = value.asInstanceOf[ArrayBuffer[InventoryLineItems]]

                  for (productDetail <- productDetails) {
                    val lineItemProductOption = productDetail.getProduct
                    if (lineItemProductOption.isDefined) {
                      val lineItemProduct= lineItemProductOption.get
                      println("Record ProductDetails LineItemProduct ProductCode: " + lineItemProduct.getProductCode)
                      println("Record ProductDetails LineItemProduct Currency: " + lineItemProduct.getCurrency)
                      println("Record ProductDetails LineItemProduct Name: " + lineItemProduct.getName)
                      println("Record ProductDetails LineItemProduct Id: " + lineItemProduct.getId)
                    }
                    println("Record ProductDetails Quantity: " + productDetail.getQuantity.toString)
                    println("Record ProductDetails Discount: " + productDetail.getDiscount)
                    println("Record ProductDetails TotalAfterDiscount: " + productDetail.getTotalAfterDiscount.toString)
                    println("Record ProductDetails NetTotal: " + productDetail.getNetTotal.toString)
                    if (productDetail.getBook.isDefined) println("Record ProductDetails Book: " + productDetail.getBook.toString)
                    println("Record ProductDetails Tax: " + productDetail.getTax.toString)
                    println("Record ProductDetails ListPrice: " + productDetail.getListPrice.toString)
                    println("Record ProductDetails UnitPrice: " + productDetail.getUnitPrice)
                    println("Record ProductDetails QuantityInStock: " + productDetail.getQuantityInStock.toString)
                    println("Record ProductDetails Total: " + productDetail.getTotal.toString)
                    println("Record ProductDetails ID: " + productDetail.getId)
                    println("Record ProductDetails ProductDescription: " + productDetail.getProductDescription)
                    val lineTaxes = productDetail.getLineTax

                    for (lineTax <- lineTaxes) {
                      println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                      println("Record ProductDetails LineTax Name: " + lineTax.getName)
                      println("Record ProductDetails LineTax Id: " + lineTax.getId)
                      println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                    }
                  }
                }
                else if (dataList(0).isInstanceOf[Tag]) {
                  @SuppressWarnings(Array("unchecked")) val tagList = value.asInstanceOf[ArrayBuffer[Tag]]

                  for (tag <- tagList) {
                    println("Record Tag Name: " + tag.getName)
                    println("Record Tag ID: " + tag.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[PricingDetails]) {
                  @SuppressWarnings(Array("unchecked")) val pricingDetails = value.asInstanceOf[ ArrayBuffer[PricingDetails]]

                  for (pricingDetail <- pricingDetails) {
                    println("Record PricingDetails ToRange: " + pricingDetail.getToRange.toString)
                    println("Record PricingDetails Discount: " + pricingDetail.getDiscount.toString)
                    println("Record PricingDetails ID: " + pricingDetail.getId)
                    println("Record PricingDetails FromRange: " + pricingDetail.getFromRange.toString)
                  }
                }
                else if(dataList(0).isInstanceOf[Participants])
                {
                  val participants = value.asInstanceOf[ ArrayBuffer[Participants]]
                  for (participant <- participants) {
                    println("Record Participants Name: " + participant.getName().toString)
                    println("Record Participants Invited: " + participant.getInvited().toString())
                    println("Record Participants ID: " + participant.getId().toString)
                    println("Record Participants Type: " + participant.getType().toString)
                    println("Record Participants Participant: " + participant.getParticipant().toString)
                    println("Record Participants Status: " + participant.getStatus().toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Record]) {
                  @SuppressWarnings(Array("unchecked")) val recordList = dataList.asInstanceOf[ ArrayBuffer[Record]]

                  for (record1 <- recordList) { //Get the details map


                    record1.getKeyValues.foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                }
                else if (dataList(0).isInstanceOf[LineTax]) {
                  @SuppressWarnings(Array("unchecked")) val lineTaxes = dataList.asInstanceOf[ ArrayBuffer[LineTax]]

                  for (lineTax <- lineTaxes) {
                    println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                    println("Record ProductDetails LineTax Name: " + lineTax.getName)
                    println("Record ProductDetails LineTax Id: " + lineTax.getId)
                    println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Comment]) {
                  @SuppressWarnings(Array("unchecked")) val comments = dataList.asInstanceOf[ ArrayBuffer[Comment]]

                  for (comment <- comments) {
                    println("Record Comment CommentedBy: " + comment.getCommentedBy)
                    println("Record Comment CommentedTime: " + comment.getCommentedTime.toString)
                    println("Record Comment CommentContent: " + comment.getCommentContent)
                    println("Record Comment Id: " + comment.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[Attachment]) { //Get the list of obtained Attachment instances
                  @SuppressWarnings(Array("unchecked")) val attachments = dataList.asInstanceOf[ ArrayBuffer[Attachment]]


                  for (attachment <- attachments) { //Get the owner User instance of each attachment
                    val ownerOption = attachment.getOwner
                    //Check if owner is not null
                    if (ownerOption.isDefined) { //Get the Name of the Owner
                      var owner = ownerOption.get
                      println("Record Attachment Owner User-Name: " + owner.getName)
                      //Get the ID of the Owner
                      println("Record Attachment Owner User-ID: " + owner.getId)
                      //Get the Email of the Owner
                      println("Record Attachment Owner User-Email: " + owner.getEmail)
                    }
                    //Get the modified time of each attachment
                    println("Record Attachment Modified Time: " + attachment.getModifiedTime.toString)
                    //Get the name of the File
                    println("Record Attachment File Name: " + attachment.getFileName)
                    //Get the created time of each attachment
                    println("Record Attachment Created Time: " + attachment.getCreatedTime.toString)
                    //Get the Attachment file size
                    println("Record Attachment File Size: " + attachment.getSize.toString)
                    //Get the parentId Record instance of each attachment
                    val parentIdOption = attachment.getParentId
                    //Check if parentId is not null
                    if (parentIdOption.isDefined) { //Get the parent record Name of each attachment
                      var parentId =parentIdOption.get
                      println("Record Attachment parent record Name: " + parentId.getKeyValue("name"))
                      //Get the parent record ID of each attachment
                      println("Record Attachment parent record ID: " + parentId.getId)
                    }
                    //Get the attachment is Editable
                    println("Record Attachment is Editable: " + attachment.geteditable().toString)
                    //Get the file ID of each attachment
                    println("Record Attachment File ID: " + attachment.getfileId())
                    //Get the type of each attachment
                    println("Record Attachment File Type: " + attachment.gettype)
                    //Get the seModule of each attachment
                    println("Record Attachment seModule: " + attachment.getseModule())
                    //Get the modifiedBy User instance of each attachment
                    var modifiedByOption = attachment.getModifiedBy
                    if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
                      var modifiedBy = modifiedByOption.get
                      println("Record Attachment Modified By User-Name: " + modifiedBy.getName)
                      println("Record Attachment Modified By User-ID: " + modifiedBy.getId)
                      println("Record Attachment Modified By User-Email: " + modifiedBy.getEmail)
                    }
                    //Get the state of each attachment
                    println("Record Attachment State: " + attachment.getstate)
                    //Get the ID of each attachment
                    println("Record Attachment ID: " + attachment.getId)
                    //Get the createdBy User instance of each attachment
                    var createdByOption = attachment.getCreatedBy
                    if (createdByOption.isDefined) {
                      var createdBy = modifiedByOption.get

                      println("Record Attachment Created By User-Name: " + createdBy.getName)
                      println("Record Attachment Created By User-ID: " + createdBy.getId)
                      println("Record Attachment Created By User-Email: " + createdBy.getEmail)
                    }
                    //Get the linkUrl of each attachment
                    println("Record Attachment LinkUrl: " + attachment.getlinkUrl())
                  }
                }
                else println(keyName + ": " + value)
              }
              else if (value.isInstanceOf[Record]) {
                val recordValue = value.asInstanceOf[Record]
                println("Record " + keyName + " ID: " + recordValue.getId)
                println("Record " + keyName + " Name: " + recordValue.getKeyValue("name"))
              }
              else if (value.isInstanceOf[Layout]) {
                val layout = value.asInstanceOf[Layout]
                if (layout != null) {
                  println("Record " + keyName + " ID: " + layout.getId)
                  println("Record " + keyName + " Name: " + layout.getName)
                }
              }
              else if (value.isInstanceOf[User]) {
                val user = value.asInstanceOf[User]
                if (user != null) {
                  println("Record " + keyName + " User-ID: " + user.getId)
                  println("Record " + keyName + " User-Name: " + user.getName)
                  println("Record " + keyName + " User-Email: " + user.getEmail)
                }
              }
              else if (value.isInstanceOf[Choice[_]]) println(keyName + ": " + value.asInstanceOf[Choice[_]].getValue)
              else if (value.isInstanceOf[RemindAt]) println(keyName + ": " + value.asInstanceOf[RemindAt].getAlarm)
              else if (value.isInstanceOf[RecurringActivity]) {
                println(keyName)
                println("RRULE" + ": " + value.asInstanceOf[RecurringActivity].getRrule)
              }
              else if (value.isInstanceOf[Consent]) {
                val consent = value.asInstanceOf[Consent]
                println("Record Consent ID: " + consent.getId)
                //Get the Owner User instance of each attachment
                val ownerOption = consent.getOwner
                if (ownerOption.isDefined) { //Get the name of the owner User
                  var owner = ownerOption.get
                  println("Record Consent Owner Name: " + owner.getName)
                  //Get the ID of the owner User
                  println("Record Consent Owner ID: " + owner.getId)
                  //Get the Email of the owner User
                  println("Record Consent Owner Email: " + owner.getEmail)
                }
                val consentCreatedByOption = consent.getCreatedBy
                if (consentCreatedByOption.isDefined) { //Get the name of the CreatedBy User
                  var consentCreatedBy = consentCreatedByOption.get
                  println("Record Consent CreatedBy Name: " + consentCreatedBy.getName)
                  //Get the ID of the CreatedBy User
                  println("Record Consent CreatedBy ID: " + consentCreatedBy.getId)
                  //Get the Email of the CreatedBy User
                  println("Record Consent CreatedBy Email: " + consentCreatedBy.getEmail)
                }
                val consentModifiedByOption = consent.getModifiedBy
                if (consentModifiedByOption.isDefined) { //Get the name of the ModifiedBy User
                  var consentModifiedBy = consentModifiedByOption.get
                  println("Record Consent ModifiedBy Name: " + consentModifiedBy.getName)
                  //Get the ID of the ModifiedBy User
                  println("Record Consent ModifiedBy ID: " + consentModifiedBy.getId)
                  //Get the Email of the ModifiedBy User
                  println("Record Consent ModifiedBy Email: " + consentModifiedBy.getEmail)
                }
                println("Record Consent CreatedTime: " + consent.getCreatedTime)
                println("Record Consent ModifiedTime: " + consent.getModifiedTime)
                println("Record Consent ContactThroughEmail: " + consent.getContactThroughEmail)
                println("Record Consent ContactThroughSocial: " + consent.getContactThroughSocial)
                println("Record Consent ContactThroughSurvey: " + consent.getContactThroughSurvey)
                println("Record Consent ContactThroughPhone: " + consent.getContactThroughPhone)
                println("Record Consent MailSentTime: " + consent.getMailSentTime.toString)
                println("Record Consent ConsentDate: " + consent.getConsentDate.toString)
                println("Record Consent ConsentRemarks: " + consent.getConsentRemarks)
                println("Record Consent ConsentThrough: " + consent.getConsentThrough)
                println("Record Consent DataProcessingBasis: " + consent.getDataProcessingBasis)
                //To get custom values
                println("Record Consent Lawful Reason: " + consent.getKeyValue("Lawful_Reason"))
              }
              else println(keyName + ": " + value)
            })
          }
        }
        else if (responseHandler.isInstanceOf[FileBodyWrapper]) {
          val fileBodyWrapper = responseHandler.asInstanceOf[FileBodyWrapper]
          //Get StreamWrapper instance from the returned FileBodyWrapper instance
          val streamWrapper = fileBodyWrapper.getFile.get
          //Create a file instance with the absolute_file_path
          val file = new File(destinationFolder + File.separatorChar + streamWrapper.getName.get)
          //Get InputStream from the response
          val is = streamWrapper.getStream.get
          //Create an OutputStream for the destination file
          val os = new FileOutputStream(file)
          val buffer = new Array[Byte](1024)
          var bytesRead = 0
          //read the InputStream till the end
          while ( {
            (bytesRead = is.read(buffer)) != -1
          }) { //write data to OutputStream
            os.write(buffer, 0, bytesRead)
          }
          //Close the InputStream
          is.close()
          //Flush and close the OutputStream
          os.flush()
          os.close()
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
   * <h3> Update Related Record </h3>
   * This method is used to update the relation between the records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to update related record.
   * @param recordId           - The ID of the record to be obtained.
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param relatedListId      - The ID of the related record.
   * @throws Exception
   */
  @throws[Exception]
  def updateRelatedRecord(moduleAPIName: String, recordId: Long, relatedListAPIName: String, relatedListId: Long): Unit = {
    val relatedRecordsOperations =new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    val request = new BodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.addKeyValue("Note_Content", "asd")
    records.addOne(record1)
    request.setData(records)
    //Call updateRelatedRecord method that takes BodyWrapper instance, relatedRecordId as parameter.
    val responseOption = relatedRecordsOperations.updateRelatedRecord(relatedListId, recordId, request)
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData
           
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
   * <h3> Delink Record </h3>
   * This method is used to delete the association between modules and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to delink related record.
   * @param recordId           - The ID of the record to be obtained.
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param relatedListId      - The ID of the related record.
   * @throws Exception
   */
  @throws[Exception]
  def delinkRecord(moduleAPIName: String, recordId: Long, relatedListAPIName: String, relatedListId: Long): Unit = {
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName)
    //Call delinkRecord method that takes relatedListId as parameter.
    val responseOption = relatedRecordsOperations.delinkRecord(relatedListId, recordId)
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData
           
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
   * <h3> Get Related Record Using External Id </h3>
   * This method is used to get the single related list record and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to get related record.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param externalFieldValue -
   * @param destinationFolder  - The absolute path of the destination folder to store the file.
   * @throws Exception
   */
  @throws[Exception]
  def getRelatedRecordUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String, externalFieldValue: String, destinationFolder: String): Unit = { //Long relatedListId = 3477061000004994115l
    val xExternal = "Leads.External,Products.Products_External"
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    val headerInstance = new HeaderMap
    val startdatetime = OffsetDateTime.of(2019, 6, 1, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetRelatedRecordHeader().IfModifiedSince, startdatetime)
    //Call getRelatedRecordUsingExternalId method that takes headerInstance and relatedRecordId as parameter
    val responseOption = relatedRecordsOperations.getRelatedRecordUsingExternalId(externalFieldValue, externalValue, Option(headerInstance))
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
          //Get the list of obtained Record instances
          val records = responseWrapper.getData

          for (record <- records) { //Get the ID of each Record
            println("Record ID: " + record.getId)
            //Get the createdBy User instance of each Record
            var createdByOption = record.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              var createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Record Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            //Get the CreatedTime of each Record
            println("Record CreatedTime: " + record.getCreatedTime)
            //Get the modifiedBy User instance of each Record
            var modifiedByOption = record.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              var modifiedBy =modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Record Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the ModifiedTime of each Record
            println("Record ModifiedTime: " + record.getModifiedTime)
            //Get the list of Tag instance each Record
            val tags = record.getTag
            //Check if tags is not null
            if (tags.length > 0) {

              for (tag <- tags) { //Get the Name of each Tag
                println("Record Tag Name: " + tag.getName)
                //Get the Id of each Tag
                println("Record Tag ID: " + tag.getId)
              }
            }
            //To get particular field value
            println("Record Field Value: " + record.getKeyValue("Last_Name")) // FieldApiName

            println("Record KeyValues: ")
            //Get the KeyValue map
            record.getKeyValues.foreach(entry=>{
              val keyName = entry._1
              var value = entry._2
              if (value.isInstanceOf[Option[_]]) value = value.asInstanceOf[Option[_]].getOrElse(null)
              if (value.isInstanceOf[ArrayBuffer[Any]]) {
                val dataList = value.asInstanceOf[ArrayBuffer[Any]]
                if (dataList.size > 0) if (dataList(0).isInstanceOf[FileDetails]) {
                  @SuppressWarnings(Array("unchecked")) val fileDetails = value.asInstanceOf[ArrayBuffer[FileDetails]]

                  for (fileDetail <- fileDetails) { //Get the Extn of each FileDetails
                    println("Record FileDetails Extn: " + fileDetail.getExtn)
                    //Get the IsPreviewAvailable of each FileDetails
                    println("Record FileDetails IsPreviewAvailable: " + fileDetail.getIsPreviewAvailable)
                    //Get the DownloadUrl of each FileDetails
                    println("Record FileDetails DownloadUrl: " + fileDetail.getDownloadUrl)
                    //Get the DeleteUrl of each FileDetails
                    println("Record FileDetails DeleteUrl: " + fileDetail.getDeleteUrl)
                    //Get the EntityId of each FileDetails
                    println("Record FileDetails EntityId: " + fileDetail.getEntityId)
                    //Get the Mode of each FileDetails
                    println("Record FileDetails Mode: " + fileDetail.getMode)
                    //Get the OriginalSizeByte of each FileDetails
                    println("Record FileDetails OriginalSizeByte: " + fileDetail.getOriginalSizeByte)
                    //Get the PreviewUrl of each FileDetails
                    println("Record FileDetails PreviewUrl: " + fileDetail.getPreviewUrl)
                    //Get the FileName of each FileDetails
                    println("Record FileDetails FileName: " + fileDetail.getFileName)
                    //Get the FileId of each FileDetails
                    println("Record FileDetails FileId: " + fileDetail.getFileId)
                    //Get the AttachmentId of each FileDetails
                    println("Record FileDetails AttachmentId: " + fileDetail.getAttachmentId)
                    //Get the FileSize of each FileDetails
                    println("Record FileDetails FileSize: " + fileDetail.getFileSize)
                    //Get the CreatorId of each FileDetails
                    println("Record FileDetails CreatorId: " + fileDetail.getCreatorId)
                    //Get the LinkDocs of each FileDetails
                    println("Record FileDetails LinkDocs: " + fileDetail.getLinkDocs)
                  }
                }
                else if (dataList(0).isInstanceOf[Choice[_]]) {
                  @SuppressWarnings(Array("unchecked")) val choiceList = dataList.asInstanceOf[(ArrayBuffer[Choice[_$1]]) forSome {type _$1}]
                  println(keyName)
                  println("values")

                  for (choice <- choiceList) {
                    println(choice.getValue)
                  }
                }
                else if (dataList(0).isInstanceOf[InventoryLineItems]) {
                  @SuppressWarnings(Array("unchecked")) val productDetails = value.asInstanceOf[ArrayBuffer[InventoryLineItems]]

                  for (productDetail <- productDetails) {
                    val lineItemProductOption = productDetail.getProduct
                    if (lineItemProductOption.isDefined) {
                      val lineItemProduct= lineItemProductOption.get
                      println("Record ProductDetails LineItemProduct ProductCode: " + lineItemProduct.getProductCode)
                      println("Record ProductDetails LineItemProduct Currency: " + lineItemProduct.getCurrency)
                      println("Record ProductDetails LineItemProduct Name: " + lineItemProduct.getName)
                      println("Record ProductDetails LineItemProduct Id: " + lineItemProduct.getId)
                    }
                    println("Record ProductDetails Quantity: " + productDetail.getQuantity.toString)
                    println("Record ProductDetails Discount: " + productDetail.getDiscount)
                    println("Record ProductDetails TotalAfterDiscount: " + productDetail.getTotalAfterDiscount.toString)
                    println("Record ProductDetails NetTotal: " + productDetail.getNetTotal.toString)
                    if (productDetail.getBook.isDefined) println("Record ProductDetails Book: " + productDetail.getBook.toString)
                    println("Record ProductDetails Tax: " + productDetail.getTax.toString)
                    println("Record ProductDetails ListPrice: " + productDetail.getListPrice.toString)
                    println("Record ProductDetails UnitPrice: " + productDetail.getUnitPrice)
                    println("Record ProductDetails QuantityInStock: " + productDetail.getQuantityInStock.toString)
                    println("Record ProductDetails Total: " + productDetail.getTotal.toString)
                    println("Record ProductDetails ID: " + productDetail.getId)
                    println("Record ProductDetails ProductDescription: " + productDetail.getProductDescription)
                    val lineTaxes = productDetail.getLineTax

                    for (lineTax <- lineTaxes) {
                      println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                      println("Record ProductDetails LineTax Name: " + lineTax.getName)
                      println("Record ProductDetails LineTax Id: " + lineTax.getId)
                      println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                    }
                  }
                }
                else if (dataList(0).isInstanceOf[Tag]) {
                  @SuppressWarnings(Array("unchecked")) val tagList = value.asInstanceOf[ArrayBuffer[Tag]]

                  for (tag <- tagList) {
                    println("Record Tag Name: " + tag.getName)
                    println("Record Tag ID: " + tag.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[PricingDetails]) {
                  @SuppressWarnings(Array("unchecked")) val pricingDetails = value.asInstanceOf[ ArrayBuffer[PricingDetails]]

                  for (pricingDetail <- pricingDetails) {
                    println("Record PricingDetails ToRange: " + pricingDetail.getToRange.toString)
                    println("Record PricingDetails Discount: " + pricingDetail.getDiscount.toString)
                    println("Record PricingDetails ID: " + pricingDetail.getId)
                    println("Record PricingDetails FromRange: " + pricingDetail.getFromRange.toString)
                  }
                }
                else if(dataList(0).isInstanceOf[Participants])
                {
                  val participants = value.asInstanceOf[ ArrayBuffer[Participants]]
                  for (participant <- participants) {
                    println("Record Participants Name: " + participant.getName().toString)
                    println("Record Participants Invited: " + participant.getInvited().toString())
                    println("Record Participants ID: " + participant.getId().toString)
                    println("Record Participants Type: " + participant.getType().toString)
                    println("Record Participants Participant: " + participant.getParticipant().toString)
                    println("Record Participants Status: " + participant.getStatus().toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Record]) {
                  @SuppressWarnings(Array("unchecked")) val recordList = dataList.asInstanceOf[ ArrayBuffer[Record]]

                  for (record1 <- recordList) { //Get the details map


                    record1.getKeyValues.foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                }
                else if (dataList(0).isInstanceOf[LineTax]) {
                  @SuppressWarnings(Array("unchecked")) val lineTaxes = dataList.asInstanceOf[ ArrayBuffer[LineTax]]

                  for (lineTax <- lineTaxes) {
                    println("Record ProductDetails LineTax Percentage: " + lineTax.getPercentage.toString)
                    println("Record ProductDetails LineTax Name: " + lineTax.getName)
                    println("Record ProductDetails LineTax Id: " + lineTax.getId)
                    println("Record ProductDetails LineTax Value: " + lineTax.getValue.toString)
                  }
                }
                else if (dataList(0).isInstanceOf[Comment]) {
                  @SuppressWarnings(Array("unchecked")) val comments = dataList.asInstanceOf[ ArrayBuffer[Comment]]

                  for (comment <- comments) {
                    println("Record Comment CommentedBy: " + comment.getCommentedBy)
                    println("Record Comment CommentedTime: " + comment.getCommentedTime.toString)
                    println("Record Comment CommentContent: " + comment.getCommentContent)
                    println("Record Comment Id: " + comment.getId)
                  }
                }
                else if (dataList(0).isInstanceOf[Attachment]) { //Get the list of obtained Attachment instances
                  @SuppressWarnings(Array("unchecked")) val attachments = dataList.asInstanceOf[ ArrayBuffer[Attachment]]


                  for (attachment <- attachments) { //Get the owner User instance of each attachment
                    val ownerOption = attachment.getOwner
                    //Check if owner is not null
                    if (ownerOption.isDefined) { //Get the Name of the Owner
                      var owner = ownerOption.get
                      println("Record Attachment Owner User-Name: " + owner.getName)
                      //Get the ID of the Owner
                      println("Record Attachment Owner User-ID: " + owner.getId)
                      //Get the Email of the Owner
                      println("Record Attachment Owner User-Email: " + owner.getEmail)
                    }
                    //Get the modified time of each attachment
                    println("Record Attachment Modified Time: " + attachment.getModifiedTime.toString)
                    //Get the name of the File
                    println("Record Attachment File Name: " + attachment.getFileName)
                    //Get the created time of each attachment
                    println("Record Attachment Created Time: " + attachment.getCreatedTime.toString)
                    //Get the Attachment file size
                    println("Record Attachment File Size: " + attachment.getSize.toString)
                    //Get the parentId Record instance of each attachment
                    val parentIdOption = attachment.getParentId
                    //Check if parentId is not null
                    if (parentIdOption.isDefined) { //Get the parent record Name of each attachment
                      var parentId =parentIdOption.get
                      println("Record Attachment parent record Name: " + parentId.getKeyValue("name"))
                      //Get the parent record ID of each attachment
                      println("Record Attachment parent record ID: " + parentId.getId)
                    }
                    //Get the attachment is Editable
                    println("Record Attachment is Editable: " + attachment.geteditable().toString)
                    //Get the file ID of each attachment
                    println("Record Attachment File ID: " + attachment.getfileId())
                    //Get the type of each attachment
                    println("Record Attachment File Type: " + attachment.gettype)
                    //Get the seModule of each attachment
                    println("Record Attachment seModule: " + attachment.getseModule())
                    //Get the modifiedBy User instance of each attachment
                    var modifiedByOption = attachment.getModifiedBy
                    if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
                      var modifiedBy = modifiedByOption.get
                      println("Record Attachment Modified By User-Name: " + modifiedBy.getName)
                      println("Record Attachment Modified By User-ID: " + modifiedBy.getId)
                      println("Record Attachment Modified By User-Email: " + modifiedBy.getEmail)
                    }
                    //Get the state of each attachment
                    println("Record Attachment State: " + attachment.getstate)
                    //Get the ID of each attachment
                    println("Record Attachment ID: " + attachment.getId)
                    //Get the createdBy User instance of each attachment
                    var createdByOption = attachment.getCreatedBy
                    if (createdByOption.isDefined) {
                      var createdBy = modifiedByOption.get

                      println("Record Attachment Created By User-Name: " + createdBy.getName)
                      println("Record Attachment Created By User-ID: " + createdBy.getId)
                      println("Record Attachment Created By User-Email: " + createdBy.getEmail)
                    }
                    //Get the linkUrl of each attachment
                    println("Record Attachment LinkUrl: " + attachment.getlinkUrl())
                  }
                }
                else println(keyName + ": " + value)
              }
              else if (value.isInstanceOf[Record]) {
                val recordValue = value.asInstanceOf[Record]
                println("Record " + keyName + " ID: " + recordValue.getId)
                println("Record " + keyName + " Name: " + recordValue.getKeyValue("name"))
              }
              else if (value.isInstanceOf[Layout]) {
                val layout = value.asInstanceOf[Layout]
                if (layout != null) {
                  println("Record " + keyName + " ID: " + layout.getId)
                  println("Record " + keyName + " Name: " + layout.getName)
                }
              }
              else if (value.isInstanceOf[User]) {
                val user = value.asInstanceOf[User]
                if (user != null) {
                  println("Record " + keyName + " User-ID: " + user.getId)
                  println("Record " + keyName + " User-Name: " + user.getName)
                  println("Record " + keyName + " User-Email: " + user.getEmail)
                }
              }
              else if (value.isInstanceOf[Choice[_]]) println(keyName + ": " + value.asInstanceOf[Choice[_]].getValue)
              else if (value.isInstanceOf[RemindAt]) println(keyName + ": " + value.asInstanceOf[RemindAt].getAlarm)
              else if (value.isInstanceOf[RecurringActivity]) {
                println(keyName)
                println("RRULE" + ": " + value.asInstanceOf[RecurringActivity].getRrule)
              }
              else if (value.isInstanceOf[Consent]) {
                val consent = value.asInstanceOf[Consent]
                println("Record Consent ID: " + consent.getId)
                //Get the Owner User instance of each attachment
                val ownerOption = consent.getOwner
                if (ownerOption.isDefined) { //Get the name of the owner User
                  var owner = ownerOption.get
                  println("Record Consent Owner Name: " + owner.getName)
                  //Get the ID of the owner User
                  println("Record Consent Owner ID: " + owner.getId)
                  //Get the Email of the owner User
                  println("Record Consent Owner Email: " + owner.getEmail)
                }
                val consentCreatedByOption = consent.getCreatedBy
                if (consentCreatedByOption.isDefined) { //Get the name of the CreatedBy User
                  var consentCreatedBy = consentCreatedByOption.get
                  println("Record Consent CreatedBy Name: " + consentCreatedBy.getName)
                  //Get the ID of the CreatedBy User
                  println("Record Consent CreatedBy ID: " + consentCreatedBy.getId)
                  //Get the Email of the CreatedBy User
                  println("Record Consent CreatedBy Email: " + consentCreatedBy.getEmail)
                }
                val consentModifiedByOption = consent.getModifiedBy
                if (consentModifiedByOption.isDefined) { //Get the name of the ModifiedBy User
                  var consentModifiedBy = consentModifiedByOption.get
                  println("Record Consent ModifiedBy Name: " + consentModifiedBy.getName)
                  //Get the ID of the ModifiedBy User
                  println("Record Consent ModifiedBy ID: " + consentModifiedBy.getId)
                  //Get the Email of the ModifiedBy User
                  println("Record Consent ModifiedBy Email: " + consentModifiedBy.getEmail)
                }
                println("Record Consent CreatedTime: " + consent.getCreatedTime)
                println("Record Consent ModifiedTime: " + consent.getModifiedTime)
                println("Record Consent ContactThroughEmail: " + consent.getContactThroughEmail)
                println("Record Consent ContactThroughSocial: " + consent.getContactThroughSocial)
                println("Record Consent ContactThroughSurvey: " + consent.getContactThroughSurvey)
                println("Record Consent ContactThroughPhone: " + consent.getContactThroughPhone)
                println("Record Consent MailSentTime: " + consent.getMailSentTime.toString)
                println("Record Consent ConsentDate: " + consent.getConsentDate.toString)
                println("Record Consent ConsentRemarks: " + consent.getConsentRemarks)
                println("Record Consent ConsentThrough: " + consent.getConsentThrough)
                println("Record Consent DataProcessingBasis: " + consent.getDataProcessingBasis)
                //To get custom values
                println("Record Consent Lawful Reason: " + consent.getKeyValue("Lawful_Reason"))
              }
              else println(keyName + ": " + value)
            })
          }
        }
        else if (responseHandler.isInstanceOf[FileBodyWrapper]) {
          val fileBodyWrapper = responseHandler.asInstanceOf[FileBodyWrapper]
          //Get StreamWrapper instance from the returned FileBodyWrapper instance
          val streamWrapper = fileBodyWrapper.getFile.get
          //Create a file instance with the absolute_file_path
          val file = new File(destinationFolder + File.separatorChar + streamWrapper.getName.get)
          //Get InputStream from the response
          val is = streamWrapper.getStream.get
          //Create an OutputStream for the destination file
          val os = new FileOutputStream(file)
          val buffer = new Array[Byte](1024)
          var bytesRead = 0
          //read the InputStream till the end
          while ( {
            (bytesRead = is.read(buffer)) != -1
          }) { //write data to OutputStream
            os.write(buffer, 0, bytesRead)
          }
          //Close the InputStream
          is.close()
          //Flush and close the OutputStream
          os.flush()
          os.close()
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
   * <h3> Update Related Record Using External Id </h3>
   * This method is used to update the relation between the records and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to update related record.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param externalFieldValue -
   * @throws Exception
   */
  @throws[Exception]
  def updateRelatedRecordUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String, externalFieldValue: String): Unit = {
    val xExternal = "Leads.External,Products.Products_External"
    val relatedRecordsOperations =new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    val request = new BodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.addKeyValue("Note_Content", "asd")
    records.addOne(record1)
    request.setData(records)
    //Call updateRelatedRecordUsingExternalId method that takes externalFieldValue, externalValue and BodyWrapper instance as parameter.
    val responseOption = relatedRecordsOperations.updateRelatedRecordUsingExternalId(externalFieldValue, externalValue, request)
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData

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
     * <h3> Delete Related Record Using External Id </h3>
   * This method is used to delete the association between modules and print the response.
   *
   * @param moduleAPIName      - The API Name of the module to delink related record.
   * @param externalValue      -
   * @param relatedListAPIName - The API name of the related list. To get the API name of the related list.
   * @param externalFieldValue -
   * @throws Exception
   */
  @throws[Exception]
  def deleteRelatedRecordUsingExternalId(moduleAPIName: String, externalValue: String, relatedListAPIName: String, externalFieldValue: String): Unit = {
    val xExternal = "Leads.External,Products.Products_External"
    val relatedRecordsOperations = new RelatedRecordsOperations(relatedListAPIName, moduleAPIName, Option(xExternal))
    //Call deleteRelatedRecordUsingExternalId method that takes relatedListId as parameter.
    val responseOption = relatedRecordsOperations.deleteRelatedRecordUsingExternalId(externalFieldValue, externalValue)
    if (responseOption.isDefined) {
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getData

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

class Relatedrecord {}