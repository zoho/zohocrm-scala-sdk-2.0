package com.zoho.crm.api.util

import java.io.UnsupportedEncodingException
import java.net.URI
import java.security.NoSuchAlgorithmException
import java.util.logging.{Level, Logger}

import com.zoho.api.logger.SDKLogger
import com.zoho.crm.api.{Initializer, RequestProxy}
import javax.net.ssl.SSLContext
import org.apache.http.auth.{AuthScope, NTCredentials}
import org.apache.http.client.config.RequestConfig
import org.apache.http.{HttpHeaders, HttpHost, HttpResponse}
import org.apache.http.client.methods._
import org.apache.http.client.utils.URIBuilder
import org.apache.http.conn.ssl.{NoopHostnameVerifier, SSLConnectionSocketFactory}
import org.apache.http.impl.client.{BasicCredentialsProvider, CloseableHttpClient, HttpClientBuilder}

import scala.collection.mutable

/**
 * This class is to make HTTP connections, trigger the requests and receive the response
 */
class APIHTTPConnector {

  private var url: String = _

  private var requestMethod: String = _

  private var headers: mutable.HashMap[String, String] = mutable.HashMap()

  private var parameters: mutable.HashMap[String, String] = mutable.HashMap()

  private var requestBody: Any = _

  private var contentType: String = _

  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  /**
   * This is a getter method to get the ContentType.
   *
   * @return A String representing the ContentType.
   */
  def getContentType: String = contentType

  /**
   * This is a setter method to set the ContentType.
   *
   * @param contentType A String containing the ContentType.
   */
  def setContentType(contentType: String): Unit = {
    this.contentType = contentType
  }

  /**
   * This is a setter method to set the API URL.
   *
   * @param url A String containing the API Request URL.
   */
  def setUrl(url: String): Unit = {
    this.url = url
  }

  /**
   * This is a setter method to set the API request method.
   *
   * @param httpMethod A String containing the API request method.
   */
  def setRequestMethod(httpMethod: String): Unit = {
    this.requestMethod = httpMethod
  }

  /**
   * This is a getter method to get API request headers.
   *
   * @return A HashMap[String, String] representing the API request headers.
   */
  def getHeaders: mutable.HashMap[String, String] = this.headers


  /**
   * This is a setter method to set API request headers.
   *
   * @param headers A HashMap[String, String] containing the API request headers.
   */
  def setHeaders(headers: mutable.HashMap[String, String]): Unit = {
    this.headers = headers
  }

  /**
   * This method to add API request header name and value.
   *
   * @param headerName  A String containing the API request header name.
   * @param headerValue A String containing the API request header value.
   */
  def addHeader(headerName: String, headerValue: String): Unit = {
    this.headers(headerName) = headerValue
  }

  /**
   * This is a getter method to get API request parameters.
   *
   * @return A HashMap[String, String] representing the API request parameters.
   */
  def getParams: mutable.HashMap[String, String] = this.parameters

  /**
   * This is a setter method to set API request parameters.
   *
   * @param parameters A HashMap[String, String] containing the API request parameters.
   */
  def setParams(parameters: mutable.HashMap[String, String]): Unit = {
    this.parameters = parameters
  }

  /**
   * This method to add API request param name and value.
   *
   * @param paramName  A String containing the API request param name.
   * @param paramValue A String containing the API request param value.
   */
  def addParam(paramName: String, paramValue: String): Unit = {
    this.parameters(paramName) = paramValue
  }

  /**
   * This is a setter method to set the API request body.
   *
   * @param requestBody A Object containing the API request body.
   */
  def setRequestBody(requestBody: Any): Unit = {
    this.requestBody = requestBody
  }

  /**
   * This method makes a Zoho CRM Rest API request
   *
   * @param converterInstance A Converter class instance to call appendToRequest method.
   * @return HttpResponse class instance or None
   * @throws Exception exception
   */
  def fireRequest(converterInstance: Converter): HttpResponse = {
    val httpclient = getHttpClient

    val uriBuilder = new URIBuilder(this.url)

    this.setQueryParams(uriBuilder)

    val requestObj = getRequestObj(uriBuilder.build)

    if (this.contentType != null) this.setContentTypeHeader()

    if (this.requestBody != null) {
      val requestBase = requestObj.asInstanceOf[HttpEntityEnclosingRequestBase]

      converterInstance.appendToRequest(requestBase, this.requestBody)
    }

    this.setQueryHeaders(requestObj)

    LOGGER.log(Level.INFO, this.toString(uriBuilder))

    val response = httpclient.execute(requestObj)

    response
  }

