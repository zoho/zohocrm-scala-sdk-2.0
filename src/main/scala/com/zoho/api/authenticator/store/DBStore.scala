package com.zoho.api.authenticator.store

import com.zoho.api.authenticator.{OAuthToken, Token}
import com.zoho.crm.api.UserSignature
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.ResultSet

import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable.ArrayBuffer

object DBStore {
  class Builder() {
    private var userName = Constants.MYSQL_USER_NAME

    private var portNumber = Constants.MYSQL_PORT_NUMBER

    private var password = ""

    private var host = Constants.MYSQL_HOST

    private var databaseName = Constants.MYSQL_DATABASE_NAME

    private var tableName = Constants.MYSQL_TABLE_NAME

    def userName(userName: Option[String]): DBStore.Builder = {
      if (userName.isDefined) {
        this.userName = userName.get
      }
      this
    }

    def portNumber(portNumber: Option[String]): DBStore.Builder = {
      if (portNumber.isDefined) {
        this.portNumber = portNumber.get
      }
      this
    }

    def password(password: Option[String]): DBStore.Builder = {
      if (password.isDefined) {
        this.password = password.get
      }
      this
    }

    def host(host: Option[String]): DBStore.Builder = {
      if (host.isDefined) {
        this.host = host.get
      }
      this
    }

    def databaseName(databaseName: Option[String]): DBStore.Builder = {
      if (databaseName.isDefined) {
        this.databaseName = databaseName.get
      }
      this
    }

    def tableName(tableName: Option[String]): DBStore.Builder = {
      if (tableName.isDefined) {
        this.tableName = tableName.get
      }
      this
    }

    def build = new DBStore(host, databaseName, tableName, userName, password, portNumber)
  }
}

/**
 * This class stores the user token details to the MySQL DataBase.
 *
 * @constructor Creates a DBStore class instance with the specified parameters.
 * @param host         A String containing the DataBase host name.
 * @param databaseName A String containing the DataBase name.
 * @param userName     A String containing the DataBase user name.
 * @param password     A String containing the DataBase password.
 * @param portNumber   A String containing the DataBase port number.
 */
class DBStore(private var host: String, private var databaseName: String, private var tableName: String, private var userName: String, private var password: String, private var portNumber: String) extends TokenStore {

  host = host

  databaseName = databaseName

  tableName = tableName

  userName = userName

  password = password

  portNumber = portNumber

  var connectionString: String = this.host + ":" + this.portNumber + "/" + this.databaseName + "?allowPublicKeyRetrieval=true&useSSL=false"

