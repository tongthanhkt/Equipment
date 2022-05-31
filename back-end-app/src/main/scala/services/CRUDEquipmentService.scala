package services

import com.fasterxml.jackson.databind.util.JSONPObject
import com.google.gson.Gson
import models.Equipment
import utils.DataConnection

import java.sql.SQLException
import java.util
import scala.util.parsing.json.JSONObject


class CRUDEquipmentService {
  def search(keyword:String,category:String,take_over_person:String,limit:Int,offset: Int): util.ArrayList[Equipment] ={

    var equipments = new util.ArrayList[Equipment]
    try{
      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ?
      and (match(e.name,e.device_id) against (?) or ? is null)
      and (e.category_id = ? or ? is null)
      and (match(u.username,u.fullname) against (?) or ? is null)

      LIMIT ? OFFSET ?;"""
      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setString(2, keyword)
      pst.setString(3, keyword)
      pst.setString(4, category)
      pst.setString(5,category)
      pst.setString(6, take_over_person)
      pst.setString(7, take_over_person)
      pst.setInt(8,limit)
      pst.setInt(9,offset)
      val rs = pst.executeQuery
      while ( rs.next) {
        val e = Equipment(id=rs.getInt("id"),
          device_id=rs.getString("device_id"),
          name=rs.getString("name"),
          start_status=rs.getInt("start_status"),
          price = rs.getDouble("price"),
          import_date = rs.getLong("import_date"),
          takeover_status=rs.getInt("takeover_status"),
          category_id = rs.getInt("category_id"),
          device_status = rs.getInt("device_status"),
          created_by = rs.getString("created_by"),
          created_time = rs.getLong("created_time"),
          updated_by = rs.getString("updated_by"),
          updated_time = rs.getLong("updated_time"),
          takeover_person_id = rs.getString("username"),
          takeover_person_name = rs.getString("fullname"));


        equipments.add(e)
      }
      con.close();
      return equipments

    }catch {
      case ex: SQLException =>{
        println(ex)
        return new util.ArrayList[Equipment]
      }
    }
  }

  def countBySearch(keyword:String,category:String,take_over_person:String): Int ={


    try{
      val sql = """
      SELECT count(*) as total
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE device_status != ?
      and (match(e.name,e.device_id) against (?) or ? is null)
      and (e.category_id = ? or ? is null)
      and (match(u.username,u.fullname) against (?) or ? is null)

      """
      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setString(2, keyword)
      pst.setString(3, keyword)
      pst.setString(4, category)
      pst.setString(5,category)
      pst.setString(6, take_over_person)
      pst.setString(7, take_over_person)

      val rs = pst.executeQuery
      var total =0
      while ( rs.next) {
        total = rs.getInt("total")
      }
      con.close();
      return total

    }catch {
      case ex: SQLException =>{
        println(ex)
        return -1

      }
    }
  }

  def deleteById(equipment_id:Int): Int = {
    try{
      val sql = "UPDATE equipment SET device_status = -1 WHERE id = ?;"

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1, equipment_id)


      val rs = pst.executeUpdate()

      con.close();
      return rs

    }catch {
      case ex: SQLException =>{
        println(ex)
        return -1

      }
    }

  }
}


object test {
  def main (args :Array[String]): Unit ={

//    var result= (new CRUDEquipmentService).findAll("tuongvy",5,0)
//    println(result)
//    if (con == null){
//      println("Cannot get connect with database")
//    }
//    else {
//      val SQL_QUERY = "select * from cau_thu"
//      val pst = con.prepareStatement(SQL_QUERY)
//      val rs = pst.executeQuery
//      while ( rs.next) {
//        println(rs.getString("TenCauThu"))
//      }
//    }
//    println("Cannot get connect with database")
//    println("Cannot get connect with database")
  }
}
