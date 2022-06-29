import http from "@/http-common";
import TakeBackRecord from "@/types/TakeBackRecord";
class   TakeBackService {
 
  add(data: Object) {
    return http.post("/take_back/add", data);
  }
  getRecordsBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/take_back/list?${queryParams}`)
  }
  update(data: Object) {
    return http.put("/take_back/update", data);
  }
  getRecordById(id: number) {
    return http.get(`/take_back/${id}`);
  }
  deleteById(id: number) {
    return http.delete(`/take_back/delete?id=${id}`);
  }
  
}
export default new TakeBackService();
