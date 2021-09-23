package com.zoho.crm.api

/**
 * This class represents the HTTP header name.
 *
 * @tparam A Any datatype
 */
class Header[A] {
  var name: String = _
  var className: String = _

  /**
   * Creates an Header class instance with the specified header name.
   *
   * @param name A String containing the header name.
   */
  def this(name: String, className: String = null) = {
    this()

    this.name = name
    this.className = className
  }

  /**
   * This is a getter method to get header name.
   *
   * @return A String representing the header name.
   */
  def getName: String = this.name

  /**
   * This is a getter method to get header class name.
   *
   * @return A String representing the header class name.
   */
  def getClassName: String = this.className
}
