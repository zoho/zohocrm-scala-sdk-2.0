package com.zoho.crm.api.util

import java.util
import java.util.logging.Level

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


object Constants {

  val JSON_FILE_EXTENSION = ".json"

  val CONSENT_LOOKUP = "consent_lookup"

  val DELETE_MODULE_FROM_FIELDFILE_ERROR = "Exception in deleting module from Fields file : "

  val REFRESH_SINGLE_MODULE_FIELDS_ERROR = "Exception in refreshing fields of module : "

  val SET_API_URL_EXCEPTION = "Exception in setting API URL : "

  val REFRESH_ALL_MODULE_FIELDS_ERROR = "Exception in refreshing fields of all modules : "

  val DELETE_FIELD_FILE_ERROR = "Exception in deleting Current User Fields file : "

  val DELETE_FIELD_FILES_ERROR = "Exception in deleting all Fields files : "

  val UNSUPPORTED_IN_API = "API UNSUPPORTED OPERATION"

  val UNSUPPORTED_IN_API_MESSAGE = " Operation is not supported by API"

  val KEYS_TO_SKIP = new ArrayBuffer[String]()
  KEYS_TO_SKIP += ("Created_Time", "Modified_Time", "Created_By", "Modified_By", "Tag")

  var SET_TO_CONTENT_TYPE = new ArrayBuffer[String]()
  SET_TO_CONTENT_TYPE += ("/crm/bulk/v2/read", "/crm/bulk/v2/write")

  val API_EXCEPTION = "API_EXCEPTION"

  val STATUS = "status"

  val MESSAGE = "message"

  val UNDERSCORE = "_"

  val RELATED_LISTS = "Related_Lists"

  val API_NAME = "api_name"

  val HREF = "href"

  val ID = "id"

  val NO_CONTENT_STATUS_CODE = 204

  val NOT_MODIFIED_STATUS_CODE = 304

  val NOTES = "Notes"

  val ATTACHMENTS = "$attachments"

  val ATTACHMENTS_NAMESPACE = "com.zoho.crm.api.attachments.Attachment"

  val LINE_TAX = "$line_tax"

  val LAYOUT = "Layout"

  val FORMULA = "formula"

  val SUBFORM = "subform"

  val SE_MODULE = "se_module"

  val USER_EMAIL_REGEX_ERROR = "UserSignature Email Regex Mismatch"

  val PARAMNAME = "ParamName"

  val MANDATORY_NOT_FOUND = "Mandatory key Not Found"

  val COMMENT_NAMESPACE = "com.zoho.crm.api.record.Comment"

  val LINE_TAX_NAMESPACE = "com.zoho.crm.api.record.LineTax"

  val ACTIVITIES = "Activities"

  val SOLUTIONS = "Solutions"

  val CASES = "Cases"

  val PRODUCT_DETAILS = "Product_Details"

  val PRICING_DETAILS = "Pricing_Details"

  val PARTICIPANT_API_NAME = "Participants"

  val COMMENTS = "Comments"

  val PRICE_BOOKS = "Price_Books"

  val EVENTS = "Events"

  val INVENTORY_MODULES = new ArrayBuffer[String]()
  INVENTORY_MODULES += ("invoices", "sales_orders", "purchase_orders", "quotes")


  val CALLS = "Calls"

  val ACCOUNTS = "Accounts"

  val CALL_DURATION = "Call_Duration"

  val EXCEPTION = "Exception"

  val FILE_ERROR = "file_error"

  val FIELDS_LAST_MODIFIED_TIME = "FIELDS-LAST-MODIFIED-TIME"

  val FILE_DOES_NOT_EXISTS = "file does not exists"

  val FILEBODYWRAPPER = "FileBodyWrapper"

  val INVALID_URL_ERROR = "Invalid URL Error"

  val INITIALIZATION_EXCEPTION = "Exception in initialization : "

  val SWITCH_USER_ERROR = "SWITCH USER ERROR"

  val SDK_UNINITIALIZATION_ERROR = "SDK UNINITIALIZED ERROR"

  val SDK_UNINITIALIZATION_MESSAGE = "SDK is UnInitialized"

  val INITIALIZATION_SUCCESSFUL = "Initialization successful "

  val INITIALIZATION_SWITCHED = "Initialization switched "

  val IN_ENVIRONMENT = " in Environment : "

  val FOR_EMAIL_ID = "for Email Id : "

  val REFRESH_TOKEN_MESSAGE = "Access Token has expired. Hence refreshing."

