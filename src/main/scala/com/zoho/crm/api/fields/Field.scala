package com.zoho.crm.api.fields

import com.zoho.crm.api.customviews.Criteria
import com.zoho.crm.api.layouts.Layout
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Field extends Model	{
	private var systemMandatory:Option[Boolean] = None
	private var webhook:Option[Boolean] = None
	private var private1:Option[Private] = None
	private var layouts:Option[Layout] = None
	private var content:Option[String] = None
	private var columnName:Option[String] = None
	private var type1:Option[String] = None
	private var transitionSequence:Option[Int] = None
	private var personalityName:Option[String] = None
	private var message:Option[String] = None
	private var mandatory:Option[Boolean] = None
	private var criteria:Option[Criteria] = None
	private var relatedDetails:Option[RelatedDetails] = None
	private var jsonType:Option[String] = None
	private var crypt:Option[Crypt] = None
	private var fieldLabel:Option[String] = None
	private var tooltip:Option[ToolTip] = None
	private var createdSource:Option[String] = None
	private var fieldReadOnly:Option[Boolean] = None
	private var displayLabel:Option[String] = None
	private var readOnly:Option[Boolean] = None
	private var associationDetails:Option[AssociationDetails] = None
	private var quickSequenceNumber:Option[Int] = None
	private var businesscardSupported:Option[Boolean] = None
	private var multiModuleLookup:HashMap[String, Any] = _
	private var currency:Option[Currency] = None
	private var id:Option[Long] = None
	private var customField:Option[Boolean] = None
	private var lookup:Option[Module] = None
	private var visible:Option[Boolean] = None
	private var length:Option[Int] = None
	private var viewType:Option[ViewType] = None
	private var subform:Option[Module] = None
	private var apiName:Option[String] = None
	private var unique:Option[Unique] = None
	private var historyTracking:Option[Boolean] = None
	private var dataType:Option[String] = None
	private var formula:Option[Formula] = None
	private var decimalPlace:Option[Int] = None
	private var massUpdate:Option[Boolean] = None
	private var blueprintSupported:Option[Boolean] = None
	private var multiselectlookup:Option[MultiSelectLookup] = None
	private var pickListValues:ArrayBuffer[PickListValue] = _
	private var autoNumber:Option[AutoNumber] = None
	private var defaultValue:Option[String] = None
	private var sectionId:Option[Int] = None
	private var validationRule:HashMap[String, Any] = _
	private var convertMapping:HashMap[String, Any] = _
	private var keyModified:HashMap[String, Int] = HashMap()

	def getSystemMandatory() :Option[Boolean]	={
		return  this.systemMandatory
	}

	def setSystemMandatory( systemMandatory: Option[Boolean]) 	={
		 this.systemMandatory = systemMandatory
		 this.keyModified("system_mandatory") = 1
	}

	def getWebhook() :Option[Boolean]	={
		return  this.webhook
	}

	def setWebhook( webhook: Option[Boolean]) 	={
		 this.webhook = webhook
		 this.keyModified("webhook") = 1
	}

	def getPrivate() :Option[Private]	={
		return  this.private1
	}

	def setPrivate( private1: Option[Private]) 	={
		 this.private1 = private1
		 this.keyModified("private") = 1
	}

	def getLayouts() :Option[Layout]	={
		return  this.layouts
	}

	def setLayouts( layouts: Option[Layout]) 	={
		 this.layouts = layouts
		 this.keyModified("layouts") = 1
	}

	def getContent() :Option[String]	={
		return  this.content
	}

	def setContent( content: Option[String]) 	={
		 this.content = content
		 this.keyModified("content") = 1
	}

	def getColumnName() :Option[String]	={
		return  this.columnName
	}

	def setColumnName( columnName: Option[String]) 	={
		 this.columnName = columnName
		 this.keyModified("column_name") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("_type") = 1
	}

	def getTransitionSequence() :Option[Int]	={
		return  this.transitionSequence
	}

	def setTransitionSequence( transitionSequence: Option[Int]) 	={
		 this.transitionSequence = transitionSequence
		 this.keyModified("transition_sequence") = 1
	}

	def getPersonalityName() :Option[String]	={
		return  this.personalityName
	}

	def setPersonalityName( personalityName: Option[String]) 	={
		 this.personalityName = personalityName
		 this.keyModified("personality_name") = 1
	}

	def getMessage() :Option[String]	={
		return  this.message
	}

	def setMessage( message: Option[String]) 	={
		 this.message = message
		 this.keyModified("message") = 1
	}

	def getMandatory() :Option[Boolean]	={
		return  this.mandatory
	}

	def setMandatory( mandatory: Option[Boolean]) 	={
		 this.mandatory = mandatory
		 this.keyModified("mandatory") = 1
	}

	def getCriteria() :Option[Criteria]	={
		return  this.criteria
	}

	def setCriteria( criteria: Option[Criteria]) 	={
		 this.criteria = criteria
		 this.keyModified("criteria") = 1
	}

	def getRelatedDetails() :Option[RelatedDetails]	={
		return  this.relatedDetails
	}

	def setRelatedDetails( relatedDetails: Option[RelatedDetails]) 	={
		 this.relatedDetails = relatedDetails
		 this.keyModified("related_details") = 1
	}

	def getJsonType() :Option[String]	={
		return  this.jsonType
	}

	def setJsonType( jsonType: Option[String]) 	={
		 this.jsonType = jsonType
		 this.keyModified("json_type") = 1
	}

	def getCrypt() :Option[Crypt]	={
		return  this.crypt
	}

	def setCrypt( crypt: Option[Crypt]) 	={
		 this.crypt = crypt
		 this.keyModified("crypt") = 1
	}

	def getFieldLabel() :Option[String]	={
		return  this.fieldLabel
	}

	def setFieldLabel( fieldLabel: Option[String]) 	={
		 this.fieldLabel = fieldLabel
		 this.keyModified("field_label") = 1
	}

	def getTooltip() :Option[ToolTip]	={
		return  this.tooltip
	}

	def setTooltip( tooltip: Option[ToolTip]) 	={
		 this.tooltip = tooltip
		 this.keyModified("tooltip") = 1
	}

	def getCreatedSource() :Option[String]	={
		return  this.createdSource
	}

	def setCreatedSource( createdSource: Option[String]) 	={
		 this.createdSource = createdSource
		 this.keyModified("created_source") = 1
	}

	def getFieldReadOnly() :Option[Boolean]	={
		return  this.fieldReadOnly
	}

	def setFieldReadOnly( fieldReadOnly: Option[Boolean]) 	={
		 this.fieldReadOnly = fieldReadOnly
		 this.keyModified("field_read_only") = 1
	}

	def getDisplayLabel() :Option[String]	={
		return  this.displayLabel
	}

	def setDisplayLabel( displayLabel: Option[String]) 	={
		 this.displayLabel = displayLabel
		 this.keyModified("display_label") = 1
	}

	def getReadOnly() :Option[Boolean]	={
		return  this.readOnly
	}

	def setReadOnly( readOnly: Option[Boolean]) 	={
		 this.readOnly = readOnly
		 this.keyModified("read_only") = 1
	}

	def getAssociationDetails() :Option[AssociationDetails]	={
		return  this.associationDetails
	}

	def setAssociationDetails( associationDetails: Option[AssociationDetails]) 	={
		 this.associationDetails = associationDetails
		 this.keyModified("association_details") = 1
	}

	def getQuickSequenceNumber() :Option[Int]	={
		return  this.quickSequenceNumber
	}

	def setQuickSequenceNumber( quickSequenceNumber: Option[Int]) 	={
		 this.quickSequenceNumber = quickSequenceNumber
		 this.keyModified("quick_sequence_number") = 1
	}

	def getBusinesscardSupported() :Option[Boolean]	={
		return  this.businesscardSupported
	}

	def setBusinesscardSupported( businesscardSupported: Option[Boolean]) 	={
		 this.businesscardSupported = businesscardSupported
		 this.keyModified("businesscard_supported") = 1
	}

	def getMultiModuleLookup() :HashMap[String, Any]	={
		return  this.multiModuleLookup
	}

	def setMultiModuleLookup( multiModuleLookup: HashMap[String, Any]) 	={
		 this.multiModuleLookup = multiModuleLookup
		 this.keyModified("multi_module_lookup") = 1
	}

	def getCurrency() :Option[Currency]	={
		return  this.currency
	}

	def setCurrency( currency: Option[Currency]) 	={
		 this.currency = currency
		 this.keyModified("currency") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
	}

	def getCustomField() :Option[Boolean]	={
		return  this.customField
	}

	def setCustomField( customField: Option[Boolean]) 	={
		 this.customField = customField
		 this.keyModified("custom_field") = 1
	}

	def getLookup() :Option[Module]	={
		return  this.lookup
	}

	def setLookup( lookup: Option[Module]) 	={
		 this.lookup = lookup
		 this.keyModified("lookup") = 1
	}

	def getVisible() :Option[Boolean]	={
		return  this.visible
	}

	def setVisible( visible: Option[Boolean]) 	={
		 this.visible = visible
		 this.keyModified("visible") = 1
	}

	def getLength() :Option[Int]	={
		return  this.length
	}

	def setLength( length: Option[Int]) 	={
		 this.length = length
		 this.keyModified("length") = 1
	}

	def getViewType() :Option[ViewType]	={
		return  this.viewType
	}

	def setViewType( viewType: Option[ViewType]) 	={
		 this.viewType = viewType
		 this.keyModified("view_type") = 1
	}

	def getSubform() :Option[Module]	={
		return  this.subform
	}

	def setSubform( subform: Option[Module]) 	={
		 this.subform = subform
		 this.keyModified("subform") = 1
	}

	def getAPIName() :Option[String]	={
		return  this.apiName
	}

	def setAPIName( apiName: Option[String]) 	={
		 this.apiName = apiName
		 this.keyModified("api_name") = 1
	}

	def getUnique() :Option[Unique]	={
		return  this.unique
	}

	def setUnique( unique: Option[Unique]) 	={
		 this.unique = unique
		 this.keyModified("unique") = 1
	}

	def getHistoryTracking() :Option[Boolean]	={
		return  this.historyTracking
	}

	def setHistoryTracking( historyTracking: Option[Boolean]) 	={
		 this.historyTracking = historyTracking
		 this.keyModified("history_tracking") = 1
	}

	def getDataType() :Option[String]	={
		return  this.dataType
	}

	def setDataType( dataType: Option[String]) 	={
		 this.dataType = dataType
		 this.keyModified("data_type") = 1
	}

	def getFormula() :Option[Formula]	={
		return  this.formula
	}

	def setFormula( formula: Option[Formula]) 	={
		 this.formula = formula
		 this.keyModified("formula") = 1
	}

	def getDecimalPlace() :Option[Int]	={
		return  this.decimalPlace
	}

	def setDecimalPlace( decimalPlace: Option[Int]) 	={
		 this.decimalPlace = decimalPlace
		 this.keyModified("decimal_place") = 1
	}

	def getMassUpdate() :Option[Boolean]	={
		return  this.massUpdate
	}

	def setMassUpdate( massUpdate: Option[Boolean]) 	={
		 this.massUpdate = massUpdate
		 this.keyModified("mass_update") = 1
	}

	def getBlueprintSupported() :Option[Boolean]	={
		return  this.blueprintSupported
	}

	def setBlueprintSupported( blueprintSupported: Option[Boolean]) 	={
		 this.blueprintSupported = blueprintSupported
		 this.keyModified("blueprint_supported") = 1
	}

	def getMultiselectlookup() :Option[MultiSelectLookup]	={
		return  this.multiselectlookup
	}

	def setMultiselectlookup( multiselectlookup: Option[MultiSelectLookup]) 	={
		 this.multiselectlookup = multiselectlookup
		 this.keyModified("multiselectlookup") = 1
	}

	def getPickListValues() :ArrayBuffer[PickListValue]	={
		return  this.pickListValues
	}

	def setPickListValues( pickListValues: ArrayBuffer[PickListValue]) 	={
		 this.pickListValues = pickListValues
		 this.keyModified("pick_list_values") = 1
	}

	def getAutoNumber() :Option[AutoNumber]	={
		return  this.autoNumber
	}

	def setAutoNumber( autoNumber: Option[AutoNumber]) 	={
		 this.autoNumber = autoNumber
		 this.keyModified("auto_number") = 1
	}

	def getDefaultValue() :Option[String]	={
		return  this.defaultValue
	}

	def setDefaultValue( defaultValue: Option[String]) 	={
		 this.defaultValue = defaultValue
		 this.keyModified("default_value") = 1
	}

	def getSectionId() :Option[Int]	={
		return  this.sectionId
	}

	def setSectionId( sectionId: Option[Int]) 	={
		 this.sectionId = sectionId
		 this.keyModified("section_id") = 1
	}

	def getValidationRule() :HashMap[String, Any]	={
		return  this.validationRule
	}

	def setValidationRule( validationRule: HashMap[String, Any]) 	={
		 this.validationRule = validationRule
		 this.keyModified("validation_rule") = 1
	}

	def getConvertMapping() :HashMap[String, Any]	={
		return  this.convertMapping
	}

	def setConvertMapping( convertMapping: HashMap[String, Any]) 	={
		 this.convertMapping = convertMapping
		 this.keyModified("convert_mapping") = 1
	}

	def isKeyModified( key: String) :Any	={
		if((( this.keyModified.contains(key))))
		{
			return  this.keyModified(key)
		}
		return None
	}

	def setKeyModified( key: String,  modification: Int) 	={
		 this.keyModified(key) = modification
	}}