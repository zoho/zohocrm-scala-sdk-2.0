package com.zoho.crm.sample.users

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util

import com.zoho.crm.api.HeaderMap
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.profiles.Profile
import com.zoho.crm.api.roles.Role
import com.zoho.crm.api.users.{APIException, ActionHandler, ActionResponse, ActionWrapper, BodyWrapper, CustomizeInfo, Info, RequestWrapper, ResponseHandler, ResponseWrapper, SuccessResponse, TabTheme, Territory, Theme, User, UsersOperations}
import com.zoho.crm.api.users.UsersOperations.GetUsersHeader
import com.zoho.crm.api.users.UsersOperations.GetUsersParam
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Users {
  /**
   * <h3> Get Users </h3>
   * This method is used to retrieve the users data specified in the API request.
   * You can specify the type of users that needs to be retrieved using the Users API.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getUsers(): Unit = { //Get instance of UsersOperations Class
    val usersOperations = new UsersOperations
    //Get instance of ParameterMap Class
    val paramInstance = new ParameterMap
    paramInstance.add(new GetUsersParam().type1, "ActiveUsers")
    paramInstance.add(new GetUsersParam().page, 1)
    //paramInstance.add(GetUsersParam.PER_PAGE, 1)
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2019, 1, 2, 1, 0, 1, 0, ZoneOffset.of("+05:30"))
    headerInstance.add(new GetUsersHeader().IfModifiedSince, ifmodifiedsince)
    //Call getUsers method that takes ParameterMap instance and HeaderMap instance as parameters
    val responseOption = usersOperations.getUsers(Option(paramInstance), Option(headerInstance))
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
          //Get the list of obtained User instances
          val users:ArrayBuffer[User] = responseWrapper.getUsers
          for (user <- users) { //Get the Country of each User
            println("User Country: " + user.getCountry)
            // Get the CustomizeInfo instance of each User
            var customizeInfoOption = user.getCustomizeInfo
            //Check if customizeInfo is not null
            if (customizeInfoOption.isDefined) {
              val customizeInfo = customizeInfoOption.get
              if (customizeInfo.getNotesDesc().isDefined) { //Get the NotesDesc of each User
                println("User CustomizeInfo NotesDesc: " + customizeInfo.getNotesDesc.toString)
              }
              if (customizeInfo.getShowRightPanel().isDefined) { //Get the ShowRightPanel of each User
                println("User CustomizeInfo ShowRightPanel: " + customizeInfo.getShowRightPanel.toString)
              }
              if (customizeInfo.getBcView().isDefined) { //Get the BcView of each User
                println("User CustomizeInfo BcView: " + customizeInfo.getBcView.toString)
              }
              if (customizeInfo.getShowHome().isDefined) { //Get the ShowHome of each User
                println("User CustomizeInfo ShowHome: " + customizeInfo.getShowHome.toString)
              }
              if (customizeInfo.getShowDetailView().isDefined) { //Get the ShowDetailView of each User
                println("User CustomizeInfo ShowDetailView: " + customizeInfo.getShowDetailView.toString)
              }
              if (customizeInfo.getUnpinRecentItem().isDefined) { //Get the UnpinRecentItem of each User
                println("User CustomizeInfo UnpinRecentItem: " + customizeInfo.getUnpinRecentItem.toString)
              }
            }
            // Get the Role instance of each User
            val roleOption = user.getRole
            //Check if role is not null
            if (roleOption.isDefined) { //Get the Name of each Role
              val role = roleOption.get
              println("User Role Name: " + role.getName)
              //Get the ID of each Role
              println("User Role ID: " + role.getId)
            }
            //Get the Signature of each User
            println("User Signature: " + user.getSignature)
            //Get the City of each User
            println("User City: " + user.getCity)
            //Get the NameFormat of each User
            println("User NameFormat: " + user.getNameFormat)
            //Get the Language of each User
            println("User Language: " + user.getLanguage)
            //Get the Locale of each User
            println("User Locale: " + user.getLocale)
            //Get the Microsoft of each User
            println("User Microsoft: " + user.getMicrosoft.toString)
            if (user.getPersonalAccount.isDefined) { //Get the PersonalAccount of each User
              println("User PersonalAccount: " + user.getPersonalAccount.toString)
            }
            //Get the DefaultTabGroup of each User
            println("User DefaultTabGroup: " + user.getDefaultTabGroup)
            //Get the Isonline of each User
            println("User Isonline: " + user.getIsonline.toString)
            //Get the modifiedBy User instance of each User
            val modifiedBy = user.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedBy.isDefined ) { //Get the Name of the modifiedBy User
              println("User Modified By User-Name: " + modifiedBy.get.getName)
              //Get the ID of the modifiedBy User
              println("User Modified By User-ID: " + modifiedBy.get.getId)
            }
            //Get the Street of each User
            println("User Street: " + user.getStreet)
            //Get the Currency of each User
            println("User Currency: " + user.getCurrency)
            //Get the Alias of each User
            println("User Alias: " + user.getAlias)
            // Get the Theme instance of each User
            val themeOption = user.getTheme
            //Check if theme is not null
            if (themeOption.isDefined) { // Get the TabTheme instance of Theme
              val theme=themeOption.get
              val normalTabOption = theme.getNormalTab
              //Check if normalTab is not null
              if (normalTabOption.isDefined) { //Get the FontColor of NormalTab
                val normalTab= normalTabOption.get
                println("User Theme NormalTab FontColor: " + normalTab.getFontColor)
                //Get the Name of NormalTab
                println("User Theme NormalTab Name: " + normalTab.getBackground)
              }
              val selectedTabOption = theme.getSelectedTab
              //Check if selectedTab is not null
              if (selectedTabOption.isDefined) { //Get the FontColor of SelectedTab
                val selectedTab = selectedTabOption.get
                println("User Theme SelectedTab FontColor: " + selectedTab.getFontColor)
                //Get the Name of SelectedTab
                println("User Theme SelectedTab Name: " + selectedTab.getBackground)
              }
              //Get the NewBackground of each Theme
              println("User Theme NewBackground: " + theme.getNewBackground)
              //Get the Background of each Theme
              println("User Theme Background: " + theme.getBackground)
              //Get the Screen of each Theme
              println("User Theme Screen: " + theme.getScreen)
              //Get the Type of each Theme
              println("User Theme Type: " + theme.getType)
            }
            //Get the ID of each User
            println("User ID: " + user.getId)
            //Get the State of each User
            println("User State: " + user.getState)
            //Get the Fax of each User
            println("User Fax: " + user.getFax)
            //Get the CountryLocale of each User
            println("User CountryLocale: " + user.getCountryLocale)
            //Get the FirstName of each User
            println("User FirstName: " + user.getFirstName)
            //Get the Email of each User
            println("User Email: " + user.getEmail)
            //Get the reportingTo User instance of each User
            val reportingToOption = user.getReportingTo
            //Check if reportingTo is not null
            if (reportingToOption.isDefined) { //Get the Name of the reportingTo User
              val reportingTo=reportingToOption.get
              println("User ReportingTo Name: " + reportingTo.getName)
              //Get the ID of the reportingTo User
              println("User ReportingTo ID: " + reportingTo.getId)
            }
            //Get the DecimalSeparator of each User
            println("User DecimalSeparator: " + user.getDecimalSeparator)
            //Get the Zip of each User
            println("User Zip: " + user.getZip)
            //Get the CreatedTime of each User
            println("User CreatedTime: " + user.getCreatedTime)
            //Get the Website of each User
            println("User Website: " + user.getWebsite)
            //Get the ModifiedTime of each User
            println("User ModifiedTime: " + user.getModifiedTime)
            //Get the TimeFormat of each User
            println("User TimeFormat: " + user.getTimeFormat)
            //Get the Offset of each User
            println("User Offset: " + user.getOffset.toString)
            //Get the Profile instance of each User
            val profileOption = user.getProfile
            //Check if profile is not null
            if (profileOption.isDefined) { //Get the Name of each Profile
              val profile=profileOption.get
              println("User Profile Name: " + profile.getName)
              //Get the ID of each Profile
              println("User Profile ID: " + profile.getId)
            }
            //Get the Mobile of each User
            println("User Mobile: " + user.getMobile)
            //Get the LastName of each User
            println("User LastName: " + user.getLastName)
            //Get the TimeZone of each User
            println("User TimeZone: " + user.getTimeZone)
            //Get the createdBy User instance of each User
            val createdByOption = user.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy=createdByOption.get
              println("User Created By User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("User Created By User-ID: " + createdBy.getId)
            }
            //Get the Zuid of each User
            println("User Zuid: " + user.getZuid)
            //Get the Confirm of each User
            println("User Confirm: " + user.getConfirm.toString)
            //Get the FullName of each User
            println("User FullName: " + user.getFullName)
            //Get the list of obtained Territory instances
            val territories = user.getTerritories
            //Check if territories is not null
            if (territories.isEmpty) {
              for (territory <- territories) { //Get the Manager of the Territory
                println("User Territory Manager: " + territory.getManager.toString)
                //Get the Name of the Territory
                println("User Territory Name: " + territory.getName)
                //Get the ID of the Territory
                println("User Territory ID: " + territory.getId)
              }
            }
            //Get the Phone of each User
            println("User Phone: " + user.getPhone)
            //Get the DOB of each User
            println("User DOB: " + user.getDob)
            //Get the DateFormat of each User
            println("User DateFormat: " + user.getDateFormat)
            //Get the Status of each User
            println("User Status: " + user.getStatus)
          }
          //Get the Object obtained Info instance
          val infoOption = responseWrapper.getInfo
          //Check if info is not null
          if (infoOption.isDefined) {
            val info = infoOption.get
            if (info.getPerPage().isDefined) { //Get the PerPage of the Info
              println("User Info PerPage: " + info.getPerPage.toString)
            }
            if (info.getCount.isDefined) { //Get the Count of the Info
              println("User Info Count: " + info.getCount.toString)
            }
            if (info.getPage.isDefined) { //Get the Page of the Info
              println("User Info Page: " + info.getPage.toString)
            }
            if (info.getMoreRecords.isDefined) { //Get the MoreRecords of the Info
              println("User Info MoreRecords: " + info.getMoreRecords.toString)
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
   * <h3> Create Users </h3>
   * This method is used to add a user to your organization and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def createUser(): Unit = {
    val usersOperations = new UsersOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val request = new RequestWrapper
    //List of User instances
    val userList = new ArrayBuffer[User]
    //Get instance of User Class
    val user1 = new User
    val role = new Role
    role.setId(Option(3477061000000026008l))
    user1.setRole(Option(role))
    user1.setFirstName(Option("TestUser"))
    user1.setEmail(Option("testuser112@zoho.com"))
    val profile = new Profile
    profile.setId(Option(3477061000000026014l))
    user1.setProfile(Option(profile))
    user1.setLastName(Option("TestUser LastName"))
    userList.addOne(user1)
    request.setUsers(userList)
    //Call createUser method that takes BodyWrapper class instance as parameter
    val responseOption = usersOperations.createUser(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val responseWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = responseWrapper.getUsers
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
   * <h3> Update Users </h3>
   * This method is used to update the details of multiple users of your organization and print the response.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateUsers(): Unit = {
    val usersOperations = new UsersOperations
    val request = new BodyWrapper
    val userList = new ArrayBuffer[User]
    var user1 = new User
    user1.setId(Option(3477061000011679011l))
    var role = new Role
    role.setId(Option(3477061000000026008l))
    user1.setRole(Option(role))
    user1.setCountryLocale(Option("en_US"))
    userList.addOne(user1)
    user1 = new User
    user1.setId(Option(3477061000005791024l))
    role = new Role
    role.setId(Option(3524033000000191017l))
    user1.setRole(Option(role))
    user1.setCountryLocale(Option("en_US"))
    //		user1.addKeyValue(apiName, value)
    userList.addOne(user1)
    request.setUsers(userList)
    //Call updateUsers method that takes BodyWrapper instance as parameter
    val responseOption = usersOperations.updateUsers(request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val responseWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = responseWrapper.getUsers
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
   * <h3> Get User </h3>
   * This method is used to get the details of any specific user.
   * Specify the unique id of the user in your API request to get the data for that particular user.
   *
   * @param userId - The ID of the User to be obtained
   * @throws Exception
   */
  @throws[Exception]
  def getUser(userId: Long): Unit = { //example
    //Long userId = 3477061000005817002L
    val usersOperations = new UsersOperations
    val headerInstance = new HeaderMap
    val ifmodifiedsince = OffsetDateTime.of(2019, 1, 2, 1, 0, 1, 0, ZoneOffset.of("+05:30"))
//    headerInstance.add(new GetUsersHeader().IfModifiedSince, ifmodifiedsince)
    //Call getUser method that takes userId as parameter
    val responseOption = usersOperations.getUser(userId, Option(headerInstance))
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
          //Get the obtained User instance
          val users = responseWrapper.getUsers
          for (user <- users) {
            println("User Country: " + user.getCountry)
            val customizeInfoOption = user.getCustomizeInfo
            if (customizeInfoOption.isDefined) {
              val customizeInfo = customizeInfoOption.get
              if (customizeInfo.getNotesDesc.isDefined) println("User CustomizeInfo NotesDesc: " + customizeInfo.getNotesDesc.toString)
              if (customizeInfo.getShowRightPanel.isDefined) println("User CustomizeInfo ShowRightPanel: " + customizeInfo.getShowRightPanel.toString)
              if (customizeInfo.getBcView.isDefined) println("User CustomizeInfo BcView: " + customizeInfo.getBcView.toString)
              if (customizeInfo.getShowHome.isDefined) println("User CustomizeInfo ShowHome: " + customizeInfo.getShowHome.toString)
              if (customizeInfo.getShowDetailView.isDefined) println("User CustomizeInfo ShowDetailView: " + customizeInfo.getShowDetailView.toString)
              if (customizeInfo.getUnpinRecentItem.isDefined) println("User CustomizeInfo UnpinRecentItem: " + customizeInfo.getUnpinRecentItem.toString)
            }
            val roleOption = user.getRole
            if (roleOption.isDefined) {
              val role = roleOption.get
              println("User Role Name: " + role.getName)
              println("User Role ID: " + role.getId)
            }
            println("User Signature: " + user.getSignature)
            println("User City: " + user.getCity)
            println("User NameFormat: " + user.getNameFormat)
            println("User Language: " + user.getLanguage)
            println("User Locale: " + user.getLocale)
            println("User Microsoft: " + user.getMicrosoft.toString)
            if (user.getPersonalAccount.isDefined) println("User PersonalAccount: " + user.getPersonalAccount.toString)
            println("User DefaultTabGroup: " + user.getDefaultTabGroup)
            println("User Isonline: " + user.getIsonline.toString)
            val modifiedByOption = user.getModifiedBy
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("User Modified By User-Name: " + modifiedBy.getName)
              println("User Modified By User-ID: " + modifiedBy.getId)
            }
            println("User Street: " + user.getStreet)
            println("User Currency: " + user.getCurrency)
            println("User Alias: " + user.getAlias)
            val themeOption = user.getTheme
            if (themeOption.isDefined) {
              val theme = themeOption.get
              val normalTabOption = theme.getNormalTab
              if (normalTabOption.isDefined) {
                val normalTab = normalTabOption.get
                println("User Theme NormalTab FontColor: " + normalTab.getFontColor)
                println("User Theme NormalTab Name: " + normalTab.getBackground)
              }
              val selectedTabOption = theme.getSelectedTab
              if (selectedTabOption.isDefined) {
                val selectedTab=selectedTabOption.get
                println("User Theme SelectedTab FontColor: " + selectedTab.getFontColor)
                println("User Theme SelectedTab Name: " + selectedTab.getBackground)
              }
              println("User Theme NewBackground: " + theme.getNewBackground)
              println("User Theme Background: " + theme.getBackground)
              println("User Theme Screen: " + theme.getScreen)
              println("User Theme Type: " + theme.getType)
            }
            println("User ID: " + user.getId)
            println("User State: " + user.getState)
            println("User Fax: " + user.getFax)
            println("User CountryLocale: " + user.getCountryLocale)
            println("User FirstName: " + user.getFirstName)
            println("User Email: " + user.getEmail)
            val reportingToOption = user.getReportingTo
            if (reportingToOption.isDefined) {
              val reportingTo = reportingToOption.get
              println("User ReportingTo Name: " + reportingTo.getName)
              println("User ReportingTo ID: " + reportingTo.getId)
            }
            println("User DecimalSeparator: " + user.getDecimalSeparator)
            println("User Zip: " + user.getZip)
            println("User CreatedTime: " + user.getCreatedTime)
            println("User Website: " + user.getWebsite)
            println("User ModifiedTime: " + user.getModifiedTime)
            println("User TimeFormat: " + user.getTimeFormat)
            println("User Offset: " + user.getOffset.toString)
            val profileOption = user.getProfile
            if (profileOption.isDefined) {
              val profile = profileOption.get
              println("User Profile Name: " + profile.getName)
              println("User Profile ID: " + profile.getId)
            }
            println("User Mobile: " + user.getMobile)
            println("User LastName: " + user.getLastName)
            println("User TimeZone: " + user.getTimeZone)
            val createdByOption = user.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("User Created By User-Name: " + createdBy.getName)
              println("User Created By User-ID: " + createdBy.getId)
            }
            println("User Zuid: " + user.getZuid)
            println("User Confirm: " + user.getConfirm.toString)
            println("User FullName: " + user.getFullName)
            val territories= user.getTerritories
            if (territories!=null) {
              for (territory <- territories) {
                println("User Territory Manager: " + territory.getManager.toString)
                println("User Territory Name: " + territory.getName)
                println("User Territory ID: " + territory.getId)
              }
            }
            println("User Phone: " + user.getPhone)
            println("User DOB: " + user.getDob)
            println("User DateFormat: " + user.getDateFormat)
            println("User Status: " + user.getStatus)
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
   * <h3> Update User </h3>
   * This method is used to update the details of a user of your organization and print the response.
   *
   * @param userId - The ID of the User to be updated
   * @throws Exception
   */
  @throws[Exception]
  def updateUser(userId: Long): Unit = {
    val usersOperations = new UsersOperations
    val request = new BodyWrapper
    val userList = new ArrayBuffer[User]
    val user1 = new User
    val role = new Role
    role.setId(Option(3524033000000026005l))
    user1.setRole(Option(role))
    user1.setCountryLocale(Option("en_US"))
    userList.addOne(user1)
    request.setUsers(userList)
    //Call updateUser method that takes BodyWrapper instance and userId as parameter
    val responseOption = usersOperations.updateUser(userId,request)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val responseWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = responseWrapper.getUsers
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
   * <h3> Delete User </h3>
   * This method is used to delete a user from your organization and print the response.
   *
   * @param userId - The ID of the User to be deleted
   * @throws Exception
   */
  @throws[Exception]
  def deleteUser(userId: Long): Unit = {
    val usersOperations = new UsersOperations
    //Call deleteUser method that takes userId as parameter
    val responseOption = usersOperations.deleteUser(userId)
    if (responseOption.isDefined) { //check response
      var response= responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val responseWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = responseWrapper.getUsers
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

class Users {}