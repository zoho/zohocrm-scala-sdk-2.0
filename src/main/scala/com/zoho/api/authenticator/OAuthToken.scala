package com.zoho.api.authenticator

import java.io.IOException
import java.security.NoSuchAlgorithmException
import java.util
import java.util.logging.{Level, Logger}
import com.zoho.api.authenticator.store.TokenStore
import com.zoho.api.logger.SDKLogger
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.{APIHTTPConnector, Constants}
import com.zoho.crm.api.{Initializer, UserSignature}
import com.zoho.crm.api.util.Utility
import javax.net.ssl.SSLContext
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ssl.{NoopHostnameVerifier, SSLConnectionSocketFactory}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONObject

import scala.collection.mutable


/**
 * This object contains OAuthToken
 */
object OAuthToken {
  class Builder() {
    private var clientID: String = _

    private var clientSecret: String = _

    private var redirectURL: String = _

    private var refreshToken: String = _

    private var grantToken: String = _

    private var accessToken: String = _

    private var id: String = _

    def id(id: String): OAuthToken.Builder = {
      this.id = id

      this
    }

    def clientID(clientID: String): OAuthToken.Builder = {
      Utility.assertNotNull(clientID, Constants.TOKEN_ERROR, Constants.CLIENT_ID_NULL_ERROR_MESSAGE)

      this.clientID = clientID

      this
    }

    def clientSecret(clientSecret: String): OAuthToken.Builder = {
      Utility.assertNotNull(clientSecret, Constants.TOKEN_ERROR, Constants.CLIENT_SECRET_NULL_ERROR_MESSAGE)

      this.clientSecret = clientSecret

      this
    }

    def redirectURL(redirectURL: String): OAuthToken.Builder = {
      this.redirectURL = redirectURL

      this
    }

    def refreshToken(refreshToken: String): OAuthToken.Builder = {
      this.refreshToken = refreshToken

      this
    }

    def grantToken(grantToken: String): OAuthToken.Builder = {
      this.grantToken = grantToken

      this
    }

    def accessToken(accessToken: String): OAuthToken.Builder = {
      this.accessToken = accessToken

      this
    }

    def build(): OAuthToken = {
      if (this.grantToken == null && this.refreshToken == null && this.id == null && this.accessToken == null) {
        throw new SDKException(Constants.MANDATORY_VALUE_ERROR, Constants.MANDATORY_KEY_ERROR + "-" + Constants.OAUTH_MANDATORY_KEYS)
      }

      new OAuthToken(this.clientID, this.clientSecret, this.grantToken, this.refreshToken, this.redirectURL, this.id, this.accessToken)
    }
  }
}

/**
 * This class gets and refreshes the tokens based on the expiry time.
 *
 * @constructor Creates an OAuthToken class instance with the specified parameters.
 * @param clientID     A String containing the OAuth client id.
 * @param clientSecret A String containing the OAuth client secret.
 * @param grantToken  A String containing the OAuth GRANT token.
 * @param refreshToken A String containing the OAuth REFRESH token.
 * @param id.
 * @param accessToken.
 */
class OAuthToken(private var clientID: String, private var clientSecret: String, private var grantToken: String, private var refreshToken: String, private var redirectURL: String, private var id: String, private var accessToken: String) extends Token {
  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  clientID = clientID

  clientSecret = clientSecret

  grantToken = grantToken

  refreshToken = refreshToken

  redirectURL = redirectURL

  id = id

  accessToken = accessToken

  private var expiresIn: String = _

  private var userMail: String = _

  def generateId(): Unit = {
    val builder: StringBuilder = new StringBuilder()

    val email: String = Initializer.getInitializer.getUser.getEmail

    builder.append(Constants.SCALA).append(email.substring(0, email.indexOf("@"))).append("_")

    builder.append(Initializer.getInitializer.getEnvironment.getName).append("_")

    builder.append(this.refreshToken.substring(this.refreshToken.length() - 4))

    this.id = builder.toString()
  }

  /**
   * This is a getter method to get OAuth client id.
   *
   * @return A String representing the OAuth client id.
   */
  def getClientId: String = clientID

  /**
   * This is a getter method to get OAuth client secret.
   *
   * @return A String representing the OAuth client secret.
   */
  def getClientSecret: String = clientSecret

