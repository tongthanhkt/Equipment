package services

import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.util.jackson.JSON
import models.{Equipment, SearchRequest, UploadFile}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection
import java.sql.SQLException
import java.util
import javax.inject.Inject



class CRUDEquipmentService @Inject() (
                                       databaseConnection: DatabaseConnection
                                     ) {
  @throws[SQLException]
  def search(searchRequest: SearchRequest,offset : Int): util.ArrayList[Equipment] ={

    val equipments = new util.ArrayList[Equipment]
      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ?
      and (? is null or match(e.name) against (?) or e.device_id = ? )
      and (? is null or e.category_id = ? )
      and (? is null or match(u.username,u.fullname) against (?) )
      and (? is null or e.device_status = ? )
      and (? is null or e.takeover_status = ? )
      LIMIT ? OFFSET ?;"""
      val con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setString(2, searchRequest.keyword)
      pst.setString(3, searchRequest.keyword)
      pst.setString(4, searchRequest.keyword)
      pst.setString(5, searchRequest.categoryId)
      pst.setString(6,searchRequest.categoryId)
      pst.setString(7, searchRequest.takeOverPerson)
      pst.setString(8, searchRequest.takeOverPerson)
      pst.setString(9, searchRequest.deviceStatus)
      pst.setString(10,searchRequest.deviceStatus)
      pst.setString(11, searchRequest.takeOverStatus)
      pst.setString(12, searchRequest.takeOverStatus)
      pst.setInt(13,searchRequest.limit)
      pst.setInt(14,offset)
      val rs = pst.executeQuery
      while ( rs.next) {
        val e = Equipment(id=rs.getInt("id"),
          deviceId=rs.getString("device_id"),
          name=rs.getString("name"),
          startStatus=rs.getInt("start_status"),
          price = rs.getDouble("price"),
          depreciatedValue = rs.getDouble("depreciated_value"),
          depreciationPeriod = rs.getDouble("depreciation_period"),
          periodType = rs.getInt("period_type"),
          importDate = rs.getLong("import_date"),
          takeOverStatus=rs.getInt("takeover_status"),
          categoryId = rs.getInt("category_id"),
          deviceStatus = rs.getInt("device_status"),
          createdBy = rs.getString("created_by"),
          createdTime = rs.getLong("created_time"),
          updatedBy = rs.getString("updated_by"),
          updatedTime = rs.getLong("updated_time"),
          takeOverPersonId = rs.getString("username"),
          takeOverPersonName = rs.getString("fullname"));


        equipments.add(e)

      }
      con.close();
      return equipments
  }

  @throws[SQLException]
  def countBySearch(keyword:String,category:String,takeOverPerson:String,takeOverStatus:String,deviceStatus:String): Int ={
    val sql = """
      SELECT count(*) as total
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ?
      and (? is null or match(e.name) against (?) or e.device_id = ? )
      and (? is null or e.category_id = ? )
      and (? is null or match(u.username,u.fullname) against (?) )
      and (? is null or e.device_status = ? )
      and (? is null or e.takeover_status = ? )
      """
      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setString(2, keyword)
      pst.setString(3, keyword)
      pst.setString(4, keyword)
      pst.setString(5, category)
      pst.setString(6,category)
      pst.setString(7, takeOverPerson)
      pst.setString(8, takeOverPerson)
      pst.setString(9, deviceStatus)
      pst.setString(10,deviceStatus)
      pst.setString(11, takeOverStatus)
      pst.setString(12, takeOverStatus)

      val rs = pst.executeQuery
      var total =0
      while ( rs.next) {
        total = rs.getInt("total")
      }
      con.close();
      return total
  }

  @throws[SQLException]
  def deleteById(equipmentId:Int): Int = {

      val sql = "UPDATE equipment SET device_status = -1 WHERE id = ?;"

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1, equipmentId)
      val rs = pst.executeUpdate()
      con.close();
      return rs
  }

  @throws[SQLException]
  def searchById(equipmentId:Int): Equipment = {

      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info
                                                  WHERE not exists (SELECT username,equipment_id
                                                                    FROM takeback_equipment_info))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ? and e.id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setInt(2, equipmentId)
      val rs = pst.executeQuery()
      var result :Equipment =null
      while ( rs.next) {
        result = Equipment(id=rs.getInt("id"),
          deviceId=rs.getString("device_id"),
          name=rs.getString("name"),
          startStatus=rs.getInt("start_status"),
          price = rs.getDouble("price"),
          depreciatedValue = rs.getDouble("depreciated_value"),
          depreciationPeriod = rs.getDouble("depreciation_period"),
          periodType = rs.getInt("period_type"),
          importDate = rs.getLong("import_date"),
          takeOverStatus=rs.getInt("takeover_status"),
          categoryId = rs.getInt("category_id"),
          deviceStatus = rs.getInt("device_status"),
          metadataInfo = toMap(rs.getString("metadata_info")),
          createdBy = rs.getString("created_by"),
          createdTime = rs.getLong("created_time"),
          updatedBy = rs.getString("updated_by"),
          updatedTime = rs.getLong("updated_time"),
          takeOverPersonId = rs.getString("username"),
          takeOverPersonName = rs.getString("fullname"));
      }
      con.close();
      return result
  }

  @throws[SQLException]
  def getIdEquipmentDESC():Int = {

      val sql = """
      SELECT *
      FROM equipment_management.equipment e where  e.device_status != ?
      order by e.id desc limit 1;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)

      val rs = pst.executeQuery()
      var id:Int = -1
      while ( rs.next) {
        id = rs.getInt("id")
      }


      con.close();
      return id


  }

  @throws[SQLException]
  def add(e: Equipment): Int={
    if (e.createdBy.isEmpty )
      return -1
    if (e.startStatus > 7 || e.startStatus < 1)
      return -2
    if (e.deviceStatus < 0 || e.deviceStatus > 2)
      return -3

      val sql = """INSERT INTO equipment (device_id, name, start_status,category_id,price,
             depreciated_value,depreciation_period,period_type,
             import_date,takeover_status,device_status,created_by,created_time,metadata_info)
      VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1,e.deviceId)
      pst.setString(2, e.name)
      pst.setInt(3,e.startStatus )
      pst.setInt(4, e.categoryId)
      pst.setDouble(5,e.price)
      pst.setDouble(6,e.depreciatedValue )
      pst.setDouble(7, e.depreciationPeriod)
      pst.setInt(8, e.periodType)
      pst.setLong(9,e.importDate)
      pst.setInt(10, 0)
      pst.setInt(11, e.deviceStatus)
      pst.setString(12,e.createdBy)
      pst.setLong(13,System.currentTimeMillis())
      pst.setString(14,
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin)

      val rs = pst.executeUpdate()
      con.close();
      return rs


  }

  @throws[SQLException]
  def updateById(e: Equipment): Int={
    if (e.updatedBy.isEmpty )
      return -2
    if (e.startStatus > 7 || e.startStatus < 1)
      return -3
    if (e.deviceStatus < -1 || e.deviceStatus > 2)
      return -4

      val sql =
        """UPDATE equipment
          SET  device_id = ? , name = ?, start_status = ? ,category_id = ?,price = ?,
                       depreciated_value = ? ,depreciation_period = ? ,period_type = ?,metadata_info = ?,
                       import_date = ?,takeover_status = ?,device_status = ? ,updated_by = ?,updated_time = ?
          WHERE device_status != ? and id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1,e.deviceId)
      pst.setString(2, e.name)
      pst.setInt(3,e.startStatus )
      pst.setInt(4, e.categoryId)
      pst.setDouble(5,e.price)
      pst.setDouble(6,e.depreciatedValue )
      pst.setDouble(7, e.depreciationPeriod)
      pst.setInt(8, e.periodType)
      pst.setString(9,
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin)
      pst.setLong(10,e.importDate)
      pst.setInt(11, 0)
      pst.setInt(12, e.deviceStatus)
      pst.setString(13,e.updatedBy)
      pst.setLong(14,System.currentTimeMillis())
      pst.setInt(15,-1)
      pst.setInt(16,e.id)


      val rs = pst.executeUpdate()

      con.close();
      return rs


  }

  private def toMap (metadata : String): Map[String, UploadFile] ={
    var map : Map[String,UploadFile] = Map()

    val images = parse(metadata)
    implicit val formats = DefaultFormats
    for (image <- (images \\ "files" ).children){
      map = image.extract[Map[String,UploadFile]]
    }
    return map
  }
}


object test {
  def main (args :Array[String]): Unit ={
//    val test = (new CRUDEquipmentService).searchById(9)
//    val test2 = (new CRUDEquipmentService).addImagesById(test,9)
    //val result = dat.searchMetaDataById(26)
   //println(result)
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
