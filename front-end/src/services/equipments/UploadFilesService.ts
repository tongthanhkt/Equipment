import http from "@/http-common";
class UploadFilesService {
  upload(file: File) {
    const formData = new FormData();
    formData.append(file.name, file, file.name);
    return http.post("/file/upload_images", formData);
  }
  deleteFile(file_name: string) {
    return http.delete(`/file/delete?file_name=${file_name}`);
  }
}
export default new UploadFilesService();
