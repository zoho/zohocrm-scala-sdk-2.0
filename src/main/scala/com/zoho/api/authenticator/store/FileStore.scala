package com.zoho.api.authenticator.store

import com.zoho.api.authenticator.Token
import com.zoho.crm.api.UserSignature
import java.io.{File, FileReader, FileWriter}

import com.zoho.api.authenticator.OAuthToken
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Creates a FileStore class instance with the specified parameters.
 *
 * @param filePath A String containing the absolute file path to store tokens.
 * @throws Exception Exception
 */
class FileStore(private var filePath: String) extends TokenStore {

  val writer = new FileWriter(new File(filePath), true)

  var fileReader: FileReader = new FileReader(filePath)

  var headerString: String = Constants.ID + Constants.COMMA + Constants.USER_MAIL + Constants.COMMA +
    Constants.CLIENT_ID + Constants.COMMA + Constants.CLIENT_SECRET + Constants.COMMA + Constants.REFRESH_TOKEN + Constants.COMMA +
    Constants.ACCESS_TOKEN + Constants.COMMA +
    Constants.GRANT_TOKEN + Constants.COMMA +
    Constants.EXPIRY_TIME + Constants.COMMA + Constants.REDIRECT_URL

  if (fileReader.read == -1) {
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
        for (i <- lines.indices) {
          val nextRecord = lines(i).split(",")

          if (this.checkTokenExists(user.getEmail, oauthToken, nextRecord)) {

            val grantToken: String = if (nextRecord(6) != null && nextRecord(6).nonEmpty) {
              nextRecord(6)
            }
            else {
              null
            }

            val redirectURL: String = if (nextRecord(8) != null && nextRecord(8).nonEmpty) {
              nextRecord(8)
            }
            else {
              null
            }

            oauthToken.setId(nextRecord(0))

            oauthToken.setUserMail(nextRecord(1))

            oauthToken.setClientId(nextRecord(2))

            oauthToken.setClientSecret(nextRecord(3))

            oauthToken.setRefreshToken(nextRecord(4))

            oauthToken.setAccessToken(nextRecord(5))

            oauthToken.setGrantToken(grantToken)

            oauthToken.setExpiresIn(String.valueOf(nextRecord(7)))

            oauthToken.setRedirectURL(redirectURL)

            return oauthToken
          }
        }
      }
    } catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_FILE_ERROR, e)
    }
    finally {
      src.close()
    }

    null
  }

  override def saveToken(user: UserSignature, token: Token): Unit = {
    val fileObject = new File(this.filePath)

    val writer = new FileWriter(fileObject, true)

    try {
      token match {
        case token1: OAuthToken =>
          val oauthToken: OAuthToken = token1

          oauthToken.setUserMail(user.getEmail)

          this.deleteToken(oauthToken)

          var str: String = oauthToken.getId + "," + user.getEmail + "," + oauthToken.getClientId + "," + oauthToken.getClientSecret + ","

          str += oauthToken.getRefreshToken + "," + oauthToken.getAccessToken + "," + oauthToken.getGrantToken + "," + oauthToken.getExpiresIn + ","

          str += oauthToken.getRedirectURL

          writer.append("\n")

          writer.append(str)
        case _ =>
      }
    }
    catch {
      case e: Exception =>
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

          for (i <- lines.indices) {
            val nextRecord = lines(i).split(",")

            if (!this.checkTokenExists(oauthToken.getUserMail, oauthToken, nextRecord)) {
              if (i != 0)
                writer.write("\n")

              writer.write(lines(i))
            }
          }
        case _ =>
      }
    }
    catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKEN_FILE_ERROR, e)
    }
    finally {
      src.close()

      writer.close()
    }
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
      for (i <- lines.indices) {

        val nextRecord = lines(i).split(",")

        val grantToken: String = if (nextRecord(6) != null && nextRecord(6).nonEmpty) {
          nextRecord(6)
        }
        else {
          null
        }

        val redirectURL: String = if (nextRecord(8) != null && nextRecord(8).nonEmpty) {
          nextRecord(8)
        }
        else {
          null
        }

        val oauthToken: OAuthToken = new OAuthToken.Builder().clientID(nextRecord(2)).clientSecret(nextRecord(3))
          .refreshToken(nextRecord(4)).grantToken(grantToken).build()

        oauthToken.setId(nextRecord(0))

        oauthToken.setUserMail(nextRecord(1))

        oauthToken.setAccessToken(nextRecord(5))

        oauthToken.setExpiresIn(String.valueOf(nextRecord(7)))

        oauthToken.setRedirectURL(redirectURL)

        tokens.addOne(oauthToken)

      }
    } catch {
      case e: Exception =>
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
    catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKENS_FILE_ERROR, e)
    }
  }

  private def checkTokenExists(email: String, oauthToken: OAuthToken, row: Array[String]): Boolean = {
    if (email == null) throw new SDKException(Constants.USER_MAIL_NULL_ERROR, Constants.USER_MAIL_NULL_ERROR_MESSAGE)
    val clientId = oauthToken.getClientId

    val grantToken = oauthToken.getGrantToken

    val refreshToken = oauthToken.getRefreshToken

    val tokenCheck: Boolean = if (grantToken != null) grantToken.equals(row(6)) else refreshToken.equals(row(4))

    if (row(1) == email && row(2) == clientId && tokenCheck) {
      return true
    }

    false
  }

  override def getTokenById(id: String, token: Token): Token = {
    val src = Source.fromFile(this.filePath)

    val lines = src.getLines.toList

    var isRowPresent: Boolean = false

    try {
      val oauthToken: OAuthToken = token.asInstanceOf[OAuthToken]
      if (token.isInstanceOf[OAuthToken]) {
        for (i <- lines.indices) {
          val nextRecord = lines(i).split(",")

          if (nextRecord(0) == id) {
            isRowPresent = true
            val grantToken: String = if (nextRecord(6) != null && nextRecord(6).nonEmpty) {
              nextRecord(6)
            }
            else {
              null
            }

            val redirectURL: String = if (nextRecord(8) != null && nextRecord(8).nonEmpty) {
              nextRecord(8)
            }
            else {
              null
            }

            oauthToken.setId(nextRecord(0))

            oauthToken.setUserMail(nextRecord(1))

            oauthToken.setClientId(nextRecord(2))

            oauthToken.setClientSecret(nextRecord(3))

            oauthToken.setRefreshToken(nextRecord(4))

            oauthToken.setAccessToken(nextRecord(5))

            oauthToken.setGrantToken(grantToken)

            oauthToken.setExpiresIn(String.valueOf(nextRecord(7)))

            oauthToken.setRedirectURL(redirectURL)

            return oauthToken
          }
        }
      }
    } catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_FILE_ERROR, e)
    }
    finally {
      src.close()
    }

    null
  }
}