  val RESOURCE_PATH_ERROR_MESSAGE = "Resource Path MUST NOT be NULL/empty."

  val USERSIGNATURE_ERROR_MESSAGE = "UserSignature MUST NOT be NULL."

  val ENVIRONMENT_ERROR_MESSAGE = "Environment MUST NOT be NULL."

  val TOKEN_ERROR_MESSAGE = "Token MUST NOT be NULL."

  val STORE_ERROR_MESSAGE = "Store MUST NOT be NULL."

  val SDK_CONFIG_ERROR_MESSAGE = "sdkConfig MUST NOT be NULL."

  val RESOURCE_PATH = "EMPTY_RESOURCE_PATH"

  val USER_SIGNATURE_ERROR = "USERSIGNATURE ERROR"

  val REQUEST_PROXY_ERROR = "REQUESTPROXY ERROR"

  val HOST_ERROR_MESSAGE = "Host MUST NOT be NULL."

  val PORT_ERROR_MESSAGE = "Port MUST NOT be NULL."

  val PARAMETER_NULL_ERROR = "NULL PARAMETER ERROR"

  val HEADER_NULL_ERROR = "NULL HEADER ERROR"

  val PARAM_NAME_NULL_ERROR = "NULL PARAM NAME ERROR"

  val HEADER_NAME_NULL_ERROR = "NULL HEADER NAME ERROR"

  val PARAM_NAME_NULL_ERROR_MESSAGE = "Param Name MUST NOT be NULL"

  val HEADER_NAME_NULL_ERROR_MESSAGE = "Header Name MUST NOT be NULL"

  val NULL_VALUE_ERROR_MESSAGE = " MUST NOT be NULL"

  val PARAM_INSTANCE_NULL_ERROR = "Param<T> Instance MUST NOT be NULL"

  val HEADER_INSTANCE_NULL_ERROR = "Header<T> Instance MUST NOT be NULL"

  val SCALAMAP_NAMESPACE = "scala.collection.mutable.HashMap"

  val LISTBUFFER_NAMESPACE = "scala.collection.mutable.ArrayBuffer"

  val GIVEN_TYPE = "given-type"

  val TYPE_ERROR = "TYPE ERROR"

  val OBJECT_NAMESPACE = "java.lang.Object"

  val PICKLIST = "picklist"

  val GIVEN_LENGTH = "given-length"

  val HTTP = "http"

  val CONTENT_API_URL = "content.zohoapis.com"

  val FORM_REQUEST_EXCEPTION = "Exception in forming request body : "

  val API_CALL_EXCEPTION = "Exception in current API call execution : "

  val REQUEST_METHOD_PATCH = "PATCH"

  val AUTHENTICATION_EXCEPTION = "Exception in authenticating current request : "

  val CANT_DISCLOSE = " ## can't disclose ## "

  val URL = "URL"

  val HEADERS: String = "HEADERS"

  val PARAMS: String = "PARAMS"

  val PROXY_SETTINGS = "Proxy settings - "

  val PROXY_HOST = "Host: "

  val PROXY_PORT = "Port: "

  val PROXY_USER = "User: "

  val PROXY_DOMAIN = "Domain: "

  val GET_TOKENS_FILE_ERROR = "Exception in getTokens - FileStore : "

  val DELETE_TOKENS_DB_ERROR = "Exception in deleteTokens - DBStore : "

  val DELETE_TOKENS_FILE_ERROR = "Exception in deleteTokens - FileStore : "

  val NULL_VALUE = "NULL"

  val GET_TOKENS_DB_ERROR = "Exception in getTokens - DBStore : "

  val USER_MAIL_NULL_ERROR = "USER MAIL NULL ERROR"

  val USER_MAIL_NULL_ERROR_MESSAGE = "User Mail MUST NOT be NULL. Use setUserMail() to set value."

  val MODULEPACKAGENAME = "modulePackageName"

  val MODULEDETAILS = "moduleDetails"

  val REQUEST_CATEGORY_READ = "READ"

  val REQUEST_CATEGORY_CREATE = "CREATE"

  val REQUEST_CATEGORY_UPDATE = "UPDATE"

  val REQUEST_CATEGORY_ACTION = "ACTION"

  val MANDATORY_KEY_ERROR = "Value missing or NULL for mandatory key(s) :"

  val PRIMARY_KEY_ERROR = "Value missing or NULL for required key(s) : "

  val MANDATORY_VALUE_ERROR = "MANDATORY VALUE ERROR"

