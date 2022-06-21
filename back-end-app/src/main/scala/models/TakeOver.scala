package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.util

case class TakeOver(
                     id: String = null,
                     equipmentId: String = null,
                     username: String = null,
                     takeOverTime: String = null,
                     status: String = null,
                     verifier: String = null,
                     takeOverPerson: String = null,
                     metadataInfo: Map[String, UploadFile]  = null,
                     Type: String = null,
                     message: String = null,
                     cost: String = null,
                     createdBy: String = null,
                     createdTime: String = null,
                     updatedBy: String = null,
                     updatedTime: String = null,

                   )

case class SearchTakeOverRequest(
                                  @QueryParam username: String = null,
                                  @QueryParam takeOverPerson: String = null,
                                  @QueryParam typeTakeOver: String = null,
                                  @QueryParam status: String = null,
                                  @QueryParam equipmentId: String = null,
                                  @QueryParam page: Int = 1,
                                  @QueryParam limit: Int = 10
                                )

case class SearchTakeOverResponse(
                                   takeOverList: util.ArrayList[TakeOver],empty: Boolean,
                                   nPages: Int,
                                   pageNumbers: util.ArrayList[Page],
                                   firstPage: Boolean,
                                   lastPage: Boolean,
                                   previousPage: Int,
                                   nextPage: Int
                                 )

case class SearchTakeOverByIdRequest(@RouteParam id: Int)
case class SearchTakeOverByIdEquipmentRequest(@RouteParam id: Int)
case class DeleteTakeOverRequest(@QueryParam id:Int)
