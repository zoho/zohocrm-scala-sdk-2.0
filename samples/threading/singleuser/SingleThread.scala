package com.zoho.crm.sample.threading.singleuser

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.Token
import com.zoho.api.authenticator.store.{DBStore, FileStore, TokenStore}
import com.zoho.crm.api.Initializer
import com.zoho.crm.api.RequestProxy
import com.zoho.crm.api.SDKConfig
import com.zoho.crm.api.UserSignature
import com.zoho.crm.api.dc.USDataCenter
import com.zoho.crm.api.dc.DataCenter.Environment
import com.zoho.crm.api.exception.SDKException
import com.zoho.api.logger.Logger
import com.zoho.crm.api.record.RecordOperations
import com.zoho.crm.api.util.APIResponse


object SingleThread {
  @throws[SDKException]
  def main(args: Array[String]): Unit = {
    val loggerInstance = new Logger.Builder()
      .level(Logger.Levels.ALL)
      .filePath("./sdk.log")
      .build
    val env = USDataCenter.PRODUCTION
    val user = new UserSignature("abc.k@zoho.com")
    val tokenstore = new FileStore("./java_sdk_token.txt")
    val token1 = new OAuthToken.Builder()
      .clientID("1000.xxxxxx")
      .clientSecret("xxxxxx")
      .refreshToken("1000.xxxxxx")
      .refreshToken("https://www.zoho.com")
      .build()
    val resourcePath = "./"
    val userProxy = new RequestProxy.Builder()
      .host("proxyHost")
      .port(80)
      .user("proxyUser")
      .password("password")
      .userDomain("userDomain")
      .build()
    val sdkConfig = new SDKConfig.Builder()
      .autoRefreshFields(false)
      .pickListValidation(true)
      .build
    new Initializer.Builder()
      .user(user)
      .environment(env)
      .token(token1)
      .store(tokenstore)
      .SDKConfig(sdkConfig)
      .resourcePath(resourcePath)
      .logger(loggerInstance)
      .initialize()
    val stsu = new SingleThread("Leads")
    stsu.start()
  }
}

class SingleThread(var moduleAPIName: String) extends Thread {
  override def run(): Unit = {
    try {
      println(Initializer.getInitializer.getUser.getEmail)
      val cro = new RecordOperations
      @SuppressWarnings(Array("rawtypes")) val getResponse = cro.getRecords(this.moduleAPIName, None, None)
      println(getResponse.get.getObject)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
