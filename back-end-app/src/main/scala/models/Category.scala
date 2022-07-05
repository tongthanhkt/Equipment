package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.io.File
import java.util
import scala.util.control._




case class Category(
                      id: String = null,
                      name: String = null ,
                      status: String = null,
                      createdBy: String = null,
                      createdTime: String = null,
                      updatedBy: String = null,
                      updatedTime: String = null,
                    ){
  def checkFitInsert(convertString: ConvertString): Map[Int,String]  ={
    var check : Map[Int,String] =Map()
    if (name == null || name == "")
      check = check + (1 -> "The 'Name' field cannot be blank.  ")
    if (status == null)
      check = check + (2 -> "The 'Status' field cannot be blank. ")
    else if (!convertString.isInt(status))
      check = check + (3 -> "The 'Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(status) && (convertString.toInt(status).get > 1 || convertString.toInt(status).get < 0))
      check = check + (4 -> "Status of category is incorrect. ")

    if (createdBy == null)
      check = check + (5 -> "There is not information of created person. ")

    check
  }
  def checkFitUpdate(convertString: ConvertString): Map[Int,String] ={
    var check : Map[Int,String] =Map()
    if (id == null)
      check = check + (0 -> s"The 'Id' field cannot be blank. ")
    if (name == null || name == "")
      check = check + (1 -> "The 'Name' field cannot be blank.  ")
    if (status != null && !convertString.isInt(status))
      check = check + (3 -> s"The 'Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(status) && (convertString.toInt(status).get > 1 || convertString.toInt(status).get < 0))
      check = check + (4 -> s"Status of category is incorrect. ")
    if (updatedBy == null)
      check = check + (6 -> "There is not information of updated person. ")
    check
  }
}


case class DeleteCategoryRequest(@QueryParam id:Int)

case class SearchCategoryByIdRequest(@RouteParam id :Int)

case class SearchCategoryRequest (@QueryParam name: String = null,
                          @QueryParam status: String = null)








