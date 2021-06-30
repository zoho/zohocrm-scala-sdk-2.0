package com.zoho.crm.api.util

import java.io.UnsupportedEncodingException
import java.util.Base64
import java.util.logging.Logger
import java.util.regex.Pattern
import java.util.stream.{Collectors, IntStream}

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.json.JSONObject
import com.zoho.crm.api.Initializer
import com.zoho.crm.api.exception.SDKException

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object Converter {
}

/**
 * This abstract class is to construct API request and response.
 * @param commonAPIHandler A CommonAPIHandler class instance.
 */
abstract class Converter(protected var commonAPIHandler: CommonAPIHandler=null) {

  /**
   * This abstract method is to process the API response.
   * @param response A Object containing the API response contents or response.
   * @param pack A String containing the expected method return type.
   * @throws Exception Exception
   * @return A Object representing the class instance.
   */
  @throws[Exception]
  def getResponse(response: Any, pack: String): Any

  /**
   * This abstract method is to construct the API request.
   * @param requestObject An Object containing the POJO class instance.
   * @param pack A String containing the expected method return type.
   * @param instanceNumber An Integer containing the POJO class instance list number.
   * @return A Object representing the API request body object.
   * @throws Exception Exception
   */
  @throws[Exception]
  def formRequest(requestObject: Any, pack: String, instanceNumber: Integer,memberDetails:JSONObject): Any

  /**
   * This abstract method is to construct the API request body.
   * @param requestBase A HttpEntityEnclosingRequestBase class instance.
   * @param requestObject A Object containing the API request body object.
   * @throws Exception Exception
   */
  @throws[Exception]
  def appendToRequest(requestBase: HttpEntityEnclosingRequestBase, requestObject: Any): Unit

  /**
   * This abstract method is to process the API response.
   * @param response A Object containing the HttpResponse class instance.
   * @param pack A String containing the expected method return type.
   * @return A Object representing the class instance.
   * @throws Exception Exception
   */
  @throws[Exception]
  def getWrappedResponse(response: Any, pack: String): Option[_]

