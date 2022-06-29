package scala.controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models.{ConvertString, Page, SearchUserRequest, SearchUserResponse, TakeOver, User}

import java.util
import javax.inject.Inject
import scala.models.{DeleteTakeBackRequest, SearchTakeBackByIdEquipmentRequest, SearchTakeBackByIdRequest, SearchTakeBackRequest, SearchTakeBackResponse, TakeBack}
import scala.services.CRUDTakeBackService

class CRUDTakeBackController @Inject()(takeBackService:CRUDTakeBackService,convertString:ConvertString) extends Controller{
  prefix("/take_back") {
    get("/list") { request: SearchTakeBackRequest => {
      print(request)
      try {
        val totalTakeBackList = takeBackService.countBySearchTakeBack(request.username, request.takeBackPerson, request.typeTakeBack, request.status, request.equipmentId)
        var nPages: Int = totalTakeBackList / request.limit;
        val currentPage = request.page;
        if (totalTakeBackList % request.limit > 0) {
          nPages += 1;
        }
        var pageNumbers = new util.ArrayList[Page]()
        for (i <- 1 to nPages) {

          pageNumbers.add(Page(i, i == currentPage));
        }
        val offset = (currentPage - 1) * request.limit
        val result: util.ArrayList[TakeBack] = takeBackService.searchTakeBack(request, offset);
        response.ok.body(SearchTakeBackResponse(takeBackList = result, empty = result.isEmpty, nPages = nPages,
          pageNumbers = pageNumbers, firstPage = +request.page == 1,
          lastPage = +currentPage == nPages, previousPage = +currentPage - 1, nextPage = +currentPage + 1));
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    delete("/delete") { request: DeleteTakeBackRequest => {
      val takeBackId = request.id;
      try {
        val result = takeBackService.deleteById(takeBackId)
        if (result == 1) {
          response.created.body(s"Delete take over with id =$takeBackId successfully .")
        }
        else response.internalServerError.body("Can not delete take back. Take back id not exist.")
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    get("/:id") { request: SearchTakeBackByIdRequest => {
      val takeBackId = request.id;
      try {
        val result = takeBackService.searchTakeBackById(takeBackId)
        if (result == null)
          response.noContent
        else response.ok.body(result)
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    post("/add") { request: TakeBack => {
      print(request)
      try {
        print(request)
        val check = request.checkDataInsert(convertString);
        if (check.isEmpty) {
          if (takeBackService.checkUserExist(request.username) == 0) {
            response.internalServerError.jsonError("Username not exists.")
          } else if (takeBackService.checkUserExist(request.takeBackPerson) == 0) {
            response.internalServerError.jsonError("Take back person not exists.")
          } else if (takeBackService.checkUserExist(request.verifier) == 0) {
            response.internalServerError.jsonError("Verifier not exists.")
          } else if (takeBackService.checkUserExist(request.createdBy) == 0) {
            response.internalServerError.jsonError("Created by not exists. ")
          } else if (takeBackService.checkequipmentForTakeBack(request.equipmentId)==0){
            response.internalServerError.jsonError("Request equipment invalid !! ")
          }
          else {
            val result = takeBackService.add(request)
            if (result == 1) {
              val takeBackId = takeBackService.getIdTakeBackDESC();
              response.created.json(
                s"""|id: $takeBackId
                    |""".stripMargin)
            } else response.internalServerError.jsonError("Can not add new take back")
          }
        }
        else
          response.badRequest.json(
            s"""{
               |"errors" : [${JSON.write(check)}]
               |}""".stripMargin)
      }
      catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    put("/update") { request: TakeBack => {
      try{
        println(request)
        val e = takeBackService.searchTakeBackById(convertString.toInt(request.id).get) /// check ID, check status bàn giao
        println(e);
        val check = request.checkDataUpdate(convertString);
        if (check.isEmpty) { // check data
          if (takeBackService.checkUserExist(request.username) == 0) {
            response.internalServerError.jsonError("Username not exists.")
          } else if (takeBackService.checkUserExist(request.takeBackPerson) == 0) {
            response.internalServerError.jsonError("Take over person not exists.")
          } else if (takeBackService.checkUserExist(request.verifier) == 0) {
            response.internalServerError.jsonError("Verifier not exists.")
          }else if (takeBackService.checkUserExist(request.updatedBy) == 0) {
            response.internalServerError.jsonError("Verifier not exists.")
          }
          else{
            val result = takeBackService.updateById(request)
            if (result == 1)
              response.created.body(s"Update take over successfully !")
            else response.badRequest.json(
              s"""{
                 |"errors" : [${JSON.write()}]
                 |}"""
            )
          }
        }
        else{
          response.badRequest.json(
            s"""{
               |"errors" : [${JSON.write(check)}]
               |}""".stripMargin)

        }

      }
      catch{
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError("Bàn giao không tồn tại hoặc không thể chỉnh sửa vì trạng thái đã xác thực ")
        }
      }
    }
    }
    get("/equipment/:id") {
      request: SearchTakeBackByIdEquipmentRequest => {
        val equipmentId = request.id;
        try {
          val result = takeBackService.searchTakeBackByEquipmentId(equipmentId)
          if (result == null)
            response.noContent
          else response.ok.body(result)
        } catch {
          case ex: Exception => {
            println(ex)
            response.internalServerError.jsonError(ex.getMessage)
          }
        }
      }
    }
    get("/get_user"){request:SearchUserRequest=>
      try {
        val result : util.ArrayList[User] = takeBackService.searchUser(request);
        response.ok.body(SearchUserResponse(userList = result))
      }catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }

    }

  }
}
