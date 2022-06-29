package scala.models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import models.{ConvertString, Page, TakeOver, UploadFile, User}

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
    if(equipmentId==null) check=check + (1->"The field 'equipmentId' cannot be blank . ")

    if(username==null) check=check + (2->"The field 'username' cannot be blank . ")
    if(takeBackTime==null)
      check=check + (3->"The field 'takeOverTime' cannot be blank . ")
    else if(!convertString.isLong(takeBackTime))
      check = check + (4 -> "The 'TakeOver Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(takeBackTime) && (convertString.toLong(takeBackTime).get < 0 ))
      check = check + (5 -> "TakeOver Time of device is incorrect. ")

    if(status==null) (6->"The field 'status' cannot be blank . ")
    else if(!convertString.isInt(status)) check=check + (7 -> "The 'Start Status' field : type mismatch, required : Int. ")

    if(verifier==null) check=check + (8->"The field 'verifier' cannot be blank . ")


    if(takeBackPerson==null) check=check + (9->"The field 'takeOverPerson' cannot be blank . ")

    if(typeTakeBack==null) check=check + (10->"The field 'Type' cannot be blank . ")
    else if (!convertString.isInt(typeTakeBack)) check=check + (11 -> "The 'Start Status' field : type mismatch, required : Int. ")


    if(createdBy==null) check=check + (12->"The field 'createdBy' cannot be blank . ")

    return check
  }
  def checkDataUpdate(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()

    if(username=="") check=check + (14->"The field 'username' cannot be blank . ")

    if(takeBackTime=="") check=check + (15->"The field 'takeOverTime' cannot be blank . ")
    else if(!convertString.isLong(takeBackTime))
      check = check + (16 -> "The 'TakeOver Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(takeBackTime) && (convertString.toLong(takeBackTime).get < 0 ))
      check = check + (17 -> "TakeOver Time of device is incorrect. ")


    if(verifier=="") check=check + (19->"The field 'verifier' cannot be blank . ")


    if(takeBackPerson=="") check=check + (20->"The field 'takeOverPerson' cannot be blank . ")

    if(typeTakeBack=="") check=check + (21->"The field 'Type' cannot be blank . ")
    else if (!convertString.isInt(typeTakeBack)) check=check + (3 -> "The 'Start Status' field : type mismatch, required : Int. ")

    if(updatedBy==null) check=check + (23->"The field 'update By' cannot be blank . ")
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
