package com.zoho.crm.sample.threading.singleuser

import com.zoho.api.authenticator.OAuthToken
import com.zoho.api.authenticator.store.FileStore
import com.zoho.crm.api.Initializer
import com.zoho.crm.api.SDKConfig
import com.zoho.crm.api.UserSignature
import com.zoho.crm.api.dc.USDataCenter
import com.zoho.api.logger.Logger
import com.zoho.crm.api.record.RecordOperations

object MultiThread {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val loggerInstance = new Logger.Builder()
      .level(Logger.Levels.ALL)
      .filePath("./sdk.log")
      .build
    val env = USDataCenter.PRODUCTION
    val user1 = new UserSignature("abc.k@zoho.com")
    val tokenstore = new FileStore("./java_sdk_token.txt")
    val token1 = new OAuthToken.Builder()
      .clientID("1000.xxxxxx")
      .clientSecret("xxxxxx")
      .refreshToken("1000.xxxxxx")
      .redirectURL("https://www.zoho.com")
      .build()
    val resourcePath = "./"
    val sdkConfig = new SDKConfig.Builder()
      .autoRefreshFields(false)
      .pickListValidation(true)
      .build
    new Initializer.Builder()
      .user(user1)
      .environment(env)
      .token(token1)
      .store(tokenstore)
      .SDKConfig(sdkConfig)
      .resourcePath(resourcePath)
      .logger(loggerInstance)
      .initialize()
    var mtsu = new MultiThread("Deals")
    mtsu.start()
    mtsu = new MultiThread("Leads")
    mtsu.start()
  }
}

class MultiThread(var moduleAPIName: String) extends Thread {
  override def run(): Unit = {
    try {
      val cro = new RecordOperations
      @SuppressWarnings(Array("rawtypes")) val getResponse = cro.getRecords(this.moduleAPIName, None, None)
      println(getResponse.get.getObject)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}