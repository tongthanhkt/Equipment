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
import com.twitter.util.jackson.JSON
import models.{CountEquipmentsResponse, DeleteEquipmentRequest, DeleteImageByIdRequest, Equipment, Page, SearchEquipmentByIdRequest, SearchEquipmentsResponse, UploadFile}
import services.CRUDEquipmentService

import java.io.{ByteArrayInputStream, File, IOException, ObjectInputStream, PrintWriter}
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
import java.sql.SQLException
import java.util
import javax.inject.Inject
import net.liftweb.json._
import org.apache.commons.io.FilenameUtils

import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`
import scala.tools.nsc.classpath.FileUtils
import scala.util.control.Breaks.{break, breakable}



class CRUDEquipmentController  extends Controller {

  prefix("/equipment"){

    get("/search"){req: Request => {
      val keyword = req.getParam("keyword",null)
      val category = req.getParam("category_id",null)
      val takeover_person = req.getParam("take_over_person",null)
      val takeover_status = req.getParam("take_over_status",null)
      val device_status = req.getParam("device_status",null)
      val current_page  = req.getIntParam("page",1)
      val limit = req.getIntParam("size",10)
      var crud_equipment_service = new CRUDEquipmentService

      try {
        val total_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,device_status)
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
         pageNumbers = pageNumbers,firstPage = +current_page==1,
          lastPage = +current_page==nPages,previousPage = +current_page-1,nextPage = +current_page+1))


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body("Error SQLException")

        }
      }
    }}

    get("/count_total_by_filters"){req: Request => {
      val keyword = req.getParam("keyword",null)
      val category = req.getParam("category_id",null)
      val takeover_person = req.getParam("take_over_person",null)
      val takeover_status = req.getParam("take_over_status",null)
      val device_status = req.getParam("device_status",null)

      var crud_equipment_service = new CRUDEquipmentService

      try {
        val total_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,device_status)

        val total_takeover_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,"1",device_status)
        val total_inventory_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,"0",device_status)
        val total_damaged_equipments = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,"2")
        val total_lost_equipment = crud_equipment_service.countBySearch(keyword,category,takeover_person,takeover_status,"0")

        response.ok.body(CountEquipmentsResponse(totalEquipments= total_equipments,
          totalTakeOverEquipments = total_takeover_equipments,
          totalInventoryEquipments = total_inventory_equipments,
          totalDamagedEquipments = total_damaged_equipments ,
          totalLostEquipment = total_lost_equipment))


      } catch {
        case ex: SQLException =>{
          println("Error exception")
          response.internalServerError.
            body("Error SQLException")

        }
      }
    }}

    delete("/delete"){req: DeleteEquipmentRequest => {
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
          response.internalServerError

        }
      }
    }

    }

    get("/:id"){request: SearchEquipmentByIdRequest =>{
      val equipment_id = request.id
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

    get("/photo/:equipment_id/:filename"){request: DeleteImageByIdRequest =>{
      val equipment_id = request.equipment_id
      var crud_equipment_service = new CRUDEquipmentService
      try {
        val result = crud_equipment_service.searchMetaDataById(equipment_id)
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

    post("/photo/:equipment_id/upload"){request: DeleteImageByIdRequest =>{
      val equipment_id = request.equipment_id
      var crud_equipment_service = new CRUDEquipmentService
      try {
        val result = crud_equipment_service.searchMetaDataById(equipment_id)
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

    delete("/photo/:equipment_id/:filename/delete"){req: DeleteEquipmentRequest => {
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
          response.internalServerError

        }
      }
    }

    }
    post("/add"){request:Equipment =>{
      var crud_equipment_service = new CRUDEquipmentService
      try {
          val add_row = crud_equipment_service.add(request)
          if (add_row == 1 )
            response.created.body(s"Add equipment successfully. ")
          else
            response.internalServerError.
              body("Cannot add images for equipment.")
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError

        }
      }
    }}

    put("/update"){request:Equipment=>{

      try {
        val result = (new CRUDEquipmentService).updateById(request)
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

  post("/milu"){request: Request => {

    var crud_equipment_service = new CRUDEquipmentService
    val map :Map[String, MultipartItem] = RequestUtils.multiParams(request)
    val equipment_info  = new String(map.get("information").get.data, StandardCharsets.UTF_8)
    implicit val formats = DefaultFormats

    try {
      val equipment = parse(equipment_info).extract[Equipment]
      var files_upload= new util.ArrayList[UploadFile]

      val dirname :String = System.getProperty("user.dir")+"/images/";
      var check_files_upload = crud_equipment_service.checkFilesUpload(map);
      if (check_files_upload == -1 ){
        response.accepted("Only selected images")
      }
      else if (check_files_upload == 0 ){
        response.accepted("File too large")
      }
      else if (check_files_upload == 1 ){
        val add_row = crud_equipment_service.add(equipment)
        if (add_row == 1){
          val new_equipment_id = crud_equipment_service.getIdEquipmentDESC();
          for (key <- map.keys){
            if (key!= "information" ){
              val image = map.get(key)
              val filename :String = "image"+new_equipment_id+"_"+System.currentTimeMillis();
              val extension = FilenameUtils.getExtension(key)
              val basename = filename.concat(".").concat(extension)
              val path = Paths.get(dirname,basename)
              val data = image.get.data
              val size = data.length
              println(image.get.contentType.get.split("/")(0))
              Files.write(path,map.get(key).get.data, StandardOpenOption.CREATE)
              files_upload.add(UploadFile(fileUrl = path.toString,fileName = filename,size = size ,fileExtension=extension))

            }

          }
          val update_row = crud_equipment_service.updateMetaDataById(files_upload,new_equipment_id);
          if (update_row == 1 )
            response.created.body(s"Add equipment successfully. ")
          else
            response.internalServerError.
              body("Cannot add images for equipment.")
        }
        else
          response.internalServerError.
            body("Cannot add equipment.")
      }


    } catch {
      case ex: Exception =>{
        println(ex)
        response.internalServerError

      }
    }
  }}







}
