package com.zoho.crm.api

import java.io.{BufferedReader, File, IOException, InputStream, InputStreamReader}
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger
import _root_.org.json.JSONObject
import com.zoho.api.authenticator.Token
import com.zoho.api.authenticator.store.TokenStore
import com.zoho.api.authenticator.store.FileStore
import com.zoho.crm.api.dc.DataCenter
import com.zoho.crm.api.dc.DataCenter.Environment
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.util.Utility

/**
 * This object is to initialize Zoho CRM SDK.
 */
object Initializer {
  class Builder() {
		private var environment: Environment = _
		
		private var store: TokenStore = _
		
		private var user: UserSignature = _
		
		private var token: Token = _
		
		private var resourcePath: String =_
		
		private var requestProxy: RequestProxy = _
		
		private var sdkConfig: SDKConfig = _
		
		private var logger: com.zoho.api.logger.Logger = _
		
		private val errorMessage: String = if (Initializer.initializer != null) Constants.SWITCH_USER_ERROR else Constants.INITIALIZATION_ERROR
    
    def Builder(): Unit = {
      if(Initializer.getInitializer != null) {
        val previousInitializer: Initializer = Initializer.getInitializer
          
        this.user = previousInitializer.user

        this.environment = previousInitializer.environment

        this.token = previousInitializer.token

        this.sdkConfig = previousInitializer.sdkConfig
      }
    }

    def initialize(): Unit = {
			Utility.assertNotNull(user, errorMessage, Constants.USER_SIGNATURE_ERROR_MESSAGE)
			
			Utility.assertNotNull(environment, errorMessage, Constants.ENVIRONMENT_ERROR_MESSAGE)
			
			Utility.assertNotNull(token, errorMessage, Constants.TOKEN_ERROR_MESSAGE)

      if(store == null) {
        store = new FileStore(System.getProperty("user.dir") + File.separator + Constants.TOKEN_FILE)
      }

      if(sdkConfig == null) {
        this.sdkConfig = new com.zoho.crm.api.SDKConfig.Builder().build
      }

      if(resourcePath == null) {
        resourcePath = System.getProperty("user.dir")
      }

			if(logger == null) {
				logger = new com.zoho.api.logger.Logger.Builder().level(com.zoho.api.logger.Logger.Levels.INFO).filePath(System.getProperty("user.dir") + File.separator + Constants.LOG_FILE_NAME).build
			}
			
			Initializer.initialize(user, environment, token, store, sdkConfig, resourcePath, logger, requestProxy)
		}
		
		def switchUser(): Unit = {
			Utility.assertNotNull(Initializer.initializer, Constants.SDK_UNINITIALIZATION_ERROR, Constants.SDK_UNINITIALIZATION_MESSAGE)

			Initializer.switchUser(user, environment, token, sdkConfig, requestProxy)
		}
		
    def logger(logger: com.zoho.api.logger.Logger): Initializer.Builder = {
			this.logger = logger
      this
		}

    def token(token: Token): Initializer.Builder = {
			Utility.assertNotNull(token, errorMessage, Constants.TOKEN_ERROR_MESSAGE)
			this.token = token
			this
		}

    def SDKConfig(sdkConfig: SDKConfig) : Initializer.Builder = {
			this.sdkConfig = sdkConfig
      this
		}
		
		def requestProxy(requestProxy: RequestProxy) : Initializer.Builder = {
			this.requestProxy = requestProxy
			this
		}

    def resourcePath(resourcePath: String) : Initializer.Builder = {
      if(resourcePath != null && !new File(resourcePath).isDirectory()) {
				throw new SDKException(errorMessage, Constants.RESOURCE_PATH_INVALID_ERROR_MESSAGE)
			}
			this.resourcePath = resourcePath
      this
		}

    def user(user: UserSignature): Initializer.Builder = {
			Utility.assertNotNull(user, errorMessage, Constants.USER_SIGNATURE_ERROR_MESSAGE)
			this.user = user
			this
		}
		
		def store(store: TokenStore): Initializer.Builder = {
			this.store = store
      this
		}

    def environment(environment: Environment): Initializer.Builder = {
			Utility.assertNotNull(environment, errorMessage, Constants.ENVIRONMENT_ERROR_MESSAGE)
			this.environment = environment
			this
		}
	}

  private val LOGGER: Logger = Logger.getLogger(classOf[com.zoho.api.logger.SDKLogger].getName)

  var jsonDetails: JSONObject = _

  private val LOCAL = new ThreadLocal[Initializer]

  private var initializer: Initializer = _

  @throws[SDKException]
  def initialize(user: UserSignature, environment: Environment, token: Token, store: TokenStore, sdkConfig: SDKConfig, resourcePath: String, logger: com.zoho.api.logger.Logger, proxy: RequestProxy): Unit = {
    try {
      com.zoho.api.logger.SDKLogger.initialize(logger)
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
      initializer.requestProxy = proxy
      LOGGER.log(Level.INFO, Constants.INITIALIZATION_SUCCESSFUL.concat(initializer.toString))
    } catch {
      case e: SDKException =>
        throw e
      case e: Exception =>
        throw new SDKException(Constants.INITIALIZATION_EXCEPTION, e)
    }
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
        
        var line: String  = br.readLine
        
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
  def switchUser(user: UserSignature, environment: Environment, token: Token, sdkConfig: SDKConfig, proxy: RequestProxy): Unit = {
    val initializer = new Initializer
    initializer.user = user
    initializer.environment = environment
    initializer.token = token
    initializer.store = Initializer.initializer.store
    initializer.sdkConfig = sdkConfig
    initializer.resourcePath = Initializer.initializer.resourcePath
    initializer.requestProxy = proxy
    LOCAL.set(initializer)
    LOGGER.log(Level.INFO, Constants.INITIALIZATION_SWITCHED.concat(initializer.toString))
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

  /**
   * This method to get Initializer class instance.
   *
   * @return A Initializer class instance representing the SDK configuration details.
   */
  def getInitializer: Initializer = {
    if (LOCAL.get != null) return LOCAL.get
    initializer
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
   * This is a getter method to get API environment.
   * @return A Environment representing the API environment.
   */
  def getEnvironment: DataCenter.Environment = Initializer.getInitializer.environment

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