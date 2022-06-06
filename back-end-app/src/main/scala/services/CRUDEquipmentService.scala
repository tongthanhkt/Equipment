package services

import com.fasterxml.jackson.databind.util.JSONPObject
import com.google.gson.{Gson, JsonArray}
import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.util.jackson.JSON
import jdk.nashorn.internal.objects.NativeJSON
import models.{Equipment, UploadFile}
import net.liftweb.json.JsonAST.RenderSettings.compact
import net.liftweb.json.{DefaultFormats, Serialization, parse}
import org.apache.commons.io.FilenameUtils
import utils.DataConnection

import java.nio.file.{Files, Paths, StandardOpenOption}
import java.sql.{SQLException, SQLType, Types}
import java.util
import scala.util.control.Breaks.break
import scala.util.parsing.json.{JSONArray, JSONObject}


class CRUDEquipmentService {
  def search(keyword:String,category:String,take_over_person:String,takeover_status:String,device_status:String,limit:Int,offset: Int): util.ArrayList[Equipment] ={

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
      and (e.device_status = ? or ? is null)
      and (e.takeover_status = ? or ? is null)
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
      pst.setString(8, device_status)
      pst.setString(9,device_status)
      pst.setString(10, takeover_status)
      pst.setString(11, takeover_status)
      pst.setInt(12,limit)
      pst.setInt(13,offset)
      val rs = pst.executeQuery
      while ( rs.next) {
        val e = Equipment(id=rs.getInt("id"),
          device_id=rs.getString("device_id"),
          name=rs.getString("name"),
          start_status=rs.getInt("start_status"),
          price = rs.getDouble("price"),
          depreciated_value = rs.getDouble("depreciated_value"),
          depreciation_period = rs.getDouble("depreciation_period"),
          period_type = rs.getInt("period_type"),
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

  def countBySearch(keyword:String,category:String,take_over_person:String,takeover_status:String,device_status:String): Int ={


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
      and (e.device_status = ? or ? is null)
      and (e.takeover_status = ? or ? is null)
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
      pst.setString(8, device_status)
      pst.setString(9,device_status)
      pst.setString(10, takeover_status)
      pst.setString(11, takeover_status)

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

  def updateMetaDataById(uploadfiles : util.ArrayList[UploadFile],equipment_id :Int ): Int = {

    try{
      val sql = """UPDATE equipment SET metadata_info = ? WHERE id = ?;"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      val files =JSON.write(uploadfiles)
      pst.setString(1,
        s"""
          |{"files": $files}
          |""".stripMargin)

      pst.setInt(2, equipment_id)
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

  def searchById(equipment_id:Int): Equipment = {
    try{
      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ? and e.id = ?;"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setInt(2, equipment_id)
      val rs = pst.executeQuery()
      var result :Equipment =null
      while ( rs.next) {
        result = Equipment(id=rs.getInt("id"),
          device_id=rs.getString("device_id"),
          name=rs.getString("name"),
          start_status=rs.getInt("start_status"),
          price = rs.getDouble("price"),
          depreciated_value = rs.getDouble("depreciated_value"),
          depreciation_period = rs.getDouble("depreciation_period"),
          period_type = rs.getInt("period_type"),
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



      }


      con.close();
      return result

    }catch {
      case ex: SQLException =>{
        println(ex)
        return null

      }
    }

  }

  def searchMetaDataById(equipment_id:Int): Map[String, UploadFile] = {
    try{
      val sql = """
      SELECT e.metadata_info
      FROM equipment_management.equipment e
      WHERE e.device_status != ? and e.id = ?;"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setInt(2, equipment_id)
      val rs = pst.executeQuery()
      var result :Any =null
      while ( rs.next) {
        result = rs.getObject("metadata_info")
      }
      con.close();
      val images =parse(result.toString)
      implicit val formats = DefaultFormats
      var map : Map[String,UploadFile] = Map()
      for (image <- (images \\ "files" ).children.toArray){
        //val file: UploadFile = image.extract[UploadFile]
        println(image)
        //map = map + (file.fileName -> file)
      }

      return map

    }catch {
      case ex: SQLException =>{
        println(ex)
        return null

      }
    }

  }

  def updateById(e: Equipment): Int={
    try{
      val sql =
        """UPDATE equipment
          SET  device_id = ? , name = ?, start_status = ? ,category_id = ?,price = ?,
                       depreciated_value = ? ,depreciation_period = ? ,period_type = ?,
                       import_date = ?,takeover_status = ?,device_status = ? ,updated_by = ?,updated_time = ?
          WHERE id = ?;"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1,e.device_id)
      pst.setString(2, e.name)
      pst.setInt(3,e.start_status )
      pst.setInt(4, e.category_id)
      pst.setDouble(5,e.price)
      pst.setDouble(6,e.depreciated_value )
      pst.setDouble(7, e.depreciation_period)
      pst.setInt(8, e.period_type)
      pst.setLong(9,e.import_date)
      pst.setInt(10, 0)
      pst.setInt(11, e.device_status)
      pst.setString(12,e.updated_by)
      pst.setLong(13,e.updated_time)
      pst.setInt(14,e.id)


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

  def getIdEquipmentDESC():Int = {
    try{
      val sql = """
      SELECT *
      FROM equipment_management.equipment e where  e.device_status != ?
      order by e.id desc limit 1;"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)

      val rs = pst.executeQuery()
      var id:Int = -1
      while ( rs.next) {
        id = rs.getInt("id")
      }


      con.close();
      return id

    }catch {
      case ex: SQLException =>{
        println(ex)
        return -1

      }
    }
  }

  def add(e: Equipment): Int={
    try{
      val sql = """INSERT INTO equipment (device_id, name, start_status,category_id,price,
             depreciated_value,depreciation_period,period_type,
             import_date,takeover_status,device_status,created_by,created_time)
      VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);"""

      var con = (new DataConnection).getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1,e.device_id)
      pst.setString(2, e.name)
      pst.setInt(3,e.start_status )
      pst.setInt(4, e.category_id)
      pst.setDouble(5,e.price)
      pst.setDouble(6,e.depreciated_value )
      pst.setDouble(7, e.depreciation_period)
      pst.setInt(8, e.period_type)
      pst.setLong(9,e.import_date)
      pst.setInt(10, 0)
      pst.setInt(11, e.device_status)
      pst.setString(12,e.created_by)
      pst.setLong(13,e.created_time)


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

  def checkFilesUpload (files: Map[String, MultipartItem]): Int ={
    for (key <- files.keys){
      if (key!= "information" ){
        val image = files.get(key)
        if ( image.get.data.length > 5000000 ){
         return 0
        }
        else if (image.get.contentType.get.split("/")(0) != "image"){
         return -1
        }
      }
    }
    return 1
  }
}


object test {
  def main (args :Array[String]): Unit ={
//    val test = (new CRUDEquipmentService).searchById(9)
//    val test2 = (new CRUDEquipmentService).addImagesById(test,9)
    val result = (new CRUDEquipmentService).searchMetaDataById(13)
   println(result)
//    for (i <- result.children){
//      println(""+i)
//      val test3 = JSON.parse(i.toString)
//      println(test3)
//    }
//var arr = (new CRUDEquipmentService).search(null,null,null,null,null,5,0).toArray
//
//    var arr1  = JSON.write(arr).toArray
//    println(arr1)
//    println(arr1.getClass)
    //var result= (new CRUDEquipmentService).updateById(4,test)
   // println(result)
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
