package models



import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.util

case class FixEquipment(
                     id: String = null,
                     equipmentId: String = null,
                     deviceId:String=null,
                     equipmentName:String=null,
                     fixer: String = null,
                     fixingTime: String = null,
                     status: String = null,
                     message: String = null,
                     cost: String = null,
                     createdBy: String = null,
                     createdTime: String = null,
                     updatedBy: String = null,
                     updatedTime: String = null,
                     metadataInfo: Map[String, UploadFile]  = null,
                   ){
  def checkFitInsert(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()
    if(equipmentId==null || equipmentId.isEmpty) check=check + (1->"The field 'equipmentId' cannot be blank . ")

    if(fixer==null || fixer.isEmpty) check=check + (2->"The field 'fixer' cannot be blank . ")

    if(fixingTime==null)
      check=check + (3->"The field 'fixingTime' cannot be blank . ")
    else if(!convertString.isLong(fixingTime))
      check = check + (4 -> "The 'Fixing Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(fixingTime) && (convertString.toLong(fixingTime).get < 0 ))
      check = check + (5 -> "Fixing Time of device is incorrect. ")

    if (status == null)
      check = check + (6 -> "The 'Status' field cannot be blank. ")
    else if (!convertString.isInt(status))
      check = check + (7 -> "The 'Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(status) && (convertString.toInt(status).get > 1 || convertString.toInt(status).get < -1))
      check = check + (8 -> "Fixing status is incorrect. ")

    if (cost!= null && !convertString.isDouble(cost))
      check = check + (9 -> "The 'cost' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (10 -> "Fixing cost of device must be > 0. ")

    if(createdBy==null || createdBy.isEmpty) check=check + (11->"The field 'createdBy' cannot be blank . ")

    return check
  }
  def checkFitUpdate(convertString: ConvertString):Map[Int,String]={
    var check:Map[Int,String] = Map()
    if(id==null || id.isEmpty) check=check + (0->"The field 'Id' cannot be blank . ")
    if(fixer==null || fixer.isEmpty) check=check + (2->"The field 'fixer' cannot be blank . ")

    if(fixingTime==null)
      check=check + (3->"The field 'fixingTime' cannot be blank . ")
    else if(!convertString.isLong(fixingTime))
      check = check + (4 -> "The 'Fixing Time' field : type mismatch, required : Long. ")
    else if (convertString.isLong(fixingTime) && (convertString.toLong(fixingTime).get < 0 ))
      check = check + (5 -> "Fixing Time of device is incorrect. ")

    if (status == null)
      check = check + (6 -> "The 'Status' field cannot be blank. ")
    else if (!convertString.isInt(status))
      check = check + (7 -> "The 'Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(status) && (convertString.toInt(status).get > 1 || convertString.toInt(status).get < -1))
      check = check + (8 -> "Fixing status is incorrect. ")

    if (cost!= null && !convertString.isDouble(cost))
      check = check + (9 -> "The 'cost' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(cost) && (convertString.toDouble(cost).get < 0))
      check = check + (10 -> "Fixing cost of device must be > 0. ")

    if(updatedBy==null || updatedBy.isEmpty) check=check + (12->"The field 'updatedBy' cannot be blank . ")

    return check
  }
}

case class SearchFixEquipmentRequest(
                                  @QueryParam fixer: String = null,
                                  @QueryParam status: String = null,
                                  @QueryParam equipmentId: String = null,
                                  @QueryParam deviceId: String = null,
                                  @QueryParam equipmentName: String = null,
                                  @QueryParam page: Int = 1,
                                  @QueryParam limit: Int = 10
                                )

case class SearchFixEquipmentResponse(
                                   fixEquipmentList: util.ArrayList[FixEquipment],
                                   empty: Boolean,
                                   nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int
                                 )

case class SearchFixEquipmentByIdRequest(@RouteParam id: String)
case class DeleteFixEquipmentRequest(@QueryParam id:Int)

