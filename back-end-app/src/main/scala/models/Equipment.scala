package models



import java.util

case class Page(value:Int,isCurrent:Boolean)

case class Equipment(
                             id: Int,
                             device_id: String,
                             name: String,
                             start_status: Int,
                             price: Double,
                             import_date: Long,
                             takeover_status: Int,
                             category_id: Int,
                             device_status : Int,
                             created_by: String,
                             created_time: Long,
                             updated_by: String,
                             updated_time: Long,
                             takeover_person_id: String,
                             takeover_person_name : String
                           )

case class SearchEquipmentResponse(equipments: util.ArrayList[Equipment],
                                   empty: Boolean,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int)

case class DeleteEquipmentRequest(equipment_id:Int)

