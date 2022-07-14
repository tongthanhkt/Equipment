package controllers

import com.twitter.finatra.http.Controller
import models.{ConvertString, FixEquipment, HistoricalKey, HistoricalRecord, Page, SearchFixEquipmentResponse, SearchHistoricalRequest, SearchHistoricalResponse}
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
      var nPages:Int = totalRecords/request.limit;
      val currentPage = request.page
      if(totalRecords%request.limit>0)
        nPages+=1
      var pageNumbers = new util.ArrayList[Page]
      for( i <- 1 to nPages){

        pageNumbers.add(Page(i,i==currentPage));
      }
      val offset = (currentPage-1)*request.limit
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
      response.ok.body(SearchHistoricalResponse(records = result,
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

  }

}
