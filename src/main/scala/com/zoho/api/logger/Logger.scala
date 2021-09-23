package com.zoho.api.logger

import com.zoho.api.logger.Logger.Levels.Levels

/**
 * This object represents the Logger level and log file path.
 */
object Logger {
  class Builder() {
    private var level: Levels.Levels = _

    private var filePath: String = _

    def level(level: Levels.Levels): Logger.Builder = {
      this.level = level

      this
    }

    def filePath(filePath: String): Logger.Builder = {
      this.filePath = filePath

      this
    }

    /**
     * Creates an Logger class instance with the specified log level and file path.
     *
     * @param level    A enum containing the log level.
     * @param filePath A String containing the log file path.
     * @return A Logger class instance.
     */
    def build = new Logger(level, filePath): Logger
  }

  /**
   * This enum contains logger levels.
   */
  object Levels extends Enumeration {
    type Levels = Value

    val OFF, FINE, FINEST, WARNING, ALL, FINER, CONFIG, INFO, SEVERE, ERROR = Value
  }
}

class Logger protected(levels: Levels, private var filePath: String) {
  private var level: String = levels.toString

  /**
   * This is a getter method to get logger level.
   *
   * @return A String representing the logger level.
   */
  def getLevel: String = this.level

  /**
   * This is a getter method to get log file path.
   *
   * @return A String representing the log file path.
   */
  def getFilePath: String = this.filePath
}
