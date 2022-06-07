package models



import com.twitter.finagle.http.exp.Multipart
import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import com.twitter.finatra.http.fileupload.MultipartItem
import io.fintrospect.parameters.{Body, Form, FormField, MultiPartFile}

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
                                    totalLostEquipment: Int,
                                    )

case class DeleteEquipmentRequest(@QueryParam equipment_id:Int)

case class SearchEquipmentByIdRequest(@RouteParam id :Int)

case class UploadFile(file_url : String,
                      file_name: String,
                      size : Long,
                      file_extension: String
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






