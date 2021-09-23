package com.zoho.crm.api.dc

import com.zoho.crm.api.dc.DataCenter.Environment

/**
 * This class represents the Australian country Zoho CRM and Accounts URL. It is used to denote the domain of the user.
 */
class AUDataCenter extends DataCenter {
  override def getIamUrl: String = "https://accounts.zoho.com.au/oauth/v2/token"

  override def getFileUploadUrl: String = "https://content.zohoapis.com.au"
}

object AUDataCenter {

  /**
   * This Environment class instance represents the Zoho CRM Production environment in AU domain
   *
   * @return An instance of Environment
   */
  val PRODUCTION: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl = "https://www.zohoapis.com.au"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new AUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new AUDataCenter().getFileUploadUrl

    override def getName = "au_prd"
  }

  /**
   * This Environment class instance represents the Zoho CRM Sandbox environment in AU domain
   *
   * @return An instance of Environment
   */
  val SANDBOX: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://sandbox.zohoapis.com.au"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new AUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new AUDataCenter().getFileUploadUrl

    override def getName = "au_sdb"
  }

  /**
   * This Environment class instance represents the Zoho CRM Developer environment in AU domain
   *
   * @return An instance of Environment
   */
  val DEVELOPER: Environment = new Environment {

    /**
     * This method is to get Zoho CRM production API URL.
     *
     * @return A String representing the Zoho CRM production API URL.
     */
    override def getUrl: String = "https://developer.zohoapis.com.au"

    /**
     * This method is to get Zoho CRM Accounts URL.
     *
     * @return A String representing the accounts URL.
     */
    override def getAccountsUrl: String = new AUDataCenter().getIamUrl

    /**
     * The method to get Zoho File Upload URL
     *
     * @return A String representing the File Upload URL.
     */
    override def getFileUploadUrl: String = new AUDataCenter().getFileUploadUrl

    override def getName = "au_dev"
  }
}

