package com.zoho.crm.api.util

import java.net.URI
import java.util.logging.{Level, Logger}

import org.json.JSONObject
import com.zoho.crm.api.ParameterMap
import com.zoho.crm.api.Initializer
import java.net.MalformedURLException

import com.zoho.api.logger.SDKLogger
import com.zoho.crm.api.Param
import com.zoho.crm.api.Header
import com.zoho.crm.api.exception.SDKException
import org.apache.http.entity.ContentType
import com.zoho.crm.api.HeaderMap

import scala.collection.mutable

/**
 * This class is to process the API request and its response.
 * Construct the objects that are to be sent as parameters or in the request body with the API.
 * The Request parameter, header and body objects are constructed here.
 * Process the response JSON and converts it to relevant objects in the library.
 */
class CommonAPIHandler {

  private var apiPath: String = _

  private var param: ParameterMap = new ParameterMap()

  private var header: HeaderMap = new HeaderMap()

  private var request: Any = _

  private var httpMethod: String = _

  private var moduleAPIName: String = _

  private var contentType: String = _

  private var categoryMethod: String = _

  private var mandatoryChecker: Boolean = _

  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  /**
   * This is a setter method to set an API request content type.
   *
   * @param contentType A String containing the API request content type.
   */
  def setContentType(contentType: String) {
    this.contentType = contentType
  }

  /**
   * This is a setter method to set the API request URL.
   *
   * @param apiPath A String containing the API request URL.
   */
  def setAPIPath(apiPath: String): Unit = {
    this.apiPath = apiPath
  }

  /**
   * This method is to add an API request parameter.
   *
   * @param paramInstance A Param containing the API request parameter.
   * @param paramValue    A T containing the API request parameter value.
   * @tparam T A T containing the specified method type.
   */
  def addParam[T](paramInstance: Param[T], paramValue: Option[T]): Unit = {
    if (paramValue.isEmpty) return

    if (this.param == null) this.param = new ParameterMap

    this.param.add(paramInstance, paramValue.get)
  }

  /**
   * This method is to add an API request header.
   *
   * @param headerInstance A Header containing the API request header .
   * @param headerValue    A T containing the API request header value.
   * @tparam T A T containing the specified method type.
   */
  def addHeader[T](headerInstance: Header[T], headerValue: Option[T]): Unit = {
    if (headerValue.isEmpty) return

    if (this.header == null) this.header = new HeaderMap

    this.header.add(headerInstance, headerValue.get)
  }

  /**
   * This is a setter method to set the API request parameter map.
   *
   * @param param A ParameterMap class instance containing the API request parameter.
   */
  def setParam(param: Option[ParameterMap]): Unit = {
    if (param.isEmpty) return

    if (this.param.getParameterMap != null && this.param.getParameterMap.nonEmpty) {
      param.get.getParameterMap.foreach(entry => {
        this.param.getParameterMap(entry._1) = entry._2
      })
    }
    else this.param = param.get
  }

  /**
   * This is a getter method to get the Zoho CRM module API name.
   *
   * @return A String representing the Zoho CRM module API name.
   */
  def getModuleAPIName: String = moduleAPIName

  /**
   * This is a setter method to set the Zoho CRM module API name.
   *
   * @param moduleAPIName A String containing the Zoho CRM module API name.
   */
  def setModuleAPIName(moduleAPIName: String): Unit = {
    this.moduleAPIName = moduleAPIName
  }

  /**
   * This is a setter method to set the API request header map.
   *
   * @param header A HeaderMap class instance containing the API request header.
   */
  def setHeader(header: Option[HeaderMap]): Unit = {
    if (header.isEmpty) return

    if (this.header.getHeaderMap != null && this.header.getHeaderMap.nonEmpty) {
      header.get.getHeaderMap.foreach(entry => {
        this.header.getHeaderMap(entry._1) = entry._2
      })
    }
    else this.header = header.get
  }

