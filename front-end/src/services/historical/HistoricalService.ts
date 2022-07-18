import http from "@/http-common";


class   HistoricalService {
 
  getRecordsBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/historical/search?${queryParams}`)
  }
  
  getRecordById(id_action: number,type_aciton : number) {
    return http.get(`/historical/detail/${type_aciton}/${id_action}`);
  }
  
}
export default new HistoricalService();