package com.zoho.crm.api.dc

import com.zoho.crm.api.dc.DataCenter.Environment

/**
 * This class represents the Japan country Zoho CRM and Accounts URL. It is used to denote the domain of the user.
 */
class JPDataCenter extends DataCenter {
  override def getIamUrl: String = "https://accounts.zoho.jp/oauth/v2/token"

  override def getFileUploadUrl: String = "https://content.zohoapis.jp"
}

object JPDataCenter {

  /**
   * This Environment class instance represents the Zoho CRM Production environment in Japan domain
   *
   * @return An instance of Environment
   */
  val PRODUCTION: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl = "https://www.zohoapis.jp"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new JPDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new JPDataCenter().getFileUploadUrl

    override def getName = "jp_prd"
  }

  /**
   * This Environment class instance represents the Zoho CRM Sandbox environment in Japan domain
   *
   * @return An instance of Environment
   */
  val SANDBOX: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://sandbox.zohoapis.jp"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new JPDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new JPDataCenter().getFileUploadUrl

    override def getName = "jp_sdb"
  }

  /**
   * This Environment class instance represents the Zoho CRM Developer environment in Japan domain
   *
   * @return An instance of Environment
   */
  val DEVELOPER: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://developer.zohoapis.jp"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new JPDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new JPDataCenter().getFileUploadUrl

    override def getName = "jp_dev"
  }
}

