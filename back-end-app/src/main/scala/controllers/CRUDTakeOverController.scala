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
        val totalTakeOverList = takeOverService.countBySearchTakeOver(request.username,request.takeOverPerson,request.typeTakeOver,request.status,request.equipmentId)
        var nPages:Int=totalTakeOverList/request.limit;
        val currentPage=request.page;
        if(totalTakeOverList%request.limit>0){
          nPages+=1;
        }
        var pageNumbers = new util.ArrayList[Page]()
        for( i <- 1 to nPages){

          pageNumbers.add(Page(i,i==currentPage));
        }
        val offset = (currentPage-1)*request.limit
        val result: util.ArrayList[TakeOver] = takeOverService.searchTakeOver(request,offset);
        response.ok.body(SearchTakeOverResponse(takeOverList=result, empty = result.isEmpty,nPages =nPages,
          pageNumbers = pageNumbers,firstPage = +request.page==1,
          lastPage = +currentPage == nPages,previousPage = +currentPage-1,nextPage = +currentPage+1));
      } catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    delete("/delete"){request:DeleteTakeOverRequest=>{
      val takeOverId=request.id;
      try{
        val result=takeOverService.deleteById(takeOverId)
        if(result==1){
          response.created.body(s"Delete take over with id =$takeOverId successfully .")
        }
        else response.internalServerError.body("Can not delete take over")
      }catch {
        case ex:Exception=>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}
    post("/add"){request:TakeOver=>{
      print(request)
      try{
        val result = takeOverService.add(request)
        if(result==1){
          val takeOverId=takeOverService.getIdTakeOverDESC();
          response.created.json(
            s"""|id: $takeOverId
                |""".stripMargin)
        }else response.internalServerError.jsonError("Can not add new takeOver")
      }catch{
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}
    put("/update"){request:TakeOver=>{
      try{
        print(request)

        val e = takeOverService.searchTakeOverById(convertString.toInt(request.id).get)

        if(e==null){
          response.badRequest.jsonError(s"Cannot find take over with id = ${e.id}.")
        }
        val result = takeOverService.updateById(request)
        if(result==1)
          response.created.body(s"Update take over successfully !")
        else  response.badRequest.json(
          s"""{
             |"errors" : [${JSON.write()}]
             |}"""
        )
      }
      catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }
    }
    get("/equipment/:id"){
      request:SearchTakeOverByIdEquipmentRequest=>{
        val equipmentId= request.id;
        try{
          val result=takeOverService.searchTakeOverByEquipmentId(equipmentId)
          if(result==null)
            response.noContent
          else response.ok.body(result)
        }catch {
          case ex:Exception=>{
            println(ex)
            response.internalServerError.jsonError(ex.getMessage)
          }
        }
      }
    }
  }

}
