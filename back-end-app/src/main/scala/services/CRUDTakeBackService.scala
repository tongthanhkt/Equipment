package scala.services

import com.twitter.util.jackson.JSON
import models.{ConvertString, Equipment, SearchUserRequest, TakeOver, UploadFile, User}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import javax.inject.Inject
import scala.models.{SearchTakeBackRequest, TakeBack}
import java.util
class CRUDTakeBackService @Inject()(databaseConnection:DatabaseConnection,convertString:ConvertString) {
  @throws[Exception]
  def searchTakeBackByEquipmentId(equipmentId:Int): util.ArrayList[TakeBack] = {
    val takeBackList = new util.ArrayList[TakeBack]()
    val sql = """
      SELECT tb.id,tb.equipment_id,e.device_id,e.name,tb.username,tb.take_back_time,tb.status,tb.verifier,tb.take_back_person,tb.metadata_info,tb.type
          ,tb.message,tb.cost,tb.created_by,tb.created_time,tb.updated_by,tb.updated_time
        FROM equipment_management.takeback_equipment_info as tb
        LEFT JOIN equipment_management.equipment as e
        on e.id = tb.equipment_id
        where tb.status!=-1
      AND  tb.equipment_id = ?;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1, equipmentId)
    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeBack(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        username = rs.getString("username"),
        takeBackTime = rs.getString("take_back_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        takeBackPerson = rs.getString("take_back_person"),
        typeTakeBack = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info")));

      takeBackList.add(e);
    }
    con.close()
    return takeBackList;
  }
  @throws[Exception]
  def searchUser(searchUserRequest:SearchUserRequest):util.ArrayList[User]={
    val userList = new util.ArrayList[User]()
    val sql =
      """
        |SELECT * from user as us
        |where (? is null or us.username LIKE CONCAT('%',?,'%') or us.fullname LIKE CONCAT('%',?,'%'));
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,searchUserRequest.keyword);
    pst.setString(2,searchUserRequest.keyword);
    pst.setString(3,searchUserRequest.keyword);
    val rs = pst.executeQuery
    while(rs.next){
      val e = User(username=rs.getString("username"),fullname = rs.getString("fullname"))
      userList.add(e)
    }
    con.close()
    return userList;
  }
  @throws[Exception]
  def searchTakeBack(searchTakeBackRequest: SearchTakeBackRequest,offset:Int):util.ArrayList[TakeBack]={
    val takeBackList = new util.ArrayList[TakeBack]()
    val sql=
      """
         SELECT tb.id,tb.equipment_id,e.device_id,e.name,tb.username,tb.take_back_time,tb.status,tb.verifier,tb.take_back_person,tb.metadata_info,tb.type
          ,tb.message,tb.cost,tb.created_by,tb.created_time,tb.updated_by,tb.updated_time,tb.takeover_id
        FROM equipment_management.takeback_equipment_info as tb
        LEFT JOIN equipment_management.equipment as e
        on e.id = tb.equipment_id
        where tb.status!=?
        and (? is null or tb.username LIKE CONCAT('%',?,'%'))
        and (? is null or tb.take_back_person LIKE CONCAT('%',?,'%'))
        and (? is null or tb.type = ?)
        and (? is null or tb.status= ?)
        and (? is null or tb.equipment_id= ?)
        LIMIT ? OFFSET ?
         ;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1,-1);
    pst.setString(2,searchTakeBackRequest.username);
    pst.setString(3,searchTakeBackRequest.username);
    pst.setString(4,searchTakeBackRequest.takeBackPerson);
    pst.setString(5,searchTakeBackRequest.takeBackPerson);
    pst.setString(6,searchTakeBackRequest.typeTakeBack);
    pst.setString(7,searchTakeBackRequest.typeTakeBack);
    pst.setString(8,searchTakeBackRequest.status);
    pst.setString(9,searchTakeBackRequest.status);
    pst.setString(10,searchTakeBackRequest.equipmentId);
    pst.setString(11,searchTakeBackRequest.equipmentId);
    pst.setInt(12,searchTakeBackRequest.limit);
    pst.setInt(13,offset);

    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeBack(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId = rs.getString("device_id"),
        takeOverId = rs.getString("takeover_id"),
        name = rs.getString("name"),
        username = rs.getString("username"),
        takeBackTime = rs.getString("take_back_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        takeBackPerson = rs.getString("take_back_person"),
        typeTakeBack = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info"),
          ));

      takeBackList.add(e);
    }
    con.close()
    return takeBackList;
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
  @throws[Exception]
  def countBySearchTakeBack(username:String,takeBackPerson:String,typeTakeBack:String,status:String,equipmentId:String):Int={
    val sql=
      """
        |SELECT count(*) as  total
        |FROM takeback_equipment_info as temp
        | WHERE
        |        (? is null or temp.username LIKE CONCAT('%',?,'%'))
        |        and (? is null or temp.take_back_person LIKE CONCAT('%',?,'%'))
        |        and (? is null or temp.type = ?)
        |        and (? is null or temp.status= ?)
        |        and (? is null or temp.equipment_id= ?)
        |        ;
        |
        |
        |""".stripMargin
    val con =databaseConnection.getConnection()
    val pst=con.prepareStatement(sql)
    pst.setString(1,username);
    pst.setString(2,username);
    pst.setString(3,takeBackPerson);
    pst.setString(4,takeBackPerson);
    pst.setString(5,typeTakeBack);
    pst.setString(6,typeTakeBack);
    pst.setString(7,status);
    pst.setString(8,status);
    pst.setString(9,equipmentId);
    pst.setString(10,equipmentId);
    val rs = pst.executeQuery
    var total =0
    while ( rs.next) {
      total = rs.getInt("total")
    }
    con.close();
    return total
  }
  @throws[Exception]
  def searchTakeBackById(takeBack:Int): TakeBack = {
    val sql = """
      SELECT tov.id, e.device_id, e.name ,tov.equipment_id,tov.username,tov.take_back_time,tov.take_back_person,tov.status,tov.verifier,tov.metadata_info,tov.type,tov.message,tov.cost,tov.created_by,tov.updated_by,tov.created_time,tov.updated_time
      FROM equipment_management.takeback_equipment_info as tov, equipment as e
      WHERE  tov.id = ?  and tov.equipment_id=e.id
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, takeBack)
    val rs = pst.executeQuery()
    var result :TakeBack = null
    while ( rs.next) {
      result = TakeBack(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId=rs.getString("device_id"),
        name=rs.getString("name"),
        username = rs.getString("username"),
        takeBackTime = rs.getString("take_back_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        takeBackPerson = rs.getString("take_back_person"),
        typeTakeBack = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_time"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info")));
    }
    con.close();
    return result
  }
  @throws[Exception]
  def checkUserExist(username: String): Int = {
    val sql =
      """
        |SELECT * from user
        |WHERE user.username = ?
        |""".stripMargin
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1, username)
    val rs = pst.executeQuery()
    var result: User = null
    while (rs.next()) {
      result = User(username = rs.getString("username"));
    }
    con.close()
    if (result != null) return 1;
    else return 0;

  }
  @throws[Exception]
  def checkequipmentForTakeBack(equipmentId: String): Int = {
    val sql =
      """
        |SELECT * from equipment
        |WHERE equipment.id = ?
        |""".stripMargin
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,equipmentId)
    val rs = pst.executeQuery()
    var result: Equipment = null
    while (rs.next()) {
      result = Equipment(id = rs.getString("id"), takeOverStatus = rs.getString("takeover_status"))
    }
    if (result == null) {
      return 0 // khong ton tai thiet bi
    }
    if (result.takeOverStatus == "0") {
      return -1
    } // Da duoc thu hoi
    if (result.deviceStatus == "0" ) {
      return -2
    } // Da bi mat
    if (result.deviceStatus == "2") {
      return -3
    } // Da hu hong
    if (result.deviceStatus == "3") {
      return -4
    } // bán cho nhân viên
    return 1
  }
  @throws[Exception]
  def getTakeOverIdForTackBack(equipmentId:String):TakeOver={
    val sql ="SELECT * from takeover_equipment_info as tov WHERE tov.equipment_id = ? and tov.takeback_status = ? ;"
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1, equipmentId)
    pst.setInt(2, 0)
    val rs = pst.executeQuery()
    var result :TakeOver = null
    while ( rs.next) {
      result = TakeOver(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        username = rs.getString("username"),
        takeOverTime = rs.getString("take_over_time"),
        status = rs.getString("status"),
        verifier = rs.getString("verifier"),
        takeOverPerson = rs.getString("take_over_person"),
        typeTakeOver = rs.getString("type"),
        message = rs.getString("message"),
        cost = rs.getString("cost"),
        createdBy = rs.getString("created_by"),
        createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"),
        updatedTime = rs.getString("updated_time"),
        metadataInfo = toMap(rs.getString("metadata_info")));
    }
    con.close();
    return result
  }
  @throws[Exception]
  def updateTakeover(equipmentId:String):Int={
    val sql =
      """UPDATE takeover_equipment_info as tov
          SET tov.takeback_status = 1
          WHERE tov.equipment_id= ?;"""
    var con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setString(1,equipmentId);
    val rs = pst.executeUpdate()
    con.close();
    return rs
  }
  @throws[Exception]
  def updateEquipment(equipmentId:String,typeTakeBack:String):Int={
    var result:String="1";
    var isCompensation :String = null
    if(typeTakeBack=="2") result="2";
    else if(typeTakeBack=="3") {
      result = "0"
      isCompensation = "1"
    };
    else if(typeTakeBack=="4") result="3";
    val sql =
      """
        |UPDATE equipment as e
        |SET e.device_status = ?, e.compensation_status = ?
        |WHERE e.id = ?;
        |""".stripMargin
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,result);
    pst.setString(2,isCompensation)
    pst.setString(3,equipmentId);
    val rs = pst.executeUpdate()
    con.close()
    return rs;
  }
  @throws[Exception]
  def add(e: TakeBack): Int = {
    val result = getTakeOverIdForTackBack(e.equipmentId);
      val sql =
        """INSERT INTO takeback_equipment_info (equipment_id, username, take_back_time,status,verifier,
              take_back_person,metadata_info,type,
              message,cost,created_by,created_time,updated_by,updated_time,takeover_id)
              VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"""
      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1, e.equipmentId)
      pst.setString(2, e.username)
      pst.setString(3, e.takeBackTime)
      pst.setInt(4, 0)
      pst.setString(5, e.verifier)
      pst.setString(6, e.takeBackPerson)
      pst.setString(7,
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin)
      pst.setString(8, e.typeTakeBack)
      pst.setString(9, e.message)
      pst.setString(10, e.cost)
      pst.setString(11, e.createdBy)
      pst.setLong(12, System.currentTimeMillis())
      pst.setString(13, e.updatedBy)
      pst.setString(14, e.updatedTime)
      pst.setString(15, result.id);
      val rs = pst.executeUpdate()
      con.close();
      return rs
  }
  @throws[Exception]
  def getIdTakeBackDESC():Int = {
    val sql = """
      SELECT *
      FROM equipment_management.takeback_equipment_info e where e.status!= ?
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
  def deleteById(takeBackId:Int):Int={
    val sql="UPDATE takeback_equipment_info SET status = -1 WHERE  id = ?;"
    val con = databaseConnection.getConnection()
    val pst=con.prepareStatement(sql)
    pst.setInt(1,takeBackId)
    val rs=pst.executeUpdate()
    con.close()
    return rs
  }

  @throws[Exception]
  def updateById(e:TakeBack):Int= {
    var uploadFile: String = null
    if (e.metadataInfo != null)
      uploadFile =
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin

    val sql =
      """UPDATE takeback_equipment_info
          SET  username = if(? is not null, ?,username),
           take_back_time = if(? is not null, ?,take_back_time),status = if(? is not null, ?,status),
           verifier = if(? is not null, ?,verifier),take_back_person = if(? is not null, ?, take_back_person),
           type = if(? is not null, ?,type),
           message = if(? is not null,?,message),cost = if(? is not null,?,cost),
           updated_by = ?,updated_time = ?,
           metadata_info = if(? is not null, ?,metadata_info)
           WHERE status != ? and id = ?;"""
    var con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setString(1, e.username);
    pst.setString(2, e.username);
    pst.setString(3, e.takeBackTime);
    pst.setString(4, e.takeBackTime);
    pst.setString(5, e.status);
    pst.setString(6, e.status);
    pst.setString(7, e.verifier);
    pst.setString(8, e.verifier);
    pst.setString(9, e.takeBackPerson);
    pst.setString(10, e.takeBackPerson);
    pst.setString(11, e.typeTakeBack);
    pst.setString(12, e.typeTakeBack);
    pst.setString(13, e.message);
    pst.setString(14, e.message);
    pst.setString(15, e.cost);
    pst.setString(16, e.cost);
    pst.setString(17, e.updatedBy);
    pst.setLong(18, System.currentTimeMillis())
    pst.setString(19, uploadFile)
    pst.setString(20, uploadFile)
    pst.setInt(21, -1);
    pst.setString(22, e.id);
    val rs = pst.executeUpdate()
    con.close();
    return rs

  }

}
