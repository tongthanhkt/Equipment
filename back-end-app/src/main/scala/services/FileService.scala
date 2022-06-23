package services

import com.twitter.finatra.http.fileupload.MultipartItem
import models.{UploadFile, UploadMultiFile}
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
  def UploadMultiFiles(map:Map[String,MultipartItem]):Map[String,UploadMultiFile]={
    var uploadFiles: Map[String,UploadMultiFile]=Map();
    for(key<-map.keys){
      var file = map.get(key).get
      val fileName : String =key
      val extension = FilenameUtils.getExtension(file.filename.get)
      val baseName = fileName.concat(".").concat(extension)
      val path = Paths.get(dirName,baseName)
      val data = file.data
      val size = data.length
      Files.write(path,data,StandardOpenOption.CREATE)
      uploadFiles = uploadFiles + (baseName ->UploadMultiFile("http://localhost:8887/file/get_file/"+baseName,file_name = baseName,size = size ,file_extension=extension))
    }
    return uploadFiles;
  }
  @throws[Exception]
  def uploadFiles(map: Map[String, MultipartItem]): Map[String, UploadFile] ={
    var uploadFiles : Map[String, UploadFile] = Map();
    for (key <- map.keys){
      val file = map.get(key).get
      val fileName :String = "image"+System.currentTimeMillis();
      val extension = FilenameUtils.getExtension(file.filename.get)
      val baseName = fileName.concat(".").concat(extension)
      val path = Paths.get(dirName,baseName)
      val data = file.data
      val size = data.length

      Files.write(path,data, StandardOpenOption.CREATE)
      uploadFiles = uploadFiles + (baseName -> UploadFile(file_url = "http://localhost:8887/file/get_file/"+baseName,file_name = baseName,size = size ,file_extension=extension))
    }
    return uploadFiles
  }

}
