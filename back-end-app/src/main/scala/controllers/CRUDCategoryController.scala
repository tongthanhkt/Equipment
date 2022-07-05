package controllers

import com.twitter.finatra.http.Controller
import com.twitter.util.jackson.JSON
import models.{Category, ConvertString, CountEquipmentsResponse, DeleteCategoryRequest, DeleteEquipmentRequest, DeleteImageByIdRequest, Equipment, Page, SearchCategoryByIdRequest, SearchCategoryRequest, SearchEquipmentByIdRequest, SearchEquipmentsResponse, SearchRequest, UploadFile}
import services.{CRUDCategoryService, CRUDEquipmentService}

import java.util
import javax.inject.Inject

class CRUDCategoryController @Inject() (
                                          convertString: ConvertString,
                                          categoryService: CRUDCategoryService
                                        )  extends Controller {

  prefix("/category"){

    get("/search"){request: SearchCategoryRequest => {
      println(request)
      try {

        val result:util.ArrayList[Category] =categoryService.search(request);
        response.ok.body(result)
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    delete("/delete"){request: DeleteCategoryRequest => {

      try {
        if(categoryService.checkToDelete(request.id)){
          val result = categoryService.deleteById(request.id)
          if (result ==1)
            response.created.json(s"""{
                                     |"msg" : Delete equipment with id = ${request.id} successfully.
                                     |}""".stripMargin)
          else
            response.internalServerError.
              jsonError("Cannot delete equipment. ")
        }
        else
          response.badRequest.
            jsonError(s"Category with id = ${request.id} has equipments. Cannot delete! ")


      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)

        }
      }
    }

    }

    get("/:id"){request: SearchCategoryByIdRequest =>{

      try {
        val result = categoryService.searchById(request.id)
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

    post("/add"){request:Category =>{
      println(request)
      try {
        val check = request.checkFitInsert(convertString)
        if (check.isEmpty){
            val result = categoryService.add(request)
            if (result > 0) {

              response.created.json(
                s"""|id: $result
                    |""".stripMargin)
            }
            else
              response.internalServerError.jsonError("Can not add new category")
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

    put("/update"){request:Category=>{
      println(request)
      try {
        val check = request.checkFitUpdate(convertString)

        println(check)
        if (check.isEmpty){


              val e = categoryService.searchById(convertString.toInt(request.id).get)
              if (e == null) {
                response.badRequest.jsonError(s"Cannot find category with id = ${request.id}. ")
              }
              else {
                val result = categoryService.updateById(request)
                if (result ==1)
                  response.created.json(s"""{
                                           |"msg" : Update equipment successfully.
                                           |}""".stripMargin)
                else response.internalServerError.jsonError("Can not update equipment")
              }


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
