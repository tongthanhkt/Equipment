package controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models.{ConvertString, CountEquipmentsResponse, DeleteEquipmentRequest, DeleteImageByIdRequest, Equipment, Page, SearchEquipmentByIdRequest, SearchEquipmentsResponse, SearchRequest, UploadFile}
import services.{CRUDCategoryService, CRUDEquipmentService}

import java.util
import javax.inject.Inject

class CRUDEquipmentController @Inject() (
                                       equipmentService: CRUDEquipmentService,
                                       convertString: ConvertString,
                                       categoryService: CRUDCategoryService
                                       )  extends Controller {

  prefix("/equipment"){

    get("/search"){request: SearchRequest => {
      println(request)
      try {
        val totalEquipments  = equipmentService.countBySearch(request.keyword,request.categoryId,request.takeOverPerson,request.takeOverStatus,request.deviceStatus,null)
        var nPages:Int = totalEquipments/request.limit;
        val currentPage = request.page
        if(totalEquipments%request.limit>0)
          nPages+=1
        var pageNumbers = new util.ArrayList[Page]
        for( i <- 1 to nPages){

          pageNumbers.add(Page(i,i==currentPage));
        }
        val offset = (currentPage-1)*request.limit
        val result:util.ArrayList[Equipment] =equipmentService.search(request,offset,null);
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
        val totalEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,deviceStatus,null)
        val totalTakeOverEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,"1",deviceStatus,null)
        val totalInventoryEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,"0",deviceStatus,null)
        val totalDamagedEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,"2",null)
        val totalLostEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,"0",null)
        val totalCompensationEquipments = equipmentService.countBySearch(keyword,category,takeOverPerson,takeOverStatus,"0","0")
        response.ok.body(CountEquipmentsResponse(totalEquipments= totalEquipments,
          totalTakeOverEquipments = totalTakeOverEquipments,
          totalInventoryEquipments = totalInventoryEquipments,
          totalDamagedEquipments = totalDamagedEquipments ,
          totalLostEquipments = totalLostEquipments,
          totalCompensationEquipments=totalCompensationEquipments))
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
        val isTakeOver = equipmentService.checkTakeOverStatus(equipmentId.toString)
        if (isTakeOver == 0){
          val result = equipmentService.deleteById(equipmentId)
          if (result ==1)
            response.created.json(s"""{
                                     |"msg" : Delete equipment with id = $equipmentId successfully.
                                     |}""".stripMargin)
          else
            response.internalServerError.
              jsonError("Cannot delete equipment. ")
        }
        else if (isTakeOver == 1)
          response.badRequest.jsonError("Cannot delete equipment has been taken over.")
        else
          response.internalServerError.
            jsonError("Cannot delete equipment. ")


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
          if(categoryService.checkAvailableCategory(request.categoryId)){
            if (equipmentService.checkDeviceIdInsert(request.deviceId)){

              val result = equipmentService.add(request)
              if (result >0) {

                response.created.json(
                  s"""|id: $result
                      |""".stripMargin)
              }
              else if (result == -1) {
                response.badRequest.jsonError("Device_id of equipment already exists")
              }
              else
                response.internalServerError.jsonError("Can not add new equipment")
            }
            else
              response.badRequest.jsonError("Device_id of equipment already exists")
          }
          else
            response.badRequest.jsonError("Category of new equipment is not available")
        }
        else
          response.badRequest.json(
            s"""{
               |"errors" : [${JSON.write(check)}]
               |}""".stripMargin)
      } catch {
        case ex: Exception =>{
          //println(ex)
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
         if(categoryService.checkAvailableCategory(request.categoryId)){
           if (equipmentService.checkDeviceIdUpdate(request.id,request.deviceId)){
             val e = equipmentService.searchById(convertString.toInt(request.id).get)
             if (e == null) {
               response.badRequest.jsonError(s"Cannot find equipment with id = ${request.id}. ")
             }
             else {
               val result = equipmentService.updateById(request)
               if (result ==1)
                 response.created.json(s"""{
                                          |"msg" : Update equipment successfully.
                                          |}""".stripMargin)
               else response.internalServerError.jsonError("Can not update equipment")
             }
           }
           else response.badRequest.jsonError("Device_id of equipment already exists")
         }
         else
           response.badRequest.jsonError("Category you want to update is not available")
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

}