  @throws[UnsupportedEncodingException]
  private def getRequestObj(urlPath: URI): HttpUriRequest = {
    this.requestMethod match {
      case "GET" =>
        return new HttpGet(urlPath)
      case "DELETE" =>
        return new HttpDelete(urlPath)
      case "POST" =>
        return new HttpPost(urlPath)
      case "PUT" =>
        return new HttpPut(urlPath)
      case "PATCH" =>
        return new HttpPatch(urlPath)
    }
    null
  }

  @throws[NoSuchAlgorithmException]
  private def getHttpClient: CloseableHttpClient = {
    val httpClientBuilder = HttpClientBuilder.create

    val requestProxy = Initializer.getInitializer.getRequestProxy

    val config = RequestConfig.custom.setConnectTimeout(Initializer.getInitializer.getSDKConfig.connectionTimeout).setConnectionRequestTimeout(Initializer.getInitializer.getSDKConfig.requestTimeout).setSocketTimeout(Initializer.getInitializer.getSDKConfig.socketTimeout).build

    httpClientBuilder.setDefaultRequestConfig(config)

    if (requestProxy != null) {
      val proxyHost = requestProxy.getHost
      val proxyPort = requestProxy.getPort
      val proxy = new HttpHost(proxyHost, proxyPort)
      if (requestProxy.getUser != null) {
        val credsProvider = new BasicCredentialsProvider
        credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new NTCredentials(requestProxy.getUser, requestProxy.getPassword, null, requestProxy.getUserDomain))
        httpClientBuilder.setDefaultCredentialsProvider(credsProvider)
      }
      httpClientBuilder.setProxy(proxy)
      LOGGER.log(Level.INFO, proxyLog(requestProxy))
    }

    val sslContext = SSLContext.getDefault

    val sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE)

    httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build
  }

  def setQueryHeaders(request: HttpUriRequest): Unit = {
    val headersHashMap: mutable.HashMap[String, String] = this.headers

    val headers = headersHashMap.keySet.iterator

    while (headers.hasNext) {
      val header = headers.next

      request.addHeader(header, headersHashMap.getOrElse(header, ""))
    }
  }

  def setQueryParams(uriBuilder: URIBuilder): Unit = {
    val paramsHashMap = this.parameters

    val params = paramsHashMap.keySet.iterator

    while (params.hasNext) {
      val param = params.next

      uriBuilder.addParameter(param, paramsHashMap.getOrElse(param, ""))
    }
  }


  def setContentTypeHeader(): Unit = {
    Constants.SET_TO_CONTENT_TYPE.foreach(contentType => {
      if (this.url.contains(contentType)) {
        this.headers(HttpHeaders.CONTENT_TYPE) = this.contentType
        return
      }
    })
  }

  def toString(uriBuilder: URIBuilder): String = {
    val reqHeaders: mutable.HashMap[String, String] = mutable.HashMap()
    this.headers.foreach(entry => {
      reqHeaders(entry._1) = entry._2
    })

    reqHeaders(Constants.AUTHORIZATION) = Constants.CANT_DISCLOSE
    this.requestMethod.concat(" - ").concat(Constants.URL.concat(" = ").concat(uriBuilder.toString).concat(" , ").concat(Constants.HEADERS).concat(" = ").concat(reqHeaders.toString).concat(" , ").concat(Constants.PARAMS).concat(" = ").concat(this.parameters.toString).concat("."))
  }

  private def proxyLog(requestProxy: RequestProxy): String = {
    var proxyDetails = Constants.PROXY_SETTINGS + Constants.PROXY_HOST + requestProxy.getHost + " , " + Constants.PROXY_PORT + requestProxy.getPort.toString
    if (requestProxy.getUser != null) proxyDetails += " , " + Constants.PROXY_USER + requestProxy.getUser
    if (requestProxy.getUserDomain != null) proxyDetails += " , " + Constants.PROXY_DOMAIN + requestProxy.getUserDomain
    proxyDetails
  }
}