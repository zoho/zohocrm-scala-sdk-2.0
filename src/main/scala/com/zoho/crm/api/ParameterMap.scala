package com.zoho.crm.api

import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants
import com.zoho.crm.api.util.DataTypeConverter

import scala.collection.mutable


/**
 * This class represents the HTTP parameter name and value.
 */
class ParameterMap {
  private val parameterHashMap:mutable.HashMap[String,String] =mutable.HashMap()

  /**
   * This is a getter method to get parameter map.
   *
   * @return A HashMap&lt;String, String&gt; representing the API request parameters.
   */
  def getParameterMap: mutable.HashMap[String,String] = parameterHashMap

  /**
   * This method to add parameter name and value.
   *
   * @param param A Param&lt;T&gt; class instance.
   * @param value A T containing the parameter value.
   * @throws SDKException Exception
   */
  @throws[SDKException]
  def add[T](param: Param[T], value: T): Unit = {
    if (param == null) throw new SDKException(Constants.PARAMETER_NULL_ERROR, Constants.PARAM_INSTANCE_NULL_ERROR)
    val paramName = param.getName
    if (paramName == null) throw new SDKException(Constants.PARAM_NAME_NULL_ERROR, Constants.PARAM_NAME_NULL_ERROR_MESSAGE)
    if (value == null) throw new SDKException(Constants.PARAMETER_NULL_ERROR, paramName + Constants.NULL_VALUE_ERROR_MESSAGE)
    var paramValue:String = ""
    try paramValue = DataTypeConverter.postConvert(value, value.getClass.getName).toString
    catch {
      case _: Exception =>
        paramValue = value.toString
    }
    if (parameterHashMap.keySet.contains(paramName) && parameterHashMap.get(paramName).isDefined) {
      var existingParamValue:String = parameterHashMap(paramName)
      existingParamValue = existingParamValue + "," + paramValue
      parameterHashMap(paramName)= existingParamValue
    }
    else parameterHashMap(paramName)= paramValue
  }
}