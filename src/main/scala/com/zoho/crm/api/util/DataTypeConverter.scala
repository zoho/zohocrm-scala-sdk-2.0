package com.zoho.crm.api.util

import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util

import org.json.{JSONArray, JSONObject}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/**
 * This object converts JSON value to the expected data type.
 */
object DataTypeConverter {
  private val PRE_CONVERTER_MAP = new (util.HashMap[String, PreConverter[_]])

  private val POST_CONVERTER_MAP = new (util.HashMap[String, PostConverter[_]])

  /**
   * This method is to initialize the PreConverter and PostConverter lambda functions.
   */
  private def init(): Unit = synchronized {
      if (!PRE_CONVERTER_MAP.isEmpty && !POST_CONVERTER_MAP.isEmpty) return

      val dateTimePreConverter = new PreConverter[LocalDate] {
        override def convert(obj: Any): LocalDate = {
          val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

          LocalDate.parse(obj.toString, formatter)
        }
      }

      val dateTimePostConverter = new PostConverter[LocalDate] {
        override def convert(obj: LocalDate): Any = {
          val date = obj.asInstanceOf[LocalDate]

          date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
      }

      val offsetDatePreConverter = new PreConverter[OffsetDateTime] {
        override def convert(obj: Any): OffsetDateTime = {
          OffsetDateTime.ofInstant(Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(obj.toString)), ZoneId.systemDefault)
        }
      }

      val offsetDatePostConverter = new PostConverter[OffsetDateTime] {
        override def convert(dateTime: OffsetDateTime): Any = dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
      }

      val stringPreConverter = new PreConverter[String] {
        override def convert(obj: Any): String = obj.toString
      }

      val stringPostConverter = new PostConverter[String] {
        override def convert(r: String): Any = r
      }

      val longPreConverter = new PreConverter[Any] {
        override def convert(obj: Any): Any = {
          if (obj.toString.equalsIgnoreCase("null")) return null
          obj.toString.toLong
        }
      }

      val longPostConverter = new PostConverter[Any] {
        override def convert(r: Any): Any = r
      }

      val integerPreConverter = new PreConverter[Int] {
        override def convert(obj: Any): Int = obj.toString.toInt
      }

      val integerPostConverter = new PostConverter[Int] {
        override def convert(r: Int): Any = r
      }

      val booleanPreConverter = new PreConverter[Boolean] {
        override def convert(obj: Any): Boolean = obj.toString.toBoolean
      }

      val booleanPostConverter: PostConverter[Boolean] = (r: Boolean) => r

      val doublePreConverter = new PreConverter[Double] {
        override def convert(obj: Any): Double = obj.toString.toDouble
      }

      val doublePostConverter = new PostConverter[Double] {
        override def convert(r: Double): Any = r
      }

      val objPreConverter = new PreConverter[Any] {
        override def convert(obj: Any): Any = preConvertObjectData(obj)
      }

      val objPostConverter = new PostConverter[Any] {
        override def convert(obj: Any): Any = postConvertObjectData(obj)

      }

      addToHashMap(classOf[LocalDate].getName, dateTimePreConverter, dateTimePostConverter)

      addToHashMap(classOf[OffsetDateTime].getName, offsetDatePreConverter, offsetDatePostConverter)

      addToHashMap(classOf[String].getName, stringPreConverter, stringPostConverter)

      addToHashMap(classOf[java.lang.Long].getName, longPreConverter, longPostConverter)

      addToHashMap(classOf[java.lang.Integer].getName, integerPreConverter, integerPostConverter)

      addToHashMap(classOf[Integer].getName, integerPreConverter, integerPostConverter)

      addToHashMap(classOf[java.lang.Boolean].getName, booleanPreConverter, booleanPostConverter)

      addToHashMap(classOf[java.lang.Double].getName, doublePreConverter, doublePostConverter)

      addToHashMap(classOf[java.lang.Object].getName, objPreConverter, objPostConverter)


  }

  /**
   * This method is to add PreConverter and PostConverter instance.
   * @param name A String containing the data type class name.
   * @param preConverter A PreConverter class.
   * @param postConverter A PostConverter class.
   * @tparam R A R containing the specified data type.
   */
  private def addToHashMap[R](name: String, preConverter: PreConverter[R], postConverter: PostConverter[R]): Unit
  = {
    PRE_CONVERTER_MAP.put(name, preConverter)
    POST_CONVERTER_MAP.put(name, postConverter)
  }

  /**
   * This method is to convert JSON value to expected data value.
   * @param obj A Object containing the JSON value.
   * @param `type` A String containing the expected method return type.
   * @tparam R A R containing the specified data type.
   * @throws Exception Exception
   * @return A R containing the expected data value.
   */
  @throws[Exception]
  def preConvert[R <: Object](obj: Any, `type`: String): Any = {
    init()

    PRE_CONVERTER_MAP.get(`type`).convert(obj)
  }

  /**
   * This method is to convert Java data to JSON data value.
   * @param obj A R containing the Java data value.
   * @param `type` A String containing the expected method return type.
   * @tparam R A R containing the specified data type.
   * @throws Exception Exception
   * @return A Object containing the expected data value.
   */
  @throws[Exception]
  def postConvert[R](obj: R, `type`: String): Any = {
    init()

     POST_CONVERTER_MAP.get(`type`).asInstanceOf[PostConverter[R]].convert(obj)
  }


  /**
   * This interface converts JSON value to Java data.
   * @tparam R A R containing the specified data type.
   */
  private abstract class PreConverter[R] {
    @throws[Exception]
    def convert(obj: Any): R
  }

  /**
   * This interface converts Java data to a JSON value.
   * @tparam R A R containing the specified data type.
   */
  private abstract class PostConverter[R] {
    @throws[Exception]
    def convert(r: R): Any
  }


  @throws[Exception]
  private def preConvertObjectData(obj: Any): Any = {
    if (obj == null) return null
    obj match {
      case jsonArray: JSONArray =>
        val values = new ArrayBuffer[Any]()

        if (jsonArray.length > 0) {

          for ( responseIndex <- 0 until jsonArray.length ) {
            values.addOne(preConvertObjectData(jsonArray.get(responseIndex)))
          }
        }
        values
      case jsonObject: JSONObject =>
        val mapInstance = new mutable.HashMap[Any, Any]
        if (jsonObject.length > 0) {

          jsonObject.keySet().forEach(memberName => {
            val jsonValue = jsonObject.get(memberName)
            mapInstance.put(memberName, preConvertObjectData(jsonValue))
          })
        }
        mapInstance
      case _ => if (obj.getClass.getName.equalsIgnoreCase("Object")) obj
      else preConvert(obj, obj.getClass.getName)
    }
  }

  @SuppressWarnings(Array("rawtypes"))
  @throws[Exception]
  private def postConvertObjectData(obj: Any): Any = {
    if (obj == null) return JSONObject.NULL
    obj match {
      case value1: ArrayBuffer[_] =>
        val list = new JSONArray
        for ( value <- value1 ) {
          list.put(postConvertObjectData(value))
        }
        list
      case _: Map[String, _] =>
        val value = new JSONObject
        val requestObject = obj.asInstanceOf[mutable.HashMap[String, _]]
        if (requestObject.nonEmpty) {
          for ( key <- requestObject.keySet ) {
            val keyValue = requestObject.get(key)
            value.put(key.asInstanceOf[String], postConvertObjectData(keyValue))
          }
        }
        value
      case _ => if (obj.getClass.getName.equalsIgnoreCase("Object")) obj
      else postConvert(obj, obj.getClass.getName)
    }
  }

}
