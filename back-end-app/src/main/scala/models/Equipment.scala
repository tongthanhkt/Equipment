package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.io.File
import java.util
import scala.util.control._


case class Page(value:Int,isCurrent:Boolean)

case class Equipment(
                             id: String = null,
                             deviceId: String = null,
                             name: String = null ,
                             startStatus: String = null,
                             price: String = null,
                             depreciatedValue:  String = null,
                             depreciationPeriod:  String = null,
                             periodType: String = null,
                             importDate: String = null,
                             takeOverStatus: String = null ,
                             categoryId: String = null,
                             deviceStatus :String = null,
                             metadataInfo: Map[String, UploadFile]  = Map(),
                             createdBy: String = null,
                             createdTime: String = null,
                             updatedBy: String = null,
                             updatedTime: String = null,
                             takeOverPersonId: String = null,
                             takeOverPersonName : String = null
                           ){
  def checkFitInsert(convertString: ConvertString): Map[Int,String]  ={
    var check : Map[Int,String] =Map()
    if (name == null)
      check = check + (1 -> "The 'Name' field cannot be blank.  ")
    if (startStatus == null)
      check = check + (2 -> "The 'Start Status' field cannot be blank. ")
    else if (!convertString.isInt(startStatus))
     check = check + (3 -> "The 'Start Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(startStatus) && (convertString.toInt(startStatus).get > 7 || convertString.toInt(startStatus).get < 1))
      check = check + (4 -> "Start status of device is incorrect. ")

    if (price == null)
      check = check + (5 -> "The 'Price' field cannot be blank. ")
    else if (!convertString.isDouble(price))
      check = check + (6 -> "The 'Price' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(price) && (convertString.toDouble(price).get < 0))
      check = check + (7 -> "Price of device must be > 0. ")

    if (depreciatedValue == null)
      check = check + (8 -> "The 'Depreciated Value' field cannot be blank. ")
    else if (!convertString.isDouble(depreciatedValue))
      check = check + (9 -> "The 'Depreciated Value' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(depreciatedValue) && (convertString.toDouble(depreciatedValue).get > 1 || convertString.toDouble(depreciatedValue).get < 0))
      check = check + (10 -> "'Depreciated Value' must be > 0")

    if (depreciationPeriod == null)
      check = check + (11 -> "The 'Depreciation Period' field cannot be blank. ")
    else if (!convertString.isDouble(depreciationPeriod))
      check = check + (12 -> "The 'Depreciation Period' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(depreciationPeriod) && (convertString.toDouble(depreciationPeriod).get < 0))
      check = check + (13 -> "'Depreciation Period' of device is incorrect. ")

    if (periodType == null)
      check = check + (14 -> "The 'Period Type' field cannot be blank. ")
    else if (!convertString.isInt(periodType))
      check = check + (15 -> "The 'Period Type' field : type mismatch, required : Int. ")
    else if (convertString.isInt(periodType) && (convertString.toInt(periodType).get > 2 || convertString.toInt(periodType).get < 1))
      check = check + (16 -> "Period Type of device is incorrect. ")

    if (importDate == null)
      check = check + (17 -> "The 'Import Date' field cannot be blank. ")
    else if (!convertString.isLong(importDate))
      check = check + (18 -> "The 'Import Date' field : type mismatch, required : Long. ")
    else if (convertString.isLong(importDate) && (convertString.toLong(importDate).get < 0 ))
      check = check + (19 -> "Import Date of device is incorrect. ")

    if (categoryId == null)
      check = check + (20 -> "The 'Category Id' field cannot be blank. ")
    else if (!convertString.isInt(categoryId))
      check = check + (21 -> "The 'Category Id' field : type mismatch, required : Int. ")

    if (deviceStatus == null)
      check = check + (22 -> "The 'Device Status' field cannot be blank.  ")
    else if (!convertString.isInt(deviceStatus))
      check = check + (23-> "The 'Device Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(deviceStatus) && (convertString.toInt(deviceStatus).get > 2 || convertString.toInt(deviceStatus).get < -1))
      check = check + (24 -> "Start device of device is incorrect. ")

    if (createdBy == null)
      check = check + (25 -> "There is not information of created person. ")

    if (metadataInfo.nonEmpty){
      val loop = new Breaks;
      loop.breakable {
      for (key <- metadataInfo.keys){
        if (metadataInfo(key).file_description == null ) {
          check = check +  (26 -> s" File must have description. ")
         loop.break
        }
      }}
    }
     return check
  }
  def checkFitUpdate(convertString: ConvertString): Map[Int,String] ={
    var check : Map[Int,String] =Map()
    if (id == null)
      check = check + (0 -> s"The 'Id' field cannot be blank. ")
     if (startStatus != null && !convertString.isInt(startStatus))
      check = check + (3 -> s"The 'Start Status' field : type mismatch, required : Int. ")
    else if (convertString.isInt(startStatus) && (convertString.toInt(startStatus).get > 7 || convertString.toInt(startStatus).get < 1))
      check = check + (4 -> s"Start status of device is incorrect. ")


    if (price!= null && !convertString.isDouble(price))
      check = check + (6 -> "The 'Price' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(price) && (convertString.toDouble(price).get < 0))
      check = check + (7 -> "Price of device must be > 0. ")


    if (depreciatedValue != null && !convertString.isDouble(depreciatedValue))
      check = check + (9 -> "The 'Depreciated Value' field : type mismatch, required : Double.  ")
    else if (convertString.isDouble(depreciatedValue) && (convertString.toDouble(depreciatedValue).get > 1 || convertString.toDouble(depreciatedValue).get < 0))
      check = check + (10 -> "'Depreciated Value' must be > 0. ")

    if (depreciationPeriod != null && !convertString.isDouble(depreciationPeriod))
      check = check + (12 -> "The 'Depreciation Period' field : type mismatch, required : Double. ")
    else if (convertString.isDouble(depreciationPeriod) && (convertString.toDouble(depreciationPeriod).get < 0))
      check = check + (13 -> "'Depreciation Period' of device is incorrect. ")

    if (periodType != null && !convertString.isInt(periodType))
      check = check + (15 -> "The 'Period Type' field : type mismatch, required : Int. ")
    else if (convertString.isInt(periodType) && (convertString.toInt(periodType).get > 2 || convertString.toInt(periodType).get < 1))
      check = check + (16 -> "Period Type of device is incorrect. ")

    if (importDate != null && !convertString.isLong(importDate))
      check = check + (18 -> "The 'Import Date' field : type mismatch, required : Long. ")
    else if (convertString.isLong(importDate) && (convertString.toLong(importDate).get < 0 ))
      check = check + (19 -> "Import Date of device is incorrect. ")


    if (categoryId != null && !convertString.isInt(categoryId))
      check = check + (21 -> "The 'Category Id' field : type mismatch, required : Int. ")

    if (deviceStatus != null && !convertString.isInt(deviceStatus))
      check = check + (23-> "The 'Device Status' field : type mismatch, required : Int.  ")
    else if (convertString.isInt(deviceStatus) && (convertString.toInt(deviceStatus).get > 2 || convertString.toInt(deviceStatus).get < -1))
      check = check + (24 -> "Start device of device is incorrect. ")

    if (updatedBy == null)
      check = check + (27 -> "There is not information of updated person. ")


    if (metadataInfo.nonEmpty){
      val loop = new Breaks;
      loop.breakable {
      for (key <- metadataInfo.keys){
        if (metadataInfo(key).file_description == null ) {
          check = check +  (26 -> s" Files must have description. ")
          loop.break
        }
      }}
    }
    return check
  }
}

case class SearchEquipmentsResponse(equipments: util.ArrayList[Equipment],
                                   empty: Boolean,
                                    nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int)
case class CountEquipmentsResponse(
                                    totalEquipments: Int,
                                    totalTakeOverEquipments: Int,
                                    totalInventoryEquipments: Int,
                                    totalDamagedEquipments : Int,
                                    totalLostEquipments: Int,
                                    )

case class DeleteEquipmentRequest(@QueryParam id:Int)

case class SearchEquipmentByIdRequest(@RouteParam id :Int)

case class UploadFile(file_url : String,
                      file_name: String,
                      size : Long,
                      file_extension: String,
                      file_description: String = null,
                )

case class DeleteImageByIdRequest(@RouteParam equipmentId :Int,
                                  @QueryParam imageName:Int)

case class SearchRequest (@QueryParam keyword: String = null,
                           @QueryParam categoryId: String = null,
                           @QueryParam takeOverPerson: String = null,
                           @QueryParam takeOverStatus: String = null,
                            @QueryParam deviceStatus :String = null,
                           @QueryParam page: Int = 1,
                           @QueryParam limit: Int = 10)