  /**
   * This is a setter method to set the API request body object.
   *
   * @param request A Object containing the API request body object.
   */
  def setRequest(request: Any): Unit = {
    this.request = request
  }

  /**
   * This is a setter method to set the HTTP API request method.
   *
   * @param httpMethod A String containing the HTTP API request method.
   */
  def setHttpMethod(httpMethod: String): Unit = {
    this.httpMethod = httpMethod
  }

  /**
   * This method is used in constructing API request and response details. To make the Zoho CRM API calls.
   *
   * @param className  A Class[A] containing the method return type.
   * @param encodeType A String containing the expected API response content type.
   * @tparam A A A containing the specified type method.
   * @see com.zoho.crm.api.util.APIHTTPConnector
   * @return A APIResponse[A] representing the Zoho CRM API response instance or null.
   */
  def apiCall[A](className: Class[A], encodeType: String): Option[APIResponse[A]] = {

    if (Initializer.getInitializer == null) throw new SDKException(Constants.SDK_UNINITIALIZATION_ERROR, Constants.SDK_UNINITIALIZATION_MESSAGE)
    val connector = new APIHTTPConnector()

    try this.setAPIUrl(connector)
    catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.SET_API_URL_EXCEPTION, e)
        throw e
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.SET_API_URL_EXCEPTION, exception)
        throw exception
    }

    connector.setRequestMethod(this.httpMethod)

    connector.setContentType(this.contentType)

    if (this.header != null && this.header.getHeaderMap.nonEmpty) {
      connector.setHeaders(this.header.getHeaderMap)
    }

    if (this.param != null && this.param.getParameterMap.nonEmpty) {
      connector.setParams(this.param.getParameterMap)
    }

    //authentication here

    try {
      val initializer = Initializer.getInitializer
      initializer.synchronized {
        initializer.getToken.authenticate(connector)
      }
    }
    catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.AUTHENTICATION_EXCEPTION, e)
        throw e
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.AUTHENTICATION_EXCEPTION, exception)
        throw exception
    }

    val pack = className.getCanonicalName

    var convertInstance: Converter = null
    if (this.contentType != null && Constants.IS_GENERATE_REQUEST_BODY.contains(httpMethod.toUpperCase())) {

      var request: Any = null

      try {
        convertInstance = getConverterClassInstance(this.contentType.toLowerCase)
        request = convertInstance.formRequest(this.request, this.request.getClass.getCanonicalName, null, null)
      } catch {
        case e: SDKException =>
          LOGGER.log(Level.SEVERE, Constants.FORM_REQUEST_EXCEPTION, e)
          throw e
        case e: Exception =>
          val exception = new SDKException(e)
          LOGGER.log(Level.SEVERE, Constants.FORM_REQUEST_EXCEPTION, exception)
          throw exception
      }

      connector.setRequestBody(request)
    }
    try {
      connector.addHeader(Constants.ZOHO_SDK, System.getProperty("os.name") + "/" + System.getProperty("os.version") + "/scala-" + Constants.ZOHO_API_VERSION + "/" + util.Properties.versionNumberString + ":" + Constants.SDK_VERSION)

      val response = connector.fireRequest(convertInstance)

      val statusCode = response.getStatusLine.getStatusCode

      val headerHashMap = getHeaders(response.getAllHeaders)

      var isModel = false

      val contentType = ContentType.getOrDefault(response.getEntity)

      val mimeType = contentType.getMimeType

      convertInstance = getConverterClassInstance(mimeType.toLowerCase)

      val returnObject: Option[Model] = convertInstance.getWrappedResponse(response, pack).asInstanceOf[Option[Model]]

      if (returnObject.isDefined) if (pack.equals(returnObject.get.getClass.getCanonicalName) || isExpectedType(returnObject.get, pack)) isModel = true

      Option(new APIResponse[A](headerHashMap, statusCode, returnObject.getOrElse(None), isModel))
    } catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.API_CALL_EXCEPTION, e)
        throw e
      case e: Exception =>
        val exception: SDKException = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.API_CALL_EXCEPTION, exception)
        throw exception
    }
  }

  /**
   * This method is used to get a Converter class instance.
   *
   * @param encodeType A String containing the API response content type.
   * @return A Converter class instance.
   */
  def getConverterClassInstance(encodeType: String): Converter = {
    encodeType match {
      case "application/json" | "text/plain" | "application/ld+json" =>
        new JSONConverter(this)
      case "application/xml" | "text/xml" =>
        new XMLConverter(this)
      case "multipart/form-data" =>
        new FormDataConverter(this)
      case "text/html" | "application/x-download" | "image/png" | "application/octet-stream" | "image/jpeg" | "application/zip" | "image/gif" | "text/csv" | "image/tiff" | "font/ttf" | "video/3gpp2" | "video/3gpp" | "video/webm" | "video/mp4" | "video/mpeg" | "audio/3gpp2" | "audio/3gpp" | "audio/x-wav" | "image/svg+xml" | "image/bmp" | "image/webp" | "text/css" | "text/javascript" | "text/calendar" | "application/pdf" | "application/java-archive" | "application/javascript" | "application/xhtml+xml" | "application/x-bzip" | "application/msword" | "application/vnd.openxmlformats-officedocument.wordprocessingml.document" | "application/gzip" | "application/x-httpd-php" | "application/vnd.ms-powerpoint" | "application/vnd.rar" | "application/x-sh" | "application/x-tar" | "application/vnd.ms-excel" | "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" | "application/x-7z-compressed" | "audio/mpeg" | "audio/x-ms-wma" | "audio/vnd.rn-realaudio" =>
        new Downloader(this)
    }
  }

  def getHeaders(headers: Array[org.apache.http.Header]): mutable.HashMap[String, String] = {
    val headerHashMap = new mutable.HashMap[String, String]
    for (header <- headers) {
      headerHashMap(header.getName) = header.getValue
    }
    headerHashMap
  }

  private def isExpectedType(model: Model, className: String): Boolean = {
    val interfaces = model.getClass.getInterfaces
    for (interfaceDetails <- interfaces) {
      if (interfaceDetails.getCanonicalName == className) return true
    }
    false
  }

  @throws[SDKException]
  private def setAPIUrl(connector: APIHTTPConnector): Unit = {
    var APIPath: String = new String()
    if (apiPath.contains(Constants.HTTP)) {
      if (apiPath.contains(Constants.CONTENT_API_URL)) {
        APIPath = APIPath.concat(Initializer.getInitializer.getEnvironment.getFileUploadUrl)
        try {
          val uri = new URI(apiPath)

          APIPath = APIPath.concat(uri.getPath)
        } catch {
          case ex: MalformedURLException =>
            val excp = new SDKException(ex)

            LOGGER.log(Level.SEVERE, Constants.INVALID_URL_ERROR, excp)

            throw excp
        }
      }
      else {
        if (apiPath.substring(0, 1).equalsIgnoreCase("/")) apiPath = apiPath.substring(1)

        APIPath = APIPath.concat(apiPath)
      }
    } else {
      APIPath = APIPath.concat(Initializer.getInitializer.getEnvironment.getUrl)

      APIPath = APIPath.concat(apiPath)
    }

    connector.setUrl(APIPath)
  }

  def isMandatoryChecker: Boolean = mandatoryChecker

  def setMandatoryChecker(mandatoryChecker: Boolean): Unit = {
    this.mandatoryChecker = mandatoryChecker
  }

  def getHttpMethod: String = this.httpMethod

  /**
   * This is a getter method to get the Category of the method.
   *
   * @return A String representing the Category of the method.
   */
  def getCategoryMethod: String = categoryMethod

  /**
   * This is a setter method to set the category of request method.
   *
   * @param categoryMethod A String containing category of request method.
   */
  def setCategoryMethod(categoryMethod: String): Unit = {
    this.categoryMethod = categoryMethod
  }

  def getAPIPath: String = apiPath
}