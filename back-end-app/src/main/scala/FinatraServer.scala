

import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import controllers.CRUDEquipmentController
import filters.CORSFilter

object FinatraServerMain extends FinatraServer


class FinatraServer extends HttpServer{

  //override protected def modules: Seq[Module] = super.modules :+ ExampleModule

  override def defaultHttpPort: String = ":8887"



  override def configureHttp(router: HttpRouter): Unit = {
    router

      .add[CORSFilter, CRUDEquipmentController]

  }

  protected override def afterPostWarmup(): Unit = {
    super.afterPostWarmup()
    println("Service setup successfully")
  }
}