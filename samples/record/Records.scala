package com.zoho.crm.sample.record

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util
import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.attachments.Attachment
import com.zoho.crm.api.layouts.Layout
import com.zoho.crm.api.record.Field.{Contacts, Deals, Events, Leads, Price_Books, Tasks}
import com.zoho.crm.api.record.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, Comment, Consent, ConvertActionHandler, ConvertActionResponse, ConvertActionWrapper, ConvertBodyWrapper, DeletedRecord, DeletedRecordsHandler, DeletedRecordsWrapper, DownloadHandler, Field, FileBodyWrapper, FileDetails, FileHandler, Info, InventoryLineItems, LeadConverter, LineItemProduct, LineTax, MassUpdate, MassUpdateActionHandler, MassUpdateActionResponse, MassUpdateActionWrapper, MassUpdateBodyWrapper, MassUpdateResponse, MassUpdateResponseHandler, MassUpdateResponseWrapper, MassUpdateSuccessResponse, Participants, PricingDetails, Record, RecordOperations, RecurringActivity, RemindAt, ResponseHandler, ResponseWrapper, SuccessResponse, SuccessfulConvert, Territory}
import com.zoho.crm.api.tags.Tag
import com.zoho.crm.api.record.RecordOperations.{CreateRecordsHeader, DeleteRecordHeader, DeleteRecordParam, DeleteRecordUsingExternalIDHeader, DeleteRecordUsingExternalIDParam, DeleteRecordsHeader, DeleteRecordsParam, GetDeletedRecordsHeader, GetDeletedRecordsParam, GetMassUpdateStatusParam, GetRecordHeader, GetRecordParam, GetRecordUsingExternalIDHeader, GetRecordUsingExternalIDParam, GetRecordsHeader, GetRecordsParam, SearchRecordsHeader, SearchRecordsParam, UpdateRecordHeader, UpdateRecordUsingExternalIDHeader, UpdateRecordsHeader, UpsertRecordsHeader}
import com.zoho.crm.api.users.User
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer


