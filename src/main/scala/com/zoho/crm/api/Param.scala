package com.zoho.crm.api

/**
 * This class represents the HTTP parameter name.
 * @tparam A Any datatype
 */
class Param[A] {
  var name: String = _
  var className : String = _

  /**
   * Creates an Param class instance with the specified parameter name.
   * @param name A String containing the parameter name.
   */
  def this(name: String, className: String = null) = {
    this()

    this.name = name
    this.className = className
  }

  /**
   * This is a getter method to get parameter name.
   * @return A String representing the parameter name.
   */
  def getName :String = this.name

  /**
   * This is a getter method to get parameter class name.
   *
   * @return A String representing the parameter class name.
   */
  def getClassName: String = this.className

}
