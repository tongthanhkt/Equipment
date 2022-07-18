package services
import models.{ConvertString, FixEquipment, HistoricalKey, HistoricalRecord, SearchFixEquipmentRequest, SearchHistoricalRequest, TakeOver, UploadFile}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.util
import javax.inject.Inject


class HistoricalService @Inject()(databaseConnection:DatabaseConnection) {

  @throws[Exception]
  def search(searchHistoricalRequest: SearchHistoricalRequest,offset:Int):util.ArrayList[HistoricalKey]={
    val keys = new util.ArrayList[HistoricalKey]()
    val sql=
      """
        SELECT *
        FROM equipment_management.historical h
        where (? is null or h.performer LIKE CONCAT('%',?,'%'))
               and (? is null or h.equipment_id= ?)
               and (? is null or h.type_action= ?)
        ORDER BY h.created_time_of_action DESC
        LIMIT ? OFFSET ?
         ;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setString(1,searchHistoricalRequest.performer);
    pst.setString(2,searchHistoricalRequest.performer);
    pst.setString(3,searchHistoricalRequest.equipmentId);
    pst.setString(4,searchHistoricalRequest.equipmentId);
    pst.setString(5,searchHistoricalRequest.typeAction);
    pst.setString(6,searchHistoricalRequest.typeAction);
    pst.setInt(7,searchHistoricalRequest.limit);
    pst.setInt(8,offset);

    val rs = pst.executeQuery
    while(rs.next){
      val e = HistoricalKey(idAction = rs.getInt("id_action"),
        typeAction = rs.getInt("type_action"));

      keys.add(e);
    }
    con.close()
    keys;
  }

  @throws[Exception]
  def countBySearch(searchHistoricalRequest: SearchHistoricalRequest):Int={

    val sql=
      """
         SELECT count(*) as total
        FROM equipment_management.historical h
        where (? is null or h.performer LIKE CONCAT('%',?,'%'))
               and (? is null or h.equipment_id= ?)
               and (? is null or h.type_action= ?)
         ;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setString(1,searchHistoricalRequest.performer);
    pst.setString(2,searchHistoricalRequest.performer);
    pst.setString(3,searchHistoricalRequest.equipmentId);
    pst.setString(4,searchHistoricalRequest.equipmentId);
    pst.setString(5,searchHistoricalRequest.typeAction);
    pst.setString(6,searchHistoricalRequest.typeAction);
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

  @throws[Exception]
  def getTakeOverRecord(id : Int):HistoricalRecord={
    val sql = """
      SELECT  e.device_id, e.name ,e.takeover_status,tov.*
      FROM equipment_management.takeover_equipment_info as tov, equipment as e
      WHERE  tov.id = ? and tov.equipment_id=e.id and tov.status!=-1
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, id )
    val rs = pst.executeQuery()
    var result :HistoricalRecord = null
    while ( rs.next) {
      result = HistoricalRecord(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId=rs.getString("device_id"),
        equipmentName=rs.getString("name"),
        user = rs.getString("username"),
        actionTime = rs.getString("take_over_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        performer = rs.getString("take_over_person"),
        reason = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info")),
        typeAction = 1,
        takeOverStatus =  rs.getString("takeover_status"),
      );
    }
    con.close();
    result
  }

  @throws[Exception]
  def getTakeBackRecord(id : Int):HistoricalRecord={
    val sql = """
      SELECT  e.device_id, e.name,e.takeover_status ,e.takeover_status,tb.*
      FROM equipment_management.takeback_equipment_info as tb, equipment as e
      WHERE  tb.id = ?  and tb.equipment_id=e.id and tb.status!=-1
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, id)
    val rs = pst.executeQuery()
    var result : HistoricalRecord = null
    while ( rs.next) {
      result = HistoricalRecord(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId=rs.getString("device_id"),
        equipmentName=rs.getString("name"),
        user = rs.getString("username"),
        actionTime = rs.getString("take_back_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        performer = rs.getString("take_back_person"),
        reason = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_time"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info")),
        typeAction = 2,
        takeOverStatus =  rs.getString("takeover_status"));
    }
    con.close();
    result
  }

  @throws[Exception]
  def getFixEquipmentRecord(id : Int):HistoricalRecord={
    val sql = """
      SELECT fix.*,e.device_id,e.name as equipment_name,e.takeover_status
        FROM equipment_management.fixing_equipment_info as fix
        JOIN equipment_management.equipment as e
        on e.id = fix.equipment_id
        where fix.id = ? and e.device_status != ? and fix.status != -1
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, id)
    pst.setInt(2, -1)
    val rs = pst.executeQuery()
    var result :HistoricalRecord = null
    while ( rs.next) {
      result = HistoricalRecord(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId = rs.getString("device_id"),
        equipmentName = rs.getString("equipment_name"),
        performer = rs.getString("fixer"),
        actionTime = rs.getString("fixing_time"),
        status = rs.getString("status"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        takeOverStatus =  rs.getString("takeover_status"),
        metadataInfo = toMap(rs.getString("metadata_info")),
        typeAction = 3);
    }
    con.close();
    result
  }

}