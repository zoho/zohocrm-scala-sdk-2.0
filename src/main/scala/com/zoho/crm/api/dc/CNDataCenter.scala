package com.zoho.crm.api.dc

import com.zoho.crm.api.dc.DataCenter.Environment

class CNDataCenter extends DataCenter {
  override def getIamUrl: String = "https://accounts.zoho.com.cn/oauth/v2/token"

  override def getFileUploadUrl: String = "https://content.zohoapis.com.cn"
}

object CNDataCenter {

  /**
   * This Environment class instance represents the Zoho CRM Production environment in CN domain
   *
   * @return An instance of Environment
   */
  val PRODUCTION: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://www.zohoapis.com.cn"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new CNDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new CNDataCenter().getFileUploadUrl

    override def getName = "cn_prd"
  }

  /**
   * This Environment class instance represents the Zoho CRM Sandbox environment in CN domain
   *
   * @return An instance of Environment
   */
  val SANDBOX: Environment = new Environment {

    /**
     * This method is to get Zoho CRM sandbox API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://sandbox.zohoapis.com.cn"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new CNDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new CNDataCenter().getFileUploadUrl

    override def getName = "cn_sdb"
  }

  /**
   * This Environment class instance represents the Zoho CRM Developer environment in CN domain
   *
   * @return An instance of Environment
   */
  val DEVELOPER: Environment = new Environment {

    /**
     * This method is to get Zoho CRM Developer API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://developer.zohoapis.com.cn"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new CNDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new CNDataCenter().getFileUploadUrl

    override def getName = "cn_dev"
  }
}