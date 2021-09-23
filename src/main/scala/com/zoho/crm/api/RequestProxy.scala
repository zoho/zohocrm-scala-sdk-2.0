package com.zoho.crm.api

import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.util.Utility

/**
 * This class represents the properties of proxy for the user.
*/

object RequestProxy {
  class Builder() {
    private var host: String = _

    private var port: Integer = _

    private var userDomain: String = _

    private var user: String = _

    private var password: String = ""

    def host(host: String): RequestProxy.Builder = {
      Utility.assertNotNull(host, Constants.REQUEST_PROXY_ERROR, Constants.HOST_ERROR_MESSAGE)

      this.host = host

      this
    }

    def port(port: Integer): RequestProxy.Builder = {
      Utility.assertNotNull(port, Constants.REQUEST_PROXY_ERROR, Constants.PORT_ERROR_MESSAGE)

      this.port = port

      this
    }

    def userDomain(userDomain: String): RequestProxy.Builder = {
      this.userDomain = userDomain

      this
    }

    def user(user: String): RequestProxy.Builder = {
      this.user = user

      this
    }

    def password(password: String): RequestProxy.Builder = {
      this.password = password

      this
    }

    def build(): RequestProxy = {
      Utility.assertNotNull(host, Constants.REQUEST_PROXY_ERROR, Constants.HOST_ERROR_MESSAGE)

      Utility.assertNotNull(port, Constants.REQUEST_PROXY_ERROR, Constants.PORT_ERROR_MESSAGE)

      return new RequestProxy(this.host, this.port, this.user, this.password, this.userDomain)
    }
  }
}

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
class RequestProxy(var host: String, var port: Integer, var user: String, var password:  String, var userDomain: String) {
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
  def getUserDomain: String = userDomain

  /**
   * This is a getter method to get Proxy user name.
   *
   * @return the user
   */
  def getUser: String = user

  /**
   * This is a getter method to get Proxy password.
   *
   * @return the password
   */
  def getPassword: String = password
}
