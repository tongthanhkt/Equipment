package controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models.{ConvertString, DeleteFixEquipmentRequest, Equipment, FixEquipment, Page, SearchEquipmentsResponse, SearchFixEquipmentByIdRequest, SearchFixEquipmentRequest, SearchFixEquipmentResponse}
import services.{CRUDEquipmentService, CRUDFixEquipmentService}

import java.util
import javax.inject.Inject

class CRUDFixEquipmentController @Inject()(fixEquipmentService: CRUDFixEquipmentService,
                                           equipmentService: CRUDEquipmentService,
                                           convertString: ConvertString) extends Controller{

  prefix("/fix_equipment"){
//    get("/search"){request:SearchFixEquipmentRequest =>{
//
//        try {
//          val totalFixEquipmentRecords  = fixEquipmentService.countBySearch(request)
//          var nPages:Int = totalFixEquipmentRecords/request.limit;
//          val currentPage = request.page
//          if(totalFixEquipmentRecords%request.limit>0)
//            nPages+=1
//          var pageNumbers = new util.ArrayList[Page]
//          for( i <- 1 to nPages){
//
//            pageNumbers.add(Page(i,i==currentPage));
//          }
//          val offset = (currentPage-1)*request.limit
//          val result:util.ArrayList[FixEquipment] =fixEquipmentService.search(request,offset);
//          response.ok.body(SearchFixEquipmentResponse(fixEquipmentList = result,
//            empty = result.isEmpty,nPages =nPages,
//            pageNumbers = pageNumbers,firstPage = +request.page==1,
//            lastPage = +currentPage == nPages,previousPage = +currentPage-1,nextPage = +currentPage+1))
//        } catch {
//          case ex: Exception =>{
//            println(ex)
//            response.internalServerError.jsonError(ex.getMessage)
//          }
//        }
//
//    }}
//
//    get("/:id"){request: SearchFixEquipmentByIdRequest =>{
//
//      try {
//        val result = fixEquipmentService.searchById(request.id)
//        if (result==null)
//          response.noContent
//        else response.ok.body(result)
//      } catch {
//        case ex: Exception =>{
//          println(ex)
//          response.internalServerError.jsonError(ex.getMessage)
//
//        }
//      }
//
//    }
//    }

    post("/add"){request:FixEquipment =>{
      println(request)
      try {
        val check = request.checkFitInsert(convertString)
        if (check.isEmpty){
          if (equipmentService.searchById(convertString.toInt(request.equipmentId).get) != null){
              val deviceStatus = equipmentService.checkDeviceStatus(request.equipmentId)
              val takeOverStatus = equipmentService.checkTakeOverStatus(request.equipmentId)

            if (takeOverStatus == 1)
              response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} has been taken over. Cannot fix! ")
            else if (takeOverStatus==0){
              if (deviceStatus == 0)
                response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} has been lost. Cannot fix! ")
              else if (deviceStatus == 1)
                response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} is working normally. Cannot fix! ")
              else if (deviceStatus == 3 )
                response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} was sold. Cannot fix! ")
              else if (deviceStatus == 2){

                if (request.status == "0" && fixEquipmentService.isFixingExists(request.equipmentId,request.id)){
                  response.badRequest.jsonError(s"Equipment is fixing. ")
                }
                else {
                  val fixEquipmentId = fixEquipmentService.add(request)
                  if (fixEquipmentId >0) {

                    response.created.json(
                      s"""|{
                          |"msg": "Add successfully!!"
                          |}""".stripMargin)
                  }
                  else if (fixEquipmentId == -1) {
                    response.badRequest.jsonError(s"Cannot fix equipment with id = ${request.equipmentId}. Try again!!")
                  }
                  else
                    response.internalServerError.jsonError(s"Cannot fix equipment with id = ${request.equipmentId}. ")
                }


              }
              else
                response.internalServerError.jsonError(s"Cannot fix equipment with id = ${request.equipmentId}. ")
            }
            else response.internalServerError.jsonError(s"Cannot fix equipment with id = ${request.equipmentId}. ")

          }
          else
            response.badRequest.jsonError(s"Cannot find equipment with id = ${request.equipmentId}. ")
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

    put("/update"){request:FixEquipment=>{
      println(request)
      try {
        val check = request.checkFitUpdate(convertString)

        if (check.isEmpty){

            if(fixEquipmentService.searchById(request.id)!= null){
              if (equipmentService.searchById(convertString.toInt(request.equipmentId).get) != null){
                val deviceStatus = equipmentService.checkDeviceStatus(request.equipmentId)
                val takeOverStatus = equipmentService.checkTakeOverStatus(request.equipmentId)
                if (takeOverStatus == 1)
                  response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} has been taken over. Cannot update fix record! ")
                else if (takeOverStatus==0){
                  if (deviceStatus == 0)
                    response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} has been losy. Cannot update fix record! ")
                  else if (deviceStatus == 3 )
                    response.badRequest.jsonError(s"Equipment with id = ${request.equipmentId} was sold. Cannot fix! ")
                  else if (deviceStatus == 1 || deviceStatus == 2 )
                    {
                      println("debug")
                      if (request.status == "0" && fixEquipmentService.isFixingExists(request.equipmentId,request.id)){
                        response.badRequest.jsonError(s"Equipment is fixing. ")
                      }
                      else {
                        val result = fixEquipmentService.updateById(request)
                        if (result >0) {

                          response.created.json(s"""{
                                                   |"msg" : "Update equipment successfully."
                                                   |}""".stripMargin)
                        }
                        else if (result == -1) {
                          response.badRequest.jsonError(s"Cannot update the fix_equipment record with equipment id = ${request.equipmentId}. Try again!!")
                        }
                        else
                          response.internalServerError.jsonError(s"Cannot update the fix_equipment record with equipment id = ${request.equipmentId}. ")
                      }
                  }
                  else
                    response.internalServerError.jsonError(s"Cannot update the fix_equipment record with equipment id = ${request.equipmentId}. ")
                }
                else response.internalServerError.jsonError(s"Cannot update the fix_equipment record with equipment id = ${request.equipmentId}. ")
              }
              else
                response.badRequest.jsonError(s"Cannot find equipment with id = ${request.equipmentId}. ")
            }
            else
              response.badRequest.jsonError(s"Cannot find fixing_equipment record with equipment id = ${request.equipmentId}. ")

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

    delete("/delete") { request: DeleteFixEquipmentRequest => {
      val id = request.id;
      try {
        val checkStatus = fixEquipmentService.checkStatusById(id)
        if(checkStatus == -2){
          response.badRequest.jsonError("Can not delete the fix equipment record. The fix equipment record id not exist.")

        }
        else if (checkStatus == 0 ){
          response.badRequest.jsonError("Can not delete the fixing equipment record. ")
        }
        else  if (checkStatus == 1 || checkStatus == 2){
          val result = fixEquipmentService.deleteById(id)
          if (result == 1) {
            response.created.json(s"""{
                                     |"msg" : "Delete take over with id =$id successfully ."
                                     |}""".stripMargin)
          }
          else response.internalServerError.jsonError("Can not delete the fix equipment record. ")
        }
        else {
          response.internalServerError.jsonError("Can not delete the fix equipment record. ")
        }

      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }

  }}
}}
