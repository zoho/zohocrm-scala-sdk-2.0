package com.zoho.crm.api

/**
 * The class to configure the SDK.
 */
object SDKConfig {

  /**
   * The Builder class to build SDKConfig
   */
  class Builder() {
    private var autoRefreshFields = false
    private var pickListValidation = true
    private var connectionTimeout = 0
    private var requestTimeout = 0
    private var socketTimeout = 0

    /**
     * This is a setter method to set autoRefreshFields.
     *
     * @param autoRefreshFields A boolean
     * @return An instance of Builder
     */
    def autoRefreshFields(autoRefreshFields: Boolean): SDKConfig.Builder = {
      this.autoRefreshFields = autoRefreshFields
      this
    }

    /**
     * This is a setter method to set pickListValidation.
     *
     * @param pickListValidation A boolean
     * @return An instance of Builder
     */
    def pickListValidation(pickListValidation: Boolean): SDKConfig.Builder = {
      this.pickListValidation = pickListValidation
      this
    }

    /**
     * This is a setter method to set connectionTimeout.
     *
     * @param connectionTimeout A int
     * @return An instance of Builder
     */
    def connectionTimeout(connectionTimeout: Int): SDKConfig.Builder = {
      this.connectionTimeout = if (connectionTimeout > 0) connectionTimeout
      else 0
      this
    }

    /**
     * This is a setter method to set requestTimeout.
     *
     * @param requestTimeout A int
     * @return An instance of Builder
     */
    def requestTimeout(requestTimeout: Int): SDKConfig.Builder = {
      this.requestTimeout = if (requestTimeout > 0) requestTimeout
      else 0
      this
    }

    /**
     * This is a setter method to set socketTimeout.
     *
     * @param socketTimeout A int
     * @return An instance of Builder
     */
    def socketTimeout(socketTimeout: Int): SDKConfig.Builder = {
      this.socketTimeout = if (socketTimeout > 0) socketTimeout
      else 0
      this
    }

    /**
     * The method to build the SDKConfig instance
     *
     * @return An instance of SDKConfig
     */
    def build = new SDKConfig(autoRefreshFields, pickListValidation, connectionTimeout, requestTimeout, socketTimeout)
  }

}
/**
 * Creates an instance of SDKConfig with the given parameters
 *
 * @param autoRefreshFields  A boolean representing autoRefreshFields
 * @param pickListValidation A boolean representing pickListValidation
 * @param connectionTimeout A long representing connectionTimeout
 * @param requestTimeout A long representing requestTimeout
 * @param socketTimeout A long representing socketTimeout
 */
class SDKConfig private(var autoRefreshFields: Boolean, var pickListValidation: Boolean,var connectionTimeout: Int, var requestTimeout: Int, var socketTimeout: Int){

  /**
   * This is a getter method to get autoRefreshFields.
   *
   * @return A boolean representing autoRefreshFields
   */
  def getAutoRefreshFields: Boolean = autoRefreshFields

  /**
   * This is a getter method to get pickListValidation.
   *
   * @return A boolean representing pickListValidation
   */
  def getPickListValidation: Boolean = pickListValidation

  /**
   * This is a getter method to get connectionTimeout.
   *
   * @return A int representing connectionTimeout
   */
  def getConnectionTimeout: Int = connectionTimeout

  /**
   * This is a getter method to get requestTimeout.
   *
   * @return A int representing requestTimeout
   */
  def getRequestTimeout: Int = requestTimeout

  /**
   * This is a getter method to get socketTimeout.
   *
   * @return A int representing socketTimeout
   */
  def getSocketTimeout: Int = socketTimeout
}
