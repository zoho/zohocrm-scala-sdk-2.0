package com.zoho.crm.sample.threading.multiuser

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.Token
import com.zoho.api.authenticator.store.{DBStore, FileStore, TokenStore}
import com.zoho.crm.api.Initializer
import com.zoho.crm.api.RequestProxy
import com.zoho.crm.api.SDKConfig
import com.zoho.crm.api.UserSignature
import com.zoho.crm.api.dc.{DataCenter, USDataCenter}
import com.zoho.crm.api.exception.SDKException
import com.zoho.api.logger.Logger
import com.zoho.crm.api.record.RecordOperations


object MultiThread {
  @throws[SDKException]
  def main(args: Array[String]): Unit = {
    val loggerInstance = new Logger.Builder()
      .level(Logger.Levels.ALL)
      .filePath("./sdk.log")
      .build
    val env = USDataCenter.PRODUCTION
    val user1 = new UserSignature("abc.k@zoho.com")
    val tokenstore = new FileStore("./scala_sdk_token.txt")
    val token1 = new OAuthToken.Builder()
      .clientID("1000.xxxxx")
      .clientSecret("xxxxxx")
      .refreshToken("1000.xx.xx")
      .redirectURL("https://www.zoho.com")
      .build()
    val resourcePath = "./"
    val user1Config = new SDKConfig.Builder()
      .autoRefreshFields(false)
      .pickListValidation(true)
      .build
    new Initializer.Builder()
      .user(user1)
      .environment(env)
      .token(token1)
      .store(tokenstore)
      .SDKConfig(user1Config)
      .resourcePath(resourcePath)
      .logger(loggerInstance)
      .initialize()
    var multiThread = new MultiThread(user1, env, token1, "Students", user1Config, null)
    multiThread.start()
    val environment = USDataCenter.PRODUCTION
    val user2 = new UserSignature("abc@zoho.com")
    val token2 = new OAuthToken.Builder()
      .clientID("1000.xxxxx")
      .clientSecret("xxxxx")
      .refreshToken("1000.xxxx.xxxxx")
      .build()
    val user2Proxy = new RequestProxy.Builder()
      .host("proxyHost")
      .port(80)
      .user("proxyUser")
      .password("password")
      .userDomain("userDomain")
      .build()
    val user2Config = new SDKConfig.Builder()
      .autoRefreshFields(true)
      .pickListValidation(false).build
    multiThread = new MultiThread(user2, environment, token2, "Leads", user2Config, user2Proxy)
    multiThread.start()
  }
}

class MultiThread(var user: UserSignature, var environment: DataCenter.Environment, var token: Token, var moduleAPIName: String, var sdkConfig: SDKConfig, var userProxy: RequestProxy) extends Thread {
  override def run(): Unit = {
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

