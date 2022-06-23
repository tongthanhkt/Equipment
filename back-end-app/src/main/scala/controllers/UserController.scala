package scala.controllers
import com.twitter.finatra.http.Controller
import models.{SearchUserRequest, SearchUserResponse, User}
import services.UserService

import java.util
import javax.inject.Inject
class UserController @Inject()(userService:UserService)extends Controller{
 prefix("/user"){
   get("/get_users"){request:SearchUserRequest=>
     try {
       val result : util.ArrayList[User] = userService.searchUser(request);
       response.ok.body(SearchUserResponse(userList = result))
     }catch {
       case ex: Exception => {
         println(ex)
         response.internalServerError.jsonError(ex.getMessage)
       }
     }
   }
 }
}
