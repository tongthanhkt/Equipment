import http from "@/http-common";
class UploadFilesService {
  upload(file: File, onUploadProgress: any) {
    console.log(file);
    const formData = new FormData();
    formData.append(file.name, file);
    console.log(formData);
    return http.post("/photo/5/upload", formData);
  }
  getFileImage() {}
}
export default new UploadFilesService();
