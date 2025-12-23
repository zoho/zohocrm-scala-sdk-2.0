package com.zoho.crm.sample.organization

import java.lang.reflect.Field
import java.util

import com.zoho.crm.api.org.{APIException, ActionResponse, FileBodyWrapper, LicenseDetails, OrgOperations, ResponseHandler, ResponseWrapper, SuccessResponse}
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model
import com.zoho.crm.api.util.StreamWrapper


object Organization {
  /**
   * <h3> Get Organization </h3>
   * This method is used to get the organization data and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getOrganization(): Unit = { //Get instance of OrgOperations Class
    val orgOperations = new OrgOperations
    //Call getOrganization method
    val responseOption = orgOperations.getOrganization
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the list of obtained Org instances
          val orgs = responseWrapper.getOrg
          for (org <- orgs) { //Get the Country of each Organization
            println("Organization Country: " + org.getCountry)
            //Get the PhotoId of each Organization
            println("Organization PhotoId: " + org.getPhotoId)
            //Get the City of each Organization
            println("Organization City: " + org.getCity)
            //Get the Description of each Organization
            println("Organization Description: " + org.getDescription)
            //Get the McStatus of each Organization
            println("Organization McStatus: " + org.getMcStatus.toString)
            //Get the GappsEnabled of each Organization
            println("Organization GappsEnabled: " + org.getGappsEnabled.toString)
            //Get the DomainName of each Organization
            println("Organization DomainName: " + org.getDomainName)
            //Get the TranslationEnabled of each Organization
            println("Organization TranslationEnabled: " + org.getTranslationEnabled.toString)
            //Get the Street of each Organization
            println("Organization Street: " + org.getStreet)
            //Get the Alias of each Organization
            println("Organization Alias: " + org.getAlias)
            //Get the Currency of each Organization
            println("Organization Currency: " + org.getCurrency)
            //Get the Id of each Organization
            println("Organization Id: " + org.getId)
            //Get the State of each Organization
            println("Organization State: " + org.getState)
            //Get the Fax of each Organization
            println("Organization Fax: " + org.getFax)
            //Get the EmployeeCount of each Organization
            println("Organization EmployeeCount: " + org.getEmployeeCount)
            //Get the Zip of each Organization
            println("Organization Zip: " + org.getZip)
            //Get the Website of each Organization
            println("Organization Website: " + org.getWebsite)
            //Get the CurrencySymbol of each Organization
            println("Organization CurrencySymbol: " + org.getCurrencySymbol)
            //Get the Mobile of each Organization
            println("Organization Mobile: " + org.getMobile)
            //Get the CurrencyLocale of each Organization
            println("Organization CurrencyLocale: " + org.getCurrencyLocale)
            //Get the PrimaryZuid of each Organization
            println("Organization PrimaryZuid: " + org.getPrimaryZuid)
            //Get the ZiaPortalId of each Organization
            println("Organization ZiaPortalId: " + org.getZiaPortalId)
            //Get the TimeZone of each Organization
            println("Organization TimeZone: " + org.getTimeZone)
            //Get the Zgid of each Organization
            println("Organization Zgid: " + org.getZgid)
            //Get the CountryCode of each Organization
            println("Organization CountryCode: " + org.getCountryCode)
            //Get the Object obtained LicenseDetails instance
            val licenseDetailsOption = org.getLicenseDetails
            //Check if licenseDetails is not null
            if (licenseDetailsOption.isDefined) { //Get the PaidExpiry of each LicenseDetails
              val licenseDetails =licenseDetailsOption.get

              println("Organization LicenseDetails PaidExpiry: " + licenseDetails.getPaidExpiry)
              //Get the UsersLicensePurchased of each LicenseDetails
              println("Organization LicenseDetails UsersLicensePurchased: " + licenseDetails.getUsersLicensePurchased.toString)
              //Get the TrialType of each LicenseDetails
              println("Organization LicenseDetails TrialType: " + licenseDetails.getTrialType)
              //Get the TrialExpiry of each LicenseDetails
              println("Organization LicenseDetails TrialExpiry: " + licenseDetails.getTrialExpiry)
              //Get the Paid of each LicenseDetails
              println("Organization LicenseDetails Paid: " + licenseDetails.getPaid.toString)
              //Get the PaidType of each LicenseDetails
              println("Organization LicenseDetails PaidType: " + licenseDetails.getPaidType)
            }
            //Get the Phone of each Organization
            println("Organization Phone: " + org.getPhone)
            //Get the CompanyName of each Organization
            println("Organization CompanyName: " + org.getCompanyName)
            //Get the PrivacySettings of each Organization
            println("Organization PrivacySettings: " + org.getPrivacySettings.toString)
            //Get the PrimaryEmail of each Organization
            println("Organization PrimaryEmail: " + org.getPrimaryEmail)
            //Get the IsoCode of each Organization
            println("Organization IsoCode: " + org.getIsoCode)
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
   * <h3> Upload Organization Photo</h3>
   * This method is used to upload the brand logo or image of the organization and print the response.
   *
   * @param absoluteFilePath - The absolute file path of the file to be attached
   * @throws Exception
   */
  @throws[Exception]
  def uploadOrganizationPhoto(absoluteFilePath: String): Unit = { //example
    //String absoluteFilePath = "./download.png"
    val orgOperations = new OrgOperations
    //Get instance of FileBodyWrapper class that will contain the request file
    val fileBodyWrapper = new FileBodyWrapper
    //Get instance of StreamWrapper class that takes absolute path of the file to be attached as parameter
    val streamWrapper = new StreamWrapper(absoluteFilePath)
    //Set file to the FileBodyWrapper instance
    fileBodyWrapper.setFile(Option(streamWrapper))
    //Call uploadOrganizationPhoto method that takes FileBodyWrapper instance
    val responseOption = orgOperations.uploadOrganizationPhoto(fileBodyWrapper)
    if (responseOption.isDefined ) { //Check response
      val response=responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionResponse = response.getObject
        //Check if the request is successful
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

class Organization {}