  /**
   * This method is to validate if the input values satisfy the constraints for the respective fields.
   * @param className A String containing the class name.
   * @param memberName A String containing the member name.
   * @param keyDetails A JSONObject containing the key JSON details.
   * @param value An Object containing the key value.
   * @param uniqueValuesHashMap A HashMap[String,ArrayList[Object] containing the value of constructed object's unique fields.
   * @param instanceNumber An Integer containing the class instance list number.
   * @return SDKException Exception
   */
  def valueChecker(className: String, memberName: String, keyDetails: JSONObject, value: Any, uniqueValuesHashMap: mutable.HashMap[String,ArrayBuffer[Any]], instanceNumber: Int): Boolean = {
    var instanceNumber1 = instanceNumber
    val detailsJO = new JSONObject

    val name = keyDetails.getString(Constants.NAME)

    var `type` = keyDetails.getString(Constants.TYPE)

    var value1:Any = value

    var varType:String = ""

    var check:Boolean = true

    if (value1 != null && value1!=None) {
      varType = value1.getClass.getCanonicalName
      check = varType.equalsIgnoreCase(`type`)
    }
    value1 match {
      case _: ArrayBuffer[Any] =>
        var expectedListType = true
        if (keyDetails.has(Constants.STRUCTURE_NAME)) {
          val structureName = keyDetails.getString(Constants.STRUCTURE_NAME)
          var index = 0
          val listValue = value1.asInstanceOf[ArrayBuffer[_]]
          breakable {
            for ( data <- listValue ) {
              val className1 = data.getClass.getCanonicalName
              if (!className1.equalsIgnoreCase(structureName)) {
                instanceNumber1 = index
                `type` = Constants.LIST_NAMESPACE + "(" + structureName + ")"
                varType = Constants.LIST_NAMESPACE + "(" + className + ")"
                expectedListType = false
                check = false
                break
              }
              index += 1
            }
          }
        }
        if (expectedListType) check = if (varType.equalsIgnoreCase(`type`)) true
        else varType.equalsIgnoreCase(Constants.LISTBUFFER_NAMESPACE)
      case _: mutable.HashMap[Any, Any] =>
        check = if (varType.equalsIgnoreCase(`type`)) true

        else varType.equalsIgnoreCase(Constants.SCALAMAP_NAMESPACE)
      case _ =>
    }
    if (!check && !`type`.equalsIgnoreCase(Constants.OBJECT_NAMESPACE)) {
      detailsJO.put(Constants.FIELD, memberName)
      detailsJO.put(Constants.CLASS, className)
      detailsJO.put(Constants.INDEX, instanceNumber1)
      detailsJO.put(Constants.EXPECTED_TYPE, `type`)
      detailsJO.put(Constants.GIVEN_TYPE, varType)
      throw new SDKException(Constants.TYPE_ERROR, detailsJO)
    }

    if (keyDetails.has(Constants.VALUES) && (!keyDetails.has(Constants.PICKLIST) || (keyDetails.getBoolean(Constants.PICKLIST) && Initializer.getInitializer.getSDKConfig.getPickListValidation))) {
      val valuesJA = keyDetails.getJSONArray(Constants.VALUES)
      value1 match {
        case choice: Choice[_] =>
          value1 = choice.getValue
        case _ =>
      }

      val pickListValue = new ArrayBuffer[Any]()
      valuesJA.forEach(value=>{
        pickListValue.addOne(value)
      })
      if (!pickListValue.contains(value1)) {
        detailsJO.put(Constants.FIELD, memberName)
        detailsJO.put(Constants.CLASS, className)
        detailsJO.put(Constants.GIVEN_VALUE, value1)
        detailsJO.put(Constants.INDEX, instanceNumber1)
        detailsJO.put(Constants.ACCEPTED_VALUES, valuesJA)
        throw new SDKException(Constants.UNACCEPTED_VALUES_ERROR, detailsJO)
      }
    }

    if (keyDetails.has(Constants.UNIQUE)) {
      var valuesArray: ArrayBuffer[Any] = uniqueValuesHashMap.get(name).orNull

      if (valuesArray != null && valuesArray.contains(value1)) {
        detailsJO.put(Constants.FIELD, memberName)

        detailsJO.put(Constants.CLASS, className)

        detailsJO.put(Constants.FIRST_INDEX, valuesArray.indexOf(value1)+1)

        detailsJO.put(Constants.NEXT_INDEX, instanceNumber1)

        throw new SDKException(Constants.UNIQUE_KEY_ERROR, detailsJO)
      }
      else {
        if (valuesArray == null) valuesArray = new ArrayBuffer[Any]

        valuesArray.addOne(value1)

        uniqueValuesHashMap(name)=valuesArray
      }
    }

    if (keyDetails.has(Constants.MIN_LENGTH) && keyDetails.has(Constants.MAX_LENGTH)) {

      var count = value1.toString.length
      value1 match {
        case value2: ArrayBuffer[_] =>
          count = value2.size
        case _ =>
      }
      if (keyDetails.has(Constants.MAX_LENGTH) && count > keyDetails.getInt(Constants.MAX_LENGTH)) {
        detailsJO.put(Constants.FIELD, memberName)

        detailsJO.put(Constants.CLASS, className)

        detailsJO.put(Constants.GIVEN_LENGTH, count)

        detailsJO.put(Constants.MAXIMUM_LENGTH, keyDetails.getInt(Constants.MAX_LENGTH))

        throw new SDKException(Constants.MAXIMUM_LENGTH_ERROR, detailsJO)

      }

      if (value1.toString.length < keyDetails.getInt(Constants.MIN_LENGTH)) {
        detailsJO.put(Constants.FIELD, memberName)

        detailsJO.put(Constants.CLASS, className)

        detailsJO.put(Constants.GIVEN_LENGTH, count)

        detailsJO.put(Constants.MINIMUM_LENGTH, keyDetails.getInt(Constants.MIN_LENGTH))

        throw new SDKException(Constants.MINIMUM_LENGTH_ERROR, detailsJO)

      }
    }
    if (keyDetails.has(Constants.REGEX)) if (!Pattern.matches(keyDetails.getString(Constants.REGEX), String.valueOf(value1))) {
      detailsJO.put(Constants.FIELD, memberName)

      detailsJO.put(Constants.CLASS, className)

      detailsJO.put(Constants.INSTANCE_NUMBER, instanceNumber1)

      throw new SDKException(Constants.REGEX_MISMATCH_ERROR, detailsJO)

    }

    true
  }

  /**
   * This method is to get the module field JSON details file path.
   * @return A String representing the module field JSON details file path.
   * @throws UnsupportedEncodingException Exception
   */
  def getEncodedFileName: String ={

    var fileName = Initializer.getInitializer.getUser.getEmail

    fileName = fileName.substring(0,fileName.indexOf("@")) + Initializer.getInitializer.getEnvironment.getUrl

    val input = fileName.getBytes("UTF-8")

    val str = Base64.getEncoder.encodeToString(input)

    str + ".json"
  }

}
