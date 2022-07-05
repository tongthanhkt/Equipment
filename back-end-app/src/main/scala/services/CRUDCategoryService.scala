package services

import com.twitter.util.jackson.JSON
import models._
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject

class CRUDCategoryService  @Inject()(databaseConnection:DatabaseConnection) {

  @throws[Exception]
  def search(searchCategoryRequest: SearchCategoryRequest):util.ArrayList[Category]={
    val categoryList = new util.ArrayList[Category]()
    val sql =
      """
        SELECT * FROM equipment_management.category c
        |where c.status != -1
        |	and (? is null or c.name LIKE CONCAT('%',?,'%'))
        | and (? is null or c.status= ?) ;
        |""".stripMargin
    val con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setString(1,searchCategoryRequest.name);
    pst.setString(2,searchCategoryRequest.name);
    pst.setString(3,searchCategoryRequest.status);
    pst.setString(4,searchCategoryRequest.status);
    val rs = pst.executeQuery
    while(rs.next){
      val e =
        Category(id=rs.getString("id"),name=rs.getString("name"),
          status = rs.getString("status"),
          createdBy = rs.getString("created_by"),createdTime = rs.getString("created_time"),
          updatedBy = rs.getString("updated_by"), updatedTime = rs.getString("updated_time"))
      categoryList.add(e)
    }
    con.close()
    categoryList;
  }

  @throws[Exception]
  def searchById(id: Int): Category = {

    val sql =
      """
      SELECT * FROM equipment_management.category c
      where c.status != -1 and c.id = ?;"""

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)

    pst.setInt(1, id)
    val rs = pst.executeQuery()
    var result: Category = null
    while (rs.next) {
      result = Category(id = rs.getString("id"), name = rs.getString("name"),
        status = rs.getString("status"),
        createdBy = rs.getString("created_by"), createdTime = rs.getString("created_time"),
        updatedBy = rs.getString("updated_by"), updatedTime = rs.getString("updated_time"))
    }
    con.close();
    println(result)
    result
  }

  @throws[Exception]
  def deleteById(id:Int): Int = {

    val sql = "UPDATE equipment_management.category SET status = -1 WHERE id = ?;"

    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, id)
    val rs = pst.executeUpdate()
    con.close();
    rs
  }

  @throws[Exception]
  def add(c:Category): Int={

      val sql = """CALL insert_category (?,?,?,?);"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)

      pst.setString(1, c.name)
      pst.setString(2,c.status )
      pst.setString(3,c.createdBy)
      pst.setLong(4,System.currentTimeMillis())


      val rs = pst.executeQuery()

      var id =0
      while ( rs.next) {
        id = rs.getInt("id")
      }
      con.close();
      id


  }

  @throws[Exception]
  def updateById(c:Category): Int={
      val sql =
        """UPDATE category
          SET  name = if(? is not null, ?,name),
           status = if(? is not null, ?,status),
           updated_by = ?, updated_time = ?
          WHERE status != ? and id = ?;"""

      var con = databaseConnection.getConnection()
      val pst = con.prepareStatement(sql)
      pst.setString(1, c.name)
      pst.setString(2, c.name)
      pst.setString(3, c.status)
      pst.setString(4, c.status)
      pst.setString(5, c.updatedBy)
      pst.setLong(6, System.currentTimeMillis())
      pst.setInt(7, -1)
      pst.setString(8, c.id)
      val rs = pst.executeUpdate()
      con.close();
      rs

  }

  @throws[Exception]
  def checkToDelete(categoryId: Int): Boolean = {
    val sql =
      """
      select count(*) as isExists
      from equipment e
      where  e.device_status != ? and e.category_id = ?;
      """
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, -1)
    pst.setInt(2, categoryId)
    val rs = pst.executeQuery
    var isExists = 0
    while (rs.next) {
      isExists = rs.getInt("isExists")
    }
    con.close();
    if (isExists == 0)
      true
    else
      false
  }

  @throws[Exception]
  def checkAvailableCategory(categoryId: String): Boolean = {
    val sql =
      """
      select count(*) as isExists
      from category
	    where  status = ? and id = ?;
      """
    var con = databaseConnection.getConnection()
    val pst = con.prepareStatement(sql)
    pst.setInt(1, 1)
    pst.setString(2, categoryId)
    val rs = pst.executeQuery
    var isExists = 0
    while (rs.next) {
      isExists = rs.getInt("isExists")
    }
    con.close();
    if (isExists == 1)
      true
    else
      false
  }
}