  /**
   * This is a getter method to get OAuth redirect URL.
   *
   * @return A String representing the OAuth redirect URL.
   */
  def getRedirectURL: String = redirectURL

  /**
   * This is a getter method to get grant token.
   *
   * @return A String representing the grant token.
   */
  def getGrantToken: String = grantToken

  /**
   * This is a getter method to get refresh token.
   *
   * @return A String representing the refresh token.
   */
  def getRefreshToken: String = refreshToken

  /**
   * This is a setter method to set refresh token.
   *
   * @param refreshToken A String containing the refresh token.
   */
  def setRefreshToken(refreshToken: String): Unit = {
    this.refreshToken = refreshToken
  }

  /**
   * This is a setter method to set redirect URL.
   *
   * @param redirectURL A String containing the redirect URL.
   */
  def setRedirectURL(redirectURL: String): Unit = {
    this.redirectURL = redirectURL
  }

  /**
   * This is a getter method to get OAuth client id.
   *
   * @return A String representing the OAuth client id.
   */
  def setClientId(clientID: String): Unit = {
    this.clientID = clientID
  }

  /**
   * This is a getter method to get OAuth client secret.
   *
   * @return A String representing the OAuth client secret.
   */
  def setClientSecret(clientSecret: String): Unit = {
    this.clientSecret = clientSecret
  }

  /**
   * This is a setter method to set grant token.
   *
   * @return A String containing the grant token.
   */
  def setGrantToken(grantToken: String): Unit = {
    this.grantToken = grantToken
  }

  /**
   * This is a getter method to get access token.
   *
   * @return A String representing the access token.
   */
  def getAccessToken: String = accessToken

  /**
   * This is a setter method to set access token.
   *
   * @param accessToken A String containing the access token.
   */
  def setAccessToken(accessToken: String): Unit = {
    this.accessToken = accessToken
  }

  /**
   * This is a getter method to get token expire time.
   *
   * @return A String containing the token expire time.
   */
  def getExpiresIn: String = expiresIn

  /**
   * This is a setter method to set token expire time.
   *
   * @param expiresIn A String containing the token expire time.
   */
  def setExpiresIn(expiresIn: String): Unit = {
    this.expiresIn = expiresIn
  }

  /**
   * This is a getter method to get token user mail.
   *
   * @return the userMail
   */
  def getUserMail: String = userMail

  /**
   * This is a setter method to set token user email.
   *
   * @param userMail the userMail to set
   */
  def setUserMail(userMail: String): Unit = {
    this.userMail = userMail
  }

  /**
   * @return the id
   */
  def getId: String = id

  /**
   * @param id the id to set
   */
  def setId(id: String): Unit = {
    this.id = id
  }

  override def authenticate(urlConnection: APIHTTPConnector): Unit = synchronized {
    var oauthToken: OAuthToken = null

    val initializer = Initializer.getInitializer

    val store: TokenStore = initializer.getStore

    val user: UserSignature = initializer.getUser

    if (this.accessToken == null) {
      if (this.id != null) {
        oauthToken = store.getTokenById(this.id, this).asInstanceOf[OAuthToken]
      }
      else {
        oauthToken = store.getToken(user, this).asInstanceOf[OAuthToken]
      }
    }
    else {
      oauthToken = this
    }

    val token: String =
      if (oauthToken == null) {
        if (this.refreshToken != null) {
          this.refreshAccessToken(user, store).getAccessToken
        } else {
          this.generateAccessToken(user, store).getAccessToken
        }
      } else if (oauthToken.getExpiresIn != null && (oauthToken.getExpiresIn.toLong - System.currentTimeMillis()) < 5000) {
        LOGGER.log(Level.INFO, Constants.REFRESH_TOKEN_MESSAGE)
        oauthToken.refreshAccessToken(user, store).getAccessToken
      } else {
        oauthToken.getAccessToken
      }

    urlConnection.addHeader(Constants.AUTHORIZATION, Constants.OAUTH_HEADER_PREFIX + token)
  }

