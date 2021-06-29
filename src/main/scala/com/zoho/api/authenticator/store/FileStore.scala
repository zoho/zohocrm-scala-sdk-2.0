package com.zoho.api.authenticator.store

import com.zoho.api.authenticator.Token
import com.zoho.crm.api.UserSignature
import java.io.{File, FileReader, FileWriter}

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.OAuthToken.TokenType
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Creates a FileStore class instance with the specified parameters.
 * @param filePath A String containing the absolute file path to store tokens.
 * @throws Exception  Exception
 */
class FileStore(private var filePath :String) extends TokenStore {

  val writer = new FileWriter(new File(filePath),true)

  var fileReader :FileReader = new FileReader(filePath)

  var headerString :String = Constants.USER_MAIL + Constants.COMMA +
    Constants.CLIENT_ID + Constants.COMMA + Constants.REFRESH_TOKEN + Constants.COMMA +
    Constants.ACCESS_TOKEN + Constants.COMMA +
    Constants.GRANT_TOKEN + Constants.COMMA +
    Constants.EXPIRY_TIME

  if(fileReader.read == -1) {
    writer.write(headerString)
  }

  fileReader.close()

  writer.flush()

  writer.close()

  override def getToken(user: UserSignature, token: Token): Token = {
    val src = Source.fromFile(this.filePath)

    val lines = src.getLines.toList

    try {
      val oauthToken: OAuthToken = token.asInstanceOf[OAuthToken]
      if (token.isInstanceOf[OAuthToken]) {
        for ( i <- lines.indices ) {
          val eachLine = lines(i).split(",")

          if (this.checkTokenExists(user.getEmail, oauthToken, eachLine)) {
            oauthToken.setAccessToken(eachLine(3))

            oauthToken.setExpiresIn(eachLine(5))

            oauthToken.setRefreshToken(eachLine(2))

            return oauthToken
          }
        }
      }
    }catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_FILE_ERROR, e)
    }
    finally {
      src.close()
    }

    null
  }

  override def saveToken(user: UserSignature, token: Token): Unit = {
    val fileObject = new File(this.filePath)

    val writer = new FileWriter(fileObject,true)

    try {
      token match {
        case token1: OAuthToken =>
          val oauthToken: OAuthToken = token1

          oauthToken.setUserMail(user.getEmail)

          this.deleteToken(oauthToken)

          var str: String = user.getEmail + "," + oauthToken.getClientID + "," + oauthToken.getRefreshToken + ","

          str += oauthToken.getAccessToken + "," + oauthToken.getGrantToken + "," + oauthToken.getExpiresIn

          writer.append("\n")

          writer.append(str)
        case _ =>
      }
    }
    catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.SAVE_TOKEN_FILE_ERROR, e)
    }
    finally {
      writer.close()
    }
  }

  override def deleteToken(token: Token): Unit = {
    val src = Source.fromFile(this.filePath)

    val lines = src.getLines.toList

    val file = new File(this.filePath)

    val writer = new FileWriter(file)

    try {
      token match {
        case token1: OAuthToken =>
          val oauthToken: OAuthToken = token1

          for ( i <- lines.indices ) {
            val eachLine = lines(i).split(",")

            if (!this.checkTokenExists(oauthToken.getUserMail, oauthToken, eachLine)) {
              if (i != 0)
                writer.write("\n")

              writer.write(lines(i))
            }
          }
        case _ =>
      }
    }
    catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKEN_FILE_ERROR, e)
    }
    finally {
      src.close()

      writer.close()
    }
  }

  private def checkTokenExists(email :String, oauthToken: OAuthToken, row :Array[String]) : Boolean = {


    if (email == null) throw new SDKException(Constants.USER_MAIL_NULL_ERROR, Constants.USER_MAIL_NULL_ERROR_MESSAGE)
    val clientId = oauthToken.getClientID

    val grantToken = oauthToken.getGrantToken

    val refreshToken = oauthToken.getRefreshToken

    val tokenCheck: Boolean = if (grantToken != null) grantToken.equals(row(4)) else refreshToken.equals(row(2))

    if(row(0) == email && row(1) == clientId && tokenCheck) {
      return true
    }

    false
  }

  /**
   * The method to retrieve all the stored tokens.
   *
   * @throws SDKException if any problem occurs.
   */
  override def getTokens: ArrayBuffer[OAuthToken] = {

    val tokens = new ArrayBuffer[OAuthToken]
    val src = Source.fromFile(this.filePath)

    val lines = src.getLines.toList

    try {
        for ( i <- lines.indices ) {

          val eachLine = lines(i).split(",")

          val tokenType = if (eachLine(4) != null && !eachLine(4).isEmpty) {
            TokenType.GRANT
          }
          else {
            TokenType.REFRESH
          }

          val tokenValue: String = if (tokenType.equals(TokenType.REFRESH)) {
            eachLine(2)
          }
          else {
            eachLine(4)
          }

          val token: OAuthToken = new OAuthToken(eachLine(1), null, tokenValue, tokenType)

          token.setUserMail(eachLine(0))

          token.setRefreshToken(eachLine(2))

          token.setAccessToken(eachLine(3))

          token.setExpiresIn(String.valueOf(eachLine(5)))

          tokens.addOne(token)

        }
    }catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKENS_FILE_ERROR, e)
    }
    finally {
      src.close()
    }

    tokens
  }

  /**
   * The method to delete all the stored tokens.
   *
   * @throws SDKException if any problem occurs.
   */
  override def deleteTokens(): Unit = {

    val file = new File(this.filePath)

    val writer = new FileWriter(file)

    try {
      writer.write(this.headerString)
      writer.flush()
    }
    catch
    {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKENS_FILE_ERROR, e)
    }
  }
}