  val ATTACHMENT_ID = "attachment_id"

  val FILE_ID = "file_id"

  val REQUIRED_IN_UPDATE = "required_in_update"

  val LOOKUP = "lookup"

  val SKIP_MANDATORY = "skip_mandatory"

  val KEY_MODIFIED = "keyModified"

  val MODULE = "Module"

  val PRIMARY = "primary"

  val PRICINGDETAILS = "com.zoho.crm.api.record.PricingDetails"

  val PARTICIPANTS = "com.zoho.crm.api.record.Participants"

  val INVENTORY_LINE_ITEMS = "com.zoho.crm.api.record.InventoryLineItems"

  val MODULE_NAMESPACE = "com.zoho.crm.api.modules.Module"

  val USER_NAMESPACE = "com.zoho.crm.api.users.User"

  val FIELD_FILE_NAMESPACE = "com.zoho.crm.api.record.FileDetails"

  val LAYOUT_NAMESPACE = "com.zoho.crm.api.layouts.Layout"

  val REMINDAT_NAMESPACE = "com.zoho.crm.api.record.RemindAt"

  val CONSENT_NAMESPACE = "com.zoho.crm.api.record.Consent"

  val RECURRING_ACTIVITY_NAMESPACE = "com.zoho.crm.api.record.RecurringActivity"

  val REMINDER_NAMESPACE = "com.zoho.crm.api.record.Reminder"

  val STRING_NAMESPACE = "java.lang.String"

  val BOOLEAN_NAMESPACE = "java.lang.Boolean"

  val FLOAT_NAMESPACE = "java.lang.Float"

  val INTEGER_NAMESPACE = "java.lang.Integer"

  val LONG_NAMESPACE = "java.lang.Long"

  val DOUBLE_NAMESPACE = "java.lang.Double"

  val DATE_NAMESPACE = "java.time.LocalDate"

  val DATETIME_NAMESPACE = "java.time.OffsetDateTime"

  val FILE_NAMESPACE = "com.zoho.crm.api.util.StreamWrapper"

  val CHOICE_NAMESPACE = "com.zoho.crm.api.util.Choice"

  val PACKAGE_NAMESPACE = "com.zoho.crm.api"

  val LOGGER_LEVELS = new mutable.HashMap[String, Level]

  val ACCESS_TOKEN = "access_token"

  val GRANT_TYPE = "grant_type"

  val GRANT_TYPE_AUTH_CODE = "authorization_code"

  val EXPIRES_IN = "expires_in"

  val EXPIRES_IN_SEC = "expires_in_sec"

  val REFRESH_TOKEN = "refresh_token"

  val CLIENT_ID = "client_id"

  val CLIENT_SECRET = "client_secret"

  val REDIRECT_URI = "redirect_uri"

  val OAUTH_HEADER_PREFIX = "Zoho-oauthtoken "

  val AUTHORIZATION = "Authorization"

  val CODE = "code"

  val LOG_FILE_NAME = "sdk_logs.log"

  val TOKEN_FILE = "sdk_tokens.txt"

  val ZOHO_SDK = "X-ZOHO-SDK"

  val SDK_VERSION = "2.0.0"

  val ZOHO_API_VERSION = "2.0"

  val MYSQL_HOST = "jdbc:mysql://localhost"

  val MYSQL_DATABASE_NAME = "zohooauth"

  val MYSQL_USER_NAME = "root"

  val DATA_TYPE: mutable.HashMap[String, String] = new mutable.HashMap[String, String]
  DATA_TYPE("java.util.List") = "scala.collection.mutable.ArrayBuffer"
  DATA_TYPE("java.util.HashMap") = "scala.collection.mutable.HashMap"

  val MYSQL_PORT_NUMBER = "3306"

  val JDBC_DRIVER_NAME = "com.mysql.jdbc.Driver"

  val USER_MAIL = "user_mail"

  val POST = "POST"

  val PUT = "PUT"

  val GET = "GET"

  val DELETE = "DELETE"

  val INTERFACE = "interface"

  val CLASSES = "classes"

  val READ_ONLY = "read-only"

  val NAME = "name"

  val IS_KEY_MODIFIED = "isKeyModified"

  val MANDATORY_VALUE_MISSING_ERROR = "MANDATORY VALUE MISSING ERROR"

  val REQUIRED = "required"

  val SET_KEY_MODIFIED = "setKeyModified"

  val TYPE = "type"

  val RECORD_NAMESPACE = "com.zoho.crm.api.record.Record"

  val LIST_NAMESPACE = "java.util.List"

