package com.zoho.api.logger

import java.io.{PrintWriter, StringWriter}
import java.util.Date
import java.util.logging.{FileHandler, Level, LogRecord, Logger}

import com.zoho.api.logger.SDKLogger._
import com.zoho.crm.api.exception.SDKException
import com.zoho.crm.api.util.Constants

import scala.collection.mutable

/**
 * This class is to initialize the SDK logger.
 */
object SDKLogger {
  private val LOGGER: Logger = Logger.getLogger(classOf[SDKLogger].getName)

  /**
   * Creates an User SDKLogger instance with the specified Logger class instance.
   *
   * @param logger A Logger class instance.
   */
  def initialize(logger: com.zoho.api.logger.Logger): Unit = {
    new SDKLogger(logger)
  }

  private class Formatter extends java.util.logging.Formatter {
    override def format(record: LogRecord): String = {
      val sb = new mutable.StringBuilder

      sb.append(new Date(record.getMillis)).append(" ").append(record.getLoggerName).append(": ").append(record.getLevel.getLocalizedName).append(" ").append(record.getSourceClassName).append(" ").append(record.getSourceMethodName).append(": ").append(formatMessage(record))

      if (record.getThrown != null) {
        try {
          val sw = new StringWriter

          val pw = new PrintWriter(sw)

          record.getThrown.printStackTrace(pw)

          pw.close()

          sb.append(" ").append(sw.toString)
        }
        catch {
          case ex: Exception => Logger.getGlobal.log(Level.INFO, ex.toString)
        }
      }

      sb.append("\n")

      sb.toString
    }
  }
}

class SDKLogger protected(logger: com.zoho.api.logger.Logger) {
  try {
    if (logger.getFilePath != null && logger.getFilePath != "") {
      val fileHandler: FileHandler = new FileHandler(logger.getFilePath, true)

      LOGGER.addHandler(fileHandler)

      LOGGER.setUseParentHandlers(false)

      fileHandler.setFormatter(new Formatter)
    }

    if (logger.getLevel != null && Constants.LOGGER_LEVELS.contains(logger.getLevel)) {
      LOGGER.setLevel(Constants.LOGGER_LEVELS(logger.getLevel))
    }
  }
  catch {
    case e: Exception => throw new SDKException(Constants.SDK_LOGGER_INITIALIZE, e)
  }
}