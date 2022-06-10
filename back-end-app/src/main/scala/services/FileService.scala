package services

import com.twitter.finatra.http.fileupload.MultipartItem
import models.UploadFile
import org.apache.commons.io.FilenameUtils

import java.io.File
import java.nio.file.{Files, Paths, StandardOpenOption}


class FileService {

  @throws[Exception]
  def deleteFile(fileUrl :String):Boolean = {
    val fileTemp = new File(fileUrl)
    if (fileTemp.exists) {
      fileTemp.delete()
      return true
    }
    false
  }

  def getFile(fileUrl: String): File ={
    new File(fileUrl)
  }

  def checkImagesUpload (files: Map[String, MultipartItem]): Int ={
    for (key <- files.keys){
      if (key!= "information" ){
        val image = files.get(key)
        if ( image.get.data.length > 5000000 ){
          return 0
        }
        else if (image.get.contentType.get.split("/")(0) != "image"){
          return -1
        }
      }
    }
    return 1
  }

  @throws[Exception]
  def uploadFiles(map: Map[String, MultipartItem]): Map[String, UploadFile] ={
    var uploadFiles : Map[String, UploadFile] = Map();
    val dirName :String = System.getProperty("user.dir")+"/images/";
    for (key <- map.keys){
      val file = map.get(key).get
      val fileName :String = "image"+System.currentTimeMillis();
      val extension = FilenameUtils.getExtension(file.filename.get)
      val baseName = fileName.concat(".").concat(extension)
      val path = Paths.get(dirName,baseName)
      val data = file.data
      val size = data.length
      println(file.contentType.get.split("/")(0))
      Files.write(path,data, StandardOpenOption.CREATE)
      uploadFiles = uploadFiles + (baseName -> UploadFile(file_url = path.toString,file_name = baseName,size = size ,file_extension=extension))
    }
    return uploadFiles
  }

}
