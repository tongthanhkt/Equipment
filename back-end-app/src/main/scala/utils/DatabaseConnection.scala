package utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource


import java.sql.{Connection, SQLException}

class DatabaseConnection {

  val cfg = new HikariConfig("src/main/resources/db.properties")
  val ds = new HikariDataSource(cfg)

  def  getConnection (): Connection ={
    try {

      var con=ds.getConnection()

      return con

      } catch {
      case ex: Exception =>{
        println("Error exception")
        return null
      }
    }
  }

}




