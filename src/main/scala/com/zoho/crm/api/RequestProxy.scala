package com.zoho.crm.api

import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants


/**
 * This class represents the properties of proxy for the user.
 */


/**
 * Creates a RequestProxy class instance with the specified parameters.
 *
 * @param host       A String containing the hostname or address of the proxy server
 * @param port       A Integer containing The port number of the proxy server
 * @param user       A String containing the user name of the proxy server
 * @param password   A String containing the password of the proxy server
 * @param userDomain A String containing the domain of the proxy server
 * @throws SDKException Exception
 */
class RequestProxy(var host: String, var port: Integer, var user: Option[String], var password:  Option[String], var userDomain:  Option[String]) {
  if (host == null) throw new SDKException(Constants.REQUEST_PROXY_ERROR, Constants.HOST_ERROR_MESSAGE)
  if (port == null) throw new SDKException(Constants.REQUEST_PROXY_ERROR, Constants.PORT_ERROR_MESSAGE)
  this.password = if (password != null) password
  else Option("")

  /**
   * Creates a RequestProxy class instance with the specified parameters.
   *
   * @param host A String containing the hostname or address of the proxy server
   * @param port A Integer containing The port number of the proxy server
   * @throws SDKException Exception
   */
  def this(host: String, port: Integer) {
    this(host, port, None, None, None)
  }

  /**
   * Creates a RequestProxy class instance with the specified parameters.
   *
   * @param host     A String containing the hostname or address of the proxy server
   * @param port     A Integer containing The port number of the proxy server
   * @param user     A String containing the user name of the proxy server
   * @param password A String containing the password of the proxy server
   * @throws Exception
   */
  def this(host: String, port: Integer, user: Option[String], password: Option[String]) {
    this(host, port, user, password, None)
  }

  /**
   * This is a getter method to get Proxy host.
   *
   * @return the host
   */
  def getHost: String = host

  /**
   * This is a getter method to get Proxy port.
   *
   * @return the port
   */
  def getPort: Integer = port

  /**
   * This is a getter method to get Proxy userDomain.
   *
   * @return the userDomain
   */
  def getUserDomain: Option[String] = userDomain

  /**
   * This is a getter method to get Proxy user name.
   *
   * @return the user
   */
  def getUser: Option[String] = user

  /**
   * This is a getter method to get Proxy password.
   *
   * @return the password
   */
  def getPassword: Option[String] = password
}