  override def getToken(user: UserSignature, token: Token): Token = {
    var connection: Connection = null

    var statement: Statement = null

    var oauthToken: OAuthToken = null

    var resultSet: ResultSet = null

    try {
      oauthToken = token.asInstanceOf[OAuthToken]

      Class.forName(Constants.JDBC_DRIVER_NAME)

      connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)

      if (token.isInstanceOf[OAuthToken]) {

        statement = connection.createStatement()

        val query = this.constructDBQuery(user.getEmail, oauthToken, isDelete = false)

        resultSet = statement.executeQuery(query)

        while (resultSet.next()) {
          oauthToken.setId(resultSet.getString(1))

          oauthToken.setUserMail(resultSet.getString(2))

          oauthToken.setClientId(resultSet.getString(3))

          oauthToken.setClientSecret(resultSet.getString(4))

          oauthToken.setRefreshToken(resultSet.getString(5))

          oauthToken.setAccessToken(resultSet.getString(6))

          oauthToken.setGrantToken(resultSet.getString(7))

          oauthToken.setExpiresIn(String.valueOf(resultSet.getString(8)))

          oauthToken.setRedirectURL(resultSet.getString(9))

          return oauthToken
        }
      }
    } catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_DB_ERROR, e)
    }
    finally {
      if (resultSet != null) resultSet.close()

      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
    null
  }

  override def saveToken(user: UserSignature, token: Token): Unit = {
    var connection: Connection = null

    var statement: PreparedStatement = null

    try {
      token match {
        case oauthToken: OAuthToken =>
          oauthToken.setUserMail(user.getEmail)

          this.deleteToken(oauthToken)

          Class.forName(Constants.JDBC_DRIVER_NAME)

          connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)

          statement = connection.prepareStatement("insert into " + this.tableName + "(id,user_mail,client_id,client_secret,refresh_token,access_token,grant_token,expiry_time,redirect_url) values(?,?,?,?,?,?,?,?,?)")

          statement.setString(1, oauthToken.getId)

          statement.setString(2, user.getEmail)

          statement.setString(3, oauthToken.getClientId)

          statement.setString(4, oauthToken.getClientSecret)

          statement.setString(5, oauthToken.getRefreshToken)

          statement.setString(6, oauthToken.getAccessToken)

          statement.setString(7, oauthToken.getGrantToken)

          statement.setString(8, oauthToken.getExpiresIn)

          statement.setString(9, oauthToken.getRedirectURL)

          statement.executeUpdate()
        case _ =>
      }
    }
    catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.SAVE_TOKEN_DB_ERROR, e)
    }
    finally {
      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
  }

  override def deleteToken(token: Token): Unit = {
    var connection: Connection = null

    var statement: PreparedStatement = null

    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)

      connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)
      token match {
        case token1: OAuthToken =>

          val query = constructDBQuery(token1.getUserMail, token1, isDelete = true)

          statement = connection.prepareStatement(query)

          statement.executeUpdate()
        case _ =>
      }
    }
    catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKEN_DB_ERROR, e)
    }
    finally {
      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
  }


  @throws[SDKException]
  override def getTokens: ArrayBuffer[OAuthToken] = {
    var statement: Statement = null
    var query: String = null
    var result: ResultSet = null
    val connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)

    val tokens = new ArrayBuffer[OAuthToken]
    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)
      statement = connection.createStatement
      query = "select * from " + this.tableName + ";"
      result = statement.executeQuery(query)
      while ( {
        result.next
      }) {
        val oauthToken = new OAuthToken.Builder().clientID(result.getString(3)).clientSecret(result.getString(4)).grantToken(result.getString(7)).refreshToken(result.getString(5)).build()
        oauthToken.setId(result.getString(1))
        oauthToken.setUserMail(result.getString(2))
        oauthToken.setAccessToken(result.getString(6))
        oauthToken.setExpiresIn(String.valueOf(result.getString(8)))
        oauthToken.setRedirectURL(result.getString(9))
        tokens.addOne(oauthToken)
      }
    } catch {
      case ex: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKENS_DB_ERROR, ex)
    }
    finally {
      if (result != null) {
        result.close()
      }
      if (statement != null) {
        statement.close()
      }
      if (connection != null) {
        connection.close()
      }
    }
    tokens
  }

  @throws[SDKException]
  override def deleteTokens(): Unit = {
    val connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)
    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)
      val query = "delete from " + this.tableName + ";"
      val statement = connection.prepareStatement(query)
      try statement.executeUpdate
      finally if (statement != null) statement.close()
    }
    catch {
      case ex: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKENS_DB_ERROR, ex)
    }
    finally if (connection != null) connection.close()
  }


  private def constructDBQuery(email: String, token: OAuthToken, isDelete: Boolean): String = {
    if (email == null) throw new SDKException(Constants.USER_MAIL_NULL_ERROR, Constants.USER_MAIL_NULL_ERROR_MESSAGE)
    var query: String = if (isDelete) "delete from " else "select * from "
    query = query.concat(this.tableName + " where user_mail='" + email + "' and client_id='" + token.getClientId + "' and ")

    if (token.getGrantToken != null) query += "grant_token='" + token.getGrantToken + "'"
    else query += "refresh_token='" + token.getRefreshToken + "'"

    query
  }

  override def getTokenById(id: String, token: Token): Token = {
    var connection: Connection = null

    var statement: Statement = null

    var oauthToken: OAuthToken = null

    var resultSet: ResultSet = null

    try {
      oauthToken = token.asInstanceOf[OAuthToken]

      Class.forName(Constants.JDBC_DRIVER_NAME)

      connection = DriverManager.getConnection(this.connectionString, this.userName, this.password)

      if (token.isInstanceOf[OAuthToken]) {

        statement = connection.createStatement()

        val query = "select * from " + this.tableName + " where id='" + id + "'"

        resultSet = statement.executeQuery(query)

        while (resultSet.next()) {
          oauthToken.setId(resultSet.getString(1))

          oauthToken.setUserMail(resultSet.getString(2))

          oauthToken.setClientId(resultSet.getString(3))

          oauthToken.setClientSecret(resultSet.getString(4))

          oauthToken.setRefreshToken(resultSet.getString(5))

          oauthToken.setAccessToken(resultSet.getString(6))

          oauthToken.setGrantToken(resultSet.getString(7))

          oauthToken.setExpiresIn(String.valueOf(resultSet.getString(8)))

          oauthToken.setRedirectURL(resultSet.getString(9))

          return oauthToken
        }
      }
    } catch {
      case e: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_DB_ERROR, e)
    }
    finally {
      if (resultSet != null) resultSet.close()

      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
    null
  }
}