  val MAP_NAMESPACE = "java.util.Map"

  val STRUCTURE_NAME = "structure_name"

  val KEY_VALUES = "keyValues"

  val KEYS = "keys"

  val VALUES = "values"

  val SAVE_TOKEN_ERROR = "Exception in saving tokens"

  val GET_TOKEN_ERROR = "Exception in getting access token"

  val GET_TOKEN_DB_ERROR = "Exception in getToken - DBStore"

  val DELETE_TOKEN_DB_ERROR = "Exception in delete_token - DBStore"

  val SAVE_TOKEN_DB_ERROR = "Exception in save_token - DBStore"

  val GET_TOKEN_FILE_ERROR = "Exception in get_token - FileStore"

  val SAVE_TOKEN_FILE_ERROR = "Exception in save_token - FileStore"

  val DELETE_TOKEN_FILE_ERROR = "Exception in delete_token - FileStore"

  val SDK_LOGGER_INITIALIZE = "Exception in Logger Initialization"

  val FIELD = "field"

  val CLASS = "class"

  val INDEX = "index"

  val ACCEPTED_VALUES = "accepted-values"

  val UNACCEPTED_VALUES_ERROR = "UNACCEPTED VALUES ERROR"

  val UNIQUE = "unique"

  val FIRST_INDEX = "first-index"

  val NEXT_INDEX = "next-index"

  val UNIQUE_KEY_ERROR = "UNIQUE KEY ERROR"

  val MIN_LENGTH = "min-length"

  val MAX_LENGTH = "max-length"

  val MAXIMUM_LENGTH = "maximum-length"

  val MAXIMUM_LENGTH_ERROR = "MAXIMUM LENGTH ERROR"

  val MINIMUM_LENGTH = "minimum-length"

  val MINIMUM_LENGTH_ERROR = "MINIMUM LENGTH ERROR"

  val REGEX = "regex"

  val INSTANCE_NUMBER = "instance-number"

  val REGEX_MISMATCH_ERROR = "REGEX MISMATCH ERROR"

  val STREAM_WRAPPER_CLASS_PATH = "com.zoho.crm.api.util.StreamWrapper"

  val CONTENT_DISPOSITION = "Content-Disposition"

  val EXCEPTION_IS_KEY_MODIFIED = "Exception in calling isKeyModified"

  val MANDATORY_KEY_MISSING_ERROR = "Value missing for mandatory key: "

  val EXCEPTION_SET_KEY_MODIFIED = "Exception in calling setKeyModified"

  val JSON_DETAILS_ERROR = "ERROR IN READING JSONDETAILS FILE"

  val INITIALIZATION_ERROR = "Exception in initialization"

  val EXCEPTION_JSONDETAILS = "Exception in loading JSONDetails"

  val JSON_DETAILS_FILE_PATH = "/JSONDetails.json"

  val EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$"

  val USER_ERROR = "USER ERROR"

  val EMAIL = "email"

  val EXPECTED_TYPE = "expected-type"

  val TOKEN_STORE = "TOKEN_STORE"

  val INVALID_CLIENT_ERROR = "INVALID CLIENT ERROR"

  val USER_AGENT = "Mozilla/5.0"

  val USER_AGENT_KEY = "user-agent"

  val ERROR_KEY = "error"

  val CONTENT_TYPE = "Content-Type"

  val MULTIPART_FORMDATA = "multipart/form-data;boundary="

  val GRANT_TOKEN = "grant_token"

  val EXPIRY_TIME = "expiry_time"

  val COMMA = ","

  val FIELD_DETAILS_DIRECTORY = "resources"

  val REQUEST_METHOD_POST = "POST"

  val REQUEST_METHOD_PUT = "PUT"

  val REQUEST_METHOD_GET = "GET"

  val REQUEST_METHOD_DELETE = "DELETE"

  val IS_GENERATE_REQUEST_BODY = new ArrayBuffer[String]()
  IS_GENERATE_REQUEST_BODY += (Constants.REQUEST_METHOD_PATCH, Constants.REQUEST_METHOD_POST, Constants.REQUEST_METHOD_PUT)

  val PHOTO = "photo"

  val CRM = "crm"

  val API_VERSION = "v2"

  val PHOTO_SUPPORTED_MODULES = new ArrayBuffer[String]()
  PHOTO_SUPPORTED_MODULES += ("leads", "contacts", "accounts", "products", "vendors")

  val PHOTO_UPLOAD_ERROR_MESSAGE = "The given module is not supported in API."

