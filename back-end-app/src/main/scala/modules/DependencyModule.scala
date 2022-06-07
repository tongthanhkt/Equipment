package modules

import com.twitter.inject.TwitterModule
import services.CRUDEquipmentService

import javax.inject.Singleton

object DependencyModule extends TwitterModule{
  @Singleton
  def provideCRUDEquipmentService: CRUDEquipmentService = {

    new CRUDEquipmentService
  }
}
