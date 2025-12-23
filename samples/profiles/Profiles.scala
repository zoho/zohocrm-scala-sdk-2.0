package com.zoho.crm.sample.profiles

import java.util
import com.zoho.crm.api.profiles.APIException
import com.zoho.crm.api.profiles.Category
import com.zoho.crm.api.profiles.PermissionDetail
import com.zoho.crm.api.profiles.ProfilesOperations
import com.zoho.crm.api.profiles.ResponseHandler
import com.zoho.crm.api.profiles.ResponseWrapper
import com.zoho.crm.api.profiles.Section
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model


object Profiles {
  /**
   * <h3> Get Profiles </h3>
   * This method is used to retrieve the data of profiles through an API request and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getProfiles(): Unit = { //Get instance of ProfilesOperations Class
    val profilesOperations = new ProfilesOperations
    //Call getProfiles method
    val responseOption = profilesOperations.getProfiles
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
          //Get the list of obtained Profile instances
          val profiles = responseWrapper.getProfiles
          for (profile <- profiles) { //Get the DisplayLabel of the each Profile
            println("Profile DisplayLabel: " + profile.getDisplayLabel)
            if (profile.getCreatedTime != null) { //Get the CreatedTime of each Profile
              println("Profile CreatedTime: " + profile.getCreatedTime)
            }
            if (profile.getModifiedTime != null) { //Get the ModifiedTime of each Profile
              println("Profile ModifiedTime: " + profile.getModifiedTime)
            }
            //Get the Name of the each Profile
            println("Profile Name: " + profile.getName)
            //Get the modifiedBy User instance of each Profile
            val modifiedByOption = profile.getModifiedBy()
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("Profile Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Profile Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Profile Modified By User-Email: " + modifiedBy.getEmail)
            }
            //Get the Description of the each Profile
            println("Profile Description: " + profile.getDescription)
            //Get the ID of the each Profile
            println("Profile ID: " + profile.getId)
            //Get the Category of the each Profile
            println("Profile Category: " + profile.getCategory.toString)
            //Get the createdBy User instance of each Profile
            val createdByOption = profile.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("Profile Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Profile Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Profile Created By User-Email: " + createdBy.getEmail)
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
   * <h3> Get Profile </h3>
   * This method is used to get the details of any specific profile.
   * Specify the unique id of the profile in your API request to get the data for that particular profile.
   *
   * @param profileId - The ID of the Profile to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getProfile(profileId: Long): Unit = { //example
    //Long profileId = 3477061000000026011l
    val profilesOperations = new ProfilesOperations
    //Call getProfile method that takes profileId as parameter
    val responseOption = profilesOperations.getProfile(profileId)
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
          val profiles = responseWrapper.getProfiles
          for (profile <- profiles) {
            println("Profile DisplayLabel: " + profile.getDisplayLabel)
            if (profile.getCreatedTime != null) println("Profile CreatedTime: " + profile.getCreatedTime)
            if (profile.getModifiedTime != null) println("Profile ModifiedTime: " + profile.getModifiedTime)
            //Get the permissionsDetails of each Profile
            val permissionsDetails = profile.getPermissionsDetails
            //Check if permissionsDetails is not null
            if (permissionsDetails != null) {
              for (permissionsDetail <- permissionsDetails) { //Get the DisplayLabel of the each PermissionDetail
                println("Profile PermissionDetail DisplayLabel: " + permissionsDetail.getDisplayLabel)
                //Get the Module of the each PermissionDetail
                println("Profile PermissionDetail Module: " + permissionsDetail.getModule)
                //Get the Name of the each PermissionDetail
                println("Profile PermissionDetail Name: " + permissionsDetail.getName)
                //Get the ID of the each PermissionDetail
                println("Profile PermissionDetail ID: " + permissionsDetail.getId)
                //Get the Enabled of the each PermissionDetail
                println("Profile PermissionDetail Enabled: " + permissionsDetail.getEnabled)
              }
            }
            println("Profile Name: " + profile.getName)
            val modifiedByOption = profile.getModifiedBy()
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("Profile Modified By User-ID: " + modifiedBy.getId)
              //Get the name of the modifiedBy User
              println("Profile Modified By User-Name: " + modifiedBy.getName)
              //Get the Email of the modifiedBy User
              println("Profile Modified By User-Email: " + modifiedBy.getEmail)
            }
            println("Profile Description: " + profile.getDescription)
            println("Profile ID: " + profile.getId)
            println("Profile Category: " + profile.getCategory.toString)
            val createdByOption = profile.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("Profile Created By User-ID: " + createdBy.getId)
              //Get the name of the createdBy User
              println("Profile Created By User-Name: " + createdBy.getName)
              //Get the Email of the createdBy User
              println("Profile Created By User-Email: " + createdBy.getEmail)
            }
            //Get the sections of each Profile
            val sections = profile.getSections
            //Check if sections is not null
            if (sections != null) {
              for (section <- sections) { //Get the Name of the each Section
                println("Profile Section Name: " + section.getName)
                //Get the categories of each Section
                val categories = section.getCategories
                for (category <- categories) { //Get the DisplayLabel of the each Category
                  println("Profile Section Category DisplayLabel: " + category.getDisplayLabel)
                  //Get the permissionsDetails List of each Category
                  val categoryPermissionsDetails = category.getPermissionsDetails
                  //Check if categoryPermissionsDetails is not null
                  if (categoryPermissionsDetails != null) {
                    for (permissionsDetailID <- categoryPermissionsDetails) { //Get the permissionsDetailID of the Category
                      println("Profile Section Category permissionsDetailID: " + permissionsDetailID)
                    }
                  }
                  //Get the Name of the each Category
                  println("Profile Section Category Name: " + category.getName)
                }
              }
            }
            if (profile.getDelete != null) { //Get the Delete of the each Profile
              println("Profile Delete: " + profile.getDelete.toString)
            }
            if (profile.getDefault != null) { //Get the Default of the each Profile
              println("Profile Default: " + profile.getDefault.toString)
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
}

class Profiles {}

