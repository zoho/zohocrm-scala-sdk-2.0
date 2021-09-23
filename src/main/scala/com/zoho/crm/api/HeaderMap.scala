package com.zoho.crm.api

import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.{Constants, DataTypeConverter}

import scala.collection.mutable
import scala.collection.mutable.HashMap

/**
 * This class represents the HTTP header name and value.
 */
class HeaderMap {
  private val headerHashMap: mutable.HashMap[String, String] = HashMap()

  /**
   * This is a getter method to get header map.
   *
   * @return A HashMap&lt;String, String&gt; representing the API request headers.
   */
  def getHeaderMap: mutable.HashMap[String, String] = headerHashMap

  /**
   * This method is to add header name and value.
   *
   * @param header A Header&lt;T&gt; class instance.
   * @param value  A T containing the header value.
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def add[T](header: Header[T], value: T): Unit = {
    if (header == null) throw new SDKException(Constants.HEADER_NULL_ERROR, Constants.HEADER_INSTANCE_NULL_ERROR)
    val headerName = header.getName
    if (headerName == null) throw new SDKException(Constants.HEADER_NAME_NULL_ERROR, Constants.HEADER_NAME_NULL_ERROR_MESSAGE)
    if (value == null) throw new SDKException(Constants.HEADER_NULL_ERROR, headerName + Constants.NULL_VALUE_ERROR_MESSAGE)
    var headerValue: String = ""
    try headerValue = DataTypeConverter.postConvert(value, value.getClass.getName).toString
    catch {
      case _: Exception =>
        headerValue = value.toString
    }
    if (headerHashMap.keySet.contains(headerName) && headerHashMap.get(headerName).isDefined) {
      var existingHeaderValue: String = headerHashMap(headerName)
      existingHeaderValue = existingHeaderValue + "," + headerValue
      headerHashMap(headerName) = existingHeaderValue
    }
    else headerHashMap(headerName) = headerValue
  }
}