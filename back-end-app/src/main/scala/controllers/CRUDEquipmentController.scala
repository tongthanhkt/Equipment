package controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import utils.DataConnection
import com.google.gson.Gson
import com.twitter.concurrent.Offer.never.?
import models.{DeleteEquipmentRequest, Equipment, Page, SearchEquipmentResponse}
import services.CRUDEquipmentService

import java.sql.SQLException
import java.util
import javax.inject.Inject
import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`



class CRUDEquipmentController  extends Controller {

  prefix("/equipment"){
    get("/search"){req: Request => {
      val keyword = req.getParam("keyword",null)
      val category = req.getParam("category_id",null)
      val takeover_person = req.getParam("take_over_person",null)
      val current_page  = req.getIntParam("page",1)
      val limit = req.getIntParam("size",5)
      println(keyword)
      println(category)
      println(takeover_person)
      println(current_page)
      println(limit)
      try {
               val totalEquipment = (new CRUDEquipmentService).countBySearch(keyword,category,takeover_person)
        var nPages:Int = totalEquipment/limit;
        if(totalEquipment%limit>0)
          nPages+=1
        var pageNumbers = new util.ArrayList[Page]
        for( i <- 1 to nPages){

          pageNumbers.add(Page(i,i==current_page));
        }
        val offset = (current_page-1)*limit

        val result:util.ArrayList[Equipment] =(new CRUDEquipmentService).search(keyword,category,takeover_person,limit,offset);
        response.ok.body(SearchEquipmentResponse(equipments = result,
          empty = result.length==0,pageNumbers = pageNumbers,firstPage = +current_page==1,
          lastPage = +current_page==nPages,previousPage = +current_page-1,nextPage = +current_page+1))


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body("Error SQLException")

        }
      }
    }}
    delete("/del"){req: DeleteEquipmentRequest => {
      val equipment_id = req.equipment_id

      try {
        val result = (new CRUDEquipmentService).deleteById(equipment_id)
        if (result ==1)
          response.created.body(s"Delete equipment with id = $equipment_id successfully. ")
        else
          response.internalServerError.
            body("Cannot delete equipment. ")


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body(ex)

        }
      }
    }

    }
  }


  get("/milu"){req:Request=> {

    try {
      (new DataConnection()).getConnection();
      println("Connection successfully")


    } catch {
      case ex: Exception =>{
        println("Error exception")
        response.
          ok.

          body("Error exception")

      }
    }

  }}





  post("/name") { req:Request =>
  {


    response.
      ok.
      header("Access-Control-Allow-Origin", "*").
      body("helow")

  }


  }

}
