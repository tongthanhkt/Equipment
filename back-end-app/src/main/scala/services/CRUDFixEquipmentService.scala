package services

import com.twitter.util.jackson.JSON
import models.{ConvertString, Equipment, FixEquipment, SearchFixEquipmentRequest, TakeOver, UploadFile}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject

class CRUDFixEquipmentService @Inject()(databaseConnection:DatabaseConnection,convertString:ConvertString) {

  private[this] var mPutDevice = new util.HashMap[String,Object]()

  @throws[Exception]
  def search(searchFixEquipmentRequest: SearchFixEquipmentRequest,offset:Int):util.ArrayList[FixEquipment]={
    val fixEquipments = new util.ArrayList[FixEquipment]()
    val sql=
      """
         SELECT fix.*,e.device_id,e.name as equipment_name,e.takeover_status
        FROM equipment_management.fixing_equipment_info as fix
        JOIN equipment_management.equipment as e
        on e.id = fix.equipment_id
        where e.device_status != ?
        and (? is null or fix.fixer LIKE CONCAT('%',?,'%'))
        and (? is null or e.device_id LIKE CONCAT('%',?,'%'))
        and (? is null or e.name LIKE CONCAT('%',?,'%'))
        and (? is null or fix.status= ?)
        and (? is null or fix.equipment_id= ?)
        LIMIT ? OFFSET ?
         ;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1,-1);
    pst.setString(2,searchFixEquipmentRequest.fixer);
    pst.setString(3,searchFixEquipmentRequest.fixer);
    pst.setString(4,searchFixEquipmentRequest.deviceId);
    pst.setString(5,searchFixEquipmentRequest.deviceId);
    pst.setString(6,searchFixEquipmentRequest.equipmentName);
    pst.setString(7,searchFixEquipmentRequest.equipmentName);
    pst.setString(8,searchFixEquipmentRequest.status);
    pst.setString(9,searchFixEquipmentRequest.status);
    pst.setString(10,searchFixEquipmentRequest.equipmentId);
    pst.setString(11,searchFixEquipmentRequest.equipmentId);
    pst.setInt(12,searchFixEquipmentRequest.limit);
    pst.setInt(13,offset);

    val rs = pst.executeQuery
    while(rs.next){
      val e = FixEquipment(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId = rs.getString("device_id"),
        equipmentName = rs.getString("equipment_name"),
        fixer = rs.getString("fixer"),
        fixingTime = rs.getString("fixing_time"),
        status = rs.getString("status"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        takeOverStatus =  rs.getString("takeover_status"),
        metadataInfo = toMap(rs.getString("metadata_info")));

      fixEquipments.add(e);
    }
    con.close()
    fixEquipments;
  }

  @throws[Exception]
  def countBySearch(searchFixEquipmentRequest: SearchFixEquipmentRequest):Int={

    val sql=
      """
         SELECT count(*) as total
        FROM equipment_management.fixing_equipment_info as fix
        JOIN equipment_management.equipment as e
        on e.id = fix.equipment_id
        where e.device_status != ?
        and (? is null or fix.fixer LIKE CONCAT('%',?,'%'))
        and (? is null or e.device_id LIKE CONCAT('%',?,'%'))
        and (? is null or e.name LIKE CONCAT('%',?,'%'))
        and (? is null or fix.status= ?)
        and (? is null or fix.equipment_id= ?)

         ;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1,-1);
    pst.setString(2,searchFixEquipmentRequest.fixer);
    pst.setString(3,searchFixEquipmentRequest.fixer);
    pst.setString(4,searchFixEquipmentRequest.deviceId);
    pst.setString(5,searchFixEquipmentRequest.deviceId);
    pst.setString(6,searchFixEquipmentRequest.equipmentName);
    pst.setString(7,searchFixEquipmentRequest.equipmentName);
    pst.setString(8,searchFixEquipmentRequest.status);
    pst.setString(9,searchFixEquipmentRequest.status);
    pst.setString(10,searchFixEquipmentRequest.equipmentId);
    pst.setString(11,searchFixEquipmentRequest.equipmentId);


