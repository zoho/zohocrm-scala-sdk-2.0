package com.zoho.crm.api.dc

import com.zoho.crm.api.dc.DataCenter.Environment

class EUDataCenter extends DataCenter {
  override def getIamUrl: String = "https://accounts.zoho.eu/oauth/v2/token"

  override def getFileUploadUrl: String = "https://content.zohoapis.eu"
}

object EUDataCenter {

  /**
   * This Environment class instance represents the Zoho CRM Production environment in EU domain
   *
   * @return An instance of Environment
   */
  val PRODUCTION: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM Production API URL.
     */
    override def getUrl: String = "https://www.zohoapis.eu"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new EUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new EUDataCenter().getFileUploadUrl

    override def getName = "eu_prd"
  }

  /**
   * This Environment class instance represents the Zoho CRM Sandbox environment in EU domain
   *
   * @return An instance of Environment
   */
  val SANDBOX: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Sandbox API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://sandbox.zohoapis.eu"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new EUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new EUDataCenter().getFileUploadUrl

    override def getName = "eu_sdb"
  }

  /**
   * This Environment class instance represents the Zoho CRM Production environment in EU domain
   *
   * @return An instance of Environment
   */
  val DEVELOPER: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Developer API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://developer.zohoapis.eu"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new EUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new EUDataCenter().getFileUploadUrl

    override def getName = "eu_dev"
  }
}