  val INVALID_MODULE = "INVALID_MODULE"

  val GIVEN_VALUE = "given-value"

  val MYSQL_TABLE_NAME = "oauthtoken"

  val REDIRECT_URL = "redirect_url"

  val TOKEN_ERROR = "TOKEN ERROR"

  val CLIENT_ID_NULL_ERROR_MESSAGE = "ClientID MUST NOT be null"

  val CLIENT_SECRET_NULL_ERROR_MESSAGE = "ClientSecret MUST NOT be null"

  val NULL_ERROR = "NULL ERROR"

  val EXPECTED_TOKEN_TYPES = "Expecting Refresh or Grant token"

  val NO_ACCESS_TOKEN_ERROR = "ACCESS TOKEN IS NOT PRESENT IN RESPONSE"

  val USER_SIGNATURE_ERROR_MESSAGE = "UserSignature MUST NOT be null."

  val RESOURCE_PATH_INVALID_ERROR_MESSAGE = "Resource Path MUST be a valid directory."

  val SDK_MODULE_METADATA = "SDK-MODULE-METADATA"

  val SKIP_MODULES = new ArrayBuffer[String]()
  SKIP_MODULES += ("deals")

  val GENERATED_TYPE = "generated_type"

  val GENERATED_TYPE_CUSTOM = "custom"

  val UPLOAD_PHOTO_UNSUPPORTED_ERROR = "UPLOAD PHOTO UNSUPPORTED MODULE"

  val UPLOAD_PHOTO_UNSUPPORTED_MESSAGE = "Photo Upload Operation is not supported by the module: "

  val OAUTH_MANDATORY_KEYS = new ArrayBuffer[String]()
  OAUTH_MANDATORY_KEYS += ("grantToken", "refreshToken", "id", "accessToken")

  val DEFAULT_MODULENAME_VS_APINAME: mutable.HashMap[String, String] = new mutable.HashMap[String, String]

  DEFAULT_MODULENAME_VS_APINAME("leads") = "Leads"

  DEFAULT_MODULENAME_VS_APINAME("contacts") = "Contacts"

  DEFAULT_MODULENAME_VS_APINAME("accounts") = "Accounts"

  DEFAULT_MODULENAME_VS_APINAME("deals") = "Deals"

  DEFAULT_MODULENAME_VS_APINAME("tasks") = "Tasks"

  DEFAULT_MODULENAME_VS_APINAME("events") = "Events"

  DEFAULT_MODULENAME_VS_APINAME("activities") = "Activities"

  DEFAULT_MODULENAME_VS_APINAME("calls") = "Calls"

  DEFAULT_MODULENAME_VS_APINAME("products") = "Products"

  DEFAULT_MODULENAME_VS_APINAME("quotes") = "Quotes"

  DEFAULT_MODULENAME_VS_APINAME("sales_orders") = "Sales_Orders"

  DEFAULT_MODULENAME_VS_APINAME("purchase_orders") = "Purchase_Orders"

  DEFAULT_MODULENAME_VS_APINAME("invoices") = "Invoices"

  DEFAULT_MODULENAME_VS_APINAME("campaigns") = "Campaigns"

  DEFAULT_MODULENAME_VS_APINAME("vendors") = "Vendors"

  DEFAULT_MODULENAME_VS_APINAME("price_books") = "Price_Books"

  DEFAULT_MODULENAME_VS_APINAME("cases") = "Cases"

  DEFAULT_MODULENAME_VS_APINAME("solutions") = "Solutions"

  DEFAULT_MODULENAME_VS_APINAME("visits") = "Visits"

  DEFAULT_MODULENAME_VS_APINAME("approvals") = "Approvals"

  DEFAULT_MODULENAME_VS_APINAME("notes") = "Notes"

  DEFAULT_MODULENAME_VS_APINAME("attachments") = "Attachments"

  LOGGER_LEVELS("ALL") = Level.ALL
  LOGGER_LEVELS("FINEST") = Level.FINEST
  LOGGER_LEVELS("TRACE") = Level.FINER
  LOGGER_LEVELS("DEBUG") = Level.FINE
  LOGGER_LEVELS("CONFIG") = Level.CONFIG
  LOGGER_LEVELS("INFO") = Level.INFO
  LOGGER_LEVELS("WARNING") = Level.WARNING
  LOGGER_LEVELS("ERROR") = Level.SEVERE
  LOGGER_LEVELS("OFF") = Level.OFF

  val SCALA = "scala_"
}
