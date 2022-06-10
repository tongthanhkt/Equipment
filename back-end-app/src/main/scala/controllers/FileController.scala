package controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.fileupload.MultipartItem
import com.twitter.finatra.http.request.RequestUtils
import models.UploadFile
import services.FileService
import javax.inject.Inject
import org.apache.commons.io.FilenameUtils

class FileController @Inject() (fileService: FileService) extends Controller {

  prefix("/file"){

    get("/:file_url"){request: Request =>{

      val fileUrl = request.getParam("file_url")

      try {
        response.ok.file(fileService.getFile(fileUrl)).header("File-Extension",FilenameUtils.getExtension(fileUrl))
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    post("/upload_images"){request: Request =>{

      val equipmentId = request.getIntParam("equipment_id")
      val map :Map[String, MultipartItem] = RequestUtils.multiParams(request)

      try {

        var checkImages = fileService.checkImagesUpload(map);
        if (checkImages == -1){
          response.internalServerError.body("Only selected images")
        }
        else if (checkImages == 0){
          response.internalServerError.body("Only selected image <= 5MB")
        }
        else if (checkImages == 1){
          var uploadFiles : Map[String, UploadFile] = fileService.uploadFiles(map);

          if (uploadFiles.isEmpty  ){
            response.internalServerError.jsonError("Can not add images")
          }
          else  response.created.body(uploadFiles)
        }

      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }

    }
    }

    delete("/:file_url/delete") {request: Request =>{
      val fileUrl = request.getParam("file_url")

      try {
        if (fileService.deleteFile(fileUrl) ){
          response.ok.body("Delete file successfully")
        }
        else response.internalServerError.jsonError(
          """
            |msg: "Can not delete file"
            |""".stripMargin)
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }

    }
    }
  }

}
