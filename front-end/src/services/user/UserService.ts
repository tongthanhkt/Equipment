import http from "@/http-common";
import User from "@/types/User";
class      UserService {
 
 
  getBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/take_over/get_user?keyword=${queryParams}`);
  }
 
  
}
export default new UserService();
