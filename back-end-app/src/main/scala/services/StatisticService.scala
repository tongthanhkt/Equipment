package services

import com.twitter.util.jackson.JSON
import models._
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject

class StatisticService  @Inject()(databaseConnection:DatabaseConnection) {

  @throws[Exception]
  def statisticImportEquipmentByCategory(fromDate: Long, toDate: Long):util.ArrayList[ImportEquipmentRecord]={
    val records = new util.ArrayList[ImportEquipmentRecord]()
    val sql =
      """
        |select c.id,count(e.id) as total_import_equipment, if(sum(e.price) is null,0,sum(e.price)) as total_import_cost
        |from equipment e right join category c on c.id=e.category_id
        |										                     and e.import_date <= ?
        |                                        and e.import_date >= ?
        |where c.status != -1
        |group by c.id;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      val e = ImportEquipmentRecord(categoryId = rs.getInt("id"),
        totalImportEquipments = rs.getInt("total_import_equipment"),
        totalImportEquipmentsCost = rs.getDouble("total_import_cost"))
      records.add(e)
    }
    con.close()
    records;
  }

  @throws[Exception]
  def statisticImportEquipment(fromDate: Long, toDate: Long):ImportEquipmentRecord={
    var recordTotal :ImportEquipmentRecord = null
    val sql =
      """
        |select count(*) as total_import_equipment, sum(e.price) as total_import_cost
        |from equipment e
        |where e.import_date <= ? and e.import_date >= ?;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      recordTotal = ImportEquipmentRecord(categoryId = 0,
        totalImportEquipments = rs.getInt("total_import_equipment"),
        totalImportEquipmentsCost = rs.getDouble("total_import_cost"))

    }
    con.close()
    recordTotal;
  }

  @throws[Exception]
  def statisticTakeOverEquipmentByCategory(fromDate: Long, toDate: Long):util.ArrayList[TakeOverEquipmentRecord]={
    val records = new util.ArrayList[TakeOverEquipmentRecord]()
    val sql =
      """
        |select c.id,count(distinct tov.equipment_id) as total_takeover_equipment
        |from equipment e left join takeover_equipment_info tov on tov.equipment_id = e.id
        |and tov.take_over_time <= ?
        |and tov.take_over_time >= ?
        |				right join category c on c.id=e.category_id
        |where c.status != -1
        |group by c.id;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeOverEquipmentRecord(categoryId = rs.getInt("id"),
        totalTakeOverEquipments = rs.getInt("total_takeover_equipment"))
      records.add(e)
    }
    con.close()
    records;
  }

  @throws[Exception]
  def statisticTakeOverEquipment(fromDate: Long, toDate: Long):Int={
    var recordTotal :Int = -1
    val sql =
      """
        |select count(distinct tov.equipment_id ) as total_takeover_equipment
        |from takeover_equipment_info tov
        |where tov.take_over_time <= ? and tov.take_over_time >= ?;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      recordTotal = rs.getInt("total_takeover_equipment")
    }
    con.close()
    recordTotal;
  }

  @throws[Exception]
  def statisticTakeBackEquipmentByCategory(fromDate: Long, toDate: Long):util.ArrayList[TakeBackEquipmentRecord]={
    val records = new util.ArrayList[TakeBackEquipmentRecord]()
    val sql =
      """
        |select c.id,count(distinct tb.equipment_id) as total_takeback_equipment
        |from equipment e left join takeback_equipment_info tb on tb.equipment_id = e.id
        |														and tb.take_back_time <= ?
        |                                                        and tb.take_back_time >= ?
        |				right join category c on c.id=e.category_id
        |where c.status != -1
        |group by c.id;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeBackEquipmentRecord(categoryId = rs.getInt("id"),
        totalTakeBackEquipments = rs.getInt("total_takeback_equipment"))
      records.add(e)
    }
    con.close()
    records;
  }

  @throws[Exception]
  def statisticTakeBackEquipment(fromDate: Long, toDate: Long):Int={
    var recordTotal :Int = -1
    val sql =
      """
        |select count(distinct tb.equipment_id ) as total_takeback_equipment
        |from takeback_equipment_info tb
        |where tb.take_back_time <= ? and tb.take_back_time >= ?;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      recordTotal = rs.getInt("total_takeback_equipment")
    }
    con.close()
    recordTotal;
  }

  @throws[Exception]
  def statisticFixedEquipmentByCategory(fromDate: Long, toDate: Long):util.ArrayList[FixedEquipmentRecord]={
    val records = new util.ArrayList[FixedEquipmentRecord]()
    val sql =
      """
        |select c.id,count(distinct fx.equipment_id) as total_fixing_equipment
        |from equipment e left join fixing_equipment_info fx on fx.equipment_id = e.id
        |													and fx.fixing_time <= ?
        |                                                    and fx.fixing_time >= ?
        |				right join category c on c.id=e.category_id
        |where c.status != -1
        |group by c.id;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      val e = FixedEquipmentRecord(categoryId = rs.getInt("id"),
        totalFixedEquipments = rs.getInt("total_fixing_equipment"))
      records.add(e)
    }
    con.close()
    records;
  }

  @throws[Exception]
  def statisticFixedEquipment(fromDate: Long, toDate: Long):Int={
    var recordTotal :Int = -1
    val sql =
      """
        |select count(distinct fx.equipment_id) as total_fixing_equipment
        |from fixing_equipment_info fx
        |where fx.fixing_time <= ? and fx.fixing_time >= ?;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setLong(1,toDate);
    pst.setLong(2,fromDate);

    val rs = pst.executeQuery
    while(rs.next){
      recordTotal = rs.getInt("total_fixing_equipment")
    }
    con.close()
    recordTotal;
  }

}
