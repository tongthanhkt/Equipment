import http from "@/http-common";
class   TakeOverService {
 
  add(data: any) {
    return http.post("/take_over/add", data);
  }
  getRecordsBySearch(queryParams: Object) {
    console.log(queryParams);
    return http.get(`/take_over/list`,{params: queryParams});
  }
  update(data: any) {
    return http.put("/take_over/update", data);
  }
  getRecordById(id: any) {
    return http.get(`/take_over/${id}`);
  }
  deleteById(id: String) {
    return http.delete(`equipment/delete?id=${id}`);
  }
  
}
export default new TakeOverService();
