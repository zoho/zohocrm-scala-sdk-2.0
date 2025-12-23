package com.zoho.crm.sample.threading.multiuser

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.Token
import com.zoho.api.authenticator.store.{DBStore, FileStore, TokenStore}
import com.zoho.crm.api.Initializer
import com.zoho.crm.api.SDKConfig
import com.zoho.crm.api.UserSignature
import com.zoho.crm.api.dc.{DataCenter, USDataCenter}
import com.zoho.crm.api.dc.DataCenter.Environment
import com.zoho.api.logger.Logger
import com.zoho.crm.api.record.RecordOperations
import com.zoho.crm.api.util.APIResponse


object SingleThread {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val loggerInstance = new Logger.Builder()
      .level(Logger.Levels.ALL)
      .filePath("./sdk.log")
      .build
    val env = USDataCenter.PRODUCTION
    val user1 = new UserSignature("abc.k@zoho.com")
    val token1 = new OAuthToken.Builder()
      .clientID("1000.xxxxxx")
      .clientSecret("xxxxxx")
      .refreshToken("1000.")
      .redirectURL("https://www.zoho.com")
      .build()
    val resourcePath = "./"
    val sdkConfig = new SDKConfig.Builder().autoRefreshFields(false).pickListValidation(true).build
    val tokenstore = new FileStore("./scala_sdk_token.txt")

    new Initializer.Builder()
      .user(user1)
      .environment(env)
      .token(token1)
      .store(tokenstore)
      .SDKConfig(sdkConfig)
      .resourcePath(resourcePath)
      .logger(loggerInstance)
      .initialize()
    var singleThread = new SingleThread(user1, env, token1, "Students", sdkConfig)
    singleThread.run()
    val environment = USDataCenter.PRODUCTION
    val user2 = new UserSignature("abc@zoho.com")
    val token2 = new OAuthToken.Builder()
      .clientID("1000.xxxx")
      .clientSecret("xxxxx")
      .refreshToken("1000.xxxxxx")
      .build()
    singleThread = new SingleThread(user2, environment, token2, "Leads", sdkConfig)
    singleThread.run()
  }
}

class SingleThread {
  private[multiuser] var environment:DataCenter.Environment = null
  private[multiuser] var user:UserSignature = null
  private[multiuser] var token:Token = null
  private[multiuser] var moduleAPIName:String = null
  private[multiuser] var sdkConfig:SDKConfig = null

  def this(moduleAPIName: String) {
    this()
    this.moduleAPIName = moduleAPIName
  }

  def this(user: UserSignature, environment: DataCenter.Environment, token: Token, moduleAPIName: String, config: SDKConfig) {
    this()
    this.environment = environment
    this.user = user
    this.token = token
    this.moduleAPIName = moduleAPIName
    this.sdkConfig = config
  }

  def run(): Unit = {
    try {
      new Initializer.Builder()
        .user(user)
        .environment(environment)
        .token(token)
        .SDKConfig(sdkConfig)
        .switchUser()
      println(Initializer.getInitializer.getUser.getEmail)
      val cro = new RecordOperations
      val getResponse = cro.getRecords(this.moduleAPIName, None, None)
      println(getResponse.get.getObject)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
