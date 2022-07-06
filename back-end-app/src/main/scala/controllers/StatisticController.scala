package scala.controllers
import com.twitter.finatra.http.Controller
import models._
import services.{StatisticService, UserService}

import java.util
import javax.inject.Inject
class StatisticController @Inject()(statisticService: StatisticService,
                                    convertString: ConvertString)extends Controller{

    get("/statistic"){request:StatisticRequest=>
      println(request)
      try {
        if(convertString.isLong(request.fromDate)&& convertString.isLong(request.fromDate)){
          val fromDate :Long = convertString.toLong(request.fromDate).get
          val toDate :Long = convertString.toLong(request.toDate).get
          val importEquipmentRecordsByCategory : util.ArrayList[ImportEquipmentRecord]
          = statisticService.statisticImportEquipmentByCategory(fromDate, toDate)
          val takeOverEquipmentRecordsByCategory : util.ArrayList[TakeOverEquipmentRecord]
          = statisticService.statisticTakeOverEquipmentByCategory(fromDate, toDate)
          val takeBackEquipmentRecordsByCategory : util.ArrayList[TakeBackEquipmentRecord]
          = statisticService.statisticTakeBackEquipmentByCategory(fromDate, toDate)
          val fixedEquipmentRecordsByCategory : util.ArrayList[FixedEquipmentRecord]
          = statisticService.statisticFixedEquipmentByCategory(fromDate, toDate)
          val importEquipmentRecord : ImportEquipmentRecord
          = statisticService.statisticImportEquipment(fromDate, toDate)
          val takeOverEquipmentRecord : Int
          = statisticService.statisticTakeOverEquipment(fromDate, toDate)
          val takeBackEquipmentRecord : Int
          = statisticService.statisticTakeBackEquipment(fromDate, toDate)
          val fixedEquipmentRecord : Int
          = statisticService.statisticFixedEquipment(fromDate, toDate)

          if(importEquipmentRecordsByCategory == null || takeBackEquipmentRecordsByCategory == null
          || takeOverEquipmentRecordsByCategory == null || fixedEquipmentRecordsByCategory == null
          || importEquipmentRecord == null || takeOverEquipmentRecord == -1 ||
          takeBackEquipmentRecord == -1 || fixedEquipmentRecord == -1)
            response.internalServerError.jsonError("Cannot statistic!!!")
          else {
            var result : Map[Int,Record] =Map()
            val recordTotal = Record(totalImportEquipments = importEquipmentRecord.totalImportEquipments,
              totalImportEquipmentsCost = importEquipmentRecord.totalImportEquipmentsCost,
              totalTakeOverEquipments = takeOverEquipmentRecord,totalTakeBackEquipments = takeBackEquipmentRecord,
              totalFixedEquipment = fixedEquipmentRecord)
            result = result.+(0 -> recordTotal )
            println(importEquipmentRecordsByCategory)
            println(takeOverEquipmentRecordsByCategory)
            println(takeBackEquipmentRecordsByCategory)
            println(fixedEquipmentRecordsByCategory)
            var i = 0
            for (i <- 0 until  importEquipmentRecordsByCategory.size()){
              result = result.+(importEquipmentRecordsByCategory.get(i).categoryId ->
                Record(totalImportEquipments = importEquipmentRecordsByCategory.get(i).totalImportEquipments,
                totalImportEquipmentsCost = importEquipmentRecordsByCategory.get(i).totalImportEquipmentsCost,
                totalTakeOverEquipments = takeOverEquipmentRecordsByCategory.get(i).totalTakeOverEquipments,
                  totalTakeBackEquipments = takeBackEquipmentRecordsByCategory.get(i).totalTakeBackEquipments,
                totalFixedEquipment = fixedEquipmentRecordsByCategory.get(i).totalFixedEquipments) )
            }
            response.ok.body(Statistic(records = result))
          }


        }
        else
          response.badRequest.jsonError("The start day or end day for statistic is incorrect!")

      }catch {
        case ex: Exception => {
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }

}
