package com.zoho.crm.api.util

import java.io.InputStream

import com.zoho.crm.api.Initializer
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.json.JSONObject

/**
 * This class is to process the download file and stream response.
 *
 * @param commonAPIHandler A CommonAPIHandler class instance.
 */
class Downloader(commonAPIHandler: CommonAPIHandler) extends Converter(commonAPIHandler) {

  /**
   * This abstract method is to construct the API request.
   *
   * @param requestObject  An Object containing the POJO class instance.
   * @param pack           A String containing the expected method return type.
   * @param instanceNumber An Integer containing the POJO class instance list number.
   * @return A Object representing the API request body object.
   * @throws Exception Exception
   */
  override def formRequest(requestObject: Any, pack: String, instanceNumber: Integer, memberDetails: JSONObject): Any = null

  override def appendToRequest(requestBase: HttpEntityEnclosingRequestBase, requestObject: Any): Unit = {
  }

  override def getWrappedResponse(response: Any, pack: String): Option[_] = {
    Option(getResponse(response, pack))
  }

  override def getResponse(response: Any, pack: String): Any = {
    val recordJsonDetails = Initializer.jsonDetails.getJSONObject(pack)

    var instance: Any = null

    if (recordJsonDetails.has(Constants.INTERFACE) && recordJsonDetails.getBoolean(Constants.INTERFACE)) {
      val classes = recordJsonDetails.getJSONArray(Constants.CLASSES)
      classes.forEach(classObject => {
        val className = classObject.toString
        if (className.contains(Constants.FILEBODYWRAPPER)) return getResponse(response, className)
      })
      return instance
    }
    else {
      instance = Class.forName(pack).newInstance
      recordJsonDetails.keySet.forEach(memberName => {
        val memberJsonDetails = recordJsonDetails.getJSONObject(memberName)

        val field = instance.getClass.getDeclaredField(memberName)

        field.setAccessible(true)

        val `type` = memberJsonDetails.get(Constants.TYPE).asInstanceOf[String]

        var instanceValue: Any = None

        if (`type`.equalsIgnoreCase(Constants.STREAM_WRAPPER_CLASS_PATH)) {
          val contentDispositionHeader = response.asInstanceOf[HttpResponse].getFirstHeader(Constants.CONTENT_DISPOSITION)

          var fileName = ""

          if (contentDispositionHeader != null) {
            val contentDisposition = contentDispositionHeader.getValue
            fileName = contentDisposition.split("=")(1)
            if (fileName.contains("''")) fileName = fileName.split("''")(1)
            if (fileName.contains("\"")) fileName = fileName.replace("\"", "")
          }

          val entity = response.asInstanceOf[HttpResponse].getEntity

          val constructor = Class.forName(`type`).getConstructor(classOf[Option[String]], classOf[Option[InputStream]])

          val fileInstance = constructor.newInstance(Option(fileName), Option(entity.getContent))

          instanceValue = fileInstance
        }

        field.set(instance, Option(instanceValue))
      })
    }

    instance
  }
}
