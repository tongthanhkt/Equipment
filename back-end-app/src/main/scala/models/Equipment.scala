package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import java.io.File
import java.util

case class Page(value:Int,isCurrent:Boolean)

case class Equipment(
                             id: Int = 0,
                             deviceId: String = null,
                             name: String,
                             startStatus: Int,
                             price: Double,
                             depreciatedValue: Double,
                             depreciationPeriod: Double,
                             periodType: Int,
                             importDate: Long,
                             takeOverStatus: Int = 0 ,
                             categoryId: Int,
                             deviceStatus : Int = 0,
                             metadataInfo: Map[String, UploadFile] = Map(),
                             createdBy: String = null,
                             createdTime: Long = 0,
                             updatedBy: String = null,
                             updatedTime: Long = 0,
                             takeOverPersonId: String = null,
                             takeOverPersonName : String = null
                           )

case class SearchEquipmentsResponse(equipments: util.ArrayList[Equipment],
                                   empty: Boolean,
                                    nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int)
case class CountEquipmentsResponse(
                                    totalEquipments: Int,
                                    totalTakeOverEquipments: Int,
                                    totalInventoryEquipments: Int,
                                    totalDamagedEquipments : Int,
                                    totalLostEquipments: Int,
                                    )

case class DeleteEquipmentRequest(@QueryParam id:Int)

case class SearchEquipmentByIdRequest(@RouteParam id :Int)

case class UploadFile(file_url : String,
                      file_name: String,
                      size : Long,
                      file_extension: String
                ){

}

case class DeleteImageByIdRequest(@RouteParam equipmentId :Int,
                                  @QueryParam imageName:Int)

case class SearchRequest (@QueryParam keyword: String = null,
                           @QueryParam categoryId: String = null,
                           @QueryParam takeOverPerson: String = null,
                           @QueryParam takeOverStatus: String = null,
                            @QueryParam deviceStatus :String = null,
                           @QueryParam page: Int = 1,
                           @QueryParam limit: Int = 10)








