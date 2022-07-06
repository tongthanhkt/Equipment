package services

import com.twitter.util.jackson.JSON
import models._
import net.liftweb.json.{DefaultFormats, parse}
import utils.DatabaseConnection

import java.sql.SQLException
import java.util
import javax.inject.Inject

class UserService  @Inject()(databaseConnection:DatabaseConnection) {
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
}
