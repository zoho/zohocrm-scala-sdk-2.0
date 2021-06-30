package com.zoho.crm.api.users

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Theme extends Model	{
	private var normalTab:Option[TabTheme] = None
	private var selectedTab:Option[TabTheme] = None
	private var newBackground:Option[String] = None
	private var background:Option[String] = None
	private var screen:Option[String] = None
	private var type1:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getNormalTab() :Option[TabTheme]	={
		return  this.normalTab
	}

	def setNormalTab( normalTab: Option[TabTheme]) 	={
		 this.normalTab = normalTab
		 this.keyModified("normal_tab") = 1
	}

	def getSelectedTab() :Option[TabTheme]	={
		return  this.selectedTab
	}

	def setSelectedTab( selectedTab: Option[TabTheme]) 	={
		 this.selectedTab = selectedTab
		 this.keyModified("selected_tab") = 1
	}

	def getNewBackground() :Option[String]	={
		return  this.newBackground
	}

	def setNewBackground( newBackground: Option[String]) 	={
		 this.newBackground = newBackground
		 this.keyModified("new_background") = 1
	}

	def getBackground() :Option[String]	={
		return  this.background
	}

	def setBackground( background: Option[String]) 	={
		 this.background = background
		 this.keyModified("background") = 1
	}

	def getScreen() :Option[String]	={
		return  this.screen
	}

	def setScreen( screen: Option[String]) 	={
		 this.screen = screen
		 this.keyModified("screen") = 1
	}

	def getType() :Option[String]	={
		return  this.type1
	}

	def setType( type1: Option[String]) 	={
		 this.type1 = type1
		 this.keyModified("type") = 1
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