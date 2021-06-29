package com.zoho.crm.api.record

import com.zoho.crm.api.util.Model
import java.time.OffsetDateTime
import scala.collection.mutable.HashMap

class Comment extends Model	{
	private var commentedBy:Option[String] = None
	private var commentedTime:Option[OffsetDateTime] = None
	private var commentContent:Option[String] = None
	private var id:Option[Long] = None
	private var keyModified:HashMap[String, Int] = HashMap()

	def getCommentedBy() :Option[String]	={
		return  this.commentedBy
	}

	def setCommentedBy( commentedBy: Option[String]) 	={
		 this.commentedBy = commentedBy
		 this.keyModified("commented_by") = 1
	}

	def getCommentedTime() :Option[OffsetDateTime]	={
		return  this.commentedTime
	}

	def setCommentedTime( commentedTime: Option[OffsetDateTime]) 	={
		 this.commentedTime = commentedTime
		 this.keyModified("commented_time") = 1
	}

	def getCommentContent() :Option[String]	={
		return  this.commentContent
	}

	def setCommentContent( commentContent: Option[String]) 	={
		 this.commentContent = commentContent
		 this.keyModified("comment_content") = 1
	}

	def getId() :Option[Long]	={
		return  this.id
	}

	def setId( id: Option[Long]) 	={
		 this.id = id
		 this.keyModified("id") = 1
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