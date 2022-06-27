package controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models.{ConvertString, CountEquipmentsResponse, DeleteEquipmentRequest, DeleteImageByIdRequest, Equipment, Page, SearchEquipmentByIdRequest, SearchEquipmentsResponse, SearchRequest, UploadFile}
import services.CRUDEquipmentService

import java.util
import javax.inject.Inject

class CRUDEquipmentController @Inject() (
                                       equipmentService: CRUDEquipmentService,
                                       convertString: ConvertString
                                       )  extends Controller {

  prefix("/equipment"){

    get("/search"){request: SearchRequest => {
      println(request)
      try {
        val totalEquipments  = equipmentService.countBySearch(request.keyword,request.categoryId,request.takeOverPerson,request.takeOverStatus,request.deviceStatus)
        var nPages:Int = totalEquipments/request.limit;
        val currentPage = request.page
        if(totalEquipments%request.limit>0)
          nPages+=1
        var pageNumbers = new util.ArrayList[Page]
        for( i <- 1 to nPages){

          pageNumbers.add(Page(i,i==currentPage));
        }
        val offset = (currentPage-1)*request.limit
        val result:util.ArrayList[Equipment] =equipmentService.search(request,offset);
        response.ok.body(SearchEquipmentsResponse(equipments = result,
          empty = result.isEmpty,nPages =nPages,
          pageNumbers = pageNumbers,firstPage = +request.page==1,
          lastPage = +currentPage == nPages,previousPage = +currentPage-1,nextPage = +currentPage+1))
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}


    get("/count_total"){request: SearchRequest => {
      val keyword = request.keyword
      val category = request.categoryId
      val takeOverPerson = request.takeOverPerson
      val takeOverStatus = request.takeOverStatus
      val deviceStatus = request.deviceStatus
      try {
        val totalEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,deviceStatus)
        val totalTakeOverEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,"1",deviceStatus)
        val totalInventoryEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,"0",deviceStatus)
        val totalDamagedEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,"2")
        val totalLostEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,"0")
        response.ok.body(CountEquipmentsResponse(totalEquipments= totalEquipments,
          totalTakeOverEquipments = totalTakeOverEquipments,
          totalInventoryEquipments = totalInventoryEquipments,
          totalDamagedEquipments = totalDamagedEquipments ,
          totalLostEquipments = totalLostEquipments))
      } catch {
        case ex:Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    delete("/delete"){request: DeleteEquipmentRequest => {
      val equipmentId = request.id
      try {
        val result = equipmentService.deleteById(equipmentId)
        if (result ==1)
          response.created.body(s"Delete equipment with id = $equipmentId successfully. ")
        else
          response.internalServerError.
            body("Cannot delete equipment. ")


      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)

        }
      }
    }

    }

    get("/:id"){request: SearchEquipmentByIdRequest =>{
      val equipmentId = request.id
      try {
        val result = equipmentService.searchById(equipmentId)
        if (result==null)
          response.noContent
        else response.ok.body(result)
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)

        }
      }

    }
    }

    post("/add"){request:Equipment =>{
      println(request)
      try {
        val check = request.checkFitInsert(convertString)
        if (check.isEmpty){
          val result = equipmentService.add(request)
          if (result ==1) {
            val equipmentId= equipmentService.getIdEquipmentDESC();
            response.created.json(
              s"""|id: $equipmentId
                |""".stripMargin)
          }  else
            response.internalServerError.jsonError("Can not add new equipment")
        }
        else
          response.badRequest.json(
            s"""{
               |"errors" : [${JSON.write(check)}]
               |}""".stripMargin)
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    put("/update"){request:Equipment=>{
      println(request)
      try {
        val check = request.checkFitUpdate(convertString)

        println(check)
        if (check.isEmpty){
          val e = equipmentService.searchById(convertString.toInt(request.id).get)
          if (e == null) {
            response.badRequest.jsonError(s"Cannot find equipment with id = ${e.id}. ")
          }
          val result = equipmentService.updateById(request)
          if (result ==1)
            response.created.body(s"Update equipment successfully. ")
          else response.internalServerError.jsonError("Can not update equipment")
        }
        else
          response.badRequest.json(
            s"""{
              |"errors" : [${JSON.write(check)}]
              |}""".stripMargin)

      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}
  }

//  post("/milu"){request: Request => {
//
//    val map :Map[String, MultipartItem] = RequestUtils.multiParams(request)
//    implicit val formats = DefaultFormats
//    try {
//      var files_upload= new util.ArrayList[UploadFile]
//      val dirname :String = System.getProperty("user.dir")+"/images/";
//      var check_files_upload = equipmentService.checkFilesUpload(map);
//      val new_equipment_id = equipmentService.getIdEquipmentDESC();
//      var uploadfiles : Map[String, UploadFile] = Map();
//      for (key <- map.keys){
//          val image = map.get(key)
//          val filename :String = "image"+new_equipment_id+"_"+System.currentTimeMillis();
//          val extension = FilenameUtils.getExtension(key)
//          val basename = filename.concat(".").concat(extension)
//          val path = Paths.get(dirname,basename)
//          val data = image.get.data
//          val size = data.length
//          println(image.get.contentType.get.split("/")(0))
//          Files.write(path,data, StandardOpenOption.CREATE)
//        uploadfiles = uploadfiles + (filename -> UploadFile(file_url = path.toString,file_name = filename,size = size ,file_extension=extension))
//    }
//          var currentFiles :  Map[String, UploadFile] = equipmentService.searchMetaDataById(new_equipment_id)
//          currentFiles = currentFiles ++ uploadfiles;
//          val update_row = equipmentService.updateMetaDataById(currentFiles,new_equipment_id);
//          if (update_row == 1 ){
//            response.created.body(JSON.write(currentFiles))
//          }
//          else response.internalServerError.json(
//            """
//              |msg: "Can not add images"
//              |""".stripMargin)
//
//    } catch {
//      case ex: Exception =>{
//        println(ex)
//        response.internalServerError.jsonError(ex.toString)
//      }
//    }
//  }}

}
