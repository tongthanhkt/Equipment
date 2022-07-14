

import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import controllers.{CRUDCategoryController, CRUDEquipmentController, CRUDFixEquipmentController, CRUDTakeOverController, FileController, HistoricalController}
import filters.{CORSFilter, CommonExceptionMapping}
import modules.{CustomJacksonModule, DependencyModule}

import scala.controllers.{CRUDTakeBackController, StatisticController, UserController}

object FinatraServerMain extends FinatraServer

class FinatraServer extends HttpServer{

  override protected def modules: Seq[Module] = Seq(DependencyModule)

  override def defaultHttpPort: String = ":8887"

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CORSFilter](beforeRouting = true)
      .add[CRUDEquipmentController]
      .add[CRUDTakeOverController]
      .add[CRUDTakeBackController]
      .add[FileController]
      .add[UserController]
      .add[CRUDFixEquipmentController]
      .add[CRUDCategoryController]
      .add[StatisticController]
      .add[HistoricalController]
      .exceptionMapper[CommonExceptionMapping]

  }

  protected override def afterPostWarmup(): Unit = {
    super.afterPostWarmup()
    println("Service setup successfully")
    //test
  }
}