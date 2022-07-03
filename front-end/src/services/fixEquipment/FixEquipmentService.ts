import http from "@/http-common";
import FixEquipmentRecord from "@/types/FixEquipmentRecord";
class FixEquipmentService {
  add(data: Object) {
    return http.post("/fix_equipment/add", data);
  }
  getRecordsBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/fix_equipment/search?${queryParams}`);
  }
  update(data: Object) {
    return http.put("/fix_equipment/update", data);
  }
  getRecordById(id: number) {
    return http.get(`/fix_equipment/${id}`);
  }
  deleteById(id: number) {
    return http.delete(`/fix_equipment/delete?id=${id}`);
  }
}
export default new FixEquipmentService();
