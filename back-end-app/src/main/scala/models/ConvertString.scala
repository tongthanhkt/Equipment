package models

class ConvertString {
  def toBigInt(s: String): Option[Long] = {
    try {
      Some(s.toLong)
    } catch {
      case e: Exception => None
    }
  }
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
  def toDouble(s: String): Option[Double] = {
    try {
      Some(s.toDouble)
    } catch {
      case e: Exception => None
    }
  }
  def toLong(s: String): Option[Long] = {
    try {
      Some(s.toLong)
    } catch {
      case e: Exception => None
    }
  }

  def isInt(s: String) : Boolean ={
    toInt(s) match {
      case Some(i) => true
      case None => false
    }
  }
  def isDouble(s: String) : Boolean ={
    toDouble(s) match {
      case Some(i) => true
      case None => false
    }
  }
  def isLong(s: String) : Boolean ={
    toLong(s) match {
      case Some(i) => true
      case None => false
    }
  }

}
