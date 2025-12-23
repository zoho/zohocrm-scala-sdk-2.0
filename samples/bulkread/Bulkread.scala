package com.zoho.crm.sample.bulkread

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Field
import java.util

import com.zoho.crm.api.bulkread.APIException
import com.zoho.crm.api.bulkread.ActionHandler
import com.zoho.crm.api.bulkread.ActionResponse
import com.zoho.crm.api.bulkread.ActionWrapper
import com.zoho.crm.api.bulkread.BulkReadOperations
import com.zoho.crm.api.bulkread.CallBack
import com.zoho.crm.api.bulkread.Criteria
import com.zoho.crm.api.bulkread.FileBodyWrapper
import com.zoho.crm.api.bulkread.JobDetail
import com.zoho.crm.api.bulkread.Query
import com.zoho.crm.api.bulkread.RequestWrapper
import com.zoho.crm.api.bulkread.ResponseHandler
import com.zoho.crm.api.bulkread.ResponseWrapper
import com.zoho.crm.api.bulkread.Result
import com.zoho.crm.api.bulkread.SuccessResponse
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper

import scala.collection.mutable.ArrayBuffer


object Bulkread {
  /**
   * <h3> Create BulkRead Job </h3>
   * This method is used to create a bulk read job to export records.
   *
   * @param moduleAPIName The API Name of the record's module
   * @throws Exception
   */
  @throws[Exception]
  def createBulkReadJob(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of BulkReadOperations Class
    val bulkReadOperations = new BulkReadOperations
    //Get instance of RequestWrapper Class that will contain the request body
    val requestWrapper = new RequestWrapper
    //Get instance of CallBack Class
    val callback = new CallBack
    // To set valid callback URL.
    callback.setUrl(Option("https://www.example.com/callback"))
    //To set the HTTP method of the callback URL. The allowed value is post.
    callback.setMethod(new Choice[String]("post"))
    //The Bulk Read Job's details is posted to this URL on successful completion / failure of job.
    requestWrapper.setCallback(Option(callback))
    //Get instance of Query Class
    val query = new Query
    //Specifies the API Name of the module to be read.
    query.setModule(Option(moduleAPIName))
    //Specifies the unique ID of the custom view whose records you want to export.
    //		query.setCvid("3477061000000087501")
    // List of Field API Names
    val fieldAPINames = new ArrayBuffer[String]
    fieldAPINames.addOne("All_day")
    //Specifies the API Name of the fields to be fetched.
    query.setFields(fieldAPINames)
    //To set page value, By default value is 1.
    query.setPage(Option(1))
    //Get instance of Criteria Class
    val criteria = new Criteria
    criteria.setGroupOperator(new Choice[String]("or"))
    val criteriaList = new ArrayBuffer[Criteria]
    val group11 = new Criteria
    group11.setGroupOperator(new Choice[String]("and"))
    val groupList11 = new ArrayBuffer[Criteria]
    val group111 = new Criteria
    group111.setAPIName(Option("All_day"))
    group111.setComparator(new Choice[String]("equal"))
    group111.setValue(false)
    groupList11.addOne(group111)
    val group112 = new Criteria
    group112.setAPIName(Option("Owner"))
    group112.setComparator(new Choice[String]("in"))
    val owner = new ArrayBuffer[String]
    owner += ("3477061000000173021")
    group112.setValue(owner)
    groupList11.addOne(group112)
    group11.setGroup(groupList11)
    criteriaList.addOne(group11)
    val group12 = new Criteria
    group12.setGroupOperator(new Choice[String]("or"))
    val groupList12 = new ArrayBuffer[Criteria]
    val group121 = new Criteria
    group121.setAPIName(Option("Event_Title"))
    group121.setComparator(new Choice[String]("equal"))
    group121.setValue("New Automated Event")
    groupList12.addOne(group121)
    val group122 = new Criteria
    // To set API name of a field.
    group122.setAPIName(Option("Created_Time"))
    // To set comparator(eg: equal, greater_than.).
    group122.setComparator(new Choice[String]("between"))
    val createdTime = new ArrayBuffer[String]
    createdTime+=("2020-06-03T17:31:48+05:30", "2020-06-03T17:31:48+05:30")
    // To set the value to be compare.
    group122.setValue(createdTime)
    groupList12.addOne(group122)
    group12.setGroup(groupList12)
    criteriaList.addOne(group12)
    criteria.setGroup(criteriaList)
    //To filter the records to be exported.
    query.setCriteria(Option(criteria))
    //To set query JSON object.
    requestWrapper.setQuery(Option(query))
    //Specify the value for this key as "ics" to export all records in the Events module as an ICS file.
    //requestWrapper.setFileType(new Choice<String>("ics"))
    //Sample request body
    //		{"query":{"criteria":{"group_operator":"or","group":[{"group_operator":"and","group":[{"comparator":"equal","api_name":"All_day","value":false},
    //       {"comparator":"in","api_name":"Owner","value":["3477061001"]}]},{"group_operator":"or","group":[{"comparator":"equal","api_name":"Event_Title","value":"New Event"},
    //       {"comparator":"between","api_name":"Created_Time","value":["2020-06-03T17:31:48+05:30","2020-06-03T17:31:48+05:30"]}]}]},"module":"Events","page":1},
    //		"callback":{"method":"post","url":"https://www.example.com/callback"}}
    //
    //Call createBulkReadJob method that takes RequestWrapper instance as parameter
    val responseOption = bulkReadOperations.createBulkReadJob(requestWrapper)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
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
   * <h3> Get BulkRead Job Details</h3>
   * This method is used to get the details of a bulk read job performed previously.
   *
   * @param jobId The unique ID of the bulk read job.
   * @throws Exception
   */
  @throws[Exception]
  def getBulkReadJobDetails(jobId: Long): Unit = { //Long jobId = 3477061000005177002l
    val bulkReadOperations = new BulkReadOperations
    //Call getBulkReadJobDetails method that takes jobId as parameter
    val responseOption = bulkReadOperations.getBulkReadJobDetails(jobId)
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
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the list of obtained jobDetail instances
          val jobDetails = responseWrapper.getData
          for (jobDetail <- jobDetails) { //Get the Job ID of each jobDetail
            println("Bulk read Job ID: " + jobDetail.getId)
            //Get the Operation of each jobDetail
            println("Bulk read Operation: " + jobDetail.getOperation)
            println("Bulk read State: " + jobDetail.getState.getValue)
            //Get the Result instance of each jobDetail
            val resultOption = jobDetail.getResult
            //Check if Result is not null
            if (resultOption.isDefined) { //Get the Page of the Result
              var result = resultOption.get
              println("Bulkread Result Page: " + result.getPage.toString)
              //Get the Count of the Result
              println("Bulkread Result Count: " + result.getCount.toString)
              //Get the Download URL of the Result
              println("Bulkread Result Download URL: " + result.getDownloadUrl)
              //Get the Per_Page of the Result
              println("Bulkread Result Per_Page: " + result.getPerPage.toString)
              //Get the MoreRecords of the Result
              println("Bulkread Result MoreRecords: " + result.getMoreRecords.toString)
            }
            // Get the Query instance of each jobDetail
            val queryOption = jobDetail.getQuery
            if (queryOption.isDefined) { //Get the Module Name of the Query
              var query= queryOption.get
              println("Bulk read Query Module: " + query.getModule)
              //Get the Page of the Query
              println("Bulk read Query Page: " + query.getPage.toString)
              //Get the cvid of the Query
              println("Bulk read Query cvid: " + query.getCvid)
              //Get the fields List of each Query
              val fields = query.getFields
              //Check if fields is not null
              if (fields != null) {
                for (fieldName <- fields) { //Get the Field Name of the Query
                  println("Bulk read Query Fields: " + fieldName)
                }
              }
              // Get the Criteria instance of each Query
              val criteriaOption = query.getCriteria
              //Check if criteria is not null
              if (criteriaOption.isDefined) printCriteria(criteriaOption.get)
            }
            //Get the CreatedBy User instance of each jobDetail
            val createdByOption = jobDetail.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the CreatedBy User
              var createdBy = createdByOption.get
              println("Bulkread Created By User-ID: " + createdBy.getId)
              //Get the Name of the CreatedBy User
              println("Bulkread Created By user-Name: " + createdBy.getName)
            }
            //Get the CreatedTime of each jobDetail
            println("Bulkread CreatedTime: " + jobDetail.getCreatedTime)
            //Get the ID of each jobDetail
            println("Bulkread File Type: " + jobDetail.getFileType)
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

  private def printCriteria(criteria: Criteria): Unit = { //Get the APIName of the Criteria
    println("Bulk read Query Criteria APIName: " + criteria.getAPIName)
    if (criteria.getComparator != null) { //Get the Comparator of the Criteria
      println("Bulk read Query Criteria Comparator: " + criteria.getComparator.getValue)
    }
    if (criteria.getValue != null) { //Get the Value of the Criteria
      println("Bulk read Query Criteria Value: " + criteria.getValue.toString)
    }
    //Get the List of Criteria instance of each Criteria
    val criteriaGroup = criteria.getGroup
    if (criteriaGroup != null) {
      for (criteria1 <- criteriaGroup) {
        printCriteria(criteria1)
      }
    }
    if (criteria.getGroupOperator != null) { //Get the Group Operator of the Criteria
      println("Bulk read Query Criteria Group Operator: " + criteria.getGroupOperator.getValue)
    }
  }

  /**
   * <h3> Download Result</h3>
   * This method is used to download the bulk read job as a CSV or an ICS file (only for the Events module).
   *
   * @param jobId             The unique ID of the bulk read job.
   * @param destinationFolder The absolute path where downloaded file has to be stored.
   * @throws Exception
   */
  @throws[Exception]
  def downloadResult(jobId: Long, destinationFolder: String): Unit = { //String destinationFolder = "./Documents"
    val bulkReadOperations = new BulkReadOperations
    //Call downloadResult method that takes jobId as parameters
    val responseOption = bulkReadOperations.downloadResult(jobId)
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
        if (responseHandler.isInstanceOf[FileBodyWrapper]) { //Get the received FileBodyWrapper instance
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

class Bulkread {}
