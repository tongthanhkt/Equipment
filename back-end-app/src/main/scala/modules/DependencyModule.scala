package modules

import com.twitter.inject.TwitterModule
import services.CRUDEquipmentService
import utils.DatabaseConnection

import javax.inject.Singleton

object DependencyModule extends TwitterModule{
  @Singleton
  def provideCRUDEquipmentService (databaseConnection: DatabaseConnection): CRUDEquipmentService = {

    new CRUDEquipmentService(databaseConnection)
  }
  @Singleton def provideDatabaseConnection: DatabaseConnection = {

    new DatabaseConnection
  }
}
