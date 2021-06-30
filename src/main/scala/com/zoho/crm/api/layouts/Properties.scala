package com.zoho.crm.api.layouts

import com.zoho.crm.api.fields.ToolTip
import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class Properties extends Model	{
	private var reorderRows:Option[Boolean] = None
	private var tooltip:Option[ToolTip] = None
	private var maximumRows:Option[Int] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getReorderRows() :Option[Boolean]	={
		return  this.reorderRows
	}

	def setReorderRows( reorderRows: Option[Boolean]) 	={
		 this.reorderRows = reorderRows
		 this.keyModified("reorder_rows") = 1
	}

	def getTooltip() :Option[ToolTip]	={
		return  this.tooltip
	}

	def setTooltip( tooltip: Option[ToolTip]) 	={
		 this.tooltip = tooltip
		 this.keyModified("tooltip") = 1
	}

	def getMaximumRows() :Option[Int]	={
		return  this.maximumRows
	}

	def setMaximumRows( maximumRows: Option[Int]) 	={
		 this.maximumRows = maximumRows
		 this.keyModified("maximum_rows") = 1
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