    val rs = pst.executeQuery
    var total = 0
    while ( rs.next) {
      total = rs.getInt("total")
    }
    con.close();
    total
  }

  private def toMap (metadata : String): Map[String, UploadFile] ={
    var map : Map[String,UploadFile] = Map()

    val images = parse(metadata)
    implicit val formats = DefaultFormats
    for (image <- (images \\ "files" ).children){

      map = image.extract[Map[String,UploadFile]]
    }

    map
  }

  @throws[SQLException]
  def searchById(id:String): FixEquipment = {
    val sql = """
      SELECT fix.*,e.device_id,e.name as equipment_name,e.takeover_status
        FROM equipment_management.fixing_equipment_info as fix
        JOIN equipment_management.equipment as e
        on e.id = fix.equipment_id
        where fix.id = ? and e.device_status != ?
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1, id)
    pst.setInt(2, -1)
    val rs = pst.executeQuery()
    var result :FixEquipment = null
    while ( rs.next) {
      result = FixEquipment(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId = rs.getString("device_id"),
        equipmentName = rs.getString("equipment_name"),
        fixer = rs.getString("fixer"),
        fixingTime = rs.getString("fixing_time"),
        status = rs.getString("status"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        takeOverStatus =  rs.getString("takeover_status"),
        metadataInfo = toMap(rs.getString("metadata_info")));
    }
    con.close();
    result
  }

  @throws[Exception]
  def add(fix: FixEquipment): Int={
    var lockObj = mPutDevice.get(fix.equipmentId)
    if(lockObj == null) {
      lockObj = new Object()
      mPutDevice.put(fix.equipmentId,lockObj)

    }
    else{
      return -1
    }


    //      if(e.deviceId=="tesst1") {
    //        println("start sleep "+e.deviceId)
    //        sleep(20000000)
    //        println("stop sleep "+e.deviceId)
    //      }
    //   lockObj.synchronized{
    try{
      var uploadFiles :  Map[String, UploadFile] = Map()
      if(fix.metadataInfo != null)
        uploadFiles =fix.metadataInfo

      val sql = """CALL insert_fixing_equipment_info (?,?,?,?,?,?,?,?,?);"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1,fix.equipmentId)
      pst.setString(2, fix.status)
      pst.setString(3,fix.message )
      pst.setString(4, fix.cost)
      pst.setString(5,fix.fixingTime )
      pst.setString(6,fix.fixer)
      pst.setString(7,
        s"""
           |{"files": ${JSON.write(uploadFiles)}}
           |""".stripMargin)
      pst.setString(8,fix.createdBy)
      pst.setLong(9,System.currentTimeMillis())


      val rs = pst.executeQuery()

      var id =0
      while ( rs.next) {
        id = rs.getInt("id")
      }
      con.close();
      id
    }catch {
      case e: Exception => throw e
    } finally {
      mPutDevice.remove(fix.equipmentId)
    }
    //}

  }

  @throws[Exception]
  def isFixingExits(equipmentId: String): Boolean={
    val sql = """
      SELECT count(*) as total
        FROM equipment_management.fixing_equipment_info as fix

        where fix.equipment_id = ? and fix.status = ?
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1, equipmentId)
    pst.setInt(2, 0)
    val rs = pst.executeQuery()
    var total = 0
    while ( rs.next) {
      total = rs.getInt("total")
    }
    con.close();
    println(total)
    if (total > 0)
      true
    else false

  }

  @throws[Exception]
  def updateById(fix: FixEquipment): Int={
    var lockObj = mPutDevice.get(fix.equipmentId)
    if(lockObj == null) {
      lockObj = new Object()
      mPutDevice.put(fix.equipmentId,lockObj)

    }
    else{
      return -1
    }
    try {
      var uploadFile: String = null
      if (fix.metadataInfo != null)
        uploadFile =
          s"""
             |{"files": ${JSON.write(fix.metadataInfo)}}
             |""".stripMargin

      val sql =
        """UPDATE fixing_equipment_info
          SET
           status = if(? is not null, ?,status),fixer = if(? is not null, ?,fixer),
           cost = if(? is not null, ?,cost),message = if(? is not null, ?,message),
           metadata_info = if(? is not null, ?,metadata_info),fixing_time = if(? is not null, ?,fixing_time),
           updated_by = ?,updated_time = ?
          WHERE  id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1, fix.status)
      pst.setString(2, fix.status)
      pst.setString(3,fix.fixer)
      pst.setString(4,fix.fixer)
      pst.setString(5,fix.cost)
      pst.setString(6,fix.cost)
      pst.setString(7,fix.message )
      pst.setString(8,fix.message )
      pst.setString(9, uploadFile)
      pst.setString(10, uploadFile)
      pst.setString(11,fix.fixingTime )
      pst.setString(12,fix.fixingTime )
      pst.setString(13,fix.updatedBy)
      pst.setLong(14,System.currentTimeMillis())
      pst.setString(15, fix.id)
      val rs = pst.executeUpdate()
      con.close();
      rs
    }
    catch {
      case e: Exception => throw e
    } finally {
      mPutDevice.remove(fix.equipmentId)
    }
  }

}
