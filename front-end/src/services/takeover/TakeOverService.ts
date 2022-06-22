import http from "@/http-common";
import TakeOverRecord from "@/types/TakeOverRecord";
class   TakeOverService {
 
  add(data: TakeOverRecord) {
    return http.post("/take_over/add", data);
  }
  getRecordsBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/take_over/list?${queryParams}`);
  }
  update(data: TakeOverRecord) {
    return http.put("/take_over/update", data);
  }
  getRecordById(id: number) {
    return http.get(`/take_over/${id}`);
  }
  deleteById(id: number) {
    return http.delete(`/take_over/delete?id=${id}`);
  }
  
}
export default new TakeOverService();
