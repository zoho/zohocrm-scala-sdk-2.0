package com.zoho.crm.api.users

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class CustomizeInfo extends Model	{
	private var notesDesc:Option[Boolean] = None
	private var showRightPanel:Option[String] = None
	private var bcView:Option[String] = None
	private var showHome:Option[Boolean] = None
	private var showDetailView:Option[Boolean] = None
	private var unpinRecentItem:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getNotesDesc() :Option[Boolean]	={
		return  this.notesDesc
	}

	def setNotesDesc( notesDesc: Option[Boolean]) 	={
		 this.notesDesc = notesDesc
		 this.keyModified("notes_desc") = 1
	}

	def getShowRightPanel() :Option[String]	={
		return  this.showRightPanel
	}

	def setShowRightPanel( showRightPanel: Option[String]) 	={
		 this.showRightPanel = showRightPanel
		 this.keyModified("show_right_panel") = 1
	}

	def getBcView() :Option[String]	={
		return  this.bcView
	}

	def setBcView( bcView: Option[String]) 	={
		 this.bcView = bcView
		 this.keyModified("bc_view") = 1
	}

	def getShowHome() :Option[Boolean]	={
		return  this.showHome
	}

	def setShowHome( showHome: Option[Boolean]) 	={
		 this.showHome = showHome
		 this.keyModified("show_home") = 1
	}

	def getShowDetailView() :Option[Boolean]	={
		return  this.showDetailView
	}

	def setShowDetailView( showDetailView: Option[Boolean]) 	={
		 this.showDetailView = showDetailView
		 this.keyModified("show_detail_view") = 1
	}

	def getUnpinRecentItem() :Option[String]	={
		return  this.unpinRecentItem
	}

	def setUnpinRecentItem( unpinRecentItem: Option[String]) 	={
		 this.unpinRecentItem = unpinRecentItem
		 this.keyModified("unpin_recent_item") = 1
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