package controllers

import com.google.common.primitives.Bytes
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import utils.DataConnection
import com.google.gson.Gson
import com.twitter.concurrent.Offer.never.?
import com.twitter.finagle.http.exp.Multipart
import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.finatra.http.request.RequestUtils
import com.twitter.io.{Buf, Reader}
import models.{DeleteEquipmentRequest, Equipment, Page, SearchEquipmentByIdRequest, SearchEquipmentsResponse, UploadFile}
import services.CRUDEquipmentService

import java.io.{ByteArrayInputStream, File, IOException, ObjectInputStream, PrintWriter}
import java.nio.ByteBuffer
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
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
      val takeover_status = req.getParam("take_over_status",null)
      val device_status = req.getParam("device_status",null)
      val current_page  = req.getIntParam("page",1)
      val limit = req.getIntParam("size",5)
      var crud_equipment_service = new CRUDEquipmentService
      println(keyword)
      println(category)
      println(takeover_person)
      println(current_page)
      println(limit)
      try {
        val total_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,device_status)

        val total_takeover_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,"1",device_status)
        val total_inventory_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,"0",device_status)
        val total_damaged_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,"2")
        val total_lost_equipment = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,"0")
        var nPages:Int = total_equipments/limit;
        if(total_equipments%limit>0)
          nPages+=1
        var pageNumbers = new util.ArrayList[Page]
        for( i <- 1 to nPages){

          pageNumbers.add(Page(i,i==current_page));
        }
        val offset = (current_page-1)*limit

        val result:util.ArrayList[Equipment] =crud_equipment_service.search(keyword,category,takeover_person,takeover_status,device_status,limit,offset);
        response.ok.body(SearchEquipmentsResponse(equipments = result,
          empty = result.length==0,totalEquipments= total_equipments,
          totalTakeOverEquipments = total_takeover_equipments,
          totalInventoryEquipments = total_inventory_equipments,
          totalDamagedEquipments = total_damaged_equipments ,
          totalLostEquipment = total_lost_equipment,pageNumbers = pageNumbers,firstPage = +current_page==1,
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

    get("/:id"){req: SearchEquipmentByIdRequest =>{
      val equipment_id = req.id
      try {
        val result = (new CRUDEquipmentService).searchById(equipment_id)
        if (result==null)
          response.noContent
        else response.ok.body(result)


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body(ex)

        }
      }

    }

    }

//    post("/update/:id"){req:TestUpdateRequest=>{
//      val equipment_id = req.id
//      val entity: TestUpdate = req.entity
//
//      try {
//        val result = (new CRUDEquipmentService).updateById(equipment_id,entity)
//        if (result ==1)
//          response.created.body(s"Delete equipment with id = $equipment_id successfully. ")
//        else
//          response.internalServerError.
//            body("Cannot delete equipment. ")
//
//
//      } catch {
//        case ex: SQLException =>{
//          println("Error exception")
//          response.internalServerError.
//            body(ex)
//
//        }
//      }
//    }}

    post("/add"){req:Equipment=>{

      try {
        val result = (new CRUDEquipmentService).add(req)
        if (result ==1)
          response.created.body(s"Add equipment successfully. ")
        else
          response.internalServerError.
            body("Cannot add equipment. ")


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body(ex)

        }
      }
    }}

    put("/update"){req:Equipment=>{

      try {
        val result = (new CRUDEquipmentService).updateById(req)
        if (result ==1)
          response.created.body(s"Update equipment successfully. ")
        else
          response.internalServerError
      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError
        }
      }
    }}
  }


//  post("/milu"){request: Request=> {
//
//    val map :Map[String, MultipartItem] = RequestUtils.multiParams(request)
//    val result  = map.get("file")
//    println(map)
//    //println((map.get("name").get.data.map(_.toChar)).mkString)
//    println((map.get("price").get.data.map(_.toChar)).mkString)
//
//    try {
//
//      //Files.write(Paths.get("name.txt"),map.get("name").get.data, StandardOpenOption.CREATE)
//     Files.write(Paths.get("test.jpg"),result.get.data, StandardOpenOption.CREATE)
//      val in = new ObjectInputStream(new ByteArrayInputStream(map.get("price").get.data.))
//      val alert = in.readObject()
//      in.close()
//      println(alert)
//    } catch {
//      case ex: Exception =>{
//        println(ex)
//
//
//      }
//    }
//   //req.body
//
//    response.ok("success")
//  }}







}
