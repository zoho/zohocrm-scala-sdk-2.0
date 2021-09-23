package com.zoho.crm.api.util

import java.io.File
import java.io.FileInputStream
import java.io.InputStream

import com.zoho.crm.api.exception.SDKException

/**
 * This class handles the file stream and name.
 */

class StreamWrapper(var name: Option[String] = None, var stream: Option[InputStream] = None) {
  def this(filePath: String) {
    this()

    try {
      val file = new File(filePath)
      this.name = Option(file.getName)
      this.stream = Option(new FileInputStream(file))
    } catch {
      case _: Exception => {
        throw new SDKException(Constants.FILE_ERROR, Constants.FILE_DOES_NOT_EXISTS + " " + filePath)
      }
    }
  }

  /**
   * This is a getter method to get the file name.
   *
   * @return A String representing the file name.
   */
  def getName: Option[String] = name

  /**
   * This is a getter method to get the file input stream.
   *
   * @return A InputStream representing the file input stream.
   */
  def getStream: Option[InputStream] = stream

}

