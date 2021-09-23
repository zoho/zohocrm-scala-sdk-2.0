package com.zoho.crm.api.exception

import org.json.JSONObject

/**
 * This class is the common SDKException object.
 *
 * @constructor Creates an SDKException class instance with the specified parameters.
 * @param code    A String containing the Exception error code.
 * @param message A String containing the Exception error message.
 * @param cause   An Exception class instance.
 */
class SDKException(private var code: String, private var message: String, private var details: JSONObject, private var cause: Exception) extends Exception(message, cause) {

  /**
   * @constructor Creates an SDKException class instance with the specified parameters.
   * @param code    A String containing the Exception error code.
   * @param details A JSONObject containing the error response.
   */
  def this(code: String, details: JSONObject) = {
    this(code, null, details, null)
  }

  /**
   * @constructor Creates an SDKException class instance with the specified parameters.
   * @param code    A String containing the Exception error code.
   * @param message A String containing the Exception error message.
   */
  def this(code: String, message: String) = {
    this(code, message, null, null)
  }

  /**
   * @constructor Creates an SDKException class instance with the specified parameters.
   * @param code    A String containing the Exception error code.
   * @param message A String containing the Exception error message.
   * @param details A JSONObject containing the error response.
   */
  def this(code: String, message: String, details: JSONObject) = {
    this(code, message, details, null)
  }


  /**
   * @constructor Creates an SDKException class instance with the specified parameters.
   * @param code    A String containing the Exception error code.
   * @param message A String containing the Exception error message.
   * @param cause   An Exception class instance.
   */
  def this(code: String, message: String, cause: Exception) = {
    this(code, message, null, cause)
  }

  /**
   * This class is the common SDKException object.
   *
   * @constructor Creates an SDKException class instance with the specified parameters.
   * @param cause An Exception class instance.
   */
  def this(cause: Exception) = {
    this(null, null, null, cause)
  }

  def this(message: String, cause: Exception) = {
    this(null, message, null, cause)
  }

  /**
   * This is a getter method to get Exception error code.
   *
   * @return A String representing the Exception error code.
   */
  def getCode: String = this.code

  /**
   * This is a getter method to get Exception error message.
   *
   * @return A String representing the Exception error message.
   */
  override def getMessage: String = this.message

  /**
   * This is a getter method to get Exception class instance.
   *
   * @return A Exception class instance.
   */
  override def getCause: Throwable = this.cause

  /**
   * This is a getter method to get error response JSONObject.
   *
   * @return A JSONObject representing the error response.
   */
  def getDetails: JSONObject = this.details

  override def toString(): String = {
    var returnMsg: String = classOf[SDKException].getName + ". Caused by : "

    if (this.details != null) {
      this.message =
        if (this.message != null) {
          this.message + this.details.toString
        }
        else {
          this.details.toString
        }
    }

    if (this.code != null) {
      returnMsg += this.code + " - " + getMessage
    } else {
      returnMsg += getMessage
    }

    returnMsg
  }

}