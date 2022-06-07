package filters

import com.twitter.finagle.http.filter.Cors.{HttpFilter, Policy}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Filter, Service, SimpleFilter}
import com.twitter.util.Future

//class CORSFilter extends SimpleFilter[Request, Response] {
//  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
//
//    service(request).map {
//      response => response.headerMap
//        .add("Access-Control-Allow-Origin", "*")
//        .add("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept,Authorization, Cookie" )
//        .add("Access-Control-Allow-Methods", "GET,HEAD,POST,DELETE,OPTIONS,PUT,PATCH")
//        .add("Access-Control-Expose-Headers", "location,link")
//        .add("Access-Control-Allow-Credentials", "true")
//        .add("Access-Control-Max-Age", "1800");
//
//
//        response
//    }
//  }}

class CORSFilter extends HttpFilter(Policy(
  allowsOrigin = { origin => Some("http://localhost:8080") },
  allowsMethods = { _ => Some(Seq("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")) },
  allowsHeaders = { _ => Some(Seq("origin", "content-type", "accept", "authorization", "X-Requested-With", "X-Codingpedia", "cookie")) },
  supportsCredentials = true)) {
}