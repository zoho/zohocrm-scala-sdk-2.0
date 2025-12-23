package com.zoho.crm.sample.tags

import java.util

import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.tags.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, ConflictWrapper, CountHandler, CountWrapper, Info, MergeWrapper, RecordActionHandler, RecordActionResponse, RecordActionWrapper, ResponseHandler, ResponseWrapper, SuccessResponse, Tag, TagsOperations}
import com.zoho.crm.api.tags.TagsOperations.CreateTagsParam
import com.zoho.crm.api.tags.TagsOperations.GetRecordCountForTagParam
import com.zoho.crm.api.tags.TagsOperations.GetTagsParam
import com.zoho.crm.api.tags.TagsOperations.RemoveTagsFromMultipleRecordsParam
import com.zoho.crm.api.tags.TagsOperations.RemoveTagsFromRecordParam
import com.zoho.crm.api.tags.TagsOperations.UpdateTagParam
import com.zoho.crm.api.tags.TagsOperations.UpdateTagsParam
import com.zoho.crm.api.tags.TagsOperations.AddTagsToMultipleRecordsParam
import com.zoho.crm.api.tags.TagsOperations.AddTagsToRecordParam
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Tags {
  /**
   * <h3> Get Tags </h3>
   * This method is used to get all the tags in a module.
   *
   * @param moduleAPIName - The API Name of the module to get tags.
   * @throws Exception
   */
  @throws[Exception]
  def getTags(moduleAPIName: String): Unit = { //example
    //String moduleAPIName = "Leads"
    //Get instance of TagsOperations Class
    val tagsOperations = new TagsOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new GetTagsParam().module, moduleAPIName)
    //paramInstance.add(GetTagsParam.MY_TAGS, "") //Displays the names of the tags created by the current user.
    //Call getTags method that takes paramInstance as parameter
    val responseOption = tagsOperations.getTags(Option(paramInstance))
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
          //Get the obtained ShareRecord instance
          val tags = responseWrapper.getTags

          for (tag <- tags) { //Get the CreatedTime of each Tag
            println("Tag CreatedTime: " + tag.getCreatedTime)
            //Get the ModifiedTime of each Tag
            println("Tag ModifiedTime: " + tag.getModifiedTime)
            //Get the Name of each Tag
            println("Tag Name: " + tag.getName)
            //Get the modifiedBy User instance of each Tag
            val modifiedByOption = tag.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the ID of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Tag Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Tag Modified By User-Name: " + modifiedBy.getName)
            }
            //Get the ID of each Tag
            println("Tag ID: " + tag.getId)
            //Get the createdBy User instance of each Tag
            val createdByOption = tag.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the ID of the createdBy User
              val createdBy = createdByOption.get
              println("Tag Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Tag Created By User-Name: " + createdBy.getName)
            }
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
            if (info.getCount != null) { //Get the Count of the Info
              println("Tag Info Count: " + info.getCount.toString)
            }
            if (info.getAllowedCount != null) { //Get the AllowedCount of the Info
              println("Tag Info AllowedCount: " + info.getAllowedCount.toString)
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

            exception.getDetails.foreach(entry=>{ //Get each value in the map
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
   * <h3> Create Tags </h3>
   * This method is used to create new tags and print the response.
   *
   * @param moduleAPIName - The API Name of the module to create tags.
   * @throws Exception
   */
  @throws[Exception]
  def createTags(moduleAPIName: String): Unit = {
    val tagsOperations = new TagsOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new BodyWrapper
    val paramInstance = new ParameterMap
    paramInstance.add(new CreateTagsParam().module, moduleAPIName)
    //List of Tag instances
    val tagList = new ArrayBuffer[Tag]
    //Get instance of Tag Class
    val tag1 = new Tag
    tag1.setName(Option("tagName"))
    tagList.addOne(tag1)
    request.setTags(tagList)
    //Call createTags method that takes BodyWrapper instance and paramInstance as parameter
    val responseOption = tagsOperations.createTags(request, Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getTags

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
   * <h3> Update Tags </h3>
   * This method is used to update multiple tags simultaneously and print the response.
   *
   * @param moduleAPIName - The API Name of the module to update tags.
   * @throws Exception
   */
  @throws[Exception]
  def updateTags(moduleAPIName: String): Unit = {
    val tagsOperations = new TagsOperations
    val request = new BodyWrapper
    val paramInstance = new ParameterMap
    paramInstance.add(new UpdateTagsParam().module, moduleAPIName)
    val tagList = new ArrayBuffer[Tag]
    val tag1 = new Tag
    tag1.setId(Option(3477061000011665002l))
    tag1.setName(Option("tagName12367"))
    tagList.addOne(tag1)
    request.setTags(tagList)
    //Call updateTags method that takes BodyWrapper instance and paramInstance as parameter
    val responseOption = tagsOperations.updateTags(request, Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTags

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
   * <h3> Update Tag </h3>
   * This method is used to update single tag and print the response.
   *
   * @param moduleAPIName - The API Name of the module to update tag.
   * @param tagId         - The ID of the tag to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def updateTag(moduleAPIName: String, tagId: Long): Unit = { //Long tagId = 3477061000005794039
    val tagsOperations = new TagsOperations
    val request = new BodyWrapper
    val paramInstance = new ParameterMap
    paramInstance.add(new UpdateTagParam().module, moduleAPIName)
    val tagList = new ArrayBuffer[Tag]
    val tag1 = new Tag
    tag1.setName(Option("tagName12345"))
    tagList.addOne(tag1)
    request.setTags(tagList)
    //Call updateTag method that takes BodyWrapper instance, paramInstance and tagId as parameter
    val responseOption = tagsOperations.updateTag(tagId,request, Option(paramInstance ))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTags

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
   * <h3> Delete Tag </h3>
   * This method is used to delete a tag from the module and print the response.
   *
   * @param tagId - The ID of the tag to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def deleteTag(tagId: Long): Unit = {
    val tagsOperations = new TagsOperations
    //Call deleteTag method that takes tag id as parameter
    val responseOption = tagsOperations.deleteTag(tagId)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTags

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
   * <h3> Merge Tag </h3>
   * This method is used to merge tags and put all the records under the two tags into a single tag and print the response.
   *
   * @param tagId      - The ID of the tag to be obtained.
   * @param conflictId - The ID of the conflict tag to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def mergeTags(tagId: Long, conflictId: String): Unit = { //Long conflictId = 3477061000005803151
    val tagsOperations = new TagsOperations
    //Get instance of MergeWrapper Class that will contain the request body
    val request = new MergeWrapper
    //List of Tag ConflictWrapper
    val tags = new ArrayBuffer[ConflictWrapper]
    //Get instance of ConflictWrapper Class
    val mergeTag = new ConflictWrapper
    mergeTag.setConflictId(Option(conflictId))
    tags.addOne(mergeTag)
    request.setTags(tags)
    //Call mergeTags method that takes MergeWrapper instance and tag id as parameter
    val responseOption = tagsOperations.mergeTags(tagId,request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getTags

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
   * <h3> Add Tags To Record </h3>
   * This method is used to add tags to a specific record and print the response.
   *
   * @param moduleAPIName - The API Name of the module to add tag.
   * @param recordId      - The ID of the record to be obtained.
   * @param tagNames      - The names of the tags to be added.
   * @throws Exception
   */
  @throws[Exception]
  def addTagsToRecord(moduleAPIName: String, recordId: Long, tagNames: ArrayBuffer[String]): Unit = { //long recordId =  3477061000005623115L
    //ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("addtag1,addtag12"))
    val tagsOperations = new TagsOperations
    val paramInstance = new ParameterMap

    for (tagName <- tagNames) {
      paramInstance.add(new AddTagsToRecordParam().tagNames, tagName)
    }
    paramInstance.add(new AddTagsToRecordParam().overWrite, "false")
    //Call addTagsToRecord method that takes paramInstance, moduleAPIName and recordId as parameter
    val responseOption = tagsOperations.addTagsToRecord( recordId,moduleAPIName,Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val recordActionHandler = response.getObject
        if (recordActionHandler.isInstanceOf[RecordActionWrapper]) { //Get the received RecordActionWrapper instance
          val recordActionWrapper = recordActionHandler.asInstanceOf[RecordActionWrapper]
          //Get the list of obtained RecordActionResponse instances
          val actionResponses = recordActionWrapper.getData

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
        else if (recordActionHandler.isInstanceOf[APIException]) {
          val exception = recordActionHandler.asInstanceOf[APIException]
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
   * <h3> Remove Tags From Record </h3>
   * This method is used to delete the tag associated with a specific record and print the response.
   *
   * @param moduleAPIName - The API Name of the module to remove tag.
   * @param recordId      - The ID of the record to be obtained.
   * @param tagNames      - The names of the tags to be remove.
   * @throws Exception
   */
  @throws[Exception]
  def removeTagsFromRecord(moduleAPIName: String, recordId: Long, tagNames: ArrayBuffer[String]): Unit = {
    val tagsOperations = new TagsOperations
    val paramInstance = new ParameterMap

    for (tagName <- tagNames) {
      paramInstance.add(new RemoveTagsFromRecordParam().tagNames, tagName)
    }
    //Call removeTagsFromRecord method that takes paramInstance, moduleAPIName and recordId as parameter
    val responseOption = tagsOperations.removeTagsFromRecord( recordId,moduleAPIName,Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val recordActionHandler = response.getObject
        if (recordActionHandler.isInstanceOf[RecordActionWrapper]) {
          val recordActionWrapper = recordActionHandler.asInstanceOf[RecordActionWrapper]
          val actionResponses = recordActionWrapper.getData

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
        else if (recordActionHandler.isInstanceOf[APIException]) {
          val exception = recordActionHandler.asInstanceOf[APIException]
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
   * <h3> Add Tags To Multiple Records </h3>
   * This method is used to add tags to multiple records simultaneously and print the response.
   *
   * @param moduleAPIName - The API Name of the module to add tags.
   * @param recordIds     - The ID of the record to be obtained.
   * @param tagNames      - The names of the tags to be add.
   * @throws Exception
   */
  @throws[Exception]
  def addTagsToMultipleRecords(moduleAPIName: String, recordIds: ArrayBuffer[Long], tagNames: ArrayBuffer[String]): Unit = { //ArrayList<Long> recordIds = new ArrayList<String>(Arrays.asList(3477061000005623115l, 3477061000006114067l))
    val tagsOperations = new TagsOperations
    val paramInstance = new ParameterMap

    for (tagName <- tagNames) {
      paramInstance.add(new AddTagsToMultipleRecordsParam().tagNames, tagName)
    }

    for (recordId <- recordIds) {
      paramInstance.add(new AddTagsToMultipleRecordsParam().ids, recordId)
    }
    paramInstance.add(new AddTagsToMultipleRecordsParam().overWrite, "false")
    //Call addTagsToMultipleRecords method that takes paramInstance, moduleAPIName as parameter
    val responseOption = tagsOperations.addTagsToMultipleRecords(moduleAPIName,Option(paramInstance) )
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val recordActionHandler = response.getObject
        if (recordActionHandler.isInstanceOf[RecordActionWrapper]) {
          val recordActionWrapper = recordActionHandler.asInstanceOf[RecordActionWrapper]
          val actionResponses = recordActionWrapper.getData

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
          //Check if locked count is null
          if (recordActionWrapper.getLockedCount != null) println("Locked Count: " + recordActionWrapper.getLockedCount.toString)
          //Check if success count is null
          if (recordActionWrapper.getSuccessCount != null) println("Success Count: " + recordActionWrapper.getSuccessCount)
          //Check if wf scheduler is null
          if (recordActionWrapper.getWfScheduler != null) println("WF Scheduler: " + recordActionWrapper.getWfScheduler)
        }
        else if (recordActionHandler.isInstanceOf[APIException]) {
          val exception = recordActionHandler.asInstanceOf[APIException]
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
   * <h3> Remove Tags From Multiple Records </h3>
   * This method is used to delete the tags associated with multiple records and print the response.
   *
   * @param moduleAPIName - The API Name of the module to remove tags.
   * @param recordIds     - The ID of the record to be obtained.
   * @param tagNames      - The names of the tags to be remove.
   * @throws Exception
   */
  @throws[Exception]
  def removeTagsFromMultipleRecords(moduleAPIName: String, recordIds: ArrayBuffer[Long], tagNames: ArrayBuffer[String]): Unit = { //ArrayList<Long> recordIds = new ArrayList<Long>(Arrays.asList(3477061000005623115, 3477061000006114067))
    val tagsOperations = new TagsOperations
    val paramInstance = new ParameterMap

    for (tagName <- tagNames) {
      paramInstance.add(new RemoveTagsFromMultipleRecordsParam().tagNames, tagName)
    }

    for (recordId <- recordIds) {
      paramInstance.add(new RemoveTagsFromMultipleRecordsParam().ids, recordId)
    }
    //Call RemoveTagsFromMultipleRecordsParam method that takes paramInstance, moduleAPIName as parameter
    val responseOption = tagsOperations.removeTagsFromMultipleRecords(moduleAPIName,Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val recordActionHandler = response.getObject
        if (recordActionHandler.isInstanceOf[RecordActionWrapper]) {
          val recordActionWrapper = recordActionHandler.asInstanceOf[RecordActionWrapper]
          val actionResponses = recordActionWrapper.getData

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
          if (recordActionWrapper.getLockedCount != null) println("Locked Count: " + recordActionWrapper.getLockedCount.toString)
          if (recordActionWrapper.getSuccessCount != null) println("Success Count: " + recordActionWrapper.getSuccessCount)
          if (recordActionWrapper.getWfScheduler != null) println("WF Scheduler: " + recordActionWrapper.getWfScheduler)
        }
        else if (recordActionHandler.isInstanceOf[APIException]) {
          val exception = recordActionHandler.asInstanceOf[APIException]
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
   * <h3> Get Record Count For Tag </h3>
   * This method is used to get the total number of records under a tag and print the response.
   *
   * @param moduleAPIName - The API Name of the module.
   * @param tagId         - The ID of the tag to be obtained.
   * @throws Exception
   */
  @throws[Exception]
  def getRecordCountForTag(moduleAPIName: String, tagId: Long): Unit = { //Long tagId = 3477061000005803151l
    val tagsOperations = new TagsOperations
    val paramInstance = new ParameterMap
    paramInstance.add(new GetRecordCountForTagParam().module, moduleAPIName)
    //Call getRecordCountForTag method that takes paramInstance and tagId as parameter
    val responseOption = tagsOperations.getRecordCountForTag( tagId,Option(paramInstance))
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val countHandler = response.getObject
        if (countHandler.isInstanceOf[CountWrapper]) { //Get the received CountWrapper instance
          val countWrapper = countHandler.asInstanceOf[CountWrapper]
          //Get the Count of Tag
          println("Tag Count: " + countWrapper.getCount)
        }
        else if (countHandler.isInstanceOf[APIException]) {
          val exception = countHandler.asInstanceOf[APIException]
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

class Tags {}