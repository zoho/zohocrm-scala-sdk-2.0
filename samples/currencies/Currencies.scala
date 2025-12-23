package com.zoho.crm.sample.currencies

import java.lang.reflect.Field
import java.util

import com.zoho.crm.api.currencies.{APIException, ActionHandler, ActionResponse, ActionWrapper, BaseCurrencyActionHandler, BaseCurrencyActionWrapper, BaseCurrencyWrapper, BodyWrapper, CurrenciesOperations, Currency, Format, ResponseHandler, ResponseWrapper, SuccessResponse}
import com.zoho.crm.api.util.APIResponse
import com.zoho.crm.api.util.Choice
import com.zoho.crm.api.util.Model

import scala.collection.mutable.ArrayBuffer


object Currencies {
  /**
   * <h3> Get Currencies </h3>
   * This method is used to get all the available currencies in your organization.
   *
   * @throws Exception
   */
  @throws[Exception]
  def getCurrencies(): Unit = { //Get instance of CurrenciesOperations Class
    val currenciesOperations = new CurrenciesOperations
    //Call getCurrencies method
    val responseOption = currenciesOperations.getCurrencies
    if (responseOption.isDefined) { //check response
   var response= responseOption.get
    println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      //Check if expected response is received
      if (response.isExpected) { //Get object from response
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) { //Get the received ResponseWrapper instance
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the list of obtained Currency instances
          val currenciesList = responseWrapper.getCurrencies
          
          for (currency <- currenciesList) { //Get the Symbol of each currency
            println("Currency Symbol: " + currency.getSymbol)
            //Get the CreatedTime of each currency
            println("Currency CreatedTime: " + currency.getCreatedTime)
            //Get the currency is IsActive
            println("Currency IsActive: " + currency.getIsActive.toString)
            //Get the ExchangeRate of each currency
            println("Currency ExchangeRate: " + currency.getExchangeRate)
            //Get the format instance of each currency
            val formatOption = currency.getFormat
            //Check if format is not null
            if (formatOption.isDefined) { //Get the DecimalSeparator of the Format
              val format =  formatOption.get
              println("Currency Format DecimalSeparator: " + format.getDecimalSeparator.getValue)
              //Get the ThousandSeparator of the Format
              println("Currency Format ThousandSeparator: " + format.getThousandSeparator.getValue)
              //Get the DecimalPlaces of the Format
              println("Currency Format DecimalPlaces: " + format.getDecimalPlaces.getValue)
            }
            //Get the createdBy User instance of each currency
            val createdByOption = currency.getCreatedBy
            //Check if createdBy is not null
            if (createdByOption.isDefined) { //Get the Name of the createdBy User
              val createdBy = createdByOption.get
              println("Currency CreatedBy User-Name: " + createdBy.getName)
              //Get the ID of the createdBy User
              println("Currency CreatedBy User-ID: " + createdBy.getId)
            }
            //Get the PrefixSymbol of each currency
            println("Currency PrefixSymbol: " + currency.getPrefixSymbol.toString)
            //Get the IsBase of each currency
            println("Currency IsBase: " + currency.getIsBase.toString)
            //Get the ModifiedTime of each currency
            println("Currency ModifiedTime: " + currency.getModifiedTime)
            //Get the Name of each currency
            println("Currency Name: " + currency.getName)
            //Get the modifiedBy User instance of each currency
            val modifiedByOption = currency.getModifiedBy
            //Check if modifiedBy is not null
            if (modifiedByOption.isDefined) { //Get the Name of the modifiedBy User
              val modifiedBy =modifiedByOption.get
              println("Currency ModifiedBy User-Name: " + modifiedBy.getName)
              //Get the ID of the modifiedBy User
              println("Currency ModifiedBy User-ID: " + modifiedBy.getId)
            }
            //Get the Id of each currency
            println("Currency Id: " + currency.getId)
            //Get the IsoCode of each currency
            println("Currency IsoCode: " + currency.getIsoCode)
          }
        }
        else { //Check if the request returned an exception
          if (responseHandler.isInstanceOf[APIException]) { //Get the received APIException instance
            val exception = responseHandler.asInstanceOf[APIException]
            //Get the Status
            println("Status: " + exception.getStatus.getValue)
            //Get the Code
            println("Code: " + exception.getCode.getValue)
            println("Details: ")
            //Get the details map

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            //Get the Message
            println("Message: " + exception.getMessage.getValue)
          }
        }
      }
      else { //If response is not as expected
        //Get model object from response
        val responseObject = response.getModel
        //Get the response object's class
        val clas = responseObject.getClass
        //Get all declared fields of the response class
        val fields = clas.getDeclaredFields
        for (field <- fields) { //Get each value
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Add Currencies </h3>
   * This method is used to add new currencies to your organization.
   *
   * @throws Exception
   */
  @throws[Exception]
  def addCurrencies(): Unit = {
    val currenciesOperations = new CurrenciesOperations
    //Get instance of BodyWrapper Class that will contain the request body
    val bodyWrapper = new BodyWrapper
    //List of Currency instances
    val currencies = new ArrayBuffer[Currency]
    //Get instance of Currency Class
    val currency = new Currency
    //To set the position of the ISO code in the currency.
    //true: Display ISO code before the currency value.
    //false: Display ISO code after the currency value.
    //To set the name of the currency.
    currency.setPrefixSymbol(Option(true))

    currency.setName(Option("Algerian Dinar - DZD"))
    //To set the ISO code of the currency.
    currency.setIsoCode(Option("DZD"))
    //To set the symbol of the currency.
    currency.setSymbol(Option("DA"))
    //To set the rate at which the currency has to be exchanged for home currency.
    currency.setExchangeRate(Option("20.000000000"))
    //To set the status of the currency.
    //true: The currency is active.
    //false: The currency is inactive.
    currency.setIsActive(Option(true))
    val format = new Format
    //It can be a Period or Comma, depending on the currency.
    format.setDecimalSeparator(new Choice[String]("Period"))
    //It can be a Period, Comma, or Space, depending on the currency.
    format.setThousandSeparator(new Choice[String]("Comma"))
    //To set the number of decimal places allowed for the currency. It can be 0, 2, or 3.
    format.setDecimalPlaces(new Choice[String]("2"))
    //To set the format of the currency
    currency.setFormat(Option(format))
    currencies.addOne(currency)
    //Set the list to Currency in BodyWrapper instance
    bodyWrapper.setCurrencies(currencies)
    //Call addCurrencies method that takes BodyWrapper instance as parameter
    val responseOption = currenciesOperations.addCurrencies(bodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) { //Get the received ActionWrapper instance
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          //Get the list of obtained ActionResponse instances
          val actionResponses = actionWrapper.getCurrencies
          
          for (actionResponse <- actionResponses) { //Check if the request is successful
            if (actionResponse.isInstanceOf[SuccessResponse]) { //Get the received SuccessResponse instance
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            else if (actionResponse.isInstanceOf[APIException]) {
              val exception = actionResponse.asInstanceOf[APIException]
              println("Status: " + exception.getStatus.getValue)
              println("Code: " + exception.getCode.getValue)
              println("Details: ")

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Update Currencies </h3>
   * This method is used to update currency details.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateCurrencies(): Unit = {
    val currenciesOperations = new CurrenciesOperations
    val bodyWrapper = new BodyWrapper
    val currencies = new ArrayBuffer[Currency]
    val currency = new Currency
    //		currency.setPrefixSymbol(true)
    //To set currency Id
    currency.setId(Option(3524033000005520008l))
    currency.setExchangeRate(Option("5.0000000"))
    currency.setIsActive(Option(true))
    val format = new Format
    format.setDecimalSeparator(new Choice[String]("Period"))
    format.setThousandSeparator(new Choice[String]("Comma"))
    format.setDecimalPlaces(new Choice[String]("2"))
    currency.setFormat(Option(format))
    //Add Currency instance to the list
    currencies.addOne(currency)
    bodyWrapper.setCurrencies(currencies)
    //Call updateCurrencies method that takes BodyWrapper instance as parameter
    val responseOption = currenciesOperations.updateCurrencies(bodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getCurrencies
          
          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            else if (actionResponse.isInstanceOf[APIException]) {
              val exception = actionResponse.asInstanceOf[APIException]
              println("Status: " + exception.getStatus.getValue)
              println("Code: " + exception.getCode.getValue)
              println("Details: ")

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Enable Multiple Currencies </h3>
   * This method is used to enable multiple currencies for your organization.
   *
   * @throws Exception
   */
  @throws[Exception]
  def enableMultipleCurrencies(): Unit = {
    val currenciesOperations = new CurrenciesOperations
    //Get instance of BaseCurrencyWrapper Class that will contain the request body
    val bodyWrapper = new BaseCurrencyWrapper
    val currency = new Currency
    //To set the position of the ISO code in the base currency.
    currency.setPrefixSymbol(Option(true))
    //To set the name of the base currency.
    currency.setName(Option("Algerian Dinar - DZD"))
    //To set the ISO code of the base currency.
    currency.setIsoCode(Option("DZD"))
    //To set the symbol of the base currency.
    currency.setSymbol(Option("DA"))
    //To set the rate at which the currency has to be exchanged for home base currency.
    currency.setExchangeRate(Option("2.0000000"))
    //To set the status of the base currency.
    currency.setIsActive(Option(true))
    val format = new Format
    //It can be a Period or Comma, depending on the base currency.
    format.setDecimalSeparator(new Choice[String]("Period"))
    //It can be a Period, Comma, or Space, depending on the base currency.
    format.setThousandSeparator(new Choice[String]("Comma"))
    //To set the number of decimal places allowed for the base currency. It can be 0, 2, or 3.
    format.setDecimalPlaces(new Choice[String]("3"))
    //To set the format of the base currency
    currency.setFormat(Option(format))
    //Set the Currency in BodyWrapper instance
    bodyWrapper.setBaseCurrency(Option(currency))
    //Call enableMultipleCurrencies method that takes BodyWrapper instance as parameter
    val responseOption = currenciesOperations.enableMultipleCurrencies(bodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val baseCurrencyActionHandler = response.getObject
        if (baseCurrencyActionHandler.isInstanceOf[BaseCurrencyActionWrapper]) { //Get the received BaseCurrencyActionWrapper instance
          val baseCurrencyActionWrapper = baseCurrencyActionHandler.asInstanceOf[BaseCurrencyActionWrapper]
          //Get the received obtained ActionResponse instances
          val actionResponse = baseCurrencyActionWrapper.getBaseCurrency
          if (actionResponse.isInstanceOf[SuccessResponse]) {
            val successResponse = actionResponse.asInstanceOf[SuccessResponse]
            println("Status: " + successResponse.getStatus.getValue)
            println("Code: " + successResponse.getCode.getValue)
            println("Details: ")

            successResponse.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + successResponse.getMessage.getValue)
          }
          else if (actionResponse.isInstanceOf[APIException]) {
            val exception = actionResponse.asInstanceOf[APIException]
            println("Status: " + exception.getStatus.getValue)
            println("Code: " + exception.getCode.getValue)
            println("Details: ")

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + exception.getMessage.getValue)
          }
        }
        else if (baseCurrencyActionHandler.isInstanceOf[APIException]) {
          val exception = baseCurrencyActionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Update Currency </h3>
   * This method is used to update base currency details.
   *
   * @throws Exception
   */
  @throws[Exception]
  def updateBaseCurrency(): Unit = {
    val currenciesOperations = new CurrenciesOperations
    val bodyWrapper = new BaseCurrencyWrapper
    val currency = new Currency
    currency.setPrefixSymbol(Option(true))
    currency.setSymbol(Option("DA"))
    currency.setExchangeRate(Option("5.0000000"))
    currency.setId(Option(3524033000005520008l))
    currency.setIsActive(Option(true))
    val format = new Format
    format.setDecimalSeparator(new Choice[String]("Period"))
    //It can be a Period, Comma, or Space, depending on the base currency.
    format.setThousandSeparator(new Choice[String]("Comma"))
    format.setDecimalPlaces(new Choice[String]("2"))
    currency.setFormat(Option(format))
    bodyWrapper.setBaseCurrency(Option(currency))
    //Call updateBaseCurrency method that takes BodyWrapper instance as parameter
    val responseOption = currenciesOperations.updateBaseCurrency(bodyWrapper)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val baseCurrencyActionHandler = response.getObject
        if (baseCurrencyActionHandler.isInstanceOf[BaseCurrencyActionWrapper]) {
          val baseCurrencyActionWrapper = baseCurrencyActionHandler.asInstanceOf[BaseCurrencyActionWrapper]
          //Get the ActionResponse instance
          val actionResponse = baseCurrencyActionWrapper.getBaseCurrency
          if (actionResponse.isInstanceOf[SuccessResponse]) {
            val successResponse = actionResponse.asInstanceOf[SuccessResponse]
            println("Status: " + successResponse.getStatus.getValue)
            println("Code: " + successResponse.getCode.getValue)
            println("Details: ")

            successResponse.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + successResponse.getMessage.getValue)
          }
          else if (actionResponse.isInstanceOf[APIException]) {
            val exception = actionResponse.asInstanceOf[APIException]
            println("Status: " + exception.getStatus.getValue)
            println("Code: " + exception.getCode.getValue)
            println("Details: ")

            exception.getDetails.foreach(entry=>{
              println(entry._1 + ": " + entry._2)
            })
            println("Message: " + exception.getMessage.getValue)
          }
        }
        else if (baseCurrencyActionHandler.isInstanceOf[APIException]) {
          val exception = baseCurrencyActionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Get Currency </h3>
   * This method is used to get the details of a specific currency.
   *
   * @param currencyId - Specify the unique ID of the currency.
   * @throws Exception
   */
  @throws[Exception]
  def getCurrency(currencyId: Long): Unit = { //example
    //Long currencyId = 3477061000006011001l
    val currenciesOperations = new CurrenciesOperations
    //Call getCurrency method that takes currencyId as parameter
    val responseOption = currenciesOperations.getCurrency(currencyId)
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (util.Arrays.asList(204, 304).contains(response.getStatusCode)) {
        println(if (response.getStatusCode == 204) "No Content"
        else "Not Modified")
        return
      }
      if (response.isExpected) {
        val responseHandler = response.getObject
        if (responseHandler.isInstanceOf[ResponseWrapper]) {
          val responseWrapper = responseHandler.asInstanceOf[ResponseWrapper]
          //Get the obtained Currency instance
          val currenciesList = responseWrapper.getCurrencies
          
          for (currency <- currenciesList) {
            println("Currency Symbol: " + currency.getSymbol)
            println("Currency CreatedTime: " + currency.getCreatedTime)
            println("Currency IsActive: " + currency.getIsActive.toString)
            println("Currency ExchangeRate: " + currency.getExchangeRate)
            //Get the format Format instance of each currency
            val formatOption = currency.getFormat
            if (formatOption.isDefined) {
              val format = formatOption.get
              println("Currency Format DecimalSeparator: " + format.getDecimalSeparator.getValue)
              println("Currency Format ThousandSeparator: " + format.getThousandSeparator.getValue)
              println("Currency Format DecimalPlaces: " + format.getDecimalPlaces.getValue)
            }
            val createdByOption = currency.getCreatedBy
            if (createdByOption.isDefined) {
              val createdBy = createdByOption.get
              println("Currency CreatedBy User-Name: " + createdBy.getName)
              println("Currency CreatedBy User-ID: " + createdBy.getId)
            }
            println("Currency PrefixSymbol: " + currency.getPrefixSymbol.toString)
            println("Currency IsBase: " + currency.getIsBase.toString)
            println("Currency ModifiedTime: " + currency.getModifiedTime)
            println("Currency Name: " + currency.getName)
            val modifiedByOption = currency.getModifiedBy
            if (modifiedByOption.isDefined) {
              val modifiedBy = modifiedByOption.get
              println("Currency ModifiedBy User-Name: " + modifiedBy.getName)
              println("Currency ModifiedBy User-ID: " + modifiedBy.getId)
            }
            println("Currency Id: " + currency.getId)
            println("Currency IsoCode: " + currency.getIsoCode)
          }
        }
        else if (responseHandler.isInstanceOf[APIException]) {
          val exception = responseHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }

  /**
   * <h3> Update Currency </h3>
   * This method is used to update currency details.
   *
   * @param currencyId - Specify the unique ID of the currency.
   * @throws Exception
   */
  @throws[Exception]
  def updateCurrency(currencyId: Long): Unit = {
    val currenciesOperations = new CurrenciesOperations
    val bodyWrapper = new BodyWrapper
    val currencies = new ArrayBuffer[Currency]
    val currency = new Currency
    currency.setPrefixSymbol(Option(true))
    currency.setExchangeRate(Option("10.0000000"))
    currency.setIsActive(Option(false))
    val format = new Format
    format.setDecimalSeparator(new Choice[String]("Period"))
    format.setThousandSeparator(new Choice[String]("Comma"))
    format.setDecimalPlaces(new Choice[String]("3"))
    currency.setFormat(Option(format))
    currencies.addOne(currency)
    bodyWrapper.setCurrencies(currencies)
    //Call updateCurrency method that takes BodyWrapper instance and currencyId as parameters
    val responseOption = currenciesOperations.updateCurrency(currencyId,bodyWrapper )
    if (responseOption.isDefined) {
      val response = responseOption.get
      println("Status Code: " + response.getStatusCode)
      if (response.isExpected) {
        val actionHandler = response.getObject
        if (actionHandler.isInstanceOf[ActionWrapper]) {
          val actionWrapper = actionHandler.asInstanceOf[ActionWrapper]
          val actionResponses = actionWrapper.getCurrencies
          
          for (actionResponse <- actionResponses) {
            if (actionResponse.isInstanceOf[SuccessResponse]) {
              val successResponse = actionResponse.asInstanceOf[SuccessResponse]
              println("Status: " + successResponse.getStatus.getValue)
              println("Code: " + successResponse.getCode.getValue)
              println("Details: ")

              successResponse.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + successResponse.getMessage.getValue)
            }
            else if (actionResponse.isInstanceOf[APIException]) {
              val exception = actionResponse.asInstanceOf[APIException]
              println("Status: " + exception.getStatus.getValue)
              println("Code: " + exception.getCode.getValue)
              println("Details: ")

              exception.getDetails.foreach(entry=>{
                println(entry._1 + ": " + entry._2)
              })
              println("Message: " + exception.getMessage.getValue)
            }
          }
        }
        else if (actionHandler.isInstanceOf[APIException]) {
          val exception = actionHandler.asInstanceOf[APIException]
          println("Status: " + exception.getStatus.getValue)
          println("Code: " + exception.getCode.getValue)
          println("Details: ")

          exception.getDetails.foreach(entry=>{
            println(entry._1 + ": " + entry._2)
          })
          println("Message: " + exception.getMessage.getValue)
        }
      }
      else {
        val responseObject = response.getModel
        val clas = responseObject.getClass
        val fields = clas.getDeclaredFields
        for (field <- fields) {
          println(field.getName + ":" + field.get(responseObject))
        }
      }
    }
  }
}
