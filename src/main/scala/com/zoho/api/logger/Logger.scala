package com.zoho.api.logger

import com.zoho.api.logger.Logger.Levels.Levels

/**
 * This object represents the Logger level and log file path.
 */
object Logger{
  /**
   * This enum contains logger levels.
   */
  object Levels extends Enumeration{
    type Levels=Value

    val OFF, FINE, FINEST, WARNING, ALL, FINER, CONFIG, INFO, SEVERE, ERROR=Value
  }

  /**
   * Creates an Logger class instance with the specified log level and file path.
   * @param level A enum containing the log level.
   * @param filePath A String containing the log file path.
   * @return A Logger class instance.
   */
  def getInstance(level: Levels, filePath: String) : Logger = new Logger(level, filePath)
}

class Logger protected(levels:Levels,private var filePath:String){

  private var level: String=levels.toString

  /**
   * This is a getter method to get logger level.
   * @return A String representing the logger level.
   */
  def getLevel: String = this.level

  /**
   * This is a getter method to get log file path.
   * @return A String representing the log file path.
   */
  def getFilePath: String = this.filePath

}
