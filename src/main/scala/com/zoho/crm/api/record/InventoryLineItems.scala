package com.zoho.crm.api.record

import com.zoho.crm.api.record.Record
import com.zoho.crm.api.util.Model
import scala.collection.mutable.ArrayBuffer

class InventoryLineItems extends Record with Model	{

	def getProduct() :Option[LineItemProduct]	={
		return  this.getKeyValue("product").asInstanceOf[Option[LineItemProduct]]
	}

	def setProduct( product: Option[LineItemProduct]) 	={
		 this.addKeyValue("product", product)
	}

	def getQuantity() :Option[Double]	={
		return  this.getKeyValue("quantity").asInstanceOf[Option[Double]]
	}

	def setQuantity( quantity: Option[Double]) 	={
		 this.addKeyValue("quantity", quantity)
	}

	def getDiscount() :Option[String]	={
		return  this.getKeyValue("Discount").asInstanceOf[Option[String]]
	}

	def setDiscount( discount: Option[String]) 	={
		 this.addKeyValue("Discount", discount)
	}

	def getTotalAfterDiscount() :Option[Double]	={
		return  this.getKeyValue("total_after_discount").asInstanceOf[Option[Double]]
	}

	def setTotalAfterDiscount( totalAfterDiscount: Option[Double]) 	={
		 this.addKeyValue("total_after_discount", totalAfterDiscount)
	}

	def getNetTotal() :Option[Double]	={
		return  this.getKeyValue("net_total").asInstanceOf[Option[Double]]
	}

	def setNetTotal( netTotal: Option[Double]) 	={
		 this.addKeyValue("net_total", netTotal)
	}

	def getBook() :Option[Double]	={
		return  this.getKeyValue("book").asInstanceOf[Option[Double]]
	}

	def setBook( book: Option[Double]) 	={
		 this.addKeyValue("book", book)
	}

	def getTax() :Option[Double]	={
		return  this.getKeyValue("Tax").asInstanceOf[Option[Double]]
	}

	def setTax( tax: Option[Double]) 	={
		 this.addKeyValue("Tax", tax)
	}

	def getListPrice() :Option[Double]	={
		return  this.getKeyValue("list_price").asInstanceOf[Option[Double]]
	}

	def setListPrice( listPrice: Option[Double]) 	={
		 this.addKeyValue("list_price", listPrice)
	}

	def getUnitPrice() :Option[Double]	={
		return  this.getKeyValue("unit_price").asInstanceOf[Option[Double]]
	}

	def setUnitPrice( unitPrice: Option[Double]) 	={
		 this.addKeyValue("unit_price", unitPrice)
	}

	def getQuantityInStock() :Option[Double]	={
		return  this.getKeyValue("quantity_in_stock").asInstanceOf[Option[Double]]
	}

	def setQuantityInStock( quantityInStock: Option[Double]) 	={
		 this.addKeyValue("quantity_in_stock", quantityInStock)
	}

	def getTotal() :Option[Double]	={
		return  this.getKeyValue("total").asInstanceOf[Option[Double]]
	}

	def setTotal( total: Option[Double]) 	={
		 this.addKeyValue("total", total)
	}

	def getProductDescription() :Option[String]	={
		return  this.getKeyValue("product_description").asInstanceOf[Option[String]]
	}

	def setProductDescription( productDescription: Option[String]) 	={
		 this.addKeyValue("product_description", productDescription)
	}

	def getLineTax() :ArrayBuffer[LineTax]	={
		return  this.getKeyValue("line_tax").asInstanceOf[ArrayBuffer[LineTax]]
	}

	def setLineTax( lineTax: ArrayBuffer[LineTax]) 	={
		 this.addKeyValue("line_tax", lineTax)
	}}