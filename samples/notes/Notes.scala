package com.zoho.crm.sample.notes

import java.lang.reflect.Field
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util

import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.attachments.Attachment
import com.zoho.crm.api.notes.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, Info, Note, NotesOperations, ResponseHandler, ResponseWrapper, SuccessResponse}
import com.zoho.crm.api.notes.NotesOperations.DeleteNotesParam
import com.zoho.crm.api.notes.NotesOperations.GetNotesHeader
import com.zoho.crm.api.notes.NotesOperations.GetNotesParam
import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Notes {
  /**
   * <h3> Get Notes </h3>
   * This method is used to get the list of notes and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getNotes(): Unit = { //Get instance of NotesOperations Class
    val notesOperations = new NotesOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new GetNotesParam().page, 1)
    //paramInstance.add(GetNotesParam.PER_PAGE, 1)
    //Get instance of HeaderMap Class
    val headerInstance = new HeaderMap
    val startdatetime = OffsetDateTime.of(2019, 6, 1, 1, 0, 1, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetNotesHeader().IfModifiedSince, startdatetime)
    //Call getNotes method that takes paramInstance and headerInstance as parameters
    val responseOption = notesOperations.getNotes(Option(paramInstance), Option(headerInstance))
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
          //Get the list of obtained Note instances
          val notes = responseWrapper.getData

          for (note <- notes) { //Get the owner User instance of each Note
            val ownerOption = note.getOwner
            //Check if owner is not null
            if (ownerOption.isDefined) { //Get the name of the owner User
              val owner= ownerOption.get
              println("Note Owner User-Name: " + owner.getName)
              //Get the ID of the owner User
              println("Note Owner User-ID: " + owner.getId)
              //Get the Email of the owner User
              println("Note Owner Email: " + owner.getEmail)
            }
            //Get the ModifiedTime of each Module
            println("Note ModifiedTime: " + note.getModifiedTime)
            //Get the list of Attachment instance each Note
            val attachments = note.getattachments()
            //Check if attachments is not null
            if (attachments != null) {

              for (attachment <- attachments) {
                printAttachment(attachment)
              }
            }
            //Get the CreatedTime of each Note
            println("Note CreatedTime: " + note.getCreatedTime)
            //Get the parentId Record instance of each Note
            val parentIdOption = note.getParentId
            //Check if parentId is not null
            if (parentIdOption.isDefined) {
              val parentId = parentIdOption.get
              if (parentId.getKeyValue("name") != null) { //Get the parent record Name of each Note
                println("Note parent record Name: " + parentId.getKeyValue("name"))
              }
              //Get the parent record ID of each Note
              println("Note parent record ID: " + parentId.getId)
            }
            //Get the Editable of each Note
            println("Note Editable: " + note.geteditable().toString)
            //Get the SeModule of each Note
            println("Note SeModule: " + note.getseModule())
            //Get the IsSharedToClient of each Note
            println("Note IsSharedToClient: " + note.getisSharedToClient().toString)
            //Get the modifiedBy User instance of each Note
            val modifiedByOption = note.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Note Modified By User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Note Modified By User-ID: " + modifiedBy.getId)
              //Get the Email of the modifiedBy User
              println("Note Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the Size of each Note
            println("Note Size: " + note.getsize())
            //Get the State of each Note
            println("Note State: " + note.getstate())
            //Get the VoiceNote of each Note
            println("Note VoiceNote: " + note.getvoiceNote().toString)
            //Get the Id of each Note
            println("Note Id: " + note.getId)
            //Get the createdBy User instance of each Note
            val createdByOption = note.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Note Created By User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Note Created By User-ID: " + createdBy.getId)
              //Get the Email of the createdBy User
              println("Note Created By User-Email: " + createdBy.getEmail)
            }
            //Get the NoteTitle of each Note
            println("Note NoteTitle: " + note.getNoteTitle)
            //Get the NoteContent of each Note
            println("Note NoteContent: " + note.getNoteContent)
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          if (infoOption.isDefined) {
            val info=infoOption.get
            if (info.getPerPage.isDefined) { //Get the PerPage of the Info
              println("Note Info PerPage: " + info.getPerPage.toString)
            }
            if (info.getCount.isDefined) { //Get the Count of the Info
              println("Note Info Count: " + info.getCount.toString)
            }
            if (info.getPage.isDefined) { //Get the Default of the Info
              println("Note Info Page: " + info.getPage)
            }
            if (info.getMoreRecords.isDefined) println("Note Info MoreRecords: " + info.getMoreRecords.toString)
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

            if (exception.getDetails != null) {
              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
            }
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

  private def printAttachment(attachment: Attachment): Unit = { //Get the Owner User instance of each attachment
    val ownerOption = attachment.getOwner
    if (ownerOption.isDefined) { //Get the Name of the Owner
      val owner = ownerOption.get
      println("Note Attachment Owner User-Name: " + owner.getName)
      //Get the ID of the Owner
      println("Note Attachment Owner User-ID: " + owner.getId)
      //Get the Email of the Owner
      println("Note Attachment Owner User-Email: " + owner.getEmail)
    }
    //Get the modified time of each attachment
    println("Note Attachment Modified Time: " + attachment.getModifiedTime.toString)
    //Get the name of the File
    println("Note Attachment File Name: " + attachment.getFileName)
    //Get the created time of each attachment
    println("Note Attachment Created Time: " + attachment.getCreatedTime.toString)
    //Get the Attachment file size
    println("Note Attachment File Size: " + attachment.getSize.toString)
    //Get the parentId Record instance of each attachment
    val parentIdOption = attachment.getParentId
    //Check if parentId is not null
    if (parentIdOption.isDefined) {
      val parentId = parentIdOption.get
      println("Note Attachment parent record Name: " + parentId.getKeyValue("name"))
      //Get the parent record ID of each attachment
      println("Note Attachment parent record ID: " + parentId.getId)
    }
    //Get the attachment is Editable
    println("Note Attachment is Editable: " + attachment.geteditable().toString)
    //Get the file ID of each attachment
    println("Note Attachment File ID: " + attachment.getfileId())
    //Get the type of each attachment
    println("Note Attachment File Type: " + attachment.gettype())
    //Get the seModule of each attachment
    println("Note Attachment seModule: " + attachment.getseModule())
    val modifiedByOption = attachment.getModifiedBy
    //Check if modifiedBy is not null
    if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
      val modifiedBy=modifiedByOption.get
      println("Note Attachment Modified By User-Name: " + modifiedBy.getName)
      println("Note Attachment Modified By User-ID: " + modifiedBy.getId)
      println("Note Attachment Modified By User-Email: " + modifiedBy.getEmail)
    }
    //Get the state of each attachment
    println("Note Attachment State: " + attachment.getstate())
    //Get the ID of each attachment
    println("Note Attachment ID: " + attachment.getId)
    //Get the createdBy User instance of each attachment
    val createdByOption = attachment.getCreatedBy
    //Check if createdBy is not null
    if (createdByOption.isDefined) { //Get the Name of the createdBy User
      val createdBy=createdByOption.get
      println("Note Attachment Created By User-Name: " + createdBy.getName)
      println("Note Attachment Created By User-ID: " + createdBy.getId)
      println("Note Attachment Created By User-Email: " + createdBy.getEmail)
    }
    //Get the linkUrl of each attachment
    println("Note Attachment LinkUrl: " + attachment.getlinkUrl())
  }

  /**
   * <h3> Create Notes </h3>
   * This method is used to add new notes and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def createNotes(): Unit = {
    val notesOperations = new NotesOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    //List of Note instances
    val notes = new ArrayBuffer[Note]
    for (i <- 1 to 5) { //Get instance of Note Class
      val note = new Note
      //Set Note_Title of the Note
      note.setNoteTitle(Option("Contacted"))
      //Set NoteContent of the Note
      note.setNoteContent(Option("Need to do further tracking"))
      //Get instance of Record Class
      val parentRecord = new Record
      //Set ID of the Record
      parentRecord.setId(Option(3477061000010780177l))
      //Set ParentId of the Note
      note.setParentId(Option(parentRecord))
      //Set SeModule of the Record
      note.setseModule(Option("Leads"))
      //Add Note instance to the list
      notes.addOne(note)
    }
    //Set the list to notes in BodyWrapper instance
    bodyWrapper.setData(notes)
    //Call createNotes method that takes BodyWrapper instance as parameter
    val responseOption = notesOperations.createNotes(bodyWrapper)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
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
   * <h3> Update Notes</h3>
   * This method is used to update an existing note and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateNotes(): Unit = {
    val notesOperations = new NotesOperations
    val bodyWrapper = new BodyWrapper
    val notes = new ArrayBuffer[Note]
    var note = new Note
    note.setId(Option(3477061000011668001l))
    note.setNoteTitle(Option("Contacted12"))
    note.setNoteContent(Option("Need to do further tracking12"))
    notes.addOne(note)
    note = new Note
    note.setId(Option(3477061000011668002l))
    note.setNoteTitle(Option("Contacted13"))
    note.setNoteContent(Option("Need to do further tracking13"))
    notes.addOne(note)
    bodyWrapper.setData(notes)
    //Call updateNotes method that takes BodyWrapper instance as parameter
    val responseOption = notesOperations.updateNotes(bodyWrapper)
    if (responseOption.isDefined) { //check response
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
                if (exception.getDetails != null) {
                  exception.getDetails.foreach(entry=>{
                    println(entry._1 + ": " + entry._2)
                  })
                }
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
   * This method is used to delete notes in bulk and print the response.
   *
   * @param notesId - The List of Note IDs to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteNotes(notesId: ArrayBuffer[Long]): Unit = { //example
    //ArrayList<Long> notesId = new ArrayList<Long>(Arrays.asList(3477061000006153001l,3477061000006153002l))
    val notesOperations = new NotesOperations
    val paramInstance = new ParameterMap

    for (id <- notesId) {
      paramInstance.add(new DeleteNotesParam().ids, id)
    }
    //Call deleteNotes method that takes paramInstance as parameter
    val responseOption = notesOperations.deleteNotes(Option(paramInstance))
    if (responseOption.isDefined) { //check response
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
                if (exception.getDetails != null) {
                  exception.getDetails.foreach(entry=>{
                    println(entry._1 + ": " + entry._2)
                  })
                }
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
   * <h3> Get Note </h3>
   * This method is used to get the note and print the response.
   *
   * @param noteId - The ID of the Note to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getNote(noteId: Long): Unit = { //example
    //Long noteId = 3477061000006153005l
    val notesOperations = new NotesOperations
    //Call getNote method that takes noteId as parameter
    val responseOption = notesOperations.getNote(noteId)
    if (responseOption.isDefined) { //check response
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
          val notes = responseWrapper.getData

          for (note <- notes) {
            val ownerOption = note.getOwner
            //Check if owner is not null
            if (ownerOption.isDefined) { //Get the name of the owner User
              val owner= ownerOption.get
              println("Note Owner User-Name: " + owner.getName)
              println("Note Owner User-ID: " + owner.getId)
              println("Note Owner Email: " + owner.getEmail)
            }
            println("Note ModifiedTime: " + note.getModifiedTime)
            val attachments = note.getattachments()
            if (attachments != null) {

              for (attachment <- attachments) {
                printAttachment(attachment)
              }
            }
            println("Note CreatedTime: " + note.getCreatedTime)
            val parentIdOption = note.getParentId
            //Check if parentId is not null
            if (parentIdOption.isDefined) {
              val parentId = parentIdOption.get
              if (parentId.getKeyValue("name") != null) println("Note parent record Name: " + parentId.getKeyValue("name"))
              println("Note parent record ID: " + parentId.getId)
            }
            println("Note Editable: " + note.toString)
            println("Note SeModule: " + note.getseModule())
            println("Note IsSharedToClient: " + note.getisSharedToClient().toString)
            val modifiedByOption = note.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy=modifiedByOption.get
              println("Note Modified By User-Name: " + modifiedBy.getName)
              println("Note Modified By User-ID: " + modifiedBy.getId)
              println("Note Modified By User-Email: " + modifiedBy.getEmail)
            }
            println("Note Size: " + note.getsize())
            println("Note State: " + note.getstate())
            println("Note VoiceNote: " + note.getvoiceNote().toString)
            println("Note Id: " + note.getId)
            val createdByOption = note.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("Note Created By User-Name: " + createdBy.getName)
              println("Note Created By User-ID: " + createdBy.getId)
              println("Note Created By User-Email: " + createdBy.getEmail)
            }
            println("Note NoteTitle: " + note.getNoteTitle)
            println("Note NoteContent: " + note.getNoteContent)
          }
        }
        else if (responseHandler.isInstanceOf[APIException]) {
          val exception = responseHandler.asInstanceOf[APIException]
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
   * <h3> Update Note</h3>
   * This method is used to update an existing note and print the response.
   *
   * @param noteId - The ID of the Note to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def updateNote(noteId: Long): Unit = {
    val notesOperations = new NotesOperations
    val bodyWrapper = new BodyWrapper
    val notes = new ArrayBuffer[Note]
    val note = new Note
    note.setNoteTitle(Option("Contacted12"))
    note.setNoteContent(Option("Need to do further tracking12"))
    val parentRecord = new Record
    parentRecord.setId(Option(3477061000007255001l))
    note.setParentId(Option(parentRecord))
    note.setseModule(Option("Contacts"))
    notes.addOne(note)
    bodyWrapper.setData(notes)
    //Call updateNote method that takes BodyWrapper instance and noteId as parameter
    val responseOption = notesOperations.updateNote(noteId,bodyWrapper)
    if (responseOption.isDefined) { //check response
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
                if (exception.getDetails != null) {
                  exception.getDetails.foreach(entry=>{
                    println(entry._1 + ": " + entry._2)
                  })
                }
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
   * <h3> Delete Note </h3>
   * This method is used to delete single Note with ID and print the response.
   *
   * @param noteID - The ID of the Note to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteNote(noteID: Long): Unit = {
    val notesOperations = new NotesOperations
    //Call deleteNote method that takes noteID as parameter
    val responseOption = notesOperations.deleteNote(noteID)
    if (responseOption.isDefined) { //check response
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
                if (exception.getDetails != null) {
                  exception.getDetails.foreach(entry=>{
                    println(entry._1 + ": " + entry._2)
                  })
                }
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
}

class Notes {}
