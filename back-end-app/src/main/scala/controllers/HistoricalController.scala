package controllers

import com.twitter.finatra.http.Controller
import models.{ConvertString, FixEquipment, GetRecordByIdRequest,  HistoricalKey, HistoricalRecord, Page, SearchFixEquipmentResponse, SearchHistoricalRequest, SearchHistoricalResponse}
import services.{CRUDEquipmentService, CRUDFixEquipmentService, HistoricalService}

import java.util
import javax.inject.Inject

class HistoricalController @Inject()(
                                     historicalService: HistoricalService,
                                     convertString: ConvertString) extends Controller{
  prefix("/historical"){
    get("/search"){request : SearchHistoricalRequest =>{
    try {
      val totalRecords  = historicalService.countBySearch(request)
      var nPages:Int = totalRecords/request.size;
      val currentPage = request.page
      if(totalRecords%request.size>0)
        nPages+=1
      val offset = (currentPage-1)*request.size
      val recordKeys:util.ArrayList[HistoricalKey] =historicalService.search(request,offset);
      var result = new util.ArrayList[HistoricalRecord]
      for (i <- 0 until(recordKeys.size()) ){
        if(recordKeys.get(i).typeAction==1){
          val record = historicalService.getTakeOverRecord(recordKeys.get(i).idAction)
          if(record!=null)
            result.add(record)
        }
        if(recordKeys.get(i).typeAction==2){
          val record = historicalService.getTakeBackRecord(recordKeys.get(i).idAction)
          if(record!=null)
            result.add(record)
        }
        if(recordKeys.get(i).typeAction==3){
          val record = historicalService.getFixEquipmentRecord(recordKeys.get(i).idAction)
          if(record!=null)
            result.add(record)
        }
      }
      response.ok.body(SearchHistoricalResponse(records = result,nPages =nPages))
    } catch {
      case ex: Exception =>{
        println(ex)
        response.internalServerError.jsonError(ex.getMessage)
      }
    }
  }}

    get("/count_total"){request : SearchHistoricalRequest =>{
      try {
        val totalRecords  = historicalService.countBySearch(request)
        response.ok.json(
          s"""{
             |"total" : $totalRecords
             |}""".stripMargin)
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    get("/detail/:type_action/:id_action"){request:GetRecordByIdRequest=>{
      try {
        val typeAction = request.typeAction
        val idAction = request.idAction
        var record : HistoricalRecord = null
        if(typeAction==1){
          record = historicalService.getTakeOverRecord(idAction)
        }
        if(typeAction==2){
          record = historicalService.getTakeBackRecord(idAction)
        }
        if(typeAction==3){
          record = historicalService.getFixEquipmentRecord(idAction)
        }
        if (record != null)
          response.ok.body(record)
        else
          response.internalServerError.jsonError(s"Cannot get record from id=$idAction and type=$typeAction")
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}
  }

}
