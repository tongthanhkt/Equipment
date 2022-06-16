import http from "@/http-common";
class EquipmentDataService {
  getImage(file_url: String) {
    return http.get(`/file/get_file?file_url=${file_url}`);
  }
  addData(data: any) {
    return http.post("/equipment/add", data);
  }
  getAllEquipments(queyParams: string) {
    console.log(queyParams);
    return http.get(`/equipment/search?${queyParams}`);
  }
  updateEquipment(data: any) {
    return http.put("/equipment/update", data);
  }
  getEquipmentDetail(id: any) {
    return http.get(`equipment/${id}`);
  }
  deleteEquipment(id: String) {
    return http.delete(`equipment/delete?id=${id}`);
  }
  searchEquipment(keyword: String) {
    return http.get(`equipment/search?keyword=${keyword}&size=10`);
  }
  getCountTotal() {
    return http.get(`equipment/count_total`);
  }
  getSumOfDamagedEquipment() {}
}
export default new EquipmentDataService();
