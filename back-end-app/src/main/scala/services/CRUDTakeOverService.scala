package services

import com.twitter.util.jackson.JSON
import models._
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject


class CRUDTakeOverService @Inject()(databaseConnection:DatabaseConnection,convertString:ConvertString){
  private[this] var mPutDevice = new util.HashMap[String,Object]()
  @throws[Exception]
  def searchTakeOverByEquipmentId(equipmentId:Int): util.ArrayList[TakeOver] = {

    val takeOverList = new util.ArrayList[TakeOver]()
    val sql = """
      SELECT tov.id,tov.equipment_id,e.device_id,e.name,tov.username,tov.take_over_time,tov.status,tov.verifier,tov.take_over_person,tov.metadata_info,tov.type
          ,tov.message,tov.cost,tov.created_by,tov.created_time,tov.updated_by,tov.updated_time
        FROM equipment_management.takeover_equipment_info as tov
        LEFT JOIN equipment_management.equipment as e
        on e.id = tov.equipment_id
        where tov.status!=-1
      AND  tov.equipment_id = ?;"""
    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1, equipmentId)
    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeOver(id = rs.getString("id"),
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

      takeOverList.add(e);
    }
    con.close()
    return takeOverList;
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
  def searchTakeOver(searchTakeOverRequest: SearchTakeOverRequest,offset:Int):util.ArrayList[TakeOver]={
    val takeOverList = new util.ArrayList[TakeOver]()
    val sql=
      """
         SELECT tov.id,tov.equipment_id,e.device_id,e.name,tov.username,tov.take_over_time,tov.status,tov.verifier,tov.take_over_person,tov.metadata_info,tov.type
          ,tov.message,tov.cost,tov.created_by,tov.created_time,tov.updated_by,tov.updated_time,tov.takeback_status
        FROM equipment_management.takeover_equipment_info as tov
        LEFT JOIN equipment_management.equipment as e
        on e.id = tov.equipment_id
        where tov.status!=? and e.device_status!=-1
        and (? is null or tov.username LIKE CONCAT('%',?,'%'))
        and (? is null or tov.take_over_person LIKE CONCAT('%',?,'%'))
        and (? is null or tov.type = ?)
        and (? is null or tov.status= ?)
        and (? is null or tov.equipment_id= ?)
        LIMIT ? OFFSET ?
         ;"""
        val con = databaseConnection.getConnection()
        val pst= con.prepareStatement(sql)
    pst.setInt(1,-1);
    pst.setString(2,searchTakeOverRequest.username);
    pst.setString(3,searchTakeOverRequest.username);
    pst.setString(4,searchTakeOverRequest.takeOverPerson);
    pst.setString(5,searchTakeOverRequest.takeOverPerson);
    pst.setString(6,searchTakeOverRequest.typeTakeOver);
    pst.setString(7,searchTakeOverRequest.typeTakeOver);
    pst.setString(8,searchTakeOverRequest.status);
    pst.setString(9,searchTakeOverRequest.status);
    pst.setString(10,searchTakeOverRequest.equipmentId);
    pst.setString(11,searchTakeOverRequest.equipmentId);
    pst.setInt(12,searchTakeOverRequest.limit);
    pst.setInt(13,offset);

    val rs = pst.executeQuery
    while(rs.next){
    val e = TakeOver(id = rs.getString("id"),
      equipmentId = rs.getString("equipment_id"),
      deviceId = rs.getString("device_id"),
      name = rs.getString("name"),
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
      takeBackStatus=rs.getString("takeback_status"),
      metadataInfo = toMap(rs.getString("metadata_info")));

      takeOverList.add(e);
    }
    con.close()
    return takeOverList;
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
  def countBySearchTakeOver(username:String,takeOverPerson:String,typeTakeOver:String,status:String,equipmentId:String):Int={
    val sql=
      """
        |SELECT count(*) as  total
        |FROM takeover_equipment_info as temp
        | WHERE
        |        (? is null or temp.username LIKE CONCAT('%',?,'%'))
        |        and (? is null or temp.take_over_person LIKE CONCAT('%',?,'%'))
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
    pst.setString(3,takeOverPerson);
    pst.setString(4,takeOverPerson);
    pst.setString(5,typeTakeOver);
    pst.setString(6,typeTakeOver);
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
  def searchTakeOverById(takeOver:Int): TakeOver = {
    val sql = """
      SELECT tov.id, e.device_id, e.name ,tov.equipment_id,tov.username,tov.take_over_time,tov.take_over_person,tov.status,tov.verifier,tov.metadata_info,tov.type,tov.message,tov.cost,tov.created_by,tov.updated_by,tov.created_time,tov.updated_time,tov.takeback_status
      FROM equipment_management.takeover_equipment_info as tov, equipment as e
      WHERE  tov.id = ? and tov.equipment_id=e.id and tov.status!=-1
				;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, takeOver)
    val rs = pst.executeQuery()
    var result :TakeOver = null
    while ( rs.next) {
      result = TakeOver(id = rs.getString("id"),
        equipmentId = rs.getString("equipment_id"),
        deviceId=rs.getString("device_id"),
        name=rs.getString("name"),
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
        takeBackStatus=rs.getString("takeback_status"),
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
  def checkDeleteTakeOver(takeOverId:Int):Int={
    val sql =
      """
        |SELECT * from takeover_equipment_info as tov
        |WHERE tov.id = ? and tov.status = ?;
        |""".stripMargin
        var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1,takeOverId);
    pst.setInt(2,0);
    val rs = pst.executeQuery()
    var result : TakeOver=null
    while(rs.next()){
      result = TakeOver(id=rs.getString("id"),status = rs.getString("status"),takeBackStatus = rs.getString("takeback_status"))
    }
    if(result==null){
      return 0 // takeover khong ton tai
    }else  if(result.takeBackStatus=="0") return -1;
    else  if(result.takeBackStatus=="1") return 1;//take over da duoc ban giao
    else return -2;
  }
  @throws[Exception]
  def checkEquipmentForTakeOver(equipmentId: String): Int = {
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
      result = Equipment(id = rs.getString("id"), takeOverStatus = rs.getString("takeover_status"),deviceStatus = rs.getString("device_status"))
    }
    if (result == null) {
      return 0
    }
    if (result.takeOverStatus == "1") {
      return -1
    } // Da duoc ban giao
    if (result.deviceStatus == "0" ) {
      return -2
    } // Da bi mat
    if (result.deviceStatus == "2") {
      return -3
    } // Da hu hong
    if (result.deviceStatus == "3") {
      return -4
    } // b??n cho nh??n vi??n
    return 1
  }
  @throws[Exception]
  def add(e: TakeOver): Int = {
    var lockObj=mPutDevice.get(e.deviceId)
    if(lockObj == null) {
      lockObj = new Object()
      mPutDevice.put(e.deviceId,lockObj)

    }
    else{
      return -1
    }

    try{
      val sql =
        """INSERT INTO takeover_equipment_info (equipment_id, username, take_over_time,status,verifier,
              take_over_person,metadata_info,type,
              message,cost,created_by,created_time,updated_by,updated_time,takeback_status)
              VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1, e.equipmentId)
      pst.setString(2, e.username)
      pst.setString(3,e.takeOverTime )
      pst.setInt(4, 0)
      pst.setString(5,e.verifier)
      pst.setString(6,e.takeOverPerson )
      pst.setString(7,
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin)
      pst.setString(8, e.typeTakeOver)
      pst.setString(9, e.message)
      pst.setString(10,e.cost)
      pst.setString(11, e.createdBy)
      pst.setLong(12,System.currentTimeMillis())
      pst.setString(13, e.updatedBy)
      pst.setString(14,e.updatedTime)
      pst.setInt(15,0)
      val rs = pst.executeUpdate()
      con.close();
      return rs
    }catch {
      case e: Exception => throw e
    } finally {
      mPutDevice.remove(e.deviceId)
    }


  }
  @throws[Exception]
  def getIdTakeOverDESC():Int = {
    val sql = """
      SELECT *
      FROM equipment_management.takeover_equipment_info e where e.status!= ?
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
  def deleteById(takeOverId:Int):Int={
    val sql="UPDATE takeover_equipment_info SET status = ? WHERE  id = ? ;"
    val con = databaseConnection.getConnection()
    val pst=con.prepareStatement(sql)
    pst.setInt(1,-1)
    pst.setInt(2,takeOverId)
    val rs=pst.executeUpdate()
    con.close()
    return rs
  }
  @throws[Exception]
  def updateById(e:TakeOver):Int= {
    print("cost 2 "+e.cost)
    var uploadFile: String = null
    if (e.metadataInfo != null)
      uploadFile =
        s"""
           |{"files": ${JSON.write(e.metadataInfo)}}
           |""".stripMargin

    val sql =
      """UPDATE takeover_equipment_info
          SET  username = if(? is not null, ?,username),
           take_over_time = if(? is not null, ?,take_over_time),status = if(? is not null, ?,status),
           verifier = if(? is not null, ?,verifier),take_over_person = if(? is not null, ?, take_over_person),
           type = if(? is not null, ?,type),
           message = if(? is not null,?,message),cost = if(? is not null,?,cost),
           updated_by = ?,updated_time = ?,
           metadata_info = if(? is not null, ?,metadata_info)
           WHERE status != ? and id = ?;"""

    var con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setString(1, e.username);
    pst.setString(2, e.username);
    pst.setString(3, e.takeOverTime);
    pst.setString(4, e.takeOverTime);
    pst.setString(5, e.status);
    pst.setString(6, e.status);
    pst.setString(7, e.verifier);
    pst.setString(8, e.verifier);
    pst.setString(9, e.takeOverPerson);
    pst.setString(10, e.takeOverPerson);
    pst.setString(11, e.typeTakeOver);
    pst.setString(12, e.typeTakeOver);
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
