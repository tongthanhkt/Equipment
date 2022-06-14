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

    get("/get_file/:file_name"){request: Request =>{

      val fileName = request.getParam("file_name")

      try {
        val file = fileService.getFile(fileName)
        if (file.exists())
          response.ok.file(file).header("File-Extension",FilenameUtils.getExtension(fileName))
        else
          response.badRequest.jsonError("File does not exist")
      } catch {
        case ex: Exception =>{
          println(ex)
          response.internalServerError.jsonError(ex.getMessage)
        }
      }
    }}

    post("/upload_images"){request: Request =>{


      val map :Map[String, MultipartItem] = RequestUtils.multiParams(request)

      try {

        var checkImages = fileService.checkImagesUpload(map);
        if (checkImages == -1){
          response.badRequest.jsonError("Only selected images")
        }
        else if (checkImages == 0){
          response.badRequest.jsonError("Only selected image <= 5MB")
        }
        else if (checkImages == 1){
          var uploadFiles : Map[String, UploadFile] = fileService.uploadFiles(map);

          if (uploadFiles.isEmpty){
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

    delete("/delete") {request: Request =>{
      val fileName = request.getParam("file_name")

      try {
        if (fileService.deleteFile(fileName) ){
          response.ok.body("Delete file successfully")
        }
        else response.badRequest.jsonError("File does not exist")
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
