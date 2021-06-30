package com.zoho.crm.api.users

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class TabTheme extends Model	{
	private var fontColor:Option[String] = None
	private var background:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getFontColor() :Option[String]	={
		return  this.fontColor
	}

	def setFontColor( fontColor: Option[String]) 	={
		 this.fontColor = fontColor
		 this.keyModified("font_color") = 1
	}

	def getBackground() :Option[String]	={
		return  this.background
	}

	def setBackground( background: Option[String]) 	={
		 this.background = background
		 this.keyModified("background") = 1
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