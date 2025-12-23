package com.zoho.crm.sample.file

import java.io.{File, FileOutputStream, InputStream, OutputStream}
import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.file.APIException
import com.zoho.crm.api.file.ActionHandler
import com.zoho.crm.api.file.ActionResponse
import com.zoho.crm.api.file.ActionWrapper
import com.zoho.crm.api.file.BodyWrapper
import com.zoho.crm.api.file.FileBodyWrapper
import com.zoho.crm.api.file.FileOperations
import com.zoho.crm.api.file.ResponseHandler
import com.zoho.crm.api.file.SuccessResponse
import com.zoho.crm.api.file.FileOperations.GetFileParam
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer


object Files {
  /**
   * <h3> Upload File</h3>
   * This method is used to upload a file and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def uploadFile(): Unit = { //Get instance of RecordOperations Class
    val fileOperations = new FileOperations
    val bodyWrapper = new BodyWrapper
    //Get instance of StreamWrapper class that takes absolute path of the file to be attached as parameter
    val streamWrapper = new StreamWrapper("./download.png")
    val streamWrapper1 = new StreamWrapper("./ownload.png")
    var file = new ArrayBuffer[StreamWrapper]
    file+=(streamWrapper, streamWrapper1)
    bodyWrapper.setFile(file)
    val paramInstance = new ParameterMap
    //Call uploadFile method that takes BodyWrapper instance as parameter.
    val responseOption = fileOperations.uploadFiles(bodyWrapper, Option(paramInstance))
    if (responseOption.isDefined) { //check response
       var response= responseOption.get
    println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained action responses
          val actionResponses = actionWrapper.getData
          
          for (actionResponse <- actionResponses) { //Check if the request is successful
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
   * <h3> Get File</h3>
   *
   * @param id                - The ID of the uploaded File.
   * @param destinationFolder - The absolute path of the destination folder to store the File
   * @throws Exception
   */
  @throws[Exception]
  def getFile(id: String, destinationFolder: String): Unit = { //example
    //String id = "ae9c7cefa418aec1d6a5cc2d9ab35c3231aae3bfeef7d5e00a54b7563c0dd42b"
    //String destinationFolder = "./"
    //Get instance of FileOperations Class
    val fileOperations = new FileOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new GetFileParam().id, id)
    //Call getFile method that takes paramInstance as parameters
    val responseOption = fileOperations.getFile(Option(paramInstance))
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
}

class Files {}
