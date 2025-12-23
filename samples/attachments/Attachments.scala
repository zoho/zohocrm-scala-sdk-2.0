package com.zoho.crm.sample.attachments

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.attachments.APIException
import com.zoho.crm.api.attachments.ActionHandler
import com.zoho.crm.api.attachments.ActionResponse
import com.zoho.crm.api.attachments.ActionWrapper
import com.zoho.crm.api.attachments.AttachmentsOperations
import com.zoho.crm.api.attachments.AttachmentsOperations.{DeleteAttachmentsParam, GetAttachmentsParam, UploadLinkAttachmentParam}
import com.zoho.crm.api.attachments.FileBodyWrapper
import com.zoho.crm.api.attachments.ResponseHandler
import com.zoho.crm.api.attachments.ResponseWrapper
import com.zoho.crm.api.attachments.SuccessResponse
import com.zoho.crm.api.record.Info
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer


@SuppressWarnings(Array("unused")) object Attachments {
  /**
   * <h3> Get Attachments</h3>
   * This method is used to get a single record's attachments' details with ID and print the response.
   *
   * @throws Exception
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to get attachments
   */
  @throws[Exception]
  def getAttachments(moduleAPIName: String, recordId: Long): Unit = { //example
    //String moduleAPIName = "Leads"
    //Long recordId = 3477061000005177002l
    //Get instance of AttachmentsOperations Class that takes recordId and moduleAPIName as parameter
    val attachmentsOperations = new AttachmentsOperations(moduleAPIName,recordId)
    //Call getAttachments method
    val paramInstance = new ParameterMap
    paramInstance.add(new GetAttachmentsParam().page,1)
    val responseOption = attachmentsOperations.getAttachments(Option(paramInstance))
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
          //Get the list of obtained Attachment instances
          val attachments = responseWrapper.getData

          for (attachment <- attachments) { //Get the owner User instance of each attachment
            val ownerOption = attachment.getOwner
            //Check if owner is not null
            if (ownerOption.isDefined) { //Get the Name of the Owner
              val owner = ownerOption.get
              println("Attachment Owner User-Name: " + owner.getName)
              //Get the ID of the Owner
              println("Attachment Owner User-ID: " + owner.getId)
              //Get the Email of the Owner
              println("Attachment Owner User-Email: " + owner.getEmail)
            }
            //Get the modified time of each attachment
            println("Attachment Modified Time: " + attachment.getModifiedTime.toString)
            //Get the name of the File
            println("Attachment File Name: " + attachment.getFileName)
            //Get the created time of each attachment
            println("Attachment Created Time: " + attachment.getCreatedTime.toString)
            //Get the Attachment file size
            println("Attachment File Size: " + attachment.getSize.toString)
            //Get the parentId Record instance of each attachment
            val parentIdOption = attachment.getParentId
            //Check if parentId is not null
            if (parentIdOption.isDefined) { //Get the parent record Name of each attachment
              val parentId = parentIdOption.get
              println("Attachment parent record Name: " + parentId.getKeyValue("name"))
              //Get the parent record ID of each attachment
              println("Attachment parent record ID: " + parentId.getId)
            }
            //Get the attachment is Editable
            println("Attachment is Editable: " + attachment.geteditable.toString)
            //Get the file ID of each attachment
            println("Attachment File ID: " + attachment.getfileId())
            //Get the type of each attachment
            println("Attachment File Type: " + attachment.gettype)
            //Get the seModule of each attachment
            println("Attachment seModule: " + attachment.getseModule())
            //Get the modifiedBy User instance of each attachment
            val modifiedByOption  = attachment.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy = modifiedByOption.get
              println("Attachment Modified By User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Attachment Modified By User-ID: " + modifiedBy.getId)
              //Get the Email of the modifiedBy User
              println("Attachment Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the state of each attachment
            println("Attachment State: " + attachment.getstate)
            //Get the ID of each attachment
            println("Attachment ID: " + attachment.getId)
            //Get the createdBy User instance of each attachment
            val createdByOption = attachment.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the name of the createdBy User
              val createdBy = createdByOption.get
              println("Attachment Created By User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Attachment Created By User-ID: " + createdBy.getId)
              //Get the Email of the createdBy User
              println("Attachment Created By User-Email: " + createdBy.getEmail)
            }
            //Get the linkUrl of each attachment
            println("Attachment LinkUrl: " + attachment.getlinkUrl())
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
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
   * <h3> Upload Attachments</h3>
   * This method is used to upload an attachment to a single record of a module with ID and print the response.
   *
   * @throws Exception
   * @param moduleAPIName    The API Name of the record's module
   * @param recordId         The ID of the record to upload attachment
   * @param absoluteFilePath The absolute file path of the file to be attached
   */
  @throws[Exception]
  def uploadAttachments(moduleAPIName: String, recordId: Long, absoluteFilePath: String): Unit = { //		String moduleAPIName = "Leads"
    //		Long recordId = 3477061000005177002l
    //		String absoluteFilePath = "./image.png"
    val attachmentsOperations = new AttachmentsOperations(moduleAPIName,recordId)
    //Get instance of FileBodyWrapper class that will contain the request file
    val fileBodyWrapper = new FileBodyWrapper
    //Get instance of StreamWrapper class that takes absolute path of the file to be attached as parameter
    val streamWrapper = new StreamWrapper(absoluteFilePath)
    //Set file to the FileBodyWrapper instance
    fileBodyWrapper.setFile(Option(streamWrapper))
    //Call uploadAttachment method that takes FileBodyWrapper instance as parameter
    val responseOption = attachmentsOperations.uploadAttachment(fileBodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained action responses
          val actionResponses = actionWrapper.getData

          for (actionResponse <- actionResponses) { //Check if the request is successful
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
              if (exception.getDetails != null) {

                exception.getDetails.foreach(entry=>{
                  println(entry._1 + ": " + entry._2)
                })
              }
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")
          if (exception.getDetails != null) {

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
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
   * <h3> Delete Attachments</h3>
   * This method is used to Delete attachments to a single record of a module with ID and print the response.
   *
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to delete attachment
   * @param attachmentIds The List of attachment IDs to be deleted
   */
  @throws[Exception]
  def deleteAttachments(moduleAPIName: String, recordId: Long, attachmentIds: ArrayBuffer[Long]): Unit = { //		List<Long> attachmentIds = new ArrayList<Long>(Arrays.asList(3477061000005979001l, 3477061000005968003, 3477061000005961010l))
    //Get instance of RecordOperations Class that takes recordId and moduleAPIName as parameter
    val attachmentOperations = new AttachmentsOperations(moduleAPIName,recordId)
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap

    for (attachmentId <- attachmentIds) {
      paramInstance.add(new DeleteAttachmentsParam().ids, attachmentId)
    }
    //Call deleteAttachments method that takes paramInstance as parameter
    val responseOption = attachmentOperations.deleteAttachments(Option(paramInstance))
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained Attachment Record instances
          val actionResponses = actionWrapper.getData

          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
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
   * <h3> Download Attachment</h3>
   * This method is used to download an attachment of a single record of a module with ID and attachment ID and write the file in the specified destination.
   *
   * @throws Exception
   * @param moduleAPIName     The API Name of the record's module
   * @param recordId          The ID of the record to download attachment
   * @param attachmentId      The ID of the attachment to be downloaded
   * @param destinationFolder The absolute path of the destination folder to store the attachment
   */
  @throws[Exception]
  def downloadAttachment(moduleAPIName: String, recordId: Long, attachmentId: Long, destinationFolder: String): Unit = { //		Long recordId = "3477061000005177002"
    //		Long attachmentId = "3477061000005177023"
    //		String destinationFolder = "./"
    val attachmentOperations = new AttachmentsOperations(moduleAPIName,recordId)
    //Call downloadAttachment method that takes attachmentId as parameters
    val responseOption = attachmentOperations.downloadAttachment(attachmentId)
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
        if (responseHandler.isInstanceOf[FileBodyWrapper]) {
          val fileBodyWrapper = responseHandler.asInstanceOf[FileBodyWrapper]
          //Get StreamWrapper instance from the returned FileBodyWrapper instance
          val streamWrapper = fileBodyWrapper.getFile.get
          //Create a file instance with the absolute_file_path
          val file = new File(destinationFolder + java.io.File.separatorChar + streamWrapper.getName.get)
          //Get InputStream from the response
          val is = streamWrapper.getStream.get
          //Create an OutputStream for the destination file
          val os = new FileOutputStream(file)
          val buffer = new Array[Byte](1024)
          var bytesRead:Integer = 0
          //read the InputStream till the end
          while ( {
            bytesRead = is.read(buffer)
            bytesRead != -1
          }) { //write data to OutputStream
            os.write(buffer, 0, bytesRead)
          }
          //Close the InputStream
          is.close()
          //Flush and close the OutputStream
          os.flush()
          os.close()
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
      else if (response.getStatusCode != 204) {
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
   * <h3> Delete Attachment</h3>
   * This method is used to delete an attachment to a single record of a module with ID and print the response.
   *
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to delete attachment
   * @param attachmentId  The ID of the attachment to be deleted
   */
  @throws[Exception]
  def deleteAttachment(moduleAPIName: String, recordId: Long, attachmentId: Long): Unit = { //		Long recordId = 3477061000005177002
    //		Long attachmentId = "3477061000005177002"
    val attachmentsOperations = new AttachmentsOperations(moduleAPIName,recordId)
    //Call deleteAttachment method that takes attachmentId as parameter
    val responseOption = attachmentsOperations.deleteAttachment(attachmentId)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained Action Response instances
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
   * <h3> Upload Link Attachments</h3>
   * This method is used to upload link attachment to a single record of a module with ID and print the response.
   *
   * @param moduleAPIName The API Name of the record's module
   * @param recordId      The ID of the record to upload Link attachment
   * @param attachmentURL The attachmentURL of the doc or image link to be attached
   */
  @throws[Exception]
  def uploadLinkAttachments(moduleAPIName: String, recordId: Long, attachmentURL: String): Unit = {
    val attachmentsOperations = new AttachmentsOperations(moduleAPIName,recordId)
    val paramInstance = new ParameterMap
    paramInstance.add(new UploadLinkAttachmentParam().attachmentUrl, attachmentURL)
    //Call uploadLinkAttachment method that takes paramInstance as parameter
    val responseOption = attachmentsOperations.uploadLinkAttachment(Option(paramInstance))
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
}

@SuppressWarnings(Array("unused")) class Attachments
{}
