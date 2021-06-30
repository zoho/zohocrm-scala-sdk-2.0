package com.zoho.crm.api

import java.io.{BufferedReader, File, IOException, InputStream, InputStreamReader}
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger

import _root_.org.json.JSONObject
import com.zoho.api.authenticator.Token
import com.zoho.api.authenticator.store.TokenStore
import com.zoho.crm.api.dc.DataCenter
import com.zoho.crm.api.dc.DataCenter.Environment
import com.zoho.api.logger.Logger.Levels
import com.zoho.api.logger.SDKLogger
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants

/**
 * This object is to initialize Zoho CRM SDK.
 */
object Initializer {
  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  var jsonDetails: JSONObject = _

  private val LOCAL = new ThreadLocal[Initializer]

  private var initializer: Initializer = _

  @throws[SDKException]
  def initialize(user: UserSignature, environment: Environment, token: Token, store: TokenStore, sdkConfig: SDKConfig, resourcePath: String, logger: Option[com.zoho.api.logger.Logger]=None, proxy: Option[RequestProxy]=None): Unit = {
    try {
      var logger1 = logger.orNull
      if (user == null) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.USERSIGNATURE_ERROR_MESSAGE)
      if (environment == null) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.ENVIRONMENT_ERROR_MESSAGE)
      if (token == null) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.TOKEN_ERROR_MESSAGE)
      if (store == null) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.STORE_ERROR_MESSAGE)
      if (sdkConfig == null) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.SDK_CONFIG_ERROR_MESSAGE)
      if (resourcePath == null || resourcePath.isEmpty) throw new SDKException(Constants.INITIALIZATION_ERROR, Constants.RESOURCE_PATH_ERROR_MESSAGE)
      if (logger1 == null) {
        val filePath = System.getProperty("user.dir") + File.separator + Constants.LOGFILE_NAME
        logger1 = com.zoho.api.logger.Logger.getInstance(Levels.INFO, filePath)
      }
      SDKLogger.initialize(logger1)
      try jsonDetails = getJSONDetails
      catch {
        case e: IOException =>
          throw new SDKException(Constants.JSON_DETAILS_ERROR, e)
      }
      initializer = new Initializer
      initializer.user = user
      initializer.environment = environment
      initializer.token = token
      initializer.store = store
      initializer.sdkConfig = sdkConfig
      initializer.resourcePath = resourcePath
      initializer.requestProxy = proxy.orNull

      LOGGER.log(Level.INFO, Constants.INITIALIZATION_SUCCESSFUL.concat(initializer.toString))
    } catch {
      case e: SDKException =>
        throw e
      case e: Exception =>
        throw new SDKException(Constants.INITIALIZATION_EXCEPTION, e)
    }
  }

  /**
   * This method to get Initializer class instance.
   *
   * @return A Initializer class instance representing the SDK configuration details.
   */
  def getInitializer: Initializer = {
    if (LOCAL.get != null) return LOCAL.get
    initializer
  }


  /**
   * The method to switch the different user in SDK environment.
   *
   * @param user        A User class instance represents the CRM user.
   * @param environment A Environment class instance containing the CRM API base URL and Accounts URL.
   * @param token       A Token class instance containing the OAuth client application information.
   * @param sdkConfig   A SDKConfig class instance containing the configuration.
   * @param proxy       An RequestProxy class instance containing the proxy properties of the user.
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def switchUser(user: UserSignature, environment: Environment, token: Token, sdkConfig: SDKConfig, proxy: Option[RequestProxy]=None): Unit = {
    if (user == null) throw new SDKException(Constants.SWITCH_USER_ERROR, Constants.USERSIGNATURE_ERROR_MESSAGE)
    if (environment == null) throw new SDKException(Constants.SWITCH_USER_ERROR, Constants.ENVIRONMENT_ERROR_MESSAGE)
    if (token == null) throw new SDKException(Constants.SWITCH_USER_ERROR, Constants.TOKEN_ERROR_MESSAGE)
    if (sdkConfig == null) throw new SDKException(Constants.SWITCH_USER_ERROR, Constants.SDK_CONFIG_ERROR_MESSAGE)
    val initializer = new Initializer
    initializer.user = user
    initializer.environment = environment
    initializer.token = token
    initializer.store = Initializer.initializer.store
    initializer.sdkConfig = sdkConfig
    initializer.resourcePath = Initializer.initializer.resourcePath
    initializer.requestProxy = proxy.orNull
    LOCAL.set(initializer)
    LOGGER.log(Level.INFO, Constants.INITIALIZATION_SWITCHED.concat(initializer.toString))
  }

  def getJSONDetails :JSONObject = {

    var is:InputStream = null

    var isr:InputStreamReader = null

    var br:BufferedReader = null

    var fileContent = ""

    try {
        is = classOf[Initializer].getClass.getResourceAsStream(Constants.JSON_DETAILS_FILE_PATH)

        isr = new InputStreamReader(is)

        br = new BufferedReader(isr)
        var line:String  = br.readLine
        while(line != null) {
          fileContent += line
          line = br.readLine
        }

    } catch {
      case e: Exception =>
        LOGGER.log(Level.FINE, Constants.EXCEPTION_JSONDETAILS, e)
    } finally {
      if (br != null) br.close()
      if (isr != null) isr.close()
      if (is != null) is.close()
    }

    new JSONObject(fileContent)
  }

  /**
   * This method to get record field information details.
   * @param filePath A String containing the class information details file path.
   * @return A JSONObject representing the class information details.
   * @throws java.io.IOException Exception
   */
  @throws[IOException]
  def getJSON(filePath: String): JSONObject = {
    new JSONObject(new String(Files.readAllBytes(Paths.get(filePath))))
  }
}

/**
 * This class contains necessary objects to initialize Zoho CRM SDK.
 */
class Initializer {

  private var environment: Environment = _
  private var user: UserSignature = _
  private var token: Token = _
  private var store: TokenStore = _
  private var resourcePath: String = _
  private var requestProxy: RequestProxy = _
  private var sdkConfig: SDKConfig = _

  /**
   * This is a getter method to get API Token Store.
   * @return A TokenStore class instance containing the token store information.
   */
  def getStore: TokenStore = Initializer.getInitializer.store

  /**
   * This is a getter method to get CRM User.
   * @return A User class instance representing the CRM user.
   */
  def getUser: UserSignature = Initializer.getInitializer.user

  /**
   * This is a getter method to get OAuth client application information.
   * @return A Token class instance representing the OAuth client application information.
   */
  def getToken: Token = Initializer.getInitializer.token

  /**
   * This is a getter method to get API environment.
   * @return A Environment representing the API environment.
   */
  def getEnvironment: DataCenter.Environment = Initializer.getInitializer.environment


  def getResourcePath: String = resourcePath

  /**
   * This is a getter method to get Proxy information.
   *
   * @return A RequestProxy class instance representing the API Proxy information.
   */
  def getRequestProxy: RequestProxy = requestProxy

  /**
   * This is a getter method to get the SDK Configuration
   *
   * @return A SDKConfig instance representing the configuration
   */
  def getSDKConfig: SDKConfig = sdkConfig


  override def toString: String = Constants.FOR_EMAIL_ID.concat(Initializer.getInitializer.getUser.getEmail).concat(Constants.IN_ENVIRONMENT).concat(Initializer.getInitializer.getEnvironment.getUrl).concat(".")
}