package modules

import com.twitter.inject.TwitterModule
import models.ConvertString
import services.{CRUDCategoryService, CRUDEquipmentService, CRUDFixEquipmentService, FileService, StatisticService}
import utils.DatabaseConnection

import javax.inject.Singleton

object DependencyModule extends TwitterModule{
  @Singleton
  def provideCRUDEquipmentService (databaseConnection: DatabaseConnection,
                                   convertString: ConvertString): CRUDEquipmentService = {

    new CRUDEquipmentService(databaseConnection,convertString)
  }

  @Singleton
  def provideCRUDFixEquipmentService (databaseConnection: DatabaseConnection,
                                   convertString: ConvertString): CRUDFixEquipmentService = {

    new CRUDFixEquipmentService(databaseConnection,convertString)
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

  @Singleton
  def provideCategoryService (databaseConnection: DatabaseConnection):CRUDCategoryService  = {
    new CRUDCategoryService(databaseConnection )
  }

  @Singleton
  def provideStatisticService (databaseConnection: DatabaseConnection
                                      ): StatisticService = {

    new StatisticService(databaseConnection)
  }
}
