import http from "@/http-common";
class UploadFilesService {
  upload(file: any) {
    console.log(file);
    let formData = new FormData();
    formData.append("upload.jpg", file);
    console.log(formData.values);
    return http.post("/photo/5/upload", formData, {
      headers: {
        "Conntent-Type": "multipart/form-data",
      },
    });
  }
  getFiles() {
    return http.get("/files");
  }
}
export default new UploadFilesService();
