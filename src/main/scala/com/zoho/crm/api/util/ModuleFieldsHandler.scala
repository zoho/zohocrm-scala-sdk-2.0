package com.zoho.crm.api.util

import java.io.File

import com.zoho.api.logger.SDKLogger
import java.util.logging.{Level, Logger}
import com.zoho.crm.api.exception.SDKException

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.json.JSONObject
import java.io.FileWriter

import com.zoho.crm.api.Initializer

object ModuleFieldsHandler {
  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  private def getDirectory = Initializer.getInitializer.getResourcePath + File.separator + Constants.FIELD_DETAILS_DIRECTORY

  @throws[SDKException]
  def deleteFieldsFile(): Unit = synchronized {
    try {
      val converterInstance = new Converter() {
        /**
         * This abstract method is to process the API response.
         *
         * @param response A Object containing the API response contents or response.
         * @param pack     A String containing the expected method return type.
         * @throws Exception Exception
         * @return A Object representing the class instance.
         */
        override def getResponse(response: Any, pack: String): Any = None

        /**
         * This abstract method is to construct the API request.
         *
         * @param requestObject  An Object containing the POJO class instance.
         * @param pack           A String containing the expected method return type.
         * @param instanceNumber An Integer containing the POJO class instance list number.
         * @return A Object representing the API request body object.
         * @throws Exception Exception
         */
        override def formRequest(requestObject: Any, pack: String, instanceNumber: Integer, memberDetails: JSONObject): Any = None

        /**
         * This abstract method is to construct the API request body.
         *
         * @param requestBase   A HttpEntityEnclosingRequestBase class instance.
         * @param requestObject A Object containing the API request body object.
         * @throws Exception Exception
         */
        override def appendToRequest(requestBase: HttpEntityEnclosingRequestBase, requestObject: Any): Unit = None

        /**
         * This abstract method is to process the API response.
         *
         * @param response A Object containing the HttpResponse class instance.
         * @param pack     A String containing the expected method return type.
         * @return A Object representing the class instance.
         * @throws Exception Exception
         */
        override def getWrappedResponse(response: Any, pack: String): Option[_] = None
      }
      val recordFieldDetailsPath = getDirectory + File.separator + converterInstance.getEncodedFileName
      val recordFieldDetails = new File(recordFieldDetailsPath)
      if (recordFieldDetails.exists) recordFieldDetails.delete
    } catch {
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.DELETE_FIELD_FILE_ERROR, exception)
        throw exception
    }
  }

  @throws[SDKException]
  def deleteAllFieldFiles(): Unit = synchronized {
    try {
      val recordFieldDetailsDirectory = new File(getDirectory)
      if (recordFieldDetailsDirectory.exists) {
        val files = recordFieldDetailsDirectory.listFiles
        if (files != null) for (file <- files) {
          if (file.getName.endsWith(Constants.JSON_FILE_EXTENSION)) file.delete
        }
      }
    } catch {
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.DELETE_FIELD_FILES_ERROR, exception)
        throw exception
    }
  }

  @throws[SDKException]
  def deleteFields(module: String): Unit = {
    try {
      val converterInstance = new Converter() {
        /**
         * This abstract method is to process the API response.
         *
         * @param response A Object containing the API response contents or response.
         * @param pack     A String containing the expected method return type.
         * @throws Exception Exception
         * @return A Object representing the class instance.
         */
        override def getResponse(response: Any, pack: String): Any = None

        /**
         * This abstract method is to construct the API request.
         *
         * @param requestObject  An Object containing the POJO class instance.
         * @param pack           A String containing the expected method return type.
         * @param instanceNumber An Integer containing the POJO class instance list number.
         * @return A Object representing the API request body object.
         * @throws Exception Exception
         */
        override def formRequest(requestObject: Any, pack: String, instanceNumber: Integer, memberDetails: JSONObject): Any = None

        /**
         * This abstract method is to construct the API request body.
         *
         * @param requestBase   A HttpEntityEnclosingRequestBase class instance.
         * @param requestObject A Object containing the API request body object.
         * @throws Exception Exception
         */
        override def appendToRequest(requestBase: HttpEntityEnclosingRequestBase, requestObject: Any): Unit = None

        /**
         * This abstract method is to process the API response.
         *
         * @param response A Object containing the HttpResponse class instance.
         * @param pack     A String containing the expected method return type.
         * @return A Object representing the class instance.
         * @throws Exception Exception
         */
        override def getWrappedResponse(response: Any, pack: String): Option[_] = None
      }
      val recordFieldDetailsPath = getDirectory + File.separator + converterInstance.getEncodedFileName
      val recordFieldDetails = new File(recordFieldDetailsPath)
      if (recordFieldDetails.exists) {
        val recordFieldDetailsJson = Initializer.getJSON(recordFieldDetailsPath)
        if (recordFieldDetailsJson.has(module.toLowerCase)) {
          Utility.deleteFields(recordFieldDetailsJson, module)
          val file = new FileWriter(recordFieldDetailsPath)
          file.flush()
          file.write(recordFieldDetailsJson.toString)
          file.flush()
          file.close()
        }
      }
    } catch {
      case e: Exception =>
        val exception = new SDKException(e)
        throw exception
    }
  }

  /**
   * The method to force-refresh fields of a module.
   *
   * @param module A string representing the module.
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def refreshFields(module: String): Unit = synchronized {
    try {
      deleteFields(module)
      Utility.getFields(module, null)
    } catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.REFRESH_SINGLE_MODULE_FIELDS_ERROR.concat(module), e)
        throw e
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.REFRESH_SINGLE_MODULE_FIELDS_ERROR + module, exception)
        throw exception
    }
  }

  /**
   * The method to force-refresh fields of all the available modules.
   *
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def refreshAllModules(): Unit = synchronized {
    try Utility.refreshModules()
    catch {
      case e: SDKException =>
        LOGGER.log(Level.SEVERE, Constants.REFRESH_ALL_MODULE_FIELDS_ERROR, e)
        throw e
      case e: Exception =>
        val exception = new SDKException(e)
        LOGGER.log(Level.SEVERE, Constants.REFRESH_ALL_MODULE_FIELDS_ERROR, exception)
        throw exception
    }
  }
}
