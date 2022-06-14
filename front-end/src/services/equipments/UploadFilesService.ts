import http from "@/http-common";
class UploadFilesService {
  upload(file: File) {
    const formData = new FormData();
    formData.append(file.name, file, file.name);
    return http.post("/file/upload_images", formData);
  }
  deleteFile(file_url: string) {
    return http.delete(`/file/delete?file_url=${file_url}`);
  }
}
export default new UploadFilesService();
