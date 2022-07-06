package models

import com.twitter.finatra.http.annotations.QueryParam

case class Statistic(records: Map[Int, Record])

case class Record(totalImportEquipments: Int,
                  totalImportEquipmentsCost: Double,
                  totalTakeOverEquipments: Int,
                  totalTakeBackEquipments:Int,
                  totalFixedEquipment:Int)

case class ImportEquipmentRecord(categoryId : Int,
                                 totalImportEquipments: Int,
                                 totalImportEquipmentsCost: Double)

case class TakeOverEquipmentRecord(categoryId : Int,
                                 totalTakeOverEquipments: Int)

case class TakeBackEquipmentRecord(categoryId : Int,
                                 totalTakeBackEquipments: Int)

case class FixedEquipmentRecord(categoryId : Int,
                                 totalFixedEquipments: Int)

case class StatisticRequest (@QueryParam fromDate:String = null,
                                  @QueryParam toDate: String = null)