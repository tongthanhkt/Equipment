package services

import com.twitter.finatra.http.fileupload.MultipartItem
import models.{UploadFile}
import org.apache.commons.io.FilenameUtils

import java.io.File
import java.nio.file.{Files, Paths, StandardOpenOption}


class FileService {
  val dirName :String = System.getProperty("user.dir")+"/UploadedFile/";
  @throws[Exception]
  def deleteFile(fileName :String):Boolean = {
    val fileUrl = dirName.concat(fileName)
    val fileTemp = new File(fileUrl)
    if (fileTemp.exists) {
      fileTemp.delete()
      return true
    }
    false
  }

  def getFile(fileName: String): File ={
    val fileUrl = dirName.concat(fileName)
    new File(fileUrl)
  }
  // thanh
  def checkFilesUpload(files: Map[String,MultipartItem]):Int={
    for(key<-files.keys){
      if(key!="infomation"){
        val file = files.get(key)
        if(file.get.data.length>50000000){
          return 0
        }
      }
    }
    return 1
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
  //thanh
  @throws[Exception]
  def uploadFiles(map: Map[String, MultipartItem]): Map[String, UploadFile] ={
    var uploadFiles : Map[String, UploadFile] = Map();
    for (key <- map.keys){
      val file = map.get(key).get
      val fileName :String = file.filename.get;
      val extension = FilenameUtils.getExtension(file.filename.get)
      //val baseName = fileName.concat(".").concat(extension)
      val path = Paths.get(dirName,fileName)
      val data = file.data
      val size = data.length

      Files.write(path,data, StandardOpenOption.CREATE)
      uploadFiles = uploadFiles + (fileName -> UploadFile(file_url = "http://localhost:8887/file/get_file/"+fileName,file_name = fileName,size = size ,file_extension=extension))
    }
    return uploadFiles
  }

}
