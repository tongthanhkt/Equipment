package models



import com.twitter.finagle.http.exp.Multipart
import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import com.twitter.finatra.http.fileupload.MultipartItem
import io.fintrospect.parameters.{Body, Form, FormField, MultiPartFile}

import java.util

case class Page(value:Int,isCurrent:Boolean)

case class Equipment(
                             id: Int = 0,
                             device_id: String = null,
                             name: String,
                             start_status: Int,
                             price: Double,
                             depreciated_value: Double,
                             depreciation_period: Double,
                             period_type: Int,
                             import_date: Long,
                             takeover_status: Int = 0 ,
                             category_id: Int,
                             device_status : Int = 0,
                             created_by: String = null,
                             created_time: Long = 0,
                             updated_by: String = null,
                             updated_time: Long = 0,
                             takeover_person_id: String = null,
                             takeover_person_name : String = null
                           )

case class SearchEquipmentsResponse(equipments: util.ArrayList[Equipment],
                                   empty: Boolean,
                                   totalEquipments: Int,
                                   totalTakeOverEquipments: Int,
                                   totalInventoryEquipments: Int,
                                   totalDamagedEquipments : Int,
                                   totalLostEquipment: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int)

case class DeleteEquipmentRequest(@QueryParam equipment_id:Int)

case class SearchEquipmentByIdRequest(@RouteParam id :Int)

case class UploadFile(fileUrl : String,
                      fileName: String,
                      size : Long,
                      fileExtension: String
                )

case class DeleteImageByIdRequest(@RouteParam equipment_id :Int,
                                  @QueryParam image_name:Int)

//class UploadFilesList (){
//  var files = new util.ArrayList[UploadFile]
//
//  def addFile(file: UploadFile): Unit ={
//    files.add(file)
//  }
//}






