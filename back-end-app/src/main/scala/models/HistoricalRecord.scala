package models

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import java.util

case class HistoricalRecord (
                              id: String = null,
                              equipmentId: String = null,
                              deviceId:String=null,
                              equipmentName:String=null,
                              user: String = null,
                              actionTime: String = null,
                              status: String = null,
                              verifier: String = null,
                              performer: String = null,
                              typeAction: Int,
                              message: String = null,
                              cost: String = null,
                              createdBy: String = null,
                              createdTime: String = null,
                              updatedBy: String = null,
                              updatedTime: String = null,
                              metadataInfo: Map[String, UploadFile]  = null,
                              reason : String= null,
                              takeOverStatus : String =null
                            ){

}

case class SearchHistoricalRequest(
                                      @QueryParam performer: String = null,
                                      @QueryParam equipmentId: String = null,
                                      @QueryParam typeAction: String = null,
                                      @QueryParam page: Int = 1,
                                      @QueryParam size: Int = 10,

                                    )

case class GetRecordByIdRequest(
                                    @RouteParam idAction: Int,
                                    @RouteParam typeAction: Int
                                  )

case class SearchHistoricalResponse(
                                       records: util.ArrayList[HistoricalRecord],
                                       nPages: Int
                                     )

case class HistoricalKey (
                         idAction: Int,
                         typeAction : Int
                         )
