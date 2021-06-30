package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import scala.collection.mutable.HashMap

class FileDetails extends Model	{
	private var extn:Option[String] = None
	private var isPreviewAvailable:Option[Boolean] = None
	private var downloadUrl:Option[String] = None
	private var deleteUrl:Option[String] = None
	private var entityId:Option[String] = None
	private var mode:Option[String] = None
	private var originalSizeByte:Option[String] = None
	private var previewUrl:Option[String] = None
	private var fileName:Option[String] = None
	private var fileId:Option[String] = None
	private var attachmentId:Option[String] = None
	private var fileSize:Option[String] = None
	private var creatorId:Option[String] = None
	private var linkDocs:Option[Int] = None
	private var delete:Option[String] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getExtn() :Option[String]	={
		return  this.extn
	}

	def setExtn( extn: Option[String]) 	={
		 this.extn = extn
		 this.keyModified("extn") = 1
	}

	def getIsPreviewAvailable() :Option[Boolean]	={
		return  this.isPreviewAvailable
	}

	def setIsPreviewAvailable( isPreviewAvailable: Option[Boolean]) 	={
		 this.isPreviewAvailable = isPreviewAvailable
		 this.keyModified("is_Preview_Available") = 1
	}

	def getDownloadUrl() :Option[String]	={
		return  this.downloadUrl
	}

	def setDownloadUrl( downloadUrl: Option[String]) 	={
		 this.downloadUrl = downloadUrl
		 this.keyModified("download_Url") = 1
	}

	def getDeleteUrl() :Option[String]	={
		return  this.deleteUrl
	}

	def setDeleteUrl( deleteUrl: Option[String]) 	={
		 this.deleteUrl = deleteUrl
		 this.keyModified("delete_Url") = 1
	}

	def getEntityId() :Option[String]	={
		return  this.entityId
	}

	def setEntityId( entityId: Option[String]) 	={
		 this.entityId = entityId
		 this.keyModified("entity_Id") = 1
	}

	def getMode() :Option[String]	={
		return  this.mode
	}

	def setMode( mode: Option[String]) 	={
		 this.mode = mode
		 this.keyModified("mode") = 1
	}

	def getOriginalSizeByte() :Option[String]	={
		return  this.originalSizeByte
	}

	def setOriginalSizeByte( originalSizeByte: Option[String]) 	={
		 this.originalSizeByte = originalSizeByte
		 this.keyModified("original_Size_Byte") = 1
	}

	def getPreviewUrl() :Option[String]	={
		return  this.previewUrl
	}

	def setPreviewUrl( previewUrl: Option[String]) 	={
		 this.previewUrl = previewUrl
		 this.keyModified("preview_Url") = 1
	}

	def getFileName() :Option[String]	={
		return  this.fileName
	}

	def setFileName( fileName: Option[String]) 	={
		 this.fileName = fileName
		 this.keyModified("file_Name") = 1
	}

	def getFileId() :Option[String]	={
		return  this.fileId
	}

	def setFileId( fileId: Option[String]) 	={
		 this.fileId = fileId
		 this.keyModified("file_Id") = 1
	}

	def getAttachmentId() :Option[String]	={
		return  this.attachmentId
	}

	def setAttachmentId( attachmentId: Option[String]) 	={
		 this.attachmentId = attachmentId
		 this.keyModified("attachment_Id") = 1
	}

	def getFileSize() :Option[String]	={
		return  this.fileSize
	}

	def setFileSize( fileSize: Option[String]) 	={
		 this.fileSize = fileSize
		 this.keyModified("file_Size") = 1
	}

	def getCreatorId() :Option[String]	={
		return  this.creatorId
	}

	def setCreatorId( creatorId: Option[String]) 	={
		 this.creatorId = creatorId
		 this.keyModified("creator_Id") = 1
	}

	def getLinkDocs() :Option[Int]	={
		return  this.linkDocs
	}

	def setLinkDocs( linkDocs: Option[Int]) 	={
		 this.linkDocs = linkDocs
		 this.keyModified("link_Docs") = 1
	}

	def getDelete() :Option[String]	={
		return  this.delete
	}

	def setDelete( delete: Option[String]) 	={
		 this.delete = delete
		 this.keyModified("_delete") = 1
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