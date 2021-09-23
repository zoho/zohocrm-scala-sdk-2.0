package com.zoho.api.authenticator.store

import com.zoho.api.authenticator.{OAuthToken, Token}
import com.zoho.crm.api.UserSignature
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable.ArrayBuffer

/**
 * This trait stores the user token details.
 */
trait TokenStore {
  /**
   * This method is used to get user token details.
   *
   * @param user  A User class instance.
   * @param token A Token class instance.
   * @return A Token class instance representing the user token details.
   * @throws SDKException Exception
   */
  def getToken(user: UserSignature, token: Token): Token

  /**
   * This method is used to get user token details by id.
   *
   * @param id    A String
   * @param token A Token class instance.
   * @return A Token class instance representing the user token details.
   * @throws SDKException Exception
   */
  def getTokenById(id: String, token: Token): Token

  /**
   * This method is used to store user token details.
   *
   * @param user  A User class instance.
   * @param token A Token class instance.
   * @throws SDKException Exception
   */
  def saveToken(user: UserSignature, token: Token): Unit

  /**
   * This method is used to delete user token details.
   *
   * @param token A Token class instance.
   * @throws SDKException Exception
   */
  def deleteToken(token: Token): Unit

  /**
   * The method to retrieve all the stored tokens.
   *
   * @throws SDKException if any problem occurs.
   */
  @throws[SDKException]
  def getTokens: ArrayBuffer[OAuthToken]

  /**
   * The method to delete all the stored tokens.
   *
   * @throws SDKException if any problem occurs.
   */
  @throws[SDKException]
  def deleteTokens(): Unit
}
