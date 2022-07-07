import http from "@/http-common";
import User from "@/types/User";
class UserService {
  getBySearch(queryParams: String) {
    console.log(queryParams);
    return http.get(`/user/get_users?keyword=${queryParams}`);
  }
}
export default new UserService();
