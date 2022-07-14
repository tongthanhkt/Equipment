package models

import com.twitter.finatra.http.annotations.QueryParam

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
                              actionType: Int,
                              message: String = null,
                              cost: String = null,
                              createdBy: String = null,
                              createdTime: String = null,
                              updatedBy: String = null,
                              updatedTime: String = null,
                              metadataInfo: Map[String, UploadFile]  = null,
                              reason : String= null,
                              takeOverStatus : String =null
                            )

case class SearchHistoricalRequest(
                                      @QueryParam performer: String = null,
                                      @QueryParam equipmentId: String = null,
                                      @QueryParam typeAction: String = null,
                                      @QueryParam page: Int = 1,
                                      @QueryParam limit: Int = 10,

                                    )

case class SearchHistoricalResponse(
                                       records: util.ArrayList[HistoricalRecord],
                                       empty: Boolean,
                                       nPages: Int,
                                       pageNumbers: util.ArrayList[Page],
                                       firstPage: Boolean,
                                       lastPage: Boolean,
                                       previousPage: Int,
                                       nextPage: Int
                                     )

case class HistoricalKey (
                         idAction: Int,
                         typeAction : Int
                         )
