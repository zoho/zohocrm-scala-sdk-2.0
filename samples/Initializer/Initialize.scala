package com.zoho.crm.sample.Initializer

import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.util.logging.Level

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.store.DBStore
import com.zoho.api.logger.Logger
import com.zoho.crm.api.Initializer.LOGGER
import com.zoho.crm.api.dc.USDataCenter
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.{Initializer, SDKConfig, UserSignature}

object Initialize {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
   initialize()
  }

  @throws[Exception]
  def initialize(): Unit = {
    val loggerInstance = new Logger.Builder()
      .level(Logger.Levels.ALL)
      .filePath("./sdk.log").build

    val user1 = new UserSignature("abc.a@zoho.com")
    val env = USDataCenter.PRODUCTION
    val token1 = new OAuthToken.Builder()
            .clientID("clientID")
            .clientSecret("clientSecret")
            .refreshToken("refreshToken")
      .build()
    val tokenstore = new DBStore.Builder().build

    val autoRefreshFields = true
    val resourcePath = "./"
    var config: SDKConfig = new SDKConfig.Builder().pickListValidation(false).autoRefreshFields(false).build

    new Initializer.Builder()
      .user(user1)
      .environment(env)
      .token(token1)
      //      .store(tokenstore)
      //      .SDKConfig(config)
      //      .resourcePath(resourcePath)
      //      .logger(loggerInstance)
      .initialize()
    //		token.remove()
  }
}
