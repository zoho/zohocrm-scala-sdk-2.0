package com.zoho.api.authenticator.store

import com.zoho.api.authenticator.{OAuthToken, Token}
import com.zoho.crm.api.UserSignature
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.ResultSet

import com.zoho.api.authenticator.OAuthToken.TokenType
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable.ArrayBuffer
/**
 * This class stores the user token details to the MySQL DataBase.
 * @constructor Creates a DBStore class instance with the specified parameters.
 * @param host A String containing the DataBase host name.
 * @param databaseName A String containing the DataBase name.
 * @param userName A String containing the DataBase user name.
 * @param password A String containing the DataBase password.
 * @param portNumber A String containing the DataBase port number.
 */
class DBStore (private var host :Option[String]=None , private var databaseName :Option[String]=None, private var userName :Option[String]=None, private var password :Option[String]=None, private var portNumber :Option[String]=None) extends TokenStore {

  host = if(host != None) host else Option(Constants.MYSQL_HOST)

  databaseName = if(databaseName != None) databaseName else Option(Constants.MYSQL_DATABASE_NAME)

  userName = if(userName != None) userName else Option(Constants.MYSQL_USER_NAME)

  password = if(password != None) password else Option("")

  portNumber = if(portNumber != None) portNumber else Option(Constants.MYSQL_PORT_NUMBER)

  var connectionString: String = this.host.get + ":" + this.portNumber.get + "/" + this.databaseName.get + "?allowPublicKeyRetrieval=true&useSSL=false"



  override def getToken(user: UserSignature, token: Token): Token = {
    var connection :Connection = null

    var statement :Statement = null

    var oauthToken:OAuthToken = null

    var resultSet :ResultSet = null

    try {
      oauthToken = token.asInstanceOf[OAuthToken]

      Class.forName(Constants.JDBC_DRIVER_NAME)

      connection = DriverManager.getConnection(this.connectionString, this.userName.get, this.password.get)

      if (token.isInstanceOf[OAuthToken]) {

        statement = connection.createStatement()

        val query = this.constructDBQuery(user.getEmail, oauthToken, isDelete = false)

        resultSet = statement.executeQuery(query)

        while (resultSet.next()) {
          oauthToken.setAccessToken(resultSet.getString(5))

          oauthToken.setExpiresIn(String.valueOf(resultSet.getString(7)))

          oauthToken.setRefreshToken(resultSet.getString(4))

          return oauthToken
        }
      }
    }catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKEN_DB_ERROR,e)
    }
    finally {
      if (resultSet != null) resultSet.close()

      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }

    null
  }

  private def constructDBQuery(email :String, token :OAuthToken, isDelete :Boolean) :String = {

    if (email == null) throw new SDKException(Constants.USER_MAIL_NULL_ERROR, Constants.USER_MAIL_NULL_ERROR_MESSAGE)
    var query :String = if (isDelete) "delete from " else "select * from "
    query = query.concat("oauthtoken where user_mail='" + email + "' and client_id='" + token.getClientID + "' and ")

    if (token.getGrantToken != null) query += "grant_token='" + token.getGrantToken + "'"
    else query += "refresh_token='" + token.getRefreshToken + "'"

    query
  }

  override def saveToken(user: UserSignature, token: Token): Unit = {
    var connection :Connection = null

    var statement :PreparedStatement = null

    try {
      token match {
        case oauthToken: OAuthToken =>

          oauthToken.setUserMail(user.getEmail)

          this.deleteToken(oauthToken)

          Class.forName(Constants.JDBC_DRIVER_NAME)

          connection = DriverManager.getConnection(this.connectionString, this.userName.get, this.password.get)

          statement = connection.prepareStatement("insert into zohooauth.oauthtoken(user_mail,client_id,refresh_token,access_token,grant_token,expiry_time) values(?,?,?,?,?,?)")

          statement.setString(1, user.getEmail)

          statement.setString(2, oauthToken.getClientID)

          statement.setString(3, oauthToken.getRefreshToken)

          statement.setString(4, oauthToken.getAccessToken)

          statement.setString(5, oauthToken.getGrantToken)

          statement.setString(6, oauthToken.getExpiresIn)

          statement.executeUpdate()
        case _ =>
      }
    }
    catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.SAVE_TOKEN_DB_ERROR, e)
    }
    finally {
      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
  }

  override def deleteToken(token: Token): Unit = {
    var connection :Connection = null

    var statement :PreparedStatement = null

    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)

      connection = DriverManager.getConnection(this.connectionString, this.userName.get, this.password.get)
      token match {
        case token1: OAuthToken =>

          val query = constructDBQuery(token1.getUserMail, token1, isDelete = true)

          statement = connection.prepareStatement(query)

          statement.executeUpdate()
        case _ =>
      }
    }
    catch {
      case e : Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.DELETE_TOKEN_DB_ERROR, e)
    }
    finally {
      if (statement != null) statement.close()

      if (connection != null) connection.close()
    }
  }


  @throws[SDKException]
  override def getTokens: ArrayBuffer[OAuthToken] = {
    var statement:Statement = null
    var query:String =null
    var result:ResultSet = null
    val connection = DriverManager.getConnection(connectionString, userName.get, password.get)

    val tokens = new ArrayBuffer[OAuthToken]
    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)
      statement = connection.createStatement
      query = "select * from oauthtoken;"
      result = statement.executeQuery(query)
      while ( {
        result.next
      }) {
        val tokenType = if (result.getString(6) != null && !result.getString(6).equalsIgnoreCase(Constants.NULL_VALUE) && !result.getString(6).isEmpty) TokenType.GRANT
        else TokenType.REFRESH
        val tokenValue = if (tokenType.equals(TokenType.REFRESH)) result.getString(4)
        else result.getString(6)
        val token = new OAuthToken(result.getString(3), null, tokenValue, tokenType)
        token.setId(result.getString(1))
        token.setUserMail(result.getString(2))
        token.setRefreshToken(result.getString(4))
        token.setAccessToken(result.getString(5))
        token.setExpiresIn(String.valueOf(result.getString(7)))
        tokens.addOne(token)
      }
    } catch {
      case ex: Exception =>
        throw new SDKException(Constants.TOKEN_STORE, Constants.GET_TOKENS_DB_ERROR, ex)
    }
    finally {
      if (result!=null){
        result.close()
      }
      if (statement!=null){
        statement.close()
      }
      if(connection!=null){
        connection.close()
      }
    }


    tokens
  }

  @throws[SDKException]
  override def deleteTokens(): Unit = {
    val connection = DriverManager.getConnection(this.connectionString, this.userName.get, this.password.get)


    try {
      Class.forName(Constants.JDBC_DRIVER_NAME)
      val query = "delete from oauthtoken"
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
}