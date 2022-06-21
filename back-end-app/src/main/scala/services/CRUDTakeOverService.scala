package services

import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.util.jackson.JSON
import models.{ConvertString, TakeOver, SearchTakeOverRequest,UploadFile}
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject

class CRUDTakeOverService @Inject()(databaseConnection:DatabaseConnection,convertString:ConvertString){

  def searchTakeOverByEquipmentId(equipmentId:Int): util.ArrayList[TakeOver] = {
    val takeOverList = new util.ArrayList[TakeOver]()
    val sql = """
      SELECT *
      FROM equipment_management.takeover_equipment_info as temp
      WHERE  temp.equipment_id = ?;"""

    val con = databaseConnection.getConnection()
    val pst= con.prepareStatement(sql)
    pst.setInt(1, equipmentId)
    val rs = pst.executeQuery
    while(rs.next){
      val e = TakeOver(id=rs.getString("id"),
        equipmentId =rs.getString("equipment_id") ,
        username =rs.getString("username") ,
        takeOverTime =rs.getString("take_over_time") ,
        status=rs.getString("status"),
        verifier=rs.getString("verifier"),
        takeOverPerson =rs.getString("take_over_person") ,
        Type=rs.getString("type"),
        message=rs.getString("message"),
        cost=rs.getString("cost"),
        createdBy=rs.getString("created_by"),
        createdTime=rs.getString("created_time"),
        updatedBy=rs.getString("updated_time"),
        updatedTime=rs.getString("updated_time"));

      takeOverList.add(e);
    }
    con.close()
    return takeOverList;
  }
  def searchTakeOver(searchTakeOverRequest: SearchTakeOverRequest,offset:Int):util.ArrayList[TakeOver]={
    val takeOverList = new util.ArrayList[TakeOver]()
    val sql=
      """
        SELECT * FROM equipment_management.takeover_equipment_info as takeover left join equipment_management.equipment as e
        on e.id = takeover.equipment_id
        WHERE  takeover.status!= ?
        and (? is null or takeover.username LIKE CONCAT('%',?,'%'))
        and (? is null or takeover.take_over_person LIKE CONCAT('%',?,'%'))
        and (? is null or takeover.type = ?)
        and (? is null or takeover.status= ?)

        LIMIT ? OFFSET ?
         ;"""
        val con = databaseConnection.getConnection()
        val pst= con.prepareStatement(sql)
    pst.setInt(1,-1);
    pst.setString(2,searchTakeOverRequest.username);
    pst.setString(3,searchTakeOverRequest.username);
    pst.setString(4,searchTakeOverRequest.takeOverPerson);
    pst.setString(5,searchTakeOverRequest.takeOverPerson);
    pst.setString(6,searchTakeOverRequest.Type);
    pst.setString(7,searchTakeOverRequest.Type);
    pst.setString(8,searchTakeOverRequest.status);
    pst.setString(9,searchTakeOverRequest.status);
    pst.setInt(10,searchTakeOverRequest.limit);
    pst.setInt(11,offset);

    val rs = pst.executeQuery
    while(rs.next){
    val e = TakeOver(id=rs.getString("id"),
      deviceId =rs.getString("device_id") ,
      name = rs.getString("name"),
      username =rs.getString("username") ,
      takeOverTime =rs.getString("take_over_time") ,
      status=rs.getString("status"),
      verifier=rs.getString("verifier"),
      takeOverPerson =rs.getString("take_over_person") ,
      Type=rs.getString("type"),
      message=rs.getString("message"),
      cost=rs.getString("cost"),
      createdBy=rs.getString("created_by"),
      createdTime=rs.getString("created_time"),
      updatedBy=rs.getString("updated_time"),
      updatedTime=rs.getString("updated_time"),
      metadataInfo = toMap(rs.getString("metadata_info")));

      takeOverList.add(e);
    }
    con.close()
    return takeOverList;
  }
  @throws[SQLException]
  def countBySearchTakeOver(username:String,takeOverPerson:String,Type:String,status:String):Int={
    val sql=
      """
        |SELECT count(*) as  total
        |FROM takeover_equipment_info as temp
        | WHERE
        |        (? is null or temp.username LIKE CONCAT('%',?,'%'))
        |        and (? is null or temp.take_over_person LIKE CONCAT('%',?,'%'))
        |        and (? is null or temp.type = ?)
        |        and (? is null or temp.status= ?);
        |""".stripMargin
        val con =databaseConnection.getConnection()
        val pst=con.prepareStatement(sql)
    pst.setString(1,username);
    pst.setString(2,username);
    pst.setString(3,takeOverPerson);
    pst.setString(4,takeOverPerson);
    pst.setString(5,Type);
    pst.setString(6,Type);
    pst.setString(7,status);
    pst.setString(8,status);
    val rs = pst.executeQuery
    var total =0
    while ( rs.next) {
      total = rs.getInt("total")
    }
    con.close();
    return total
  }
  def searchTakeOverById(takeOver:Int): TakeOver = {
    val sql = """
      SELECT *
      FROM equipment_management.takeover_equipment_info as temp
      WHERE  temp.id = ?;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, takeOver)
    val rs = pst.executeQuery()
    var result :TakeOver = null
    while ( rs.next) {
      result = TakeOver(id=rs.getString("id"),
        equipmentId =rs.getString("equipment_id") ,
        username =rs.getString("username") ,
        takeOverTime =rs.getString("take_over_time") ,
        status=rs.getString("status"),
        verifier=rs.getString("verifier"),
        takeOverPerson =rs.getString("take_over_person") ,
        Type=rs.getString("type"),
        message=rs.getString("message"),
        cost=rs.getString("cost"),
        createdBy=rs.getString("created_by"),
        createdTime=rs.getString("created_time"),
        updatedBy=rs.getString("updated_time"),
        updatedTime=rs.getString("updated_time"));
    }
    con.close();
    return result
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
  def add(e: TakeOver): Int={

    val sql = """INSERT INTO takeover_equipment_info (equipment_id, username, take_over_time,status,verifier,
             take_over_person,metadata_info,type,
            message,cost,created_by,created_time,updated_by,updated_time)
      VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,e.equipmentId)
    pst.setString(2, e.username)
    pst.setString(3,e.takeOverTime )
    pst.setString(4, e.status)
    pst.setString(5,e.verifier)
    pst.setString(6,e.takeOverPerson )
    pst.setString(7,
      s"""
         |{"files": ${JSON.write(e.metadataInfo)}}
         |""".stripMargin)
    pst.setString(8, e.Type)
    pst.setString(9,e.message)
    pst.setString(10,e.cost)
    pst.setString(11, e.createdBy)
    pst.setLong(12,System.currentTimeMillis())
    pst.setString(13, e.updatedBy)
    pst.setString(14,e.updatedTime)
    val rs = pst.executeUpdate()
    con.close();
    return rs
  }
  @throws[SQLException]
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
  def deleteById(takeOverId:Int):Int={
    val sql="UPDATE takeover_equipment_info SET status = -1 WHERE  id = ?;"
    val con = databaseConnection.getConnection()
    val pst=con.prepareStatement(sql)
    pst.setInt(1,takeOverId)
    val rs=pst.executeUpdate()
    con.close()
    return rs
  }
  def updateById(e:TakeOver):Int={
    var uploadFile :String =null
    if(e.metadataInfo != null)
      uploadFile = s"""
                      |{"files": ${JSON.write(e.metadataInfo)}}
                      |""".stripMargin

    val sql=
      """UPDATE takeover_equipment_info
          SET  equipment_id = if(? is not null, ?,equipment_id) , username = if(? is not null, ?,username),
           take_over_time = if(? is not null, ?,take_over_time),status = if(? is not null, ?,status),
           verifier = if(? is not null, ?,verifier),take_over_person = if(? is not null, ?, take_over_person),
           type = if(? is not null, ?,type),
           message = if(? is not null,?,message),cost = if(? is not null,?,cost), created_by = if(? is not null, ?,created_by),
           created_time = if(? is not null, ?,created_time),
           updated_by = if(? is not null, ?,updated_by),updated_time = if(? is not null, ?,updated_time),
           metadata_info = if(? is not null, ?,metadata_info)
           WHERE status != ? and id = ?;"""
          var con = databaseConnection.getConnection()
          val pst= con.prepareStatement(sql)
    pst.setString(1,e.equipmentId);
    pst.setString(2,e.equipmentId);
    pst.setString(3,e.username);
    pst.setString(4,e.username);
    pst.setString(5,e.takeOverTime);
    pst.setString(6,e.takeOverTime);
    pst.setString(7,e.status);
    pst.setString(8,e.status);
    pst.setString(9,e.verifier);
    pst.setString(10,e.verifier);
    pst.setString(11,e.takeOverPerson);
    pst.setString(12,e.takeOverPerson);
    pst.setString(13,e.Type);
    pst.setString(14,e.Type);
    pst.setString(15,e.message);
    pst.setString(16,e.message);
    pst.setString(17,e.cost);
    pst.setString(18,e.cost);
    pst.setString(19,e.createdBy);
    pst.setString(20,e.createdBy);
    pst.setString(21,e.createdTime);
    pst.setString(22,e.createdTime);
    pst.setString(23,e.updatedBy);
    pst.setString(24,e.updatedBy);
    pst.setString(25,e.updatedTime);
    pst.setString(26,e.updatedTime);
    pst.setString(27,uploadFile);
    pst.setString(28,uploadFile);
    pst.setInt(29,-1);
    pst.setString(30,e.id);
    val rs = pst.executeUpdate()
    con.close();
    return rs

  }

}