  private def getResponseFromServer(requestParams: mutable.HashMap[String, String]): String = {
    try {
      val USER_AGENT: String = Constants.USER_AGENT

      val url: String = Initializer.getInitializer.getEnvironment.getAccountsUrl

      val client: HttpClient = getHttpClient

      val post: HttpPost = new HttpPost(url)

      post.setHeader(Constants.USER_AGENT_KEY, USER_AGENT)

      val urlParameters: util.List[NameValuePair] = new util.ArrayList[NameValuePair]()

      /*
			 * Set request Parameters
			 */
      if (requestParams != null && requestParams.nonEmpty) {
        for ((key, value) <- requestParams) {
          if (value != null) {
            urlParameters.add(new BasicNameValuePair(key, value))
          }
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters))

        val response: String = EntityUtils.toString(client.execute(post).getEntity)

        return response
      }
    }
    catch {
      case e: NoSuchAlgorithmException => throw new SDKException(e)
      case e: IOException => throw new SDKException(e)
    }

    null
  }

  private def getHttpClient: CloseableHttpClient = {
    val httpClientBuilder: HttpClientBuilder = HttpClientBuilder.create()

    val sslContext: SSLContext = SSLContext.getDefault

    val sslConnectionSocketFactory: SSLConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE)

    httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build()
  }

  private def refreshAccessToken(user: UserSignature, store: TokenStore): OAuthToken = {
    val requestParams: mutable.HashMap[String, String] = new mutable.HashMap[String, String]()

    requestParams(Constants.CLIENT_ID) = this.clientID

    requestParams(Constants.CLIENT_SECRET) = this.clientSecret

    requestParams(Constants.GRANT_TYPE) = Constants.REFRESH_TOKEN

    requestParams(Constants.REFRESH_TOKEN) = this.refreshToken

    val response: String = getResponseFromServer(requestParams)

    try {
      parseResponse(response)
      if(this.id == null) {
        this.generateId()
      }
      store.saveToken(user, this)
    }
    catch {
      case e: SDKException => throw e
      case e: Exception =>

        throw new SDKException(Constants.SAVE_TOKEN_ERROR, e)
    }

    this
  }

  private def generateAccessToken(user: UserSignature, store: TokenStore): OAuthToken = {
    val requestParams: mutable.HashMap[String, String] = new mutable.HashMap[String, String]()

    requestParams(Constants.CLIENT_ID) = this.clientID

    requestParams(Constants.CLIENT_SECRET) = this.clientSecret

    if (this.redirectURL != null) {
      requestParams(Constants.REDIRECT_URI) = this.redirectURL
    }

    requestParams(Constants.GRANT_TYPE) = Constants.GRANT_TYPE_AUTH_CODE

    requestParams(Constants.CODE) = this.grantToken

    val response: String = getResponseFromServer(requestParams)

    try {
      parseResponse(response)
      this.generateId()
      store.saveToken(user, this)
    }
    catch {
      case e: SDKException => throw e
      case e: Exception =>

        throw new SDKException(Constants.SAVE_TOKEN_ERROR, e)
    }

    this
  }

  private def parseResponse(response: String): OAuthToken = {
    val responseJSON: JSONObject = new JSONObject(response)

    if (!responseJSON.has(Constants.ACCESS_TOKEN)) {
      throw new SDKException(Constants.INVALID_CLIENT_ERROR, if (responseJSON.has(Constants.ERROR_KEY)) responseJSON.getString(Constants.ERROR_KEY) else Constants.NO_ACCESS_TOKEN_ERROR)
    }

    this.accessToken = responseJSON.getString(Constants.ACCESS_TOKEN)

    this.expiresIn = String.valueOf(this.getTokenExpiryTime(responseJSON))

    if (responseJSON.has(Constants.REFRESH_TOKEN)) {
      this.refreshToken = responseJSON.getString(Constants.REFRESH_TOKEN)
    }

    this
  }

  private def getTokenExpiryTime(responseJO: JSONObject): java.lang.Long = {
    System.currentTimeMillis() +
      (if (responseJO.has(Constants.EXPIRES_IN_SEC)) {
        responseJO.getLong(Constants.EXPIRES_IN)
      }
      else {
        responseJO.getInt(Constants.EXPIRES_IN) * 1000
      })
  }

  /**
   * The method to remove the current token from the Store.
   *
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def remove: Boolean = try {
    Initializer.getInitializer.getStore.deleteToken(this)
    true
  } catch {
    case ex: SDKException =>
      throw ex
    case ex: Exception =>
      throw new SDKException(ex)
  }
}