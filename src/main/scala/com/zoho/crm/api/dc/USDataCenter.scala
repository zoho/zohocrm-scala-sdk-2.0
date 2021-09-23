package com.zoho.crm.api.dc

import com.zoho.crm.api.dc.DataCenter.Environment

class USDataCenter extends DataCenter {
  override def getIamUrl: String = "https://accounts.zoho.com/oauth/v2/token"

  override def getFileUploadUrl: String = "https://content.zohoapis.com"
}

object USDataCenter {

  /**
   * This Environment class instance represents the Zoho CRM Production environment in US domain
   *
   * @return An instance of Environment
   */
  val PRODUCTION: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://www.zohoapis.com"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new USDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new USDataCenter().getFileUploadUrl

    override def getName = "us_prd"
  }

  /**
   * This Environment class instance represents the Zoho CRM Sandbox environment in US domain
   *
   * @return An instance of Environment
   */
  val SANDBOX: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Sandbox API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://sandbox.zohoapis.com"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new USDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new USDataCenter().getFileUploadUrl

    override def getName = "us_sdb"
  }

  /**
   * This Environment class instance represents the Zoho CRM Developer environment in US domain
   *
   * @return An instance of Environment
   */
  val DEVELOPER: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Developer API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://developer.zohoapis.com"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new USDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new USDataCenter().getFileUploadUrl

    override def getName = "us_dev"
  }
}

