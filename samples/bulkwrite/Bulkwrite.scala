package com.zoho.crm.sample.bulkwrite

import java.io.File
import java.io.FileOutputStream
import java.util

import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.bulkwrite.SuccessResponse
import com.zoho.crm.api.bulkwrite.APIException
import com.zoho.crm.api.bulkwrite.BulkWriteOperations
import com.zoho.crm.api.bulkwrite.BulkWriteOperations.UploadFileHeader
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.bulkwrite.BulkWriteResponse
import com.zoho.crm.api.bulkwrite.CallBack
import com.zoho.crm.api.bulkwrite.FieldMapping
import com.zoho.crm.api.bulkwrite.FileBodyWrapper
import com.zoho.crm.api.bulkwrite.RequestWrapper
import com.zoho.crm.api.bulkwrite.Resource

import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap



object Bulkwrite {
  /**
   * <h3> Upload File</h3>
   * This method is used to upload a CSV file in ZIP format for bulk write API. The response contains the file_id.
   * Use this ID while making the bulk write request.
   *
   * @param orgID            The unique ID (zgid) of your organization obtained through the Organization API.
   * @param absoluteFilePath The absoluteFilePath of the zip file you want to upload.
   * @throws Exception
   */
  @throws[Exception]
  def uploadFile(orgID: String, absoluteFilePath: String): Unit = { //example
    //String absoluteFilePath = "./Leads.zip"
    //String orgID = "673573045"
    //Get instance of BulkWriteOperations Class
    val bulkWriteOperations = new BulkWriteOperations
    //Get instance of FileBodyWrapper class that will contain the request file
    val fileBodyWrapper = new FileBodyWrapper
    //Get instance of StreamWrapper class that takes absolute path of the file to be attached as parameter

    val streamWrapper = new StreamWrapper(absoluteFilePath)

    //FileInputStream stream = new FileInputStream(absoluteFilePath)
    //Get instance of StreamWrapper class that takes file name and stream of the file to be attached as parameter
    //StreamWrapper streamWrapper = new StreamWrapper("Leads.zip", stream)
    //Set file to the FileBodyWrapper instance
    fileBodyWrapper.setFile(Option(streamWrapper))
    //Get instance of HeaderMap Class
    val headerInstance = new HeaderMap
    //To indicate that this a bulk write operation
    headerInstance.add(new UploadFileHeader().feature, "bulk-write")
    headerInstance.add(new UploadFileHeader().XCRMORG, orgID)
    //Call uploadFile method that takes FileBodyWrapper instance and headerInstance as parameter
    val responseOption = bulkWriteOperations.uploadFile(fileBodyWrapper, Option(headerInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val actionResponse = response.getObject
        //Check if the request is successful
        if (actionResponse.isInstanceOf[SuccessResponse]) { //Get the received SuccessResponse instance
          val successResponse = actionResponse.asInstanceOf[SuccessResponse]
          //Get the Status
          println("Status: " + successResponse.getStatus.getValue)
          //Get the Code
          println("Code: " + successResponse.getCode.getValue)
          println("Details: ")
          //Get the details map

          successResponse.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          //Get the Message
          println("Message: " + successResponse.getMessage.getValue)
        }
        else { //Check if the request returned an exception
          if (actionResponse.isInstanceOf[APIException]) { //Get the received APIException instance
            val exception = actionResponse.asInstanceOf[APIException]
            if (exception.getStatus != null) println("Status: " + exception.getStatus.getValue)
            if (exception.getCode != null) println("Code: " + exception.getCode.getValue)
            if (exception.getMessage != null) println("Message: " + exception.getMessage.getValue)
            println("Details: ")
            if (exception.getDetails != null) {

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
            if (exception.getErrorMessage != null) { //Get the ErrorMessage
              println("ErrorMessage: " + exception.getErrorMessage.getValue)
            }
            //Get the ErrorCode
            println("ErrorCode: " + exception.getErrorCode)
            if (exception.getXError != null) { //Get the XError
              println("XError: " + exception.getXError.getValue)
            }
            if (exception.getInfo != null) { //Get the Info
              println("Info: " + exception.getInfo.getValue)
            }
            if (exception.getXInfo != null) { //Get the XInfo
              println("XInfo: " + exception.getXInfo.getValue)
            }
            //Get the HttpStatus
            println("HttpStatus: " + exception.getHttpStatus)
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
   * <h3> Create BulkWrite Job</h3>
   * This method is used to create a bulk write job.
   *
   * @param moduleAPIName The API Name of the record's module.
   * @param fileId        The ID of the uploaded file to create BulkWrite Job.
   */
  @throws[Exception]
  def createBulkWriteJob(moduleAPIName: String, fileId: String): Unit = { //String moduleAPIName = "Leads"
    //String fileId  = "3477061000006121001"
    val bulkWriteOperations = new BulkWriteOperations
    //Get instance of RequestWrapper Class that will contain the request body
    val requestWrapper = new RequestWrapper
    //Get instance of CallBack Class
    val callback = new CallBack
    // To set valid callback URL.
    callback.setUrl(Option("https://www.example.com/callback"))
    //To set the HTTP method of the callback URL. The allowed value is post.
    callback.setMethod(new Choice[String]("post"))
    //The Bulk Write Job's details are posted to this URL on successful completion of job or on failure of job.
    requestWrapper.setCallback(Option(callback))
    //To set the charset of the uploaded file
    requestWrapper.setCharacterEncoding(Option("UTF-8"))
    //To set the type of operation you want to perform on the bulk write job.
    requestWrapper.setOperation(new Choice[String]("insert"))
    val resource = new ArrayBuffer[Resource]
    //Get instance of Resource Class
    val resourceIns = new Resource
    // To set the type of module that you want to import. The value is data.
    resourceIns.setType(new Choice[String]("data"))
    //To set API name of the module that you select for bulk write job.
    resourceIns.setModule(Option(moduleAPIName))
    //To set the file_id obtained from file upload API.
    resourceIns.setFileId(Option(fileId))
    //True - Ignores the empty values.The default value is false.
    resourceIns.setIgnoreEmpty(Option(true))
    // To set a field as a unique field or ID of a record.
    //resourceIns.setFindBy(Option("Last_Name"))
    val fieldMappings = new ArrayBuffer[FieldMapping]
    var fieldMapping:FieldMapping = null
    //Get instance of FieldMapping Class
    fieldMapping = new FieldMapping
    //To set API name of the field present in Zoho module object that you want to import.
    fieldMapping.setAPIName(Option("Last_Name"))
    //To set the column index of the field you want to map to the CRM field.
    fieldMapping.setIndex(Option(0))
    fieldMappings.addOne(fieldMapping)
    fieldMapping = new FieldMapping
    fieldMapping.setAPIName(Option("Email"))
    fieldMapping.setIndex(Option(1))
    fieldMappings.addOne(fieldMapping)
    fieldMapping = new FieldMapping
    fieldMapping.setAPIName(Option("Company"))
    fieldMapping.setIndex(Option(1))
    fieldMappings.addOne(fieldMapping)
    fieldMapping = new FieldMapping
    fieldMapping.setAPIName(Option("Phone"))
    fieldMapping.setIndex(Option(3))
    fieldMappings.addOne(fieldMapping)
    fieldMapping = new FieldMapping
    fieldMapping.setAPIName(Option("Website"))
    //fieldMapping.setFormat("")
    //fieldMapping.setFindBy("")
    val defaultValue:HashMap[String,Any] = HashMap()
    defaultValue.put("value", "https://www.zohoapis.com")
    //To set the default value for an empty column in the uploaded file.
    fieldMapping.setDefaultValue(defaultValue)
    fieldMappings.addOne(fieldMapping)
    resourceIns.setFieldMappings(fieldMappings)
    resource.addOne(resourceIns)
    requestWrapper.setResource(resource)
    //Call createBulkWriteJob method that takes RequestWrapper instance as parameter
    val responseOption = bulkWriteOperations.createBulkWriteJob(requestWrapper)
    if (responseOption.isDefined) {
      val response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionResponse = response.getObject
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
   * <h3> Get BulkWriteJob Details</h3>
   * This method is used to get the details of a bulk write job performed previously.
   *
   * @param jobId The unique ID of the bulk write job.
   * @throws Exception
   */
  @throws[Exception]
  def getBulkWriteJobDetails(jobId: Long): Unit = { //Long jobId = 3477061000005615003l
    val bulkWriteOperations = new BulkWriteOperations
    //Call getBulkWriteJobDetails method that takes jobId as parameter
    val responseOption = bulkWriteOperations.getBulkWriteJobDetails(jobId)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val responseWrapper = response.getObject
        if (responseWrapper.isInstanceOf[BulkWriteResponse]) { //Get the received BulkWriteResponse instance
          val bulkWriteResponse = responseWrapper.asInstanceOf[BulkWriteResponse]
          //Get the Job Status of each bulkWriteResponse
          println("Bulk write Job Status: " + bulkWriteResponse.getStatus)
          //Get the CharacterEncoding of each bulkWriteResponse
          println("Bulk write CharacterEncoding: " + bulkWriteResponse.getCharacterEncoding)
          val resources = bulkWriteResponse.getResource
          if (resources != null) {

            for (resource <- resources) { //Get the Status of each Resource
              println("Bulk write Resource Status: " + resource.getStatus.getValue)
              //Get the Type of each Resource
              println("Bulk write Resource Type: " + resource.getType.getValue)
              //Get the Module of each Resource
              println("Bulk write Resource Module: " + resource.getModule)
              val fieldMappings = resource.getFieldMappings
              if (fieldMappings != null) {

                for (fieldMapping <- fieldMappings) { //Get the APIName of each FieldMapping
                  println("Bulk write Resource FieldMapping Module: " + fieldMapping.getAPIName)
                  if (fieldMapping.getIndex.isDefined) { //Get the Index of each FieldMapping
                    println("Bulk write Resource FieldMapping Index: " + fieldMapping.getIndex.toString)
                  }
                  if (fieldMapping.getFormat.isDefined) { //Get the Format of each FieldMapping
                    println("Bulk write Resource FieldMapping Format: " + fieldMapping.getFormat)
                  }
                  if (fieldMapping.getFindBy.isDefined) { //Get the FindBy of each FieldMapping
                    println("Bulk write Resource FieldMapping FindBy: " + fieldMapping.getFindBy)
                  }
                  if (fieldMapping.getDefaultValue!=null) { //Get all entries from the keyValues map

                    fieldMapping.getDefaultValue.foreach(entry=>{
                      println(entry._1 + ": " + entry._2)
                    })
                  }
                }
              }
              val fileOption = resource.getFile
              if (fileOption.isDefined) { //Get the Status of each File
                val file= fileOption.get
                println("Bulk write Resource File Status: " + file.getStatus.getValue)
                //Get the Name of each File
                println("Bulk write Resource File Name: " + file.getName)
                //Get the AddedCount of each File
                println("Bulk write Resource File AddedCount: " + file.getAddedCount.toString)
                //Get the SkippedCount of each File
                println("Bulk write Resource File SkippedCount: " + file.getSkippedCount.toString)
                //Get the UpdatedCount of each File
                println("Bulk write Resource File UpdatedCount: " + file.getUpdatedCount.toString)
                //Get the TotalCount of each File
                println("Bulk write Resource File TotalCount: " + file.getTotalCount.toString)
              }
              println("Bulk write Resource FindBy: " + resource.getFindBy)
            }
          }
          val callbackOption = bulkWriteResponse.getCallback
          if (callbackOption.isDefined) { //Get the CallBack Url
            val callback= callbackOption.get
            println("Bulk write CallBack Url: " + callback.getUrl)
            //Get the CallBack Method
            println("Bulk write CallBack Method: " + callback.getMethod.getValue)
          }
          //Get the ID of each BulkWriteResponse
          println("Bulk write ID: " + bulkWriteResponse.getId.toString)
          val resultOption = bulkWriteResponse.getResult
          if (resultOption.isDefined) { //Get the DownloadUrl of the Result
            val result = resultOption.get
            println("Bulk write DownloadUrl: " + result.getDownloadUrl)
          }
          //Get the CreatedBy User instance of each BulkWriteResponse
          val createdByOption = bulkWriteResponse.getCreatedBy
          //Check if createdBy is not null
          if (createdByOption.isDefined) { //Get the ID of the CreatedBy User
            var createdBy = createdByOption.get
            println("Bulkread Created By User-ID: " + createdBy.getId)
            //Get the Name of the CreatedBy User
            println("Bulkread Created By user-Name: " + createdBy.getName)
          }
          //Get the Operation of each BulkWriteResponse
          println("Bulk write Operation: " + bulkWriteResponse.getOperation)
          //Get the CreatedTime of each BulkWriteResponse
          println("Bulk write File CreatedTime: " + bulkWriteResponse.getCreatedTime.toString)
        }
        else if (responseWrapper.isInstanceOf[APIException]) {
          val exception = responseWrapper.asInstanceOf[APIException]
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
   * <h3> Download BulkWrite Result</h3>
   * This method is used to download the result of the bulk write job as a CSV file.
   *
   * @param downloadUrl       The URL present in the download_url parameter in the response of Get Bulk Write Job Details.
   * @param destinationFolder The absolute path where downloaded file has to be stored.
   * @throws Exception
   */
  @throws[Exception]
  def downloadBulkWriteResult(downloadUrl: String, destinationFolder: String): Unit = { //String downloadUrl = "https://download-accl.zoho.com/v2/crm/6735/bulk-write/347706122009/347706122009.zip"
    //String destinationFolder = "./Documents"
    val bulkWriteOperations = new BulkWriteOperations
    //Call downloadBulkWriteResult method that takes downloadUrl as parameter
    val responseOption = bulkWriteOperations.downloadBulkWriteResult(downloadUrl)
    if (responseOption.isDefined) {
      var response = responseOption.get
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
        else if (responseHandler.isInstanceOf[APIException]) {
          val exception = responseHandler.asInstanceOf[APIException]
          if (exception.getStatus != null) println("Status: " + exception.getStatus.getValue)
          if (exception.getCode != null) println("Code: " + exception.getCode.getValue)
          if (exception.getDetails != null) {
            println("Details: ")

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
          }
          if (exception.getMessage != null) println("Message: " + exception.getMessage.getValue)
          if (exception.getXError != null) println("XError: " + exception.getXError.getValue)
          if (exception.getXInfo != null) println("XInfo: " + exception.getXInfo.getValue)
          if (exception.getHttpStatus != null) println("Message: " + exception.getHttpStatus)
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

class Bulkwrite {}
