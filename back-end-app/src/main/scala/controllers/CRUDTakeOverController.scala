package controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models._
import services.CRUDTakeOverService

import java.util
import javax.inject.Inject

class CRUDTakeOverController @Inject()(takeOverService: CRUDTakeOverService,
                                       convertString: ConvertString) extends Controller {
  prefix("/take_over") {
    get("/list") { request: SearchTakeOverRequest => {
      print(request)
      try {
        val totalTakeOverList = takeOverService.countBySearchTakeOver(request.username, request.takeOverPerson, request.typeTakeOver, request.status, request.equipmentId)
        var nPages: Int = totalTakeOverList / request.limit;
        val currentPage = request.page;
        if (totalTakeOverList % request.limit > 0) {
          nPages += 1;
        }
        var pageNumbers = new util.ArrayList[Page]()
        for (i <- 1 to nPages) {

          pageNumbers.add(Page(i, i == currentPage));
        }
        val offset = (currentPage - 1) * request.limit
        val result: util.ArrayList[TakeOver] = takeOverService.searchTakeOver(request, offset);
        response.ok.body(SearchTakeOverResponse(takeOverList = result, empty = result.isEmpty, nPages = nPages,
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
    delete("/delete") { request: DeleteTakeOverRequest => {
      val takeOverId = request.id;
      try {
        val checkTakeOver = takeOverService.checkDeleteTakeOver(request.id);
        if(checkTakeOver==0) response.internalServerError.jsonError(" Thông tin bàn giao không tồn tại hoặc đã được xác nhận. Không thể xóa!")
        else if (checkTakeOver == -1) response.internalServerError.jsonError(" Thông tin bàn giao thiết bị hiện tại. Không thể xóa!")
        else if (checkTakeOver == 1)  {
          val result = takeOverService.deleteById(takeOverId)
          println(result)
          if (result == 1) {
            response.created.json(s"""{
                                     |"msg" : Delete take over with id =$takeOverId successfully.
                                     |}""".stripMargin)
          }
          else response.internalServerError.jsonError("Can not delete take over. Take over id not exist.")
        }
        else response.internalServerError.jsonError("Can not delete take over.")
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    get("/:id") { request: SearchTakeOverByIdRequest => {
      println(request);
      val takeOverId = request.id;
      try {
        val result = takeOverService.searchTakeOverById(takeOverId)
        if (result == null)
          response.internalServerError.jsonError("Thiết bị không tồn tại !")
        else response.ok.body(result)
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    post("/add") { request: TakeOver => {
      try {
        print(request)
        val check = request.checkDataInsert(convertString);
        if (check.isEmpty) {
          if (takeOverService.checkUserExist(request.username) == 0) {
            response.internalServerError.jsonError("Username not exists.")
          } else if (takeOverService.checkUserExist(request.takeOverPerson) == 0) {
            response.internalServerError.jsonError("Take over person not exists.")
          } else if (takeOverService.checkUserExist(request.verifier) == 0) {
            response.internalServerError.jsonError("Verifier not exists.")
          }  else if (takeOverService.checkUserExist(request.createdBy) == 0) {
            response.internalServerError.jsonError("Created by not valid. ")
          }else if (takeOverService.checkequipmentForTakeOver(request.equipmentId) == -1) {
            response.internalServerError.jsonError("Equipment have been taken over.")
          }else if (takeOverService.checkequipmentForTakeOver(request.equipmentId) == 0) {
            response.internalServerError.jsonError("Equipment not exist.")
          }else if (takeOverService.checkequipmentForTakeOver(request.equipmentId) == -2) {
            response.internalServerError.jsonError("Equipment was lost.")
          }else if (takeOverService.checkequipmentForTakeOver(request.equipmentId) == -3) {
            response.internalServerError.jsonError("Equipment was damaged.")
          }
          else {
            val result = takeOverService.add(request)
            if (result == 1) {
              val takeOverId = takeOverService.getIdTakeOverDESC();
              response.created.json(
                s"""|id: $takeOverId
                    |""".stripMargin)
            } else response.internalServerError.jsonError("Can not add new takeOver")
          }
        }
        else
          response.badRequest.json(
            s"""{
               |
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
    put("/update") { request: TakeOver => {
      try{
        println(request)
        val check = request.checkDataUpdate(convertString);
        if (check.isEmpty) { // check data
          println(takeOverService.checkUserExist(request.verifier))
          if (takeOverService.checkUserExist(request.username) == 0) {
            response.internalServerError.jsonError("Username not exists.")
          } else if (takeOverService.checkUserExist(request.takeOverPerson) == 0) {
            response.internalServerError.jsonError("Take over person not exists.")
          } else if (takeOverService.checkUserExist(request.verifier) == 0) {
            response.internalServerError.jsonError("Verifier not exists.")
          }else if (takeOverService.checkUserExist(request.updatedBy) == 0) {
            response.internalServerError.jsonError("Updater not exists.")
          }
          else{
            val result = takeOverService.updateById(request)
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
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    get("/equipment/:id") {
        request: SearchTakeOverByIdEquipmentRequest => {
          val equipmentId = request.id;
          try {
            val result = takeOverService.searchTakeOverByEquipmentId(equipmentId)
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
        val result : util.ArrayList[User] = takeOverService.searchUser(request);
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
