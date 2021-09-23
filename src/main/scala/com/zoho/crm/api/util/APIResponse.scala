package com.zoho.crm.api.util

import scala.collection.mutable

/**
 * This class is the common API response object.
 *
 * @param headers    A HashMap[String, String] containing the API response headers.
 * @param statusCode A Integer containing the API response HTTP status code.
 * @param obj        A Object containing the API response class instance.
 * @param expected   A Boolean containing the API response instance expected type or not.
 * @tparam A This is the class type
 */
class APIResponse[A](var headers: mutable.HashMap[String, String], var statusCode: Int, var obj: Any, var expected: Boolean) {

  /**
   * This is a getter method to get API response headers.
   *
   * @return A HashMap[String, String] representing the API response headers.
   */
  def getHeaders: mutable.HashMap[String, String] = {
    this.headers
  }

  /**
   * This is a getter method to get the API response HTTP status code.
   *
   * @return A Int representing the API response HTTP status code.
   */
  def getStatusCode: Int = {
    this.statusCode
  }

  /**
   * This is a getter method to get an API response instance that is the expected type or not.
   *
   * @return A Boolean representing the instance is expected type or not.
   */
  def isExpected: Boolean = {
    this.expected
  }

  /**
   * This is a getter method to get the API response Model trait instance.
   *
   * @return A Model trait instance.
   */
  def getModel: Any = {
    this.obj
  }

  /**
   * This method to get an API response class instance.
   *
   * @return A class instance.
   */
  def getObject: A = {
    this.obj.asInstanceOf[A]
  }
}