package modules

import com.twitter.inject.TwitterModule
import models.ConvertString
import services.{CRUDEquipmentService, FileService}
import utils.DatabaseConnection

import javax.inject.Singleton

object DependencyModule extends TwitterModule{
  @Singleton
  def provideCRUDEquipmentService (databaseConnection: DatabaseConnection,
                                   convertString: ConvertString): CRUDEquipmentService = {

    new CRUDEquipmentService(databaseConnection,convertString)
  }

  @Singleton
  def provideFileService ():FileService  = {
    new FileService()
  }

  @Singleton def provideDatabaseConnection: DatabaseConnection = {

    new DatabaseConnection
  }

  @Singleton
  def provideConvertString ():ConvertString  = {
    new ConvertString()
  }
}
