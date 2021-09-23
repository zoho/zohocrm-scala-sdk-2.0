package com.zoho.crm.api

import java.util.regex.Matcher
import java.util.regex.Pattern
import _root_.org.json.JSONObject
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants

/**
 * This class represents the Zoho CRM User.
*/

/**
 * Creates an UserSignature class instance with the specified user email.
 *
 * @param email A String containing the CRM user email.
 * @throws SDKException if a problem occurs.
 */
class UserSignature(var email: String){
  val regex: String = Constants.EMAIL_REGEX
  val pattern: Pattern = Pattern.compile(regex)
  val matcher: Matcher = pattern.matcher(email)
  if (!matcher.matches) {
    val error = new JSONObject
    error.put(Constants.FIELD, Constants.EMAIL)
    error.put(Constants.EXPECTED_TYPE, Constants.EMAIL)
    val ex = new SDKException(Constants.USER_SIGNATURE_ERROR, error)
    throw ex
  }

  /**
   * This is a getter method to get user email.
   *
   * @return A String representing the CRM user email.
   */
  def getEmail: String = email
}