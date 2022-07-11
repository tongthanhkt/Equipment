package services

import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.util.jackson.JSON
import models.{ConvertString, Equipment, SearchRequest, UploadFile}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.lang.Thread.sleep
import java.sql.SQLException
import java.util
import javax.inject.Inject



class CRUDEquipmentService @Inject() (
                                       databaseConnection: DatabaseConnection,
                                       convertString: ConvertString
                                     ) {
  private[this] var mPutDevice = new util.HashMap[String,Object]()
  @throws[Exception]
  def search(searchRequest: SearchRequest,offset : Int,isCompensation:String): util.ArrayList[Equipment] ={

    val equipments = new util.ArrayList[Equipment]
      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info o
                                                  WHERE not exists (SELECT *
                                                                    FROM takeback_equipment_info b
                                                                    where b.takeover_id = o.id ))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ?
      and (? is null or e.name LIKE CONCAT('%' ,?,'%') or e.device_id LIKE CONCAT('%',?,'%') )
      and (? is null or e.category_id = ? )
      and (? is null or u.username LIKE CONCAT('%',?,'%') or u.fullname LIKE CONCAT('%',?,'%') )
      and (? is null or e.device_status = ? )
      and (? is null or e.takeover_status = ? )
      and (? is null or e.compensation_status = ? )
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
      pst.setString(9, searchRequest.takeOverPerson)
      pst.setString(10, searchRequest.deviceStatus)//null
      pst.setString(11,searchRequest.deviceStatus) //device status
      pst.setString(12, searchRequest.takeOverStatus)//null
      pst.setString(13, searchRequest.takeOverStatus)//take over status
    pst.setString(14, isCompensation)
    pst.setString(15, isCompensation)
      pst.setInt(16,searchRequest.limit) //litmit
      pst.setInt(17,offset) //offset
      val rs = pst.executeQuery
      while ( rs.next) {
        val e = Equipment(id=rs.getString("id"),
          deviceId=rs.getString("device_id"),
          name=rs.getString("name"),
          startStatus=rs.getString("start_status"),
          price = rs.getDouble("price").toString,
          depreciatedValue = rs.getDouble("depreciated_value").toString,
          depreciationPeriod = rs.getDouble("depreciation_period").toString,
          periodType =rs.getString("period_type"),
          importDate = rs.getString("import_date"),
          takeOverStatus=rs.getString("takeover_status"),
          categoryId = rs.getString("category_id"),
          deviceStatus = rs.getString("device_status"),
          createdBy = rs.getString("created_by"),
          createdTime = rs.getString("created_time"),
          updatedBy = rs.getString("updated_by"),
          updatedTime = rs.getString("updated_time"),
          takeOverPersonId = rs.getString("username"),
          takeOverPersonName = rs.getString("fullname"),
          compensationStatus = rs.getString("compensation_status"),
          metadataInfo = toMap(rs.getString("metadata_info")));


        equipments.add(e)

      }
      con.close();
      return equipments
  }

  @throws[Exception]
  def countBySearch(keyword:String,category:String,takeOverPerson:String,takeOverStatus:String,deviceStatus:String,isCompensation:String): Int ={
    val sql = """
      SELECT count(*) as total
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info o
                                                  WHERE not exists (SELECT *
                                                                    FROM takeback_equipment_info b
                                                                    where b.takeover_id = o.id ))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ?
      and (? is null or e.name LIKE CONCAT('%',?,'%') or e.device_id LIKE CONCAT('%',?,'%') )
      and (? is null or e.category_id = ? )
      and (? is null or u.username LIKE CONCAT('%',?,'%') or u.fullname LIKE CONCAT('%',?,'%') )
      and (? is null or e.device_status = ? )
      and (? is null or e.takeover_status = ? )
      and (? is null or e.compensation_status = ? )
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
    pst.setString(9, takeOverPerson)
      pst.setString(10, deviceStatus)
      pst.setString(11,deviceStatus)
      pst.setString(12, takeOverStatus)
      pst.setString(13, takeOverStatus)
      pst.setString(14, isCompensation)
      pst.setString(15, isCompensation)

      val rs = pst.executeQuery
      var total =0
      while ( rs.next) {
        total = rs.getInt("total")
      }
      con.close();
      return total
  }

  @throws[Exception]
  def deleteById(equipmentId:Int): Int = {

      val sql = "UPDATE equipment SET device_status = -1 WHERE id = ? and takeover_status != ? ;"

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1, equipmentId)
    pst.setInt(2, 1)
      val rs = pst.executeUpdate()
      con.close();
      return rs
  }

  @throws[Exception]
  def   searchById(equipmentId:Int): Equipment = {

      val sql = """
      SELECT *
      FROM equipment_management.equipment e left join (SELECT username as take_over_person_id,equipment_id
                                                  FROM takeover_equipment_info o
                                                  WHERE not exists (SELECT *
                                                                    FROM takeback_equipment_info b
                                                                    where b.takeover_id = o.id ))
                                                as used on used.equipment_id=e.id
                                            left join  user u on used.take_over_person_id = u.username
      WHERE e.device_status != ? and e.id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setInt(1,-1)
      pst.setInt(2, equipmentId)
      val rs = pst.executeQuery()
      var result :Equipment = null
      while ( rs.next) {
        result = Equipment(id=rs.getString("id"),
          deviceId=rs.getString("device_id"),
          name=rs.getString("name"),
          startStatus=rs.getString("start_status"),
          price = rs.getDouble("price").toString,
          depreciatedValue = rs.getDouble("depreciated_value").toString,
          depreciationPeriod = rs.getDouble("depreciation_period").toString,
          periodType =rs.getString("period_type"),
          importDate = rs.getString("import_date"),
          takeOverStatus=rs.getString("takeover_status"),
          categoryId = rs.getString("category_id"),
          deviceStatus = rs.getString("device_status"),
          createdBy = rs.getString("created_by"),
          createdTime = rs.getString("created_time"),
          updatedBy = rs.getString("updated_by"),
          updatedTime = rs.getString("updated_time"),
          takeOverPersonId = rs.getString("username"),
          takeOverPersonName = rs.getString("fullname"),
          compensationStatus = rs.getString("compensation_status"),
          metadataInfo = toMap(rs.getString("metadata_info")));
      }
      con.close();
    println(result)
      return result
  }

  @throws[Exception]
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

  @throws[Exception]
  def add(e: Equipment): Int={
    var lockObj = mPutDevice.get(e.deviceId)
    if(lockObj == null) {
      lockObj = new Object()
      mPutDevice.put(e.deviceId,lockObj)

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
        if(e.metadataInfo != null)
          uploadFiles =e.metadataInfo

        val sql = """CALL insert_equipment (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"""

        var con = databaseConnection.getConnection()
        val pst = con.prepareStatement(sql)
        pst.setString(1,e.deviceId)
        pst.setString(2, e.name)
        pst.setString(3,e.startStatus )
        pst.setString(4, e.categoryId)
        pst.setString(5,e.price)
        pst.setString(6,e.depreciatedValue )
        pst.setString(7, e.depreciationPeriod)
        pst.setString(8, e.periodType)
        pst.setString(9,e.importDate)
        pst.setInt(10, 0)
        pst.setString(11, e.deviceStatus)
        pst.setString(12,e.createdBy)
        pst.setLong(13,System.currentTimeMillis())
        pst.setString(14,
          s"""
             |{"files": ${JSON.write(uploadFiles)}}
             |""".stripMargin)
        pst.setString(15,e.compensationStatus)

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
        mPutDevice.remove(e.deviceId)
      }
    //}

  }

  @throws[Exception]
  def checkDeviceIdInsert(deviceId:String): Boolean ={
    val sql = """
      select count(*) as isExists
      from equipment e
      where  e.device_status != ? and e.device_id = ?;
      """
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1,-1)
    pst.setString(2, deviceId)
    val rs = pst.executeQuery
    var isExists =0
    while ( rs.next) {
      isExists = rs.getInt("isExists")
    }
    con.close();
    if (isExists == 0)
      true
    else
      false
  }

  @throws[Exception]
  def checkDeviceIdUpdate( equipmentId:String,deviceId:String): Boolean ={
    val sql = """
      select count(*) as isExists
      from equipment e
      where  e.device_status != ? and e.id != ? and e.device_id = ?;
      """
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1,-1)
    pst.setString(2, equipmentId)
    pst.setString(3, deviceId)
    val rs = pst.executeQuery
    var isExists =0
    while ( rs.next) {
      isExists = rs.getInt("isExists")
    }
    con.close();
    if (isExists == 0)
      true
    else
      false
  }

  @throws[Exception]
  def updateById(e: Equipment): Int={
    var lockObj = mPutDevice.get(e.deviceId)
    if(lockObj == null) {
      lockObj = new Object()
      mPutDevice.put(e.deviceId,lockObj)

    }
    else{
      return -1
    }
    try {
      var uploadFile: String = null
      if (e.metadataInfo != null)
        uploadFile =
          s"""
             |{"files": ${JSON.write(e.metadataInfo)}}
             |""".stripMargin

      val sql =
        """UPDATE equipment
          SET  device_id = if(? is not null, ?,device_id) , name = if(? is not null, ?,name),
           start_status = if(? is not null, ?,start_status),category_id = if(? is not null, ?,category_id),
           price = if(? is not null, ?,price),depreciated_value = if(? is not null, ?, depreciated_value)
           ,depreciation_period = if(? is not null, ?,depreciation_period) ,period_type = if(? is not null, ?,period_type),
           metadata_info = if(? is not null, ?,metadata_info),import_date = if(? is not null, ?,import_date),
           device_status = if(? is not null, ?,device_status) ,
           updated_by = ?,updated_time = ?,
           compensation_status=?
          WHERE device_status != ? and id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1, e.deviceId)
      pst.setString(2, e.deviceId)
      pst.setString(3, e.name)
      pst.setString(4, e.name)
      pst.setString(5, e.startStatus)
      pst.setString(6, e.startStatus)
      pst.setString(7, e.categoryId)
      pst.setString(8, e.categoryId)
      pst.setString(9, e.price)
      pst.setString(10, e.price)
      pst.setString(11, e.depreciatedValue)
      pst.setString(12, e.depreciatedValue)
      pst.setString(13, e.depreciationPeriod)
      pst.setString(14, e.depreciationPeriod)
      pst.setString(15, e.periodType)
      pst.setString(16, e.periodType)
      pst.setString(17, uploadFile)
      pst.setString(18, uploadFile)
      pst.setString(19, e.importDate)
      pst.setString(20, e.importDate)

      pst.setString(21, e.deviceStatus)
      pst.setString(22, e.deviceStatus)
      pst.setString(23, e.updatedBy)

      pst.setLong(24, System.currentTimeMillis())
      pst.setString(25, e.compensationStatus)
      pst.setInt(26, -1)
      pst.setString(27, e.id)
      val rs = pst.executeUpdate()
      con.close();
      return rs
    }
    catch {
      case e: Exception => throw e
    } finally {
      mPutDevice.remove(e.deviceId)
    }
  }

  @throws[Exception]
  def checkDeviceStatus(equipmentId: String): Int = {
    val sql =
      """
        |SELECT * from equipment
        |WHERE equipment.id = ? and equipment.device_status != ?
        |""".stripMargin
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,equipmentId)
    pst.setInt(2,-1)
    val rs = pst.executeQuery()
    var result: Equipment = null
    while (rs.next()) {
      result = Equipment(id = rs.getString("id"), deviceStatus = rs.getString("device_status"))
    }
    con.close();
    if (result != null) {
      if (result.deviceStatus == "0")
      return 0
      if (result.deviceStatus == "1")
        return 1
      if (result.deviceStatus == "2")
        return 2
      if (result.deviceStatus == "3")
        return 3
      else -1

    }
    else
      -1
  }

  @throws[Exception]
  def checkTakeOverStatus(equipmentId: String): Int = {
    val sql =
      """
        |SELECT * from equipment
        |WHERE equipment.id = ? and equipment.device_status != ?
        |""".stripMargin
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,equipmentId)
    pst.setInt(2,-1)
    val rs = pst.executeQuery()
    var result: Equipment = null
    while (rs.next()) {
      result = Equipment(id = rs.getString("id"), takeOverStatus = rs.getString("takeover_status"))
    }
    con.close();
    if (result != null) {
      if (result.takeOverStatus == "0")
        return 0
      if (result.takeOverStatus == "1")
        return 1

      else -1

    }
    else
      -1
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
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
  def main (args :Array[String]): Unit ={
    var mPutDevice1 = new util.HashMap[String,Object]()
    println(mPutDevice1.get("mt4"))
  }
}
