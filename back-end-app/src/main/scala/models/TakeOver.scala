package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.util

case class TakeOver(
                     id: String = null,
                     equipmentId: String = null,
                     deviceId:String=null,
                     name:String=null,
                     username: String = null,
                     takeOverTime: String = null,
                     status: String = null,
                     verifier: String = null,
                     takeOverPerson: String = null,
                     typeTakeOver: String = null,
                     message: String = null,
                     cost: String = null,
                     createdBy: String = null,
                     createdTime: String = null,
                     updatedBy: String = null,
                     updatedTime: String = null,
                     metadataInfo: Map[String, UploadFile]  = null,
                     takeBackStatus:String=null
                   ){
  def checkDataInsert(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()
    if(equipmentId==null||equipmentId=="") check=check + (1->"The field 'equipmentId' cannot be blank . ")
    if(username==null||username=="")       check=check + (2->"The field 'username' cannot be blank . ")
    if(takeOverTime==null||takeOverTime=="")
      check=check + (3->"The field 'takeOverTime' cannot be blank . ")
    else if(!convertString.isLong(takeOverTime))
      check = check + (4 -> "The 'TakeOver Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(takeOverTime) && (convertString.toLong(takeOverTime).get < 0 ))
      check = check + (5 -> "TakeOver Time of device is incorrect. ")
    if(verifier==null||verifier=="") check=check + (6->"The field 'verifier' cannot be blank . ")
    if(takeOverPerson==null||takeOverPerson=="") check=check + (7->"The field 'takeOverPerson' cannot be blank . ")
    if(createdBy==null||createdBy=="") check=check + (8->"The field 'createdBy' cannot be blank . ")


    if(typeTakeOver==null||typeTakeOver=="") check=check + (9->"The field 'Type' cannot be blank . ")
    else if (!convertString.isInt(typeTakeOver)) check = check + (10 -> "The 'TakeOver Time' field : type mismatch, required : Int. ")
    else if (convertString.isInt(typeTakeOver)&&(convertString.toInt(typeTakeOver).get>2)||convertString.toInt(typeTakeOver).get<1)
                                             check=check + (11 -> "The typeTakeOver is incorrect ! ")

    if (cost!= null && !convertString.isDouble(cost))
      check = check + (15 -> "The 'Cost' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (16 -> "Cost of take over must be > 0. ")
    return check
  }
  def checkDataUpdate(convertString: ConvertString):Map[Int,String]={

    var check:Map[Int,String] = Map()

    if(username=="") check=check + (2->"The field 'username' cannot be blank . ")
    if(takeOverTime=="")
      check=check + (3->"The field 'takeOverTime' cannot be blank . ")
    else if(!convertString.isLong(takeOverTime))
      check = check + (4 -> "The 'TakeOver Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(takeOverTime) && (convertString.toLong(takeOverTime).get < 0 ))
      check = check + (5 -> "TakeOver Time of device is incorrect. ")

    if(verifier=="") check=check + (6->"The field 'verifier' cannot be blank . ")
    if(takeOverPerson=="") check=check + (7->"The field 'takeOverPerson' cannot be blank . ")
    if(updatedBy=="") check=check + (8->"The field 'update By' cannot be blank . ")

    if(typeTakeOver=="") check=check + (9->"The field 'Type' cannot be blank . ")
    else if (!convertString.isInt(typeTakeOver)) check = check + (10 -> "The 'TakeOver Time' field : type mismatch, required : Int. ")
    else if (convertString.isInt(typeTakeOver)&&(convertString.toInt(typeTakeOver).get>2)||convertString.toInt(typeTakeOver).get<1)
      check=check + (11 -> "The typeTakeOver is incorrect ! ")



    if (cost!=null && !convertString.isDouble(cost))
      check = check + (15 -> "The 'Cost' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (16 -> "Cost of take over must be > 0. ")


    return check
  }
}

case class SearchTakeOverRequest(
                                  @QueryParam username: String = null,
                                  @QueryParam takeOverPerson: String = null,
                                  @QueryParam typeTakeOver: String = null,
                                  @QueryParam status: String = null,
                                  @QueryParam equipmentId: String = null,
                                  @QueryParam page: Int = 1,
                                  @QueryParam limit: Int = 10
                                )
case class SearchUserRequest(@QueryParam keyword:String=null)
case class SearchUserResponse(@QueryParam userList:util.ArrayList[User])
case class SearchTakeOverResponse(
                                   takeOverList: util.ArrayList[TakeOver],empty: Boolean,
                                   nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int
                                 )

case class SearchTakeOverByIdRequest(@RouteParam id: Int)
case class SearchTakeOverByIdEquipmentRequest(@RouteParam id: Int)
case class DeleteTakeOverRequest(@QueryParam id:Int)