object Records {
  /**
   * <h3> Get Record</h3>
   * This method is used to get a single record of a module with ID and print the response.
   *
   * @param moduleAPIName     - The API Name of the record's module.
   * @param recordId          - The ID of the record to be obtained.
   * @param destinationFolder - The absolute path of the destination folder to store the file.
   * @throws Exception
   */
  @throws[Exception]
  def getRecord(moduleAPIName: String, recordId: Long, destinationFolder: String): Unit = { //example
    //Get instance of RecordOperations Class
    val recordOperations = new RecordOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap

    paramInstance.add(new GetRecordParam().approved, "both")
    paramInstance.add(new GetRecordParam().converted, "false")
    var fieldNames = new ArrayBuffer[String]()
    fieldNames += ("Company", "Email")
    fieldNames.foreach(fieldName=>{
      paramInstance.add(new GetRecordParam().fields, fieldName)
    })

    var  startdatetime = OffsetDateTime.of(2020, 7, 2, 11, 0, 1, 0, ZoneOffset.of("+05:30"))
    paramInstance.add(new GetRecordParam().startDateTime, startdatetime)
    var  enddatetime = OffsetDateTime.of(2020, 8, 2, 12, 0, 1, 0, ZoneOffset.of("+05:30"))
    paramInstance.add(new GetRecordParam().endDateTime, enddatetime)
    paramInstance.add(new GetRecordParam().territoryId, "3477061000003051357")
    paramInstance.add(new GetRecordParam().includeChild, "true")
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2019, 1, 2, 10, 0, 0, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetRecordHeader().IfModifiedSince, ifmodifiedsince)
//    headerInstance.add(new GetRecordHeader().XEXTERNAL, "Leads.External")
    //Call getRecord method that takes paramInstance, moduleAPIName and recordID as parameter
    val responseOption = recordOperations.getRecord(recordId, moduleAPIName, Option(paramInstance), Option(headerInstance))
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
   * <h3> Update Record</h3>
   * This method is used to update a single record of a module with ID and print the response.
   *
   * @param moduleAPIName - The API Name of the record's module.
   * @param recordId      - The ID of the record to be updated.
   * @throws Exception
   */
  @throws[Exception]
  def updateRecord(moduleAPIName: String, recordId: Long): Unit = { //API Name of the module
    val recordOperations = new RecordOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Record instances
    val records = new ArrayBuffer[Record]
    //Get instance of Record Class
    val record1 = new Record
    /*
     * Call addFieldValue method that takes two arguments
     * 1 -> Call Field "." and choose the module from the displayed list and press "." and choose the field name from the displayed list.
     * 2 -> Value
     */
    record1.addFieldValue(new Leads().City, "City")

    /*
     * Call addKeyValue method that takes two arguments
     * 1 -> A string that is the Field's API Name
     * 2 -> Value
     */
    record1.addKeyValue("Custom_field", "Value")
    record1.addKeyValue("Custom_field_2", "value")
    record1.addKeyValue("Date_Time_2", OffsetDateTime.of(2019, 11, 20, 10, 0, 1, 0, ZoneOffset.of("+05:30")))
    record1.addKeyValue("Date_1", LocalDate.of(2017, 1, 13))
    val fileDetails = new ArrayBuffer[FileDetails]
    val fileDetail1 = new FileDetails
    fileDetail1.setAttachmentId(Option("3477061000007410016"))
    fileDetail1.setDelete(null)
    fileDetails.addOne(fileDetail1)
    val fileDetail2 = new FileDetails
    fileDetail2.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c326ef21accd646c01e85c34b1b2e7fe45c"))
    fileDetails.addOne(fileDetail2)
    val fileDetail3 = new FileDetails
    fileDetail3.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c3256b4b32b984bad140a629d9f4d4fc8e2"))
    fileDetails.addOne(fileDetail3)
    record1.addKeyValue("File_Upload", fileDetails)
    //Used when GDPR is enabled
    val dataConsent = new Consent
    dataConsent.setConsentRemarks(Option("Approved."))
    dataConsent.setConsentThrough(Option("Email"))
    dataConsent.setContactThroughEmail(Option(true))
    dataConsent.setContactThroughSocial(Option(false))
    record1.addKeyValue("Data_Processing_Basis_Details", dataConsent)
    var  subformList = new ArrayBuffer[com.zoho.crm.api.record.Record]
    var subform = new com.zoho.crm.api.record.Record()
    subform.addKeyValue("Subform FieldAPIName", "FieldValue")
    subformList.addOne(subform)
    record1.addKeyValue("Subform Name", subformList)
    /** Following methods are being used only by Inventory modules */
    var dealName = new com.zoho.crm.api.record.Record()
    dealName.addFieldValue(new Deals().id, 3477061000004995070l)
    var contactName = new com.zoho.crm.api.record.Record()
    contactName.addFieldValue(new Contacts().id, 3477061000004977055l)
    var accountName = new com.zoho.crm.api.record.Record()
    accountName.addKeyValue("name", "automatedAccount")
    //
    var  inventoryLineItemList = new ArrayBuffer[InventoryLineItems]
    var inventoryLineItem = new InventoryLineItems()
    var lineItemProduct = new LineItemProduct()
    lineItemProduct.setId(Option(3524033000003659082L))
    inventoryLineItem.setProduct(Option(lineItemProduct))
    inventoryLineItem.setQuantity(Option(1.5))
    inventoryLineItem.setProductDescription(Option("productDescription"))
    inventoryLineItem.setListPrice(Option(10.0))
    inventoryLineItem.setDiscount(Option("5.0"))
    inventoryLineItem.setDiscount(Option("5.25%"))
    var  productLineTaxes = new ArrayBuffer[LineTax]

    var productLineTax = new LineTax()
    productLineTax.setName(Option("Sales Tax"))
    productLineTax.setPercentage(Option(20.0))
    productLineTaxes.addOne(productLineTax)
    inventoryLineItem.setLineTax(productLineTaxes)
    inventoryLineItemList.addOne(inventoryLineItem)
    //
    record1.addKeyValue("Product_Details", inventoryLineItemList)
    var  lineTaxes = new ArrayBuffer[LineTax]()
    var lineTax = new LineTax()
    lineTax.setName(Option("Sales Tax"))
    lineTax.setPercentage(Option(20.0))
    lineTaxes.addOne(lineTax)
    record1.addKeyValue("$line_tax", lineTaxes)
    /** End Inventory **/
    val tagList = new ArrayBuffer[Tag]
    val tag = new Tag
    tag.setName(Option("Testtask1"))
    tagList.addOne(tag)
    record1.setTag(tagList)
    //Add Record instance to the list
    records.addOne(record1)
    //Set the list to Records in BodyWrapper instance
    request.setData(records)
    val trigger = new ArrayBuffer[String]
    trigger.addOne("approval")
    trigger.addOne("workflow")
    trigger.addOne("blueprint")
    request.setTrigger(trigger)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new UpdateRecordHeader().XEXTERNAL, "Leads.External")
    //Call updateRecord method that takes BodyWrapper instance, ModuleAPIName and recordId as parameter.
    val responseOption = recordOperations.updateRecord(recordId, moduleAPIName, request, Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
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
   * <h3> Delete Record</h3>
   * This method is used to delete a single record of a module with ID and print the response.
   *
   * @param moduleAPIName - The API Name of the record's module.
   * @param recordId      - The ID of the record to be deleted.
   * @throws Exception
   */
  @throws[Exception]
  def deleteRecord(moduleAPIName: String, recordId: Long): Unit = { //API Name of the module to delete record
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new DeleteRecordParam().wfTrigger, false)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new DeleteRecordHeader().XEXTERNAL, "Leads.External")
    //Call deleteRecord method that takes paramInstance, ModuleAPIName and recordId as parameter.
    val responseOption = recordOperations.deleteRecord(recordId, moduleAPIName, Option(paramInstance), Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
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
     * <h3> Get Record Using External Id </h3>
   * This method is used to get a single record of a module with ID and print the response.
   *
   * @param moduleAPIName     - The API Name of the record's module.
   * @param externalFieldValue -
   * @param destinationFolder - The absolute path of the destination folder to store the file.
   * @throws Exception
   */
  @throws[Exception]
  def getRecordUsingExternalId(moduleAPIName: String, externalFieldValue: String, destinationFolder: String): Unit = { //example
    //Get instance of RecordOperations Class
    val recordOperations = new RecordOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap

//    paramInstance.add(new GetRecordUsingExternalIDParam().approved, "both")
//    paramInstance.add(new GetRecordUsingExternalIDParam().converted, "false")
//    var fieldNames = new ArrayBuffer[String]()
//    fieldNames += ("Company", "Email")
//    fieldNames.foreach(fieldName=>{
//      paramInstance.add(new GetRecordUsingExternalIDParam().fields, fieldName)
//    })
//
//    var  startdatetime = OffsetDateTime.of(2020, 7, 2, 11, 0, 1, 0, ZoneOffset.of("+05:30"))
//    paramInstance.add(new GetRecordUsingExternalIDParam().startDateTime, startdatetime)
//    var  enddatetime = OffsetDateTime.of(2020, 8, 2, 12, 0, 1, 0, ZoneOffset.of("+05:30"))
//    paramInstance.add(new GetRecordUsingExternalIDParam().endDateTime, enddatetime)
//    paramInstance.add(new GetRecordUsingExternalIDParam().territoryId, "3477061000003051357")
//    paramInstance.add(new GetRecordUsingExternalIDParam().includeChild, "true")
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2019, 1, 2, 10, 0, 0, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetRecordUsingExternalIDHeader().IfModifiedSince, ifmodifiedsince)
    headerInstance.add(new GetRecordUsingExternalIDHeader().XEXTERNAL, "Leads.External")
    //Call getRecordUsingExternalId method that takes externalFieldValue, moduleAPIName, paramInstance and headerInstance as parameter
    val responseOption = recordOperations.getRecordUsingExternalId(externalFieldValue, moduleAPIName, Option(paramInstance), Option(headerInstance))
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
   * <h3> Update Record Using External Id </h3>
   * This method is used to update a single record of a module with ID and print the response.
   *
   * @param moduleAPIName - The API Name of the record's module.
   * @param externalFieldValue -
   * @throws Exception
   */
  @throws[Exception]
  def updateRecordUsingExternalId(moduleAPIName: String, externalFieldValue: String): Unit = { //API Name of the module
    val recordOperations = new RecordOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    //List of Record instances
    val records = new ArrayBuffer[Record]
    //Get instance of Record Class
    val record1 = new Record
    /*
      * Call addFieldValue method that takes two arguments
      * 1 -> Call Field "." and choose the module from the displayed list and press "." and choose the field name from the displayed list.
      * 2 -> Value
      */
    record1.addFieldValue(new Leads().City, "City")

    /*
      * Call addKeyValue method that takes two arguments
      * 1 -> A string that is the Field's API Name
      * 2 -> Value
      */
    record1.addKeyValue("Custom_field", "Value")
    record1.addKeyValue("Custom_field_2", "value")
    record1.addKeyValue("Date_Time_2", OffsetDateTime.of(2019, 11, 20, 10, 0, 1, 0, ZoneOffset.of("+05:30")))
    record1.addKeyValue("Date_1", LocalDate.of(2017, 1, 13))
    val fileDetails = new ArrayBuffer[FileDetails]
    val fileDetail1 = new FileDetails
    fileDetail1.setAttachmentId(Option("3477061000007410016"))
    fileDetail1.setDelete(null)
    fileDetails.addOne(fileDetail1)
    val fileDetail2 = new FileDetails
    fileDetail2.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c326ef21accd646c01e85c34b1b2e7fe45c"))
    fileDetails.addOne(fileDetail2)
    val fileDetail3 = new FileDetails
    fileDetail3.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c3256b4b32b984bad140a629d9f4d4fc8e2"))
    fileDetails.addOne(fileDetail3)
    record1.addKeyValue("File_Upload", fileDetails)
    //Used when GDPR is enabled
    val dataConsent = new Consent
    dataConsent.setConsentRemarks(Option("Approved."))
    dataConsent.setConsentThrough(Option("Email"))
    dataConsent.setContactThroughEmail(Option(true))
    dataConsent.setContactThroughSocial(Option(false))
    record1.addKeyValue("Data_Processing_Basis_Details", dataConsent)
    var  subformList = new ArrayBuffer[com.zoho.crm.api.record.Record]
    var subform = new com.zoho.crm.api.record.Record()
    subform.addKeyValue("Subform FieldAPIName", "FieldValue")
    subformList.addOne(subform)
    record1.addKeyValue("Subform Name", subformList)
    /** Following methods are being used only by Inventory modules */
    var dealName = new com.zoho.crm.api.record.Record()
    dealName.addFieldValue(new Deals().id, 3477061000004995070l)
    var contactName = new com.zoho.crm.api.record.Record()
    contactName.addFieldValue(new Contacts().id, 3477061000004977055l)
    var accountName = new com.zoho.crm.api.record.Record()
    accountName.addKeyValue("name", "automatedAccount")
    //
    var  inventoryLineItemList = new ArrayBuffer[InventoryLineItems]
    var inventoryLineItem = new InventoryLineItems()
    var lineItemProduct = new LineItemProduct()
    lineItemProduct.setId(Option(3524033000003659082L))
    inventoryLineItem.setProduct(Option(lineItemProduct))
    inventoryLineItem.setQuantity(Option(1.5))
    inventoryLineItem.setProductDescription(Option("productDescription"))
    inventoryLineItem.setListPrice(Option(10.0))
    inventoryLineItem.setDiscount(Option("5.0"))
    inventoryLineItem.setDiscount(Option("5.25%"))
    var  productLineTaxes = new ArrayBuffer[LineTax]

    var productLineTax = new LineTax()
    productLineTax.setName(Option("Sales Tax"))
    productLineTax.setPercentage(Option(20.0))
    productLineTaxes.addOne(productLineTax)
    inventoryLineItem.setLineTax(productLineTaxes)
    inventoryLineItemList.addOne(inventoryLineItem)
    //
    record1.addKeyValue("Product_Details", inventoryLineItemList)
    var  lineTaxes = new ArrayBuffer[LineTax]()
    var lineTax = new LineTax()
    lineTax.setName(Option("Sales Tax"))
    lineTax.setPercentage(Option(20.0))
    lineTaxes.addOne(lineTax)
    record1.addKeyValue("$line_tax", lineTaxes)
    /** End Inventory **/
    val tagList = new ArrayBuffer[Tag]
    val tag = new Tag
    tag.setName(Option("Testtask1"))
    tagList.addOne(tag)
    record1.setTag(tagList)
    //Add Record instance to the list
    records.addOne(record1)
    //Set the list to Records in BodyWrapper instance
    request.setData(records)
    val trigger = new ArrayBuffer[String]
    trigger.addOne("approval")
    trigger.addOne("workflow")
    trigger.addOne("blueprint")
    request.setTrigger(trigger)
    val headerInstance1 = new HeaderMap()
    headerInstance1.add(new UpdateRecordUsingExternalIDHeader().XEXTERNAL, "Leads.External")
    //Call updateRecordUsingExternalId method that takes externalFieldValue, moduleAPIName, BodyWrapper instance and headerInstance as parameter.
    val responseOption = recordOperations.updateRecordUsingExternalId(externalFieldValue, moduleAPIName, request, Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
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
   * <h3> Delete Record Using External Id </h3>
   * This method is used to delete a single record of a module with ID and print the response.
   *
   * @param moduleAPIName - The API Name of the record's module.
   * @param externalFieldValue -
   * @throws Exception
   */
  @throws[Exception]
  def deleteRecordUsingExternalId(moduleAPIName: String, externalFieldValue: String): Unit = { //API Name of the module to delete record
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new DeleteRecordUsingExternalIDParam().wfTrigger, false)
    val headerInstance1 = new HeaderMap()
    headerInstance1.add(new DeleteRecordUsingExternalIDHeader().XEXTERNAL, "Leads.External")
    //Call deleteRecordUsingExternalId method that takes paramInstance, ModuleAPIName and recordId as parameter.
    val responseOption = recordOperations.deleteRecordUsingExternalId(externalFieldValue, moduleAPIName, Option(paramInstance), Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
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
   * <h3> Get Records</h3>
   * This method is used to get all the records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to obtain records.
   * @throws Exception
   */
  @throws[Exception]
  def getRecords(moduleAPIName: String): Unit = {
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new GetRecordsParam().approved, "both")
    paramInstance.add(new GetRecordsParam().converted, "both")
    paramInstance.add(new GetRecordsParam().cvid, "3477061000000087501")
    var ids = new ArrayBuffer[String]()
    ids += ("External121123456", "3477061000004352001")
    ids.foreach(id=>{
      paramInstance.add(new GetRecordsParam().ids,id)
    })
    paramInstance.add(new GetRecordsParam().ids, "3524033000005971038")
    paramInstance.add(new GetRecordsParam().uid, "3477061000005181008")
    paramInstance.add(new GetRecordsParam().fields, "LastName")
    paramInstance.add(new GetRecordsParam().sortBy, "Email")
    paramInstance.add(new GetRecordsParam().sortOrder, "desc")
    paramInstance.add(new GetRecordsParam().page, 1)
    paramInstance.add(new GetRecordsParam().perPage, 1)
    var  startdatetime = OffsetDateTime.of(2019, 11, 20, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
    paramInstance.add(new GetRecordsParam().startDateTime, startdatetime)
    var  enddatetime = OffsetDateTime.of(2019, 12, 20, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
    paramInstance.add(new GetRecordsParam().endDateTime, enddatetime)
    paramInstance.add(new GetRecordsParam().territoryId, "3477061000003051357")
    paramInstance.add(new GetRecordsParam().includeChild, "true")
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2019, 5, 20, 10, 0, 1, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetRecordsHeader().IfModifiedSince, ifmodifiedsince)
//    headerInstance.add(new GetRecordsHeader().XEXTERNAL, "Leads.External")
    //Call getRecords method that takes paramInstance, headerInstance and moduleAPIName as parameter.
    val responseOption = recordOperations.getRecords(moduleAPIName, Option(null), Option(headerInstance))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) { //Get the object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) {
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the obtained Record instances
          val records = responseWrapper.getData

          for (record <- records) {
            println("Record ID: " + record.getId)
            var createdByOption = record.getCreatedBy
            if (createdByOption.isDefined) {
              var createdBy= createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              println("Record Created By User-Name: " + createdBy.getName)
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            println("Record CreatedTime: " + record.getCreatedTime)
            var modifiedByOption = record.getModifiedBy
            if (modifiedByOption.isDefined) {
              var modifiedBy = modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              println("Record Modified By User-Name: " + modifiedBy.getName)
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            println("Record ModifiedTime: " + record.getModifiedTime)
            val tags = record.getTag
            if (tags.length > 0) {

              for (tag <- tags) {
                println("Record Tag Name: " + tag.getName)
                println("Record Tag ID: " + tag.getId)
              }
            }
            println("Record Field Value: " + record.getKeyValue("Last_Name"))
            println("Record KeyValues: ")


          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            var info = infoOption.get
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
   * <h3> Create Records</h3>
   * This method is used to create records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to create records.
   * @throws Exception
   */
  @throws[Exception]
  def createRecords(moduleAPIName: String): Unit = { //API Name of the module to create records
    val recordOperations = new RecordOperations
    val bodyWrapper = new BodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.addFieldValue( new Field.Leads().City, "City")
    record1.addFieldValue(new Field.Leads().LastName, "Last Name")
    record1.addFieldValue(new Field.Leads().FirstName, "First Name")
    record1.addFieldValue(new Field.Leads().Company, "KKRNP")
//    record1.addKeyValue("Date_1", LocalDate.of(2021, 1, 13))
//    record1.addKeyValue("Subject", "AutomatedSDK")
//    val fileDetails = new ArrayBuffer[FileDetails]
//    val fileDetail1 = new FileDetails
//    fileDetail1.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c32537b7c2400dadca8ff55f620c65357da"))
//    fileDetails.addOne(fileDetail1)
//    val fileDetail2 = new FileDetails
//    fileDetail2.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c32e0063e7321b5b4ca878a934519e6cdb2"))
//    fileDetails.addOne(fileDetail2)
//    val fileDetail3 = new FileDetails
//    fileDetail3.setFileId(Option("ae9c7cefa418aec1d6a5cc2d9ab35c323daf4780bfe0058133556f155795981f"))
//    fileDetails.addOne(fileDetail3)
//    record1.addKeyValue("File_Upload", fileDetails)
    val subformList = new ArrayBuffer[Record]
    val subform = new Record
//    subform.addKeyValue("DOB", "sep")
//    subform.addKeyValue("Name1", "test")
    subformList.addOne(subform)
    record1.addKeyValue("Subform_1", subformList)
//    val dataConsent = new Consent
//    dataConsent.setConsentRemarks(Option("Approved."))
//    dataConsent.setConsentThrough(Option("Email"))
//    dataConsent.setContactThroughEmail(Option(true))
//    dataConsent.setContactThroughSocial(Option(false))
//    record1.addKeyValue("Data_Processing_Basis_Details", dataConsent)
//    /** Following methods are being used only by Activity modules */
//    //    		Tasks
//    record1.addFieldValue(new Tasks().Description, "Test Task")
//    record1.addKeyValue("Currency", new Choice[String]("INR"))
//    var  remindAt1 = new RemindAt()
//    remindAt1.setAlarm(Option("FREQ=NONE;ACTION=EMAILANDPOPUP;TRIGGER=DATE-TIME:2020-07-03T12:30:00+05:30"))
//    var  whoId = new com.zoho.crm.api.record.Record()
//    whoId.setId(Option(3477061000004977055L))
//    record1.addFieldValue(new Tasks().WhoId, whoId)
//    record1.addFieldValue(new Tasks().Status, new Choice[String]("Waiting on someone else"))
//    record1.addFieldValue(new Tasks().DueDate, LocalDate.of(2021, 1, 13))
//    record1.addFieldValue(new Tasks().Priority, new Choice[String]("High"))
//    record1.addFieldValue(new Tasks().Subject, "Email1")
//    record1.addKeyValue("$se_module", "Accounts")
//    var whatId = new com.zoho.crm.api.record.Record()
//    whatId.setId(Option(3477061000000207118L))
//    record1.addFieldValue(new Tasks().WhatId, whatId)
//    /** Recurring Activity can be provided in any activity module */
//    var recurringActivity = new RecurringActivity()
//    recurringActivity.setRrule(Option("FREQ=DAILY;INTERVAL=10;UNTIL=2020-08-14;DTSTART=2020-07-03"))
//    record1.addFieldValue(new Events().RecurringActivity, recurringActivity)
//    //        	Events
//    record1.addFieldValue(new Events().Description, "Test Events")
//    var startDateTime = OffsetDateTime.of(2020, 1, 2, 10, 0, 0, 0, ZoneOffset.of("+05:30"))
//    record1.addFieldValue(new Events().StartDateTime, startDateTime)
//    var  participants = new ArrayBuffer[Participants]()
//    var participant1 = new Participants()
//    participant1.setParticipant(Option("abc@gmail.com"))
//    participant1.setType(Option("email"))
//    participant1.setId(Option(3477061000005902017L))
//    participants.addOne(participant1)
//    var participant2 = new Participants()
//    participant2.addKeyValue("participant", "3477061000007420005")
//    participant2.addKeyValue("type", "lead")
//    participants.addOne(participant2)
//    record1.addFieldValue(new Events().Participants, participants)
//    record1.addKeyValue("$send_notification", true)
//    record1.addFieldValue(new Events().EventTitle, "New Automated Event")
//    var enddatetime = OffsetDateTime.of(2020, 5, 2, 1, 0, 0, 0, ZoneOffset.of("+05:30"))
//    record1.addFieldValue(new Events().EndDateTime, enddatetime)
//    var remindAt2= OffsetDateTime.of(2020, 5, 2, 5, 0, 0, 0, ZoneOffset.of("+05:30"))
//    record1.addFieldValue(new Events().RemindAt,remindAt2 )
//    record1.addFieldValue(new Events().CheckInStatus, "PLANNED")
//    record1.addKeyValue("$se_module", "Leads")
//    whatId.setId(Option(3477061000004381002L))
//    record1.addFieldValue(new Events().WhatId, whatId)
//    record1.addKeyValue("Currency", new Choice[String]("USD"))
//    /** End Activity **//** Following methods are being used only by Price_Books modules */
//    var pricingDetails = new ArrayBuffer[PricingDetails]()
//    var pricingDetail1 = new PricingDetails()
//    pricingDetail1.setFromRange(Option(1.0))
//    pricingDetail1.setToRange(Option(5.0))
//    pricingDetail1.setDiscount(Option(2.0))
//    pricingDetails.addOne(pricingDetail1)
//    var  pricingDetail2 = new PricingDetails()
//    pricingDetail2.addKeyValue("from_range", 6.0)
//    pricingDetail2.addKeyValue("to_range", 11.0)
//    pricingDetail2.addKeyValue("discount", 3.0)
//    pricingDetails.addOne(pricingDetail2)
//    record1.addFieldValue(new Price_Books().PricingDetails, pricingDetails)
//    record1.addKeyValue("Email", "abc.k124@zoho.com")
//    record1.addFieldValue(new Price_Books().Description, "TEST")
//    record1.addFieldValue(new Price_Books().PriceBookName, "book_name")
//    record1.addFieldValue(new Price_Books().PricingModel, new Choice[String]("Flat"))
    val tagList = new ArrayBuffer[Tag]
    val tag = new Tag
    tag.setName(Option("Testtask"))
    tagList.addOne(tag)
    record1.setTag(tagList)
    records.addOne(record1)
    bodyWrapper.setData(records)
    val trigger = new ArrayBuffer[String]
    trigger.addOne("approval")
    trigger.addOne("workflow")
    trigger.addOne("blueprint")
//    bodyWrapper.setTrigger(trigger)
    //bodyWrapper.setLarId("3477061000000087515")
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new CreateRecordsHeader().XEXTERNAL, "Leads.External")
    //Call createRecords method that takes BodyWrapper instance as parameter.
    val responseOption = recordOperations.createRecords(moduleAPIName, bodyWrapper, Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
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
   * <h3> Update Records</h3>
   * This method is used to update the records of a module with ID and print the response.
   *
   * @param moduleAPIName - The API Name of the module to update records.
   * @throws Exception
   */
  @throws[Exception]
  def updateRecords(moduleAPIName: String): Unit = {
    val recordOperations = new RecordOperations
    val request = new BodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.setId(Option(3477061000011675057l))
    record1.addFieldValue( new Field.Leads().City, "City")
    record1.addFieldValue(new Field.Leads().LastName, "Last Name")
    record1.addFieldValue(new Field.Leads().FirstName, "First Name")
    record1.addFieldValue(new Field.Leads().Company, "KKRNP")
    record1.addKeyValue("Custom_field", "Value")
    record1.addKeyValue("Custom_field_2", "value")
    val dataConsent = new Consent
    dataConsent.setConsentRemarks(Option("Approved."))
    dataConsent.setConsentThrough(Option("Email"))
    dataConsent.setContactThroughEmail(Option(true))
    dataConsent.setContactThroughSocial(Option(false))
    record1.addKeyValue("Data_Processing_Basis_Details", dataConsent)
    records.addOne(record1)
    val record2 = new Record
    record2.setId(Option(3477061000005844005l))
    record2.addFieldValue( new Field.Leads().City, "City")
    record2.addFieldValue(new Field.Leads().LastName, "Last Name")
    record2.addFieldValue(new Field.Leads().FirstName, "First Name")
    record2.addFieldValue(new Field.Leads().Company, "KKRNP")
    record2.addKeyValue("Custom_field", "Value")
    record2.addKeyValue("Custom_field_2", "value")
    records.addOne(record2)
    request.setData(records)
    val trigger = new ArrayBuffer[String]
    trigger.addOne("approval")
    trigger.addOne("workflow")
    trigger.addOne("blueprint")
    request.setTrigger(trigger)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new UpdateRecordsHeader().XEXTERNAL, "Leads.External")
    //Call updateRecords method that takes BodyWrapper instance and moduleAPIName as parameter.
    val responseOption = recordOperations.updateRecords(moduleAPIName, request, Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
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
   * <h3> Delete Records</h3>
   * This method is used to delete records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to delete records.
   * @param recordIds     - The List of the record IDs to be deleted.
   * @throws Exception
   */
  @throws[Exception]
  def deleteRecords(moduleAPIName: String, recordIds:  ArrayBuffer[String]): Unit = { //List<Long> recordIds = new ArrayList<Long>(Arrays.asList(3477061000005908033l,3477061000005908017l))
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap

    for (id <- recordIds) {
      paramInstance.add(new DeleteRecordsParam().ids, id)
    }
    paramInstance.add(new DeleteRecordsParam().wfTrigger, false)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new DeleteRecordsHeader().XEXTERNAL, "Leads.External")
    //Call deleteRecords method that takes paramInstance and moduleAPIName as parameter.
    val responseOption = recordOperations.deleteRecords(moduleAPIName, Option(paramInstance), Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
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
   * <h3> Upsert Records</h3>
   * This method is used to Upsert records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to upsert records.
   * @throws Exception
   */
  @throws[Exception]
  def upsertRecords(moduleAPIName: String): Unit = {
    val recordOperations = new RecordOperations
    val request = new BodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.addFieldValue( new Field.Leads().City, "City")
    record1.addFieldValue(new Field.Leads().LastName, "Last Name")
    record1.addFieldValue(new Field.Leads().FirstName, "First Name")
    record1.addFieldValue(new Field.Leads().Company, "KKRNP")

    record1.addKeyValue("Custom_field", "Value")
    record1.addKeyValue("Custom_field_2", "value")
    records.addOne(record1)
    val record2 = new Record
    record2.addFieldValue( new Field.Leads().City, "City")
    record2.addFieldValue(new Field.Leads().LastName, "Last Name")
    record2.addFieldValue(new Field.Leads().FirstName, "First Name")
    record2.addFieldValue(new Field.Leads().Company, "KKRNP")
    record2.addKeyValue("Custom_field", "Value")
    record2.addKeyValue("Custom_field_2", "value")
    records.addOne(record2)
    val duplicateCheckFields = new ArrayBuffer[String]
    duplicateCheckFields+= ("City", "First_Name")
    request.setDuplicateCheckFields(duplicateCheckFields)
    request.setData(records)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new UpsertRecordsHeader().XEXTERNAL, "Leads.External")
    //Call upsertRecords method that takes BodyWrapper instance and moduleAPIName as parameter.
    val responseOption = recordOperations.upsertRecords(moduleAPIName, request, Option(headerInstance1))
    if (responseOption.isDefined) {
      val response = responseOption.get
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
   * <h3> Get Deleted Records</h3>
   * This method is used to get the deleted records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to get the deleted records.
   * @throws Exception
   */
  @throws[Exception]
  def getDeletedRecords(moduleAPIName: String): Unit = {
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new GetDeletedRecordsParam().type1, "permanent") //all, recycle, permanent

    paramInstance.add(new GetDeletedRecordsParam().page, 1)
    paramInstance.add(new GetDeletedRecordsParam().perPage, 2)
    //Get instance of HeaderMap Class
    val headerInstance = new HeaderMap
    val ifModifiedSince = OffsetDateTime.of(2020, 5, 2, 12, 0, 30, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetDeletedRecordsHeader().IfModifiedSince, ifModifiedSince)
    //    Call getDeletedRecords method that takes paramInstance, headerInstance and moduleAPIName as parameter
    val responseOption = recordOperations.getDeletedRecords(moduleAPIName,Option(paramInstance), Option(headerInstance))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val deletedRecordsHandler = response.getObject
        if (deletedRecordsHandler.isInstanceOf[DeletedRecordsWrapper]) { //Get the received DeletedRecordsWrapper instance
          val deletedRecordsWrapper = deletedRecordsHandler.asInstanceOf[DeletedRecordsWrapper]
          //Get the list of obtained DeletedRecord instances
          val deletedRecords = deletedRecordsWrapper.getData

          for (deletedRecord <- deletedRecords) { //Get the deletedBy User instance of each DeletedRecord
            val deletedByOption = deletedRecord.getDeletedBy
            //Check if deletedBy is not null
            if (deletedByOption.isDefined) { //Get the name of the deletedBy User
              val deletedBy =deletedByOption.get
              println("DeletedRecord Deleted By User-Name: " + deletedBy.getName)
              //Get the ID of the deletedBy User
              println("DeletedRecord Deleted By User-ID: " + deletedBy.getId)
            }
            //Get the ID of each DeletedRecord
            println("DeletedRecord ID: " + deletedRecord.getId)
            //Get the DisplayName of each DeletedRecord
            println("DeletedRecord DisplayName: " + deletedRecord.getDisplayName)
            //Get the Type of each DeletedRecord
            println("DeletedRecord Type: " + deletedRecord.getType)
            //Get the createdBy User instance of each DeletedRecord
            val createdByOption = deletedRecord.getCreatedBy
            if (createdByOption.isDefined) {
              var createdBy = createdByOption.get
              println("DeletedRecord Created By User-Name: " + createdBy.getName)
              println("DeletedRecord Created By User-ID: " + createdBy.getId)
            }
            //Get the DeletedTime of each DeletedRecord
            println("DeletedRecord DeletedTime: " + deletedRecord.getDeletedTime)
          }
          val infoOption = deletedRecordsWrapper.getInfo
          if (infoOption.isDefined) {
            var info = infoOption.get
            if (info.getPerPage.isDefined) println("Record Info PerPage: " + info.getPerPage.toString)
            if (info.getCount.isDefined) println("Record Info Count: " + info.getCount.toString)
            if (info.getPage.isDefined) println("Record Info Page: " + info.getPage.toString)
            if (info.getMoreRecords.isDefined) println("Record Info MoreRecords: " + info.getMoreRecords.toString)
          }
        }
        else if (deletedRecordsHandler.isInstanceOf[APIException]) {
          val exception = deletedRecordsHandler.asInstanceOf[APIException]
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
   * <h3> Search Records</h3>
   * This method is used to search records of a module and print the response.
   *
   * @param moduleAPIName - The API Name of the module to obtain records.
   * @throws Exception
   */
  @throws[Exception]
  def searchRecords(moduleAPIName: String): Unit = {
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new SearchRecordsParam().criteria, "(Last_Name:starts_with:a)")
//    paramInstance.add(new SearchRecordsParam().criteria, "(External:in:TestExternal221)");
    paramInstance.add(new SearchRecordsParam().email, "abc@gmail.com")
    paramInstance.add(new SearchRecordsParam().phone, "234567890")
    paramInstance.add(new SearchRecordsParam().word, "First Name Last Name")
    paramInstance.add(new SearchRecordsParam().converted, "both")
    paramInstance.add(new SearchRecordsParam().approved, "both")
    paramInstance.add(new SearchRecordsParam().page, 1)
    paramInstance.add(new SearchRecordsParam().perPage, 2)
    val headerInstance1 = new HeaderMap()
//    headerInstance1.add(new SearchRecordsHeader().XEXTERNAL, "Leads.External")
    //Call searchRecords method that takes ParameterMap Instance and moduleAPIName as parameter
    val responseOption = recordOperations.searchRecords(moduleAPIName, Option(paramInstance), Option(headerInstance1))
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
          //Get the obtained Record instance
          val records = responseWrapper.getData

          for (record <- records) {
            println("Record ID: " + record.getId)
            var createdByOption = record.getCreatedBy
            if (createdByOption.isDefined) {
              var createdBy = createdByOption.get
              println("Record Created By User-ID: " + createdBy.getId)
              println("Record Created By User-Name: " + createdBy.getName)
              println("Record Created By User-Email: " + createdBy.getEmail)
            }
            println("Record CreatedTime: " + record.getCreatedTime)
            var modifiedByOption = record.getModifiedBy
            if (modifiedByOption.isDefined) {
              var modifiedBy= modifiedByOption.get
              println("Record Modified By User-ID: " + modifiedBy.getId)
              println("Record Modified By User-Name: " + modifiedBy.getName)
              println("Record Modified By User-Email: " + modifiedBy.getEmail)
            }
            println("Record ModifiedTime: " + record.getModifiedTime)
            val tags = record.getTag
            if (tags.length > 0) {

              for (tag <- tags) {
                println("Record Tag Name: " + tag.getName)
                println("Record Tag ID: " + tag.getId)
              }
            }
            println("Record Field Value: " + record.getKeyValue("Last_Name"))
            println("Record KeyValues: ")

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
                      var lineItemProduct = lineItemProductOption.get
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
                      var owner =ownerOption.get
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
                      var parentId = parentIdOption.get
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
                    createdByOption = attachment.getCreatedBy
                    if (createdByOption.isDefined) {
                      var createdBy = createdByOption.get
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
                  var consentCreatedBy= consentCreatedByOption.get
                  println("Record Consent CreatedBy Name: " + consentCreatedBy.getName)
                  //Get the ID of the CreatedBy User
                  println("Record Consent CreatedBy ID: " + consentCreatedBy.getId)
                  //Get the Email of the CreatedBy User
                  println("Record Consent CreatedBy Email: " + consentCreatedBy.getEmail)
                }
                val consentModifiedByOption = consent.getModifiedBy
                if (consentModifiedByOption.isDefined) { //Get the name of the ModifiedBy Use
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
          val infoOption = responseWrapper.getInfo
          if (infoOption.isDefined) {
            var info =infoOption.get
            if (info.getPerPage.isDefined) println("Record Info PerPage: " + info.getPerPage.toString)
            if (info.getCount.isDefined) println("Record Info Count: " + info.getCount.toString)
            if (info.getPage.isDefined) println("Record Info Page: " + info.getPage.toString)
            if (info.getMoreRecords.isDefined) println("Record Info MoreRecords: " + info.getMoreRecords.toString)
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
   * <h3> Convert Lead</h3>
   * This method is used to Convert Lead record and print the response.
   *
   * @param leadId - The ID of the Lead to be converted.
   * @throws Exception
   */
  @throws[Exception]
  def convertLead(leadId: Long): Unit = { //long leadId = 3477061000006603276L
    val recordOperations = new RecordOperations
    //Get instance of ConvertBodyWrapper Class that will contain the request body
    val request = new ConvertBodyWrapper
    //List of LeadConverter instances
    val data = new ArrayBuffer[LeadConverter]
    //Get instance of LeadConverter Class
    val record1 = new LeadConverter
    record1.setOverwrite(Option(true))
    record1.setNotifyLeadOwner(Option(true))
    record1.setNotifyNewEntityOwner(Option(true))
    record1.setAccounts(Option("3477061000005848125"))
    record1.setContacts(Option("3477061000000358009"))
    record1.setAssignTo(Option("3524033000000191017"))
    val deals = new Record
    deals.addFieldValue(new Field.Deals().DealName, "deal_name")
    deals.addFieldValue(new Field.Deals().Description, "deals description")
    deals.addFieldValue(new Field.Deals().ClosingDate, LocalDate.of(2021, 1, 13))
    deals.addFieldValue(new Field.Deals().Stage, new Choice[String]("Closed Won"))
    deals.addFieldValue(new Field.Deals().Amount, 50.7)
    deals.addKeyValue("Custom_field", "Value")
    deals.addKeyValue("Custom_field_2", "value")
    record1.setDeals(Option(deals))
    data.addOne(record1)
    request.setData(data)
    //Call convertLead method that takes ConvertBodyWrapper instance and leadId as parameter.
    val responseOption = recordOperations.convertLead(leadId,request)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val convertActionHandler = response.getObject
        if (convertActionHandler.isInstanceOf[ConvertActionWrapper]) { //Get the received ConvertActionWrapper instance
          val convertActionWrapper = convertActionHandler.asInstanceOf[ConvertActionWrapper]
          //Get the list of obtained ConvertActionResponse instances
          val convertActionResponses = convertActionWrapper.getData

          for (convertActionResponse <- convertActionResponses) {
            if (convertActionResponse.isInstanceOf[SuccessfulConvert]) { //Get the received SuccessfulConvert instance
              val successfulConvert = convertActionResponse.asInstanceOf[SuccessfulConvert]
              //Get the Accounts ID of  Record
              println("LeadConvert Accounts ID: " + successfulConvert.getAccounts)
              //Get the Contacts ID of  Record
              println("LeadConvert Contacts ID: " + successfulConvert.getContacts)
              //Get the Deals ID of  Record
              println("LeadConvert Deals ID: " + successfulConvert.getDeals)
            }
            else if (convertActionResponse.isInstanceOf[APIException]) {
              val exception = convertActionResponse.asInstanceOf[APIException]
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
        else if (convertActionHandler.isInstanceOf[APIException]) {
          val exception = convertActionHandler.asInstanceOf[APIException]
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
   * This method is used to download a photo associated with a module.
   *
   * @param moduleAPIName     - The API Name of the record's module
   * @param recordId          - The ID of the record to be obtained.
   * @param destinationFolder - The absolute path of the destination folder to store the photo.
   * @throws Exception
   */
  @throws[Exception]
  def getPhoto(moduleAPIName: String, recordId: Long, destinationFolder: String): Unit = { //Long recordId = 3477061000005177002L
    //String destinationFolder = "./"
    val recordOperations = new RecordOperations
    //Call getPhoto method that takes moduleAPIName and recordId as parameters
    val responseOption = recordOperations.getPhoto(recordId,moduleAPIName )
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val downloadHandler = response.getObject
        if (downloadHandler.isInstanceOf[FileBodyWrapper]) {
          val fileBodyWrapper = downloadHandler.asInstanceOf[FileBodyWrapper]
          val streamWrapper = fileBodyWrapper.getFile.get
          val file = new File(destinationFolder + File.separatorChar + streamWrapper.getName.get)
          val is = streamWrapper.getStream.get
          val os = new FileOutputStream(file)
          val buffer = new Array[Byte](1024)
          var bytesRead = 0
          //read the InputStream till the end
          while ( {
            bytesRead = is.read(buffer)
            bytesRead != -1
          }) { //write data to OutputStream
            os.write(buffer, 0, bytesRead)
          }
          is.close()
          os.flush()
          os.close()
        }
        else if (downloadHandler.isInstanceOf[APIException]) {
          val exception = downloadHandler.asInstanceOf[APIException]
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
   * This method is used to attach a photo to a record. You must include the photo in the request with content type as multipart/form data.
   *
   * @param moduleAPIName    - The API Name of the record's module
   * @param recordId         - The ID of the record to be obtained.
   * @param absoluteFilePath - The absolute file path of the file to be uploads
   * @throws Exception
   */
  @throws[Exception]
  def uploadPhoto(moduleAPIName: String, recordId: Long, absoluteFilePath: String): Unit = { //String absoluteFilePath = "./image.png"
    val recordOperations = new RecordOperations
    //Get instance of FileBodyWrapper class that will contain the request file
    val fileBodyWrapper = new FileBodyWrapper
    //Get instance of StreamWrapper class that takes absolute path of the file to be attached as parameter
    val streamWrapper = new StreamWrapper(absoluteFilePath)
    //Set file to the FileBodyWrapper instance
    fileBodyWrapper.setFile(Option(streamWrapper))
    //Call uploadPhoto method that takes FileBodyWrapper instance, moduleAPIName and recordId as parameter
    val responseOption = recordOperations.uploadPhoto(recordId,moduleAPIName,fileBodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val fileHandler = response.getObject
        if (fileHandler.isInstanceOf[SuccessResponse]) {
          val successResponse = fileHandler.asInstanceOf[SuccessResponse]
          println("Status: " + successResponse.getStatus.getValue)
          println("Code: " + successResponse.getCode.getValue)
          println("Details: ")

          successResponse.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + successResponse.getMessage.getValue)
        }
        else if (fileHandler.isInstanceOf[APIException]) {
          val exception = fileHandler.asInstanceOf[APIException]
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
   * This method is used to delete a photo from a record in a module.
   *
   * @param moduleAPIName - The API Name of the record's module
   * @param recordId      - The ID of the record to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def deletePhoto(moduleAPIName: String, recordId: Long): Unit = {
    val recordOperations = new RecordOperations
    //Call deletePhoto method that takes moduleAPIName and recordId as parameter
    val responseOption = recordOperations.deletePhoto(recordId,moduleAPIName )
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val fileHandler = response.getObject
        if (fileHandler.isInstanceOf[SuccessResponse]) {
          val successResponse = fileHandler.asInstanceOf[SuccessResponse]
          println("Status: " + successResponse.getStatus.getValue)
          println("Code: " + successResponse.getCode.getValue)
          println("Details: ")

          successResponse.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + successResponse.getMessage.getValue)
        }
        else if (fileHandler.isInstanceOf[APIException]) {
          val exception = fileHandler.asInstanceOf[APIException]
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
   * This method is used to update the values of specific fields for multiple records and print the response.
   *
   * @param moduleAPIName - The API Name of the module to obtain records.
   * @throws Exception
   */
  @throws[Exception]
  def massUpdateRecords(moduleAPIName: String): Unit = { //API Name of the module to massUpdateRecords
    val recordOperations = new RecordOperations
    //Get instance of MassUpdateBodyWrapper Class that will contain the request body
    val request = new MassUpdateBodyWrapper
    val records = new ArrayBuffer[Record]
    val record1 = new Record
    record1.addKeyValue("City", "Value")
    record1.addKeyValue("Company", "Value")
    records.addOne(record1)
    request.setData(records)
//    request.setCvid(Option("3524033000000087501"))
    val ids = new ArrayBuffer[String]
    ids += ("3477061000005922192")
    request.setIds(ids)
    var  territory = new Territory()
    //    territory.setId("")
    territory.setIncludeChild(Option(true))
    request.setTerritory(Option(territory))
    request.setOverWrite(Option(true))
    //Call massUpdateRecords method that takes BodyWrapper instance, ModuleAPIName as parameter.
    val responseOption = recordOperations.massUpdateRecords(moduleAPIName,request )
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val massUpdateActionHandler = response.getObject
        if (massUpdateActionHandler.isInstanceOf[MassUpdateActionWrapper]) { //Get the received MassUpdateActionWrapper instance
          val massUpdateActionWrapper = massUpdateActionHandler.asInstanceOf[MassUpdateActionWrapper]
          //Get the list of obtained MassUpdateActionResponse instances
          val massUpdateActionResponses = massUpdateActionWrapper.getData

          for (massUpdateActionResponse <- massUpdateActionResponses) {
            if (massUpdateActionResponse.isInstanceOf[MassUpdateSuccessResponse]) { //Get the received MassUpdateSuccessResponse instance
              val massUpdateSuccessResponse = massUpdateActionResponse.asInstanceOf[MassUpdateSuccessResponse]
              println("Status: " + massUpdateSuccessResponse.getStatus.getValue)
              println("Code: " + massUpdateSuccessResponse.getCode.getValue)
              println("Details: ")

              massUpdateSuccessResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + massUpdateSuccessResponse.getMessage.getValue)
            }
            else if (massUpdateActionResponse.isInstanceOf[APIException]) {
              val exception = massUpdateActionResponse.asInstanceOf[APIException]
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
        else if (massUpdateActionHandler.isInstanceOf[APIException]) {
          val exception = massUpdateActionHandler.asInstanceOf[APIException]
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
   * This method is used to get the status of the mass update job scheduled previously and print the response.
   *
   * @param moduleAPIName - The API Name of the module to obtain records.
   * @param jobId         - The ID of the job from the response of Mass Update Records.
   * @throws Exception
   */
  @throws[Exception]
  def getMassUpdateStatus(moduleAPIName: String, jobId: String): Unit = { //String jobId = "3477061000005177002"
    val recordOperations = new RecordOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new GetMassUpdateStatusParam().jobId, jobId)
    //Call getMassUpdateStatus method that takes paramInstance, moduleAPIName as parameter
    val responseOption = recordOperations.getMassUpdateStatus(moduleAPIName,Option(paramInstance))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val massUpdateResponseHandler = response.getObject
        if (massUpdateResponseHandler.isInstanceOf[MassUpdateResponseWrapper]) { //Get the received MassUpdateResponseWrapper instance
          val massUpdateResponseWrapper = massUpdateResponseHandler.asInstanceOf[MassUpdateResponseWrapper]
          //Get the list of obtained MassUpdateResponse instances
          val massUpdateResponses = massUpdateResponseWrapper.getData

          for (massUpdateResponse <- massUpdateResponses) {
            if (massUpdateResponse.isInstanceOf[MassUpdate]) { //Get the received MassUpdate instance
              val massUpdate = massUpdateResponse.asInstanceOf[MassUpdate]
              //Get the Status of each MassUpdate
              println("MassUpdate Status: " + massUpdate.getStatus.getValue)
              //Get the FailedCount of each MassUpdate
              println("MassUpdate FailedCount: " + massUpdate.getFailedCount.toString)
              //Get the UpdatedCount of each MassUpdate
              println("MassUpdate UpdatedCount: " + massUpdate.getUpdatedCount.toString)
              //Get the NotUpdatedCount of each MassUpdate
              println("MassUpdate NotUpdatedCount: " + massUpdate.getNotUpdatedCount)
              //Get the TotalCount of each MassUpdate
              println("MassUpdate TotalCount: " + massUpdate.getTotalCount.toString)
            }
            else if (massUpdateResponse.isInstanceOf[APIException]) {
              val exception = massUpdateResponse.asInstanceOf[APIException]
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
        else if (massUpdateResponseHandler.isInstanceOf[APIException]) {
          val exception = massUpdateResponseHandler.asInstanceOf[APIException]
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

class Records {}