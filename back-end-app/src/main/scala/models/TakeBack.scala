package scala.models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import models.{ConvertString, Page, UploadFile, User}

import java.util

case class TakeBack(
                     id: String = null,
                     takeOverId:String=null,
                     equipmentId: String = null,
                     deviceId:String=null,
                     name:String=null,
                     username: String = null,
                     takeBackTime: String = null,
                     status: String = null,
                     verifier: String = null,
                     takeBackPerson: String = null,
                     typeTakeBack: String = null,
                     message: String = null,
                     cost: String = null,
                     createdBy: String = null,
                     createdTime: String = null,
                     updatedBy: String = null,
                     updatedTime: String = null,
                     metadataInfo: Map[String, UploadFile]  = null
                   ){
  def checkDataInsert(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()

    if(takeBackTime==null||takeBackTime=="")
      check=check + (3->"The field 'takeBackTime' cannot be blank . ")
    else if(!convertString.isLong(takeBackTime))
      check = check + (4 -> "The 'takeBackTime' field : type mismatch, required : Long. ")
    else if (convertString.isLong(takeBackPerson) && (convertString.toLong(takeBackPerson).get < 0 ))
      check = check + (5 -> "takeBackTime of device is incorrect. ")
    if(verifier==null||verifier=="") check=check + (6->"The field 'verifier' cannot be blank . ")
    if(takeBackPerson==null||takeBackPerson=="") check=check + (7->"The field 'takeBackPerson' cannot be blank . ")
    if(createdBy==null||createdBy=="") check=check + (8->"The field 'createdBy' cannot be blank . ")

    if(typeTakeBack==null||typeTakeBack=="") check=check + (9->"The field 'typeTakeBack' cannot be blank . ")
    else if (!convertString.isInt(typeTakeBack)) check = check + (10 -> "The 'typeTakeBack' field : type mismatch, required : Int. ")
    else if (convertString.isInt(typeTakeBack)&&(convertString.toInt(typeTakeBack).get>4)||convertString.toInt(typeTakeBack).get<1)
      check=check + (11 -> "The typeTakeBack is incorrect ! ")

    if (cost!= null && !convertString.isDouble(cost))
      check = check + (15 -> "The 'Cost' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (16 -> "Cost of take over must be > 0. ")
    return check
  }
  def checkDataUpdate(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()
    if(username=="") check=check + (2->"The field 'username' cannot be blank . ")

    if(takeBackTime=="")
      check=check + (3->"The field 'takeBackTime' cannot be blank . ")
    else if(takeBackTime!= null && !convertString.isLong(takeBackTime))
      check = check + (4 -> "The 'takeBackTime Time' field : type mismatch, required : Long. ")
    else if (takeBackTime!= null && convertString.isLong(takeBackTime) && (convertString.toLong(takeBackTime).get < 0 ))
      check = check + (5 -> "takeBackTime of device is incorrect. ")

    if(verifier=="") check=check + (6->"The field 'verifier' cannot be blank . ")
    if(takeBackPerson=="") check=check + (7->"The field 'takeBackPerson' cannot be blank . ")
    if(updatedBy=="") check=check + (8->"The field 'update By' cannot be blank . ")
    println("bug1")
    if(typeTakeBack=="" ) check=check + (9->"The field 'typeTakeBack' cannot be blank . ")
    else if (typeTakeBack != null && !convertString.isInt(typeTakeBack)) check = check + (10 -> "typeTakeBack' field : type mismatch, required : Int. ")
    else if (typeTakeBack != null && convertString.isInt(typeTakeBack))
      if((convertString.toInt(typeTakeBack).get>4)||convertString.toInt(typeTakeBack).get<1)
      check=check + (11 -> "The typeTakeBack is incorrect ! ")
    println("bug2")


    if (cost!=null && !convertString.isDouble(cost))
      check = check + (15 -> "The 'Cost' field : type mismatch, required : Double. ")
    else if (cost!=null && convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (16 -> "Cost of take over must be > 0. ")

    if (status != null && !convertString.isInt(status))
      check = check + (17-> "The 'Status' field : type mismatch, required : Int.  ")
    else if (status != null &&  convertString.isInt(status) && (convertString.toInt(status).get > 1 || convertString.toInt(status).get < 0))
      check = check + (18 -> "Status of take_over record is incorrect. ")
    println("hihihi")
    return check
  }
}
case class SearchTakeBackRequest(
                                  @QueryParam username: String = null,
                                  @QueryParam takeBackPerson: String = null,
                                  @QueryParam typeTakeBack: String = null,
                                  @QueryParam status: String = null,
                                  @QueryParam equipmentId: String = null,
                                  @QueryParam page: Int = 1,
                                  @QueryParam limit: Int = 10
                                )

case class SearchUserResponse(@QueryParam userList:util.ArrayList[User])
case class SearchTakeBackResponse(
                                   takeBackList: util.ArrayList[TakeBack],empty: Boolean,
                                   nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int
                                 )

case class SearchTakeBackByIdRequest(@RouteParam id: Int)
case class SearchTakeBackByIdEquipmentRequest(@RouteParam id: Int)
case class DeleteTakeBackRequest(@QueryParam id:Int)
