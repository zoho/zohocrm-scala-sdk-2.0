package com.zoho.api.authenticator

import com.zoho.crm.api.util.APIHTTPConnector
import com.zoho.crm.api.exception.SDKException

/**
 * This trait verifies and sets token to APIHTTPConnector instance.
 */
trait Token {
  /**
   * This method is to set authentication token to APIHTTPConnector instance.
   *
   * @param urlConnection A APIHTTPConnector class instance.
   * @throws SDKException
   */
  @throws[SDKException]
  def authenticate(urlConnection: APIHTTPConnector): Unit


  @throws[SDKException]
  def remove: Boolean
}