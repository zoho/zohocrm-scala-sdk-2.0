package com.zoho.crm.api.dc

/**
 * This object represents the Zoho CRM environment and accounts URL.
 */
object DataCenter {

  /**
   * This abstract class represents the Zoho CRM environment.
   */
  abstract class Environment {

    /**
     * This method is used to get Zoho CRM API URL.
     *
     * @return A String representing the Zoho CRM API URL.
     */
    def getUrl: String

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    def getAccountsUrl: String

    /**
     * This method is to get Zoho CRM File Upload URL.
     *
     * @return A String representing the File Upload URL.
     */
    def getFileUploadUrl: String

    def getName: String
  }
}

/**
 * This abstract class represents the Zoho CRM environment and accounts URL.
 */
abstract class DataCenter {

  /**
   * This method is to get accounts URL.
   * URL to be used when calling an OAuth accounts.
   *
   * @return A String representing the accounts URL.
   */
  def getIamUrl: String

  /**
   * This method is to get File Upload URL.
   *
   * @return A String representing the accounts URL.
   */
  def getFileUploadUrl: String
}
